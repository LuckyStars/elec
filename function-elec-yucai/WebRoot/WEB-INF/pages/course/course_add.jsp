<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../../common/common.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课程添加</title>
<link href="${med }/css/place/xqstyle.css" rel="stylesheet" type="text/css" />
<link href="${med }/css/tree.css" rel="stylesheet" type="text/css" />
<link href="${med }/js/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="${med }/js/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${med }/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${med }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${med }/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${med }/js/kindeditor-4.1.5/kindeditor-min.js" ></script>
<script type="text/javascript" src="${med }/js/kindeditor-4.1.5/lang/zh_CN.js"></script>
<script type="text/javascript" src="${med }/js/verify.js"></script>
<script type="text/javascript" src="${med }/js/course/course_add.js"></script>
<script type="text/javascript" src="${med }/js/common/agent.js" ></script>
<style>.combo{float:left;margin-top:11px;}</style>
<script type="text/javascript"> 
var prc = "${prc}", signType = "${signType}"; 
var i18nGradeNames = '<fmt:message key="i18nGradeNames" bundle="${bundler}" />'; //年级范围
var editorCourseContent;
var editorCourseRequire;
var editorCourseNote;
KindEditor.ready(function(K) {
	editorCourseContent = K.create('textarea[name="ecCond.courseContent"]', {
		resizeType : 0,
		allowFileManager : false,
		allowImageUpload : true,
		uploadJson : '${prc}/elec/uploadEcCommon.action',
		items : [
				'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', 'link', '|', 'insertfile', 'image']
	});
	editorCourseRequire = K.create('textarea[name="ecCond.courseRequire"]', {
		resizeType : 0,
		allowFileManager : false,
		allowImageUpload : false,
		items : [
				'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', 'link']
	});
	editorCourseNote = K.create('textarea[name="ecCond.courseNote"]', {
		resizeType : 0,
		allowFileManager : false,
		allowImageUpload : false,
		items : [
				'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', 'link']
	});
	
});
</script>
</head>
<body style="background:#F0F8FC;">
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

<div class="con_conent fixed" >
	<form action="addEcCourse.action" method="post" id="add_course_form" >
    <h1 class="tit">
    	<span class="title"><fmt:message key="i18nTermTitle" bundle="${bundler}" /></span>
    	<div class="return fr"><a href="listEcCourse.action">返回</a></div>
    </h1>
    <h1><span>新的课程</span></h1>
    <div class="text_box">
        <p>
            <label><fmt:message key="i18nTermName" bundle="${bundler}" />名称：</label>
            &nbsp;${currentTerm.name }
            <input type="hidden" name="ecCond.termId" value="${currentTerm.id }" />
        </p>
        <p>
            <label>课程分类：</label>
			<select class="sel" name="ecCond.typeId" style="margin-left:4px; margin-right:3px; width: 133px;">
				<option value="">--请选择--</option>
				<c:forEach items="${typeList}" var="type" >
				<option title="${type.name}" value="${type.id}">${type.name}</option>
				</c:forEach>
			</select>
			<span class="redTip" id="ecCond_typeId_span">*</span>
        </p>
        <p>
            <label>课程名称：</label>
			<input name="ecCond.name" type="text" style="width:500px;"  maxlength="50"/>
			<span class="redTip" id="ecCond_name_span">*</span>
        </p>
        <p class="chkbox">
            <label style="padding:0;"><%--年级范围--%><fmt:message key="i18nGradeNames" bundle="${bundler}" />：</label>
            <input type="hidden" name="ecCond.gradeIds" />
            <input type="hidden" name="ecCond.gradeNames" />
            
            <c:forEach items="${ucGradeList}" var="ucGrade" >
            <input class="chk" name="ucGrade_ipt" type="checkbox" value="${ucGrade.id }" style="margin:7px 3px 0 10px; " hiddenName="${ucGrade.gradeName }" />
           	<span style="float: left;"> ${ucGrade.gradeName }</span>
			</c:forEach>
           	
           	&nbsp;&nbsp;<span class="redTip" id="ecCond_gradeIds_span">*</span>
        </p>
            <div class="clear"></div>
        <p>
            <label>限定人数：</label>
			<input name="ecCond.maxStudentNum" type="text" maxlength="4" />
			<span class="redTip" id="ecCond_maxStudentNum_span">*</span>
        </p>
        <p>
            <label>任课老师：</label>
            <input type="hidden" name="ecCond.teacherIds" />
			<input name="ecCond.teacherNames" type="text" style="width: 272px;" title="" readonly="readonly" />
			<input type="button" value="添加" id="sel_teacher_a" class="add_course_btn" />
			<span class="redTip" id="ecCond_teacherIds_span">*</span>
        </p>
        <c:if test="${signType=='shijia'}">
        <p>
            <label>是否俱乐部课程：</label>
            <input type="hidden" name="ecCond.clubState" />
			<input type="checkbox" id="isClubState" />
			<span class="redTip" id="ecCond_clubState_span"></span>
        </p>
        </c:if>
        <c:if test="${signType=='yucai'}">
        <p>
            <label>课程属性：</label>
           	<select class="sel" name="ecCond.courseAttr" style="margin-left:4px; margin-right:3px; width: 133px;">
				<option value="">--请选择--</option>
				<c:forEach items="${courseAttrs}" var="courseAttr" >
				<option title="${courseAttr.name}" value="${courseAttr.id}">${courseAttr.name}</option>
				</c:forEach>
			</select>
        </p>
        </c:if>
        <p>
            <label>报名开始：</label>
			<input name="ecCond.signStartDateStr" value="<fmt:formatDate value="${currentTerm.signDateStart}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" class="Wdate" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'#F{$dp.$D(\'d4312\')}', minDate:'<fmt:formatDate value="${currentTerm.openDateStart}" pattern="yyyy-MM-dd HH:mm:ss"/>'})" id="d4311" style="width:135px;" />
            <label>报名结束：</label>
            <input name="ecCond.signEndDateStr" value="<fmt:formatDate value="${currentTerm.signDateEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" class="Wdate" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',  maxDate:'<fmt:formatDate value="${currentTerm.openDateEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>', minDate:'#F{$dp.$D(\'d4311\')}'})" id="d4312" style="width:135px;" />
            <span class="redTip" id="ecCond_signDate_span">*</span>
        </p>
        <p>
            <label>开课日期：</label>
			<input name="ecCond.classStartDateStr" value="<fmt:formatDate value="${currentTerm.lessonDateStart}" pattern="yyyy-MM-dd"/>" type="text" class="Wdate" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'d4314\')}', minDate:'#F{$dp.$D(\'d4312\')}'})" id="d4313" style="width:135px;" />
            <label>结课日期：</label>
            <input name="ecCond.classEndDateStr" value="<fmt:formatDate value="${currentTerm.lessonDateEnd}" pattern="yyyy-MM-dd"/>" type="text" class="Wdate" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'d4313\')}'})" id="d4314" style="width:135px;" />
            <span class="redTip" id="ecCond_classDate_span">*</span>
        </p>
        <c:if test="${signType=='shijia'}">
         <p>
            <label>要求课时：</label>
			<input name="ecCond.classhourRequire" type="text" maxlength="4" /><span>课时/周</span>
			<span class="redTip" id="ecCond_classhourRequire_span">*</span>
        </p>
        </c:if>
		<%--
        <div class="ptextl">
            <span class="fl">--%><%--可选课时--%><%--<fmt:message key="i18nClasshourNum" bundle="${bundler}" />：&nbsp;</span>
            <select class="sel" name="ecCond.classhourNum" style="width:50px;">
            	<option value="1">1</option>
            	<option value="2">2</option>
            	<option value="3">3</option>
            	<option value="4">4</option>
            	<option value="5">5</option>
            	<option value="6">6</option>
            	<option value="7">7</option>
            </select>
            <span>课时/周</span>
            <span class="redTip" id="ecCond_classhourNum_span">*</span>
        </div>
		--%>
        <div class="ptext fixed" id="classHour_div_parent">
        	<c:forEach begin="1" end="7" step="1" varStatus="stats">
            <div class="textform classHour_div" style="padding:3px 0 3px 70px; display: none;" id="classHour_div${stats.index}" >
                <select class="sel" name="ecCond_weekIndexs">
                	<c:forEach items="${weekList}" var="week" >
		            <option value="${week.id }" <c:if test="${week.id==stats.index}">selected="selected"</c:if> >${week.name }</option>
					</c:forEach>
                </select>
                <span>从</span>
                <input class="fl easyui-timespinner" required="required" data-options="min:'06:00',showSeconds:true" name="ecCond_weekStartTimes" value="<fmt:formatDate value="${currentTerm.classTimeStart}" pattern="HH:mm:ss"/>" type="text" size="15" />
                <span>至</span>
                <input class="fl easyui-timespinner" required="required" data-options="min:'06:00',showSeconds:true" name="ecCond_weekEndTimes" value="<fmt:formatDate value="${currentTerm.classTimeEnd}" pattern="HH:mm:ss"/>" type="text" size="15" />
                <span class="redTip ecCond_classHour_span" id="ecCond_classHour_span${stats.index}" ></span>
            </div>
            </c:forEach>
        </div>
        <div style="display: none;" id="real_classHour_div"></div>
        <p>
            <label>上课地点：</label>
			<select class="sel" name="ecCond.startPlaceId">
				<option value="">--请选择--</option>
				<c:forEach items="${placeList}" var="place" >
	            <option value="${place.id }">${place.name }</option>
				</c:forEach>
			</select>
			<span class="redTip" id="ecCond_startPlaceId_span">*</span>
        </p>
		<%--
        <p>
			<label>放学地点：</label>
			<select class="sel" name="ecCond.endPlaceId">
				<option value="">--请选择--</option>
				<c:forEach items="${placeList}" var="place" >
	            <option value="${place.id }">${place.name }</option>
				</c:forEach>
			</select>
			<span class="redTip" id="ecCond_endPlaceId_span"></span>
        </p>
		--%>
        <p style="float:left; clear:both; height: auto; margin-top: 5px;"> <span class="fl">选课提示：</span>
			<textarea class="fl" name="ecCond.courseComment" maxlength="100" style="height:50px;"></textarea><br/>
        </p>
		<p class="pRedTip" style="color:#808080;" id="ecCond_courseComment_span">选课提示，将显示在课程详细页最上方，且以红色字体高亮</p>
		
		<p style="color:#808080; float:left; clear:both;" >
			<a href="javascript:void(0);" id="showClose" style="color:#275A8D;">展开课程内容详情↓</a>
		</p>
		<div style="display: none; float:left;" id="detailContent">
	        <p style="float:left; clear:both; height: auto; margin-top: 0px;"> <span class="fl">课程内容：</span>
				<textarea class="fl" name="ecCond.courseContent" style="width:650px;" ></textarea>
	        </p>
	        
	        <p style="float:left; clear:both; height: auto; margin-top: 10px;"> <span class="fl">学生要求：</span>
				<textarea class="fl" name="ecCond.courseRequire" style="width:650px;"></textarea>
	        </p>
	        
	        <p style="float:left; clear:both; height: auto; margin-top: 10px;"> <span class="fl">备　　注：</span>
				<textarea class="fl" name="ecCond.courseNote" style="width:650px;" ></textarea>
	        </p>
        </div>
        <div class="clear"></div>
    </div>
    <div class="btn">
    	<span class="redTip" style="height:28px; line-height:26px; margin-left:10px; color: #808080;" id="form_submit_waitingTip"></span>
    	<a style="margin-left:250px;" href="javascript:void(0);" id="add_course_submit">提交</a>
    	<a href="javascript:void(0);" id="add_course_cancle">返回</a>
    </div>
    </form>
</div>
</body>
</html>