/**
 * com.ue.box 盒子模型
 *
 * Copyright 2011, wupeigui
 * 
 */ 

box = {
	/**
	 * 盒子模型一
	 * @author wpg
	 */	
	box1:function(){
	    if ($(".box1").length > 0) {
	    	
	        $(".box1").each(function() {
	        	
	        	if($(this).find(".box1-header").length<=0){
	        	
	        		alert('')
	        		
		        	var E = $(this).html();
		        	
	            	var box_content = [];
	            	
	            	box_content.push('<div class="box1-header">');
		            box_content.push('	<h2></h2>');
	            	box_content.push('	<div class="box1-header-ctrls">');
	            	box_content.push('		<a class="close" title="" href="javascript:void(null);"></a>');
	            	box_content.push('	</div>');
	            	box_content.push('</div>');
	            	
	            	$(this).wrapInner('<div class="box1-content"></div>');
	            	
	            	
	            	if ($(this).attr("angle") != null && $(this).attr("angle") == "corner") {
	            		$(this).addClass("corners shadow");
	            	}
	            	
	            	$(this).prepend($(box_content.join("")));
	            	
	            	
	            	if ($(this).attr("boxTitle") != null) {
	            		//var result = String($(this).attr("boxTitle")).replace(/&/g, "&amp;").replace(/\"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
	            		alert($(this).attr("boxTitle"))
	                	$(this).find(".box1-header h2").append($(this).attr("boxTitle"));
	                	//alert($(this).attr("boxTitle"))
	                	//alert($(this).find(".box1-header h2").html());
	            	}
	            	
	            	box.resetWidth($(this));

		            if ($(this).attr("boxHeight") != null) {
		                $(this).find(".box1-content").height($(this).attr("boxHeight"));
		            }
	            	
	            	if ($(this).attr("boxState") != null && $(this).attr("boxState") == "close") {
	            		
		                $(this).find(".box1-content").slideToggle(200);
		                $(this).find(".box1-header-ctrls a").removeClass("close").addClass("open");
		            }
	            	
	            	if ($(this).attr("boxState") != null && $(this).attr("boxState") == "false") {
	            		$(this).find(".box1-header-ctrls").addClass("none");
	            	}
            	
	        	}
            	
	        });
	        
	        this.control();
	            
	    }
		
	},
	resetWidth:function(obj){
		obj.each(function() {

			if ($(this).attr("boxWidth") != null) {
	            var C = $(this).attr("boxWidth");
	            var J = C.substr(C.length - 1, 1);
	            if (J == "%") {
	                $(this).width(C);
	                //百分百时减去边距宽度
	                //var S = Number($(this).width()-($(this).outerWidth()-$(this).width()));
	                //$(this).width(S);
	                
	            } else {
	                var e = Number($(this).attr("boxWidth"));
	                $(this).width(e)
	            }
	        }
	        else{
	        	
	        	$(this).outerWidth("100%");
	        	//var P = Number($(this).width()-($(this).outerWidth()-$(this).width()));
	        	//$(this).width(P);
	        	 
	        }
		})
	},
	/**
	 * 盒子模型二
	 * @author wpg
	 */		
	box2:function(){
		
	    if ($(".box2").length > 0) {
	    	
	        $(".box2").each(function() {
	        	
	        	
			});
	        
		}   
		
	},
	/**
	 * 盒子收起/展开控制
	 * @author wpg
	 */	
	control:function(){
		$(".box1-header-ctrls a").unbind('click');
		
		$(".box1-header-ctrls a").bind('click', function() {
				
			$(this).parents(".box1-header").next(".box1-content").slideToggle(200);
			
			var btnclass=$(this).attr("class");
			
			if(btnclass=="open"){
				$(this).removeClass("open").addClass("close");
			}else{
				$(this).removeClass("close").addClass("open");
			}
		});
		this.spinner();
		this.resetWidth($('.box1'));
		
	},	
	/**
	 * 加载标志
	 * @author wpg
	 */		
	spinner:function(){
		$(".box1-header-ctrls").prepend('<span class="spin" alt=""></span>');
		$(".close, .focus").click(function(){
			$(this).parent(".box1-header-ctrls").find("span.spin").fadeIn(400).show(600,function(){
				$("span.spin").fadeOut(350);
			});
		});
	}
}
