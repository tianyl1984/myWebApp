Namespace.register("com.gleasy.libs");

/**
 * 观察者模式实现类
 * 所有继承于该类的类都具备观察者的通知能力
 */
com.gleasy.libs.Observable = function(){
	var _this = this;
	var listeners = {};
	
	this.noticeListener = function(event,param){
		var funcs = listeners[event];
		if(funcs != null && funcs.length >0 ){
			$.each(funcs,function(index,fn){
				typeof fn == "function" && fn(param);
			});
		}
	};
		
	this.addEventListener = function(event,func){
		var funcs = listeners[event];
		if(typeof funcs == 'undefined' || !funcs){
			funcs = [];
			listeners[event] = funcs;
		}
		funcs.push(func);
		return _this;
	};
	
	this.removeEventListener = function(event,func){
		var funcs = listeners[event];
		if(typeof funcs == 'undefined' || !funcs){
			return;
		}
		var idx = $.inArray(func,funcs);
		if(idx>=0){
			funcs.splice(idx,1);
		}		
	};
};
