<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/function/function-booksite/pages/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>活动类型管理列表</title>
<link href="${prc }/function/function-booksite/style/spagination.css" rel="stylesheet" type="text/css" />
<link href="${prc }/function/function-booksite/styles/basic.css" rel="stylesheet" type="text/css" />
<script src="${prc }/function/function-booksite/scripts/jquery-1.4.2.js" type="text/javascript"></script>
<script src="${prc }/function/function-booksite/scripts/backgroundPopup.js" type="text/javascript"></script>
<script src="${prc }/common/agent.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(".edit_actType").click(function() {
		centerPopup();
		$(".deluser").show();
		$(".popfor2").show();
		var edid = $(this).attr('editType');
		$.ajax( {
			type : "post", // 传送方式,参数
			url : "${prc}/bookSite/retrieveByIdActivityType.action?activityType.activityType_id="+ edid,
			success : function(data) {
				$('.updateit').html(data);
				sethash(resizePop);
			},
			error : function() {
				alert('请求出错');
			}
		});
	});
	$('#addentity').submit(function(){
		$('#addtypenote').html("");
		$('#addlvnote').html("");
		var actn = $('#addtype').val();
		var addtype = $.trim(actn);
		var addsort = parseInt($.trim($('#addsort').val()));
		if(addtype == '' || addtype.length > 20 ){
			$('#addtypenote').html('<font color=red>不能未空或超过20个字符</font>');
			return false;
		}else{
			$('#addtypenote').html('<font color=green>输入正确</font>');
		}
		if(addsort == "" || !isNumber(addsort) || addsort<1 ){
			$('#addlvnote').html('<font color=red>只能输入整数!</font>');
			return false;
		}else{
			$('#addlvnote').html('<font color=green>输入正确</font>');
		}
	});
});
</script>
</head>

<body>
<div class="box">
	<div class="box_head">
    	<div class="left">
	        <b>
	        	<a href="${prc }/bookSite/indexUser.action">场馆预订</a>
	        </b>
	        <font size="2" color="#336699">&raquo</font>
	        <a href="${prc }/bookSite/adminUser.action">管理员管理</a>
	        <font size="2" color="#336699">&raquo</font>
	        <a href="${prc }/bookSite/retrieveAllBackActivityType.action">活动类型管理</a>
        </div>
        <div class="right"><font size="2" color="#336699">&laquo</font><a href="${prc }/bookSite/adminUser.action" hidefocus="true">返回上一页</a></div>
    </div>
    <div class="box_main">
    	<div class="box_main_top"></div>
        <div class="box_main_center">
        	<div class="table_box">
	            <h1><img src="${prc }/function/function-booksite/images/title_img.gif" align="absmiddle"/><b>活动类型管理</b></h1>
	        	<table id="table5" width="740" bgcolor="#CCCCCC" border="0" cellspacing="1" cellpadding="0">
	            	
	            	<tr class="title">
		                <td width="300"><b>类型名称</b></td>
		                <td width="100"><b>等级</b></td>
		                <td width="100"><b>状态</b></td>
		                <td><b>操作</b></td>
	            	</tr>
	            	
	            	<s:iterator value="listActivityType" status="sta">  
	              	<tr bgcolor="#FFFFFF">
		                <td>
		                	<s:property value="activityType_name"/>
		                </td>
		                <td>
		                	<s:property value="activityType_sort"/>等
		                </td>
		                <td>
		                	<s:property value="siteStatus.siteStatus_name"/>
		                </td>
		                <td>
		                	<a id="activityTypeId" editType='<s:property value="activityType_id"/>' style="cursor: pointer;" class="a_1 edit_actType">编辑</a>
		                	<s:if test='siteStatus.siteStatus_checkId == 2 || siteStatus.siteStatus_checkId == 4'>
		                		<a href="${prc }/bookSite/updateTypeStatusActivityType.action?activityType_id=<s:property value='activityType_id'/>&activityTypeStatus_id=<s:property value='siteStatus.siteStatus_checkId'/>" class="a_1">启用</a>
		                	</s:if>
		                	<s:elseif test='siteStatus.siteStatus_checkId == 1'>
		                		<a href="${prc }/bookSite/updateTypeStatusActivityType.action?activityType_id=<s:property value='activityType_id'/>&activityTypeStatus_id=<s:property value='siteStatus.siteStatus_checkId'/>" class="a_1">禁用</a>
		                	</s:elseif>
		                	<s:if test="siteStatus.siteStatus_checkId == 1 || siteStatus.siteStatus_checkId == 2 || siteStatus.siteStatus_checkId == 4">
		                		<a href="${prc }/bookSite/updateTypeStatusActivityType.action?activityType_id=<s:property value='activityType_id'/>&activityTypeStatus_id=3" class="a_1" onclick="javascript: return confirm('是否确认撤销？撤销后此类型将不再显示！');">撤销</a>
		                	</s:if>
		                </td>
	              	</tr>
	            	</s:iterator>
	              
	            </table>
            </div>
			<div class="table_box_bottom"><input type="button" value="添加" class="btn0 add_jibie" /></div>
        </div>
    </div>
</div>

<!--弹出窗口 新建-->
<s:form action="addEntityActivityType" namespace="/bookSite" id="addentity" method="post">
<div id="backgroundPopup" class="deluser"></div>
<div class="delete_group popfor1" style="display:none;">
	<div class="dg_congtent">
    	<div class="dg_title">添加活动类型</div>
      	<div class="dg_par">
	   		<table id="table1" width="440" border="0" cellspacing="0" cellpadding="0">
	          	<tr>
		            <td width="100" height="30" align="right">活动类型名称：</td>
		            <td width="12" height="30">&nbsp;</td>
		            <td height="30">
		            	<s:textfield id="addtype" name="activityType.activityType_name"></s:textfield> <span id="addtypenote">必填并且长度不能超过20.</span>
		            </td>
	          	</tr>
	          	<tr>
		            <td width="100" height="30" align="right">状态：</td>
		            <td width="12" height="30">&nbsp;</td>
		            <td height="30">
		            	<%--删掉了也没事....
		            		<s:action name="retrieveAllBackActivityType" namespace="/bookSite" id="typeBean" />    
						<s:select list="listSiteStatus" name="siteStatus.siteStatus_id" listKey="siteStatus_id" listValue="siteStatus_name"></s:select>--%> 
		            	<select name="siteStatus.siteStatus_id">
			            	<c:if test="${not empty listSiteStatus}">
			            		<c:forEach var="siteStatu" items="${listSiteStatus}" >
			            			<c:if test="${siteStatu.siteStatus_checkId!=3 }">
			            			<option value="${siteStatu.siteStatus_id }">${siteStatu.siteStatus_name }</option>
			            			</c:if>
			            		</c:forEach>
			            	</c:if>
		            	</select>
		            </td>
	          	</tr>
	          	<tr>
		            <td width="100" height="30" align="right">等级：</td>
		            <td width="12" height="30">&nbsp;</td>
		            <td height="30">
		            	<s:textfield id="addsort" name="activityType.activityType_sort"></s:textfield> <span id="addlvnote">必填并且只能是数字.</span>
		            </td>
	          	</tr>
			</table>
      	</div>
		<div class="dg_button">
      		<input type="submit" value="创建" class="lauch" id="delu" onMouseOver="this.title=this.value;"/>
      		<input type="button" value="返回" class="lauch" id="delu1" onMouseOver="this.title=this.value;"/>
      	</div>
	</div>
</div>
</s:form>    
<!--弹出窗口结束-->	

<!--弹出窗口 编辑-->
<s:form action="updateEntityActivityType" id="updatetype" namespace="/bookSite" method="post">
<div class="delete_group popfor2" style="display:none;">
	<div class="dg_congtent">
		<div class="dg_title">编辑活动类型</div>
		<div class="dg_par updateit">
		<%--此处为 activityType/manage_update.jsp --%>
		</div>
		<div class="dg_button">
			<input type="submit" value="提交" class="lauch" id="delu" onMouseOver="this.title=this.value;"/>
			<input type="button" value="返回" class="lauch" id="delu8" onMouseOver="this.title=this.value;"/>
		</div>
	</div>
</div>
</s:form>
<!--弹出窗口结束-->	
</body>
</html>
