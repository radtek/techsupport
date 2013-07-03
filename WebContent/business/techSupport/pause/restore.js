/**
 * Filename:restore.js Description:支持单恢复
 * 
 */

var processUrl2;
var ingridUrl;
var ingridWidth = 750;
var trackingWindowWidth = 850;
// 技术部门审批时间
var techDepartmentApprovalTime = null;
// 产品部门审批时间
var productDepartmentApprovalTime = null;
// 追踪批复链接
var trackingInfoURL = BUSNEISS_PATH + "/querylistNoPage_tracking.action";
/** 保存验证 */
function saveVerify() {
	if (!checkControlValue("p_newProcess", "String", 1, 3000, null, 1, "恢复原因"))
		return false;
	if (!checkControlValue("p_trackingDate", "Datetime", null, null, null, 1,
			"恢复日期"))
		return false;

	if ($("#st input[name=st.psgScheDate]").val()) {
		if (!checkControlValue("psgScheDate", "Date", 1, 100, null, 1, "计划完成时间"))
			return false;
		// 计划完成时间必须大于当前部门审批的时间
		if ($('#psgScheDate').val() < productDepartmentApprovalTime) {
			jAlert('计划完成时间必须大于等于审批日期', '提示');
			return false;
		}
		if($('#psgScheDate').val() < $('#st input[name="st.psgScheDate"]').val()){
			jAlert('现计划完成时间必须大于原计划完成时间', '提示');
			return false;
		}
		if ($('#psgstage2').attr('checked')) {
			if (!checkControlValue("psgDsScheDate", "Date", 1, 100, null, 1,
					"计划需求时间"))
				return false;

			if ($('#psgDsScheDate').val() != $('#psgScheDate').val()) {
				jAlert('计划需求时间必须等于计划完成时间', '提示');
				return false;
			}
		}
	}
	if ($('#st input[name=st.devScheDate]').val()) {
		if (!checkControlValue("devScheDate", "Date", 1, 100, null, 1, "计划完成时间"))
			return false;
		// 计划完成时间必须大于当前部门审批的时间
		if ($('#devScheDate').val() < techDepartmentApprovalTime) {
			jAlert('计划完成时间必须大于等于审批日期', '提示');
			return false;
		}
		if($('#devScheDate').val() < $('#st input[name="st.devScheDate"]').val()){
			jAlert('现计划完成时间必须大于原计划完成时间', '提示');
			return false;
		}
		if ($('#devstage2').attr('checked')) {
			if (!checkControlValue("devDsScheDate", "Date", 1, 100, null, 1,
					"计划设计时间"))
				return false;
			if (!checkControlValue("devDdScheDate", "Date", 1, 100, null, 1,
					"计划开发时间"))
				return false;
			if (!checkControlValue("devDtScheDate", "Date", 1, 100, null, 1,
					"计划测试时间"))
				return false;
			// 移动实施时间到技术部
			if (!checkControlValue("psgIsScheDate", "Date", 1, 100, null, 1,
					"计划实施时间"))
				return false;
			if (!($('#devDsScheDate').val() < $('#devDdScheDate').val()
					&& $('#devDdScheDate').val() < $('#devDtScheDate').val()
					&& $('#devDtScheDate').val() < $('#psgIsScheDate').val() && $('#devScheDate')
					.val() == $('#psgIsScheDate').val())) {
				jAlert(
						'计划设计时间必须小于计划开发时间必须小于计划测试时间，测试时间必须小于实施时间，实施时间必须等于计划完成时间',
						'提示');
				return false;
			}
		}
	}
	return true;
}

/** onload */
$(function() {

			// 保存连接
			var saveURL = getContextPath() + "/techsupport/save_restore.action";

			// 只读化控件
			$('.ro').attr('readOnly', true);
			// 设置时间控件
			$('.date').each(function() {
						$(this).datepicker();
						$(this).attr('readOnly', true);
					});
			$('.schetime').hide();
			// 初始化该页面值
			$('#p_taskId').val(dataid);

			processUrl2 = getContextPath()
					+ "/techsupport/init_tracking.action";
			// 追踪批复查询URL
			ingridUrl = getContextPath()
					+ "/techsupport/querylist_tracking.action";

			divnid = "trackingTableDiv";// 查询内容容器ID
			tableid = "trackingTable";// 查询内容格式表格ID

			loadPageTrackingQuery(divnid);

			loadData();

			// 设置保存按钮
			$('#saveBtn').click(function() {
						if (!saveVerify()) {
							return;
						}
						if (!confirm("您确认本支持单恢复吗？"))
							return;
						var params = {};

						
						params = getSubmitParams('#restoreCt [name^=tracking.]');
						params = getSubmitParams('#restoreCt [name^=st.]',params);
						// 设置当前的track.stId
						params['tracking.stId'] = $('input:hidden[name=st.id]')
								.val();
						params['st.id'] = $('input:hidden[name=st.id]').val();
						params['taskId'] = $('#p_taskId').val();

						if ($('#st input[name=st.devScheDate]').val()) {
						  params['timeChange.type'] = ST_TIME_CHANGE_TYPE_DEVELOP;
						}
						else
						  params['timeChange.type'] = ST_TIME_CHANGE_TYPE_PRODUCT;
						  
						$.post(saveURL, params, function(data) {
									if (!data) {
										alert("传输错误，管理人员");
									}

									if (data.returnNo == 0) {
										$(detailWindow).empty();
										$(detailWindow).hideAndRemove("show");
										worksheetQuery(1);
									} else
										jAlert(data.returnMsg, "提示");
								}, 'json');

					});
		});

function loadPageTrackingQuery(divpageid) {
	tables = $("#" + divpageid).html();
	trackingQuery(1, '#');
}

/**
 * 查询函数
 */
function trackingQuery(pageno, url) {

	if (true) {
		url = setList(pageno, url);
		// create the grid
		// returns a jQ object with a 'g' property - that's ingrid
		var mygrid2 = $("#" + tableid).ingrid({
					url : url,
					height : 60,
					ingridPageWidth : ingridWidth,
					isPlayResultNull : false,
					havaWaiDivGunDong : true,
					ingridPageParams : sXML,
					onRowSelect : null,
					pageNumber : pageno,
					colWidths : ["14%", "70%", "16%"]
				});
	}
}

/**
 * 初始化页面数据
 */
function loadData() {
	var paramsss = {
		'taskId' : $('#p_taskId').val()
	};

	$.post(processUrl2, paramsss, function(data) {

		$('#st input[name^=st.]').each(function() {
			try {
				$(this).val(setNull(eval("(" + "data." + $(this).attr('name')
						+ ")")));
			} catch (e) {

			}

		});
		$('#st textarea[name^=st.]').each(function() {
			try {
				$(this).val(setNull(eval("(" + "data." + $(this).attr('name')
						+ ")")));
			} catch (e) {

			}
		});

		// 非阶段性隐藏
		relateHide('devstage');
		relateHide('psgstage');

		// 非阶段性隐藏
		relateHide('devstage2');
		relateHide('psgstage2');

		// 初始化单位信息
		var deptNameStr = "";
		var deptList = data.st.supportDeptList;
		for (i = 0; i < deptList.length; i++) {
			deptNameStr += "," + deptList[i].departname;
		}
		deptNameStr = deptNameStr.substring(1, deptNameStr.length);
		$('#p_deptName').val(deptNameStr);

		// 初始化技术负责人
		var sSlNames = "";
		for (i = 0; i < data.st.lstSupportLeaders.length; i++)
			sSlNames += "," + data.st.lstSupportLeaders[i].username;
		sSlNames = sSlNames.length > 0 ? sSlNames.substring(1) : sSlNames;
		$('#p_slName').val(sSlNames);

		$('#p_region').val(getDictitem({
					dictcode : ST_REGION_DICT_CODE,
					value : $('#p_region').val()
				})[0].display_name);

		trackingQuery(1, ingridUrl);

		// 颜色控制和现实控制
		if ($("#st input[name=st.psgScheDate]").val()) {
			$('#psgLabel').addClass("red");
			$('.psgTime').show();
		}
		if ($('#st input[name=st.devScheDate]').val()) {
			$('#devLabel').addClass("red");
			$('.devTime').show();
		}

		
		// 获取最后的部门审批日期
		setParams("p_");
		$.post(trackingInfoURL, params, function(result) {
			if (result == null) {
				return;
			}
			var trackinglist = result.lTracking;
			for (var i = trackinglist.length - 1; i >= 0; i--) {
				if (trackinglist[i].type == TRACKING_TYPE_HDEVREPLY) {
					techDepartmentApprovalTime = setNull(trackinglist[i].trackingDate);
				} else if (trackinglist[i].type == TRACKING_TYPE_PGMREPLY) {
					productDepartmentApprovalTime = setNull(trackinglist[i].trackingDate);
				}
			}

		}, 'json');
	}, 'json');

}
