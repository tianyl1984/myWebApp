Namespace.register("com.gleasy.os.view");

/**
 *桌面提示
 * 
 * 
 */
com.gleasy.os.view.TipView = function(){
	var _this = this;	
	var name = "com.gleasy.os.view.TipView";//名称
	var interests = [
		Constants.command.loadDesktopOK
	];//监听的消息IDs
	
	
	
	
	/**
	 * 模拟构造函数
	 */
	var _construct = function(){
		_this.setInterests(interests);
	}
	
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
	}
	
	var showInfoTip = function(){
		var settingkey = "os.help.tip.info";
		window.application.sendNotify(Constants.command.os.setting.load, {
			key:settingkey,
			callback:function(settings){
				var v = settings[settingkey];
				if(!v){
					setTimeout(function(){
						renderInfoTip();
					},3000);
				}
			}
		});	
	}
	
	var renderInfoTip = function(){
		var desktop = $(".desktop");
		var infotip = $("#util_div .msgtiptop").clone();
		infotip.appendTo(desktop);
		infotip.css("zIndex",Constants.config.os.layer.windowNormal - 1);
		infotip.css({
			marginLeft:-135,
			top:51
		});
		$(".content",infotip).html("补充信息，让更多人找到您 :)");
		$(".closeBtn",infotip).bind("click",function(){
			infotip.hide();
			window.application.sendNotify(Constants.command.os.setting.save, {key:"os.help.tip.info",value:"1"});
		});
		infotip.show();
	}

	var showRclickTip = function(){
		var settingkey = "os.help.tip.rclick";
		window.application.sendNotify(Constants.command.os.setting.load, {
			key:settingkey,
			callback:function(settings){
				var v = settings[settingkey];
				if(!v){
					setTimeout(function(){
						renderRclickTip();
					},3000);
				}
			}
		});	
	}
	
	var renderRclickTip = function(){
		var desktop = $(".desktop");
		var infotip = $("#util_div .msgtipbottom").clone();
		infotip.appendTo(desktop);
		infotip.css("zIndex",Constants.config.os.layer.windowNormal - 1);
		infotip.css({
			marginLeft:-300,
			top:"50%"
		});
		$(".content",infotip).html("试试鼠标右键?");
		$(".closeBtn",infotip).bind("click",function(){
			infotip.hide();
			window.application.sendNotify(Constants.command.os.setting.save, {key:"os.help.tip.rclick",value:"1"});
		});
		infotip.show();
	}

	var showShowhideTip = function(){
		var settingkey = "os.help.tip.showhide";
		window.application.sendNotify(Constants.command.os.setting.load, {
			key:settingkey,
			callback:function(settings){
				var v = settings[settingkey];
				if(!v){
					setTimeout(function(){
						renderShowhideTip();
					},3000);
				}
			}
		});	
	}
	
	var renderShowhideTip = function(){
		var desktop = $(".desktop");
		var infotip = $("#util_div .msgtipbottom").clone();
		infotip.appendTo(desktop);
		infotip.css("zIndex",Constants.config.os.layer.bottomBar + 1);
		infotip.css({
			marginLeft:-350,
			top:"auto",
			bottom:20
		});
		$(".content",infotip).html("点击这里显示桌面");
		$(".closeBtn",infotip).bind("click",function(){
			infotip.hide();
			window.application.sendNotify(Constants.command.os.setting.save, {key:"os.help.tip.showhide",value:"1"});
		});
		infotip.show();
	}

	var showControlPanelTip = function(){
		setTimeout(function(){
			if(!Constants.data.user) {
				showControlPanelTip();
				return;
			}
			if(Constants.data.user.type != 2) return;
			var settingkey = "os.help.tip.controlpanel";
			window.application.sendNotify(Constants.command.os.setting.load, {
				key:settingkey,
				callback:function(settings){
					var v = settings[settingkey];
					if(!v){
						renderControlPanelTip();
					}
				}
			});	
		},3000);
	}
	
	var renderControlPanelTip = function(){
		var desktop = $(".desktop");
		var infotip = $("#util_div .msgtipbottom").clone();
		infotip.appendTo(desktop);
		infotip.css("zIndex",Constants.config.os.layer.bottomBar + 1);
		infotip.css({
			left:"auto",
			top:"auto"
		});
		$(".content",infotip).html('去"企业面板"添加您的员工吧 :)');
		$(".closeBtn",infotip).bind("click",function(){
			infotip.hide();
			window.application.sendNotify(Constants.command.os.setting.save, {key:"os.help.tip.controlpanel",value:"1"});
		});
		infotip.show();
		
		try{
			var p = com.gleasy.os.IconPan.getPanById(0).findIconByApp("控制面板").view;
			infotip.position({
				my: "center bottom",
				at: "center top",
				of: p,
				offset:"0 0"
			});
		}catch(e){
			infotip.hide();
		}
	}
	
				//
			//com.gleasy.os.IconPan.getPanById(0).findIconByApp("控制面板");
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		if(eventId == Constants.command.loadDesktopOK){
			showInfoTip();
			showRclickTip();
			showShowhideTip();
			showControlPanelTip();
		}
	}

	
	var configEvent = function(){
		      
	}
	
	//构造函数调用
	_construct();
}

//继承MvcBase
com.gleasy.os.view.TipView.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本View
window.application.addView(new com.gleasy.os.view.TipView());
