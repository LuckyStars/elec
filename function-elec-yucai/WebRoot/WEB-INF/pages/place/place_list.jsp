<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>地点列表</title>
<link  type="text/css" href="${med}/css/place/xqstyle.css" rel="stylesheet" />
<script type="text/javascript"  src="${med}/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript"  src="${med}/js/place/place.js"></script>
</head>
<body>
	<!--弹出层    修改-->	
	<div class="shade" id="edshade" style="display:none;">
		<form id="updatePlace" name="updatePlace" method="post"  action="updatePlaceplaceAction.action">
	    	<div class="photo_bg fixed">
	        	<h1>
	        		<span>编辑</span>
	        		<a href="javascript:void(0);" id="edcloseOpenDiv"></a>
	        	</h1>
		        <div class="list">
		            <p>
		            	<label class="p_tit">地点名称</label>
		                <input type="text" name="ecPlace.name" id="edplaceName"  size="30"  /> 
		                <span id="ednameSpan"></span>
					</p>
		          	<p>
		            	<label class="p_tit">相关课程：</label>
		            	<c:forEach items="${ecTypelist}" var = "str">
							<input type="checkbox" class="eck2" id="${str.id}"  name="typeName" value="${str.id}" />
							<label >${str.name}</label> 
						</c:forEach>
						<span id="edcategorysSpan"></span>
					</p>
				</div>
		        <div class="clear"></div>
		        <div class="layer_btn">
			        <input type="button" class="button" value="提交"  id="updateForm"  style="cursor: pointer;"/>
		        	<input type="button" class="button" value="返回"  id="edbackOpenDiv" style="cursor: pointer;"/>
		        	<input type = "hidden" name="ecPlace.id" id="ecplaceId" />
		        </div>
	    	</div>
		</form>	
	</div>
<!--弹出层    修改-->		
<!--弹出层    添加-->
	<div class="shade" id="shade" style="display:none;">
			
			<form id="addPlace" name="addPlace" method="post"  action="addPlaceplaceAction.action">
			<div class="photo_bg fixed">
				<h1>
					<span>添加</span>
					<a class="addcloseOpenDiv" href="javascript:void(0);"></a>
				</h1>
				<div class="list">
				<p>
						<label class="p_tit">地点名称：</label> 
						<input type="text" id="placeName" name="ecPlace.name" value="" size="30" maxlength="30" />
						<span id="nameSpan"></span>
					</p>
					<p>
						<label class="p_tit">相关课程：</label> 
						<c:forEach items="${ecTypelist}" var = "str">
							<input type="checkbox" id="${str.id}"  name="typeName" value="${str.id}" />
							<label >${str.name}</label> 
						</c:forEach>
						<span id="categorysSpan"></span>
					</p>
					
				</div>
				<div class="clear"></div>
				<div class="layer_btn">
					<input type="button" class="button" value="提交" style="cursor: pointer;" id="addForm" /> 
					<input type="button" class="button" value="返回" style="cursor: pointer;" id="addcloseOpenDiv" />
				</div>
			</div>
		</form>
	
	</div>
<!--弹出层    添加-->

	<div id="contentId" class="con_conent fixed">
	<h1 class="tit"><span class="title"><fmt:message key="i18nTermTitle" bundle="${bundler}" /></span><a class="fr" href="${prc}/index.jsp">返回</a></h1>
    <h2>
    <span class="sp_place" >地点管理</span>
	<a style="padding:0px 12px 0px 0px;;float: right;margin:13px 25px 0px 10px;display:block;height:21px;text-align:center;line-height:21px;color:#fff;background:url(${med}/images/btn.gif) repeat;" href="javascript:void(0);">
	<span class="spAdd" style=" padding-top:2px;padding-left:30px;line-height:21px;background: url(${med}/images/add.gif) no-repeat 10px 4px;">添加地点</span></a>
    </h2>
	
	<div style="clear: both;height: 5px;"></div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="change_tab">
		<tr>
			<th width="20%" scope="col">地点名称</th>
			<th width="40%" scope="col">所属课程</th>
			<th width="40%" scope="col">操作</th>
		</tr>
		<c:choose>
		<c:when test="${empty placeList}">
			<tr><td colspan="3">暂无数据</td></tr>
		</c:when>
		<c:otherwise>
		<c:forEach items="${placeList}" var="str">
		<tr>
			<td align="left" >${str.name}</td>
			<td align="left" >${str.typeName}</td>
			<td  >
				<a href="javascript:void(0);" onclick="editPlace('${str.id}');" >编辑</a>
				<c:choose>
					<c:when test="${str.delState eq '0'}">
						<a href="javascript:void(0);"  onclick="openORclose('${str.id}' , 'close');">关闭</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0);" onclick="openORclose('${str.id}' , 'open');");">开启</a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		</c:forEach>
		</c:otherwise>
		</c:choose>
	</table>
	<div class="clear"></div>
	<%--导入分页--%>
	<jsp:include page="../../common/page.jsp" >
	<jsp:param name="urlAction" value="elec/findAllplaceAction.action" />
	<jsp:param name="page" value="page" />
	</jsp:include>
	<%--导入分页--%>
	</div>
</body>
</html>