
/* Demo Note:  This demo uses a FileProgress class that handles the UI for displaying the file name and percent complete.
The FileProgress class is not part of SWFUpload.
*/
/* **********************
   Event Handlers
   These are my custom event handlers to make my
   web application behave the way I went when SWFUpload
   completes different tasks.  These aren't part of the SWFUpload
   package.  They are part of my application.  Without these none
   of the actions SWFUpload makes will show up in my application.
   ********************** */
function fileQueued(file) {
	try {

		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setStatus("");
		progress.toggleCancel(true, this);
	}
	catch (ex) {
		this.debug(ex);
	}
}
function fileQueueError(file, errorCode, message) {
	try {
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
			alert("\u60a8\u9009\u62e9\u7684\u6587\u4ef6\u592a\u591a.\n" + (message === 0 ? "\u5df2\u7ecf\u8fbe\u5230\u6700\u5927\u9650\u5236." : "\u6700\u591a\u9009\u62e9 " + (message > 1 ? " " + message + "\u4e2a\u6587\u4ef6." : "\u4e00\u4e2a\u6587\u4ef6.")));
			return;
		}
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);
		switch (errorCode) {
		  case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			progress.setStatus("\u6587\u4ef6\u592a\u5927.");
			this.debug("Error Code: File too big, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		  case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			progress.setStatus("\u4e0d\u80fd\u4e0a\u4f200\u5b57\u8282\u7684\u6587\u4ef6.");
			this.debug("Error Code: Zero byte file, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		  case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			progress.setStatus("\u65e0\u6548\u7684\u6587\u4ef6\u7c7b\u578b.");
			this.debug("Error Code: Invalid File Type, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		  default:
			if (file !== null) {
				progress.setStatus("\u672a\u5904\u7406\u7684\u9519\u8bef.");
			}
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		}
	}
	catch (ex) {
		this.debug(ex);
	}
}
function fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		if (numFilesSelected > 0) {
			document.getElementById(this.customSettings.cancelButtonId).disabled = false;
		}
		/* I want auto start the upload and I can do that here */
		this.startUpload();
	}
	catch (ex) {
		this.debug(ex);
	}
}
function uploadStart(file) {
	try {
		/* I don't want to do any file validation or anything,  I'll just update the UI and
		return true to indicate that the upload should start.
		It's important to update the UI here because in Linux no uploadProgress events are called. The best
		we can do is say we are uploading.
		 */
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setStatus("\u6b63\u5728\u4e0a\u4f20...");
		progress.toggleCancel(true, this);
	}
	catch (ex) {
	}
	return true;
}
function uploadProgress(file, bytesLoaded, bytesTotal) {
	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setProgress(percent);
		progress.setStatus("\u6b63\u5728\u4e0a\u4f20...");
		var status = document.getElementById("divStatus");
		status.innerHTML = percent + "%";
		document.getElementById("submitButton").disabled = true;
		document.getElementById("gobackButton").disabled = true;
	}
	catch (ex) {
		this.debug(ex);
	}
}
function uploadSuccess(file, serverData) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setComplete();
		progress.setStatus("\u4e0a\u4f20\u5b8c\u6bd5.");
		progress.toggleCancel(false);
		
       document.getElementById("attid").value=serverData;
 
     
	}
	catch (ex) {
		this.debug(ex);
	}
}
function uploadError(file, errorCode, message) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);
		switch (errorCode) {
		  case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			progress.setStatus("\u4e0a\u4f20\u9519\u8bef: " + message);
			this.debug("Error Code: HTTP Error, File name: " + file.name + ", Message: " + message);
			break;
		  case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			progress.setStatus("\u4e0a\u4f20\u5931\u8d25.");
			this.debug("Error Code: Upload Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		  case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			progress.setStatus("\u670d\u52a1\u8bfb\u5199\u9519\u8bef");
			this.debug("Error Code: IO Error, File name: " + file.name + ", Message: " + message);
			break;
		  case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			progress.setStatus("\u5b89\u5168\u6027\u9519\u8bef");
			this.debug("Error Code: Security Error, File name: " + file.name + ", Message: " + message);
			break;
		  case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			progress.setStatus("\u4e0a\u4f20\u9650\u5236.");
			this.debug("Error Code: Upload Limit Exceeded, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		  case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
			progress.setStatus("\u9a8c\u8bc1\u5931\u8d25,\u4e0a\u4f20\u8df3\u8fc7.");
			this.debug("Error Code: File Validation Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		  case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			// If there aren't any files left (they were all cancelled) disable the cancel button
			if (this.getStats().files_queued === 0) {
				document.getElementById(this.customSettings.cancelButtonId).disabled = true;
			}
			progress.setStatus("\u5df2\u53d6\u6d88");
			progress.setCancelled();
			break;
		  case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			progress.setStatus("\u5df2\u505c\u6b62");
			break;
		  default:
			progress.setStatus("\u672a\u5904\u7406\u7684\u9519\u8bef: " + errorCode);
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		}
	}
	catch (ex) {
		this.debug(ex);
	}
}

function uploadComplete(file) {
var currid=$("#attid").val();
var delspan="delspan('"+currid+"')";
var cont="<span id='" + currid+ "'>" + file.name+ "<a href='#' onclick="+delspan+">\u3010\u5220\u9664\u3011</a>,</span><br/>";
$("#show").children().last().append(cont);

	if (this.getStats().files_queued === 0) {
		document.getElementById(this.customSettings.cancelButtonId).disabled = true;
		var status = document.getElementById("divStatus");
		status.innerText = "";
	}
}
//set deleted span value
function delspan(num){

var delspantxt = document.getElementById(num).innerText;
var ss=delspantxt.replace(/【删除】,/g," ");
var delspanname=document.getElementById("deletespan").value;
delspanname+=ss+",";
document.getElementById("deletespan").value=delspanname;
document.getElementById(num).innerText="";
$("#attid").val("");
var modelid=document.getElementById("modelid").value;

$.ajax({
		   type: "POST",
		   url: "${path}/mr/mediaresource!delbeforesave.action?mid="+modelid+"&deletespanvalue="+num,
		   success: function(msg){
		   }
		});

swfu.setStats({successful_uploads : 0});
}
// This event comes from the Queue Plugin
function queueComplete(numFilesUploaded) {
	//var status = document.getElementById("divStatus");
	//status.innerHTML = numFilesUploaded + "个文件已上传";
	var allnum=document.getElementById("allfiles").value;
	if(allnum==''){
	allnum=0;
	}
	allnum=parseInt(allnum)+numFilesUploaded;
	document.getElementById("allfiles").value=allnum;
	document.getElementById("submitButton").disabled = false;
	document.getElementById("gobackButton").disabled = false;
}
/**********************************************************************************************************/

