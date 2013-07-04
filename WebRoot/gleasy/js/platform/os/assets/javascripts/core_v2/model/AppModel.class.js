Namespace.register("com.gleasy.os.model");

/**
 * 第三方应用管理Model
 * 1. 根据ID加载第三方应用
 * 2. 在本地缓存第三方应用
 * 3. 根据ID列表加载第三方应用
 * @author xueke
 */
com.gleasy.os.model.AppModel = function(){
	var _this = this;
	var dataURL = AjaxConfig.url.appCenter+"/loadApp.json?id="+Math.random();//数据源url
	var name = "com.gleasy.os.model.AppModel";//Model的名称
	var interests = [
					Constants.command.loadApplication,
					Constants.command.addApplication,
					Constants.command.directLoadApplication
				];//Model监听的消息IDs
		
	var cache = {};//本地缓存
	
	
	/**
	 * 模拟构造函数
	 */
	var _construct = function(){
		_this.setInterests(interests);
	}
	
	var _loadData = function(params,callback ,error){
		AjaxConfig.ajax({
		   type: "POST",
		   url: dataURL,
		   dataType:"json",
		   data: params,
		   success: callback,
		   error:error
		});

		//$.getJSON(dataURL, params,callback);
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
		var _this = this;
	}
	
	this.directLoadApplication = function(ids,callback ,error){
			if(typeof ids != 'object') {
				return;
			}
			var realIds = [];
			for(var i=0;i<ids.length;i++){
				var k = ids[i];
				if(typeof cache[k] == 'undefined' || !cache[k]){
					realIds.push(k);
				}
			}
			if(realIds.length <= 0){
				callback(cache);
				return;
			}
			
			ids = realIds.join(",");
			$.log("Ids="+ids);
			_loadData({ids:ids},function(data){
				var result = parseData(data);
				callback(cache);
			},error);	
	}
	
	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		if(eventId == Constants.command.loadApplication){
			var ids = messageBody;
			directLoadApplication(ids,function(result){
				_this.sendNotify(Constants.command.loadApplicationOK,result);
			},function(){
				//网络异常
				
			});
		}else if(eventId == Constants.command.addApplication){
			addApp(messageBody);
		}else if(eventId == Constants.command.directLoadApplication){
			_directLoadApplication(messageBody);
		}
		
	}
	
	var _directLoadApplication = function(param){
		var ids = param.ids;
		var callback = param.callback;
		var error = param.error;
		
		if(typeof ids != 'object') {
			return;
		}
		var realIds = [];
		for(var i=0;i<ids.length;i++){
			var k = ids[i];
			//$.log("k="+k+";"+cache[k]);
			if(typeof cache[k] == 'undefined' || !cache[k]){
				realIds.push(k);
			}
		}
		if(realIds.length <= 0){
			callback(cache);
			return;
		}

		
		ids = realIds.join(",");
		//$.log("Ids="+ids);
		_loadData({ids:ids},function(data){
			var result = parseData(data);
			callback(cache);
		},error );	
	}
	
	var addApp = function(app){
		if(app == null) return;
		cache[app.id] = app;
	}
	
	var parseData = function(data){
		var status = data.status;
		if(status != 0){
			//alert("加载数据失败:"+data.description);
			return;
		}
		var realData = data.data;
		
		$.each(realData,function(index,item){
			var app = new com.gleasy.os.model.App();
			$.extend(app,item);
			//$.log("app.id="+app.id);
			cache[app.id] = app;
		});
		
		return realData;
	}
	
	_construct();
}

//继承MvcBase
com.gleasy.os.model.AppModel.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本Model
window.application.addModel(new com.gleasy.os.model.AppModel());
