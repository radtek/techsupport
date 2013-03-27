<%@page import="com.aisino2.techsupport.common.Constants"%>
<%@ page language="java" pageEncoding="utf8"%>
<%
	String stId = request.getParameter("stId") != null? request.getParameter("stId") : "";
	
%>
<!DOCTYPE script PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<script language="javascript" type="text/javascript" src="../common/javascript/uploadify/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="../../../javascript/htmlConfig.js"></script>
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
		
		<script type="text/javascript">
			var printControl = null;//打印控件
			
			/**
				load,入口
			*/
			$(function(){
				<%-- 加载数据 --%>
				loadData();
			});
			
			/**
			*
			* 初始化数据
			*/
			function loadData(){
				var url = getContextPath()+ "/techsupport/query_supportTicket.action";
				var params = {'supportTicket.id':$('#stId').val()};
				$.post(url,params,function(json){
					//初始化表单数据
					$('[name*=st.]').each(function(idx){
						var _obj = $(this);
						try{
							
							if(_obj.attr('tagName').toLowerCase() == 'input')
								_obj.val(setNull(eval("json.supportTicket."+_obj.attr('name').split('st.')[1])));
							else
								_obj.text(setNull(eval("json.supportTicket."+_obj.attr('name').split('st.')[1])));
						}catch(e){
							//alert(e);
						}
					});
					
					//进展提示
					var _tracking_content_a = [];
					var _tracking_a = json.supportTicket.trackList;
					for(var i=0;i<_tracking_a.length;i++){
						if(_tracking_a[i].type != TRACKING_TYPE_ARCHIVE 
								|| _tracking_a[i].type != TRACKING_TYPE_EXCEPTION
								|| _tracking_a[i].type != TRACKING_TYPE_FEEDBACK
								|| _tracking_a[i].type != TRACKING_TYPE_REASSIGN_DEPARTMENT
								|| _tracking_a[i].type != TRACKING_TYPE_REASSIGN_SUPPORT_LEADER){
							_tracking_content_a.push(_tracking_a[i].newProcess+":"+_tracking_a[i].processor.username+"/"+setNull(_tracking_a[i].trackingDate));
						}
					}
				
						
					$('#tracking').html(_tracking_content_a.join('<br>'));
					delete _tracking_content_a;
					delete _tracking_content_d;
					delete _tracking_a;
					
					//实际完成时间
					var _compDate_a = [];
					if(json.supportTicket.psgCompDate)
						_compDate_a.push("产品部:"+setNull(json.supportTicket.psgCompDate));
					if(json.supportTicket.devCompDate)
						_compDate_a.push("技术部:"+setNull(json.supportTicket.devCompDate));
					$('#compDate').text(_compDate_a.join(','));
					
					// 申请人评价
					var _lFeedbackContent = [];
					var _sFeedbackPerson="";
					var _sFeedbackDate="";
					for(var i=0;i<_tracking_a.length;i++){
						if(_tracking_a[i].type == TRACKING_TYPE_FEEDBACK ){
							_lFeedbackContent.push(_tracking_a[i].newProcess+":"+setNull(_tracking_a[i].trackingDate));
							_sFeedbackPerson = _tracking_a[i].processor.username;
							_sFeedbackDate = setNull(_tracking_a[i].trackingDate);
						}
					}
					
					$('#applicantAppraisement').html(_lFeedbackContent.join('<br>'));
					$('#applicantAppraisementPersonAndDate').val(_sFeedbackPerson +" / "+_sFeedbackDate);
				},'json');
				
			}
			
			function dayin(){
				if(onbeforeprint()){
					window.print();
					onafterprint();
				}
					
			}
			
			var onbeforeprint = function(){
				if(!$('#stId').val()){
					alert('无法获取到支持单唯一编号');
					return false;
				}
				
				return true;
			}
			
			var onafterprint = function(){
			}
			
			//选打印机后，点击取消按钮触发事件
			function printcannal(){
	
			}
			function shezhi(){
				printControl.showSet();//弹出设置窗口
			}
		</script>
	</head>
	<body>
		<input type="hidden" id="stId" value="<%=stId%>">
		<div id="print_div">
			<input id="printPage" type="button" value="打印" onclick="dayin();" />
		</div>
		<div id="printResultHtml_td">
		<table border="0" cellpadding="0" cellspacing="0" align="center" width="860">
			<tr>
				<td align="center"><h1>北京航天金盾科技有限公司</h1></td>
			</tr>
			<tr>
				<td align="center"><h2>技术支持反馈表</h2></td>
			</tr>
			<tr>
				<td>
					<table border="1" cellpadding="0" cellspacing="0" align="center" bordercolor="black">
						<tr>
							<td width="6%" rowspan="10" align="center">技<br>术<br>支<br>持<br>单<br>信<br>息</td>
							<td width="21%">申请人姓名:</td>
							<td width="21%">&nbsp;<span name="st.applicant.username">填写申请人</span></td>
							<td width="21%">申请日期:</td>
							<td width="21%">&nbsp;<span name="st.applyDate">填写申请日期</span></td>
						</tr>
						<tr>
							<td>大区/区域:</td>
							<td>&nbsp;<span name="st.regionName">大区</span></td>
							<td>支持单编号:</td>
							<td>&nbsp;<span name="st.stNo">编号</span></td>
						</tr>
						<tr>
							<td>产品（项目）名称:</td>
							<td colspan="3">&nbsp;<span></span></td>
						</tr>
						
						<tr>
							<td>客户单位名称:</td>
							<td>&nbsp;<span></span></td>
							<td>客户联系人:</td>
							<td>&nbsp;<span></span></td>
						</tr>
						<tr>
							<td>客户Email地址:</td>
							<td>&nbsp;<span></span></td>
							<td>客户联系电话:</td>
							<td>&nbsp;<span></span></td>
						</tr>
						<tr>
							<td colspan="4">
								具体工作内容:<br>
								<p name="st.supportContent">内容</p>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								技术支持工作日程:
								<p id="tracking">进展信息</p>
								<div>
									服务方式:<label>□远程</label><label>□现场</label><label>□电话</label><label>□其它：<input type="text" class="document_field" value=""></label>
								</div>
								<div>实际完成时间:<span id="compDate">完成时间</span></div>
								<div>服务时间投入:<input type="text" class="document_field" value="" style="width:20px;">小时/天</div>
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
								&nbsp;<span></span>
							</td>
						</tr>
						<tr>
							<td>遗留问题及后续计划:</td>
							<td colspan="3">
								&nbsp;<span></span>
							</td>
						</tr>
						<tr>
							<td align="center">客<br>户<br>评<br>价</td>
							<td colspan="4">客户评价及建议:&nbsp;<div></div>
								<label class="signature">签字/日期:<input type="text" class="document_field"></label>
							</td>
						</tr>
						<tr>
							<td rowspan="3" align="center">公<br>司<br>内<br>部<br>评<br>价</td>
							<td colspan="4">申请人评价: <br/>
								&nbsp;<div id="applicantAppraisement"></div>
								<label class="signature">签字/日期:<input id="applicantAppraisementPersonAndDate" type="text" class="document_field"></label>
							</td>
						</tr>
						<tr>
							<td colspan="4">二级审批人评价:&nbsp;<div></div>
								<label class="signature">签字/日期:<input type="text" class="document_field"></label>
							</td>
						</tr>
						<tr>
							<td colspan="4">一级审批人评价:&nbsp;<div></div>
								<label class="signature">签字/日期:<input type="text" class="document_field"></label>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</div>
	</body>
</html>
