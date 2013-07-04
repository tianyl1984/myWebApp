Namespace.register("com.gleasy.os.control");

/**
 * XMPP连接全局信息
 * @author xueke
 */
com.gleasy.os.control.ConnectionSession = {};
com.gleasy.os.control.ConnectionSession._jid = null;//当前连接的JID
com.gleasy.os.control.ConnectionSession._accountType = null;//当前连接的类型
com.gleasy.os.control.ConnectionSession._connected = false;//是否已经连接
com.gleasy.os.control.ConnectionSession._connecting = false;	//是否正在连接
com.gleasy.os.control.ConnectionSession._uid = null;	//当前连接的uid
com.gleasy.os.control.ConnectionSession._domain = null;
com.gleasy.os.control.ConnectionSession._server = null;
com.gleasy.os.control.ConnectionSession._presenceCache = {};

com.gleasy.os.control.ConnectionSession.setPresence = function(uid,presence){
	com.gleasy.os.control.ConnectionSession._presenceCache[uid] = presence;
}

com.gleasy.os.control.ConnectionSession.setServer = function(server){
	com.gleasy.os.control.ConnectionSession._server = server;
}

com.gleasy.os.control.ConnectionSession.getServer = function(){
	return com.gleasy.os.control.ConnectionSession._server;
}

com.gleasy.os.control.ConnectionSession.setJid = function(jid){
	com.gleasy.os.control.ConnectionSession._jid = jid;
}

com.gleasy.os.control.ConnectionSession.getJid = function(){
	return com.gleasy.os.control.ConnectionSession._jid;
}

com.gleasy.os.control.ConnectionSession.setConnected = function(param) {
	com.gleasy.os.control.ConnectionSession._connected = param;
}

com.gleasy.os.control.ConnectionSession.isConnected = function(){
	return com.gleasy.os.control.ConnectionSession._connected;
}

com.gleasy.os.control.ConnectionSession.setConnecting = function(param) {
	com.gleasy.os.control.ConnectionSession._connecting = param;
}

com.gleasy.os.control.ConnectionSession.isConnecting = function(){
	return com.gleasy.os.control.ConnectionSession._connecting;
}

com.gleasy.os.control.ConnectionSession.getUid = function(){
	return com.gleasy.os.control.ConnectionSession._uid;
}

com.gleasy.os.control.ConnectionSession.setUid = function(uid){
	return com.gleasy.os.control.ConnectionSession._uid = uid;
}

com.gleasy.os.control.ConnectionSession.getDomain = function(){
	return com.gleasy.os.control.ConnectionSession._domain;
}

com.gleasy.os.control.ConnectionSession.setDomain = function(domain){
	com.gleasy.os.control.ConnectionSession._domain = domain;
}
