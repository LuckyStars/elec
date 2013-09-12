<%@ page language="java" contentType="text/html; charset=utf-8"pageEncoding="utf-8"%>
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
<script type="text/javascript">
$(function(){
	$("#searchMain").click(function(){
		var mainName = $.trim($("#mainName").val());
		if (mainName != ""&&mainName!="请输入课程名称") {
			var postForm = document.createElement("form");//表单对象   
		    postForm.method="post" ;   
		    postForm.action = '${prc}/elec/searchAllOurClassheaderMaster.action' ;   
		     
		    var nameInput = document.createElement("input") ;  //name input 
		    nameInput.setAttribute("name", "courseName") ;   
		    nameInput.setAttribute("value", mainName);   
		    postForm.appendChild(nameInput) ;
		
		    document.body.appendChild(postForm) ;   
		    postForm.submit() ;   
		    document.body.removeChild(postForm) ; 
		}else{
			alert("请输入要搜索的课程名称!");
		}
	});

	
  
 });

function radio_click(obj){
    if (parseInt(obj.value) == 1) {
    	window.location.href="${prc}/elec/findAllMainheaderMaster.action";
    } else {
    	window.location.href="${prc}/elec/findAllClassheaderMaster.action";
    }
}
</script>

</head>
<body>

<div class="con_conent fixed">
<h1 class="tit"><span class="title"><fmt:message key="i18nTermTitle" bundle="${bundler}" /></span><a class="fr" href="${prc}/index.jsp">返回</a></h1>
    <div class="nextmenu">
	     <p>
	    	<a  href="${prc}/elec/findStuByHeaderMasterheaderMaster.action">学生选课信息</a>
	    	<span></span>
	    	<a  class="current" href="${prc}/elec/findAllMainheaderMaster.action">课程信息</a>
	    </p>
    </div>
    <div class="title_box fixed" >
        <div class="title" style=" height: 30px;line-height: 25px;padding-top:0px;">
        		<c:out value="${ecTerm.name}" escapeXml="false"/>
        </div>
        <p>报名时间：<fmt:formatDate value="${ecTerm.signDateStart}"  pattern="yyyy-MM-dd HH:mm"/> - <fmt:formatDate value="${ecTerm.signDateEnd}"  pattern="yyyy-MM-dd HH:mm"/> </p>
        <p class="red">选课提示：<c:out value="${ecTerm.name}" escapeXml="false" /></p>
    </div>
    <h2>
        <input class="rad fl" name="radioId" type="radio" value="1"  onclick="radio_click(this)"/>
        <span class="fl">所有课程</span>
        <input class="rad fl" name="radioId" type="radio" value="2" checked="checked" onclick="radio_click(this)"/>
        <span class="fl">本班报名课程</span> 
        
        <span class="spanleft_list">
        	<input type="text" name="courseName" id="mainName" value="${courseName}" />
        	 <script type="text/javascript">
	        	var s = $("#mainName").val();
	        	if(s == ""){
					$("#mainName").val("请输入课程名称");
	        	}else{
	        		//
		        }
	        	$("#mainName").focus(function(){

	        		if($(this).val() == "请输入课程名称"){
						$(this).val('');
		        	}else{
						//
			        }
		        });
		        $("#mainName").blur(function(){
		        	if($(this).val() == ""){
						$(this).val("请输入课程名称");
		        	}else{
						//
			        }
			    });
	        </script>
        	<a class="s_btn" href="#" id="searchMain"></a>
        	<a class="c_btn" href="${prc}/elec/exportOurClassheaderMaster.action?ecTerm.id=${ecTerm.id}">导出</a>
        </span> 
    </h2>
<form id="subForm" action="elec/main_deleteSelectedMain.action" method="post" >
    <div class="clear"></div>
    <table width="100%"border="0" cellpadding="0" cellspacing="0" class="change_tab">
        <tr>
            <th width="7%" scope="col">课程类别</th>
			<th width="10%" scope="col">课程名称</th>
			<th width="15%" scope="col">任课老师</th>
			<th width="13%" scope="col">开结课日期</th>
			<th width="19%" scope="col">上课时间</th>
			<th width="13%" scope="col">上课地点</th>
			<th width="7%" scope="col">限定人数</th>
			<th width="9%" scope="col">本班报名数</th>
			<th width="7%" scope="col">操作</th>
        </tr>
        
       <c:choose>
       		<c:when test="${empty classList}">
       			<tr>
       				<td colspan="9">暂时无数据</td>
       			</tr>
       		</c:when>
       		<c:otherwise>
       			<c:forEach items="${classList}" var="str" >
       				<tr>
       					<td>${str.typeName}</td>
       					<td ><a href="${prc}/elec/viewEcCourse.action?id=${str.coursrId}&flag=2" ><c:out value="${str.courseName}" escapeXml="false" /></a></td>
       					<td title="${str.teacherName}">
       						<c:choose>
								<c:when test="${fn:length(str.teacherName)>9}">
									${fn:substring(str.teacherName,0,8) }…
								</c:when>
								<c:otherwise>
									${str.teacherName}
								</c:otherwise>
							</c:choose>
       					</td>
       					<td>
       						<fmt:formatDate value="${str.startTime}" pattern="yyyy-MM-dd" />
       						<fmt:formatDate value="${str.endTime}" pattern="yyyy-MM-dd" />
       					</td>
       					<td>
       						<c:forEach items="${str.classTime}" var="tt">
       							<c:out value="${tt}" escapeXml="false" />
       						</c:forEach>
       					</td>
       					<td><c:out value="${str.lessonPlace}" escapeXml="false" /></td>
       					<td>${str.totolNum}</td>
       					<td><a href="${prc}/elec/findOneMainStuheaderMaster.action?id=${str.coursrId}" >${str.selectedNum}</a></td>
       					<td><a href="${prc}/elec/viewEcCourse.action?id=${str.coursrId}&flag=2" >查看</a></td>
       				</tr>
       			</c:forEach>
       		</c:otherwise>
       </c:choose> 
    </table>
    <div class="clear"></div>
</form>            
</div>
</body>
</html>