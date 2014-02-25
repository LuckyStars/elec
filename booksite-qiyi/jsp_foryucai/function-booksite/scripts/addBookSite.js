$(document).ready(function() {
		
	$('#addevent').submit(function(){
		var siteId = $('#site_id').val();
		var explain = $('#explain').val();
		var beginTime = $('.combo-value[name=bookSite.bookSite_beginTimeStr]').val();
		var begin = new Array();

		var str1 = new Array();
		var str2 = new Array();
		begin = beginTime.split(" ");
		str1 = begin[0].split("-");
		str2 = begin[1].split(":");

		
		var endTime = $('.combo-value[name=bookSite.bookSite_endTimeStr]').val();
		var end = endTime.split(" ");
		var str3 = new Array();
		var str4 = new Array();
		str3 = end[0].split("-");
		str4 = end[1].split(":");	
		
		//now time
		var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		var now = y+'-'+m+'-'+d;
		var h = date.getHours();
		var min = date.getMinutes();
		
		var s1 = str1[0]+'-'+str1[1]+'-'+str1[2];
		var s2 = str3[0]+'-'+str3[1]+'-'+str3[2];
		s1 = s1.replace(/-/g, "/"); 
		s2 = s2.replace(/-/g, "/"); 
		now = now.replace(/-/g, "/");
		s1 = new Date(s1);
		s2 = new Date(s2);
		now = new Date(now);
		
		var days= s1.getTime() - s2.getTime(); 
		var nowdays = s1.getTime() - now.getTime();
		var nowtime = parseInt(nowdays / (1000 * 60 * 60 * 24));
		var time = parseInt(days / (1000 * 60 * 60 * 24));
		if (nowtime < 30) {
			$('#timewrong').html('<font color=red>选择错误</font>');
			return false;
		}else if (nowtime == 30) {
			   if(h - str2[0] < 0){
				   $('#timewrong').html('<font color=green>输入正确</font>');
				}
				if(h - str2[0] == 0){
					 if(min - str2[1] <= 0 ){
						 $('#timewrong').html('<font color=green>输入正确</font>');
					    	
					 }else{
						$('#timewrong').html('<font color=red>选择错误</font>');
						return false;
					 }
				  }
				if(h - str2[0] > 0){
					 $('#timewrong').html('<font color=red>选择错误</font>');
					 return false;
				 }
			  }
		if (time > 0) {
			$('#timewrong').html('<font color=red>选择错误</font>');
			return false;
		} else if (time == 0) {
			   if(str2[0] - str4[0] <= 0){
					var check = $.ajax( {
						type : "post", // 传送方式,参数
						url : "bookSite/checkAddFrontEntityBookSite.action",
						data: "siteId="+siteId+"&beginTime="+beginTime+"&endTime="+endTime+"&t="+(new Date()).getTime(),
						async : false
					}).responseText;
				   if (check == "true") {
			    		 $('#timewrong').html('<font color=red>时间冲突</font>');
			    		return false;
			    	} else {
			    		 $('#timewrong').html('<font color=green>输入正确</font>');
			    	}
				
			   }else if(str2[0] - str4[0] == 0){
				 if(str2[1] - str4[1] <= 0 ){
						var check = $.ajax( {
							type : "post", // 传送方式,参数
							url : "bookSite/checkAddFrontEntityBookSite.action",
							data: "siteId="+siteId+"&beginTime="+beginTime+"&endTime="+endTime+"&t="+(new Date()).getTime(),
							async : false
						}).responseText;
					 if (check == "true") {
			    		 $('#timewrong').html('<font color=red>时间冲突</font>');
			    		return false;
			    	} else {
			    		 $('#timewrong').html('<font color=green>输入正确</font>');
			    	}
				    	
				 }else{
					$('#timewrong').html('<font color=red>选择错误</font>');
					return false;
				 }
			  }else{
				 $('#timewrong').html('<font color=red>选择错误</font>');
				 return false;
			 }
		  } else {
				var check = $.ajax( {
					type : "post", // 传送方式,参数
					url : "bookSite/checkAddFrontEntityBookSite.action",
					data: "siteId="+siteId+"&beginTime="+beginTime+"&endTime="+endTime+"&t="+(new Date()).getTime(),
					async : false
				}).responseText;
			  if (check == "true") {
		    		 $('#timewrong').html('<font color=red>时间冲突</font>');
		    		return false;
		    	} else {
		    		 $('#timewrong').html('<font color=green>输入正确</font>');
		    	}
		}


		if(explain == "" || explain.length >= 140){
			$('#explainnote').html('<font color=red>不能为空或长度过长</font>');
			return false;
		}else{
			$('#explainnote').html('<font color=green>输入正确</font>');
		}

		$("#delu").attr("disabled","disabled");
	});
});