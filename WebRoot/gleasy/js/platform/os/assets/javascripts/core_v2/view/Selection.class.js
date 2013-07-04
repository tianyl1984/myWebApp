/**
 * 选择器
*/
Namespace.register("com.gleasy.os.view");

com.gleasy.os.view.Selection = function(param){
	com.gleasy.libs.Observable.call(this,param);//继承Frame
	
	var _this = this;
	var view = null;
	var startPoint = {x:0,y:0};
	var drawing = false;
	var l1 = 0;
	
	var options = {
		container:null,
		useRelative:false,
		helper:null,
		click:null,
		exclude:null,
		excludes:null
	};
	
	var initComponent = function(){
		view = $("<div style='position:absolute;z-index:1024;border:1px solid #3399ff;opacity:0.5;filter:alpha(opacity=50);background:#a8caec;'/>");
//		view.css({
//				position:'absolute',
//				zIndex:1024,
//				borderWidth:1,
//				borderStyle:'solid',
//				borderColor:"#3399ff",
//				opacity:'.5',
//				padding:0,
//				margin:0,
//				filter:"alpha(opacity=50)",
//				backgroundColor:"#a8caec",
//				cursor:'default'
//		});
		view.appendTo(options.container);
		view.hide();
	}
	
	var configEvent = function(){
		options.container.unbind("mousedown").bind("mousedown",mouseDown);
	}
	
	var mouseDown = function(evt){
		if(evt.button==2){
			return;
		}
			
			var target = $(evt.target);
			var bingo = true;;
			if(options.helper){
				if(target.closest(options.helper).length){
					bingo = true;
				}else{
					bingo = false;	
				}
			}
			if(options.exclude){
				if(target.closest(options.exclude).length){
					bingo = false;	
				}
			}
			if(options.excludes){
				$.each(options.excludes,function(index,exclude){
					if(target.closest(exclude).length){
						bingo = false;
						return false;
					}
				})
			}
			if(bingo){
				startPoint = {x:evt.pageX - options.container.offset().left,y:evt.pageY-options.container.offset().top};	
				
				var scrollTop = options.container.parent().scrollTop();
				startPoint.y += scrollTop;
				
				//$.log("scrollTop="+scrollTop+";startPoint.y="+startPoint.y);
				if(options.useRelative){
					var rela = 	options.container.position();
					startPoint.x += rela.left;
					startPoint.y += rela.top;
				}
				//$.log("startPoint {x,y} = "+"{"+startPoint.x+","+startPoint.y+"}"+";old. {x,y} {"+rela.left+","+rela.top+"}");		
				drawing = true;
				view.css({
					width:0,
					height:0,
					left:startPoint.x,
					top:startPoint.y,
					position:'absolute'
				});
				view.show();
				
				document.body.setCapture && options.container[0].setCapture(); // IE下鼠标超出视口仍可被监听
				
				l1 = getCurrentTimeMills();
				$(document).bind("mousemove",mouseMove).bind("mouseup",mouseUp);
			}
	}
	
	var mouseMove = function(evt){
		if(drawing){
				var l2 = getCurrentTimeMills();
				//$.log("用时:"+(l2-l1));
				var interval = l2 - l1;
				if(interval < 30) return;
				l1 = l2;
				var p = {x:evt.pageX-options.container.offset().left,y:evt.pageY-options.container.offset().top};
				var scrollTop = options.container.parent().scrollTop();
				if(options.useRelative){
					var rela = 	options.container.position();
					p.x += rela.left;
					p.y += rela.top;
				}
				p.y += scrollTop;
				var w = p.x - startPoint.x;
				var h = p.y - startPoint.y;
				
				var res  = {
					left:startPoint.x,
					top:startPoint.y
				};
				if(w < 0){
					view.css({
						left:p.x,
						width:Math.abs(w)
					});
					res.left = p.x;
					res.width = Math.abs(w);
				}else{
					view.css({
						width:w
					});	
					res.width = w;
				}
				if(h < 0){
					view.css({
						top:p.y,
						height:Math.abs(h)
					});	
					res.top = p.y;
					res.height = Math.abs(h);
				}else{
					view.css({
						height:h
					});
					res.height = h;
				}
				res.left = view.offset().left;
				res.top = view.offset().top;
				//$.log("start callback."+getCurrentTimeMills());
				asynNotice("select",res);
				//$.log("用时:"+(getCurrentTimeMills()-l1));
		}
	}
	
	var messageStack = [];
		
	var asynNotice = function(event,message){
		messageStack.push(message);
		setTimeout(function(){
			if(!messageStack.length){
				return;
			}
			var msg = messageStack.pop();
			messageStack = [];
			_this.noticeListener(event,msg);
		},15);
	}
	
	var mouseUp = function(evt){
		if(evt.button==2){
			return;
		}	
			
		if(drawing){
			drawing = false;
			view.hide();
			document.body.releaseCapture && options.container[0].releaseCapture();
			$(document).unbind("mousemove",mouseMove).unbind("mouseup",mouseUp);
		}
		_this.noticeListener("mouseup",{});
	}
	
	this.hide = function(){
		if(drawing){
			drawing = false;
			view.hide();
		}		
	}
	
	var resize = function(){
	}
	
	var _construct = function(){
		$.extend(options,param);
		
		initComponent();
		configEvent();
	}
	
	_construct();
}
