/**
 * Gleasy OS 容器顶级类
 * 所有可以添加进OS的窗体全部继承自本类
 * 本类所提供的功能如下：
 * 1. 窗口可移动
 * 2. 窗口可设置title,icon
 * 3. 窗口可resize（拖动设定大小）
 * 4. 窗口可以关闭
 * 5. 窗口可以最大化
 * 6. 窗口可以最小化
 * 7. 窗口标题栏双击最大化
 * 8. 窗口标题栏图标双击关闭
*/
Namespace.register("com.gleasy.os.view");

com.gleasy.os.view.Container = function (param) {
    var _this = this;

    this.view = null;

    var focused = false;
    var contentMask = null;
    var loadMask = null;

    var maxed = false;
    var body = null;
    var wid = null;
    var zIndex = 0;
    var modalMask = null;
    var modalShow = false;

    var focuser = null;
    var url = null;
    var urlLoaded = false;

    var proxy = null;
    var exitConfirm = null;
    var dragMask = null;
    this.parent = null;

    this.dragHandle = "";
    this.children = {};
    this.focusChild = null;
    this.visible = false;
    var sizeBack = null;
    var clickOutMap = [];
    var mouseupOutMap = [];
    var iframe = null;
    var filesReceived = null;
    var selectedUsers = null;
    var selectedTopics = null;
	var destroyed = false;
	
    var dragHelper = null;

    var closeHandler = null;

    var fullscreened = false;
    var _hasFrame = null;
    var openUrlAnchor = null;

    var frameWidthAdded = false;

    var fullscreenContext = {

    };
    var minSize = {
        width :100,
        height:100
    };
    var maxSize = {
        width :-1,
        height:-1
    };

    var containerSize = {
        width :100,
        height:100
    };

    this.zindexChange = function () {
    };

    this.setZindex = function (index) {
        zIndex = index;
        _this.view.css("zIndex", zIndex);
        $.each(_this.children, function (index, child) {
            index != 'null' && child != null && child.zindexChange();
        });
        if (modalMask != null) {
            modalMask.css("zIndex", zIndex + 1);
        }
    };


    this.getZindex = function () {
        return zIndex;
    };

    this.options = {
        pid          :null, //所属进程ID
        parent       :null, //父容器,如果为null,则父容器为OS容器
        container    :$(".desktop"),
        title        :"", //标题
        icon         :"", //标题栏的图标
        canMax       :1,
        canMin       :1,
        canResize    :1,
        canFullscreen:1,
        hasFrame     :1,
        hasTitle     :1,
        position     :{top:10, left:10},
        wid          :null,
        files        :null,
        size         :{width:400, height:300},
        app          :"未知应用"
    };

    var listeners = {
        "focus"  :null,
        "blur"   :null,
        "resize" :null,
        "move"   :null,
        "close"  :null,
        "min"    :null,
        "max"    :null,
        "restore":null,
        "destroy":null
    };

    var _construct = function () {
        $.extend(_this.options, param);
        filesReceived = _this.options.files;

        if (_this.options.initPosition && _this.options.initPosition.length) {
            _this.options.position = {
                top :_this.options.position[0],
                left:_this.options.position[1]
            };
        }
        if (_this.options.initSize && _this.options.initSize.length) {
            _this.options.size = {
                width :_this.options.initSize[0],
                height:_this.options.initSize[1]
            };
        }

        wid = UuidGenerator.uuid("f");
        _this.options.wid = wid;
        _this.repaint();
        _this.setXY(_this.options.position.left, _this.options.position.top);
        _this.setSize(_this.options.size.width, _this.options.size.height);
        registerCommand();
        com.gleasy.os.view.Container.factory[wid] = _this;
        if (_this.options.parent != null) {
            _this.options.pid = _this.options.parent.getPid();
        }

        return wid;
    };

    this.setParent = function (_parent) {
        _this.parent = _parent;
    };

    this.showLoading = function (show) {
        showLoading(show);
    };

    var showLoading = function (show) {
        if (show) {
            $(".oLoadingWrap", _this.view).show();
        } else {
            $(".oLoadingWrap", _this.view).hide();
        }
    };

    this.setFilesReceived = function (fs) {
        filesReceived = fs;
        _this.noticeListener("cloudDiskFileDrop", filesReceived);
    };

    this.setMinSize = function (minWidth, minHeight) {
        minSize.width = minWidth;
        minSize.height = minHeight;
    };

    var getMinSize = function () {
        return {
            width :minSize.width + _this.getFrameSize().width,
            height:minSize.height + _this.getFrameSize().height
        }
    };

    this.setMaxSize = function (maxWidth, maxHeight) {
        maxSize.width = maxWidth;
        maxSize.height = maxHeight;
    };

    var getResizeMaxSize = function () {
        var obj = {
            width :maxSize.width < 0 ? null : maxSize.width + _this.getFrameSize().width,
            height:maxSize.height < 0 ? null : maxSize.height + _this.getFrameSize().height
        };
        return obj;
    };

    this.getFrameSize = function () {
        var o = {};
        if (_this.hasFrame() == 1) {
            o.width = 8;
            o.height = $('.oWindowTop', _this.view).height();
        } else {
            o.width = o.height = 0;
        }
        return o;
    };

    this.setFocusChild = function (ch) {
        _this.focusChild = ch;
        if (_this.parent != null) {
            _this.parent.setFocusChild(ch);
        }
    };

    this.getPid = function () {
        return     _this.options.pid;
    };

    this.hideDragMask = function () {
        if (dragMask == null) {
            dragMask = contentMask.clone();
            dragMask.bind('mousedown', function () {
                dragMask.hide();
            });
            dragMask.appendTo(contentMask.parent());
        }
        dragMask.hide();
    };
    this.showDragMask = function () {
        if (dragMask == null) {
            dragMask = contentMask.clone();
            dragMask.appendTo(contentMask.parent());
            dragMask.bind('mousedown', function () {
                dragMask.hide();
            });
        }
        dragMask.show();
    };
    this.setVisible = function (show) {
        $.each(_this.children, function (index, child) {
            index != 'null' && child != null && child.setVisible(show);
        });
        if (show) {
            _this.show();
        } else {
            _this.hide();
        }
    };

    this.hide = function () {
//		_this.view.css({
//				width:0,
//				height:0
//		});
        _this.view.css({
            left :-4000,
            right:0
        });

        modalMask && modalMask.hide();
        //_this.view.hide();
        _this.visible = false;
    };
    this.show = function () {
//		_this.view.css({
//				width:options.size.width,
//				height:options.size.height
//		});
        _this.view.css({
            left:_this.options.position.left,
            top :_this.options.position.top
        });
        modalShow && modalMask && modalMask.show();
        //_this.view.show();
        _this.visible = true;
    };
    this.addChild = function (container) {
        var wid = container.getWid();
        //$.log("adding child."+wid);
        _this.children[wid] = container;
    };

    this.removeChild = function (container) {
        var wid = container.getWid();
        //$.log("removing child."+wid);
        var obj = _this.children;
        obj[wid] = null;
        delete obj.wid;
    };

    this.doInterFocus = function () {
        contentMask.hide();

        var c = 0;
        $.each(_this.children, function (index, child) {
            index != 'null' && child != null && c++;
        });
        if (c <= 0) {
            if (url) {
                iframe.focus();
            }
        }
    };

    this.showModal = function (show) {
        if (show) {
            modalShow = true;
            if (modalMask == null) {
                modalMask = $("<div class='modalMask mdBg'/>");
                modalMask.addClass("abs");
                _this.view.parent().append(modalMask);
                modalMask.bind("mousedown", function () {
                    _this.view.trigger('mousedown');
                    if (_this.focusChild != null) {
                        _this.focusChild.showFocusWarning();
                    }
                });
            }
            //$.log("index="+_this.getZindex());
            modalMask.css({
                top   :_this.view.position().top,
                left  :_this.view.position().left,
                width :_this.view.width() + 1,
                height:_this.view.height() + 1,
                zIndex:_this.getZindex() + 1
            });

            modalMask.show();
        } else {
            modalShow = false;
            if (modalMask != null) {
                modalMask.hide();
            }
        }
    };

    var flashing = false;
    this.showFocusWarning = function () {
        if (flashing) return;
        flashing = true;
        var s = 0;
        var interval = setInterval(function () {
            if (s > 5) {
                clearInterval(interval);
                flashing = false;
                 _this.view.addClass('oWindow_current');
                return;
            }
            s++;
            if (s % 2 == 0) {
                if (!_this.view.hasClass('oWindow_current')) {
                    _this.view.addClass('oWindow_current');
                }
            } else {
                if (_this.view.hasClass('oWindow_current')) {
                    _this.view.removeClass('oWindow_current');
                }
            }
        }, 50);
    };

    this.addDragHandler = function (elm) {
        if (typeof elm == 'string') {
            _this.dragHandle += "," + elm;
        } else if (typeof elm == 'object') {
            $.each(elm, function (index, item) {
                _this.dragHandle += "," + item;
            });
        }
    };

    this.isFocus = function () {
        return false;
    };

    this.tryFocus = function () {
        if (_this.parent != null) {
            _this.parent.tryFocus();
        }
    };

    this.setContent = function (content) {
        var contentPane = _this.getContentPane();
        contentPane && contentPane.children() && contentPane.children().detach();
        contentPane.append(content);
    };

    this.getModalMask = function () {
        return modalMask;
    };


    this.getXY = function () {
        return {x:_this.options.position.left, y:_this.options.position.top};
    };

    this.getSize = function () {
        return {width:_this.options.size.width, height:_this.options.size.height};
    };

    this.getContentSize = function () {
        var contentView = $(".oWindowBody", _this.view);
        return {width:contentView.width(), height:contentView.height()};
    };


    this.repaint = function () {
        _repaintAll();

        _this.doResize();
    };

    var _repaintAll = function () {
        if (_this.view == null) {
            _this.dragHandle += ".oWindowTop";
            _this.view = $("#util_div .oWindow").clone();
			_this.view.attr("wid",_this.options.wid);

            //dragHelper = $("<div class='window_ui_helper' style='position:absolute;'/>");
            //_this.view.append(dragHelper);


            contentMask = $("<div class='window_mask bg_transparent' style='position:absolute;'/>");
            $(".oWindowBody", _this.view).append(contentMask);
            contentMask.css({
                top   :0,
                left  :0,
                width :"100%",
                zIndex:9,
                height:"100%"
            });
            contentMask.hide();

            focuser = $("<input type='text' />");
            focuser.css({
                width  :30, height:30, top:0, left:-9999, position:"absolute", zIndex:9999,
                opacity:'.0',
                filter :"alpha(opacity=0)"
            });
            _this.view.append(focuser);

//            if (_this.options.icon == null || _this.options.icon == '') {
//                _this.options.icon = Constants.config.assetPrefix + "/platform/os/assets/images/img/default.png";
//            }

            //$(".oWindowTitle",_this.view).html(_this.options.title);

            if (_this.options.canMin != 1) {
                $("div.oWindowBtnBar a.oWinMin", _this.view).hide();
            }
            if (_this.options.canMax != 1) {
                $("div.oWindowBtnBar a.oWinMax", _this.view).remove();
                $("div.oWindowBtnBar a.oWinRestore", _this.view).remove();
            }

            _this.view.appendTo(_this.options.container);
            configEvent();
        }
        $(".oWindowTitle", _this.view).html(_this.options.title);
        if (_this.options.hasFrame == 1) {
            _this.showFrame();
        } else {
            _this.hideFrame();
        }
        _this.refreshContainerSize();
    };

    this.hasFrame = function () {
        return _hasFrame;
    };

    this.showFrame = function (_param) {
        var old = _hasFrame;

        if (_hasFrame == 1) {
            _param && typeof _param.callback == 'function' && _param.callback({hasFrame:old});
            return;
        }
        _this.options.hasFrame = 1;
        _hasFrame = 1;
        $("div.oWindowTop", _this.view).show();
        if (_this.view.hasClass("noframe")) {
            _this.view.removeClass("noframe");
        }
//		if(maxed){
//			$("div.oWindowBtnBar a.oWinMax",_this.view).hide();
//			$("div.oWindowBtnBar a.oWinRestore",_this.view).show();
//		}else{
//			$("div.oWindowBtnBar a.oWinMax",_this.view).show();
//			$("div.oWindowBtnBar a.oWinRestore",_this.view).hide();
//		}

        _this.refreshContainerSize();
        if (maxed) {
            _this.doResize();
            _this.noticeListener("resizeFinish", _this.getContentSize());
        }
        _param && typeof _param.callback == 'function' && _param.callback({hasFrame:old});
    };

    this.osFocus = function () {
        focuser.focus();
    };

    this.hideFrame = function (_param) {
        var old = _hasFrame;

        if (_hasFrame == 0) {
            _param && typeof _param.callback == 'function' && _param.callback({hasFrame:old});
            return;
        }
        _this.options.hasFrame = 0;
        _hasFrame = 0;
        $("div.oWindowTop", _this.view).hide();
        if (!_this.view.hasClass("noframe")) {
            _this.view.addClass("noframe");
        }
        _this.refreshContainerSize();
        if (maxed) {
            _this.doResize();
            _this.noticeListener("resizeFinish", _this.getContentSize());
        }
        _param && typeof _param.callback == 'function' && _param.callback({hasFrame:old});
    };


    this.refreshContainerSize = function () {
        if (maxed) {
            _this.view.css({
                'top'   :'0',
                'left'  :'0',
                'right' :'0',
                'bottom':'0',
                'width' :'100%',
                'height':_this.options.container.height() - $(".oTaskOnBottom").height() - 36
            });
        } else {
            var w = _this.options.size.width;
            if (!frameWidthAdded) {
                w += _this.getFrameSize().width;
                frameWidthAdded = true;
            }
            _this.view.css({
                top   :_this.options.position.top,
                left  :_this.options.position.left,
                width :w,
                height:_this.options.size.height + _this.getFrameSize().height
            });
        }
    };


    var configEvent = function () {
        _this.addEventListener("blur", function (param) {
            $.each(clickOutMap, function (k, v) {
                var o = v;
                try{typeof o.fn == 'function' && o.fn();}catch(e){$.log(e);$.log(o.fn);}
            });
            $.each(mouseupOutMap, function (k, v) {
                var o = v;
                try{typeof o.fn == 'function' && o.fn();}catch(e){$.log(e);$.log(o.fn);}
            });
        });

        contentMask.bind("mousedown", function (evt) {
            contentMask.hide();
            _this.noticeListener("mousedown", evt);
            evt.preventDefault();//阻止冒泡
            evt.stopPropagation();
        });

        _this.addEventListener("mousedown", function (evt) {
            $.each(clickOutMap, function (k, v) {
                var o = v;
                if (!$(evt.target).closest(o.dom).length) {
                    try{typeof o.fn == 'function' && o.fn(evt);}catch(e){$.log(e);$.log(o.fn);}
                }
            });
        });

        _this.view.bind("mouseup", function (evt) {
            $.each(mouseupOutMap, function (k, v) {
                var o = v;
                if (!$(evt.target).closest(o.dom).length) {
                    try{typeof o.fn == 'function' && o.fn(evt);}catch(e){$.log(e);$.log(o.fn);}
                }
            });
        });

        _this.view.bind("mousedown",function (evt) {
/*       		var tags = ['a', 'button', 'input', 'select', 'textarea',".window","label","object"]; 
	       	if (!$(evt.target).closest(tags).length && $(evt.target).attr("contenteditable") != "true") {
	           for(var i=0;i<clickOutMap.length;i++){
	        		var k = clickOutMap[i].dom;
	        		if($(evt.target).closest(k)){
	        			evt.preventDefault();//阻止冒泡
	                	evt.stopPropagation();
	        		}
	        	}
				for(var i=0;i<mouseupOutMap.length;i++){
	        		var k = mouseupOutMap[i].dom;
	        		if($(evt.target).closest(k)){
	        			evt.preventDefault();//阻止冒泡
	                	evt.stopPropagation();
	        		}
	        	}
	        }     
 */       	
        	
            $(this).draggable('option', 'handle', _this.dragHandle);
            _this.noticeListener("mousedown", evt);

            $(this).draggable("option", "disabled", modalShow || maxed);
            $(this).resizable("option", "disabled", modalShow || maxed || _this.options.canResize != 1);
     	}).bind('mouseenter',function (evt) {
 			var obj = com.gleasy.os.Clipboard.dragData;
            if (obj) {
            	if(!_this.isFocus()){
                	window.application.sendNotify(Constants.command.os.frame.focus,{wid:_this.options.wid});
                	if(url){
                		_this.showMask();
                	}
            	}
            }
 			var win = $(this);
            $(this).die('mouseenter').draggable({
                handle     :_this.dragHandle,
                helper     :"window_ui_helper",
                scroll     :false,
                cancel     :'a,textarea,input,select,.undraggable',
                start      :function () {
                    com.gleasy.os.view.Container.showDragMask();
                },
                stop       :function () {
                    _this.options.position = {
                        top :win.position().top,
                        left:win.position().left
                    };
                    com.gleasy.os.view.Container.hideDragMask();
                    _this.noticeListener("move", {x:_this.options.position.left, y:_this.options.position.top});
                },
                containment:[0 - $(this).width() + 20, -10, $(this).parent().width() - 20, $(this).parent().height() - $(".oTaskOnBottom").height() - 20]
            }).resizable({
                    containment:'parent',
                    // helper:dragHelper,
                    minWidth   :getMinSize().width,
                    minHeight  :getMinSize().height,
                    maxWidth   :getResizeMaxSize().width,
                    maxHeight  :getResizeMaxSize().height,
                    handles    :'all',
                    start      :function () {
                        com.gleasy.os.view.Container.showDragMask();
                    },
                    stop       :function () {
                        com.gleasy.os.view.Container.hideDragMask();
                        _this.noticeListener("resizeFinish", _this.getContentSize());
                    },
                    resize     :function () {
                        _this.doResize();
                    }
                });
            return false;
        }).find('div.oWindowTop').bind('dblclick', function (ev) {
            if (!maxed) {
                _this.maximize();
                _this.noticeListener("maxByUser", "");
            } else {
                _this.unmaximize();
                _this.noticeListener("unMaxByUser", "");
            }
            return false;
        });


        _this.view.find('div.oWindowBtnBar a.oWinClose').bind("click", function () {
            _this.close();
            // 阻止冒泡
            return false;
        });

        _this.view.find('div.oWindowBtnBar a.oWinMax').bind("click", function () {
            _this.maximize();
            _this.noticeListener("maxByUser", "");
            // 阻止冒泡
            return false;
        });

        _this.view.find('div.oWindowBtnBar a.oWinMin').bind("click", function () {
            _this.minimize();
            // 阻止冒泡
            return false;
        });

        _this.view.find('div.oWindowBtnBar a.oWinRestore').bind("click", function () {
            _this.unmaximize();
            _this.noticeListener("unMaxByUser", "");
            // 阻止冒泡
            return false;
        });

        _this.view.bind("dragenter",
            function (evt) {
                $.log("dragenter container");
                _this.dragEnterHandler();
                evt.preventDefault();//阻止冒泡
                evt.stopPropagation();
            }).bind("dragexit dragleave",
            function (evt) {
                $.log("dragleave container");
                _this.dragLeaveHandler();
                evt.preventDefault();//阻止冒泡
                evt.stopPropagation();
            }).bind("dragover",
            function (evt) {
                $.log("dragover container");
                evt.preventDefault();//阻止冒泡
                evt.stopPropagation();
            }).bind("drop",
            function (evt) {
                $.log("drop container");
                com.gleasy.os.view.Container.hideDragMask();
                var obj = evt.originalEvent.dataTransfer;
                if (obj) {
                    var obj = evt.originalEvent.dataTransfer;
                    if (!_this.dropHandler(obj,evt)) {
                        evt.preventDefault();//阻止冒泡
                        evt.stopPropagation();
                    }
                }
            }).bind("mouseup", function (evt) {
                var obj = com.gleasy.os.Clipboard.dragData;
                //var json = $.toJSON(obj);

                if (obj) {
                    var p = {
                        getData:function () {
                            return obj;
                        }
                    };

                    if (!_this.dropHandler(p,evt)) {
                        //evt.preventDefault();//阻止冒泡
                        //evt.stopPropagation();
                    }
                }
            });


        $(window).resize(function () {
            if (fullscreened) {
                _this.doResize();
                _this.noticeListener("resizeFinish", _this.getContentSize());
            } else if (maxed) {
                _this.view.css({
                    'top'   :'0',
                    'left'  :'0',
                    'right' :'0',
                    'bottom':'0',
                    'width' :'100%',
                    'height':_this.options.container.height() - $(".oTaskOnBottom").height()
                });
                _this.doResize();
                _this.noticeListener("resizeFinish", _this.getContentSize());
            }

        });
    };

    this.initDragData = function (data) {
        if (!data) return;
        com.gleasy.os.Clipboard.dragData = data;
    };

    this.clearDragData = function () {
        if (!com.gleasy.os.Clipboard.dragData) return;
        com.gleasy.os.Clipboard.dragData = null;
    };

    this.getDragData = function () {
        return com.gleasy.os.Clipboard.dragData;
    };

    this.dragEnterHandler = function () {
        if (!url) {
            _this.hideDragMask();
        }
    };

    this.dragLeaveHandler = function () {
//        if (!url) {
//            _this.showDragMask();
//        }
    };

    this.registerClickOutsideEvent = function (dom, fn) {
        clickOutMap.push({
            dom:dom,
            fn :fn
        });
    };

    this.registerMouseupOutsideEvent = function (dom, fn) {
        mouseupOutMap.push({
            dom:dom,
            fn :fn
        });
    };

    this.topDropHandler = function (dataTransfer) {
        var dragData = dataTransfer.getData("Text");
        if (!dragData) return;
        var dragObj = dragData;
        if (typeof dragData == 'string') {
            dragObj = $.evalJSON(trimZero(dragData));
        }
        var type = dragObj.type;
        if ('cloudDiskFile' == type) {
            filesReceived = dragObj.message;
            _this.noticeListener("cloudDiskFileDrop", filesReceived);
        } else if ('dragLinks' == type) {
            selectedUsers = dragObj.message;
            _this.noticeListener("linksDrop", selectedUsers);
        } else if ('dragTopics' == type) {
            selectedTopics = dragObj.message;
            _this.noticeListener("topicsDrop", selectedTopics);
        } else {
            //TODO 其它抓的消息
        }
    };

    this.dropHandler = function (dataTransfer) {
        _this.topDropHandler(dataTransfer);
        //TODO 必须被继承
        return true;
    };

    this.noticeListener = function (event, param) {
        var funcs = listeners[event];
        if(!funcs) return;
        var len = funcs.length;
        for(var i=0;i<len;i++){
    		var fn = funcs[i];
    		typeof fn == "function" && fn(param);
    	}
    };


	/*
	usage: formatTitle(str, maxWidth)
	str:输入的字符
	maxWidth:允许的最大宽度
	返回适当的title串
	*/
	
	//width of '...' is 18px;
	
	var textWidth = function(text){
		var sensor = $("<div class='oWindowTitle' />");
		sensor.html(text);
		//var sensor = $('<div>'+ text +'</div>').css({'display': 'none', 'font-size':'12px', 'font-family':'\5B8B\4F53', 'font-weight':'bold'});
		$('body').append(sensor);
		var width = sensor.width();
		sensor.remove();
		return width;
	};
	
	var formatTitle = function(str, maxWidth){//输入的字符,允许的最大宽度
		var returnValue = str;
		
		if(textWidth(str) > maxWidth){//first check
			var oneDotWidth = textWidth('.');
			var fillDot = '...';
		
			var len = str.length;
			var firstHalfNum = Math.round(len/2);
			var firstHalf = str.substring(0, firstHalfNum);
			var calcWidth = textWidth(firstHalf + fillDot);
			if(calcWidth > maxWidth){
				var tmpStr = '';
				var tmpWidth = 0;
				for(var i = firstHalfNum; i >= 0; i--){
					tmpStr = firstHalf.substring(0, i) + fillDot;
					tmpWidth = textWidth(tmpStr);
					if(tmpWidth <= maxWidth){
						break;
					}
				}
				if(tmpStr == fillDot){
					var firstCharWidth = textWidth(firstHalf.substring(0,1));
					tmpStr = firstHalf.substring(0,1);
					var j;
					for( j = 2; j >= 1; j--){
						if(firstCharWidth + j*oneDotWidth <= maxWidth){
							break;
						}
					}
					for(var i = j; i > 0; i--){
						tmpStr += '.';
					}
			
				}
				returnValue = tmpStr;
			}else if(calcWidth < maxWidth){
				var tmpStr = '';
				for(var i = firstHalfNum; i <= len; i++){
					tmpStr = firstHalf + str.substring(firstHalfNum, i + 1) + fillDot;
					if(textWidth(tmpStr)> maxWidth){
						break;
					}
				}
				returnValue = tmpStr.substring(0, tmpStr.length - 4) + fillDot;
			}else{
				returnValue = firstHalf + fillDot;
			}
		}
		return returnValue;
	}

    this.setTitle = function (title) {
        if (typeof title == 'object') {
            title = title.title;
        }
        _this.options.title = title;
        _this.changeDockInfo({
            title:title
        });
        _this.repaint();
    };

    this.setIconInfo = function (_param) {

    };

    this.topDoResize = function () {
        _this.options.position = {
            top :_this.view.position().top,
            left:_this.view.position().left
        };
        _this.options.size = {
            width :$(".oWindowBody", _this.view).width(),
            height:$(".oWindowBody", _this.view).height()
        };
    };

    this.doResize = function () {
        var theight = _this.view.height();
//		$(".oWindowBody",_this.view).height(theight - _this.getFrameSize().height);
//        $(".oWindowBody", _this.view).css({'height':'auto', 'position':'absolute'});
        _this.noticeListener("resize", {wid:wid, width:_this.view.width(), height:_this.view.height()});
        _this.topDoResize();
    };

    var finishLoad = function () {
        if (urlLoaded) return;
        urlLoaded = true;
        showLoading(false);
        _this.noticeListener("urlLoad", {});
    };

    this.setUrl = function (_url, _proxy) {
        if (!_url) {
            showLoading(true);
            return;
        }
        urlLoaded = false;
        showLoading(true);
        var id = _this.getWid();
        //allowTransparency='true'
        iframe = $("<iframe allowTransparency='true' frameborder='0' name='" + id + "' id='" + id + "' />");
        iframe.css({
            top   :0,
            left  :0,
            width :_this.getContentSize().width,
            height:_this.getContentSize().height
        });


        iframe.attr("src", _url);
        url = _url;
        proxy = _proxy;
        _this.setContent(iframe);

        iframe.bind("load", function () {
            finishLoad();
        });

        _this.addEventListener("resize", function () {
            var p = _this.getContentSize();
            //$.log("width"+p.width+";"+p.height);
            iframe.css({
                top   :0,
                left  :0,
                width :p.width,
                height:p.height
            });
        });
    };


    var parseApiParam = function (_param, level) {
        if (!_param) return;
        var l = level + 1;
        if (l > 3) return;
        $.each(_param, function (k, v) {
            if (!v) return;
            if (typeof v == 'object' && v.type == "gleasyApiFunction") {
                if (typeof v.pointer == 'number') {
                    _param[k] = function (result) {
                        sendMessageToApp(result, v.pointer);
                    }
                } else if (v.subtype == 'container') {
                    _param[k] = _this;
                }
            } else if (typeof v == 'object') {
                parseApiParam(v, l);
            }
        });
    };

    this.runCommand = function (data) {
        if (typeof data == "string") {
            data = $.evalJSON(data);
        }
        var obj = data;
        var eventId = obj._eventId;
        var pp = obj._param;

        var level = 0;
        parseApiParam(pp, level);

        _this.noticeListener(eventId, pp);
    };


    var requestUrl = function (_param) {
        AjaxConfig.ajax(_param);
    };

    var getCurrentUser = function (_param) {
    	if(!Constants.data || !Constants.data.user){
    		setTimeout(function(){
    			getCurrentUser(_param);
    		},1000);
    		return;
    	}
    	var result = {
    		appname:_this.options.app,
    		timestamp:new Date().getTime(),
    		nonce:UuidGenerator.uuid("nonce")
    	};
    	$.extend(result,Constants.data.user);
    	var content = result.uid+","+result.name+","+result.type+","+result.timestamp+","+result.nonce+","+result.appname;
       	sign(content,"b64-rsa-md5",
       		function(data){
        		result.checksum = data.signature;
        		result.encrypt = "b64-rsa-md5";
        		typeof _param.callback == 'function' && _param.callback(result);
           	},
           	function(){
        		var param = {};
				param.message = "获取当前用户资料失败";
				window.application.sendNotify(Constants.command.system.alert,param);
           	});
    };

	var sign = function(content,encrypt, success,error){
		window.application.sendNotify(Constants.command.os.process.sendMessage, {
            pname      :"安全卫士",
            eventId    :'signature',
            messageBody:{
            	content: content,
            	encrypt: encrypt,
            	success:function(data){
            		typeof success == 'function' && success(data);
            	},error:function(){
            		error && error();
            	}
            }
        });	
	}
	
	var gencert = function(data, success,error){
		window.application.sendNotify(Constants.command.os.process.sendMessage, {
            pname      :"安全卫士",
            eventId    :'cert.gen',
            messageBody:{
            	data: data,
            	success:function(dt){
            		typeof success == 'function' && success(dt);
            	},error:function(){
            		error && error();
            	}
            }
        });	
	}
	
	var getString = function(b){
		if(b) return b;
		return "";
	}
	
//  	var openFileSaveBrowser = function(_param){
//		if(!_param) return;
//		var orignalSelect = _param.select;
//		_param.select = function(selectedFile){
//	       selectedFile.uid = Constants.data.user.uid;
//	       selectedFile.appname = _this.options.app?_this.options.app:"API测试器";
//	       selectedFile.timestamp = new Date().getTime();
//	       selectedFile.nonce = UuidGenerator.uuid("nonce");
//	       
//	       selectedFile.fid = getString(selectedFile.fid);
//	       
//	       var content = "";
//	       content += getString(selectedFile.uid)+",";
//	       content += getString(selectedFile.pid)+",";
//	       content += getString(selectedFile.fid)+",";
//	       content += getString(selectedFile.name)+",";
//	       content += getString(selectedFile.appname)+",";
//	       content += getString(selectedFile.timestamp)+",";
//	       content += getString(selectedFile.nonce);
//	       
//	       //var content = selectedFile.uid+","+selectedFile.pid+","+selectedFile.fid+","+selectedFile.name+","+selectedFile.appname+","+result.timestamp+","+result.nonce;
//	       sign(content,"hex-rsa-md5",function(data){
//	       	selectedFile.checksum = data.signature;
//        	selectedFile.encrypt = "hex-rsa-md5";
//	       	orignalSelect && orignalSelect(selectedFile);
//	       });
//		}
//		window.application.sendNotify(Constants.command.os.process.sendMessage, {
//			pname:"一盘守护进程",
//			eventId:"openFileBrowserDialog",
//			messageBody:_param
//		});	
//	}
	
	var openFileSaveBrowser = function(_param){
		if(!_param) return;
		var orignalSelect = _param.select;
		_param.select = function(selectedFile){
	       selectedFile.uid = Constants.data.user.uid;
	       selectedFile.appname = _this.options.app?_this.options.app:"API测试器";
	       selectedFile.timestamp = new Date().getTime();
	       selectedFile.nonce = UuidGenerator.uuid("nonce");
	       selectedFile.fid = getString(selectedFile.fid);
	       //var content = selectedFile.uid+","+selectedFile.pid+","+selectedFile.fid+","+selectedFile.name+","+selectedFile.appname+","+result.timestamp+","+result.nonce;
	       gencert(selectedFile,function(data){
	       	orignalSelect && orignalSelect({
	       		cert:data.cert
	       	});
	       });
		}
		window.application.sendNotify(Constants.command.os.process.sendMessage, {
			pname:"一盘守护进程",
			eventId:"openFileBrowserDialog",
			messageBody:_param
		});	
	}
    var convertToHtml = function (_param) {
        var p = {};
        $.extend(p, _param);

        $.extend(p, {
            success:function (obj) {
                 typeof _param.success == 'function' && _param.success(obj.id);
            }
        });
        window.application.sendNotify(Constants.command.os.process.sendMessage, {
            pname      :"office转换器",
            eventId    :'doc2html',
            messageBody:p
        });
    };
    
    this.openAvatarEditor = function (_param) {
        var callback = _param.callback;
        $LAB
            .script(Constants.config.assetPrefix + "/platform/os/assets/javascripts/components/avatar/AvatarEditorDialog.class.js")
            .wait(function () {
                var args = {
                    title :"头像编辑",
                    parent:_this
                };
                $.extend(args, _param);
                var fb = new com.gleasy.component.avatar.AvatarEditorDialog(args);
                fb.addEventListener("avatarEdit_finish", function (param) {
                    callback && typeof callback == 'function' && callback(param);
                });
                fb.setVisible(true);
            });
    };

    var setCloseHandler = function (param) {
        var handler = param.handler;
        closeHandler = handler;
    };

    var registerCommand = function () {
        _this.addEventListener("c.openAvatarEditor", function (param) {
            _this.openAvatarEditor(param);
        });

        _this.addEventListener("c.setCloseHandler", function (param) {
            setCloseHandler(param);
        });

        _this.addEventListener("c.getUserSetting", function (param) {
            window.application.sendNotify(Constants.command.os.setting.load, param);
        });

        _this.addEventListener("c.saveUserSetting", function (param) {
            window.application.sendNotify(Constants.command.os.setting.save, param);
        });


        _this.addEventListener("c.exit", function (param) {
            _this.exit();
        });

        _this.addEventListener("c.sendMessageToProcess", function (param) {
            _this.sendMessageToProcess(param);
        });

        _this.addEventListener("c.finishLoad", function (param) {
            finishLoad();
        });

        _this.addEventListener("c.showDragMask", function (param) {
            _this.showDragMask();
        });

        _this.addEventListener("c.addMessage", function (param) {
            window.application.sendNotify(Constants.command.os.message.add, param);
        });

        _this.addEventListener("c.changeDockInfo", function (param) {
            _this.changeDockInfo(param);
        });

		_this.addEventListener("c.changeIconInfo", function (param) {
            _this.changeIconInfo(param);
        });
        
        _this.addEventListener("c.addMessageDock", function (param) {
            _this.addMessageDock(param);
        });

        _this.addEventListener("c.removeMessageDock", function (param) {
            _this.removeMessageDock(param);
        });

        _this.addEventListener("c.close", function (param) {
            _this.close();
        });

        _this.addEventListener("c.runProgram", function (param) {
            window.application.sendNotify(Constants.command.os.process.runProgram, param);
        });

        _this.addEventListener("c.osFullscreen", function (param) {
            window.application.sendNotify(Constants.command.os.toFullscreen, {});
        });

        _this.addEventListener("c.osCancelFullscreen", function (param) {
            window.application.sendNotify(Constants.command.os.cancelFullscreen, {});
        });

        _this.addEventListener("c.systemexit", function (param) {
            window.application.sendNotify(Constants.command.os.systemexit, {});
        });

        _this.addEventListener("c.close", function (param) {
            _this.close();
        });
        _this.addEventListener("c.registerHotkeyHandler", function (param) {
            window.application.sendNotify(Constants.command.system.registerHotkeyHandler, param);
        });

        _this.addEventListener("c.print", function (param) {
            $LAB.script(Constants.config.assetPrefix + "/script/common/Gprinter.class.js").wait(function () {
                Gprinter.print({
                    callback:param.callback,
                    pages   :param.pages
                });
            });

        });
        _this.addEventListener("c.getInstalledApps", function (param) {
            com.gleasy.os.reg.getInstalledApps(param);
        });
        _this.addEventListener("c.setFileAssociation", function (param) {
            com.gleasy.os.reg.setUseTypeBinding(param.type, param.pname, param.callback);
        });
        _this.addEventListener("c.getFileAssociation", function (param) {
            com.gleasy.os.reg.getPnameByType(param.type, param.callback);
        });

        _this.addEventListener("c.iframeLoadFinish", function (param) {
            showLoading(false);
        });

        _this.addEventListener("c.showLoading", function (param) {
            showLoading(param.show);
        });

        _this.addEventListener("c.getCurrentUser", function (param) {
            getCurrentUser(param);
        });

        _this.addEventListener("c.convertToHtml", function (_param) {
            convertToHtml(_param);
        });
//
//        _this.addEventListener("c.convertToPdf", function (_param) {
//            convertToPdf(_param);
//        });

        _this.addEventListener("c.requestUrl", function (_param) {
            requestUrl(_param);
        });
        _this.addEventListener("c.toFullscreen", function (param) {
            _this.toFullscreen();
        });
        _this.addEventListener("c.cancelFullscreen", function (param) {
            _this.cancelFullscreen();
        });
        _this.addEventListener("c.showFrame", function (param) {
            _this.options.hasFrame = 1;
            _this.showFrame(param);
        });
        _this.addEventListener("c.hideFrame", function (param) {
            _this.options.hasFrame = 0;
            _this.hideFrame(param);
        });

        _this.addEventListener("c.exit", function (param) {
            _this.exit();
        });
        _this.addEventListener("c.focus", function (param) {
            _this.tryFocus();
        });
        _this.addEventListener("c.setXY", function (param) {
            _this.setXY(param);
        });
        _this.addEventListener("c.setSize", function (param) {
            _this.setSize(param);
        });
        _this.addEventListener("c.setTitle", function (param) {
            _this.setTitle(param);
        });

        _this.addEventListener("c.maximize", function (param) {
            _this.maximize(param);
        });
        _this.addEventListener("c.unmaximize", function (param) {
            _this.unmaximize(param);
        });
        _this.addEventListener("c.minimize", function (param) {
            _this.minimize(param);
        });
        _this.addEventListener("c.moveBy", function (param) {
            _this.moveBy(param);
        });
        _this.addEventListener("c.showTip", function (_param) {
            var message = _param.message;
            window.application.sendNotify(Constants.command.os.showTip, message);
        });
        _this.addEventListener("c.addEventListener", function (param) {
            var wid = param.wid;
            var event = param.event;
            var fn = param.callback;
            if (wid && wid != '') {
                var container = com.gleasy.os.view.Container.getByWid(wid);
                if (container) {
                    container.addEventListener(event, fn);
                }
            } else {
                _this.addEventListener(event, fn);
            }
        });
        _this.addEventListener("c.noticeListener", function (param) {
            var event = param.event;
            var message = param.message;
            _this.noticeListener(event, message);
        });

        _this.addEventListener("c.cloudDiskFileDrop", function (param) {
            _this.addEventListener("cloudDiskFileDrop", param.callback);
            if (filesReceived != null && filesReceived.length) {
                typeof param.callback == 'function' && param.callback(filesReceived);
            }
        });

        _this.addEventListener("c.resizeFinish", function (param) {
            _this.addEventListener("resizeFinish", param.callback);
        });

        _this.addEventListener("c.registerExitConfirm", function (param) {
            //var message = param.message;
            //exitConfirm = message;
            exitConfirm = param;

            typeof param.callback == 'function' && param.callback({result:true});
        });

        _this.addEventListener("c.removeExitConfirm", function (param) {
            exitConfirm = null;
            typeof param.callback == 'function' && param.callback({result:true});
        });

        _this.addEventListener("c.alert", function (_param) {
            window.application.sendNotify(Constants.command.system.alert, _param);
        });
        _this.addEventListener("c.center", function (_param) {
            _this.center();
        });
        _this.addEventListener("c.fitRight", function (_param) {
            var space = _param.space;
            _this.fitRight(space);
        });
        _this.addEventListener("c.fitBottom", function (_param) {
            _this.fitBottom();
        });
        _this.addEventListener("c.setOffset", function (_param) {
            _this.setOffset(_param);
        });
        _this.addEventListener("c.confirm", function (_param) {
            _param.parent = _this;
            window.application.sendNotify(Constants.command.system.confirm, _param);
        });
        _this.addEventListener("c.openUrl", function (_param) {
            _this.openUrl(_param);
        });
        _this.addEventListener("c.winMask", function (_param) {
            _this.winMask(_param);
        });
        _this.addEventListener("c.winUnMask", function (_param) {
            _this.winUnMask(_param);
        });


        _this.addEventListener("c.createDialog", function (_param) {
            var p = {
            };
            $.extend(p, _param);
            $.extend(p, {
                parent:_this
            });
            var dialog = new com.gleasy.os.view.Dialog(p);
            if (p.url && p.url != null) {
                dialog.setUrl(p.url);
            } else if (p.content != null) {
                dialog.setContent(p.content);
            }
            dialog.setVisible(true);

            if (typeof _param.callback == 'number') {
                var fpointer = _param.callback;
                _param.callback = function (result) {
                    sendMessageToApp(result, fpointer);
                };
            }
            typeof _param.callback == 'function' && _param.callback({wid:dialog.getWid()});
        });

        _this.addEventListener("c.closeDialog", function (_param) {
            if (_param == null) return;
            var wid = _param.wid;

            var dialog = com.gleasy.os.view.Container.getByWid(wid);
            dialog.close();
        });

		_this.addEventListener("c.closeAllDialog", function (_param) {
           
            $.each(_this.children, function (index, child) {
	            index != 'null' && child != null && child.close();
	        });
        });
        
        _this.addEventListener("c.installApp", function (_param) {
            if (_param == null) return;
            window.application.sendNotify(Constants.command.system.installApp, _param);
        });

        _this.addEventListener("c.uninstallApp", function (_param) {
            if (_param == null) return;
            window.application.sendNotify(Constants.command.system.unInstallApp, _param);
        });

        _this.addEventListener("c.setWallpaper", function (_param) {
            window.application.sendNotify(Constants.command.os.setWallpaper, _param);
        });

		_this.addEventListener("c.openFileSaveBrowser", function (_param) {
           openFileSaveBrowser(_param);
        });

    };




    this.notify = function () {

    };
	
    var checkBrowserSupport = function (config) {
        var supportBrowser = config.supportBrowser;
        if (!supportBrowser) return true;
        if (typeof supportBrowser == 'string') {
            config.supportBrowser = supportBrowser = $.evalJSON(supportBrowser);
        }
        var sys = getBrowserVersion();
        var type = sys.type;
        var version = sys.version;
        var supprtV = supportBrowser[type];

        var message = "";
        if (supprtV) {
            if (supprtV == 'all') {
                return true;
            } else if (supprtV.indexOf(version) > -1) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    };

    this.openUrl = function (_param) {
         window.application.sendNotify(Constants.command.os.openUrl, _param.url);
    };
    
    this.winMask = function (_param) {
    	var $loading = $('<div class="mdBg Loading"/>'); 
		_this.getContentPane().prepend($loading);
        //window.application.sendNotify(Constants.command.os.openUrl, _param.url);
    };
    
    this.winUnMask = function (_param) {
    	var $loading = $('<div class="mdBg Loading"/>'); 
		_this.getContentPane().find('.mdBg:first').remove();
        //window.application.sendNotify(Constants.command.os.openUrl, _param.url);
    };

    var toQueryString = function (obj) {
        var result = [];
        for (var key in obj) {
            result.push(encodeURIComponent(key) + "=" + encodeURIComponent(obj[key]));
        }
        return result.join("&");
    };


    /**
     * 把消息发给窗口内的应用(可以支持跨域)
     * 1. 主要用于回调方法(客户使用gleasy.api来调用OS方法时,如果有回调,则使用本机制)
     */
    var sendMessageToApp = function (result, fpointer) {
        var back = {
            _eventId :'callback_response',
            _callback:fpointer,
            _param   :result
        };
        if (typeof window.postMessage == 'function' || typeof window.postMessage == 'object') {
            var win = window.frames[wid];
            win.postMessage($.toJSON(back), "*");
        } else {
            var p = new Poly9.URLParser(url);
            var host = p.getHost();
            if (p.getPort()) {
                host += ":" + p.getPort();
            }
            if (proxy == null) {
                proxy = p.getProtocol() + "://" + p.getHost();
                if (p.getPort()) {
                    proxy += ":" + p.getPort();
                }
                proxy += "/app.proxy.html";
            }
            var div = $("<div/>");
            div.append("<iframe name='" + $.toJSON(back) + "' src='" + proxy + "#" + toQueryString({target:'top.frames["' + wid + '"]', host:host}) + "' ></iframe>");
            div.hide();
            _this.view.append(div);
        }
    };

    this.setOffset = function (param) {
        var xOffset = param.x;
        var yOffset = param.y;

        if (_this.parent == null) {
            _this.setXY(xOffset, yOffset);
        } else {
            var pos = _this.parent.getXY();
            if (pos) {
                var t = pos.top + xOffset;
                var l = pos.left + yOffset;
                _this.setXY(x, y);
            }
        }
    };

    this.moveBy = function (param) {
        var xOffset = param.x;
        var yOffset = param.y;


        var newX = _this.options.position.left + xOffset;
        var newY = _this.options.position.top + yOffset;

        _this.setXY(newX, newY);
    };


    this.showMask = function () {
    	if(!url) return;
        contentMask.show();
    }

    this.hideMask = function () {
        contentMask.hide();
    };

    this.getWid = function () {
        return wid;
    }

    this.getContentPane = function () {
        return $(".osWindowBodyContent", _this.view);
    };

    this.isVisible = function () {
        return _this.visible;
    }


    this.destruct = function () {

    };

    this.getMaxSize = function () {
        var s = {
            width :9999,
            height:9999
        };

        var pos = _this.options.position;
        var container = _this.view.parent();
        if (!container) return s;

        //s.width = container.width() - pos.left - _this.getFrameSize().width;
        //s.height = container.height() - pos.top - _this.getFrameSize().height - $(".oTaskOnBottom").height();

        return s;
    };


    this.setSize = function (newWidth, newHeight) {
        if (typeof newWidth == 'object' && typeof newHeight == 'undefined') {
            var param = newWidth;
            newWidth = param.width;
            newHeight = param.height;
        }
        if (newWidth < 0) {
            newWidth = 0;
        }
        if (newHeight < 0) {
            newHeight = 0;
        }

        var maxSize = _this.getMaxSize();
        if (newWidth > maxSize.width) {
            newWidth = maxSize.width;
        }
        if (newHeight > maxSize.height) {
            newHeight = maxSize.height;
        }
        _this.options.size = {
            width :newWidth,
            height:newHeight
        };
        _this.repaint();
        _this.noticeListener("resizeFinish", _this.getContentSize());
    };

    this.setXY = function (newX, newY) {
        if (typeof newX == 'object' && typeof newY == 'undefined') {
            var param = newX;
            newX = param.x;
            newY = param.y;
        }
        var size = _this.options.size;
        var container = _this.view.parent();
        if (container) {
            //$.log("desktop?"+container.hasClass("desktop"));
            //$.log("newx="+newX+";newY="+newY+";size.width="+size.width+";height="+size.height+";c.w"+container.width()+";c.h"+container.height());
            if (newX < 0) {
                newX = container.width() - size.width + newX;
            }
            if (newY < 0) {
                newY = container.height() - size.height + newY;
            }
            if (newX < 0) {
                newX = 0;
            }
            if (newY < 0) {
                newY = 0;
            }

            if ((newX + size.width) > container.width()) {
                newX = container.width() - size.width;
            }
            if ((newY + size.height) > container.height()) {
                newY = container.height() - size.height;
            }

            if (newY < 0) {
                newY = 0;
            }
            //$.log("newx="+newX+";newY="+newY);
        }

        _this.options.position = {
            top :newY,
            left:newX
        };

        _this.noticeListener("move", {x:_this.options.position.left, y:_this.options.position.top});
        _this.repaint();
    }

    this.fitRight = function (space) {
        if (!space) space = 0;
        if (_this.parent == null) {
            var container = _this.view.parent();
            if (container) {
                var totalWidth = container.width();
                var x = (totalWidth - _this.view.width() - space);
                (x < 0) && (x = 0);
                var y = _this.options.position.top;
                _this.setXY(x, y);
            }
        } else {
            var container = _this.parent.getSize();
            if (container) {
                var totalWidth = container.width;
                var x = _this.parent.getXY().x + (totalWidth - _this.view.width() - space);
                (x < 0) && (x = 0);
                var y = _this.options.position.top;
                _this.setXY(x, y);
            }
        }
    };

    this.fitBottom = function () {
        if (_this.parent == null) {
            var container = _this.view.parent();
            if (container) {
                var totalHeight = container.height();
                var y = (totalHeight - _this.view.height());
                (y < 0) && (y = 0);
                var x = _this.options.position.left;
                _this.setXY(x, y);
            }
        } else {
            var size = _this.options.size;
            var container = _this.parent.getSize();
            if (container) {
                var totalHeight = container.height;
                var y = _this.parent.getXY().y + (totalHeight - _this.view.height());
                var x = _this.options.position.left;
                (y < 0) && (y = 0);
                _this.setXY(x, y);
            }
        }
    };

    this.center = function () {
        if (_this.parent == null) {
            var size = {
            	width:_this.view.width(),
            	height:_this.view.height()
            };
            
            var container = _this.view.parent();
            if (container) {
                var totalWidth = container.width();
                var totalHeight = container.height() - $(".oTaskOnBottom").height();
                var x = (totalWidth - size.width) / 2;
                var y = (totalHeight - size.height) / 2;
                (x < 0) && (x = 0);
                (y < 0) && (y = 0);
                _this.setXY(x, y);
            }
        } else {
           var size = {
            	width:_this.view.width(),
            	height:_this.view.height()
            };
            var container = _this.parent.getSize();
            if (container) {
                var totalWidth = container.width;
                var totalHeight = container.height;
                var x = _this.parent.getXY().x + (totalWidth - size.width) / 2;
                var y = _this.parent.getXY().y + (totalHeight - size.height) / 2;
                (x < 0) && (x = 0);
                (y < 0) && (y = 0);
                _this.setXY(x, y);
            }
        }
    }

    this.close = function () {
        if (closeHandler && typeof closeHandler == 'function') {
            closeHandler();
            return true;
        }
        _this.exit();
        return true;
    };


    this.doExit = function () {
        try {
            $.each(_this.children, function (index, child) {
                index != 'null' && child != null && child.close();
            });

            if (_this.parent != null) {
                _this.parent.removeChild(_this);
            }
            
            destroyed = true;
            _this.noticeListener("close", "");
            _this.view.hide();
            _this.view.remove();
            _this.destruct();
            delete com.gleasy.os.view.Container.factory[wid];
            delete _this;
        } catch (e) {
            $.log(e);
            if (e == 'cancel') {
                //如果有其中一个handler抛出了cancel异常，由不能关闭哦
            }
        }
    };
    this.exit = function () {
        if (exitConfirm != null) {
            var p = {
                title   :"退出确认",
                parent  :_this,
                callback:function (dt) {
                    if (dt.result) {
                        _this.doExit();
                    }
                }
            };
            $.extend(exitConfirm, p);
            window.application.sendNotify(Constants.command.system.confirm, exitConfirm);
        } else {
            _this.doExit();
        }
    };

    /**
     * 强制关闭
     */
    this.kill = function () {
        try {
        	if(destroyed) return;
            $.each(_this.children, function (index, child) {
                index != 'null' && child != null && child.kill();
            });
            if (_this.parent != null) {
                _this.parent.removeChild(_this);
            }
            _this.noticeListener("close", "");
        } catch (e) {
            $.log(e);
        } finally {
            _this.view.hide();
            _this.view.remove();
            _this.destruct();
            delete com.gleasy.os.view.Container.factory[wid];
            delete _this;
        }
    };

    this.canMin = function () {
        return _this.options.canMin;
    };

    this.minimize = function () {
        if (_this.options.canMin != 1) {
            return;
        }
        try {
            $.each(_this.children, function (index, child) {
                if (index != 'null' && child != null) {
                    if (child.canMin() == 1) {
                        child.minimize();
                    } else {
                        child.setVisible(false);
                    }
                }
            });
        } catch (e) {
            $.log(e);
        }
        _this.hide();
        _this.noticeListener("min", "");
    };

    this.toFullscreen = function () {
        if (_this.options.canFullscreen != 1) return;
        if (fullscreened) return;

        fullscreenContext = {
            maxed   :maxed,
            postData:{
                'data-t':_this.view.css('top'),
                'data-l':_this.view.css('left'),
                'data-r':_this.view.css('right'),
                'data-b':_this.view.css('bottom'),
                'data-w':_this.view.css('width'),
                'data-h':_this.view.css('height')
            },
            hasFrame:_this.hasFrame(),
            zIndex  :_this.view.css('zIndex')
        };
        _this.hideFrame();
        _this.view.css({
            'top'   :'0',
            'left'  :'0',
            'right' :'0',
            'bottom':'0',
            'width' :'100%',
            'height':'100%'
        });
        _this.setZindex(Constants.config.os.layer.fullscreen);
        fullscreened = true;
        _this.doResize();
        _this.noticeListener("resizeFinish", _this.getContentSize());
    };

    this.isFullscreen = function () {
        return fullscreened;
    };

    this.cancelFullscreen = function () {
        if (!fullscreened) return;
        if (fullscreenContext['hasFrame'] == 1) {
            _this.showFrame();
        }

        if (fullscreenContext.maxed) {
            _this.maximize();
        } else {
            var pos = fullscreenContext.postData;
            _this.view.css({
                'top'   :pos['data-t'],
                'left'  :pos['data-l'],
                'right' :pos['data-r'],
                'bottom':pos['data-b'],
                'width' :pos['data-w'],
                'height':pos['data-h']
            });
        }
        _this.setZindex(fullscreenContext['zIndex']);
        _this.doResize();
        _this.noticeListener("resizeFinish", _this.getContentSize());
        fullscreened = false;
    };

    this.maximize = function (_param) {
        if (_this.options.canMax != 1) return;
        var oldMaxed = maxed;
        if (!maxed) {
            if (!_this.view.hasClass("oWindowMax")) {
                _this.view.addClass("oWindowMax");
            }

            _this.view.data("pos-data", {
                'data-t':_this.view.css('top'),
                'data-l':_this.view.css('left'),
                'data-r':_this.view.css('right'),
                'data-b':_this.view.css('bottom'),
                'data-w':_this.view.css('width'),
                'data-h':_this.view.css('height')
            });

            _this.view.css({
                'top'   :'0',
                'left'  :'0',
                'right' :'0',
                'bottom':'0',
                'width' :'100%',
                'height':_this.options.container.height() - $(".oTaskOnBottom").height() - 36
            });
            maxed = true;
            _this.doResize();
            _this.noticeListener("resizeFinish", _this.getContentSize());

            // _this.view.find('div.oWindowBtnBar a.oWinMax').toggle();
            // _this.view.find('div.oWindowBtnBar a.oWinRestore').toggle();
            _this.noticeListener("max", "");
        }
        _param && typeof _param.callback == 'function' && _param.callback({maxed:oldMaxed});
    };

    this.unmaximize = function () {
        //$.log("xxxx"+maxed);
        if (!maxed) return;
        var pos = _this.view.data("pos-data");
        _this.view.css({
            'top'   :pos['data-t'],
            'left'  :pos['data-l'],
            'right' :pos['data-r'],
            'bottom':pos['data-b'],
            'width' :pos['data-w'],
            'height':pos['data-h']
        });

        if (_this.view.hasClass("oWindowMax")) {
            _this.view.removeClass("oWindowMax");
        }

        maxed = false;
        _this.doResize();
        _this.noticeListener("resizeFinish", _this.getContentSize());

        //_this.view.find('div.oWindowBtnBar a.oWinMax').toggle();
        // _this.view.find('div.oWindowBtnBar a.oWinRestore').toggle();
        _this.noticeListener("restore", pos);
    };


    this.addEventListener = function (event, func) {
        var funcs = listeners[event];
        if (typeof funcs == 'undefined' || !funcs) {
            funcs = [];
            listeners[event] = funcs;
        }
        funcs.push(func);
        return _this;
    }

    this.removeEventListener = function (event, func) {
        var funcs = listeners[event];
        if (typeof funcs == 'undefined' || !funcs) {
            return;
        }
        var idx = $.inArray(func, funcs);
        if (idx >= 0) {
            funcs.splice(idx, 1);
        }
    };

    this.addMessageDock = function (_param) {
        if (!_param) return;
        if (!_param.messageId) {
            return;
        }
        _param.wid = wid + ":" + _param.messageId;
        _param.pid = _this.getPid();
        _param.app = _this.options.app;

        window.application.sendNotify(Constants.command.os.dock.addMessage, _param);
    };

    this.removeMessageDock = function (_param) {
        if (!_param) return;
        if (!_param) return;
        if (!_param.messageId) {
            return;
        }
        _param.wid = wid + ":" + _param.messageId;
        window.application.sendNotify(Constants.command.os.dock.removeMessage, _param);
    }

    this.changeDockInfo = function (_param) {
        if (!_param) return;
        if (_param.messageId) {
            _param.wid = wid + ":" + _param.messageId;
        } else {
            _param.wid = wid;
        }
        window.application.sendNotify(Constants.command.os.dock.changeInfo, _param);
    };
	
	this.changeIconInfo = function(_param){
		if (!_param) return;
        if (_param.messageId) {
            _param.wid = wid + ":" + _param.messageId;
        } else {
            _param.wid = wid;
        }
        window.application.sendNotify(Constants.command.os.dock.changeIconInfo, _param);
	}
	
    this.sendMessageToProcess = function (param) {
        if (!param || typeof param != 'object') return;
        var pname = param.pname;
        var eventId = param.eventId;
        var messageBody = param.messageBody;
		if(!pname || !eventId || !messageBody) return;
		
        if(pname == "一盘守护进程"){
        	if(eventId == "openFileBrowserDialog"){
        		if(messageBody.showDownloadButton == null){
        			messageBody.showDownloadButton = false;
        		}
        	}
        }
        window.application.sendNotify(Constants.command.os.process.sendMessage, param);
    };

    this.newProcess = function (param) {
        if (!param || typeof param != 'object') return;
        window.application.sendNotify(Constants.command.os.process.runProgram,
            {
                pname:param.pname,
                args :param.args
            }
        );
    };

    _construct();
};

com.gleasy.os.view.Container.factory = {};
com.gleasy.os.view.Container.getByWid = function (wid) {
    return com.gleasy.os.view.Container.factory[wid];
};
com.gleasy.os.view.Container.showDragMask = function (except) {
    $.each(com.gleasy.os.view.Container.factory, function (wid, container) {
        if (except === container) return;
        container.showDragMask();
    });
};
com.gleasy.os.view.Container.hideDragMask = function () {
    $.each(com.gleasy.os.view.Container.factory, function (wid, container) {
        container.hideDragMask();
    });
};
