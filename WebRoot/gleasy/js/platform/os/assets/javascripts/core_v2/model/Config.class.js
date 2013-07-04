Config = {};
Config.loadUrl = AjaxConfig.url['os']+"/config/load.json";

Config.cache = {};


Config.batchLoad = function(keys,callback){
	if(!keys) return;
	if(keys.length<=0) return;
	
	var unMatchKeys = [];
	for(var i=0;i<keys.length;i++){
		var k = keys[i];
		var v = Config.cache[k];
		if(!v){
			unMatchKeys.push(k);
		}
	}
	if(!unMatchKeys.length){
		callback && typeof callback == 'function' && callback(Config.cache);
		return;	
	}
	Config.loadData(unMatchKeys,function(result){
 		if(result.status == 0){
		 	for(var i=0;i<unMatchKeys.length;i++){
				var k = unMatchKeys[i];
				var v = result.data[k];
				if(v){
					Config.cache[k] = v;
				}
			}
	   	 	callback && typeof callback == 'function' && callback(Config.cache);
	   	 	return;
	   	 }		
	});
}

Config.loadData = function(keys,success,error){
	if(!keys || keys.length<=0) return;
	AjaxConfig.ajax({
	   type: "POST",
	   url: Config.loadUrl,
	   dataType:"json",
	   data: {keys:keys.join(",")},
	   success: function(result){
	   	success && typeof success == 'function' && success(result);
	   },
	   error:function(){
	   	$.log("Config.class.js 33 加载配置失败了");
	   	error && typeof error == 'function' && error(result);
	   }
	});	
}

/**
 * 直接获取配置
 * 仅用于配置已经确保在缓存中的情况
 * 比如外部下通过
 * Config.batchLoad([key1,key2],function(){
 * 	 var v1 = Config.directGet('key1');
 * });
 */
Config.directGet = function(key){
	var v = Config.cache[key];
	return v;
}


/**
 * 获取配置的值，异步调用，不会返回结果
 */
Config.get = function(key,callback){
	var v = Config.cache[key];
	if(v){
		callback && typeof callback == 'function' && callback(v);
		return;
	}
	Config.loadData([key],function(){
 		if(result.status == 0){
	   	 	var v = result.data[key];
	   	 	Config.cache[key] = v;
	   	 	callback && typeof callback == 'function' && callback(v);
	   	 	return;
	   	 }		
	});
}
