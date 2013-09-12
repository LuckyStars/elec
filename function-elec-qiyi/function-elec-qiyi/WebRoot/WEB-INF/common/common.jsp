<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.nbcedu.function.elec.util.Constants" %>
<%-- JSP context --%>
<c:set var="prc" value="${pageContext.request.contextPath}"/>
<%-- media,js,pic context --%>
<c:set var="med" value="${pageContext.request.contextPath}"/>
<%-- resource --%>
<fmt:setBundle var="bundler" basename="message.${signType}" />
<%-- currentUser --%>
<c:set var="curUser" value="${sessionScope.elec_init}"/>