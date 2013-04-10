<%@page import="com.aisino2.techsupport.common.Constants"%>
<%@page import="java.util.UUID"%>
<%@ page language="java" pageEncoding="utf8"%>
<%@include file="../../../public/common.jsp"%>
<%@include file="../common/base.jsp" %>
<script type="text/javascript" src="<%=tsBase%>/common/javascript/uploadify/jquery.uploadify.v2.1.4.js"></script> 
<script type="text/javascript" src="<%=tsBase%>/common/javascript/uploadify/swfobject.js"></script> 
<script type="text/javascript" src="<%=tsBase %>/query/techsupportModify.js"></script>
<link href="<%=tsBase %>/common/css/basets.css" type="text/css" rel="stylesheet"></link>
<link href="<%=tsBase%>/common/javascript/uploadify/uploadify.css"  rel="stylesheet" type="text/css"/>

<style type="text/css">

.date {
	width: 100px !important;
}
.label-90{
	width: 90px !important;
}
</style>
<input type="hidden" id="id">
<input type="hidden" id="track_stId">
<input type="hidden" id="sv_st_id">
<input type="hidden" id="att_stId">

<input id="batchNumber"  type="hidden" name="uploadId" value="<%=UUID.randomUUID()%>">
<div id="fileUploadPanel" style="position: absolute; display: none;width: 400px;  background-color: #E9E9E9;">

</div>

<div id="techsupportDetail" style="margin-left:auto; margin-right:auto; margin-top:auto; width: 840px; text-align: center;padding-top:10px;padding-bottom:5px;">
<fieldset>
		<legend>支持单信息</legend>
		<div class="row">
			<div class="column">
				<label class="label">技术支持单编号:</label>
				<input type="text" class="item ro inputstyle" id="stNo" name="st.stNo">
				<div class="clear-column"></div>
			</div>
			<div class="column column-250">
				<label class="label label-80">版本出库领用单编号:</label>
				<input type="text" class="item ro inputstyle" id="archive_code" name="st.archive_code">
				<div class="clear-column"></div>
			</div>
			<div class="clear-row"></div>
		</div>
		
		
		<div class="row">
			<div class="column">
				<label class="label">技术支持单负责人:</label>
				<input type="text" class="item ro inputstyle" name="st.supportLeader.username" id="q_slName"> 
				<div class="clear-column"></div>
			</div>
			<div class="column column-250">
				<label class="label label-80">大区/区域:</label>
				<input type="text" class="item ro inputstyle" name="st.region" id="region">
				<div class="clear-column"></div>
			</div>
			<div class="column column-250">
				<label class="label label-80">申请人:</label>
				<input type="text" class="item inputstyle ro" name="st.applicant.username" id="applicant">
				<div class="clear-column"></div>
			</div>
			<div class="clear-row"></div>
		</div>
		
		<div class="row">
			<div class="column"  style="width: 800px;">
				<label class="label">技术支持单内容:</label>
				<textarea style="width:658px ! important;height: 200px;" class="item inputstyle" name="st.supportContent" id="supportContent"></textarea>
				<div class="clear-column"></div>
			</div>
			
			<div class="clear-row"></div>
		</div>
	</fieldset>
	
	<fieldset>
			<legend>附件</legend>
			<div class="row">
				<div class="column" style="width: 100%;">
					<label></label>
					<div class="item">
						<div id="attachment_list_div">
							<table id="attachment_list_table">
								<thead>
									<th>文件名</th>
									<th>文件大小</th>
									<th>备注</th>
									<th>操作</th>
								</thead>
								
							</table>
						</div>
					</div>
					<div class="clear-column"></div>
				</div>
				<div class="clear-row"></div>
			</div>	
		</fieldset>
		<div style="float: right;margin-right: 20%; margin-top: 7px;">
			<div style="float:left; margin-right: 5px;">
				<input type="file" name="upload" id="uploadFile" class="submitbutton" >
			</div>
			<a href="#" style="float: left;margin-top:1px; margin-right: 5px;" id="uploadButton" class="submitbutton" >上传附件</a>
			<a href="#" style="float: left;margin-top:1px; margin-right: 5px;" id="modify_btn" class="submitbutton">保存</a>
		</div>
</div>