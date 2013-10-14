Namespace.register("com.tianyl.myjs2");

com.tianyl.myjs.Demo2 = function(){
	var _this = this;
	
	this.getName = function(){
		return "com.tianyl.myjs.Demo2";
	}
	
	this.init = function(){
		$.log(this.getName() + " init...");
	}
	
	this.notify = function(eventId,messageBody){
		if("eventId1" == eventId){
			$("#testDiv").hide();
		}
	}
	
	var _construct = function(){
		_this.setInterests(["eventId1"]);
	}
	
	_construct();
}
//继承MvcBase
com.tianyl.myjs.Demo2.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本View
window.application.addMvc(new com.tianyl.myjs.Demo2());


