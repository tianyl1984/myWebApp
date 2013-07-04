$(function(){
	/*
	//'<div style = "position:absolute;top:0;right:0;bottom:0;left:0;overflow:hidden;">'+
	var content = '<div class="npTextareaWrap" style="left:3px;position:absolute;top:0;right:0;bottom:0;overflow:hidden;">'+
			    	'<textarea id="edit_area" name="content" class="npTextarea" style="width:100%;height:100%;position:absolute;top:0;left:0;right:0;bottom:0;padding:0;margin:0;outline:none;border:none;resize:none;overflow-y:auto;border:0;"></textarea>'+
			        '</div>';//+
			    	//'</div>';
			    	*/
	//var content = '<div class="npWrap" style="width:100%;height:100%;">'+
		//'<div class="">'+
		var content ='<textarea id="edit_area" name="content" class="npTextarea" spellcheck = "false" style="width:100%;height:100%;padding:0;margin:0;outline:none;border:none;resize:none;border:0;overflow:auto;"></textarea>';
	var table = '<table cellpadding="0" cellspacing="0" border="0" style="width:100%;height:100%;table-layout:fixed"><tr>' 
				+' <td style ="width:3px;">&nbsp;</td><td id = "innerTd">' + content +'</td>' 
				+' </tr></table>'
	var css = 'body,	ul,	li,	h3,	p,	dl,	dd,	dt,	h4,	input,	button,	h2,	h5{	padding:0;	margin:0;	} body{	font-size:12px;	color:#333;	background-position:center	center;	overflow:hidden;	position:relative;	} body,	html{	height:100%;	width:100%;	padding-bottom:0;	} html{	overflow:hidden;	} ul,	li{	list-style:none;	} a{	text-decoration:none;	color:#0075b8;	cursor:pointer;	} a:hover{	text-decoration:none;	} h3,	h2,	h4{	font-size:12px;	} img{	border:none;	} s{	font-style:normal;	text-decoration:none;	} :focus{	outline:none;	} .clear{	clear:both;	} .fl{	float:left;	} .fr{	float:right;	} .npFileBtn,	.npFileBtnSelected,	.npBack:hover,	.npSave:hover,	.npCut:hover,	.npCopy:hover,	.npPaste:hover,	.npFind:hover,	.npReplace:hover,	.npAll:hover,	.npBack:active,	.npSave:active,	.npCut:active,	.npCopy:active,	.npPaste:active,	.npFind:active,	.npReplace:active,	.npAll:active{	background:url(images/btnBg.png)	repeat-x;	} .npWrap,	.npContentWrap{	position:absolute;	top:0;	right:0;	bottom:0;	left:0;	} .npWrap{	overflow:hidden;	} .npToolBar{	height:30px;	border-bottom:1px	solid	#acb2b5;	background:#edeeef;	background:-moz-linear-gradient(#f5f4f4,	#e6e8e9);	background:-webkit-linear-gradient(#f5f4f4,	#e6e8e9);	background:-o-linear-gradient(#f5f4f4,	#e6e8e9);	overflow:hidden;	} .npIco{	background:url(images/ico.png)	no-repeat;	} .npFileBtn{	color:#fff;	line-height:29px;	height:30px;	width:50px;	padding-left:16px;	display:block;	text-shadow:0	-1px	0	#4078ab;	font-weight:bold;	background-position:0	-26px;	} .npFileBtn:hover{	color:#fff;	} .npFileBtnSelected{	background-position:0	-61px;	} .npFileBtnSelected1{	border-top:1px	solid	#9dcdfb;	background:#54a5f1;	background:-moz-linear-gradient(#74b7f7,	#5aa5ec,	#4a9eee,	#4a9eee);	background:-webkit-linear-gradient(#74b7f7,	#5aa5ec,	#4a9eee,	#4a9eee);	background:-o-linear-gradient(#74b7f7,	#5aa5ec,	#4a9eee,	#4a9eee);	} .npFileMenu{	width:94px;	border:1px	solid	#c2c4c4;	padding:6px	0;	background:#f0f0f0;	position:absolute;	top:32px;	left:1px;	display:none;	z-index:9;	box-shadow:1px	2px	3px	#c6c6c6,	0	1px	0	#fff	inset;	} .npFileMenuList{	color:#505867;	text-shadow:0	1px	#fff;	line-height:22px;	margin-top:4px;	padding:0	12px;	cursor:pointer;	} .npFileMenuList:hover{	background:#d7e0e8;	} .npFileMenuKey{	color:#858687;	text-shadow:0	1px	#fff;	display:none;	} .npFileArr{	height:5px;	width:7px;	margin:13px	12px	0	0;	display:block;	background-position:-47px	-13px;	} .npToolListWrap{	height:25px;	padding-top:5px;	} .npToolList{	height:20px;	width:26px;	margin-left:10px;	display:block;	} .npBack,	.npSave,	.npCut,	.npCopy,	.npPaste,	.npFind,	.npReplace,	.npAll,	.npBackIco,	.npSaveIco,	.npCutIco,	.npCopyIco,	.npPasteIco,	.npFindIco,	.npReplaceIco,	.npAllIco{	height:20px;	width:26px;	display:block;	position:relative;	border-radius:3px;	} .npBack:hover,	.npSave:hover,	.npCut:hover,	.npCopy:hover,	.npPaste:hover,	.npFind:hover,	.npReplace:hover,	.npAll:hover{	border:1px	solid	#c0c8d0;	top:-1px;	left:-1px;	background-position:0	-121px;	box-shadow:0	1px	0	#fafafa;	} .npBack:active,	.npSave:active,	.npCut:active,	.npCopy:active,	.npPaste:active,	.npFind:active,	.npReplace:active,	.npAll:active{	background-position:0	-96px;	box-shadow:0	1px	0	#fafafa;	} .npDisableBtn,	.npDisableBtn .npIco{	cursor:default;	opacity:0.5;	filter:alpha(opacity	=	50);	} .npDisableBtn:hover,	.npDisableBtn:active{	background:none;	border:none;	top:0;	left:0;	box-shadow:none;	} .npBackIco{	background-position:-75px	-5px;	} .npSaveIco{	background-position:-111px	-5px;	} .npCutIco{	background-position:-144px	-5px;	} .npCopyIco{	background-position:-175px	-5px;	} .npPasteIco{	background-position:-208px	-5px;	} .npFindIco{	background-position:-239px	-5px;	} .npReplaceIco{	background-position:-268px	-5px;	} .npAllIco{	background-position:-298px	-5px;	} .npToolLine{	height:30px;	width:2px;	margin:0	14px;	display:block;	background-position:-338px	-0px;	} .npLineFeed{	color:#505867;	height:30px;	line-height:30px;	padding-left:20px;	display:block;	background-position:-352px	7px;	} .npLineFeed:hover{	color:#505867;	} .npLineFeedNoSelected{	background-position:-352px	-17px;	} .npWrapTxt{	padding:24px	18px;	} .npContentWrap{	top:31px;	overflow:auto;	} .npContentNoLineFeed{	white-space:nowrap;	} .npContentLineFeed{	white-space:normal;	word-break:break-all;	} .npTextarea{	width:100%;	height:100%;	padding:0;	margin:0;	outline:none;	border:none;	resize:none;	} .npTextareaWrap{	position:absolute;	top:31px;	left:0;	right:0;	bottom:0;	overflow:hidden;	} .npReplaceBoxWrap,	.npSearchBoxWrap{	width:375px;	z-index:9;	position:absolute;	top:64px;	left:208px;	box-shadow:0	0;	display:none;	} .npSearchBoxWrap{	height:145px;	} .npReplaceBoxWrap{	height:160px;	} .npPopupBox{	left:0;	right:0;	top:0;	bottom:0;	background:#edf1f3;	position:absolute;	overflow:hidden;	} .npPopupContent{	position:absolute;	top:28px;	left:36px;	right:18px;	bottom:25px;	color:#585757;	} .npPopupInput{	width:180px;	height:21px;	line-height:21px;	margin-left:6px;	border:1px	solid	#9ea6b7;	font-size:12px;	box-shadow:0	1px	0	#f9fafb;	position:absolute;	left:48px;	} .npPopupBtn{	cursor:pointer;	height:23px;	line-height:16px;	width:78px;	border:1px	solid	#bdbdbd;	margin-left:9px;	background:url(images/btnBg.png)	repeat-x	0	-146px;	color:#677f9b;	position:absolute;	right:1px;	border-radius:3px;	} .npPopupBtn:hover{	background-position:0	-198px;	box-shadow:1px	0	0	#fcfcfc	inset,	-1px	0	0	#fbfbfb	inset;	} .npPopupBtn:active{	background-position:0	-172px;	box-shadow:2px	0	3px	#bdbdbd	inset,	1px	0	0	#d4d5d6;	} .npPopupList{	height:23px;	margin-bottom:11px;	position:relative;	} .npPopupInputLabel{	cursor:default;	line-height:23px;	} .npMatchCase{	cursor:pointer;	height:16px;	line-height:16px;	margin:3px	16px	0	0;	display:block;	padding-left:22px;	background-position:-352px	0;	} .npMatchCaseNoSelected{	background-position:-352px	-24px;	} .npSearchDirWrp{	padding-top:10px;	} .npSearchDir{	height:16px;	margin:3px	0	0	10px;	padding-left:20px;	background-position:0	0;	cursor:pointer;	} .npSearchDirSelected{	background-position:0	-16px;	} .npWrap .oWindowBtnBar{	margin-top:2px;	} .npWrap .pButton{	cursor:pointer;	} .npTextarea,	.npWrapTxt{	border:0;	overflow:auto;	} .isFindReplaceWrp{	z-index:99999;	height:246px;	width:356px;	} .isFindReplaceMain{	padding:27px;	background-color:#EDF1F3;	} .isFindReplace_row{	margin-bottom:22px;	} .isFindReplace_label{	color:#657384;	font-size:13px;	line-height:23px;	} .isFindReplaceMain .isInsCol_ipt{	height:21px;	border:1px	solid	#b6bbc2;	background:#fcfcfc;	box-shadow:0	1px	0	#f9fafb,	0	1px	0	#f2f2f2	inset;	margin-left:10px;	width:242px;	} .isFindReplaceBtnWrp{	padding-left:40px;	margin-bottom:14px;	} .isFindReplaceBtn,	.isDlogBtn{	cursor:pointer;	color:#606060;	width:77px;	height:25px;	vertical-align:middle;	margin-right:8px;	border:1px	solid	#a9a8a9;	border-color:#b1b1b1	#acadad	#9c9a9a	#acadad;	border-radius:5px;	box-shadow:0	1px	1px	#cacdcf,	1px	0	0	#e3e7e9,	-1px	0	0	#e3e7e9;	} .isFindReplaceBtn,	.isFindReplaceBtn_disable{	background:url(images/bg.png)	repeat-x;	} .isFindReplaceBtn{	background-position:0	-531px;	padding-bottom:2px;	} .isFindReplaceBtn:hover{	background-position:0	-562px;	} .isFindReplaceBtn:active{	background-position:0	-592px;	} .isFindReplaceBtn_disable,	.isFindReplaceBtn_disable:hover,	.isFindReplaceBtn_disable:active{	cursor:default;	color:#b1b0b0;	text-shadow:0	1px	0	#fff;	background-position:0	-623px;	}';
	var incss = '<style type="text/css">' + css + '</style>';
	var header = '<link rel="stylesheet" type="text/css" href="http://asset.gleasy.com/platform/os/assets/stylesheets/reset.css" />'
				+'<link rel="stylesheet" type="text/css" href="http://asset.gleasy.com/platform/os/assets/stylesheets/os/os-style.css" media="all" />'
				+'<link rel="stylesheet" type="text/css" href="http://asset.gleasy.com/platform/os/assets/stylesheets/os-desktop.css" media="all" />';
	var link =  '<link rel="stylesheet" type="text/css" href="http://asset.gleasy.com/platform/os/assets/javascripts/softwares/gnotepad/notepad/style/notepad.css" media="all" />';
				;//+ incss;
	if($.browser.msie && $.browser.version < 9){
		header += link;
	}else{
		header += incss;
	}
	$('#if').contents().find('head').append(header);
	var iframeBody = $('#if').contents().find("body").css({"margin":"0px","padding":"0px"}).append(table);  
	var view = iframeBody.find("#edit_area");
	var searchInput = $("#notepadSearchStr");
	
	//点击其他位置则隐藏菜单
	iframeBody.click(function(){
		$('.npFileMenu').css('display','none');
		$(".npFileBtn").removeClass('npFileBtnSelected');
	});
	
	var supportedExts = [
	'.txt','.js','.properties','.htm','.html','.css','.php','.java','.c','.h','.cpp','.as','.log','.csv'
	];
	
	var maskInterval = null;
	var currentFileID = null;
	var currentFileName = null; 
	var clipboardData4notepad = "";
	var currentUser = null;
	var myAppName = "记事本";
	var myLockKey = UuidGenerator.uuid("gs");
	var isLock = false;
	var locker = "";
	var hasSaved = true;
	var isCookieOk = true;
	
	var isSkipLock = false;
	var lockType = -1;
	var readOnlyMsg = "此文件为只读,";
	var editingMsg = "此文件正在被编辑,";
	var cancelShareMsg = "此文件已被所有者取消共享，";
			
	var isDebug = false;//true for wlan, false for intractnet
	var oldPrompt = false// true for wlan, false for intractnet
	var isIE9 = false;
	if($.browser.msie && $.browser.version >= 9){
		isIE9 = true;
	}
	
	var exitAfterSave = false;
	var autoCheckLockTime = 25000;
	var autoSaveTime = 60000;
	
	var text = view, startValue = text.val(), timer;
	text.bind('contextmenu', function(e){
		return false;
	});
	var setViewParentLeft = function(){
		if(document.selection){
			if($.browser.msie && $.browser.version < 9){
				//view.parent().css("left", "0px").css("width", "100%");
			}
		}
	};
	
	var setEnableSave = function(flag){
		if(flag){
			$("#notepadSave, #notepadMenuSave").removeClass("npDisableBtn");
		}else{
			$("#notepadSave, #notepadMenuSave").addClass("npDisableBtn");
		}
	};
	
	var textWidth = function(text){
		var sensor = $('<div>'+ text +'</div>').css({'display': 'none', 'font-size':'12px', 'font-family':'\5B8B\4F53', 'font-weight':'bold'});
		$('body').append(sensor);
		var width = sensor.width();
		sensor.remove();
		return width;
	};
	var formatTitle = function(str, maxWidth){//输入的字符,允许的最大宽度
		var returnValue = str;
		
		if(textWidth(str) > maxWidth){//first check
			var oneDotWidth = textWidth('.');
			var fillDot = '...';
				
			var len = str.length;
			var firstHalfNum = Math.round(len/2);
			var firstHalf = str.substring(0, firstHalfNum);
			var calcWidth = textWidth(firstHalf + fillDot);
			if(calcWidth > maxWidth){
				var tmpStr = '';
				var tmpWidth = 0;
				for(var i = firstHalfNum; i >= 0; i--){
					tmpStr = firstHalf.substring(0, i) + fillDot;
					tmpWidth = textWidth(tmpStr);
					if(tmpWidth <= maxWidth){
						break;
					}
				}
				if(tmpStr == fillDot){
					var firstCharWidth = textWidth(firstHalf.substring(0,1));
					tmpStr = firstHalf.substring(0,1);
					var j;
					for( j = 2; j >= 1; j--){
						if(firstCharWidth + j*oneDotWidth <= maxWidth){
							break;
						}
					}
					for(var i = j; i > 0; i--){
						tmpStr += '.';
					}
					
				}
				returnValue = tmpStr;
			}else if(calcWidth < maxWidth){
				var tmpStr = '';
				for(var i = firstHalfNum; i <= len; i++){
					tmpStr = firstHalf + str.substring(firstHalfNum, i + 1) + fillDot;
					if(textWidth(tmpStr)> maxWidth){
						break;
					}
				}
				returnValue = tmpStr.substring(0, tmpStr.length - 4) + fillDot;
			}else{
				returnValue = firstHalf + fillDot;
			}
		}
		return returnValue;
	}
	
	var setGWindowTitle = function(titleName){
		if(!isDebug && isLock){
			titleName = '(只读)' + titleName;
		}
		titleName = formatTitle(titleName, 410);
		
		gleasy.window.setTitle({title:titleName});
	};
	var setGWindowTitleForAuto = function(titleName){
		if(!isDebug && isLock){
			titleName = '(只读)' + titleName;
		}
		titleName = formatTitle(titleName, 410);
		
		//gleasy.window.setTitle({title:titleName});
		gleasy.system.noticeListener({
			event:'autoSetTitle',
			message:{title:titleName}
		});	
	};
	
	var promptLockBeforeSave = function(){
		if(!isDebug && isLock){
			var msg = "此文件为只读/正在被编辑，";
			if(lockType == 12){
				msg = readOnlyMsg;
			}else if(lockType == 13){
				msg = editingMsg;
			}else if(lockType == 11){
				msg = cancelShareMsg;
			}
			gleasy.system.confirm({
				message:msg,
			    confirmMessage:"您编辑文件后不能保存，<p>只能另存。", 
			    system:false,//是否系统提醒
			    title:"提示",
			    icon:"prompt",//icon={confirm,fail,success,prompt,warning}
			    okBtn:true,
			    okBtnText:"另存为",
			    cancelBtn:true,
			    cancelBtnText:"知道了",
				callback:function(dt){
					if(dt.result){
						saveasfun();
					}
				}
			});
		}
	};

	//打开一个txt文件
	var getFileCallback = function(data, name){
		view.val(data);
		startValue = text.val();
		if(!isIE9)
			gleasy.window.showLoading({show:false});
		//gleasy.window.setTitle({title:name});
		setGWindowTitle(name);
		
		view.gleasySelectPos(0, 0, searchInput);
		
		if(!isDebug && isLock){
			$("#notepadSave, #notepadMenuSave").addClass("npDisableBtn");
			if(!$.cookie('gsheet_test')){
				$.cookie('gsheet_test', 'testcookie=yes;', { expires: 1});
				if($.cookie('gsheet_test').indexOf('testcookie=yes') <= -1){
					isCookieOk = false;
				}
			}
			if(isCookieOk){
				if($.cookie('gsheet_not_prompt_'+currentFileID) == 'yes'){
					isSkipLock = true;
				}
			}
		}		
		$("#notepadSave, #notepadMenuSave").addClass("npDisableBtn");
		if(!isDebug && !isSkipLock && isLock && lockType != 12){
			
			var msg = "此文件为只读/正在被编辑，";
			if(lockType == 12){
				msg = readOnlyMsg;
			}else if(lockType == 13){
				msg = editingMsg;
			}else if(lockType == 11){
				msg = cancelShareMsg;
			}
			gleasy.system.confirm({
				message:msg,
			    confirmMessage:"您编辑文件后不能保存，<p>只能另存。", 
			    system:false,//是否系统提醒
			    title:"提示",
			    icon:"prompt",//icon={confirm,fail,success,prompt,warning}
			    okBtn:false,
			    //okBtnText:"继续往前",
			    cancelBtn:true,
			    cancelBtnText:"知道了",
			    checkBtn:false,//isCookieOk,
			    checkBtnText:"不再提示",
				callback:function(dt){
					view.focus();
					setViewParentLeft();
					if(dt.check){
						if(isCookieOk){
							$.cookie('gsheet_not_prompt_'+currentFileID, 'yes', { expires: 7});
							isSkipLock = true;
						}
					}
				}
			});
		}else{
			view.focus();
			setViewParentLeft();
		}
	};
	var prepareContent = function(fileObj){
		gleasy.system.noticeListener({
			event:'Constants.command.notepad.saveNew',
			message:{fid:fileObj.id}
		});	
		//if(fileObj.size > 0){
			gleasy.system.sendMessageToProcess({
				pname:"office转换器",
				eventId:'decodeFile',
				messageBody:{
					fid:fileObj.id,
					success: function(htmlId){
						getFileCallback(htmlId, fileObj.name);
					},
					error:function(result){
						getFileCallback("", fileObj.name);
					}
				}
			});	
			
		//}else{
			//getFileCallback("", fileObj.name);
			//hasSaved = true;
		//}
	};
	var autoSave = function(){
		if(isLock || hasSaved) return;
		saveFile(true);
	};
	var autoCheckLock = function(){
		if(currentFileID){
			var params = {
					fid:currentFileID,
					appName:myAppName,
					//appSign:"应用签名",--暂时可不传
					lockKey:myLockKey,
					success:function(data){
						//alert(data.file);
						isLock = false;
						//$("#notepadSave, #notepadMenuSave").removeClass("npDisableBtn");
						setGWindowTitleForAuto(currentFileName);
					},
					error:function(data){
						//alert(data.status);
						isLock = true;
						lockType = data.status;
						locker = "Gleasy";
						$("#notepadSave, #notepadMenuSave").addClass("npDisableBtn");
						setGWindowTitleForAuto(currentFileName);
						//unlockFile();//not work since has no right
					}
				}
			gleasy.system.sendMessageToProcess({
					pname:"一盘守护进程",
					eventId:"lockFileByApp",
					messageBody:params
				});
		}
	};
	gleasy.system.addEventListener({
		event:'Constants.command.notepad.reCheckLock',
		callback:function(param){
			if(currentFileID && param.fid == currentFileID){
				autoCheckLock();
			}
		}
	});	
	var showCallback = function(obj){
		//$.log("拖拽文件回调2");

	    //判断是从一盘打开还是在写写中用文件选择器打开
		
		var files ;
		if(obj.files != null){//文件选择器 
			files = obj.files;
		}else{
			files = obj;
		}
		
		var fileObj = {};
		for(var i=0;i<files.length;i++){
				
				fileObj = files[i];
				if($.inArray(fileObj.ext.toLowerCase(),supportedExts) < 0) {
					alert(fileObj.ext+"格式不支持用本应用打开");
					return;
				}
				var id = fileObj.id; 
				currentFileID = id;
				currentFileName = fileObj.name;
				
				//start to process file encode convert
				//lock file first
				var params = {
						fid:currentFileID,
						appName:myAppName,
						//appSign:"应用签名",--暂时可不传
						lockKey:myLockKey,
						success:function(data){
							//alert(data.file);
							isLock = false;
							prepareContent(fileObj);
							window.setInterval(autoCheckLock, autoCheckLockTime);
							window.setInterval(autoSave, autoSaveTime);
						},
						error:function(data){
							//alert(data.status);
							isLock = true;
							lockType = data.status;
							locker = "Gleasy";
							prepareContent(fileObj);
							window.setInterval(autoCheckLock, autoCheckLockTime);
							window.setInterval(autoSave, autoSaveTime);
						}
					}
				gleasy.system.sendMessageToProcess({
						pname:"一盘守护进程",
						eventId:"lockFileByApp",
						messageBody:params
					});
				//prepareContent(fileObj);
				
				//end to process file encode convert
	
			break;//只有一个文件
		}
	};//showCallback fun
var mycopy = $("#notepadCopy"), mycut = $("#notepadCut");
	
	var changeStatus = function(){

		if(view.gleasyGetSelection() !==''){
			mycopy.removeClass("npDisableBtn");
			mycut.removeClass("npDisableBtn");
		}else{
			mycopy.addClass("npDisableBtn");
			mycut.addClass("npDisableBtn");
		}
	};
	$("#notepadSelectAll").click(function(){
		view.gleasySelectAll();
		view.focus();
		changeStatus();
	});
	
//	$("#notepadUndo").click(function(){
//		//alert("undo")
//		//editAreaLoader.execCommand("edit_area", "undo", "");
//	});
//	$("#notepadRedo").click(function(){
//		//editAreaLoader.execCommand("edit_area", "redo", "");
//	});
	var replaceKey = $('#notepadReplaceStr');
	var updateReplaceBtnStatus = function(){
		if(replaceKey.val() == ''){
			$('#notepadDoReplace').addClass('isFindReplaceBtn_disable');
			$('#notepadReplaceAll').addClass('isFindReplaceBtn_disable');
		}else{
			$('#notepadDoReplace').removeClass('isFindReplaceBtn_disable');
			$('#notepadReplaceAll').removeClass('isFindReplaceBtn_disable');
		}
	};
	replaceKey.bind('change', function(){
		updateReplaceBtnStatus();
	}).bind('keyup', function(){
		updateReplaceBtnStatus();
	}).bind('paste', function(){
		setTimeout(function(){
			updateReplaceBtnStatus();
		}, 200);
		
	});
	$("#notepadDoSearch, #notepadFindPrevious").click(function(){
		if($("#notepadSearchStr").val() == "") return;
		var caseSensitive = 0;//$("#notepadCaseSensitive").hasClass("npMatchCaseNoSelected") ? 0 : 1;
		var goNext = $(this).attr('id') == 'notepadDoSearch' ? 1 : 0;//$("#notepadGoNext").hasClass("npSearchDirSelected") ? 1 : 0;
		
		var searchStr = $("#notepadSearchStr").val(), mode = "search";
		var s = view.gleasySearch4Start(searchStr, mode, caseSensitive, goNext, '',searchInput);
		if(s != -1){
			view.gleasySelectPos(s, searchStr.length, searchInput);
		}
		
	});
	/*
    $("#notepadDoSearch4Rp").click(function(){
    	if($("#notepadSearchStr4Rp").val() == "") return;
		var caseSensitive = $("#notepadCaseSensitive4Rp").hasClass("npMatchCaseNoSelected") ? 0 : 1;
		var goNext = 1;
		var searchStr = $("#notepadSearchStr4Rp").val(), mode = "search";
		var s = view.gleasySearch4Start(searchStr, mode, caseSensitive, goNext, '', searchInput);
		if(s != -1){
			view.gleasySelectPos(s, searchStr.length, searchInput);
		}
		
	});*/
	$("#notepadDoReplace").click(function(){
		//if($("#notepadSearchStr4Rp").val() == "") return;
		if($("#notepadSearchStr").val() == "") return;
		if($(this).hasClass('isFindReplaceBtn_disable')) return;
		var caseSensitive = 0;//$("#notepadCaseSensitive4Rp").hasClass("npMatchCaseNoSelected") ? 0 : 1;
		var goNext = 1;//must go next for replacement
		var searchStr = $("#notepadSearchStr").val();//$("#notepadSearchStr4Rp").val();
		var mode = "replace";
		var replaceStr = $("#notepadReplaceStr").val();
		var s = view.gleasySearch4Start(searchStr, mode, caseSensitive, goNext, replaceStr, searchInput);
		s = view.gleasySearch4Start(searchStr, "search", caseSensitive, goNext, '', searchInput);
		if(s != -1){
			view.gleasySelectPos(s, searchStr.length, searchInput);
		}
		textKeyupAction();
	});
	$("#notepadReplaceAll").click(function(){
	
		//if($("#notepadSearchStr4Rp").val() == "") return;
		if($("#notepadSearchStr").val() == "") return;
		if($(this).hasClass('isFindReplaceBtn_disable')) return;
		var caseSensitive = 0;//$("#notepadCaseSensitive4Rp").hasClass("npMatchCaseNoSelected") ? 0 : 1;
		var searchStr = $("#notepadSearchStr").val();//$("#notepadSearchStr4Rp").val();
		var replaceStr = $("#notepadReplaceStr").val();
		view.gleasyReplaceAll(searchStr, replaceStr, caseSensitive);
		textKeyupAction();		
	});
	$("#notepadWrap").click(function(){
		
		
		if($(this).hasClass("npLineFeedNoSelected")){//
			$(this).removeClass("npLineFeedNoSelected");
			view.attr("wrap", "hard");
			view.css("overflow-x", "");
			var v = view.val();
			view.val(v+' ');
			view.val(v);
		}else{
			$(this).addClass("npLineFeedNoSelected");
			view.attr("wrap", "off");
			view.css("overflow-x", "auto");
			var v = view.val();
			view.val(v+' ');
			view.val(v);
		}
						
	});
	$("#notepadPrint").click(function(){
		//隐藏下拉框
		$(".npFileMenu").hide();
		view.printArea();
	});
	var mypaste = $("#notepadPaste");
	$("#notepadCopy").click(function(){
		if($(this).hasClass("npDisableBtn")) return;
		//var sField = editAreaLoader.getEditArea("edit_area").textarea;
		//var s = sField.selection.createRange().text;
		clipboardData4notepad = view.gleasyGetSelection();
		mypaste.removeClass("npDisableBtn");
		mycopy.addClass("npDisableBtn");
		mycut.addClass("npDisableBtn");
		view.focus();
	});
	$("#notepadPaste").click(function(){
		if($(this).hasClass("npDisableBtn")) return;
		if(clipboardData4notepad == "") return;
		//var sField = editAreaLoader.getEditArea("edit_area").textarea;
		view.gleasyInsertAtCursor(clipboardData4notepad);
		//mypaste.addClass("npDisableBtn");
		textKeyupAction();
	});
	$("#notepadCut").click(function(){
		if($(this).hasClass("npDisableBtn")) return;
		//var sField = editAreaLoader.getEditArea("edit_area").textarea;
		clipboardData4notepad = view.gleasyCutAtCursor();
		mypaste.removeClass("npDisableBtn");
		mycopy.addClass("npDisableBtn");
		mycut.addClass("npDisableBtn");
		textKeyupAction();
	});
	
	view.select(function(){
		changeStatus();
	});
	view.mouseup(function(){
		setTimeout(function() {
			changeStatus();
		}, 200);
		
	});
	

	var stack = new Undo.Stack(), EditCommand = Undo.Command.extend({
		constructor : function(textarea, oldValue, newValue) {
			this.textarea = textarea;
			this.oldValue = oldValue;
			this.newValue = newValue;
		},
		execute : function() {
		},
		undo : function() {
			this.textarea.val(this.oldValue);
		},

		redo : function() {
			this.textarea.val(this.newValue);
		}
	});
	stack.changed = function() {
		stackUI();
	};
	
	//var undo = $("#undo")//,redo = $(".redo")
	
	var myundo = $("#notepadUndo");
	function stackUI() {
		if(stack.canUndo()){
			myundo.removeClass("npDisableBtn");
			//hasSaved = false;
		}else{
			myundo.addClass("npDisableBtn");
			hasSaved = true;
			setEnableSave(false);
		}
		
	}
	stackUI();
	setEnableSave(true);//can save at the beginning
	$(document.body).delegate("#notepadUndo", "click", function() {
		//var what = $(this).attr("id");
		if($(this).hasClass("npDisableBtn")) return false;
		if(stack.canUndo()){
			stack["undo"]();
			startValue = text.val();
		}
		return false;
	});
	
	
	var addToUndoStack = function(){
		var newValue = text.val();
		// ignore meta key presses
		if (newValue != startValue) {
			// this could try and make a diff instead of storing snapshots
			stack.execute(new EditCommand(text, startValue, newValue));
			startValue = newValue;
			setEnableSave(true);
		}
	};
	
	var textKeyupAction = function(){
		// a way too simple algorithm in place of single-character undo
		clearTimeout(timer);
		hasSaved = false;
		
		timer = setTimeout(function() {
			addToUndoStack();
		}, 800);
	};
	view.bind('paste', function(e){
		if (e.ctrlKey || e.type == "paste") {
			addToUndoStack();
			hasSaved = false;
			setEnableSave(true);
		}
	}).bind('cut', function(e){
		if (e.ctrlKey || e.type == "cut") {
			addToUndoStack();
			hasSaved = false;
			setEnableSave(true);
		}
	});
	view.bind("keyup", function(e) {
		var kcode = e.keyCode;
		if (e.ctrlKey && (kcode == 90 || kcode == 89) || kcode == 17){//z
			
		}else{
			textKeyupAction();
		}
	});
	var unlockFile = function(){
		if(!currentFileID){
			return;
		}
		var params = {
				fid:currentFileID,
				appName:myAppName,
				//appSign:"应用签名",--暂时可不传
				lockKey:myLockKey,
				success:function(data){
					//alert(data.file);
					
					if(exitAfterSave){
						gleasy.window.exit();
					}
				},
				error:function(data){
					
					//alert(data.status);
					if(exitAfterSave){
						gleasy.window.exit();
					}
				}
			}
		gleasy.system.sendMessageToProcess({
				pname:"一盘守护进程",
				eventId:"unlockFileByApp",
				messageBody:params
			});
	};

	
	//保存按钮公用callback
	var saveCallback = function(data, doUnlock, needExit){//result.data.file
		try {
						
			var result = data;//$.parseJSON(data);
			//if(result.status == 0){
				var isNew = false;
				if(!currentFileID){
					isNew = true;
				}
				if(currentFileID && currentFileID != result.file.id){
					unlockFile();
					gleasy.system.noticeListener({
						event:'Constants.command.notepad.saveNew',
						message:{fid:result.file.id}
					});	
				}
				currentFileID = result.file.id;
				currentFileName = result.file.name;
				//gleasy.window.setTitle({title:result.file.name});
				setGWindowTitle(currentFileName);
				//$.log('保存成功:' + result.data.file.id);
				//alert("保存成功")
				hasSaved = true;
				setEnableSave(false);
				$('.msgtip').hide();
				//$("#notepadSave, #notepadMenuSave").removeClass("npDisableBtn");
				isLock = false;
				if(!doUnlock && !exitAfterSave){
					autoCheckLock();
				}else{
					//doUnlock();
					unlockFile();
				}
				if(isNew){
					
					window.setInterval(autoCheckLock, autoCheckLockTime);
					window.setInterval(autoSave, autoSaveTime);
					gleasy.system.noticeListener({
						event:'Constants.command.notepad.saveNew',
						message:{fid:currentFileID}
					});	
				}
				if(!needExit && exitAfterSave){
					unlockFile();
					//gleasy.window.exit();
				}
				
			//}else{
				//hasSaved = false;
				//alert("一盘保存失败，原因：" + result.description);
				//$.log("一盘保存失败，原因：" + result.description);
				
			//}
		} catch (e) {
			//$.log('调用云硬盘保存： json parse err');
			//alert("save error")
			hasSaved = false;
			setEnableSave(true);
			//$('.msgtip').hide();
			if(doUnlock){
				doUnlock();
			}
			if(!needExit && exitAfterSave){
				unlockFile();
				//gleasy.window.exit();
			}
		}
		
	};
	var saveCallbackDoUnlock = function(data){

		saveCallback(data, unlockFile, true);
		gleasy.window.exit();
	};

	var saveErrCallback = function(needExit){
		//$.log('保存失败,saveErrCallback被回调');
		hasSaved = false;
		setEnableSave(true);
		//$('.msgtip').hide();
		if(!needExit && exitAfterSave){
			unlockFile();
			//gleasy.window.exit();
		}
	};
	var saveErrCallbackDoUnlock = function(){
		saveErrCallback(true);
		unlockFile();
		gleasy.window.exit();
	};
	
	
	view.focus();
	setViewParentLeft();
	var notepadNewFun = function(){
		currentFileID = null;
		currentFileName = null;
		view.val("");		
		//gleasy.window.setTitle("记事本");
		setGWindowTitle("记事本");
		//隐藏“文件”下拉菜单
		$(".npFileMenu").hide();
		view.get(0).focus();
	};
	
	$("#notepadNew").click(function(){
		//notepadNewFun();
		var param = {
				pname:"记事本",
			 	args:{
			 		//url:"http://www.baidu.com"
			 	}
			};
			gleasy.system.runProgram(param);

	});
	
	$("#notepadClose").click(function(){
		unlockFile();
		gleasy.window.close();
	});
	
	var notepadOpen = function(){
		//alert("open");
		//隐藏下拉框
		$(".npFileMenu").hide();
		var param = {
				parent:gleasy.window._this,
				exts:'*',
				//filter:['.txt','.css','.html'],
				filter:['*.txt','*.css','*.html', '*.csv'],
				type:'file',//可选值为folder,file,all
				multi:false,//是否支持返回多个项,true false
				select:function(obj){
					//alert("before select");
					var files ;
					if(obj.files != null){//文件选择器 
						files = obj.files;
					}else{
						files = obj;
					}
					var openFunc = function(){
						if(currentFileID && currentFileID != '' && files[0].id != currentFileID){
							
							var fileObj = {};
							for(var i=0;i<files.length;i++){
								var param = {
										pname:"记事本",
									 	args:{
									 		files:files //url:"http://www.baidu.com"
									 	}
									};
									gleasy.system.runProgram(param);
							}
						}else{
							showCallback(obj);
						}
					}
					
					var openPara = {fid:files[0].id, callback:openFunc};
					gleasy.system.noticeListener({
				    	event:'checkExistAndOpen',
						message:openPara
				    });
				},//选中时回调
				cancel:function(){
					//$.log("打开文件操作取消");
				},//取消时回调
				error:function(){
					//$.log("打开文件操作失败");
				}//出错时回调,比如文件不存在什么的
			};
			//gleasy.system.openFileBrowser(param);
			
			gleasy.system.sendMessageToProcess({
				pname:"一盘守护进程",
				eventId:"openFileBrowserDialog",
				messageBody:param

			});
	};
		
	$("#notepadOpen").click(function(){
		notepadOpen();
	});
	var notepadSaveAs = function(defName){
//$.log("开始另存为gdoc咯")
		var saveName = currentFileName;
		if(defName){
			saveName = defName;
		}
		//隐藏“文件”下拉菜单
		$(".npFileMenu").hide();
		
		var params = {
			parent:gleasy.window._this,
			mode:"save",			
			name:(saveName?saveName:""),
			filter:['*.txt','*.css','*.html', '*.csv'],
			appName:myAppName,
			//appSign:"应用签名",--暂时可不传
			lockKey:myLockKey,
			showDownloadButton:false,//显示下载到本地的按钮
			showUploadButton:false,//显示从本地上传的按钮
			select:function(result){
				//判断文件名是否为空
				if(result.name == ".txt"){
					gleasy.system.alert({
						title:"文件名为空",
						icon:"warning",//icon={confirm,fail,success,prompt,warning}
						cancelBtn:false,
						message:"文件名不能为空!"
					});
					return;
				}
				var fileNamePattern = /.txt$/;
				var fileNamePattern2 = /.csv$/;
				var fileNamePattern3 = /.css$/;
				var fileNamePattern4 = /.html$/;
				if(!fileNamePattern.test(result.name) && !fileNamePattern2.test(result.name)
						 && !fileNamePattern3.test(result.name)
						 && !fileNamePattern4.test(result.name)){
					//alert("文件名类型（后缀）必须是gdoc");
					/*
					gleasy.system.alert({
						title:"文档命名错误",
						icon:"warning",//icon={confirm,fail,success,prompt,warning}
						cancelBtn:false,
						message:"文件名类型（后缀）必须是txt"
					});
					return;
					*/
					var extension = result.extension;
					if(extension){
						if (extension != "*.*") {
							var index = extension.indexOf(";");
							if (index != -1) {
								extension = extension.substring(0, index);
							}
							result.name += extension.substring(1);
						}
					}else{
						result.name = result.name + '.txt';
					}
					
				}
							
				//var folder = result.folder;
				var fileForm = {};
				
				fileForm.pid = result.pid;
				fileForm.name=result.name;
				
				//do same name check
				/*
					var pid = dt.pid;//目录ID
					var fid = dt.fid;//要替换的文件ID
					var name = dt.name;//文件名称
					var rename = dt.rename;//自动重命名
				 */
				
				
				var saveContent = view.val();
				
				fileForm.fileContent = saveContent;
				fileForm.contentType = "string";
				
				//$.log('另存为保存文件开始:pid:' + fileForm.pid + '|||' + fileForm.fileContent);
				/*$( "#progressbar" ).show();*/
				//$("#editorProgress").show();
				if(result.fid){
					$('.msgtip').show();
					fileForm.name=result.name;
					fileForm.fid = result.fid;

					fileForm.name=result.rename;
					params4save = {
							fid:fileForm.fid,
							//name:fileForm.name,
							fileContent:saveContent,
							appName:myAppName,
							//appSign:"应用签名",--暂时可不传
							lockKey:myLockKey,
							contentType:'string',
							success:saveCallback,
							error:saveErrCallback
					};
					//调用一盘保存函数
					if(params4save != null){
						//$("#editorProgress").show();
						gleasy.system.sendMessageToProcess({
							pname:"一盘守护进程",
							eventId:"saveFile",
							messageBody:params4save

						});
					}
					/*
					//重名处理
					gleasy.system.confirm({
						message:result.name+" 已存在。",
					    confirmMessage:"要替换它吗?", 
					    system:false,//是否系统提醒
					    icon:"confirm",//icon={confirm,fail,success,prompt,warning}
					    okBtn:true,
					    okBtnText:"是",
					    cancelBtn:true,
					    cancelBtnText:"否",
						callback:function(dt){
							//参数
							var params4save = null;
							//不覆盖，不处理
							if(dt.result == false){
								return false;
							}else{
								//覆盖
								fileForm.name=result.name;
								fileForm.fid = result.fid;
								//com.gleasy.gdoceditor.FILE_ID = fileForm.fid;
								$('.msgtip').show();
							}
							
							
							fileForm.name=result.rename;
							params4save = {
									fid:fileForm.fid,
									//name:fileForm.name,
									fileContent:saveContent,
									appName:myAppName,
									//appSign:"应用签名",--暂时可不传
									lockKey:myLockKey,
									contentType:'string',
									success:saveCallback,
									error:saveErrCallback
							};
							//调用一盘保存函数
							if(params4save != null){
								//$("#editorProgress").show();
								gleasy.system.sendMessageToProcess({
									pname:"一盘守护进程",
									eventId:"saveFile",
									messageBody:params4save

								});
							}
							 
						}
					});
					*/
				}else{
					$('.msgtip').show();
				//无重名，直接保存
					var params4save = {
							pid:fileForm.pid,
							name:fileForm.name,
							fileContent:saveContent,
							contentType:'string',
							success:saveCallback,
							error:saveErrCallback
					};
					
					
					gleasy.system.sendMessageToProcess({
						pname:"一盘守护进程",
						eventId:"saveFile",
						messageBody:params4save
	
					});
				
				}
				
			},
			cancel:function(){
				//$.log("操作取消");
			}
//			error:function(){
//				//$.log("gleasy.system.openFileSaveBrowser error callback");
//			}
		};
		//gleasy.system.openFileSaveBrowser(param);
		gleasy.system.sendMessageToProcess({
			pname:"一盘守护进程",
			eventId:"openFileBrowserDialog",
			messageBody:params

		});
	};
	var saveasfun = function(defName){
		notepadSaveAs(defName);
		
	};
	
	//初始化另存为按钮
	$("#notepadSaveAs").click(function(){
		saveasfun();
	});
	
	
	var saveFile = function(checkShare, doUnlock){
		//隐藏“文件”下拉菜单
		$(".npFileMenu").hide();
		var fileForm = {};
		fileForm.fid = currentFileID;
		fileForm.fileContent=view.val();//editAreaLoader.getValue("edit_area");
		fileForm.contentType = "string";
		
		if(checkShare && currentFileID){
			var params = {
				    fid:currentFileID,//文件ID
				    coownerType:0,//共享者类型，0：表示普通用户
				    success:function(dt){
				        var coownerIds = dt.coownerIds;
				        //alert(coownerIds);//共享者ID
				        var runTime = 0;//since  gleasy.system.getCurrentUser will run two times
				        gleasy.system.getCurrentUser({
							callback:function(user){
								runTime++;
								if(runTime > 1) return;
								if($.inArray(user.uid, coownerIds) < 0){
									gleasy.system.confirm({
										message:"此文件已被所有者取消共享，",
									    confirmMessage:"不能直接保存。", 
									    system:false,//是否系统提醒
									    title:"提示",
									    icon:"prompt",//icon={confirm,fail,success,prompt,warning}
									    okBtn:true,
									    okBtnText:"另存为",
									    cancelBtn:true,
									    cancelBtnText:"关闭文件",
										callback:function(dt){
											if(dt.result){
												saveasfun();
											}else{
												if(dt.cancel){
													unlockFile();
													gleasy.window.exit();
												}else{
													return false;
												}
											}
										}
									});
								}else{
									//get lock again
									var paramsL = {
											fid:currentFileID,
											appName:myAppName,
											//appSign:"应用签名",--暂时可不传
											lockKey:myLockKey,
											success:function(data){
												//alert(data.file);
												isLock = false;
												//$("#notepadSave, #notepadMenuSave").removeClass("npDisableBtn");
												$('.msgtip').show();
												var params4save = {
														/*
														appName:"abc",
														appSign:"应用签名",//暂时可不传
														lockKey:"xxx",
														*/
														appName:myAppName,
														//appSign:"应用签名",--暂时可不传
														lockKey:myLockKey,
														fid:fileForm.fid,
														fileContent:fileForm.fileContent,
														contentType:'string',
														success:saveCallback,
														error:saveErrCallback
												};
												
												
												gleasy.system.sendMessageToProcess({
													pname:"一盘守护进程",
													eventId:"saveFile",
													messageBody:params4save
										
												});
											},
											error:function(data){
												//alert(data.status);
												isLock = true;
												lockType = data.status;
												locker = "Gleasy";
												var msg = "此文件已被所有者取消共享，";
												if(lockType == 12){
													msg = readOnlyMsg;
												}else if(lockType == 13){
													msg = editingMsg;
												}else if(lockType == 11){
													msg = cancelShareMsg;
												}
												$("#notepadSave, #notepadMenuSave").addClass("npDisableBtn");
												gleasy.system.confirm({
													message:msg,
												    confirmMessage:"不能直接保存。", 
												    system:false,//是否系统提醒
												    title:"提示",
												    icon:"prompt",//icon={confirm,fail,success,prompt,warning}
												    okBtn:true,
												    okBtnText:"另存为",
												    cancelBtn:true,
												    cancelBtnText:"关闭文件",
													callback:function(dt){
														if(dt.result){
															saveasfun();
															return;
														}else{
															if(dt.cancel){
																unlockFile();
																gleasy.window.exit();
															}else{
																return false;
															}
														}
													}
												});
											}
										}
									gleasy.system.sendMessageToProcess({
											pname:"一盘守护进程",
											eventId:"lockFileByApp",
											messageBody:paramsL
										});
									
									
								}
							}
						});
				    },
				    error:function(dt){
				    	gleasy.system.confirm({
							message:"此文件已被所有者取消共享，",
						    confirmMessage:"不能直接保存。", 
						    system:false,//是否系统提醒
						    title:"提示",
						    icon:"prompt",//icon={confirm,fail,success,prompt,warning}
						    okBtn:true,
						    okBtnText:"另存为",
						    cancelBtn:true,
						    cancelBtnText:"关闭文件",
							callback:function(dt){
								if(dt.result){
									saveasfun();
								}else{
									if(dt.cancel){
										unlockFile();
										gleasy.window.exit();
									}else{
										return false;
									}
								}
							}
						});
				    }
				}
				gleasy.system.sendMessageToProcess({
				    pname:"一盘守护进程",
				    eventId:"getFileCoownerIds",
				    messageBody:params
				});
		}else{
		
			$('.msgtip').show();
			var params4save = {
					/*
					appName:"abc",
					appSign:"应用签名",//暂时可不传
					lockKey:"xxx",
					*/
					appName:myAppName,
					//appSign:"应用签名",--暂时可不传
					lockKey:myLockKey,
					fid:fileForm.fid,
					fileContent:fileForm.fileContent,
					contentType:'string',
					success:(doUnlock ? saveCallbackDoUnlock : saveCallback),
					error:(doUnlock ? saveErrCallbackDoUnlock : saveErrCallback)
			};
			
			
			gleasy.system.sendMessageToProcess({
				pname:"一盘守护进程",
				eventId:"saveFile",
				messageBody:params4save
	
			});
		}
		
	};
	
	$("#notepadSave, #notepadMenuSave").click(function(){
		if($(this).hasClass('npDisableBtn')){
			return;
		}
		if(!currentFileID){
			saveasfun();
			return;
		}
		saveFile(true);
	});
	$(".msgtipclose").click(function(){
		$('.msgtip').hide();
	});
	
	
	
	var keyDownFunction = function(event){

		var kcode = event.keyCode;
		//if (!event.metaKey || event.keyCode != 90) {
			//return;
		//}
		//event.preventDefault();
		
		if(kcode == 13){//enter
			if($('.npSearchBoxWrap, .isFindReplaceWrp').is(":visible")){
				event.preventDefault();
				event.stopPropagation(); 
				event.cancelBubble = true;
	            event.returnValue=false;
				$("#notepadDoSearch").trigger('click');
				return false;
			}
		}
		if(event.ctrlKey && event.shiftKey && kcode == 83){//c s f
			event.preventDefault();
			//event.stopPropagation(); 
			//event.cancelBubble = true;
			notepadSaveAs();
			//event.returnValue=false;
			return false;
		}
		if (event.ctrlKey && (kcode != 67 && kcode != 86 && kcode != 88 && kcode != 35 && kcode !=36 && kcode !=65))//skip ctrl+c/v/x/home/end/a
        {
			event.preventDefault();
			event.stopPropagation(); 
			event.cancelBubble = true;
            event.returnValue=false;
			if(kcode == 78) {//n
				$("#notepadNew").trigger('click');
				return false;
			}
			else if(kcode == 79) {//o
				notepadOpen();
				return false;
			}
			else if(kcode == 70) {//f
				$('.npFind').trigger('click');
				return false;
			}
			else if(kcode == 72) {//h
				$('.npReplace').trigger('click');
				return false;
			}
			else if(kcode == 83) {//s
				if(!isDebug && isLock){
					promptLockBeforeSave();
					return false;
				}
				$("#notepadSave").trigger('click');
				return false;
			}else if(kcode == 80) {//p
				$("#notepadPrint").trigger('click');
					return false;
			}else if(kcode == 90) {//z
				if(stack.canUndo()){
					stack["undo"]();
					startValue = text.val();
				}
				return false;
			}else if(kcode == 89) {//y
				if(stack.canRedo()){
					stack["redo"]();
					startValue = text.val();
				}
				return false;
			}
			
			
        }else if(kcode == 27) {//escape
			$('.npSearchBoxWrap, .isFindReplaceWrp').hide();
			$('.npReplaceBoxWrap, .isFindReplaceWrp').hide();
			view.focus();
			//view.parent().css("margin-left", "3px").css("left", "0px");
		}
		
		//event.returnValue = true;
		
		
	
	};
	var mydoc = $('#if').contents().get(0);
	$(mydoc).bind('keydown',keyDownFunction);
	$(document).bind('keydown',keyDownFunction);
	        
	
	var param = {
		callback:function(files){
			//view.blur();
			if(!isIE9)
				gleasy.window.showLoading({show:true});
			showCallback(files);
			
		}
	};
	gleasy.system.fileReceived(param);
	gleasy.window.resizeFinish({
		callback:function(param){
//			view.css({
//				width:param.width,
//				height:param.height
//			});
		}
	});	
//	gleasy.window.setSize({
//		width:600,
//		height:400
//	});		
	
	gleasy.window.center();	
	
	//handle unsave
	gleasy.window.setCloseHandler({
		handler:function(){
			//你的处理在这里
			//还没保存的话，提示用户
			if(!hasSaved ){
				if(oldPrompt){
					var div = $("#saveFileDialog");
					div.show();
					//return false;
					var fileName = currentFileName;
					var displayName = fileName;
					if(fileName == null){
						displayName = "未命名";
						div.find("#fileName").html(displayName+".txt");
					}else{
						div.find("#fileName").html(displayName);
					}
					
					
					div.find("a.oWinClose").click(function(){
						div.hide();
						return false;
					});
					div.find("#gdocCancel").click(function(){
						div.hide();
						return false;
					});
					div.find("#gdocSave").click(function(){
						div.hide();
						if(!currentFileID){
							saveasfun(fileName);
							return;
						}
						saveFile();
						unlockFile();
						gleasy.window.exit();
					});
					div.find("#gdocUnSave").click(function(){
						unlockFile();
						gleasy.window.exit();
					});
				}else{
					if(!isDebug && isLock){
						var msg = "此文件为只读/正在被编辑，";
						if(lockType == 12){
							msg = readOnlyMsg;
						}else if(lockType == 13){
							msg = editingMsg;
						}else if(lockType == 11){
							msg = cancelShareMsg;
						}
						gleasy.system.confirm({
							message:msg,
						    confirmMessage:"您编辑文件后不能保存，<p>只能另存。", 
						    system:false,//是否系统提醒
						    title:"提示",
						    icon:"prompt",//icon={confirm,fail,success,prompt,warning}
						    okBtn:true,
						    okBtnText:"另存为",
						    cancelBtn:true,
						    cancelBtnText:"不保存",
							callback:function(dt){
								if(dt.result){
									saveasfun(currentFileName);
									return;
								}else{
									if(dt.cancel){
										unlockFile();
										gleasy.window.exit();
									}else{
										return false;
									}
								}
							}
						});
					}else{
						if(!currentFileID){

							var fileName = currentFileName;
							var displayName = fileName;
							if(fileName == null){
								displayName = "未命名.txt";
							}
							gleasy.system.confirm({
								message:"是否将更改保存到" + displayName + "中？",
							    confirmMessage:"", 
							    system:false,//是否系统提醒
							    icon:"confirm",//icon={confirm,fail,success,prompt,warning}
							    okBtn:true,
							    okBtnText:"保存",
							    cancelBtn:true,
							    cancelBtnText:"不保存",
							    checkBtn:false,
							    checkBtnText:"",
								callback:function(dt){
									if(dt.result){
										if(!currentFileID){
											exitAfterSave = true;
											saveasfun(fileName);
											
											return;
										}
										saveFile();
										unlockFile();
										gleasy.window.exit();
									}else{
										if(dt.cancel){
											unlockFile();
											gleasy.window.exit();
										}else{
											return false;
										}
									}
								}
							});
						}else{
							var params = {
								    fid:currentFileID,//文件ID
								    coownerType:0,//共享者类型，0：表示普通用户
								    success:function(dt){
								        var coownerIds = dt.coownerIds;
								        //alert(coownerIds);//共享者ID
								        var runTime = 0;//since  gleasy.system.getCurrentUser will run two times
								        gleasy.system.getCurrentUser({
											callback:function(user){
												runTime++;
												if(runTime > 1) return;
												if($.inArray(user.uid, coownerIds) < 0){
													gleasy.system.confirm({
														message:"此文件已被所有者取消共享，",
													    confirmMessage:"不能直接保存。", 
													    system:false,//是否系统提醒
													    title:"提示",
													    icon:"prompt",//icon={confirm,fail,success,prompt,warning}
													    okBtn:true,
													    okBtnText:"另存为",
													    cancelBtn:true,
													    cancelBtnText:"关闭文件",
														callback:function(dt){
															if(dt.result){
																saveasfun();
															}else{
																if(dt.cancel){
																	unlockFile();
																	gleasy.window.exit();
																}else{
																	return false;
																}
															}
														}
													});
												}else{
													var params = {
															fid:currentFileID,
															appName:myAppName,
															//appSign:"应用签名",--暂时可不传
															lockKey:myLockKey,
															success:function(data){
																//alert(data.file);
																isLock = false;
																//$("#notepadSave, #notepadMenuSave").removeClass("npDisableBtn");
																var fileName = currentFileName;
																var displayName = fileName;
																if(fileName == null){
																	displayName = "未命名.txt";
																}
																gleasy.system.confirm({
																	message:"是否将更改保存到" + displayName + "中？",
																    confirmMessage:"", 
																    system:false,//是否系统提醒
																    icon:"confirm",//icon={confirm,fail,success,prompt,warning}
																    okBtn:true,
																    okBtnText:"保存",
																    cancelBtn:true,
																    cancelBtnText:"不保存",
																    checkBtn:false,
																    checkBtnText:"",
																	callback:function(dt){
																		if(dt.result){
																			if(!currentFileID){
																				saveasfun(fileName);
																				return;
																			}
																			saveFile(false, unlockFile);// will exit
																			//unlockFile();
																			//gleasy.window.exit();
																			return false;
																		}else{
																			if(dt.cancel){
																				unlockFile();
																				gleasy.window.exit();
																			}else{
																				return false;
																			}
																		}
																	}
																});
															},
															error:function(data){
																//alert(data.status);
																isLock = true;
																lockType = data.status;
																locker = "Gleasy";
																var msg = "此文件已被所有者取消共享，";
																if(lockType == 12){
																	msg = readOnlyMsg;
																}else if(lockType == 13){
																	msg = editingMsg;
																}else if(lockType == 11){
																	msg = cancelShareMsg;
																}
																$("#notepadSave, #notepadMenuSave").addClass("npDisableBtn");
																gleasy.system.confirm({
																	message:msg,
																    confirmMessage:"不能直接保存。", 
																    system:false,//是否系统提醒
																    title:"提示",
																    icon:"prompt",//icon={confirm,fail,success,prompt,warning}
																    okBtn:true,
																    okBtnText:"另存为",
																    cancelBtn:true,
																    cancelBtnText:"关闭文件",
																	callback:function(dt){
																		if(dt.result){
																			saveasfun();
																		}else{
																			if(dt.cancel){
																				unlockFile();
																				gleasy.window.exit();
																			}else{
																				return false;
																			}
																		}
																	}
																});
															}
														}
													gleasy.system.sendMessageToProcess({
															pname:"一盘守护进程",
															eventId:"lockFileByApp",
															messageBody:params
														});
													
												}
											}
										});
								    },
								    error:function(dt){
								        //alert(dt.status);
								    	gleasy.system.confirm({
											message:"此文件已被所有者取消共享，",
										    confirmMessage:"不能直接保存。", 
										    system:false,//是否系统提醒
										    title:"提示",
										    icon:"prompt",//icon={confirm,fail,success,prompt,warning}
										    okBtn:true,
										    okBtnText:"另存为",
										    cancelBtn:true,
										    cancelBtnText:"关闭文件",
											callback:function(dt){
												if(dt.result){
													saveasfun();
												}else{
													if(dt.cancel){
														unlockFile();
														gleasy.window.exit();
													}else{
														return false;
													}
												}
											}
										});
								    }
								}
								gleasy.system.sendMessageToProcess({
								    pname:"一盘守护进程",
								    eventId:"getFileCoownerIds",
								    messageBody:params
								});
						}
					}
				}
			}else{
				unlockFile();
				gleasy.window.exit();
			}
			
		}
	});
	
});
jQuery.fn.extend({

    /**
     * 光标处插入字符
     *
     * @param  待插入字符
     * @return 改变后的对象
     */
    gleasyInsertAtCursor : function(myValue) {
            
         var _this =  $(this).get(0);
         
         if (document.selection) { //IE
             _this.focus();
             var mydoc = $('#if').contents().get(0);
             sel = mydoc.selection.createRange();
             sel.text = myValue;
             sel.select();
         } else { //FF
             var startPos = _this.selectionStart;
             var endPos = _this.selectionEnd;
             
             var restoreTop = _this.scrollTop; // save scrollTop before insert
             _this.value = _this.value.substring(0, startPos) + myValue + _this.value.substring(endPos, _this.value.length);
             if (restoreTop > 0) {
             _this.scrollTop = restoreTop;
             }
             _this.focus();
             _this.selectionStart = startPos + myValue.length;
             _this.selectionEnd = startPos + myValue.length;
         }
         return $(this);
    },
    
    /**
     * 获取选择的字符
     *
     * @return 选择的字符
     */
    gleasyGetSelection : function() {
         var myValue = "";   
         var _this =  $(this).get(0);
         
         if (document.selection) { //IE
        	 _this.focus();
        	 var mydoc = $('#if').contents().get(0);
             sel = mydoc.selection.createRange();
             myValue = sel.text;
         } else { //FF
             var startPos = _this.selectionStart;
             var endPos = _this.selectionEnd;
             myValue = _this.value.substring(startPos, endPos);
             
         }
         return myValue;
    },
    getPosStart: function(){
    	var textBox = $(this).get(0);
        var start = -1;
        var end = -1;
        //下面是IE(6.0)的方法，麻烦得很，还要计算上'/n'
        if(document.selection){
        	//if($.browser.msie && $.browser.version >= 9){
        		textBox.focus();
        	//}
        	var mydoc = $('#if').contents().get(0);
            var range = mydoc.selection.createRange();
            if(range.parentElement().id == textBox.id){
                // create a selection of the whole textarea
                var range_all = mydoc.body.createTextRange();
                range_all.moveToElementText(textBox);
                //两个range，一个是已经选择的text(range)，一个是整个textarea(range_all)
                //range_all.compareEndPoints()比较两个端点，如果range_all比range更往左(further to the left)，则                //返回小于0的值，则range_all往右移一点，直到两个range的start相同。
                // calculate selection start point by moving beginning of range_all to beginning of range
                for (start=0; range_all.compareEndPoints("StartToStart", range) < 0; start++)
                    range_all.moveStart('character', 1);
                // get number of line breaks from textarea start to selection start and add them to start
                // 计算一下/n
                for (var i = 0; i <= start; i ++){
                    if ( $.browser.version >= 9 && (textBox.value.charAt(i) == '/n')//no affect, just keep it here
                    		|| $.browser.version < 9 && textBox.value.charAt(i) == '\n')
                        start++;
                }
                // create a selection of the whole textarea
                 var range_all = mydoc.body.createTextRange();
                 range_all.moveToElementText(textBox);
                 // calculate selection end point by moving beginning of range_all to end of range
                 for (end = 0; range_all.compareEndPoints('StartToEnd', range) < 0; end ++)
                     range_all.moveStart('character', 1);
                     // get number of line breaks from textarea start to selection end and add them to end
                     for (var i = 0; i <= end; i ++){
                         if ( $.browser.version >= 9 && (textBox.value.charAt(i) == '/n') //no affect, just keep it here
                         		|| $.browser.version < 9 && textBox.value.charAt(i) == '\n')
                             end ++;
                     }
                     
                     //if(end == -1){
                    	 //end = start;
                     //}
            }
        }
			 //如果是Firefox(1.5)的话，方法很简单
        else {
            start = textBox.selectionStart;
            end = textBox.selectionEnd;
        }
        return [start, end];
        
    },
    
    /**
     * 剪切选中的字符
     *
     * @return 剪切的字符
     */
    gleasyCutAtCursor : function() {
         var myValue = "";   
         var returnValue = $(this).gleasyGetSelection();
         $(this).gleasyInsertAtCursor(myValue);
         return returnValue;
    },
    gleasySelectAll : function(obj) {
    	var myValue = "";   
    	var _this =  $(this).get(0);
        
        if (document.selection) { //IE
        	_this.createTextRange().select();
        } else { //FF
        	//_this.selectionStart = 0;
            //_this.selectionEnd = _this.value.length;
            //_this.setSelectionRange(0, _this.value.length);
        	 $(this).select();
        }
        return myValue;
   },
   /**
    * 选择指定文本
    * @param start
    * @param length
    */
   gleasySelectPos : function(start, length, searchInput){
	   var _this =  $(this).get(0);
	   //_this.focus();
		
		start	= Math.max(0, Math.min(_this.value.length, start));
		end		= Math.max(start, Math.min(_this.value.length, start+length));

		if(document.selection)
		{
			/*
			_this.selectionStart	= start+1;
			_this.selectionEnd		= end+1;		
			var a = _this, nbLineStart, nbLineEnd, range;
						
			nbLineStart	= a.value.substr(0, a.selectionStart).split("\n").length - 1;
			nbLineEnd 	= a.value.substr(0, a.selectionEnd).split("\n").length - 1;
			range		= document.selection.createRange();
			range.moveToElementText( a );
			range.setEndPoint( 'EndToStart', range );
			
			range.moveStart('character', a.selectionStart - nbLineStart);
			range.moveEnd('character', a.selectionEnd - nbLineEnd - (a.selectionStart - nbLineStart)  );
			range.select();*/
			var a = _this, nbLineStart, nbLineEnd;
			
			var nbLineStart	= a.value.substr(0, start).split("\n").length - 1;
			var nbLineEnd 	= a.value.substr(0, end).split("\n").length - 1;
			if($.browser.msie && $.browser.version >= 9){//important for IE9
				nbLineStart = 0;
				nbLineEnd = 0;
			}
			
			var rng = _this.createTextRange();
			rng.moveEnd("character", -_this.value.length);
			rng.moveStart("character", -_this.value.length);
			rng.collapse(true);
			rng.moveEnd("character", end - nbLineEnd);
			rng.moveStart("character", start - nbLineStart);
			
			rng.select(); 
			//alert('start=' +start+'----end ='+end );
			if(length>0)
			rng.scrollIntoView();
		}
		else
		{
			if($.browser.mozilla){
				$(this).focus();
			}
			//$(this).focus();//should not set here
			_this.selectionStart = start;
            _this.selectionEnd = end;
            if(!$.browser.mozilla){
				$(this).focus();
			}
			//_this.setSelectionRange(start, end);
            _this.scrollIntoView();
           // $(this).focus();
            if(searchInput && !$.browser.mozilla){
            	searchInput.focus();
			}
		}
		
	},
	/**
	 * 
	 * @param search
	 * @param mode
	 * @param caseSensitive
	 * @param goNext
	 * @param replace
	 * @returns {Number}
	 */
	gleasySearch4Start : function(search, mode, caseSensitive, goNext, replace, searchInput){
		   var _this =  $(this).get(0);
		   //_this.focus();
		   var start = _this.selectionStart;
		   var myend = _this.selectionEnd;
		   var begin = -1;
		   var original_start = start;
			
		    var pos=-1;
			var pos_begin=-1;
			
			if(document.selection){
				var startend = $(this).getPosStart();
				start = startend[0];
				myend = startend[1];
				original_start = start;
			}
			
			// advance to the next occurence if no text selected
			if(mode!="replace" && start < myend){
				if(caseSensitive == 1 && $(this).gleasyGetSelection() == search
						||
				   caseSensitive == 0 && $(this).gleasyGetSelection().toLowerCase() == search.toLowerCase()){
					start+= search.length;
				}
			}
			if(goNext == 0){
				if(document.selection){
					start = original_start - search.length;
				}else{
					start = _this.selectionStart - search.length;
				}
				
				if(start < 0){
					start = search.lenght - 1;
				}
			}
			
			//var length=search.length;
			if(caseSensitive == 1){
				if(goNext == 1){
					pos= _this.value.indexOf(search, start); 
					pos_begin= _this.value.indexOf(search); 
				}else{
					pos= _this.value.lastIndexOf(search, start); 
					if($.browser.msie){
						var cutStr = _this.value.substring(0, start + search.length -1);
						pos = cutStr.lastIndexOf(search, start);
						
					}
					pos_begin= _this.value.lastIndexOf(search); 
				}
			}else{
				if(goNext == 1){
					pos= _this.value.toLowerCase().indexOf(search.toLowerCase(), start); 
					pos_begin= _this.value.toLowerCase().indexOf(search.toLowerCase()); 
				}else{
					pos= _this.value.toLowerCase().lastIndexOf(search.toLowerCase(), start); 
					if($.browser.msie){
						var cutStr = _this.value.toLowerCase().substring(0, start + search.length -1);
						pos = cutStr.lastIndexOf(search.toLowerCase(), start);
						
					}
					pos_begin= _this.value.toLowerCase().lastIndexOf(search.toLowerCase()); 
				}
			}
			var length=search.length; 
			
			if(pos==-1 && pos_begin==-1){
				//notfound//_$("area_search_msg").innerHTML="<strong>"+search+"</strong> "+this.get_translation("not_found");
				return -1;//
			}else if(pos==-1 && pos_begin != -1){
				//found before the start
				begin= pos_begin;
				//_$("area_search_msg").innerHTML=this.get_translation("restart_search_at_begin");
			}else
				begin= pos;
			
			if(mode=="replace"){
				var new_text="";
				new_text= _this.value.substr(0, begin) + replace + _this.value.substr(begin + length);
				
				_this.value=new_text;
				_this.selectionStart = begin + replace.length;
				_this.selectionEnd = _this.selectionStart;
				if(document.selection){
					$(this).gleasySelectPos(_this.selectionStart, 0, searchInput);
				}
			}
			
			
			
			//set selection
			
			//view.gleasySelectPos(s, searchStr.length);
			
			return begin;
		},
		
		gleasyReplaceAll : function(search, replace, caseSensitive){
			var tmpStr = "";
			var ones = "";
			for(var i = 0; i < search.length; i++){
				ones = search.charAt(i);
				switch(ones){
					case '#':
					case '$':
					case '%':
					case '^':
					case '&':
					case '*':
					case '(':
					case ')':
					case '+':
					case '/':
					case '\\':
					case '.':
					case '?':
					case '{':
					case '}':
					case '|':
					case '=':
					case '[':
					case ']':
					case '@':
					case '!':
					case '~':
					case '`':
					case '-': tmpStr += '\\' + ones;
						break;
					default: tmpStr += ones;
				}
			}
			
			var _this =  $(this).get(0);
			var opt = "ig";
			if(caseSensitive == 1){
				opt = 'g';
			}
			var reg= new RegExp(tmpStr, opt);
			_this.value = _this.value.replace(reg, replace);
		}
   
});


