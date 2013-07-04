/**
 *　可执行程序
 *
*/
Namespace.register("com.gleasy.core.view");

com.gleasy.core.view.Runnable = function(param){
	com.gleasy.os.view.Frame.call(this,param);//继承Frame
	
	var _this = this;
	var view = null;
	
	var options = {
		url:''
	};
	
	var _construct = function(){
		$.extend(options,param);
		initComponent();
		_this.center();
	}
	
	var initComponent = function(){
		if(options.url){
			_this.setUrl(options.url);
		}
		configEvent();
	}

	
	var configEvent = function(){		
	}
	
	_construct();
}

/**
 * 执行入口
 * 有main方法的类才可以被OS执行
 */
com.gleasy.core.view.Runnable.main = function(param){
	$.extend(param,{
		clazz:com.gleasy.core.view.Runnable,
		callback:function(res){
			var wid = res.wid;
			window.application.sendNotify(Constants.command.os.frame.setVisible,{wid:wid,visible:true});
		}
	});
	window.application.sendNotify(Constants.command.os.frame.create,param);	
}
