<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<html>
  <head>
    <title>index.jsp</title>
    <%@ include file="/common/jsp/htmlHeader.jsp"%>
    <script type="text/javascript" src="${path }/fp/js/swfupload.js"></script>
	<script type="text/javascript" src="${path }/fp/js/swfupload.queue.js"></script>
	<script type="text/javascript" src="${path }/fp/js/fileprogress.js"></script>
	<script type="text/javascript" src="${path }/fp/js/handlers.js"></script>
	<script type="text/javascript">
	var swfu = {};
	$(document).ready(function(){
        $('#myfileupload').MultiFile({
            max: 5,
            STRING: {
                remove:"[删除]",
                selected: '文件：: $file',
                denied: '不支持上传 $ext 格式的文件!',
                duplicate: '文件已经在上传列表中: $file'
            }
        });
		var settings = {
				flash_url : "${path}/fp/js/swfupload.swf", // 指定flash 位置
				upload_url: "${path}/swfUploadServlet", // 处理上传的servlet
				file_size_limit : "1 GB", //设置每个文件最大值
				file_types : "*",          // 准许上传的文件类型 *.gif
				file_types_description : "All Files",
				file_upload_limit : 0,      //设置文件上传的个数 0表示没有限制
				file_queue_limit : 0,
				custom_settings : {
					progressTarget : "fsUploadProgress", //下面div的Id
					cancelButtonId : "btnCancel"
				},
				debug: false,

				// 按钮设置
			 	button_image_url: "${path}/fp/XPButtonNoText_61x22.png", 
				button_width: "61",
				button_height: "22",
				button_placeholder_id: "spanButtonPlaceHolder",
				button_text: '<span class="theFont">上传</span>',
				button_text_style: ".theFont { font-size: 12; }",
				button_text_left_padding: 12,
				button_text_top_padding: 3, 
				button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
				button_cursor: SWFUpload.CURSOR.HAND,
				
				// handlers.js 中事件
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,
				queue_complete_handler : queueComplete	// Queue plugin event
		};

		swfu = new SWFUpload(settings);
<%--		swfu.setFileTypes('*.avi;*.wmv;*.flv;*.mp4;*.rmvb;*.mpg', "All Files(*.avi;*.wmv;*.flv;*.mp4;*.rmvb;*.mpg)");--%>
	});
	</script>
  </head>
  <body>
	<form action="${path}/fp/fileUpload!upload.action" method="post" enctype="multipart/form-data">
		<table width="100%">
			<tbody>
				<tr><td>名称：</td><td><input type="text" name="aaa" value="OK"/></td></tr>
				<tr><td>文件：</td><td><input type="file" id="myfileupload" name="myfileupload" contenteditable="false"/></td></tr>
				<tr>
					<td>文件：</td>
					<td>
					<span id="spanButtonPlaceHolder"></span>
					<div class="fieldset flash" id="fsUploadProgress"><span class="legend">上传列表</span></div>
					<input id="btnCancel" type="button" value="取消所有上传 " onclick="swfu.cancelQueue();" disabled="disabled" style="margin-left: 2px; font-size: 8pt; height: 29px;" />
					<input id="btn" type="button" value="取消上传" onclick="swfu.cancelUpload();" style="margin-left: 2px; font-size: 8pt; height: 29px;" />
					</td>
				</tr>
				<tr><td colspan="2"><input type="submit" value="提交" /></td></tr>
			</tbody>
		</table>
	</form>
  </body>
  <script type="text/javascript">

  </script>
</html>