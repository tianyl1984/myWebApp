/**
 *　XMPP连接代理
*/

Namespace.register("com.gleasy.os.control");

com.gleasy.os.control.LongConnectionProxy = function(){
	var _this = this;

	var name = "com.gleasy.os.control.LongConnectionProxy";
	var interests = [
					Constants.command.im.connect,
					Constants.command.im.DirectConnect,
					Constants.command.im.send,
					Constants.command.im.disconnect,
					Constants.command.im.data.receive,
					Constants.command.im.data.send,
					Constants.command.os.loginSuccess,
					Constants.command.im.connection.disconnected,
					Constants.command.im.connection.connectfail,
					Constants.command.im.loginFail,
					Constants.command.im.data.message,
					Constants.command.im.data.presence,
					Constants.command.os.requireLogin,
					Constants.command.im.sessionStart
				];//Control监听的消息IDs
	var connection = null;
	var finishResource = false;
	var retryCount = 0;
	
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
		$.log(this.getName()+" is initing.....");
		if(!finishResource){
		   var connectionScript = Constants.config.assetPrefix+"/script/common/im/ImConnectionWS.class.js";
		   if (!window.WebSocket && !window.MozWebSocket) {
				//如果不支持Websocket
				connectionScript = Constants.config.assetPrefix+"/script/common/im/ImConnectionFlash.class.js";
		   }
		   $LAB
		    .script(Constants.config.assetPrefix+"/script/common/SwfCaller.class.js")
		    .script(Constants.config.assetPrefix+"/script/jquery/jquery.swfobject.1-1-1.min.js")
		    .script(Constants.config.assetPrefix+"/script/strophe/strophe.js")
		    .script(Constants.config.assetPrefix+"/script/strophe/strophe-openfire.js")
			.script(connectionScript)
			.script(Constants.config.assetPrefix+"/script/common/im/ImMessageHandler.class.js")
			.script(Constants.config.assetPrefix+"/script/common/im/ImPresenceHandler.class.js")
			.wait(function(){
				//向application中注册本Control
				connection = new com.gleasy.common.im.ImConnection({brokerUrl:AjaxConfig.url['cloudIm'],application:_this.application});
				_this.application.addMvc(connection);
				
				$(window).unload(function(){
				  connection && connection.disconnect();
				});
		
				var mh = new com.gleasy.common.im.ImMessageHandler();
				mh.setApplication(_this.application);
				_this.application.addMvc(mh);
				
				var ph = new com.gleasy.common.im.ImPresenceHandler();
				ph.setApplication(_this.application);
				_this.application.addMvc(ph);
				
				finishResource = true;
				
				_this.application.runMvc(_this);
			});
		}
		
		if(!finishResource){
			return true;
		}
		
		return false;
	}

	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		$.log(this.getName()+" 收到消息 "+eventId);
		if(eventId == Constants.command.im.send){
			if(!com.gleasy.os.control.ConnectionSession.isConnected()){
				$.log("连接未建立，忽略发送请求");
				return;
			}
			connection.send(messageBody);
		}else if(eventId == Constants.command.im.data.receive){
			$.log("收到:"+messageBody);
		}else if(eventId == Constants.command.im.data.send){
			$.log("发出:"+messageBody);
		}else if(eventId == Constants.command.im.sessionStart){
			$.log("已经连接上啦");
			retryCount = 0;
			com.gleasy.os.control.ConnectionSession.setJid(messageBody.jid);
			com.gleasy.os.control.ConnectionSession.setDomain(messageBody.domain);
			com.gleasy.os.control.ConnectionSession.setConnected(true);
			com.gleasy.os.control.ConnectionSession.setConnecting(false);
		}else if(eventId == Constants.command.im.connection.disconnected){
			$.log("断了线哦..");
			com.gleasy.os.control.ConnectionSession.setConnecting(false);
			com.gleasy.os.control.ConnectionSession.setConnected(false);
			
			reconnect();
		}else if(eventId == Constants.command.im.connection.connectfail){
			$.log("连接失败了哦..");
			com.gleasy.os.control.ConnectionSession.setConnecting(false);
			com.gleasy.os.control.ConnectionSession.setConnected(false);
			
			reconnect();
		}else if(eventId == Constants.command.im.loginFail){
			$.log("登录失败了哦..");
			com.gleasy.os.control.ConnectionSession.setConnecting(false);
			com.gleasy.os.control.ConnectionSession.setConnected(false);
			
			reconnect();
		}else if(eventId == Constants.command.im.data.presence){
			var from = messageBody.from;
			var uid = Strophe.getNodeFromJid(from);
			com.gleasy.os.control.ConnectionSession.setPresence(uid,messageBody);
		}else if(eventId == Constants.command.im.data.message){
			onMessage(messageBody);
		}else if(Constants.command.os.loginSuccess == eventId){
			retryCount = 0;
			connect();
		}else if(Constants.command.os.requireLogin == eventId){
			disconnect();
		}
	}

	var onMessage = function(message){
		var from = message.from;
		var to = message.to;
		var type = message.type;
		var body = message.body;
		var subject = message.subject;

		if("im-message" == subject){
			handleImMessage(message);
		}
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
		
	var handleImMessage = function(message){
		var body = message.body;
		body = body.replace(/\+/g,"%20");
		body = decodeURIComponent(body);
		var param = $.evalJSON(body);
		var event = param.event;
		var messageBody = param.messageBody;
		var app  = param.app;
		
		if(!app || !event ){
			$.log("无效的im-message消息,message:"+message);
			return;
		}
		if(true == com.gleasy.os.control.ConnectionSession.isConnecting() || true != com.gleasy.os.control.ConnectionSession.isConnected()){
			$.log("186断线中,忽略连接请求");
			return;
		}
		if("osCore" == app){
			window.application.sendNotify(event,messageBody);
		}else{
			var process = findProcessByPname(app);
			if(process){
				window.application.sendNotify(Constants.command.os.process.sendMessage,{
									pname:app,
									eventId:event,
									messageBody:messageBody
				});	
			}else{
				$.log("im-message的目标应用"+app+"没有启动");
			}
		}
	}
	
	var reconnect = function(){
		retryCount++;
		if(retryCount == 15){
			//重试次数爆了
			window.application.sendNotify(Constants.command.os.repeatLogin,{msg:"连接服务器失败"});	
			return;
		}
		
		if(retryCount > 15){
			return;
		}
		var interval = retryCount;
		if(interval > 10){
			interval = 10;
		}
		setTimeout(function(){
			connect();
		},interval*1000);
	}
	
	var connect = function(){
		if(true == com.gleasy.os.control.ConnectionSession.isConnecting() || true == com.gleasy.os.control.ConnectionSession.isConnected()){
			$.log("正在或已经连接上了，忽略连接请求");
			return;
		}
		if(!com.gleasy.os.SystemInfo.login){
			$.log("没有登录,忽略连接请求");
			return;
		}
		com.gleasy.os.control.ConnectionSession.setConnecting(true);
		connection.connect();
	}
	
	var disconnect = function(){
		connection.disconnect();	
	}
	
	
	_construct();	
}

//继承MvcBase
com.gleasy.os.control.LongConnectionProxy.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本mvc
window.application.addMvc(new com.gleasy.os.control.LongConnectionProxy());
