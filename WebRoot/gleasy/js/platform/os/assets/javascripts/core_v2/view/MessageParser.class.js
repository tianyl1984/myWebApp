Namespace.register("com.gleasy.os.view");

/**
 * 消息解析器
 */
com.gleasy.os.view.MessageParser = {};
com.gleasy.os.view.MessageParser.templates = {};
com.gleasy.os.view.MessageParser.templates['default']={
	resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/core_v2/view/MessageTemplateDefault.class.js",
	exe:"com.gleasy.os.view.MessageTemplateDefault"
};
com.gleasy.os.view.MessageParser.templates['empty']={
	resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/core_v2/view/MessageTemplateEmpty.class.js",
	exe:"com.gleasy.os.view.MessageTemplateEmpty"
};
com.gleasy.os.view.MessageParser.init = function(){
	$.each(com.gleasy.os.view.MessageParser.templates,function(k,config){
		$LAB
		.script(config.resource)
		.wait(function(){
			if(typeof config.exe == 'string' ){
				eval('config.obj = new '+config.exe+'()');
			}
		});
	});	
}
com.gleasy.os.view.MessageParser.init();

com.gleasy.os.view.MessageParser.parse = function(domContainer,message,callback,showDetail){
	var template = message.template;
	if(!template) return null;
	var config = com.gleasy.os.view.MessageParser.templates[template];
	if(!config){
		$.log("找不到消息模板"+template);
		return null;
	}
	if(config.obj && typeof config.obj.parse == 'function' ){
		var result = config.obj.parse($(".template_"+template,domContainer),message,showDetail);
		callback && typeof callback == 'function' && callback(result);
		return;
	}
	$LAB
	.script(config.resource)
	.wait(function(){
		if(typeof config.exe == 'string' ){
			eval('config.obj = new '+config.exe+'()');
		}
		if(config.obj && typeof config.obj.parse == 'function' ){
			var result = config.obj.parse($(".template_"+template,domContainer),message,showDetail);
			callback && typeof callback == 'function' && callback(result);
			return;
		}
	});
}
