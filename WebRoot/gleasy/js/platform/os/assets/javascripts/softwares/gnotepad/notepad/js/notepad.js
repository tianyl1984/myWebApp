$(function(){
	/*
	var cdRightHeight = function(){
		$(".cdRight").css({height: function(){return $(".cdLeft").height();}});
	};
	cdRightHeight();
	$( ".oWindow" ).resizable({
		resize: function(){
			cdRightHeight();
		}
	});
	*/
	$('.oWindow').draggable({
		 cancel     :'a,textarea,input,select,.undraggable, button'
	});
	//$("#saveFileDialog").draggable();
	
});
//弹出文件菜单
$(".npFileBtn").click(function(e){
	e.stopPropagation();
	//$(this).addClass('npFileBtnSelected');
	//$('.npFileMenu').css('display','block');
	if($('.npFileMenu').is(":visible")){
		$('.npFileMenu').hide();
		$(".npFileBtn").removeClass('npFileBtnSelected');
	}else{
		$('.npFileMenu').show();
		$(this).addClass('npFileBtnSelected');
	}
});
//设置'文件'按钮样式
$('.npFileMenuList').click(function(){
	$(".npFileBtn").removeClass('npFileBtnSelected');
	});
//点击其他位置则隐藏菜单
$("body").click(function(){
	$('.npFileMenu').css('display','none');
	$(".npFileBtn").removeClass('npFileBtnSelected');
});
/*
//自动换行
$('.npLineFeed').click(function(){
	if($(this).hasClass('npLineFeedNoSelected')){
		$(this).removeClass('npLineFeedNoSelected');
		$('.npWrapTxt').removeClass('npContentNoLineFeed').addClass('npContentLineFeed');
	}else{
		$(this).addClass('npLineFeedNoSelected');
		$('.npWrapTxt').removeClass('npContentLineFeed').addClass('npContentNoLineFeed');	
	}
});*/
$('.npReplace').click(function(){
	//$('.npReplaceBoxWrap').show();
	//$('.npSearchBoxWrap').hide();
	$('.isFindReplaceWrp').show().css("top", "64px").css("left","208px");
	//$("#notepadSearchStr4Rp").focus();
	$("#notepadSearchStr").focus();
	//$('.npReplaceBoxWrap').css("top", "64px").css("left","208px");
});
$('.npFind').click(function(){
	//$('.npSearchBoxWrap').show();
	//$('.npReplaceBoxWrap').hide();
	$('.isFindReplaceWrp').show().css("top", "64px").css("left","208px");
	$("#notepadSearchStr").focus();
	//$('.npSearchBoxWrap').css("top", "64px").css("left","208px");
});
$('.isFindReplaceWrp .oWinClose').click(function(){
	$('.isFindReplaceWrp').hide();
});
/*
$('.npSearchDir').click(function(){
	$('.npSearchDir').removeClass('npSearchDirSelected');
	$(this).addClass('npSearchDirSelected');
});
$('.npSearchBoxWrap .oWinClose').click(function(){
	$('.npSearchBoxWrap').hide();
});
$('.npReplaceBoxWrap .oWinClose').click(function(){
	$('.npReplaceBoxWrap').hide();
});
$('.npMatchCase').click(function(){
	if($(this).hasClass('npMatchCaseNoSelected')){
		$(this).removeClass('npMatchCaseNoSelected');
	}else{
		$(this).addClass('npMatchCaseNoSelected');
	}
});*/
