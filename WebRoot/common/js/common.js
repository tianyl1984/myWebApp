
String.prototype.replaceAll = function(s1,s2){
	return this.replace(new RegExp(s1,"gm"),s2);
};

function goUrl(url){
	location.href = url;
}

function replace(str){
	var result = str;
	var arr = arguments;
	for(var i=1;i<arr.length;i++){
		result = result.replaceAll("\\{" + (i-1) + "\\}",(typeof arr[i]) == "undefined"?"":arr[i]);
	}
	return result;
}

$(document).ready(function(){
	$("body").prepend("<input type='button' value='首页' onclick='window.location.href=\"" + CONTEXTPATH + "\"' ><br>");
});

(function($){
	
	$.fn.outerHTML = function() {
		return (!this.length) ? this : (this[0].outerHTML ||
		(function(el) {
			var div = document.createElement('div');
			div.appendChild(el.cloneNode(true));
			var contents = div.innerHTML;
			div = null;
			return contents;
		})(this[0]));
	};
	
})(jQuery);