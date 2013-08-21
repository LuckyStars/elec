<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学生报名</title>
<link href="${med}/css/xqstyle.css" rel="stylesheet" type="text/css" />
<link href="${med }/css/reset.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${med}/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${med }/js/common/agent.js" ></script>
<script type="text/javascript">
$(function(){
	$(".change_tab tr:odd").css("background","#f0f8fc");
	$(".change_tab tr:even").css("background","#d5e0ee");
	$("ul li").click(function(){
        $(".arr").attr("class","");
		$(this).attr("class","arr");
	});
	$("ul li.mag_btn").click(function(){
		$(this).attr("class","mag_btn");
	});
	var x = $(document).height();
                        $(".shade").css({"width": $(document).width(), "height": $(document).height()});

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
function historygo(){
	var locat='${locationGo}';
	if(locat=='1'){
		location="${prc}/elec/ecsignSigAction.action?ecCourseVO.id=${course.ecCourseVO.id}"+"&locationGo=${locationGo}&clubState=${clubState}";
		}
	if(locat=='2'){
		location="${prc}/elec/ecsignMySigAction.action?ecCourseVO.id=${course.ecCourseVO.id}"+"&locationGo=${locationGo}&clubState=${clubState}";
		}
}
var typeId='${ecCourseVO.typeId }';
var weekIndex='${ecCourseVO.weekIndex }';
var searchName="${ecCourseVO.name}"+"&flag=3&ecCourseVO.id=${ecCourseVO.id}&locationGo=${locationGo}&clubState=${clubState}";
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
<div class="con_conent fixed" style="height: 100%;" >
    <h1 class="tit"><span class="title"><fmt:message key="i18nTermTitle" bundle="${bundler}" /></span></h1>
    <h1><span>课程详细</span></h1>
     <div class="btn" style="margin-left:200px;" >
      <c:if test="${ecCourseVO.maxStudentNum<=ecCourseVO.currentStudentNum 
	             	&& ecSignCourseVo.signStarState && !ecSignCourseVo.signUpState && (term.maxCourse>courseCount|| term.maxCourse==0)}">
          <a  href="javascript:" >报满了</a>
       </c:if>
       
       <c:if test="${term.maxCourse<=courseCount && !ecSignCourseVo.signUpState && term.maxCourse!=0 }">
			        <a  href="javascript:" >你的课程已满</a>
		</c:if>
	    <c:if test="${!ecSignCourseVo.signStarState && !ecSignCourseVo.signUpState && (term.maxCourse>courseCount||term.maxCourse==0) }">
       		<a  href="javascript:" >报名没有开启</a>
        </c:if>
        <c:if test="${ecCourseVO.maxStudentNum>ecCourseVO.currentStudentNum 
			        &&ecSignCourseVo.signStarState && !ecSignCourseVo.signUpState && (term.maxCourse>courseCount||term.maxCourse==0) }">
			<a  onclick="addSign('${ecCourseVO.id}','${ecCourseVO.name }')" href="javascript:">选课</a>
	    </c:if>
	    <c:if test="${ecSignCourseVo.signUpState  && ecsignType==1 }"> 
	       	<a 	onclick="delSignShiJia('${ecCourseVO.id }',${ecCourseVO.classhourRequire}
	       	,'${ecCourseVO.name }','${ecSignCourseVo.ecSign.id }','${ecSignCourseVo.ecSign.classhourIds }')"
	       	 href="javascript:" >退课/报课时</a>
	   </c:if>
       <a href="javascript:" onclick="historygo()" >返回</a>
     </div>
     <div class="clear"></div>
    <div class="text_box" style="height: 100%;" >
      
        <c:if test="${!empty ecCourseVO.courseComment }">
        	<p class="red" style="width:660px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${ecCourseVO.courseComment}">
        	选课提示：${ecCourseVO.courseComment }
        	</p>
        </c:if>
        <p style="width:720px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${ecCourseVO.name }">
            <label>课程名称：</label>${ecCourseVO.name }
		</p>
        <p>
            <label>课程分类：</label> ${ecCourseVO.typeName }
         </p>
        <p>
            <label>年纪范围：</label>${ecCourseVO.gradeNames }
        </p>
        <p>
            <label>限定人数：</label>${ecCourseVO.maxStudentNum }人
         </p>
        <p style="width:720px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${ecCourseVO.teacherNames }">
            <label>任课老师：</label>${ecCourseVO.teacherNames }
		</p>
		 <p>
            <label>开课日期：</label><fmt:formatDate value="${ecCourseVO.classStartDate}" pattern="yyyy年MM月dd日" /> 至  <fmt:formatDate value="${ecCourseVO.classEndDate}" pattern="yyyy年MM月dd日" />
		</p>
		<p>
            <label>报名日期：</label>
             <fmt:formatDate value="${ecCourseVO.signStartDate}" pattern="yyyy年MM月dd日 HH:mm" />
             	 至  
             <fmt:formatDate value="${ecCourseVO.signEndDate}" pattern="yyyy年MM月dd日 HH:mm" />
		</p>
		 <p>
            <label>每周课时：</label>${ecCourseVO.classhourNum } 课时/周
		</p>
       <div class="ptext fixed" style="padding:5px 0 5px 60px; width:602px; line-height:25px;">
	             <c:forEach items="${ecCourseVO.classhourList}" var="ch" varStatus="chStats" >
			   	${ch.weekIndexStr } 
			   	从
			   	<fmt:formatDate value="${ch.weekStartTime}" pattern="HH:mm" />
				至
				<fmt:formatDate value="${ch.weekEndTime}" pattern="HH:mm" />
				<c:if test="${!chStats.last}"><br/></c:if>
			    </c:forEach>
		</div>
        <p>
            <label>上课地点：</label>${ecCourseVO.startPlace.name }
		</p>
		<p>
                <label>放学地点：</label>
                ${ecCourseVO.endPlace.name }
        </p>
          <p style="">
            <a href="javascript:" id="showClose" style="color:#275A8D;">展开课程内容详情↓</a>
		</p>
        <div id="detailContent"  class="ptext fixed" style="display: none; width:672px;" >
            <p>
                <label>课程内容：</label>
                <div class="notContent" >
                ${ecCourseVO.courseContent}
                </div>
                </p>
            <p>
                <label>备注：</label>
                <div class="notContent" > ${ecCourseVO.courseNote}</div></p>
        </div>
       
    </div>
       <div class="btn" style="margin-left:200px;" >
       <c:if test="${ecCourseVO.maxStudentNum<=ecCourseVO.currentStudentNum 
	             	&& ecSignCourseVo.signStarState && !ecSignCourseVo.signUpState && (term.maxCourse>courseCount|| term.maxCourse==0)}">
          <a  href="javascript:" >报满了</a>
       </c:if>
       
       <c:if test="${term.maxCourse<=courseCount && !ecSignCourseVo.signUpState && term.maxCourse!=0 }">
			        <a  href="javascript:" >你的课程已满</a>
		</c:if>
	    <c:if test="${!ecSignCourseVo.signStarState && !ecSignCourseVo.signUpState && (term.maxCourse>courseCount||term.maxCourse==0) }">
       		<a  href="javascript:" >报名没有开启</a>
        </c:if>
        <c:if test="${ecCourseVO.maxStudentNum>ecCourseVO.currentStudentNum 
			        &&ecSignCourseVo.signStarState && !ecSignCourseVo.signUpState && (term.maxCourse>courseCount||term.maxCourse==0) }">
			<a  onclick="addSign('${ecCourseVO.id}','${ecCourseVO.name }')" href="javascript:">选课</a>
	    </c:if>
        <c:if test="${ecSignCourseVo.signUpState  &&ecsignType==0}">
			        	<a   onclick="delSign('${ecSignCourseVo.ecSign.id }','${ecCourseVO.name }')"  href="javascript:">报错退课</a>
	    </c:if>
	    <c:if test="${ecSignCourseVo.signUpState  && ecsignType==1 }"> 
	       	<a 	onclick="delSignShiJia('${ecCourseVO.id }',${ecCourseVO.classhourRequire}
	       	,'${ecCourseVO.name }','${ecSignCourseVo.ecSign.id }','${ecSignCourseVo.ecSign.classhourIds }')"
	       	 href="javascript:" >退课/报课时</a>
	   </c:if>
       <a href="javascript:" onclick="historygo()" >返回</a>
       </div>
</div>
</body>
</html>