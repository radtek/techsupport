<%@page import="com.aisino2.techsupport.common.Constants"%>
<%@ page language="java" pageEncoding="utf8"%>
<%
	String stId = request.getParameter("stId") != null? request.getParameter("stId") : "";
	
%>
<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>技术支持单打印预览</title>
	<script type="text/javascript" src="../common/javascript/uploadify/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="../../../javascript/htmlConfig.js"></script>
	<%@include file="../common/base.jsp" %>
	<script type="text/javascript" src="<%=tsBase %>/common/javascript/common.js"></script>
	<style type="text/css">
	
	.document_field {
		border-top:none;
		border-left: none;
		border-right: none;
		border-bottom: black 1px solid;
		background-color: transparent; 
		Min-width: 10px;
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
				
				//一级审批
				var _1lvlappr_person = $('#1lvlappr_person').val(); 
				$('#1lvlappr_person').val((_1lvlappr_person ? _1lvlappr_person+" / "+setNull(json.supportTicket.trackList[0].trackingDate) : ''));
				//二级审批内容
				var _2lvlappr_arr = [];
				var _2lvlappr_parr = [];
				var _2lvlappr_datearr = [];
				for(var i=0;i<json.supportTicket.trackList.length;i++){
					if(json.supportTicket.trackList[i].type == TRACKING_TYPE_PGMREPLY
							|| json.supportTicket.trackList[i].type == TRACKING_TYPE_HDEVREPLY){
						_2lvlappr_arr.push(json.supportTicket.trackList[i].newProcess);
						if(!_2lvlappr_parr.hasOwnProperty(json.supportTicket.trackList[i].processor.username)){
							_2lvlappr_parr.push(setNull(json.supportTicket.trackList[i].processor.username));
							_2lvlappr_datearr.push(setNull(json.supportTicket.trackList[i].trackingDate));
						}
					}
				}
				
				$('#2lvlappr').text(_2lvlappr_parr.join(","));
				$('#2lvlappr_person').val(_2lvlappr_parr.length > 0 ? _2lvlappr_parr.join(",")+"/"+ _2lvlappr_datearr.join(","): "");
				
				//负责人
				var _stleader_a = [];
				for(var i=0;i<json.supportTicket.lstSupportLeaders.length;i++){
					_stleader_a.push(json.supportTicket.lstSupportLeaders[i].username);
				}
				$('#stleader').text(_stleader_a.join(","));
				
				//支持部门
				var _stdept_a = [];
				for(var i=0;i<json.supportTicket.supportDeptList.length;i++){
					_stdept_a.push(json.supportTicket.supportDeptList[i].departname);
				}
				$('#stdept').text(_stdept_a.join(","));
				
				//计划完成时间
				$('#scheDate').text(
						_stdept_a.length > 0 ? 
								(json.supportTicket.devScheDate?'技术部:'+setNull(json.supportTicket.devScheDate) : '')
								+
								(json.supportTicket.psgScheDate?'产品部:'+setNull(json.supportTicket.psgScheDate) : "")
								:"");
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
			<table width="880" align="center">
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
								<td width="6%" rowspan="10" align="center">申<br>请<br>信<br>息</td>
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
								<td colspan="3">&nbsp;<span>暂留</span></td>
							</tr>
							
							<tr>
								<td>单位名称:</td>
								<td>&nbsp;<span>暂留</span></td>
								<td>联系人:</td>
								<td>&nbsp;<span>暂留</span></td>
							</tr>
							<tr>
								<td>Email地址:</td>
								<td>&nbsp;<span>暂留</span></td>
								<td>联系电话:</td>
								<td>&nbsp;<span>暂留</span></td>
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
								<td colspan="4" style="min-height: 200px;height:auto; vertical-align: top;">
									技术支持内容及具体要求:<br>
									<p name="st.supportContent">内容</p>
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
								<td>&nbsp;<span>暂留</span></td>
								<td>支持人员工作地点:</td>
								<td>&nbsp;<span>暂留</span></td>
							</tr>
							<tr>
								<td>预计工作时间:</td>
								<td colspan="3">
									&nbsp;<span>暂留</span>
								</td>
							</tr>
							<tr>
								<td id="apply" rowspan="3" align="center">审<br>批</td>
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
									<div name="st.trackList[0].newProcess">一级审批意见</div>
									<label class="signature">一级审批人/日期: 
										<input type="text" id="1lvlappr_person" name="st.trackList[0].processor.username"  class="document_field"> 
									</label>
								 </td>
							</tr>
							<tr id="2lvlappr_tr">
								<td colspan="4">
									<div id="2lvlappr">二级审批意见</div>
									<label class="signature">二级审批人/日期:
										<input type="text" id="2lvlappr_person" class="document_field"> 
									</label>
								</td>
							</tr>
							<tr>
								<td rowspan="2" align="center">具<br>体<br>安<br>排</td>
								<td>支持人员:</td>
								<td>&nbsp;<span id="stleader">负责人</span></td>
								<td>支持人员所在部门:</td>
								<td>&nbsp;<span id="stdept">支持部门</span></td>
							</tr>
							<tr>
								<td>计划完成时间:</td>
								<td colspan="3">&nbsp;<span id="scheDate">计划完成时间</span></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		
	</body>
</html>