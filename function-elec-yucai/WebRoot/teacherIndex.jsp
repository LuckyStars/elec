<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>403 - 缺少权限</title>
</head>
<body style="background:#F0F8FC;">
<div>
	<%-- 
		说明：由于门户代码跳转写死了，所以本页面存在，只是为了迎合门户！
	--%>
	<div><h3 style="border-bottom:1px solid #AEC2DB; font-size:12px; color:#336699; padding-bottom:5px;">403 - 缺少权限</h3></div>
	<div style="text-align:center; font-size:12px;">
		<img src="${pageContext.request.contextPath}/images/403.png" alt="权限不够" style="margin:10px 0 20px 0;" /><br/>
		您没有访问该页面的权限，可能由于权限不够 或 会话失效（请重新登录）！<br/>
	</div>
</div>
</body>
</html>