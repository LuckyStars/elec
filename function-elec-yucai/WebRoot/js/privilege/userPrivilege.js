$(function(){
	var json="";
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
    $("#shade").css({"width": $(document).width(), "height": $(document).height()});

    $("#selectTeacher").click(function() {
        if(json==""){// 在不刷新页面的情况下异步加载树以免出现每次刷新卡的情况
        	$("#allTeacherTree").html("请稍后,数据加载中......");
        	json=$("#allTeacherTree").tree( {
    			checkbox:"true",
    			animate:"true",
    			url:'ecuserTreePgAction.action'
    		});
        }
		centerPopup();
		$(".deluser").show();
		$(".popfor1").show();
	});
	$("#delu1").click(function() {
		centerPopup();
		$(".deluser").hide();
		$(".popfor1").hide();
	});
	function centerPopup(t){
			var windowWidth = document.documentElement.clientWidth;
			var windowHeight = document.documentElement.clientHeight;
			$("#backgroundPopup").css({
				"height": windowHeight
			});
		}

	$("#postAdmin").click(function(){
		$("#un").val("");
		var roleid=$("#type").val();
		var node = $("#allTeacherTree").tree('getChildren');
		var users="";
		var type="";
		var qxs=$("input[name='qx']");
		$(qxs).each(function(){
				if(this.checked){
					type+=this.value+",";
				}
			});		
		var children = $('#allTeacherTree').tree('getChecked', node.target);
		if(children==""||children==null){alert("请选择老师!");return;}
		if(type==""){alert("请选择权限类型!");return;}
		type=type.substring(0,type.length-1);
		$(children).each(function(){
				if(this.id.indexOf('J')==0){
					users+=this.id+"/"+this.text+"/"+type+"#";
				}
			});
		users=users.substring(0,users.length-1);
		if(users==""||users==null){alert("请选择老师!");return;}
		$("#userRole").val(users);
		

			centerPopup();
			$(".deluser").hide();
			$(".popfor1").hide();
			
		addRole.submit();

	});
 });
function showUpdate(id,name,roleIds){
	$("#userid").val(id);
	$("#userName").val(name);
	var qxs=$("input[name='cb']");
	$(qxs).each(function(){//清除原来的复选框选中状态
			$("#"+this.id).attr("checked",false);
	});	
	var roles=roleIds.split(",");
	for(var i=0;i<roles.length;i++){
		$(qxs).each(function(){
				if(this.id==roles[i]){
					$("#"+this.id).attr("checked",true);
				}
			});	
	}
	$("#shade").show();
}

function sureUpdate(){
	var types="";
	var cb=$("input[name='cb']");
	$(cb).each(function(){
			if(this.checked){
				types+=this.value+",";
			}
		});
	if(types==""){alert("请选择权限类型!");return;}	
	$("#roleId").val(types);
	updateRole.submit();
	$("#shade").hide();
}

function closes(){
	$("#shade").hide();
}
function del(id){
	if(confirm('你确定删除吗?')){
		document.getElementById(id).submit();
	}
}
function qxcheck(checkState){
	if(!checkState){
		$("#qxcheckbox").attr("checked",checkState);
	}
}
function selectCheckBox(checkState){
	var trainIds=$("input[name='chid']");
	$(trainIds).each(function(){
		var id=this.id;
		 $("#"+id).attr("checked",checkState);
	});
}
function delAll(userName,offset){
	  var tids=$("input[name='chid']");
	  var size=0;
	  var ids="";
		$(tids).each(function(){
			if(this.checked){size=size+1;ids+=this.value+",";}
		});
	  if(size<=0){alert("请选择需要删除的用户!");return;};
	  if(confirm("你确定要对所选的进行删除吗?")){
	  	location="ecuserDelsPgAction.action?id="+ids+"&userName="+userName+"&page.offset="+offset;
	  }
	}