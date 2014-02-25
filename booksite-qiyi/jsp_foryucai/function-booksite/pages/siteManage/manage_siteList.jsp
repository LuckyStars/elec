<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/function/function-booksite/pages/common.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>场馆列表管理</title>
<link href="${prc }/function/function-booksite/styles/basic.css" rel="stylesheet" type="text/css" />
<link href="${prc }/function/function-booksite/styles/pagination.css" rel="stylesheet" type="text/css" />
<script src="${prc }/function/function-booksite/scripts/jquery-1.4.2.js" type="text/javascript"></script>
<script src="${prc }/function/function-booksite/scripts/backgroundPopup.js" type="text/javascript"></script>
<script src="${prc }/common/agent.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${prc}/common/lib/nbc-ui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${prc}/common/lib/nbc-ui/themes/icon.css"/>
<script type="text/javascript" src="${prc}/common/lib/nbc-ui/jquery.easyui.min.js"></script>
<!--[if IE 6]>
	<script type="text/javascript" src="../function/function-booksite/scripts/DD_belatedPNG.js" ></script> 
	<script type="text/javascript">  
   		DD_belatedPNG.fix('.img_layer');
   	</script>
   	<![endif]-->
<script type="text/javascript">
$(document).ready(function(){
	$(".add_changguan").click(function() {
		
		$('#personId').combotree({   
		     url:'${prc}/bookSite/treeUser.action',
		     multiple:true,
		     cascadeCheck:true,
		     onLoadSuccess:function(){
		    		var node = $('#personId').combotree('tree').tree('getSelected');
					if (node){
						$('#personId').combotree('tree').tree('collapseAll', node.target);
					} else {
						$('#personId').combotree('tree').tree('collapseAll');
					}
			 }
		});
		
		centerPopup();
		$(".deluser").show();
		clearCreateWindow();//清空
		$(".popfor1").show();
		sethash(resizePop);
	});
	$('#adden').submit(function(){
		var picture = $('#picture').val();
		var name = $('#name').val();
		var area = $('#area').val();
		var capacity = $('#capacity').val();
		var address = $('#address').val();
		var other = $('#other').val();
		var siteType = $('#siteType').val();
		var personId = $('#personId').val();
		var check1 = $('input[name=activityLevel_id]:checked').val();
		var check2 = $('input[name=activityType_id]:checked').val();

		if(picture == '' ){
			$('#picturenote').html('<font color=red>输入有误</font>');
			return false;
		}else{
				$('#picturenote').html('<font color=green>输入正确</font>');
		}

		if(name == '' || name.length > 20 ){
			$('#namenote').html('<font color=red>不能为空或超过20个字符</font>');
			return false;
		}else{
				$('#namenote').html('<font color=green>输入正确</font>');
		}
		if(area.length > 15 )
		{
			$('#areanote').html('<font color=red>不能为空或超过15个字符</font>');
			return false;
		}else{
				$('#areanote').html('<font color=green>输入正确</font>');
		}
		if(capacity.length > 15 )
		{
			$('#capacitynote').html('<font color=red>不能为空或超过15个字符</font>');
			return false;
		}else{
				$('#capacitynote').html('<font color=green>输入正确</font>');
		}
		if(address.length > 15 )
		{
			$('#addressnote').html('<font color=red>输入有误</font>');
			return false;
		}else{
				$('#capacitynote').html('<font color=green>输入正确</font>');
		}

		if(other.length > 15 )
		{
			$('#othernote').html('<font color=red>不能为空或超过15个字符</font>');
			return false;
		}else{
				$('#othernote').html('<font color=green>输入正确</font>');
		}

		if(check1 == undefined)
		{
			$('#check1note').html('<font color=red>至少选择一个</font>');
			return false;
		}else{
				$('#check1note').html('<font color=green>输入正确</font>');
		}
		if(check2 == undefined)
		{
			$('#check2note').html('<font color=red>至少选择一个</font>');
			return false;
		}else{
				$('#check2note').html('<font color=green>输入正确</font>');
		}
		var nodes = $('.easyui-combotree').combotree('tree').tree('getChecked');
		var userIdArray='';
		for(var i=0; i<nodes.length; i++){
			if(nodes[i].id.indexOf('ne|') == -1 && nodes[i].id.indexOf('n|') == -1){   // 判断是否是部门
				userIdArray += nodes[i].id;
				if(i!=nodes.length-1){
					userIdArray +=',';
				}
			}
		}
		if(userIdArray==''){
			return false;
		}else{
			$("#userIdArray").val(userIdArray);
		}
		<%-- if(siteType == undefined){
			$('.siteTypeError').html('<font color=red>至少选择一个</font>');
			return false;
		}else{
			$('.siteTypeError').html('<font color=green>输入正确</font>');
		}
		if(personId == undefined){
			$('.userIdError').html('<font color=red>至少选择一个</font>');
			return false;
		}else{
			$('.userIdError').html('<font color=green>输入正确</font>');
		} --%>
		
	});
});
function clearCreateWindow(){
	$("input[name='site.site_name']").val("");
	$("input[name='site.site_area']").val("");
	$("input[name='site.site_area']").val("");
	$("input[name='site.site_address']").val("");
	$("input[name='site.site_other']").val("");
	$("input[name='site.site_capacity']").val("");
}

</script>
</head>

<body>
<div class="box">
	<div class="box_head">
    	<div class="left">
        <b><a href="${prc }/bookSite/indexUser.action">场馆预订</a></b>
        <font size="2" color="#336699">&raquo</font><a href="${prc }/bookSite/adminUser.action">管理员管理</a>
        <font size="2" color="#336699">&raquo</font><a href="${prc }/bookSite/retrieveAllBackSite.action">场馆管理</a>
        </div>
        <div class="right"><font size="2" color="#336699">&laquo</font><a href="${prc }/bookSite/adminUser.action" hidefocus="true">返回上一页</a></div>
    </div>
    <div class="box_main">
    	<div class="box_main_top">
        	<ul class="ul_type1">
            	<li><img src="${prc }/function/function-booksite/images/gif-03.gif" align="absmiddle"/><a href="javascript:void(0)" class="add_changguan" hidefocus="true">添加场馆</a></li>
            </ul>
        </div>
        <div class="box_main_center">
        	<ul class="ul_type2">
        		<s:iterator value="pm.datas">
            		<li>
                		<div class="img" style="width: 116px;height: 116px;">
                			<img width="116" height="116" alt="" src="${prc }/function/function-booksite/uploadPic/<s:property value="site_editorPicName"/>">
                		</div>
                    	<div class="img_layer" title="<s:property value="site_name"/>">
                    		<a href="javascript:void(0)"  hidefocus="true">
								
							</a>
                   	 	</div>
                   	 	<div class="name">
                    		<b>
	                    		<s:if test="site_name.length() >6">     
									<s:property value="site_name.substring(0,6)+'...'" />     
								</s:if>
								<s:else>     
									<s:property value="site_name" />
								</s:else>   
                    		</b><br />
                    		
                    		<a id="siteId" edit='<s:property value="site_id"/>' class="a_1 edit_changguan" style="cursor: pointer;">编辑</a><br />
                    		<s:if test='siteStatus.siteStatus_checkId == 2 || siteStatus.siteStatus_checkId == 4'>
                    			<a id="siteId" class="a_4" href="${prc }/bookSite/updateStatusSite.action?site_id=<s:property value='site_id'/>&siteStatus_id=<s:property value='siteStatus.siteStatus_checkId'/>">启用</a>
                    		</s:if>
                    		<s:elseif test='siteStatus.siteStatus_checkId == 1'>
                    			<a id="siteId" class="a_5" href="${prc }/bookSite/updateStatusSite.action?site_id=<s:property value='site_id'/>&siteStatus_id=<s:property value='siteStatus.siteStatus_checkId'/>">停用</a>
                    		</s:elseif>
                    		<a id="siteId" class="a_2" href="${prc }/bookSite/updateStatusSite.action?site_id=<s:property value='site_id'/>&siteStatus_id=3" onclick="javascript:return confirm('是否确认撤销？撤销后此场馆将不再显示！');">撤销</a>
                    	</div>
                	</li>
               </s:iterator>  
            </ul>
            <div class="c"></div>
            <div class="page_nav" id="pagingBars">
   	<c:if test="${pm.total > 12}">
		  <pg:pager url="retrieveAllBackSite.action" items="${pm.total}" maxPageItems="12" export="currentPageNumber=pageNumber">
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
<!--弹出窗口-->
<s:form action="addEntitySite" id="adden" namespace="/bookSite" method="post" name="adden" enctype="multipart/form-data">
<div id="backgroundPopup" class="deluser"></div>
<input type="hidden" name="userIdArray" id="userIdArray"/>
<div class="delete_group popfor1" style="display:none;">
     <div class="dg_congtent">
      <div class="dg_title">添加场馆</div>
      <div class="dg_par">
     	<table id="table2" width="440" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="120" height="30" align="right">图标：</td>
            <td width="12" height="30">&nbsp;</td>
            <td height="30">
            	<s:file name="picture" id="picture" ></s:file> <span id="picturenote">必填</span>
            </td>
          </tr>
          
          <tr>
            <td height="30" align="right">场馆负责人：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<input name="userId" id="personId" class="easyui-combotree" style="border: 1px solid #ccc;height:18px;" width="150px;"/><span id="namenote">必填</span>
            </td>
          </tr>
           
           <tr>
            <td height="30" align="right">场馆类型：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<select  name="siteType" id="siteType">
            		<option value="1">电教</option>
            		<option value="2">行政</option>
            	</select><span id="siteTypenote">必填</span>
            </td>
          </tr>
          
          <tr>
            <td height="30" align="right">场馆名称：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<s:textfield name="site.site_name" id="name"></s:textfield> <span id="namenote">必填</span>
            </td>
          </tr>
          <tr>
            <td height="30" align="right">场馆面积：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<s:textfield name="site.site_area" id="area"></s:textfield>  <span id="areanote"></span>    
            </td>
          </tr>
          <tr>
            <td height="30" align="right">场馆容量：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<s:textfield name="site.site_capacity" id="capacity"></s:textfield> <span id="capacitynote"></span> 
            </td>
          </tr>
          <tr>
            <td height="30" align="right">地址：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<s:textfield name="site.site_address" id="address" ></s:textfield> <span id="addressnote"></span> 
            </td>
          </tr>
          <tr>
            <td height="30" align="right">场馆说明：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<s:textfield name="site.site_other" id="other" ></s:textfield> <span id="othernote"></span>	
            </td>
          </tr>
           <tr>
            <td height="30" align="right" valign="top">可承办活动级别：</td>
            <td height="30">&nbsp;</td>
            <td height="30" valign="top">
            <ul>
            	<s:checkboxlist name="activityLevel_id" value="site.activityLevel.activityLevel_id" list="listActivityLevel" listKey="activityLevel_id" listValue="activityLevel_name" ></s:checkboxlist><span id="check1note"></span>	
            </ul>
            </td>
          </tr>
          <tr>
            <td height="30" align="right" valign="top">可承办的活动类型：</td>
            <td>&nbsp;</td>
            <td valign="top">
            <ul>
            	<s:checkboxlist name="activityType_id" value="site.activityType.activityType_id" list="listActivityType" listKey="activityType_id" listValue="activityType_name" ></s:checkboxlist><span id="check2note"></span>	
            </ul>
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
<!--弹出编辑窗口-->
<s:form action="updateSite" namespace="/bookSite" method="post" id="editen" name="" enctype="multipart/form-data">
<input type="hidden" name="userIdArrayEdit" id="userIdArrayEdit"/>
<div class="delete_group popfor2" style="display:none;">
     <div class="dg_congtent">
      <div class="dg_title">修改场馆</div>
      <div class="dg_par updateit" style="height:380px;overflow: auto;">
     	

      </div>
      <div class="dg_button">
       <input type="submit" value="修改" class="lauch" id="delu" onMouseOver="this.title=this.value;"/>
       <input type="button" value="返回" class="lauch" id="delu8" onMouseOver="this.title=this.value;"/>
      </div>
     </div>
    </div>
</s:form>    
<!--弹出编辑窗口结束-->		
</body>
</html>
