<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../common/common.jsp"%>
<title>权限管理</title>
<link href="${med}/css/xqstyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${med}/js/jquery-1.8.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="${med}/js/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${med}/js/easyui/themes/icon.css"/>
<script type="text/javascript" src="${med}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${med}/js/privilege/userPrivilege.js"></script>
<script type="text/javascript" src="${med}/js/common/agent.js" ></script>
</head>
<body style="background:#f0f8fc">
<!--弹出层   update-->
<div id="shade" class="shade" style="width: 100%; height: 931px;display:none" >
<form id="updateRole" action="${prc }/elec/ecuserUpdatePgAction.action" method="post" >
    <div class="photo_bg fixed" >
        <h1><span>修改</span><a onclick="closes()" href="javascript:"></a></h1>
        <div class="list">
            <p style="height: 15px;" >
                <span style="margin-left: 60px" >老师：
                 <input type="text" name="ecUserPrivilege.userName" id="userName" disabled="disabled" value="" size="30" /></span>
                 <input  type="hidden" name="ecUserPrivilege.id" value="" id="userid"  />
                 <input  type="hidden" name="ecUserPrivilege.privileges" value="" id="roleId"  />
                 <input name="userName" type="hidden" value="${userName }" />
                  <input type="hidden" name="page.offset" value="${page.offset}"  />
            </p>
             <p style="height: 15px;" >
                <span style="margin-left: 60px" >权限：
				<c:forEach var="p" items="${privileges}" >
				<input name="cb" id="${p.id }" type="checkbox" value="${p.id }" />${p.name }
				</c:forEach>
				</span>
             </p>
        </div>
        <div class="layer_btn"><a onclick="sureUpdate()" href="javascript:">提交</a><a onclick="closes()" href="javascript:">返回</a></div>
    </div>
    </form>
</div>

<!-- 树弹出空间 -->
<div class="delete_group popfor1">
<div class="dg_congtent">
<div class="dg_title">组织结构</div>
<div class="dg_par">
<div style="overflow:auto;height:290px;background:#fff;">
<ul style="background:#fff;" id="allTeacherTree"></ul>
</div>
</div>

<div style="height: 75px;" class="dg_button">
<span>权限:
<c:forEach var="p" items="${privileges}" >
<input type="checkbox" name="qx" value="${p.id }" />${p.name }
</c:forEach>
</span><br /><br />
<form action="${prc }/elec/ecuserAddPgAction.action" method="post" id="addRole" >
<input type="hidden" value="" id="userRole" name="userRoles" />
<input name="userName" id="un" type="hidden" value="${userName}" />
<input type="hidden" name="page.offset" value="${page.offset}"  />
<input type="button" class="btn3" value="确 定" id="postAdmin" onmouseover="this.title = this.value"/>
<input type="button" class="btn3" value="返 回" id="delu1" onmouseover="this.title = this.value"/>
</form>
</div>
</div>
</div>


<!--弹出层-->
<div class="con_conent fixed">
    <h1 class="tit"><span class="title">校本选课权限管理</span><a class="fr" href="${prc}/elec/index.action">返回</a></h1>
 
    <h2><a class="b_btn" id="selectTeacher" href="javascript:">增加</a><a class="b_btn" onclick="delAll('${userName }','${page.offset}')" href="javascript:">批量删除</a>
    <form action="${prc }/elec/listPgPgAction.action" method="post" id="condForm">
        <span class="spanleft"><label>用户名：</label>
            <input name="userName" type="text" value="${userName }" />
            <a class="s_btn" onclick="condForm.submit()" href="javascript:"></a>
            </span>
    </form>
    </h2>
    <div class="clear"></div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="change_tab">
        <tr>
        	<th  width="10%" ><input name="allchid" id="qxcheckbox" onclick="selectCheckBox(this.checked)"  type="checkbox" value="" />全选</th>
            <th width="23%" scope="col">教师姓名</th>
            <th width="55%" scope="col">权限</th>
            <th width="12%" scope="col">操作</th>
        </tr>
        <c:forEach var="ecu" items="${ecUserPrivilegeVos}" >
        <tr><td> <input name="chid" id="${ecu.ecUserPrivilege.id}" onclick="qxcheck(this.checked)" type="checkbox" value="${ecu.ecUserPrivilege.id}" /></td>
            <td>${ecu.ecUserPrivilege.userName }</td>
            <td><c:forEach var="n" items="${ecu.names}" > ${n} </c:forEach></td>
            <td><a onclick="showUpdate('${ecu.ecUserPrivilege.id}','${ecu.ecUserPrivilege.userName}','${ecu.ecUserPrivilege.privileges }')" href="javascript:">修改</a>
            <form id="del${ecu.ecUserPrivilege.id}" action="${prc }/elec/ecuserDelPgAction.action" method="post"  >
               <input type="hidden" name="id" value="${ecu.ecUserPrivilege.id}"  />
               <input name="userName" type="hidden" value="${userName }" />
               <input type="hidden" name="page.offset" value="${page.offset}"  />
            	<a href="javascript:" onclick="del('del${ecu.ecUserPrivilege.id}')" >删除</a>
            </form>
            </td>
        </tr>
        </c:forEach> 
    </table>
    <div class="clear"></div>
   
    <jsp:include page="../../common/page.jsp" >
	<jsp:param name="urlAction" value="elec/listPgPgAction.action" />
	<jsp:param name="page" value="page" />
	<jsp:param name="params"  value="userName:${userName}" />
	</jsp:include>
	
</div>
</body>
</html>
