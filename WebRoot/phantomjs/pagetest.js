

var page = require('webpage').create();

page.onConsoleMessage = function(msg){
	console.log("ConsoleMessage:" + msg);
};

page.onAlert = function(msg){
	console.log("Alert:" + msg);
};

page.open("about:blank",function(status){
//	console.log(status);
//	page.injectJs();
//	page.evaluateJavaScript("alert(123)",function(){
//		console.log("finish js");
//	});
	
	page.injectJs("../common/js/jquery-1.6.2.js");
	page.injectJs("../jqplot/jqplot/jquery.jqplot.js");
	page.injectJs("../jqplot/jqplot/plugins/jqplot.meterGaugeRenderer.js");
	
	page.evaluate(function(){
		$("body").append("<div id='chart2' style='width: 600px;height:400px;color: #000000'></div>");
		$.jqplot('chart2',[[13.5]],{
			title: 'Network Speed',
			seriesDefaults: {
				renderer: $.jqplot.MeterGaugeRenderer,
				rendererOptions: {
					intervals:[3,10,20],
		            intervalColors:['#66cc66', '#E7E658', '#cc6666'],
		        	label: 'aaaaaaaa'
		        }
			}
		});
		alert("OK");
		return null;
	});
	
	page.render("E:\\测试文件\\page.pdf",{format: 'pdf', quality: '100'});
	page.close();
	phantom.exit();
});

