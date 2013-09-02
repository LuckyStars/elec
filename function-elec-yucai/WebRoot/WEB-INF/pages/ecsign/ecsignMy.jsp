<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../common/common.jsp"%>
<title>学生自己课程信息</title>
<link href="${med}/css/xqstyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${med}/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${med}/js/common/agent.js" ></script>
<script type="text/javascript">
var typeId='${ecCourseVO.typeId }';
var weekIndex='${ecCourseVO.weekIndex }';
var searchName="${ecCourseVO.name}"+"&flag=2&clubState=${clubState}";
var messages="${message}";
</script>
<script type="text/javascript" src="${med}/js/ecesgin/ecesgin.js"></script>
</head>
<body onload="message()" >
<!--弹出层 退课/选择课时-->
<div id="shade1" class="shade" style="width: 100%; height: 931px;display:none"  >
 <div class="photo_bg fixed" >
        <h1><span>报名选择</span><a onclick="closeDiv('shade1')" href="javascript:"></a></h1>
        <div class="list">
            <p id="ptextVal" class="ptext" style="padding:20px;font-size:14px;line-height:22px;" >
            </p>
        </div>
        <div class="clear"></div>
        <div class="layer_btn"><a id="delSign" href="javascript:">退课?</a><a id="clsDate" href="javascript:" >选择课时</a></div>
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
    <h1 class="tit"><span class="title">校本选课</span><a class="fr" href="${prc}/elec/indexSigAction.action?clubState=${clubState}">返回</a></h1>
    <div class="title_box fixed">
        <div class="title">${term.name }</div>
        <p>报名时间：<fmt:formatDate value="${term.signDateStart}" pattern="yyyy年MM月dd日" />
         - <fmt:formatDate value="${term.signDateEnd}" pattern="yyyy年MM月dd日" />
         </p>
        <p class="red">
			<span style="margin:0 auto; display:block; width:600px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${term.comments }">
		选课提示：${term.comments }</p>
    </div>
    <h2><a class="c_btn" href="${prc}/elec/ecsignSigAction.action?clubState=${clubState}">查看所有课程</a><span class="fl"><c:if test="${term.maxCourse!=0}">&nbsp; &nbsp;共可以报<b> &nbsp;${term.maxCourse}&nbsp; </b>门课</c:if>&nbsp;&nbsp;您已经报名了<b>&nbsp;${courseCount }&nbsp;</b> 门课程!</span>
    <!--<a class="print_btn" href="#">打印报到凭据</a>
    --></h2>
    <div class="clear"></div>
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="change_tab">
        <tr>
            <th width="10%" scope="col">课程名称</th>
            <th width="6%" scope="col">类别 </th>
            <th width="4%" scope="col">老师 </th>
            <th width="10%" scope="col">开结课日期</th>
			<%--
            <th width="14%" scope="col">上课时间</th>
			--%>
            <th width="8%" scope="col">年级范围 </th>
				<%--
            <c:if test="${ecsignType==1 }">
            <th width="16%" scope="col">你的上课时间</th>
            </c:if>
				--%>
            <th width="14%" scope="col">报名</th>
        </tr>
        <c:forEach var="course"  items="${ecSignCourseVos}" >
	        <tr>
	            <td>
		            <a href="${prc}/elec/ecsignDetailsSigAction.action?ecCourseVO.id=${course.ecCourseVO.id}&locationGo=2&ecCourseVO.typeId=${ecCourseVO.typeId }&ecCourseVO.weekIndex=${ecCourseVO.weekIndex }&ecCourseVO.name=${ecCourseVO.name}&clubState=${clubState}">
			            <div style="cursor:pointer;width:60px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${course.ecCourseVO.name }">
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
				<%--
	            <td><c:if test="${!empty course.ecCourseVO.classhourList && (fn:length(course.ecCourseVO.classhourList)>0)}">
				    <c:forEach items="${course.ecCourseVO.classhourList}" var="ch" varStatus="chStats" >
				   	${ch.weekIndexStr } 
				   	<fmt:formatDate value="${ch.weekStartTime}" pattern="HH:mm" />-
				   	<fmt:formatDate value="${ch.weekEndTime}" pattern="HH:mm" />
					<c:if test="${!chStats.last}"><br/></c:if>
				    </c:forEach>
				    </c:if>
			    </td>
				--%>
	            <td>${course.ecCourseVO.gradeNames }</td>
				<%--
	            <c:if test="${ecsignType==1 }">
	            <td>
	            	<c:if test="${!empty course.ecClasshours && (fn:length(course.ecClasshours)>0)}">
				    <c:forEach items="${course.ecClasshours}" var="ch" varStatus="chStats" >
				   	${ch.weekIndexStr } 
				   	<fmt:formatDate value="${ch.weekStartTime}" pattern="HH:mm" />-
				   	<fmt:formatDate value="${ch.weekEndTime}" pattern="HH:mm" />
					<c:if test="${!chStats.last}"><br/></c:if>
				    </c:forEach>
				    </c:if>
				   <c:if test="${empty course.ecClasshours || (fn:length(course.ecClasshours)<=0)}">
				    	此课程没有选上课时间
				    </c:if>
				</td>
				</c:if>
				--%>
	            <td>
			         <c:if test="${ecsignType==0  }">
			        	<a class="td_b"   onclick="delSign('${course.ecSign.id }','${course.ecCourseVO.name }')"  href="javascript:" >报错了/退课</a>
			        </c:if>
			        <c:if test="${ecsignType==1 }">
			        	<a class="td_b" 
			        	onclick="delSignShiJia('${course.ecCourseVO.id }',${course.ecCourseVO.classhourRequire}
			        	,'${course.ecCourseVO.name }','${course.ecSign.id }','${course.ecSign.classhourIds }')"
			        	 href="javascript:" >退课？/报课时</a>
			        </c:if>
	            </td>
	        </tr>
        </c:forEach>
    </table>
    <c:if test="${openSign==0}">
    	<p class="remind"><span class="fl">报名没有进行开放!请等待具体通知!</span></p>
    </c:if>
    <c:if test="${(term.maxCourse>courseCount||term.maxCourse==0) && courseCount!=0 && openSign==1 }">
    <p class="remind"><span class="fl">您已报名了<b>&nbsp;${courseCount }&nbsp;</b> 门课程，还可以</span><a class="c_btn" href="${prc}/elec/ecsignSigAction.action?clubState=${clubState}" >继续报名</a></p>
    </c:if>
    <c:if test="${courseCount==0 && openSign==1 }">
      <p class="remind"><span class="fl"> 你还没有自己的课程哦！快来</span><a class="c_btn" href="${prc}/elec/ecsignSigAction.action?clubState=${clubState}" >报名</a><span class="fl">吧~</span></p>
    </c:if>
    <c:if test="${term.maxCourse<=courseCount &&term.maxCourse!=0 && openSign==1 }">
    <div class="coursetext">
               你已经报名了<b>&nbsp;${courseCount}&nbsp;</b> 门课程<br/>  
               <img src="${med}/images/alerm.gif" width="16" height="15" />课程已经爆满啦~如果对你选的课程不满意请<b>"退课"</b>，退课后可报名其他课程！
    </div> 
    </c:if>
</div>
</body>
</html>