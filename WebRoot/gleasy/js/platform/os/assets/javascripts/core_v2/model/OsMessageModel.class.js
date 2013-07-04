/**
 * os 消息model
 */

Namespace.register("com.gleasy.os.model");
com.gleasy.os.model.OsMessageModel = function(){
	var _this = this;
	
	var messageLoadUrl = AjaxConfig.url.os + "/message/load.json";//获取消息
	var messageAddUrl = AjaxConfig.url.os + "/message/add.json";//获取消息
	var messageRemoveUrl = AjaxConfig.url['os']+"/message/remove.json";//删除消息
	var name = "com.gleasy.os.model.OsMessageModel";//Model的名称
	
	var interests = [
		Constants.command.os.message.init,
		Constants.command.os.message.remove,
		Constants.command.os.message.arrive,
		Constants.command.os.message.reload,
		Constants.command.im.sessionStart,
		Constants.command.os.requireLogin,
		Constants.command.os.message.appremove,
		Constants.command.os.message.numChange,
		Constants.command.os.message.add
	];//Model监听的消息IDs
	
	var messageCache = {};
	var messageCacheArray = [];
	var maxId = -1;
	var interval = null;
	var lastLoadTime = -1;
	var loaded = false;
	var loading = false;
	var messageAppCache = {};
	var numCache = {};
	
	/**
	 * 模拟构造函数
	 */
	var _construct = function(){
		//调用基类的方法
		_this.setInterests(interests);
		
	};
	
	
	/**
	 * Mvc必需方法
	 * 返回对象唯一标识字符串
	 * 
	 */
	this.getName = function(){	
		return name;
	};
	
	/**
	 * Mvc必需方法
	 * 初始化方法
	 */
	this.init = function(){
		$.log(this.getName()+" is initing.....");
		
	};
	
	/**
	 * Mvc必需方法
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		if(eventId == Constants.command.os.message.remove){
			removeMessage(messageBody);
		}else if(eventId == Constants.command.os.message.add){
			addMessage(messageBody);
		}//else if(eventId == Constants.command.os.message.reload){
			//loadMessage();
		//}
		else if(eventId == Constants.command.os.message.init){
			
		}else if(Constants.command.im.sessionStart == eventId){
			loadMessage();
		}else if(Constants.command.os.requireLogin == eventId){
			loaded = false;
			loading = false;
		}else if(Constants.command.os.message.arrive == eventId){
			messageArrive(messageBody);
		}else if(Constants.command.os.message.numChange == eventId){
			numChange(messageBody);
		}
	};
	
	
	var numChange = function(param){
		var app = param.app;
		var num = param.num;
		numCache[app] = num;
		refreshNumNotify();
	}
	
	var refreshNumNotify = function(){
		var total = 0;
		$.each(numCache,function(app,num){
			if(num < 0) num = 0;
			total += num;
		});
		if(total > 0 || messageCacheArray.length){
			typeof gleasy != 'undefined' && typeof gleasy.send == 'function' &&gleasy.send("onAppMessage",[true]);
		}else{
			typeof gleasy != 'undefined' && typeof gleasy.send == 'function' &&gleasy.send("onAppMessage",[false]);
		}
	}
	
	var messageArrive = function(message){
		if(!message) return;
		var id = message.id;
		if(id <= maxId){
			return;
		}
		var newArrival = [];
		maxId = id;
		if(!messageCache[message.id]){
			messageCache[message.id] = message;
			messageCacheArray.unshift(message);
			if(messageCacheArray.length > 50){
				messageCacheArray.pop();
			}
			newArrival.unshift(message);
			refreshAppIonNum(message.app);
		}
		_this.sendNotify(Constants.command.os.setting.save, {key:"os.message.pointer",value:maxId});
		_this.sendNotify(Constants.command.os.message.blink, {all:messageCacheArray,plus:newArrival});
		
		if(newArrival.length > 0){
		 	refreshNumNotify();
		}   			
	}
	
	
//	var sendRemindToApp = function(newMessages){
//		if(!newMessages || !newMessages.length) return;
//		var cache = {};
//		for(var i=0;i<newMessages.length;i++){
//			var message = newMessages[i];
//			var pname = message.pname?message.pname:message.app;
//			if(cache[pname]) continue;
//			cache[pname] = 1;
//		 	window.application.sendNotify(Constants.command.os.process.sendMessage,{
//				pname:pname,
//				eventId:"app.message.arrive",
//				messageBody:{
//				}
//			});
//		}
//		delete cache;
//		cache = null;
//	}
	
	var addMessage = function(_param){
		AjaxConfig.ajax({
		   type: "POST",
		   url: messageAddUrl,
		   dataType:"json",
		   data: {
		   	uid:Constants.data.user.uid,
		   	app:_param.app,
		   	template:_param.template?_param.template:"default",
		   	content:$.toJSON(_param.content)
		   },
		   success: function(ret){
		   	 $.log("生成消息成功");
		   },
		   error:function(xhr){
		   }
		});			
	}
	
	var loadMessage = function(){	
		if(loaded || loading) return;
		loading = true;	
		_this.sendNotify(Constants.command.os.setting.load, {key:"os.message.pointer",callback:function(settings){
			var pointer = settings["os.message.pointer"];
			if(pointer){
				maxId = pointer;
			}else{
				maxId = 0;
			}
			_loadMessages();
		}});
	};
	
	var _loadMessages = function(){
		AjaxConfig.ajax({
		   type: "POST",
		   url: messageLoadUrl,
		   dataType:"json",
		   data: {maxId: 0},
		   success: function(ret){
		   		if(ret.status != 0) return;
		   		var messages = ret.data.messages;
		   		if(messages && messages.length){
		   			messages.reverse();
		   			var newArrival = [];
		   			for(var i=0;i<messages.length;i++){
		   				var message = messages[i];
		   				if(!message) continue;
		   				if(message.id > maxId){
		   					maxId = message.id;
		   					newArrival.unshift(message);
		   				}
		   				if(!messageCache[message.id]){
		   					messageCache[message.id] = message;
		   					messageCacheArray.unshift(message);
		   					if(messageCacheArray.length > 50){
								messageCacheArray.pop();
							}
		   				}
		   			}
		   			_this.sendNotify(Constants.command.os.setting.save, {key:"os.message.pointer",value:maxId});
		   			_this.sendNotify(Constants.command.os.message.blink, {all:messageCacheArray,plus:newArrival});
		   			if(newArrival.length > 0){
		   				if(typeof gleasy != 'undefined'){
		   					typeof gleasy.send == 'function' && gleasy.send("onAppMessage");
		   				}
		   			}
		   			refreshAppIonNum();
		   		}else{
		   			_this.sendNotify(Constants.command.os.message.blink, {all:messageCacheArray,plus:[]});
		   		}
		   		loaded = true;
		   		loading = false;
		   },
		   error:function(xhr){
		 	loading = false;
		   }
		});
	}
	
	var refreshAppIonNum = function(appName){
		if(!appName && !messageCacheArray.length) return;
		if(appName){
			messageAppCache[appName] = [];
		}else{
			messageAppCache = {};
		}
		var invalidCache = {};
		for(var i=0;i<messageCacheArray.length;i++){
			var message = messageCacheArray[i];
			var app = message.app;
			var id = message.id;
			if(!app) continue;
			if(appName && appName != app) continue;
			var appcache = messageAppCache[app];
			if(!appcache){
				appcache = [];
				messageAppCache[app] = appcache;
			}
			if(message.uuid){
				if(message.type == 'del'){
					invalidCache[app+"_"+message.uuid] = 1;
					var idx = $.inArray(message.uuid,appcache);
					if(idx >= 0) appcache.splice(idx,1);
				}else if(!message.autoremove){
					if(invalidCache[app+"_"+message.uuid]) continue;
					var idx = $.inArray(message.uuid,appcache);
					if(idx < 0) appcache.push(message.uuid);
				}
			}
		}
		if(appName && !messageAppCache[appName]){
			messageAppCache[appName] = [];
		}
		updateAppDeskMessage(appName);
		invalidCache = null;
	}
	
	var updateAppDeskMessage = function(appName){
		if(appName){
			var appcache = messageAppCache[appName];
			var length = appcache.length;
			window.application.sendNotify(Constants.command.os.updateAppDockNum,{
				app:appName,num:length
			});
			return;
		}
		$.each(messageAppCache,function(app,appcache){
			if(!appcache) return;
			var length = appcache.length;
			window.application.sendNotify(Constants.command.os.updateAppDockNum,{
				app:app,num:length
			});
		});
	}
	
	var removeMessage = function(param){
		var id = param.id;
		if(!id) return;
		var ids = [];
		if(typeof id == 'object'){
			ids = id;
		}else{
			ids.push(id);
		}
		var app = null;
		for(var i=0;i<ids.length;i++){
			var iid = ids[i];
			var message = messageCache[iid];
			app = message.app;
	
			var idx = $.inArray(message,messageCacheArray);
			messageCacheArray.splice(idx,1);
			
			messageCache[iid] = null;
			delete messageCache[iid];
		}
		refreshAppIonNum(app);
		refreshNumNotify();
		var idparam = ids.join(",");
		AjaxConfig.ajax({
			url: messageRemoveUrl,
			dataType: "json",
			type: "POST",
			data: {id: idparam,app:app},
			success:function(ret){
			}
		});
	}
	_construct();
}

//继承MvcBase
com.gleasy.os.model.OsMessageModel.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本Model
window.application.addModel(new com.gleasy.os.model.OsMessageModel());
