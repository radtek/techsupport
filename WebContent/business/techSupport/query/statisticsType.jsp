<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function(){
		$('#statisticsBtn2').click(function(){
			detailDialog('statisticsDetailCt',800,'business/techSupport/query/statistics.jsp',
					$('#statisticsType option[value='+$('#statisticsType').val()+']').text(),{statisticsType:$('#statisticsType').val()});
			$('#statisticsCt').hideAndRemove('show');
		});
	});
</script>
<div>
	<select id="statisticsType" >
		<option value="1">按区域统计</option>
		<option value="2">按部门统计</option>
		<option value="3">按负责人统计</option>
	</select>
	<input type="button" value="统计" class="submitbutton" id="statisticsBtn2">
</div>
