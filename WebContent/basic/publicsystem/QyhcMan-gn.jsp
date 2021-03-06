<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
<script type="text/javascript">
	var qyjbxx_requestType=""; //添加、查看、修改
	var qyjbxx_dataid="";
	var qyjbxx_tables = "";
	var qyjbxx_divnid="QyjbxxData";
	var qyjbxx_tableid="QyjbxxTable";
	var qyjbxx_pageUrl="publicsystem/queryQyhcList_qyjbxx.action";
	var qyjbxx_modHtml="basic/publicsystem/QyjbxxModify-gzth.jsp";
	var qyjbxx_modUrl="publicsystem/hecha_qyjbxx.action";
	var qyjbxx_modWidth="1000";
	var qyjbxx_detailid="qyjbxx_detail";
	var qyjbxx_detailHtml="basic/publicsystem/QyjbxxDetail-gzth.jsp";
	var qyjbxx_detailUrl="publicsystem/query_qyjbxx.action";
	var qyjbxx_detailWidth="1000";
	var qyjbxx_isPlayResultNull = true;
	$(document).ready(function() {
		qyjbxx_loadPage(qyjbxx_divnid);
		
		$("#p_zabdm").selectBox({code:"dm_cszajb"});// 治安级别
		$("#p_relation").selectBox({state:"temp",width:"80px"});
		
		$("#p_gxdwmc").click(function(){ //管辖单位
			getGxdw("p_gxdwmc","p_gxdwbm");
		});
		
		// 行业类别与企业主分类联动
	    hylbLinkQyzfl("p_hylbdm","p_qyzflbm");
		
		daggleDiv(qyjbxx_detailid);
	});
	
	function qyjbxx_loadPage(divpageid){
		qyjbxx_tables=$("#"+divpageid).html();
		$("#"+qyjbxx_detailid).hide(); 	
		qyjbxx_setPageList(1,'#');
	}
	function qyjbxx_setList(pageno,url){	
		$("#"+qyjbxx_divnid).html(qyjbxx_tables);	
		createXML("p_");
		if (url==null || url=="undefined"){
			url=qyjbxx_pageUrl;
		}
		return url;
	}
	function qyjbxx_setPageList_cx(){
		qyjbxx_isPlayResultNull = true;
		qyjbxx_setPageList(1);
	}
	function qyjbxx_setPageList_gengxin(){
		qyjbxx_isPlayResultNull = false;
		qyjbxx_setPageList(1);
	}
	function qyjbxx_setPageList(pageno,url){
		if (manVerify()){
			url=qyjbxx_setList(pageno,url);
			var mygrid1 = $("#"+qyjbxx_tableid).ingrid({ 
										url: url,
										ingridPageParams:sXML,
										height: pageHeight-253,
										isPlayResultNull:qyjbxx_isPlayResultNull,
										pageNumber: pageno,
										//colClasses: ['grid-col-style2','grid-col-style2'],
										//colIndex: [0,1],
										onRowSelect: function(tr, selected){
										},
										onTdSelect: function(tr){
											$(tr).find("td").click(function(){
												if (sFlag=="true"){
													var tdnum = $(tr).find("td").index(this);
													if(tdnum!=$(tr).find("td").length-1){
														qyhc_setQuery($(tr).attr("id"));
													}
												}else{
													sFlag="true";
												}
											});	
										},
										colWidths: ["19%","34%","10%","23%","10%","5%"]									
									});				
		}
	}
	function qyhc_setQuery(id){
		qyjbxx_requestType="detail";
		qyjbxx_dataid=id;
		setWidth(qyjbxx_detailid,qyjbxx_detailWidth);
		setUrl(qyjbxx_detailid,qyjbxx_detailHtml);
		bindDocument(qyjbxx_detailid);
	}
	function qyjbxx_setDetail(){
		setParams("q_");
		jQuery.post(qyjbxx_detailUrl,params,qyjbxx_updatediv,"json");
	}
	function qyhc_setHecha(id){
		qyjbxx_requestType="hecha";
		qyjbxx_dataid=id;
		setWidth(qyjbxx_detailid,qyjbxx_modWidth);
		setUrl(qyjbxx_detailid,qyjbxx_modHtml);
	}
	function qyjbxx_hecha(){ //核查
		if (qyjbxx_modifyVerify()){
			ajaxAddDivCeng();
			dataid=['baryid','bwryid','bldsjksbid','anjcsbid'];
			var child=new Array("BaryxxListData","BwryxxListData","BldsjksbListData","AqjcsbListData");
			setParams("m_",child);
			jQuery.post(qyjbxx_modUrl,params,qyjbxx_hechaback,"json"); 
		}
	}
	function qyjbxx_hechaback(json){	
		removeAllDivCeng();			
		if  (json.result=="success"){
			jAlert('操作成功','保存信息');
			qyjbxx_setPageList_gengxin(); //不提示 查询结果为空
			$("#"+qyjbxx_detailid).hideAndRemove("slow"); 
		}else{
			jAlert(json.result,'错误信息');
		}
	}
	function qyhc_setWidth(divid,sWidth){
		$("#"+divid).css("width",sWidth+"px");
		$("#"+divid).css("left","0px");
	}
	function gaojichaxun_qyhcgnd(){
		gaojichaxun("44",qyjbxx_detailid);
	}
	function manVerify(){
		if (!checkControlValue("p_qymc","String",1,120,null,0,"企业名称"))
			return false;
		if (!checkControlValue("p_zrs","Integer",-999999,999999,null,0,"从业人员数量"))
			return false;
		if (!checkControlValue("p_gxdwmc","String",1,60,null,0,"管辖单位名称"))
			return false;
		if (!checkControlValue("p_qybm","String",1,20,null,0,"企业编码"))
			return false;
		if (!checkControlValue("p_zjbh","String",1,60,null,0,"营业执照编号"))
			return false;
		return true;
	}
	function formatDate(strDate){ //转换成日期格式
		var rqAndTime = strDate.split(" ");
		var riqi = rqAndTime[0].split("-");
		return new Date(riqi[0],(riqi[1]-1),riqi[2]);
	}
	function dateDiff(datestart,dateend){ //日期相差的天数
		return parseInt(Math.abs(datestart - dateend) / 1000 / 60 / 60 /24);
	}
	function gaojichaxun_qyhc(){
		gaojichaxun("44,46",qyjbxx_detailid,"qyjbxx_setPageList");
	}
</script>
</head>
<body>
<input type="hidden" id="p_gxdwbm" value=""/>
<table width="100%" cellpadding="0" cellspacing="0"  class="tableborder">
  <tr>
    <td class="queryfont">企业核查</td>
  </tr>
  <tr>
    <td class="tdbg">
    	<table width="100%" border="0" cellspacing="0" cellpadding="2">
				<tr>
				    <td width="10%" class="pagedistd">行业类别</td>
			       <td width="23%" class="pagetd"><select type="select" id="p_hylbdm"></select>
			       </td>
					<td width="10%" class="pagedistd">企业分类</td>
					<td width="23%" class="pagetd" colspan="2"><select name="select" id="p_qyzflbm">
			        <option></option></select></td>
					<td width="10%" class="pagedistd">管辖单位</td>
					<td width="23%" class="pagetd"><input type="text" class="inputstyle"  id="p_gxdwmc" value="" readonly></td>
				</tr>
				<tr>
				    <td width="10%" class="pagedistd">企业名称</td>
				    <td width="23%" class="pagetd"><input type="text" class="inputstyle" id="p_qymc" value=""></td>
					<td width="10%" class="pagedistd">企业代码</td>
					<td width="23%" class="pagetd" colspan="2"><input type="text" class="inputstyle"  id="p_qybm" value=""></td>
					<td width="10%" class="pagedistd">营业执照编号</td>
				    <td width="23%" class="pagetd"><input type="text" class="inputstyle"  id="p_zjbh" value=""></td>
		 		 </tr>
				 <tr>
				   <td width="10%" class="pagedistd">治安级别</td>
				    <td width="23%" class="pagetd"><select id="p_zabdm">
		            <option></option></select></td>
				   <td width="10%" class="pagedistd">从业人员数量</td>
				    <td class="pagetd" width="6%">
				    <select style="width:60px;" id="p_relation">
				    <option></option>
				    <option value="3">等于</option>
				    <option value="4">大于</option>
				    <option value="5">小于</option>
				    <option value="1">大于等于</option>
				    <option value="2">小于等于</option>
				    </select></td>
				    <td class="pagetd"><input type="text" class="inputstyle"  id="p_zrs" style="width:60px;"></td>
				    <td colspan="2">
                    <table width="124" border="0" align="right" cellpadding="0" cellspacing="0">
	                    <tr>
	                      <td><a href="#" class="searchbutton" id="querys" onClick="qyjbxx_setPageList_cx();">查询</a></td>
	                    </tr>
	                </table>
                    </td>
				 </tr>
    	</table>
    </td>
  </tr>
</table>	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td height="3"></td>
		</tr>
	</table>
<div id="qyjbxx_detail" class="page-layout" src="#" style="z-index:2000; top:10px; left:0px;">
</div>
<div id="QyjbxxData" style="width:100%;">
	<table id="QyjbxxTable" width="100%">
	  <thead>
	    <tr>       
	     	<th name="l_qybm">企业代码</th>
	     	<th name="l_qymc">企业名称</th>
	     	<th name="l_zrs">总人数</th>
	     	<th name="l_qyzflmc">行业类别</th>
	     	<th name="l_ztmc">状态</th>
			<th name="">操作</th>
	    </tr>
	  </thead>
	  
	</table>	
</div>
</body>
</html>