/**
 *开机loading...
*/
Namespace.register("com.gleasy.os.view");

com.gleasy.os.view.BootLoading = function(param){
	$.extend(param,{
		hasFrame:0,
		hasDock:0,
		canResize:0,
		initSize:[300,116]
	});
	com.gleasy.os.view.Container.call(this,param);//继承Container
	var _this = this;
	var view = null;
	
	var _construct = function(){
		//$.extend(options,param);
		initComponent();
	}

	var initComponent = function(){
		view = $("#util_dv .bootLoading").clone();
		_this.setZindex(Constants.config.os.layer.screenMask+1);
		_this.setContent(view);
		_this.center();
	}
	
	_construct();
}
