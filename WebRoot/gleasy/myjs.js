Namespace.register("com.tianyl.myjs");

com.tianyl.myjs.Demo = function(){
	var _this = this;
	
	this.getName = function(){
		return "com.tianyl.myjs.Demo";
	}
	
	this.init = function(){
		$.log(this.getName() + " init...");
	}
	
	this.notify = function(eventId,messageBody){
		if("eventId1" == eventId){
			alert(messageBody);
		}
		if("eventId2" == eventId){
			$.log(messageBody);
		}
	}
	
	var _construct = function(){
		_this.setInterests(["eventId1","eventId2"]);
	}
	
	_construct();
}
//继承MvcBase
com.tianyl.myjs.Demo.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本View
window.application.addMvc(new com.tianyl.myjs.Demo());


