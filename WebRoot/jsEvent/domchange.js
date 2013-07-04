(function($) {
	
	function isDOMAttrModifiedSupported() {
		var p = document.createElement('p');
		var flag = false;
		if(p.addEventListener) {
			p.addEventListener('DOMAttrModified', function() {
				flag = true;
			}, false);
		}else if(p.attachEvent) {
			p.attachEvent('onDOMAttrModified', function() {
				flag = true;
			});
		}else {
			flag = false;
		}
		return flag;
   }
   //initialize Mutation Observer
   var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
   $.fn.attrchange = function(callback) {
		if (MutationObserver) {//FF,Chrome
			var options = {
				subtree: true,
				attributes: true,
				attributeOldValue: true
			};
			var observer = new MutationObserver(function(mutations) {
				mutations.forEach(function(e) {
					callback.call(e.target, e, e.attributeName);
				});
			});
			return this.each(function() {
				observer.observe(this, options);
			});
		} else if (isDOMAttrModifiedSupported()) { //Opera
			return this.on('DOMAttrModified', function(e) {
				callback.call(this, e, e.attrName);
			});
		} else if ('onpropertychange' in document.body) { //works only in IE		
			return this.on('propertychange', function(e) {
				callback.call(this, e, window.event.propertyName);
			});
		}
		return this;
    };
})(jQuery);
