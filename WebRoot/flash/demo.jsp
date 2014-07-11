<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
	<head>
    	<title>Title</title>
    	<%@ include file="/common/jsp/htmlHeader.jsp"%>
    	<script type="text/javascript">

		</script>
	</head>
	<body>
		<div>
			swf url：<input type="text" id="swfurl" style="width:500" value="bb.swf"></br>
			hijackurl：<input type="text" id="csrfurl" style="width: 500" value="http://www.baidu.com"></br>
			<input type="button" value="submit" id="submit">
		</div>
		<iframe name="swf" style="width:1000;height:1000"></iframe>
		<script>
			function writeflashobject(url,parastr) {
				swf =window.frames["swf"];
				swf.document.write("<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0\"width=\"1000\" height=\"1000\" id=\"FlashVars\"align=\"middle\"\>\n");
				swf.document.write("<param name=\"allowScriptAccess\" value=\"always\"/\>\n");
				swf.document.write("<param name=\"movie\" value=\"FlashVars.swf\" /\>\n");
				swf.document.write("<param name=\"FlashVars\" value=\""+ parastr +"\"/\>\n");
				swf.document.write("<param name=\"quality\" value=\"high\" /\>\n");
				swf.document.write("<param name=\"bgcolor\" value=\"#ffffff\" /\>\n");
				swf.document.write("<embed src=\""+url+"\" quality=\"high\"bgcolor=\"#ffffff\" width=\"550\" height=\"400\"name=\"FlashVars\" align=\"middle\" allowScriptAccess=\"always\"FlashVars=\""+ parastr +"\"type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\"/\>");
				swf.document.write("</object\>");
				swf.document.close();
			}
		
// 		function get (name) {
// 		                   var query =window.location.search.substring(1);
// 		                   var pairs =query.split("&");
// 		                   for (var i = 0; i<pairs.length; i++)
// 		                   {
// 		                            var pos=pairs[i].indexOf('=');
// 		                            if(pos ==-1)continue;
// 		                            var argname =pairs[i].substring(0,pos);
// 		                            var value =pairs[i].substring(pos+1);
// 		                            if (argname == name){return value;}
// 		                   };
// 		         }
			var submit = document.getElementById("submit");
			submit.addEventListener("click",function() {
		         var swfurl = document.getElementById("swfurl").value;
		         var param = "url="+document.getElementById("csrfurl").value;//"url="+get("csrfurl");
		         writeflashobject(swfurl,param);
		         return false;
			});
		</script>
	</body>
</html>