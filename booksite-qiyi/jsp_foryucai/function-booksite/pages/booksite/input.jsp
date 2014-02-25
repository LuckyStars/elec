<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/function/function-booksite/pages/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>更新我的预订信息</title>
<link href="${prc }/function/function-booksite/styles/basic.css" rel="stylesheet" type="text/css" />
<script src="${prc }/function/function-booksite/scripts/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="${prc }/function/function-booksite/scripts/backgroundPopup.js" type="text/javascript"></script>
<script type="text/javascript" src="${prc}${dataPicker}"></script>
<script src="${prc }/common/agent.js" type="text/javascript"></script>
<script type="text/javascript">
	function submitSiteForm(){
	    if($.trim($("#beginDate").val())=="" || $.trim($("#endDate").val())==""){
	        alert('请选择时间。');
	        return false ;
	    }

	    if($.trim($("#explain").val())==""){
        	alert('请输入说明。');
            return false ;
    	}
	    if($.trim($("#oldBeginTime").val())==$.trim($("#beginDate").val()) && $.trim($("#oldEndTime").val())==$.trim($("#endDate").val())){
		}
    	$.post('${prc}/bookSite/updatemySite.action',		
     			$("#editeven").serialize(),
     			//{"siteId":$('#site_id').val(),"beginTime":$.trim($("#beginDate").val()),"endTime":$.trim($("#endDate").val())},
				function(data){
					if(data=="already"){
						alert('您选择的时间段已经有人预定了。');
				    	return false;
					}else{
						window.location.href="${prc}/bookSite/searchBookSite.action";
					}
			});
	}
</script>
</head>

<body>
<div style="color:red">
    <s:fielderror />
</div>
<div class="box">
	<div class="box_head">
    	<div class="left">
       <b><a href="${prc }/bookSite/indexUser.action">场馆预订</a></b>
        <font size="2" color="#336699">&raquo</font><a href="${prc}/bookSite/searchBookSite.action">我的预订</a>
        </div>  
    </div>
    <div class="box_main">
    	<div class="box_main_top">
        </div>
        <div class="box_main_center">
        <s:form method="post" id="editeven" namespace="/bookSite">
        	<input type="hidden" name="bookSite.bookSite_id" value="${bookSite.bookSite_id}" />
        	<s:hidden id="site_id" name="bookSite.site.site_id"></s:hidden>
        	<table id="table3" width="600" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="100" height="30" align="right"><b>场馆名称：</b></td>
                <td width="20">&nbsp;</td>
                <td width="530" align="left">
                	${bookSite.site.site_name}
                </td>
              </tr>
              <tr>
                <td height="30" align="right"><b>时间：</b></td>
                <td>&nbsp;</td>
                <td align="left">
                	<input type="hidden" id="oldBeginTime" name="oldBeginTime" value="<fmt:formatDate value="${bookSite.bookSite_beginTime}" pattern="yyyy-MM-dd HH:mm"/>" />
                	<input type="hidden" id="oldEndTime" name="oldEndTime" value="<fmt:formatDate value="${bookSite.bookSite_endTime}" pattern="yyyy-MM-dd HH:mm"/>" />
                	<input type="text" class="Wdate" name="bookSite.bookSite_beginTime" value="<fmt:formatDate value="${bookSite.bookSite_beginTime}" pattern="yyyy-MM-dd HH:mm"/>" id="beginDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endDate\');}'})"/> 到
		         	<input type="text" class="Wdate" name="bookSite.bookSite_endTime" value="<fmt:formatDate value="${bookSite.bookSite_endTime}" pattern="yyyy-MM-dd HH:mm"/>" id="endDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'beginDate\');}'})"/>
                </td>
              </tr>
             
              <tr>
                <td height="30" align="right"><b>级别：</b></td>
                <td>&nbsp;</td>
                <td align="left">
					<select name="bookSite.activityLevel.activityLevel_id">
                		<s:iterator value="listActivityLevel">
	                		<option value="${activityLevel_id}" <c:if test="${bookSite.activityLevel.activityLevel_id eq  activityLevel_id}">selected="selected"</c:if> >${activityLevel_name}</option>
	                	</s:iterator>
                	</select>
                </td>
              </tr>
              <tr>
                <td height="30" align="right"><b>类型：</b></td>
                <td>&nbsp;</td>
                <td align="left">
                	<select name="bookSite.activityType.activityType_id">
                		<s:iterator value="listActivityType">
	                		<option value="${activityType_id}" <c:if test="${bookSite.activityType.activityType_id eq  activityType_id}">selected="selected"</c:if> >${activityType_name}</option>
	                	</s:iterator>
                	</select>
                </td>
              </tr>
              <tr>
                <td align="right" valign="top"><b>说明：</b></td>
                <td>&nbsp;</td>
                <td valign="top" align="left">
					<s:textarea id="explain" cols="36" rows="5" name="bookSite.bookSite_explain"></s:textarea>	 <span id="explainnote">必填</span>
				</td>
              </tr>
               <tr>
                <td height="30">&nbsp;</td>
                <td>&nbsp;</td>
                <td align="left">
                	<input type="button" value="修改" class="btn0" onclick="javascript:submitSiteForm();" />
                	<input type="button" class="btn0" onclick="history.back(-1);" value="返 回"/>
                </td>
              </tr>
               <tr>
                <td height="30">&nbsp;</td>
                <td>&nbsp;</td>
                <td align="left"></td>
              </tr>
            </table>
           </s:form> 
        </div>
    </div>
</div>
</body>
</html>
