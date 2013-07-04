Namespace.register("com.gleasy.os.view");

/**
 * Frame窗口管理器
 * 
 */
com.gleasy.os.view.FrameManager = function(){
	var _this = this;
		
	var name = "com.gleasy.os.model.FrameManager";//名称
	var interests = [Constants.command.os.frame.create,
					Constants.command.os.frame.clearfocus,
					Constants.command.os.frame.addEventListener,
					Constants.command.os.frame.focus,
					Constants.command.os.frame.toggle,
					Constants.command.os.frame.setContent,
					Constants.command.os.frame.forceclose,
					Constants.command.os.frame.close,
					Constants.command.os.frame.createAndShow,
					Constants.command.clearActiveLink,
					Constants.command.os.frame.setVisible,
					Constants.command.os.frame.showHideFrameGroup,
					Constants.command.os.frame.runCommand];
	
	var frameList = [];
	var indexStack = [];
	
	var frameContainer = null;
	
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
		var os = $(".osWrap");
		frameContainer = $(".desktop",os);
		configWindowEvent();
	}
	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		//$.log(messageBody);
		$.log(this.getName()+" 收到消息 "+eventId);
		if(eventId == Constants.command.os.frame.toggle){
			showHideDesktop();
		}else if(eventId == Constants.command.os.frame.create){
			var param = messageBody;
			createFrame(param);
		}else if(eventId == Constants.command.os.frame.addEventListener){
			addEventListener(messageBody);
		}else if(eventId == Constants.command.os.frame.setVisible){
			setVisible(messageBody);
		}else if(eventId == Constants.command.os.frame.clearfocus){
			unfocusAll();
		}else if(eventId == Constants.command.os.frame.setContent){
			setContent(messageBody);
		}else if(eventId == Constants.command.os.frame.focus){
			doFocus(messageBody);
		}else if(eventId == Constants.command.os.frame.createAndShow){
			createAndShow(messageBody);
		}else if(Constants.command.os.frame.forceclose == eventId){
			closeFrame(messageBody);
		}else if(Constants.command.os.frame.close == eventId){
			frameClosed(messageBody);
		}else if(Constants.command.os.frame.runCommand == eventId){
			runCommand(messageBody);
		}else if(Constants.command.os.frame.showHideFrameGroup == eventId){
			showHideFrameGroup(messageBody);
		}else if(Constants.command.clearActiveLink == eventId){
			blurAll(messageBody);
		}
	}
	
	
	/**
	 * blur all
	 */
	var blurAll = function(messageBody){
		var t = null;
		if(messageBody && messageBody.target){
			t = messageBody.target;
		}
		for(var i=0;i<frameList.length;i++){
			var frame = frameList[i];
			//_this.noticeListener("resizeFinish", _this.getContentSize());
			if(t){
				var tp = t.closest(".oWindow");
				
				if(tp && tp.attr("wid") != frame.view.attr("wid")){
					frame && frame.noticeListener("blur","");
				}
			}
		}
	}
	
	var runCommand = function(param){
		var wid = param.wid;
		var frame = top.com.gleasy.os.view.Container.getByWid(wid);
		if(frame == null || !frame){
			return;
		}
		frame.runCommand(param.data);
	}

	var frameClosed = function(param){
		var wid = param.wid;
		var frame = findFrameByWid(wid);
		if(frame == null || !frame){
			return;
		}
		var focused = frame.isFocus();
		var index = $.inArray(frame,frameList);		
		frameList.splice(index,1);			
		if(focused){
			focusNextTopFrame();
		}
	}
	
	var createAndShow = function(param){
		var frame = createFrame(param);
		frame.setVisible(true);	
	}
	
	var closeFrame = function(param){
		var wid = param.wid;
		var frame = findFrameByWid(wid);
		if(frame == null || !frame){
			return;
		}
		
		var index = $.inArray(frame,frameList);		
		if(frame.close()){
			
		}
	}
	
	
	var focusNextTopFrame = function(){
		var topFrame = null;
		for(var i=0;i<frameList.length;i++){
			var frame = frameList[i];
			if(!frame.isVisible()){
				continue;
			}
			if(topFrame == null){
				topFrame = frame;
			}else{
				if( frame.getZindex() > topFrame.getZindex()){
					topFrame = frame;
				}
			}
		}
		doFocusFrame(topFrame);
	}
	
	/**
	 * 更新窗口内容
	 */
	var setContent = function(param){
		var wid = param.wid;
		var content = param.content;
		var frame = findFrameByWid(wid);
		if(frame == null || !frame){
			return;
		}
		frame.setContent(content);		
	}
	
	/**
	 * 把所有窗口设为非激活状态
	 */
	var unfocusAll = function(){
		for(var i=0;i<frameList.length;i++){
			var frame = frameList[i];
			frame.setFocus(false,i);
		}		
	}
	
	/**
	 * 选中激活某个窗口
	 */
	var doFocus = function(param){
		var wid = param.wid;
		var frame = findFrameByWid(wid);
		doFocusFrame(frame);
	}
	
	var doFocusFrame = function(frame){
		if(frame == null || !frame){
			return;
		}
		var index = $.inArray(frame,frameList);		
		frameList.splice(index,1);
		frameList.push(frame);
		
		unfocusAll();
		frame.setFocus(true,frameList.length);			
	}
	
	/**
	 * 显示某个窗口
	 */
	var setVisible = function(param){
		var wid = param.wid;
		var visible = param.visible;
		
		var frame = findFrameByWid(wid);
		if(frame == null || !frame){
			return;
		}
		if(visible == 'toggle'){
			if(frame.isVisible()){
				if(frame.isFocus()){
					frame.setVisible(false);
				}else{
					window.application.sendNotify(Constants.command.os.frame.focus,{wid:wid});
				}
			}else{
				frame.setVisible(true);
				window.application.sendNotify(Constants.command.os.frame.focus,{wid:wid});
			}
		}else{
			var isVisible = frame.isVisible();
			if(visible != isVisible){
				frame.setVisible(visible);
			}
			if(visible){
				window.application.sendNotify(Constants.command.os.frame.focus,{wid:wid});
			}else{
				var focused = frame.isFocus();
				if(focused){
					focusNextTopFrame();
				}
			}
		}		
	}
	
	/**
	 * 新建窗口
	 */
	var createFrame = function(param){
		var pid = param.parent?param.parent.getPid():null;
		if(!pid){
			pid = param.pid
		}
		if(!pid){
			$.log("FrameManager.class.js 196. 进程ID(pid)为空，不能创建窗体，请确保传入了pid属性.");
			return null;
		}
		var callback = param.callback;
		var clazz = param.clazz;
		param.container = frameContainer;
		var frame = null;
		$.log("typeof clazz = "+typeof clazz);
		if(typeof clazz == 'function' ){
			frame = new clazz(param);
		}else if(typeof clazz == 'string'){
			eval("frame = new "+clazz+"(param);");
		}else{
			frame = new com.gleasy.os.view.Frame(param);
		}
		if(frame == null){
			return;
		}
		frameList.push(frame);
		
		(typeof callback == 'function') && callback({wid:frame.getWid()});
		return frame;
	}
	
	/**
	 * 添加事件监听
	 */
	var addEventListener = function(param){
		var event = param.event;
		var callback = param.callback;
		var wid = param.wid;
		if(!event || !callback || !wid) return;
		var frame = findFrameByWid(wid);
		if(frame == null || !frame){
			return;
		}
		frame.addEventListener(event,callback);
	}
	
	
	var findFrameByWid = function(wid){
		for(var i=0;i<frameList.length;i++){
			var frame = frameList[i];
			if(wid == frame.getWid()){
				return frame;
			}
		}
	}
	var configWindowEvent = function(){
	}
	
	
	var showHideFrameGroup = function(wids){
		if(!wids || !wids.length) return;
		
		var group = [];
		$.each(wids,function(idx,wid){
			group.push(findFrameByWid(wid));
		});
		var hasFocus = 0;
		var showCount = 0;
    	for(var i=0;i<group.length;i++){
			var frame = group[i];
			if(frame.isVisible()){
				showCount++;
			}
			if(frame.isFocus()){
				hasFocus = 1;
			}
    	}
    	//$.log("showCount="+showCount+";wids.length="+wids.length);
    	if(showCount < wids.length){
    		var frame = null;
			for(var i=0;i<group.length;i++){
				frame = group[i];
				frame.setVisible(true);
			}
			window.application.sendNotify(Constants.command.os.frame.focus,{wid:frame.getWid()});
    	}else{
    		if(!hasFocus){
    			window.application.sendNotify(Constants.command.os.frame.focus,{wid:group[0].getWid()});
    			return;
    		}
    		var frame = null;
			for(var i=0;i<group.length;i++){
				frame = group[i];
				frame.setVisible(false);
			}
    	}
	}
	
	/**
	 * 显隐桌面
	 */
	var showHideDesktop = function(){
		var group = [];
		var showCount = 0;
    	for(var i=0;i<frameList.length;i++){
			var frame = frameList[i];
			if(frame.isVisible() && !frame.isWidget()){
				showCount++;
			}
			if(!frame.isWidget()){
				group.push(frame);
			}
    	}
    	$.log("showCount="+showCount);
    	if(showCount > 0){
    		for(var i=0;i<group.length;i++){
    			var frame = group[i];
				var state = frame.getState();
				if(state == 'show'){
					frame.setVisible(false);
				}
			}
    	}else{
    		for(var i=0;i<group.length;i++){
				var frame = group[i];
				var state = frame.getState();
				if(state == 'hide'){
					frame.setVisible(true);
				}
			}
    	}
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
com.gleasy.os.view.FrameManager.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本View
window.application.addModel(new com.gleasy.os.view.FrameManager());
