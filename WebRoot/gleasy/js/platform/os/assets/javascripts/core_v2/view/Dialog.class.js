/**
 * Gleasy OS Frame
 * 
*/
Namespace.register("com.gleasy.os.view");

com.gleasy.os.view.Dialog = function(param){
	$.extend(param,{
		canMin:0
	});
	com.gleasy.os.view.Container.call(this,param);//继承Container
	
	var _this = this;
	var focused = false;
	
	this.dialogOptions = {
		container:$(".desktop")
	};
	
	this.dialogDestruct = function(){
		var parent = _this.dialogOptions.parent;
		parent.showModal(false);	
	}

	this.destruct = function(){
		_this.dialogDestruct();
	}
	
	this.zindexChange = function(){
		var parent = _this.dialogOptions.parent;
		_this.setZindex(parent.getZindex()+2);		
	}	
	
	var _construct = function(){
		$.extend(_this.dialogOptions,_this.options);
		var parent = _this.dialogOptions.parent;
		if(parent == null){
			throw "父窗口为空.";
		}
		_this.parent = parent;
		_this.options.pid = _this.parent.getPid();
		_this.parent.addChild(_this);		
		initComponent();	
		_this.setFocus(true);	
	}
	
	var configEvent = function(){	
		_this.view.bind("keydown",'esc',function(event){
			_this.close();
		});
				
		_this.view.bind("mousedown",function(ev){
			if($(ev.target).closest("input").length <= 0 ){
				_this.tryFocus();
			}
		});	
	}

	this.tryFocus = function(){
		var parent = _this.parent;
		if(parent != null){
			parent.tryFocus();
		}
		_this.setFocus(true);
		_this.doInterFocus();
	}

	this.setFocus = function(focus){
		if(!_this.isVisible()){
			return;
		}
		$.each(_this.children,function(index,child){
			child && typeof child.setFocus=='function' && child.setFocus(focus);
		});			
		if(focus && !focused){
			focused = true;
			if(!_this.view.hasClass("oWindow_current")){
				_this.view.addClass("oWindow_current");
			}
			_this.hideMask();
		}else if(!focus && focused){
			focused = false;
			_this.view.removeClass("oWindow_current");	
			_this.showMask();
		}
	}
	
	var initComponent = function(){
		var parent = _this.parent;
		parent.setFocusChild(_this);
		parent.showModal(true);
		_this.setZindex(parent.getZindex()+2);
		_this.view.addClass("oWindow_current");
		configEvent();
	}

	return _construct();
}
