$(function(){
	//----------------- 任课老师树 -----------------------------
    var json = "";
    
    //弹出
    $("#sel_teacher_a").click(function(){
        $("#tree_popu_div").show();
        
        if (json == "") {// 在不刷新页面的情况下异步加载树以免出现每次刷新卡的情况
            $.ajax({
                type: "POST",
                url: prc + '/elec/ecuserTreePgAction.action',
                async: true,
                dataType: "json",
                success: function(msg){
                    json = $("#allTeacherTree").tree({
						checkbox:"true",
                        animate: "true",
                        data: msg
                    });
                },
				error: function(){
					alert('会话超时，请求出现错误，请重新登录再试！');
					return false;
	            }
            });
        }
    });
    
    //tree_ok
    $("#tree_ok").click(function(){
		var nodes = $('#allTeacherTree').tree('getChecked');
		var teacherIds = '';
        var teacherNames = '';
		var count = 0;
		for(var i=0; i<nodes.length; i++){
			var node = nodes[i];
			if (node) {
				if (node.id.indexOf('J') == 0) {
					count++;
					
					if (teacherIds != '') teacherIds += ',';
					teacherIds += nodes[i].id;
					
					if (teacherNames != '') teacherNames += ', ';
					teacherNames += nodes[i].text;
	            }
			}
		}
		if(teacherIds == ''){
			alert("请选择教师!");
			return;
		} else if(count>20){
			alert("所选教师数不能超过20位!");
			return;
		} else {
			$("input[name='ecCond.teacherIds']").val(teacherIds);
	        $("input[name='ecCond.teacherNames']").val(teacherNames);
	        $("input[name='ecCond.teacherNames']").attr("title", teacherNames);
	        $("#tree_popu_div").hide();
		}
    });
    
    //tree_cancel
    $("#tree_cancel").click(function(){
        $("#tree_popu_div").hide();
    });
	
	//----------------------- 富文本编辑器 ---------------------
	$("#showClose").toggle(
		function () {
		  $("#detailContent").slideDown(function(){
			  sethash();
		  });
		  $(this).html("收缩课程内容详情↑");
		},
		function () {
		  $("#detailContent").slideUp();
		  $(this).html("展开课程内容详情↓");
		}
	);
	
	//----------------------- 各种ajax验证 ---------------------
	//课程分类
	$("select[name='ecCond.typeId']").change(function(){
		var typeId = $(this).val();
		$.getJSON("getStartPlacesEcCourse.action", {typeId:typeId, time:new Date().getTime()}, function(callData){
			$("select[name='ecCond.startPlaceId']").empty();
			$("select[name='ecCond.startPlaceId']").append('<option value="">--请选择--</option>');
			for(var i=0; i<callData.length; i++){
				$("select[name='ecCond.startPlaceId']").append('<option value="'+ callData[i].id +'">'+ callData[i].name +'</option>');
			}
		});
	});
		
	//课程名称
	function checkNameSame(ecCond_name, termId){
		if(ecCond_name != ""){
			$.getJSON("checkNameEcCourse.action", {name:ecCond_name, termId:termId, time:new Date().getTime()}, function(callData){
				if(callData.success == 0){
					$("#ecCond_name_span").html("课程名称存在重复！");
					return false;
				}else{
					$("#ecCond_name_span").html("*");
					return true;
				}
			});
		}
	}
	
	$("input[name='ecCond.name']").blur(function(){
		var ecCond_name = $.trim($(this).val());
		$(this).val(ecCond_name);
		
		var termId = $("input[name='ecCond.termId']").val();
		
		checkNameSame(ecCond_name, termId);
	});
	
	//限定人数
	function checkMaxStudentNum(maxStudentNum){
		if(maxStudentNum != " "){
			if(maxStudentNum != '0'&&!isNegative(maxStudentNum) && isInt(maxStudentNum)){
				$("#ecCond_maxStudentNum_span").html("*");
				return true;		
				
			}else{
				$("#ecCond_maxStudentNum_span").html("限定人数只能为正整数,人数不能为零!");
				return false;
			}
		}
	}
	$("input[name='ecCond.maxStudentNum']").blur(function(){
		var maxStudentNum = $.trim($(this).val());
		$(this).val(maxStudentNum);
		
		checkMaxStudentNum(maxStudentNum);
	});
	
	//要求课时数(史家)
	function checkClasshourRequire(classhourRequire){
		if(classhourRequire != ""){
			if(!isNegative(classhourRequire) && isInt(classhourRequire)){
				if(classhourRequire<= 7 && classhourRequire>0){
					$("#ecCond_classhourRequire_span").html("*");
					return true;
				}else{
					$("#ecCond_classhourRequire_span").html("要求课时数不能大于7");
					return false;
				}
				
			}else{
				$("#ecCond_classhourRequire_span").html("要求课时数只能为正整数！");
				return false;
			}
		}
	}
	if (signType == "shijia") {
		$("input[name='ecCond.classhourRequire']").blur(function(){
			var classhourRequire = $.trim($(this).val());
			$(this).val(classhourRequire);
			
			checkClasshourRequire(classhourRequire);
		});
	}
	
	//每周课时
	$("select[name='ecCond.classhourNum']").change(function(){
		$(".ecCond_classHour_span").html("");
		
		var classhourNum = parseInt($(this).val());
						
		$(".classHour_div").hide();
		//$(".easyui-timespinner").timespinner('reset');
		$("#real_classHour_div").empty();
		for(var i=1; i<=classhourNum; i++){
			$("#classHour_div"+i).show();
		}
		
		if (signType == "shijia") {
			var optClasshourNum = parseInt($(this).val());
			var requiredClasshour = parseInt($.trim($("input[name='ecCond.classhourRequire']").val()));
			if(optClasshourNum < requiredClasshour ){
				$("#ecCond_classhourNum_span").html("可选课时不能小于要求课时！");
			}else{
				$("#ecCond_classhourNum_span").html("*");
			}
		}
	});
	$("select[name='ecCond.classhourNum']").change();
	
	//选课提示
	$("textarea[maxlength]").bind('input propertychange', function() {  
	    var maxLength = $(this).attr('maxlength');   
	    if ($(this).val().length > maxLength) {   
	        $(this).val($(this).val().substring(0, maxLength));   
	    }
	});
	
	
	//----------------- 提交/取消 ---------------
	function checkNotEmpty(val, elemID, errorInfo){
		if(isSpace(val)){
			$("#"+elemID).html(errorInfo);
			return false;
		}else{
			$("#"+elemID).html("*");
			return true;
		}
	}
	//提交
	$("#add_course_submit").click(function(){
		//$("#form_submit_waitingTip").html("正在进行校验，请稍候...");
		
		//课程分类-非空
		var typeId = $("select[name='ecCond.typeId']").val();
		if(!checkNotEmpty(typeId, "ecCond_typeId_span", "请选择课程分类！")){
			return false;
		}
				
		//课程名称-非空
		var ecCond_name = $.trim($("input[name='ecCond.name']").val());
		$("input[name='ecCond.name']").val(ecCond_name);
		if(!checkNotEmpty(ecCond_name, "ecCond_name_span", "课程名称不能为空！")){
			return false;
		}
		//课程名称-重复
		var classNameSame = true;
		var termId = $("input[name='ecCond.termId']").val();
		$.ajax({
            type: "POST",
            url: 'checkNameEcCourse.action',
			data: "name="+ ecCond_name +"&termId="+ termId +"&time="+new Date().getTime(),
            async: false,
            dataType: "json",
            success: function(callData){
                if(callData.success == 0){
					$("#ecCond_name_span").html("课程名称存在重复！");
					classNameSame = false;
				}else{
					$("#ecCond_name_span").html("*");
				}
            },
			error: function(){
				alert('会话超时，请求出现错误，请重新登录再试！');
				classNameSame = false;
            }
        });
		if(!classNameSame){
			return false;
		}
		
		//年级范围-非空
		if($("input[name='ucGrade_ipt']:checked").length <= 0){
			$("#ecCond_gradeIds_span").html(i18nGradeNames + "不能为空！");
			return false;
		}else{
			$("#ecCond_gradeIds_span").html("*");
		}
		
		var ecCond_gradeIds = "";
		var ecCond_gradeNames = "";
		$("input[name='ucGrade_ipt']:checked").each(function(){
			if (ecCond_gradeIds != '') ecCond_gradeIds += ',';
			ecCond_gradeIds += $(this).val();
			
			if (ecCond_gradeNames != '') ecCond_gradeNames += ', ';
			ecCond_gradeNames += $(this).attr("hiddenName");
		});
		$("input[name='ecCond.gradeIds']").val(ecCond_gradeIds);
        $("input[name='ecCond.gradeNames']").val(ecCond_gradeNames);
		
		//限定人数
		var maxStudentNum = $.trim($("input[name='ecCond.maxStudentNum']").val());
		$("input[name='ecCond.maxStudentNum']").val(maxStudentNum);
		if(!checkNotEmpty(maxStudentNum, "ecCond_maxStudentNum_span", "限定人数不能为空！")){
			return false;
		}
		if(!checkMaxStudentNum(maxStudentNum)){
			return false;
		}
		
		//任课老师
		var teacherIds = $("input[name='ecCond.teacherIds']").val();
		if(!checkNotEmpty(teacherIds, "ecCond_teacherIds_span", "任课老师不能为空！")){
			return false;
		}
		
		//是否俱乐部(史家)
		if(signType == "shijia"){
			$("input[name='ecCond.clubState']").val($("#isClubState").is(":checked")? 1 : 0);
		}
		
		//报名日期、开课日期
		var signStartDateStr = $("#d4311").val();
		var signEndDateStr = $("#d4312").val();
		var classStartDateStr = $("#d4313").val();
		var classEndDateStr = $("#d4314").val();
		if(!checkNotEmpty(signStartDateStr, "ecCond_signDate_span", "报名开始日期不能为空！")){
			return false;
		}
		if(!checkNotEmpty(signEndDateStr, "ecCond_signDate_span", "报名结束日期不能为空！")){
			return false;
		}
		if(!checkNotEmpty(classStartDateStr, "ecCond_classDate_span", "开课日期不能为空！")){
			return false;
		}
		if(!checkNotEmpty(classEndDateStr, "ecCond_classDate_span", "结课日期不能为空！")){
			return false;
		}
		if(compareDate(signStartDateStr, signEndDateStr)){
			$("#ecCond_signDate_span").html("报名开始不能大于报名结束！");
			return false;
		}
		if(compareDate(signEndDateStr, classStartDateStr)){
			$("#ecCond_signDate_span").html("报名结束不能大于开课日期！");
			return false;
		}
		if(compareDate(classStartDateStr, classEndDateStr)){
			$("#ecCond_classDate_span").html("开课日期不能大于结课日期！");
			return false;
		}
		
		//要求课时数（史家）
		if (signType == "shijia") {
			var classhourRequire = $.trim($("input[name='ecCond.classhourRequire']").val());
			$("input[name='ecCond.classhourRequire']").val(classhourRequire);
			if(!checkNotEmpty(classhourRequire, "ecCond_classhourRequire_span", "要求课时数不能为空！")){
				return false;
			}
			if(!checkClasshourRequire(classhourRequire)){
				return false;
			}
		}
		
		//可选课时数（史家）
		if (signType == "shijia") {
			var optClasshourNum = parseInt($("select[name='ecCond.classhourNum']").val());
			var requiredClasshour = parseInt($.trim($("input[name='ecCond.classhourRequire']").val()));
			if(optClasshourNum < requiredClasshour ){
				$("#ecCond_classhourNum_span").html("可选课时不能小于要求课时！");
				return false;
			}else{
				$("#ecCond_classhourNum_span").html("*");
			}
		}
		
		//上课地点
		var startPlaceId = $("select[name='ecCond.startPlaceId']").val();
		if(!checkNotEmpty(startPlaceId, "ecCond_startPlaceId_span", "请选择上课地点！")){
			return false;
		}
				
		//周课时
		if (signType == "shijia") {
		var chBoolError2_info = "";
		var chBoolError0 = false;  //0-失败(不允许)
		var chBoolError2 = false;  //2-失败(允许)
		$(".ecCond_classHour_span").html("");
		var ajaxParams = $("select[name='ecCond_weekIndexs']:visible, input[name='ecCond_weekStartTimes']:visible, input[name='ecCond_weekEndTimes']:visible").serialize();
		var teacherIds = $("input[name='ecCond.teacherIds']").val();
		var teacherNames = $("input[name='ecCond.teacherNames']").val();
		var termId = $("input[name='ecCond.termId']").val();
		$.ajax({
            type: "POST",
            url: 'checkClasshourEcCourse.action',
			data: ajaxParams+"&teacherNames="+teacherNames + "&teacherIds="+teacherIds+"&startPlaceId="+startPlaceId+"&termId="+termId+"&classStartDateStr="+classStartDateStr+"&classEndDateStr="+classEndDateStr+"&time="+new Date().getTime(),
            async: false,
            dataType: "json",
            success: function(callData){
				if(callData.success == 0){//0-失败(不允许)
					$("#ecCond_classHour_span" + callData.pxOrder).html(callData.msg);
					chBoolError0 = true;
				}else if(callData.success == 2){//2-失败(允许)
					if(callData.pxOrder != 8){
						$("#ecCond_classHour_span" + callData.pxOrder).html(callData.msg);
					}else{
						$("#ecCond_startPlaceId_span").html(callData.msg);
					}
					if(chBoolError2_info != "") chBoolError2_info += "\r\n";
					chBoolError2_info += callData.msg;
					chBoolError2 = true;
				}else{ //1- 成功 
					$(".ecCond_classHour_span").html("");
				}
            },
			error: function(){
				alert('会话超时，请求出现错误，请重新登录再试！');
				checkClasshourBool = false;
            }
        });
		
		
		if(chBoolError0){
			return false;
		}
		
	}
		//周课时-提交
		/*
		var classhourNum = parseInt($("select[name='ecCond.classhourNum']").val());
		$("#real_classHour_div").empty();
		for(var i=1; i<=classhourNum; i++){
			var ecCond_real_weekIndex = $("#classHour_div"+i).find("select[name='ecCond_weekIndexs']").val();
			var ecCond_real_weekStartTime = $("#classHour_div"+i).find("input[name='ecCond_weekStartTimes']").timespinner('getValue');
			var ecCond_real_weekEndTime = $("#classHour_div"+i).find("input[name='ecCond_weekEndTimes']").timespinner('getValue');
			$("#real_classHour_div").append(
				'<input type="hidden" name="ecCond_real_weekIndexs" value="'+ ecCond_real_weekIndex +'" />'+
				'<input type="hidden" name="ecCond_real_weekStartTimes" value="'+ ecCond_real_weekStartTime +'" />'+
				'<input type="hidden" name="ecCond_real_weekEndTimes" value="'+ ecCond_real_weekEndTime +'" />'
			);
		}*/
		
		//放学地点
		/*
		var endPlaceId = $("select[name='ecCond.endPlaceId']").val();
		if(!checkNotEmpty(endPlaceId, "ecCond_endPlaceId_span", "请选择放学地点")){
			return false;
		}
		*/
		
		//3个富文本编辑器
		var courseContentCount =  editorCourseContent.count();
		if(courseContentCount > 44700){
			alert("课程内容字符总数不能大于44700！");
			return false;
		}
		editorCourseContent.sync();
		var courseRequireCount =  editorCourseRequire.count();
		if(courseRequireCount > 44700){
			alert("学生要求字符总数不能大于44700！");
			return false;
		}
		editorCourseRequire.sync();
		var courseNoteCount =  editorCourseNote.count();
		if(courseNoteCount > 44700){
			alert("备注字符总数不能大于44700！");
			return false;
		}
		editorCourseNote.sync();
		
		
		if(chBoolError2){
			if(!confirm("确定忽略如下信息：\r\n\r\n" + chBoolError2_info)){
				return false;
			}
		}
		
		//$("#form_submit_waitingTip").html("校验成功，数据提交中...");
		$("#add_course_form").submit();
		$(this).css("color", "#A2A2A2").unbind("click");
	});
	
	//取消
	$("#add_course_cancle").click(function(){
		window.location = "listEcCourse.action";
	});
	
});
