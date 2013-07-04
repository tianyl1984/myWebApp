/**
 * 开机,OS初始化
*/
$(function(){
	/*
   $LAB.setGlobalDefaults({
   	CacheBust:true
   });*/

 
	window.application.sendNotify(Constants.command.os.showBootLoading,{});   
});
