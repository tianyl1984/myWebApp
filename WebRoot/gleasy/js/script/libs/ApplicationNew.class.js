Namespace.register("com.gleasy.libs");

/**
 * @author xueke
 * MVC 主类－应用
 */
com.gleasy.libs.Application = function(name){
	var _this = this;

	this.name = name;
	if(typeof name == "undefined"){
		this.name = "root";
	}
		
	
	var mvcs = [];//mvc

	this.isRunning = false;
		
	var eventMap = {};
	
	var jobQueue = {};
	
	var inited = false;
	
	this.addMvc = function(mvc,order){
		mvcs.push(mvc);
		mvc && mvc.setApplication && mvc.setApplication(_this);
		if(inited){
			if(!mvc.inited){
				var res = mvc.init();
				mvc.inited = !res;
				mvc.processQueue();
			}
		}
	}
	
	this.removeMvc = function(mvc){
		if(!mvc) return;
		var index = $.inArray(mvc,mvcs);
		if(index >= 0){
			mvcs.splice(index,1);
		}
		$.each(eventMap,function(index,arr){
			if(!arr.length) return;
			for(var i=0;i<arr.length;i++){
				var idx = $.inArray(mvc,arr);
				if(idx>=0){
					arr.splice(idx,1);
				}
			}
		});
	}
	
	/**
	 * 注册model
	 */
	this.addModel = function(model){
		_this.addMvc(model);
	}
	
	
	/**
	 * 注册view
	 */
	this.addView = function(view){
		_this.addMvc(view);
	}		
	
	/**
	 * 注册control
	 */
	this.addController = function(controller){
		_this.addMvc(controller);
	}
	
	this.initAll = function(){
		$.each(mvcs,function(index,mvc){
			if(!mvc.inited){
				var res = mvc.init();
				mvc.inited = !res;
				mvc.processQueue();
			}
		});	
	}
	
	/**
	 * 注册监听项
	 */
	this.addInterest = function(actionId,mvcObj){
		if(typeof eventMap[actionId] == 'undefined'){
			eventMap[actionId] = [];
		}
		var arr = eventMap[actionId];
		
		if( $.inArray(mvcObj,arr) > -1){
			return;
		}
		arr.push(mvcObj);
	}
	
	/**
	 * 发送消息通知
	 */
	this.sendNotify = function(actionId, messageBody){
		if(typeof eventMap[actionId] == 'undefined'){
			return;
		}
		var arr = eventMap[actionId];
		$.each(arr,function(index,mvc){
			if(mvc&&mvc != null){
				if(!mvc.inited){
					$.log(mvc.getName()+"还未初始化,进入等待队列.消息类型："+actionId);
					mvc.pushToQueue(actionId, messageBody);
				}else{
					mvc.notify(actionId, messageBody);
				}
			}
		});
	}
	
	
	/**
	 * 执行入口
	 */
	this.run = function(){
		this.initAll();
		inited  = true;
	}
	
	
	this.runMvc = function(mvc){
		if(!mvc.inited){
			var res = mvc.init();
			mvc.inited = !res;
			mvc.processQueue();
		}
	}
}


		
window.application = new com.gleasy.libs.Application();
// When the DOM is ready, run the application.
$(function(){
	window.application.run();
});	
