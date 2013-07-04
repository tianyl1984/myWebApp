/**
 * 进程类
*/
Namespace.register("com.gleasy.os.view");

com.gleasy.os.view.Process = function(param,args){
	var _this = this;
	var name = "Process";//Model的名称
	
	var pid = null;//进程ID
	
	var frames = {};
	
	var mainFrame = null;
	
	var context = {};//进程上下文环境
	
	var jobQueue = [];
	
	var options = {
		pname:"",//进程名称(唯一标识)
		singleton:false,//是否只能唯一(即进程是否只能存在一份)
		exe:null//可执行路径
	};
	
	var _construct = function(){	
		$.extend(options,param);
		if(typeof options.exe == 'string' ){
			eval('options.exe = '+options.exe);
		}
		pid = UuidGenerator.uuid("p");
		name = "Process:"+pid;
		if(args == null){
			args = {};
		}
		$.extend(args,{pid:pid});		
	}
	
	this.getApp = function(){
		return  options.app?options.app:options.pname;
	}
	
	this.isSingleton = function(){
		return options.singleton;
	}
	
	this.getPid = function(){
		return pid;	
	}
	
	this.getPname = function(){
		return options.pname;
	}
	
	this.getFrames = function(){
		return 	frames;
	}
	
	this.getContext = function(){
		return context;	
	}
	
	this.getExe = function(){
		return options.exe;	
	}
	
	this.run = function(){
		if(options.exe != null && (typeof options.exe == 'function' || typeof options.exe == 'object')){
			if(!args){
				args = {};
			}
			args.app = options.app?options.app:options.pname;
			args.pid = pid;
			var result = options.exe.main(args,pid);
			return result;
		}
	}
	
	this.getMainFrame = function(){
		return mainFrame;
	}
	
	this.showMainFrame = function(){
		if(mainFrame != null){
			window.application.sendNotify(Constants.command.os.frame.setVisible,{wid:mainFrame.getWid(),visible:true});		
		}
	}
	
	this.addFrame = function(frame){
		if(mainFrame == null || frame.isMainFrame()){
			if(mainFrame == null && jobQueue.length){
				mainFrame = frame;
				setTimeout(function(){
					var len = jobQueue.length;
					for(var i=0;i<len;i++){
						_this.notify(jobQueue[i].eventId,jobQueue[i].messageBody);
					}
					jobQueue = [];
				},400);
			}else{
				mainFrame = frame;
			}
		}
		
		var wid = frame.getWid();
		frames[wid] = frame;
	}
	
	this.removeFrame = function(wid){
		if(mainFrame!=null && mainFrame.getWid() == wid){
			window.application.sendNotify(Constants.command.os.process.kill,{pid:pid});
		}else{
			delete frames[wid];
		}
	}

	var hasOtherAppProcess = function(){
		var app = _this.getApp();
		for(var i=0;i<com.gleasy.os.view.ProcessManager.processList.length;i++){
			var p = com.gleasy.os.view.ProcessManager.processList[i];
			if(p.getApp() == app && p.getPid() != _this.getPid()){
				return true;
			}
		}
		return false;			
	}
	
	/**
	 * 结束进程
	 */
	this.kill = function(){	
		var hasother = hasOtherAppProcess();
		$.each(frames,function(index,frame){
			(!hasother) && frame && frame.noticeListener("logout","");
			frame&&frame.kill();
		});
		window.application.sendNotify(Constants.command.os.process.dead,{pid:pid,pname:options.pname});	
		if(options.exe != null && typeof options.exe == 'function' && typeof options.exe.exit == 'function' ){
			options.exe.exit({
				pid:pid
			});
		}
		delete _this;
	}

	
	/**
	 * 返回对象唯一标识字符串
	 * 
	 */
	this.getName = function(){	
		return name;
	}
	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		if(!_this.isSingleton() && mainFrame == null){
			//alert("进程"+_this.getName()+"没有窗口，不能接受事件通知");
			jobQueue.push({
					eventId:eventId,
					messageBody:messageBody
			});
			return;
		}
		
		if(mainFrame && typeof mainFrame.notify == 'function'){
			$.log(_this.getName()+"收到消息"+eventId);
			typeof mainFrame.notify == 'function' && mainFrame.notify(eventId,messageBody);
		}else{
			if(options.exe != null && typeof options.exe == 'function' && typeof options.exe.notify == 'function' ){
				options.exe.notify({
					pid:pid,
					eventId:eventId,
					messageBody:messageBody
				});
			}else{
				jobQueue.push({
					eventId:eventId,
					messageBody:messageBody
				});
			}	
		}
	}
	
	_construct();
}

