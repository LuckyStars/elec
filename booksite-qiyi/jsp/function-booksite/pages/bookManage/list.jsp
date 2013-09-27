<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/function/function-booksite/pages/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台管理预订场馆信息</title>
<script src="${prc }/function/function-booksite/scripts/jquery-1.4.4.min.js"></script>
<link href="${prc }/function/function-booksite/styles/basic.css" rel="stylesheet" type="text/css" />
<link href="${prc }/function/function-booksite/styles/pagination.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${prc}${dataPicker}"></script>
<script src="${prc }/function/function-booksite/scripts/backgroundPopup.js" type="text/javascript"></script>
<script src="${prc }/function/function-booksite/scripts/select.js" type="text/javascript"></script>
<script src="${prc }/common/agent.js" type="text/javascript"></script>
<script language="javascript">
function jumpBookSite(totalPage){
	$("#pageNoErr").html("");
	var pageNo = $("#jumpPageNo").val();
	if(isNaN(pageNo)){
		$("#pageNoErr").html("请输入整数!");
		return false;
	}
	pageNo = parseInt(pageNo);
	if(pageNo<1||pageNo>parseInt(totalPage)){
		$("#pageNoErr").html("输入页数超出范围!");
		return false;
	}
	return true;
}

function cancelBook(siteId){
	if(confirm("确定取消预订?")){
		location.href="${prc }/bookSite/cancelBookInfoBookSite.action?bookSite_id="+siteId;
	}
}
</script>

</head>

<body>

<div class="box">
	<div class="box_head">
    	<div class="left">
        <b><a href="${prc }/bookSite/retrieveOpenSite.action">场馆预订</a></b>
        <font size="2" color="#336699">&raquo</font><a href="${prc }/bookSite/adminUser.action">管理员管理</a>
        <font size="2" color="#336699">&raquo</font><a href="${prc }/bookSite/retrieveAllBackBookSite.action">预订管理</a>
        </div>
        <div class="right"><font size="2" color="#336699">&laquo</font><a href="../bookSite/adminUser.action" hidefocus="true">返回上一页</a></div>
    </div>
	<div class="box_main">
	<div class="box_main_top"></div>
	<div class="box_main_center">
	<div class="search2"><s:hidden name="opId" id='selid'></s:hidden>
	
	<s:form method="post" namespace="/bookSite" action="searchBookManage">
		<b>选择查询条件：</b>
		
		<select name="opId" size="1" id="opId">
			<option value="0" id='0s'>全部</option>
			<option value="1" id='1s'>日期</option>
			<option value="4" id='4s'>名称</option>
		</select>
		
		<ul id='hideli'>
			<li id='1' class="li_mr"><input id="beginTime" name="beginTime"
				value="${beginTime}" class="Wdate" readonly="readonly"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\');}'})" />
			到&nbsp;&nbsp; <input id="endTime" name="endTime" value="${endTime}"
				class="Wdate" readonly="readonly"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'beginTime\');}'})" />
				<input class="btn0" type="submit" value="搜索" />
			</li>
			<%-- <li id='0' class="li_mr">
				<input class="btn0" type="button" onclick="javascript:location.href='${prc}/bookSite/searchBookSiteBS.action'" value="搜索" />
			</li> --%>
			<li id='4'>
				<select name="siteId">
				<s:iterator value="listSite">
					<option value="${site_id}"
						<c:if test="${siteId eq  site_id}">selected="selected"</c:if>>${site_name}</option>
				</s:iterator>
				</select>
				<input class="btn0" type="submit" value="搜索" />
			</li>
		</ul>
	</s:form></div>
	<div class="table_box">
	<h1><img
		src="${prc }/function/function-booksite/images/title_img.gif"
		align="absmiddle" /><b>场馆预订管理</b></h1>
	<table id="table5" width="740" bgcolor="#CCCCCC" border="0"
		cellspacing="1" cellpadding="0">
		<tr class="title">
	
			<td width="150"><b>场馆名称</b></td>
			<td width="100"><b>活动级别</b></td>
			<td width="275"><b>预订日期</b></td>
			<td width="100"><b>预订人</b></td>
			<td><b>操作</b></td>
		</tr>
		<s:iterator value="pm.datas" status="sta">
			<s:if test="bookSite_statusId==0">
				<tr bgcolor="#FFFFFF">
					<td><s:if test="bookSite_clashId != null">
						<img class="gt_img"
							src="${prc }/function/function-booksite/images/gif_03.gif"
							align="absmiddle" />
					</s:if> <s:property value="site.site_name" /></td>
					<td><s:property value="activityLevel.activityLevel_name" /></td>
					<td><s:date name="bookSite_beginTime" format="yyyy-MM-dd HH:mm" />
					到 <s:date name="bookSite_endTime" format="yyyy-MM-dd HH:mm" /></td>
					<td><s:property value="userName" /></td>
					<td><s:if test="bookSite_statusId == 2">
						<a href="javascript:;" style="color: #ccc;" class="a_3">查看</a>
						<a
							href="${prc }/bookSite/cancelBookInfoBookSite.action?bookSite_id=<s:property value="bookSite_id"/>"
							class="a_3">取消</a>
					</s:if> <s:else>
						<a
							href="${prc }/bookSite/retrieveByIdBackBookSite.action?bookSite_id=<s:property value="bookSite_id"/>"
							class="a_3">查看</a>
						<a
							href="${prc }/bookSite/cancelBookInfoBookSite.action?bookSite_id=<s:property value="bookSite_id"/>"
							class="a_3">取消</a>
					</s:else> <!-- <a href="javascript:void(0)" class="a_3">查看</a> --></td>
				</tr>
			</s:if>
			<s:else>
				<tr bgcolor="#FFFFFF" style="color: gray; text-decoration: none;">
	
					<td><s:if test="bookSite_clashId != null">
						<img class="gt_img"
							src="${prc }/function/function-booksite/images/gif_03.gif"
							align="absmiddle" />
					</s:if> <s:property value="site.site_name" />&nbsp;(<font color="red">已确认</font>)
					</td>
					<td><s:property value="activityLevel.activityLevel_name" /></td>
					<td><s:date name="bookSite_beginTime" format="yyyy-MM-dd HH:mm" />
					到 <s:date name="bookSite_endTime" format="yyyy-MM-dd HH:mm" /></td>
					<td><s:property value="userName" /></td>
	
					<td><s:if test="bookSite_statusId == 2">
						<a
							href="${prc }/bookSite/searchBookManage.action?bookSite_id=<s:property value="bookSite_id"/>"
							style="color: #ccc;" class="a_3">查看</a>
							<%-- 
						<a href="javascript:cancelBook('${bookSite_id}');" class="a_3">取消</a>
						--%>
					</s:if> <s:else>
						<!-- <a style="color:gray; text-decoration:none;"  href="#" class="a_3">查看</a> -->
						<a
							href="${prc }/bookSite/searchBookManage.action?bookSite_id=<s:property value="bookSite_id"/>"
							class="a_3">查看</a>
						<a href="javascript:cancelBook('${bookSite_id}');" class="a_3">取消</a>
					</s:else> <!-- <a href="javascript:void(0)" class="a_3">查看</a> --></td>
				</tr>
			</s:else>
		</s:iterator>
	</table>
	</div>
	<div class="c"></div>
	
	<%--分页 --%>
	<div class="page_nav" id="pagingBars"><c:if
		test="${pm.total > 10}">
		<pg:pager url="searchBookSiteBS.action" items="${pm.total}"
			maxPageItems="10" export="currentPageNumber=pageNumber">
			<pg:param name="state" value="${state}" />
			<pg:param name="beginTime" value="${beginTime}" />
			<pg:param name="endTime" value="${endTime}" />
			<pg:param name="valueId" value="${valueId}" />
			<pg:param name="typeId" value="${typeId}" />
			<pg:param name="siteId" value="${siteId}" />
			<pg:param name="opId" value="${opId}" />
			<pg:prev>
				<span class="page_nav_prev"><a href="${pageUrl}">上一页</a></span>
			</pg:prev>
			<ul>
				<pg:pages>
					<c:choose>
						<c:when test="${currentPageNumber eq pageNumber}">
							<li class="page_nav_current">${pageNumber}</li>
						</c:when>
						<c:otherwise>
							<li><a href="${pageUrl}" class="first">${pageNumber}</a></li>
						</c:otherwise>
					</c:choose>
				</pg:pages>
			</ul>
			<pg:next>
				<span class="page_nav_next"><a href="${pageUrl}">下一页</a></span>
			</pg:next>
		</pg:pager>
	</c:if> <%--分页 --%></div>
	</div>
	</div>
</div>

</body>
</html>