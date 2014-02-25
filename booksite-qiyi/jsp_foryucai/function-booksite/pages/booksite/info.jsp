<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/function/function-booksite/pages/common.jsp"%> 
<table id="table3" width="450" border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td width="100" height="30" align="right"><b>场馆名：</b></td>
       <td width="20">&nbsp;</td>
       <td width="330" align="left">
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
</table>
       
    
