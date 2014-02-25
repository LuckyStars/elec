<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/function/function-booksite/pages/common.jsp"%>     
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>待处理任务</title>
<link href="${prc }/function/function-booksite/styles/basic.css" rel="stylesheet" type="text/css" />
<link href="${prc}/function/function-booksite/styles/pagination.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${prc}${dataPicker}"></script>
<script src="${prc }/function/function-booksite/scripts/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="${prc }/function/function-booksite/scripts/backgroundPopup.js" type="text/javascript"></script>
<script src="${prc }/function/function-booksite/scripts/select.js" type="text/javascript"></script>
<script src="${prc }/common/agent.js" type="text/javascript"></script>
<script type="text/javascript">
  $(".combo-arrow").live("click",function(){
	  sethash();
  });
function cancelBook(id,state){
	var msg="";
	if(state==1){
		msg = '您确认通过该预定';
	}else{
		msg = '您确认退回该预定';
	}
	if(confirm(msg)){
		location.href="${prc}/bookSite/updateAudit.action?id="+id+"&state="+state;
	}
}
</script>
</head>

<body>
<div class="box">
	<div class="box_head">
    	<div class="left">
        <b><a href="${prc }/bookSite/indexUser.action">场馆预订</a></b>
        <font size="2" color="#336699">&raquo</font><a href="${prc}/bookSite/searchBookSite.action">待处理任务</a>
        </div>
        <div class="right"><font size="2" color="#336699">&laquo</font><a href="${prc }/bookSite/indexUser.action" hidefocus="true">返回上一页</a></div>
    </div>
    <div class="box_main">
    	<div class="box_main_top">
        </div>
        <div class="box_main_center">
        	<div class="search2">
        	 <s:hidden name="opId" id='selid'></s:hidden>
        <s:form method="post" namespace="/bookSite" action="listPending">	
            <b>选择查询条件：</b>
            <ul id='hideli'>
            	<li id='1' class="li_mr">
            	<input id="beginTime" name="beginTime" value="${beginTime}" class="Wdate" readonly="readonly"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\');}'})"/>
            			到&nbsp;&nbsp;
            		<input id="endTime" name="endTime" value="${endTime}" class="Wdate" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'beginTime\');}'})"/>
            		<input class="btn0" type="submit" value="搜索"/>
            	</li>
            </ul>
         </s:form>   
          </div>
          
        	<div class="table_box">
        	<table id="table5" width="740" bgcolor="#CCCCCC" border="0" cellspacing="1" cellpadding="0">
              <tr class="title">

                <td width="150"><b>场馆名</b></td>
                <td width="260"><b>预订时间</b></td>
                <td width="80"><b>状态</b></td>
                  <td width="80"><b>场馆预订人</b></td>
                <td><b>操作</b></td>
              </tr>
           <s:iterator value="pm.datas" status="sta">   
              <tr bgcolor="#FFFFFF">
                <td>
                	<s:property value="site.site_name"/>
                </td>
                <td>
                	<s:date name="bookSite_beginTime" format="yyyy-MM-dd HH:mm"/>
                		到
                	<s:date name="bookSite_endTime" format="yyyy-MM-dd HH:mm"/>
                </td>
                <td>
                	<s:if test="bookSite_statusId==0">
                		未审核
                	</s:if>
                	<s:elseif test="bookSite_statusId==1">
                		已通过
                	</s:elseif>
                	<s:elseif test="bookSite_statusId==2">
                		已完成
                	</s:elseif>
                	<s:elseif test="bookSite_statusId==3">
                		已退回
                	</s:elseif>
                </td>
                <td>
                	${bookSite_people}
                </td>
                <td>
                	<a class="a_3" href="${prc}/bookSite/detailBookSite.action?id=${bookSite_id}" > 查看 </a>
                	<a class="a_3" href="${prc}/bookSite/detailPending.action?id=${bookSite_id}" >任务处理</a>
                </td>
              </tr>
            </s:iterator> 
            </table>
            </div>
			<div class="c"></div>
             <div class="page_nav" id="pagingBars">
   		  <c:if test="${pm.total > 10}">
		  <pg:pager url="listAudit.action" items="${pm.total}" maxPageItems="10" export="currentPageNumber=pageNumber">
		  <pg:param name="state" value="${state}"/>
		  <pg:param name="beginTime" value="${beginTime}"/>
		  <pg:param name="endTime" value="${endTime}"/>
					<pg:prev> <span class="page_nav_prev"><a href="${pageUrl}">上一页</a></span></pg:prev>
						<ul>
							<pg:pages>	
								<c:choose>
									<c:when test="${currentPageNumber eq pageNumber}"> <li class="page_nav_current">${pageNumber}</li> </c:when>
									<c:otherwise> <li><a href="${pageUrl}" class="first">${pageNumber}</a></li></c:otherwise>
								</c:choose>	
							</pg:pages>
						</ul>
					<pg:next><span class="page_nav_next"><a href="${pageUrl}">下一页</a></span></pg:next>
				 </pg:pager>
	</c:if>	
    </div>
        </div>
    </div>
</div>
</body>

<%@page import="java.util.Date"%>
</html>
