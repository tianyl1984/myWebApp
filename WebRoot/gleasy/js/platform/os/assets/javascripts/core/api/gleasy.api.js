/**
 * Hotkey组件
 */
(function(jQuery){

	jQuery.hotkeys = {
		version: "0.8",

		specialKeys: {
			8: "backspace", 9: "tab", 13: "return", 16: "shift", 17: "ctrl", 18: "alt", 19: "pause",
			20: "capslock", 27: "esc", 32: "space", 33: "pageup", 34: "pagedown", 35: "end", 36: "home",
			37: "left", 38: "up", 39: "right", 40: "down", 45: "insert", 46: "del", 
			96: "0", 97: "1", 98: "2", 99: "3", 100: "4", 101: "5", 102: "6", 103: "7",
			104: "8", 105: "9", 106: "*", 107: "+", 109: "-", 110: ".", 111 : "/", 
			112: "f1", 113: "f2", 114: "f3", 115: "f4", 116: "f5", 117: "f6", 118: "f7", 119: "f8", 
			120: "f9", 121: "f10", 122: "f11", 123: "f12", 144: "numlock", 145: "scroll", 191: "/", 224: "meta"
		},

		shiftNums: {
			"`": "~", "1": "!", "2": "@", "3": "#", "4": "$", "5": "%", "6": "^", "7": "&", 
			"8": "*", "9": "(", "0": ")", "-": "_", "=": "+", ";": ": ", "'": "\"", ",": "<", 
			".": ">",  "/": "?",  "\\": "|"
		}
	};

	function keyHandler( handleObj ) {
		// Only care when a possible input has been specified
		if ( typeof handleObj.data !== "string" ) {
			return;
		}

		var origHandler = handleObj.handler,
			keys = handleObj.data.toLowerCase().split(" ");

		handleObj.handler = function( event ) {
			if ( this !== event.target && (/textarea|select/i.test( event.target.nodeName ) ||
				 event.target.type == "text"|| event.target.type == "password"  || "true"==$(event.target).attr("contenteditable")) ) {
				return;
			}
			// Keypress represents characters, not special keys
			var special = event.type !== "keypress" && jQuery.hotkeys.specialKeys[ event.which ],
				character = String.fromCharCode( event.which ).toLowerCase(),
				key, modif = "", possible = {};

			// check combinations (alt|ctrl|shift+anything)
			if ( event.altKey && special !== "alt" ) {
				modif += "alt+";
			}

			if ( event.ctrlKey && special !== "ctrl" ) {
				modif += "ctrl+";
			}

			// TODO: Need to make sure this works consistently across platforms
			if ( event.metaKey && !event.ctrlKey && special !== "meta" ) {
				modif += "meta+";
			}

			if ( event.shiftKey && special !== "shift" ) {
				modif += "shift+";
			}

			if ( special ) {
				possible[ modif + special ] = true;

			} else {
				possible[ modif + character ] = true;
				possible[ modif + jQuery.hotkeys.shiftNums[ character ] ] = true;

				// "$" can be triggered as "Shift+4" or "Shift+$" or just "$"
				if ( modif === "shift+" ) {
					possible[ jQuery.hotkeys.shiftNums[ character ] ] = true;
				}
			}

			for ( var i = 0, l = keys.length; i < l; i++ ) {
				if ( possible[ keys[i] ] ) {
					return origHandler.apply( this, arguments );
				}
			}
		};
	}

	jQuery.each([ "keydown", "keyup", "keypress" ], function() {
		jQuery.event.special[ this ] = { add: keyHandler };
	});

})( jQuery );

gleasy = {};
gleasy.OSPROXYURL = "http://www.gleasy.com/gleasy.proxy.html";
gleasy.OSHOST = "www.gleasy.com";
gleasy.CALLBACKCACHE = {};
gleasy.CALLBACKKEY = 0;
gleasy.WID = window.name;
gleasy.toQueryString=function(obj){
	var result=[];
	for(var key in obj){
		var value = obj[key];
		if(typeof value != 'function'){
			result.push(encodeURIComponent(key)+"="+encodeURIComponent(value));
		}
	}
	return result.join("&");
}
if(location.host == gleasy.OSHOST){
	try{
		var ua = navigator.userAgent.toLowerCase();
		if(!ua.match(/msie ([\d.]+)/)){
			document.domain = getFirstLevelDomain(host);
	    }
	}catch(e){
	}
}
gleasy.fromQueryString=function(query){
	var param = {};
	var splits = query.split("&");
	for(var i=0;i<splits.length;i++){
		if(splits[i] && splits[i].length>0 && splits[i].indexOf("=")>-1){
			var pair = splits[i].split("=");
			var key = decodeURIComponent(pair[0]);
			var value = decodeURIComponent(pair[1]);
			param[key] = value; 
		}
	}
	return param;
}
var getFirstLevelDomain = function(url){
	if(url == null || url.length == 0) return;
	if(url.indexOf(":") >= 0){
		url = url.split(":")[0];
	}
	var doms = url.split(".");
	if(doms.length <= 2) return url;
	return doms.slice(-2).join(".");
}


gleasy.parseApiParam = function(_param,level){
	if(!_param) return;
	var l = level+1;
	if(l > 3) return;
	$.each(_param,function(k,v){
		if(typeof v == 'function'){
			var func = gleasy.register_callback(v);
			_param[k] = {
				type:"gleasyApiFunction",
				pointer:func
			}
		}else if(typeof v == 'object'){
			gleasy.parseApiParam(v,l);
		}
	});	
}
	
gleasy.postMessage = function(param){
	var host = location.host;
	if(host == gleasy.OSHOST){
		try{
			var ua = navigator.userAgent.toLowerCase();
			if(!ua.match(/msie ([\d.]+)/)){
				document.domain = getFirstLevelDomain(host);
		    }
			var container = top.com.gleasy.os.view.Container.getByWid(gleasy.WID);
			container.runCommand(param);
		}catch(e){
		}
		return;
	}
	
	if(getFirstLevelDomain(host) == getFirstLevelDomain(gleasy.OSHOST)){
	    var ua = navigator.userAgent.toLowerCase();
	    if(ua.match(/msie ([\d.]+)/)){
	    	//如果是IE
	    }else{
			try{
				document.domain = getFirstLevelDomain(host);
				var container = top.com.gleasy.os.view.Container.getByWid(gleasy.WID);
				container.runCommand(param);
			}catch(e){
			}
			return;	
	    }
	}
	
	if(typeof window.postMessage == 'function' || typeof window.postMessage == 'object'){
		//$.log("使用postmessage机制");
		gleasy.parseApiParam(param._param);
		var msg = {
				eventId:'os.frame.runCommand',
				messageBody:{
					wid:gleasy.WID,
					data:param
				}
		};
		top.postMessage($.toJSON(msg),"*");	
	}else{
		gleasy.parseApiParam(param._param);
		var div = $("<div/>");
		div.append("<iframe name='"+$.toJSON(param)+"' src='"+gleasy.OSPROXYURL+"#"+gleasy.toQueryString({target:'parent.window',host:gleasy.OSHOST,wid:gleasy.WID})+"' ></iframe>");
		div.hide();
		$("body").append(div);
	}
}

gleasy.messageHandler = function(evt){
	var data = evt.data;
	if(typeof data == 'string'){
		data = $.evalJSON(data);
	}
	var eventId = data._eventId;
	var param = data._param;
	if(eventId == "callback_response"){
		var fpointer = data._callback;
		gleasy.call_callback(fpointer,param);
	}
}
gleasy.registerMessageHandler = function(){
	if (window.addEventListener) {
		window.addEventListener("message", gleasy.messageHandler, false);
	}else {
		window.attachEvent("onmessage", gleasy.messageHandler);
	}
}

gleasy.register_callback = function(callback){
	var k = gleasy.CALLBACKKEY;
	gleasy.CALLBACKKEY = k + 1;
	gleasy.CALLBACKCACHE[k] = callback;
	return k;
}
gleasy.call_callback = function(key,param){
	var func = gleasy.CALLBACKCACHE[key];
	try{
		var result = func(param);
		if(!result){
			delete func;
			gleasy.CALLBACKCACHE[key] = null;
		}
	}catch(e){
		delete func;
		gleasy.CALLBACKCACHE[key] = null;
	}
	
}

gleasy.runCommand = function(data){
	if(typeof data == 'string'){
		data = $.evalJSON(data);
	}
	var eventId = data._eventId;
	var param = data._param;
	if(eventId == "callback_response"){
		var fpointer = data._callback;
		gleasy.call_callback(fpointer,param);
	}
}

gleasy.system = {};
gleasy.system.alert = function(param){
	var obj = {
		_eventId:"c.alert",
		_param:param
	};
	gleasy.postMessage(obj);
}
gleasy.system.confirm = function(param){
	var obj = {
		_eventId:"c.confirm",
		_param:param
	};
	gleasy.postMessage(obj);
}
gleasy.system.openUrl = function(param){
	var obj = {
		_eventId:"c.openUrl",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.createDialog = function(param){
	var obj = {
		_eventId:"c.createDialog",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.closeDialog = function(param){
	var obj = {
		_eventId:"c.closeDialog",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.closeAllDialog = function(param){
	var obj = {
		_eventId:"c.closeAllDialog",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.setWallpaper = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.setWallpaper",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.readFileContent = function(param){
	var obj = {
		_eventId:"c.readFileContent",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.readDirectory = function(param){
	var obj = {
		_eventId:"c.readDirectory",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.fileReceived = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.cloudDiskFileDrop",
		_param:param
	};
	gleasy.postMessage(obj);
}

gleasy.system.addEventListener = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.addEventListener",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.noticeListener = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.noticeListener",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.openAvatarEditor = function(param){
	var obj = {
		_eventId:"c.openAvatarEditor",
		_param:param
	};
	var originParam = {};
	$.extend(originParam,param);
	gleasy.postMessage(obj,originParam);	
}

gleasy.system.requestUrl = function(param){
	var obj = {
		_eventId:"c.requestUrl",
		_param:param
	};	
	gleasy.postMessage(obj);		
}
gleasy.system.getCurrentUser = function(param){
	var obj = {
		_eventId:"c.getCurrentUser",
		_param:param
	};		
	gleasy.postMessage(obj);		
}
gleasy.system.getInstalledApps = function(param){
	var obj = {
		_eventId:"c.getInstalledApps",
		_param:param
	};	
	gleasy.postMessage(obj);		
}
gleasy.system.setFileAssociation = function(param){
	var obj = {
		_eventId:"c.setFileAssociation",
		_param:param
	};		
	gleasy.postMessage(obj);		
}
gleasy.system.getFileAssociation = function(param){
	var obj = {
		_eventId:"c.getFileAssociation",
		_param:param
	};		
	gleasy.postMessage(obj,originParam);		
}
gleasy.system.print = function(param){
	var obj = {
		_eventId:"c.print",
		_param:param
	};	
	gleasy.postMessage(obj);		
}

gleasy.system.keyHandlerMap = {};
gleasy.system._registerHotkeyHandler = function(param){
		var key = param.key;
		if(!key) return;
		var handler = param.callback;
		var cache = gleasy.system.keyHandlerMap[key];
		if(!cache){
			cache = gleasy.system.keyHandlerMap[key] = [];
			$(document).bind('keydown', key ,function (evt){
				gleasy.system.runHandlers(key);
				return false;
			});
		}
		cache.push(handler);
}
gleasy.system.runHandlers = function(key){
		var cache = gleasy.system.keyHandlerMap[key];
		$.each(cache,function(k,fn){
			try{
				fn && typeof fn == 'function' && fn();
			}catch(e){
			}
		});
}
gleasy.system.registerHotkeyHandler = function(param){
	var obj = {
		_eventId:"c.registerHotkeyHandler",
		_param:param
	};
	gleasy.system._registerHotkeyHandler(param);
	gleasy.postMessage(obj);		
}
gleasy.system.runProgram = function(param){
	var obj = {
		_eventId:"c.runProgram",
		_param:param
	};	
	gleasy.postMessage(obj);
}
gleasy.system.osFullscreen = function(param){
	var obj = {
		_eventId:"c.osFullscreen",
		_param:param
	};	
	gleasy.postMessage(obj);
}
gleasy.system.osCancelFullscreen = function(param){
	var obj = {
		_eventId:"c.osCancelFullscreen",
		_param:param
	};		
	gleasy.postMessage(obj);
}
gleasy.system.systemexit = function(param){
	var obj = {
		_eventId:"c.systemexit",
		_param:param
	};
	gleasy.postMessage(obj);
}

gleasy.window = {};
gleasy.window.vars = {};
gleasy.window._this = {
	type:"gleasyApiFunction",
	subtype:"container"
};
gleasy.window.moveBy = function(param){
	var obj = {
		_eventId:"c.moveBy",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.setSize = function(param){
	var obj = {
		_eventId:"c.setSize",
		_param:param
	};
	gleasy.postMessage(obj);		
}
gleasy.window.setTitle = function(param){
	var obj = {
		_eventId:"c.setTitle",
		_param:param
	};
	gleasy.postMessage(obj);		
}
gleasy.window.maximize = function(param){
	var obj = {
		_eventId:"c.maximize",
		_param:param
	};
	gleasy.postMessage(obj);		
}
gleasy.window.unmaximize = function(param){
	var obj = {
		_eventId:"c.unmaximize",
		_param:param
	};
	gleasy.postMessage(obj);		
}
gleasy.window.minimize = function(param){
	var obj = {
		_eventId:"c.minimize",
		_param:param
	};
	gleasy.postMessage(obj);		
}
gleasy.window.setXY = function(param){
	var obj = {
		_eventId:"c.setXY",
		_param:param
	};
	gleasy.postMessage(obj);		
}
gleasy.window.close = function(param){
	var obj = {
		_eventId:"c.close",
		_param:param
	};
	gleasy.postMessage(obj);		
}
gleasy.window.center = function(param){
	var obj = {
		_eventId:"c.center",
		_param:param
	};
	gleasy.postMessage(obj);		
}
gleasy.window.fitRight = function(param){
	var obj = {
		_eventId:"c.fitRight",
		_param:param
	};
	gleasy.postMessage(obj);		
}
gleasy.window.fitBottom = function(param){
	var obj = {
		_eventId:"c.fitBottom",
		_param:param
	};
	gleasy.postMessage(obj);		
}
gleasy.window.setOffset = function(param){
	var obj = {
		_eventId:"c.setOffset",
		_param:param
	};
	gleasy.postMessage(obj);		
}
gleasy.window.registerExitConfirm = function(param){
	var obj = {
		_eventId:"c.registerExitConfirm",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.removeExitConfirm = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.removeExitConfirm",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.installApp = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.installApp",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.uninstallApp = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.uninstallApp",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.resizeFinish = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.resizeFinish",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.toFullscreen = function(param){
	var obj = {
		_eventId:"c.toFullscreen",
		_param:param
	};
	gleasy.postMessage(obj);		
}
gleasy.window.cancelFullscreen = function(param){
	var obj = {
		_eventId:"c.cancelFullscreen",
		_param:param
	};	
	gleasy.postMessage(obj);		
}
gleasy.window.showFrame = function(param){
	var obj = {
		_eventId:"c.showFrame",
		_param:param
	};	
	gleasy.postMessage(obj);		
}
gleasy.window.hideFrame = function(param){
	var obj = {
		_eventId:"c.hideFrame",
		_param:param
	};
	gleasy.postMessage(obj);		
}
gleasy.window.focus = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.focus",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.iframeLoadFinish = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.iframeLoadFinish",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.changeDockInfo = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.changeDockInfo",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.changeIconInfo = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.changeIconInfo",
		_param:param
	};
	gleasy.postMessage(obj);	
}

gleasy.window.addMessageDock = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.addMessageDock",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.removeMessageDock = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.removeMessageDock",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.showDragMask = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.showDragMask",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.setCloseHandler = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.setCloseHandler",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.exit = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.exit",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.showLoading = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.showLoading",
		_param:param
	};
	gleasy.postMessage(obj);	
}

gleasy.system.addMessage = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.addMessage",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.finishLoad = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.finishLoad",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.sendMessageToProcess = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.sendMessageToProcess",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.getCurrentUser = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.getCurrentUser",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.getUserSetting = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.getUserSetting",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.saveUserSetting = function(param){
	if(param == null) param = {};
	var obj = {
		_eventId:"c.saveUserSetting",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.system.openFileSaveBrowser = function(param){
	if(param == null) param = {};
	param.parent = gleasy.window._this;
	param.mode = "save";
	param.name = "未命名";
	param.type = "file";
	param.multi = false;
	var obj = {
		_eventId:"c.openFileSaveBrowser",
		_param:param
	};
	gleasy.postMessage(obj);	
}
gleasy.window.closeOnEscDisabled = true;
gleasy.window.disableCloseOnEsc = function(param){
	gleasy.window.closeOnEscDisabled=true;
}
gleasy.window.enableCloseOnEsc = function(param){
	gleasy.window.closeOnEscDisabled=false;
}
gleasy.window.clearOnMousedown = true;

$(function(){
	gleasy.system.finishLoad();
	$(document).bind("keydown",'esc',function(event){
		if(!gleasy.window.closeOnEscDisabled){
			gleasy.window.close();
		}
	});
	$(document).bind("keydown",'backspace',function(event){
		return false;
	});
	$(document).bind("mousedown",function(event){
		if(gleasy.window.clearOnMousedown ){
			gleasy.system.sendMessageToProcess({
				pname:"sys",
				eventId:"clearActiveLink",
				messageBody:"a"
			});
		}
	});
	$(document.body).bind("dragenter",function(evt){
		gleasy.window.showDragMask();
		evt.preventDefault();//阻止冒泡
	    evt.stopPropagation();
	}); 	
	gleasy.registerMessageHandler();
});
