<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
var statisticsWidth=800;
var regionDiv='byRegion__Div';
var regionTableId='byRegion__table';
var regionTable;
var regionURL=BUSNEISS_PATH+'/querylistStatisticsByRegion_statistics.action';
var departmentDiv='byDepartment__Div';
var departmentTableId='byDepartment__table';
var departmentTable;
var departmentURL=BUSNEISS_PATH+'/querylistStatisticsByDepartment_statistics.action';
var supportLeaderDiv='bySupportLeader__Div';
var supportLeaderTableId='bySupportLeader__table';
var supportLeaderTable;
var supportLeaderURL=BUSNEISS_PATH+'/querylistStatisticsBySupportLeader_statistics.action';
var sessionStatisticsDataURL = BUSNEISS_PATH + '/querySessionStatisticsData_statistics.action';

		
$(function(){
	$('#byRegion').hide();
	$('#byDepartment').hide();
	$('#bySupportLeader').hide();
	//statisticsTypeAction:key = 1 表示按区域统计,key = 2表示按部门统计, key = 3 表示按负责人统计
	var statisticsTypeAction = {'1':{container:'byRegion',queryMethod:StatisticsByRegionQuery},
			'2':{container:'byDepartment',queryMethod:StatisticsByDepartmentQuery},
			'3':{container:'bySupportLeader',queryMethod:StatisticsBySuppportLeaderQuery}};
// 	区域统计图表构建
	statisticsTypeAction['1']['buildChartMethod'] = function(ldata){
		
	};
	
	initStatisticsByRegionQuery(regionDiv);
	initStatisticsByDepartmentQuery(departmentDiv);
	initStatisticsBySuppportLeaderQuery(supportLeaderDiv);
	
	$('.dataresult').hide();
	$('input:radio[name="displayType"]').each(function(){
		var _radio = $(this);
		_radio.click(function(){
			$('.dataresult').hide();
			$('.'+_radio.attr('id')).show();
		});
	});
	$('#'+statisticsTypeAction[$('#statisticsType2').val()]['container']).show();
	statisticsTypeAction[$('#statisticsType2').val()]['queryMethod'](1);
// 	buildChart($('#'+statisticsTypeAction[$('#statisticsType2').val()]['container']),statisticsTypeAction[$('#statisticsType2').val()]['buildChartMethod']);
	$('#'+statisticsTypeAction[$('#statisticsType2').val()]['container']+' input:radio').eq(0).click();
});
function buildChart(container,func){
	$.post(sessionStatisticsDataURL,null,function(data){
		if(data.lStatistics){
			var chartContainers = container.find('.chart');
			
			var pieData = [];
			var pieTotal=data.lStatistics[data.lStatistics.length-1].statusWaitCompanyApprovalCount+
			data.lStatistics[data.lStatistics.length-1].statusWaitDepartmentApprovalCount+
			data.lStatistics[data.lStatistics.length-1].statusGoingCount+
			data.lStatistics[data.lStatistics.length-1].statusWaitFeedbackCount+
			data.lStatistics[data.lStatistics.length-1].statusFeedbackCount+
			data.lStatistics[data.lStatistics.length-1].statusGoneCount+
			data.lStatistics[data.lStatistics.length-1].statusPauseCount+
			data.lStatistics[data.lStatistics.length-1].statusStopCount;
			
// 			for(var i=0;i< data.lStatistics.length-1 ; i++){
// 				var x = data.lStatistics[i].statusWaitCompanyApprovalCount+
// 				data.lStatistics[i].statusWaitDepartmentApprovalCount+
// 				data.lStatistics[i].statusGoingCount+
// 				data.lStatistics[i].statusWaitFeedbackCount+
// 				data.lStatistics[i].statusFeedbackCount+
// 				data.lStatistics[i].statusGoneCount+
// 				data.lStatistics[i].statusPauseCount+
// 				data.lStatistics[i].statusStopCount;
// 				var data = [data.lStatistics[i].regionName,x/pieTotal];
// 				pieData.push(data);
// 			}
			chartContainers.eq(0).highcharts({
				chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
		        },
		        tooltip: {
		    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    color: '#000000',
		                    connectorColor: '#000000',
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		                }
		            }
		        },
		        series: [{
		            type: 'pie',
		            name: '区域统计',
		            data: [
		                ['Firefox',   45.0],
		                ['IE',       26.8],
		                {
		                    name: 'Chrome',
		                    y: 12.8,
		                    sliced: true,
		                    selected: true
		                },
		                ['Safari',    8.5],
		                ['Opera',     6.2],
		                ['Others',   0.7]
		            ]
		        }]
			});
		}
	},'json');
}
function initStatisticsByRegionQuery(divpageid){
	regionTable=$("#"+divpageid).html();
	StatisticsByRegionQuery(1,'#');
} 
function setStatisticsByRegionQuery(pageno,url){
 $("#"+regionDiv).html(regionTable);
 createXML("p_");
 if (url==null || url=="undefined"){
   url=regionURL;
 }
 return url;
}
	 
function StatisticsByRegionQuery(pageno,url){

  if (true){
    url=setStatisticsByRegionQuery(pageno,url);
    // create the grid
    // returns a jQ object with a 'g' property - that's ingrid
    var mygrid1 = $("#"+regionTableId).ingrid({
                    url: url, 
                    height:ingridHeight,
                    ingridPageWidth: statisticsWidth,
                    ingridPageParams:sXML,
                    noSortColIndex:[0,1,2,3,4,5,6,7,8],
                    onRowSelect:null,
                    pageNumber: pageno,
                    colWidths: ["11%","11%","11%","11%","11%","11%","11%","11%","11%"]        
                  });       
    }
}

function initStatisticsByDepartmentQuery(divpageid){
	departmentTable=$("#"+divpageid).html();
	StatisticsByDepartmentQuery(1,'#');
} 
function setStatisticsByDepartmentQuery(pageno,url){
 $("#"+departmentDiv).html(departmentTable);
 createXML("p_");
 if (url==null || url=="undefined"){
   url=departmentURL;
 }
 return url;
}
	 
function StatisticsByDepartmentQuery(pageno,url){

  if (true){
    url=setStatisticsByDepartmentQuery(pageno,url);
    // create the grid
    // returns a jQ object with a 'g' property - that's ingrid
    var mygrid1 = $("#"+departmentTableId).ingrid({
                    url: url, 
                    height:ingridHeight,
                    ingridPageWidth: statisticsWidth,
                    ingridPageParams:sXML,
                    noSortColIndex:[0,1,2,3,4,5,6,7,8],
                    onRowSelect:null,
                    pageNumber: pageno,
                    colWidths: ["11%","11%","11%","11%","11%","11%","11%","11%","11%"]        
                  });       
    }
}

function initStatisticsBySuppportLeaderQuery(divpageid){
	supportLeaderTable=$("#"+divpageid).html();
	StatisticsBySuppportLeaderQuery(1,'#');
} 
function setStatisticsBySuppportLeaderQuery(pageno,url){
 $("#"+supportLeaderDiv).html(supportLeaderTable);
 createXML("p_");
 if (url==null || url=="undefined"){
   url=supportLeaderURL;
 }
 return url;
}
	 
function StatisticsBySuppportLeaderQuery(pageno,url){

  if (true){
    url=setStatisticsBySuppportLeaderQuery(pageno,url);
    // create the grid
    // returns a jQ object with a 'g' property - that's ingrid
    var mygrid1 = $("#"+supportLeaderTableId).ingrid({
                    url: url, 
                    height:ingridHeight,
                    ingridPageWidth: statisticsWidth,
                    ingridPageParams:sXML,
                    noSortColIndex:[0,1,2,3,4,5,6,7,8,9],
                    onRowSelect:null,
                    pageNumber: pageno,
                    ingridComplete:function(){
                    	var $table = $('#'+supportLeaderDiv+' table');
                    	$table.find('tr').each(function(idx){
                    		var _tr = $(this);
                    		if(_tr.find('td:nth(0)').html() == _tr.find('td:nth(1)').html()){
                    			_tr.find('td:nth(1)').remove();
                    			_tr.find('td:nth(0)').attr('colSpan',2);
                    		}
                    			
                    	});
                    },
                    colWidths: ["10%","10%","10%","10%","10%","10%","10%","10%","10%","10%"]        
                  });       
    }
}
</script>
<%
	String statisticsType = request.getParameter("statisticsType");
	if(statisticsType==null)
		statisticsType="";
%>

<style>
	.chart {
		float: left;
		margin: 2px 5px 2px 5px;
	}
	.bar {
		display: none;
	}
</style>
<input type="hidden" id="statisticsType2" value="<%=statisticsType%>">
<div id="byRegion">
	<div class="bar">
		<label><input type="radio" name="displayType" id="list__byRegion">列表</label>
		<label><input type="radio" name="displayType" id="chart__byRegion">图形</label>
	</div>
	
	<div id="byRegion__Div" class="list__byRegion dataresult">
		<table id="byRegion__table">
			<thead>
				<tr>
					<th>大区</th>
					<th>待公司审批</th>
					<th>待部门审批</th>
					<th>进行中</th>
					<th>待反馈</th>
					<th>已反馈</th>
					<th>已完成</th>
					<th>已暂停</th>
					<th>已中止</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="byRegion__chart" class="chart__byRegion dataresult">
		<div class="chart"></div>
		<div class="chart"></div>
	</div>
</div>

<div id="byDepartment">
	<div class="bar">
		<label><input type="radio" name="displayType" id="list__byDepartment">列表</label>
		<label><input type="radio" name="displayType" id="chart__byDepartment">图形</label>
	</div>
	
	<div id="byDepartment__Div" class="list__byDepartment dataresult">
		<table id="byDepartment__table">
			<thead>
				<tr>
					<th>部门</th>
					<th>部门审批不通过</th>
					<th>待部门审批</th>
					<th>进行中</th>
					<th>待反馈</th>
					<th>已反馈</th>
					<th>已完成</th>
					<th>已暂停</th>
					<th>已中止</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="byDepartment__chart" class="chart__byDepartment dataresult">
		<div class="chart"></div>
		<div class="chart"></div>
	</div>
</div>

<div id="bySupportLeader">
	<div class="bar">
		<label><input type="radio" name="displayType" id="list__bySupportLeader">列表</label>
		<label><input type="radio" name="displayType" id="chart__bySupportLeader">图形</label>
	</div>
	
	<div id="bySupportLeader__Div" class="list__bySupportLeader dataresult">
		<table id="bySupportLeader__table">
			<thead>
				<tr>
					<th colspan="2">部门</th>
					<th>负责人</th>
					<th>待部门审批</th>
					<th>进行中</th>
					<th>待反馈</th>
					<th>已反馈</th>
					<th>已完成</th>
					<th>已暂停</th>
					<th>已中止</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div id="bySupportLeader__chart" class="chart__bySupportLeader dataresult">
		<div class="chart"></div>
		<div class="chart"></div>
	</div>
</div>