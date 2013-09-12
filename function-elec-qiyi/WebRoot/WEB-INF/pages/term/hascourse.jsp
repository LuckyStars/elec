<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<script>
	alert('当前兴趣班已经有课程,不能进行编辑!');
	location.href='${prc}/elec/listTerm.action';
</script>