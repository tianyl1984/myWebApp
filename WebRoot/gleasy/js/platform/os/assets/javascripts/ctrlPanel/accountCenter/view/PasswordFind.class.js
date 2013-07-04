Namespace.register("com.gleasy.ctrlPanel.accountCenter.view");

com.gleasy.ctrlPanel.accountCenter.view.PasswordFind = function(param){


    var contentHtml = Constants.config.assetPrefix
        +"/platform/os/assets/javascripts/ctrlPanel/accountCenter/html/PasswordFind.html?rand="+ResourceConfig.version;
    var passwordFindUrl = AjaxConfig.url['cp']+"/password/find.json";


    var _this = this;
    var view = null;
    var container = null;
    var setting = null;

	var options = {
	};

    this.show = function(){
        container.setTitle("找回密码");
        container.setContent(view);
        container.setSize(510,350);
        container.center();
        container.showFrame();
        container.view.addClass("oWindow_current");
    };


	var _construct = function(){
		$.extend(options,param);
        container = param.container;
        setting = param.setting;
        view = $("<div class='cWrap'></div>");
        //_this.setContent(view);
        //_this.center();
        initComponent();
	};
	
	
	var initComponent = function(){

        loadCss(Constants.config.assetPrefix+"/platform/os/assets/stylesheets/ctrl/ctrl.css");
        loadCss(Constants.config.assetPrefix+"/script/jquery/css/jquery.validate.password.css");
        $LAB
            .script(Constants.config.assetPrefix+"/script/jquery/jquery.validate.js")
            .wait()
            .script(Constants.config.assetPrefix+"/script/jquery/jquery.validate.ex.js")
            .script(Constants.config.assetPrefix+"/script/jquery/jquery.validate.password.js")
            .script(Constants.config.assetPrefix+"/script/jquery/jquery.metadata.js")
            .wait()
            .script(Constants.config.assetPrefix+"/script/jquery/localization/messages_cn.js")
            .script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/accountCenter/view/TopView.class.js")
            .script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/accountCenter/view/SuccessView.class.js")
            .script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/accountCenter/view/TimeoutView.class.js")
            .script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/accountCenter/view/ValidateView.class.js")
            .script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/accountCenter/view/PasswordResetView.class.js")
            .script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/accountCenter/view/PasswordNotFindPanel.class.js")
            .script(Constants.config.assetPrefix+"/script/common/EncryptUtil.js")

            .wait(function(){
                AjaxConfig.ajax({
                    url:contentHtml,
                    type:'GET',
                    dataType:'html',
                    success:function(result){
                        view.html(result);
                        //submitUsername();
                        initEx();
                        var passwdResetView = new com.gleasy.ctrlPanel.accountCenter.view.PasswordResetView({
                            container: container,
                            successCallback: function(){
                                view.empty();
                                container.nav("login");
                            },
                            successBtnTxt: '返回登录'
                        });
                        view.append(passwdResetView.getContentPane());


                    }
                });
            });

	};


	var initEx = function(){

        configEvent();
	};

	var configEvent = function(){
        $("#returnBtn", view).click(function(){
            retLogin();
        });
    };

    var retLogin = function(){
        view.empty();
        container.nav("login");
    };
	
	_construct();
};

