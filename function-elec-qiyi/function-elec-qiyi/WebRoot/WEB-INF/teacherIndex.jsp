<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选课系统</title>
<link href="${med}/css/teacherIndex.css" rel="stylesheet" type="text/css" />
<link href="${med}/css/main.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="box">
		<h1 class="tit">
	    <span class="title"><fmt:message key="i18nTermTitle" bundle="${bundler}" /></span>
	  	</h1>
	  	<div class="cl"></div>
	    <div class="box_main">
	    	<div class="box_main_center">
	        	<ul class="ul_type3">
	        		<c:if test="${curUser.admin || curUser.manager}">
		        	<li>
		            	<a href="${prc }/elec/listTerm.action" hidefocus="true">
		                <img src="${med}/images/gl_113.PNG" width="90" title="<fmt:message key="i18nTermName" bundle="${bundler}" />管理"/>                   
		                <b><fmt:message key="i18nTermName" bundle="${bundler}" />管理</b>
		                </a>
		            </li>
		            </c:if>
					<c:if test="${curUser.admin || curUser.manager}">
		        	<li>
		            	<a href="${prc }/elec/findAllplaceAction.action" hidefocus="true">
		                <img src="${med}/images/gl_7.png" width="90" title="地点管理"/>                   
		                <b>地点管理</b>
		                </a>
		            </li>
		            </c:if>
		            <%-- 管理员、教务老师、任课老师（可录入）、任课教师自己 --%>
   					<c:if test="${curUser.admin || curUser.manager || curUser.courseTeacher || curUser.courseTeacherViewable }">
		        	<li>
		            	<a href="${prc }/elec/listEcCourse.action" hidefocus="true">
		                <img src="${med}/images/gl_13.png" width="90" title="课程管理"/>                   
		                <b>课程管理</b>
		                </a>
		            </li>
		            </c:if>
		            
		     	 	<c:if test="${curUser.classMaster}"> 
			        <li>
		            	<a href="${prc}/elec/findStuByHeaderMasterheaderMaster.action" hidefocus="true">
		                <img src="${med}/images/bj_14.GIF" width="90" title="班主任管理"/>                   
		                <b>班主任管理</b>
		                </a>
			        </li>
		          	</c:if>
		          	<c:if test="${curUser.admin}">
		            <li>
		            	<a href="${prc}/elec/listPgPgAction.action" hidefocus="true">
		                <img src="${med}/images/bj_02.GIF" width="90" title="权限管理"/>                   
		                <b>权限管理</b>
		                </a>
		            </li>
		            </c:if>
	            </ul>
	        </div>
	    </div>
	</div>
</body>
</html>
