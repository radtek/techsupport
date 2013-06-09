<%@page import="com.aisino2.techsupport.common.Constants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../../public/common.jsp" %>
<%@include file="../common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支持单恢复</title>
<script type="text/javascript" src="<%=tsBase %>/common/javascript/common.js"></script>
<link href="<%=tsBase %>/common/css/basets.css" type="text/css" rel="stylesheet"></link>
</head>
<body>
	<script type="text/javascript" src="<%=tsBase %>/pause/restore.js"></script>
<link href="<%=tsBase %>/common/css/basets.css" type="text/css" rel="stylesheet"></link>
<style type="text/css">
.date {
	width: 100px !important;
}

</style>
<input type="hidden" id="p_taskId">
<input type="hidden" name="st.id">
<div id="tracking" style="padding: 5 5 5 5">
	<fieldset>
		<legend>支持单信息</legend>
		<div class="row">
			<div class="column">
				<label class="label">技术支持单编号:</label>
				<input type="text" class="item inputstyle ro" id="p_stNo" name="st.stNo">
				<div class="clear-column"></div>
			</div>
			<div class="column column-250">
				<label class="label label-80">技术支持部门:</label>
				<input type="text" class="item inputstyle ro" id="p_deptName" >
				<div class="clear-column"></div>
			</div>
			<div class="clear-row"></div>
		</div>
		
		
		<div class="row">
			<div class="column">
				<label class="label">技术支持单负责人:</label>
				<input type="text" class="item inputstyle ro" id="p_slName">
				<div class="clear-column"></div>
			</div>
			<div class="column column-250">
				<label class="label label-80">大区/区域:</label>
				<input type="text" class="item inputstyle ro" name="st.region" id="p_region">
				<div class="clear-column"></div>
			</div>
			<div class="column column-250">
				<label class="label label-80">申请人:</label>
				<input type="text" class="item inputstyle ro" name="st.applicant.username" id="p_applicant">
				<div class="clear-column"></div>
			</div>
			<div class="clear-row"></div>
		</div>
		
		<div class="row">
			<div class=" column-block column" >
				<label class="label">技术支持单内容:</label>
				<textarea style="width:250px ! important;height: 80px;" class="ro item  inputstyle" name="st.supportContent" id="p_supportContent"></textarea>
				<div class="clear-column"></div>
			</div>
			<div class="column-block2 column">
				<label class="label label-80">产品方案部:</label>
				<div class="clear-column"></div>
				
				<div class="row">
					<div class="column">
						<label class="label label-80">原计划完成时间:</label>
						<input type="text" class=" item inputstyle ro label-100" name="st.psgScheDate" >
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
				
				<div class="row">
					<div class="column">
						<label class="label label-80">阶段:</label>
						<input type="checkbox" class=" item" id="psgstage">
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
				
				<div class="row">
					<div class="column">
						<div class="psgstage">
							<label class="label label-80" >需求:</label>
							<input type="text" class="psgstage item inputstyle ro label-100" name="st.psgDsScheDate">
						</div>
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
				
			</div>
			
			<div class="column-block2 column">
				<label class="label label-80">技术开发部:</label>
				<div class="clear-column"></div>
				
				<div class="row">
					<div class="column">
						<label class="label label-80">原计划完成时间:</label>
						<input type="text" class=" item inputstyle ro label-100" name="st.devScheDate" >
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
				<div class="row">
					<div class="column">
						<label class="label label-80">阶段:</label>
						<input type="checkbox" class=" item"  id="devstage">
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
				
				<div class="row">
					<div class="column">
						<div class="devstage">
							<label class="label label-80" >设计:</label>
							<input type="text" class="devstage item inputstyle ro label-100" name="st.devDsScheDate" >
						</div>
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
			
				<div class="row">
					<div class="column">
						<div class="devstage">
							<label class="label label-80" >开发:</label>
							<input type="text" class="devstage item inputstyle label-100 ro" name="st.devDdScheDate" >
						</div>
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
				
				<%-- ++ 新需求 技术开发部添加阶段测试 --%>
				<div class="row">
					<div class="column">
						<div class="devstage">
							<label class="label label-80" >测试:</label>
							<input type="text" class="devstage item inputstyle label-100 ro" name="st.devDtScheDate" >
						</div>
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
			
			<%-- -- 新需求 技术开发部添加阶段测试 --%>
				<%-- 移动实施到技术部 --%>
				<div class="row">
					<div class="column">
						<div class="devstage">
							<label class="label label-80" >实施:</label>
							<input type="text" class="devstage item inputstyle ro label-100" name="st.psgIsScheDate">
						</div>
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
			</div>
			
			<div class="clear-row"></div>
		</div>
	</fieldset>
	
	
	<fieldset>
		<legend>进展信息</legend>
		<div class="row" style="width: 90%; margin: 0 auto; ">
			<div class="column" style="width: 100%;">
				<div id="trackingTableDiv" style="width: 100%;">
					<table id="trackingTable" width="100%" border="1" cellpadding="0" cellspacing="0">
					  <thead>
					    <tr>       
					     	<th name="l_stNo">日期</th>
					     	<th name="l_region">进展</th>
					     	<th name="l_applicant">填写人员</th>
					    </tr>
					  </thead>
					</table>
				</div>
				<div class="clear-column"></div>
			</div>
			<div class="clear-row"></div>
		</div>
	</fieldset>
	<fieldset>
		<legend>恢复信息</legend>
		<div class="row">
			<div class="column">
				<div>
					<label class="label red">恢复原因:</label>
					<textarea class="item  inputstyle" style="width:450px ! important;height: 70px;" name="tracking.newProcess" id="p_newProcess"></textarea>
				</div>
				<div class="clear-column"></div>
				<div class="row">
					<div class="column">
						<div>
							<label class="label red">恢复日期:</label>
							<input type="text" class="ro  item  inputstyle" name="tracking.trackingDate" id="p_trackingDate" value="<%=datetime%>">
						</div>
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
				<div class="row">
					<div class="column">
						<div>
							<label class="label red">恢复人:</label>
							<input type="text" class="ro  item  inputstyle" name="tracking.processor.username" id="p_tracking_person_name" value="<%=username%>">
							<input type="hidden" name="tracking.processor.userid" value="<%=user.getUserid()%>">
							<input type="hidden" name="tracking.type" value="<%=Constants.TRACKING_TYPE_PAUSE%>">
						</div>
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
			</div>
			<%--产品方案部看到的计划时间 --%>
			<div class="column-block2 column">
				<label class="label label-80 red">产品方案部:</label>
				<div class="clear-column"></div>
				
				<div class="row">
					<div class="column">
						<label class="label label-80">现计划完成时间:</label>
						<input type="text" class="date item inputstyle ro label-100" name="st.psgScheDate" >
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
				
				<div class="row">
					<div class="column">
						<label class="label label-80">阶段:</label>
						<input type="checkbox" class=" item" id="psgstage2">
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
				
				<div class="row">
					<div class="column">
						<div class="psgstage2">
							<label class="label label-80" >需求:</label>
							<input type="text" class="date psgstage2 item inputstyle ro label-100" name="st.psgDsScheDate">
						</div>
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
			</div>
			
			<%--技术部门看到的计划时间 --%>
			<div class="column-block2 column">
				<label class="label label-80 red">技术开发部:</label>
				<div class="clear-column"></div>
				
				<div class="row">
					<div class="column">
						<label class="label label-80">现计划完成时间:</label>
						<input type="text" class="date item inputstyle ro label-100" name="st.devScheDate" id="p_devScheDate">
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
				<div class="row">
					<div class="column">
						<label class="label label-80">阶段:</label>
						<input type="checkbox" class=" item"  id="devstage2">
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
				
				<div class="row">
					<div class="column">
						<div class="devstage2">
							<label class="label label-80" >设计:</label>
							<input type="text" class="date devstage2 item inputstyle ro label-100" name="st.devDsScheDate" id="p_devDsScheDate">
						</div>
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
			
				<div class="row">
					<div class="column">
						<div class="devstage2">
							<label class="label label-80" >开发:</label>
							<input type="text" class="date devstage2 item inputstyle label-100 ro" name="st.devDdScheDate" id="p_devDdScheDate">
						</div>
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
				
				<%-- ++ 新需求 技术开发部添加阶段测试 --%>
				<div class="row">
					<div class="column">
						<div class="devstage2">
							<label class="label label-80" >测试:</label>
							<input type="text" class="date devstage2 item inputstyle label-100 ro" name="st.devDtScheDate" id="p_devDtScheDate">
						</div>
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
			
			<%-- -- 新需求 技术开发部添加阶段测试 --%>
				<%-- 移动实施到技术部 --%>
				<div class="row">
					<div class="column">
						<div class="devstage2">
							<label class="label label-80" >实施:</label>
							<input type="text" class="date devstage2 item inputstyle ro label-100" name="st.psgIsScheDate" id="p_psgIsScheDate">
						</div>
						<div class="clear-column"></div>
					</div>
					<div class="clear-row"></div>
				</div>
			</div>
			<div class="clear-row"></div>
		</div>
	</fieldset>
	
	
	<div class="row">
		<div class="column" style="width: 70%">
			<div class="clear-column"></div>
		</div>
		<div class="column" style="width: 120px;">
			<a class="item submitbutton" id="saveBtn" href="#">恢复</a>
			<div class="clear-column"></div>
		</div>
		<div class="clear-row"></div>
	</div>
</div>
</body>
</html>