/**
 * 系统登录
 */
Namespace.register("com.gleasy.os.login.view");
com.gleasy.os.login.view.SystemRegisterPanel = function(param){
	var _this = this;
	var message = "";
	var contentHtml = Constants.config.assetPrefix+"/platform/os/assets/javascripts/login/view/SystemRegisterPanel.html?id="+ResourceConfig.version;
    var verifyCodeUrl = AjaxConfig.url['sso']+"/vcs.do?rand=";
    var usernameCheckUrl = AjaxConfig.url['cp']+"/username/check.json";
    var getInviterUrl = AjaxConfig.url['cp']+"/inviter/get.json";
    var codeCheckUrl = AjaxConfig.url['sso']+"/chkCodeValidate";
	var container = null;
    var regUserForm = null;
    var regCompanyForm = null;
    var inviter = null;
    var options = {};

	var view = null;
	var _construct = function(){
		container = param.container;
        $.extend(options, param);
		initComponent();
        var tmpPans = com.gleasy.os.login.view.SystemLockContainer.pans.slice(0);
        if(tmpPans.length > 1) {
            for(var i=0; i<tmpPans.length; i++) {
                var pan = tmpPans[i];
                if( pan != container) {
                    pan.view.remove();
                }
            }
        }
	};


	var initComponent = function(){		
		view = $("<div class='systemRegisterPanel'/>");

        loadCss(Constants.config.assetPrefix+"/script/jquery/css/jquery.validate.password.css");
        loadCss(Constants.config.assetPrefix+"/platform/os/assets/stylesheets/ctrl/ctrl.css");
	   $LAB
           .script(Constants.config.assetPrefix+"/script/libs/Event.class.js")
           .script(Constants.config.assetPrefix+"/script/components/DatePicker/DatePickerView.class.js")
           .script(Constants.config.assetPrefix+"/script/components/DatePicker/LunarModel.class.js")
           .script(Constants.config.assetPrefix+"/script/components/UI/Select.class.js")
           .script(Constants.config.assetPrefix+"/script/components/VerificationCode/VerificationCodeView.class.js")
           .script(Constants.config.assetPrefix+"/script/jquery/jquery.gleasy.drag.js")
           .script(Constants.config.assetPrefix+"/script/jquery/jquery.validate.js")
           .wait()
           .script(Constants.config.assetPrefix+"/script/jquery/jquery.validate.ex.js")
           .wait()
           .script(Constants.config.assetPrefix+"/script/jquery/jquery.validate.password.js")
           .script(Constants.config.assetPrefix+"/script/jquery/jquery.metadata.js")
           .script(Constants.config.assetPrefix+"/script/jquery/localization/messages_cn.js")
           .script(Constants.config.assetPrefix+"/script/components/ProvinceAndCity/jquery.citySelect.js")
	   .script(Constants.config.assetPrefix+"/script/common/CodeConfig.class.js")
	   .wait(function(){
               AjaxConfig.ajax({
                   url:contentHtml,
                   type:'GET',
                   dataType:'html',
                   success:function(result){
                       view.html(result);
                       initEx();
                   }
               });

           });
	};
	
	this.show = function(){
		container.setContent(view);
		container.setSize(480,470);
        container.view.css("z-index", 99999);
		container.center();
	};

    var initEx = function() {
        if(options.paramEx && options.paramEx.inviter) {
            inviter = options.paramEx.inviter;
            showInviter(inviter);
        }else{
            $("#regTxt", view).show();
        }
        configEvent();
        resize();
        _this.show();
    };
	
	var adjust = function(width,height){
		container.setSize(width,height);
		container.center();	
	};


    var showInviter = function(inviter) {

        $("#inviter", view).text(options.paramEx.inviterName);
        $("#regTxt", view).hide();
        $("#invitePan", view).show();

        $(".inviteCodePan", view).hide();//邀请注册
    };

    var checkCode = function(code, success, error) {
        var param = {
            checkCode: code
        };
        AjaxConfig.loadData(codeCheckUrl, param, success, error);
    };

    var resetAgreeBtn = function(agreeBtn, disabled) {
        if(disabled) {
            agreeBtn.addClass("disabled").attr("disabled", "disabled").text("提交中..");
        }else{
            agreeBtn.removeClass("disabled").attr("disabled", false).text("同意协议并提交");
        }

    };

    var setCodeInvalid = function(codePan) {
        codePan.addClass(" cFormItem_iptError");
        var parent = codePan.parent();
        parent.find(".validPan").hide();
        parent.find(".errorPan").addClass("cError").html("请输入正确的验证码").show();
    };

    var validateBirth = function(birthView) {
        var valid = birthView.yearSelected && birthView.monthSelected && birthView.daySelected;
        if(!valid) {
            var parent = birthView.view.parent();
            parent.find(".validPan").hide();
            parent.find(".errorPan").addClass("cError").html("不能为空").show();
            return false;
        }else{
            var parent = birthView.view.parent();
            parent.find(".errorPan").hide();
            parent.find(".validPan").show();
        }
        return true;
    };

    var  configUserFormEvent = function() {
        var thisYear = (new Date()).getFullYear();
        if(thisYear < 2012) {
            thisYear = 2012;
        }
        var birthModel = new com.gleasy.component.LunarModel({
            id:"birthModel",
            lastYear:thisYear
        });
        /*var now = new Date();
        now.setYear(1980);
        var defDate = $.datepicker.formatDate("yy-mm-dd", now);*/
        var birthView = new com.gleasy.component.DatePickerView(birthModel, {
            id:"birthView",
            view:$('.birthInputPan', view),
            hiddenType:{id:"birthdayType"},
            showEmpty: true,
            hiddenDate:{id:"birthInput", name:"sendDate"/*, value:defDate*/}
        });

        $("[name='userbirthDay']", regUserForm).data("birthView", birthView);

        birthView.view.find("select").change(function() {
            var hasChecked = birthView.view.data("hasChecked");
            if(!hasChecked) {
                return;//第一次检验过才会判断 select的变化
            }
            var valid = birthView.yearSelected && birthView.monthSelected && birthView.daySelected;
            if(!valid) {
                var parent = birthView.view.parent();
                parent.find(".validPan").hide();
                parent.find(".errorPan").addClass("cError").html("不能为空").show();
            }else{
                var parent = birthView.view.parent();
                parent.find(".errorPan").hide();
                parent.find(".validPan").show();
            }
        });


        var locationPan = $(".locationPan", regUserForm).wCitySelect({
            provBtn:".province", //省份按钮
            cityBtn:".city", //城市按钮
            address:""
        });


        var setErrorMsg = function(msg){
            $(".exceptionPan",regUserForm).html(msg);
        };

        var reg = function(agreeBtn, verifyCode) {
            resetAgreeBtn(agreeBtn, true);
            //var inviteCode = trim($(".oRegInviteCodeIpt",view).val());
            var account = trim($('input[name="account"]', regUserForm).val());
            var password = trim($('input[name="password"]', regUserForm).val());
            var name = trim($('input[name="name"]', regUserForm).val());
            var birth = $("#birthInput", regUserForm).val();
            var birthdayType = $("#birthdayType", regUserForm).val();
            var location = locationPan.getData();
            var gender = $("input[name='gender']:checked", regUserForm).val();
            var inviteCode = $("input[name='inviteCode']", regUserForm).val();


            window.application.sendNotify(Constants.command.os.account.regIndividual, {
                data:{
                    username:account,
                    password:password,
                    name:name,
                    birthday:birth,
                    gender:gender,
                    birthdayType:birthdayType,
                    verifyCode:verifyCode,
                    location:location,
                    inviter:inviter,
                    inviteCode:inviteCode
                },
                success:function (result) {
                    $.log(result + ";" + result.status);
                    if (result.status == 0) {
                        container.nav("autoLogin", {account:account, password:password, verifyCode:verifyCode});
                    }else if(result.status == 10){
                        setErrorMsg(result.description);
                        submited = false;
                        resetAgreeBtn(agreeBtn, false);
                    } else if (result.status == 30) {

                        submited = false;
                        resetAgreeBtn(agreeBtn, false);

                        var element = $("input[statusCode='30']", regUserForm);
                        element.addClass(" cFormItem_iptError");
                        var parent = element.parent();
                        parent.find(".validPan").hide();
                        element.closest("dd").find(".errorPan").addClass("cError").html(result.description).show();

                    } else if (result.status == 40) {

                        submited = false;
                        resetAgreeBtn(agreeBtn, false);

                        var element = $("input[statusCode='40']", regUserForm);
                        element.addClass(" cFormItem_iptError");
                        var parent = element.parent();
                        parent.find(".validPan").hide();
                        element.closest("dd").find(".errorPan").addClass("cError").html(result.description).show();

                    } else {
                        submited = false;
                        resetAgreeBtn(agreeBtn, false);
                        setErrorMsg("注册失败 :(");
                    }
                    $(".changeVcs", regUserForm).trigger("click");
                },
                error:function () {
                    submited = false;
                    resetAgreeBtn(agreeBtn, false);
                    setErrorMsg("网络异常");
                    $(".changeVcs", regUserForm).trigger("click");
                }
            });
        };

        var submited = false;
        $(".agreeBtn", regUserForm).click(function () {
            if(submited) {
                return;
            }
            submited =true;
            var agreeBtn = $(this);
            if (!regUserForm.valid()) {
                submited = false;
                return false;
            }
            if(!validateBirth(birthView)){
                submited = false;
                return false;
            }

            var codePan = $(".verifyCode", regUserForm);
            var verifyCode = codePan.val();
            if(verifyCode.length != 5) {
                submited = false;
                setCodeInvalid(codePan);
                return false;
            }
            checkCode(verifyCode, function(data) {
                reg(agreeBtn, verifyCode);

            }, function(ret) {
                if (ret.status == 20) {
                    submited = false;
                    setCodeInvalid(codePan);
                } else {
                    submited = false;
                    setErrorMsg("网络异常 :(");
                }
            });

        });
    };

    var configEvent = function(){
        regUserForm = $("#regUserForm", view);
        regCompanyForm = $("#regCompanyForm", view);
		$(".oReturnLoginBtn",view).click(function(evt){
			container.nav('login', {fromRegister: true});
			return false;
		});

        $('.Swichbtn', view).swichbtndragable({
            //defaultPos:defaultPos,
            //disabled: disabled,
            firstCallback: function(){
                $(".regEtpPan",view).hide();
                $(".regUserPan",view).show();
                adjust(480,510);
            },
            lastCallback: function(){
                $(".regUserPan",view).hide();
                $(".regEtpPan",view).show();
                adjust(480,580);
            }
        });

        $(".vcsImg", view).attr("src", verifyCodeUrl + Math.random());
        $(".changeVcs", view).click(function (evt) {
            $(".vcsImg", view).attr("src", verifyCodeUrl + Math.random());
            $(".verifyCode", view).val("").trigger("change");
            return false;
        });

        container.registerClickOutsideEvent("div.agreementView", function() {
            container.hideAgreementView();
        });

        $(".showAgreement", view).click(function () {
            container.showAgreementView();
        });

        configUserRule();
        configUserFormEvent();

        configCompanyRule();
        configCompanyEvent();
    };

    var configCompanyEvent = function(){

        var tradeSelect = null;
        var scaleSelect = null;

        var locationPan = $(".locationPan", regCompanyForm).wCitySelect({
            provBtn:".province", //省份按钮
            cityBtn:".city", //城市按钮
            address:""
        });

        var setErrorMsg = function(msg){
            $(".exceptionPan",regCompanyForm).html(msg);
        };

        Config.batchLoad([CodeConfig.INDUSTRY, CodeConfig.SCALE],function(cache){
            var key1 = cache[CodeConfig.INDUSTRY];
            var key2 = cache[CodeConfig.SCALE];
            if(typeof key1 == 'string'){
                key1 = $.evalJSON(key1);
            }
            if(typeof key2 == 'string'){
                key2 = $.evalJSON(key2);
            }
            var keys = $.merge(key1, key2);
            if(keys && keys.length > 0) {
                Config.batchLoad(keys,function(){
                    tradeSelect = new com.gleasy.component.ui.select({
                        container:$(".tradeSpan",view),
                        configKey:CodeConfig.INDUSTRY,
                        inputName:"oRegCmpTradeIpt",
                        inputId:"oRegCmpTradeIpt",
                        emptyOption:true
                    });

                    scaleSelect = new com.gleasy.component.ui.select({
                        container:$(".scaleSpan",view),
                        configKey:CodeConfig.SCALE,
                        inputName:"oRegScaleIpt",
                        inputId:"oRegScaleIpt",
                        emptyOption:true
                    });
                });
            }
        });



        var submited = false;

        var reg = function(agreeBtn, verifyCode) {
            resetAgreeBtn(agreeBtn, true);
            var domain = trim($('input[name="account"]', regCompanyForm).val());
            var password = trim($('input[name="password"]', regCompanyForm).val());
            var name = trim($('input[name="name"]', regCompanyForm).val());
            var trade = $("#oRegCmpTradeIpt", regCompanyForm).val();
            var scale = $("#oRegScaleIpt", regCompanyForm).val();
            var location = locationPan.getData();
            var contactName = trim($('input[name="contactName"]', regCompanyForm).val());
            var phone = trim($('input[name="contactPhone"]', regCompanyForm).val());
            var inviteCode = trim($('input[name="inviteCode"]', regCompanyForm).val());


            window.application.sendNotify(Constants.command.os.account.regEnterprise, {
                data:{
                    regCode:domain,
                    password:password,
                    name:name,
                    contactName:contactName,
                    contactPhone:phone,
                    verifyCode:verifyCode,
                    industry:trade,
                    scale:scale,
                    location:location,
                    inviter:inviter,
                    inviteCode:inviteCode
                },
                success:function (result) {
                    $.log(result + ";" + result.status);
                    if (result.status == 0) {
                        container.nav("autoLogin", {account:"admin@" + domain, password:password, verifyCode:verifyCode});
                    } else if (result.status == 10) {
                        setErrorMsg(result.description);
                        submited = false;
                        resetAgreeBtn(agreeBtn, false);
                    } else if (result.status == 30) {

                        submited = false;
                        resetAgreeBtn(agreeBtn, false);

                        var element = $("input[statusCode='30']", regCompanyForm);
                        element.addClass(" cFormItem_iptError");
                        var parent = element.parent();
                        parent.find(".validPan").hide();
                        element.closest("dd").find(".errorPan").addClass("cError").html(result.description).show();

                    }else if (result.status == 40) {

                        submited = false;
                        resetAgreeBtn(agreeBtn, false);

                        var element = $("input[statusCode='40']", regCompanyForm);
                        element.addClass(" cFormItem_iptError");
                        var parent = element.parent();
                        parent.find(".validPan").hide();
                        element.closest("dd").find(".errorPan").addClass("cError").html(result.description).show();

                    }  else {
                        setErrorMsg("注册失败 :(");
                        submited = false;
                        resetAgreeBtn(agreeBtn, false);
                    }
                    $(".changeVcs", regCompanyForm).trigger("click");
                },
                error:function () {
                    setErrorMsg("网络异常");
                    submited = false;
                    resetAgreeBtn(agreeBtn, false);
                    $(".changeVcs", regCompanyForm).trigger("click");
                }
            });
        }

        $(".agreeBtn",regCompanyForm).click(function(){
            if(submited) {
                return;
            }
            submited =true;
            var agreeBtn = $(this);
            if(!regCompanyForm.valid()) {
                submited = false;
                resetAgreeBtn(agreeBtn, false);
                return false;
            }
            var codePan = $(".verifyCode", regCompanyForm);
            var verifyCode = codePan.val();
            if(verifyCode.length != 5) {
                submited = false;
                setCodeInvalid(codePan);
                return false;
            }
            checkCode(verifyCode, function(data) {
                reg(agreeBtn, verifyCode);
            }, function(ret) {
                if (ret.status == 20) {
                    submited = false;
                    setCodeInvalid(codePan);
                } else {
                    submited = false;
                    setErrorMsg("网络异常 :(");
                }
            });

        });
    };


    var configUserRule = function(){
        var rule = {

            "account": {
                //required: true,
                regUserName: [true,4],
                checkUsername: [usernameCheckUrl]
            },
            "name": {
                required: true,
                minlength: 1,
                maxlength:30
            },

            "password": {
                password: [true, 6, 14],
                //required: true,
                //minlength: 6,
                maxlength:14
            },
            "passwordConfirm": {
                required: true,
                equalTo: "#password"
            },
            "userbirthDay": {
                birthDayRequired: []
            },
            "checkCode": {
                required: true,
                minlength:5,
                maxlength:5
                //remoteCheck:[codeCheckUrl]
            }
        };

        var errMsg = {
            "account": {
                required: "不能为空",
                userName: "请输入正确格式",
                checkUsername: "此用户已存在",
                minlength: "字符数不足",
                maxlength: "不能超过18个字符"
            },
            "name": {
                required: "不能为空",
                minlength: "字符数不足",
                maxlength: "不能超过30个字符"
            },

            "password": {
                required: "不能为空",
                //minlength: "字符数不足",
                maxlength: "不能超过14个字符"
            },
            "passwordConfirm": {
                required: "不能为空",
                equalTo: "两个密码不一致"
            },
            "checkCode": {
                required: "不能为空",
                remoteCheck:"请输入正确的验证码",
                minlength:"请输入正确的验证码",
                maxlength:"请输入正确的验证码"
            }
        };

        /*if(!inviter) {
            rule["inviteCode"] = {
                required: true
            };
            errMsg["inviteCode"] = {
                required: "不能为空"
            };

        }*/

        $("#regUserForm", view).validate({
            rules: rule,
            errorClass: "cFormItem_iptError",
            messages: errMsg,
            focusCleanup: true,
            /* errorPlacement:function(error,element) {
             element.closest("tr").find("td.errorPan").html(error);
             },*/
            showErrors:function(errorMap, errorList){
                $.each(this.successList, function(i, item) {
                    var element = $(item);
                    element.removeClass(" cFormItem_iptError");
                    element.closest("dd").find(".errorPan").hide();
                    if(!element.hasClass("passwd")){
                        element.parent().find(".validPan").show();
                    }
                });
                $.each(errorList, function(i, ele) {
                    var element = $(ele.element);
                    element.addClass(" cFormItem_iptError");
                    var parent = element.parent();
                    parent.find(".validPan").hide();
                    if(!element.hasClass("passwd") && !element.hasClass("regUserName") ) {
                        element.closest("dd").find(".errorPan").addClass("cError").html(ele.message).show();
                    }
                });

            },
            onkeyup: function(element) {
                var $ele = $(element);
                var keyCount = $ele.data("keyCount");
                if(!keyCount) {
                    keyCount = 0;
                }
                keyCount++;
                $ele.data("keyCount", keyCount);
                if ($ele.hasClass("validateOnKeyup")) {
                    this.element(element);
                    $ele.removeClass("cFormItem_iptError");
                }
            },
            onfocusout: function(element) {
                this.element(element);
            }
        });
    };


    var configCompanyRule = function(){
        var rule = {

            "account": {
                regUserName: [true,4],
                checkUsername: [usernameCheckUrl, "admin@"]
            },
            "name": {
                required: true,
                minlength: 1,
                maxlength:30
            },

            "password": {
                password: [true, 6, 14],
                maxlength:14
            },
            "passwordConfirm": {
                required: true,
                equalTo: "#passwordCompany"
            },
            "checkCode": {
                required: true,
                minlength:5,
                maxlength:5
                //remoteCheck:[codeCheckUrl]
            },
            "oRegCmpTradeIpt": {
                required: true
            },
            "oRegScaleIpt": {
                required: true
            },
            "contactName": {
                required: true
            },
            "contactPhone": {
                required: true,
                isPhone: true
            }
        };

        var errMsg = {
            "account": {
                required: "不能为空",
                userName: "请输入正确格式",
                checkUsername: "此用户已存在",
                minlength: "字符数不足",
                maxlength: "不能超过18个字符"
            },
            "name": {
                required: "不能为空",
                minlength: "字符数不足",
                maxlength: "不能超过30个字符"
            },

            "password": {
                required: "不能为空",
                //minlength: "字符数不足",
                maxlength: "不能超过14个字符"
            },
            "passwordConfirm": {
                required: "不能为空",
                equalTo: "两个密码不一致"
            },
            "checkCode": {
                required: "不能为空",
                remoteCheck:"请输入正确的验证码",
                minlength:"请输入正确的验证码",
                maxlength:"请输入正确的验证码"
            },
            "oRegCmpTradeIpt": {
                required: "不能为空"
            },
            "oRegScaleIpt": {
                required: "不能为空"
            },
            "contactName": {
                required: "不能为空"
            },
            "contactPhone": {
                required: "不能为空",
                isPhone: "请输入正确格式"
            }
        };
        /*if(!inviter) {
            rule["inviteCode"] = {
                required: true
            };
            errMsg["inviteCode"] = {
                required: "不能为空"
            };

        }*/
        regCompanyForm.validate({
            rules: rule,
            errorClass: "cFormItem_iptError",
            messages: errMsg,
            focusCleanup: true,
            showErrors:function(errorMap, errorList){
                $.each(this.successList, function(i, item) {
                    var element = $(item);
                    element.removeClass(" cFormItem_iptError");
                    element.closest("dd").find(".errorPan").hide();
                    if(!element.hasClass("passwd")){
                        element.parent().find(".validPan").show();
                    }
                });
                $.each(errorList, function(i, ele) {
                    var element = $(ele.element);
                    element.addClass(" cFormItem_iptError");
                    var parent = element.parent();
                    parent.find(".validPan").hide();
                    if(!element.hasClass("passwd") && !element.hasClass("regUserName") ) {
                        element.closest("dd").find(".errorPan").addClass("cError").html(ele.message).show();
                    }
                });

            },
            onkeyup: function(element) {
                var $ele = $(element);
                var keyCount = $ele.data("keyCount");
                if(!keyCount) {
                    keyCount = 0;
                }
                keyCount++;
                $ele.data("keyCount", keyCount);
                if ($ele.hasClass("validateOnKeyup")) {
                    this.element(element);
                    $ele.removeClass("cFormItem_iptError");
                }
            },
            onfocusout: function(element) {
                this.element(element);
            }
        });
    };
    var resize = function(){
	};
	
	return _construct();
}
