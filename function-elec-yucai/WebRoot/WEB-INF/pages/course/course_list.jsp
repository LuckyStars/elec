<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../common/common.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课程列表</title>
<link href="${med }/css/place/xqstyle.css" rel="stylesheet" type="text/css" />
<link href="${med }/css/tree.css" rel="stylesheet" type="text/css" />
<link href="${med }/js/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="${med }/js/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
<style>.combo{float:left;margin-top:11px;}
.change_tab td a {padding:0;}</style>
<script type="text/javascript" src="${med }/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${med }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${med }/js/course/course_list.js"></script>
<script type="text/javascript" src="${med }/js/common/agent.js" ></script>
<script type="text/javascript"> 
var prc = "${prc}";
$(function(){
	$(".change_tab tr:odd").css("background","#f0f8fc");
	$(".change_tab tr:even").css("background","#d5e0ee");
	$(".shade").css({"width": $(document).width(), "height": $(document).height()});
});
</script>
</head>
<body style="background:#F0F8FC;">
<%-- 弹出层 --%>
<div class="shade" style="background:none; display:none;" id="term_div_popue">
    <div class="photo_bg fixed" style="width:250px; left: 60%;">
        <h1><span>选课列表</span><a href="javascript:void(0);" class="term_div_close"></a></h1>
        <div class="list" style="height:100px; overflow: auto;">
        	<c:forEach items="${termList}" var="term" varStatus="stats" >
            <p>
            	<input id="radio_${stats.index}" name="term_popue_id" type="radio" value="${term.id }" />
            	<c:if test="${term.currentTerm}">
	      			<img src="${med}/images/table_selected.gif" style="float:left;" title="当前学期" />
      	 		</c:if>
            	<label for="radio_${stats.index}" style="display:block; width:150px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${term.name }">
            		${term.name }
            	</label>
            </p>
		    </c:forEach>
        </div>
        <div class="clear"></div>
        <div class="layer_btn" style="margin:20px 0px 20px 20px;">
        	<a href="#" id="term_div_submit">确定</a>
        	<a href="javascript:void(0);" class="term_div_close">返回</a>
        </div>
        <script type="text/javascript">
        $("input[name='term_popue_id']").val(["${ecTerm.id}"]);
        </script>
    </div>
</div>
<%--弹出层 end --%>


<%-- 树 --%>
<div class="delete_group popfor1" id="tree_popu_div">
	<div class="dg_congtent">
		<div class="dg_title">组织结构</div>
		<div class="dg_par">
			<div style="overflow:auto;height:290px;background:#fff;">
				<ul style="background:#fff;" id="allTeacherTree">请稍候，数据加载中...</ul>
			</div>
		</div>
		<div class="dg_button">
			<input type="button" class="btn3" value="确 定" id="tree_ok" onmouseover="this.title = this.value"/>
			<input type="button" class="btn3" value="返 回" id="tree_cancel" onmouseover="this.title = this.value"/>
		</div>
	</div>
</div>
 <%-- 树 emd --%>

<div class="con_conent fixed">
 	<h1 class="tit">
 		<span class="title"><fmt:message key="i18nTermTitle" bundle="${bundler}" /></span>
 		<div class="return fr"><a href="${prc}/elec/index.action">返回</a></div>
 	</h1>
	<c:if test="${!empty ecTerm }">
    <div class="title_box fixed">
        <div class="title">
        	<span id="xqb_title">${ecTerm.name }</span>
        </div>
        <p>报名时间：
        	<fmt:formatDate value="${ecTerm.signDateStart}" pattern="yyyy-MM-dd HH:mm" />
			-
			<fmt:formatDate value="${ecTerm.signDateEnd}" pattern="yyyy-MM-dd HH:mm" />
        </p>
        <c:if test="${!empty ecTerm.comments }">
        <p class="red">
        	<span style="margin:0 auto; display:block; width:600px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${ecTerm.comments }">
        	选课提示：${ecTerm.comments }
        	</span>
        </p>
        </c:if>
    </div>
   	<form action="${prc}/elec/listEcCourse.action" method="post" id="form_search" style="overflow: auto;" >
    <h2 style="margin-bottom:0; border-bottom:0;">
        <label 
        	<%-- 排除：任课教师自己 --%>
			<c:if test="${curUser.courseTeacherViewable }">style="margin-left:120px;"</c:if>
        >课程类别：</label>
        <select name="ecCond.typeId" style="width:96px;">
			<option value="">--请选择--</option>
			<c:forEach items="${typeList}" var="type" >
			<option title="${type.name}" value="${type.id}">${type.name}</option>
			</c:forEach>
		</select>
		<script type="text/javascript">
			$("select[name='ecCond.typeId']").val("${ecCond.typeId}");
		</script>
        <label style="margin-left: 10px;"><%--年级范围--%><fmt:message key="i18nGradeNames" bundle="${bundler}" />：</label>
        <select id="cc" style="width:92px;" panelHeight="auto" ></select>
		<div id="sp" style="line-height:22px; " >
			<c:forEach items="${ucGradeList}" var="ucGrade" >
			<input type="checkbox" value="${ucGrade.id}" /><span>${ucGrade.gradeName}</span><br/>
			</c:forEach>
		</div>
		<input type="hidden" name="ecCond.gradeIds" value="${ecCond.gradeIds }" />
		<input type="hidden" name="ecCond.gradeNames" value="${ecCond.gradeNames }" />
		
		<c:if test="${!curUser.courseTeacherViewable }">
        <label style="margin-left: 10px;">任课老师：</label>
        <input type="text" name="ecCond.teacherNames" style="width:95px; margin-top: 11px;" readonly="readonly" value="${ecCond.teacherNames}" /><%--其实只有一个 --%>
        <input type="hidden" name="ecCond.teacherIds" value="${ecCond.teacherIds }" />
        <a class="s_btn" href="javascript:void(0);" style="font-size:12px; margin-top:11px;" id="sel_teacher_a">添加</a>
        </c:if>
        
        <span class="spanleft" style="width:180px; float:left; margin-left:10px;">
        <label style="margin-left: 0px; width: 60px;">课程名称：</label>
        <input name="ecCond.name" id="trimVal" value="${ecCond.name}" type="text" maxlength="20" style="width:100px; height:18px;" />
        <input name="ecCond.termId" type="hidden" id="currentTerm_id" value="${ecCond.termId }" />
        </span>
    </h2>
    <h3 class="condition" style="background: #F0F3F7; border: 1px solid #AEC2DB; border-top:0;">
        <a class="search_btn" style="margin-left:340px;" id="form_submit_search" href="javascript:void(0);" >查询</a>
        <a class="search_btn" id="form_reset_search" href="javascript:void(0);" >重置</a>
    </h3>
   	</form>
   	
    <h3 class="condition" style="height: 32px;">
    	<%-- 排除：任课教师自己 --%>
		<c:if test="${!curUser.courseTeacherViewable }">
        <input class="eck" type="checkbox" name="checkbox" id="alloperCheckbox" style="width:13px;" />
        <a class="e_btn" href="javascript:void(0);" id="delCheckedCourses">删除已选中课程</a>
        </c:if>
        <a style="position:relative;" class="d_btn padlr" href="javascript:void(0);"><span id="export_excel">导出</span>
        	<div class="alert" style="left:-55px; display:none;" id="menuContent">
        		<p id="export_allCourse_excel">导出课程列表</p>
				<p id="export_allStudent_excel">导出选课学生列表</p>
			</div>
		</a>
		<%-- 排除：任课教师自己 --%>
		<c:if test="${!curUser.courseTeacherViewable }">
		<c:if test="${btnBool}">
        <a class="d_btn" href="javascript:void(0);" id="add_course_btn"><span>录入课程</span></a>
        </c:if>
        </c:if>
    </h3>
	    
    <div class="clear"></div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="change_tab" style="margin-top:5px;">
        <tr>
            <th width="4%" scope="col">&nbsp;</th>
            <th width="7%" scope="col">课程类别</th>
            <th width="9%" scope="col">课程名称</th>
            <th width="10%" scope="col">任课老师 </th>
            <th width="20%" scope="col">上课时间</th>
            <th width="13%" scope="col"><%--年级范围--%><fmt:message key="i18nGradeNames" bundle="${bundler}" /></th>
            <th width="10%" scope="col">上课地点 </th>
            <th width="13%" scope="col">已选人数/限定人数</th>
            <th width="14%" scope="col">操作</th>
        </tr>
        <c:if test="${!empty page.datas && (fn:length(page.datas)>0)}">
	    <c:forEach items="${page.datas}" var="pp" varStatus="stats" >
	    <tr>
	        <td >
	        	<c:if test="${!curUser.courseTeacherViewable }">
	        	<input type="checkbox" class="operCheckbox" value="${pp.id }" <c:if test="${!pp.edit}">disabled="disabled"</c:if> />
	        	</c:if>
	        	<c:if test="${curUser.courseTeacherViewable }">
	        	&nbsp;
	        	</c:if>
	        </td>
	        <td >${pp.typeName}</td>
	        <td >
	        	<div style="width:60px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${pp.name }">
	        	<a href="viewEcCourse.action?id=${pp.id}&flag=1">${pp.name }</a>
	        	</div>
	        </td>
	        <td >
	        	<div style="width:79px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${pp.teacherNames }">
	        	${pp.teacherNames}
	        	</div>
	        </td>
	        <td >
	        	<c:if test="${!empty pp.classhourList && (fn:length(pp.classhourList)>0)}">
			    <c:forEach items="${pp.classhourList}" var="ch" varStatus="chStats" >
			   	${ch.weekIndexStr } 
			   	<fmt:formatDate value="${ch.weekStartTime}" pattern="HH:mm:ss" />
				-
				<fmt:formatDate value="${ch.weekEndTime}" pattern="HH:mm:ss" />
				<c:if test="${!chStats.last}"><br/></c:if>
			    </c:forEach>
			    </c:if>
	        </td>
	        <td >
	        	<div style="width:95px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${pp.gradeNames }">
	        	${pp.gradeNames}
	        	</div>
	        </td>
	        <td >
	        	<div style="width:79px; word-break:keep-all; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;" title="${pp.startPlaceName }">
	        	${pp.startPlaceName}
	        	</div>
	        </td>
	        <td style="color:#275A8D;">
	        	<a href="${prc}/elec/listStudentInfo.action?flag=0&course.id=${pp.id }&flagx=1">${pp.currentStudentNum}/${pp.maxStudentNum}</a>
	        </td>
	        <td >
	        	<a href="viewEcCourse.action?id=${pp.id}&flag=1">查看</a>
	        	<c:if test="${pp.edit}">
	            <a href="addUIEcCourse.action?id=${pp.id}&flag=edit">编辑</a>
	            <%-- 排除：任课教师自己 --%>
				<c:if test="${!curUser.courseTeacherViewable }">
	            <a href="javascript:void(0);" onclick="javascript:if(confirm('确认删除？删除后不可恢复！')){window.location='removeEcCourse.action?ids=${pp.id}';}">删除</a>
	            </c:if>
	            </c:if>
	        </td>
	    </tr>
	    </c:forEach>
	    </c:if>
	    <c:if test="${empty page.datas || (fn:length(page.datas)<=0)}">
	    	<tr><td colspan="9">暂无数据</td></tr>
	    </c:if>
        
    </table>
    <div class="clear"></div>
   	<%-- 分页 --%>
	<jsp:include page="../../common/page.jsp" >
		<jsp:param name="urlAction" value="elec/listEcCourse.action" />
		<jsp:param name="page" value="page" />
		<jsp:param name="params"  value="ecCond.termId:${ecCond.termId}" />
		<jsp:param name="params"  value="ecCond.typeId:${ecCond.typeId}" />
		<jsp:param name="params"  value="ecCond.gradeIds:${ecCond.gradeIds}" />
		<jsp:param name="params"  value="ecCond.gradeNames:${ecCond.gradeNames}" />
		<jsp:param name="params"  value="ecCond.teacherIds:${ecCond.teacherIds}" />
		<jsp:param name="params"  value="ecCond.teacherNames:${ecCond.teacherNames}" />
		<jsp:param name="params"  value="ecCond.name:${ecCond.name}" />
	</jsp:include>
	<%-- 分页 emd --%>
	</c:if>
	<c:if test="${empty ecTerm }">
		<h2 style="font-size:12px; text-align:center;">系统暂未录入兴趣班管理记录，无法操作本应用！</h2>
	</c:if>
</div>
</body>
</html>