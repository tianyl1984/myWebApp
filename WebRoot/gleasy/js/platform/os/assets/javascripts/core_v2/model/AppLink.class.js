Namespace.register("com.gleasy.os.model");

/**
 * 应用链接（图标）Model
 */
com.gleasy.os.model.AppLink = function(option){
	this.id = "";
	this.url = "";
	this.image = "";
	this.title = "";
	this.shortName = "";
	this.shortIntro = "";
	this.fileTypes = [];
	
	this.order = 0;
	this.pageId = 0;
	this.openType=1;//取值为0=新窗口打开,1=桌面内打开
	
	this.initPosition = null;//初始位置[top,left]
	this.initSize = null;//初始大小[width,height]
	
	this.hasFrame = 1;//是否有边框，取值0,1
	this.hasTitle = 1;//是否有边框，取值0,1
	this.canMin = 1;//是否可最小化
	this.canMax = 1;//是否可最大化
	this.canResize = 1;//是否可改变大小
	this.canReset = 1;//是否可以还原原始尺寸和位置
	
	this.hasToolbar = 1;//是否有工具栏
	this.hasDock = 1;//是否生成任务栏图标
};
