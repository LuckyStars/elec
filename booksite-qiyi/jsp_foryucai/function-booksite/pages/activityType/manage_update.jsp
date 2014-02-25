<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>       
	<table id="table1" width="440" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100" height="30" align="right">类型名称：</td>
            <td width="12" height="30">&nbsp;</td>
            <td height="30">
            	<s:textfield name="activityType.activityType_name" id="upname"></s:textfield> <span id="upnamenote">必填并且长度不能超过20.</span>
				<input type="hidden" name="activityType.activityType_id" value="${activityType.activityType_id }"/>
            </td>
          </tr>
          <tr>  
            <td width="100" height="30" align="right">状态：</td>
            <td width="12" height="30">&nbsp;</td>
            <td height="30">
            	<s:action name="retrieveByIdActivityType" namespace="/bookSite" id="typeBean" />     
				<s:select list="listSiteStatus" name="siteStatus.siteStatus_id" value="siteStatus.siteStatus_id" listKey="siteStatus_id" listValue="siteStatus_name"></s:select>
            </td>
          </tr>
          <tr>  
            <td width="100" height="30" align="right">等级：</td>
            <td width="12" height="30">&nbsp;</td>
            <td height="30">
            	<s:textfield id="addsort" name="activityType.activityType_sort"></s:textfield> <span id="addsortnote">必填并且只能是数字.</span>
            </td>
          </tr>
</table>
<script type="text/javascript">
$(document).ready(function(){
	$('#updatetype').submit(function(){
		var actn = $('#upname').val();
		var upname = $.trim(actn);
		if(upname == '' || upname.length > 20 ){
			$('#upnamenote').html('<font color=red>输入有误</font>');
			return false;
		}else{
			$('#upnamenote').html('<font color=green>输入正确</font>');
		}
	});
});
</script>