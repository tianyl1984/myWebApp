Namespace.register("com.gleasy.os.view");

/**
 * 进程管理器
 * 
 */
com.gleasy.os.view.ProcessManager = function(){
	var _this = this;
		
	var name = "com.gleasy.os.view.ProcessManager";//名称
	var interests = [Constants.command.os.process.create,
					Constants.command.os.process.kill,
					Constants.command.os.process.runProgram,
					Constants.command.os.process.list,
					Constants.command.os.process.addFrame,
					Constants.command.os.process.sendMessage,
					Constants.command.os.frame.close];
	
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
	}
	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
//		if(eventId != Constants.command.os.process.list){
//			$.log(this.getName()+" 收到消息 "+eventId);
//		}
		if(eventId == Constants.command.os.process.create){
			createProcess(messageBody);
		}else if(Constants.command.os.process.addFrame == eventId){
			addFrame(messageBody);
		}else if(Constants.command.os.frame.close == eventId){
			removeFrame(messageBody);
		}else if(Constants.command.os.process.list == eventId){
			listProcess(messageBody);
		}else if(Constants.command.os.process.kill == eventId){
			killProcess(messageBody);
		}else if(Constants.command.os.process.sendMessage == eventId){
			sendMessageToProcess(messageBody);
		}else if(Constants.command.os.process.runProgram == eventId){
			runProgram(messageBody);
		}
	}
	
	var killProcess = function(param){
		var pid = param.pid;
		var p = findProcessByPid(pid);
		if(p == null) return;
		var index = $.inArray(p,com.gleasy.os.view.ProcessManager.processList);
		com.gleasy.os.view.ProcessManager.processList.splice(index,1);
		p.kill();		
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
		
		var param = {};
		if(!supprtV){
			param.message = "您使用的浏览器不支持本应用，建议安装并使用Gleasy客户端.";
			param.okBtn = true;
			param.okBtnText = "下载Gleasy客户端";
			param.cancelBtn = true;
        	param.cancelBtnText = "不用了,谢谢";
        	param.icon = "prompt";//icon={confirm,fail,success,prompt,warning}
        	param.callback = function(obj){
        		if(obj.result){
        			window.application.sendNotify(Constants.command.os.openUrl,Constants.config.clientPrefix+"/platform/softwares/client/download.html");
        		}
        	}
		}else{
			if(supprtV == 'all'){
				return true;
			}else if(supprtV.indexOf(version) > -1){
				return true;
			}else{
				param.message = "您使用的浏览器版本太低，请升级至最新版后再使用本应用.";
			}
		}
		window.application.sendNotify(Constants.command.system.alert,param);
		return false;
	}
	var runProgram = function(param){
		var pname = param.pname,callback = param.callback,_args=param.args, sysonly=param.sysonly;
		if(sysonly){
			var config = com.gleasy.os.reg.programDef[pname];
			config.pname = pname;
			_runProgram(config);
		}else{
			com.gleasy.os.reg.getInstalledApps({
				args:pname,
				callback:function(programDef,_pname){
					var config = programDef[_pname];
					if(!config) return;
					if(!checkBrowserSupport(config)){
						return;
					}
					config.pname = _pname;
					var type = config.type;
					if(type == 'app'){
						if(!config.args || !config.args.url || config.args.status != 1){
							var param = {};
							param.message = "该应用已经下架。";
							param.okBtn = true;
							param.okBtnText = "我知道了";
							param.cancelBtn = false;
				        	param.icon = "prompt";//icon={confirm,fail,success,prompt,warning}
							window.application.sendNotify(Constants.command.system.alert,param);
							return;
						}
						_args = config.args;
					}
					_runProgram(config,callback,_args);
				}
			});
		}
	}
	
	
	var _runProgram = function(config,callback,args){
		$.log(config);
		if(!config) return;
		$LAB
		.script(config.resource)
		.wait(function(){
			var singleton = config.singleton;
			var pname = config.pname;
			var process =  findProcessByPname(pname);
			if(singleton && process!=null){
				$.log("不允许同时开多个"+pname+"进程哦。。");
				process.showMainFrame();
				return;
			}
			if(args){
				args.pid = null;
				args.parent = null;
			}else{
				args = {};
			}
			args.version = config.version;
			var process = new com.gleasy.os.view.Process(config,args);
			com.gleasy.os.view.ProcessManager.processList.push(process);
			var result = process.run();
			if(result && result > 0){
				killProcess({pid:process.getPid()});
				delete process;
				return;
			}
			
			typeof callback == 'function' && callback({pid:process.getPid(),process:process});	
			if(config.app == pname){
				window.application.sendNotify(Constants.command.os.updateAppDeskMessage,{app:config.app,num:0});
			}
		});
	}
	
	var sendMessageToProcess = function(param){
		var pname = param.pname;
		if(pname == 'sys'){
			window.application.sendNotify(param.eventId,param.messageBody);
			return;
		}
		if(!param.noneedlogin && !com.gleasy.os.SystemInfo.login){
			return;
		}
		
		var autoOpen = true;
		var autoActive = false;
		if(typeof param.autoOpen != 'undefined'){
			autoOpen = param.autoOpen;
		}
		if(typeof param.autoActive != 'undefined'){
			autoActive = param.autoActive;
		}
		
		//var autoOpen = param.autoOpen?param.autoOpen:true;
		var p = findProcessByPname(pname);
		if(p == null) {
			if(autoOpen){
				runProgram({pname:pname,args:{eventId:param.eventId,messageBody:param.messageBody},callback:function(res){
					res.process.notify(param.eventId,param.messageBody);
				}});
			}
		}else{
			if(p.isSingleton()){
				//如果是单例进程，发送进程通知
				p.notify(param.eventId,param.messageBody);
				if(autoActive){
					var f = p.getMainFrame();
					if(f){
						var wid = f.getWid();
						window.application.sendNotify(Constants.command.os.frame.setVisible,{wid:wid,visible:true});
					}
				}
			}else{
				runProgram({pname:pname,args:{eventId:param.eventId,messageBody:param.messageBody},callback:function(res){
					res.process.notify(param.eventId,param.messageBody);
				}});
			}
		}
	}
	
	var listProcess = function(param){
		var callback = param.callback;
		typeof callback == 'function' && callback(com.gleasy.os.view.ProcessManager.processList);
	}
	
	var removeFrame = function(param){
		var pid = param.pid;
		var p = findProcessByPid(pid);
		
		if(p == null) return;
		
		var wid = param.wid;
		
		p.removeFrame(wid);
	}
	
	var addFrame = function(param){
		var pid = param.pid;
		$.log("find p:pid:"+pid);
		var p = findProcessByPid(pid);
		if(p == null) return;
		$.log("find p");
		var frame = param.frame;
		$.log("find p : wid : "+frame.getWid());
		p.addFrame(frame);
	}
	
	
	var findProcessByPid = function(pid){
		for(var i=0;i<com.gleasy.os.view.ProcessManager.processList.length;i++){
			var p = com.gleasy.os.view.ProcessManager.processList[i];
			if(p.getPid() == pid){
				return p;
			}
		}	
		return null;
	}
	
	/**
	 * 创建进程
	 */
	var createProcess = function(obj){
		var param = obj.param;
		var args = obj.args;
		var callback = obj.callback;
		var singleton = param.singleton;
		var pname = param.pname;
		var process =  findProcessByPname(pname);
		if(singleton &&process!=null){
			$.log("不允许同时开多个"+param.pname+"进程哦。。");
			process.showMainFrame();
			return;
		}
		
		var process = new com.gleasy.os.view.Process(param,args);
		com.gleasy.os.view.ProcessManager.processList.push(process);	
		process.run();
		typeof callback == 'function' && callback({pid:process.getPid(),process:process});
	}
	
	var findProcessByPname = function(pname){
		for(var i=0;i<com.gleasy.os.view.ProcessManager.processList.length;i++){
			var p = com.gleasy.os.view.ProcessManager.processList[i];
			if(p.getPname() == pname){
				return p;
			}
		}
		return null;			
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
com.gleasy.os.view.ProcessManager.prototype = new com.gleasy.libs.MvcBase();
com.gleasy.os.view.ProcessManager.processList = [];

//向application中注册本View
window.application.addView(new com.gleasy.os.view.ProcessManager());
