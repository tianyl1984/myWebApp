
Constants = {};
Constants.config = {};
Constants.config.assetPrefix = "http://asset.gleasy.com";
Constants.config.downloadPrefix = "http://download.gleasy.com";
Constants.config.uploadPrefix = "http://upload.gleasy.com";
Constants.config.clientPrefix = "http://client.gleasy.com";

Constants.config.os = {};
Constants.config.os.layer = {};

/*
 *在osWrap中
*/
Constants.config.os.layer.wallpaper = 0;
Constants.config.os.layer.startMenu = 1000;
Constants.config.os.layer.desktop = 1;


/*在desktop中*/
Constants.config.os.layer.topNav = 103;
Constants.config.os.layer.topNavMenu = 600;
Constants.config.os.layer.collectionPan = 600;
Constants.config.os.layer.windowNormal = 103;
Constants.config.os.layer.windowActive = 590;
Constants.config.os.layer.windowActiveMax = 590;
Constants.config.os.layer.dragingIcon = 602;
Constants.config.os.layer.bottomBar = 601;
Constants.config.os.layer.contextMenu = 605;
Constants.config.os.layer.sideNav = 103;
Constants.config.os.layer.tip = 900;
Constants.config.os.layer.fullscreen = 610;
Constants.config.os.layer.messageDialog = 999;
Constants.config.os.layer.screenMask = 1200;
Constants.config.os.layer.loginPanel = 1201;

Constants.config.os.message = {};
Constants.config.os.message.size=10;
Constants.config.os.defaultHead=Constants.config.assetPrefix+"/platform/os/assets/stylesheets/workbench/images/defaultHead.png";
Constants.config.os.supportBrowser=[
    {type:'ie', minVer:"9.0"},
    {type:'ie', minVer:"5.0"},
    {type:'ie', minVer:"6.0"},
    {type:'ie', minVer:"7.0"},
    {type:'ie', minVer:"8.0"},
    {type:'firefox'},
    {type:'chrome'},
    {type:'gleasy'},
    {type:'safari'}
];

Constants.data = {};
//Constants.data.user = {};
//Constants.data.user.type = 1;

Constants.app = {};
Constants.app.workbench = "一信";
Constants.app.appStore = "应用商店";
Constants.app.ctrlPanel = "控制面板";
Constants.app.background = "桌面背景";
Constants.app.capacityManage = "容量管理";
Constants.app.CapacityHandleView = "容量通知";

Constants.pname = {};
Constants.pname.workbench = {};
Constants.pname.workbench.wbDaemon = '一信守护进程';
Constants.pname.workbench.selector = '一信选人组件';
Constants.pname.workbench.task = "发任务";
Constants.pname.workbench.activity = "发活动";
Constants.pname.workbench.vote = "发投票";
Constants.pname.workbench.approval = "发审批";
Constants.pname.workbench.mail = "发邮件";


Constants.pname.ctrlPanel = {};
Constants.pname.ctrlPanel.main = "控制面板";
Constants.pname.ctrlPanel.daemon = "控制面板守护进程";
Constants.pname.ctrlPanel.companyPanel = "企业面板";
Constants.pname.ctrlPanel.gbankCenter = "G币中心";
Constants.pname.ctrlPanel.charge = "兑换G币";
Constants.pname.ctrlPanel.accountCenter = "帐号中心";
Constants.pname.ctrlPanel.orderManager = "订单管理";
Constants.pname.ctrlPanel.capacityMonitor = "容量监测";
Constants.pname.ctrlPanel.inviteUser = "邀请注册";

Constants.pname.background = {};
Constants.pname.background.daemon = "桌面背景守护进程";

Constants.pname.gbank = {};
Constants.pname.gbank.daemon = "G币中心守护进程";



Constants.command = {};

Constants.command.loadDesktop = "loadDesktop";
Constants.command.loadDesktopOK = "loadDesktopOK";
Constants.command.desktopDataChangeByUser = "desktopDataChangeByUser";
Constants.command.toggleAllWindow = "toggleAllWindow";
Constants.command.activeWindow = "activeWindow";
Constants.command.activeWindowById = "activeWindowById";
Constants.command.createNewWindow = "createNewWindow";
Constants.command.closeWindow = "closeWindow";
Constants.command.toggleWindow="toggleWindow";
Constants.command.inActiveWindow="inActiveWindow";
Constants.command.clearActiveLink="clearActiveLink";
Constants.command.loadWidget = "loadWidget";
Constants.command.loadWidgetOK = "loadWidgetOK";
Constants.command.widgetDataChangeByUser = "widgetDataChangeByUser";
Constants.command.changeWallpaper = "changeWallpaper";
Constants.command.switchDesktop = "switchDesktop";
Constants.command.openInGWindow = "openInGWindow";
Constants.command.sendMessageToApp = "sendMessageToApp";
Constants.command.runCommand = "runCommand";
Constants.command.parseAndInitFromUrl = "parseAndInitFromUrl";
Constants.command.runCommand = "runCommand";
Constants.command.runInnerCommand = "runInnerCommand";
Constants.command.loadApplication = "loadApplication";
Constants.command.addApplication = "addApplication";
Constants.command.directLoadApplication = "directLoadApplication";
Constants.command.lockDesktop = "lockDesktop";
Constants.command.systemLogout = "systemLogout";
Constants.command.retrieveDesktop = "retrieveDesktop";
Constants.command.showCollection = "showCollection";
Constants.command.setAppConfig = "setAppConfig";
Constants.command.moveIcon = "moveIcon";

Constants.command.createCollection = "createCollection";
Constants.command.renameCollection = "renameCollection";
Constants.command.removeCollection = "removeCollection";

Constants.command.setDragData = "setDragData";
Constants.command.getDragData = "getDragData";


Constants.command.os = {};
Constants.command.os.resetTopNavBar = "os.resetTopNavBar";
Constants.command.os.showStartMenu = "os.showStartMenu";
Constants.command.os.hideStartMenu = "os.hideStartMenu";
Constants.command.os.showHotAppMenu = "os.showHotAppMenu";
Constants.command.os.hideHotAppMenu = "os.hideHotAppMenu";
Constants.command.os.loadHotApp = "os.loadHotApp";
Constants.command.os.changeDesktopPage = "os.changeDesktopPage";
Constants.command.os.navDesktopPage = "os.navDesktopPage";
Constants.command.os.iconDragStart = "os.iconDragStart";
Constants.command.os.iconDragEnd = "os.iconDragEnd";
Constants.command.os.setWallpaper = "os.setWallpaper";
Constants.command.os.loadDefaultWallpaper = "os.loadDefaultWallpaper";
Constants.command.os.showTip = "os.showTip";
Constants.command.os.toFullscreen = "os.toFullscreen";
Constants.command.os.cancelFullscreen = "os.cancelFullscreen";
Constants.command.os.systemexit = "os.systemexit";
Constants.command.os.requireLogin = "os.requireLogin";
Constants.command.os.showScreenMask = "os.showScreenMask";
Constants.command.os.hideScreenMask = "os.hideScreenMask";
Constants.command.os.showDesktopMask = "os.showDesktopMask";
Constants.command.os.hideDesktopMask = "os.hideDesktopMask";
Constants.command.os.registerHeartbeatJob = "os.registerHeartbeatJob";
Constants.command.os.unRegisterHeartbeatJob = "os.unRegisterHeartbeatJob";
Constants.command.os.startHeartBeat = "os.startHeartBeat";
Constants.command.os.stopHeartBeat = "os.startHeartBeat";
Constants.command.os.fireHeartBeat = "os.fireHeartBeat";
Constants.command.os.reloadUserCard = "os.reloadUserCard";
Constants.command.os.reloadUserCardFinish = "os.reloadUserCardFinish";
Constants.command.os.updateCurrentUserInfo = "os.updateCurrentUserInfo";
Constants.command.os.openUrl = "os.openUrl";
Constants.command.os.loginSuccess = "os.loginSuccess";
Constants.command.os.repeatLogin = "os.repeatLogin";
Constants.command.os.notSupportBrowser = "os.notSupportBrowser";
Constants.command.os.savePageImage = "os.savePageImage";

Constants.command.os.showIconMenu = "os.showIconMenu";
Constants.command.os.updateAppMessage = "os.updateAppMessage";
Constants.command.os.updateAppDeskMessage = "os.updateAppDeskMessage";
Constants.command.os.updateAppDockNum = "os.updateAppDockNum";

Constants.command.os.frame = {};
Constants.command.os.frame.create = "os.frame.create";
Constants.command.os.frame.createAndShow = "os.frame.createAndShow";
Constants.command.os.frame.addEventListener = "os.frame.addEventListener";
Constants.command.os.frame.setVisible = "os.frame.setVisible";
Constants.command.os.frame.toggle = "os.frame.toggle";
Constants.command.os.frame.focus = "os.frame.focus";
Constants.command.os.frame.setFocus = "os.frame.focus";
Constants.command.os.frame.clearfocus = "os.frame.clearfocus";
Constants.command.os.frame.setContent = "os.frame.setContent";
Constants.command.os.frame.close = "os.frame.close";
Constants.command.os.frame.forceclose = "os.frame.forceclose";
Constants.command.os.frame.runCommand = "os.frame.runCommand";
Constants.command.os.frame.showHideFrameGroup = "os.frame.showHideFrameGroup";

Constants.command.os.process = {};
Constants.command.os.process.create = "os.process.create";
Constants.command.os.process.kill = "os.process.kill";
Constants.command.os.process.list = "os.process.list";
Constants.command.os.process.addFrame = "os.process.addFrame";
Constants.command.os.process.removeFrame = "os.process.removeFrame";
Constants.command.os.process.sendMessage = "os.process.sendMessage";
Constants.command.os.process.runProgram = "os.process.runProgram";
Constants.command.os.process.dead = "os.process.dead";

Constants.command.os.account = {};
Constants.command.os.account.auth = "os.account.auth";
Constants.command.os.account.regIndividual = "os.account.regIndividual";
Constants.command.os.account.regEnterprise = "os.account.regEnterprise";
Constants.command.os.account.logout = "os.account.logout";
Constants.command.os.account.regEnterprise = "os.account.regEnterprise";
Constants.command.os.account.finishStaffInfo = "os.account.finishStaffInfo";

Constants.command.os.dock = {};
Constants.command.os.dock.create = "os.dock.create";
Constants.command.os.dock.changeInfo = "os.dock.changeInfo";
Constants.command.os.dock.addMessage = "os.dock.addMessage";
Constants.command.os.dock.removeMessage = "os.dock.removeMessage";
Constants.command.os.dock.changeIconInfo = "os.dock.changeIconInfo";

Constants.command.os.iconPan = {};
Constants.command.os.iconPan.dockId = -1000;
Constants.command.os.iconPan.pinId = -2000;

Constants.command.os.iconTypeStr = {
	1:'sys',
	2:'app',
	3:'collection'
};
Constants.command.os.iconTypeInt = {
	sys:1,
	app:2,
	collection:3
};

Constants.command.os.tray = {};
Constants.command.os.tray.create = "os.tray.create";
Constants.command.os.tray.close = "os.tray.close";
Constants.command.os.tray.addEventListener = "os.tray.addEventListener";

Constants.command.os.message = {};
Constants.command.os.message.add = "os.message.add";
Constants.command.os.message.reload = "os.message.reload";
Constants.command.os.message.remove = "os.message.remove";
Constants.command.os.message.blink = "os.message.blink";
Constants.command.os.message.init = "os.message.init";
Constants.command.os.message.arrive = "os.message.arrive";
Constants.command.os.message.appremove = "os.message.appremove";
Constants.command.os.message.numChange = "os.message.numChange";

Constants.command.os.setting = {};
Constants.command.os.setting.save = "os.setting.save";
Constants.command.os.setting.load = "os.setting.load";


Constants.command.minidesk = {};
Constants.command.minidesk.debug = {};
Constants.command.minidesk.hideScroll = "minidesk.hideScroll";
Constants.command.minidesk.showScroll = "minidesk.showScroll";
Constants.command.minidesk.addApp = "minidesk.addApp";
Constants.command.minidesk.doAddApp = "minidesk.doAddApp";

Constants.command.desktop = {};
Constants.command.desktop.addApp = "desktop.addApp";
Constants.command.desktop.addAppSuccessful = "desktop.addAppSuccessful";
Constants.command.desktop.removeApp = "desktop.removeApp";

Constants.command.system = {};
Constants.command.system.alert = "system.alert";
Constants.command.system.confirm = "system.confirm";
Constants.command.system.openURL = "system.openURL";
Constants.command.system.showLoginBox = "system.showLoginBox";
Constants.command.system.addToMiniDesk = "system.addToMiniDesk";
Constants.command.system.addToDesktop = "system.addToDesktop";
Constants.command.system.installApp = "system.installApp";
Constants.command.system.unInstallApp = "system.unInstallApp";
Constants.command.system.openModalDialog = "system.openModalDialog";
Constants.command.system.closeModalDialog = "system.closeModalDialog";
Constants.command.system.registerHotkeyHandler = "system.registerHotkeyHandler";
Constants.command.system.getInstalledApps = "system.getInstalledApps";

Constants.command.window = {};
Constants.command.window.close = "window.close";
Constants.command.window.min = "window.min";
Constants.command.window.max = "window.max";
Constants.command.window.setSize = "window.setSize";
Constants.command.window.setXY = "window.setXY";
Constants.command.window.setExitConfirm = "window.setExitConfirm";
Constants.command.window.setExitConfirm = "window.removeExitConfirm";
Constants.command.window.resizeStart = "window.resizeStart";
Constants.command.window.resizeEnd = "window.resizeEnd";
Constants.command.window.startDrag = "window.startDrag";
Constants.command.window.moveby = "window.moveby";
Constants.command.window.registerExitConfirm = "window.registerExitConfirm";
Constants.command.window.removeExitConfirm = "window.removeExitConfirm";


Constants.command.debug = {};
Constants.command.debug.minidesk = {};
Constants.command.debug.desktop = {};
Constants.command.debug.minidesk.addApp = "debug.minidesk.addApp";
Constants.command.debug.desktop.addApp = "debug.desktop.addApp";

Constants.config.wb = {};
Constants.config.wb.logPageSize = 5;
Constants.config.wb.mailRecMax = 50;
Constants.config.wb.autoSaveInteval = 3;//WORKBENCH-188 
Constants.config.wb.maxAttachment = 25 * 1024 *1024;//25M
Constants.config.wb.maxLimitTxt = "附件不能超过25M";//

Constants.command.wb = {};

Constants.command.wb.msgevt = {};
Constants.command.wb.msgevt.wbnew = 'OS_MSGCENTER_EVENT_WB_NEW';
Constants.command.wb.nav = 'wb.nav';
Constants.command.wb.folderList = {};
Constants.command.wb.folderList.loadData = "wb.folderList.loadData";
Constants.command.wb.folderList.refresh = "wb.folderList.refresh";
Constants.command.wb.task = {};
Constants.command.wb.task.create = "wb.task.create";
Constants.command.wb.task.edit = "wb.task.edit";

Constants.command.wb.saved = "wb.saved";
Constants.command.wb.sent = "wb.sent";

Constants.command.wb.drapFiles = "wb.drapFiles";
Constants.command.wb.refreshPageOne = 'wb.refreshPageOne';//20M

Constants.command.wb.showDetail={};
Constants.command.wb.showDetail.task = "wb.showDetail.task";
Constants.command.wb.showDetail.returnTo = "wb.showDetail.returnTo";

Constants.command.wb.tagAlter = "wb.tagAlter";
Constants.command.wb.dataView = {}
Constants.command.wb.dataView = {};
Constants.command.wb.dataView.tagAlter = "wb.dataView.tagAlter";//feture del
Constants.command.wb.dataView.detailOpen = "wb.dataView.detailOpen";

Constants.command.wb.sideView = {};
Constants.command.wb.sideView.tagAlter = "wb.sideView.tagAlter";

Constants.command.wb.detail = {};
Constants.command.wb.detail.refresh = "wb.detail.refresh";//刷新详情
Constants.command.wb.detail.toDelete = "wb.detail.toDelete";//删除
Constants.command.wb.detail.pdel = "wb.detail.pdel";//永久删除
Constants.command.wb.detail.toolbarTop = {};
Constants.command.wb.detail.toolbarTop.tagAlter = "wb.detail.toolbarTop.tagAlter";//feture del
Constants.command.wb.detail.toolbarTop.returnList = "wb.detail.toolbarTop.returnList";

Constants.command.appStore = {};
Constants.command.appStore.appRefresh = "Constants.command.appStore.appRefresh";

Constants.command.ctrlPanel = {};
Constants.command.ctrlPanel.companyPanel = {};
Constants.command.ctrlPanel.companyPanel.selectDepartment = "companyPanel.selectDepartment";
Constants.command.ctrlPanel.companyPanel.updateDepartment = "companyPanel.updateDepartment";
Constants.command.ctrlPanel.companyPanel.loadDepartment = "companyPanel.loadDepartment";
Constants.command.ctrlPanel.companyPanel.selectStaff = "companyPanel.selectStaff";
Constants.command.ctrlPanel.companyPanel.updateStaff = "companyPanel.updateStaff";
Constants.command.ctrlPanel.companyPanel.toStaffList = "companyPanel.toStaffList";
Constants.command.ctrlPanel.companyPanel.reloadStaffList = "companyPanel.reloadStaffList";
Constants.command.ctrlPanel.companyPanel.updateDomain = "companyPanel.updateDomain";
Constants.command.ctrlPanel.companyPanel.serviceBuyed = "companyPanel.serviceBuyed";
Constants.command.ctrlPanel.companyPanel.serviceAuthed = "companyPanel.serviceAuthed";

Constants.command.ctrlPanel.accountCenter = {};
Constants.command.ctrlPanel.accountCenter.verified = "accountCenter.verified";
Constants.command.ctrlPanel.accountCenter.getInviteRegUrl = "accountCenter.getInviteRegUrl";

Constants.command.ctrlPanel.background={};
Constants.command.ctrlPanel.background.save = "background.save";
Constants.command.ctrlPanel.background.refresh = "background.refresh";

Constants.command.ctrlPanel.gbank={};
Constants.command.ctrlPanel.gbank.charge = "gbank.charge";
Constants.command.ctrlPanel.gbank.chargeHelp = "gbank.chargeHelp";


Constants.command.im = {};
Constants.command.im.error = {};
Constants.command.im.canvas = {};
Constants.command.im.connection = {};
Constants.command.im.data = {};
Constants.command.im.canvas.importPic = "importPic";
Constants.command.im.canvas.clearCanvas = "clearCanvas";
Constants.command.im.canvas.sendPic = "sendPic";
Constants.command.im.canvas.drawRect = "drawRect";
Constants.command.im.canvas.drawCircle = "drawCircle";
Constants.command.im.canvas.drawOval = "drawOval";
Constants.command.im.canvas.drawArrow = "drawArrow";
Constants.command.im.canvas.drawLine = "drawLine";
Constants.command.im.canvas.drawPen = "drawPen";
Constants.command.im.canvas.drawImage = "drawImage";
Constants.command.im.canvas.drawEraser = "drawEraser";
Constants.command.im.canvas.drawText = "drawText";
Constants.command.im.canvas.endDrawing = "endDrawing";
Constants.command.im.canvas.setDrawer = "setDrawer";
Constants.command.im.canvas.saveGraphItem = "saveGraphItem";
Constants.command.im.loadshardconfig = "loadshardconfig";
Constants.command.im.canvas.showMask = "canvas.showMask";
Constants.command.im.canvas.hideMask = "canvas.hideMask";
Constants.command.im.canvas.initDataAvailable = "canvas.initdata";	//初始化数据到达
Constants.command.im.canvas.drawer = {};
Constants.command.im.canvas.drawer.setStyle = "canvas.drawer.setStyle";

Constants.command.im.canvas.textDrawer = {};
Constants.command.im.canvas.textDrawer.setStyle = "canvas.text.drawer.setStyle";
Constants.command.im.canvas.saveCanvas = {};
Constants.command.im.canvas.saveCanvas.toRemote = "canvas.save.remote";	//保存涂鸦结果到云硬盘
Constants.command.im.canvas.saveCanvas.saveAs = "canvas.save.as";
Constants.command.im.canvas.scroll = "canvas.scroll";

    
Constants.command.im.connectByIM = "im.connectByIM";
Constants.command.im.connect = "im.connect";
Constants.command.im.DirectConnect = "im.DirectConnect";
Constants.command.im.disconnect = "im.disconnect";
Constants.command.im.send = "im.send";
Constants.command.im.loginSuccess = "im.loginSuccess";
Constants.command.im.loginFail = "im.loginFail";
Constants.command.im.sessionStart = "im.sessionStart";
Constants.command.im.sessionReady = "im.sessionReady";
Constants.command.im.connection.connected = "connection.connected";
Constants.command.im.connection.disconnected = "connection.disconnected";
Constants.command.im.connection.fail = "connection.fail";
Constants.command.im.connection.connectfail = "connection.connectfail";
Constants.command.im.data.message = "im.data.message";
Constants.command.im.data.presence = "im.data.presence";
Constants.command.im.data.iq = "im.data.iq";
Constants.command.im.data.receive = "im.data.receive";
Constants.command.im.data.send = "im.data.send";
Constants.command.im.ready = "im.ready";
//add by jiahao
Constants.command.im.chat = {};
Constants.command.im.chat.component = {};
Constants.command.im.chat.message = {};
Constants.command.im.chat.presence = {};
Constants.command.im.chat.member = {};
Constants.command.im.chat.unconfirm = {};

Constants.command.im.chat.member.recv = "im.chat.member.recv";
Constants.command.im.chat.unconfirm.recv = "im.chat.unconfirm.recv";
Constants.command.im.chat.member.substracted = "im.chat.member.substracted";
Constants.command.im.chat.member.getAllMembersAndShowUserSelector = "getAllMembersAndShowUserSelector";	//获取当前讨论组所有memnber并显示选人组件
Constants.command.im.chat.member.render = "im.chat.member.render";
Constants.command.im.chat.unconfirm.render = "im.chat.unconfirm.render";
Constants.command.im.chat.member.updateInfo = "im.chat.member.updateInfo";
Constants.command.im.chat.unconfirm.active = "im.chat.unconfirm.active";
Constants.command.im.chat.unconfirm.identify = "im.chat.unconfirm.identify";

Constants.command.im.chat.presence.recv = "im.chat.pres.recv";
Constants.command.im.chat.presence.render = "im.chat.pres.render";

Constants.command.im.chat.message.clearDockInfo = "im.chat.message.clearDockInfo";		//清除dock区的消息条数
Constants.command.im.chat.message.recv = "im.chat.message.recv";    //收到im消息
Constants.command.im.chat.message.send = "im.chat.message.send";    //发送im消息
Constants.command.im.chat.message.render = "im.chat.message.render";    //render消息
Constants.command.im.chat.message.renderBatch = "im.chat.message.renderBatch";  //render批量消息
Constants.command.im.chat.message.normalType = 0;	//普通消息
Constants.command.im.chat.message.sysTypeAttachment = 1;	//附件共享消息
Constants.command.im.chat.message.sysTypeTopicTitleUpdate = 2;		//修改讨论组标题
Constants.command.im.chat.message.sysTypeAttachmentUnshare = 3;	//删除附件共享消息
Constants.command.im.chat.message.sysTypeUserExitTopic = 4;	//用户退出了讨论组
Constants.command.im.chat.message.whiteBoardTip = 5;	//有用户邀请其他人使用白板

Constants.command.im.chat.component.send = "sendMessage";   //component级别的发送消息
Constants.command.im.chat.component.sendTopicMessage = "sendTopicMessage";   //component级别的发送消息(讨论组)
Constants.command.im.chat.component.sendPrivateMessage = "sendPrivateMessage";   //component级别的发送消息(私聊)
Constants.command.im.chat.component.recv = "recvMessage";   //component级别的获得消息
Constants.command.im.chat.component.recvSys = "recvSystemMessage";   //component级别的获得系统消息
Constants.command.im.chat.component.presRecv = "recvPresences"; //component级别的获得在线状态		@deprecated
Constants.command.im.chat.component.presGet = "getMultiplePresences";   //component级别的批量获取在线状态
Constants.command.im.chat.component.topicUsersGet = "getTopicUsers";    //component级别的获取讨论组成员
Constants.command.im.chat.component.topicUsersRecv = "recvTopicUsers";  //component级别的获得讨论组成员(服务器响应)
Constants.command.im.chat.component.topicUnconfirmListRecv = "recvTopicUnconfirmList";	//component级别的获得讨论组未激活成员(服务器响应)
Constants.command.im.chat.component.inviteUserToTopic = "inviteUserToTopic";    //component级别，邀请用户进入讨论组
Constants.command.im.chat.component.quitQuitTopic = "quitTopic";    //component级别，用户退出讨论组
Constants.command.im.chat.component.removeTopicInMainWin = "removeTopicInMainWin";		//从主面板删除退出的讨论组，即时的，不等结果反馈
Constants.command.im.chat.component.quitTopicSucceed = "quitTopicSucceed";  //component级别，用户退出讨论组(成功的返回消息)
Constants.command.im.chat.component.topicUserSubstracted = "topicUserSubstracted";  //component级别，有用户退出了讨论组（服务器响应）
Constants.command.im.chat.component.getTopicOfflineMsg = "getTopicOfflineMsg";  //component级别，获取讨论组离线消息
Constants.command.im.chat.component.recvTopicOfflineMsg = "recvTopicOfflineMsg";    //component级别，获取讨论组离线消息（服务器响应）
Constants.command.im.chat.component.commentGet = "getComment";  //获取批注,针对某条消息
Constants.command.im.chat.component.commentGot = "gotComment";  //获取了批注,针对某条消息
Constants.command.im.chat.component.commentSave = "saveComment";  //保存批注,针对某条消息
Constants.command.im.chat.component.commentUpdate = "updateComment";    //update批注,针对某条消息(缓存)
Constants.command.im.chat.component.sendComment = "commentMessage"; //发送批注
Constants.command.im.chat.component.dropFiles = "dropFiles";    //从云硬盘拖拽文件进来
Constants.command.im.chat.component.shareFile = "shareFile";		//共享文件
Constants.command.im.chat.component.unshareFile = "unshareFile";		//删除共享文件
Constants.command.im.chat.component.activeUser = "activeUser";		//尝试激活用户
Constants.command.im.chat.component.activeUserFailed = "activeUserFailed";		//激活用户失败
Constants.command.im.chat.component.activeUserSucceeded = "activeUserSucceeded";		//激活用户成功
Constants.command.im.chat.component.activeUserExist = "activeUserExist";			//激活用户已经存在
Constants.command.im.chat.component.identifyUser = "identifyUser";		//尝试识别用户
Constants.command.im.chat.component.identifyUserFailed = "identifyUserFailed";		//识别用户失败
Constants.command.im.chat.component.identifyUserSucceeded = "identifyUserSucceeded";		//识别用户成功
Constants.command.im.chat.component.identifyUserSucceededToOperator = "identifyUserSucceededToOperator";	//识别用户成功(operator)
Constants.command.im.chat.component.searchCards = "chat.searchCards";		//根据占位符的名称搜索卡片
Constants.command.im.chat.component.cardsAvailable = "chat.cardsAvailable";			//搜索到了名片
Constants.command.im.chat.component.cardsSearchFailed = "chat.cardsSearchFailed";			//搜索名片失败
Constants.command.im.chat.component.whiteBoardCleared = "chat.clearWhiteBoard";		//白板清屏了
Constants.command.im.chat.component.whiteBoardContentChanged = "chat.whiteBoardContentChanged";		//白板内容变更了（不包括清屏）

Constants.command.im.chat.component.attachTabPane = "attachTabPane";    //关联tab和content pane

Constants.command.im.chat.component.showAttachments = "showAttachments";    //请求显示附件(tab)
Constants.command.im.chat.component.showAttachmentsV = "showAttachmentsV";  //请求显示附件(view)
Constants.command.im.chat.component.refreshAttachments = "refreshAttachments";  //请求刷新附件
Constants.command.im.chat.component.fetchAttachments = "fetchAttachments";  //从后台读取附件
Constants.command.im.chat.component.refreshAttachmentsM = "refreshAttachmentsM";    //刷新附件[model]
Constants.command.im.chat.component.attachmentsAvailable = "attachmentsAvailable";  //附件列表数据来到

Constants.command.im.chat.component.showCommentPane = "showCommentPane";		//请求显示批注面板（tab）
Constants.command.im.chat.component.showForwardPane = "showFowardPane";		//请求显示转发列表面板（tab）

Constants.command.im.chat.component.myStatusChanged = "myStatusChanged";	//自己的状态变更

Constants.command.im.chat.component.changeTopicTitle = "changeTopicTitle";   //修改讨论组标题
Constants.command.im.chat.component.recvTopicTitle = "recvTopicTitle";	//接收讨论组标题
Constants.command.im.chat.component.userCloseTopicWindow = "userCloseTopicWindow";    //component级别，用户关闭讨论组窗口
Constants.command.im.chat.component.inputing = "chatInputing";	//正在输入消息
Constants.command.im.chat.component.inputingShow = "chatInputingShow";	//正在输入消息，显示

Constants.command.im.chat.component.renderReplyTipToChatContentBox = "renderReplyToChatContentBox";		//在其他地方（如历史记录窗口）'引用'消息，填充到输入框

Constants.command.im.chat.component.showMediaChatRequestPane = "showMediaChatRequestPane";		//打开语音/视频聊天请求界面

Constants.command.im.chat.component.requestForVoiceChatInComponent = "requestForVoiceChatInComponent";		//请求语音聊天，component内
Constants.command.im.chat.component.requestForVoiceChat = "requestForVoiceChat";	//请求语音聊天，到of的
Constants.command.im.chat.component.requestForVoiceChatToPrivateChat = "requestForVoiceChatToPrivateChat";		//请求语音聊天，被im wedget收到后，转换传送到私聊窗口
Constants.command.im.chat.component.dealWithVoiceChatReuqest = "dealWithVoiceChatReuqest";		//处理语音聊天请求，到of的
Constants.command.im.chat.component.voiceChatConnected = "voiceChatConnected";		//语音聊天成功连接双方了
Constants.command.im.chat.component.voiceChatCancelled = "voiceChatCancelled";		//语音聊天取消了或者结束了
Constants.command.im.chat.component.voiceChatAccepted = "voiceChatAccepted";		//语音聊天被接受了
Constants.command.im.chat.component.voiceChatRejected = "voiceChatRejected";		//语音聊天被拒绝了

Constants.command.im.chat.component.requestForVideoChatInComponent = "requestForVideoChatInComponent";		//请求视频聊天，component内
Constants.command.im.chat.component.requestForVideoChat = "requestForVideoChat";	//请求视频聊天，到of的
Constants.command.im.chat.component.requestForVideoChatToPrivateChat = "requestForVideoChatToPrivateChat";		//请求视频聊天，被im wedget收到后，转换传送到私聊窗口
Constants.command.im.chat.component.dealWithVideoChatReuqest = "dealWithVideoChatReuqest";		//处理视频聊天请求，到of的
Constants.command.im.chat.component.cancelvideoChat = "cancelvideoChat";	//取消视频聊天
Constants.command.im.chat.component.videoChatConnected = "videoChatConnected";		//视频聊天成功连接双方了
Constants.command.im.chat.component.videoChatCancelled = "videoChatCancelled";		//视频聊天取消了或者结束了
Constants.command.im.chat.component.videoChatAccepted = "videoChatAccepted";		//视频聊天被接受了
Constants.command.im.chat.component.videoChatRejected = "videoChatRejected";		//视频聊天被拒绝了

Constants.command.im.chat.component.showVideoChatPane = "showVideoChatPane";		//打开视频聊天界面

Constants.command.im.chat.component.enterTopic = "enterTopic";		//登入讨论组
Constants.command.im.chat.component.leaveTopic = "leaveTopic";		//登出讨论组

Constants.command.im.chat.component.pchatUserCardLoaded = "pchatUserCardLoaded";		//私聊中用户个人名片读取成功
Constants.command.im.chat.component.updatePchatUserInfo = "updatePchatUserInfo";		//更新私聊用户信息，主要用于打开了的窗口，但用户还没load出来的情况

Constants.command.im.chat.component.screenShotReady = "screenShotReady";		//有截图数据,并且是某个窗口的,那就传递给自己的子对象

Constants.command.im.chat.component.showOfflineTip = "pchat.showOfflineTip";
Constants.command.im.chat.component.hideOfflineTip = "pchat.hideOfflineTip";

Constants.command.im.chat.component.changeSendHotKey = "chat.changeSendHotKey";     //修改快捷键

Constants.command.im.chat.component.openWhiteBoard = "chat.openWhiteBoard";     //请求打开白板

Constants.command.im.history = {};
Constants.command.im.history.component = {};
Constants.command.im.history.queryHistory = "queryHistory"; //查询讨论组历史记录(单个讨论组)
Constants.command.im.history.queryHistoryByRange = "queryHistoryByRange"; //查询讨论组历史记录(范围，目前是根据一个msgId，找其所在的区间)
Constants.command.im.history.searchHistory = "searchHistory"; //搜索讨论组历史记录
Constants.command.im.history.deleteHistory = "deleteHistory"; //删除讨论组部分记录
Constants.command.im.history.deleteHistoryOK = "deleteHistoryOK";	//删除讨论组部分记录成功
Constants.command.im.history.deleteHistoryFail = "deleteHistoryFail";	//删除讨论组部分记录失败
Constants.command.im.history.historyDataAvailable = "historyDataAvailable"; //讨论组历史记录数据到达
Constants.command.im.history.historySearchDataAvailable = "historySearchDataAvailable"; //讨论组历史记录数据到达(搜索的)
Constants.command.im.history.historyDataEmpty = "historyDataEmpty"; //讨论组历史记录数据为空结果集
Constants.command.im.history.historyDataFailed = "historyDataFailed";		//讨论组历史记录数据获取失败
Constants.command.im.history.scrollToHistory = "scrollToHistory";		//滚动到某条历史记录
Constants.command.im.history.prevHistoryPage = "prevHistoryPage";   //上一页
Constants.command.im.history.nextHistoryPage = "nextHistoryPage";   //下一页
Constants.command.im.history.requestGrouptopic = "history.request.grouptopic"; //请求加载分组讨论组数据
Constants.command.im.history.requestGrouptopicPage = "history.request.grouptopic.page"; //请求加载分组讨论组数据
Constants.command.im.history.grouptopicAvailable = "history.grouptopic.available"; //分组的讨论组数据到达
Constants.command.im.history.topicgroupAvailable = "history.topicgroup.available"; //用户自定义分组数据到达
Constants.command.im.history.currentTopicChanged = "currentTopicChanged";   //点击了其他讨论组
Constants.command.im.history.loadModifyTopicGroupMenu = 'history.request.modify.groupMenu';     //请求加载修改分组菜单
Constants.command.im.history.modifyTopicGroupMenuAvailable = 'history.modify.groupMenu.available';      //请修改分组菜单数据到达
Constants.command.im.history.deleteTopic = 'history.request.delete.topic';      //请求删除讨论组
Constants.command.im.history.deleteTopicOK = 'history.request.delete.topic.ok';     //请求删除讨论组
Constants.command.im.history.searchTopics = 'history.topic.search';	//搜索讨论组
Constants.command.im.history.searchTopicsPage = 'history.topic.search.page';	//搜索讨论组(使用分页)
Constants.command.im.history.searchTopicsAvailable = 'history.topic.search.avail';	//搜索讨论组数据到达
Constants.command.im.history.changeTopic = 'hisotry.changeTopic';		//改显示另一个topic

Constants.command.im.history.queryReplies = "history.queryReplies";		//查询转发列表
Constants.command.im.history.setQueryRepliesMsg = "history.setQueryRepliesMsg";		//设置转发列表源消息
Constants.command.im.history.repliesDataAvailable = "history.repliesDataAvailable";	//转发列表分页数据返回了

Constants.command.im.history.queryComments = "history.queryComments";		//查询批注列表
Constants.command.im.history.commentsDataAvailable = "history.commentsDataAvailable";	//批注列表分页数据返回了
Constants.command.im.history.commentsDataFetchError = "history.commentsDataFetchError";	//批注列表数据获取错误
Constants.command.im.history.editComment = "history.editComment";		//编辑批注
Constants.command.im.history.backToCommentList = "history.backToCommentList";		//返回到列表
Constants.command.im.history.showAllComments = "history.showAllComments";		//显示某个讨论组的批注列表
Constants.command.im.history.commentUpdated = "history.commentUpdated";		//批注成功修改了（主要用于改回去列表）

Constants.command.im.history.loadMylinkData = 'history.request.mylink'; //请求我的好友
Constants.command.im.history.mylinkDataAvailable = 'history.link.available';    //我的好友数据到达
Constants.command.im.history.loadDepartments = 'history.request.departments';       //请求我的公司部门数据
Constants.command.im.history.departmentsAvailable = 'history.departments.available';        //我的公司部门数据到达
Constants.command.im.history.loadDeptMemberDatas = 'history.request.deptMembers';       //请求我的公司组织成员数据
Constants.command.im.history.deptMemberDatasAvailable = 'history.deptMembers.available';        //我的公司组织成员数据到达
Constants.command.im.history.loadStrangerDatas = "history.request.strangers";		//请求陌生人列表数据
Constants.command.im.history.strangerDatasAvailable = "history.strangers.available";		//陌生人数据到达
Constants.command.im.history.topicSelected = "topicSelected"; //选中了某个讨论组了
Constants.command.im.history.noTopicSelected = "noTopicSelected"; //没有选中任何讨论组了

Constants.command.im.remind = {};
Constants.command.im.remind.component = {};
Constants.command.im.remind.topicOpened = "im.remind.topicOpened";  //从提醒组件打开了讨论组

Constants.command.im.desktop = {};
Constants.command.im.desktop.userLinkAdded = "userLinkAdded";
Constants.command.im.desktop.userLinkRemoved = "userLinkRemoved";
Constants.command.im.desktop.userInfoChanged = "userInfoChanged";
Constants.command.im.desktop.orgMemberAdded = "orgMemberAdded";
Constants.command.im.desktop.orgMemberRemoved = "orgMemberRemoved";
Constants.command.im.desktop.orgMemberInfoChanged = "orgMemberInfoChanged";
Constants.command.im.desktop.orgDepartmentUpdate = "orgDepartmentUpdate";		//部门信息更新...
Constants.command.im.desktop.orgDepartmentRemove = "orgDepartmentRemove";		//部门删除...
Constants.command.im.desktop.newTopicGroupAdded = "newTopicGroupAdded";		//新讨论组分组被创建了
Constants.command.im.desktop.activeTopicLoadFailed = "activeTopicLoadFailed";		//最近消息讨论组数据拉取失败
Constants.command.im.desktop.groupTopicLoadFailed = "groupTopicLoadFailed";		//分组下的讨论组数据拉取失败
Constants.command.im.desktop.refreshActiveTopics = "refreshActiveTopics";		//重新加载最近消息讨论组列表
Constants.command.im.desktop.recvUserSign = "recvUserSign";		//接收用户个性签名
Constants.command.im.desktop.updateUserAvatar = "updateUserAvatar";		//请求更新用户头像(是用户头像，不是名片头像!)
Constants.command.im.desktop.clearCapacity = "clearCapacity";		//清除容量回调
Constants.command.im.desktop.reloadFriendStatus = "im.desktop.reloadFriendStatus";		//重绘所有好友和企业成员的在线状态，根据cache记录
Constants.command.im.desktop.updateBox = "im.updateBox";		//名片夹创建或更新了
Constants.command.im.desktop.deleteBox = "im.deleteBox";		//名片夹删除了
Constants.command.im.desktop.updateCard = "im.updateCard";		//更新名片
Constants.command.im.desktop.dragCardToBox = "im.dragCardToBox";		//将名片拖入名片夹
Constants.command.im.desktop.removeCardFromBox = "im.removeCardFromBox";		//将名片从某个名片夹删除
Constants.command.im.desktop.setAllOffline = "im.setAllOffline";		//假离线，全部变灰
Constants.command.im.desktop.clearTopicUnreadNum = "im.clearTopicUnreadNum";		//清除讨论组未读消息数

Constants.command.im.command = {};
Constants.command.im.command.drawGraph = "im.command.drawGraph";
Constants.command.im.command.sendDrawGraph = "send.im.command.drawGraph";
Constants.command.im.command.initImg = "im.command.initImg";
Constants.command.im.command.importBackImg = "im.command.importBackImg";	//导入背景图片
Constants.command.im.command.imStatusInit = "im.command.imStatusInit";	//设置IM状态

//im错误状态码列表
Constants.command.im.error.CODE_USER_NOT_FOUND = 1001;		//用户不存在
Constants.command.im.error.CODE_NO_LINK_RELATION = 1002;	//不是连接关系
Constants.command.im.error.CODE_LINK_PRIVATE_CHAT_NOT_ALLOW_SELF = 1003;			//自己设置了连接不允许私聊
Constants.command.im.error.CODE_LINK_PRIVATE_CHAT_NOT_ALLOW_OTHER = 1004;			//对方设置了连接不允许私聊
Constants.command.im.error.CODE_LINK_PRIVATE_CHAT_NOT_ALLOW_ORG_SELF = 1005;		//自己的企业不允许跟企业外的人私聊
Constants.command.im.error.CODE_LINK_PRIVATE_CHAT_NOT_ALLOW_ORG_OTHER = 1006;		//对方的企业不允许跟企业外的人私聊
Constants.command.im.error.CODE_MEMBER_PRIVATE_CHAT_NOT_ALLOW_SELF = 1007;		//自己不允许跟企业的成员私聊
Constants.command.im.error.CODE_MEMBER_PRIVATE_CHAT_NOT_ALLOW_OTHER = 1008;		//对方不允许跟企业的成员私聊
Constants.command.im.error.CODE_ME_IN_OTHERS_BLACK_LIST = 1009;		//我在对方黑名单
Constants.command.im.error.CODE_OTHER_IN_MY_BLACK_LIST = 1010;		//对方在我黑名单

//各类im tip常量
Constants.command.im.desktop.tip = {};
Constants.command.im.desktop.tip.FIND_PERSON_TIP = 0;       //找人链接的tip

if(!Constants.im) Constants.im = {};
if(!Constants.im.cons) Constants.im.cons = {};
if(!Constants.im.cons.presence) Constants.im.cons.presence = {};
Constants.im.cons.presence.type = {};
Constants.im.cons.presence.type.available = 'available';
Constants.im.cons.presence.type.unavailable = 'unavailable';
 
Constants.im.cons.presence.status = {};
Constants.im.cons.presence.status.offline = "offline";
Constants.im.cons.presence.status.online = "online";
Constants.im.cons.presence.status.away = "away";
Constants.im.cons.presence.status.busy = "busy";
Constants.im.cons.presence.status.dnd = "dnd";      //忙碌
Constants.im.cons.presence.status.hiding = "hiding";    //隐身
Constants.im.cons.presence.status.offlineHeader = "offlineHeader";		//对header view 来说，clazz有所不同
Constants.im.cons.presence.status.onlineHeader = "onlineHeader";		//对header view 来说，clazz有所不同

Constants.im.cons.presence.status.clazz = {
	onlineHeader : 'osStsOnline',
    offlineHeader : 'osStsOffline',
	offline : '',
    online : '',
    away : 'osStsAfk',
    busy : 'osStsBusy',
    dnd : 'osStsDND',
    hiding : 'osStsHiding'
};
Constants.im.cons.presence.status.text = {
	onlineHeader : '我在线上',
    offlineHeader : '离线',
    offline : '离线',
    online : '我在线上',
    away : '离开',
    busy : '忙碌',
    dnd : '请勿打扰',
    hiding : '隐身'
};

if(typeof com == 'undefined') com = {};
if(!com.gleasy) com.gleasy = {};
if(!com.gleasy.im) com.gleasy.im = {};
if(!com.gleasy.im.util) com.gleasy.im.util = {};
if(!com.gleasy.im.util.osImWedget) com.gleasy.im.util.osImWedget = {};
if(!com.gleasy.im.util.osImWedget.view) com.gleasy.im.util.osImWedget.view = {};
if(!com.gleasy.im.util.osImWedget.view.status) com.gleasy.im.util.osImWedget.view.status = {};

com.gleasy.im.util.osImWedget.view.status.topicGroupType = {
    system : 'system',
    custom : 'custom'
}
com.gleasy.im.util.osImWedget.view.status.topicGroupSystemValue = {
    active : 1,
    all : 2,
    star : 3,
    rubbish : 6
}


Constants.command.cloudDisk = {};
Constants.command.cloudDisk.fupload = {};
Constants.command.cloudDisk.fupload.browse = "fupload.browse";
Constants.command.cloudDisk.fupload.select = "fupload.select";
Constants.command.cloudDisk.fupload.error = "fupload.error";
Constants.command.cloudDisk.fupload.localload = "fupload.localload";
Constants.command.cloudDisk.fupload.upload = "fupload.upload";
Constants.command.cloudDisk.fupload.cancel = "fupload.cancel";
Constants.command.cloudDisk.fupload.progress = "fupload.progress";
Constants.command.cloudDisk.fupload.uploadok = "fupload.uploadok";
Constants.command.cloudDisk.fupload.mouseOver = "fupload.mouseOver";
Constants.command.cloudDisk.fupload.mouseOut = "fupload.mouseOut";
Constants.command.cloudDisk.fupload.mouseDown = "fupload.mouseDown";
Constants.command.cloudDisk.fupload.mouseUp = "fupload.mouseUp";
Constants.command.cloudDisk.fupload.alert = "fupload.alert";

Constants.command.cloudDisk.refresh = "cloudDisk.refresh";
Constants.command.cloudDisk.openMain = "cloudDisk.openMain";
Constants.command.cloudDisk.openUpload = "cloudDisk.openUpload";

Constants.command.gajax = {};
Constants.command.gajax.post = "gajax.post";
Constants.command.gajax.get = "gajax.get";
Constants.command.gajax.error = "gajax.error";
Constants.command.gajax.cancel = "gajax.cancel";
Constants.command.gajax.success = "gajax.success";
Constants.command.gajax.ready = "gajax.ready";
Constants.command.gajax.log = "gajax.log";

Constants.command.avatar = {};
Constants.command.avatar.capture = "avatar.capture";
Constants.command.avatar.reset = "avatar.reset";
Constants.command.avatar.success = "avatar.success";
Constants.command.avatar.error = "avatar.error";
Constants.command.avatar.ready = "avatar.ready";

Constants.command.os.clipboard = {};
Constants.command.os.clipboard.set = "os.clipboard.set";
Constants.command.os.clipboard.get = "os.clipboard.get";

Constants.command.gprint = {};
Constants.command.gprint.print = "gprint.print";
Constants.command.gprint.finish = "gprint.finish";
Constants.command.gprint.progress = "gprint.progress";


Constants.command.person = {};

Constants.command.person.card = {};
Constants.command.person.card.create = "person.viewer.card.create";
Constants.command.person.card.update = "person.viewer.card.update";
Constants.command.person.card.remove = "person.viewer.card.remove";
Constants.command.person.card.avatar = {};
Constants.command.person.card.avatar.update = "person.viewer.card.avatar.update";
Constants.command.person.card.moveToblack = 'person.card.move.to.black';
Constants.command.person.box = {};
Constants.command.person.box.removeCard = 'person.box.remove.card';
Constants.command.person.box.create = "person.box.create";
Constants.command.person.box.update = "person.box.update";
Constants.command.person.box.remove = "person.box.remove";
Constants.command.person.box.addcard = 'person.box.add.card';

Constants.command.person.box.addConnent = "person.box.addConnect";
Constants.command.person.box.removeConnect = "person.box.removeConnect";
Constants.command.person.connection = {};
Constants.command.person.connection.accept = 'person.connection.accpet';
Constants.command.person.connection.disconnect = 'person.connection.disconnect';

Constants.command.person.view = {};
Constants.command.person.view.homepage = 'openHomepage';
Constants.command.person.view.cardstorage = 'openCardStorage';
Constants.command.person.view.findPerson = 'openFindPerson';
Constants.command.person.view.merge = "openMerge";


Constants.command.cloudBrowser = {};
Constants.command.cloudBrowser.tabbar = {};
Constants.command.cloudBrowser.toolbar = {};
Constants.command.cloudBrowser.tipbar = {};
Constants.command.cloudBrowser.cookie = {};
Constants.command.cloudBrowser.history = {};
Constants.command.cloudBrowser.MostRecent = {};
Constants.command.cloudBrowser.bookmark = {};
Constants.command.cloudBrowser.download = {};
Constants.command.cloudBrowser.famousSite = {};
Constants.command.cloudBrowser.favouriteSite = {};

Constants.command.cloudBrowser.tabbar.setTabTitle = "cloudBrowser.tabbar.setTabTitle";	//设置tab标题
Constants.command.cloudBrowser.tabbar.setTabUrl = "cloudBrowser.tabbar.setTabUrl";	//设置tab url
Constants.command.cloudBrowser.tabbar.setTabIcon = "cloudBrowser.tabbar.setTabIcon";	//设置tab icon
Constants.command.cloudBrowser.tabbar.newTabAdded = "cloudBrowser.tabbar.newTabAdded";	//打开了新选项卡
Constants.command.cloudBrowser.tabbar.updateClosedTabList = "cloudBrowser.tabbar.updateClosedTabList";	//更新打开过的tab列表，用于以后撤销
Constants.command.cloudBrowser.tabbar.recoverTab = "cloudBrowser.tabbar.recoverTab";	//撤销一个标签
Constants.command.cloudBrowser.tabbar.recoverTabInView = "cloudBrowser.tabbar.recoverTabInView";		//在view中recover选项卡
Constants.command.cloudBrowser.tabbar.showTab = "cloudBrowser.tabbar.showTab";		//使某个tab获取焦点

Constants.command.cloudBrowser.tabbar.changeTab = "cloudBrowser.tabbar.changeTab";		//换一个选项卡（目前用在web一览）

Constants.command.cloudBrowser.toolbar.emptyAddress = "cloudBrowser.toolbar.emptyAddress";	//清空地址栏
Constants.command.cloudBrowser.toolbar.updateAddress = "cloudBrowser.toolbar.updateAddress";	//更新地址栏
Constants.command.cloudBrowser.toolbar.updateProgress = "cloudBrowser.toolbar.updateProgress";	//更新网页打开进度
Constants.command.cloudBrowser.toolbar.addNewTab = "cloudBrowser.toolbar.addNewTab";	//新增一个tab

Constants.command.cloudBrowser.tipbar.addDownload = "cloudBrowser.tipbar.addDownload";	//新增下载

Constants.command.cloudBrowser.cookie.cookiePageShown = "cloudBrowser.cookie.cookiePageShown";		//cookie管理页面打开了
Constants.command.cloudBrowser.cookie.loadCookies = "cloudBrowser.cookie.loadCookies";		//读取cookies
Constants.command.cloudBrowser.cookie.searchCookies = "cloudBrowser.cookie.searchCookies";		//搜索cookies
Constants.command.cloudBrowser.cookie.searchCookiesOK = "cloudBrowser.cookie.searchCookiesOK";		//搜索cookies OK
Constants.command.cloudBrowser.cookie.saveCookies = "cloudBrowser.cookie.saveCookies";		//保存cookies
Constants.command.cloudBrowser.cookie.deleteAllCookies = "cloudBrowser.cookie.deleteAllCookies";		//删除所有cookies
Constants.command.cloudBrowser.cookie.deleteSomeCookies = "cloudBrowser.cookie.deleteSomeCookies";		//删除一些cookies
Constants.command.cloudBrowser.cookie.loadCookiesForManage = "cloudBrowser.cookie.loadCookiesForManage";		//读取cookies,管理页面
Constants.command.cloudBrowser.cookie.loadCookiesForManageOK = "cloudBrowser.cookie.loadCookiesForManageOK";		//读取cookies,管理页面

Constants.command.cloudBrowser.bookmark.bookmarkPageShown = "cloudBrowser.bookmark.bookmarkPageShown";		//bookmark管理页面打开了
Constants.command.cloudBrowser.bookmark.loadBookmarks = "cloudBrowser.bookmark.loadBookmarks";		//读取bookmark列表
Constants.command.cloudBrowser.bookmark.loadBookmarkRelations = "cloudBrowser.bookmark.loadBookmarkRelations";		//读取bookmark目录列表
Constants.command.cloudBrowser.bookmark.loadBookmarksOK = "cloudBrowser.bookmark.loadBookmarksOK";	//读取bookmark列表ok
Constants.command.cloudBrowser.bookmark.loadBookmarkRelationsOK = "cloudBrowser.bookmark.loadBookmarkRelationsOK";		//读取bookmark目录列表ok

Constants.command.cloudBrowser.history.loadHistories = "cloudBrowser.history.loadHistories";		//读取历史记录
Constants.command.cloudBrowser.history.loadHistoriesOK = "cloudBrowser.history.loadHistoriesOK";		//读取历史记录成功
Constants.command.cloudBrowser.history.searchHistories = "cloudBrowser.history.searchHistories";		//搜索历史记录
Constants.command.cloudBrowser.history.searchHistoriesOK = "cloudBrowser.history.searchHistoriesOK";		//搜索历史记录成功
Constants.command.cloudBrowser.history.saveHistories = "cloudBrowser.history.saveHistories";		//保存历史记录
//（只是更新浏览器中的缓存对象，真正与后端交互的是通过js定时调度器 CloudBrowserRefresh.js;注:MostRecentManageModel也用到这个缓存对象）
Constants.command.cloudBrowser.history.deleteHistories = "cloudBrowser.history.deleteHistories";		//保存历史记录

Constants.command.cloudBrowser.MostRecent.loadMostRecents = "cloudBrowser.MostRecent.loadMostRecents";//读取最常访问页面（8个）
Constants.command.cloudBrowser.MostRecent.loadMostRecentsOK = "cloudBrowser.MostRecent.loadMostRecentsOK";//读取最常访问页面（8个）成功
Constants.command.cloudBrowser.MostRecent.deleteMostRecent = "cloudBrowser.MostRecent.deleteMostRecent";//删除一个最常访问页面
Constants.command.cloudBrowser.MostRecent.fixMostRecent = "cloudBrowser.MostRecent.fixMostRecent";//固定一个最常访问页面
Constants.command.cloudBrowser.MostRecent.saveMostRecents = "cloudBrowser.MostRecent.saveMostRecents";//保存最常访问记录

Constants.command.cloudBrowser.download.queryDownloadList = "cloudBrowser.download.queryDownloadList";		//查询下载列表
Constants.command.cloudBrowser.download.renderDownloadList = "cloudBrowser.download.renderDownloadList";		//绘制下载列表
Constants.command.cloudBrowser.download.showDownloadManageView = "cloudBrowser.download.showDownloadManageView";		//显示下载列表管理页
Constants.command.cloudBrowser.download.addFileQueryTask = "cloudBrowser.download.addFileQueryTask";		//增加文件查询task
Constants.command.cloudBrowser.download.fileQueryTaskAdded = "cloudBrowser.download.fileQueryTaskAdded";		//增加了文件查询task
Constants.command.cloudBrowser.download.fileQueryResult = "cloudBrowser.download.fileQueryResult";		//文件查询了一次...

Constants.command.cloudBrowser.famousSite.loadFamousSites = "cloudBrowser.famousSite.loadFamousSites";		//读取分类下的名站列表
Constants.command.cloudBrowser.famousSite.famousSitesLoaded = "cloudBrowser.famousSite.famousSitesLoaded";		//分类下的名站列表获取成功
Constants.command.cloudBrowser.famousSite.loadFamousSiteCategories = "cloudBrowser.famousSite.loadFamousSiteCategories";		//读取所有名站分类
Constants.command.cloudBrowser.famousSite.famousSiteCategoriesLoaded = "cloudBrowser.famousSite.famousSiteCategoriesLoaded";		//所有名站分类读取成功

Constants.command.cloudBrowser.favouriteSite.pageQueryFavouriteSites = "cloudBrowser.favouriteSite.pageQueryFavouriteSites";		//分页查询自选网站
Constants.command.cloudBrowser.favouriteSite.favouriteSitesDataAvailable = "cloudBrowser.favouriteSite.favouriteSitesDataAvailable";		//自选网站数据到达
Constants.command.cloudBrowser.favouriteSite.saveFavouriteSite = "cloudBrowser.favouriteSite.saveFavouriteSite";		//请求保存自选网站
Constants.command.cloudBrowser.favouriteSite.favouriteSiteAdded = "cloudBrowser.favouriteSite.favouriteSiteAdded";			//自选网站添加成功了
Constants.command.cloudBrowser.favouriteSite.favouriteSiteUpdated = "cloudBrowser.favouriteSite.favouriteSiteUpdated";		//自选网站更新成功了
Constants.command.cloudBrowser.favouriteSite.favouriteSiteRemoved = "cloudBrowser.favouriteSite.favouriteSiteRemoved";		//自选网站删除成功了
Constants.command.cloudBrowser.favouriteSite.iconUploaded = "cloudBrowser.favouriteSite.iconUploaded";		//自定义自选网站上传了图标
Constants.command.cloudBrowser.favouriteSite.requestEdit = "cloudBrowser.favouriteSite.requestEdit";		//编辑自定义item

Constants.command.notes = {};
Constants.command.notes.postMessage = {};
Constants.command.notes.postMessage.create = 'notes.postmessage.create';//主程序新建了便签
Constants.command.notes.postMessage.update = 'notes.postmessage.update';
Constants.command.notes.postMessage.remove = 'notes.postmessage.remove';
Constants.command.notes.postMessage.widget = {};
Constants.command.notes.postMessage.widget.create = 'notes.postmessage.widget.create';//widget新建了便签
Constants.command.notes.postMessage.widget.update = 'notes.postmessage.widget.update';
Constants.command.notes.postMessage.clickoutside = 'notes.postmessage.clickoutside';



Constants.command.gdoc = {}
Constants.command.gdoc.drapFiles = "Constants.command.gdoc.drapFiles";//拖拽文件到写写
Constants.command.gdoc.initOK = "Constants.command.gdoc.initOK";








