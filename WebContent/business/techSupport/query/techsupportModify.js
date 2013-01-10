/**
 * techsupportModify.js
 * 申请人修改
 * */
 
var processUrl2;
var ingridUrl;
var ingridWidth=400;
//附件
var attachment_div_id="attachment_list_div";
var attachment_table_id="attachment_list_table";
var attachment_tables;
//附件查询路径
var attachment_query_url = BUSNEISS_PATH +"/querylistAttachment_tscommon.action";


function load_page_attachment_query(divpageid){
	attachment_tables=$("#"+divpageid).html();
	attachment_query(1,'#');
}

function set_attachment_list(pageno,url){	
	$("#"+attachment_div_id).html(attachment_tables);
	createXML("att_");
	if (url==null || url=="undefined"){
		url=attachment_query_url;
	}
	return url;
}

/**
 * 查询函数
 * */
function attachment_query(pageno,url){
	
	if (true){
		url=set_attachment_list(pageno,url);
		// create the grid
		// returns a jQ object with a 'g' property - that's ingrid
		var mygrid2 = $("#"+attachment_table_id).ingrid({ 
										url: url,	
										height:40,
										ingridPageWidth:820,
										isPlayResultNull: false,
										havaWaiDivGunDong: true,
                                      	ingridPageParams:sXML,
                                      	onRowSelect:null,
										pageNumber: pageno,
										noSortColIndex:[3],
										changeHref:function($table){
											render_attachment_filesize($table);
										},
										hideColIndex:[2],
										colWidths: ["70%","10%","10%","20%"]			
									});
		}
}


/** onload */
$(function(){
	//只读化控件
	$('.ro').attr('readOnly',true);
	//设置时间控件
	$('.date').each(function(){
		$(this).datepicker();
		$(this).attr('readOnly',true);
	});
	
	
	//初始化该页面值
	$('#id').val(dataid);
	
	
	processUrl2=getContextPath()+ "/techsupport/query_supportTicket.action";
	
	loadData();
	//附件显示框
	load_page_attachment_query(attachment_div_id);
	
	//保存动作
	$('#modify_btn').click(applicant_modify);
});

/**
 * 初始化页面数据
 */
function loadData(){
	var paramsss = {'supportTicket.id':$('#id').val()};
	
	$.post(processUrl2,paramsss,function(data){
		$('input[name^=st.]').each(function(){
			try{
				$(this).val(setNull(eval("("+"data.lSupportTicket[0]"+$(this).attr('name').substring($(this).attr('name').indexOf('.'))+")")));
			}catch(e){
				
			}
			
		});
		$('textarea[name^=st.]').each(function(){
			try{
				$(this).val(eval("("+"data.lSupportTicket[0]"+$(this).attr('name').substring($(this).attr('name').indexOf('.'))+")"));
			}catch(e){
				
			}
			
		});
//		提起反馈时间
		var applyingFeedbackDate=$('#applyingFeedbackDate').val();
		if(applyingFeedbackDate){
			applyingFeedbackDate=/^(\d{4}-\d{2}-\d{2}) \d{2}:\d{2}:\d{2}$/.exec(applyingFeedbackDate)[1];
			$('#applyingFeedbackDate').val(applyingFeedbackDate);
		}
			
//		反馈确认时间
		var feedbackConfirmDate=$('#feedbackConfirmDate').val();
		if(feedbackConfirmDate){
			feedbackConfirmDate=/^(\d{4}-\d{2}-\d{2}) \d{2}:\d{2}:\d{2}$/.exec(feedbackConfirmDate)[1];
			$('#feedbackConfirmDate').val(feedbackConfirmDate);
		}
		
		//		初始化单位信息
		var deptNameStr="";
		var deptList = data.lSupportTicket[0].supportDeptList;
		for(i=0;i<deptList.length;i++){
			deptNameStr += ","+deptList[i].departname;
		}
		deptNameStr = deptNameStr.substring(1,deptNameStr.length);
		
		$('#deptName').val(deptNameStr);
		
		//		初始化技术负责人
		if(data.lSupportTicket[0].lstSupportLeaders){
			var sSlNames="";
			
			for(i=0;i<data.lSupportTicket[0].lstSupportLeaders.length;i++){
				sSlNames+=","+data.lSupportTicket[0].lstSupportLeaders[i].username;
			}
				
			sSlNames=sSlNames.length > 0? sSlNames.substring(1) : sSlNames;
			$('#q_slName').val(sSlNames);
		}
		
		var regionCode = $('#region').val();
		var region = getDictitem({dictcode:ST_REGION_DICT_CODE,value:regionCode})[0];
		$('#region').val(region.display_name);
		$('#track_stId').val(data.lSupportTicket[0].id);
		//附件
		$('#att_stId').val(data.lSupportTicket[0].id);
		
		attachment_query(1);
		
		
		//上传地址
		var uploadURL="techsupport/common_upload.action?"+'uploadId='+$('#batchNumber').val();
		//上传队列容器id
		var queue_id = 'fileUploadPanel';
		//上传组件名字
		var upload_name = 'upload';
		//上传文件存放地址
		var upload_folder = '/upload';
		//允许上传大小 字节
		var allow_max_size = 1024000000;
		
//		daggleDiv(queue_id);
		//设置上传
		$('#uploadFile').uploadify({
					  'uploader'  : 'business/techSupport/common/javascript/uploadify/uploadify.swf',  
	                  'script'    : uploadURL,  
	                  'cancelImg' : 'business/techSupport/common/javascript/uploadify/cancel.png',  
	                  'fileDataName':upload_name,
	                  'queueID' : queue_id,
	                  'folder': upload_folder,
	                  'scriptData':{stId:$('#id').val()},
	                  //解决中文按钮名的好方式  
	                  'buttonImg' : 'business/techSupport/common/javascript/uploadify/select_file.png',  
	                  //可选  
	                  'height'    : 22,  
	                  //可选  
	                  'width'     : 61,  
	                  //设置允许上传的文件格式  
	                  'fileExt'   : '*;',  
	                  //设置允许上传的文件格式后，必须加上下面这行代码才能帮你过滤  
//	                  'fileDesc'    : 'txt',  
	                  //允许连续上传多个文件  
	                  'multi':true,  
	                  //一次性最多允许上传多少个,不设置的话默认为999个  
//	                  'queueSizeLimit' : 3,  
	                  //每个文件允许上传的大小(字节)  
	                  'sizeLimit'   : allow_max_size,
	                  'onSelect' : function(file) {
	                      var left = pageWidth / 2 - $('#'+queue_id).width() / 2 ;
	                      var top = document.documentElement.clientHeight / 2 - $('#'+queue_id).height() / 2 ;
	                      $('#'+queue_id).css('top',top).css('left',left)
	                      				.show();
	                  },
	                  'onAllComplete'  : function(event,data) {
	                	  $('#'+queue_id).hide();
	                	  attachment_query(1);
	                  },
	                  auto:false
		});
		
		$('#uploadButton').click(function(){
			$('#uploadFile').uploadifyUpload();
		});
	},'json');
	
	
}

/**
 * 申请人修改验证
 * @returns {Boolean}
 */
function modify_verify(){
	if (!checkControlValue("supportContent","String",1,7000,null,1,"技术支持单内容"))
		return false;
	return true;
}
/**
 * 申请人修改
 */
function applicant_modify(){
	$.post("techsupport/modify_supportTicket.action",
			{'supportTicket.supportContent':$('#supportContent').val(),'supportTicket.id':$('#id').val()},
			function(data){
				if(data.result == "success")
					$("#detailCt").hideAndRemove("show");
				else
					jAlert(data.result,"警告");
			},'json');
}
