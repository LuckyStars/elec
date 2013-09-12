<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>500 - 系统内部错误</title>
</head>
<body style="background:#F0F8FC;">
<div>
	<div><h3 style="border-bottom:1px solid #AEC2DB; font-size:12px; color:#336699; padding-bottom:5px;">500 - 系统内部错误</h3></div>
	<div style="text-align:center; font-size:12px;">
		<img src="${pageContext.request.contextPath}/images/500.png" alt="系统内部错误" style="margin:10px 0 20px 0; width:130px;" /><br/>
		500，系统内部错误，<a style="color: #1E5388; text-decoration: none;" href="<c:url value="/"></c:url>">返回首页</a>！<br/>
	</div>
</div>
</body>
</html>