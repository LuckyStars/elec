<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../common/common.jsp"%>
<title>学生报名</title>
<link href="${med}/css/xqstyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${med}/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${med }/js/common/agent.js" ></script>
<script type="text/javascript" src="${med }/js/ecesgin/ecesgin.js" ></script>
<script type="text/javascript">
var typeId='${ecCourseVO.typeId }';
var weekIndex='${ecCourseVO.weekIndex }';
var searchName="${ecCourseVO.name}"+"&flag=1&clubState=${clubState}";
var messages="${message}";
</script>
<script type="text/javascript" src="${med}/js/ecesgin/ecesgin.js"></script>
</head>
<body onload="message()" >
<!--弹出层-->
<div id="shade1" class="shade" style="width: 100%; height: 931px;display:none"  >
 <div class="photo_bg fixed" >
        <h1><span>报名选择</span><a onclick="closeDiv('shade1')" href="javascript:"></a></h1>
        <div class="list">
            <p id="ptextVal" class="ptext" style="padding:20px;font-size:14px;line-height:22px;" >
            </p>
        </div>
        <div class="clear"></div>
        <div class="layer_btn"><a id="delSign"  href="javascript:">退课?</a><a id="clsDate" href="javascript:" >选择课时</a></div>
    </div>
</div>
<!--弹出层-->
<!--弹出层-->
<div id="shade2" class="shade" style="width: 100%; height: 931px;display:none"  >
 <div class="photo_bg fixed"  >
        <h1><span>选择课时</span><a onclick="closeDiv('shade2')" href="javascript:"></a></h1>
        <div class="list">
            <p id="ptextVal1" class="ptext" style="padding:20px;font-size:14px;line-height:22px;" >
            </p>
        </div>
        <div class="clear"></div>
        <div class="layer_btn"><a id="addClassDate" href="javascript:">确定</a><a id="delClsDate" href="javascript:" >退课时</a></div>
    </div>
</div>
<!--弹出层-->
<div class="con_conent fixed">
    <h1 class="tit"><span class="title"><fmt:message key="i18nTermTitle" bundle="${bundler}" /></span><a class="fr" href="${prc}/elec/indexSigAction.action?clubState=${clubState}">返回</a></h1>
    <div class="title_box fixed">
        <div class="title">${term.name }</div>
        <p>报名时间：<fmt:formatDate value="${term.signDateStart}" pattern="yyyy年MM月dd日 HH:mm" />
         - <fmt:formatDate value="${term.signDateEnd}" pattern="yyyy年MM月dd日 HH:mm" />
         </p>
        <p class="red">选课提示：${term.comments }</p>
    </div>
    <p class="linkclass"><label>课程类别：</label>
	    <a <c:if test="${fn:length(ecCourseVO.typeId)==0}">class="clcult"</c:if> 
	    href="${prc}/elec/ecsignSigAction.action?ecCourseVO.typeId=&ecCourseVO.weekIndex=${ecCourseVO.weekIndex }&ecCourseVO.name=${ecCourseVO.name}&clubState=${clubState}">不限</a>
	    <c:forEach var="type" items="${ecTypes}" >
		    <a href="${prc}/elec/ecsignSigAction.action?ecCourseVO.typeId=${type.id }&ecCourseVO.weekIndex=${ecCourseVO.weekIndex }&ecCourseVO.name=${ecCourseVO.name}&clubState=${clubState}" 
		    <c:if test="${ecCourseVO.typeId==type.id }">class="clcult"</c:if> id="${type.id }" >${type.name }</a>
	    </c:forEach>
    </p>
    <div class="clear"></div>
    <p class="linkclass">
    <label>上课时间：</label>
    	<a <c:if test="${empty ecCourseVO.weekIndex}">class="clcult"</c:if> 
    	href="${prc}/elec/ecsignSigAction.action?ecCourseVO.weekIndex=&ecCourseVO.typeId=${ecCourseVO.typeId }&ecCourseVO.name=${ecCourseVO.name}&clubState=${clubState}">不限</a>
    	<c:forEach var="week" items="${weekMap}" >
			<a href="${prc}/elec/ecsignSigAction.action?ecCourseVO.typeId=${ecCourseVO.typeId }&ecCourseVO.weekIndex=${week.key }&ecCourseVO.name=${ecCourseVO.name}&clubState=${clubState}" 
			id="${week.key }" <c:if test="${ecCourseVO.weekIndex==week.key }">class="clcult"</c:if> >${week.value }</a>
		</c:forEach>
	</p>
	<form id="sreach" action="${prc}/elec/ecsignSigAction.action?ecCourseVO.typeId=${ecCourseVO.typeId }&ecCourseVO.weekIndex=${week.key }&clubState=${clubState}" method="post">
	    <p class="sc_link">
	    	<input name="ecCourseVO.name" type="text" value="${ecCourseVO.name }" />
	    	<a class="linkcult" onclick="sreachSubmit();" href="javascript:void(0);" >搜课程</a>
	    </p> 
    </form>
    <h2><a class="c_btn" href="${prc}/elec/ecsignMySigAction.action?clubState=${clubState}">查看${uEcUser.name }已选课程</a><span class="fl"><c:if test="${term.maxCourse!=0}">&nbsp; &nbsp;共可以报<b> &nbsp;${term.maxCourse}&nbsp; </b>门课</c:if>&nbsp;&nbsp;您已经报名了<b>&nbsp;${courseCount }&nbsp;</b> 门课程!</span>
    </h2>
    <div class="clear"></div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="change_tab">
        <tr>
            <th width="12%" scope="col">课程名称</th>
            <th width="4%" scope="col">类别 </th>
            <th width="4%" scope="col">老师 </th>
            <th width="10%" scope="col">开结课日期</th>
            <th width="14%" scope="col">上课时间</th>
            <th width="14%" scope="col">报名时间</th>
            <th width="8%" scope="col">年级范围 </th>
            <th width="6%" scope="col">报课节数</th>
            <th width="14%" scope="col">报名</th>
        </tr>
        <c:forEach var="course"  items="${ecSignCourseVos}" >
	        <tr>
	            <td id="name${course.ecCourseVO.id }">
		            <a href="${prc}/elec/ecsignDetailsSigAction.action?ecCourseVO.id=${course.ecCourseVO.id}&locationGo=1&ecCourseVO.typeId=${ecCourseVO.typeId }&ecCourseVO.weekIndex=${ecCourseVO.weekIndex }&ecCourseVO.name=${ecCourseVO.name}&clubState=${clubState}">
			            <div style="cursor:pointer;width:45px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${course.ecCourseVO.name }">
			        	${course.ecCourseVO.name }
			        	</div>
			        </a>
			        <c:if test="${course.ecCourseVO.maxStudentNum==course.ecCourseVO.currentStudentNum}">
			        	<span class="warn"></span>
			        </c:if>
	            </td>
	            <td>${course.ecCourseVO.typeName }</td>
	            <td>
		            <div style="width:60px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${course.ecCourseVO.teacherNames }">
		        	${course.ecCourseVO.teacherNames }
		        	</div>
	            </td>
	            <td>
		            <p>
		            <fmt:formatDate value="${course.ecCourseVO.classStartDate}" pattern="yyyy-MM-dd" />
		            </p>
	            	<p>
	            	<fmt:formatDate value="${course.ecCourseVO.classEndDate}" pattern="yyyy-MM-dd" />
	            	</p>
	            </td>
	            <td><c:if test="${!empty course.ecCourseVO.classhourList && (fn:length(course.ecCourseVO.classhourList)>0)}">
				    <c:forEach items="${course.ecCourseVO.classhourList}" var="ch" varStatus="chStats" >
				   	${ch.weekIndexStr } 
				   	<fmt:formatDate value="${ch.weekStartTime}" pattern="HH:mm" />-
				   	<fmt:formatDate value="${ch.weekEndTime}" pattern="HH:mm" />
					<c:if test="${!chStats.last}"><br/></c:if>
				    </c:forEach>
				    </c:if>
			    </td>
			     <td>
			     	 <p>
		            <fmt:formatDate value="${course.ecCourseVO.signStartDate}" pattern="yyyy-MM-dd HH:mm" />
		            </p>
	            	<p>
	            	<fmt:formatDate value="${course.ecCourseVO.signEndDate}" pattern="yyyy-MM-dd HH:mm" />
	            	</p>
			     </td>
	            <td>${course.ecCourseVO.gradeNames }</td>
	            <td>${course.ecCourseVO.classhourRequire}</td>
	            <td>
	             	<c:if test="${course.ecCourseVO.maxStudentNum<=course.ecCourseVO.currentStudentNum 
	             	&& course.signStarState && !course.signUpState && (term.maxCourse>courseCount|| term.maxCourse==0)}">
			        	报满了!
			        </c:if>
			        <c:if test="${term.maxCourse<=courseCount && !course.signUpState && term.maxCourse!=0 }">
			        	你的课程已报满!
			        </c:if>
			        <c:if test="${course.ecCourseVO.maxStudentNum>course.ecCourseVO.currentStudentNum 
			        &&  course.signStarState && !course.signUpState && (term.maxCourse>courseCount||term.maxCourse==0) }">
			        	<a class="td_b" onclick="addSign('${course.ecCourseVO.id}','${course.ecCourseVO.name }')" href="javascript:">我要报名?/选课</a>
			        </c:if>
			        <c:if test="${!course.signStarState && !course.signUpState && (term.maxCourse>courseCount||term.maxCourse==0) }">
			        	报名没有开启!
			        </c:if>
			         <c:if test="${course.signUpState  &&ecsignType==0}">
			        	<a class="td_b" style="color:red;"  onclick="delSign('${course.ecSign.id }','${course.ecCourseVO.name }')"  href="javascript:">报错了/退课</a>
			        </c:if>
			        <c:if test="${course.signUpState  && ecsignType==1 }"> 
			        	<a class="td_b" style="color:red;" 
			        	onclick="delSignShiJia('${course.ecCourseVO.id }',${course.ecCourseVO.classhourRequire}
			        	,'${course.ecCourseVO.name }','${course.ecSign.id }','${course.ecSign.classhourIds }')"
			        	 href="javascript:" >退课?/报课时</a>
			        </c:if>
	            </td>
	        </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
