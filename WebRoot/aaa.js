
var js = [];
//js.push("chrome://resources/css/tree.css.js");
js.push("chrome://resources/js/cr.js");
//js.push("chrome://resources/js/event_tracker.js");
js.push("chrome://resources/js/cr/event_target.js");
js.push("chrome://resources/js/cr/ui.js");
//js.push("chrome://resources/js/cr/ui/touch_handler.js");
//js.push("chrome://resources/js/cr/ui/array_data_model.js");
js.push("chrome://resources/js/cr/ui/bubble.js");
js.push("chrome://resources/js/cr/ui/bubble_button.js");
//js.push("chrome://resources/js/cr/ui/focus_manager.js");
//js.push("chrome://resources/js/cr/ui/list_selection_model.js");
//js.push("chrome://resources/js/cr/ui/list_selection_controller.js");
//js.push("chrome://resources/js/cr/ui/list_single_selection_model.js");
js.push("chrome://resources/js/cr/ui/list_item.js");
js.push("chrome://resources/js/cr/ui/list.js");
//js.push("chrome://resources/js/cr/ui/menu_item.js");
//js.push("chrome://resources/js/cr/ui/menu.js");
//js.push("chrome://resources/js/cr/ui/autocomplete_list.js");
//js.push("chrome://resources/js/cr/ui/grid.js");
//js.push("chrome://resources/js/cr/ui/overlay.js");
//js.push("chrome://resources/js/cr/ui/position_util.js");
//js.push("chrome://resources/js/cr/ui/repeating_button.js");
//js.push("chrome://resources/js/cr/ui/tree.js");
//js.push("chrome://resources/js/load_time_data.js");
js.push("chrome://resources/js/util.js");
js.push("chrome://settings-frame/strings.js");
js.push("chrome://settings-frame/options_bundle.js");
//js.push("chrome://resources/js/i18n_template2.js");
var oHead = document.getElementsByTagName('HEAD').item(0);
for(var i=0;i<js.length;i++){
	var oScript= document.createElement("script"); 
	oScript.type = "text/javascript"; 
	oScript.src = js[i];
	oHead.appendChild(oScript);
}
var loadTimeData = {
	getValue:function(){},
	getString:function(){}
};
//(function(){
//	
window.onload = function(){
	document.getElementById("test").addEventListener("click",function(){
		cr.define('options',function(){
			var argumentList = ["homepage","http://192.168.1.8/dc"];
			chrome.send('setURLPref', argumentList);
		});
	});
};
////	alert("aa");
//})();
//<script src="chrome://resources/css/tree.css.js"></script>
//<script src="chrome://resources/js/cr.js"></script>
//<script src="chrome://resources/js/event_tracker.js"></script>
//<script src="chrome://resources/js/cr/event_target.js"></script>
//<script src="chrome://resources/js/cr/ui.js"></script>
//<script src="chrome://resources/js/cr/ui/touch_handler.js"></script>
//<script src="chrome://resources/js/cr/ui/array_data_model.js"></script>
//<script src="chrome://resources/js/cr/ui/bubble.js"></script>
//<script src="chrome://resources/js/cr/ui/bubble_button.js"></script>
//<script src="chrome://resources/js/cr/ui/focus_manager.js"></script>
//<script src="chrome://resources/js/cr/ui/list_selection_model.js"></script>
//<script src="chrome://resources/js/cr/ui/list_selection_controller.js"></script>
//<script src="chrome://resources/js/cr/ui/list_single_selection_model.js"></script>
//<script src="chrome://resources/js/cr/ui/list_item.js"></script>
//<script src="chrome://resources/js/cr/ui/list.js"></script>
//<script src="chrome://resources/js/cr/ui/menu_item.js"></script>
//<script src="chrome://resources/js/cr/ui/menu.js"></script>
//<script src="chrome://resources/js/cr/ui/autocomplete_list.js"></script>
//<script src="chrome://resources/js/cr/ui/grid.js"></script>
//<script src="chrome://resources/js/cr/ui/overlay.js"></script>
//<script src="chrome://resources/js/cr/ui/position_util.js"></script>
//<script src="chrome://resources/js/cr/ui/repeating_button.js"></script>
//<script src="chrome://resources/js/cr/ui/tree.js"></script>
//<script src="chrome://resources/js/load_time_data.js"></script>
//<script src="chrome://resources/js/util.js"></script>
//<script src="chrome://settings-frame/strings.js"></script>
//<script src="chrome://settings-frame/options_bundle.js"></script>
//<script src="chrome://resources/js/i18n_template2.js"></script>