$(function(){
	//----------------- 年级范围 -----------------------------
    $('#cc').combo({
        required: false,
        editable: false
    });
    $('#sp').appendTo($('#cc').combo('panel'));
    $('#sp input').click(function(){
        var val = "";
        var textVal = "";
        $('#sp input:checked').each(function(){
            val += $(this).val() + ",";
            textVal += $(this).next('span').text() + ", ";
        });
        
        if (val.lastIndexOf(",") == val.length - 1) {
            val = val.substring(0, val.length - 1);
        }
        if (textVal.lastIndexOf(", ") == textVal.length - 2) {
            textVal = textVal.substring(0, textVal.length - 2);
        }
        
        //alert(val);
        $('#cc').combo('setValue', val).combo('setText', textVal);
        
		$("input[name='ecCond.gradeIds']").val(val);
		$("input[name='ecCond.gradeNames']").val(textVal);
    });
	    
	//回显年级
	var val = $("input[name='ecCond.gradeIds']").val();
	var textVal = $("input[name='ecCond.gradeNames']").val();
	$('#cc').combo('setValue', val).combo('setText', textVal);
	
	var gradeIdArr = val.split(",");
	for(var i=0; i<gradeIdArr.length; i++){
		$('#sp input[value="'+ gradeIdArr[i] +'"]').attr("checked","checked");
	}
	//----------------- 搜索 -----------------------------
	
	//搜索
	$("#form_submit_search").click(function(){
		var trimVal = $("#trimVal").val();
			trimVal = $.trim(trimVal);
			$("#trimVal").val(trimVal);
		var gradeIds = $('#cc').combo('getValue');
		//alert(gradeIds);
		
		var gradeNames = $('#cc').combo('getText');
		//alert(gradeNames);
		
		$("#form_search").submit();
	});
	//重置
	$("#form_reset_search").click(function(){
		$("select[name='ecCond.typeId']").val("");
		
		$('#cc').combo('setValue', "").combo('setText', "");
		$('#sp input').removeAttr("checked");
		$("input[name='ecCond.gradeIds']").val("");
		$("input[name='ecCond.gradeNames']").val("");
		
		$("input[name='ecCond.teacherNames']").val("");
		$("input[name='ecCond.teacherIds']").val("");
		
		$("input[name='ecCond.name']").val("");
	});
	
	//----------------- 切换兴趣班 -----------------------------
	$("#xqb_title").click(function(){
		$("#term_div_popue").show();		
	});
	
	//确定
	$("#term_div_submit").click(function(){
		var termId = $("input[name='term_popue_id']:checked").val();
		window.location.href = "listEcCourse.action?ecCond.termId=" + termId;
	});
	
	//关闭
	$(".term_div_close").click(function(){
		$("#term_div_popue").hide();		
	});
		
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
                        animate: "true",
                        data: msg
                    });
                }
            });
        }
    });
    
    //tree_ok
    $("#tree_ok").click(function(){
        var teacherId = "";
        var teacherName = "";
        var node = $('#allTeacherTree').tree('getSelected');
        if (node) {
            if (node.id.indexOf('J') == 0) {
                var teacherId = node.id;
                var teacherName = node.text;
                
                $("input[name='ecCond.teacherIds']").val(teacherId);
                $("input[name='ecCond.teacherNames']").val(teacherName);
                
                $("#tree_popu_div").hide();
            }
            else {
                alert("请选择老师!");
                return;
            }
        }
    });
    
    //tree_cancel
    $("#tree_cancel").click(function(){
        $("#tree_popu_div").hide();
    });
    
    
	//----------------- 去录入课程  -----------------------------
	$("#add_course_btn").click(function(){
        window.location = 'addUIEcCourse.action';
    });
	//------------------删除个个课程加录入学生-------------------------
	
	//----------------- 全选删除  -----------------------------
	$("#alloperCheckbox").click(function(){
        $(".operCheckbox:enabled").attr("checked", $(this).is(":checked"));
    });
	
	$("#delCheckedCourses").click(function(){
		var checkedCoursesVal = '';
        $(".operCheckbox:checked").each(function(){
			if(checkedCoursesVal != '') checkedCoursesVal += ',';
			checkedCoursesVal += $(this).val();
		});
		
		if(checkedCoursesVal != ''){
			if(confirm('确认删除？删除后不可恢复！')){
				window.location='removeEcCourse.action?ids=' + checkedCoursesVal;
			}
		}else{
			alert("请先选择课程！");
		}
    });
	
	//----------------- 导出Excel  -----------------------------
	$("#menuContent p").hover(
		function () {
			$(this).css("color","#1E5388");
		},
		function () {
			$(this).css("color","#000000");
		}
	);
	$("#export_excel").click(function(){
		$("#menuContent").show();
		$("body").bind("mousedown", onBodyDown);
    });
	$("#export_allCourse_excel").click(function(){
		window.location = "exportExlEcCourse.action?ecCond.termId=" + $("#currentTerm_id").val();		
		hideMenu();
    });
	
	$("#export_allStudent_excel").click(function(){
       	window.location = "exportExlWithStuInfoEcCourse.action?ecCond.termId=" + $("#currentTerm_id").val();
		hideMenu();
    });
	
});
function hideMenu() {
	$("#menuContent").hide();
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "export_excel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		hideMenu();
	}
}