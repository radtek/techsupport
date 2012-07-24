/**
 * 流程控制
 * workflowManager.js
 */


/**
 * 成功回调
 * @param data
 */
function success_callback(data){
	if(!data.returnNo){
		$('#iframe').load('business/techSupport/worksheet/workflowManager.jsp');
	}
	else{
		alert(data.returnMsg);
	}
	
}
/**
 * 发布基于xml的新流程
 */
function deployByXml(){
	var url = BUSNEISS_PATH +"/deploy_by_xml_worksheet.action";
	$.post(url,null,success_callback,'json');
}

/**
 * 发布在线编辑的流程
 */
function deployOnline(){
	
}

/**
 * 删除已经发布的流程，这是一个递归删除，在删除的流程
 * 的时候，他会清除所有还在使用这个流程的实例，不过也他会
 * 清除业务数据，只清除流程数据。
 */
function removeDeployment(deploy_id){
	var url = BUSNEISS_PATH +"/remove_deployment_worksheet.action";
	var para = {deploy_id:deploy_id};
	$.post(url,para,success_callback,'json');
}

