<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>

<div class="edit_course">
	<ul>
		<li>
			<span><fmt:message key="i18nTermName" bundle="${bundler}" />名称：</span>
			<input name="term.name" value="${term.name }" type="text" />
			<font id="nameErr" style="color: red;"></font>
		</li>
		<li>
			<span>开放日期：</span>
			<input name="term.openDateStart" class="Wdate" type="text" id="openDateStart"
			onfocus="WdatePicker({onpicked:function(){openDateEnd.focus();},dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'openDateEnd\');}',isShowClear:false})"
			readonly="readonly"
			value="<fmt:formatDate value="${term.openDateStart}" pattern="yyyy-MM-dd HH:mm:ss"/>"
			 />至 
			<input name="term.openDateEnd" class="Wdate" type="text" id="openDateEnd"
			onfocus="WdatePicker({onpicked:function(){signDateStart.focus();},dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'openDateStart\');}',isShowClear:false})"	
			readonly="readonly"
			value="<fmt:formatDate value="${term.openDateEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
			/>
			<font id="openDateErr" style="color: red;"></font>
		</li>
		<li>
			<span>报名时间：</span>
			<input name="term.signDateStart" class="Wdate" type="text" id="signDateStart"
			onfocus="WdatePicker({onpicked:function(){signDateEnd.focus();},onpicking:checkDate('openDate'),dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'openDateStart\');}',maxDate:'#F{$dp.$D(\'openDateEnd\');}',isShowClear:false})"
			readonly="readonly"
			value="<fmt:formatDate value="${term.signDateStart}" pattern="yyyy-MM-dd HH:mm:ss"/>"
			 />至
			<input name="term.signDateEnd" class="Wdate" type="text" id="signDateEnd"
			onfocus="WdatePicker({onpicking:checkDate('openDate'),dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'openDateEnd\');}',minDate:'#F{$dp.$D(\'signDateStart\');}',isShowClear:false})"
			readonly="readonly"
			value="<fmt:formatDate value="${term.signDateEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
			/>
			<font id="signDateErr" style="color: red;"></font>
		</li>
		<li>
			<span>最大选课数：</span>
			<select name="term.maxCourse">
			<c:forEach begin="0" end="7" step="1" varStatus="i">
					<option value="${i.index }"
						<c:if test="${i.index==term.maxCourse}">
						selected="selected"
						</c:if>
						><c:choose>
							<c:when test="${i.index==0}">不限</c:when>
							<c:otherwise>${i.index }</c:otherwise>
						</c:choose>
						</option>
				</c:forEach>
		</select>&nbsp;课程/人</li>
		<%--
		<li>
			<span>默认开课日期：</span>
			<input name="term.lessonDateStart" class="Wdate" type="text" id="lessonDateStart"
			onfocus="WdatePicker({onpicked:function(){lessonDateEnd.focus();},onpicking:checkDate('signDate'),dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'signDateEnd\');}',maxDate:'#F{$dp.$D(\'lessonDateEnd\');}',isShowClear:false})"
			readonly="readonly"
			value="<fmt:formatDate value="${term.lessonDateStart}" pattern="yyyy-MM-dd HH:mm:ss"/>"
			/>
		</li>
		<li>
			<span>默认结课日期：</span>
			<input name="term.lessonDateEnd" class="Wdate" type="text" id="lessonDateEnd"
			onfocus="WdatePicker({onpicked:function(){classTimeStart.focus();},onpicking:checkDate('signDate'),dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'lessonDateStart\');}',isShowClear:false})"
			readonly="readonly"
			value="<fmt:formatDate value="${term.lessonDateEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
			/>
			<font id="lessonDateErr" style="color: red;"></font>
		</li>
		<li>
			<span>默认上课时间：</span>
			<input name="term.classTimeStart" class="Wdate" type="text" id="classTimeStart"  
			onfocus="WdatePicker({onpicked:function(){classTimeEnd.focus();},dateFmt:'HH:mm:ss',maxDate:'#F{$dp.$D(\'classTimeEnd\');}',isShowClear:false})"
			readonly="readonly"
			value="<fmt:formatDate value="${term.classTimeStart}" pattern="HH:mm:ss"/>"
			/>至
			<input name="term.classTimeEnd" class="Wdate" type="text" id="classTimeEnd"
			onfocus="WdatePicker({dateFmt:'HH:mm:ss',minDate:'#F{$dp.$D(\'classTimeStart\');}',isShowClear:false})"
			readonly="readonly"
			value="<fmt:formatDate value="${term.classTimeEnd}" pattern="HH:mm:ss"/>"
			/>
			<font id="classTimeErr" style="color: red;"></font>
		</li>
		--%>
		<li>
			<span>提示：</span>
			<textarea id="comments" name="term.comments" cols="" rows=""><c:out value="${term.comments}" escapeXml="true"></c:out></textarea>
			<font id="commentsErr" style="color: red;"></font>
		</li>
	</ul>
</div>
<div class="cl"></div>
<div class="button_border">
	<ul>
		<li>
			<input class="button" type="submit" value="确认" title="确认" />
		</li>
		<li>
			<input onclick="location.href='${prc}/elec/listTerm.action'" class="button" type="button" value="取消" title="取消" />
		</li>
	</ul>
</div>