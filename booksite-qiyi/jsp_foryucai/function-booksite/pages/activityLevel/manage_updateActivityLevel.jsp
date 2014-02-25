<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>       

	<table id="table1" width="440" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100" height="30" align="right">级别名称：</td>
            <td width="12" height="30">&nbsp;</td>
            <td height="30">
            	<s:textfield name="activityLevel.activityLevel_name" id="uplevel"></s:textfield> <span id="uplevelnote">必填并且长度不能超过20.</span>
				<s:hidden name="activityLevel.activityLevel_id"></s:hidden>
				<s:hidden name="activityLevel.activityLevel_sort"></s:hidden>
				<s:hidden name="siteStatus.siteStatus_id"></s:hidden>
            </td>
          </tr>
   </table>
<script type="text/javascript">
$(document).ready(function(){
	$('#updatelevel').submit(function(){
		var actn = $('#uplevel').val();
		var uplevel = $.trim(actn);
		if(uplevel == '' || uplevel.length > 20 )
		{
			$('#uplevelnote').html('<font color=red>输入有误</font>');
			return false;
		}else{
			$('#uplevelnote').html('<font color=green>输入正确</font>');
		}
	});
});
</script>