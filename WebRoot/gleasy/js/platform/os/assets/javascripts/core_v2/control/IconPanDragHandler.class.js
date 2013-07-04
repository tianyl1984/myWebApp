Namespace.register("com.gleasy.os");

com.gleasy.os.IconPanDragHandler = {};

com.gleasy.os.IconPanDragHandler.dragStart = function(icon){
	com.gleasy.os.IconPan.dragIcon = icon;
	icon.targetPage = icon.pan;
	icon.dragStartTime = getCurrentTimeMills();
	
	icon.dragStartOrder = icon.order;
	icon && icon.pan && icon.pan.iconDragStart(icon);
}


var sortByOrder = function(arr){
	return arr.sort(function(a,b){
		return b.level - a.level;
	});
}

com.gleasy.os.IconPanDragHandler.findPage = function(centerPoint){
	//sortByOrder(com.gleasy.os.IconPan.pans);
	for(var i=0;i<com.gleasy.os.IconPan.pans.length;i++){
		var pan = com.gleasy.os.IconPan.pans[i];
		if(pan.contains(centerPoint.x,centerPoint.y)){
			return pan;
		}
	}
	return null;
}


com.gleasy.os.IconPanDragHandler.draging = function(evt,force){
	var icon = com.gleasy.os.IconPan.dragIcon;
	if(!icon) return;
	
	var helper = icon.helper;
	icon.centerPoint = {x:helper.offset().left + helper.width()/2, y:helper.offset().top + helper.height()/2};
	
	if(!force && com.gleasy.os.view.TopBarView.instance.dragOver(icon)){
		return;
	}
	
	var targetPage = com.gleasy.os.IconPanDragHandler.findPage(icon.centerPoint);
	if(!targetPage){
		//$.log("无人接受");
		return 0;
	}
	
	
	//$.log(targetPage.getId()+";"+targetPage.getName());
	
	var oldPage = icon.targetPage;
	if(targetPage !== oldPage){
		//$.log("离开..."+oldPage.getId());
		oldPage.iconDragLeave(icon);
		
		//改变容器
		if(!targetPage.isAccept(icon)){
			//$.log("对方不接受，恢复..."+icon.pan.getId());
			if(!$(".oUndropable",helper).length){
				helper.append('<i class="oUndropable" />');
			}
			$(".oUndropable",helper).show();
			icon.targetPage = icon.pan;
			return 0;
		}
		
		if($(".oUndropable",helper)){
			$(".oUndropable",helper).hide();
		}
		//$.log("进入..."+targetPage.getId());
		targetPage.iconDragEnter(icon);
		icon.targetPage = targetPage;
	}else{
		if($(".oUndropable",helper)){
			$(".oUndropable",helper).hide();
		}
		//$.log("不变.."+icon.pan.getId());
		targetPage.iconDragOver(icon);
	}
	
}

com.gleasy.os.IconPanDragHandler.dragStop = function(){
	var icon = com.gleasy.os.IconPan.dragIcon;	
	
	if(com.gleasy.os.view.TopBarView.instance.dragDrop(icon)){
		//pan.iconDragStop(icon);
		com.gleasy.os.IconPan.dragIcon = null;
		return;
	}
	
	var endTime = getCurrentTimeMills();
	var startTime = icon.dragStartTime;
	var targetPage = icon.targetPage;
	var pan = icon.pan;

	if(endTime - startTime < 200 && targetPage === pan && icon.order == icon.dragStartOrder){
	///	$.log("xx yy mm");
		pan.iconDragStop(icon);
		pan.iconClick(icon);
		com.gleasy.os.IconPan.dragIcon = null;
		
		return;
	}
	
	var ok1 = targetPage.startTransaction();
	var ok2 = pan.startTransaction();
	
	if(targetPage !== pan){
		pan.iconLeaveFrom(icon);	
		targetPage.iconDropTo(icon);
		targetPage.iconDragStop(icon);
	}else{
		if(icon.order != icon.dragStartOrder){
			targetPage.fireDataChange();
		}
		targetPage.iconDragStop(icon);	
	}
	for(var i=0;i<com.gleasy.os.IconPan.pans.length;i++){
		var _pan = com.gleasy.os.IconPan.pans[i];
		var ooo = _pan.startTransaction();
		_pan.iconPanChange(icon,pan,targetPage);
		ooo && _pan.endTransaction();
	}
	ok1 && targetPage.endTransaction();
	ok2 && pan.endTransaction();
	
	com.gleasy.os.IconPan.dragIcon = null;
}
