Namespace.register("com.gleasy.os.login.model");

/**
 * 第三方应用管理Model
 * 1. 根据ID加载第三方应用
 * 2. 在本地缓存第三方应用
 * 3. 根据ID列表加载第三方应用
 * @author xueke
 */
com.gleasy.os.login.model.AccountModel = function(){
	var _this = this;
	var authUrl = AjaxConfig.url['sso']+"/gclientLogin";
	var keyAgreeUrl = AjaxConfig.url['sso']+"/secure/keyAgree.do";
	var individualRegisterUrl = AjaxConfig.url['ucenter']+"/user/register.json";
	var finishStaffInfoUrl = AjaxConfig.url['ucenter']+"/staff/register.json";
	var enterpriseRegisterUrl = AjaxConfig.url['ucenter']+"/company/register.json";
	var logoutUrl = AjaxConfig.url['sso']+"/gclientLogout";	
	
	var name = "com.gleasy.os.login.model.AccountModel";//Model的名称
	var interests = [
					Constants.command.os.account.auth,
					Constants.command.os.account.regIndividual,
					Constants.command.os.account.regEnterprise,
					Constants.command.os.account.finishStaffInfo,
					Constants.command.os.account.logout
				];//Model监听的消息IDs
	
	/**
	 * 模拟构造函数
	 */
	var _construct = function(){
		_this.setInterests(interests);
	}
	
	var _loadData = function(url,params,callback ,error){
		AjaxConfig.ajax({
		   type: "POST",
		   url: url,
		   contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		   type:'POST',
           dataType:'json',
		   data: params,
		   success: callback,
		   error:error
		});
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
		$.log(this.getName()+" is initing.....");
		var _this = this;
	}
	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		if(eventId == Constants.command.os.account.auth){
			auth(messageBody);
		}else if(eventId == Constants.command.os.account.regIndividual){
			regIndividual(messageBody);
		}else if(eventId == Constants.command.os.account.regEnterprise){
			regEnterprise(messageBody);
		}else if(eventId == Constants.command.os.account.logout){
			logout(messageBody);
		}else if(eventId == Constants.command.os.account.finishStaffInfo){
			finishStaffInfo(messageBody);
		}
	}
	
	

	
	var loadSecureSuit = function(callback){
	   $LAB
	   .script(Constants.config.assetPrefix+"/script/libs/securesuit/base64.js")
	   .script(Constants.config.assetPrefix+"/script/libs/securesuit/bigint.js")
	   .script(Constants.config.assetPrefix+"/script/libs/securesuit/xxtea.js")	
	   .script(Constants.config.assetPrefix+"/script/libs/securesuit/DH.class.js")		
	   .script(Constants.config.assetPrefix+"/script/libs/securesuit/Url.js")		
	   .script(Constants.config.assetPrefix+"/script/libs/securesuit/SessionKey.class.js")	
	   .script(Constants.config.assetPrefix+"/script/libs/securesuit/SecureSuit.class.js")		
	   .wait(function(){
	   		com.gleasy.libs.securesuit.SecureSuit.sessionKeyAgree({
				keyAgreeUrl:keyAgreeUrl,
				success:function(sessionKey){
					callback && typeof callback == 'function' && callback(sessionKey);
				},
				fail:function(reason){
					$.log("密钥协商失败");	
				}
			});
	   });
	}
	
	var auth = function(param){
		/*loadSecureSuit(function(){
			var cipher = com.gleasy.libs.securesuit.SecureSuit.sessionKeyEncrypt(param.password);	
			var data = {
				account:param.account,
				checkCode:param.verifyCode,
				psd:param.password,
				type:'os'
			};
			_loadData(authUrl,data,param.success,param.error);
		});*/
        window.application.sendNotify(Constants.command.os.process.sendMessage,{
            pname:"安全卫士",
            noneedlogin:true,
            eventId:"encrypt.sessionkey",
            messageBody:{
                value:param.password,
                callback:function (dt) {
                    var data = {
                        account:param.account,
                        checkCode:param.verifyCode,
                        psd:dt,
                        type:'os'
                    };
                    _loadData(authUrl,data,param.success,param.error);
                }
            }
        });
	}

	var finishStaffInfo = function(param){
			/*loadSecureSuit(function(){
			_loadData(finishStaffInfoUrl,param.data,param.success,param.error);
		});*/
        window.application.sendNotify(Constants.command.os.process.sendMessage,{
            pname:"安全卫士",
            eventId:"encrypt.sessionkey",
            messageBody:{
                value:param.data.password,
                callback:function (dt) {
                    param.data.password = dt;
                    _loadData(finishStaffInfoUrl,param.data,param.success,param.error);
                }
            }
        });
	}
	
	var regIndividual = function(param){
		/*loadSecureSuit(function(){
			_loadData(individualRegisterUrl,param.data,param.success,param.error);
		});*/

        window.application.sendNotify(Constants.command.os.process.sendMessage,{
            pname:"安全卫士",
            eventId:"encrypt.sessionkey",
            noneedlogin:true,
            messageBody:{
                value:param.data.password,
                callback:function (dt) {
                    param.data.password = dt;
                    _loadData(individualRegisterUrl,param.data,param.success,param.error);
                }
            }
        });
	}

	var regEnterprise = function(param){
		/*loadSecureSuit(function(){
			_loadData(enterpriseRegisterUrl,param.data,param.success,param.error);
		});*/

        window.application.sendNotify(Constants.command.os.process.sendMessage,{
            pname:"安全卫士",
            noneedlogin:true,
            eventId:"encrypt.sessionkey",
            messageBody:{
                value:param.data.password,
                callback:function (dt) {
                    param.data.password = dt;
                    _loadData(enterpriseRegisterUrl,param.data,param.success,param.error);
                }
            }
        });
	}

	var logout = function(param){
		var data = {};
		if(param.data){
			data = param.data;
		}
		_loadData(logoutUrl,data,param.success,param.error);
	}

	_construct();
}

//继承MvcBase
com.gleasy.os.login.model.AccountModel.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本Model
window.application.addModel(new com.gleasy.os.login.model.AccountModel());
