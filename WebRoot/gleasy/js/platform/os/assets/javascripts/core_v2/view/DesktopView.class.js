Namespace.register("com.gleasy.os.view");

/**
 * 桌面容器类－能够操作的桌面效果全靠它了!
 * 1. 本身可slide
 * 2. 用来存放page 
 * 
 */
com.gleasy.os.view.DesktopView = function(){
	var _this = this;
		
	var name = "com.gleasy.os.view.DesktopView";//名称
	var interests = [Constants.command.loadDesktopOK,
					Constants.command.switchDesktop,
					Constants.command.os.iconDragStart,
					Constants.command.os.iconDragEnd,
					Constants.command.getCurrentDesktopPage,
					Constants.command.os.navDesktopPage,
					Constants.command.showCollection,
					Constants.command.renameCollection,
					Constants.command.createCollection,
					Constants.command.removeCollection,
					Constants.command.moveIcon,
					Constants.command.os.updateAppDeskMessage,
					Constants.command.clearActiveLink];//Model监听的消息IDs
	

	
	var emptyPageData = null;
	var emptyId = -1;	
	var collectionId = -10000;
	
	var bgDraging = false;//是否正在拖动桌面
	var dataChanged = false;
	
	
	var option = {
		desktop:$(".desktop"),
		render:$('.slides'),//必填
		top:0,
		bottom:0,
		pages:[],
		collections:[],
		pageDataList:[]
	};
			
			
	var elem,control ,
				total,
				width,
				height,
				start=0,
				effect,
				paginationEffect,
				next = 0, prev = 0, current = -1, loaded, active, clicked, position, direction, imageParent, pauseTimeout, playInterval;
	
	var minideskPlugin = null, minideskSlide = null, animating  = false , mask = null;


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
		
		$LAB
		.script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/core_v2/view/IconPan.class.js")
		.script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/core_v2/view/GridLayoutIconPan.class.js")
		.script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/core_v2/view/Collection.class.js")
		.script(Constants.config.assetPrefix+"/platform/os/assets/javascripts/core_v2/view/Page.class.js")
   		.wait(function(){
			resize();
			
			_this.inited = true;
			_this.processQueue();
   		});
   		
   		return true;
	}
	
	/**
	 * 接受消息，并且处理
	 */
	this.notify = function(eventId,messageBody){
		//$.log(messageBody);
		$.log(this.getName()+" 收到消息 "+eventId);
		if(eventId == Constants.command.loadDesktopOK){
			option.pageDataList = messageBody;
			_this.reload({});
		}else if(eventId == Constants.command.clearActiveLink){
			$.each(option.pages,function(index,page){
				page.clearActiveLink();
			});
			hideAllCollections();
		}else if(eventId == Constants.command.switchDesktop){
			toggle();
		}else if(Constants.command.os.navDesktopPage == eventId){
			navPage(messageBody);
		}else if(eventId == Constants.command.os.iconDragStart){
			iconDragStart(messageBody);
		}else if(eventId == Constants.command.os.iconDragEnd){
			iconDragEnd(messageBody);
		}else if(eventId == Constants.command.showCollection){
			showCollection(messageBody);
		}else if(eventId == Constants.command.createCollection ){
			createCollection(messageBody);
		}else if(eventId == Constants.command.renameCollection ){
			renameCollection(messageBody);
		}else if(Constants.command.removeCollection == eventId){
			removeCollection(messageBody);
		}else if(Constants.command.moveIcon == eventId){
			moveIcon(messageBody);
		}else if(Constants.command.os.updateAppDeskMessage == eventId){
			updateAppDeskMessage(messageBody);
		}
	}
	
	var updateAppDeskMessage = function(_param){
		if(typeof _param == 'string'){
			_param = $.evalJSON(_param);
		}
		var app = _param.app;
		if(!app) return;
		for(var i in option.pages){
			var p = option.pages[i];
			if(!p) continue;
			var icon = p.findIconByApp(app);
			if(icon){
				p.updateAppMessage(icon,_param);
				return;
			}
		}
		for(var i in option.collections){
			var p = option.collections[i];
			if(!p) continue;
			var icon = p.findIconByApp(app);
			if(icon){
				p.updateAppMessage(icon,_param);
				return;
			}
		}
	}
	
	/**
	 * 根据index返回当前页对象
	 */
	this.getPage = function(index){
		return option.pages[index];
	}
	
	/**
	 * 返回当前打开的页
	 */
	this.getCurrentPage = function(){
		return option.pages[current];
	}

	var iconDragStart = function(){
		if(option.pageDataList.length < 20){
			showEmptyPage();
		}
		com.gleasy.os.view.Container.showDragMask();
	}

	var iconDragEnd = function(){
		com.gleasy.os.view.Container.hideDragMask();
		com.gleasy.os.IconPan.refreshAll();
		pageDataChange();
		hideEmptyPage();
		if(current > total - 1){
			_this.sendNotify(Constants.command.os.navDesktopPage,total-1);
		}
	}
	
	var moveIcon = function(_param){
		var from =_param.from;
		var to = _param.to;
		var icon = _param.icon;
		if(!from || !to || !icon) return;
		icon.draging = false;
		from.iconLeaveFrom(icon);	
		to.addIconObj(icon);
		to.refresh();
		
		for(var i=0;i<com.gleasy.os.IconPan.pans.length;i++){
				var _pan = com.gleasy.os.IconPan.pans[i];
				_pan.iconPanChange(icon,from,to);
		}
	}

	var createCollectionForIcon = function(icon){
		var pageData = new com.gleasy.os.model.PageData();
		pageData.pageId = icon.appId;
		pageData.name = icon.app;
		option.pageDataList.push(pageData);
		var collection = addCollection(pageData);
		icon.collection = collection;
		collection.setIconInPage(icon);
		return collection;
	}
	
	var showCollection = function(icon,silence){
		var found = false;
		var clt = null;
		for(var i=0;i<option.collections.length;i++){
			var collection = option.collections[i];
			if(icon.appId == collection.getId()){
				icon.collection = collection;
				collection.setIconInPage(icon);
				if(!silence){
					icon.view && icon.view.is(":visible") && collection.show(icon.view);
				}
				found = true;
				clt = collection;
			}else{
				collection.hide();
			}
		}		
		if(!found){
			var collection = createCollectionForIcon(icon);
			collection.show(icon.view);
			clt = collection;
		}
		return clt;
	}

	var renameCollection = function(icon){
		var collection = showCollection(icon);
		collection.rename();
	}

	var removeCollection = function(icon){
		for(var i=0;i<option.collections.length;i++){
			var collection = option.collections[i];
			if(icon.appId == collection.getId()){
				collection.destruct();
				option.collections.splice(i,1);
				return;
			}
		}
	}		
	
	var hideAllCollections = function(){
		$.each(option.collections,function(idx,collection){
			collection.hide();
		});		
	}
	
	var preventAppItemPropagation = function(ev){
		var iconLen = $(ev.target).closest("div.desktopAppIcon").length;
        if(iconLen >0){
         	ev.preventDefault();
          	ev.stopPropagation();
          	$.log("哥阻止了..");
          	return false;
        }
        return true;
	}
	
	
	/**
	 * 配置窗口处理事件
	 */
	var configWindowEvent = function(){
       	$(window).resize(function(){
		  resize();
		});
		
		/**
		 * 清除ACTIVE，关闭右键菜单
		 */
       option.desktop.bind("mousedown",function(ev){
       	  if(ev.button != 2){
       	       	 
	          var tags = ['a', 'button', 'input', 'select', 'textarea',".window","label","object"]; 
	       
	       	  var iconLen = $(ev.target).closest("div.desktopAppIcon").length;
	          if(iconLen >0){
	          	//ev.preventDefault();
	          //	ev.stopPropagation();
	          }else{
		          if(!$(ev.target).closest('.collectionPan').length) {
		           	hideAllCollections();
		           	if (!$(ev.target).closest(tags).length && $(ev.target).attr("contenteditable") != "true") {
		            	_this.sendNotify(Constants.command.clearActiveLink,{target:$(ev.target)});
		          	}
		          }
		             
		     }  
       	  } 	
       });
       

     
       option.desktop.delegate('div.desktopAppIcon','click', function(ev) {
			if(ev.button == 2){
				//右键
				ev.preventDefault();//阻止冒泡
          		ev.stopPropagation();
          		return false;
			}
			var __a =  $(this);
			var clicked = __a.data("clicked");
			if(clicked == '1'){
				return false;
			}
			__a.data("clicked","1");
           	window.application.sendNotify(Constants.command.clearActiveLink,null);
           	var icon = __a.data("model");
            if(!icon) {
                return;
            }
           	icon.pan.iconClick(icon);
           	setTimeout(function(){
           		__a.data("clicked","0");
           	},800);
        }).delegate('div.desktopAppIcon','mousedown', function(ev) {
        	var a =  $(this);
			if(ev.button != 2){
				if(!a.hasClass("desktopAppIconActive")){
          			a.addClass("desktopAppIconActive");
          		}
			}
        }).delegate('div.desktopAppIcon','mouseup', function(ev) {
        	var a =  $(this);
			if(ev.button == 2){
				var icon = a.data("model");
				if(icon.pan && icon.pan.options && icon.pan.options.iconInPage &&  icon.pan.options.iconInPage.type == 'collection'){
					
				}else{
					window.application.sendNotify(Constants.command.clearActiveLink,null);
				}
				
				ev.preventDefault();//阻止冒泡
          		ev.stopPropagation();
          		
          		window.application.sendNotify(Constants.command.os.showIconMenu,{x:ev.pageX,y:ev.pageY,icon:icon});
          		return false;
			}else{
				var a =  $(this);
        		a.removeClass("desktopAppIconActive");
			}
        }).delegate('div.desktopAppIcon','mouseleave', function(ev) {
        	if(com.gleasy.os.IconPan.dragIcon) return;
        	var a =  $(this);
        	a.removeClass("desktopAppIconHover");
        	a.removeClass("desktopAppIconActive");
        }).delegate('div.desktopAppIcon','mouseenter', function() {
          if(com.gleasy.os.IconPan.dragIcon) return;
        	  
          if(!$(this).hasClass("desktopAppIconHover")){
          	$(this).addClass("desktopAppIconHover");
          }
          
		  var __a =  $(this);
		  var icon = __a.data("model");
		  if(!icon || icon.type == 'addNew') return;
		  
		  var cursorAt = false;
		  var sys = getBrowserVersion();
		  var type = sys.type;
		  var version = sys.version;
		  //if(type == 'ie' && version == '8.0'){
		  	cursorAt = { left: 15,top:15 };
		  //}
		  $(this).die('mouseenter').draggable({
		    revert: false,
		    scroll:false,
		    cursorAt:cursorAt,
		    helper:function(){ 	
		    	var c = __a.clone();
		    	c.removeClass("desktopAppIconActive");
		    	c.css("zIndex",Constants.config.os.layer.dragingIcon);
		    	c.appendTo(option.desktop);
		    	__a.data("clone",c);
		    	icon.helper = c;
		    	return c;
		    },
		    start:function(evt){
		    	if(icon && icon.type == 'collection'){
		    		hideAllCollections();
		    	}
          		__a.removeClass("desktopAppIconActive");
		    	__a.removeClass("desktopAppIconHover");
		   		com.gleasy.os.IconPanDragHandler.dragStart(icon);
		    },
		    drag:function(evt){
		    	com.gleasy.os.IconPanDragHandler.draging(evt);
		    },
		    stop:function(evt){
		    	com.gleasy.os.IconPanDragHandler.dragStop(evt);       	
		    }
		  });
        }).delegate('div.slides_control','mouseenter', function() {
          	$(this).die('mouseenter').draggable({
					revert:false,
					axis:'x',
					cursor:'hand',
					disabled:false,
					handle:'.bg_transparent,.widget_column',
				  	cancel:'div.desktopAppIcon,a.configtool,a.configMenu,div.widget_window',
					start:function(){
				 		var initpos = $(this).position().left;
				 		$(this).data("initpos",initpos);
				 		$(this).data("offset",0);
				 		$(this).data("drag_cancel",false);
				  	},
				  	drag:function(){
				  		bgDraging = true;
				  		var initpos = $(this).data("initpos");
				  		var newPos = $(this).position().left;
				        var	offset = newPos - initpos;
				 		$(this).data("offset",offset);
				 	},
					stop:function(){
						var cancel = $(this).data("drag_cancel");
						if(cancel){
							bgDraging = false;
							$(this).draggable("option","revertDuration",1);
							$(this).draggable("option","revert",true);
							return;
						}
						
						bgDraging = false;
						var offset = $(this).data("offset");
				      	if(offset >100 && current>0){
				      		navPage(current-1);
				      	}else if(offset<-100 && current < total-1){
				      		navPage(current+1);
				        }else{
				      		navPage(current);
				        }
				   	}
			});
		}).delegate('div.slides_control','mouseleave', function(ev) {
			if(bgDraging){
				$(this).data("drag_cancel",true);
				$(this).draggable("option","revertDuration",100);
				$(this).draggable("option","revert",true);
				$(this).trigger("mouseup");
			}
		}).delegate('div.slides_control','mouseup', function(ev) {
        	var a =  $(this);
			if(ev.button == 2){
				window.application.sendNotify(Constants.command.clearActiveLink,null);
				ev.preventDefault();//阻止冒泡
          		ev.stopPropagation();
          		var icon = a.data("model");
          		window.application.sendNotify(Constants.command.os.showIconMenu,{x:ev.pageX,y:ev.pageY,icon:{type:"os",pan:_this.getCurrentPage()}});
          		return false;
			}
        });
	}


	
	/**
	 * 解决IE下透明的DIV无法感应鼠标事件的问题而加的方法
	 * 用于生成一个满屏的儿子DIV，这样父亲就可以感应事件了
	 */
	var createBgDiv = function(){
		var empty = $("<div>");
		empty.addClass("bg_transparent");
		return empty;
	}
	
	/**
	 * 创建遮罩层,用于在显示效果时屏蔽用户的手动动作
	 */
	var createTransparentMask = function(){
		if(mask) return;
		mask = $("<div class='abs mask'>");
		mask.appendTo(option.render);
		mask.hide();
	}
	
	/**
	 * 生成空白PAGE页
	 */
	var createEmptyPage = function(){
		var emptyPage = new com.gleasy.os.model.PageData();
		emptyPage.pageId = emptyId;
		emptyId--;
		return emptyPage;
	}
	
	var showEmptyPage = function(){
		if(emptyPageData == null){
			emptyPageData = createEmptyPage(); 
			addPageData(emptyPageData);
			_this.sendNotify(Constants.command.os.resetTopNavBar,{total:total,current:current,flash:total-1});
		}
	}
	
	var hideEmptyPage = function(){
		if(emptyPageData != null){
			deletePageData(emptyPageData);
			emptyPageData = null;
			refresh();
		}
	}	
	
	/**
	 * 往模型里添加页
	 */
	var addPageData = function(pageData){
		option.pageDataList.push(pageData);
		addPage(pageData);
		if(emptyPageData === pageData) return;
		//_this.sendNotify(Constants.command.desktopDataChangeByUser,pageData);
	}
	
	var getMinCollectionId = function(){
		var min = collectionId;
		for(var i=0;i<option.collections.length;i++){
			var collection = option.collections[i];
			if(collection.getId() < min) min = collection.getId();
		}
		return min;
	}
	
	var createCollection = function(iconPan,collectionName){
		var pageData = new com.gleasy.os.model.PageData();
		pageData.pageId = getMinCollectionId()-1;
		option.pageDataList.push(pageData);
		var collection = addCollection(pageData);
			
		if(iconPan && typeof iconPan.createCollection == 'function'){
			iconPan.createCollection(pageData.pageId,collection,collectionName);
		}
		
		return collection;
	}


		
	var addCollection = function(pageData){
		var collection = new com.gleasy.os.Collection({
			pageData:pageData,
			name:pageData.name,
			dataChange:function(){
				collection.updateIcon();
 				dataChanged = true;
 				_this.sendNotify(Constants.command.desktopDataChangeByUser,{});
		 	}
		});
		option.collections.push(collection);
		return collection;
	}
	
	/**
	 * 生成页的SLIDE展示组件
	 */
	var addPage = function(pageData){
		if(emptyPageData !== pageData && pageData.datasource.length <=0 ) return;
		
		width = option.render.width() ;
		height = option.render.height() ;

   		
		var slide = $("<div/>");			
		var p = new com.gleasy.os.Page({
		 			container:slide,
		 			datasource:pageData.datasource,
		 			pageId:pageData.pageId,
		 			showGrid:false,
		 			pageData:pageData,
		 			desktop:_this,
		 			dataChange:function(pp,ds){
		 				dataChanged = true;
		 				_this.sendNotify(Constants.command.desktopDataChangeByUser,{});
		 			},
		 			width:width,
		 			height:height
		});
		p.setIndex(option.pages.length);
		p.setName("桌面"+(option.pages.length+1));
		option.pages.push(p);
		//$.log("adding page....");
		control.append(slide);
		slide.addClass("slide");	
		slide.append(createBgDiv());
		refresh();		
		p.resize(width,height);
	}
	
	/**
	 * 页内容改变监听事件
	 * 1. 如果是最后一页(空白页)，而且有了内容，那么就生成额外一个空白页
	 * 2. 如果是我们程序生成的空白页,而且现在没有内容了,删除它.
	 */
	var pageDataChange = function(){
		if(!dataChanged) return;
		dataChanged = false;
		var toDelete = [];
		var needToNav = false;
		for(var i=0;i<option.pages.length;i++){
			var page = option.pages[i];
			var pageData = page.getPageData();		
			if(emptyPageData != null){
				var emptyPageId = emptyPageData.pageId;
				var pageId = page.getId();
				if(pageId == emptyPageId && !page.isEmpty())	{
					emptyPageData = null;
					continue;
				}
			}
			if(page.isEmpty()){
				if(i<=current){
					needToNav = true;
				}
				toDelete.push(pageData);
			}
		}
		for(var i=0;i<toDelete.length;i++){
			if(option.pageDataList.length == 1) continue;
			var pageData = toDelete[i];
			deletePageData(pageData);
		}
		refresh();
		if(needToNav){
			navPage(current);
		}
	}
	
	var navPage = function(pageNo){	
		if(pageNo<0) pageNo=0;
		if(pageNo>total) pageNo=total-1;
		current = pageNo;
		if(!control) return;
		
		var left = (current) * width;
		try{
			if(animating){
				control.stop(true,true);
			}
			animating = true;
			control.animate({
				left:-left
			},200,function(){
				animating = false;
				if(com.gleasy.os.IconPan.dragIcon){
					com.gleasy.os.IconPanDragHandler.draging(null,true);
				}
			});
		}catch(e){
			control.css({
				left:-left
			});
		}
		
		hideAllCollections();
		_this.sendNotify(Constants.command.os.changeDesktopPage,current);		
	}
	
	var animateControl = function(css,time){
		
	}
	
	
	/**
	 * 删除页对应的slide组件
	 */
	var deletePage = function(pageData){
		var id = pageData.pageId;
		for(var i=0;i<option.pages.length;i++){
			var page = option.pages[i];
			if(page.getId() ==  id){
				option.pages.splice(i,1);
				page.container().detach();
				page.destruct();
				if(current>i){
					//如果当前页在删除页的后面，要重新定位当前页的index
					current = current - 1;
				}
				total =  option.pages.length+1;
				return;
			}
		}
		for(var i=0;i<option.pages.length;i++){
			var page = option.pages[i];
			page.setIndex(i);
		}
	}
	/**
	 * 从模型里删除Page
	 */
	var deletePageData = function(pageData){
		var index = $.inArray(pageData,option.pageDataList);
		if(index >= 0){
			option.pageDataList.splice(index,1);
		}
		deletePage(pageData);
		if(emptyPageData === pageData) return;
		//_this.sendNotify(Constants.command.desktopDataChangeByUser,pageData);
	}
	
	/**
	 * 判断页是否已经创建了，避免重复创建页
	 */
	var findPageByID = function(pageId){
		for(var i=0;i<option.pages.length;i++){
			var page = option.pages[i];
			if(page.getId() ==  pageId){
				return page;
			}
		}
		return null;
	}
	
	
	var _createSysCollection = function(page){
		for(var i=0;i<option.collections.length;i++){
			var collection = option.collections[i];
			var n = collection.getName();
			$.log(n);
			if(n == '系统应用'){
				return collection;
			}
		}
		return createCollection(page,"系统应用");		
	}
	var createSysCollection = function(){
		var sysPrograms = [];
		com.gleasy.os.reg.getInstalledApps({
			callback:function(programs){
				for(var app in programs){
					var program = programs[app];
					if(program.app == app && program.type == 'sys' && !program.silence && !isAppExists(program.app)){
						if($.inArray(program,sysPrograms) < 0){
							sysPrograms.push(program);
						}
					}
				}

				if(sysPrograms.length){
					if(!option.pages.length){
						showEmptyPage();
					}
					var collection = _createSysCollection(option.pages[0]);
					for(var i=0;i<sysPrograms.length;i++){
						var program = sysPrograms[i];
						if(!collection.findIconByApp(program.app)){
							collection.addIcon(program.app);
						}
					}
					collection.refresh();
					option.pages[0].refresh();
				}
			}
		});
	}
	
	var isAppExists = function(appName){
		for(var i=0;i<option.pageDataList.length;i++){
			var pageData = option.pageDataList[i];
			var ds = pageData.datasource;
			for(var j=0;j<ds.length;j++){
				if(ds[j].app == appName){
					return ds[j];
				}
			}
		}
		return null;	
	}
	
	
	var updateCollectionIconRelation = function(icon){
		showCollection(icon,true);
	}
		
	/**
	 * 根据数据源生成Page对象并缓存
	 */
	var updateDatasource = function(){
		for(var i=0;i<option.pageDataList.length;i++){
			var pageData = option.pageDataList[i];	
			if(findPageByID(pageData.pageId)) continue;
			if(pageData.pageId <= -10000){
				//合集
				addCollection(pageData);
				continue;
			}
		}		
		for(var i=0;i<option.pageDataList.length;i++){
			var pageData = option.pageDataList[i];	
			if(findPageByID(pageData.pageId)) continue;
			if(pageData.pageId <= -10000){
				continue;
			}
			if(pageData.pageId <= -1000){
				//PIN区
				continue;
			}
			//桌面页
			addPage(pageData);
			
			for(var k=0;k<pageData.datasource.length;k++){
				var icon = pageData.datasource[k];
				if(icon.type == 'collection'){
					updateCollectionIconRelation(icon);
				}
			}
			
		}
		
		createSysCollection();
	}
	
	/**
	 * 根据浏览器窗口大小调整本桌面控件的大小
	 */
	var resize = function(){
		var totalWidth = document.body.clientWidth;
		var totalHeight = document.body.clientHeight - option.top - option.bottom;
		
		if(totalWidth < 800 ) totalWidth = 800;
		
		if(option.desktop){
			/**
			 * #desktop
			 */
			option.desktop.width(totalWidth);
			option.desktop.height(totalHeight);
			option.desktop.css({top:option.top});
		}
		
		if(option.render){
			/**
			 * #desktop.slides
			 */
			option.render.width(totalWidth);
			option.render.height(totalHeight);
		}
		
		
		width = totalWidth;
		height = totalHeight;
		
				
		$.each(option.pages,function(index,page){
			page.resize(totalWidth,totalHeight);
		});
		
		refresh();
		
		if(current > 0){
			navPage(current);
		}
	}
	
	/**
	 * 初始化入口
	 */
	this.reload = function(params){
		$.each(option.pages,function(index,page){
			page.destruct();
		});
		if(control){
			control.remove();
		}
		
		option.desktop.css("zIndex",Constants.config.os.layer.desktop);
		option.pages = [];
		$.extend( option, params );	
		
		elem = option.render;
		if(elem == null) return;

		createTransparentMask();
		
		control = $("<div class='slides_control' />");
		elem.append(control);
		
		/**
		 * 设定SIZE(根据分辨率)
		 */
		resize();	

	
		//addMiniDesktop();
		
		/**
		 * 根据数据源生成页
		 */
		updateDatasource();
		
		
		_init();
		
		
		_this.sendNotify(Constants.command.os.resetTopNavBar,{total:total,current:current});
		
		navPage(0);
	}

	
	
	/**
	 * 动态更改内容时使用
	 */
	var refresh = function(){
		total =  option.pages.length;
		if(control){
			control.css({
				width:total * option.render.width()
			});
		}
		//navPage(current);
		_this.sendNotify(Constants.command.os.resetTopNavBar,{total:total,current:current});
	}
	
	var _init = function(){
			total =  option.pages.length;

			// 2 or more slides required
			if (total < 2) {
				return;
			}
			
			// error corection for start slide
			if (start < 0) {
				start = 0;
			}
			
			if (start > total) {
				start = total - 1;
			}
			
	
	}


	
	
	/**
	 * 模拟构造函数
	 */
	var _construct = function(){
		_this.setInterests(interests);
		configWindowEvent();

	}
	
	
	//构造函数调用
	_construct();
}
//继承MvcBase
com.gleasy.os.view.DesktopView.prototype = new com.gleasy.libs.MvcBase(com.gleasy.os.view.DesktopView.instance);


com.gleasy.os.view.DesktopView.getCurrentPage = function(){
	return com.gleasy.os.view.DesktopView.instance.getCurrentPage();	
}

com.gleasy.os.view.DesktopView.instance = new com.gleasy.os.view.DesktopView();


//向application中注册本View
window.application.addMvc(com.gleasy.os.view.DesktopView.instance);
