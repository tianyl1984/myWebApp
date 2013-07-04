/**
 * OS专用Ajax跨域调用
*/

if(typeof FileUploaderFlash == 'undefined'){
AjaxCallerHtml5 = function(){
	var _this = this;
	var cache = {};
	var frameLoadCache = {};
	var frameCallStack = {};
	
	var _construct = function(){
	}
	
	var initComponent = function(){
	}
	
	var configEvent = function(){
	}
	
		
	var getDomainAndPort = function(url){
		try{
			var p = new Poly9.URLParser(url);
			var domain = p.getHost();
			var port = p.getPort();
			if(!port) port = "80";			
			return {
				protocol:p.getProtocol(),
				host:p.getHost(),
				port:p.getPort(),
				domain:p.getHost()+"_"+p.getPort()
			};	
		}catch(e){
			$.log(e);
			return null;
		}
	}
	
	var getProxyFrame = function(infos,crossdomainfilepath){
		try{
			var domain = infos.domain;
			var f = window.frames[domain];
			if(!f){
				var proxy = infos.protocol+"://"+infos.host;
				if(infos.port){
					proxy += ":"+infos.port;
				}
				if(crossdomainfilepath){
					proxy += crossdomainfilepath;	
				}else{
					proxy += "/crossdomain.html?random="+Math.random();		
				}
				$.log("proxy="+proxy);
				var frame = $("<iframe name='"+domain+"' id='"+domain+"' src='"+proxy+"' ></iframe>");
				frame.hide();
				frame.appendTo($(document.body));
				frame.bind("load",function(){
					setTimeout(function(){
						if(frameLoadCache[domain] != 1){
							$.log("找不到跨域代理文件:"+proxy);
						}
					},1000);
				});
				f = window.frames[domain];
			}
			return f;
		}catch(e){
			$.log(e);
			return null;
		}
	}

	this.call = function(param){
		var url = param.url;
		var infos = getDomainAndPort(url);
		if(infos == null){
			typeof c.error == 'function' && c.error("URL非法");	
			return;
		}
		var win = getProxyFrame(infos,param.crossdomainfilepath);
		
		var domain = infos.domain;
		if(frameLoadCache[domain] == 1){
			var upid = UuidGenerator.uuid("ga");
			cache[upid] = {
				dataType:param.dataType,
				success:param.success,
				error:param.error
			}		
			param.upid = upid;
			
			win.postMessage($.toJSON({
				eventId:'gajax.post',
				messageBody:{
					url:param.url,
	   				data:param.data,
	   				type:param.type,
	   				upid:upid,
	   				contentType:param.contentType,
	   				dataType:param.dataType
	   			}
			}),"*");
		}else{
			var calls = frameCallStack[domain];
			if(!calls){
				calls = frameCallStack[domain] = [];
			}
			calls.push(param);
		}
	}

	this.notify = function(eventId,message){
		if('gajax.success' == eventId){
			success(message);
		}else if('gajax.error' == eventId){
			error(message);
		}else if('gajax.ready' == eventId){
			frameReady(message);
		}
	}
		
	var frameReady = function(param){
		var domain = param.domain;
		frameLoadCache[domain] = 1;
		var calls = frameCallStack[domain];
		if(calls){
			$.each(calls,function(index,param){
				_this.call(param);
			});
		}
	}
	
	var success = function(param){
		var upid = param.upid;
		if(!upid) return;
		var c = cache[upid];
		if(!c) return;
		var result = param.response;
		typeof c.success == 'function' && c.success(result);
	}
	
	var error = function(param){
		var upid = param.upid;
		if(!upid) return;
		var c = cache[upid];
		if(!c) return;
		typeof c.error == 'function' && c.error(param.error);		
	}
	_construct();
}

AjaxCallerHtml5.instance = new AjaxCallerHtml5();

AjaxCallerHtml5.registerMessageHandler = function(){
	if (window.addEventListener) {
		window.addEventListener("message", AjaxCallerHtml5.messageHandler, false);
	}else {
		window.attachEvent("onmessage", AjaxCallerHtml5.messageHandler);
	}
}

AjaxCallerHtml5.messageHandler = function(evt){
	var data = evt.data;
	if(!data) return;
	if(typeof data == 'string'){
		data = $.evalJSON(data);
	}
	if(!data) return;
	var eventId = data.eventId;
	var messageBody = data.messageBody;
	if(eventId){
		AjaxCallerHtml5.instance.notify(eventId,messageBody);
	}
}

AjaxCallerHtml5.ajax = function(param){
	AjaxCallerHtml5.instance.call(param);
}

AjaxCallerHtml5.registerMessageHandler();
}
