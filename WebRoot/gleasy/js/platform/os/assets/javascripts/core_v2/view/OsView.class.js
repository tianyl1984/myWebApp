Namespace.register("com.gleasy.os.view");

/**
 * Gleasy Os 主View
 * 
 * 
 */
com.gleasy.os.view.OsView = function(){
	var _this = this;	
	var name = "com.gleasy.os.view.OsView";//名称
	var interests = [
		Constants.command.system.alert,
		Constants.command.system.confirm,
		Constants.command.os.cancelFullscreen,
		Constants.command.os.toFullscreen,
		Constants.command.os.systemexit,
		Constants.command.os.showScreenMask,
		Constants.command.os.hideScreenMask,
		Constants.command.os.showDesktopMask,
		Constants.command.os.hideDesktopMask,		
		Constants.command.os.requireLogin,
		Constants.command.os.openUrl,
		Constants.command.setDragData,
		Constants.command.getDragData,
		Constants.command.loadDesktopOK,
		Constants.command.os.savePageImage,
		Constants.command.os.loginSuccess,
		Constants.command.system.registerHotkeyHandler,
		Constants.command.im.connection.disconnected,
		Constants.command.im.sessionStart
	];//监听的消息IDs
	
	var view = null;

	var dialogs = {};
	
	var keyHandlerMap = {};
	var screenMask = null;
	var showLogin = false;
	
	var desktopMask = null;
	var loginDialog = null;
	
	var bootLoading = null;
	var connectedEver = false;
	
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
		view = $(document);
		configEvent();
		resize();
		$(window).resize(function(){
		  resize();
		});
		jQuery.event.props.push('dataTransfer');

		
//		screenMask = $("<div class='mdBg' />");
//		screenMask.appendTo($(document.body));
//		screenMask.css("zIndex",Constants.config.os.layer.screenMask);
//		screenMask.hide();
//		
//		desktopMask = $("<div class='mdBg' />");
//		desktopMask.appendTo($(".desktop"));
//		desktopMask.css("zIndex",Constants.config.os.layer.screenMask);
//		desktopMask.hide();
		
        if(!isBrowserSupport()) {
            window.application.sendNotify(Constants.command.os.notSupportBrowser);
            return;
        }
        
//		parseRegFromUrl();
		
		typeof gleasy != 'undefined' && typeof gleasy.send == 'function' && gleasy.send("onShowLoadingState",[false]);
		
		//window.application.sendNotify(Constants.command.os.process.runProgram,{pname:"安全卫士",sysonly:true});
	}
	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		$.log(this.getName()+" 收到消息："+eventId);
		if(Constants.command.system.alert == eventId){
			showAlert(messageBody);
		}else if(Constants.command.system.confirm == eventId){
			showConfirm(messageBody);
		}else if(Constants.command.system.registerHotkeyHandler == eventId){
			registerHotkeyHandler(messageBody);
		}else if(Constants.command.os.toFullscreen == eventId){
			toFullscreen(messageBody);
		}else if(Constants.command.os.cancelFullscreen == eventId){
			cancelFullscreen(messageBody);
		}else if(Constants.command.os.systemexit == eventId){
			if(JsWindowControl){
				JsWindowControl.exit();
			}else{
			}
		}else if(Constants.command.os.showScreenMask == eventId){
			showScreenMask();
		}else if(Constants.command.os.hideScreenMask == eventId){
			hideScreenMask();
		}else if(Constants.command.os.showDesktopMask == eventId){
			showDesktopMask();
		}else if(Constants.command.os.hideDesktopMask == eventId){
			hideDesktopMask();
		}else if(Constants.command.os.requireLogin == eventId){
			requireLogin(messageBody);
			showConnecting(false);
			connectedEver = false;
		}else if(Constants.command.os.openUrl == eventId){
			openUrl(messageBody,true);
		}else if(Constants.command.getDragData == eventId){
			var callback = messageBody.callback;
			callback && typeof callback=='function' && callback(com.gleasy.os.Clipboard.dragData);
		}else if(Constants.command.setDragData == eventId){
			com.gleasy.os.Clipboard.dragData = messageBody;
		}else if(Constants.command.loadDesktopOK == eventId){
		}else if(Constants.command.os.loginSuccess == eventId){
			hideScreenMask();
			
			$(".intro",view).hide();	
			showLogin = false;
			parseAndInitFromUrl();
			
			loginDialog && loginDialog.exit();
		}else if(Constants.command.im.connection.disconnected == eventId){
			connectedEver && showConnecting(true);
		}else if(Constants.command.im.sessionStart == eventId){
			showConnecting(false);
			connectedEver = true;
		}else if(Constants.command.os.savePageImage == eventId){
			savePageImage(messageBody);
		}
	}
	
	var savePageImage = function(param){
		var type = param.type;
		var content = param.content;
		var name = param.name;
		if(!content) return;
		if("base64"==type){
			var image = content;
			var param = {
				parent:_this,
				filter:['*.jpg','*.png','*.gif','*.icon','*.tif','*.jpeg','*.bmp'],
				mode:"save",
				name:"未命名",
				type:'all',//可选值为folder,file,all
				multi:false,//是否支持返回多个项,true false
				select:function(params){
				   var pid = params.pid;
				   var fid = params.fid;
				   var rename = params.rename;
				   window.application.sendNotify(Constants.command.os.process.sendMessage,{
						pname:"一盘守护进程",
						eventId:"saveFile",
						messageBody:{
							appName:"fconv",
							id:fid,
							pid:pid,
							name:rename,
							fileContent:image,
							contentType:"base64_byte",
							success:function(data){
								$.log("保存成功");
							},
							error:function(){
								$.log("保存失败");
							}
						}
					});
				}
			};
			window.application.sendNotify(Constants.command.os.process.sendMessage,{
				pname:"一盘守护进程",
				eventId:"openFileBrowserDialog",
				messageBody:param
			});	
		}
	}
	
	var showConnecting = function(show){
		if(show){
			if(!com.gleasy.os.SystemInfo.login) return;
			$(".connectTip",view).show();
		}else{
			$(".connectTip",view).hide();
		}
	}
	
	var checkBrowserSupport = function(config){
		var supportBrowser = config.supportBrowser;
		if(!supportBrowser) return true;
		if(typeof supportBrowser == 'string'){
			config.supportBrowser = supportBrowser = $.evalJSON(supportBrowser);
		}
		var sys = getBrowserVersion();
		var type = sys.type;
		var version = sys.version;
		var supprtV = supportBrowser[type];
		
		var message = "";
		if(supprtV){
			if(supprtV == 'all'){
				return true;
			}else if(supprtV.indexOf(version) > -1){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	var openUrl = function(url,force){
		var p = new Poly9.URLParser(url);
		if(url.indexOf("/home/")>-1){
			return false;
		}
		if(p.getHost() == window.location.hostname && p.getPort() == window.location.port && (p.getPathname() == '/' ||  p.getPathname() == '/os/')){
			parseAndInitFromUrl(url);
			return true;
		}
		
		
		if(!com.gleasy.os.SystemInfo.login){
			return false;
		}
		var config = com.gleasy.os.reg.programDef["一览守护进程"];
		window.application.sendNotify(Constants.command.os.process.sendMessage, {
			pname:"一览守护进程",
			eventId:"openUrl",
			messageBody:{url:url, force : force}
		});
		return true;
	}
	
	var requireLogin = function(messageBody){
		if(showLogin) return;
		showLogin = true;
		showScreenMask();
		var p = {
			container:$(document.body),
			panel:messageBody.panel,
			message:messageBody.msg
		};
        var dialog = new com.gleasy.os.login.view.SystemLockContainer(p);
        dialog.setVisible(true);
		loginDialog = dialog;

			
		$(".intro",view).show();	
	}

	var showDesktopMask = function(){
		desktopMask.show();
	}
	
	var hideDesktopMask = function(){
		desktopMask.hide();
	}
		
	var showScreenMask = function(){
		screenMask.show();
	}
	
	var hideScreenMask = function(){
		screenMask.hide();
	}
	
	
	var toFullscreen = function(){
		if(JsWindowControl){
			JsWindowControl.fullScreen(true);
		}else{
			alert("JsWindowControl不存在");
		}
	}
	
	var cancelFullscreen = function(){
		if(JsWindowControl){
			JsWindowControl.fullScreen(false);
		}else{
				alert("JsWindowControl不存在");
			}
	}
	
	var registerHotkeyHandler = function(param){
		var key = param.key;
		if(!key) return;
		var handler = param.callback;
		var cache = keyHandlerMap[key];
		if(!cache){
			cache = keyHandlerMap[key] = [];
			var k = key;
	    	view.bind('keydown', k ,function (evt){
					runHandlers(k);
					return false;
			});
		}
		cache.push(handler);
	}

	var runHandlers = function(key){
		var cache = keyHandlerMap[key];
		$.each(cache,function(k,fn){
			try{
				fn && typeof fn == 'function' && fn();
			}catch(e){
				$.log(e);
			}
		});
	}
	
	var showAlert = function(param){
		if(param == null) return;
		if(!param.title){
			param.title = "提示";
		}
		var dialog = new com.gleasy.os.view.MessageDialog(param);
		dialog.setVisible(true);
	}
	
	var showConfirm = function(param){
		if(param == null) return;
		if(!param.title){
			param.title = "提示";
		}
		param.confirm = true;
		if(param.system){
			showDesktopMask();
		}
		var dialog = new com.gleasy.os.view.MessageDialog(param);
		dialog.addEventListener("close",function(){
			hideDesktopMask();
		});
		dialog.setVisible(true);
	}
			
	/**
	 * 根据浏览器窗口大小调整本控件的大小
	 */
	var resize = function(){

	}

	
	/**
	 * 用于通过URL进行测试时使用
	 */
	var parseAndInitFromUrl = function(url){
 		try{
 			if(!url){
 				url = location.href;
 			}
			var p = new Poly9.URLParser(url);
			var run = p.getQuery("run");
			if(run == null) return;
			run = decodeURIComponent(run);
			var param = $.evalJSON(run);
			window.application.sendNotify(Constants.command.os.process.sendMessage,param);
 		}catch(e){
 			$.log(e);
 		}
	}


	/**
	 * 解析邀请注册
	 */
	var parseRegFromUrl = function(url){
 		try{
 			if(!url){
 				url = location.href;
 			}
			var p = new Poly9.URLParser(url);
			var from = p.getQuery("from");
			if(from == null){
                window.application.sendNotify(Constants.command.os.fireHeartBeat);
                return;
            }
			window.application.sendNotify("inviteReg",{inviter: from});
 		}catch(e){
 			$.log(e);
 		}
	}

    var isBrowserSupport = function() {
        var bs = getBrowserVersion();
        var supportBs = Constants.config.os.supportBrowser;
        for(var i=0; i<supportBs.length; i++) {
            var item = supportBs[i];
            if(item.type == bs.type) {
                if(item.minVer) {
                    if( bs.version >= item.minVer) {
                        return true;
                    }
                }else{
                    return true;
                }
            }
        }
        return false;

    };
	
	var getFirstLevelDomain = function(url){
		if(url == null || url.length == 0) return;
		if(url.indexOf(":") >= 0){
			url = url.split(":")[0];
		}
	
		var doms = url.split(".");
		if(doms.length <= 2) return url;
		return doms.slice(-2).join(".");
	}
	
	var messageHandler = function(evt){
		var data = evt.data;
		if(typeof data == 'string'){
			data = $.evalJSON(data);
		}
		var eventId = data.eventId;
		var messageBody = data.messageBody;
		if(eventId){
			window.application.sendNotify(eventId,messageBody);
		}
	}
	
	var configEvent = function(){
		var ua = navigator.userAgent.toLowerCase();
	    if(!ua.match(/msie ([\d.]+)/)){
//			document.domain = getFirstLevelDomain(location.host.split(":")[0]);
	    }
	    
		if (window.location !== window.top.location) {
          window.top.location = window.location;
        }
       	view.bind('contextmenu', function(ev) {
       			if (/textarea|select/i.test( ev.target.nodeName )) {
					return true;
				}
				if (ev.target.type == "text"|| ev.target.type == "password") {
					return true;
				}
				var b = $(ev.target).attr("contenteditable");
				if (b == "true"||b == "TRUE" || b== "1") {
					return true;
				}
				if ($(ev.target).closest(".allowContexMenu").length) {
					return true;
				}
				
				return false;
        });
        
      
        window.onbeforeunload = function(){
        	if(!com.gleasy.os.SystemInfo.login){
				$.log("用户未登录，不提供服务.");
				return;
			}
		 	return "确认要退出Gleasy OS?请确认己保存所有资料.";
		}
        
 		view.click(function(ev){
 			//$.log("Osview 收到 了");
			//_this.sendNotify(Constants.command.clearActiveLink, "");
			//ev.preventDefault();//阻止冒泡
		    //ev.stopPropagation();
		});       

        
        view.delegate("a.urlLink","click",function(ev){
        	var a = $(this);
        	var url = a.attr('href');
        	if(openUrl(url)){
        		ev.preventDefault();//阻止冒泡
		    	ev.stopPropagation();
        		return false;
        	}else{
        		a.attr("target","_blank");
        		return true;
        	}
        }).delegate("a","click",function(ev){
        	if($(ev.target).closest("a.urlLink").length){
        		return true;
        	}
        	ev.preventDefault();//阻止冒泡
	    	ev.stopPropagation();
    		return false;
        });
        
		$(document.body).bind("dragenter",function(evt){
			com.gleasy.os.view.Container.showDragMask();
			evt.preventDefault();//阻止冒泡
		    evt.stopPropagation();
		}).bind("dragexit dragleave",function(evt){
			//com.gleasy.os.view.Container.hideDragMask();
			evt.preventDefault();//阻止冒泡
		    evt.stopPropagation();
		}).bind("dragover",function(evt){
			evt.preventDefault();//阻止冒泡
		    evt.stopPropagation();
		}).bind("drop",function(evt){
			com.gleasy.os.view.Container.hideDragMask();
			evt.preventDefault();//阻止冒泡
		    evt.stopPropagation();
		    //TODO --默认上传行为
		});  
		
		if (window.addEventListener) {
			// For standards-compliant web browsers
			window.addEventListener("message", messageHandler, false);
		}
		else {
			window.attachEvent("onmessage", messageHandler);
		}
		
		registerHotkeyHandler({
			key:"Shift+t",
			callback:function(){
				window.application.sendNotify(Constants.command.os.process.runProgram,{
					pname:"API测试器"
				});
			}
		});
		
		registerHotkeyHandler({
			key:"Shift+h",
			callback:function(){
				window.application.sendNotify(Constants.command.os.process.runProgram,{
					pname:"os帮助"
				});
			}
		});
		registerHotkeyHandler({
			key:"backspace",
			callback:function(){
				return false;
			}
		});
	}
	
	
	//构造函数调用
	_construct();
}

//继承MvcBase
com.gleasy.os.view.OsView.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本View
window.application.addModel(new com.gleasy.os.view.OsView(),2);
