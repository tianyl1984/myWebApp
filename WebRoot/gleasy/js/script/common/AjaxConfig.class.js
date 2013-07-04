/**
 * 
 *全局对象，用于Ajax数据访问路径 
 */
 
var host = window.location.protocol+"//"+window.location.host;
AjaxConfig = {};
AjaxConfig.downloadPrefix = "http://download.gleasy.com";
AjaxConfig.uploadPrefix = "http://upload.gleasy.com";
AjaxConfig.url = {};
AjaxConfig.url['os'] = host+"/os";
AjaxConfig.url['is'] = "http://www.gleasy.com/gsheet";
AjaxConfig.url['im'] = host+"/GTalkWeb";
AjaxConfig.url['imDL'] = AjaxConfig.downloadPrefix+"/GTalkWeb";
// AjaxConfig.url['im'] = "http://im.dev.gleasy.com:8082/GTalkWeb";
// AjaxConfig.url['im'] = "http://shaojiahao.dev.gleasy.com:8080";
AjaxConfig.url['ucenter'] = host+"/ucenter";
AjaxConfig.url['appCenter'] = host+"/appCenterUserWeb";
AjaxConfig.url['provider'] = host+"/provider";

AjaxConfig.url['sso'] = host+"/auth";
AjaxConfig.url['cc'] = host+"/ctrlCenter";
AjaxConfig.url['uiCommon'] = host+"/uiCommon";
AjaxConfig.url['wb'] = host+"/wb";
AjaxConfig.url['wbUpload'] = AjaxConfig.uploadPrefix+"/wb";
AjaxConfig.url['wbDownload'] = AjaxConfig.downloadPrefix+"/wb";
AjaxConfig.url['cp'] = AjaxConfig.url['ucenter'];
AjaxConfig.url['contact'] = host+"/person";
AjaxConfig.url['gcd'] = host+"/gcd";
AjaxConfig.url['gcd-webopen'] = host+"/gcd-webopen";
AjaxConfig.url['download'] = AjaxConfig.downloadPrefix+"/gcd";
AjaxConfig.url['upload'] = AjaxConfig.downloadPrefix+"/gcd";

//AjaxConfig.url['gcd'] = "http://www.gleasy.com/gcd";
//AjaxConfig.url['gcd-webopen'] = "http://www.gleasy.com/gcd-webopen";

AjaxConfig.url['systemCircle'] = host+"/systemCircle";
AjaxConfig.url['ge'] = host+"/editor";

AjaxConfig.url['cloudBrowser'] = host+"/cloudBrowser-web";
AjaxConfig.url['cloudBrowserDL'] = AjaxConfig.downloadPrefix+"/cloudBrowser-web";
// AjaxConfig.url['cloudBrowser'] = "http://shaojiahao.dev.gleasy.com:8085";
// AjaxConfig.url['cloudBrowserDL'] = "http://shaojiahao.dev.gleasy.com:8085";

AjaxConfig.url['osCore'] = host+"/os";
AjaxConfig.url['notes'] = "http://www.gleasy.com/notes";
AjaxConfig.url['helpCenter'] = host+"/helpCenter";


AjaxConfig.url['fconv'] = host+"/fconv-web";

//@Deprecated(统一使用AjaxConfig.url['gcd'])
AjaxConfig.url['cloudDisk'] = host+"/gcd";
//@Deprecated(统一使用AjaxConfig.url['contact'])
AjaxConfig.url['person'] = host+"/person";

if(host.indexOf("cn") > -1){
	AjaxConfig.url['cloudIm'] = "http://im.dev.gleasy.cn:9090/plugins/cloud-im";
}else{
	AjaxConfig.url['cloudIm'] = "http://im.dev.gleasy.com:9090/plugins/cloud-im";
}

AjaxConfig.filter = [];

AjaxConfig.addFilter = function(fn){
	AjaxConfig.filter.push(fn);
}
AjaxConfig.gsession = null;
AjaxConfig.gsessionloading = false;
AjaxConfig.waitqueue = [];
AjaxConfig.sessionname = "GSESSIONID";
 
 
AjaxConfig.getBase64Image = function(img) {
    var canvas = document.createElement("canvas");
    canvas.width = img.width;
    canvas.height = img.height;
    var ctx = canvas.getContext("2d");
    ctx.drawImage(img, 0, 0);
    var dt = canvas.toDataURL("image/jpeg");
	dt = dt.substring(23);
   	return dt;
}

AjaxConfig.doajax = function(options){
	if(options.type == 'image'){
		var image = new Image();
		image.onload = function(){
			try{
				var ct = AjaxConfig.getBase64Image(image);
				options.success && options.success(ct);
			}catch(e){
				options.error && options.error();
			}
		}
		image.src = options.url;
	}else{
		$.ajax(options);
	}
}

AjaxConfig._ajax = function(options){
	if(!options.type){
		options.type = 'POST';
	}
	var urlm = options.url;
	
	if(options.type != 'image' &&(urlm.indexOf(".html")>0  ||  urlm.indexOf(".htm")>0)){
		var ttuuu = urlm;
		var idx = ttuuu.indexOf("?");
		if(idx > -1){
			ttuuu = ttuuu.substring(0,idx);
		}
		ttuuu = ttuuu + "?v="+ResourceConfig.version;
		options.url = ttuuu;
	}
	
	if((options.type == 'POST' || options.type == 'post') && !options.contentType){
		options.contentType =  "application/x-www-form-urlencoded; charset=utf-8";
	}
	
	if(!options.contentType){
		options.contentType = "application/x-www-form-urlencoded";
	}
	
	var qindex = urlm.indexOf("?");
	if(qindex >= 0){
		urlm = urlm.substring(0, qindex);
	}
    if(options.dataType == "json" || options.type.toUpperCase() == 'POST' ) {//hzt:如果是post, 逻辑上也不用缓存
        if(qindex >= 0){
            options.url += "&rand="+Math.random();
        }else{
            options.url += "?rand="+Math.random();
        }
    }
	
	var p = new Poly9.URLParser(location.href);
	var host = p.getHost();
	var port = p.getPort();
	
	var p1 = new Poly9.URLParser(urlm);
	if(p1.getHost() == host && p1.getPort() == port){
		AjaxConfig.doajax(options);
	}else if(typeof window.postMessage == 'function' || typeof window.postMessage == 'object'){
		var data = options.data;
		var GSESSIONID = $.cookie(AjaxConfig.sessionname);
		var loginTicket = $.cookie('loginTicket');
		if(typeof data === 'object'){
			data[AjaxConfig.sessionname] = GSESSIONID;
			data.loginTicket = loginTicket;
		}
		AjaxCallerHtml5.ajax(options);	
	}else{
		//$.log("使用Gajax进行调用"+options.url);
		var data = options.data;
		if(!data){
			options.data = data = {};
		}
		var GSESSIONID = $.cookie(AjaxConfig.sessionname);
		var loginTicket = $.cookie('loginTicket');

		if(typeof data === 'object'){
			data[AjaxConfig.sessionname] = GSESSIONID;
			data.loginTicket = loginTicket;
		}
		AjaxCallerFlash.ajax(options);
	}
}

/**
 * Ajax代理方法
 */
AjaxConfig.ajax = function(options){
	if(!options.ignoreFilter){
		$.each(AjaxConfig.filter,function(idx,fn){
			fn && typeof fn == 'function' && fn();
		});
	}
	options.errorCount = 0;
	var error = options.error;
	
	
	if((options.type =='post'||options.type =='POST') && AjaxConfig.gsession == null){
		if(AjaxConfig.gsessionloading){
			AjaxConfig.waitqueue.push(options);
			return;
		}
		AjaxConfig.gsessionloading = true;
		var success = options.success;
		options.success = function(dt){
			success && success(dt);	
			AjaxConfig.gsessionloading = false;
			var GSESSIONID = $.cookie(AjaxConfig.sessionname);
			GSESSIONID && (AjaxConfig.gsession=GSESSIONID);
			var len = AjaxConfig.waitqueue.length;
			for(var i=0;i<len;i++){
				var tmp = AjaxConfig.waitqueue[i];
				AjaxConfig.ajax(tmp);
			}
			AjaxConfig.waitqueue=[];
		}
	}
	
	options.error = function(e){
		options.errorCount ++;
		if(e && e.status == 0 && e.readyState == 4 && options.errorCount <= 5){
			$.log("开始重试第"+options.errorCount+"次");
			setTimeout(function(){
				AjaxConfig._ajax(options);
			},1000);
		}else{
			error && typeof error == 'function' && error(e);
		}
	};
	AjaxConfig._ajax(options);
}

AjaxConfig.load = function(dom,url,callback){
	if(!url) return;
	AjaxConfig.ajax({
		url:url,
		type:'GET',
		dataType:'html',
		success:function(result){
			dom.html(result);
			typeof callback=='function' && callback();
		}
	});	
}

AjaxConfig.loadData = function(url, params, success, error, token) {
	AjaxConfig.ajax({
		type : "POST",
		url : url,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		data : params,
		success : function(result) {
			if (result && result.status == 0) {
				success && typeof success == 'function' && success(result.data, token);
			} else {
				error && typeof error == 'function' && error({
					status : result.status,
					description : result.description
				}, token);
			}
		},
		error : function() {
			error && typeof error == 'function' && error({
				status : -1
			}, token);
		}
	});
}

AjaxConfig.buildField = function(param){
	var CRLF  = "\r\n";
	var part = "";
	if(param.type == 'txtfile'){
        part += 'Content-Disposition: form-data; ';
        part += 'name="' + param.name + '"; ';
        part += 'filename="'+ param.filename + '"' + CRLF;
        part += "Content-Type: text/plain" + CRLF + CRLF;
        part += param.content + CRLF;
	}else if(param.type == 'binfile'){
		part += 'Content-Disposition: form-data; ';
        part += 'name="' + param.field + '"; ';
        part += 'filename="'+ param.name + '"' + CRLF;
        part += "Content-Type: application/octet-stream" + CRLF + CRLF;
        part += param.content + CRLF;
	}else{
		part += 'Content-Disposition: form-data; ';
        part += 'name="' + param.name + '"' + CRLF + CRLF;
        part += param.value + CRLF;
	}
	return part;
}
AjaxConfig.buildform = function(boundary,params){
	var CRLF  = "\r\n";
 	var parts = [];
	$.each(params,function(idx,param){
		parts.push(AjaxConfig.buildField(param));
	});
    var request = "--" + boundary + CRLF;
        request+= parts.join("--" + boundary + CRLF);
        request+= "--" + boundary + "--" + CRLF;
    return request;
}

AjaxConfig.multiAjax = function(options){
	var file = options.file;
	var data = options.data;
	var params = [];
	$.each(data,function(key,value){
		params.push({
			name:key,
			value:value
		});
	});
	params.push(file);
	var boundary = "---------------------------" + (new Date).getTime();
	$.extend(options,{
		type : "POST",
		contentType :'multipart/form-data; charset=utf-8; boundary=' + boundary,
		dataType : "json",
		data : AjaxConfig.buildform(boundary,params)
	});
	AjaxConfig.ajax(options);
}

AjaxConfig.multiAjax2 = function(options){
	AjaxConfig.multiAjax({
		type : "POST",
		url : options.url,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		data : options.data,
		file: options.file,
		success : function(result) {
			if (result && result.status == 0) {
				options.success && typeof options.success == 'function' && options.success(result.data, options.token);
			} else {
				options.error && typeof options.error == 'function' && options.error({
					status : result.status,
					description : result.description
				}, options.token);
			}
		},
		error : function() {
			options.error && typeof options.error == 'function' && options.error({
				status : -1
			}, options.token);
		}
	});
}
