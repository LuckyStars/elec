<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课程设置-<fmt:message key="i18nTermName" bundle="${bundler}" />信息列表</title>
<link href="${med}/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
</script>
<style type="text/css">
a:HOVER {
	text-decoration: none;
}
</style>
</head>
<body>
	<div class="main" style="float: left;">
		<h1 class="tit">
	    <span class="title"><fmt:message key="i18nTermTitle" bundle="${bundler}" /></span>
	    <a class=".fr" href="${prc}/elec/index.action" style="float:right;padding-right:10px;font-weight: normal;" >返回</a>
	  	</h1>
	  	<div class="cl"></div>
	  	<div class="title_h4">
	    	<h4><fmt:message key="i18nTermName" bundle="${bundler}" />信息列表</h4>
	  	</div>
	  	<div class="list_title">
	    	<div class="small_button">
	      	<ul>
	        	<li><img src="${med}/images/add_arrow.gif" />
	        	<a  style="color: white;font-size: 12px;font-weight: 100;" 
	        	href="${prc }/elec/gotoAddTerm.action">新的<fmt:message key="i18nTermName" bundle="${bundler}" /></a></li>
	      	</ul>
	    	</div>
	  	</div>
	  	<table  border="0" cellspacing="0" cellpadding="0" class="table_border">
	    	<tr class="table_title">
	      		<td align="left" class="first">学期名称</td>
	      		<td>开课日期</td>
	      		<td>报名日期</td>
	      		<td width="100">最大选课数</td>
	      		<td width="100">操作</td>
	    	</tr>
	    	<c:if test="${not empty termList}">
	    		<c:forEach items="${termList}" var="term" varStatus="i">
	    			<tr 
	    			${(i.index+1)%2==0 ? 'class="second"':'' }
	    			>
			      		<td align="left" class="first">
			      	 		<c:if test="${term.currentTerm}">
			      	 			<div class="table_selected">
				      				<img src="${med}/images/table_selected.gif" />
				      			</div>
			      	 		</c:if>
				     		<div style="width:140px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
				     			<b><c:out value="${term.name }" escapeXml="true"></c:out></b>
				     		</div>
			      		</td>
				      	<td>
				      		<fmt:formatDate value="${term.lessonDateStart}" pattern="yyyy年MM月dd日"/>
				      		-
				      		<fmt:formatDate value="${term.lessonDateEnd}" pattern="yyyy年MM月dd日"/>
				      	</td>
				      	<td>
				      		<fmt:formatDate value="${term.signDateStart}" pattern="yyyy年MM月dd日"/>
				      		-
				      		<fmt:formatDate value="${term.signDateEnd}" pattern="yyyy年MM月dd日"/>	
					  	</td>
				      	<td>
				      		<c:choose>
				      			<c:when test="${term.maxCourse>0}">
				      				${term.maxCourse}课程/人
				      			</c:when>
				      			<c:otherwise>
				      				不限
				      			</c:otherwise>
				      		</c:choose>
				      	</td>
			      		<td>
			      			<c:if test="${term.currentTerm}">
			      				<a href="${prc }/elec/editTerm.action?id=${term.id}">编辑</a>
			      			</c:if>
			      			<a href="${prc }/elec/viewTerm.action?id=${term.id}">查看</a>
			      		</td>
			    	</tr>
	    		</c:forEach>
	    	</c:if>
	  	</table>
	  	<div class="cl"></div>
	</div>
</body>
</html>
