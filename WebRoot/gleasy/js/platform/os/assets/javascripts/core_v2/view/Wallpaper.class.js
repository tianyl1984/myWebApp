Namespace.register("com.gleasy.os.view");

/**
 * 桌面任务栏VIEW
 * 
 * 
 */
com.gleasy.os.view.Wallpaper = function(){
	var _this = this;	
	var name = "com.gleasy.os.view.Wallpaper";//名称
	var interests = [Constants.command.os.setWallpaper,Constants.command.os.requireLogin,
        Constants.command.os.loadDefaultWallpaper];//监听的消息IDs
	
	var view = null;
	var wallpaper = null;
	//var picture = Constants.config.assetPrefix+'/platform/os/assets/stylesheets/os/images/gleasy.jpg';
	var picture = null;
	var type = "tile";
	
	var pictureSize = null;
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
		view = $(".osWrap");
		wallpaper = $("div.wallpapper",view);
		var segment = $('<div class="segment" />');
		segment.append($("<img/>"));
		wallpaper.append(segment);
		segment.hide();
		//$(".wallLoading",wallpaper).show();
		wallpaper.css("zIndex",Constants.config.os.layer.wallpaper);
		configEvent();
	}
	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		$.log(this.getName()+" 收到消息："+eventId);
		if(eventId == Constants.command.os.setWallpaper){
			//$(".wallLoading",wallpaper).show();
			changePicture(messageBody);
		}else if(Constants.command.os.requireLogin == eventId){
			picture = Constants.config.assetPrefix+'/platform/os/assets/stylesheets/os/images/gleasy.jpg';
			//$(".wallLoading",wallpaper).hide();
			drawWallpaper();
		}else if(Constants.command.os.loadDefaultWallpaper == eventId){
			picture = Constants.config.assetPrefix+'/platform/os/assets/stylesheets/os/images/gleasy.jpg';
			//$(".wallLoading",wallpaper).hide();
			drawWallpaper();
		}
	}
	
	var showDefault = function(){
		picture = Constants.config.assetPrefix+'/platform/os/assets/stylesheets/os/images/gleasy.jpg';
		//$(".wallLoading",wallpaper).hide();
		drawWallpaper();
	}
	
	var interval = null;
	var drawWallpaper = function(){
		if(!picture) return;
		if(interval){
			clearTimeout(interval);
		}
		pictureSize = null;
		interval = setTimeout(function(){
			if(!pictureSize){
				showDefault();
			}
		},10000);
		
		var img = new Image();
    	img.src = picture;
    	if (img.complete){	
    		var w =  img.width;//图片的宽
			var h = img.height;//图片的高	
			pictureSize = {
				width:w,
				height:h
			};
			settingBg(type,picture);
    	}else{
	    	img.onload = function(){
	    		var w =  img.width;//图片的宽
				var h = img.height;//图片的高	
				pictureSize = {
					width:w,
					height:h
				};
				settingBg(type,picture);
	    	}
    	}
//		imgReady(picture, function(){
//			
//		});		
	}

	var settingBg = function(type,url){
		var wallpapperimg = wallpaper.find('img');
		var segment = wallpaper.find('.segment');
		segment.show();
		wallpaper.removeAttr('style'); //清理 style
		wallpapperimg.removeAttr('style');
		segment.removeAttr('style');
		switch(type){
			case "full": //填充 ,填满并居中,保持宽高比
			wallpapperimg.show().attr('src',url);
			var _papperWidth = pictureSize.width;
			var _papperHeight = pictureSize.height;
			segment.css({'position':'static'});
			if ( _papperWidth/_papperHeight >  wallpaper.width()/wallpaper.height() ){ //图片宽高比 大于 屏幕宽高比
				
				wallpapperimg.css({'position':'absolute','top':'0','height':wallpaper.height()});
				
				/*document resize 时要重新计算 以下两行*/
				var _pagerLeft = -(_papperWidth - wallpaper.width())/2; 
				wallpapperimg.css({'left':_pagerLeft});
				
				
			} else {
                var wallpaperWidth = wallpaper.width();
				wallpapperimg.css({'position':'absolute','left':'0','width':wallpaperWidth});
                var newPaperHeight = wallpaperWidth/_papperWidth * _papperHeight;
				/*document resize 时要重新计算 以下两行*/
				var _pagerTop = -(newPaperHeight - wallpaper.height())/2;
				wallpapperimg.css({'top':_pagerTop});
				
			}
			break;
			
			case "adjust": //适应 , 取一个最大值，保持宽高比，并居中
			wallpapperimg.show().attr('src',url);
			var _papperWidth = pictureSize.width;
			var _papperHeight = pictureSize.height;
			if( _papperWidth > _papperHeight){ //图片宽大于高,垂直居中
				wallpapperimg.css({'width':'100%'});
				segment.css({'display':'table-cell','position':'static'});
				
				//document resize 时要重新计算
				segment.css({'width':wallpaper.width(),'height':wallpaper.height()}); 
			} else { //图片宽小于高，水平居中
				segment.css({'width':'100%','height':'100%'});
				wallpapperimg.css({'height':'100%','text-align':'center'});
			}
			break;
			
			case "tile": //平铺
			wallpapperimg.hide();
			wallpaper.attr('style','background:url(' + url + ');');
			break;
			
			case "stretch": //拉伸
			wallpapperimg.show().attr('src',url);
			wallpapperimg.css({'width':'100%','height':'100%'});
			segment.css({'position':'static','width':'100%','height':'100%'});
			break;
			
			case "center": //居中
			wallpapperimg.hide();
			wallpaper.attr('style','background:url(' + url + ') center center no-repeat;');
			break;
		}
		
	}
	

	/**
	 * 更改墙纸
	 */
	var changePicture = function(param){
		$.log(param);
		var url = param.url;
		var fileid = param.fileid;
		picture = url;
		type = param.type?param.type:type;
		drawWallpaper();
		
		/*
		AjaxConfig.ajax({
		   type: "POST",
		   url: saveUrl,
		   dataType:"json",
		   contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		   data: {url:url,fileId:fileid},
		   success: function(){
		   	window.application.sendNotify(Constants.command.os.showTip,"保存壁纸成功");
		   },
		   error:function(){
		   	window.application.sendNotify(Constants.command.os.showTip,"保存壁纸失败");
		   }
		});*/
	}
	
	
	/**
	 * 根据浏览器窗口大小调整本控件的大小
	 */
	var resize = function(){
		drawWallpaper();
	}
	
	var configEvent = function(){
		resize();
		$(window).resize(function(){
		  resize();
		});
		/*
		AjaxConfig.ajax({
		   type: "POST",
		   url: getUrl,
		   dataType:"json",
		   contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		   data: {},
		   success: function(res){
		   	if(res.status == 0 && res.data){
		   		if(res.data.fileId){
		   			picture = AjaxConfig.url['cloudDisk']+"/common/download?fid="+res.data.fileId;
		   		}else if(res.data.url){
		   			picture = res.data.url;
		   		}
		   		drawWallpaper();
		   	}
		   },
		   error:function(){
		   }
		});	*/	
	}
	
	
	//构造函数调用
	_construct();
}

//继承MvcBase
com.gleasy.os.view.Wallpaper.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本View
window.application.addModel(new com.gleasy.os.view.Wallpaper());
