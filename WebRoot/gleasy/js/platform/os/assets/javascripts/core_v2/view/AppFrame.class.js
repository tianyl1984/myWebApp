/**
 * Gleasy OS Frame
 * 继承自Frame
 * 并具有以下单独特性：
 * 1. 拥有工具栏
 * 2. 它可以单独代表一个应用
*/
Namespace.register("com.gleasy.os.view");

com.gleasy.os.view.AppFrame = function(param){	
	com.gleasy.os.view.Frame.call(this,param);//继承Frame
	var _this = this;

	var showToolbar = true;
	var options = {
		appId:0,
		url:""
	};
	var tbar = null;
	var urlLoaded = false;
	var messageCache = [];
	
	var _construct = function(){
		$.extend(options,param);
		initComponent();
		_this.center();
	}


	var initComponent = function(){
		if(options.url.indexOf("?")>=0){
			options.url += "&";
		}else{
			options.url += "?";
		}
		options.url += "rand="+Math.random();
		
		//alert(options.url);
		
		
		configEvent();
		
		var param = {
			ids:[options.appId],
			callback:function(result){
				var app = result[options.appId];
				_this.setUrl(app.url);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				//网络异常时处理
				window.application.sendNotify(Constants.command.system.alert,{message:"网络异常"});
			}
		};
		window.application.sendNotify(Constants.command.directLoadApplication,param);
		
	}
	
	var doShowToolbar = function(show){
		if(!show){
			showToolbar = show;
			$(".osWinToolBar",_this.view).hide();
			$(".osWinToolBarShow",_this.view).show();
			_this.repaint();		
		}else{
			if(!tbar){
				tbar = $("#util_div .appToolBar").clone();
				$(".osWindowBodyArea",_this.view).before(tbar.html());
			}
			showToolbar = show;
			$(".osWinToolBar",_this.view).show();
			$("a.osWinToolBarShow",_this.view).hide();			
			_this.repaint();	
		}
	}
	
	var configEvent = function(){	
		_this.addEventListener("urlLoad",function(){
			urlLoaded = true;
			doShowToolbar(true);	
			if(messageCache.length > 0){
				$.each(messageCache,function(k,msg){
					handleMessage(msg.eventId,msg.messageBody);
				});
			}
		});
		
		_this.view.delegate("li.end","click",function(ev){
			doShowToolbar(false);
		}).delegate("a.osWinToolBarShow","click",function(ev){
			doShowToolbar(true);
		});
		
		_this.dragHandle += ",.osWinToolBar";
		
		_this.addEventListener("resize",function(){
			var size = _this.getContentSize();
			if(size.width<450){
				if(!$(".osWinToolBar",_this.view).hasClass("osWinToolBarMin")){
					$(".osWinToolBar",_this.view).addClass("osWinToolBarMin");
				}
			}else{
				if($(".osWinToolBar",_this.view).hasClass("osWinToolBarMin")){
					$(".osWinToolBar",_this.view).removeClass("osWinToolBarMin");
				}
			}	
		});	
	}

		
	this.getFrameSize = function(){
		var heightPlus = 0;
		if(showToolbar){
			heightPlus = 24;
		}
		
		var o = {};
		if(_this.hasFrame() == 1){
			o.width = 8;
			o.height = $('.osWindow_title',_this.view).height() + heightPlus;
		}else{
			o.width = 0;
			o.height = heightPlus;
		}	
		return o;
	}
	
	var handleMessage = function(eventId,messageBody){
		if(eventId == 'openFile'){
			_this.setFilesReceived(messageBody);
			_this.tryFocus();
		}else{
			_this.noticeListener(eventId,messageBody);
		}
	}
	
	this.notify = function(eventId,messageBody){
		if(urlLoaded){
			handleMessage(eventId,messageBody);
		}else{
			messageCache.push({
				eventId:eventId,
				messageBody:messageBody
			});
		}
	}
		
	return _construct();
}

/**
 * 执行入口
 * 有main方法的类才可以被OS执行
 */
com.gleasy.os.view.AppFrame.main = function(appLink){
	var param = {};
	$.extend(param,appLink);
	$.extend(param,{
		icon:appLink.image,
		clazz:com.gleasy.os.view.AppFrame,
		appId:appLink.id,
		app:appLink.app,
		callback:function(res){
			var wid = res.wid;
			window.application.sendNotify(Constants.command.os.frame.setVisible,{wid:wid,visible:true});
		}
	});
	window.application.sendNotify(Constants.command.os.frame.create,param);	
}
