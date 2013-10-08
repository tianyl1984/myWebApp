com = {};
com.ue = {};
com.ue.pqgrid = {
	changeTable:function(tabId,divId){
		var $div = $("#" + divId);
		var $tbl = $("#" + tabId);
		var $tbody = $("tbody",$tbl);
		var $thead = $("thead",$tbl);
		var data = [];
		var tdAttr = [];
		$tbody.find("tr").each(function(i,tr){
			var $tr=$(tr);
			var arr2=[];
			var arr3 = [];
			$tr.find("td").each(function(j,td){
				arr2.push($.trim($(td).html()));
				var att = {};
				if(td.rowSpan != 1){
					att.rowSpan = td.rowSpan;
				}
				if(td.colSpan != 1){
					att.colSpan = td.colSpan;
				}
				arr3.push(att);
				
				if($(this).attr("colspan") && $(this).attr("colspan") > 1){
					var colspan = $(this).attr("colspan");
					for(var k=0;k<colspan-1;k++){
//						arr2.push("");
					}
				}
			});
			data.push(arr2);
			tdAttr.push(arr3);
		});
		
		
		var newObj = {};
		newObj.width = $div.width();
		newObj.height = $div.height();
		newObj.resizable = false;
		var freezeCols = $tbl.attr("freezeCols");
		if(freezeCols){
			newObj.freezeCols = freezeCols;
		}
		newObj.editable = false;
		newObj.numberCell = false;
        newObj.dataModel = {data:data};
        newObj.colModel = [];
        newObj.tdAttrs = tdAttr;
        
        //目前只支持rowspan最大为2的
        var rowSize = $("tr",$thead).size();
        $("tr",$thead).each(function(i){
        	$("th",$(this)).each(function(j){
	        	var col = {};
	        	var $th = $(this);
        		col.title = $th.html();
        		col.sortable = $th.attr("sortable") == "true";
        		col.width = $th.attr("width")?$th.attr("width"):$th.outerWidth();
        		col.dataType = $th.attr("dataType");
        		if(col.sortable && !col.dataType){
        			col.dataType = "integer";
        		}
        		col.rowspan = $th.attr("rowspan")?parseInt($th.attr("rowspan"),10):1;
        		col.colspan = $th.attr("colspan")?parseInt($th.attr("colspan"),10):1;
        		if(col.rowspan == 1 && i < rowSize - 1){//只占一行
	        		col.colModel = [];
        		}
        		if(i == 0){
	        		newObj.colModel.push(col);
        		}else{
        			var rows = newObj.colModel;
        			for(var k=0;k<rows.length;k++){
        				var row = newObj.colModel[k];
        				if(!row.colModel){
        					continue;
        				}
    					if(row.colModel.length == row.colspan){
    						continue;
    					}else{
        					row.colModel.push(col);
        					break;
    					}
        			}
        		}
        	});
        });
        $div.pqGrid(newObj);
	}
};
