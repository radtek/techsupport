<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>时间变更情况</title>
<script type="text/javascript">
var developTimeCt = "developTimeChangeDiv";
var developTimeTableId = "developTimeChangeTable";
var developTimeCtWidth=505;
var developTimeTable;

var productTimeCt = "productTimeChangeDiv";
var productTimeTableId = "productTimeChangeTable";
var productTimeCtWidth  = 430;
var productTimeTable;

var timeChangeQueryUrl = BUSNEISS_PATH + "/querylist_timeChange.action";
$(function(){
	initProductTimeChangeQuery(productTimeCt);
	
	initDevelopTimeChangeQuery(developTimeCt);
	
	productTimeChangeQuery(1);
	
	developTimeChangeQuery(1);
});
function initProductTimeChangeQuery(divpageid){
	productTimeTable=$("#"+divpageid).html();
	productTimeChangeQuery(1,'#');
	} 
	 function setProductTimeChangeQuery(pageno,url){
	  $("#"+productTimeCt).html(productTimeTable);
// 	  createXML("tc_");
	  params = {'timeChange.tracking.stId':dataid,'timeChange.type':ST_TIME_CHANGE_TYPE_PRODUCT};
	  if (url==null || url=="undefined"){
	    url=timeChangeQueryUrl;
	  }
	  return url;
	 }
	 /**
	  * 查询函数
	  * */
	 function productTimeChangeQuery(pageno,url){

	  if (true){
	    url=setProductTimeChangeQuery(pageno,url);
	    // create the grid
	    // returns a jQ object with a 'g' property - that's ingrid
	    var mygrid1 = $("#"+productTimeTableId).ingrid({
	                    url: url, 
	                    height:ingridHeight,
	                    ingridPageWidth:productTimeCtWidth,
	                    ingridExtraParams:params,
                      ingridPageParams:sXML,
                      noSortColIndex:[0,1,2,3,4,5,6,7,8,9],
                      onRowSelect:null,
	                    pageNumber: pageno,
	                    hideColIndex:[4,5,6,7,8],
	                    colWidths: ["15%","15%","15%","15%","0","0","0","0","0","40%"]        
	                  });       
	    }
	}
	 
	 function initDevelopTimeChangeQuery(divpageid){
		 developTimeTable=$("#"+divpageid).html();
		  developTimeChangeQuery(1,'#');
		  } 
		   function setDevelopTimeChangeQuery(pageno,url){
		    $("#"+developTimeCt).html(developTimeTable);
//		    createXML("tc_");
		    params = {'timeChange.tracking.stId':dataid,'timeChange.type':ST_TIME_CHANGE_TYPE_DEVELOP};
		    if (url==null || url=="undefined"){
		      url=timeChangeQueryUrl;
		    }
		    return url;
		   }
		   /**
		    * 查询函数
		    * */
		   function developTimeChangeQuery(pageno,url){

		    if (true){
		      url=setDevelopTimeChangeQuery(pageno,url);
		      // create the grid
		      // returns a jQ object with a 'g' property - that's ingrid
		      var mygrid1 = $("#"+developTimeTableId).ingrid({
		                      url: url, 
		                      height:ingridHeight,
		                      ingridPageWidth:developTimeCtWidth,
		                      ingridExtraParams:params,
		                      ingridPageParams:sXML,
		                      noSortColIndex:[0,1,2,3,4,5,6,7,8,9],
		                      onRowSelect:null,
		                      pageNumber: pageno,
		                      hideColIndex:[2,3],
		                      colWidths: ["15%","15%","0","0","10%","10%","10%","10%","10%","20%"]        
		                    });       
		      }
		  }
</script>
<style type="text/css">
	.timeCt {
		padding: 5 5 5 5;
	}
</style>
</head>
<body>
  <div id="productTimeCt" style="width:45%;float: left;" class="timeCt">
    <fieldset>
      <legend>产品计划时间变更</legend>
      <div id="productTimeChangeDiv" >
        <table id="productTimeChangeTable">
         <thead>
          <tr>
             <th>变更人</th>
            <th>变更时间</th>
            <%-- 产品计划时间 --%>
            <th>计划完成时间</th>
            <th>需求阶段</th>
            <%--开发计划时间,需要隐藏 --%>
            <th>计划完成时间</th>
            <th>设计阶段</th>
            <th>开发阶段</th>
            <th>测试阶段</th>
            <th>实施阶段</th>
            <th>变更原因</th>
          </tr>
         </thead>
        </table>
      </div>
    </fieldset>
  </div>
  <div id="developTimeCt" style="float: left;width:53%" class="timeCt">
    <fieldset>
      <legend>开发计划时间变更</legend>
      <div id="developTimeChangeDiv">
        <table id="developTimeChangeTable">
        	 <thead>
          <tr>
            <th>变更人</th>
            <th>变更时间</th>
            <%-- 产品计划时间 ,需要隐藏--%>
            <th>计划完成时间</th>
            <th>需求阶段</th>
            <%--开发计划时间--%>
            <th>计划完成时间</th>
            <th>设计阶段</th>
            <th>开发阶段</th>
            <th>测试阶段</th>
            <th>实施阶段</th>
            <th>变更原因</th>
          </tr>
           </thead>
        </table>
      </div>
    </fieldset>
  </div>
</body>
</html>