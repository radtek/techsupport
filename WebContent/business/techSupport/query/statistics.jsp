<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
var statisticsTypeActionUrl = {'1':BUSNEISS_PATH+'/querylistStatisticsByRegion_statistics.action',
		'2':BUSNEISS_PATH+'/querylistStatisticsByDepartment.action',
		'3':BUSNEISS_PATH+'/querylistStatisticsBySupportLeader.action'};
		
$(function(){
	
});
</script>
<%
	String statisticsType = request.getParameter("statisticsType");
	if(statisticsType==null)
		statisticsType="";
%>
<input type="hidden" id="statisticsType2" value="<%=statisticsType%>">
<div>
	<div>
		<label><input type="radio" name="displayType" id="list">列表</label>
		<label><input type="radio" name="displayType" id="chart">图形</label>
	</div>
	
	<div id="byRegion__Div">
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
</div>

<div>
	<div>
		<label><input type="radio" name="displayType" id="list">列表</label>
		<label><input type="radio" name="displayType" id="chart">图形</label>
	</div>
	
	<div id="byDepartment__Div">
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
</div>

<div id="">
	<div>
		<label><input type="radio" name="displayType" id="list">列表</label>
		<label><input type="radio" name="displayType" id="chart">图形</label>
	</div>
	
	<div id="bySupportLeader__Div">
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
</div>