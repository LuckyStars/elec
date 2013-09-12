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
	$(".shade").css({"width": $(document).width(), "height": $(document).height()});
	
	$("#clsDate").click(function(){
		var div="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;此课程的报课节数为"
			+classNum+"节,所选课时不能超过"+classNum+"节<br/><br/>";
		var clsDates="";
		closeDiv('shade1');
		$.ajax({
			type: "POST",
			url:"classDateSigAction.action",
			data:{"id":id,"rr":Math.random()},
			async: false,
			dataType:"json",
			success: function(msg){ 
				$(msg).each(function(){
					
					clsDates+="<input style='margin-left: 110px' name='ch' id='"+this.id+"' "+(this.state?"disabled='disabled'":"")
					+" onclick='checkbx("+classNum+",\""+this.id+"\")' type='checkbox' value='"+this.id+"'  />"
					+"&nbsp;&nbsp;&nbsp;&nbsp;"+this.name+(this.state?" <span style='color:red;float:none' >满</span>":"")+"<br/>";
				});
			}
		 });
		$("#ptextVal1").html(div+clsDates);
		var cds=classDates.split(",");
		for(var i=0;i<cds.length;i++){
			$($("input[name='ch']")).each(function(){
				if(this.id==cds[i]){
					$("#"+this.id).attr("checked","checked");
					$("#"+this.id).attr("disabled",false);
					}
				});
			}
		showDiv('shade2');
	});
	$("#addClassDate").click(function(){
		var checkNum=0;
		var classDate="";
		var checks=$("input[name='ch']");
		$(checks).each(function(){
			if(this.checked){
				checkNum++;
				classDate+=this.value+",";
			}
			});
		if(checkNum<classNum){
			alert("课时数必须选择"+classNum+"课时!");
			return;
		}
		if(classDate!=""){
			classDate=classDate.substring(0,classDate.length-1);
		}
		if(confirm("你确定报课时吗?")){
			location.href="ecsignAddSigAction.action?ecSign.courseId="+id+
			"&ecSign.id="+signId+"&ecSign.classhourIds="+classDate+
			"&ecCourseVO.typeId="+typeId+"&ecCourseVO.weekIndex="+weekIndex+"&ecCourseVO.name="+searchName;
		}
	});
	$("#delSign").click(function(){
			delSign(signId,courseName);
		});
	$("#delClsDate").click(function(){
		if(confirm("你确定退掉 ["+courseName+"] 此课程的课时吗?")){
			location.href="ecsignUpdateSigAction.action?id="+signId+
			"&ecCourseVO.typeId="+typeId+"&ecCourseVO.weekIndex="+weekIndex+"&ecCourseVO.name="+searchName;
			}
	});
 });
function showDiv(id){
	$("#"+id).show();
}
function closeDiv(id){
	$("#"+id).hide();
}
var id="";
var classNum="";
var courseName="";
var signId="";
var classDates="";
function delSignShiJia(idd,classNumm,name,signid,classDate){
	showDiv('shade1');
	 $("#ptextVal").html("你选择的课程是["+name+"],请选择你的操作!");
	this.id=idd;
	this.classNum=classNumm;
	this.courseName=name;
	this.signId=signid;
	this.classDates=classDate;
}
function checkbx(num,id){
	var checkNum=0;
	$($("input[name='ch']")).each(function(){
		if(this.checked){checkNum++;}
		});
	if(checkNum>num){
		alert("课时数以经达到最大值!");
		$("#"+id).attr("checked",false);
	}
}
function addSign(courseId,name){
	if(confirm("你确定报 ["+name+"] 此课程吗?")){
		location.href="ecsignAddSigAction.action?ecSign.courseId="+courseId+
		"&ecCourseVO.typeId="+typeId+"&ecCourseVO.weekIndex="+weekIndex+"&ecCourseVO.name="+searchName;
	}
}
function delSign(id,name){
	if(confirm("你确定退掉 ["+name+"] 此课程吗?")){
	location.href="ecsignDelSigAction.action?id="+id+
	"&ecCourseVO.typeId="+typeId+"&ecCourseVO.weekIndex="+weekIndex+"&ecCourseVO.name="+searchName;
	}
}
//搜课程
function sreachSubmit(){
	var mainName = $("input[name='ecCourseVO.name']").val();
	$("input[name='ecCourseVO.name']").val($.trim(mainName));
	$("#sreach").submit();
}
function message(){/*报名提示信息*/
	if(messages!=''){
		alert(messages);
	}
}