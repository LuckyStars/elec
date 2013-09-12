<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课程设置_选课详细</title>
<link href="${med}/css/main.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="main" style="float: left;">
	<h1 class="tit">
	    <span class="title"><fmt:message key="i18nTermTitle" bundle="${bundler}" /></span>
	  	</h1>
	<div class="sub_title">
		<h2>查看</h2>
	</div>
	<div class="edit_course">
		<ul>
			<li>
				<span style="font-weight: bold;">选课学期名称:</span>
				<c:out value="${term.name}"escapeXml="true"></c:out>
			</li>
			<li>
				<span style="font-weight: bold;">选课开放日期:</span>
				<fmt:formatDate value="${term.openDateStart}" pattern="yyyy年MM月dd日" />
				至
				<fmt:formatDate value="${term.openDateEnd}" pattern="yyyy年MM月dd日" />
			</li>
			<li>
				<span style="font-weight: bold;">选课报名时间:</span>
				<fmt:formatDate value="${term.signDateStart}" pattern="yyyy年MM月dd日"/>
				至
				<fmt:formatDate value="${term.signDateEnd}" pattern="yyyy年MM月dd日"/>
			</li>
			<li>
				<span style="font-weight: bold;">最大选课数:</span>
				<c:choose>
	      			<c:when test="${term.maxCourse>0}">
	      				${term.maxCourse}课程/人
	      			</c:when>
	      			<c:otherwise>
	      				不限
	      			</c:otherwise>
	      		</c:choose>
			</li>
			<%--<li>
				<span><b>默认开课日期:</b></span>
				<fmt:formatDate value="${term.lessonDateStart}" pattern="yyyy年MM月dd日" />
			</li>
			<li>
				<span><b>默认结课日期:</b></span>
				<fmt:formatDate value="${term.lessonDateEnd}" pattern="yyyy年MM月dd日" />
			</li>--%>
			<li>
			<span style="font-weight: bold;">备注说明:</span>
				<div style="width:600px;overflow:hidden;word-warp:break-word;word-break:break-all;">
				<c:out value="${term.comments}" escapeXml="true"></c:out>
				</div>
			</li>
		</ul>
	</div>
	<div class="cl"></div>
	<div class="button_border" id="one">
		<ul>
			<li>
			<input class="button"
				onclick="location.href='${prc}/elec/listTerm.action'" type="button"
				value="返回" title="返回" />
			</li>
		</ul>
	</div>
</div>
</body>
</html>
