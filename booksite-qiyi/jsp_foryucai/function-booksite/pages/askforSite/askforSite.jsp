<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/function/function-booksite/pages/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>预订场馆表单页</title>
<link href="${prc }/function/function-booksite/styles/basic.css" rel="stylesheet" type="text/css" />
<link href="${prc }/function/function-booksite/styles/fullcalendar.css" rel="stylesheet" type="text/css" />
<script src="${prc }/function/function-booksite/scripts/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src='${prc }/function/function-booksite/scripts/fullcalendar.js' type='text/javascript' charset="utf-8"></script>
<script src="${prc }/function/function-booksite/scripts/backgroundPopup.js" type="text/javascript"></script>
<script src="${prc }/common/agent.js" type="text/javascript"></script>
<script type="text/javascript" src="${prc}${dataPicker}"></script>
<script type='text/javascript'>

	var curSelectDate ;

	$(function() {
		$('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,basicWeek,basicDay'
			},
			editable: false,
			events: "${prc}/bookSite/retrieveAllBookSite.action",
            dayClick: function(date, allDay, jsEvent, view) {
			
				var curDateStr = $.trim(formatDateStr(date).toString());
				var endDateStr = $.trim(formatEndDateStr(date).toString());
				curSelectDate = date;
				
                centerPopup();//打开增加预定窗口
                $(".deluser").show();
                $(".popfor1").show();
                //$("#explain").text('');
                $('#beginDate').val(curDateStr);
				$('#endDate').val(endDateStr);
				$("#explainnote").html('');
				
            },
            eventClick: function(calEvent, jsEvent, view) {
				var id = calEvent.id;
            	$.ajax({
    				type: "post",  
    				url: "${prc}/bookSite/infoBookSite.action?bookSiteId="+ id,  
    				success: function(data){    
    					centerPopup();
    					$('.popfor').show();
    	                $(".deluser").show();
    	                $('.load').children().remove();
    					$('.load').append(data);
    				},
    				error: function(){
    					alert('error');
    				}
    			});
            }

        });

        $('#delu2').click(function(){
			$(this).parent('.dg_button').siblings('.load').children().remove();
			$(".deluser").hide();	
			$(".popfor").hide();
        });

	});
	
	var getHourStr = function(date,hour,minute){
		var result = date.getFullYear();
		result += "-" + (date.getMonth() + 1);
		result += "-" + date.getDate();
		//var mydate = new Date();
		result += " " + hour;//mydate.getHours(); //获取当前小时数(0-23)
		result += ":" + minute;//mydate.getMinutes(); //获取当前分钟数(0-59)
		return result;
	};
	
	function formatDateStr(date){
		return getHourStr(date,"00","00");
	}
	
	function formatEndDateStr(date){
		return getHourStr(date,"23","59");
	}

	function submitSiteForm(){
        if($.trim($("#beginDate").val())=="" || $.trim($("#endDate").val())==""){
            alert('请选择时间。');
            return false ;
        }else{
        	var siteId = $('#site_id').val();
        	var check = $.ajax( {
					type : "post", // 传送方式,参数
					url : "${prc}/bookSite/checkTimeBookSite.action",
					data: "siteId="+siteId+"&beginTime="+$.trim($("#beginDate").val())+"&endTime="+$.trim($("#endDate").val())+"&t="+(new Date()).getTime(),
					async : false
				}).responseText;
			if (check == "true") {
				alert('您选择的时间段已经有人预定了。');
	    		return false;
	    	}else{
	    		$("#explainnote").html('');
				if($.trim($("#explain").val())==""){
					$("#explainnote").html("<font style='color:red;'>请输入说明。</font>");
	                return false ;
	        	}
	        	if($.trim($("#explain").val()).length>240){
	        		$("#explainnote").html("<font style='color:red;'>说明长度不能超过240</font>");
		        	return false;
		        }
		        $("#addevent").submit();
	    	}
        }
    }
	
	var setDatesStrs = function(begin,end){
		$('#beginDate').val(begin);
		$('#endDate').val(end);
	
	};
	
	/**设置为上午***/
	function setMorning(){
		var start = getHourStr(curSelectDate,"07","30");
		var end = getHourStr(curSelectDate,"12","00");
		setDatesStrs(start,end);
	}
	
	/**设置为全天***/
	function setHoleDay(){
		var start = getHourStr(curSelectDate,"07","30");
		var end = getHourStr(curSelectDate,"17","30");
		setDatesStrs(start,end);
	}
	
	/**设置为下午***/
	function setAfternoon(){
		var start = getHourStr(curSelectDate,"13","00");
		var end = getHourStr(curSelectDate,"17","30");
		setDatesStrs(start,end);
	}
	
</script>

</head>

<body>
<div class="box">
	<div class="box_head">
    	<div class="left"><b><a href="${prc }/bookSite/indexUser.action">场馆预订</a></b>
    		<font size="2" color="#336699">&raquo</font><a href="${prc}/bookSite/retrieveOpenSite.action">场馆列表</a>
    		<font size="2" color="#336699">&raquo</font><a href="#">申请预订</a>
    	</div>
        <div class="right"><a href="${prc}/bookSite/retrieveOpenSite.action"  hidefocus="true"> << 返回上一页</a></div>
    </div>
    <div class="box_main" id="calendar"></div>
</div>
<!--弹出窗口 新建活动-->
<s:form action="addFrontEntityBookSite" id="addevent" namespace="/bookSite" method="post">
<s:hidden id="site_id" name="site.site_id"></s:hidden>
<s:token/>
<div id="backgroundPopup" class="deluser"></div>
<div class="delete_group popfor1" style="display:none;">
     <div class="dg_congtent">
      <div class="dg_title">活动详情</div>
      <div class="dg_par">
     	<table id="table1" width="440" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="40" height="30">时间：</td>
            <td width="12" height="30">&nbsp;</td>
            <td height="30">
            	<div id="time">
            	 
             			<!--<s:textfield id="beginTime" name="bookSite.bookSite_beginTimeStr" cssClass="easyui-datetimebox" value="01/01/2011 00:00:00"></s:textfield>   
            		<span>到</span>
            			<s:textfield id="endTime" name="bookSite.bookSite_endTimeStr" cssClass="easyui-datetimebox" value="01/01/2011 00:00:00"></s:textfield>     <span id="timewrong">请选择时间</span>   
            	-->
            	 <input type="text" class="Wdate" name="bookSite.bookSite_beginTimeStr" value="${beginDate}" id="beginDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endDate\');}'})"/> 到
		         <input type="text" class="Wdate" name="bookSite.bookSite_endTimeStr" value="${endDate}" id="endDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'beginDate\');}'})"/>
            	</div>
				<input type="button" onclick="setMorning()" value="上午" />
				<input type="button" onclick="setAfternoon()" value="下午" />
				<input type="button" onclick="setHoleDay()" value="全天" />
            </td>
          </tr>
          <tr>
            <td height="30">级别：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
         		<s:select id="activityLevel" size="1" list="listActivityLevel" name="activityLevel.activityLevel_id" listKey="activityLevel_id" listValue="activityLevel_name"></s:select>
           		
            </td>
          </tr>
           <tr>
            <td height="30">类型：</td>
            <td height="30">&nbsp;</td>
            <td height="30">
           		<s:select id="activityType" size="1" list="listActivityType" name="activityType.activityType_id" listKey="activityType_id" listValue="activityType_name"></s:select>
            </td>
          </tr>
          <tr>
            <td valign="top">说明：</td>
            <td>&nbsp;</td>
            <td>
			<!--这里的格式不要动-->
            	<textarea id="explain" cols="36" rows="5" name="bookSite.bookSite_explain">
场馆用途：
需要设备：
空调 台
话筒 个
公文夹 个
吊麦 个
话筒 个
钢琴 台
茶歇 个
舞台灯 个
射灯 个
地毯 张
小排练门 个
大排练门 个
主引门 个
东门 个
北门 个
				</textarea><br></br><span id="explainnote">必填</span>
			</td>
          </tr>
        </table>
		
      </div>
      <div class="dg_button">
       
       <input type="button" value="创建" class="lauch" onclick="javascript:submitSiteForm();" onMouseOver="this.title=this.value;"/>
       <input type="button" value="返回" class="lauch" id="delu1" onMouseOver="this.title=this.value;"/>
      </div>
     </div>
    </div>
 </s:form>   
<!--弹出窗口结束-->
<!--弹出窗 查看详情口-->
<div class="delete_group popfor" style="display:none;">

 <div class="box_main" id="calendar"></div>
     <div class="dg_congtent">
	 
	 
      <div class="dg_title">活动详情</div>
	  
      <div class="dg_par load">
     	<%--此处连接  booksite/info.jsp--%>
      </div>
      <div class="dg_button">
       <input type="button" value="返回" class="lauch" id="delu2" />
      </div>
     </div>
    </div>
<!--弹出窗口结束-->
</body>
</html>