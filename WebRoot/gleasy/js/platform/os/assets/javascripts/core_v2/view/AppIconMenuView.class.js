Namespace.register("com.gleasy.os.view");

/**
 *消息提醒
 * 
 * 
 */
com.gleasy.os.view.AppIconMenuView = function(param){
	var _this = this;	
	var name = "com.gleasy.os.view.AppIconMenuView";//名称
	var interests = [
		Constants.command.os.showIconMenu,
		Constants.command.clearActiveLink
	];//监听的消息IDs
	var contentHtml = Constants.config.assetPrefix+"/platform/os/assets/javascripts/core_v2/view/AppIconMenu.html?id="+Math.random();
		
	var view = null;
	var options = {
		container:$(".desktop")
	};
	var target = null;
	var interval = null;
	var pages = null;
	
	/**
	 * 模拟构造函数
	 */
	var _construct = function(){
		$.extend(options,param);
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
		initComponent();
	}
	
	
	var initComponent = function(){
		view = $("<div class='oConTextMenuBox'/>");
		view.appendTo(options.container);
		view.hide();
		
		AjaxConfig.load(view,contentHtml,function(){
			configEvent();
		});
	}
	
	var clearing = false;
	this.chromeCallback = function(state){
		if(state == true){
			//清除缓存完成
			//window.location.reload();
			$.log("清除完成啦....");
			clearing = false;
		}
	}
	
	var clearCache = function(){
		window.location.reload();
	}
	var configEvent = function(){
		 view.bind("click",function(ev){  
		 	 var iconLen = $(ev.target).closest(".moveTo").length;
	         if(iconLen >0){
	         	ev.preventDefault();
	          	ev.stopPropagation();
	          	return false;
	         }
	         view.hide();
	         ev.preventDefault();
	         ev.stopPropagation(); 
	         return false;    	
        });
        
		view.delegate("li.createCollection","click",function(evt){
			doCreateCollection();
		}).delegate("li.renameApp","click",function(evt){
			window.application.sendNotify(Constants.command.renameCollection,target);	
		}).delegate("li.removeApp","click",function(evt){
			if(target.type == 'collection'){
				if(target.collection && !target.collection.isEmpty()){
					window.application.sendNotify(Constants.command.system.confirm,{
							message:"删除应用容器会所把所包含的应用都卸载.",
						    confirmMessage:"您确定要删除吗?", 
						    system:true,//是否系统提醒
						    icon:"prompt",//icon={confirm,fail,success,prompt,warning}
						    okBtn:true,
						    okBtnText:"确定",
						    cancelBtn:false,
							callback:function(param){
								if(param.result){
									target && target.pan && target.pan.removeIcon(target,true);
									target && target.pan && target.pan.refresh();
								}
							}
					});
				}else{
					target && target.pan && target.pan.removeIcon(target,true);
					target && target.pan && target.pan.refresh();	
				}
			}else{
				window.application.sendNotify(Constants.command.system.confirm,{
						message:"确定卸载  "+target.title+" 吗?",
					    system:true,//是否系统提醒
					    icon:"warning",//icon={confirm,fail,success,prompt,warning}
					    okBtn:true,
					    okBtnText:"确定",
					    cancelBtn:false,
						callback:function(param){
							if(param.result){
								target && target.pan && target.pan.removeIcon(target,true);
								target && target.pan && target.pan.refresh();
							}
						}
				});	
			}		
		}).delegate("li.openApp","click",function(evt){		
			if(target.type == 'collection'){
				window.application.sendNotify(Constants.command.showCollection,target);	
				return;
			}	
			target && window.application.sendNotify(Constants.command.os.process.runProgram,{
				pname:target.app
			});	
		}).delegate("li.openAutoRun","click",function(evt){		
			window.application.sendNotify(Constants.command.setAppConfig,{
				app:target.app,
				config:{autoRun:1}
			});
		}).delegate("li.closeAutoRun","click",function(evt){		
			window.application.sendNotify(Constants.command.setAppConfig,{
				app:target.app,
				config:{autoRun:0}
			});	
		}).delegate("li.addApp","click",function(evt){		
			 window.application.sendNotify(Constants.command.os.process.runProgram,{
				pname:"应用商店"
			});
		}).delegate("li.addCollection","click",function(evt){		
			doCreateCollection();
		}).delegate("li.uploadFile","click",function(evt){		
			window.application.sendNotify(Constants.command.os.process.sendMessage,{
												pname:"一盘",
												eventId:"uploadFile",
												messageBody:{}
											});	
		}).delegate("li.setBg","click",function(evt){		
			window.application.sendNotify(Constants.command.os.process.runProgram,{
				pname:Constants.app.background
			});	
		}).delegate("li.refresh","click",function(evt){		
			clearCache();
		}).delegate("li.moveTo","mouseenter",function(ev){		
				if(interval){
					clearTimeout(interval);
				}
				$(".forCreate",view).show();
				$(".forCreate li",view).show();
				$(".forCreate",view).position({
					of:$(this),
					my:"right bottom",
					at:"left bottom",
					offset:"0 0"
				});
				ev.preventDefault();
		        ev.stopPropagation(); 
		        return false; 
		}).delegate("li.moveTo","mouseleave",function(ev){		
				if(interval){
					clearTimeout(interval);
				}
				interval = setTimeout(function(){
					$(".forCreate",view).hide();
				},300);
		}).delegate(".forCreate","mouseenter",function(evt){		
				if(interval){
					clearTimeout(interval);
				}	
		}).delegate(".forCreate","mouseleave",function(ev){
				if(interval){
					clearTimeout(interval);
				}
				interval = setTimeout(function(){
					$(".forCreate",view).hide();
				},300);
		}).delegate("li.moveToPage","click",function(evt){		
			var id = $(this).attr("dt");
			if($(this).find("a").hasClass("oDisabled")) return;
			if(!id) return;
			var from = target.pan;
			var to = com.gleasy.os.IconPan.getPanById(id);
			window.application.sendNotify(Constants.command.moveIcon,{
				from:from,
				to:to,
				icon:target
			});
		});
	}

	
	var doCreateCollection = function(li){
		window.application.sendNotify(Constants.command.createCollection,target.pan);
		
	}
	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		$.log(this.getName()+" 收到消息："+eventId);
		if(Constants.command.os.showIconMenu == eventId){
			showIconMenu(messageBody);
		}else if(Constants.command.clearActiveLink == eventId){
			hideIconMenu();
		}
	};

	var hideIconMenu = function(_param){
		view.hide();
	}
		
	var showIconMenu = function(_param){
		var x = _param.x;
		var y = _param.y;
		
		var totalW = options.container.width();
		var totalH = options.container.height();

		target = _param.icon;
		
		$("li",view).hide();
		if(target.type == 'collection'){
			if(target.collection && target.collection.isEmpty()){
				$("li.removeApp",view).find("a").html("删除");
					$("li.removeApp",view).show();	
			}
		
			$("li.renameApp",view).show();	
			$("li.openApp",view).show();	
			$("li.moveTo",view).show();	
		}else if(target.type == 'os' || target.type == 'addNew' ){
			$("li.addApp",view).show();	
			$("li.addCollection",view).show();	
			$("li.uploadFile",view).show();	
			$("li.setBg",view).show();	
			if(typeof chrome != 'undefined' && chrome && typeof chrome.send == 'function'){
				//$("li.refresh",view).show();		
			}	
		}else {
			$("li.removeApp",view).show();	
			if(target.type == 'sys'){
				$("li.removeApp",view).hide();	
			}else{
				$("li.removeApp",view).find("a").html("卸载");	
			}
			$("li.openApp",view).show();
			if(target.pan && target.pan.getId() == Constants.command.os.iconPan.dockId){
				view.hide();
				return;
			}else{
				$("li.moveTo",view).show();	
			}
			if(!target.forceAutoRun){
				if(target.autoRun == 1){
					$("li.closeAutoRun",view).show();
				}else{
					$("li.openAutoRun",view).show();	
				}
			}
		}
		if(!(target.type == 'os' || target.type == 'addNew')){
			$(".forCreate",view).empty();
			var pages = com.gleasy.os.IconPan.pans;
			var pin = null;
			for(var i=0;i<pages.length;i++){
				var page = pages[i];
				if(page.id >= -100){
					var li = $('<li class="moveToPage" dt="'+(page.id)+'"></li>');
					var a = $('<a href="#a">'+page.getName()+'</a>');
					if(page === target.pan){
						a.addClass("oDisabled");
					}
					a.appendTo(li);
					$(".forCreate",view).append(li);
				}
				if(page.id == Constants.command.os.iconPan.pinId){
					pin = page;
				}
			}
			if(pin){
				var li = $('<li class="moveToPage" dt="'+(pin.id)+'"></li>');
				var a = $('<a href="#a">'+pin.getName()+'</a>');
				if(target.type == 'collection' || pin === target.pan){
					a.addClass("oDisabled");
				}
				a.appendTo(li);
				$(".forCreate",view).append(li);
			}
		}
        view.show();
        var w = view.width();
        var h = view.height();
        if(x + w > totalW){
            x = totalW - w - (totalW - x);
        }
        if(y + h > totalH){
            y = totalH - h - (totalH - y);
        }
        view.css({
            left:x,
            top:y
        });

	}
	
	
	//构造函数调用
	_construct();
}

//继承MvcBase
com.gleasy.os.view.AppIconMenuView.prototype = new com.gleasy.libs.MvcBase();

com.gleasy.os.view.AppIconMenuView.instance = new com.gleasy.os.view.AppIconMenuView();

function refresh_callback(state){
	com.gleasy.os.view.AppIconMenuView.instance.chromeCallback(state);
}
//向application中注册本View
window.application.addMvc(com.gleasy.os.view.AppIconMenuView.instance);
