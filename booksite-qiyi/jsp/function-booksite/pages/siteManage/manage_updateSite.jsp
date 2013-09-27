<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>       
<%@ include file="/function/function-booksite/pages/common.jsp"%>
<script type="text/javascript">
<!--
	$(function(){
		$('#userId').combotree({   
		     url:'${prc}/bookSite/treeUser.action',
		     multiple:true,
		     cascadeCheck:true,
		     onLoadSuccess:function(){
		    		var node = $('#userId').combotree('tree').tree('getSelected');
					if (node){
						$('#userId').combotree('tree').tree('collapseAll', node.target);
					} else {
						$('#userId').combotree('tree').tree('collapseAll');
					}
			 }
		});
		
		$("#userId").combotree('setValues', [${userIdArray}]);
		
		
	});
//-->
</script>
<script type="text/javascript">
$(document).ready(function(){
	$('#editen').submit(function(){
		var epicture = $('#epicture').val();
		var ename = $('#ename').val();
		var earea = $('#earea').val();
		var ecapacity = $('#ecapacity').val();
		var eaddress = $('#eaddress').val();
		var eother = $('#eother').val();
		//var siteTypeEdit = $('#siteTypeEdit').val();
		var userIdTest = $('#siteTypeEdit').val();
		var check1 = $('input[name=activityLevel_id]:checked').val();
		var check2 = $('input[name=activityType_id]:checked').val();
		
		<%-- 
		if(epicture == '' )
		{
			$('#epicturenote').html('<font color=red>输入有误</font>');
			return false;
	}else{
				$('#epicturenote').html('<font color=green>输入正确</font>');
		} --%>

		if(ename == '' || ename.length > 20 )
		{
			$('#enamenote').html('<font color=red>不能为空或超过20个字符</font>');
			return false;
		}else{
				$('#enamenote').html('<font color=green>输入正确</font>');
		}

		if(earea.length > 15 )
		{
			$('#eareanote').html('<font color=red>不能为空或超过15个字符</font>');
			return false;
		}else{
				$('#eareanote').html('<font color=green>输入正确</font>');
		}

		if( ecapacity.length > 15 )
		{
			$('#ecapacitynote').html('<font color=red>不能为空或超过15个字符</font>');
			return false;
		}else{
				$('#ecapacitynote').html('<font color=green>输入正确</font>');
		}

		if( eaddress.length > 15 )
		{
			$('#eaddressnote').html('<font color=red>不能为空或超过15个字符</font>');
			return false;
		}else{
				$('#eaddressnote').html('<font color=green>输入正确</font>');
		}

		if(eother.length > 15 )
		{
			$('#eothernote').html('<font color=red>不能为空或超过15个字符</font>');
			return false;
		}else{
				$('#eothernote').html('<font color=green>输入正确</font>');
		}

		if(check1 == undefined)
		{
			$('#check1update').html('<font color=red>至少选择一个</font>');
			return false;
		}else{
				$('#check1update').html('<font color=green>输入正确</font>');
		}
		if(check2 == undefined){
			$('#check2update').html('<font color=red>至少选择一个</font>');
			return false;
		}else{
				$('#check2update').html('<font color=green>输入正确</font>');
		}
		/* if(siteTypeEdit == undefined){
			$('.siteTypeError').html('<font color=red>至少选择一个</font>');
			return false;
		}else{
			$('.siteTypeError').html('<font color=green>输入正确</font>');
		}
		if(userId == undefined){
			$('.userIdError').html('<font color=red>至少选择一个</font>');
			return false;
		}else{
			$('.userIdError').html('<font color=green>输入正确</font>');
		} */
		var nodes = $('#userId').combotree('tree').tree('getChecked');
		var userIdArrayEdit='';
		for(var i=0; i<nodes.length; i++){
			if(nodes[i].id.indexOf('ne|') == -1 && nodes[i].id.indexOf('n|') == -1){   // 判断是否是部门
				userIdArrayEdit += nodes[i].id;
				if(i!=nodes.length-1){
					userIdArrayEdit +=',';
				}
			}
		}
		
		if(userIdArray==''){
			return false;
		}else{
			$("#userIdArrayEdit").val(userIdArrayEdit);
		}
		

	});
});
</script>


<table id="table2" width="440" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="120" height="30" align="right">图标：</td>
            <td width="12" height="30">&nbsp;</td>
            <td height="30">
            	<img alt="" width='195' src="${prc }/function/function-booksite/uploadPic/<s:property value="site.site_editorPicName"/>">
            	<s:file name="picture" id="epicture"  ></s:file><span id="epicturenote">必填</span>
            	<s:hidden name="site.site_editorPicName"></s:hidden>
            </td>
          </tr>
          <tr>
            <td height="30" align="right">场馆负责人：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<input name="userId" id="userId" class="easyui-combotree"
            	 style="border: 1px solid #ccc;height:18px;" width="150px;"/>
            	 <span class="userIdError">必填</span>
            </td>
          </tr>
          <tr>
            <td height="30" align="right">场馆类型：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<select  name="siteTypeEdit" id="siteTypeEdit">
            		<option value="1" <s:if test='site.siteType =="1"'>selected="selected"</s:if> >电教</option>
            		<option value="2"  <s:if test='site.siteType =="2"'>selected="selected"</s:if> >行政</option>
            	</select><span class="sitetypeError">必填</span>
            </td>
          </tr>
          <tr>
            <td height="30" align="right">场馆名称：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<s:textfield name="site.site_name" id="ename"></s:textfield> <span id="enamenote">必填</span>
            </td>
          </tr>
          <tr>
            <td height="30" align="right">场馆面积：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<s:textfield name="site.site_area" id="earea"></s:textfield>  <span id="eareanote"></span>   
            </td>
          </tr>
          <tr>
            <td height="30" align="right">场馆容量：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<s:textfield name="site.site_capacity" id="ecapacity"></s:textfield> <span id="ecapacitynote"></span>   
            </td>
          </tr>
          <tr>
            <td height="30" align="right">地址：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<s:textfield name="site.site_address" id="eaddress"></s:textfield> <span id="eaddressnote"></span>
            </td>
          </tr>
          <tr>
            <td height="30" align="right">场馆说明：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<s:textfield name="site.site_other" id="eother"></s:textfield>  <span id="eothernote"></span>    
            </td>
          </tr>
           <tr>
            <td height="30" align="right" valign="top">可承办活动级别：</td>
            <td height="30">&nbsp;</td>
            <td height="30" valign="top">
            <ul>
            	<s:checkboxlist name="activityLevel_id" value="#request.level" list="listActivityLevel" listKey="activityLevel_id" listValue="activityLevel_name" ></s:checkboxlist><span id="check1update">
            </ul>
            </td>
          </tr>
          <tr>
            <td height="30" align="right" valign="top">可承办的活动类型：</td>
            <td>&nbsp;</td>
            <td valign="top">
            
            <ul>
            	<s:checkboxlist name="activityType_id" value="#request.type" list="listActivityType" listKey="activityType_id" listValue="activityType_name" ></s:checkboxlist></span><span id="check2update"></span>		
            
            </ul>
            </td>
          </tr>
          <tr>
            <td height="30" align="right" valign="top">场馆状态：</td>
            <td>&nbsp;</td>
            <td valign="top">
            <ul>
            	<s:action name="retrieveByIdSite" namespace="/bookSite" id="siteBean" />     
				<s:select list="listSiteStatus" name="siteStatus.siteStatus_id" listKey="siteStatus_id" listValue="siteStatus_name"></s:select>

            </ul>
            </td>
          </tr>
          <s:hidden name="site.site_id"></s:hidden>
    </table>
 