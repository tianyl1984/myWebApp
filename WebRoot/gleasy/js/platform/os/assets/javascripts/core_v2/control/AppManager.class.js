Namespace.register("com.gleasy.os.view");

/**
 * APP管理器
 * 
 */
com.gleasy.os.view.AppManager = function(){
	var _this = this;
		
	var name = "com.gleasy.os.model.AppManager";//名称
	var interests = [Constants.command.system.installApp,
					Constants.command.system.unInstallApp,
					Constants.command.loadDesktopOK,
					Constants.command.system.getInstalledApps];
	

	var desktopPages = [];
	var pin = null;
	var dock = null;
	var pageDataList = null;
	var booted = false;
	
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
		$.log(this.getName()+" 收到消息 "+eventId);
		if(eventId == Constants.command.system.installApp){
			installApp(messageBody);
		}else if(eventId == Constants.command.system.unInstallApp){
			unInstallApp(messageBody);
		}else if(eventId == Constants.command.system.getInstalledApps){
			getInstalledApps(messageBody);
		}else if(eventId == Constants.command.loadDesktopOK){
			loadDesktopOK(messageBody);
		}
	}

	var loadDesktopOK = function(_pageDataList){
		if(booted) return;
		booted = true;
		pageDataList = _pageDataList;
		getInstalledApps({
			callback:function(apps){
				$.each(apps,function(pname,appObj){
					if(appObj.autoRun == 1){
					   window.application.sendNotify(Constants.command.os.process.runProgram,{
									pname:pname
					   });
					}
				});
			}
		});
		
		setTimeout(function(){
			showHelp();
		},1000);
		
	}
	
	
	var showHelp = function(){
		if(!Constants || !Constants.data || !Constants.data.user){
			setTimeout(function(){
				showHelp();
			},1000);
			return;
		}
		window.application.sendNotify(Constants.command.os.setting.load,{
			key:SettingProperty.user.showHelp,
			callback:function(settings){
				var showHelp = settings[SettingProperty.user.showHelp];
				if(showHelp != "off"){
					 window.application.sendNotify(Constants.command.os.process.runProgram,{
						pname:"os帮助"
					 });
				}
			}
		});
	}
	
	
	/**
	 * 判断用户是否已经安装过该应用
	 */
	var isInstalled = function(app){
		return getPageInstalled(app) != null;
	}
	
	/**
	 * 获取该应用安装所在页
	 */
	var getInstalledIcon = function(app){
		for(var i=0;i<pageDataList.length;i++){
			var pageData = pageDataList[i];
			var pan = pageData.pan;
			var icon = pan.findIconByApp(app);
			if(icon){
				return icon;
			}
		}
		return null;
	}
	
	var doInstall = function(appName,appObj,callback){
		var page = com.gleasy.os.view.DesktopView.getCurrentPage();
		if(page){
			if(!page.isAccept()){
				callback && typeof callback  == 'function' && callback({
					status:100,
					description:"当前页满"
				});
				return;
			}
			if(appObj){
				var key = appObj.name;
				var ic = {};
				$.extend(ic,appObj);
				$.extend(ic,{
					pname:appObj.name,
					app:appObj.name,
					appId:appObj.id,
					shortName:appObj.shortName,
					type:"app",
					icon:appObj.image,
					fileTypes:appObj.fileTypes,
					intro:appObj.shortIntro,
					singleton:appObj.singleton,
					exe:com.gleasy.os.view.AppFrame,
					args:appObj
				});
				page.addIconObj(ic);
			}else{
				page.addIcon(appName);
			}
			
			page.refresh();
			callback && typeof callback  == 'function' && callback({
				status:0,
				description:"安装成功"
			});
		}else{
			callback && typeof callback  == 'function' && callback({
				status:102,
				description:"当前页无法安装应用"
			});
		}		
	}
	
	
	/**
	 * 安装应用
	 */
	var installApp = function(param){
		var appId = param.id;
		var appName = param.app;
		if(!appName){
			$.log("应用名称为空，无法安装");
			return;
		}
		var callback = param.callback;
		
		var icon = getInstalledIcon(appName);
		if(icon){
			callback && typeof callback  == 'function' && callback({
				status:100,
				description:"本应用已经在图形桌面安装过."
			});
			return;	
		}
		
		var apps = com.gleasy.os.reg.programDef;
		if(appId){
			var appParam = {
				ids:[appId],
				callback:function(result){
					var app = result[appId];
					if(!app){
						//如果app不存在
						callback && typeof callback  == 'function' && callback({
								status:101,
								description:"应用不存在"
						});
						return;
					}
					doInstall(app.name,app,callback);
					return;	
				},error:function(XMLHttpRequest, textStatus, errorThrown){
					var result = {
						status:99,
						description:"网络异常."
					};
					callback && typeof callback  == 'function' && callback(result);
					return;
				}
			};
			_this.sendNotify(Constants.command.directLoadApplication,appParam);				
		}else{
			var program = com.gleasy.os.reg.programDef[appName];
			if(!program){
				callback && typeof callback  == 'function' && callback({
					status:100,
					description:"应用"+appName+"不存在"
				});	
			}else{
				doInstall(appName,null,callback);
			}
		}
	}

	var findProcessByPname = function(pname){
		for(var i=0;i<com.gleasy.os.view.ProcessManager.processList.length;i++){
			var p = com.gleasy.os.view.ProcessManager.processList[i];
			if(p.getPname() == pname){
				return p;
			}
		}
		return null;			
	}
	
	var unInstallApp = function(param){
		var appId = param.id;
		var appName = param.app;
		if(!appName){
			$.log("应用名称为空，无法卸载");
			return;
		}
		var callback = param.callback;
		var icon = getInstalledIcon(appName);
		if(!icon){
			return;	
		}
		if(findProcessByPname(appName)){
			//如果应用正在运行
			callback && typeof callback  == 'function' && callback({
				status:100,
				description:"应用"+appName+"正在运行，不可卸载"
			});	
			return;	
		}
		icon.pan.removeIcon(icon,true);
		icon.pan.refresh();
		callback && typeof callback  == 'function' && callback({
			status:0,
			description:"卸载成功"
		});			
	}
	
	var getInstalledApps = function(_param){
		com.gleasy.os.reg.getInstalledApps(_param);
	}
		
	/**
	 * 模拟构造函数
	 */
	var _construct = function(){
		_this.setInterests(interests);
	}
	
	
	//构造函数调用
	_construct();
}

//继承MvcBase
com.gleasy.os.view.AppManager.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本View
window.application.addMvc(new com.gleasy.os.view.AppManager());
