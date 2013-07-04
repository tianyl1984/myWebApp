/********************
	参数：str:原始字符串
	      n:需要返回的长度，汉字=2
	返回值：处理后的字符串
********************/
function strEllip(str,n){
		var ilen = str.length;
		if(ilen*2 <= n) return str;
		n -= 3;
		var i = 0;
		while(i < ilen && n > 0)
		{
		if(escape(str.charAt(i)).length>4) n--;
		n--;
		i++;
		}
		if( n > 0) return str;
		return str.substring(0,i)+"...";
}

function getFileName(path){
	if(!path) return "";
	var name = path.substr(path.lastIndexOf('/')+1);
	if(!name){
		name = path.substr(path.lastIndexOf('\\')+1);
	}
	return name;
}

function getFileExt(obj){
	//var result = obj.match(/^.*\.([a-zA-Z0-9]+)$/)[1];
	//return "."+result;
	if(obj){
		var index = obj.lastIndexOf(".");
		if(index>0){
			return obj.substring(index);
		}	
	}
	return "";
}

var getCurrentTimeMills = function(){
	var date=new Date(); 
	return date.getTime();
}

var trim = function(ori){
	if(!ori || ori == null){
		return '';
	}
	return ori.replace(/(^\s*)|(\s*$)/g, "");	
}

/**
 * 转换成友好的文件大小
 * @param {} bytes
 * @return {}
 */
function readablizeBytes(bytes) {
	var s = ['bytes', 'KB', 'MB', 'GB', 'TB', 'PB'];
	var e = Math.floor(Math.log(bytes)/Math.log(1024));
	return (bytes/Math.pow(1024, Math.floor(e))).toFixed(2)+" "+s[e];
}



(!window.cssCache) && (window.cssCache = []);
var loadCss = function(c){
	var idx = c.indexOf("?");
	if(idx > -1){
		c = c.substring(0,idx);
	}
	c = c + "?v="+ResourceConfig.version;
		
	var index = $.inArray(c,window.cssCache);
	if(index >= 0) return;
	window.cssCache.push(c);
	var b=document.getElementsByTagName("head")[0],
	a=document.createElement("link");
	a.rel="stylesheet";
	a.href=c;
	b.appendChild(a);
}



// 更新：
// 05.27: 1、保证回调执行顺序：error > ready > load；2、回调函数this指向img本身
// 04-02: 1、增加图片完全加载后的回调 2、提高性能
/**
 * 图片头数据加载就绪事件 - 更快获取图片尺寸
 * @version  2011.05.27
 * @author  TangBin
 * @see    http://www.planeart.cn/?p=1121
 * @param  {String}  图片路径
 * @param  {Function}  尺寸就绪
 * @param  {Function}  加载完毕 (可选)
 * @param  {Function}  加载错误 (可选)
 * @example imgReady('http://www.google.com.hk/intl/zh-CN/images/logo_cn.png', function () {
    alert('size ready: width=' + this.width + '; height=' + this.height);
  });
 */
var imgReady = (function () {
  var list = [], intervalId = null,

  // 用来执行队列
  tick = function () {
    var i = 0;
    for (; i < list.length; i++) {
      list[i].end ? list.splice(i--, 1) : list[i]();
    };
    !list.length && stop();
  },

  // 停止所有定时器队列
  stop = function () {
    clearInterval(intervalId);
    intervalId = null;
  };

  return function (url, ready, load, error) {
    var onready, width, height, newWidth, newHeight,
      img = new Image();

    img.src = url;

    // 如果图片被缓存，则直接返回缓存数据
    if (img.complete) {
      ready.call(img);
      load && load.call(img);
      return;
    };

    width = img.width;
    height = img.height;

    // 加载错误后的事件
    img.onerror = function () {
      error && error.call(img);
      onready.end = true;
      img = img.onload = img.onerror = null;
    };

    // 图片尺寸就绪
    onready = function () {
      newWidth = img.width;
      newHeight = img.height;
      if (newWidth !== width || newHeight !== height ||
        // 如果图片已经在其他地方加载可使用面积检测
        newWidth * newHeight > 1024
      ) {
        ready.call(img);
        onready.end = true;
      };
    };
    onready();

    // 完全加载完毕的事件
    img.onload = function () {
      // onload在定时器时间差范围内可能比onready快
      // 这里进行检查并保证onready优先执行
      !onready.end && onready();

      load && load.call(img);

      // IE gif动画会循环执行onload，置空onload即可
      img = img.onload = img.onerror = null;
    };

    // 加入队列中定期执行
    if (!onready.end) {
      list.push(onready);
      // 无论何时只允许出现一个定时器，减少浏览器性能损耗
      if (intervalId === null) intervalId = setInterval(tick, 40);
    };
  };
})();

var imageSizeAdjust = function(width,height,previewUrl,callback){
	imgReady(previewUrl, function () {
		var w =  this.width;//图片的宽
		var h = this.height;//图片的高
				
		var scaleW = width/w;
		var scaleH = height/h;
				
		var newW = scaleH * w;
		var newH = scaleW * h;
				
		var scale = scaleW>scaleH?scaleW:scaleH;
		if(newW > width){
			scale = scaleW;
		}
		if(newH > height){
			scale = scaleH;
		}
	
		var _w = w * scale;
		var _h = h * scale;   
			
		callback({
			width:_w,
			height:_h
		});
	});
}
var imageAutoAdjust = function(target,width,height,previewUrl,callback,pp){
	if(!target) return;
	imgReady(previewUrl, function () {
		var w =  this.width;//图片的宽
		var h = this.height;//图片的高
				
		var scaleW = width/w;
		var scaleH = height/h;
				
		var newW = scaleH * w;
		var newH = scaleW * h;
				
		var scale = scaleW>scaleH?scaleW:scaleH;
		if(newW > width){
			scale = scaleW;
		}
		if(newH > height){
			scale = scaleH;
		}
	
		var _w = w * scale;
		var _h = h * scale;   
			
		target.css({
			width:_w,
			height:_h,
			paddingTop:height-_h
		});
		
		callback && typeof callback == 'function' && callback(_w,_h,pp);
	});
}


function trimZero(str){
	if(!str) return "";
	var res = "";
	var ilen = str.length;
	for(var i=0;i<ilen;i++){
		if( str.charCodeAt(i) != 0){
			res += 	str.charAt(i);	
		}
	}
	return res;
}


function buildDragData(data){
	return trimZero(data);
}


var getBrowserVersion = function(){
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    if(s = ua.match(/msie ([\d.]+)/)){
    	Sys.type = 'ie';
        Sys.version = s[1];
    }else if(s = ua.match(/gleasy client\/([\d.]+)/)){
    	Sys.type = 'gleasy';
       Sys.version = s[1];
    }else if(s = ua.match(/firefox\/([\d.]+)/)){
    	Sys.type = 'firefox';
       Sys.version = s[1];
    }else if(s = ua.match(/chrome\/([\d.]+)/)){
    	Sys.type = 'chrome';
       Sys.version = s[1];
    }else if(s = ua.match(/opera.([\d.]+)/)){
    	Sys.type = 'opera';
        Sys.version = s[1];
    }else if(s = ua.match(/version\/([\d.]+).*safari/)){
    	Sys.type = 'safari';
        Sys.version = s[1];
    }else{
    	Sys.type = 'unkown';
        Sys.version = 0;
    }
 
 	return Sys;
	
	/*
    //以下进行测试
    if (Sys.ie) document.write('IE: ' + Sys.ie);
    if (Sys.firefox) document.write('Firefox: ' + Sys.firefox);
    if (Sys.chrome) document.write('Chrome: ' + Sys.chrome);
    if (Sys.gleasy) document.write('Gleasy: ' + Sys.gleasy);	
    if (Sys.opera) document.write('Opera: ' + Sys.opera);
    if (Sys.safari) document.write('Safari: ' + Sys.safari);
    * */	
}

Date.prototype.format=function(format){var returnStr='';var replace=Date.replaceChars;for(var i=0;i<format.length;i++){var curChar=format.charAt(i);if(i-1>=0&&format.charAt(i-1)=="\\"){returnStr+=curChar}else if(replace[curChar]){returnStr+=replace[curChar].call(this)}else if(curChar!="\\"){returnStr+=curChar}}return returnStr};Date.replaceChars={shortMonths:['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'],longMonths:['January','February','March','April','May','June','July','August','September','October','November','December'],shortDays:['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],longDays:['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'],d:function(){return(this.getDate()<10?'0':'')+this.getDate()},D:function(){return Date.replaceChars.shortDays[this.getDay()]},j:function(){return this.getDate()},l:function(){return Date.replaceChars.longDays[this.getDay()]},N:function(){return this.getDay()+1},S:function(){return(this.getDate()%10==1&&this.getDate()!=11?'st':(this.getDate()%10==2&&this.getDate()!=12?'nd':(this.getDate()%10==3&&this.getDate()!=13?'rd':'th')))},w:function(){return this.getDay()},z:function(){var d=new Date(this.getFullYear(),0,1);return Math.ceil((this-d)/86400000)}, W:function(){var d=new Date(this.getFullYear(),0,1);return Math.ceil((((this-d)/86400000)+d.getDay()+1)/7)},F:function(){return Date.replaceChars.longMonths[this.getMonth()]},m:function(){return(this.getMonth()<9?'0':'')+(this.getMonth()+1)},M:function(){return Date.replaceChars.shortMonths[this.getMonth()]},n:function(){return this.getMonth()+1},t:function(){var d=new Date();return new Date(d.getFullYear(),d.getMonth(),0).getDate()},L:function(){var year=this.getFullYear();return(year%400==0||(year%100!=0&&year%4==0))},o:function(){var d=new Date(this.valueOf());d.setDate(d.getDate()-((this.getDay()+6)%7)+3);return d.getFullYear()},Y:function(){return this.getFullYear()},y:function(){return(''+this.getFullYear()).substr(2)},a:function(){return this.getHours()<12?'am':'pm'},A:function(){return this.getHours()<12?'AM':'PM'},B:function(){return Math.floor((((this.getUTCHours()+1)%24)+this.getUTCMinutes()/60+this.getUTCSeconds()/ 3600) * 1000/24)}, g:function(){return this.getHours()%12||12},G:function(){return this.getHours()},h:function(){return((this.getHours()%12||12)<10?'0':'')+(this.getHours()%12||12)},H:function(){return(this.getHours()<10?'0':'')+this.getHours()},i:function(){return(this.getMinutes()<10?'0':'')+this.getMinutes()},s:function(){return(this.getSeconds()<10?'0':'')+this.getSeconds()},u:function(){var m=this.getMilliseconds();return(m<10?'00':(m<100?'0':''))+m},e:function(){return"Not Yet Supported"},I:function(){return"Not Yet Supported"},O:function(){return(-this.getTimezoneOffset()<0?'-':'+')+(Math.abs(this.getTimezoneOffset()/60)<10?'0':'')+(Math.abs(this.getTimezoneOffset()/60))+'00'},P:function(){return(-this.getTimezoneOffset()<0?'-':'+')+(Math.abs(this.getTimezoneOffset()/60)<10?'0':'')+(Math.abs(this.getTimezoneOffset()/60))+':00'},T:function(){var m=this.getMonth();this.setMonth(0);var result=this.toTimeString().replace(/^.+ \(?([^\)]+)\)?$/,'$1');this.setMonth(m);return result},Z:function(){return-this.getTimezoneOffset()*60},c:function(){return this.format("Y-m-d\\TH:i:sP")},r:function(){return this.toString()},U:function(){return this.getTime()/1000}};


var imgReadyWithRetry = function(_param){
	if(!_param.errorcount){
		_param.errorcount = 0;
	}
	if(_param.errorcount > 5){
		return;
	}
	_param.error = function(){
		_param.errorcount = _param.errorcount + 1;
		setTimeout(function(){
			imgReadyWithRetry(_param);
		},1000);
	}
	imgReady(_param.url,_param.ready,_param.load,_param.error);
}
