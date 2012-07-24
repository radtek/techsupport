<%@page import="org.jbpm.api.*"%>
<%@page import="org.jbpm.api.task.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@include file="../common/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>流程测试</title>
<script type="text/javascript" src="<%=tsBase %>/common/javascript/common.js"></script>
<script type="text/javascript" src="<%=tsBase %>/worksheet/workflowManager.js"></script>

<link href="<%=tsBase %>/common/css/basets.css" type="text/css" rel="stylesheet"></link>
</head>
<body>
<%
	ProcessEngine processEngine=Configuration.getProcessEngine();
	RepositoryService repositoryService=processEngine.getRepositoryService();
	ExecutionService executionService=processEngine.getExecutionService();
	TaskService taskService=processEngine.getTaskService();
	
	List<ProcessDefinition> pdList=repositoryService.createProcessDefinitionQuery().list();
	List<ProcessInstance> piList=executionService.createProcessInstanceQuery().list();
%>
	<a href="#" onclick="javascript:deployByXml();">发布新流程</a>&nbsp; 
	<table>
		<caption>流程定义</caption>
		<thead>
			<tr>
				<td>id</td><td>name</td><td>version</td><td></td>
			</tr>
		</thead>
		<tbody>
		<%
		for(ProcessDefinition pd:pdList){
			%>
			<tr>
				<td><%=pd.getId() %></td><td><%=pd.getName() %></td><td><%=pd.getVersion() %></td><td><a href="#" onclick="javascript:removeDeployment('<%=pd.getDeploymentId()  %>')">删除</a>&nbsp;<a href="start.jsp?ptid=<%= pd.getId()  %>">开始</a>
			</tr>
			<%
		}
		%>
		</tbody>
	</table>
	
	<table>
		<caption>流程实例</caption>
		<thead>
			<tr>
				<td>id</td><td>activity</td><td>state</td><td></td>
			</tr>
		</thead>
		<tbody>
		<%
			for(ProcessInstance pi : piList){
				%>
			<tr>
				<td><%=pi.getId() %></td><td><%=pi.findActiveActivityNames() %></td><td><%=pi.getState() %></td><td><a href="">do</a></td>
			</tr>
				<%
			}
		%>
			
		</tbody>
	</table>
	
</body>
</html>