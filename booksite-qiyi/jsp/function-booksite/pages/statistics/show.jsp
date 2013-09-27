<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/function/function-booksite/pages/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>统计</title>
<link href="${prc}/function/function-booksite/styles/basic.css" rel="stylesheet" type="text/css" />
<script src="${prc}/function/function-booksite/scripts/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="${prc}/function/function-booksite/scripts/backgroundPopup.js" type="text/javascript"></script>
<script src="${prc}/common/agent.js" type="text/javascript"></script>
<script language="JavaScript" src="${prc}/function/function-booksite/FusionChartsFree/JSClass/FusionCharts.js"></script>
<script type="text/javascript" src="${prc}${dataPicker}"></script>
<script type="text/javascript">

	function submitSiteForm(type){
		$("#type").val(type);
		$("#siteForm").submit();
	}
	
</script>
</head>

<body>
<div class="box">
	<div class="box_head">
    	<div class="left">
    		<b><a href="${prc }/bookSite/indexUser.action">场馆预订</a></b>
    		<font size="2" color="#336699">&raquo</font><a href="${prc }/bookSite/adminUser.action">管理员管理</a>
    		<font size="2" color="#336699">&raquo</font><a href="${prc }/bookSite/showStatistics.action?type=1">预订信息统计</a>
    	</div>
        <div class="right"><a href="${prc }/bookSite/adminUser.action" hidefocus="true"> << 返回上一页</a></div>
    </div>
    <div class="box_main">
    	<div class="box_main_top">
        </div>
        <div class="box_main_center">
        	<s:hidden name="#request.beginTime" id="beginval"></s:hidden>
            <s:hidden name="#request.endTime" id="endval"></s:hidden>
            <form action="${prc}/bookSite/showStatistics.action" method="post" name="siteForm" id="siteForm" >
            	<input type="hidden" name="type" id="type" value="1" />
				<div class="tj_search">
					时间：从  <input type="text" class="Wdate" name="beginDate" value="${beginDate}" id="beginDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endDate\');}'})"/> 到
		                <input type="text" class="Wdate" name="endDate" value="${endDate}" id="endDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'beginDate\');}'})"/>
				</div>
            	<div class="tj_search">
            		<input class="btn2" id="bt1" type="button" value="按场馆次数统计" onclick="javascript:submitSiteForm(1);" />
            		<input class="btn2" id="bt2" type="button"  value="按活动级别统计" onclick="javascript:submitSiteForm(2);" />
            	</div>
            </form>
            
            <div class="tj_map" id="chartdiv" style="height: 500px;"> </div>
            <script type="text/javascript"> 
			   var chart = new FusionCharts("${prc}/function/function-booksite/FusionChartsFree/Charts/FCF_Column3D.swf", "chartdiv", "750", "500");
			   chart.setDataXML("${xml}");		   
			   chart.render("chartdiv");
			</script>
			<div id="siteNoList" class="tj_search" style="width:700px; text-align: center;margin: 20px;">
				<c:if test="${not empty siteNameList}">
					<c:forEach items="${siteNameList}" var="siteName" varStatus="i">
						<div style="display:inline; color:#${siteName.color};">
							■
							<font style="color:black;font-weight: bold;">${siteName.name}
							</font>&nbsp;&nbsp;&nbsp;
						</div>
					</c:forEach>
				</c:if>
			</div>
			<!--
	            <h4 class="map_title"> 图为：按场馆次数统计 </h4>
	            <div class="tj_search">
	            	<form method="post" id='stat' action="retrieveXLSBookSite" method="/bookSite">
	            		<input type="hidden" name="statisticsId" value="1"/>
	            		<input type="submit" class="btn2" id='bt3' value="导出EXCEL" />
	            	</form>
	            </div>
        	-->
        </div>
    </div>
</div>
</html>