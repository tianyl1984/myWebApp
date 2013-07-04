/**
 * 注册表类
*/
Namespace.register("com.gleasy.os");

com.gleasy.os.reg = {};

com.gleasy.os.reg.typeBinding = [];
com.gleasy.os.reg.typeBinding.push({type:'.png',pname:'图片查看器'});
com.gleasy.os.reg.typeBinding.push({type:'.jpg',pname:'图片查看器'});
com.gleasy.os.reg.typeBinding.push({type:'.bmp',pname:'图片查看器'});
com.gleasy.os.reg.typeBinding.push({type:'.gif',pname:'图片查看器'});
com.gleasy.os.reg.typeBinding.push({type:'.tnf',pname:'图片查看器'});
com.gleasy.os.reg.typeBinding.push({type:'.jpeg',pname:'图片查看器'});
com.gleasy.os.reg.typeBinding.push({type:'.icon',pname:'图片查看器'});
com.gleasy.os.reg.typeBinding.push({type:'.pdf',pname:'读读'});
com.gleasy.os.reg.typeBinding.push({type:'.gdoc',pname:'写写'});
com.gleasy.os.reg.typeBinding.push({type:'.doc',pname:'写写'});
com.gleasy.os.reg.typeBinding.push({type:'.docx',pname:'写写'});
com.gleasy.os.reg.typeBinding.push({type:'.xls',pname:'表格'});
com.gleasy.os.reg.typeBinding.push({type:'.xlsx',pname:'表格'});
com.gleasy.os.reg.typeBinding.push({type:'.ppt',pname:'读读'});
com.gleasy.os.reg.typeBinding.push({type:'.pptx',pname:'读读'});
com.gleasy.os.reg.typeBinding.push({type:'.csv',pname:'记事本'});
com.gleasy.os.reg.typeBinding.push({type:'.vcf',pname:'名片查看器'});
com.gleasy.os.reg.typeBinding.push({type:'.txt',pname:'记事本'});
com.gleasy.os.reg.typeBinding.push({type:'.css',pname:'记事本'});
com.gleasy.os.reg.typeBinding.push({type:'.html',pname:'记事本'});
com.gleasy.os.reg.typeBinding.push({type:'.grid',pname:'表格'});

com.gleasy.os.reg.userTypeBinding = null;

com.gleasy.os.reg.loadUserTypeBinding = function(callback){
		AjaxConfig.ajax({
			url:AjaxConfig.url['appCenter']+"/fileTypeBinding/get.json",
			success:function(result){
				if(result.status == 0){
					com.gleasy.os.reg.userTypeBinding = result.data;
					callback && typeof callback == 'function' && callback(com.gleasy.os.reg.userTypeBinding);
				}else{
					callback && typeof callback == 'function' && callback(null);
				}
			},
			error:function(){
				callback && typeof callback == 'function' && callback(null);
			}
		});	
}


com.gleasy.os.reg._setUseTypeBinding = function(type,pname,callback){
		var found = false;
		for(var i=0;i<com.gleasy.os.reg.userTypeBinding.length;i++){
			var map = com.gleasy.os.reg.userTypeBinding[i];
			if(trim(map.type).toLowerCase() == trim(type).toLowerCase()){
				map.pname = pname;
				found = true;
				break;
			}
		}
		if(!found){
				com.gleasy.os.reg.userTypeBinding.push({
					type:type,
					pname:pname
				});
		}	
		AjaxConfig.ajax({
				url:AjaxConfig.url['appCenter']+"/fileTypeBinding/save.json",
				data:{
					type:type,
					pname:pname
				},
				success:function(result){
					callback && typeof callback == 'function' && callback({result:1});
				},
				error:function(){
					callback && typeof callback == 'function' && callback({result:0});
				}
		});			
}

/**
 * 设置默认打开方式
 */
com.gleasy.os.reg.setUseTypeBinding = function(type,pname,callback){
	if(com.gleasy.os.reg.userTypeBinding == null){
		com.gleasy.os.reg.loadUserTypeBinding(function(){
			com.gleasy.os.reg._setUseTypeBinding(type,pname,callback);		
		});
	}else{
		com.gleasy.os.reg._setUseTypeBinding(type,pname,callback);	
	}
}

com.gleasy.os.reg.getPnameByUserType = function(type,callback){
	if(com.gleasy.os.reg.userTypeBinding == null){
		com.gleasy.os.reg.loadUserTypeBinding(callback);
	}else{
		callback && typeof callback == 'function' && callback(com.gleasy.os.reg.userTypeBinding);
	}
}

/**
 * 获取默认打开方式
 */
com.gleasy.os.reg.getPnameByType = function(type,callback){
	com.gleasy.os.reg.getPnameByUserType(type,function(bindings){
		var dealed = false;
		if(bindings != null){
			for(var i=0;i<bindings.length;i++){
				var map = bindings[i];
				if(trim(map.type).toLowerCase() == trim(type).toLowerCase()){
					callback && typeof callback == 'function' && callback({pname:map.pname});
					return;
				}
			}
		}
		if(!dealed){
			for(var i=0;i<com.gleasy.os.reg.typeBinding.length;i++){
				var map = com.gleasy.os.reg.typeBinding[i];
				if(trim(map.type).toLowerCase() == trim(type).toLowerCase()){
					callback && typeof callback == 'function' && callback({pname:map.pname});
					return;
				}
			}
		}
		callback && typeof callback == 'function' && callback(null);
		return;
	});
}

com.gleasy.os.reg.installedApps = null;
/**
* 获取己安装APP列表
* */
com.gleasy.os.reg.getInstalledApps = function(_param){
	if(_param.allowCache && com.gleasy.os.reg.installedApps ){
		_param.callback && typeof _param.callback == 'function' && _param.callback(com.gleasy.os.reg.installedApps,_param.args);	
		return;
	}
	
	var apps = {};
	$.extend(apps,com.gleasy.os.reg.programDef);
	window.application.sendNotify(Constants.command.retrieveDesktop,{
		callback:function(appDatas,_args){
				$.each(appDatas,function(index,pageData){
					var dt = pageData.datasource;
					$.each(dt,function(i,app){
						if(app.type == 'sys'){
							$.extend(apps[app.app],{
								messageOpen : app.messageOpen,
								supportMessage: app.supportMessage,
								autoRun : app.autoRun
							});
						}
						if(app.forceAutoRun){
							$.extend(apps[app.app],{
								autoRun : true
							});
						}
						if(!app.appId || (app.type && app.type != 'app')){
							return;
						}
						var key = app.title;
						if(!key || key == 'undefined'){
							return;
						}
						//var aaaas = {};
						apps[key] = {
							//pname:app.title,
							//app:app.title,
							pname:app.name,
							app:app.name,
							appId:app.appId,
							shortName:app.shortName,
							type:"app",
							icon:app.image,
							fileTypes:app.fileTypes,
							supportBrowser:app.supportBrowser,
							intro:app.shortIntro,
							singleton:app.singleton, 
							exe:com.gleasy.os.view.AppFrame,
							isWidget:app.isWidget,
							autoRun: app.autoRun,
							version: app.version,
							args:app.args
						};
						//$.extend(aaaas,app);
					});
				});
				com.gleasy.os.reg.installedApps = apps;
				_param.callback && typeof _param.callback == 'function' && _param.callback(apps,_args);				
		},args:_param.args
	});
}
	
com.gleasy.os.reg.programDef = {};
com.gleasy.os.reg.programDef['读读'] = {
	type:'sys',
	intro:"读读",
	app:'读读',
	shortName:"读读",
	silence:true,
	singleton:false,
	fileTypes:[".pdf",".ppt",".pptx"],
	supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
	resource:Constants.config.assetPrefix+"/platform/softwares/pdfViewer/js/PdfViewerFrame.class.js",
	icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_read.png",
	exe:"com.gleasy.soft.view.PdfViewerFrame"
};

com.gleasy.os.reg.programDef['图片查看器'] = {
	type:'sys',
	intro:"图片查看",
	app:'图片查看器',
	shortName:"图片查看",
	singleton:true,
	silence:true,
	fileTypes:[".png",".jpg",".bmp",".gif",".tnf",".icon",".jpeg"],
	supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
	resource:Constants.config.assetPrefix+"/platform/softwares/photoViewer/js/PohotoViewerFrame.class.js",
	icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_photoViewer.png?v=2",
	exe:"com.gleasy.soft.view.PhotoViewerFrame"
};

/*
com.gleasy.os.reg.programDef['任务管理器'] = {
	type:'sys',
	app:'任务管理器',
	intro:"任务管理器",
	shortName:"任务管理器",
	singleton:true,
	fileTypes:[],
	resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/softwares/taskManager/view/TaskManager.class.js",
	icon:Constants.config.assetPrefix+"/platform/os/assets/images/img/default.png",
	exe:"com.gleasy.soft.view.TaskManager"
};*/
com.gleasy.os.reg.programDef['联系人'] = {
		type:'sys',
		intro:"联系人",
		app:"联系人",
		shortName:"联系人",
		version:"0.1",
		singleton:true,
		forceAutoRun:false,
		supportMessage:true,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/contact/view/Contact.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_person.png",
		exe:"com.gleasy.contact.view.Contact"
	};
com.gleasy.os.reg.programDef['联系人守护进程'] = {
		type:'sys',
		intro:"联系人守护进程",
		app:"联系人",
		shortName:"联系人守护进程",
		singleton:true,
		silence:true,
		autoRun:true,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/contact/daemon/ContactDaemon.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_person.png",
		exe:"com.gleasy.contact.daemon.ContactDaemon"
	};

com.gleasy.os.reg.programDef['小名片'] = {
		type:'sys',
		intro:"小名片",
		app:"小名片",
		shortName:"小名片",
		singleton:true,
		silence:true,
		autoRun:true,
		fileTypes:[],
		version:"0.14",
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/contact/components/mini/Daemon.class.js?v=0.14",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_person.png",
		exe:"com.gleasy.contact.mini.Daemon"
};
com.gleasy.os.reg.programDef['小名片单元测试'] = {
		type:'sys',
		intro:"小名片单元测试",
		app:"小名片单元测试",
		shortName:"小名片单元测试",
		singleton:true,
		silence:true,
		autoRun:false,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/contact/components/mini/UnitTest.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_person.png",
		exe:"com.gleasy.contact.mini.UnitTest"
};

com.gleasy.os.reg.programDef['名片查看器'] = {
		type:'sys',
		intro:"名片查看器",
		app:"联系人",
		shortName:"名片查看器",
		singleton:false,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/contact/components/cardEditor/view/CardViewer.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_cd.png",
		exe:"com.gleasy.contact.cardViewer.view.CardViewer"
	};
com.gleasy.os.reg.programDef['个人主页'] = {
		type:'sys',
		intro:"个人主页",
		app:"联系人",
		shortName:"个人主页",
		singleton:false,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/contact/components/personalHomePage/view/PersonalHomePageDialog.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_cd.png",
		exe:"com.gleasy.contact.components.personalHomePage.view.PersonalHomePageDialog"
	};
com.gleasy.os.reg.programDef['一盘'] = {
	type:'sys',
	intro:"一盘",
	app:"一盘",
	shortName:"一盘",
	singleton:false,
	supportMessage:true,
	fileTypes:[],
	supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
	resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/gcd/view/Gcd.class.js",
	icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_cd.png",
	exe:"com.gleasy.gcd.view.Gcd"
};
com.gleasy.os.reg.programDef['一盘守护进程'] = {
		type:'sys',
		intro:"一盘守护进程",
		app:"一盘",
		shortName:"一盘守护进程",
		singleton:true,
		silence:true,
		autoRun:true,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/gcd/daemon/GcdDaemon.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_cd.png",
		exe:"com.gleasy.gcd.daemon.GcdDaemon"
	};
com.gleasy.os.reg.programDef['应用空间守护进程'] = {
		type:'sys',
		intro:"应用空间守护进程",
		app:"应用空间守护进程",
		shortName:"应用空间守护进程",
		singleton:true,
		silence:true,
		autoRun:true,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/gcd/daemon/AppDaemon.class.js",
		exe:"com.gleasy.gcd.daemon.AppDaemon"
	};
com.gleasy.os.reg.programDef['本地硬盘'] = {
	type:'sys',
	intro:"本地硬盘",
	app:"本地硬盘",
	shortName:"本地硬盘",
	singleton:true,
	silence:true,
	fileTypes:[],
	supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
	resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/localDisk/view/LocalDisk.class.js",
	icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_local.png",
	exe:"com.gleasy.localDisk.view.LocalDisk"
};

com.gleasy.os.reg.programDef[Constants.app.workbench] = {
	type:'sys',
	intro:"一信",
	app:Constants.app.workbench,
	singleton:true,
	shortName:"一信",
	supportMessage:true,
	fileTypes:[],
	supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
	icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_mail.png",
	resource:Constants.config.assetPrefix+"/platform/softwares/workbench/js/view/Workbench.class.js",
	exe:"com.gleasy.wb.view.Workbench"
};
com.gleasy.os.reg.programDef[Constants.pname.workbench.wbDaemon] = {
	type:'sys',
	intro:"一信守护进程",
	app:Constants.app.workbench,
	shortName:"一信守护进程",
	singleton:true,
	silence:true,
	autoRun:true,
	fileTypes:[],
	supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
	resource:Constants.config.assetPrefix+"/platform/softwares/workbench/js/daemon/WbDaemon.class.js",
	icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_mail.png",
	exe:"com.gleasy.wb.daemon.WbDaemon"
};
com.gleasy.os.reg.programDef[Constants.pname.workbench.selector] = {
	type:'sys',
	intro:"一信选人组件",
	app:Constants.app.workbench,
	shortName:"一信选人组件",
    version:"0.1",
	singleton:true,
	silence:true,
	autoRun:true,
	fileTypes:[],
	supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
	resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/contact/components/emailSelector/view/CardSelectorDaemon.class.js",
	icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_mail.png",
	exe:"com.gleasy.contact.components.emailSelector.view.CardSelectorDaemon"
};

com.gleasy.os.reg.programDef[Constants.pname.workbench.task] = {
	type:'sys',
	intro:"发任务",
	app:Constants.app.workbench,
	singleton:false,
	shortName:"发任务",
	fileTypes:[],
	supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
	icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_mail.png",
	resource:Constants.config.assetPrefix+"/platform/softwares/workbench/js/view/OaTaskEditFrame.class.js",
	exe:"com.gleasy.wb.view.OaTaskEditFrame"
};

com.gleasy.os.reg.programDef[Constants.pname.workbench.activity] = {
	type:'sys',
	intro:"发活动",
	app:Constants.app.workbench,
	singleton:false,
	shortName:"发活动",
	fileTypes:[],
	supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
	icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_mail.png",
	resource:Constants.config.assetPrefix+"/platform/softwares/workbench/js/view/OaActivityEditFrame.class.js",
	exe:"com.gleasy.wb.view.OaActivityEditFrame"
};

com.gleasy.os.reg.programDef[Constants.pname.workbench.vote] = {
	type:'sys',
	intro:"发活动",
	app:Constants.app.workbench,
	singleton:false,
	shortName:"发活动",
	fileTypes:[],
	supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
	icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_mail.png",
	resource:Constants.config.assetPrefix+"/platform/softwares/workbench/js/view/OaVoteEditFrame.class.js",
	exe:"com.gleasy.wb.view.OaVoteEditFrame"
};

com.gleasy.os.reg.programDef[Constants.pname.workbench.approval] = {
	type:'sys',
	intro:"发审批",
	app:Constants.app.workbench,
	singleton:false,
	shortName:"发审批",
	fileTypes:[],
	supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
	icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_mail.png",
	resource:Constants.config.assetPrefix+"/platform/softwares/workbench/js/view/OaApprovalEditFrame.class.js",
	exe:"com.gleasy.wb.view.OaApprovalEditFrame"
};

com.gleasy.os.reg.programDef[Constants.pname.workbench.mail] = {
	type:'sys',
	intro:"发邮件",
	app:Constants.app.workbench,
	singleton:false,
	shortName:"发邮件",
	fileTypes:[],
	supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
	icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_mail.png",
	resource:Constants.config.assetPrefix+"/platform/softwares/workbench/js/view/MailNewFrame.class.js",
	exe:"com.gleasy.wb.view.MailNewFrame"
};


com.gleasy.os.reg.programDef['写写'] = {
		type:'sys',
		intro:"写写",
		app:'写写',
		shortName:"写写",
		singleton:false,
		fileTypes:[".gdoc",".doc",".docx"],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/softwares/gdocEditor/view/EditorEntry.class.js?ge_version=0.2",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_write.png",
		exe:"com.gleasy.soft.view.GdocEditor"
};
com.gleasy.os.reg.programDef['写写守护进程'] = {
		type:'sys',
		intro:"写写守护进程",
		app:'写写守护进程',
		shortName:"写写守护进程",
		singleton:true,
		silence:true,
		forceAutoRun:true,
		autoRun:true,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/softwares/gdocEditor/view/EditorEntryDaemon.class.js?ge_version=0.2",
		exe:"com.gleasy.soft.view.GdocEditorDaemon"
};

com.gleasy.os.reg.programDef['记事本'] = {
		type:'sys',
		intro:"记事本",
		app:'记事本',
		shortName:"记事本",
		singleton:false,
		fileTypes:[".txt",".css",".js", ".html",".php",".python",".vb",".xml",".c",".cpp",".sql",".java"],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/softwares/gnotepad/js/GNotepad.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_notepad.png",
		exe:"com.gleasy.soft.gnotepad.GNotepad"
};

com.gleasy.os.reg.programDef['表格'] = {
		type:'sys',
		intro:"表格",
		app:'表格',
		shortName:"表格",
		singleton:false,
		fileTypes:[".grid"],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/sites/sheet/Sheet.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_sheet.png",
		exe:"com.gleasy.soft.sheet.Sheet"
};


	
com.gleasy.os.reg.programDef['云浏览器'] = {
		type:'sys',
		intro:"一览",
		shortName:"一览",
		app:'云浏览器',
		singleton:true,
		silence:true,
		fileTypes:[],
		supportBrowser:{gleasy:"j"},
		resource:Constants.config.assetPrefix+"/platform/softwares/browser/js/BrowserFrame.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_browser.png",
		exe:"com.gleasy.soft.view.BrowserFrame"
};	

com.gleasy.os.reg.programDef['一览'] = {
		type:'sys',
		intro:"网址导航",
		shortName:"网址导航",
		app:'一览',
		singleton:true,
		fileTypes:[],
//		supportBrowser:{gleasy:"all"},
		resource:Constants.config.assetPrefix+"/platform/softwares/browser/js/WebBrowserFrame.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_browser.png",
		exe:"com.gleasy.soft.view.WebBrowserFrame"
};

com.gleasy.os.reg.programDef['一览守护进程'] = {
		type:'sys',
		intro:"一览守护进程",
		app:"一览守护进程",
		shortName:"一览守护进程",
		singleton:true,
		silence:true,
		autoRun:true,
		fileTypes:[],
//		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/softwares/browser/js/daemon/BrowserDaemon.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_browser.png",
		exe:"com.gleasy.soft.cloudBrowser.BrowserDaemon"
	};

com.gleasy.os.reg.programDef['工作窗口'] = {
	type:'sys',
	intro:"一说",
	shortName:"一说",
	app:'工作窗口',
	singleton:true,
	forceAutoRun:true,
	fileTypes:[],
	supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
	resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/im/view/ImMain.class.js",
	icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_im.png",
	exe:"com.gleasy.im.view.ImMain"
};

/*
com.gleasy.os.reg.programDef['应用商店'] = {
		type:'sys',
		intro:"应用商店",
		shortName:"应用商店",
		app:'应用商店',
		singleton:true,
		fileTypes:[],
		resource:Constants.config.assetPrefix+"/platform/softwares/appStore/AppCenter.view.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/images/img/default.png",
		exe:"com.gleasy.soft.view.AppCenterView"
};	*/
com.gleasy.os.reg.programDef['应用商店'] = {
		type:'sys',
		intro:"应用商店",
		shortName:"应用商店",
		app:'应用商店',
		singleton:true,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/softwares/appStore/js/view/AppStore.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_appStore.png",
		exe:"com.gleasy.appStore.view.AppStore"
};	

com.gleasy.os.reg.programDef['API测试器'] = {
		type:'sys',
		intro:"API测试器",
		shortName:"API测试器",
		singleton:true,
		app:'API测试器',
		silence:true,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/softwares/apiTester/view/ApiTester.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_msg.png",
		exe:"com.gleasy.soft.view.ApiTester"
};

com.gleasy.os.reg.programDef[Constants.app.ctrlPanel] = {
		type:'sys',
		intro:"控制面板",
		shortName:"控制面板",
		singleton:true,
		app:Constants.app.ctrlPanel,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/view/ControlPanel.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_setting.png",
		exe:"com.gleasy.ctrlPanel.view.ControlPanel"
};

com.gleasy.os.reg.programDef[Constants.app.background] = {
		type:'sys',
		intro:"桌面背景",
		shortName:"桌面背景",
		singleton:true,
		silence:true,
		app:Constants.app.background,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/background/view/BackgroundSetting.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/ctrl/images/ctrlIco_06.png",
		exe:"com.gleasy.ctrlPanel.background.view.BackgroundSetting"
};

com.gleasy.os.reg.programDef[Constants.app.capacityManage] = {
		type:'sys',
		intro:"容量管理",
		shortName:"容量管理",
		singleton:true,
		silence:true,
		app:Constants.app.ctrlPanel,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/capacityManager/view/CapacityManage.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/ctrl/images/ctrlIco_04.png",
		exe:"com.gleasy.ctrlPanel.capacityManager.view.CapacityManage"
};

com.gleasy.os.reg.programDef[Constants.app.CapacityHandleView] = {
		type:'sys',
		intro:Constants.app.CapacityHandleView,
		shortName:Constants.app.CapacityHandleView,
		singleton:true,
		silence:true,
		app:Constants.app.ctrlPanel,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/capacityManager/view/CapacityHandleView.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/ctrl/images/ctrlIco_04.png",
		exe:"com.gleasy.ctrlPanel.capacityManager.view.CapacityHandleView"
};

com.gleasy.os.reg.programDef[Constants.pname.background.daemon] = {
    type:'sys',
    intro:"桌面背景守护进程",
    app:Constants.app.background,
    singleton:true,
    shortName:"桌面背景守护进程",
    autoRun:true,
    silence:true,
    fileTypes:[],
    resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/background/view/BackgroundSettingDaemon.js",
    exe:"com.gleasy.ctrlPanel.background.view.BackgroundSettingDaemon"
};

com.gleasy.os.reg.programDef[Constants.pname.gbank.daemon] = {
    type:'sys',
    intro:"G币中心守护进程",
    app:Constants.app.background,
    singleton:true,
    shortName:"G币中心守护进程",
    autoRun:true,
    fileTypes:[],
    resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/gbankCenter/view/GbankDaemon.class.js",
    exe:"com.gleasy.ctrlPanel.gbankCenter.view.GbankDaemon"
};

com.gleasy.os.reg.programDef[Constants.pname.ctrlPanel.daemon] = {
    type:'sys',
    intro:Constants.pname.ctrlPanel.daemon,
    app:Constants.app.ctrlPanel,
    singleton:true,
    shortName:Constants.pname.ctrlPanel.daemon,
    autoRun:true,
    fileTypes:[],
    resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/view/ControlPanelDaemon.class.js",
    exe:"com.gleasy.ctrlPanel.view.ControlPanelDaemon"
};

com.gleasy.os.reg.programDef[Constants.pname.ctrlPanel.charge] = {
    type:'sys',
    intro:Constants.pname.ctrlPanel.charge,
    shortName:Constants.pname.ctrlPanel.charge,
    fileTypes:[],
    resource:Constants.config.assetPrefix+"/platform/os/assets/javascripts/ctrlPanel/gbankCenter/view/Charge.class.js",
    exe:"com.gleasy.ctrlPanel.gbankCenter.view.Charge"
};



com.gleasy.os.reg.programDef['便签主程序'] = {
		type:'sys',
		intro:"便签",
		app:"便签",
		shortName:"便签",
		singleton:true,
		silence:true,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/sites/notes/NoteFrame.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_noteBook.png",
		exe:"com.gleasy.app.notes.view.NoteFrame"
	};
com.gleasy.os.reg.programDef['便签'] = {
		type:'sys',
		intro:"便签",
		app:"便签",
		shortName:"便签",
		singleton:true,
		isWidget:true,
		autoRun:true,
		mainProcess:"便签主程序",
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/sites/notes/widget/NoteWidgetMain.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_noteBook.png",
		exe:"com.gleasy.app.notes.view.NoteWidgetMain"
	};

com.gleasy.os.reg.programDef['fconv'] = {
		type:'sys',
        app:"fconv",
        shortName:"系统缓存",
        icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/ctrl/images/ctrlIco_clear.png",
		silence:true,
		singleton:true,
		fileTypes:[],
		autoRun:false
	};

com.gleasy.os.reg.programDef['office转换器'] = {
		type:'sys',
		app:"office转换器",
		silence:true,
		singleton:true,
		fileTypes:[],
		autoRun:true,
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/softwares/office/js/OfficeConverter.class.js",
		exe:"com.gleasy.soft.office.OfficeConverter"
	};
com.gleasy.os.reg.programDef['图片转换器'] = {
		type:'sys',
		app:"图片转换器",
		silence:true,
		singleton:true,
		autoRun:true,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/softwares/imageConverter/js/ImageConverter.class.js",
		exe:"com.gleasy.soft.imageConverter.ImageConverter"
	};
com.gleasy.os.reg.programDef['pdf转换器'] = {
		type:'sys',
		app:"pdf转换器",
		silence:true,
		singleton:true,
		autoRun:true,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/softwares/pdfViewer/js/PdfConverter.class.js",
		exe:"com.gleasy.soft.pdf.PdfConverter"
	};	
com.gleasy.os.reg.programDef['文件转换器'] = {
		type:'sys',
		app:"文件转换器",
		silence:true,
		singleton:true,
		autoRun:true,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/softwares/fileConverter/DefaultConverter.class.js",
		exe:"com.gleasy.soft.fileConverter.DefaultConverter"
	};	
com.gleasy.os.reg.programDef['os帮助'] = {
		type:'sys',
		app:"os帮助",
		silence:true,
		singleton:true,
		autoRun:false,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/softwares/osHelper/js/OsHelperFrame.class.js",
		exe:"com.gleasy.soft.osHelper.OsHelperFrame"
	};		
com.gleasy.os.reg.programDef['下载任务'] = {
		type:'sys',
		intro:"下载任务",
		app:"下载任务",
		shortName:"下载任务",
		version:"1.5",
		singleton:true,
		autoRun:false,
		fileTypes:[],
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/download.png",
		supportBrowser:{gleasy:"all"},
		resource:Constants.config.assetPrefix+"/platform/softwares/downloader/js/DownloaderFrame.class.js",
		exe:"com.gleasy.soft.downloader.DownloaderFrame"
	};	
com.gleasy.os.reg.programDef['下载任务Damon'] = {
		type:'sys',
		app:"下载任务",
		silence:true,
		singleton:true,
		autoRun:true,
		fileTypes:[],
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/download.png",
		resource:Constants.config.assetPrefix+"/platform/softwares/downloader/js/DownloaderDaemon.class.js",
		exe:"com.gleasy.soft.downloader.DownloaderDaemon"
	};	
com.gleasy.os.reg.programDef['表格Daemon'] = {
		type:'sys',
		app:"表格",
		silence:true,
		singleton:true,
		autoRun:true,
		fileTypes:[],
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_sheet.png",
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/sites/sheet/SheetDaemon.class.js",
		exe:"com.gleasy.soft.sheet.SheetDaemon"
	};	
com.gleasy.os.reg.programDef['头像编辑器'] = {
		type:'sys',
		app:"头像编辑器",
		silence:true,
		singleton:true,
		autoRun:true,
		version:"1.1.19",
		fileTypes:[],
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/download.png",
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/softwares/avatar/js/AvatarEditorDaemon.class.js",
		exe:"com.gleasy.soft.avatar.AvatarEditorDaemon"
	};	
com.gleasy.os.reg.programDef['白板'] = {
		type:'sys',
		app:"工作窗口",
		silence:true,
		singleton:false,
		version:"1.0.0",
		fileTypes:[],
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/im/images/imWbDockIco.png",
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/softwares/canvas/js/CanvasFrame.class.js",
		exe:"com.gleasy.soft.canvas.CanvasFrame"
	};		
com.gleasy.os.reg.programDef['白板守护进程'] = {
		type:'sys',
		app:"工作窗口",
		silence:true,
		singleton:true,
		version:"1.0.0",
		fileTypes:[],
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/download.png",
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/platform/softwares/canvas/js/Daemon.class.js",
		exe:"com.gleasy.soft.canvas.Daemon"
	};			
	
	
com.gleasy.os.reg.programDef['帮助中心'] = {
		type:'sys',
		intro:"帮助中心",
		app:"帮助中心",
		shortName:"帮助中心",
		singleton:true,
		forceAutoRun:false,
		fileTypes:[],
		supportBrowser:{chrome:"all",safari:"all",firefox:"all",gleasy:"all",ie:"7.0,8.0,9.0,10.0,11.0"},
		resource:Constants.config.assetPrefix+"/sites/helpCenter/HelpCenter.class.js",
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/ico_help.png",
		exe:"com.gleasy.helpCenter.view.HelpCenter"
	};
	
	
com.gleasy.os.reg.programDef['插件适配'] = {
		type:'sys',
		app:"插件适配",
		silence:true,
		singleton:true,
		autoRun:true,
		fileTypes:[],
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/download.png",
		resource:Constants.config.assetPrefix+"/platform/softwares/plugin/js/PluginAdapterIframe.class.js",
		exe:"com.gleasy.soft.plugin.PluginAdapter"
	};	
	
com.gleasy.os.reg.programDef['安全卫士'] = {
		type:'sys',
		app:"安全卫士",
		silence:true,
		singleton:true,
		autoRun:true,
		fileTypes:[],
		icon:Constants.config.assetPrefix+"/platform/os/assets/stylesheets/os/images/download.png",
		resource:Constants.config.assetPrefix+"/platform/softwares/security/js/SafeGuard.class.js",
		exe:"com.gleasy.soft.security.SafeGuard"
	};	
