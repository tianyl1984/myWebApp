function graphTrace(pid) {
    // 获取图片资源
    var imageUrl = CONTEXTPATH + "/fr/financialReport!showDiagram.action";
    $.getJSON(CONTEXTPATH + '/fr/financialReport!trace.action?pid=' + pid, function(result) {
        var positionHtml = "";

        var infos = result.infos;
        // 生成图片
        var varsArray = new Array();
        $.each(infos, function(i, v) {
            var $positionDiv = $('<div/>', {
                'class': 'activity-attr'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 2),
                height: (v.height - 2),
                backgroundColor: 'black',
                zIndex: $.fn.qtip.zindex - 1,
                opacity: 0
            });

            // 节点边框
            var $border = $('<div/>', {
                'class': 'activity-attr-border'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 4),
                zIndex: $.fn.qtip.zindex - 2,
                height: (v.height - 3),
            });

            //当前
            if (v.currentActiviti) {
                $border.addClass('ui-corner-all-12').css({
                    border: '3px solid red'
                });
            }
            
            //历史
            if (v.inHistory && !v.currentActiviti) {
                $border.addClass('ui-corner-all-12').css({
                    border: '3px solid green'
                });
            }
            
            positionHtml += $positionDiv.outerHTML() + $border.outerHTML();
            varsArray[varsArray.length] = v.vars;
        });
        
        var content = "<div><img src='" + imageUrl + "' style='position:absolute; left:0px; top:0px;' />";
        content += "<div id='processImageBorder' style='position:absolute; left:" + result.left + "px; top:" + result.top + "px;'>" + positionHtml + "</div></div>";
        
		art.dialog({
		    title: '流程图',
		    content: content,
		    lock : true,
		    height : 500,
		    width: 800,
		    init: function(){
		        // 设置每个节点的data
		        $('.activity-attr').each(function(i, v) {
		            $(this).data('vars', varsArray[i]);
		        });

		        $('.activity-attr').qtip({
		            content: function() {
		                var vars = $(this).data('vars');
		                var tipContent = "<table class='need-border'>";
		                $.each(vars, function(varKey, varValue) {
		                    if (varValue) {
		                        tipContent += "<tr><td class='label'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
		                    }
		                });
		                tipContent += "</table>";
		                return tipContent;
		            },
		            position: {
		                at: 'bottom left',
		                adjust: {
		                    x: 3
		                }
		            }
		        });
		    },
		    ok: function(){
		        
		    },
		    cancel:function(){
		    	
		    }
		});

        return;
        // 打开对话框
        $('#workflowTraceDialog').dialog({
            modal: true,
            resizable: false,
            dragable: false,
            open: function() {
                $('#workflowTraceDialog').dialog('option', 'title', '查看流程（按ESC键可以关闭）<button id="changeImg">如果坐标错乱请点击这里</button><button id="diagram-viewer">Diagram-Viewer</button>');
                $('#workflowTraceDialog').css('padding', '0.2em');
                $('#workflowTraceDialog .ui-accordion-content').css('padding', '0.2em').height($('#workflowTraceDialog').height() - 75);

                // 此处用于显示每个节点的信息，如果不需要可以删除
                $('.activity-attr').qtip({
                    content: function() {
                        var vars = $(this).data('vars');
                        var tipContent = "<table class='need-border'>";
                        $.each(vars, function(varKey, varValue) {
                            if (varValue) {
                                tipContent += "<tr><td class='label'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
                            }
                        });
                        tipContent += "</table>";
                        return tipContent;
                    },
                    position: {
                        at: 'bottom left',
                        adjust: {
                            x: 3
                        }
                    }
                });
                // end qtip
            },
            close: function() {
                $('#workflowTraceDialog').remove();
            },
            width: document.documentElement.clientWidth * 0.95,
            height: document.documentElement.clientHeight * 0.95
        });

    });
}
