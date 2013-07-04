Namespace.register("com.gleasy.os.model");

/**
 * 桌面内容MODEL
 * 1. 从服务器获取桌面内容
 * 2. 维护一个内容数组，当内容更改时，需要通知监听者
 * @author xueke
 */
com.gleasy.os.model.DesktopModel = function(){
	var _this = this;
	//var dataURL = AjaxConfig.url.os+"/data.json?id="+Math.random();//数据源url
	var dataURL = AjaxConfig.url.osCore+"/user/app/load.json";//获取桌面url
	var updateDesktopURL = AjaxConfig.url.osCore+"/user/app/save.json";//保存桌面url
	
	var checkStatusUrl = AjaxConfig.url.sso + "/beatheart/check";//心跳
	//var addAppSuccessfulUrl = AjaxConfig.url.ucenter + "/pub/appInstallNotify";
	var name = "com.gleasy.os.model.DesktopModel";//Model的名称
	var interests = [
					Constants.command.loadDesktop,
					Constants.command.loadApplicationOK,
					Constants.command.desktopDataChangeByUser,
					Constants.command.retrieveDesktop,
					Constants.command.os.loginSuccess,
					Constants.command.setAppConfig
				];//Model监听的消息IDs
		
	var pageDataList = [];//桌面数据本地缓存
	
	var dataChanged = false;
	var updating = false;
	var errorCount = 0;
	var appIconIds = [];
	
	var timeout = null;
	var jobQueue = [];
	
	var initDesktopData = [
		{id:0,apps:["系统应用","表格","写写","记事本","便签","应用商店","网址导航",Constants.app.ctrlPanel]},
		{id:-2000,apps:[Constants.app.workbench,"工作窗口","一盘","联系人","帮助中心"]}
	];
	
	/**
	 * 模拟构造函数
	 */
	var _construct = function(){
		_this.setInterests(interests);
	}
	
	var _loadData = function(params,callback,error){
		AjaxConfig.ajax({
		   type: "POST",
		   url: dataURL,
		   contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		   dataType:"json",
		   data: params,
		   success: callback,
		   error:error
		});
	}
	
	/**
	 * 返回对象唯一标识字符串
	 * 
	 */
	this.getName = function(){	
		return name;
	}
	
	
	
	/**
	 * 初始化方法
	 */
	this.init = function(){
		$.log(this.getName()+" is initing.....");
	}
	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		if(!com.gleasy.os.SystemInfo.login){
			$.log("用户未登录，不提供服务.");
			return;
		}
		if(eventId == Constants.command.loadDesktop){
			_loadData({action:'getall'},function(data){
				parseData(data);
			});
		}else if(eventId == Constants.command.desktopDataChangeByUser){
			//数据被用户更改，需要上传到服务器
			$.log("data changed....need to upload");
			updateDesktop();
		}else if(eventId == Constants.command.retrieveDesktop){
			retrieveDesktop(messageBody);
		}else if(eventId == Constants.command.setAppConfig){
			setAppConfig(messageBody);
		}else if(Constants.command.os.loginSuccess == eventId){
			if(com.gleasy.os.SystemInfo.login){
				_loadData({action:'getall',cname:'xxxx'},function(data){
					parseData(data);
				},function(p1,p2,p3){
					//alert("os出问题啦"+p1+","+p2+","+p3);
					//_this.sendNotify(Constants.command.system.alert,{message:"网络异常"});
				});
			}
		}
	};
	
	var setAppConfig = function(_param){
		var appName = _param.app;
		var config = _param.config;
		
		for(var i=0;i<pageDataList.length;i++){
			var pageData = pageDataList[i];
			var ds = pageData.datasource;
			for(var j=0;j<ds.length;j++){
				var icon = ds[j];
				if(icon.app == appName){
					$.extend(icon,config);
					updateDesktop();
					return;
				}
			}	
		}	
	}
	
	var retrieveDesktop = function(_param){
		var callack = _param.callback;
		
		if(!pageDataList.length || appIconIds.length){
			jobQueue.push(_param);
			return;
		}
		callack && typeof callack == 'function' && callack(pageDataList,_param.args);
	}
	

	
	
	var sortByOrder = function(arr){
		return arr.sort(function(a,b){
			return a.order - b.order;
		});
	}
	
	/**
	 * 保存桌面信息
	 */
	var updateDesktop = function(){
		if(updating){
			dataChanged = true;
			return;
		}
		updating = true;
		dataChanged = false;
		//$.log(pageDataList);
		//$.log($.toJSON(pageDataList));
		var apps = [];
		
		var appMap = {};
		var idx = 0;
		
		var collections = [];
		for(var i=0;i<pageDataList.length;i++){
			var pageData = pageDataList[i];
			var ds = pageData.datasource;
			for(var j=0;j<ds.length;j++){
				var icon = ds[j];
				if( icon.type == 'collection'){
					collections.push(icon.appId);
				}
			}	
		}	
		for(var i=0;i<pageDataList.length;i++){
			var pageData = pageDataList[i];
			var ds = pageData.datasource.concat();
			ds = sortByOrder(ds);
			var pageId = pageData.pageId;
			
			if(pageId <= -10000){
				//说明是合集
				if(!($.inArray(pageId,collections) >= 0)){
					continue;
				}
			}else if(pageId <= Constants.command.os.iconPan.pinId){
				//Pin区
			}else{
				pageId = idx;
				idx ++;
			}
			
			for(var j=0;j<ds.length;j++){
				var icon = ds[j];
				var appId = icon.appId;
				var order = icon.order;
				var type = icon.type;
				var appName = icon.app;
				if(!appName) continue;
				if(icon.silence) continue;
				if(type != 'collection' && appMap[appName]) continue;
				var app = {appId: appId, order: j, appType: type, pageId: pageId, appName: appName, messageOpen:icon.messageOpen, autoRun: icon.autoRun};
				apps.push(app);
				appMap[appName] = 1;
			}
		}
		//$.log(apps);
		//$.log($.toJSON(apps));
		var params = {data: $.toJSON(apps)};
		AjaxConfig.ajax({
		   type: "POST",
		   url: updateDesktopURL,
		   dataType:"json",
		   data: params,
		   contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		   success: function(ret){
		   		//checkUserStatus(ret);
		   		$.log("保存桌面-"+ret.description);
		   		updating = false;
		   		errorCount = 0;
		   		if(dataChanged){
		   			updateDesktop();
		   		}
		   },
		   error:function(xhr){
		   		$.log("保存桌面出错");
		   		updating = false;
		   		errorCount ++;
		   		if(errorCount < 5){
		   			updateDesktop();
		   		}
		   }
		});
	}
	
	var runQueue = function(){
		for(var i in jobQueue){
			var _param = jobQueue[i];
			var callback = _param.callback;
			var args = _param.args;
			callback && typeof callback == 'function' && callback(pageDataList,args);
		}
		jobQueue=[];
	}
	
	var loadAppData = function(ids){
		if(!ids || !ids.length){
			runQueue();
			_this.sendNotify(Constants.command.loadDesktopOK,pageDataList);
			return;
		}
		var param = {
			ids:ids,
			callback:function(result){	
				for(var i =0;i<pageDataList.length;i++){
					var pageData = pageDataList[i];
					var icons = pageData.datasource;
					for(var j in icons){
						var icon = icons[j];
						if(!icon.appId) continue;
						if(icon.type != 'app') continue;
						var app = result[icon.appId];
						if(app){
							icon.app = app.name;
							icon.args = app;
							$.extend(icon,app);
						}
					}
				}
				appIconIds = [];
				runQueue();
				_this.sendNotify(Constants.command.loadDesktopOK,pageDataList);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				//网络异常时处理
				//alert("我勒个去:desktop.class.js 96行。。。");
				_this.sendNotify(Constants.command.system.alert,{message:"网络异常"});
			}
		};
		_this.sendNotify(Constants.command.directLoadApplication,param);
	}
	
	var parseData = function(data){
		var status = data.status;
		if(status != 0){
			//alert("加载数据失败:"+data.description);
			return;
		}
		//var realData = data.data;
		var realData = data.data.apps;
		if(!realData || !realData.length){
			realData = [];
			//第一次登录系统哦，姑娘们，出来招呼啦
			for(var i=0;i<initDesktopData.length;i++){
				var d = initDesktopData[i];
				var apps = d.apps;
				for(var j=0;j<apps.length;j++){
					var ap = apps[j];
					
					if(ap == '系统应用'){
						realData.push({
							appName:ap,
							appType:'collection',
							pageId:d.id,
							appId:-10001,
							messageOpen:1,
							autoRun:0
						});
						continue;
					}
					
					var config = com.gleasy.os.reg.programDef[ap];
					if(!config) continue;
					var autoRun = config.autoRun?1:0;
					
					realData.push({
						appName:apps[j],
						appType:'sys',
						pageId:d.id,
						messageOpen:1,
						autoRun:autoRun
					});
				}
			}
			window.application.sendNotify(Constants.command.os.process.runProgram,{
					pname:"os帮助"
			});
		}
		pageDataList = [];
		var ids = [];
		
		var idDataMap = {};
		
		if(realData) {
			$.each(realData,function(index,app){
				var pageId = app.pageId;
				var pageData = idDataMap[pageId];
				if(!pageData){
					pageData = new com.gleasy.os.model.PageData();
					pageData.pageId = pageId;
					pageData.datasource = [];
					idDataMap[pageId] = pageData;
					pageDataList.push(pageData);
				}
				var icon = {};
				if(app.appType == 'sys'){
					var config = com.gleasy.os.reg.programDef[app.appName];
					if(!config || config.app != app.appName ){
						return;
					}
					if(config){
						$.extend(icon,config);
					}
				}
				icon.appId = app.appId;
				icon.order = app.order;
				icon.app = app.appName;
				icon.type = app.appType;
				icon.messageOpen = app.messageOpen;
				icon.autoRun = app.autoRun;
				
				//icon.type = Constants.command.os.iconTypeStr[app.openType];
				pageData.datasource.push(icon);
				if(icon.appId && icon.type == 'app'){
					ids.push(icon.appId);	 
				}
				if(icon.appId && icon.type == 'collection'){
					pageData = idDataMap[icon.appId];
					if(!pageData){
						pageData = new com.gleasy.os.model.PageData();
						pageData.pageId = icon.appId;
						pageData.datasource = [];
						idDataMap[icon.appId] = pageData;
						pageDataList.push(pageData);
					}
					pageData.name = icon.app;
				}
			});
		}
		appIconIds = ids;
		loadAppData(ids);
	}
	
	_construct();
}

//继承MvcBase
com.gleasy.os.model.DesktopModel.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本Model
window.application.addModel(new com.gleasy.os.model.DesktopModel());
