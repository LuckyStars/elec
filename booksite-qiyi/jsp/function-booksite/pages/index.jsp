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
        </div>
    </div>
    <div class="box_main">
        <div class="box_main_center">
        	<ul class="ul_type3">
        	   <c:if test='${fn:indexOf(ROLEID,"2") != -1 }'>
        	      <li>
                	<a href="${prc }/bookSite/retrieveOpenSite.action" hidefocus="true">
                    <img src="${prc }/function/function-booksite/images/gl_112.png" title="申请场馆预订"/>                   
                    <b>申请场馆预订</b>
                    </a>
                </li>
            	<li>
                	<a href="${prc }/bookSite/searchBookSite.action" hidefocus="true">
                    <img src="${prc }/function/function-booksite/images/gl_111.png" title="我的预订"/>                  
                    <b>我的预订</b>
                    </a>
                </li>
                 </c:if>
                <!-- 场馆负责人 -->
          <c:if test='${fn:indexOf(FZR,"4") != -1 }'> 
	                <li>
	                	<a href="${prc }/bookSite/listPending.action" hidefocus="true">
	                    <img src="${prc }/function/function-booksite/images/gl_111.png" title="待处理任务"/>                   
	                    <b>待处理任务</b>
	                    </a>
	                </li>
    		 </c:if>
                <!-- 场馆管理员 -->
               <c:if test='${fn:indexOf(ROLEID,"3") != -1 }'>
                <li>
                	<a href="${prc }/bookSite/listAudit.action" hidefocus="true">
                    <img src="${prc }/function/function-booksite/images/gl_111.png" title="我的审批"/>                   
                    <b>我的审批</b>
                    </a>
                </li>
               </c:if> 
               <!-- 系统管理员 -->
             <c:if test='${fn:indexOf(ROLEID,"1")!=-1}'>
             		   <li>
                	<a href="${prc }/bookSite/retrieveOpenSite.action" hidefocus="true">
                    <img src="${prc }/function/function-booksite/images/gl_112.png" title="申请场馆预订"/>                   
                    <b>申请场馆预订</b>
                    </a>
                </li>
            	<li>
                	<a href="${prc }/bookSite/searchBookSite.action" hidefocus="true">
                    <img src="${prc }/function/function-booksite/images/gl_111.png" title="我的预订"/>                  
                    <b>我的预订</b>
                    </a>
                </li>
	                <li>
	                	<a href="${prc }/bookSite/adminUser.action" hidefocus="true">
	                    <img src="${prc }/function/function-booksite/images/gl_113.png" title="管理员管理"/>                   
	                    <b>管理员管理</b>
	                    </a>
	                </li>
             	</c:if>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
