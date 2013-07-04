/**
 * OS专用Ajax跨域调用
*/

AjaxCallerFlash = function(){
	var _this = this;
	
	var cache = {};
	var _construct = function(){
		initComponent();
		configEvent();
	}
	var initComponent = function(){
	}
	
	this.call = function(param){
		var upid = UuidGenerator.uuid("ga");
		cache[upid] = {
			dataType:param.dataType,
			success:param.success,
			error:param.error
		}
		
		var url = param.url;
		var p = new Poly9.URLParser(url); 
		var crossdomain = p.getProtocol()+"://"+p.getHost();
		if(p.getPort()){
			crossdomain += ":"+p.getPort();
		}
		crossdomain += "/crossdomain.xml?rand="+Math.random();
		
		SwfCaller.callFlash("ajaxCaller",function(){
			if(param.type == 'POST' || param.type == 'post' ){
	   			SwfCaller.getFlash("ajaxCaller").sendNotify(Constants.command.gajax.post,{
	   				url:param.url,
	   				data:param.data,
	   				upid:upid,
	   				crossdomain:crossdomain,
	   				dataType:param.dataType
	   			});
			}else{
				SwfCaller.getFlash("ajaxCaller").sendNotify(Constants.command.gajax.get,{
	   				url:param.url,
	   				data:param.data,
	   				upid:upid,
	   				crossdomain:crossdomain,
	   				dataType:param.dataType
	   			});
			}
   		});
	}

	this.sendNotify = function(eventId,message){
		log("AjaxCaller:"+eventId+";"+message);
		if(Constants.command.gajax.success == eventId){
			success(message);
		}else if(Constants.command.gajax.error == eventId){
			error(message);
		}else if(Constants.command.gajax.log == eventId){
			log(message);
		}
	}
	
	var log = function(param){
		$.log(param);
	}
	
	var success = function(param){
		var upid = param.upid;
		if(!upid) return;
		var c = cache[upid];
		if(!c) return;
		var result = param.response;
		if(c.dataType && c.dataType == 'json' && typeof result == 'string'){
			try{
				result = $.evalJSON(result);
			}catch(e){
				$.log(e);
			}
		}
		$.log(result);
		typeof c.success == 'function' && c.success(result);
	}
	
	var error = function(param){
		var upid = param.upid;
		if(!upid) return;
		var c = cache[upid];
		if(!c) return;
		typeof c.error == 'function' && c.error(param.error);		
	}
	
	
	var configEvent = function(){
		var componentId = "ajaxCaller";
		SwfCaller.registerCallbackObj(componentId,_this);	
		
		imCoreSwf = $("<div/>");
		imCoreSwf.css({
			position:'absolute',
			left:-100,
			top:-5
		});
		imCoreSwf.flash({
			swf: Constants.config.assetPrefix+'/script/common/gajax.swf?rand='+Math.random(),
			width: 1,
			height: 1,
			id: "ajaxCaller",   
			name: "ajaxCaller",
			allowScriptAccess:"always",
			flashvars: {
				id: "ajaxCaller",
				componentId:componentId
			}
		});
		imCoreSwf.appendTo($(document.body));
	}
	_construct();
}
AjaxCallerFlash.instance = null;
AjaxCallerFlash.ajax = function(param){
   $LAB
   .script(Constants.config.assetPrefix+"/script/common/SwfCaller.class.js")
   .script(Constants.config.assetPrefix+"/script/jquery/jquery.swfobject.1-1-1.min.js")
   .wait(function(){
	if(AjaxCallerFlash.instance == null){
		AjaxCallerFlash.instance = new AjaxCallerFlash();
	}
	AjaxCallerFlash.instance.call(param);
   });
}
