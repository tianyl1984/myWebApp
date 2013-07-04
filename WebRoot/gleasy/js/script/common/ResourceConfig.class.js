ResourceConfig = {};
ResourceConfig.version = "2012111209";
ResourceConfig.common = {};
ResourceConfig.common.CodeConfig = "/script/common/CodeConfig.class.js";
ResourceConfig.common.FileUploaderFlash = "/script/common/FileUploaderFlash.class.js?v=0.1";
ResourceConfig.common.FileUploaderHtml5 = "/script/common/FileUploaderHtml5.class.js";
ResourceConfig.common.SwfCaller = "/script/common/SwfCaller.class.js";

//
//var oldscript = $LAB.script;
//var oldwait = $LAB.wait;
//$LAB.script = function(url){
//	if(!url) return $LAB;
//	var idx = url.indexOf("?");
//	if(idx > -1){
//		url = url.substring(0,idx);
//	}
//	url = url + "?v="+ResourceConfig.version;
//	oldscript(url);
//	return $LAB;
//}
//$LAB.wait = function(p){
//	oldwait(p);
//	return $LAB;
//}
