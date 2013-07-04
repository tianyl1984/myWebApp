Namespace.register("com.gleasy.os.control");

/**
 * Gleasy OS 心跳
 * 
 */
com.gleasy.os.control.HeartBeat = function(){
	var _this = this;
		
	var name = "com.gleasy.os.control.HeartBeat";//名称
	var interests = [
		Constants.command.os.startHeartBeat,
		Constants.command.os.stopHeartBeat,
		Constants.command.os.reloadUserCard,
		Constants.command.os.loginSuccess,
		Constants.command.os.repeatLogin,
		"remindUpgrade",
		Constants.command.os.fireHeartBeat
	];
	var statusChanged = false;
	
	var checkStatusUrl = AjaxConfig.url.sso + "/beatheart/check";//心跳

	var interval = null;
	var lastCheck = -1;
	var inProgress = false;
	var needCheck = false;
	var errorCount = 0;
	var upgradeReminder = 0;
	var upgradeRemindTime = 0;
	var repairVerify = null;
	var overflowdialog = null;
	var updatedialog = null;
	var updatewarndialog = null;
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
        //setTimeout(checkStatus, 500);//由os触发心跳
		//checkStatus();
	}
	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		if(Constants.command.os.startHeartBeat == eventId){
			startHeartBeat();
		}else if(Constants.command.os.stopHeartBeat == eventId){
			stopHeartBeat();
		}else if(Constants.command.os.fireHeartBeat == eventId){
			checkStatus();
		}else if(Constants.command.os.reloadUserCard == eventId){
			reloadUserCard(messageBody);
		}else if(Constants.command.os.loginSuccess == eventId){
			if(com.gleasy.os.SystemInfo.login){
				startHeartBeat();
		
//				AjaxConfig.addFilter(function(){
//					checkStatus();
//				});
				reloadUserCard();
		        //setTimeout(reloadUserCard, 1000);//1秒后获取
			}
		}else if(Constants.command.os.repeatLogin == eventId){
			$.log("有人把你踢出来了..");
			if(messageBody && messageBody.msg){
				sendLoginNotify(messageBody.msg);
			}else{
				sendReloginNotify("您的帐号已在别处登录");
			}
		}else if("remindUpgrade" == eventId){
			remindUpgrade(messageBody);
		}
	}

	var reloadUserCard = function(_param){
		//var callback = _param && _param.callback;
	    $LAB
	    .script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/core/model/UserCard.js")
	    .wait(function(){
		   //获取当前登录用户信息
                var params = {
                    success:function(result){
                        var  userInfo = result.userInfo;
                        var uc = new com.gleasy.os.model.UserCard();
                        uc.name = userInfo.name;
                        uc.type = userInfo.userType;
                        uc.uid = userInfo.userId;
                        uc.bigAvatar = userInfo.avatarBig;
                        uc.smallAvatar = userInfo.avatarSmall;
                        Constants.data.user = uc;
                        window.application.sendNotify(Constants.command.os.reloadUserCardFinish,{});
                        _param && _param.callback && typeof _param.callback == 'function' && _param.callback(Constants.data.user );
                    },
                    error:function(e){
                        $.log(e);
                    }
                };

                window.application.sendNotify(Constants.command.os.process.sendMessage,{
                    pname:"联系人守护进程",
                    eventId:"getUserInfoByUserId",
                    messageBody:params
                });

	   });
	}
	
	var stopHeartBeat = function(){
		errorCount = 0;
		clearInterval(interval);
		interval = null;		
	}
	
	var startHeartBeat = function(){
		errorCount = 0;
		if(interval){
			return;
		}
		interval = setInterval(function(){
			checkStatus();
		},15000);
	}

	var checkStatus = function(){
		if(inProgress){
			needCheck = true;
			return;
		}
		needCheck = false;
		var tm = getCurrentTimeMills();
		if(tm - lastCheck < 2000){ 
			//检测太过频繁啦
			return;
		}
		inProgress = true;
		lastCheck = tm;
		AjaxConfig.ajax({
		   type: "GET",
		   url: checkStatusUrl+"?rand="+Math.random(),
		   ignoreFilter:true,
		   dataType:"json",
		   success: function(ret){
		   		errorCount = 0;
		   		inProgress = false;
		   		checkUserStatus(ret);
		   		if(needCheck){
		   			checkStatus();
		   		}
		   },
		   error:function(xhr){
		   	errorCount++;
		   	if(errorCount > 6){
		   		stopHeartBeat();
                sendLoginNotify("超时退出");
		   	}
		   	inProgress = false;
		   }
		});
		
	}
	
	var remindUpgrade = function(upgradeStatus){
		if(!upgradeStatus){
			if(overflowdialog != null){
				overflowdialog.close();overflowdialog=null;
			}
			if(updatedialog) {updatedialog.close();	updatedialog=null;upgradeReminder=-1;}
			if(updatewarndialog) {updatewarndialog.close();updatewarndialog=null;}
			return false;
		}
		if(repairVerify == true){
			return false;
		}else if(repairVerify==null){
			var p = new Poly9.URLParser(location.href);
			var v = p.getQuery("repairVerify");
			if(v == 'goodluck'){
				repairVerify = true;
				return false;
			}
		}
		
		var status = upgradeStatus.status;
		if(status == 1){
			if(overflowdialog != null) return true;
			setTimeout(function(){		
				window.application.sendNotify(Constants.command.system.confirm, {
					message:"抱歉，由于访问量过大，",
				    confirmMessage:"服务暂停，请过一会再试.", 
				    icon:"fail",//icon={confirm,fail,success,prompt,warning}
				    okBtn:false,
				    cancelBtn:false,
					system:true,
					noclose:true,
					initcallback:function(dt){
						overflowdialog = dt.dialog;
					}
				});
			},1000);
			return true;
		}
		var pass = upgradeStatus.pass;
		if(pass >= -2*60 && pass < -15 ){
			status = 1;
		}else if(pass >= -15 && pass <= -2){
			status = 2;
		}else if(pass > -2){
			status = 3;
		}
		
		var end = upgradeStatus.end;
		var start = upgradeStatus.start;
		
		if(status == 3 && upgradeReminder==status){
			return true;
		}
		if(!com.gleasy.os.SystemInfo.login && (status == 1 || status == 2) ){
			return false;
		}
		if(upgradeReminder >= status){
			return false;
		}
		upgradeReminder = status;	

		if(status == 1 || status == 2){
			var now = getCurrentTimeMills();
			if((now - upgradeRemindTime) < 1000*60){
				return false;
			}
			upgradeRemindTime = now;
			var startDate = new Date(start);
			var endDate = new Date(end);
			var diff = (startDate.getDay() != endDate.getDay());
			var timestr = startDate.format("n月j日G:i");
			timestr += "-";
			if(diff){
				timestr += endDate.format("n月j日G:i");		
			}else{
				timestr += endDate.format("G:i");		
			}	
			if(updatewarndialog) {updatewarndialog.close();updatewarndialog=null;}
			window.application.sendNotify(Constants.command.system.confirm, {
				message:"您好，系统将在"+timestr+"升级，",
			    confirmMessage:"届时系统将停止使用，敬请原谅。", 
			    icon:"prompt",//icon={confirm,fail,success,prompt,warning}
			    okBtn:true,
			    okBtnText:"知道了",
			    cancelBtn:false,
			    system:true,
			    initcallback:function(dt){
					updatewarndialog = dt.dialog;
				}
			});
			return false;
		}else if(status == 3){
			if(!com.gleasy.os.SystemInfo.login){
				window.application.sendNotify( Constants.command.os.loadDefaultWallpaper,{});
			}
			if(updatewarndialog) {updatewarndialog.close();updatewarndialog=null;}
			setTimeout(function(){
				var endDate = new Date(end);
				var timestr = endDate.format("n月j日G:i");		
				window.application.sendNotify(Constants.command.system.confirm, {
					message:"抱歉，系统升级中，",
				    confirmMessage:"预计在"+timestr+"后重新开放", 
				    icon:"fail",//icon={confirm,fail,success,prompt,warning}
				    okBtn:false,
				    cancelBtn:false,
					system:true,
					noclose:true,
			    	initcallback:function(dt){
						updatedialog = dt.dialog;
					}
				});
			},1000);
			
			return true;
		}
	}
	
	var checkUserStatus = function(ret){
		if(!ret){
			sendLoginNotify("Sorry服务异常,我们正在处理");
			return;
		}
		if(ret.data && ret.data.upgradeStatus){
			var result = remindUpgrade(ret.data.upgradeStatus);
			if(result) return;
		}else{
			var result = remindUpgrade();
			if(result) return;
		}
		switch (ret.status){
   			case 0:
   			if(ret.userStatus && ret.userStatus.code == 0 && !com.gleasy.os.SystemInfo.login){
   				com.gleasy.os.SystemInfo.login = true;
   				window.application.sendNotify(Constants.command.os.loginSuccess,{});
   			}
                   break;
   			case 1:
                   sendLoginNotify("超时退出");
   			return;
   			case 2:
                sendReloginNotify("您的帐号已在别处登录");
   			return;
   			case 3:
                   sendLoginNotify("您已登出");
   			return;
   			default:
                   sendLoginNotify("您已登出");
   			return;
   		}
   		if(ret.userStatus){
	 		switch (ret.userStatus.code){
	   			case 0:
	   			break;
	   			case 1:
	   				sendAccountInvalidNotify("您的帐号已经到期。<br/>我们将为您保存30天数据，<br/>请尽快联系管理员。");
	   			break;
	   			case 2:
	   				sendAccountInvalidNotify("您的帐号已经被暂停使用，<br/>请联系贵公司管理员。");
	   			break;
	   			case 3:
	   				sendAccountInvalidNotify("您的帐号已经被删除");
	   			break;
	   			default:
	   				requireStaffInfo();
	   			break;
	   		}  		
   		}
	}
	
	var sendAccountInvalidNotify = function(msg){
		com.gleasy.os.SystemInfo.login = false;
		_this.sendNotify(Constants.command.systemLogout,{});
		_this.sendNotify(Constants.command.os.requireLogin,{msg: msg, panel:'accountInvalid'});
		stopHeartBeat();
	}

	var sendLoginNotify = function(msg){
		com.gleasy.os.SystemInfo.login = false;
		_this.sendNotify(Constants.command.systemLogout,{});
		_this.sendNotify(Constants.command.os.requireLogin,{ panel:'login'});
		stopHeartBeat();
	}

	var sendReloginNotify = function(msg){
		com.gleasy.os.SystemInfo.login = false;
		_this.sendNotify(Constants.command.systemLogout,{});
		_this.sendNotify(Constants.command.os.requireLogin,{msg: msg, panel:'relogin'});
		stopHeartBeat();
	}

	var requireStaffInfo = function(msg){
		_this.sendNotify(Constants.command.os.requireLogin,{msg: msg,panel:'staffInfo'});
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
com.gleasy.os.control.HeartBeat.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本View
window.application.addMvc(new com.gleasy.os.control.HeartBeat());
