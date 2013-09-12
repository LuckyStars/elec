<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<style type="text/css">
	.page_css{width:100%; border-bottom:#a2b1c3 solid 1px;}
	.page_css td {font-size: 13px;height: 38px;line-height: 10px;}
	.page_css td a {color: #1E5388; height: 38px;line-height: 38px; padding-bottom: 0;padding-left: 4px;padding-right: 4px;padding-top: 0;}
	.page_css td a.page_btn { background: url("${med}/images/btn_xb1.gif") repeat-x scroll 0 0 transparent; border: 1px solid #889DBE;
	    color: #133891; height: 38px; line-height: 20px; margin: 5px 5px 0 0; padding: 0 8px; }
	.alt {height:38px;margin:0px 8px 0px 0px;padding:0 5px;line-height:18px;border:#889dbe solid 1px;background:url(${med}/images/btn_xb1.gif) repeat-x; color:#133891}
</style>
<pg:pager id="${param.page}" url="${prc}/${param.urlAction}" maxPageItems="${page.pageSize}" items="${page.count}" export="currentPageNumber=pageNumber">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="page_css">
<tr>
<td><span style="float: left;">
	<c:if test="${!empty paramValues.params}">
	 	<c:forEach items="${paramValues.params}" var="p">
			<pg:param name="${fn:split(p,':')[0]}" value="${fn:split(p,':')[1]}"/>
		</c:forEach>
	</c:if>
	<pg:first>
		<a href="${pageUrl}" class="page_btn" >首页</a>
	</pg:first>
	<pg:prev>
		<a href="${pageUrl}" class="page_btn" >&lt;上一页</a>
	</pg:prev>
	<pg:pages>
		<s:if test="#attr.currentPageNumber==#attr.pageNumber">
			<%--当前页特殊处理 --%>
			<span>[${pageNumber}]</span>
		</s:if>
		<s:else>
			<a href="${pageUrl}">${pageNumber}</a>
		</s:else>
	</pg:pages>
	
	<pg:next>
		<a href="${pageUrl}" class="page_btn" >下一页&gt;</a>
	</pg:next>
	<pg:last>
		<a href="${pageUrl}" class="page_btn" >尾页</a>
	</pg:last>
	<pg:pages></pg:pages>
	</span>
</td>
<td ><span style="float: right;"><c:if test="${page.count==0}">
共${page.totalPages-1}页
</c:if>   
<c:if test="${page.count!=0}">
共${page.totalPages}页 
</c:if>|共${page.count}条
<pg:page>
第<input type="text" size="2"  style="font-size: 12px;font-family:'宋体'; height:12px;" id="jumpPage" value="${attr.currentPageNumber}"  >页<a href="javascript:void(0);"  onclick="jump('${pageUrl}')" class="alt" >GO</a>
</pg:page>
<!--<c:if test="${page.totalPages==1}">
共${page.totalPages-1}页  0/${page.totalPages-1}页
</c:if>   
<c:if test="${page.totalPages!=1}">
共${page.totalPages}页 ${attr.currentPageNumber}/${page.totalPages}页
</c:if>  
-->
</span></td>
</tr>
</table>
<script type="text/javascript" ><!--
function jump(url){
	var re = /^\d+$/g;
	var pageNo = $("#jumpPage").val(); 
	if(!re.test(pageNo)){
		alert("请输入正整数");
		$("#jumpPage").val("");
		return;
	}	
	if(pageNo.length>1){
		if(pageNo.substring(0,1)==0){
			pageNo = pageNo.substring(1,pageNo.length);
		}
	}
	if(pageNo==""){
		alert("请填写跳转页数");
		$("#jumpPage").val("");
		return;
	}
	if(parseInt(pageNo)<=0||parseInt(pageNo)>parseInt('${page.totalPages}')){
		alert("请输入正确跳转的页码数");
		$("#jumpPage").val("");
		return;
	};
	pageNo = (parseInt(pageNo)-1)*parseInt('${page.pageSize}');
	location.href=changeURLArg(url,"page.offset",pageNo);
	
}
//截取取值的test替换page.size
function changeURLArg(url,arg,arg_val){
	var pattern=arg+'=([^&]*)';
	var replaceText=arg+'='+arg_val;
	if(url.match(pattern)){
		var tmp='/('+ arg+'=)([^&]*)/gi';
		tmp=url.replace(eval(tmp),replaceText);
		return tmp;
	}else{
		if(url.match('[\?]')){
			return url+'&'+replaceText;
		}else{
			return url+'?'+replaceText;
		}
	}
	return url+'\n'+arg+'\n'+arg_val;
}
</script>
</pg:pager>
