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


		
$(function(){
	$('#byRegion').hide();
	$('#byDepartment').hide();
	$('#bySupportLeader').hide();
	
	var statisticsTypeAction = {'1':{container:'byRegion',queryMethod:StatisticsByRegionQuery},
			'2':{container:'byDepartment',queryMethod:StatisticsByDepartmentQuery},
			'3':{container:'bySupportLeader',queryMethod:StatisticsBySuppportLeaderQuery}};
	
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
	var query = statisticsTypeAction[$('#statisticsType2').val()]['queryMethod'];
	query(1);
	$('#'+statisticsTypeAction[$('#statisticsType2').val()]['container']+' input:radio').eq(0).click();
});

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
                    noSortColIndex:[0,1,2,3,4,5,6,7,8],
                    onRowSelect:null,
                    pageNumber: pageno,
                    colWidths: ["11%","11%","11%","11%","11%","11%","11%","11%","11%"]        
                  });       
    }
}
</script>
<%
	String statisticsType = request.getParameter("statisticsType");
	if(statisticsType==null)
		statisticsType="";
%>
<input type="hidden" id="statisticsType2" value="<%=statisticsType%>">
<div id="byRegion">
	<div>
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
		
	</div>
</div>

<div id="byDepartment">
	<div>
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
	
	</div>
</div>

<div id="bySupportLeader">
	<div>
		<label><input type="radio" name="displayType" id="list__bySupportLeader">列表</label>
		<label><input type="radio" name="displayType" id="chart__bySupportLeader">图形</label>
	</div>
	
	<div id="bySupportLeader__Div" class="list__bySupportLeader dataresult">
		<table id="bySupportLeader__table">
			<thead>
				<tr>
					<th>上级部门</th>
					<th>部门</th>
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
	
	</div>
</div>