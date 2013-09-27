<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/function/function-booksite/pages/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>管理场馆预订信息详细</title>
<link href="${prc }/function/function-booksite/styles/basic.css" rel="stylesheet" type="text/css" />
<script src="${prc }/common/agent.js" type="text/javascript"></script>
</head>

<body>
<div class="box">
	<div class="box_head">
    	<div class="left">
        <b><a href="${prc }/bookSite/retrieveOpenSite.action">场馆预订</a></b>
        <font size="2" color="#336699">&raquo</font><a href="${prc }/bookSite/retrieveToManageUser.action">管理员管理</a>
        <font size="2" color="#336699">&raquo</font><a href="${prc }/bookSite/retrieveAllBackBookSite.action">预订管理</a>
        <font size="2" color="#336699">&raquo</font><a href="#">预订详情</a>

        </div>
        <div class="right"><font size="2" color="#336699">&laquo</font><a href="${prc }/bookSite/retrieveAllBackBookSite.action" hidefocus="true">返回上一页</a></div>
    </div>
    <div class="box_main">
    	<div class="box_main_top">
        	
        </div>
        <div class="box_main_center">
        <s:form method="post" action="updateBookSite" namespace="/bookSite">
        	<table id="table3" width="600" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="100" height="30" align="right"><b>场馆名：</b></td>
                <td width="20">&nbsp;</td>
                <td width="530" align="left">
                	<s:property value="site.site_name"/>
                	<s:hidden name="site.site_id"></s:hidden>
                </td>
              </tr>
              <tr>
                <td height="30" align="right"><b>时间：</b></td>
                <td>&nbsp;</td>
                <td align="left">
                	
                	<s:date name="bookSite.bookSite_beginTime" format="yyyy-MM-dd HH:mm"/>
                	到
                	<s:date name="bookSite.bookSite_endTime" format="yyyy-MM-dd HH:mm"/>
                </td>
              </tr>
              <tr>
                <td height="30" align="right"><b>预订人：</b></td>
                <td>&nbsp;</td>
                <td align="left">
                	${bookSite.userName}
                	<s:hidden name="user.id"></s:hidden>
                	<s:hidden name="user.nbcid"></s:hidden>
                </td>
              </tr>
              <tr>
                <td height="30" align="right"><b>级别：</b></td>
                <td>&nbsp;</td>
                <td align="left">
                	<s:property value="activityLevel.activityLevel_name"/>
                	<s:hidden name="activityLevel.activityLevel_id"></s:hidden>
                </td>
              </tr>
              <tr>
                <td height="30" align="right"><b>类型：</b></td>
                <td>&nbsp;</td>
                <td align="left">
                	<s:property value="activityType.activityType_name"/>
                	<s:hidden name="activityType.activityType_id"></s:hidden>
                </td>
              </tr>
              <tr>
                <td align="right" valign="top"><b>说明：</b></td>
                <td>&nbsp;</td>
                <td valign="top" align="left">
                	<div style="width:300px;overflow:hidden;word-warp:break-word;word-break:break-all;">
					<s:property value="bookSite.bookSite_explain"/>
					</div>
				</td>
              </tr>
               <tr>
                <td height="30">&nbsp;</td>
                <td>&nbsp;</td>
                <td align="left">
                	<s:hidden name="bookSite.bookSite_id"></s:hidden>
                	<input type="button" class="btn0" onclick="location.href='${prc }/bookSite/retrieveAllBackBookSite.action'" value="返 回"/>
                </td>
              </tr>
               <tr>
                <td height="30">&nbsp;</td>
                <td>&nbsp;</td>
                 <td>&nbsp;</td>
              </tr>
            </table>
           </s:form> 
        </div>
    </div>
</div>
</body>
</html>
