/**
 * os 消息model
 */

Namespace.register("com.gleasy.os.model");
com.gleasy.os.model.UserSettingModel = function(){
	var _this = this;
	
	var loadUrl = AjaxConfig.url.os + "/setting/load.json";//获取消息
	var saveUrl = AjaxConfig.url.os + "/setting/save.json";//获取消息
	
	var name = "com.gleasy.os.model.UserSettingModel";//Model的名称
	
	var interests = [
		Constants.command.os.setting.save,
		Constants.command.os.requireLogin,
		Constants.command.os.setting.load
	];//Model监听的消息IDs
	
	var settingCache = {};
	var settingTempList = [];
	var saving = false;
	var loading = false;
	var loadQueue = [];
	var saveQueue = [];
	
	
	/**
	 * 模拟构造函数
	 */
	var _construct = function(){
		//调用基类的方法
		_this.setInterests(interests);
	};
	
	
	/**
	 * Mvc必需方法
	 * 返回对象唯一标识字符串
	 * 
	 */
	this.getName = function(){	
		return name;
	};
	
	/**
	 * Mvc必需方法
	 * 初始化方法
	 */
	this.init = function(){
		$.log(this.getName()+" is initing.....");
	};
	
	/**
	 * Mvc必需方法
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		if(eventId == Constants.command.os.setting.load){
			loadSetting(messageBody);
		}else if(eventId == Constants.command.os.setting.save){
			saveSetting(messageBody);
		}else if(Constants.command.os.requireLogin == eventId){
			settingCache = {};
		}
	};
	
	var loadSetting = function(_param){
		var key = _param.key;
		var callback = _param.callback;
		if(settingCache[key]){
			callback && typeof callback == 'function' && callback(settingCache);
			return;
		}
		loadQueue.push(_param);
		doLoad();
	}
	
	var doLoad = function(){
		if(loading) return;
		loading = true;
		
		var tmp = loadQueue.splice(0);
		if(!tmp || !tmp.length) {
			loading = false;
			return;
		}
		
		var data = [];
		for(var i=0;i<tmp.length;i++){
			var tt = tmp[i];
			data[i] = tt.key;
		}
		var keys = data.join(",");
		
		AjaxConfig.ajax({
		   type: "GET",
		   url: loadUrl,
		   dataType:"json",
		   data: {
		   	keys:keys
		   },
		   success: function(ret){
			   	var values = ret.data;
			   	$.each(values,function(k,v){
			   		settingCache[k] = v;
			   	});			   	
			   	for(var i=0;i<tmp.length;i++){
					var tt = tmp[i];
					try{
						tt.callback && tt.callback(settingCache);
					}catch(e){
						$.log(e);
					}
				}
				loading = false;
				doLoad();
				return;
		   },
		   error:function(xhr){
		   		for(var i=0;i<tmp.length;i++){
					var tt = tmp[i];
					try{
						tt.callback && tt.callback(settingCache);
					}catch(e){
						$.log(e);
					}
				}
				loading = false;
				doLoad();
		   }
		});		
	}
	
	var saveSetting = function(_param){
		var k = _param.key;
		var v = _param.value;
		settingTempList.push({
			k:k,v:v
		});
		
		if(saving){
			return;
		}
		
		doSave();	
	}
	
	
	var doSave = function(){
		if(!settingTempList.length) return;
		saving = true;
		var copy = settingTempList.slice(0);
		settingTempList = [];
		var keys = values = "";
		for(var i=0;i<copy.length;i++){
			var o = copy[i];
			keys += o.k+",";
			values += o.v+",";
		}
		
		AjaxConfig.ajax({
		   type: "POST",
		   url: saveUrl,
		   dataType:"json",
		   data: {
		   	keys:keys,
		   	values:values
		   },
		   success: function(ret){
		   	 $.log("保存配置成功");
		   	 saving = false;
		   	 doSave();
		   },
		   error:function(xhr){
		   	saving = false;
		   }
		});		
	}
	_construct();
}

//继承MvcBase
com.gleasy.os.model.UserSettingModel.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本Model
window.application.addModel(new com.gleasy.os.model.UserSettingModel());
