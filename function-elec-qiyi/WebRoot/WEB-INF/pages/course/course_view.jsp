<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../common/common.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课程查看</title>
<link href="${med }/css/place/xqstyle.css" rel="stylesheet" type="text/css" />
<link href="${med }/css/reset.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${med }/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${med }/js/common/agent.js" ></script>
<script type="text/javascript"> 
$(function(){
	$("#showClose").toggle(
		function () {
		  $("#detailContent").slideDown(function(){
			  sethash();
		  });
		  $(this).html("收缩课程内容详情↑");
		  
		},
		function () {
		  $("#detailContent").slideUp();
		  $(this).html("展开课程内容详情↓");
		}
	);
});
</script>
</head>
<body style="background:#F0F8FC;">
<div class="con_conent fixed" >
    <h1 class="tit">
    	<span class="title"><fmt:message key="i18nTermTitle" bundle="${bundler}" /></span>
    	<div class="return fr">
    		<%-- 
    		<a href="javascript:void(0);" onclick="javascript:history.back();">返回</a>
    		--%>
			<c:if test="${flag==1}">
		    <a href="listEcCourse.action?ecCond.termId=${ec.termId }">返回</a> 
		    </c:if>
		    <c:if test="${flag==2}">
		    <a href="${prc}/elec/findAllClassheaderMaster.action">返回</a> 
		    </c:if>
		    <c:if test="${flag==3}">
		    <a href="${prc}/elec/findAllMainheaderMaster.action">返回</a> 
		    </c:if>
		    <c:if test="${flag==4}">
		    <a href="${prc}/elec/findStuByHeaderMasterheaderMaster.action">返回</a> 
		    </c:if>    		
    	</div>
    </h1>
    <h1><span>课程详细</span></h1>
    <div class="text_box">
    	<c:if test="${!empty ec.courseComment }">
        	<p class="red" style="width:720px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${ec.courseComment}">
        	选课提示：${ec.courseComment }
        	</p>
        </c:if>
        <p>
            <label><fmt:message key="i18nTermName" bundle="${bundler}" />：</label>${ec.ecTerm.name }
		</p>
        <p style="width:720px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${ec.name }">
            <label>课程名称：</label>${ec.name }
		</p>
        <p>
            <label>课程分类：</label>${ec.typeName }
		</p>
        <p>
            <label><%--年级范围--%><fmt:message key="i18nGradeNames" bundle="${bundler}" />：</label>${ec.gradeNames }
		</p>
        <%-- 管理员、教务老师、任课老师（可录入）、任课教师自己 --%>
        <c:if test="${curUser.admin || curUser.manager || curUser.courseTeacher || fn:contains(ec.teacherIds, curUser.uid)}">
        <p>
            <label>已选人数：</label>${ec.currentStudentNum }人
        </p>
        </c:if>
        <p>
            <label>限定人数：</label>${ec.maxStudentNum }人
        </p>
        <p style="width:720px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${ec.teacherNames }">
            <label>任课老师：</label>${ec.teacherNames }
		</p>
		<c:if test="${signType=='shijia'}">
        <p>
            <label>是否俱乐部课程：</label>${ec.clubStateStr }
		</p>
		</c:if>
		<c:if test="${signType=='yucai'}">
        <p>
            <label>课程属性：</label>${ec.courseAttrStr }
		</p>
		</c:if>
        <p>
            <label>报名日期：</label><fmt:formatDate value="${ec.signStartDate}" pattern="yyyy-MM-dd HH:mm:ss" /> 至  <fmt:formatDate value="${ec.signEndDate}" pattern="yyyy-MM-dd HH:mm:ss" />
		</p>
        <p>
            <label>开课日期：</label><fmt:formatDate value="${ec.classStartDate}" pattern="yyyy-MM-dd" /> 至  <fmt:formatDate value="${ec.classEndDate}" pattern="yyyy-MM-dd" />
		</p>
		<c:if test="${signType=='shijia'}">
		<p>
            <label>要求课时：</label>${ec.classhourRequire } 课时/周
        </p>
        </c:if>
		<%-- 
        <p>
            <label>--%><%--可选课时--%><%-- <fmt:message key="i18nClasshourNum" bundle="${bundler}" />：</label>
            <c:if test="${signType=='yucai'}">
            ${ec.classhourNum } 课时/周
            </c:if>
		</p>
		
		<div class="ptext fixed" style="padding:5px 0 5px 60px; width:672px; line-height:25px;">
		    <c:forEach items="${ec.classhourList}" var="ch" varStatus="chStats" >
		   	${ch.weekIndexStr } 
		   	从
		   	<fmt:formatDate value="${ch.weekStartTime}" pattern="HH:mm:ss" />
			至
			<fmt:formatDate value="${ch.weekEndTime}" pattern="HH:mm:ss" />
			<c:if test="${!chStats.last}"><br/></c:if>
		    </c:forEach>
        </div>
        --%>
        <p>
            <label>上课地点：</label>${ec.startPlace.name }
		</p>
		<%--
        <p>
            <label>放学地点：</label>${empty ec.endPlace.name ? '无' : ec.endPlace.name}
		</p>
		--%>
        <p style="">
            <a href="#" id="showClose" style="color:#275A8D;">展开课程内容详情↓</a>
		</p>
        <div class="ptext fixed" style="display: none;" id="detailContent">
            <p>
                <label>课程内容：</label>
                <div class="notContent">${ec.courseContent}</div>
			</p>
            <p>
                <label>学生要求：</label>
                <div class="notContent">${ec.courseRequire}</div>
			</p>
            <p>
                <label>备　　注：</label>
                <div class="notContent">${ec.courseNote}</div>
			</p>
        </div>
       
    </div>
   	<div class="btn" style="margin-left: 20px;">
   		<%-- 管理员、教务老师、任课老师（可录入）、任课教师自己 --%>
   		<c:if test="${curUser.admin || curUser.manager || curUser.courseTeacher || fn:contains(ec.teacherIds, curUser.uid)}">
   		<a class="view_btn" href="${prc}/elec/listStudentInfo.action?course.id=${ec.id }&flag=${flag}&flagx=2">查看选课学生</a>
   		</c:if>
   		<%-- 管理员、教务老师、任课老师（可录入）、任课教师自己 --%>
   		<c:if test="${ec.edit && (curUser.admin || curUser.manager || curUser.courseTeacher || fn:contains(ec.teacherIds, curUser.uid) )}">
   		<a href="addUIEcCourse.action?id=${ec.id}&flag=edit">编辑</a>
	    </c:if>
	    <c:if test="${flag==1}">
	    <a href="listEcCourse.action?ecCond.termId=${ec.termId }">返回</a> 
	    </c:if>
	    <c:if test="${flag==2}">
	    <a href="${prc}/elec/findAllClassheaderMaster.action">返回</a> 
	    </c:if>
	    <c:if test="${flag==3}">
	    <a href="${prc}/elec/findAllMainheaderMaster.action">返回</a> 
	    </c:if>
	    <c:if test="${flag==4}">
	    <a href="${prc}/elec/findStuByHeaderMasterheaderMaster.action">返回</a> 
	    </c:if>
	    <%-- <a href="javascript:void(0);" onclick="javascript:history.back();">返回</a>  --%>
   	</div>
</div>
</body>
</html>