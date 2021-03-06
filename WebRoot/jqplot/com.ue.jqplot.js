var com = {};
com.ue = {};
com.ue.jqplot = {
	chartTypes:{column:0,bar:1,line:2,pie:3},
	/**- 缓存：已创建图表的类型 -**/
	existChartTypes : new JsMap(),
	/**- 缓存：已创建图表的参数设置 -**/
	existChartSettings : new JsMap(),
	/**- 构建：柱/条形图表数据 -**/
	buildBarData:function(chartAllData,dataSeries,dataCategory){
		var dataObj = {dataList:[],series:[],category:[]};
		dataObj.category = dataCategory;
		$(dataSeries).each(function(i,nowSeries){
      		dataObj.series.push({label:nowSeries});
      		var seriesGroupData = [];
      		$(dataCategory).each(function(j,nowCategory){
      			var dataItem;
      			$(chartAllData).each(function(k,data){
	      			if(data.series == nowSeries && data.category == nowCategory){
	      				dataItem = data.data;
	      			}
      			});
      			seriesGroupData.push(dataItem);
      		});
      		
      		dataObj.dataList.push(seriesGroupData);
		});
		return dataObj;
	},
	/**- 构建：线形图表数据 -**/
	buildLineData:function(chartAllData,dataSeries,dataCategory){
		var dataObj = {dataList:[],series:[],category:[]};
		dataObj.category = dataCategory;
		$(dataSeries).each(function(i,nowSeries){
      		dataObj.series.push({label:nowSeries});
      		var seriesGroupData = [];
      		$(dataCategory).each(function(j,nowCategory){
      			var dataItem;
      			$(chartAllData).each(function(k,data){
	      			if(data.series == nowSeries && data.category == nowCategory){
	      				dataItem = data.data;
	      			}
      			});
      			seriesGroupData.push(dataItem);
      		});
      		
      		dataObj.dataList.push(seriesGroupData);
		});
		return dataObj;
	},
	/**- 构建：饼形图表数据 -**/
	buildPieData:function(chartAllData,dataSeries,dataCategory){
		var dataObj = {dataList:[],series:[],category:[]};
		dataObj.category = dataCategory;
			var nowSeries = dataSeries[0];
      		dataObj.series.push({label:nowSeries});
      		var seriesGroupData = [];
			$(chartAllData).each(function(k,data){
     			if(data.series == nowSeries){
     				var dataItem = [];
     				dataItem.push(data.category);
     				dataItem.push(data.data);
     				seriesGroupData.push(dataItem);
     			}
			});
      		
      		dataObj.dataList.push(seriesGroupData);
		return dataObj;
	},
	/**- 初始化图表数据 -**/
	initChartDataObject:function(containerId,settings,chartType){
		$("#"+containerId).html('');
		$("#"+containerId).css({'width' : settings.width, 'height' : settings.height});
		this.existChartSettings.put(containerId,settings);
		var chartDataJson = eval(settings.dataStr);
		var chartAllData = chartDataJson[0].datas;
		var dataSeries = chartDataJson[0].series;
		var dataCategory = chartDataJson[0].category;
		this.existChartTypes.put(containerId,chartType);
		switch(chartType){
			case this.chartTypes.column: 
			case this.chartTypes.bar: 
				return this.buildBarData(chartAllData,dataSeries,dataCategory);
			case this.chartTypes.line: 
				return this.buildLineData(chartAllData,dataSeries,dataCategory);
			case this.chartTypes.pie: 
				return this.buildPieData(chartAllData,dataSeries,dataCategory);
		}
		
	},
	/**- 创建柱形图表 -**/
	createColumnChart:function(containerId,settings){
		var dataObj = this.initChartDataObject(containerId,settings,this.chartTypes.column);
		$.jqplot(containerId, dataObj.dataList, {
			seriesDefaults:{
			    renderer:$.jqplot.BarRenderer,
			    pointLabels: { show: true },
			    rendererOptions: {fillToZero: true}
			},
			series:dataObj.series,
			legend: {
			    show: true,
			    placement: 'outsideGrid'
			},
			axes: {
			    xaxis: {
					renderer: $.jqplot.CategoryAxisRenderer,
					ticks: dataObj.category,
					tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					tickOptions: {angle: 30}
			    },
			    yaxis: {
			    	//autoscale: true,
					min: 0,
					max: null,
			    	tickInterval:1,
					tickOptions: {formatString: '%.4p'}
			    }
			}
		});
		
	},
	/**- 创建条形图表 -**/
	createBarChart:function(containerId,settings){
		var dataObj = this.initChartDataObject(containerId,settings,this.chartTypes.bar);
		$.jqplot(containerId, dataObj.dataList, {
			seriesDefaults: {
            	renderer:$.jqplot.BarRenderer,
                pointLabels: { show: true, location: 'e', edgeTolerance: -15 },
                shadowAngle: 135,
                rendererOptions: {
                    barDirection: 'horizontal'
                }
            },
            series:dataObj.series,
		    	legend: {
			        show: true,
			        placement: 'outsideGrid'
		    	},
            axes: {
                yaxis: {
                    renderer: $.jqplot.CategoryAxisRenderer,
                    ticks: dataObj.category,
                    tickRenderer: $.jqplot.CanvasAxisTickRenderer,
					tickOptions: {angle: 30}
                },
                xaxis: {
			    	min: 0,
			    	tickInterval:1,
			    	tickOptions: {formatString: '%.4p'}
			    }
            }
        });
	},
	/**- 创建线形图表 -**/
	createLineChart:function(containerId,settings){
		var dataObj = this.initChartDataObject(containerId,settings,this.chartTypes.line);
		$.jqplot(containerId, dataObj.dataList, {
			legend: {show:true,placement: 'outsideGrid'},
		    seriesDefaults: { pointLabels: { show: true }},
		    series:dataObj.series,
		    axes:{
		    	xaxis:{
		        	renderer: $.jqplot.CategoryAxisRenderer,
		        	ticks: dataObj.category,
		        	tickRenderer: $.jqplot.CanvasAxisTickRenderer,
		        	tickOptions: {angle: 30}
		    	}
		    }
		});
	},
	/**- 创建饼形图表 -**/
	createPieChart:function(containerId,settings){
		var dataObj = this.initChartDataObject(containerId,settings,this.chartTypes.pie);
		$.jqplot(containerId, dataObj.dataList, {
			seriesDefaults: {shadow: true, renderer: jQuery.jqplot.PieRenderer, rendererOptions: { showDataLabels: true } }, 
			legend: {show: true}
		});
	},
	/**- 更新图表数据 -**/
	chartDataUpdate:function(containerId,dataStr){
		var chartType = this.existChartTypes.get(containerId);
		var chartSetting = this.existChartSettings.get(containerId);
		chartSetting.dataStr = dataStr;
		switch(chartType){
			case this.chartTypes.column: 
				this.createColumnChart(containerId,chartSetting);break;
			case this.chartTypes.bar: 
				this.createBarChart(containerId,chartSetting);break;
			case this.chartTypes.line: 
				this.createLineChart(containerId,chartSetting);break;
			case this.chartTypes.pie: 
				this.createPieChart(containerId,chartSetting);break;
		}
	}
};