<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/function/function-booksite/pages/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>场馆列表</title>
<link href="${prc }/function/function-booksite/styles/basic.css" rel="stylesheet" type="text/css" />
<script src="${prc }/common/agent.js" type="text/javascript"></script>
<script src="${prc }/function/function-booksite/scripts/jquery-1.4.2.js" type="text/javascript"></script>
<script type="text/javascript">
            $(function(){
                $(".img_layer").mouseover(function(e){
                    var xx = $(this).offset().left;
                    var yy = $(this).offset().top;
                    var frameWidth = 785;
                    var autosize = frameWidth - xx;

                    $("#info").html($(this).next().next().html());
                    if(autosize > 220)
                    {
                        $("#info").css("left", xx + 120);
                    }else{
                        $("#info").css("left", xx - 260);
                    }
                    $("#info").css("top", yy);
                    $("#info").show();
                });
                $(".img_layer").mouseout(function(e){
                    $("#info").hide();
                });
            });
</script>
<!--[if IE 6]>
	<script type="text/javascript" src="../function/function-booksite/scripts/DD_belatedPNG.js" ></script> 
	<script type="text/javascript">  
   		DD_belatedPNG.fix('.img_layer');
   	</script>
   	<![endif]-->
</head>

<body>

<div class="box">
	<div class="box_head">
    	<div class="left"><b><a href="${prc }/bookSite/indexUser.action">场馆预订</a></b>
    		<font size="2" color="#336699">&raquo</font><a href="#">场馆列表</a>
    	</div>
        <div class="right"><font size="2" color="#336699">&laquo;</font><a href="${prc }/bookSite/indexUser.action" hidefocus="true">返回上一页</a></div>
    </div>
    <div class="box_main">
    
        <style>
                    #info {
                        padding: 10px;
                        position: absolute;
                        z-index: 99999;
                        width: 240px;
                        border: 1px solid #bdd8eb;
                        background-color: #eef8fa;
						display:none;
                    }
					.tab_info .right{
						width:100px;
						text-align:right;
						vertical-align:top;
					}
					.info{
						display:none;
					}
                </style>
        <div id="info"></div>
        <div class="box_main_center">
        	<ul class="ul_type2">
        	<s:iterator value="pm.datas" id="site">
            	<li >
                	<div class="img" style="width: 116px;height: 116px;" > 
                		<img src="${prc }/function/function-booksite/uploadPic/<s:property value="site_editorPicName"/>" width="116" height="116"/>
                	</div>
                    <div class="img_layer" title="<s:property value="site_name"/>">
                    	<a href="javascript:void(0)"  hidefocus="true">&nbsp;</a>
                    </div>
                    <div class="name">

                    	<div style="width:84px;float:left; white-space:nowrap;overflow:hidden;" title="<s:property value="site_name"/>">
                    		<s:if test="site_name.length()>6">     
								<s:property value="site_name.substring(0,6)+ '...'" />     
							</s:if>     
							<s:else>     
								<s:property value="site_name" />
							</s:else>
                    	</div>
                    	<div style="width:24px;float:right;"><a href="${prc }/bookSite/retrieveSiteAndBookInfoByIdBookSite.action?site.site_id=<s:property value="site_id"/>">预订</a></div>
                    </div>
                    <div class="info">
                                <table class="tab_info">
			                        <tr>
			                            <td class="right">场馆名称：</td>
			                            <td class="left">
											<s:property value="site_name" />     
			                            </td>
			                        </tr>
			                        <tr>
			                            <td class="right">场馆面积：</td>
			                            <td class="left"><s:property value="site_area"/></td>
			                        </tr>
			                        <tr>
			                            <td class="right">场馆容量：</td>
			                            <td class="left"><s:property value="site_capacity"/></td>
			                        </tr>
			                        <tr>
			                            <td class="right">场馆地址：</td>
			                            <td class="left"><s:property value="site_address"/></td>
			                        </tr>
			                        <tr>
			                            <td class="right">可承办活动级别：</td>
			                            <td class="left">
			                            	<s:iterator value="#site.activityLevels">
			                            		<s:property value="activityLevel_name"/>&nbsp; 
			                            	</s:iterator>
			                            </td>
			                        </tr>
			                        <tr>
			                            <td class="right">可承办活动类型： </td>
			                            <td class="left">
			                           		 <s:iterator value="#site.activityTypes">
			                            		<s:property value="activityType_name"/>&nbsp; 
			                           		 </s:iterator>
			                            </td>
			                        </tr>
			                    </table>
                            </div>
                </li>
           </s:iterator>
            </ul>
            <c:if test="${empty pm.datas || fn:length(pm.datas) == 0}">
				<h2 style="font-size:16px; text-align:center; color: blue; position:relative; top:45px ">系统暂未录入场馆记录;或场馆没有开启，无法操作本应用！</h2>
		</c:if>
                  <div class="c"></div>
            <div class="page_nav" id="pagingBars" style="text-align: center">
   				<c:if test="${pm.total > 12}">
					 <pg:pager url="retrieveOpenSite.action" items="${pm.total}" maxPageItems="12" export="currentPageNumber=pageNumber">
						<pg:prev> <span class="page_nav_prev"><a href="${pageUrl}">上一页</a></span></pg:prev>
								<pg:pages>	
									<c:choose>
										<c:when test="${currentPageNumber eq pageNumber}"> <a style="color: red;">${pageNumber}</a> </c:when>
										<c:otherwise> <a href="${pageUrl}" class="first">${pageNumber}</a></c:otherwise>
									</c:choose>	
								</pg:pages>
						<pg:next><span class="page_nav_next"><a href="${pageUrl}">下一页</a></span></pg:next>
					 </pg:pager>
				</c:if>	    
   			</div>
        </div>
    </div>
</div>
</body>
</html>