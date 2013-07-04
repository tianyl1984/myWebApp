/**
 * 剪切板
*/
Namespace.register("com.gleasy.os");

com.gleasy.os.Clipboard = {};
com.gleasy.os.Clipboard.data  = {};
com.gleasy.os.Clipboard.dragData  = null;

com.gleasy.os.Clipboard.set = function(type,content){
	com.gleasy.os.Clipboard.data.type = type;
	com.gleasy.os.Clipboard.data.content = content;
}

com.gleasy.os.Clipboard.get = function(){
	return com.gleasy.os.Clipboard.data;
}


com.gleasy.os.Clipboard.getLocal = function(){
	if(!typeof FileTool == 'object' && !typeof FileTool == 'function'){
		return;
	}
	var contents = FileTool.getCliboard();
	$.log(contents);
	return;
}
