Namespace.register("com.gleasy.os.model");

/**
 * 第三方应用定体类
 */
com.gleasy.os.model.App = function(option){
	this.id = "";
	this.url = "";
	this.image = "";
	this.title = "";
	this.shortName = "";
	this.name = "";
	this.supportDesktop = 1;
	this.supportMinidesk = 1;
	
	this.widgetUrl = "";
	
	
	this.height = 100;
	
	this.openType=1;//取值为0=新窗口打开,1=桌面内打开
	this.initPosition = null;//初始位置[top,left]
	this.initSize = null;//初始大小[width,height]
	
	this.hasFrame = 1;//是否有边框，取值0,1
	this.canMin = 1;//是否可最小化
	this.canMax = 1;//是否可最大化
	this.canResize = 1;//是否可改变大小
	this.canReset = 1;//是否可以还原原始尺寸和位置
	
	this.hasToolbar = 1;//是否有工具栏
	this.hasDock = 1;//是否生成任务栏图标
}
