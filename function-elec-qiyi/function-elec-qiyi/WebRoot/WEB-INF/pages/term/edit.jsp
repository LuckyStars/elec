<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>课程设置_编辑学期</title>
	<link href="${med}/css/main.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${med}/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${med}/js/datePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${med}/js/term.js"></script>
</head>
<body>
	<div class="main" style="float: left;">
		<h1 class="tit">
	    <span class="title"><fmt:message key="i18nTermTitle" bundle="${bundler}" /></span>
	  	</h1>
		<div class="sub_title">
			<h2>编辑选课</h2>
		</div>
		<form action="${prc}/elec/updateTerm.action" method="post" onsubmit="return checkForm()">
			<input type="hidden" value="${term.id}" name="id" />
			
			<%@ include file="/WEB-INF/pages/term/form.jsp" %>
		</form>
	</div>
</body>
</html>
