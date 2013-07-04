/**
 * 系统登录
 */
Namespace.register("com.gleasy.os.login.view");
com.gleasy.os.login.view.SystemLockContainer = function(param){
	$.extend(param,{
		hasFrame:0,
		hasDock:0,
		canResize:0,
		canMax:0,
		title:"登录",
		initSize:[300,120],
		canMin:0	
	});
	com.gleasy.os.view.Container.call(this,param);//继承Container
	
	var _this = this;
	var loginPanel = null;
	var registerPanel = null;
	var inviteRegister = null;
	var findPwdPanel = null;
	var systemStaffInfoPanel = null;
	var accountInvalidPanel = null;
	var reloginPanel = null;

	var panel = 'login';
    var options = {};
	
	var _construct = function(param){
        com.gleasy.os.login.view.SystemLockContainer.addPan(_this);
        $.extend(options, param);
        if(param.panel){
            panel = param.panel;
        }
        initComponent();
    }

	var initComponent = function(){
		loginPanel = new com.gleasy.os.login.view.SystemLoginPanel({container:_this,message:param.message});
		_this.view.addClass("osWindowCurrent");
		_this.setZindex(Constants.config.os.layer.loginPanel);
		_this.center();
		
		_this.nav(panel, options);
        configEvent();
	};

    this.isInviteReg = function(){

    }

	
	this.nav = function(_panel,_param){

		panel = _panel;
        _this.hideFrame();
		if(panel=='login'){
            loadCss(Constants.config.assetPrefix+"/platform/os/assets/stylesheets/ctrl/ctrl.css");
            loginPanel.show();
        }else if(panel == 'inviteRegister'){
            if (!inviteRegister) {
                var params = {};
                $.extend(params, _param, {container:_this});
                inviteRegister = new com.gleasy.os.login.view.SystemRegisterPanel(params);
            }
            inviteRegister.show();
		}else if(panel == 'register'){
            if (!registerPanel) {
                var params = {};
                $.extend(params, _param, {container:_this});
                registerPanel = new com.gleasy.os.login.view.SystemRegisterPanel(params);
            }
			registerPanel.show();
		}else if(panel == 'autoLogin'){
			loginPanel.autoLogin(_param);
		}else if(panel == 'findPasswd'){
            $LAB
                .script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/accountCenter/view/PasswordFind.class.js")
                .wait(function(){
                    var param = _param || {};
                    $.extend(param, {container:_this});
                    findPwdPanel = new com.gleasy.ctrlPanel.accountCenter.view.PasswordFind(param);
                    findPwdPanel.show();
                });

		}else if(panel == 'passwordNotFind'){
            $LAB
                .script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/accountCenter/view/PasswordNotFindPanel.class.js")
                .wait(function(){
                    var param = _param || {};
                    $.extend(param, {container:_this});
                    var notFound = new com.gleasy.ctrlPanel.accountCenter.view.PasswordNotFindPanel(param);
                    notFound.show();
                });

		}else if(panel == 'staffInfo'){
			if(!systemStaffInfoPanel){ 
            	$LAB
	   			.script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/login/view/SystemStaffInfoPanel.class.js")
	   			.wait(function(){	
		            systemStaffInfoPanel = new com.gleasy.os.login.view.SystemStaffInfoPanel({container:_this});
		            systemStaffInfoPanel.show();
            	});
            }else{
            	systemStaffInfoPanel.show();
            }
		}else if(panel == 'accountInvalid'){
			if(!accountInvalidPanel){
                accountInvalidPanel = null;
            }
            $LAB
                .script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/login/view/AccountInvalidPanel.class.js")
                .wait(function(){
                    var msg = _param.msg || _param.message;
                    accountInvalidPanel = new com.gleasy.os.login.view.AccountInvalidPanel({
                        container:_this,
                        subject: msg
                    });
                    accountInvalidPanel.show();
                });
        }else if(panel == 'relogin'){
			if(!reloginPanel){
            	$LAB
	   			.script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/login/view/AccountInvalidPanel.class.js")
	   			.wait(function(){
                    reloginPanel = new com.gleasy.os.login.view.AccountInvalidPanel({
                        container:_this,
                        subject: _param.message,
                        okTxt:"重新登录",
                        callback: function(){
                            _this.nav("login");
                        }
                    });
                    reloginPanel.show();
            	});
            }else{
                reloginPanel.show();
            }
		}else if(panel == "notSupportBrowser"){
            $LAB
                .script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/login/view/SystemNotSupportBrowser.class.js")
                .wait(function(){
                    var param = _param || {};
                    $.extend(param, {container:_this});
                    var np = new com.gleasy.os.login.view.SystemNotSupportBrowser(param);
                    np.show();
                });
        }

	};

    this.showAgreementView = function(){
        $("div.agreementView", _this.view).show();
        _this.center();
    };

    this.hideAgreementView = function(){
        $("div.agreementView", _this.view).hide();
        _this.center();
    };


	
	this.close = function(){
		//window.location.reload();
        _this.nav("login");
	};
	
	var resize = function(){
	};

    var configEvent = function() {
        $(window).resize(function() {
            _this.center();
        });
    };
	
	return _construct(param);
};

com.gleasy.os.login.view.SystemLockContainer.instance = null;
com.gleasy.os.login.view.SystemLockContainer.getInstance = function(param){
    if(com.gleasy.os.login.view.SystemLockContainer.instance == null) {
        com.gleasy.os.login.view.SystemLockContainer.instance = new com.gleasy.os.login.view.SystemLockContainer(param);
    }
    return com.gleasy.os.login.view.SystemLockContainer.instance;
}


com.gleasy.os.login.view.SystemLockContainer.pans = [];
com.gleasy.os.login.view.SystemLockContainer.addPan = function(pan) {
    com.gleasy.os.login.view.SystemLockContainer.pans.push(pan);
};
