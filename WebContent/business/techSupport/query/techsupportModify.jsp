<%@page import="com.aisino2.techsupport.common.Constants"%>
<%@ page language="java" pageEncoding="utf8"%>
<%@include file="../../../public/common.jsp"%>
<%@include file="../common/base.jsp" %>
 
<script type="text/javascript" src="<%=tsBase %>/query/techsupportDetail.js"></script>
<link href="<%=tsBase %>/common/css/basets.css" type="text/css" rel="stylesheet"></link>

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

<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
    <tr>
      <td align="left" class="title1">支持单修改</td>
      <td align="right"><a href="#" id="closeDiv" onclick='$("#detailCt").hideAndRemove("show");' class="close"></a></td>
    </tr>
</table>
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
				<textarea style="width:658px ! important;height: 79px;" class="ro item inputstyle" name="st.supportContent" id="supportContent"></textarea>
				<div class="clear-column"></div>
			</div>
			
			<div class="clear-row"></div>
		</div>
	</fieldset>
</div>