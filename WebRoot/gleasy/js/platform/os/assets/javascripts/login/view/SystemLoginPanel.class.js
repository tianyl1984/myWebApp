/**
 * 系统登录
 */
Namespace.register("com.gleasy.os.login.view");
com.gleasy.os.login.view.SystemLoginPanel = function(param){
	var _this = this;
	var message = "";
	var contentHtml = Constants.config.assetPrefix+"/platform/os/assets/javascripts/login/view/SystemLoginPanel.html?id="+ResourceConfig.version;
    var verifyCodeUrl = AjaxConfig.url['sso']+"/vcs.do?rand=";
    var accountStatusUrl = AjaxConfig.url['sso']+"/accountStatus";
    var passwordFindUrl = AjaxConfig.url['cp']+"/password/find.json";
    var container = null;
    var usernameIpt = null;
    var passwordTip = null;
    var passwordIpt = null;
    var checkCodeIpt = null;
    var checkCodePane = null;
    var okBtn = null;

    var badNum = null;

    var usernameTip = "帐号/手机号/邮箱";
    var checkCodeTip = "验证码";

	var view = null;
	var _construct = function(){
		container = param?param.container:null;
		initComponent();
		
	};
	
	var loging = false;

	var size = {w:412,h:264};
	var initComponent = function(){		
		view = $("<div class='systemLoginPanel'/>");
		if(com.gleasy.os.login.view.SystemLoginPanel.content){
			view.html(com.gleasy.os.login.view.SystemLoginPanel.content.clone());
				//setErrorMsg(param.message);
			configEvent();
			resize();
			return;
		}
		AjaxConfig.ajax({
			url:contentHtml,
			type:'GET',
			dataType:'html',
			success:function(result){
                com.gleasy.os.login.view.SystemLoginPanel.content = $(result);
                view.html(result);
				configEvent();
				resize();
			}
		});
	};
	
	this.show = function(){
		container.setTitle("登录");
		container.setContent(view);
		adjust(size.w,size.h);
	};
	
	this.autoLogin = function(_param){
		_this.show();
        usernameIpt.val(_param.account);
		//passwordIpt.val(_param.password);
		_this.login(_param.account,_param.password,_param.verifyCode);
	};
	
	this.login = function(account,password,verifyCode){
		if(loging) return;
		loging = true;
		$("input",view).attr("disabled",true);

        okBtn.attr("disabled", "disabled").removeClass("oLoginBtn").addClass("oLoginBtnLoading");
        $(".oLoginLoadingBtn",view).show();
        okBtn.val("登录中");
		//setErrorMsg("正在为您登录系统...");
		
		window.application.sendNotify(Constants.command.os.account.auth,{
			account:account,
			verifyCode:verifyCode,
			password:password,
			success:function(result){
				loging = false;
				$("input",view).attr("disabled",false);
                okBtn.attr("disabled", false).addClass("oLoginBtn").removeClass("oLoginBtnLoading");
                $(".oLoginLoadingBtn",view).hide();
                okBtn.val("登录");
                if(!result){
                    setErrorMsg("网络异常 :(");
                    return;
                }
				$.log("login:"+result+";"+result.status);
				if(result.status == 0){
                    badNum = null;
                    $.cookie('username', account, { expires: 7});
                    //window.onbeforeunload = null;
					//window.location.reload();
					$.log("login:22");
					if(Constants.data.user && Constants.data.user.uid != result["ucenter.uid"]){
						window.onbeforeunload = null;
						window.location.reload();
					}else{
						container.exit();
						com.gleasy.os.SystemInfo.login = true;
						window.application.sendNotify(Constants.command.os.loginSuccess,{});
					}
				}else{
                    switch (result.status){
                        case 80:
                            setErrorMsg(result.description);
                            break;
                        case 20:
                            if($("#checkCode:visible", view).length < 1) {
                                setErrorMsg("请输入验证码 :(");
                            }else{
                                setErrorMsg("验证码不正确 :(");
                            }
                            badNum = account;
                            break;
                        case 1:
                            if(result.data && result.data.expireDate) {
                                var expireDays = result.data.expireDays;
                                var msg = "您的帐号已于" + result.data.expireDate + "到期，<br/>";
                                //expireDays = 29;
                                if(expireDays < 30 && expireDays > -1) {
                                    var left = 30 - expireDays;
                                    msg += ("我们将为您保留"+left+"天数据，<br/>");
                                }
                                msg += "请尽快联系管理员。";
                                container.nav('accountInvalid', {msg:msg});
                            }else{
                                container.nav('accountInvalid', {msg:"您的帐号已经到期。<br/>我们将为您保存30天数据，<br/>请尽快联系管理员。"});
                            }
                            break;
                        case 2:
                        case 4:
                            container.nav('accountInvalid', {msg:"您的帐号已经被暂停使用，<br/>请联系贵公司管理员。"});
                            break;
                        case 3:
                            container.nav('accountInvalid', {msg:"您的帐号已经被删除"});
                            break;
                        default:
                            setErrorMsg("登录失败 :(");
                            break;
                    }
                    if(result.needChkCode /*&& $(".checkCodePane:visible",view).length < 1*/){

                        checkCodePane.show();
                        $("#vcsImg", view).attr("src", verifyCodeUrl);
                        badNum = account;
                    }
                    //adjust(412,245);

				}
			},
			error:function(){
				loging = false;
				$("input",view).attr("disabled",false);
                okBtn.attr("disabled", false).addClass("oLoginBtn").removeClass("oLoginBtnLoading");
                $(".oLoginLoadingBtn",view).hide();
                okBtn.val("登录");
				setErrorMsg("网络异常 :(");
			}
		});	
	};

	var adjust = function(width,height){
		size.w = width;
		size.h = height;
		container.setSize(width,height);
		container.center();	
	};

    var checkNeedCode = function(account, success, error) {

        AjaxConfig.loadData(accountStatusUrl, {account: account}, function(data) {
            //不用输入验证码
            checkCodePane.hide();
            typeof success == "function" && success();
        }, function(ret) {
            if(ret.status == 1) {
                //需要
                checkCodePane.show();
                badNum = account;
                typeof error == "function" && error();
            }
        });
    };
		
	var setErrorMsg = function(msg){
		message = msg;
        if (message) {
            $(".oLoginErrorMsg", view).html(message).show();
        }else{
            $(".oLoginErrorMsg", view).hide();
        }
	};

    var  valueIsPlaceholder = function(input) {
        return input.val() == input.attr('placeholder');
    }
	
	var configEvent = function(){

        usernameIpt = $("#usernameIpt", view);
        checkCodeIpt = $("#checkCode", view);
        passwordIpt = $("#passwordIpt", view);
        passwordTip = $("#passwordTip", view);
        checkCodePane = $("#checkCodePane", view);
        okBtn = $(".okBtn",view);
        $("#vcsImg", view).attr("src", verifyCodeUrl+Math.random());

        usernameIpt.blur(function(){
            var val = this.value;
            if(val == ''){
            }else{
                checkNeedCode(val);
            }
        });
        usernameIpt.focus(function(){
            setErrorMsg(false);
            usernameIpt.removeClass("cFormItem_iptError");
        }).keyup(function() {
                usernameIpt.removeClass("cFormItem_iptError").removeAttr('style');

            });

        passwordIpt.focus(function(){
            setErrorMsg(false);
            passwordIpt.removeClass("cFormItem_iptError");
        });
        passwordIpt.keyup(function() {
                passwordIpt.removeClass("cFormItem_iptError");
            });


        checkCodeIpt.focus(function(){
            setErrorMsg(false);
            checkCodeIpt.removeClass("cFormItem_iptError");
        }).keyup(function() {
                checkCodeIpt.removeClass("cFormItem_iptError");
            });


        $(".oChangeIden",view).click(function(evt){
			$("#vcsImg", view).attr("src", verifyCodeUrl+Math.random());
			return false;
		});	
		$(".oRegisterAcount",view).click(function(evt){
			container.nav('register');
			return false;
		});


		
		$LAB
            .script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/view/WidgetUtil.js")
            .script(Constants.config.assetPrefix+"/script/jquery/jquery.placeholder.js")
            .script(Constants.config.assetPrefix+"/script/jquery/jquery.ui.datepicker.js")
            .wait()
            .script(Constants.config.assetPrefix+"/script/jquery/jquery.ui.datepicker-zh-CN.js")
            .script(Constants.config.assetPrefix+"/script/jquery/jquery.validate.js")
            .wait()
            .script(Constants.config.assetPrefix+"/script/jquery/jquery.validate.ex.js")
            .wait(function(){
                com.gleasy.ctrlPanel.view.WidgetUtil.addEnterTriggerClick(passwordIpt, okBtn);
                com.gleasy.ctrlPanel.view.WidgetUtil.addEnterTriggerClick(checkCodeIpt, okBtn);

                var isIE = $.browser.msie;
                if (isIE) {
                    usernameIpt.placeholder();
                    passwordIpt.placeholder();
                    checkCodeIpt.placeholder();
                }

                var username = $.cookie('username');
                var inp = usernameIpt[0];
                if(username){
                    usernameIpt.val(username).removeClass("oPlaceholder").focus();
                    inp = passwordIpt[0];
                }
                if(inp.setSelectionRange){
                    inp.focus();
                    //inp.setSelectionRange(0,0);
                }else if (inp.createTextRange) {
                    var range = inp.createTextRange();
                    range.collapse(true);
                    range.moveEnd('character', 0);
                    range.moveStart('character', 0);
                    range.select();
                }

		});


		$(".checkCodePane",view).hide();
		adjust(412,264);



        okBtn.click(function(){
			var account = trim(usernameIpt.val());
			if(!account || account.length<1 || valueIsPlaceholder(usernameIpt)){
                usernameIpt.addClass("cFormItem_iptError");
                setErrorMsg("请输入帐号 :(");
				return false;
			}
			
			var pwd = passwordIpt.val();
			if(!pwd || trim(pwd)=='' || valueIsPlaceholder(passwordIpt)){
                $(".passwordField:visible", view).addClass("cFormItem_iptError");
                setErrorMsg("请输入密码 :(");
				return false;
			}
            if ($("#checkCode:visible", view).length > 0) {
                var checkCode = trim(checkCodeIpt.val());
                if (!checkCode || checkCode.length<1 || valueIsPlaceholder(checkCodeIpt)) {
                    checkCodeIpt.addClass("cFormItem_iptError");
                    setErrorMsg("请输入验证码 :(");
                    return false;
                }else if(checkCode.length != 5){
                    checkCodeIpt.addClass("cFormItem_iptError");
                    setErrorMsg("验证码不正确 :(");
                    return false;
                }
                _this.login(account,pwd,checkCode);
            }else{
                checkNeedCode(account, function() {
                    _this.login(account,pwd,"");
                }, function() {
                    setErrorMsg("请输入验证码 :(");
                });
            }


        });

		$("#findPasswd",view).bind("click",function(){
			var account = trim(usernameIpt.val());
			if(!account || account.length<1 || account == usernameTip){
                usernameIpt.addClass("cFormItem_iptError");
                setErrorMsg("请输入帐号 :(");
				return false;
			}
            AjaxConfig.ajax({
                url : passwordFindUrl,
                dataType : "json",
                data:{
                    username: account
                },
                success : function(ret) {

                    if(ret.status == 0){
                        var setting = ret.data.setting || {};
                        if(setting.backupEmailBinded || setting.passwdProtectionBinded || setting.mobileBinded){
                            container.nav("findPasswd", {username: account, setting: setting});
                        }else{
                            container.nav("passwordNotFind");
                        }
                    }else if(ret.status == 80){
                        setErrorMsg("帐号不存在 :(");
                        return false;
                    }else{
                        setErrorMsg("网络异常 :(");
                        return false;
                    }


                }
            });

            return false;
		});

	};
	
	var resize = function(){
	};
	
	return _construct();
}

com.gleasy.os.login.view.SystemLoginPanel.initCache = function() {
    var contentHtml = Constants.config.assetPrefix+"/platform/os/assets/javascripts/login/view/SystemLoginPanel.html?id="+ResourceConfig.version;
    AjaxConfig.ajax({
        url:contentHtml,
        type:'GET',
        dataType:'html',
        success:function(result){
            com.gleasy.os.login.view.SystemLoginPanel.content = $(result);
        }
    });
};
