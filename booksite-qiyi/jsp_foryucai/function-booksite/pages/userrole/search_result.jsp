<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/function/function-booksite/pages/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>人员管理增加</title>
<link href="${prc}/function/function-booksite/styles/basic.css" rel="stylesheet" type="text/css" />
<link href="${prc}/function/function-booksite/styles/pagination.css" rel="stylesheet" type="text/css" />
<script src="${prc}/common/agent.js" type="text/javascript"></script>
<script src="${prc}/function/function-booksite/scripts/jquery-1.4.2.js" type="text/javascript"></script>
<script src="${prc}/function/function-booksite/scripts/backgroundPopup.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${prc}/common/lib/nbc-ui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${prc}/common/lib/nbc-ui/themes/icon.css"/>
<script type="text/javascript" src="${prc}/common/lib/nbc-ui/jquery.easyui.min.js"></script>
<script type="text/javascript">
	function addur(){
		$('.easyui-combotree').combotree({   
		     url:'${prc}/bookSite/treeUser.action',
		     multiple:true,
		     cascadeCheck:true,
		     onLoadSuccess:function(){
		    		var node = $('.easyui-combotree').combotree('tree').tree('getSelected');
					if (node){
						$('.easyui-combotree').combotree('tree').tree('collapseAll', node.target);
					} else {
						$('.easyui-combotree').combotree('tree').tree('collapseAll');
					}
			 }
		});
		centerPopup();
		$(".deluser").show();
		$(".popfor1").show();
	}

	function colseur(){
		$(".deluser").hide();
		$(".popfor1").hide();
	}
	
	function submitUR(){
		var roleArray = new Array(); 
		$("#addForm").find("input:checkbox[name=roleid]:checked").each(function(){roleArray.push($(this).val());});
		if(roleArray!=""){
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
			if(userIdArray==""){
				alert('请选择人员。');
				return ;
			}else{
				$("#userIdArray").val(userIdArray);
				$("#roleArray").val(roleArray);
				$("#addForm").submit();
			}
		}else{
			alert('请选择角色。');
		}
	}

	function remove(id){
		if(confirm('您确定要删除吗？')){
			window.location.href="${prc}/bookSite/delUser.action?id="+id;
		}
	}
	
</script>
</head>

<body>
<div class="box">
	<div class="box_head">
    	<div class="left">
        <b><a href="${prc }/bookSite/retrieveOpenSite.action">场馆预订</a></b>
        <font size="2" color="#336699">&raquo;</font><a href="${prc }/bookSite/adminUser.action">管理员管理</a>
        <font size="2" color="#336699">&raquo;</font><a href="${prc }/bookSite/listUser.action">人员权限管理</a>
        </div>
        <div class="right"><font size="2" color="#336699">&laquo;</font><a href="../bookSite/adminUser.action" hidefocus="true">返回上一页</a></div>
    </div>
    <div class="box_main">
    	<div class="box_main_top">
        </div>
        <div class="box_main_center">
        <form action="${prc}/bookSite/searchUser.action" method="post" name="surForm" id="surForm">
        	<div class="search">
        		姓名： <input type="text" name="userName" id="userName" maxlength="20" value="${userName}">
        		  <input class="btn0" type="submit" value="搜索"/>
        		  <input class="btn0" type="button" value="添加" onclick="javascript:addur();" />
        	</div>
        </form>	
        	<div class="table_box">
            <h1><img src="${prc}/function/function-booksite/images/title_img.gif" align="absmiddle"/><b>人员管理</b></h1>
        	<table id="table5" width="740" bgcolor="#CCCCCC" border="0" cellspacing="1" cellpadding="0">
              <tr class="title">
                
                <td width="200"><b>教师名</b></td>
                <td width="324"><b>权限</b></td>
                <td><b>操作</b></td>
              </tr>
             <!--<s:iterator value="listUser" status="sta" var="uName"> 
              	<tr bgcolor="#FFFFFF">
              		<td>
               		 	<s:property value="#uName.name"/>
               		 </td>
               		<s:iterator value="#uName.roles" var="urole"> 
     	 
            		<s:if test='#urole.name.equals("BOOKSITE_ADMIN") || #urole.name.equals("BOOKSITE_COMMON")'>
                	 	<td>
                	 		<s:property value="displayName"/>
                	 	</td>
                	 </s:if>

                	 <s:if test='#urole.name.equals("BOOKSITE_ADMIN")'>
               		 	<td>
               		 		<a href="../bookSite/updateUser.action?user_uid=<s:property value="uid"/>&roleName=BOOKSITE_ADMIN" class="a_1">改为普通教师</a>
               		 	</td>
               		 </s:if>
               		 <s:elseif test='#urole.name.equals("BOOKSITE_COMMON")'>
               		 	<td>
               		 		<a href="../bookSite/updateUser.action?user_uid=<s:property value="uid"/>&roleName=BOOKSITE_COMMON" class="a_1">改为管理员</a>
						</td>
               		 </s:elseif>
               		</s:iterator> 
              	</tr>
              </s:iterator>
            -->
            <c:forEach items="${pm.datas}" var="ur" varStatus="vs"> 
              	<tr bgcolor="#FFFFFF">
              		<td>
               		 	${ur.userName}
               		 </td>
               		 <td>
               		 	<c:forTokens items="${ur.roleArray}" delims="," var="roleId">
               		 		<c:choose>
               		 			<c:when test="${roleId eq 1}">管理员</c:when>
               		 		</c:choose>
               		 	</c:forTokens>
               		 </td>
              		 <td>
              		  	<a href="javascript:remove('${ur.id}')" class="a_2">删除</a>
					 </td>
              	</tr>
              </c:forEach>
            </table>
            </div>
			<div class="c"></div>
            <div class="page_nav" id="pagingBars">
            	<c:if test="${pm.total > 10}">
		          <pg:pager url="listUser.action" items="${pm.total}" maxPageItems="10" export="currentPageNumber=pageNumber">
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
<form action="${prc}/bookSite/addORupdateUser.action" id="addForm" name="addForm" method="post">
<input name="userIdArray" id="userIdArray" type="hidden" />
<input name="roleArray" id="roleArray" type="hidden" />
<div id="backgroundPopup" class="deluser"></div>
<div class="delete_group popfor1" style="display:none;">
     <div class="dg_congtent">
      <div class="dg_title">权限分配</div>
      <div class="dg_par">
     	<table id="table2" width="440" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="30" align="right">人员选择：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<input name="userId" class="easyui-combotree" style="border: 1px;" width="150px;"/>
            </td>
          </tr>
          <tr>
            <td height="30" align="right">角色：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
            	<input type="checkbox" name="roleid" value="1"> 管理员
            </td>
          </tr>
        </table>

      </div>
      <div class="dg_button">
       <input type="button" value="添加" class="lauch" onclick="javascript:submitUR();"/>
       <input type="button" value="返回" class="lauch" onclick="javascript:colseur();"/>
      </div>
     </div>
    </div>
</form>
<!--弹出窗口结束-->	
</body>
</html>
