<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<script type="text/javascript">
var CONTEXTPATH = '${path}';
</script>

<script type="text/javascript" src="${path }/common/js/jquery-1.6.2.js"></script>
<%-- <script type="text/javascript" src="${path }/common/js/jquery-1.7.2.min.js"></script> --%>

<script type="text/javascript" src="${path }/common/js/jquery.MultiFile.js"></script>
<script type="text/javascript" src="${path }/common/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="${path }/common/js/jquery.jqGrid.src.js"></script>
<script type="text/javascript" src="${path }/common/js/box.js"></script>
<script type="text/javascript" src="${path }/common/js/common.js"></script>
<script type="text/javascript" src="${path }/common/js/template.js"></script>
<script type="text/javascript" src="${path }/common/js/jsMap.js"></script>

<script type="text/javascript" src="${path }/common/js/artdialog/artDialog.js"></script>
<script type="text/javascript" src="${path }/common/js/artdialog/artDialog.drag.js"></script>
<script type="text/javascript" src="${path }/common/js/artdialog/artDialog.iframeTools.js"></script>
<script type="text/javascript" src="${path }/common/js/artdialog/artDialog.JQuery.js"></script>

<!-- artdialog5.0 -->
<%-- <link href="${path }/common/js/artdialog5.0/skins/default.css" rel="stylesheet" /> --%>
<%-- <script src="${path }/common/js/artdialog5.0/source/artDialog.js"></script> --%>
<%-- <script src="${path }/common/js/artdialog5.0/source/artDialog.plugins.js"></script> --%>
<%-- <script src="${path }/common/js/artdialog5.0/source/jquery.artDialog.js"></script> --%>

<link rel="stylesheet" href="${path }/common/css/ui.jqgrid.css" />
<link rel="stylesheet" href="${path }/common/css/box.css" />
<link rel="stylesheet" href="${path }/common/css/table.css" />
<link rel="stylesheet" href="${path }/common/css/popupLayer/css/default.css" />
<script>
jQuery.ajaxSetup({
	cache : false,
	error : function(){
		alert("出错啦！");
	}
});
</script>