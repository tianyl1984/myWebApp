/**
 * Gleasy OS Frame
 * 继承自Container类
 * Frame具有如下独特的特性：
 * 1. 受FrameManager统一管理
 * 2. 创建后永远为desktop的子结点，为OS的顶级窗体
 * 3. 可以生成任务栏图标并与任务栏图标交互
 * 4. 可以focus
*/
Namespace.register("com.gleasy.os.view");

com.gleasy.os.view.Frame = function(param){
	com.gleasy.os.view.Container.call(this,param);//继承Container
	var _this = this;

	var added = false;
	var state = "show";
	var focused = false;

	this.frameOptions = {
		container:$(".desktop"),
		isMain:false,
		isWidget:0,
		app:"未知应用"
	};
	
	var _construct = function(){
		$.extend(_this.frameOptions,_this.options);
		initComponent();
		window.application.sendNotify(Constants.command.os.process.addFrame,{pid:_this.getPid(),frame:_this});
	}
	
	var configEvent = function(){
		_this.addEventListener("mousedown",function(ev){
			if($(ev.target).closest("input").length <= 0 ){
				_this.tryFocus();
			}
		});
	}
	
	var initComponent = function(){
		_this.view.css("zIndex",Constants.config.os.layer.windowNormal);		
				
		_this.addEventListener("min",function(){
			state = "min";
			window.application.sendNotify(Constants.command.os.frame.setVisible,{wid:_this.frameOptions.wid,pid:_this.getPid(),visible:false});
		}).addEventListener("close",function(){
			state = "close";
			window.application.sendNotify(Constants.command.os.frame.close,{wid:_this.frameOptions.wid,pid:_this.getPid()});
		});
		configEvent();
	}

	this.notify = function(){
		
	}
		
	this.isMainFrame = function(){
		return _this.frameOptions.isMain;
	}
	
	this.hasDock = function(){
		return _this.frameOptions.hasDock;
	}
	
	this.isWidget = function(){
		return _this.frameOptions.isWidget;
	}
	
	this.getState = function(){
		return state;
	}

    this.destruct = function () {
		delete _this;	
    }

	this.setVisible = function(show){
		$.each(_this.children,function(index,child){
			index !='null' && child != null && child.setVisible(show);
		});	
		if(show){
			state = "show";
			_this.show();
			if(!added){
				added = true;
				_this.doResize();
				_this.tryFocus();
				if(!_this.frameOptions.isWidget){
					window.application.sendNotify(Constants.command.os.dock.create,{
						app:_this.frameOptions.app,
						pid:_this.frameOptions.pid,
						wid:_this.frameOptions.wid,
						icon:_this.frameOptions.icon,
						title:_this.frameOptions.title
					});
				}
			}else{
				if(_this.view.hasClass('oWindow_current')){
					_this.noticeListener("refocus","");
				}
			}
		}else{
			state = "hide";
			_this.hide();
			_this.noticeListener("blur","");
		}
	}
	
	this.toggle = function(){
		if(_this.isVisible()){
			_this.setVisible(false);
		}else{
			_this.setVisible(true);
		}
	}

	this.tryFocus = function(){
		if(focused) return;
		var parent = _this.parent;
		if(parent != null){
			parent.tryFocus();
		}else{
			window.application.sendNotify(Constants.command.clearActiveLink,null);
			window.application.sendNotify(Constants.command.os.frame.focus,{wid:_this.frameOptions.wid});	
		}
		_this.doInterFocus();
	}
	
	this.setFocus = function(focus,idx){
		if(_this.isFullscreen()){
			return;
		}
		if(!_this.isVisible()){
			return;
		}
		$.each(_this.children,function(index,child){
			index !='null' &&  child!=null && child.setFocus(focus);
		});	
		
		if(focus && !focused){
			if(!_this.view.hasClass('oWindow_current')){
				_this.view.addClass('oWindow_current');
			}
			_this.setZindex(Constants.config.os.layer.windowActive);
			_this.noticeListener("focus","");	
			if(!_this.isWidget()){
				_this.hideMask();
			}
			focused = true;
		}else if(!focus && focused){
			if(_this.view.hasClass('oWindow_current')){
				_this.view.removeClass('oWindow_current');
			}
			focused = false;
			var zindex = Constants.config.os.layer.windowNormal;
			if(idx){
				zindex += idx;
			}
			_this.setZindex(zindex);
			_this.noticeListener("blur","");
			if(!_this.isWidget()){
				_this.showMask();
			}
		}else{
			if(_this.view.hasClass('oWindow_current')){
				_this.view.removeClass('oWindow_current');
			}
			var zindex = Constants.config.os.layer.windowNormal;
			if(idx){
				zindex += idx;
			}
			_this.setZindex(zindex);
		}
	}

	this.isFocus = function(){
		return _this.isVisible() && focused;
	}
	
	return _construct();
}
