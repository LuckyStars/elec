<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/function/function-booksite/pages/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>管理首页</title>
<link href="${prc }/function/function-booksite/styles/basic.css" rel="stylesheet" type="text/css" />
<script src="${prc }/common/agent.js" type="text/javascript"></script>
</head>
<body>
<div class="box">
	<div class="box_head">
    	<div class="left">
        <b><a href="${prc }/bookSite/indexUser.action">场馆预订</a></b>
        <font size="2" color="#336699">&raquo</font><a href="${prc }/bookSite/adminUser.action">管理员管理</a>
        </div>
        <div class="right">
        <font size="2" color="#336699">&laquo;</font><a href="${prc }/bookSite/indexUser.action" hidefocus="true">返回上一页</a></div>
    </div>
    <div class="box_main">
        <div class="box_main_center">
        	<ul class="ul_type3">
            	<li>
                	<a href="${prc }/bookSite/retrieveAllBackSite.action" hidefocus="true">
                    <img src="${prc }/function/function-booksite/images/gl_1.png" title="场馆管理"/>                  
                    <b>场馆管理</b>
                    </a>
                </li>
                <!--<li>
                	<a href="../bookSite/retrieveAllBackUser.action" hidefocus="true">
                    <img src="../function/function-booksite/images/gl_2.png" title="人员管理"/>                   
                    <b>人员管理</b>
                    </a>
                </li>
                -->
                <li>
                	<a href="${prc }/bookSite/listUser.action" hidefocus="true">
                    <img src="${prc }/function/function-booksite/images/gl_2.png" title="人员管理"/>                   
                    <b>人员管理</b>
                    </a>
                </li>
                <li>
                	<a href="${prc }/bookSite/searchBookManage.action" hidefocus="true">
                    <img src="${prc }/function/function-booksite/images/gl_3.png" title="预订管理"/>                   
                    <b>预订管理</b>
                    </a>
                </li>
                <li>
                	<a href="${prc }/bookSite/retrieveAllBackActivityLevel.action" hidefocus="true">
                    <img src="${prc }/function/function-booksite/images/gl_4.png" title="活动级别管理"/>                   
                    <b>活动级别管理</b>
                    </a>
                </li>
                <li>
                	<a href="${prc }/bookSite/retrieveAllBackActivityType.action" hidefocus="true">
                    <img src="${prc }/function/function-booksite/images/gl_5.png" title="活动类型管理"/>                  
                    <b>活动类型管理</b>
                    </a>
                </li>
               <%-- <li>
                	<a href="${prc }/bookSite/retrieveSendMessageUser.action" hidefocus="true">
                    <img src="${prc }/function/function-booksite/images/gl_6.png" title="发送信息配置"/>                  
                    <b>发送信息配置</b>
                    </a>
                </li>
                <li>
                	<a href="../bookSite/retrieveJfreeChartBookSite.action?statisticsId=1" hidefocus="true">
                    <img src="../function/function-booksite/images/gl_7.png" title="预订信息统计"/>                  
                    <b>预订信息统计</b>
                    </a>
                </li>
            --%>
            	<li>
                	<a href="${prc }/bookSite/showStatistics.action?type=1" hidefocus="true">
                    <img src="${prc }/function/function-booksite/images/gl_7.png" title="预订信息统计"/>                  
                    <b>预订信息统计</b>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
