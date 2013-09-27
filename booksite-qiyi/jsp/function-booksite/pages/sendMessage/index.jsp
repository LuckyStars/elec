<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/function/function-booksite/pages/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息发送管理</title>
<link href="${prc }/function/function-booksite/styles/basic.css" rel="stylesheet" type="text/css" />
<script src="${prc }/common/agent.js" type="text/javascript"></script>
</head>

<body>
<div class="box">
	<div class="box_head">
    	<div class="left">
    		<b><a href="${prc }/bookSite/indexUser.action">场馆预订</a></b>
        	<font size="2" color="#336699">&raquo</font><a href="${prc }/bookSite/adminUser.action">管理员管理</a>
        	<font size="2" color="#336699">&raquo</font><a href="#">信息发送管理</a>
    	</div>
        <div class="right"><a href="${prc }/bookSite/adminUser.action" hidefocus="true"> << 返回上一页</a></div>  <!-- href="javascript:void(0)" -->
    </div>
    <div class="box_main">
    	<div class="box_main_top">
        </div>
        <div class="box_main_center">
        	<table id="table4" width="600" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="30" align="right"><img src="${prc }/function/function-booksite/images/pencil.png"/></td>
                <td align="left"><b>请选择消息推送机制:</b></td>
              </tr>
              <tr>
                <td height="60">&nbsp;</td>
                <td align="left">
                <ul>
                	<li><input name="" type="checkbox" value="" checked disabled="disabled"/>协同办公系统提示</li>
                    <li><input name="" type="checkbox" value="" disabled="disabled"/>短信提示</li>
                    <li><input name="" type="checkbox" value="" disabled="disabled"/>邮箱提示</li>
                </ul>
                </td>
              </tr>
              <tr>
                <td height="30">&nbsp;</td>
                <td align="left"><input class="btn0" type="submit" value="提交" disabled="disabled"/> <input class="btn0" type="button" onclick="history.back(-1);" value="返回"/></td>
                
              </tr>           
            </table>
        </div>
    </div>
</div>
</body>
</html>