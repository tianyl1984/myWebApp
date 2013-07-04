
/*!---------------------------------------------------------------
	可选外置模块：话框拖拽支持
------------------------------------------------------------------*/
;(function ($) {
	var _dragEvent, _use,
		_$window = $(window),
		_$document = $(document),
		_elem = document.documentElement,
		_isIE6 = !-[1,] && !('minWidth' in _elem.style),
		_isLosecapture = 'onlosecapture' in _elem,
		_isSetCapture = 'setCapture' in _elem;

	// 拖拽事件
	artDialog.dragEvent = function () {
		var that = this,
			proxy = function (name) {
				var fn = that[name];
				that[name] = function () {
					return fn.apply(that, arguments);
				};
			};
			
		proxy('start');
		proxy('move');
		proxy('end');
	};

	artDialog.dragEvent.prototype = {
	
		// 开始拖拽
		onstart: $.noop,
		start: function (event) {
			_$document
			.bind('mousemove', this.move)
			.bind('mouseup', this.end)
			.bind('touchmove', this.move)
			.bind('touchend', this.end);
			
			this._sClientX = getClientX(event);
			this._sClientY = getClientY(event);
			this.onstart(this._sClientX, this._sClientY);
			
			return false;
		},
		
		// 正在拖拽
		onmove: $.noop,
		move: function (event) {
			this._mClientX = getClientX(event);
			this._mClientY = getClientY(event);
			
			this.onmove(
				getClientX(event) - this._sClientX,
				getClientY(event) - this._sClientY
			);
			
			return false;
		},
		
		// 结束拖拽
		onend: $.noop,
		end: function (event) {
			_$document
			.unbind('mousemove', this.move)
			.unbind('mouseup', this.end);
			
			//扩展pad端支持
			_$document
			.unbind('touchmove', this.move)
			.unbind('touchend', this.end);
			
			this.onend(event.clientX, event.clientY);
			return false;
		}
		
	};

	_use = function (event) {
		var limit, startWidth, startHeight, startLeft, startTop, isResize,
			api = artDialog.focus,
			config = api.config,
			DOM = api.DOM,
			wrap = DOM.wrap,
			title = DOM.title,
			main = DOM.main;
			// extends code
			$content = DOM.content;
	
		// 清除文本选择
		var clsSelect = 'getSelection' in window ? function () {
			window.getSelection().removeAllRanges();
		} : function () {
			try {
				document.selection.empty();
			} catch (e) {};
		};
		
		// 对话框准备拖动
		_dragEvent.onstart = function (x, y) {
			if (isResize) {
				startWidth = main[0].offsetWidth;
				startHeight = main[0].offsetHeight;
			} else {
				startLeft = wrap[0].offsetLeft;
				startTop = wrap[0].offsetTop;
			};
			
			_$document.bind('dblclick', _dragEvent.end);
			
			!_isIE6 && _isLosecapture ?
				title.bind('losecapture', _dragEvent.end) :
				_$window.bind('blur', _dragEvent.end);
			
			_isSetCapture && title[0].setCapture();
			
			
			wrap.addClass('aui_state_drag');
			api.focus();
		};
		
		// 对话框拖动进行中
		_dragEvent.onmove = function (x, y) {
			
			if (isResize) {
				var wrapStyle = wrap[0].style,
					style = main[0].style,
					width = x + startWidth,
					height = y + startHeight,
					contentStyle = $content[0].style;
				
				wrapStyle.width = 'auto';
				
				style.width = Math.max(200, width) + 'px';
				contentStyle.width = style.width;
				config.width = wrap[0].offsetWidth;		//外边框宽
				
				wrapStyle.width = config.width + 'px';
				
				config.height = Math.max(0, height);
				style.height = config.height + 'px';
				contentStyle.height = style.height;
				
			} else {
				var style = wrap[0].style,
					left = x + startLeft,
					top = y + startTop;
				style.left = Math.max(limit.minX, Math.min(limit.maxX, left)) + 'px';
				style.top = Math.max(limit.minY, Math.min(limit.maxY, top)) + 'px';
				// --*-- 改变对话框left和top属性
				config.left = left;
				config.top = top;
			};
			
			clsSelect();
			api._ie6SelectFix();
		};
		
		// 对话框拖动结束
		_dragEvent.onend = function (x, y) {
			_$document.unbind('dblclick', _dragEvent.end);
			!_isIE6 && _isLosecapture ?
				title.unbind('losecapture', _dragEvent.end) :
				_$window.unbind('blur', _dragEvent.end);
			_isSetCapture && title[0].releaseCapture();
			
			_isIE6 && api._autoPositionType();
			
			wrap.removeClass('aui_state_drag');
			
			// --*-- 执行回调
			if (isResize) {
				config.resizeFun();
			} else {
				config.dragFun();
			}
		};
		
		isResize = event.target === DOM.se[0] ? true : false;
		
		limit = (function () {
			var maxX, maxY,
				wrap = api.DOM.wrap[0],
				fixed = wrap.style.position === 'fixed',
				ow = wrap.offsetWidth,
				oh = wrap.offsetHeight,
				ww = _$window.width(),
				wh = _$window.height(),
				dl = fixed ? 0 : _$document.scrollLeft(),
				dt = fixed ? 0 : _$document.scrollTop(),
				
			// 坐标最大值限制
			maxX = ww - ow + dl;
			maxY = wh - oh + dt;
			
			return {
				minX: dl,
				minY: dt,
				maxX: maxX,
				maxY: maxY
			};
		})();
		
		_dragEvent.start(event);
	};

	// 代理 mousedown 事件触发对话框拖动
	_$document.bind('mousedown', function (event) {
		var api = artDialog.focus;
		if (!api) return;
	
		var target = event.target,
			config = api.config,
			DOM = api.DOM;
		
		if (config.drag !== false && target === DOM.title[0]
		|| config.resize !== false && target === DOM.se[0]) {
			_dragEvent = _dragEvent || new artDialog.dragEvent();
			_use(event);
			return false;// 防止firefox与chrome滚屏
		};
	});
	
	// pad端 代理 touchStart 事件触发对话框拖动
	_$document.bind('touchstart', function (event) {
		var api = artDialog.focus;
		if (!api) return;
	
		var target = event.target,
			config = api.config,
			DOM = api.DOM;
		
		if (config.drag !== false && target === DOM.title[0]
		|| config.resize !== false && target === DOM.se[0]) {
			_dragEvent = _dragEvent || new artDialog.dragEvent();
			_use(event);
			return false;// 防止firefox与chrome滚屏
		};
	});

})(window.jQuery || window.art);

/**判断终端是否为pad*/
function isIpadBrowser(){
	if((navigator.userAgent.match(/iPhone/i)) || (navigator.userAgent.match(/iPad/i))) {	// 判断是否为ipad或iphone
		if (document.cookie.indexOf("iphone_redirect=false") == -1) {
			return true;
		}
	} 
	return false;
}

/**获取事件坐标，兼容pad*/
function getClientX(event){
	if (isIpadBrowser()){
		event = window.event;
		return event.targetTouches[0].clientX;
	} else {
		return event.clientX;
	}
}
function getClientY(event){
	if (isIpadBrowser()){
		event = window.event;
		return event.targetTouches[0].clientY;
	} else {
		return event.clientY;
	}
}