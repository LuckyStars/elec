<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/function/function-booksite/pages/common.jsp"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>我的预订信息</title>
<link href="${prc }/function/function-booksite/styles/basic.css" rel="stylesheet" type="text/css" />
<script src="${prc }/common/agent.js" type="text/javascript"></script>
<script src="${prc }/function/function-booksite/scripts/jquery-1.4.4.min.js" type="text/javascript"></script>
<script type="text/javascript" >
function savebtn(){
	var r = $.trim($("#dutyExplain").val());
	if(r==""){
		alert('不能为空'); return ;
	}
	if(r.length>200){
		alert('内容不能超出200字符'); return ;
	}
	$("#editeven").submit();
}
</script>
</head>

<body>
<div class="box">
	<div class="box_head">
    	<div class="left">
       <b><a href="${prc }/bookSite/indexUser.action">场馆预订</a></b>
        <font size="2" color="#336699">&raquo</font><a href="${prc}/bookSite/searchBookSite.action">我的预订</a>
        </div>
        <div class="right"><font size="2" color="#336699">&laquo</font><a href="${prc }/bookSite/indexUser.action" hidefocus="true">返回上一页</a></div>
    </div>
    <div class="box_main">
    	<div class="box_main_top">
        	
        </div>
        <div class="box_main_center">
        <s:form method="post" id="editeven" action="updatePending" namespace="/bookSite">
        <input name="id" type="hidden" value="<s:property value="bookSite.bookSite_id"/>" />
        	<table id="table3" width="600" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="100" height="30" align="right"><b>场馆名：</b></td>
                <td width="20">&nbsp;</td>
                <td width="530" align="left">
                	<s:property value="bookSite.site.site_name"/>
                </td>
              </tr>
              <tr>
                <td height="30" align="right"><b>时间：</b></td>
                <td>&nbsp;</td>
                <td align="left">
                	<s:date name="bookSite.bookSite_beginTime" format="yyyy-MM-dd HH:mm:ss"/>
                	到
                	<s:date name="bookSite.bookSite_endTime" format="yyyy-MM-dd HH:mm:ss"/>
                </td>
              </tr>
              <tr>
                <td height="30" align="right"><b>预订人：</b></td>
                <td>&nbsp;</td>
                <td align="left">
                	${bookSite.userName}
                </td>
              </tr>
              <tr>
                <td height="30" align="right"><b>级别：</b></td>
                <td>&nbsp;</td>
                <td align="left">
                	${bookSite.activityLevel.activityLevel_name}
                </td>
              </tr>
              <tr>
                <td height="30" align="right"><b>类型：</b></td>
                <td>&nbsp;</td>
                <td align="left">
                	${bookSite.activityType.activityType_name}
                </td>
              </tr>
              <tr>
                <td align="right" valign="top"><b>说明：</b></td>
                <td>&nbsp;</td>
                <td valign="top" align="left">
                	  <div style="width:300px;overflow:hidden;word-warp:break-word;word-break:break-all;">
                	${bookSite.bookSite_explain}
                		</div>
				</td>
              </tr>
              <tr>
                <td align="right" valign="top"><b>场馆处理情况：</b></td>
                <td>&nbsp;</td>
                <td valign="top" align="left">
                	<div style="width:300px;overflow:hidden;word-warp:break-word;word-break:break-all;">
                		<textarea rows="5" cols="20" name="dutyExplain" id="dutyExplain"></textarea>
                	</div>
				</td>
              </tr>
              
               <tr>
                <td height="30">&nbsp;</td>
                <td>&nbsp;</td>
                <td align="left">
                	<input type="button" class="btn0" onclick="savebtn()" value="保存"/>
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
