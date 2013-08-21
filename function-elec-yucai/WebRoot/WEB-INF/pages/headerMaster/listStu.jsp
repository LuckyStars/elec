<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课程列表</title>
<link  type="text/css" href="${med}/css/place/xqstyle.css" rel="stylesheet" />
<script type="text/javascript"  src="${med}/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript"  src="${med}/js/headerMaster/headerMaster.js"></script>
<script type="text/javascript" src="${med }/js/common/agent.js" ></script>
<style type="text/css">
</style>
<script type="text/javascript">
$(function(){
    $("#searchMain").click(function(){
             var mainName = $.trim($("#mainName").val());
             if(mainName == "" | mainName =="请输入课程名称"){
            	 window.location.href ='${prc}/elec/findStuByHeaderMasterheaderMaster.action';
            	 
                 }else if(mainName != null){
		             var postForm = document.createElement("form");//表单对象   
		             postForm.method="post" ;   
		             postForm.action = '${prc}/elec/searchStuByHeaderMasterheaderMaster.action?flag=search';
		              
		             var nameInput = document.createElement("input") ;  //name input 
		             nameInput.setAttribute("name", "ecCourseVO.name") ;   
		             nameInput.setAttribute("value", mainName);  
		             postForm.appendChild(nameInput) ;
		             document.body.appendChild(postForm) ;   
		             postForm.submit() ;   
		             document.body.removeChild(postForm) ; 
             }
    });                        
 });
</script>
</head>
<body>
<div class="con_conent fixed">
<h1 class="tit"><span class="title"><fmt:message key="i18nTermTitle" bundle="${bundler}" /></span><a class="fr" href="${prc}/index.jsp">返回</a></h1>
    <div class="nextmenu" style="height: 25px;">
	    <p style="line-height: 30px">
	    	
	    	<a class="current" href="${prc}/elec/findStuByHeaderMasterheaderMaster.action">学生选课信息</a>
	    	<span style="margin-top: 9px;"></span>
	    	<a href="${prc}/elec/findAllMainheaderMaster.action">课程信息</a>
	    </p>
    </div>
    <div class="title_box fixed">
    	<c:choose>
	    	<c:when test="${!empty apply}">
	    		<div class="title" style="font-size: 20px;font-weight: bold;height: 36px;line-height: 25px;text-align: center;">本班选择 <c:out value="${ecCourse.name}" escapeXml="false" /> 课程的学生列表</div>
	    	</c:when>
	        <c:otherwise>
	        	<div class="title" style="font-size: 20px;font-weight: bold;height: 36px;line-height: 25px;text-align: center;"><fmt:message key="i18nTermName" bundle="${bundler}" />:<c:out value="${ecTerm.name}" escapeXml="false" /></div>
	   		</c:otherwise>
   		</c:choose>
    </div>
    <h2>
        <span class="spanleft_list">
        	<c:choose>
        		<c:when test="${!empty apply}">
        			<a  style="margin-right: 20px;"  class="c_btn" href="${prc}/elec/findAllClassheaderMaster.action">返回</a>
        		</c:when>
        		<c:otherwise>
	        		<input type="text" name="ecCourseVO.name" id="mainName" value="${ecCourseVO.name}" />
				<script type="text/javascript">
	        	var s = $("#mainName").val();
	        	if(s == ""){
					$("#mainName").val("请输入课程名称");
	        	}
	        	$("#mainName").focus(function(){

	        		if($(this).val() == "请输入课程名称"){
						$(this).val('');
		        	}
		        });
	        	</script>
	        		<a class="s_btn" href="#" id="searchMain"></a>
	        		<a class="c_btn" href="${prc}/elec/exportByStuheaderMaster.action">导出</a>
        		</c:otherwise>
        	</c:choose>      	
        </span> 
    </h2>
    <div class="clear"></div>
    <table width="100%"border="0" cellpadding="0" cellspacing="0" class="change_tab">
        <tr>
            <th width="15%" scope="col">学号</th>
            <th width="20%" scope="col">姓名</th>
            <th width="10%" scope="col">性别</th>
            <th width="30%" scope="col">家长电话 </th>
            <c:if test="${empty apply}">
          	  <th width="25%" scope="col">报名课程</th>
            </c:if>
        </tr>
     	<c:if test="${empty search}">
			 <jsp:include page="searchShow.jsp"></jsp:include> 
     	</c:if>
     	<c:if test="${not empty search}">
			 <jsp:include page="searchShow.jsp"></jsp:include>
     	</c:if>
    </table>
<div class="clear"></div>
</div>
</body>
</html>