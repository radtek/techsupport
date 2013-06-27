<%@page import="com.aisino2.techsupport.common.Constants"%>
<%@page import="com.aisino2.sysadmin.domain.User_role"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../common/base.jsp" %>

<html>
	<head>
	<meta http-equiv="pragma" content="no-cache" />
		<title>技术支持单查询统计</title>
		<script type="text/javascript" src="<%=tsBase %>/common/javascript/common.js"></script>
		<script type="text/javascript" src="<%=tsBase%>/common/javascript/uploadify/jquery.uploadify.v2.1.4.js"></script> 
		<script type="text/javascript" src="<%=tsBase%>/common/javascript/uploadify/swfobject.js"></script>
<link href="<%=tsBase %>/common/css/basets.css" type="text/css" rel="stylesheet"></link>
<link href="<%=tsBase%>/common/javascript/uploadify/uploadify.css"  rel="stylesheet" type="text/css"/>
<style>
<!--
#title {
	margin-top: 4px;
	margin-bottom: 4px;
	line-height: 25px;
}

.item{
	margin-right: 5px;
}

#queryContent {
	padding-top: 2px;
	padding-bottom: 2px;
	padding-left: 2px;
	padding-right: 2px;
	
	margin-top: 2px;
	margin-bottom: 2px;
}

#contentCt {
	padding-left:2px;
 	padding-right:2px;
 	background-color: #edf1f7;
}

-->
</style>

<script type="text/javascript">
var ingridHeight=180;
var detailWindow;
detailWidth=930;
//督办容器
var supervision_div = "supervision_div";
//督办页面路径
var supervision_page = "business/techSupport/query/supervision.jsp";
//督办操作路径
var supervision_url = BUSNEISS_PATH + "/save_supervision.action";
//督办查询路径
var supervision_query_url = BUSNEISS_PATH +"/querylist_supervision.action";
//督办详情路径
var supervision_detail_url = BUSNEISS_PATH +"/query_supervision.action";

var supervision_width = 690;


// 申请人修改页面
var applicant_page = "business/techSupport/query/techsupportModify.jsp";
// 时间变更情况链接地址
var timeChangeUrl = "business/techSupport/query/timeChange.jsp";
var timeChangeDetailWidth = detailWidth;
var timeChangeCt = "timeChangeCt";

// 延迟加载
function lazyLoad(){
	queryPanelHeight = $("#queryPanel").outerHeight(true);
		ingridHeight=document.body.clientHeight -$('#allDiv').outerHeight(true) -queryPanelHeight
		-$('#title').outerHeight(true)-70;
	loadPageSupportTicketQuery(divnid);
	
}
//初始化加载
	$(function(){
		setTimeout(lazyLoad,5);
		divnid="queryContent";//查询内容容器ID
		tableid="queryContentTable";//查询内容格式表格ID
		pageUrl=BUSNEISS_PATH+ "/querylist_supportTicket.action";
		detailid="detailCt";
		
		detailURL="business/techSupport/query/techsupportDetail.jsp";
		queryTable=$("#"+tableid);
		detailWindow=$('#'+detailid);
		//lazyLoad(); 
		
		daggleDiv(detailid);
		
		//时间变更轨迹
	    daggleDiv('timeChangeCt');
		//统计口径窗口
		daggleDiv('statisticsCt');
		daggleDiv('statisticsDetailCt');
		$('#statisticsBtn').click(openStatisticsDialog); //统计按钮弹出统计口径选择框
// 		设置状态下拉条
		$('#p_stStatus').selectBox({code:ST_STATUS_DICT_CODE});
		//设置部门选择器
		
		$('#p_departcode').selectBox({code:"dm_ts_depart_list"});
// 		时间选择器设置
		$('.date').each(function(){
			$(this).attr('readOnly',true);
			$(this).datepicker();
		});

		
		$('.ro').attr('readOnly',true);
		//导出按钮
		$('#export_btn').attr("disabled",true);
		
		//上传地址
		var uploadURL="techsupport/importTechSupport_tscommon.action";
		//上传队列容器id
		var queue_id = 'fileUploadPanel';
		//上传组件名字
		var upload_name = 'upload';
		//上传文件存放地址
		var upload_folder = '/upload';
		//允许上传大小 字节
		var allow_max_size = 20971520;
		
		
		//20121109 支持单未完成导入
		daggleDiv('import_detail_div');
		$('#import_btn').click(function(){
			setWidth('import_detail_div', 480);
			$("#" + 'import_detail_div').empty();
			var html= '<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">'+
			'		    <tr>'+
			'		      <td align="left" class="title1">未完成支持单导入</td>'+
			'		      <td align="right"><a href="#" id="closeDiv" onclick="close_dialog(this);" class="close"></a></td>'+
			'		    </tr>'+
			'		</table>'+
			'		<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" style="margin-top:10px;">'+
			'			<tr>'+
			'				<td align="right">文件：</td>'+	
			'				<td>'+
			'					<div id="fileUploadPanel" style="width:260px;text-align:center;">请选择一个上传的文件</div>'+
			'				</td>'+
			'				<td>'+
			'					<input type="file" id="uploadFile">'+
			'				</td>'+
			'			</tr>'+
			'			<tr>'+
			'				<td colspan="2"></td>'+	
			'				<td>'+
			'					<a href="#" class="addbutton" id="uploadButton">确认</a>'+
			'				</td>'+
			'			</tr>'+
			'		</table>';
			$('#import_detail_div').html(html);
			$('#uploadFile').uploadify({
				  'uploader'  : 'business/techSupport/common/javascript/uploadify/uploadify.swf',  
                'script'    : uploadURL,  
                'cancelImg' : 'business/techSupport/common/javascript/uploadify/cancel.png',  
                'fileDataName':upload_name,
                'queueID' : queue_id,
                'folder': upload_folder,
                //解决中文按钮名的好方式  
                'buttonImg' : 'business/techSupport/common/javascript/uploadify/select_file.png',  
                //可选  
                'height'    : 22,  
                //可选  
                'width'     : 61,  
                //设置允许上传的文件格式  
                'fileExt'   : '*.xls;*.xlsx',  
                //设置允许上传的文件格式后，必须加上下面这行代码才能帮你过滤  
                'fileDesc'    : ['请选择xls,xlsx文件'],  
                //允许连续上传多个文件  
                'multi':false,  
                //一次性最多允许上传多少个,不设置的话默认为999个  
//                'queueSizeLimit' : 3,  
                //每个文件允许上传的大小(字节)  
                'sizeLimit'   : allow_max_size,
                'onAllComplete'  :  function(event,data) {
              	  if(data.filesUploaded){
              		  SupportTicketQuery($('#pageNo').val());
              		  alert("导入成功");
              	  }
              		  
              	  try{
              		  $('#import_detail_div').hideAndRemove("show");
              	  }catch (e) {}
                },
                'onSelect':function(file){
              	  if(file)
              		  $('#'+queue_id).text('');
                },
                onError: function(event,queueId,fileObj,errorObj){
              	  jAlert(fileObj.name+"导入错误，请联系管理员","警告");
                },
                auto:false
	});
	$('#uploadButton').click(function(){
		$('#uploadFile').uploadifyUpload();
	});
	
	$('#import_detail_div').css('top',document.body.offsetHeight/2-30+'px');
	upAllPage('import_detail_div');
	bindDocument('import_detail_div');
	$('#import_detail_div').show('slow');
	});
 		
		var roleURL="sysadmin/queryUsreRoleList_user.action"
		setParams('t_');
 		$.post(roleURL,params,function(data){
 			var rgm_mapping_dictitems=getDictitem({dictcode:ST_RGM_RG_MAP_DICT_CODE});
 			for(var i=0;i<data.userRoleList.length;i++){
//  				申请人角色 申请人选择框控制
 				if(ST_ROLE_NAME_APPLICANT == data.userRoleList[i].rolename){
 					//设置申请人
 					$('#applicantName').val('<%=username%>');
 					$('#p_applicantId').val('<%=user.getUserid()%>');

 					//支持单负责人选择器
 					$('#slName').unbind('click');
 					$("#slName").click(function(){
 						getUserofDept('slName','p_supportLeaderId',$('#p_departcode').val(),[ST_ROLE_NAME_STLEADER],true);
	 	 			});
 					
 				}
//  	 			督办 角色控制
 				else if(ST_ROLE_NAME_SUPERVISION == data.userRoleList[i].rolename){
 					$('#applicantName').click(function(){
 	 					var __gxdwbm=null;
 	 					if($('#p_region').val())
 	 	 					__gxdwbm = $('#p_region').val();
 	 					getUserofDept('applicantName','p_applicantId',__gxdwbm,[ST_ROLE_NAME_APPLICANT],true);
 	 				});
 					//设置技术负责人
 					$('#p_supportLeaderId').val('');
 					$('#slName').val('');
 					$('#slName').unbind('click');
 					$('#slName').click(function(){
 						getUserofDept('slName','p_supportLeaderId',$('#p_departcode').val(),ST_ROLE_NAME_STLEADER,true);
 					});
 					
 					//设置大区
 	 				$('#rgName').click(function(){
 	 					getDict_item('rgName','p_region',ST_REGION_DICT_CODE);
 	 				});
 					break;
 				}
 				//反馈人 控制
 				else if(ST_ROLE_NAME_FEEDBACKER == data.userRoleList[i].rolename){
					//设置大区智能选择器
					$('#rgName').unbind('click');
					$('#rgName').click(function(){
						getRegionWithRole('rgName','p_region');
					});
 	 			}
 	 			//支持单负责人 控制
 				else if(ST_ROLE_NAME_STLEADER == data.userRoleList[i].rolename){
 	 				//填报人
 	 				$('#applicantName').unbind('click');
 	 				$('#applicantName').click(function(){
 	 					var __gxdwbm=null;
 	 					if($('#p_region').val())
 	 	 					__gxdwbm = $('#p_region').val();
 	 					getUserofDept('applicantName','p_applicantId',__gxdwbm,[ST_ROLE_NAME_APPLICANT],true);
 	 				});
 	 				//设置支持单负责人为本人
					$('#slName').val("<%=username%>");
					$('#p_supportLeaderId').val("<%=user.getUserid()%>");
					
					//设置大区智能选择器
					$('#rgName').unbind('click');
					$('#rgName').click(function(){
						getDict_item('rgName','p_region',ST_REGION_DICT_CODE);
					});
 	 			}
 	 			//全部导出
 				else if (ST_ROLE_NAME_DM == data.userRoleList[i].rolename){
 	 				//设置导出按钮
 	 				$('#export_btn').attr('disabled',false);
 	 				$('#export_btn').click(function(){
 	 	 				window.open(BUSNEISS_PATH+'/export_excel_supportTicket.action');
						
 	 	 			});
 	 				
 	 				//20121109添加未完成支持单导入
 	 				$('#import_panel').show();
 	 			}
 	 			//区总
 				else if (data.userRoleList[i].rolename.indexOf('区总') != -1){
					//填报人
					$('#applicantName').unbind('click');
 					$('#applicantName').click(function(){
 	 					var __gxdwbm=null;
 	 					if($('#p_region').val())
 	 	 					__gxdwbm = $('#p_region').val();
 	 					getUserofDept('applicantName','p_applicantId',__gxdwbm,[ST_ROLE_NAME_APPLICANT],true);
 	 				});
					
 					//设置大区智能选择器
 					$('#rgName').unbind('click');
					$('#rgName').click(function(){
						getRegionWithRole('rgName','p_region');
					});

					//支持单负责人选择器
					$('#slName').unbind('click');
 					$("#slName").click(function(){
 						getUserofDept('slName','p_supportLeaderId',$('#p_departcode').val(),[ST_ROLE_NAME_STLEADER],true);
	 	 			});
 	 			}

 				
 			}
 			
 		},'json');
		
		
		
// 		初始化查询动作
		$('#queryBtn').click(function(){
			SupportTicketQuery(1);
		});
		
	});
	
	/**
	  * 表单验证
	  * */
	 function validate(){
		
		if($('#p_applyDateFrom').val() && $('#p_applyDateTo').val())
		{
			var fromDateStr = $('#p_applyDateFrom').val().replace("-","/"); 
			var toDateStr = $('#p_applyDateTo').val().replace("-","/");
			var fromDate = new Date(Date.parse(fromDateStr))
			var toDate = new Date(Date.parse(toDateStr));
			if(fromDate > toDate)
			{
				jAlert("支持单申请时间起始时间应该小于等于结束时间","提示");
				return false;
			}
		}
		
		//计划完成时间 
		if($('#p_scheTimeFrom').val() && $('#p_scheTimeTo').val())
		{
			var fromDateStr = $('#p_scheTimeFrom').val().replace("-","/"); 
			var toDateStr = $('#p_scheTimeTo').val().replace("-","/");
			var fromDate = new Date(Date.parse(fromDateStr))
			var toDate = new Date(Date.parse(toDateStr));
			if(fromDate > toDate)
			{
				jAlert("计划完成时间起始时间应该小于等于结束时间","提示");
				return false;
			}
		}
		//实际完成时间
		if($('#p_compTimeFrom').val() && $('#p_compTimeTo').val())
		{
			var fromDateStr = $('#p_compTimeFrom').val().replace("-","/"); 
			var toDateStr = $('#p_compTimeTo').val().replace("-","/");
			var fromDate = new Date(Date.parse(fromDateStr))
			var toDate = new Date(Date.parse(toDateStr));
			if(fromDate > toDate)
			{
				jAlert("实际完成时间起始时间应该小于等于结束时间","提示");
				return false;
			}
		}
	 	return true;
	 }
	 
	 function loadPageSupportTicketQuery(divpageid){
		tables=$("#"+divpageid).html();
		SupportTicketQuery(1,'#');
	}	

	 /**
	  * 查询函数
	  * */
	 function SupportTicketQuery(pageno,url){
	 	if (validate()){
	 		//总工查询时间控制
	 		if($('#p_trackingDateFrom').val() || $('#p_trackingDateTo').val()){
	 			$('#p_type').val(TRACKING_TYPE_CEREPLY);
	 		}
	 		else
	 			$('#p_type').val(null);
			url=setList(pageno,url);
			// create the grid
			// returns a jQ object with a 'g' property - that's ingrid
			if($('#'+tableid).length == 0){
				$('#'+divnid).html($(queryTable));
			}
			var mygrid1 = $("#"+tableid).ingrid({ 
											url: url,	
											height:ingridHeight,
											ingridPageWidth:1000,
											isPlayResultNull: false,
// 											havaWaiDivGunDong: true,
	                                       	ingridPageParams:sXML,
	                                       	onRowSelect:null,
											pageNumber: pageno,
											noSortColIndex:[3,4,6,7,8,9,10],
											changeHref:function(table){
												$("tr",table).each(function(){
													var __tr = $(this);
													var text = __tr.find('td').eq(9).text();
													//督办控制
													if(text == ST_STATUS_COMPLETE || text == ST_STATUS_STOP){
														__tr.find("a[title=督办]").remove();
													}
													//控制:只有申请人在待公司审批的时候才能够修改和删除
													if(userid == __tr.find('td:nth(8)').text() && text == ST_STATUS_WAIT_COMPANY_APPRAVAL){
														__tr.find('td:last').append('<a title="删除" class="fontbutton" href="#" onclick="setDelete('+__tr.attr('id')+')" style="margin-right:5px;">删除</a>');
														__tr.find('td:last').append('<a title="修改" class="fontbutton" href="#" onclick="setModify('+__tr.attr('id')+')" style="margin-right:5px;">修改</a>');
													}
													
													__tr.find('td:last').append('<a title="打印申请单" class="fontbutton" href="#" onclick="setPrintApply('+__tr.attr('id')+')" style="margin-right:5px;">打印申请单</a>');
													__tr.find('td:last').append('<a title="打印反馈单" class="fontbutton" href="#" onclick="setPrintFeedback('+__tr.attr('id')+')" style="margin-right:5px;">打印反馈单</a>');
													
													//添加时间变更情况
													__tr.find("td:last").append('<a title="时间变更情况" class="fontbutton" href="#" onclick="setTimeChange('+__tr.attr('id')+')" style="margin-right:5px;">时间变更情况</a>');
													//当在进行中的时候,“计划完成时间”<=当前时间的记录行，高亮（突出）显示
													var	scheTimeStr = __tr.find('td:nth(6)').html().trim().replace(/&nbsp;/g,'');
													if(scheTimeStr && text == ST_STATUS_GOING){
														var scheTimeArray = scheTimeStr.split("/");
														var	timeStr = scheTimeArray[scheTimeArray.length-1].split(":")[1];
														var scheTime = Date.parse(timeStr.replace(/-/g,"/"));
														var now = new Date();
														now = new Date(now.getYear(),now.getMonth(),now.getDate());
														if(scheTime <= now)
															__tr.find("td").css('color','red');
													}
													
													
												});
											},
											colWidths: ['11.1%','7%','7%','11.1%','11.1%','11.1%','15.1%','15.1%','0','0','11.1%'],
											hideColIndex:[8,9]
										});				
			}
	}
	/**
		详情展示
		
	*/ 
	function setDetail(id){
		dataid=id;
		detailDialog(detailid,detailWidth,detailURL,'技术支持单详情');
	}

	/**
		督办
	*/
	function set_supervision(id){
		dataid=id;
		detailDialog(supervision_div,supervision_width,supervision_page,'督办进展');
	}
	
	///////////////////////添加申请人修改和删除////////////////////////////
	/** 删除 */
	function setDelete(id){
		var delete_url = "techsupport/remove_supportTicket.action";
		var params = {"supportTicket.id":id};
		if(confirm("确认删除支持单记录?")){
			$.post(delete_url,params,function(data){
				if(data.result == "success")
					SupportTicketQuery($('#pageNo').val());
				else
					jAlert(data.result,"警告");
			});
		}
		
	}
	/** 修改 */
	function setModify(id){
		dataid=id;
		detailDialog(detailid,detailWidth,applicant_page,'支持单修改');
	}
	////////////////////////////////////////////////////////////////////
	
	//////////////////////////////打印///////////////////////////////////
	function setPrintApply(id){
		window.open('business/techSupport/query/techsupportApplyPrintViewer.jsp?stId='+id,'printWindow');
	}
	
	function setPrintFeedback(id) {
		window.open('business/techSupport/query/techsupportFeedbackPrintViewer.jsp?stId='+id,'printWindow');
	}
	////////////////////////////////////////////////////////////////////
	
// 	时间变更情况查询
  function setTimeChange(id){
		dataid=id;
	  detailDialog(timeChangeCt,timeChangeDetailWidth,timeChangeUrl,'时间变更情况');
	}
	
	/**
		打开统计口径选择窗口
	*/
	function openStatisticsDialog() {
		detailDialog('statisticsCt',300,'business/techSupport/query/statisticsType.jsp','统计口径');
		$('#statisticsCt').css('left',$(this).offset().left+"px")
											.css('top',$(this).offset().top + $(this).height()+5+"px");
	}
</script>
	</head>
	<body>
		<input type="hidden" id="p_tag" value="">
<input type="hidden" id="t_userid" value="<%=user.getUserid() %>">
<input type="hidden" id="p_processorId" value=""/>
<div id="tsworksheet" class="bnbody">
	<div id="title" class="queryfont">
		技术支持单查询
	</div>
	<div id="contentCt">
		<div id="queryPanel">
			<div class="row">
				<div class="column column-width-default">
					<label class="label" style="margin-right: 5px">支持单状态:</label>
					<select class="item" name="st.stStatus" id="p_stStatus">
						<option></option>
					</select>
					<div class="clear-column"></div>
				</div>
				<div class="column column-width-default">
					<label class="label">支持单申请时间:从:</label>
						<input type="text" class="item date  inputstyle" id="p_applyDateFrom">
<!--						<input type="hidden" value="" id="p_type">-->
					<div class="clear-column"></div>
				</div>
				<div class="column column-width-default">
					<label class="label">到:</label>
						<input type="text" class="item date  inputstyle" id="p_applyDateTo">
					<div class="clear-column"></div>
				</div>
				
				<div class="clear-row"></div>
			</div>
			<div class="row">
				<div class="column column-width-default">
						<label class="label">申请人:</label>
						<input type="text" class="item inputstyle ro" id="applicantName">
						<input type="hidden" id="p_applicantId">
					<div class="clear-column"></div>
				</div>
				
				<div class="column column-width-default">
						<label class="label">技术支持单负责人:</label>
						<input type="text" class="item inputstyle ro readOnly" id="slName">
						<input type="hidden" id="p_supportLeaderId" name="p_supportLeaderId">
					<div class="clear-column"></div>
				</div>
				
				<div class="column column-width-default">
					<label class="label">大区/区域:</label>
					<input type="text" class="item inputstyle ro" id="rgName">
					<input type="hidden" id="p_region" name="p_regionCode">
					<div class="clear-column"></div>
				</div>
				
				<div class="clear-row"></div>
			</div>
			
			<div class="row">
				<div class="column column-width-default">
					<label class="label" style="margin-right: 5px">技术支持部门:</label>
					<select class="item" id="p_departcode" >
						<option></option>
					</select>
					
					<div class="clear-column"></div>
				</div>
				<div class="column column-width-default">
					<label class="label" >计划完成时间:从:</label>
					<input type="text" class="item date  inputstyle" id="p_scheTimeFrom">
					<div class="clear-column"></div>
				</div>
				
				<div class="column column-width-default">
					<label class="label" >到:</label>
					<input type="text" class="item date  inputstyle" id="p_scheTimeTo">
					
					<div class="clear-column"></div>
				</div>
				
				<div class="clear-row"></div>
			</div>
			
			<div class="row">
				<div class="column column-width-default">
					<label class="label" >实际完成时间:从:</label>
					<input type="text" class="item date  inputstyle" id="p_compTimeFrom">
					
					<div class="clear-column"></div>
				</div>
				
				<div class="column column-width-default">
					<label class="label">到:</label>
					<input type="text" class="item date  inputstyle" id="p_compTimeTo">
					
					<div class="clear-column"></div>
				</div>
				<div class="clear-row"></div>
			</div>
			<div class="row">
				<div class="column" style="width: 65%;">
					<div class="clear-column"></div>
				</div>
				<div class="column" style="width:35%;">
					<div class="column">
						<a href="#" class="item submitbutton " id="statisticsBtn">统  计</a>
						<div class="clear-column"></div>
					</div>
					<div class="column">
						<a href="#" class="item searchbutton " id="queryBtn">查  询</a>
						<div class="clear-column"></div>
					</div>
					<%--
					添加未完成导入功能, 默认不显示
					 --%>
					<div class="column" style="display: none;" id="import_panel">
						<a href="#" class="item submitbutton " id="import_btn" style="margin-left: 10px;">导  入</a>
						<div class="clear-column"></div>
					</div>
					<div class="column">
						<a href="#" class="item submitbutton" id="export_btn" style="margin-left: 10px;">全部导出</a>
						<div class="clear-column"></div>
					</div>
					<div class="clear-column"></div>
				</div>
				<div class="clear-row"></div>
			</div>
			
		</div>
		<div id="queryContent">
			<table id="queryContentTable" width="100%" border="1">
			  <thead>
			    <tr>       
			     	<th name="l_stNo">支持单编号</th>
			     	<th name="l_region">大区/区域</th>
			     	<th name="l_applicant">申请人</th>
			     	<th name="l_supportLeader">技术支持单负责人</th>
			     	<th name="l_supportDept">技术支持部门</th>
			     	<th name="l_supportStatusnName">状态</th>
			     	<%--需要合并 --%>
			     	<th name="l_scheTime">计划完成时间</th>
			     	<th name="l_compTime">实际完成时间</th>
			     	<%-- 申请人修改删除用 --%>
			     	<th name="l_applicantId">申请人ID</th>
			     	<%--支持单状态代码 --%> 
			     	<th name="l_stStatus">状态代码</th>
			     	<th name="">操作</th>
			    </tr>
			  </thead>
			</table>
			
		</div>
	</div>
<div id="detailCt" style="position: absolute; z-index: 1000; top:0px; left:160px; display: none;" class="page-layout"></div>
<div id="supervision_div" style="position: absolute; z-index: 1000; top:0px; left:160px; display: none;" class="page-layout"></div>
<div id="import_detail_div" style="position: absolute; z-index: 1000; top:0px; left:160px; display: none;" class="page-layout"></div>
<div id="timeChangeCt" style="position: absolute; z-index: 1000; top:0px; left:160px; display: none;" class="page-layout"></div>
<div id="statisticsCt" style="position: absolute; z-index: 1000; top:0px; left:160px; display: none;" class="page-layout"></div>
<div id="statisticsDetailCt" style="position: absolute; z-index: 1000; top:0px; left:160px; display: none;" class="page-layout"></div>
	</body>
</html>
