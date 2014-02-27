
var w2ui  = w2ui  || {};
var w2obj = w2obj || {};

$.fn.w2destroy = function (name) {
	if (!name && this.length > 0) name = this.attr('name');
	if (typeof name == 'string' && w2ui[name]) w2ui[name].destroy();
	if (typeof name == 'object') name.destroy();
};

(function () {
	var w2grid = function(options) {

		// public properties
		this.name  	  			= null;
		this.box				= null; 	// HTML element that hold this element
		this.columns			= []; 		// { field, caption, size, attr, render, hidden, gridMinWidth, [editable: {type, inTag, outTag, style, items}] }
		this.columnGroups		= [];		// { span: int, caption: 'string', master: true/false }
		this.records			= [];		// { recid: int(requied), field1: 'value1', ... fieldN: 'valueN', style: 'string', editable: true/false, summary: true/false, changed: true/false, changes: object }

		this.show = {
			columnHeaders	: true,
			emptyRecords	: true
		};

		this.autoLoad		= true; 	// for infinite scroll
		this.recordHeight	= 24;
		this.keyboard 		= true;

		this.total			= 0;		// server total
		this.style			= '';
		this.ranges 		= [];

		// internal
		this.last = {
			field		: 'all',
			scrollTop	: 0,
			scrollLeft	: 0,
			selected	: [],
			range_start : null,
			range_end   : null
		};

		$.extend(true, this, w2obj.grid, options);
	};

	// ====================================================
	// -- Registers as a jQuery plugin

	$.fn.w2grid = function(method) {
		if (typeof method === 'object' || !method ) {
			// check required parameters
			if (!method || typeof method.name == 'undefined') {
				console.log('ERROR: The parameter "name" is required but not supplied in $().w2grid().');
				return;
			}
			if (typeof w2ui[method.name] != 'undefined') {
				console.log('ERROR: The parameter "name" is not unique. There are other objects already created with the same name (obj: '+ method.name +').');
				return;
			}
			// remember items
			var columns		= method.columns;
			var columnGroups= method.columnGroups;
			var records		= method.records;
			// extend items
			var object = new w2grid(method);
			$.extend(object, { postData: {}, records: [], columns: [], searches: [], toolbar: {}, sortData: [], searchData: [], handlers: [] });
			// reassign variables
			for (var p in columns)		object.columns[p]		= $.extend(true, {}, columns[p]);
			for (var p in columnGroups) object.columnGroups[p] 	= $.extend(true, {}, columnGroups[p]);

			// check if there are records without recid
			for (var r in records) {
				if (records[r].recid == null || typeof records[r].recid == 'undefined') {
					console.log('ERROR: Cannot add records without recid. (obj: '+ object.name +')');
					return;
				}
				object.records[r] = $.extend(true, {}, records[r]);
			}
			// render if necessary
			if ($(this).length != 0) {
				object.render($(this)[0]);
			}
			// register new object
			w2ui[object.name] = object;
			
			$(document).on('keydown', function(event){
				var w2ui_name = object.name;
				var tag = event.target.tagName;
				if ($.inArray(tag, ['INPUT', 'SELECT', 'TEXTAREA']) != -1) return;
				if ($(event.target).prop('contenteditable') == 'true') return;
				if (!w2ui_name) return;
				// pass to appropriate widget
				if (w2ui[w2ui_name] && typeof w2ui[w2ui_name].keydown == 'function') {
					w2ui[w2ui_name].keydown.call(w2ui[w2ui_name], event);
				}
			});
			
			return object;

		} else if (w2ui[$(this).attr('name')]) {
			var obj = w2ui[$(this).attr('name')];
			obj[method].apply(obj, Array.prototype.slice.call(arguments, 1));
			return this;
		} else {
			console.log('ERROR: Method ' +  method + ' does not exist on jQuery.w2grid');
		}
	};

	
	var tmp = {};
	
	w2grid.prototype = {

		find: function (obj, returnIndex) {
			if (typeof obj == 'undefined' || obj == null) obj = {};
			var recs = [];
			for (var i=0; i<this.records.length; i++) {
				var match = true;
				for (var o in obj) {
					var val = this.records[i][o];
					if (String(o).indexOf('.') != -1) val = this.parseField(this.records[i],o);
					if (obj[o] != val) match = false;
				}
				if (match && returnIndex !== true) recs.push(this.records[i]);
				if (match && returnIndex === true) recs.push(i);
			}
			return recs;
		},

		get: function (recid, returnIndex) {
			for (var i=0; i<this.records.length; i++) {
				if (this.records[i].recid == recid) {
					if (returnIndex === true) return i; else return this.records[i];					
				}
			}
			return null;
		},

		select: function () {
			var selected = 0;
			for (var a = 0; a < arguments.length; a++) {
				var recid  = typeof arguments[a] == 'object' ? arguments[a].recid : arguments[a];
				var record = this.get(recid);
				if (record == null) continue;
				if (record.selected === true) continue;
				record.selected = true;
				$('#grid_'+ this.name +'_rec_'+ recid).addClass('w2ui-selected').data('selected', 'yes');
				selected++;
			}
			return selected;
		},

		selectNone: function () {
			this.last.selected = [];
			for (var i in this.records) {
				var rec = this.records[i];
				if (rec.selected === true) {
					rec.selected = false;
					var tmp = $('#grid_'+ this.name +'_rec_'+ rec.recid);
					tmp.removeClass('w2ui-selected').removeData('selected');
				}
			}
		},

		getSelection: function (returnIndex) {
			var sel = this.find({ selected: true }, true);
			var ret = [];
			for (var s in sel) {
				if (returnIndex === true) {
					ret.push(sel[s]);
				} else {
					ret.push(this.records[sel[s]].recid);
				}
			}
			return ret;
		},

		reset: function (noRefresh) {
			// reset last remembered state
			this.last.scrollTop		= 0;
			this.last.scrollLeft	= 0;
			this.last.selected		= [];
			this.last.range_start	= null;
			this.last.range_end		= null;
			// select none without refresh
			this.set({ selected: false }, true);
			// refresh
			if (!noRefresh) this.refresh();
		},	

		click: function (recid, event) {
			var column = null;
			if (typeof recid == 'object') {
				column = recid.column;
				recid  = recid.recid;
			}
			if (typeof event == 'undefined') event = {};
			
			// column user clicked on
			if (column == null && event.target) {
				var tmp = event.target;
				if (tmp.tagName != 'TD') tmp = $(tmp).parents('td')[0];
				if (typeof $(tmp).attr('col') != 'undefined') column = parseInt($(tmp).attr('col'));
			}

			// default action
			var obj = this;
			var ind    = this.get(recid, true);
			var record = this.records[ind];
			
			this.selectNone();
			this.select({ recid: record.recid, column: column });
			setTimeout(function () { if (window.getSelection) window.getSelection().removeAllRanges(); }, 10);
			
			obj.initResize();
		},

		keydown: function (event) {
			var obj = this;
			if (obj.keyboard !== true) return;
			var sel 	= obj.getSelection();
			if (sel.length == 0) return;
			var recid	= sel[0];
			var columns = [];
			var ind		= obj.get(recid, true);
			var recEL	= $('#grid_'+ obj.name +'_rec_'+ obj.records[ind].recid);
			var cancel  = false;
			switch (event.keyCode) {
				case 38: // up
					if (recEL.length <= 0) break;
					var prev = ind-1;
					obj.selectNone();
					if (prev >= 0) {
						obj.click({ recid: obj.records[prev].recid, column: columns[0] }, event);
						obj.scrollIntoView(prev);
						cancel = true;
					}
					break;

				case 40: // down
					if (recEL.length <= 0) break;
					var next = ind+1;
					obj.selectNone();
					if (next < obj.records.length) {
						obj.click({ recid: obj.records[next].recid, column: columns[0] }, event);
						obj.scrollIntoView(next);
						cancel = true;
					}
					break;
			}

			if (cancel) { // cancel default behaviour
				if (event.preventDefault) event.preventDefault();
			}
		},

		scrollIntoView: function (ind) {
			var obj = this;
			if (typeof ind == 'undefined') {
				var sel = this.getSelection();
				if (sel.length == 0) return;
				ind	= this.get(sel[0], true);
			}
			var records	= $('#grid_'+ this.name +'_records');
			if (records.length == 0) return;
			var len = obj.records.length;
			if (records.height() > this.recordHeight * len) return;
			
			var t1 = Math.floor(records[0].scrollTop / this.recordHeight);
			var t2 = t1 + Math.floor(records.height() / this.recordHeight);
			
			if (ind == t1) records.animate({ 'scrollTop': records.scrollTop() - records.height() / 1.3 });
			if (ind == t2) records.animate({ 'scrollTop': records.scrollTop() + records.height() / 1.3 });
			if (ind < t1 || ind > t2) records.animate({ 'scrollTop': (ind - 1) * this.recordHeight });
		},
		
		resize: function () {
			var time = (new Date()).getTime();
			var obj  = this;
			if (window.getSelection) window.getSelection().removeAllRanges(); // clear selection
			$(this.box).find('> div').css('width', $(this.box).width()).css('height', $(this.box).height());
			
			var body = $('#grid_'+ this.name +'_body');
			body.css({
				top: '0px',
				bottom: '0px',
				left:   '0px',
				right:  '0px'
			});
			
			obj.resizeRecords();
			return (new Date()).getTime() - time;
		},

		refresh: function () {
			var obj  = this;
			var time = (new Date()).getTime();
			if (this.total <= 0) {
				this.total = this.records.length;
			}
			if (window.getSelection) window.getSelection().removeAllRanges(); // clear selection

			// -- body
			var bodyHTML = '';
			bodyHTML +=  '<div id="grid_'+ this.name +'_records" class="w2ui-grid-records"'+
						'	onscroll="var obj = w2ui[\''+ this.name + '\']; '+
						'		obj.last.scrollTop = this.scrollTop; '+
						'		obj.last.scrollLeft = this.scrollLeft; '+
						'		$(\'#grid_'+ this.name +'_columns\')[0].scrollLeft = this.scrollLeft;'+
						'		obj.scroll(event);">'+
							this.getRecordsHTML() +
						'</div>'+
						'<div id="grid_'+ this.name +'_columns" class="w2ui-grid-columns">'+
						'	<table>'+ this.getColumnsHTML() +'</table>'+
						'</div>'; // Columns need to be after to be able to overlap
			$('#grid_'+ this.name +'_body').html(bodyHTML);
			obj.resize();
			
			setTimeout(function () {  obj.resize(); }, 1);
			
			return (new Date()).getTime() - time;
		},

		render: function (box) {
			var time = (new Date()).getTime();
			if (window.getSelection) window.getSelection().removeAllRanges(); // clear selection
			if (typeof box != 'undefined' && box != null) {
				if ($(this.box).find('#grid_'+ this.name +'_body').length > 0) {
					$(this.box).removeAttr('name').removeClass('w2ui-reset w2ui-grid').html('');
				}
				this.box = box;
			}
			if (!this.box) return;
			
			$(this.box).attr('name', this.name).addClass('w2ui-reset w2ui-grid').html('<div><div id="grid_'+ this.name +'_body" class="w2ui-grid-body"></div></div>');
			
			$(this.box)[0].style.cssText += this.style;
			
			this.refresh(); // show empty grid (need it)
			
			return (new Date()).getTime() - time;
		},

		destroy: function () {
			$(window).off('resize', this.tmp_resize);
			// clean up
			if (typeof this.toolbar == 'object' && this.toolbar.destroy) this.toolbar.destroy();
			if ($(this.box).find('#grid_'+ this.name +'_body').length > 0) {
				$(this.box).removeAttr('name').removeClass('w2ui-reset w2ui-grid').html('');
			}
			delete w2ui[this.name];
		},

		initResize: function () {
			var obj = this;
			$(this.box).find('.w2ui-resizer').off('click')
				.on('click', function (event) {
					if (event.stopPropagation) event.stopPropagation(); else event.cancelBubble = true;
					if (event.preventDefault) event.preventDefault();
				})
				.off('mousedown')
				.on('mousedown', function (event) {
					obj.resizing = true;
					obj.last.tmp = {
						x 	: event.screenX,
						y 	: event.screenY,
						gx 	: event.screenX,
						gy 	: event.screenY,
						col : parseInt($(this).attr('name'))
					};
					if (event.stopPropagation) event.stopPropagation(); else event.cancelBubble = true;
					if (event.preventDefault) event.preventDefault();
					// fix sizes
					for (var c in obj.columns) {
						if (typeof obj.columns[c].sizeOriginal == 'undefined') obj.columns[c].sizeOriginal = obj.columns[c].size;
						obj.columns[c].size = obj.columns[c].sizeCalculated;
					}
					// set move event
					var mouseMove = function (event) {
						if (obj.resizing != true) return;
						if (!event) event = window.event;
						obj.last.tmp.x = (event.screenX - obj.last.tmp.x);
						obj.last.tmp.y = (event.screenY - obj.last.tmp.y);
						obj.columns[obj.last.tmp.col].size = (parseInt(obj.columns[obj.last.tmp.col].size) + obj.last.tmp.x) + 'px';
						obj.resizeRecords();
						// reset
						obj.last.tmp.x = event.screenX;
						obj.last.tmp.y = event.screenY;
					};
					var mouseUp = function (event) {
						delete obj.resizing;
						$(document).off('mousemove', 'body');
						$(document).off('mouseup', 'body');
						obj.resizeRecords();
					};
					$(document).on('mousemove', 'body', mouseMove);
					$(document).on('mouseup', 'body', mouseUp);
				})
				.each(function (index, el) {
					var td  = $(el).parent();
					$(el).css({
						"height" 		: '25px',
						"margin-left" 	: (td.width() - 3) + 'px'
					});
				});
		},

		getSize: function(el, type) {
			var bwidth = {
				left: 	parseInt($(el).css('border-left-width')) || 0,
				right:  parseInt($(el).css('border-right-width')) || 0,
				top:  	parseInt($(el).css('border-top-width')) || 0,
				bottom: parseInt($(el).css('border-bottom-width')) || 0
			};
			var mwidth = {
				left: 	parseInt($(el).css('margin-left')) || 0,
				right:  parseInt($(el).css('margin-right')) || 0,
				top:  	parseInt($(el).css('margin-top')) || 0,
				bottom: parseInt($(el).css('margin-bottom')) || 0
			};
			var pwidth = {
				left: 	parseInt($(el).css('padding-left')) || 0,
				right:  parseInt($(el).css('padding-right')) || 0,
				top:  	parseInt($(el).css('padding-top')) || 0,
				bottom: parseInt($(el).css('padding-bottom')) || 0
			};
//			console.log("bwidth.top:"+bwidth.top+",bwidth.bottom:"+bwidth.bottom+",mwidth.top:"+mwidth.top+",mwidth.bottom:"+mwidth.bottom+",pwidth.top:"+pwidth.top+",pwidth.bottom:"+pwidth.bottom+",parseInt($(el).height()):"+parseInt($(el).height()));
			switch (type) {
				case 'top': 	return bwidth.top + mwidth.top + pwidth.top; 
				case 'bottom': 	return bwidth.bottom + mwidth.bottom + pwidth.bottom; 
				case 'left': 	return bwidth.left + mwidth.left + pwidth.left; 
				case 'right': 	return bwidth.right + mwidth.right + pwidth.right; 
				case 'width': 	return bwidth.left + bwidth.right + mwidth.left + mwidth.right + pwidth.left + pwidth.right + parseInt($(el).width()); 
				case 'height': 	return bwidth.top + bwidth.bottom + mwidth.top + mwidth.bottom + pwidth.top + pwidth.bottom + parseInt($(el).height());
				case '+width': 	return bwidth.left + bwidth.right + mwidth.left + mwidth.right + pwidth.left + pwidth.right; 
				case '+height': return bwidth.top + bwidth.bottom + mwidth.top + mwidth.bottom + pwidth.top + pwidth.bottom;
			}
			return 0;
		},
		
		scrollBarSize: function () {
			if (tmp.scrollBarSize) return tmp.scrollBarSize; 
			var html = '<div id="_scrollbar_width" style="position: absolute; top: -300px; width: 100px; height: 100px; overflow-y: scroll;">'+
					   '	<div style="height: 120px">1</div>'+
					   '</div>';
			$('body').append(html);
			tmp.scrollBarSize = 100 - $('#_scrollbar_width > div').width();
			$('#_scrollbar_width').remove();
			if (String(navigator.userAgent).indexOf('MSIE') >= 0) tmp.scrollBarSize  = tmp.scrollBarSize / 2; // need this for IE9+
			return tmp.scrollBarSize;
		},
		
		resizeRecords: function () {
			var obj = this;
			// remove empty records
			$(this.box).find('.w2ui-empty-record').remove();
			// -- Calculate Column size in PX
//			var box			= $(this.box);
			var grid		= $(this.box).find('> div');
			var body 		= $('#grid_'+ this.name +'_body');
			var columns 	= $('#grid_'+ this.name +'_columns');
			var records 	= $('#grid_'+ this.name +'_records');

			body.css('height', grid.height());

			var bodyOverflowX = false;
			var bodyOverflowY = false;
			if (body.width() < $(records).find('>table').width()) bodyOverflowX = true;
			if (body.height() - columns.height() < $(records).find('>table').height() + (bodyOverflowX ? obj.scrollBarSize() : 0)) bodyOverflowY = true;
			
			if (bodyOverflowX || bodyOverflowY) {
				columns.find('> table > tbody > tr:nth-child(1) td.w2ui-head-last').css('width', obj.scrollBarSize()).show();
				records.css({
					top: ((this.columnGroups.length > 0 && this.show.columns ? 1 : 0) + obj.getSize(columns, 'height')) +'px',
					"-webkit-overflow-scrolling": "touch",
					"overflow-x": (bodyOverflowX ? 'auto' : 'hidden'), 
					"overflow-y": (bodyOverflowY ? 'auto' : 'hidden') });
			} else {
				columns.find('> table > tbody > tr:nth-child(1) td.w2ui-head-last').hide();
				records.css({
					top: ((this.columnGroups.length > 0 && this.show.columns ? 1 : 0) + obj.getSize(columns, 'height')) +'px', 
					overflow: 'hidden' 
				});
				if (records.length > 0) { this.last.scrollTop  = 0; this.last.scrollLeft = 0; } // if no scrollbars, always show top
			}
			if (this.show.emptyRecords && !bodyOverflowY) {
				var max = Math.floor(records.height() / this.recordHeight) + 1;
				for (var di = this.total; di <= max; di++) {
					var html  = '';
					html += '<tr class="'+ (di % 2 ? 'w2ui-even' : 'w2ui-odd') + ' w2ui-empty-record" style="height: '+ this.recordHeight +'px">';
					var j = 0;
					while (true && this.columns.length > 0) {
						var col = this.columns[j];
						if (col.hidden) { j++; if (typeof this.columns[j] == 'undefined') break; else continue; }
						html += '<td class="w2ui-grid-data" '+ (typeof col.attr != 'undefined' ? col.attr : '') +' col="'+ j +'"></td>';
						j++;
						if (typeof this.columns[j] == 'undefined') break;
					}
					html += '<td class="w2ui-grid-data-last"></td>';
					html += '</tr>';
					$('#grid_'+ this.name +'_records > table').append(html);
				}
			}
			var width_max = parseInt(body.width()) - (bodyOverflowY ? obj.scrollBarSize() : 0);
			var width_box = width_max;
			var percent = 0;
				// gridMinWidth processiong
			var restart = false;
			for (var i=0; i<this.columns.length; i++) {
				var col = this.columns[i];
				if (typeof col.gridMinWidth != 'undefined') {
					if (col.gridMinWidth > width_box && col.hidden !== true) {
						col.hidden = true;
						restart = true;
					}
					if (col.gridMinWidth < width_box && col.hidden === true) {
						col.hidden = false;
						restart = true;
					}
				}
			}
			if (restart === true) {
				this.refresh();
				return;
			}
			// assign PX column s
			for (var i=0; i<this.columns.length; i++) {
				var col = this.columns[i];
				if (col.hidden) continue;
				if (String(col.size).substr(String(col.size).length-2).toLowerCase() == 'px') {
					width_max -= parseFloat(col.size);
					this.columns[i].sizeCalculated = col.size;
					this.columns[i].sizeType = 'px';
				} else {
					percent += parseFloat(col.size);
					this.columns[i].sizeType = '%';
					delete col.sizeCorrected;
				}
			}
			// if sum != 100% -- reassign proportionally
			if (percent != 100 && percent > 0) {
				for (var i=0; i<this.columns.length; i++) {
					var col = this.columns[i];
					if (col.hidden) continue;
					if (col.sizeType == '%') {
						col.sizeCorrected = Math.round(parseFloat(col.size) * 100 * 100 / percent) / 100 + '%';
					}
				}
			}
			// calculate % columns
			for (var i=0; i<this.columns.length; i++) {
				var col = this.columns[i];
				if (col.hidden) continue;
				if (col.sizeType == '%') {
					if (typeof this.columns[i].sizeCorrected != 'undefined') {
						// make it 1px smaller, so margin of error can be calculated correctly
						this.columns[i].sizeCalculated = Math.floor(width_max * parseFloat(col.sizeCorrected) / 100) - 1 + 'px';
					} else {
						// make it 1px smaller, so margin of error can be calculated correctly
						this.columns[i].sizeCalculated = Math.floor(width_max * parseFloat(col.size) / 100) - 1 + 'px';
					}
				}
			}
			// fix margin of error that is due percentage calculations
			var width_cols = 0;
			for (var i=0; i<this.columns.length; i++) {
				var col = this.columns[i];
				if (col.hidden) continue;
				if (typeof col.min == 'undefined') col.min = 20;
				if (parseInt(col.sizeCalculated) < parseInt(col.min)) col.sizeCalculated = col.min + 'px';
				if (parseInt(col.sizeCalculated) > parseInt(col.max)) col.sizeCalculated = col.max + 'px';
				width_cols += parseInt(col.sizeCalculated); 
			}
			var width_diff = parseInt(width_box) - parseInt(width_cols);
			if (width_diff > 0 && percent > 0) {
				var i = 0;
				while (true) {
					var col = this.columns[i];
					if (typeof col == 'undefined') { i = 0; continue; }
					if (col.hidden || col.sizeType == 'px') { i++; continue; }
					col.sizeCalculated = (parseInt(col.sizeCalculated) + 1) + 'px';
					width_diff--;
					if (width_diff == 0) break;
					i++;
				}
			} else if (width_diff > 0) {
				columns.find('> table > tbody > tr:nth-child(1) td.w2ui-head-last').css('width', obj.scrollBarSize()).show();
			}
			// resize columns
			columns.find('> table > tbody > tr:nth-child(1) td').each(function (index, el) {
				var ind = $(el).attr('col');
				if (typeof ind != 'undefined' && obj.columns[ind]) $(el).css('width', obj.columns[ind].sizeCalculated);
				// last column
				if ($(el).hasClass('w2ui-head-last')) {
					$(el).css('width', obj.scrollBarSize() + (width_diff > 0 && percent == 0 ? width_diff : 0) + 'px');
				}
			});
			// if there are column groups - hide first row (needed for sizing)
			if (columns.find('> table > tbody > tr').length == 3) {
				columns.find('> table > tbody > tr:nth-child(1) td').html('').css({
					'height'	: '0px',
					'border'	: '0px',
					'padding'	: '0px',
					'margin'	: '0px'
				});
			}
			// resize records
			records.find('> table > tbody > tr:nth-child(1) td').each(function (index, el) {
				var ind = $(el).attr('col');
				if (typeof ind != 'undefined' && obj.columns[ind]) $(el).css('width', obj.columns[ind].sizeCalculated);
				// last column
				if ($(el).hasClass('w2ui-grid-data-last')) {
					$(el).css('width', (width_diff > 0 && percent == 0 ? width_diff : 0) + 'px');
				}
			});

			this.initResize();
			// apply last scroll if any
			if (this.last.scrollTop != '' && records.length > 0) {
				columns.prop('scrollLeft', this.last.scrollLeft);
				records.prop('scrollTop',  this.last.scrollTop);
				records.prop('scrollLeft', this.last.scrollLeft);
			}
		},
		
		getColumnsHTML: function () {
			var obj  = this;
			var html = '';
			if (this.show.columnHeaders) {
				if (this.columnGroups.length > 0) {
					html = getColumns(true) + getGroups() + getColumns(false);
				} else {
					html = getColumns(true);
				}
			}
			return html;

			function getGroups () {
				var html = '<tr>';
				// add empty group at the end
				if (obj.columnGroups[obj.columnGroups.length-1].caption != '') obj.columnGroups.push({ caption: '' });

				var ii = 0;
				for (var i=0; i<obj.columnGroups.length; i++) {
					var colg = obj.columnGroups[i];
					var col  = obj.columns[ii];
					if (typeof colg.span == 'undefined' || colg.span != parseInt(colg.span)) colg.span = 1;
					if (typeof colg.colspan != 'undefined') colg.span = colg.colspan;
					if (colg.master === true) {
						var resizer = "";
						if (col.resizable == true) {
							resizer = '<div class="w2ui-resizer" name="'+ ii +'"></div>';
						}
						html += '<td class="w2ui-head" col="'+ ii + '" rowspan="2" colspan="'+ (colg.span + (i == obj.columnGroups.length-1 ? 1 : 0) ) +'" '+
									'>'+
									resizer +
								'	<div class="w2ui-col-group">'+
										(col.caption == '' ? '&nbsp;' : col.caption) +
								'	</div>'+ 
								'</td>';
					} else {
						html += '<td class="w2ui-head" col="'+ ii + '" '+
								'		colspan="'+ (colg.span + (i == obj.columnGroups.length-1 ? 1 : 0) ) +'">'+
								'	<div class="w2ui-col-group">'+
									(colg.caption == '' ? '&nbsp;' : colg.caption) +
								'	</div>'+
								'</td>';
					}
					ii += colg.span;
				}
				html += '</tr>';	
				return html;			
			}

			function getColumns (master) {
				var html = '<tr>';
				var ii = 0;
				var id = 0;
				for (var i=0; i<obj.columns.length; i++) {
					var col  = obj.columns[i];
					var colg = {};
					if (i == id) {
						id = id + (typeof obj.columnGroups[ii] != 'undefined' ? parseInt(obj.columnGroups[ii].span) : 0);
						ii++;
					}
					if (typeof obj.columnGroups[ii-1] != 'undefined') var colg = obj.columnGroups[ii-1];
					if (col.hidden) continue;
					if (colg['master'] !== true || master) { // grouping of columns
						var resizer = "";
						if (col.resizable == true) {
							resizer = '<div class="w2ui-resizer" name="'+ i +'"></div>';
						}
						html += '<td col="'+ i +'" class="w2ui-head" >'+
									resizer +
								'	<div>'+  
										(col.caption == '' ? '&nbsp;' : col.caption) +
								'	</div>'+ 
								'</td>';
					}
				}
				html += '<td class="w2ui-head w2ui-head-last"><div>&nbsp;</div></td>';
				html += '</tr>';
				return html;
			}
		},

		getRecordsHTML: function () {
			// larget number works better with chrome, smaller with FF.
			if (this.total > 300) this.show_extra = 30; else this.show_extra = 300; 
			var records	= $('#grid_'+ this.name +'_records');
			var limit	= Math.floor(records.height() / this.recordHeight) + this.show_extra + 1;
			// always need first record for resizing purposes
			var html = '<table>' + this.getRecordHTML(-1, 0);
			// first empty row with height
			html += '<tr id="grid_'+ this.name + '_rec_top" line="top" style="height: '+ 0 +'px">'+
					'	<td colspan="200"></td>'+
					'</tr>';
			for (var i = 0; i < limit; i++) {
				html += this.getRecordHTML(i, i+1);
			}
			html += '<tr id="grid_'+ this.name + '_rec_bottom" line="bottom" style="height: '+ ((this.total - limit) * this.recordHeight) +'px">'+
					'	<td colspan="200"></td>'+
					'</tr>'+
					'<tr id="grid_'+ this.name +'_rec_more" style="display: none">'+
					'	<td colspan="200" class="w2ui-load-more"></td>'+
					'</tr>'+
					'</table>';
			this.last.range_start = 0;
			this.last.range_end	  = limit;
			return html;
		},

		scroll: function (event) {
			var obj  = this;
			var records	= $('#grid_'+ this.name +'_records');
			if (this.records.length == 0 || records.length == 0 || records.height() == 0) return;
			if (this.total > 300) this.show_extra = 30; else this.show_extra = 300; 
			// need this to enable scrolling when this.limit < then a screen can fit
			if (records.height() < this.total * this.recordHeight && records.css('overflow-y') == 'hidden') {
				if (this.total > 0) this.refresh();
				return;
			}
			var t1 = Math.floor(records[0].scrollTop / this.recordHeight + 1);
			var t2 = Math.floor(records[0].scrollTop / this.recordHeight + 1) + Math.round(records.height() / this.recordHeight);
			if (t1 > this.total) t1 = this.total;
			if (t2 > this.total) t2 = this.total;
			// only for local data source, else no extra records loaded
			if ((this.total <= 300)) return;
			// regular processing
			var start 	= Math.floor(records[0].scrollTop / this.recordHeight) - this.show_extra;
			var end		= start + Math.floor(records.height() / this.recordHeight) + this.show_extra * 2 + 1;
			// var div     = start - this.last.range_start;
			if (start < 1) start = 1;
			if (end > this.total) end = this.total;
			var tr1 = records.find('#grid_'+ this.name +'_rec_top');
			var tr2 = records.find('#grid_'+ this.name +'_rec_bottom');
			var first = parseInt(tr1.next().attr('line'));
			var last  = parseInt(tr2.prev().attr('line'));
			if (first < start || first == 1 || this.last.pull_refresh) { // scroll down
				if (end <= last + this.show_extra - 2 && end != this.total) return;
				this.last.pull_refresh = false;
				// remove from top
				while (true) {
					var tmp = records.find('#grid_'+ this.name +'_rec_top').next();
					if (tmp.attr('line') == 'bottom') break;
					if (parseInt(tmp.attr('line')) < start) tmp.remove();  else break;
				}
				// add at bottom
				var tmp = records.find('#grid_'+ this.name +'_rec_bottom').prev();
				var rec_start = tmp.attr('line');
				if (rec_start == 'top') rec_start = start;
				for (var i = parseInt(rec_start) + 1; i <= end; i++) {
					if (!this.records[i-1]) continue;
					if (this.records[i-1].expanded === true) this.records[i-1].expanded = false;
					tr2.before(this.getRecordHTML(i-1, i));
				}
			} else { // scroll up
				if (start >= first - this.show_extra + 2 && start > 1) return;
				// remove from bottom
				while (true) {
					var tmp = records.find('#grid_'+ this.name +'_rec_bottom').prev();
					if (tmp.attr('line') == 'top') break;
					if (parseInt(tmp.attr('line')) > end) tmp.remove(); else break;
				}
				// add at top
				var tmp = records.find('#grid_'+ this.name +'_rec_top').next();
				var rec_start = tmp.attr('line');
				if (rec_start == 'bottom') rec_start = end;
				for (var i = parseInt(rec_start) - 1; i >= start; i--) {
					if (!this.records[i-1]) continue;
					if (this.records[i-1].expanded === true) this.records[i-1].expanded = false;
					tr1.after(this.getRecordHTML(i-1, i));
				}
			}
			// first/last row size
			var h1 = (start - 1) * obj.recordHeight;
			var h2 = (this.total - end) * obj.recordHeight;
			if (h2 < 0) h2 = 0;
			tr1.css('height', h1 + 'px');
			tr2.css('height', h2 + 'px');
			obj.last.range_start = start;
			obj.last.range_end   = end;
			return;
		},

		getRecordHTML: function (ind, lineNum) {
			var rec_html = '';
			// first record needs for resize purposes
			if (ind == -1) {
				rec_html += '<tr line="0">';
				for (var i in this.columns) {
					if (this.columns[i].hidden) continue;
					rec_html += '<td class="w2ui-grid-data" col="'+ i +'" style="height: 0px;"></td>';					
				}
				rec_html += '<td class="w2ui-grid-data-last" style="height: 0px;"></td>';
				rec_html += '</tr>';
				return rec_html;
			}
			// regular record
			if (ind >= this.records.length) return '';
			record = this.records[ind];
			if (!record) return '';
			var isRowSelected = false;
			if (record.selected) isRowSelected = true;
			// render TR
			rec_html += '<tr id="grid_'+ this.name +'_rec_'+ record.recid +'" recid="'+ record.recid +'" line="'+ lineNum +'" '+
				' class="'+ (lineNum % 2 == 0 ? 'w2ui-even' : 'w2ui-odd') + (isRowSelected ? ' w2ui-selected' : '') + '" ' +
					(
						'	onclick	 = "w2ui[\''+ this.name +'\'].click(\''+ record.recid +'\', event);"'
					 )+ 
				' style="height: '+ this.recordHeight +'px; '+ (!isRowSelected && record['style'] ? record['style'] : '') +'" '+
					(record['style'] ? 'custom_style="'+ record['style'] +'"' : '') +
				'>';

			var col_ind = -1;
			while (true) {
				col_ind++;
				if (typeof this.columns[col_ind] == 'undefined') break;
				
				var col = this.columns[col_ind];
				if (col.hidden) {continue; }
				var rec_cell  = this.getCellHTML(ind, col_ind);
				rec_html += '<td class="w2ui-grid-data' + '" col="'+ col_ind +'" '+
							'	style="' + (typeof col.style != 'undefined' ? col.style : '') +'" '+
										  (typeof col.attr != 'undefined' ? col.attr : '') +'>'+
								rec_cell +
							'</td>';
			}
			rec_html += '<td class="w2ui-grid-data-last"></td>';
			rec_html += '</tr>';
			return rec_html;
		},

		getCellHTML: function (ind, col_ind) {
			var col  	= this.columns[col_ind];
			var record 	= this.records[ind];
			var data 	= this.parseField(record, col.field);
			// various renderers
			if (data == null || typeof data == 'undefined') data = '';
			// title overwrite
			var title = String(data).replace(/"/g, "''");
			if (typeof col.title != 'undefined') {
				if (typeof col.title == 'function') title = col.title.call(this, record, ind, col_ind);
				if (typeof col.title == 'string')   title = col.title;
			}
			data = '<div title="'+ title +'">'+ data +'</div>';	
			return data;
		},

		parseField: function (obj, field) {
			var val = '';
			try { // need this to make sure no error in fields
				val = obj;
				var tmp = String(field).split('.');
				for (var i in tmp) {
					val = val[tmp[i]];
				}
			} catch (event) {
				val = '';
			}
			return val;
		}
	};

	w2obj.grid = w2grid;
})();
