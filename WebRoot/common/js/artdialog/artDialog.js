/*
 * artDialog 4.1.1
 * Date: 2011-08-28 23:14
 * http://code.google.com/p/artdialog/
 * (c) 2009-2011 TangBin, http://www.planeArt.cn
 *
 * This is licensed under the GNU LGPL, version 2.1 or later.
 * For details, see: http://creativecommons.org/licenses/LGPL/2.1/
 */
 
/*583-1931*/

/*!
	dialog		
------------------------------------------------------------------*/
;(function ($, window, undefined) {

	$.noop = $.noop || function () {}; // jQuery 1.3.2
	
	var _box, _thisScript, _skin, _path,
		_count = 0,
		_$window = $(window),
		_$document = $(document),
		_$html = $('html'),
		_$body = $(function(){_$body = $('body')}),
		_elem = document.documentElement,
		_isIE6 = window.VBArray && !window.XMLHttpRequest,
		_isMobile = 'createTouch' in document && !('onmousemove' in _elem)
			|| /(iPhone|iPad|iPod)/i.test(navigator.userAgent),
		_expando = 'artDialog' + (new Date).getTime();

	var artDialog = function (config, ok, cancel) {
		config = config || {};
		if (typeof config === 'string' || config.nodeType === 1) {
			config = {content: config, fixed: !_isMobile};
		};
		
		var api, buttons = [],
			defaults = artDialog.defaults,
			elem = config.follow = this.nodeType === 1 && this || config.follow;
			
		// 合并默认配置
		for (var i in defaults) {
			if (config[i] === undefined) config[i] = defaults[i];		
		};
		
		// 兼容v4.1.0之前的参数，未来版本将删除此
		$.each({ok:"yesFn",cancel:"noFn",close:"closeFn",init:"initFn",okVal:"yesText",cancelVal:"noText"},
		function(i,o){config[i]=config[i]!==undefined?config[i]:config[o]});
		
		// 返回跟随模式或重复定义的ID
		if (typeof elem === 'string') elem = $(elem)[0];
		config.id = elem && elem[_expando + 'follow'] || config.id || _expando + _count;
		api = artDialog.list[config.id];
		if (elem && api) return api.follow(elem).focus();
		if (api) return config.lock ? api.show().focus().lock() : api.show().focus();
		// 目前主流移动设备对fixed支持不好
		if (_isMobile) config.fixed = false;
		
		// 按钮队列
		if (!$.isArray(config.button)) {
			config.button = config.button ? [config.button] : [];
		};
		if (ok !== undefined) config.ok = ok;
		if (cancel !== undefined) config.cancel = cancel;
		config.ok && config.button.push({
			name: config.okVal,
			callback: config.ok,
			focus: true
		});
		config.cancel && config.button.push({
			name: config.cancelVal,
			callback: config.cancel
		});
		
		// zIndex全局配置
		artDialog.defaults.zIndex = config.zIndex;
		
		_count ++;
		
		return artDialog.list[config.id] = _box ?
			_box._init(config) : new artDialog.fn._init(config);
	};

	artDialog.fn = artDialog.prototype = {
	
		version: '4.1.1',
		
		/**初始化 */
		_init: function (config) {
			var that = this, DOM,
				icon = config.icon,
				_themePath = '/myWebApp/common/css/popupLayer/images',
				iconBg = icon && (_isIE6 ? {png: 'icons/' + icon + '.png'}
				: {backgroundImage: 'url(\'' + _themePath + '/icons/' + icon + '.png\')'});
			that.config = config;
			
			that._listeners = {};
			that._elemBack = that._timer = that._focus = that._isClose = that._lock = null;
			
			DOM = that.DOM = that.DOM || that._getDOM();
			DOM.wrap.addClass(config.skin);
			DOM.close[config.cancel === false ? 'hide' : 'show']();
			DOM.max[config.max === false ? 'hide' : 'show']();
			DOM.min[config.min === false ? 'hide' : 'show']();
			if (config.isWidget){
				$(DOM.outer).addClass('widgetWrap');
			}
			if (!config.max && config.min){
				DOM.min[0].style.right = '20px';
			}
			DOM.icon[0].style.display = icon ? '' : 'none';
			DOM.iconBg.css(iconBg || {background: 'none'});
			DOM.se.css('cursor', config.resize ? 'se-resize' : 'auto');
			DOM.title.css('cursor', config.drag ? 'move' : 'auto');
			DOM.content.css('padding', config.padding);
			
			that[config.show ? 'show' : 'hide'](false)
			.button(config.button)
			.title(config.title)
			.content(config.content, false)
			.size(config.width, config.height)
			.time(config.time);
			
			config.follow
			? that.follow(config.follow)
			: that.position(config.left, config.top);
			
			that.focus(config.focus);
			config.lock && that.lock();
			
			that._addEvent();
			that._ie6PngFix();
			_box = null;
			
			config.init && config.init.call(that, window);
			return that;
		},
		
		/**
		 * 设置内容
		 * @param	{String, HTMLElement}	内容 (可选)
		 * @return	{this, HTMLElement}		如果无参数则返回内容容器DOM对象
		 */
		content: function (msg) {
			var prev, next, parent, display,
				that = this,
				DOM = that.DOM,
				wrap = DOM.wrap[0],
				width = wrap.offsetWidth,
				height = wrap.offsetHeight,
				left = parseInt(wrap.style.left),
				top = parseInt(wrap.style.top),
				cssWidth = wrap.style.width,
				$content = DOM.content,
				content = $content[0];
			
			that._elemBack = null;
			wrap.style.width = 'auto';
			
			if (msg === undefined) return content;
			if (typeof msg === 'string') {
				$content.html(msg);
			} else if (msg && msg.nodeType === 1) {
			
				// 让传入的元素在对话框关闭后可以返回到原来的地方
				display = msg.style.display;
				prev = msg.previousSibling;
				next = msg.nextSibling;
				parent = msg.parentNode;
				that._elemBack = function () {
					if (prev && prev.parentNode) {
						prev.parentNode.insertBefore(msg, prev.nextSibling);
					} else if (next && next.parentNode) {
						next.parentNode.insertBefore(msg, next);
					} else if (parent) {
						parent.appendChild(msg);
					};
					msg.style.display = display;
				};
				
				$content.html('');
				content.appendChild(msg);
				msg.style.display = 'block';
				
			};
			
			// 新增内容后调整位置
			if (arguments[1] !== false) {
				if (that.config.follow) {
					that.follow(that.config.follow);
				} else {
					width = wrap.offsetWidth - width;
					height = wrap.offsetHeight - height;
					left = left - width / 2;
					top = top - height / 2;
					wrap.style.left = Math.max(left, 0) + 'px';
					wrap.style.top = Math.max(top, 0) + 'px';
				};
				if (cssWidth && cssWidth !== 'auto') {
					wrap.style.width = wrap.offsetWidth + 'px';
				};
				that._autoPositionType();
			};
			
			that._ie6SelectFix();
			that._runScript(content);
			
			return that;
		},
		
		/**
		 * 设置标题
		 * @param	{String, Boolean}	标题内容. 为false则隐藏标题栏
		 * @return	{this, HTMLElement}	如果无参数则返回内容器DOM对象
		 */
		title: function (text) {
			var that = this;
			var DOM = that.DOM,
				wrap = DOM.wrap,
				title = DOM.title,
				className = 'aui_state_noTitle';
				
			if (text === undefined) return title[0];
			if (text === false) {
				title.hide().html('');
				wrap.addClass(className);
			} else {
				title.show().html(text || '');
				wrap.removeClass(className);
			};
			
			return this;
		},
		
		/**
		 * 位置(相对于可视区域)
		 * @param	{Number, String}
		 * @param	{Number, String}
		 */
		position: function (left, top) {
			var that = this,
				config = that.config,
				wrap = that.DOM.wrap[0],
				isFixed = _isIE6 ? false : config.fixed,
				ie6Fixed = _isIE6 && that.config.fixed,
				docLeft = _$document.scrollLeft(),
				docTop = _$document.scrollTop(),
				dl = isFixed ? 0 : docLeft,
				dt = isFixed ? 0 : docTop,
				ww = _$window.width(),
				wh = _$window.height(),
				ow = wrap.offsetWidth,
				oh = wrap.offsetHeight,
				style = wrap.style;
			
			if (!left && left !== 0) left = config.left;
			if (!top && top !== 0) top = config.top;
			config.left = left;
			config.top = top;
			
			left = that._toNumber(left, ww - ow);
			if (typeof left === 'number') left = ie6Fixed ? (left += docLeft) : left + dl;
	
			if (top === 'goldenRatio') {
				// 黄金比例垂直居中
				top = (oh < 4 * wh / 7 ? wh * 0.382 - oh / 2 : (wh - oh) / 2) + dt;
			} else {
				top = that._toNumber(top, wh - oh);
				if (typeof top === 'number') top = ie6Fixed ? (top += docTop) : top + dt;
			};
			
			if (typeof left === 'number') {
				style.left = Math.max(left, dl) + 'px';
			} else if (typeof left === 'string') {
				style.left = left;
			};
			
			if (typeof top === 'number') {
				style.top = Math.max(top, dt) + 'px';
			} else if (typeof top === 'string') {
				style.top = top;
			};
			
			/*that.config.follow = null;*/
			that._autoPositionType();
			
			return that;
		},
		
		/**
		 *	尺寸
		 *	@param	{Number, String}	宽度
		 *	@param	{Number, String}	高度
		 *	@return	this
		 */
		size: function (width, height) {
			var maxWidth, maxHeight, scaleWidth, scaleHeight,
				that = this,
				config = that.config,
				DOM = that.DOM,
				wrap = DOM.wrap,
				main = DOM.main,
				wrapStyle = wrap[0].style,
				style = main[0].style;
				// extends code...
				$content = DOM.content;
				contentStyle = $content[0].style;
				
			if (!width && width !== 0) width = config.width;
			if (!height && height !== 0) height = config.height;
			
			maxWidth = _$window.width() - wrap[0].offsetWidth + main[0].offsetWidth;
			scaleWidth = that._toNumber(width, maxWidth);
			config.width = width;
			width = scaleWidth;
			
			maxHeight = _$window.height() - wrap[0].offsetHeight + main[0].offsetHeight;
			scaleHeight = that._toNumber(height, maxHeight);
			config.height = height;
			height = scaleHeight;
			
			// rewrite code... 防止iframe宽度指定无效
			if (!isNaN(parseInt(width))){
				width = parseInt(width);
				wrapStyle.width = 'auto';
				style.width = Math.max(that.config.minWidth, width) + 'px';
				contentStyle.width = style.width;
				wrapStyle.width = wrap[0].offsetWidth + 'px'; // 防止未定义宽度的表格遇到浏览器右边边界伸缩
			} else {
				style.width = width;
				width === 'auto' && wrap.css('width', 'auto');
			}
			
			if (!isNaN(parseInt(height))){
				height = parseInt(height);
				style.height = Math.max(that.config.minHeight, height) + 'px';
			} else {
				style.height = height;
			}
			/**
			if (typeof width === 'number') {
				wrapStyle.width = 'auto';
				style.width = Math.max(that.config.minWidth, width) + 'px';
				contentStyle.width = style.width;
				wrapStyle.width = wrap[0].offsetWidth + 'px'; // 防止未定义宽度的表格遇到浏览器右边边界伸缩
			} else if (typeof width === 'string') {
				style.width = width;
				width === 'auto' && wrap.css('width', 'auto');
			};
			if (typeof height === 'number') {
				style.height = Math.max(that.config.minHeight, height) + 'px';
			} else if (typeof height === 'string') {
				style.height = height;
			};
			*/
			contentStyle.height = style.height;
			
			that._ie6SelectFix();
			
			return that;
		},
		
		/**
		 * 跟随元素
		 * @param	{HTMLElement}
		 */
		follow: function (elem) {
			var $elem, that = this;
	
			if (typeof elem === 'string' || elem && elem.nodeType === 1) {
				$elem = $(elem);
			};
			if (!$elem || $elem.css('display') === 'none') {
				return that.position(that.config.left, that.config.top);
			};
			
			var winWidth = _$window.width(),
				winHeight = _$window.height(),
				docLeft =  _$document.scrollLeft(),
				docTop = _$document.scrollTop(),
				offset = $elem.offset(),
				width = $elem[0].offsetWidth,
				height = $elem[0].offsetHeight,
				isFixed = _isIE6 ? false : that.config.fixed,
				left = isFixed ? offset.left - docLeft : offset.left,
				top = isFixed ? offset.top - docTop : offset.top,
				wrap = that.DOM.wrap[0],
				style = wrap.style,
				wrapWidth = wrap.offsetWidth,
				wrapHeight = wrap.offsetHeight,
				setLeft = left - (wrapWidth - width) / 2,
				setTop = top + height,
				dl = isFixed ? 0 : docLeft,
				dt = isFixed ? 0 : docTop;
			
			setLeft = setLeft < dl ? left :
			(setLeft + wrapWidth > winWidth) && (left - wrapWidth > dl)
			? left - wrapWidth + width
			: setLeft;
	
			setTop = (setTop + wrapHeight > winHeight + dt)
			&& (top - wrapHeight > dt)
			? top - wrapHeight
			: setTop;
			
			style.left = setLeft + 'px';
			style.top = setTop + 'px';
			
			that.config.follow = elem;
			$elem[0][_expando + 'follow'] = that.config.id;
			that._autoPositionType();
			return that;
		},
		
		/**
		 * 自定义按钮
		 * @example
			button({
				name: 'login',
				callback: function () {},
				disabled: false,
				focus: true
			}, .., ..)
		 */
		button: function () {
			var that = this,
				ags = arguments,
				DOM = that.DOM,
				wrap = DOM.wrap,
				buttons = DOM.buttons,
				elem = buttons[0],
				strongButton = 'aui_state_highlight',
				list = $.isArray(ags[0]) ? ags[0] : [].slice.call(ags);
			
			if (ags[0] === undefined) return elem;
			$.each(list, function (i, val) {
				var name = val.name,
					listeners = that._listeners,
					isNewButton = !listeners[name],
					button = !isNewButton ?
						listeners[name].elem :
						document.createElement('button');
						
				if (!listeners[name]) listeners[name] = {};
				if (val.callback) listeners[name].callback = val.callback;
				if (val.className) button.className = val.className;
				if (val.focus) {
					that._focus && that._focus.removeClass(strongButton);
					that._focus = $(button).addClass(strongButton);
					that.focus();
				};
				
				button[_expando + 'callback'] = name;
				button.disabled = !!val.disabled;
	
				if (isNewButton) {
					button.innerHTML = name;
					listeners[name].elem = button;
					elem.appendChild(button);
				};
			});
			
			buttons[0].style.display = list.length ? '' : 'none';
			
			that._ie6SelectFix();
			return that;
		},
		
		/** 显示对话框 */
		show: function () {
			var that = this;
			
			that.DOM.wrap.show();
			arguments[0] === false && this._lockMaskWrap && this._lockMaskWrap.show();
			// 使用display:none隐藏以后，window发生resize，再show以后窗口出现变形bug, 此处手动再次设置size和position
			that.size(that.config.width, that.config.height);
			that.position(that.config.left, that.config.top);
			
			// 执行show回调
			if (that.config.showFun){
				that.config.showFun();
			}
			return this;
		},
		
		/** 隐藏对话框 */
		hide: function () {
			var that = this;
			that.DOM.wrap.hide();
			arguments[0] === false && this._lockMaskWrap && this._lockMaskWrap.hide();
			
			return this;
		},
		
		/**载入指定URL到弹出层*/
		load: function(url){
			var that = this;
			that.content('Loading...');
			$.post(url,function(data){
				that.content(data);
			});
		},
		
		/**最大化 还原*/
		max: function () {
			var win = $(this),that = this;
			
			var targetDialog = art.dialog.list[that.config.id];
			
			if (that.config.width === '100%' && that.config.height === '100%') {
				if (win.attr('data-w') && win.attr('data-h')){
					targetDialog.size(win.attr('data-w'), win.attr('data-h'));
				} else {
					targetDialog.size(850, 500);
				}
				if (win.attr('data-l') && win.attr('data-t')){
					targetDialog.position(win.attr('data-l'), win.attr('data-t'));
				}else{
					targetDialog.position('60%', '45%');
				}
				//that.config.width=win.attr('data-w');
				//that.config.height=win.attr('data-h');
				if (that.config.remaxFun){
					that.config.remaxFun();
				}
			} else {
				win.attr({
					// 保存窗口位置
					'data-t': that.config.top,
					'data-l': that.config.left,
					'data-w': that.config.width,
					'data-h': that.config.height
				})
				//win.addClass('window_full');
				targetDialog.position('0%', '0%');
				targetDialog.size('100%', '100%');
				if (that.config.maxFun){
					that.config.maxFun();
				}
			}
			
		},
		
		/**最小化*/
		min: function() {
			var that = this;
			var targetDialog = artDialog.list[that.config.id];
			if (that.config.lock){
				targetDialog.unlock().hide();
			} else {
				targetDialog.hide();
			}
			if (that.config.minFun){
				that.config.minFun();
			}
		},
		
		/** 关闭对话框 */
		close: function () {
			var that = this,
				DOM = that.DOM,
				wrap = DOM.wrap,
				list = artDialog.list,
				fn = that.config.close,
				follow = that.config.follow,
				 // extends code...
				_main = DOM.main,
				_mainStyle = _main[0].style,
				_content = DOM.content,
				_contentStyle = _content[0].style;
			// extends code
			_mainStyle.width = 'auto';
			_mainStyle.height = 'auto';
			_contentStyle.width = 'auto';
			_contentStyle.height = 'auto';
			
			if (that._isClose) return that;
			that.time();
			if (typeof fn === 'function' && fn.call(that, window) === false) {
				return that;
			};
			
			that.unlock();
			wrap[0].className = wrap[0].style.cssText = '';
			
			that._elemBack && that._elemBack();
			DOM.title.html('');
			DOM.content.html('');
			DOM.buttons.html('');
			
			// extends code...
			$(DOM.outer).removeClass('widgetWrap');
			
			if (artDialog.focus === that) artDialog.focus = null;
			if (follow) follow[_expando + 'follow'] = null;
			delete list[that.config.id];
			that._isClose = true;
			that._removeEvent();
			that.hide(true)._setAbsolute();
			
			_box ? wrap.remove() : _box = that;
			
			return that;
		},
		
		/**
		 * 定时关闭
		 * @param	{Number}	单位为秒, 无参数则停止计时器
		 */
		time: function (second) {
			var that = this,
				cancel = that.config.cancelVal,
				timer = that._timer;
				
			timer && clearTimeout(timer);
			
			if (second) {
				that._timer = setTimeout(function(){
					that._trigger(cancel);
				}, 1000 * second);
			};
			
			return that;
		},
		
		/** 设置焦点 */
		focus: function () {
			var elemFocus,
				that = this,
				DOM = that.DOM,
				wrap = DOM.wrap,
				top = artDialog.focus,
				index = artDialog.defaults.zIndex ++;
			
			// 设置叠加高度
			wrap.css('zIndex', index);
			that._lockMask && that._lockMask.css('zIndex', index - 1);
			
			// 设置最高层的样式
			top && top.DOM.wrap.removeClass('aui_state_focus');
			artDialog.focus = that;
			wrap.addClass('aui_state_focus');
			
			// 添加焦点
			if (arguments[0] !== false) {
				try {
					elemFocus = that._focus && that._focus[0]// || DOM.close[0]; 不让关闭按钮得到焦点
					elemFocus && elemFocus.focus();
				} catch (e) {}; // IE对不可见元素设置焦点会报错
			};
			
			// 聚焦窗口回调
			if (that.config.focusFun){
				that.config.focusFun();
			}
			return that;
		},
		
		/** 设置屏锁 */
		lock: function () {
			if (this._lock) return this;
			
			var that = this,
				index = artDialog.defaults.zIndex - 1,
				wrap = that.DOM.wrap,
				config = that.config,
				docWidth = _$document.width(),
				docHeight = _$document.height(),
				lockMaskWrap = that._lockMaskWrap || $(_$body[0].appendChild(document.createElement('div'))),
				lockMask = that._lockMask || $(lockMaskWrap[0].appendChild(document.createElement('div'))),
				domTxt = '(document).documentElement',
				sizeCss = _isMobile ? 'width:' + docWidth + 'px;height:' + docHeight
					+ 'px' : 'width:100%;height:100%',
				ie6Css = _isIE6 ?
					'position:absolute;left:expression(' + domTxt + '.scrollLeft);top:expression('
					+ domTxt + '.scrollTop);width:expression(' + domTxt
					+ '.clientWidth);height:expression(' + domTxt + '.clientHeight)'
				: '';
			
			that.focus(false);
			wrap.addClass('aui_state_lock');
			
			lockMaskWrap[0].style.cssText = sizeCss + ';position:fixed;z-index:'
				+ index + ';top:0;left:0;overflow:hidden;' + ie6Css;
			lockMask[0].style.cssText = 'height:100%;background:' + config.background
				+ ';filter:alpha(opacity=0);opacity:0';
			
			// 让IE6锁屏遮罩能够盖住下拉控件
			if (_isIE6) lockMask.html(
				'<iframe src="about:blank" style="width:100%;height:100%;position:absolute;' +
				'top:0;left:0;z-index:-1;filter:alpha(opacity=0)"></iframe>');
				
			lockMask.stop();
			//lockMask[0].ondblclick = function () { that.close() };
			if (config.duration === 0) {
				lockMask.css({opacity: config.opacity});
			} else {
				lockMask.animate({opacity: config.opacity}, config.duration);
			};
			
			that._lockMaskWrap = lockMaskWrap;
			that._lockMask = lockMask;
			
			that._lock = true;
			return that;
		},
		
		/** 解开屏锁 */
		unlock: function (onfx) {
			var that = this,
				lockMaskWrap = that._lockMaskWrap,
				lockMask = that._lockMask;
			
			if (!that._lock) return that;
			var style = lockMaskWrap[0].style;
			var un = function () {
				if (_isIE6) {
					style.removeExpression('width');
					style.removeExpression('height');
					style.removeExpression('left');
					style.removeExpression('top');
				};
				style.cssText = 'display:none';
				
				if (_box) {
					lockMaskWrap.remove();
					that._lockMaskWrap = that._lockMask = null;
				};
			};
			
			lockMask.stop()
			lockMask[0].ondblclick = null;
			that.DOM.wrap.removeClass('aui_state_lock');
			if (that.config.duration === 0) {// 取消动画，快速关闭
				un();
			} else {
				lockMask.animate({opacity: 0}, that.config.duration, un);
			};
			
			that._lock = false;
			return that;
		},
		
		// 获取元素
		_getDOM: function (wrap) {
			var that = this;
			wrap = document.createElement('div');
			wrap.style.cssText = 'position:absolute;left:0;top:0';
			wrap.innerHTML = artDialog.templates;
			document.body.appendChild(wrap);
			
			var name, i = 0,
				DOM = {wrap: $(wrap)},
				els = wrap.getElementsByTagName('*'),
				elsLen = els.length;
				
			for (; i < elsLen; i ++) {
				name = els[i].className.split('aui_')[1];
				if (name) DOM[name] = $(els[i]);
			};
			
			return DOM;
		},
		
		// px与%单位转换成数值 (百分比单位按照最大值换算)
		// 其他的单位返回原值
		_toNumber: function (thisValue, maxValue) {
			if (!thisValue && thisValue !== 0 || typeof thisValue === 'number') {
				return thisValue;
			};
			
			var last = thisValue.length - 1;
			if (thisValue.lastIndexOf('px') === last) {
				thisValue = parseInt(thisValue);
			} else if (thisValue.lastIndexOf('%') === last) {
				thisValue = parseInt(maxValue * thisValue.split('%')[0] / 100);
			};
			
			return thisValue;
		},
		
		// 让IE6 CSS支持PNG背景
		_ie6PngFix: _isIE6 ? function () {
			var i = 0, elem, png, pngPath, runtimeStyle,
				path = artDialog.defaults.path + '/skins/',
				list = this.DOM.wrap[0].getElementsByTagName('*');
			
			for (; i < list.length; i ++) {
				elem = list[i];
				png = elem.currentStyle['png'];
				if (png) {
					pngPath = path + png;
					runtimeStyle = elem.runtimeStyle;
					runtimeStyle.backgroundImage = 'none';
					runtimeStyle.filter = "progid:DXImageTransform.Microsoft." +
						"AlphaImageLoader(src='" + pngPath + "',sizingMethod='crop')";
				};
			};
		} : $.noop,
		
		// 强制覆盖IE6下拉控件
		_ie6SelectFix: _isIE6 ? function () {
			var $wrap = this.DOM.wrap,
				wrap = $wrap[0],
				expando = _expando + 'iframeMask',
				iframe = $wrap[expando],
				width = wrap.offsetWidth,
				height = wrap.offsetHeight;
	
			width = width + 'px';
			height = height + 'px';
			if (iframe) {
				iframe.style.width = width;
				iframe.style.height = height;
			} else {
				iframe = wrap.appendChild(document.createElement('iframe'));
				$wrap[expando] = iframe;
				iframe.src = 'about:blank';
				iframe.style.cssText = 'position:absolute;z-index:-1;left:0;top:0;'
				+ 'filter:alpha(opacity=0);width:' + width + ';height:' + height;
			};
		} : $.noop,
		
		// 解析HTML片段中自定义类型脚本，其this指向artDialog内部
		// <script type="text/dialog">/* [code] */</script>
		_runScript: function (elem) {
			var fun, i = 0, n = 0,
				tags = elem.getElementsByTagName('script'),
				length = tags.length,
				script = [];
				
			for (; i < length; i ++) {
				if (tags[i].type === 'text/dialog') {
					script[n] = tags[i].innerHTML;
					n ++;
				};
			};
			
			if (script.length) {
				script = script.join('');
				fun = new Function(script);
				fun.call(this);
			};
		},
		
		// 自动切换定位类型
		_autoPositionType: function () {
			this[this.config.fixed ? '_setFixed' : '_setAbsolute']();
		},
		
		
		// 设置静止定位
		// IE6 Fixed @see: http://www.planeart.cn/?p=877
		_setFixed: (function () {
			_isIE6 && $(function () {
				var bg = 'backgroundAttachment';
				if (_$html.css(bg) !== 'fixed' && _$body.css(bg) !== 'fixed') {
					_$html.css({
						backgroundImage: 'url(about:blank)',
						backgroundAttachment: 'fixed'
					});
				};
			});
			
			return function () {
				var that = this;
				var $elem = that.DOM.wrap,
					style = $elem[0].style;
				
				if (_isIE6) {
					var left = parseInt($elem.css('left')),
						top = parseInt($elem.css('top')),
						sLeft = _$document.scrollLeft(),
						sTop = _$document.scrollTop(),
						txt = '(document.documentElement)';
					
					this._setAbsolute();
					style.setExpression('left', 'eval(' + txt + '.scrollLeft + '
						+ (left - sLeft) + ') + "px"');
					style.setExpression('top', 'eval(' + txt + '.scrollTop + '
						+ (top - sTop) + ') + "px"');
				} else {
					style.position = 'fixed';
				};
			};
		}()),
		
		// 设置绝对定位
		_setAbsolute: function () {
			var that = this;
			var style = that.DOM.wrap[0].style;
			
			if (_isIE6) {
				style.removeExpression('left');
				style.removeExpression('top');
			};
	
			style.position = 'absolute';
		},
		
		// 按钮事件触发
		_trigger: function (name) {
			var that = this,
				fn = that._listeners[name] && that._listeners[name].callback;
			if (name === 'max'){
				return that.max();
			} else if (name === 'min'){
				return that.min();
			} else {
				return typeof fn !== 'function' || fn.call(that, window) !== false ?
					that.close() : that;
			}
		},
		
		// 事件代理
		_addEvent: function () {
			var winResize, resizeTimer,
				that = this,
				config = that.config,
				DOM = that.DOM,
				winSize = _$window.width() * _$window.height();
			
			winResize = function () {
				if (!$(that.DOM.content).is(':hidden')){
					var newSize,
					oldSize = winSize,
					elem = config.follow,
					width = config.width,
					height = config.height,
					left = config.left,
					top = config.top;
					if ('all' in document) {
						// IE6~7 window.onresize bug
						newSize = _$window.width() * _$window.height();
						winSize = newSize;
						if (oldSize === newSize) return;
					};
					
					if (width || height) that.size(width, height);
					if (elem) {
						that.follow(elem);
					} else if (left || top) {
						that.position(left, top);
					} else {
					};
				}
			};
			
			that._winResize = function () {
				resizeTimer && clearTimeout(resizeTimer);
				resizeTimer = setTimeout(function () {
					winResize();
				}, 40);
			};
			
			// 窗口调节事件
			_$window.bind('resize', that._winResize);
			
			// 监听点击
			DOM.wrap
			.bind('click', function (event) {
				var target = event.target, callbackID;
				
				if (target.disabled) return false; // IE BUG
				
				if (target === DOM.close[0]) {
					that._trigger(config.cancelVal);
					return false;
				} else if (DOM.max && target === DOM.max[0]){
					that._trigger('max');
					return false;
				} else if (DOM.min && target === DOM.min[0]){
					that._trigger('min');
					return false;
				}else {
					callbackID = target[_expando + 'callback'];
					callbackID && that._trigger(callbackID);
				};
				
				that._ie6SelectFix();
			})
			.bind('mousedown', function () {
				that.focus(false);
			});
		},
		
		// 卸载事件代理
		_removeEvent: function () {
			var that = this,
				DOM = that.DOM;
			
			DOM.wrap.unbind();
			_$window.unbind('resize', that._winResize);
		}
		
	};
	
	artDialog.fn._init.prototype = artDialog.fn;
	
	$.fn.dialog = $.fn.artDialog = function () {
		var config = arguments;
		this[this.live ? 'live' : 'bind']('click', function () {
			artDialog.apply(this, config);
			return false;
		});
		return this;
	};


	
	/** 最顶层的对话框API */
	artDialog.focus = null;
	
	
	
	/** 对话框列表 */
	artDialog.list = {};



	// 全局快捷键
	_$document.bind('keydown', function (event) {
		var target = event.target,
			nodeName = target.nodeName,
			rinput = /^INPUT|TEXTAREA$/,
			api = artDialog.focus,
			keyCode = event.keyCode;
	
		if (!api || !api.config.esc || rinput.test(nodeName)) return;
			
		keyCode === 27 && api._trigger(api.config.cancelVal);
	});



	// 获取artDialog路径
	_path = window['_artDialog_path'] || (function (script, i, me) {
		for (i in script) {
			// 如果通过第三方脚本加载器加载本文件，请保证文件名含有"artDialog"字符
			if (script[i].src && script[i].src.indexOf('artDialog') !== -1) me = script[i];
		};
		
		_thisScript = me || script[script.length - 1];
		me = _thisScript.src.replace(/\\/g, '/');
		return me.lastIndexOf('/') < 0 ? '.' : me.substring(0, me.lastIndexOf('/'));
	}(document.getElementsByTagName('script')));




	// 无阻塞载入CSS (如"artDialog.js?skin=aero")
	_skin = _thisScript.src.split('skin=')[1];
	if (_skin) {
		var link = document.createElement('link');
		link.rel = 'stylesheet';
//		link.href = _path + '/skins/' + _skin + '.css?' + artDialog.fn.version;
		
		//换肤修改
		link.href = com.ue.global.path + '/framework/theme/' + com.ue.global.theme + '/component/popupLayer/css/default.css?' + artDialog.fn.version;
		
		_thisScript.parentNode.insertBefore(link, _thisScript);
	};



	// 触发浏览器预先缓存背景图片
	_$window.bind('load', function () {
		setTimeout(function () {
			if (_count) return;
			artDialog({left: '-9999em',time: 9,fixed: false,lock: false,focus: false});
		}, 150);
	});



	// 开启IE6 CSS背景图片缓存
	try {
		document.execCommand('BackgroundImageCache', false, true);
	} catch (e) {};



	/** 模板 */
	artDialog.templates = [
	'<div class="aui_outer">',
		'<table class="aui_border">',
			'<tbody>',
				'<tr>',
					'<td class="aui_nw"></td>',
					'<td class="aui_n"></td>',
					'<td class="aui_ne"></td>',
				'</tr>',
				'<tr>',
					'<td class="aui_w"></td>',
					'<td class="aui_center">',
						'<table class="aui_inner">',
							'<tbody>',
								'<tr>',
									'<td colspan="2" class="aui_header">',
										'<div class="aui_titleBar">',
											'<div class="aui_title"></div>',
											'<div class="op_menu">',
											'<a onkeydown="return false;" class="aui_close" href="javascript:/*artDialog*/;" title="关闭">',
												'\xd7',
											'</a>',
											'<a onkeydown="return false;" class="aui_max" href="javascript:/*artDialog*/;" title="最大化">',
												'+',
											'</a>',
											'<a onkeydown="return false;" class="aui_min" href="javascript:/*artDialog*/;" title="最小化">',
												'-',
											'</a>',
											'<a style="clear: both;"></a>',
											'</div>',
										'</div>',
									'</td>',
								'</tr>',
								'<tr>',
									'<td class="aui_icon">',
										'<div class="aui_iconBg"></div>',
									'</td>',
									'<td class="aui_main">',
										'<div class="aui_content"></div>',
									'</td>',
								'</tr>',
								'<tr>',
									'<td colspan="2" class="aui_footer">',
										'<div class="aui_buttons"></div>',
									'</td>',
								'</tr>',
							'</tbody>',
						'</table>',
					'</td>',
					'<td class="aui_e"></td>',
				'</tr>',
				'<tr>',
					'<td class="aui_sw"></td>',
					'<td class="aui_s"></td>',
					'<td class="aui_se"></td>',
				'</tr>',
			'</tbody>',
		'</table>',
	'</div>'
	].join('');
	
	/**
	 * 默认配置
	 */
	artDialog.defaults = {
									// 消息内容
		content: '<div class="aui_loading"><span>loading..</span></div>',
		title: "消息",		// 标题. 默认'消息'
		button: null,				// 自定义按钮
		ok: null,					// 确定按钮回调函数
		cancel: null,				// 取消按钮回调函数
		init: null,					// 对话框初始化后执行的函数
		close: null,				// 对话框关闭前执行的函数
		okVal: "确定",		// 确定按钮文本. 默认'确定'
		cancelVal: "取消",	// 取消按钮文本. 默认'取消'
		width: 'auto',				// 内容宽度
		height: 'auto',				// 内容高度
		minWidth: 96,				// 最小宽度限制
		minHeight: 32,				// 最小高度限制
		padding: '0px 0px',		// 内容与边界填充距离
		skin: '',					// 皮肤名(多皮肤共存预留接口)
		icon: null,					// 消息图标名称
		time: null,					// 自动关闭时间
		esc: false,					// 是否支持Esc键关闭
		focus: true,				// 是否支持对话框按钮聚焦
		show: true,					// 初始化后是否显示对话框
		follow: null,				// 跟随某元素(即让对话框在元素附近弹出)
		path: _path,				// artDialog路径
		lock: false,				// 是否锁屏
		background: '#000',			// 遮罩颜色
		opacity: .3,				// 遮罩透明度
		duration: 300,				// 遮罩透明度渐变动画速度
		fixed: false,				// 是否静止定位
		left: '50%',				// X轴坐标
		top: '50%',					// Y轴坐标
		zIndex: 1987,				// 对话框叠加高度值(重要：此值不能超过浏览器最大限制)
		resize: true,				// 是否允许用户调节尺寸
		drag: true,					// 是否允许用户拖动位置
		
		// 新增属性
		isWidget: false,			// 是否以widget样式显示
		max: false,					// 是否显示最大化按钮
		maxFun: function(){},		// 最大化时的回调
		remaxFun: function(){},		// 还原时的回调
		min: false,					// 是否显示最小化按钮
		minFun: function(){},		// 最小化时的回调
		showFun: function(){},
		resizeFun: function(){},	// 窗口改变大小时的回调
		dragFun: function(){},		// 窗口拖动时的回调
		
		focusFun: function(){}		// 窗口聚焦时执行的回调
};

	window.artDialog = $.dialog = $.artDialog = artDialog;
}((window.jQuery && (window.art = jQuery)) || window.art, this));
