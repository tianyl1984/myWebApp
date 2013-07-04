Namespace.register("com.gleasy.libs");

/**
 * MVC基类 
 * 所有M,V,C共同的基类
 */
com.gleasy.libs.MvcBase = function(){
	var _this = this;
	this.inited = false;
	var jobQueue = [];
	this.application = window.application;//默认是全局的application
	
	this.pushToQueue = function(actionId,messageBody){
		jobQueue.push({a:actionId,m:messageBody});
	}
	
	this.processQueue = function(){
		if(_this.inited) return;
		var len = jobQueue.length;
		for(var i=0;i<len;i++){
			var jb = jobQueue[i];
			if(jb){
				try{
					this.notify(jb.a,jb.m);
				}catch(e){
					$.log(e);
				}
			}
		}
		jobQueue = [];
	}

	this.setApplication = function(app){
		this.application = app;
	}
	
	this.addInterest = function(eventId){
		this.application.addInterest(eventId,this);
	}
	
	this.sendNotify = function(actionId, messageBody){
		this.application.sendNotify(actionId,messageBody);
	}
	
	this.getThis = function(){
		return this;
	}
	
	this.setInterests = function(interests){
		var _this = this;
		$.each(interests,function(index,interest){
			_this.addInterest(interest);
		});
	}
		
	this.init = function(){
		
	}
	
	this.getName = function(){	
		return ("mvc-" + (new Date()).getTime());
	}
	
	this.notify = function(eventId,messageBody){
		//$.log("MvcBase line 35 :"+eventId);
		$.log(messageBody);
	}
};
