<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../common/common.jsp"%>
<title>学生报名首页</title>
<link href="${med}/css/xqstyle.css" rel="stylesheet" type="text/css" />
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
 });
var starTime=${starTime};//距开始时间
var endTime=${endTime};//距结束时间
function getTimes(reduceTime) { 
	var timeStr="";
	if (reduceTime>0) {
		var day=parseInt(reduceTime/(24*60*60*1000));   
		var hour=parseInt((reduceTime/(60*60*1000)-day*24));   
		var min=parseInt(((reduceTime/(60*1000))-day*24*60-hour*60));   
		var s=parseInt((reduceTime/1000-day*24*60*60-hour*60*60-min*60));
		if(day>0){
			timeStr=day+"天";
		}
		if (hour>0) {
			timeStr+=hour+"小时";
		}
		if (min>0) {
			timeStr+=min+"分钟";
		}
		if (s>0) {
			timeStr+=s+"秒";
		}
	}
	return timeStr;
}
function showTime() {
	if(endTime>0){
		$("#star").html("报名正在继续,离结束时间还有"+getTimes(endTime));
		endTime=endTime-1000;
		if(endTime<=0){
			window.location.reload(); 
		}	
	}
	if(starTime>0){
		$("#noStar").html("距离报名开始时间还有"+getTimes(starTime));
		starTime=starTime-1000;
		if(starTime<0){
			window.location.reload(); 
			}
	}
	window.setTimeout(showTime,1000);
}
</script>
</head>
<body onload="showTime()" >
<div class="con_conents fixed" style="width: 100%" >
    <h1 class="tit"><span class="title"><fmt:message key="i18nTermTitle" bundle="${bundler}" /></span>
     <c:if test="${!empty clubState}">
     <a class="fr" href="${prc}/elec/ecsignTypeSigAction.action">返回</a>
     </c:if>
    </h1>
        <div class="title_box fixed">
        <div class="title">${term.name }</div>
        <p>报名时间：<fmt:formatDate value="${term.signDateStart}" pattern="yyyy年MM月dd日" />
         - <fmt:formatDate value="${term.signDateEnd}" pattern="yyyy年MM月dd日" /></p>
        </div>
        <br/><br/><br/>
        <div class="apply" >
        	<c:if test="${endTime>0&&starTime==0 }">
	          <a id="star" class="appnamec" style="color: green;" href="${prc}/elec/ecsignSigAction.action?clubState=${clubState}"></a>
	        </c:if>
	        <c:if  test="${starTime>0 }">
	          <a id="noStar" class="appnamec" href="#"></a>
	        </c:if>
	        <c:if test="${starTime==0&&endTime==0}">
	        	<a class="appnamea" href="#"></a>
	        </c:if>
        </div>
        <c:if test="${openSign==1}">
        <div class="messagebox3"><span>想知道我们都为您提供了哪些课程吗？点击这里哦！</span><a  href="${prc}/elec/ecsignSigAction.action?clubState=${clubState}">所有课程信息</a></div>
        </c:if>
         <c:if test="${openSign==0}">
         <div class="messagebox1"><span>报名信息<fmt:formatDate value="${term.openDateStart}" pattern="MM月dd日HH:mm" />-
           <fmt:formatDate value="${term.openDateEnd}" pattern="MM月dd日HH:mm" />进行开放!
         </span><a  href="javascript:">所有课程信息</a></div>
        </c:if>
        <div class="messagebox2"><span>想知道您已经选了哪些课程吗？点击这里哦！</span><a href="${prc}/elec/ecsignMySigAction.action?clubState=${clubState}">${uEcUser.name }的课程信息</a></div>
</div>
</body>
</html>