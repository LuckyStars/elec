<%--搜索页面 --%>
 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<link  type="text/css" href="${med}/css/place/xqstyle.css" rel="stylesheet" />
<script type="text/javascript"  src="${med}/js/headerMaster/headerMaster.js"></script>
<script type="text/javascript" src="${med }/js/common/agent.js" ></script>
 			<c:choose> 
			    	<c:when test="${empty stuList}">
			    		<%--<tr><td colspan="5">暂无数据</td></tr> --%>
			    		<tr>
		     				<td colspan="5" >暂无数据
		     				<a style="font-size: 15px;font-weight: bold;" href="${prc}/elec/findStuByHeaderMasterheaderMaster.action"" >返回学生选课信息</a>
		     				</td>
		     			</tr>
			    	</c:when>
			    	<c:otherwise>
				    	<c:forEach items="${stuList}" var = "str" >
						  <tr>
				            <td>
				            	<s:property value="stuNo"/>
				            	<c:out value="${str.stuNo}"  escapeXml="false"></c:out>
				            </td>
				            <td>
				            	<c:out value="${str.stuName}"  escapeXml="false"></c:out>
				            </td>
				            <td>
				            	<c:out value="${str.sex}"  escapeXml="false"></c:out>
				            </td>
				            <td>
				            	<p><c:out value="${str.parentsPhone}"  escapeXml="false"></c:out></p>
				            </td>
				            <c:if test="${empty apply}">
				            <td>
				            	
				            	<c:choose>
				            		<c:when test="${empty str.mainList}">
				            			没有报名
				            		</c:when>
				            		<c:otherwise>
						            	
						            	<c:forEach items="${str.mainList}" var="temp">
						    				
						    				<a class="bluelink_2" href="${prc}/elec/viewEcCourse.action?id=${temp.id}&flag=4" >${temp.name}</a>

						            	</c:forEach>
						            	
				            		</c:otherwise>
				            	</c:choose>
				            	
				            </td>
				            </c:if>
				        </tr>
					</c:forEach> 
			    </c:otherwise>
		    </c:choose>   