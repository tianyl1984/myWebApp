
Namespace.register("com.gleasy.os.login.view");
com.gleasy.os.login.view.AccountView = function(){
    var _this = this;
    var getInviterUrl = AjaxConfig.url['cp']+"/inviter/get.json";
    var checkStaffExpireUrl = AjaxConfig.url['ucenter']+"/staffExpire/check.json";
    var view = null;//本类所操作的目标界面元素对象
    var name = "com.gleasy.os.login.view.AccountView";//View的名称
    var interests = ['inviteReg',Constants.command.os.loginSuccess,Constants.command.os.notSupportBrowser];//View监听的消息IDs

    var showReg = false;
    var checkCapacityStatusing = false;
    var checkStaffExpiring = false;
    /**
     * 模拟构造函数
     */
    var _construct = function(){
        //调用基类的方法
        _this.setInterests(interests);
    }

    /**
     * Mvc必需方法
     * 返回对象唯一标识字符串
     *
     */
    this.getName = function(){
        return name;
    }

    /**
     * Mvc必需方法
     * 初始化方法
     */
    this.init = function(){
        $.log(this.getName()+" is initing.....");
    }

    /**
     * Mvc必需方法
     * 接受消息，并且处理
     */
    this.notify = function(eventId,messageBody){
        $.log(this.getName()+"收到消息："+eventId);
        if(eventId == "inviteReg"){
            inviteReg(messageBody.inviter);
        }else if(eventId == Constants.command.os.loginSuccess){
            try {
                checkCapacityStatus();
            } catch (e) {
                $.log(e);
            }
            try {
                checkStaffExpire();
            } catch (e) {
                $.log(e);
            }
            reloadBackground();//登录成功后获取自定义壁纸
        }else if(eventId == Constants.command.os.notSupportBrowser){
            notSupportBrowser();
        }

    };

    var inviteReg = function(inviter){
        if(!inviter) {
            return;
        }
        if(showReg || com.gleasy.os.SystemInfo.login) {
            return;
        }
        showReg = true;
        window.application.sendNotify(Constants.command.os.loadDefaultWallpaper);
        AjaxConfig.ajax({
            url:AjaxConfig.url.sso + "/beatheart/check",
            dataType:"json",
            ignoreFilter:true,
            data:{},
            success:function (ret) {
                if(ret && ret.status == 3) {

                    /*var p = {
                        container:$(document.body),
                        panel:"register",
                        paramEx: {inviter:inviter}
                    };
                    var dialog = new com.gleasy.os.login.view.SystemLockContainer(p);
                    dialog.setVisible(true);*/

                    getInviter(inviter);
                }else{
                    window.application.sendNotify(Constants.command.os.fireHeartBeat);
                }
            }
        });

    };

    var getInviter = function(inviter) {
        var param = {
            inviter: inviter
        };
        AjaxConfig.loadData(getInviterUrl, param, function(data) {
            var name = data.name;
            if (name) {
                var p = {
                    container:$(document.body),
                    panel:"register",
                    paramEx: {inviter:inviter, inviterName: name}
                };
                var dialog = new com.gleasy.os.login.view.SystemLockContainer(p);
                dialog.setVisible(true);
            }else{
                window.application.sendNotify(Constants.command.os.fireHeartBeat);
            }
        });
    };

    var checkCapacityStatus = function(){

        if(checkCapacityStatusing || !com.gleasy.os.SystemInfo.login) {
            return;
        }
        checkCapacityStatusing = true;
        AjaxConfig.ajax({
            url:AjaxConfig.url.cc + "/capacityStatus/get.json",
            dataType:"json",
            ignoreFilter:true,
            data:{},
            success:function (ret) {
                checkCapacityStatusing = false;
                if(!ret || !ret.data) {
                    return;
                }
                var capacityStatus = ret.data.capacityStatus;
                if(!capacityStatus || capacityStatus == "enough"){
                    return;
                }
                $LAB
                    .script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/capacityManager/view/CapacityHandleView.class.js")
                    .wait(function(){
                        var p = {
                            //container:$(document.body)
                        };
                        $.extend(p, ret.data);
                        var view = new com.gleasy.ctrlPanel.capacityManager.view.CapacityHandleView(p);
                        view.setVisible(true);
                    });

                /*window.application.sendNotify(Constants.command.os.process.runProgram, {
                    pname:Constants.app.CapacityHandleView,
                    args:ret.data
                });*/


            },error: function() {
                checkCapacityStatusing = false;
            }
        });

    };
    var checkStaffExpire = function(){

        if(checkStaffExpiring || !com.gleasy.os.SystemInfo.login) {
            return;
        }
        checkStaffExpiring = true;
        AjaxConfig.ajax({
            url:checkStaffExpireUrl,
            dataType:"json",
            ignoreFilter:true,
            data:{},
            success:function (ret) {
                checkStaffExpiring = false;
                if(!ret || !ret.data || !ret.data.expiredDate) {
                    return;
                }
                var expiredDate = ret.data.expiredDate;
                var message = "您的帐号将于"+expiredDate+"到期，<br/>请尽快联系管理员";
                var _param = {
                    message:message,
                    //confirmMessage:"确定取消验证吗?",
                    system:true,//是否系统提醒
                    confirm:false,
                    title:"提示",
                    icon:"fail",//icon={confirm,fail,success,prompt,warning}
                    okBtn:true,
                    okBtnText:"关闭",
                    cancelBtn:false,
                    cancelBtnText:"取消",
                    callback:function(dt){
                    }
                };
                window.application.sendNotify(Constants.command.system.alert,_param);

            },
            error:function() {
                checkStaffExpiring = false;
            }
        });

    };

    var reloadBackground = function() {
        window.application.sendNotify(Constants.command.os.process.sendMessage, {
            pname: Constants.pname.background.daemon,
            eventId: Constants.command.ctrlPanel.background.refresh,
            messageBody: {}
        });
    };

    var notSupportBrowser = function(){
        window.application.sendNotify(Constants.command.os.loadDefaultWallpaper);
        var p = {
            container:$(document.body),
            panel:"notSupportBrowser"
        };
        var dialog = new com.gleasy.os.login.view.SystemLockContainer(p);
        dialog.setVisible(true);
    };



    _construct();
}

//继承MvcBase
com.gleasy.os.login.view.AccountView.prototype = new com.gleasy.libs.MvcBase();

window.application.addView(new com.gleasy.os.login.view.AccountView());

