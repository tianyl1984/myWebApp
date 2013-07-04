Namespace.register("com.gleasy.os.view");

/**
 * Update管理器
 * 
 */
com.gleasy.os.model.UpdateManager = function(){
	var _this = this;
		
	var name = "com.gleasy.os.model.UpdateManager";//名称
	var interests = [];
	

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
		checkClientVersion();
	}
	
	var checkClientVersion = function(){
		var ua = navigator.userAgent.toLowerCase();
		if(ua.indexOf("gleasy client") >= 0){
			if(ua.indexOf("gleasyclient") < 0){
				setTimeout(function(){
					var param = {};
					param.message = "您当前的客户端版本太低,";
					param.confirmMessage = "建议立即升级至最新版。";
					param.okBtn = true;
					param.okBtnText = "下载Gleasy客户端";
					param.cancelBtn = true;
		        	param.cancelBtnText = "不用了,谢谢";
		        	param.icon = "prompt";//icon={confirm,fail,success,prompt,warning}
		        	//param.system = true;
		        	param.callback = function(obj){
		        		if(obj.result){
		        			window.application.sendNotify(Constants.command.os.openUrl,Constants.config.clientPrefix+"/platform/softwares/client/download.html");
		        		}
		        	}
					window.application.sendNotify(Constants.command.system.alert,param);
				},700);
			}
		}
	}
	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
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
com.gleasy.os.model.UpdateManager.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本View
window.application.addMvc(new com.gleasy.os.model.UpdateManager());
