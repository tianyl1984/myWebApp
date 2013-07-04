
Namespace.register("com.gleasy.soft.gnotepad");

com.gleasy.soft.gnotepad.GNotepad = function(param){
	com.gleasy.os.view.Frame.call(this,param);//继承Frame
	
	var _this = this;
	var view = null;
	var process = null;
	
	var options = {
		url:Constants.config.assetPrefix+"/platform/os/assets/javascripts/softwares/gnotepad/index.html?rand="+Math.random()
	};
	
	var findProcessByPid = function(pid){
		for(var i=0;i<com.gleasy.os.view.ProcessManager.processList.length;i++){
			var p = com.gleasy.os.view.ProcessManager.processList[i];
			if(p.getPid() == pid){
				return p;
			}
		}	
		return null;
	}
	
	var _construct = function(){
		process = findProcessByPid(_this.getPid());
		//process.getContext().fid = xx;
		
		
		$.extend(options,param);
		initComponent();
		_this.setMinSize(456,130);
		_this.center();
	}
	
	var initComponent = function(){
		_this.setUrl(options.url);
		configEvent();
	}
	this.notify = function(eventId,messageBody){
		if(eventId == 'openFile'){
			for(var i=0;i<com.gleasy.os.view.ProcessManager.processList.length;i++){
				var p = com.gleasy.os.view.ProcessManager.processList[i];
				if(p && p.getPname() == "记事本"){
					if(p.getContext().fid 
							&& p.getContext().fid == messageBody[0].id){
						p.getMainFrame().noticeListener("Constants.command.notepad.reCheckLock",{fid:p.getContext().fid});
						window.application.sendNotify(Constants.command.os.frame.setVisible,{wid:p.getMainFrame().getWid(),visible:true});
						_this.exit();
					}
					
				}
			}
			
			if(process){
				process.getContext().fid = messageBody[0].id;
			}
			_this.setFilesReceived(messageBody);
			_this.tryFocus();
		}
	}
	
	var configEvent = function(){		
		_this.addEventListener('Constants.command.notepad.saveNew', function(param){
			if(process){
				process.getContext().fid = param.fid;
			}
		});
		_this.addEventListener('autoSetTitle', function(paramt){
			_this.options.title = paramt.title;
	        _this.changeDockInfo({
	            title:paramt.title
	        });
	        $(".oWindowTitle", _this.view).html(_this.options.title);
		});
		_this.addEventListener('checkExistAndOpen', function(param){
			if(param.fid){
				var exists = false;
				for(var i=0;i<com.gleasy.os.view.ProcessManager.processList.length;i++){
					var p = com.gleasy.os.view.ProcessManager.processList[i];
					if(p && p.getPname() == "记事本"){
						if(p.getContext().fid 
								&& p.getContext().fid == param.fid){
							p.getMainFrame().noticeListener("Constants.command.notepad.reCheckLock",{fid:p.getContext().fid});
							window.application.sendNotify(Constants.command.os.frame.setVisible,{wid:p.getMainFrame().getWid(),visible:true});
							//_this.exit();
							exists = true;
							break;
						}
						
					}
				}
				if(!exists && param.callback){
					param.callback();
				}
			}
		});
	}
	
	_construct();
}

/**
 * 执行入口
 * 有main方法的类才可以被OS执行
 */
com.gleasy.soft.gnotepad.GNotepad.main = function(param){
	
	$.extend(param,{
		title:"记事本",
		clazz:com.gleasy.soft.gnotepad.GNotepad,
		hasDock:1,
		size:{
			width:600,
			height:400
		},
		callback:function(res){
			var wid = res.wid;
			window.application.sendNotify(Constants.command.os.frame.setVisible,{wid:wid,visible:true});
		}
	});
	window.application.sendNotify(Constants.command.os.frame.create,param);	
}
