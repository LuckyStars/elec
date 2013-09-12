<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>教务管理-选课学生信息</title>
	<link href="${med}/css/main.css" rel="stylesheet" type="text/css" />
	<link href="${med}/js/easyui/themes/metro/easyui.css" rel="stylesheet" type="text/css" />
	<link href="${med}/js/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
	<link href="${med}/css/main.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${med }/js/common/agent.js" ></script>
	<style type="text/css">
a:HOVER {
	text-decoration: none;
}
</style>
	<script type="text/javascript" src="${med}/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${med}/js/easyui/jquery.easyui.min.js"></script>
	
	<script type="text/javascript">
	function removeStu(stuId){
		if(confirm("确定删除吗?")){
			location.href="${prc}/elec/removeStudentInfo.action?flag=${flag_list}&stuInfo.signId=" + stuId;
		}
	}
		$(function(){	
			$('#stuTree').tree({ 
				url:'${prc}/elec/treeStudentInfo.action?course.id=${course.id}',
				checkbox:true 
			}); 
		});
		function showPop(){
			$("#popDiv").show();
		}
		function closePop(){
			$("#popDiv").hide();
		}
		
		function checkForm(){
			$("input[name='studentIds']").val("");
			<c:if test="${sessionScope.signType=='shijia'}">
			var times = ${course.classhourRequire};
			if($("input[name='classHourId']:checked").length!=times){
				alert('请选择正确的课时数');
				return false;
			}
			</c:if>
			
			var nodes = $('#stuTree').tree('getChecked');
			var result = false;
			var stuids = "";
			var k=0 ;
			var maxS = ${course.maxStudentNum};
			
			for(var i=0; i<nodes.length; i++){
				if(nodes[i].id.indexOf("|")<=0){
					stuids = stuids + nodes[i].id +"," 
					result = true;
					k++;
					$("input[name='studentIds']").val(stuids);	
				}
			}

			
			if(k>maxS){
					alert("最大报名数为:${course.maxStudentNum}人，请重新添加学生");
					return false;
				}

			if(result==false){
				
				alert('请至少选择一个学生');
				return false;
			}
			
			var classHourId = "";
			<c:if test="${sessionScope.signType=='shijia'}">
			var claChecked=document.getElementsByName("classHourId");
			for ( var i = 0; i < claChecked.length; i++) {
				
				if(claChecked[i].checked==true){
					var array_element = claChecked[i].value;
					classHourId = "&classHourId=" + array_element + classHourId ;
				}
			}
			
			</c:if>
			$.ajax({
				  url: "${prc }/elec/validateStudentStudentInfo.action",
				  dataType: 'json',
				  data: "course.id=${course.id}&studentIds=" + $("input[name='studentIds']").val() + classHourId,
				  async:false,
				  success: function(data) {
					if(data.state=="fail"){
						result = false;
						var msg = "所选学生不能提交.\r\r";
						if(data.max!=""){
						
							msg = msg+ "最大可报名数的为" +${course.maxStudentNum}+ ".\r\r";
							
						}
						if(data.hour!=""){
							msg = msg+ "与已报名课程时间冲突的学生:" + data.hour + ".\r\r";
						}
						if(data.already!=""){
							msg = msg+ "已经报名此课程的学生:" + data.already +" 不能再报名,请重新修改"+".\r\r";
						}
						if(data.stuCount!="true"){
							//msg = msg + "如果添加所选学生则会超出课程最大报名数!";
							msg = msg + "最大报名数为:${course.maxStudentNum}人，请重新添加学生?";
						}
						alert(msg);
					}else{
						result = true;
					}
				  }
			});
		
			return result;
		}

	</script>
</head>
	<body>
	<%-- 弹出层 --%>
	<div class="w2warp" style="display:none; position:absolute; z-index:5;padding:5px;width:100%;height:100%;background:url(${med}/images/fiter.png);" 
		id="popDiv">
		<div class="popup_border big" style="left:50%;margin-top:100px;left:50%;margin-left:35%;">
			<form action="${prc }/elec/addSignStudentInfo.action?flag=${flag_list}"  onsubmit="return checkForm();"  method="post">
				<input type="hidden" name="course.id" value="${course.id}"/>
				<input type="hidden" name="studentIds" value=""/>
				<h2>录入学生
				<c:if test="${sessionScope.signType=='shijia'}">
					(必上课程数为${course.classhourRequire})
				</c:if>
					<div class="close" style="cursor:pointer">
						<img  src="${med }/images/close.gif" onclick="closePop()"/>
					</div>
				</h2>
				<div class="edit_course" style="display: block;" ></div>
				<div class="cl"></div>
				<div style="text-align: left;height: 200px;">
					<div id="stuTree" style="height: 200px;overflow: auto;" class="easyui-tree" animate="true" dnd="true"></div>
				</div>
				<c:if test="${sessionScope.signType=='shijia'}">
					<%-- 课时选择 --%>
					<div>
						<c:if test="${not empty course.classhourList}">
							<c:forEach items="${course.classhourList}" var="claHour">
								${claHour.weekIndexStr}
								<fmt:formatDate value="${claHour.weekStartTime}" pattern="HH:mm"/>
								至
								<fmt:formatDate value="${claHour.weekEndTime}" pattern="HH:mm"/>
								<input type="checkbox" value="${claHour.id }" name="classHourId"/><br>
								
							</c:forEach>
						</c:if>
					</div>
				</c:if>
				<div class="cl"></div>
				<div class="button_border">
					<ul>
						<li>
							<input name="" class="button" type="submit" value="提交" title="提交" />
						</li>
						<li>
							<input onclick="closePop()" class="button" type="button" value="返回" title="返回" />
						</li>
					</ul>
				</div>
			</form>
		</div>
	</div>
	<%-- 弹出层 --%>
<div class="main">
	<h1 class="tit">
	    <span class="title"><fmt:message key="i18nTermTitle" bundle="${bundler}" /></span>
	    <c:if test="${flag_list==0}">
	    	<a class=".fr" href="${prc}/elec/listEcCourse.action" style="float:right;padding-right:10px;font-weight: normal;" >返回</a>
	    </c:if>
	   <c:if test="${flag_list==1}">
	    	<a class=".fr" href="${prc}/elec/viewEcCourse.action?id=${course.id}&flag=1" style="float:right;padding-right:10px;font-weight: normal;" >返回</a>
	    </c:if>
	    <c:if test="${flag_list==2}">
	    	<a class=".fr" href="${prc}/elec/viewEcCourse.action?id=${course.id}&flag=2" style="float:right;padding-right:10px;font-weight: normal;" >返回</a>
	    </c:if>
	    <c:if test="${flag_list==4}">
	    	<a class=".fr" href="${prc}/elec/viewEcCourse.action?id=${course.id}&flag=4" style="float:right;padding-right:10px;font-weight: normal;" >返回</a>
	    </c:if>
	    <c:if test="${flag_list==3}">
	    	<a class=".fr" href="${prc}/elec/viewEcCourse.action?id=${course.id}&flag=3" style="float:right;padding-right:10px;font-weight: normal;" >返回</a>
	    </c:if>
	  	</h1>
	<div class="cl"></div>
	<div class="title_h4">
		<h4>
			<c:out value="${course.name}" escapeXml="false"></c:out>学生列表
		</h4>
	</div>
	<div class="time">选课人数：${page.count}&nbsp;&nbsp;&nbsp;&nbsp;任课教师：${course.teacherNames }</div>
	<div class="flip_border">
	<div class="small_button">
	<ul>
		<li>
			<a style="color: white;" href="javascript:showPop();">
			
				录入学生<input type="hidden" name="${flag_list}"/>	
				</a>
		</li>
		<li>
			<a href="${prc}/elec/exportExlStudentInfo.action?course.id=${course.id}" style="color: white;">
				导出全部
			</a>
		</li>
	</ul>
	</div>
	</div>
	<div class="cl"></div>
	<table border="0" cellspacing="0" cellpadding="0" class="table_border">
		<tr class="table_title">
			<td class="first" width="100">学号</td>
			<td width="100" align="left">学生姓名</td>
			<td>性别</td>
			<td width="150">班级</td>
			<td width="100" align="left">班主任</td>
			<td>家长电话</td>
			<td width="100">操作</td>
		</tr>
		<c:if test="${not empty page.datas}" >
			<c:forEach items="${page.datas}" var="stu" varStatus="i">
				<tr ${(i.index+1)%2==0 ? 'class="second"':'' }>
					<td class="first">${stu.stuNo }</td>
					<td align="left">${stu.stuName }</td>
					<td>${stu.sex }</td>
					<td>${stu.className }</td>
					<td align="left">${stu.classMasterName }</td>
					<td>${stu.parentMobile }</td>
					<td>
						<a href="javascript:removeStu('${stu.signId}');">删除</a>
					</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	<div class="cl"></div>
	<jsp:include page="/WEB-INF/common/page.jsp" >
	<jsp:param name="urlAction" value="elec/listStudentInfo.action" />
	<jsp:param name="page" value="page" />
	<jsp:param name="params"  value="course.id:${course.id}" />
	</jsp:include>
	</div>
</body>
</html>
