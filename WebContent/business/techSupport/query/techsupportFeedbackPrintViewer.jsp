<%@page import="com.aisino2.techsupport.common.Constants"%>
<%@ page language="java" pageEncoding="utf8"%>

<!DOCTYPE script PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@include file="../../../public/common.jsp"%>
		<%@include file="../common/base.jsp" %>
		<link href="<%=tsBase %>/common/css/basets.css" type="text/css" rel="stylesheet"></link>
		
		<style type="text/css">
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
		<table border="0" cellpadding="0" cellspacing="0" align="center" width="860">
			<tr>
				<td align="center"><h1>北京航天金盾科技有限公司</h1></td>
			</tr>
			<tr>
				<td align="center"><h2>技术支持申请表</h2></td>
			</tr>
			<tr>
				<td>
					<table border="1" cellpadding="0" cellspacing="0" align="center" bordercolor="black">
						<tr>
							<td width="40" rowspan="10" align="center">技<br>术<br>支<br>持<br>单<br>信<br>息</td>
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
							<td>客户单位名称:</td>
							<td><span>暂留</span></td>
							<td>客户联系人:</td>
							<td><span>暂留</span></td>
						</tr>
						<tr>
							<td>客户Email地址:</td>
							<td><span>暂留</span></td>
							<td>客户联系电话:</td>
							<td><span>暂留</span></td>
						</tr>
						<tr>
							<td colspan="4">
								具体工作内容:<br>
								<p>内容</p>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								技术支持工作日程:
								<p>进展信息</p>
								<div>
									服务方式:<label>□远程</label><label>□现场</label><label>□电话</label><label>□其它：<input type="text" class="document_field" value=""></label>
								</div>
								<div>实际完成时间:<span>完成时间</span></div>
								<div>服务时间投入:<input type="text" class="document_field" value="5">小时/天</div>
							</td>
						</tr>
						<tr>
							<td>是否完成本次技术支持:</td>
							<td><label>□是</label><label>□否</label></td>
							<td>是否达到预期效果:</td>
							<td><label>□是</label><label>□否</label></td>
						</tr>
						<tr>
							<td>未完成工作原因分析:</td>
							<td colspan="3">
								<span>暂留</span>
							</td>
						</tr>
						<tr>
							<td>遗留问题及后续计划:</td>
							<td colspan="3">
								<span>暂留</span>
							</td>
						</tr>
						<tr>
							<td align="center">客户评价</td>
							<td colspan="4">客户评价及建议:<div>暂留</div>
								<label class="signature">签字/日期:<input type="text" class="document_field"></label>
							</td>
						</tr>
						<tr>
							<td rowspan="3" align="center">公司内部评价</td>
							<td colspan="4">申请人评价: <div>暂留</div>
								<label class="signature">签字/日期:<input type="text" class="document_field"></label>
							</td>
						</tr>
						<tr>
							<td colspan="4">二级审批人评价:<div>暂留</div>
								<label class="signature">签字/日期:<input type="text" class="document_field"></label>
							</td>
						</tr>
						<tr>
							<td colspan="4">一级审批人评价:<div>暂留</div>
								<label class="signature">签字/日期:<input type="text" class="document_field"></label>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
