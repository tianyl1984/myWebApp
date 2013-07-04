Namespace.register("com.gleasy.os.view");

/**
 * 桌面任务栏VIEW
 * 
 * 
 */
com.gleasy.os.view.TopBarView = function(){
	var _this = this;	
	var name = "com.gleasy.os.view.TopBarView";//名称
	var interests = [Constants.command.os.resetTopNavBar,
	Constants.command.os.iconDragStart,
	Constants.command.os.iconDragEnd,
	Constants.command.os.updateCurrentUserInfo,
	Constants.command.os.reloadUserCardFinish,
	Constants.command.os.loginSuccess,
	Constants.command.os.changeDesktopPage];//监听的消息IDs
	
	var view = null;
	var total = 1;
	var current = 0;
	var topNavMenu = null;
	
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
		var desktop = $(".desktop");
		view = $("div.oTopBar",desktop);
		//view.show();
		view.css("zIndex",Constants.config.os.layer.topNav);
		topNavMenu = $("#util_div .topoNavMenu .oTopTip").clone();
		topNavMenu.css("zIndex",Constants.config.os.layer.topNavMenu);
		topNavMenu.appendTo(desktop);
		topNavMenu.hide();
		
		
		configEvent();
		resize();
		$(window).resize(function(){
		  resize();
		});
	}
	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		$.log(this.getName()+" 收到消息："+eventId);
		if(eventId == Constants.command.os.resetTopNavBar){
			resetTopNavBar(messageBody);
		}else if(eventId == Constants.command.os.changeDesktopPage){
			changeDesktopPage(messageBody);
		}else if(eventId == Constants.command.os.iconDragStart){
			iconDragStart(messageBody);
		}else if(eventId == Constants.command.os.iconDragEnd){
			iconDragEnd(messageBody);
		}else if(eventId == Constants.command.os.updateCurrentUserInfo || eventId == Constants.command.os.reloadUserCardFinish){
			var force = messageBody?(messageBody.type==0):false;
			com.gleasy.os.SystemInfo.getCurrentUser(function(userCard){
				$(".oTopHeadImg",view).attr("src",userCard.smallAvatar);
			},force);
		}else if(Constants.command.os.loginSuccess == eventId){
			view.show();
		}
	}
	
	var iconDragStart = function(){
		//view.css("zIndex",Constants.config.os.layer.dragingIcon+1);	
	}
	
	var iconDragEnd = function(){
		view.css("zIndex",Constants.config.os.layer.topNav);	
	}
	
	var changeDesktopPage = function(current){
						// remove current class from all
		$('div.oTopNav a', view).removeClass('oNavCurrent');
						// add current class to next
		$('div.oTopNav a:eq('+ (current) +')', view).addClass('oNavCurrent');
		
		//com.gleasy.os.IconPanDragHandler.draging(null,true);
	}
	
	/**
	 * 根据浏览器窗口大小调整本控件的大小
	 */
	var resize = function(){
		var width = $(".oTopHead",view).width() + 30 + (total) * 38;
		
		var totalWidth = $(".desktop").width();
		if(totalWidth < 600){
			totalWidth = 600;
		}
		
		var left = totalWidth/2 - width/2;
		if(left < 0){
			left = 0;
		}
		
		//$.log("totalWidth="+totalWidth+";width="+width+";left="+left);
		view.width(width);
		view.css({left:left});
	}
	
	var configEvent = function(){
		resetTopNavBar({total:1,current:0});
		
		view.click(function(ev){
			ev.preventDefault();//阻止冒泡
		    ev.stopPropagation();
		});  
		
		com.gleasy.os.SystemInfo.getCurrentUser(function(userCard){
			$(".oTopHeadImg",view).attr("src",userCard.bigAvatar);
		});
		
		view.delegate("a.oTopBtn","click",function(){
			var _interval = $(this).data('_interval');
			if(_interval!=null){
				clearTimeout(_interval);
			}
			var index = $(this).data("nav_index");
			$.log("nav.clicked:"+index);
			if(!$(this).hasClass("selected")){
				_this.sendNotify(Constants.command.os.navDesktopPage,index);
			}
		}).delegate(".oTopHead","click",function(evt){
			topNavMenu.trigger("mouseenter");
		}).delegate(".oTopHead","mouseenter",function(evt){
			var show_interval = setTimeout(function(){
				topNavMenu.trigger("mouseenter");	
			},400);
			topNavMenu.data("show_interval",show_interval);	
		}).delegate(".oTopHead","mouseleave",function(evt){
			var show_interval = topNavMenu.data("show_interval");
			if(show_interval){
				clearTimeout(show_interval);
			}
			topNavMenu.trigger("mouseleave");
		});
		
		$(".oExit",topNavMenu).closest("a").bind("click",function(){
			window.application.sendNotify(Constants.command.system.confirm,{
			    message:"确定退出Gleasy吗？", 
			    okBtn:true,
			    okBtnText:"退出Gleasy",
			    system:true,
			    cancelBtn:true,
			    cancelBtnText:"点错了",
				callback:function(dt){
					if(dt.result){
						window.application.sendNotify(Constants.command.os.account.logout,{
								data:{},
								success:function(result){
									com.gleasy.os.SystemInfo.login = false;
									Constants.user = null;
									window.location.reload();
								},
								error:function(){
									window.application.sendNotify(Constants.command.system.alert,{message:"网络异常，注销不成功。您可以直接关闭。"});
								}
						});
					}
				}
			});	
			
			return false;
		});
		
		$(".oHomePage",topNavMenu).closest("a").bind("click",function(){
			var params = {
				type : 'personalHomePage', //cardStorage,personalHomePage,messageCenter,searchPerson
				success : function(dt){},
				error : function(dt) {}
			}
			window.application.sendNotify(Constants.command.os.process.sendMessage,{
				pname:"联系人守护进程",
				eventId:"openContact",
				messageBody:params
			});
			
			return false;
		});		
		topNavMenu.bind("mouseenter",function(){
			var hide_interval = topNavMenu.data("hide_interval");
			if(hide_interval){
				clearTimeout(hide_interval);
			}
			topNavMenu.show();
			topNavMenu.position({
				 my: "center top",
				 at: "center bottom",
				 of: $(".oTopHead",view),
				 offset:"0 10"
			});
		}).bind("mouseleave",function(){
			if(!topNavMenu.is(":visible")){
				return;
			}
			var hide_interval = setTimeout(	
				function(){
					topNavMenu.hide();
				},300
			);
			topNavMenu.data("hide_interval",hide_interval);	
		});
	}
	
	
	var findNav = function(centerPoint){
		var navContainer = $("div.oTopNav",view);
		var left = navContainer.offset().left;
		var top = navContainer.offset().top;
		var totalWidth = navContainer.width();
		var totalHeight = view.height();
		var gridWidth = totalWidth/total;
		var gridHeight = totalHeight;
		
		for(var i=0;i<total;i++){
			var dom = navContainer.children()[i];
			var dimension = {
				top:top,
				left:left+gridWidth*i,
				width:gridWidth,
				height:gridHeight
			}; 
			if(centerPoint.x >= dimension.left && centerPoint.x <= dimension.left + dimension.width && centerPoint.y >= dimension.top && centerPoint.y<=dimension.top+dimension.height){
				return i;
			}
		}
		return -1;
	}
	
	var currentMatch = -1;
	var trigger_interval = null;
	var mini = false;
	this.dragOver = function(icon){

		if(icon.silence) return false;
		if(icon.pan.id == Constants.command.os.iconPan.dockId) return false;
		
		var helper = icon.helper;
		var centerPoint = icon.centerPoint;
		var idx = findNav(centerPoint);
		if(idx >= 0){
			if(!mini){
				mini = true;
				helper.css({
					width:25,
					height:25
				});	
				$(".oIcon",helper).stop().animate({
					width:25,
					opacity:0.5,
					height:25
				},200);
			}
			//命中了
			if(currentMatch != idx){
				$(".oTopBtn",view).removeClass("oTopBtn_hover");
				$("#switch_"+idx,view).addClass("oTopBtn_hover");
				if(trigger_interval){
					clearTimeout(trigger_interval);
				}
				currentMatch = idx;
				trigger_interval = setTimeout(function(){
					$("#switch_"+idx,view).trigger("click");
					
				},800);
			}
			return true;
		}else{
			if(mini){
				$(".oTopBtn",view).removeClass("oTopBtn_hover");
				mini = false;
				helper.css({
					width:50,
					height:50
				});	
				$(".oIcon",helper).stop().animate({
					width:50,
					height:50,
					opacity:1
				},200);
			}
			currentMatch = -1;
			if(trigger_interval){
				clearTimeout(trigger_interval);
			}
		}
		return false;
	}
	
	this.dragDrop = function(icon){
		var centerPoint = icon.centerPoint;
		var idx = findNav(centerPoint);
		if(idx >= 0){
			currentMatch = -1;
			if(trigger_interval){
				clearTimeout(trigger_interval);
			}
			var to = com.gleasy.os.view.DesktopView.instance.getPage(idx);
			var from = icon.pan;
			if(to === from) return false;
			window.application.sendNotify(Constants.command.moveIcon,{
				from:from,
				to:to,
				icon:icon
			});
			window.application.sendNotify(Constants.command.os.iconDragEnd,{});
			
			return true;
		}
		return false;
	}
	
	var resetTopNavBar = function(param){
		total = param.total;
		if(typeof total != 'number' || total < 1){
			total = 1;
		}
		current = param.current;
		if(typeof current != 'number' || current < 0){
			current = 0;
		}
		if(current >= total) {
			current = total - 1;
		}
		clearNav();
		addPageNav(total,current);
		if(param.flash){
			flashSingleNav(param.flash);
		}
		resize();
	}
	
	
	var flashSingleNav = function(no){
		if(!no) return;
		if(no <= 0 || no > total) return;
		var nav = $("#switch_"+no,view).parent();
		if(!nav) return;
		var s = 0;
		var interval = setInterval(function(){
			if(s > 10){
				clearInterval(interval);
				return;
			}
			s ++ ;
			if(s % 2 == 0){
				nav.hide();
			}else{
				nav.show();
			}
		},100);
	}
	
	var clearNav = function(){
		var navContainer = $("div.oTopNav",view);
		navContainer.empty();	
	}
	
	
	var addPageNav = function(total,current){
		var navContainer = $("div.oTopNav",view);
		for(var i = 0; i < total ;i++){
			var nav = $('<a href="#" class="oTopBtn '+i+'" id="switch_'+i+'"></a>');
			nav.data("nav_index",i);
			if( i == current ){
				nav.addClass("oNavCurrent");
			}
			var d = $("<div class='oTopBtnBox' />");
			d.append(nav);
			navContainer.append(d);
		}
	}
	
	//构造函数调用
	_construct();
}

//继承MvcBase
com.gleasy.os.view.TopBarView.prototype = new com.gleasy.libs.MvcBase();

com.gleasy.os.view.TopBarView.instance = new com.gleasy.os.view.TopBarView();

//向application中注册本View
window.application.addModel(com.gleasy.os.view.TopBarView.instance);
