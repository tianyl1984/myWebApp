Namespace.register("com.gleasy.os.model");

/**
 * 应用链接（图标）Model
 */
com.gleasy.os.model.WindowData = function(obj){
	var _this = this;
	
	this.plugin="";
	this.url = "";
	this.image = "";
	this.title = "";
	this.id = "";
	this.shortName = "";

	
	this.initPosition = null;//初始位置[top,left]
	this.initSize = null;//初始大小[width,height]
	
	this.hasFrame = 1;//是否有边框，取值0,1
	this.canMin = 1;//是否可最小化
	this.canMax = 1;//是否可最大化
	this.canRefresh = 1;//是否可刷新
	this.canResize = 1;//是否可改变大小
	this.canReset = 1;//是否可以还原原始尺寸和位置
	
	this.hasDock = 1;//是否生成任务栏图标
	this.hasToolbar = 1;//是否有工具栏
	this.showToolbar = 1;//是否显示工具栏
	
	this.refresh = 1;//重新打开时是否刷新页面(openurl时起效)
	var _construct = function(){
		if(!obj) return;
		if(typeof obj == 'string'){
			var j = $.parseJSON(obj);
			$.extend(_this,j);
		}else{
			$.extend(_this,obj);
		}
	}
	
	
	_construct();
}
