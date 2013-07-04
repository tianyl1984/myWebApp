Namespace.register("com.gleasy.os");

com.gleasy.os.SystemInfo = {};
com.gleasy.os.SystemInfo.ver = "1.0";
com.gleasy.os.SystemInfo.login = false; //当前客户端是否有用户登录
com.gleasy.os.SystemInfo.account = null;

com.gleasy.os.SystemInfo.getCurrentUser = function(callback,force){
	if(!force && Constants.data.user){
		callback && typeof callback == 'function' && callback(Constants.data.user);
	}else{
		window.application.sendNotify(Constants.command.os.reloadUserCard,{
			callback:callback
		});
	}
}
