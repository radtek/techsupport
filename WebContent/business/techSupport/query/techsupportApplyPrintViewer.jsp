<%@page import="com.aisino2.techsupport.common.Constants"%>
<%@ page language="java" pageEncoding="utf8"%>

<html>
	<head>
	<title>技术支持单打印预览</title>
	<%@include file="../../../public/common.jsp"%>
	<%@include file="../common/base.jsp" %>
	<link href="<%=tsBase %>/common/css/basets.css" type="text/css" rel="stylesheet"></link>
	<style type="text/css">
	
	.date {
		width: 100px !important;
	}
	.label-90{
		width: 90px !important;
	}
	
	.document_field {
		border-top:none;
		border-left: none;
		border-right: none;
		border-bottom: black 1px solid;
		background-color: transparent; 
		min-width: 10px;
	}
	.signature {
		float: right;
		margin-right: 10%;					
	}
	</style>
	</head>
	<body>
		<table border="0" cellpadding="0" cellspacing="0" align="center" width="880">
			<tr>
				<td align="center"><h1>北京航天金盾科技有限公司</h1></td>
			</tr>
			<tr>
				<td align="center"><h2>技术支持申请表</h2></td>
			</tr>
			<tr>
				<td align="center">
					<table border="1" cellpadding="0" cellspacing="0" align="center" bordercolor="black">
						<tr>
							<td width="40" rowspan="10" align="center">申<br>请<br>信<br>息</td>
							<td>申请人姓名:</td>
							<td><span name="">填写申请人</span></td>
							<td>申请日期:</td>
							<td><span>填写申请日期</span></td>
						</tr>
						<tr>
							<td>大区/区域:</td>
							<td><span>大区</span></td>
							<td>支持单编号:</td>
							<td><span>编号</span></td>
						</tr>
						<tr>
							<td>产品（项目）名称:</td>
							<td colspan="3"><span>暂留</span></td>
						</tr>
						
						<tr>
							<td>单位名称:</td>
							<td><span>暂留</span></td>
							<td>联系人:</td>
							<td><span>暂留</span></td>
						</tr>
						<tr>
							<td>Email地址:</td>
							<td><span>暂留</span></td>
							<td>联系电话:</td>
							<td><span>暂留</span></td>
						</tr>
						<tr>
							<td colspan="4">支持类型:
								<label>□新项目售前</label>
								<label>□项目实施</label>
								<label>□维护服务</label>
								<label>□软件修复</label>
								<label>□需求变更</label>
								<label>□其它:
									<input type="text" class="document_field"> 
								</label>
							 </td>
						</tr>
						<tr>
							<td colspan="4">
								技术支持内容及具体要求:<br>
								<p>内容</p>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								客户方环境是否已具备（必须明确说明涉及到的软硬件环境）:<br>
								<span>暂留</span>
							</td>
						</tr>
						<tr>
							<td>希望进行支持的人员:</td>
							<td><span>暂留</span></td>
							<td>支持人员工作地点:</td>
							<td><span>暂留</span></td>
						</tr>
						<tr>
							<td>预计工作时间:</td>
							<td colspan="3">
								<span>暂留</span>
							</td>
						</tr>
						<%--公司审批 --%>
						<tr>
							<td rowspan="3" align="center">审<br>批</td>
							<td colspan="4">
								<div>暂留</div>
								<label class="signature">申请人主管/日期:
									<input type="text" class="document_field"> 
								</label>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								支持类型:
								<label>□新项目售前</label>
								<label>□项目实施</label>
								<label>□维护服务</label>
								<label>□软件修复</label>
								<label>□需求变更</label>
								<label>□其它:
									<span name="support_type_value" class="document_field"></span>
								</label>
								<div>一级审批意见</div>
								<label class="signature">一级审批人/日期: 
									<input type="text" class="document_field"> 
								</label>
							 </td>
						</tr>
						<tr>
							<td colspan="4">
								<div>二级审批意见</div>
								<label class="signature">二级审批人/日期:
									<input type="text" class="document_field"> 
								</label>
							</td>
						</tr>
						<tr>
							<td rowspan="2" align="center">具<br>体<br>安<br>排</td>
							<td>支持人员:</td>
							<td><span>负责人</span></td>
							<td>支持人员所在部门:</td>
							<td><span>支持部门</span></td>
						</tr>
						<tr>
							<td>计划完成时间:</td>
							<td colspan="3"><span>计划完成时间</span></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>