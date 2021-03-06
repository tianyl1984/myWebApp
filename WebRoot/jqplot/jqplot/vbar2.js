
var use_excanvas = !!!document.createElement('canvas').getContext;

if( typeof html5jp == 'undefined' ) {
	html5jp = new Object();
}
if( typeof html5jp.graph == 'undefined' ) {
	html5jp.graph = new Object();
}
html5jp.graph.vbar = function (id) {
	var canvas = document.createElement('canvas');
	canvas.id = "canvas" + id;
	canvas.width = $("#" + id).width();
	canvas.height = $("#" + id).height();
	if(use_excanvas){
		window.G_vmlCanvasManager.initElement(canvas);
	}
	$("#" + id).append(canvas);
	var elm = document.getElementById("canvas" + id);
	this.canvas = elm;
	this.ctx = this.canvas.getContext('2d');
	this.canvas.style.margin = "0";
	this.canvas.parentNode.style.position = "relative";
	this.canvas.parentNode.style.padding = "0";
};
html5jp.graph.vbar.prototype.draw = function(items, inparams) {
	if( ! this.ctx ) {return;}
	this.items = items;
	var params = {
		x: [],
		y: [],
		yLabel:[],
		yColors:["#ef5b9c","#f58220","#bed742","#7fb80e","#1d953f","#005831","#65c294","#78cdd1"],
		yMax: null,
		yMin: 0,
		backgroundColor: "#ffffff",
		gBackgroundColor: "#dddddd",
		gGradation: true,
		barShape: "line",
		barColors: null,
		_barColors: ["rgb(24,41,206)", "rgb(198,0,148)", "rgb(214,0,0)", "rgb(255,156,0)", "rgb(33,156,0)", "rgb(33,41,107)", "rgb(115,0,90)", "rgb(132,0,0)", "rgb(165,99,0)", "rgb(24,123,0)"],
		barGradation: true,
		barAlpha: 0.7,
		borderAlpha: 0.2,
		xAxisWidth: 1,
		xAxisColor: "#aaaaaa",
		yAxisWidth: 1,
		yAxisColor: "#aaaaaa",
		xScale: true,
		xScaleColor: "#000000",
		xScaleFontSize: "10px",
		xScaleFontFamily: "Arial,sans-serif",
		yScale: true,
		yScaleColor: "#000000",
		yScaleFontSize: "10px",
		yScaleFontFamily: "Arial,sans-serif",
		xCaptionColor: "#000000",
		xCaptionFontSize: "12px",
		xCaptionFontFamily: "Arial,sans-serif",
		yCaptionColor: "#000000",
		yCaptionFontSize: "12px",
		yCaptionFontFamily: "Arial,sans-serif",
		aLineWidth: 1,
		aLineAlpha: 0.2,
		dLabel: true,
		dLabelColor: "#000000",
		dLabelFontSize: "10px",
		dLabelFontFamily: "Arial,sans-serif",
		legend: true,
		legendFontSize: "12px",
		legendFontFamily: "Arial,sans-serif",
		legendColor: "#000000"
	};
	if( inparams && typeof(inparams) == 'object' ) {
		for( var key in inparams ) {
			if( key.match(/^_/) ) { continue; }
			params[key] = inparams[key];
		}
	}
	if( params.barColors != null && params.barColors.length > 0 ) {
		for( var i=0; i<params._barColors.length; i++ ) {
			var c = params.barColors[i];
			var co = this._csscolor2rgb(c);
			if( co == null ) {
				params.barColors[i] = params._barColors[i];
			} else {
				params.barColors[i] = c;
			}
		}
	} else {
		params.barColors = params._barColors;
	}
	this.params = params;
	if(this.canvas.width / this.canvas.height < 1.5 || this.canvas.height < 200) {
		params.legend == false;
	}
	var item_num = items.length;
	if(item_num > 10) { item_num = 10; }
	var max_n = 0;//前10组数据中个数最多的-1
	for(var i=0; i<item_num; i++) {
		var n = items[i].length;
		if(n < 2) { continue; }
		if(n - 1 >= max_n) {
			max_n = n - 1;
		}
	}
	params.max_n = max_n;
	if(max_n == 0) {
		throw new Error('no graph item data.');
	}
	var max_v = 0;
	for(var i=1; i<=max_n; i++) {
		var n = items.length;
		var sum = 0;
		for(var j=0; j<n; j++) {
			var v = items[j][i];
			if( isNaN(v) || v < 0 ) {
				throw new Error('invalid graph item data.' + n);
			}
			sum += v;
		}
		if(sum >= max_v) {
			max_v = sum;
		}
	}
	if( typeof(params.yMin) != "number" ) {
		params.yMin = 0;
	}
	if( typeof(params.yMax) != "number" ) {
		params.yMax = max_v * 1.1;
	} else if( params.yMax <= max_v) {
		params.yMax = max_v;
	}
	if( params.y.length < 2 ) {
		params.aLinePositions = this._aline_positions_auto_calc(params.yMin, params.yMax);
	} else {
		params.aLinePositions = params.y.slice(1);
	}
	var cpos = {
		x0: this.canvas.width * 0.05,
		y0: this.canvas.height * 0.95,
		x1: this.canvas.width * 0.95,
		y1: this.canvas.height * 0.05
	};
	if(params.legend == true) {//没有图例
		cpos.x1 = this.canvas.width * 0.7;
	}
	if(true){//留下右侧Y轴提示
		cpos.x1 = this.canvas.width * 0.8;
	}
	if(params.x.length > 0) {
		var x_caption_text_size = this._getTextBoxSize("あa", params.xCaptionFontSize, params.xCaptionFontFamily);
		cpos.y0 -= x_caption_text_size.h * 1.5;
		cpos.x_caption_y = cpos.y0 + x_caption_text_size.h/2;
	}
	if(params.xScale == true || params.x.length > 1) {
		var x_scale_text_size = this._getTextBoxSize("あa", params.xScaleFontSize, params.xScaleFontFamily);
		cpos.y0 -= x_scale_text_size.h * 1.5;
		cpos.x_scale_y = cpos.y0 + x_scale_text_size.h * 0.7;
	}
	if(params.y.length > 0) {
		var y_caption_text_size = this._getTextBoxSize("あa", params.yCaptionFontSize, params.yCaptionFontFamily);
		cpos.y1 += y_caption_text_size.h * 1.5;
		cpos.y_caption_y = cpos.y1 - y_caption_text_size.h * 1.5;
	}
	if(params.yScale == true || params.y.length > 1) {
		var y_scale_text_size = this._getTextBoxSize(params.aLinePositions[params.aLinePositions.length-1].toString(), params.yScaleFontSize, params.yScaleFontFamily);
		cpos.x0 += y_scale_text_size.w * 1.1;
	}
	cpos.w = cpos.x1 - cpos.x0;
	cpos.h = cpos.y0 - cpos.y1;
	if(params.barShape == "square") {
		cpos.r = 0.6 * cpos.w / max_n / 2;
	} else {
		cpos.r = 0.7 * cpos.w / max_n / 2;
	}
	if(cpos.r < 5 && cpos.r >=3) {
		params.barShape = "line";
	}
	params.cpos = cpos;
	this.params = params;
	this._draw_canvas_background();
	this._draw_graph_background();
	//画层次
	this._draw_graph_level();
	var x_scale_positions = new Array();
	var d_labels = new Array();
	for(var i=1; i<=max_n; i++) {
		var sum = 0;
		var x = cpos.x0 + (i - 0.5) * ( cpos.w / max_n );
		x_scale_positions.push(x);
		for(var j=0; j<items.length; j++) {
			var v = items[j][i];
			var y = cpos.y0 - sum * cpos.h / params.yMax;
			var h = v * cpos.h / params.yMax;
			if( params.barShape == "line" ) {
				this._draw_vertical_bar_line(this.ctx, x, y, h, cpos.r, params.barColors[j], params.barAlpha);
			} else if( params.barShape == "flat" ) {
// 				this._draw_vertical_bar_flat(this.ctx, x, y, h, cpos.r, params.barColors[j], params.barAlpha, params.borderAlpha, params.barGradation);
			} else if( params.barShape == "square" ) {
// 				this._draw_vertical_bar_square(this.ctx, x, y, h, cpos.r, cpos.r/3, params.barColors[j], params.barAlpha, params.borderAlpha, params.barGradation);
			} else if( params.barShape == "cylinder" ) {
// 				this._draw_vertical_bar_cylinder(this.ctx, x, y, h, cpos.r, cpos.r/3, params.barColors[j], params.barAlpha, params.borderAlpha, params.barGradation);
			}
			sum += v;
		}
		d_labels.push( { x:x, v:sum } );
	}
	this._draw_data_label(d_labels);
	this._draw_x_scale_label(x_scale_positions);
	this._draw_y_scale_label();
	this._draw_legend();
};
html5jp.graph.vbar.prototype._draw_graph_level = function() {
	this.ctx.save();
	var p = this.params;
	var x = p.cpos.x0;
	var y = p.cpos.y0;
	var w = p.cpos.w;
	var h = p.cpos.h;
	var aa = p.aLinePositions;
// 	var colors = ["#ef5b9c","#f58220","#bed742","#7fb80e","#1d953f","#005831","#65c294","#78cdd1"];
// 	var levelTip = ;
	for(var i=0;i<aa.length;i++){
		var a = aa[i];
		var aY = y -  h * a / ( p.yMax - p.yMin );
		aY = Math.round(aY);
		this.ctx.beginPath();
		this.ctx.globalAlpha = 0.9;
		this.ctx.fillStyle = p.yColors[i];
		var h1 = 0;
		if(i == 0){
			h1 = a * h / ( p.yMax - p.yMin );
		}else{
			h1 = (a - aa[i-1]) * h / ( p.yMax - p.yMin );
		}
		h1 = Math.round(h1);
		this.ctx.fillRect(x, aY, w, h1);
		var tip = p.yLabel[i];
		var s = this._getTextBoxSize(tip, p.dLabelFontSize, p.dLabelFontFamily);
		s.x = x + w + 10;
		s.y = aY + (h1 / 2) - (s.h / 2);
		this._drawText(s.x, s.y, tip, p.dLabelFontSize, p.dLabelFontFamily, p.dLabelColor);
	}
	this.ctx.restore();
};
html5jp.graph.vbar.prototype._draw_data_label = function(labels) {
	var p = this.params;
	if( p.dLabel != true ) { return; }
	var n = labels.length;
	var pos = new Array();
	var max_w = 0;
	for( var i=0; i<n; i++ ) {
		var text = labels[i].v.toString();
		var s = this._getTextBoxSize(text, p.dLabelFontSize, p.dLabelFontFamily);
		max_w = Math.max(s.w, max_w);
		var dx = labels[i].x - s.w / 2;
		var dy = p.cpos.y0 - labels[i].v * p.cpos.h / p.yMax - s.h * 1.3;
		pos.push( { x:dx, y:dy, text:text } );
	}
	if( max_w < p.cpos.w / n ) {
		for( var i=0; i<n; i++ ) {
			this._drawText(pos[i].x, pos[i].y, pos[i].text, p.dLabelFontSize, p.dLabelFontFamily, p.dLabelColor);
		}
	}
};
html5jp.graph.vbar.prototype._draw_legend = function() {
	var p = this.params;
	if(p.legend != true) { return; }
	var s = this._getTextBoxSize('あTEST', p.legendFontSize, p.legendFontFamily);
	var item_num = this.items.length;
	var lpos = {
		x: Math.round( p.cpos.x1 + this.canvas.width * 0.05 ),
		y: Math.round( ( this.canvas.height - ( s.h * item_num + s.h * 0.2 * (item_num - 1) ) ) / 2 ),
		h: s.h
	};
	lpos.cx = lpos.x + Math.round( lpos.h * 1.5 ); 
	lpos.cw = this.canvas.width - lpos.cx;
	for(var i=0; i<item_num; i++) {
		this._drawText(lpos.cx, lpos.y, this.items[i][0], p.legendFontSize, p.legendFontFamily, p.legendColor);
		this.ctx.save();
		this._make_path_legend_mark(lpos.x, lpos.y, s.h, s.h);
		this.ctx.fillStyle = p.gBackgroundColor;
		this.ctx.fill();
		this.ctx.restore();
		this.ctx.save();
		this._make_path_legend_mark(lpos.x, lpos.y, s.h, s.h);
		this.ctx.fillStyle = p.barColors[i];
		this.ctx.globalAlpha = p.barAlpha;
		this.ctx.fill();
		this.ctx.restore();
		this.ctx.save();
		this._make_path_legend_mark(lpos.x, lpos.y, s.h, s.h);
		this.ctx.strokeStyle = p.barColors[i];
		this.ctx.globalAlpha = p.borderAlpha;
		this.ctx.stroke();
		this.ctx.restore();
		if( ! document.uniqueID && p.barGradation == true ) {
			this.ctx.save();
			this._make_path_legend_mark(lpos.x, lpos.y, s.h, s.h);
			var grad = this.ctx.createLinearGradient(lpos.x, lpos.y, lpos.x+s.h, lpos.y+s.h);
			grad.addColorStop(0, "rgba(0, 0, 0, 0.1)");
			grad.addColorStop(0.3, "rgba(255, 255, 255, 0.1)");
			grad.addColorStop(1, "rgba(0, 0, 0, 0.4)");
			this.ctx.fillStyle = grad;
			this.ctx.fill();
			this.ctx.restore();
		}
		lpos.y = lpos.y + lpos.h * 1.2;
	}
};
html5jp.graph.vbar.prototype._make_path_legend_mark = function(x,y,w,h) {
	this.ctx.beginPath();
	this.ctx.moveTo(x, y);
	this.ctx.lineTo(x+w, y);
	this.ctx.lineTo(x+w, y+h);
	this.ctx.lineTo(x, y+h);
	this.ctx.closePath();
};
html5jp.graph.vbar.prototype._aline_positions_auto_calc = function(min, max) {
	var range = max - min;
	var power10 = Math.floor( Math.log(range) / Math.log(10) );
	var unit = Math.pow( 10,  power10);
	if( (Math.log(range) / Math.log(10)) % 1 == 0 ) {
		unit = unit / 10;
	}
	var keta_age = 1;
	if(unit < 1) {
		keta_age += Math.abs(power10);
	}
	var p = Math.pow(10, keta_age);
	range = range * p;
	unit = unit * p;
	min = min * p;
	max = max * p;
	var array = [min];
	var unum = range / unit;
	if( unum > 5 ) {
		unit = unit * 2;
	} else if( unum <= 2 ) {
		unit = unit * 3 / 10;
	}
	var i = 1;
	while(min+unit*i<=max) {
		array.push(min+unit*i);
		i++;
	}
	for(var i=0; i<array.length; i++) {
		array[i] = array[i] / p;
	}
	return array;
};
html5jp.graph.vbar.prototype._draw_y_scale_label = function(pos) {
	var p = this.params;
	if( p.y.length > 0 ) {
		var text = p.y[0].toString();
		if(text != "") {
			var s = this._getTextBoxSize(text, p.yCaptionFontSize, p.yCaptionFontFamily);
			var x = p.cpos.x0 - s.w/2;
			if(x < this.canvas.width*0.05) {
				x = this.canvas.width*0.05;
			}
			this._drawText(x, p.cpos.y_caption_y, text, p.yCaptionFontSize, p.yCaptionFontFamily, p.yCaptionColor);
		}
	}
	if( p.yScale == true && p.aLinePositions.length > 0 ) {
		for( var i=0; i<p.aLinePositions.length; i++ ) {
			var v = p.aLinePositions[i];
			if(v > p.yMax) { continue; }
			var text = v.toString();
			var s = this._getTextBoxSize(text, p.yScaleFontSize, p.yScaleFontFamily);
			var x = p.cpos.x0 - p.cpos.r/2 - s.w*1.1;
			var y = p.cpos.y0 - v * p.cpos.h / (p.yMax - p.yMin) - s.h/2;
// 			if( p.barShape == "cylinder" || p.barShape == "square" ) {
// 				var d = p.cpos.r / 3;
// 				y += d;
// 			}
			this._drawText(x, y, text, p.yScaleFontSize, p.yScaleFontFamily, p.yScaleColor);
		}
	}
};
html5jp.graph.vbar.prototype._draw_x_scale_label = function(pos) {
	var p = this.params;
	if( p.x.length > 0 ) {
		var text = p.x[0].toString();
		if(text != "") {
			var s = this._getTextBoxSize(text, p.xCaptionFontSize, p.xCaptionFontFamily);
			var x = Math.round( p.cpos.x0 + p.cpos.w/2 - s.w/2 );
			this._drawText(x, p.cpos.x_caption_y, text, p.xCaptionFontSize, p.xCaptionFontFamily, p.xCaptionColor);
		}
	}
	if( p.xScale == true && p.x.length > 1 ) {
		for(var i=0; i<pos.length; i++) {
			if(i + 1 > p.x.length - 1) { break; }
			var text = p.x[i+1].toString();
			if(text == "") { continue; }
			var s = this._getTextBoxSize(text, p.xScaleFontSize, p.xScaleFontFamily);
			var x = Math.round( pos[i] - s.w / 2 );
			this._drawText(x, p.cpos.x_scale_y, text, p.xScaleFontSize, p.xScaleFontFamily, p.xScaleColor);
		}
	}
};
html5jp.graph.vbar.prototype._draw_graph_background = function() {
	var p = this.params;
	var d = p.cpos.r / 3;
	if(p.barShape == "line" || p.barShape == "flat") {
		this._draw_graph_background_back(p.cpos.x0, p.cpos.y0, p.cpos.w, p.cpos.h);
		this._draw_graph_axis(p.cpos.x0, p.cpos.y0, p.cpos.w, p.cpos.h);
	} else {
		
	}
};
html5jp.graph.vbar.prototype._draw_graph_axis = function(x, y, w, h) {
	this.ctx.save();
	var p = this.params;
	this.ctx.beginPath();
	this.ctx.lineWidth = p.xAxisWidth;
	this.ctx.strokeStyle = p.xAxisColor;
	this.ctx.moveTo(x, y);
	this.ctx.lineTo(x+w, y);
	this.ctx.stroke();
	this.ctx.beginPath();
	this.ctx.lineWidth = p.yAxisWidth;
	this.ctx.strokeStyle = p.yAxisColor;
	this.ctx.moveTo(x, y);
	this.ctx.lineTo(x, y-h);
	this.ctx.stroke();
	this.ctx.restore();
};
html5jp.graph.vbar.prototype._draw_graph_background_back = function(x, y, w, h) {
	var p = this.params;
	this.ctx.save();
	this.ctx.fillStyle = p.gBackgroundColor;
	this.ctx.fillRect(x, y-h, w, h);
	this.ctx.beginPath();
	this.ctx.moveTo(x, y);
	this.ctx.lineTo(x+w, y);
	this.ctx.lineTo(x+w, y-h);
	this.ctx.lineTo(x, y-h);
	this.ctx.closePath();
	this.ctx.fill();
	this.ctx.restore();
	if( p.gGradation == true) {
		this.ctx.save();
		var grad = this.ctx.createLinearGradient(x, y-h, x+w, y);
		grad.addColorStop(0, "rgba(0, 0, 0, 0.1)");
		grad.addColorStop(0.3, "rgba(255, 255, 255, 0.2)");
		if(document.uniqueID ) { grad.addColorStop(0.5, "rgba(255, 255, 255, 0.2)"); }
		grad.addColorStop(1, "rgba(0, 0, 0, 0.3)");
		this.ctx.fillStyle = grad;
		this.ctx.beginPath();
		this.ctx.moveTo(x, y);
		this.ctx.lineTo(x+w, y);
		this.ctx.lineTo(x+w, y-h);
		this.ctx.lineTo(x, y-h);
		this.ctx.closePath();
		if(document.uniqueID ) { this.ctx.globalAlpha = 0.3; }
		this.ctx.fill();
		if(document.uniqueID ) { this.ctx.globalAlpha = 1; }
		this.ctx.restore();
	}
	if(p.aLineWidth > 0) {
		this.ctx.save();
		for( var i=0; i<p.aLinePositions.length; i++ ) {
			if(p.aLinePositions[i] > p.yMax) { continue; }
			var aY = y -  h * p.aLinePositions[i] / ( p.yMax - p.yMin );
			aY = Math.round(aY);
			this.ctx.beginPath();
			this.ctx.strokeStyle = "rgba(0,0,0," + p.aLineAlpha + ")";
			this.ctx.lineWidth = p.aLineWidth;
			this.ctx.moveTo(x, aY-p.aLineWidth/2);
			this.ctx.lineTo(x+w, aY-p.aLineWidth/2);
			this.ctx.stroke();
			this.ctx.beginPath();
			this.ctx.strokeStyle = "rgba(255,255,255," + p.aLineAlpha + ")";
			this.ctx.lineWidth = p.aLineWidth;
			this.ctx.moveTo(x, aY+p.aLineWidth/2);
			this.ctx.lineTo(x+w, aY+p.aLineWidth/2);
			this.ctx.stroke();
		}
		this.ctx.restore();
	}
};
html5jp.graph.vbar.prototype._draw_canvas_background = function() {
	var c = this._csscolor2rgb(this.params.backgroundColor);
	if( c != null ) {
		this.ctx.save();
		this.ctx.beginPath();
		this.ctx.fillStyle = this.params.backgroundColor;
		this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
		this.ctx.restore();
	}
};

html5jp.graph.vbar.prototype._drawText = function(x, y, text, font_size, font_family, color) {
	var div = document.createElement('DIV');
	div.appendChild( document.createTextNode(text) );
	div.style.fontSize = font_size;
	div.style.fontFamily = font_family;
	div.style.color = color;
	div.style.margin = "0";
	div.style.padding = "0";
	div.style.position = "absolute";
	div.style.left = x.toString() + "px";
	div.style.top = y.toString() + "px";
	this.canvas.parentNode.appendChild(div);
};
html5jp.graph.vbar.prototype._getTextBoxSize = function(text, font_size, font_family) {
	var tmpdiv = document.createElement('DIV');
	tmpdiv.appendChild( document.createTextNode(text) );
	tmpdiv.style.fontSize = font_size;
	tmpdiv.style.fontFamily = font_family;
	tmpdiv.style.margin = "0";
	tmpdiv.style.padding = "0";
	tmpdiv.style.visible = "hidden";
	tmpdiv.style.position = "absolute";
	tmpdiv.style.left = "0px";
	tmpdiv.style.top = "0px";
	this.canvas.parentNode.appendChild(tmpdiv);
	var o = {
		w: tmpdiv.offsetWidth,
		h: tmpdiv.offsetHeight
	};
	tmpdiv.parentNode.removeChild(tmpdiv);
	return o;
};

html5jp.graph.vbar.prototype._getElementAbsPos = function(elm) {
	var obj = new Object();
	obj.x = elm.offsetLeft;
	obj.y = elm.offsetTop;
	while(elm.offsetParent) {
		elm = elm.offsetParent;
		obj.x += elm.offsetLeft;
		obj.y += elm.offsetTop;
	}
	return obj;
};

html5jp.graph.vbar.prototype._csscolor2rgb = function (c) {
	if( ! c ) { return null; }
	var color_map = {
		black: "#000000",
		gray: "#808080",
		silver: "#c0c0c0",
		white: "#ffffff",
		maroon: "#800000",
		red: "#ff0000",
		purple: "#800080",
		fuchsia: "#ff00ff",
		green: "#008000",
		lime: "#00FF00",
		olive: "#808000",
		yellow: "#FFFF00",
		navy: "#000080",
		blue: "#0000FF",
		teal: "#008080",
		aqua: "#00FFFF"
	};
	c = c.toLowerCase();
	var o = new Object();
	if( c.match(/^[a-zA-Z]+$/) && color_map[c] ) {
		c = color_map[c];
	}
	var m = null;
	if( m = c.match(/^\#([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i) ) {
		o.r = parseInt(m[1], 16);
		o.g = parseInt(m[2], 16);
		o.b = parseInt(m[3], 16);
	} else if( m = c.match(/^\#([a-f\d]{1})([a-f\d]{1})([a-f\d]{1})$/i) ) {
		o.r = parseInt(m[1]+"0", 16);
		o.g = parseInt(m[2]+"0", 16);
		o.b = parseInt(m[3]+"0", 16);
	} else if( m = c.match(/^rgba*\(\s*(\d+),\s*(\d+),\s*(\d+)/i) ) {
		o.r = m[1];
		o.g = m[2];
		o.b = m[3];
	} else if( m = c.match(/^rgba*\(\s*(\d+)\%,\s*(\d+)\%,\s*(\d+)\%/i) ) {
		o.r = Math.round(m[1] * 255 / 100);
		o.g = Math.round(m[2] * 255 / 100);
		o.b = Math.round(m[3] * 255 / 100);
	} else {
		return null;
	}
	return o;
};
html5jp.graph.vbar.prototype._draw_vertical_bar_line = function(ctx, x, y, h, xr, color, alpha) {
	if( typeof(alpha) != "number" || alpha < 0 || alpha > 1 ) {
		alpha = 1;
	}
	if( typeof(xr) != "number" || xr <= 0 ) {
		xr = 0.5;
	}
	ctx.save();
	ctx.strokeStyle = color;
	ctx.globalAlpha = 1;//不透明
	ctx.lineCap = "butt";
	ctx.lineWidth = Math.round( xr * 2 );
	ctx.beginPath();
	ctx.moveTo(x, y);
	ctx.lineTo(x, y-h);
	ctx.stroke();
	ctx.restore();
};