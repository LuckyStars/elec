<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../common/common.jsp"%>
<title>选课系统</title>
<link href="${med}/css/teacherIndex.css" rel="stylesheet" type="text/css" />
<link href="${med}/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${med }/js/common/agent.js" ></script>
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
		        	<li>
		            	<a href="${prc}/elec/indexSigAction.action?clubState=0" hidefocus="true">
		                <img src="${med}/images/interest3.gif" width="90" title="兴趣班报名"/>                   
		                <b>兴趣班报名</b>
		                </a>
		            </li>
		        	<li>
		            	<a href="${prc}/elec/indexSigAction.action?clubState=1" hidefocus="true">
		                <img src="${med}/images/club1.gif" width="90" title="俱乐部报名"/>                   
		                <b>俱乐部报名</b>
		                </a>
		            </li>
	            </ul>
	        </div>
	    </div>
	</div>
</body>
</html>
