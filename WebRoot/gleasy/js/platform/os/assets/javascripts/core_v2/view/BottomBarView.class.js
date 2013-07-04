Namespace.register("com.gleasy.os.view");

/**
 * 桌面任务栏VIEW
 * 
 * 
 */
com.gleasy.os.view.BottomBarView = function(){
	var _this = this;	
	var name = "com.gleasy.os.view.BottomBarView";//名称
	var interests = [Constants.command.clearActiveLink,
					Constants.command.os.frame.focus,
					Constants.command.os.frame.close,
					Constants.command.loadDesktopOK,
					Constants.command.os.dock.changeInfo,
					Constants.command.os.dock.changeIconInfo,
					Constants.command.os.dock.addMessage,
					Constants.command.os.dock.removeMessage,
					Constants.command.os.loginSuccess,
					Constants.command.os.updateAppDockNum,
					Constants.command.os.updateAppMessage,
					Constants.command.os.dock.create
			];//监听的消息IDs
	
	var view = null;
	
	var dock = null;
	var dockView = null;
	
	var pin = null;
	var pinView = null;
	var os = null;
	
	/**
	 * 模拟构造函数
	 */
	var _construct = function(){
		_this.setInterests(interests);
	}
	
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
		os = $(".osWrap");
		view = $("div.oTaskOnBottom",os);
		
		view.css("zIndex",Constants.config.os.layer.bottomBar);
		
		pinView = $("div.oTaskFixedList",view);
		dockView =  $("div.oTaskShortList",view);
		
		$LAB
		.script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/core_v2/view/IconPan.class.js")
		.script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/core_v2/view/GridLayoutIconPan.class.js")
		.script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/core_v2/view/BottomIconPan.class.js")
   		.script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/core_v2/view/Dock.class.js")
   		.script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/core_v2/view/Pin.class.js")
   		.wait(function(){
			dock = new com.gleasy.os.Dock({
				dragContainment:$(".osWrap"),
				dragAxis:false,
				view:dockView,
				width:300,
				showGrid:false,
				dataChange:function(){
					resize();
				}
			});
			pin = new com.gleasy.os.Pin({
				dragContainment:$(".osWrap"),
				view:pinView,
				dragAxis:false,
				showGrid:false,
				width:300,
				dataChange:function(){
					_this.sendNotify(Constants.command.desktopDataChangeByUser,{});
					resize();
				}
			});			
			resize();
			
			_this.inited = true;
			_this.processQueue();
   		});
   		  		
   		configEvent();
		$(window).resize(function(){
		  resize(true);
		});
		
		return true;
	}

	var findProcessByPid = function(pid){
		for(var i=0;i<com.gleasy.os.view.ProcessManager.processList.length;i++){
			var p = com.gleasy.os.view.ProcessManager.processList[i];
			if(p.getPid() == pid){
				return p;
			}
		}	
		return null;
	}
		
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		$.log(this.getName()+" 收到消息："+eventId);
		if(Constants.command.clearActiveLink == eventId){
			//TODO　清除所有选中
		}else if(Constants.command.os.dock.create == eventId){
			var pid = messageBody.pid;
			var process = findProcessByPid(pid);
			if(!process){
				$.log("BottomBarView.class.js 115 process is null");
				return;	
			}
			var app = process.getApp();
			if(!app){
				$.log("BottomBarView.class.js 120 app is null");
				return;
			}
			
			if(pin.findIconByApp(app)){
				pin.addDock(messageBody);
			}else{
				dock.addDock(messageBody);
			}
			//adjustWidth();
		}else if(Constants.command.os.frame.focus == eventId){
			pin.focusDock(messageBody);
			dock.focusDock(messageBody);
		}else if(Constants.command.os.frame.close == eventId){
			var wid = messageBody.wid;
			if(!wid) return;
			var _dock = pin.findDockByWid(wid);
			if(!_dock) return;
			var app = _dock.app;
			if(!app){
				$.log("BottomBarView.class.js 120 app is null");
				return;
			}
			if(pin.findIconByApp(app)){
				pin.closeDock(messageBody);
			}else{
				dock.closeDock(messageBody);
			}
		}else if(Constants.command.loadDesktopOK == eventId){
			loadDesktopOK(messageBody);
		}else if(Constants.command.os.dock.changeInfo == eventId){
			changeDockInfo(messageBody);
		}else if(Constants.command.os.dock.addMessage == eventId){
			addMessageDock(messageBody);
		}else if(Constants.command.os.dock.removeMessage == eventId){
			removeMessageDock(messageBody);
		}else if(Constants.command.os.dock.changeIconInfo == eventId){
			changeIconInfo(messageBody);
		}else if(Constants.command.os.loginSuccess == eventId){
			view.show();
		}else if(Constants.command.os.updateAppDockNum == eventId){
			updateAppMessage(messageBody);
		}
		
		//removeMessage
	}
	
	var updateAppMessage = function(_param){
		if(typeof _param == 'string'){
			_param = $.evalJSON(_param);
		}
		var app = _param.app;
		if(!app) return;
		var icon = pin.findIconByApp(app);
		if(icon){
			pin.updateAppMessage(icon,_param);
		}else{
			icon = dock.findIconByApp(app);
			if(icon){
				dock.updateAppMessage(icon,_param);
			}else{
				window.application.sendNotify(Constants.command.os.updateAppDeskMessage,_param);
			}
		}
	}
	
	var addMessageDock = function(_param){
		var pid = _param.pid;
		var process = findProcessByPid(pid);
		if(!process){
			$.log("BottomBarView.class.js 115 process is null");
			return;	
		}
		var app = process.getApp();
		if(!app){
			$.log("BottomBarView.class.js 120 app is null");
			return;
		}
		
		_param.type = "message";
		
		if(pin.findIconByApp(app)){
			pin.addDock(_param);
		}else{
			dock.addDock(_param);
		}		
	}
	
	var removeMessageDock = function(_param){
		var wid = _param.wid;
		if(!wid) return;
		var _dock = pin.findDockByWid(wid);
		if(!_dock) return;
		var app = _dock.app;
		if(!app){
			$.log("BottomBarView.class.js 120 app is null");
			return;
		}
		if(pin.findIconByApp(app)){
			pin.closeDock(_param);
		}else{
			dock.closeDock(_param);
		}		
	}
	
	var changeDockInfo = function(_param){
		var wid = _param.wid;
		if(!wid) return;
		var _dock = pin.findDockByWid(wid);
		if(!_dock) return;
		var app = _dock.app;
		if(!app){
			$.log("BottomBarView.class.js 166 app is null");
			return;
		}
		var icon = pin.findIconByApp(app);
		if(icon){
			pin.changeDockInfo(_dock,icon,_param);
		}else{
			icon = dock.findIconByApp(app);
			dock.changeDockInfo(_dock,icon,_param);
		}		
	}
	
	var changeIconInfo = function(_param){
		var wid = _param.wid;
		if(!wid) return;
		var _dock = pin.findDockByWid(wid);
		if(!_dock) return;
		var app = _dock.app;
		if(!app){
			$.log("BottomBarView.class.js 234 app is null");
			return;
		}
		var icon = pin.findIconByApp(app);
		if(icon){
			pin.changeIconInfo(icon,_param);
		}else{
			icon = dock.findIconByApp(app);
			dock.changeIconInfo(icon,_param);
		}
	}
	var loadDesktopOK = function(pageDataList){
		if(pin){
			pin.empty();
		}
		var ds = null;
		for(var i=0;i<pageDataList.length;i++){
			if(pageDataList[i].pageId == Constants.command.os.iconPan.pinId){
				ds = pageDataList[i];
				break;
			}
		}
		if(!ds){
			ds = {
				pageId:Constants.command.os.iconPan.pinId,
				datasource:[]
			};
			pageDataList.push(ds);
		}
		ds.pan = pin;
		pin.setDatasource(ds.datasource);
		ds.datasource = pin.icons;
		
		for(var i=0;i<pin.icons.length;i++){
			var icon = pin.icons[i];
			var _icon = dock.findIconByApp(icon.app);
			_icon && dock.removeIcon(_icon,true);
		}
		resize();
	}
	
	
	/**
	 * 根据浏览器窗口大小调整本控件的大小
	 */
	var resize = function(force){
		var osWidth = os.width();
		var osHeight = os.height();
		var rightLength = $(".oTaskRight",view).width() + 10;
		//var rightLength = 0;
		var cutLineWidth = 0;
		if(dock && dock.icons.length){
			$("div.oCutLine",view).show();
			cutLineWidth = $("div.oCutLine",view).width();
		}else{
			$("div.oCutLine",view).hide();
		}
		var minWidth = pin.getMinWidth() + dock.getMinWidth() + rightLength + cutLineWidth;
		if(osWidth < minWidth) return;
		if(osHeight < 400) return;
		
		
		var wrap = $(".taskbarWrap",view);	
		wrap.css({
			left:0,
			width:osWidth - rightLength
		});
		
		var leftLen = pin.getTotalWidth();
		var rightLen = cutLineWidth +  dock.getTotalWidth();
		var totalWidth =  leftLen + rightLen;
		var space = osWidth - rightLength - totalWidth;
		
		var leftAdjust = rightLength;
		if(space > 0){	
			var dockPlus = 0;	
			var pinplus = 0;
			if(space/2 < leftAdjust){
				dockPlus = 0;
				pinplus = space/2;
			}else{
				dockPlus = space/2 - leftAdjust;
				pinplus = space/2 + leftAdjust;
			}
			
			var plus = dock.setWidth(dockPlus + dock.getTotalWidth());
			pinView.width(pinplus + pin.getTotalWidth() + plus);
		}else{
			var pinWidth = pin.getTotalWidth();
			var dockWidth = totalWidth - cutLineWidth - pinWidth + space;
			var dockMinwidth = dock.getMinWidth();
			if(dockMinwidth>dockWidth){
				dockWidth = dockMinwidth;
				pinWidth = pinWidth - (dockMinwidth - dockWidth);
			}
			leftAdjust = 0;
			//pinView.width(pinWidth);
			var plus = dock.setWidth(dockWidth);			
			pinView.width(pinWidth + plus);
		}
//		$("div.oCutLine",view).css({
//				position:'absolute',
//				left:pinView.width() + leftAdjust
//		});
		var left = cutLineWidth + pinView.width() ;
		dock.setXY(left,0);
		
		dock.refresh();
		pin.refresh();
		
		/*
		setTimeout(function(){
			
		},500);
		*/
	}

	
	/**
	 * 配置事件监听器
	 */
	var configEvent = function(){  
		view.click(function(ev){
			var tags = ['.oIco', '.oShortScrollBtn', '.frameDock','.dockIconWin']; 
			
			if(!$(ev.target).closest(tags).length){
				var x = ev.pageX;
				var y = ev.pageY;
				if(x < view.offset().left || y < view.offset().top){
					return;
				}
				_this.sendNotify(Constants.command.os.frame.toggle,"");
				_this.sendNotify(Constants.command.clearActiveLink, "");
				ev.preventDefault();//阻止冒泡
		    	ev.stopPropagation();
			}
		
		});
		
		$LAB
   		.script(Constants.config.assetPrefix+"/script/jquery/jquery.reflection.js")
   		.wait(function(){
			$("img.oIcon",view).reflect({width:45,height:12,opacity:0.7});
			$("img.oIconBg",view).reflect({width:45,height:12,opacity:0.4,canvasClass:"bgReflectCanvas"});
   		});
		
	}
	
	//构造函数调用
	_construct();
}

//继承MvcBase
com.gleasy.os.view.BottomBarView.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本View
window.application.addModel(new com.gleasy.os.view.BottomBarView());
