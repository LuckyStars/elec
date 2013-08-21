$(function(){
	$(".change_tab tr:odd").css("background","#f0f8fc");
	$(".change_tab tr:even").css("background","#d5e0ee");
	$("ul li").click(function(){
        $(".arr").attr("class","");
		$(this).attr("class","arr");
	});
	$("ul li.mag_btn").click(function(){
		$(this).attr("class","mag_btn");
	});
	var x = $(document).height();
	//弹出窗口的定位
    $("#shade").css({"width": $(document).width(), "height": $(document).height()});
    $("#edshade").css({"width": $(document).width(), "height": $(document).height()});
	//显示添加窗口 
	$(".spAdd").click(function(){
		$("#shade").show();	
	 });
	//关闭添加窗口
    $(".addcloseOpenDiv").click(function(){
    	$("#shade").hide();
    });
    //添加窗口的返回按钮
    $("#addcloseOpenDiv").click(function(){
    	window.location.href="findAllplaceAction.action";
    });
  //关闭修改窗口
	$("#edcloseOpenDiv").click(function(){
		$("#edshade").hide();
		//window.location.href="../electiveCourse/place_findAllPlace.action?type=0";
	});
	//修改窗口的返回按钮
	$("#edbackOpenDiv").click(function(){
		//window.location.href="findAllplaceAction.action";
		$("#edshade").hide();
	});
	
	//增加地点的数据
	$("#addForm").click(function(){
//		var placeName = html2char($.trim($("#placeName").val()));
		var placeName = $.trim($("#placeName").val());
		if (placeName == "") {
			$("#nameSpan").html("<font color=red>地点名不能为空</font>");
			return false;
		} else if (placeName.length > 40) {
			$("#nameSpan").html("<font color=red>地点名称过长</font>");
			return false;
		} else {
			var check = $.ajax( {
				type : "post", // 传送方式,参数
				url : "checkPlaceNameplaceAction.action?flag=add",
				data: "ecPlace.name=" + $.trim(placeName)+ "&t="+(new Date()).getTime(),
				async : false
			}).responseText;
			if (check == "true") {
				$("#nameSpan").html("<font color=red>该地点名称已存在</font>");
				return false;
			} else {
				$("#nameSpan").html("<font color=green>*</font>");
			}
		}
		var cat = document.getElementsByName("typeName");//;
		var count = 0;
		for (var i=0; i < cat.length; i++) {
			if (cat[i].checked) {
				count = 1;
			}
		}
		if (count == 0) {
			$("#categorysSpan").html("<font color=red>相关课程至少选一项</font>");
			return false;
		} else {
			$("#categorysSpan").html("<font color=green>*</font>");
		}
		$("#addPlace").submit();
		$(this).attr("disabled", "disabled");
	});
	//编辑地点数据
	$("#updateForm").click(function(){
//		var placeName = html2char($.trim($("#edplaceName").val()));
		var placeName = $.trim($("#edplaceName").val());
		var epId =  $.trim($("#ecplaceId").val());
		if (placeName == "") {
			$("#ednameSpan").html("<font color=red>地点名不能为空</font>");
			return false;
		} else if (placeName.length > 40) {
			$("#ednameSpan").html("<font color=red>地点名称过长</font>");
			return false;
		} else {
			var check = $.ajax( {//ajax后台判断地点名称是否已经存在
				type : "post", // 传送方式,参数
				url : "checkPlaceNameplaceAction.action?flag=edit",
				data: "ecPlace.name=" + placeName + "&ecPlace.id=" + $.trim($("#ecplaceId").val())+"&t="+(new Date()).getTime(),
				async : false
			}).responseText;
			if (check == "true") {
				$("#ednameSpan").html("<font color=red>该地点名称已存在</font>");
				return false;
			} else {
				$("#ednameSpan").html("<font color=green>*</font>");
			}
		}
		var cat = $("input[class='eck2']");
		var count = 0;
		for (var i=0; i < cat.length; i++) {
			if (cat[i].checked) {
				count=1;
			}
		}
		if (count == 0) {
			$("#edcategorysSpan").html("<font color=red>相关课程至少选一项</font>");
			return false;
		} else {
			$("#edcategorysSpan").html("<font color=green>*</font>");
		}
		$("#updatePlace").submit();//表单提交
		$(this).attr("disabled", "disabled");
	});
	
	
	
});	
/**
 * 开启或关闭地点
 * @param id
 * @param status
 * @return
 */
function openORclose(  id , status  ){
	if(status=='close'){//关闭
		if(confirm("是否确认关闭?")){
			var check =$.ajax( {
				type : "post", // 传送方式,参数
				url : "closeplaceAction.action?flag="+status,
				data: "ecPlace.id=" + id+ "&t="+(new Date()).getTime(),
				async : false
			}).responseText;
			if (check == "true") {
				alert("地点关闭成功!");
				window.location.href = "findAllplaceAction.action"; 
			} else {
				alert("该地点已经被占用，暂不能关闭!");
				return false;
			}
		}
	}else if(status=='open'){//开启	
		if(confirm("是否确认开启?")){
			var check =$.ajax( {
				type : "post", // 传送方式,参数
				url : "openplaceAction.action?flag="+status,
				data: "ecPlace.id=" + id+ "&t="+(new Date()).getTime(),
				async : false
			}).responseText;
			if (check == "true") {
				alert("地点成功开启!");
				window.location.href = "findAllplaceAction.action"; 
			} else {
				alert("开启失败 ，请联系管理员!");
				return false;
			}
		}
	}	
}

//编辑地点(ajax返回要修改地点的信息)
function editPlace( id ){
	$.ajax({
		async: false,
        type: "POST",
        url: "editPlaceplaceAction.action",
        data: "ecPlace.id="+ id +"&t="+new Date().getTime(),
        dataType: "json",
        success:function(data){
        	$("#ecplaceId").val(data.id);
        	$("#edplaceName").val(data.name);
        	var temp = data.typeName.split(",");
        	$("input[class='eck2']").attr("checked",null);
        	for(var i=0;i<temp.length;i++){
        		$("input[class='eck2'][value='"+temp[i]+"']").attr("checked","checked");
        	}	
        },
        error: function(){
			alert('会话超时，请求出现错误，请重新登录再试！');
        }
	}); 
	//显示修改窗口
	$("#edshade").show();
}

/**
 * 字符转义
 */
function html2char(source){
	return String(source)
			.replace(/&/g,'&amp;')
            .replace(/</g,'&lt;')
            .replace(/>/g,'&gt;')
            .replace(/\\/g,'&#92;')
            .replace(/"/g,'&quot;')
            .replace(/'/g,'&#39;');
}



