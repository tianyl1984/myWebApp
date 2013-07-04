Namespace.register("com.gleasy.os");

com.gleasy.os.MessageCenter = function(param){
	var _this = this;	
	var name = "com.gleasy.os.MessageCenter";//名称
	var interests = [
		Constants.command.clearActiveLink,
		Constants.command.os.message.appremove,
		Constants.command.os.message.blink
	];//监听的消息IDs

	var contentHtml = Constants.config.assetPrefix+"/platform/os/assets/javascripts/core_v2/view/MessageCenterMain.html?id="+Math.random();
	
	
	var domContainer = null;
	
	var view = null;
	var interval = null;
	
	var mainView = null;
	var messagesCache = null;
	var allMessages = null;
	var mainLoadOver = false;
	
	var messageConfig = null;
	var messageConfigShow = false;
	
	var messageReminder = null;
	var reminding = false;
	
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
		initComponent();
	}
	
	
	var initComponent = function(){
		var taskbar = $("div.taskbar");
		view = $(".messageCenterIcon",taskbar);
		
		domContainer = $("<div/>");
		

   		
		AjaxConfig.load(domContainer,contentHtml,function(){
			mainView = $(".messageCenter",domContainer);
			mainView.appendTo($(".desktop"));
			mainView.css({
				zIndex:Constants.config.os.layer.messageDialog
			});
			mainView.hide();
			
			messageReminder = $(".messageReminder",domContainer);
			messageReminder.appendTo($(".desktop"));
			messageReminder.css({
				right:10,
				bottom:20,
				zIndex:Constants.config.os.layer.messageDialog
			});
			messageReminder.hide();
			
			messageConfig = $(".messageConfig",domContainer);
			messageConfig.appendTo($(".desktop"));
			messageConfig.css({
				zIndex:Constants.config.os.layer.messageDialog
			});
			messageConfig.hide();
			
			configEvent();
			refresh();
			mainLoadOver = true;
			if(messagesCache){
				messageArrive(messagesCache);
			}
		});
	}

	var showMessageReminder = function(messages){
		if(reminding) return;
		reminding = true;
		if(!mainView.is(":visible")){
			messageReminder.show();
			if(messages.length > 3){
				messages = messages.slice(0,3);
			}
			messages.reverse();
			var idx = 0;
			var message = messages[idx];
			remindMessage(message);
			idx++;
			var itv = setInterval(function(){
				message = messages[idx];
				idx++;
				if(idx>messages.length){
					clearInterval(itv);
					reminding = false;
					return;
				}
				remindMessage(message);
			},1500);
		}
	}
	
	var remindMessage = function(message){
		com.gleasy.os.view.MessageParser.parse(domContainer,message,function(result){
			$(".oWLTitle",messageReminder).html(message.app);
			$(".oWMsgList",messageReminder).empty();
			$(".oWMsgList",messageReminder).append(result);
			messageReminder.data("model",message);
			
			messageReminder.css({
				bottom:20,
				opacity: '0'
			});
			messageReminder.stop(true,true).animate({
				bottom:65,
				opacity: '1'
			},1000);
			if(interval){
				clearInterval(interval);
			}
			interval = setTimeout(function(){
				messageReminder.stop(true,true).animate({
					bottom:20,
					opacity: '0'
				},1000,function(){
					messageReminder.hide();
				});
				interval = null;
			},5000);
		},true);		
	}
	
	var hideMessageReminder = function(){
		if(interval){
			clearTimeout(interval);
		}
		messageReminder.hide();
	}
	
	
	var iconJumpUp = function(){
		var _a = view;
		_a.find(".oIcoTxt").stop().animate({top:"-30px",opacity: '1'},"fast");
	}
	
	var iconJumpDown = function(){
		var _a = view;
		_a.find(".oIcoTxt").stop().animate({top:"-20px",opacity: '0'},"fast");
	}	
	
	var refresh = function(){
		var num = allMessages?allMessages.length:0;
		if(num <=0){
			view.find(".oNum").hide();
		}else{
			if(num > 999) num = 999;
			view.find(".oNum").html(num);
			view.find(".oNum").show();
		}
		
		if(mainView && mainView.is(":visible")){
			mainView.position({
				my:"right bottom",
				at:"right top",
				of:view,
				offset:"10 -5"
			});		
		}
		
		
	}
	
	var reloadAppConfig = function(){
		var configItem = $(".configItem",domContainer);
		$(".oWmsgSettingList",messageConfig).empty();
		com.gleasy.os.reg.getInstalledApps({
			allowCache:false,
			callback:function(apps){
				$.each(apps,function(pname,appObj){
					if(appObj.silence || appObj.app != pname || (!appObj.supportMessage && appObj.type=='sys')) return;
					var item = configItem.clone();
					item.show();
					item.data("model",appObj);
					$(".configItemAppImage",item).attr("src",appObj.icon);
					$(".configItemAppName",item).html(appObj.shortName);				
					$(".oWmsgSettingList",messageConfig).append(item);
					var messageOpen = (appObj.type == 'sys')?appObj.messageOpen:appObj.args.messageOpen;
					if(!messageOpen){
						$(".closeApp",item).addClass("selected");
						$(".Swichbg",item).css({left:43});
					}else{
						$(".openApp",item).addClass("selected");
						$(".Swichbg",item).css({left:2});
					}
					$("li",item).css({'pointer-events':"none"});
				});
				
			}
		});
	}
	
	var configEvent = function(){
		messageConfig.bind("mousedown",function(ev){       	 
          ev.preventDefault();
	      ev.stopPropagation();
	      return false;    	
       });
       mainView.bind("mousedown",function(ev){       	 
          ev.preventDefault();
	      ev.stopPropagation();
	      return false;    	
       });
       
		$(".iconImage",view).bind("mouseenter",function(evt){
			iconJumpUp();
		}).bind("mousedown",function(ev){       	 
          ev.preventDefault();
	      ev.stopPropagation();
	      return false;    	
       	})
       	
       	
       	view.bind("mouseleave",function(evt){
	        iconJumpDown();
		}).bind("click",function(evt){
			hideMessageReminder();
			if(mainView.is(":visible")){
				mainView.hide();
				if(messageConfigShow){
					messageConfig.hide();
				}
			}else{
				window.application.sendNotify(Constants.command.os.message.reload,{});
				iconJumpDown();
				mainView.show();
//				if(messageConfigShow){
//					messageConfig.show();
//					messageConfig.position({
//					my:"right top",
//					at:"left top",
//					of:mainView,
//					offset:"0 0"
//				});
//				}
				refresh();
			}
			evt.preventDefault();//阻止冒泡
		    evt.stopPropagation();
		    return false;
		});
		
		mainView.bind("click",function(evt){
			var exclude = [".oDel",".configBtn"];
			if($(evt.target).closest(exclude)){
				evt.preventDefault();//阻止冒泡
		    	evt.stopPropagation();
		    	return false;
			}
		});

		messageConfig.bind("click",function(evt){
			evt.preventDefault();//阻止冒泡
		    evt.stopPropagation();
		    return false;
		});
		
		messageConfig.delegate(".Swichbg","mouseenter",function(evt){
			$(this).die('mouseenter').draggable({
				revert:false,
				axis:'x',
				cursor:'hand',
				disabled:false,
				containment:'parent',
			    start:function(evt){
			    	var initpos = $(this).position().left;
				 	$(this).data("initpos",initpos);
			    },
			    drag:function(evt){
			    	var initpos = $(this).data("initpos");
			  		var newPos = $(this).position().left;
			        var	offset = newPos - initpos;
			 		$(this).data("offset",offset);
			    },
			    stop:function(evt){
			    	var offset = $(this).data("offset");
			      	if(offset > 24){
			      		$(this).parent().find(".closeApp").trigger("click");
			      	}else if(offset < -24){
			      		$(this).parent().find(".openApp").trigger("click");
			      	}else{
			      		var initpos = $(this).data("initpos");
			      		$(this).animate({left:initpos});
			        }       	
			    }
		  });
		});
		
		messageConfig.delegate(".closeBtn","click",function(evt){
			messageConfig.hide();
			messageConfigShow = false;
			evt.preventDefault();//阻止冒泡
		    evt.stopPropagation();
		    return false;
		}).delegate(".Swichbtn","click",function(evt){
			var pos = evt.pageX - $(this).offset().left;
			if(pos > 40){
				$(this).find(".closeApp").trigger("click");
			}else{
				$(this).find(".openApp").trigger("click");
			}
		    return false;
		}).delegate(".Swichbtn li","click",function(evt){
			var li = $(this);
			if(li.hasClass("selected")) return;
			
			var configItem = $(this).closest(".configItem");
			$(".Swichbtn li",configItem).removeClass("selected");
			li.addClass("selected");
			var appObj = configItem.data("model");
			var index = li.index();
			var liWidth = li.attr("offsetWidth");
			$(".Swichbtn .Swichbg",messageConfig).width(liWidth);
			switch(index){
				case 0:
				$(this).parent("ul").siblings(".Swichbg").animate({left:2});
				if(appObj.messageOpen != 1){
					appObj.messageOpen = 1;
					window.application.sendNotify(Constants.command.setAppConfig,{
						app:appObj.app,
						config:{messageOpen:1}
					});
				}
				break;
				case 1:
				var leftPosition = $(this).prev().attr("offsetWidth")+2;
				$(this).parent("ul").siblings(".Swichbg").animate({left:leftPosition});
				$(".CheckboxChecked",messageConfig).hide();
				$(".CheckboxDefault",messageConfig).show();
				if(appObj.messageOpen != 0){
					appObj.messageOpen = 0;
					window.application.sendNotify(Constants.command.setAppConfig,{
						app:appObj.app,
						config:{messageOpen:0}
					});
				}
				break;
			}
		}).delegate(".oWMsgSettingBtn","click",function(evt){
			$(".openApp",messageConfig).trigger("click");
			$(".CheckboxChecked",messageConfig).show();
			$(".CheckboxDefault",messageConfig).hide();
		});
		
		mainView.delegate(".oDel","click",function(){
			var msg = $(this).closest(".oWMsg");
			var message = msg.data("model");
			if(!message.id) return;
			window.application.sendNotify(Constants.command.os.message.remove,{id:message.id});
			msg.remove();
			refresh();
		}).delegate(".oWMsg","click",function(){
			var msg = $(this);
			var message = msg.data("model");
			if(!message || !message.id) return;
			if(message.autoremove){
				if(message.app && message.uuid){
					appremove({
						app:message.app,
						uuid:message.uuid
					});
				}else{
					window.application.sendNotify(Constants.command.os.message.remove,{id:message.id});
					msg.remove();
					refresh();
				}
			}
		}).delegate(".oWMsg","mousedown",function(evt){
			if($(evt.target).closest(".oDel").length) return;
			if(!$(this).hasClass("oWMsg_active")){
				$(this).addClass("oWMsg_active");
			}
		}).delegate(".oWMsg","mouseleave",function(evt){
			if($(this).hasClass("oWMsg_active")){
				$(this).removeClass("oWMsg_active");
			}
		}).delegate(".configBtn","click",function(evt){
			if(messageConfig.is(":visible")){
				messageConfig.hide();
				messageConfigShow = false;
			}else{
				reloadAppConfig();
				messageConfig.show();
				messageConfigShow = true;
				messageConfig.position({
					my:"right top",
					at:"left top",
					of:mainView,
					offset:"0 0"
				});
			}
			evt.preventDefault();//阻止冒泡
		    evt.stopPropagation();
		    return false;
		});	
		
		messageReminder.delegate(".oDel","click",function(){
			hideMessageReminder();
		}).delegate(".oWMsg","click",function(){
			hideMessageReminder();
			var message = messageReminder.data("model");
			if(message && message.autoremove){
				if(message.app && message.uuid){
					appremove({
						app:message.app,
						uuid:message.uuid
					});
				}else{
					window.application.sendNotify(Constants.command.os.message.remove,{id:message.id});				
					$(".message_item_"+message.id,mainView).remove();
					refresh();
				}
				$(this).remove();
			}
		});
	}
	
	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		$.log(this.getName()+" 收到消息："+eventId);
		if(Constants.command.clearActiveLink == eventId){
			mainView && mainView.hide();
			if(messageConfigShow){
				messageConfig && messageConfig.hide();
			}
		}else if(Constants.command.os.message.blink == eventId){
			messageArrive(messageBody);
		}else if(Constants.command.os.message.appremove == eventId){
			appremove(messageBody);
		}
	}
	
	var appremove = function(param){
		var app  = param.app;
		var uuid = param.uuid;
		if(!app || !uuid) return;
		var uuids = [];
		if(typeof uuid == 'object'){
			uuids = uuid;
		}else{
			uuids.push(uuid);
		}
		var ids = [];
		for(var i=0;i<uuids.length;i++){
			var mitem = $(".message_item_"+app+"_"+uuids[i],mainView);
			if(!mitem.length) continue;
			mitem.each(function(idx,aa){
				var item = $(this);
				var message = $(this).data("model");
				if(!message) return;
				item.remove();
				ids.push(message.id);
			});
			
		}
		window.application.sendNotify(Constants.command.os.message.remove,{id:ids});
		refresh();
	}
	
	var messageArrive = function(messages){
		messagesCache = messages;
		
		if(!mainLoadOver){
			return;
		}
		
		allMessages = messages.all;
		$(".oWMsgList",mainView).empty();
		
		if(!allMessages.length){
			var message_null = $(".message_null",domContainer).clone();
			message_null.show();
			$(".oWMsgList",mainView).append(message_null);
		}else{
			for(var i=0;i<allMessages.length;i++){
	   			var message = allMessages[i];
	   			com.gleasy.os.view.MessageParser.parse(domContainer,message,function(result){
	   				result.addClass("message_item_"+message.id);
	   				result.addClass("message_item_"+message.app+"_"+message.uuid);
	   				result.data("model",message);
					$(".oWMsgList",mainView).append(result);
					refresh();
				},false);
	   		}
		}
		if(messages.plus && messages.plus.length){
			showMessageReminder(messages.plus);
		}
	}
	
	//构造函数调用
	_construct();
}

//继承MvcBase
com.gleasy.os.MessageCenter.prototype = new com.gleasy.libs.MvcBase();

//向application中注册本View
window.application.addMvc(new com.gleasy.os.MessageCenter());
