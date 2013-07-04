/**
 * jQuery Log Fast & safe logging in fb,chr,ie console
 * 修改自jQueryLog，解决了ie下不能log的问题
 * @param mixed -
 *            as many parameters as needed
 * @return void
 * 
 * @url http://plugins.jquery.com/project/jQueryLog
 * @author Gleasy团队
 * @version 1.0
 * @example: $.log(someObj, someVar); $.log("%s is %d years old.", "Bob", 42);
 *           $('div.someClass').log().hide();
 */
(function(a) {
	a.log = function() {
		if($.browser.msie){
			if(typeof console != 'undefined' && typeof arguments != 'undefined'){
				var len = arguments.length;
				for(var i=0; i<len; i++){
					console.log(arguments[i]);
				}
			}
		}
		else if (window.console && window.console.log) {
			console.log.apply(window.console, arguments);
		}
	};
	a.fn.log = function() {
		a.log(this);
		return this;
	}
})(jQuery);
