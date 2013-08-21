<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<script>
	alert('当前课程已开放,不能更改状态');
	location.href='${prc}/elec/listTerm.action';
</script>