/**
 *消息对话框，具备以下能力
 * 1. Alert
 * 2. Confirm
*/
Namespace.register("com.gleasy.os.view");

com.gleasy.os.view.MessageDialog = function(param){
	$.extend(param,{
		hasFrame:1,
		hasDock:0,
		canMax:0,
		initSize:[398,160],
		canMin:0,
		canResize:0
	});
	if(param.confirm && !param.system){
		com.gleasy.os.view.Dialog.call(this,param);//继承Dialog
	}else{
		com.gleasy.os.view.Container.call(this,param);//继承Container
	}
	var _this = this;

	var options = {
		title:null,
		icon:"confirm",//icon={confirm,fail,success,prompt,warning}
		message:"",
		confirm:false,
		okBtn:true,
		okBtnText:"确定",
		cancelBtn:true,
        cancelBtnText:"取消",
        checkBtn:false,
        checkBtnText:"不再提示",
        initcallback:null,
		callback:null
	};
	
	var view = null;
	
	var _construct = function(){
		$.extend(options,param);
		initComponent();
	}

	var initComponent = function(){
		if(options.title == null){
			_this.setTitle("提示");
		}		
		view = $("<div class='domainTip'/>");
		view.css({top:0,left:0,width:'100%',height:'100%'});
		_this.setContent(view);
		
		var osMessageDialog = $("#util_div .osMessageDialog").clone();
		view.append(osMessageDialog);

		if(!param.confirm ){
			_this.view.addClass("oWindow_current");
			_this.setZindex(Constants.config.os.layer.messageDialog);
		}
		
		if(param.system){
		  _this.view.addClass("oWindow_current");
		  _this.setZindex(Constants.config.os.layer.screenMask+1);
		}
		
		if(param.noclose){
			_this.view.find('div.oWindowBtnBar a.oWinClose').hide();
		}
		
		configEvent();
		resize();
		_this.center();
		
		typeof options.initcallback=='function' && options.initcallback({dialog:_this})
	}

	
	var configEvent = function(){	
		//if(!options.confirm){
			//$(".mdBtnbox",view).hide();
		//}else{
			if(!options.okBtn){
				$(".okBtn",view).hide();
			}else{
				$(".okBtn",view).text(options.okBtnText);
			}
			if(!options.cancelBtn){
				$(".cancelBtn",view).hide();
			}else{
				$(".cancelBtn",view).text(options.cancelBtnText);
			}
			if(options.checkBtn){
				$(".checkBtn",view).show();
				$(".mdCheckTxt",view).text(options.checkBtnText);
			}else{
				$(".checkBtn",view).hide();
			}
		//}
		if(options.title){
			_this.setTitle(options.title);
		}

		//二次确认：mdico_confirm ,失败： mdico_fail ,成功： mdico_success , 提示： mdico_prompt ,警告： mdico_warning 
	
		$(".mdTxti",view).addClass("mdico_"+options.icon);
		
		$(".headLine",view).html(options.message);
		
		if(options.confirmMessage){
			$(".confirmMessage",view).html(options.confirmMessage);
		}else{
			$(".confirmMessage",view).hide();	
		}
		$(".okBtn",view).click(function(){
			_this.exit();
			var check = $(".checkBtnInput",view).is(":checked");
			typeof options.callback=='function'&& options.callback({result:true,check:check});
		});
		
		$(".cancelBtn",view).click(function(){
			_this.close(true);	
		});		
		
		_this.addEventListener("resize",function(param){
			resize();
		});	
	}
	
	this.close = function(isCancel){
		var check = $(".checkBtnInput",view).is(":checked");
		_this.exit();
		typeof options.callback=='function' && options.callback({result:false,check:check,cancel:(isCancel?isCancel:false)});
		return true;
	}
	
	var resize = function(){
		var size = _this.getContentSize();
		var bottomHeight = 	$(".mdBtnbox",view).height() + 10;
		if(!options.confirm){
			bottomHeight = 0;
		}
		$(".mdTxt",view).css({
			height:size.height - 2 - bottomHeight - 31
		});
	}
	
	return _construct();
}
