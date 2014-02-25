function centerPopup(t) {

	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $(".delete_group").height();
	var popupWidth = $(".delete_group").width();
	var groupHeight = $("#creatnew").height();

	if (t != "group") {
		$(".delete_group").css( {
			"position" : "absolute",
			"top" : 50,
			"left" : 120
		});
	} else {
		$("#creatnew").css( {
			"position" : "absolute",
			"top" : windowHeight / 4 - groupHeight / 4,
			"left" : windowWidth / 2 - popupWidth / 2
		});
	}


	$("#backgroundPopup").css( {
		"height" : windowHeight
	});
}
function resizePop() {
	var windowHeight = document.documentElement.clientHeight;
	$("#backgroundPopup").css( {
		"height" : windowHeight
	});
}
$(document)
		.ready(
				function() {
					//时间输入(shijian shuru wenti)
					$('.combo-text').focus(function(){
						$(this).blur();
					});
					$('.combo-text').keypress(function(){
						$(this).blur();
					});
					
					$(".removeuser").click(function() {
						centerPopup();
						$(".deluser").show();
						$(".popfor1").show();
					});
					
					$(".edit_changguan")
							.click(
									function() {
										centerPopup();
										$(".deluser").show();
										$(".popfor2").show();

										var edid = $(this).attr('edit');

										$
												.ajax( {
													type : "get", // 传送方式,参数
																	// GET ,
																	// POST
													url : "bookSite/retrieveByIdSite.action?t="+(new Date).getTime()+"&site_id="
															+ edid, // 数据提交地址,如果使用GET的时候data内容需要写在这里
													beforeSend : function(
															XMLHttpRequest) {
														// 传送开始之前,可以里面加个函数来执行loading等操作
														// ShowLoading();
													},
													success : function(data) {
														$('.updateit').html(
																data);
														sethash(resizePop);
													},
													error : function() {
														// 请求出错处理
													alert('请求出错');
												}
												});

									});
					$(".add_jibie").click(function() {
						centerPopup();
						$(".deluser").show();
						$(".popfor1").show();
						sethash(resizePop);
					});
					$(".edit_jibie")
							.click(
									function() {

										centerPopup();
										$(".deluser").show();
										$(".popfor2").show();
										var edid = $(this).attr('editLevel');
										$.ajax( {
													type : "get", // 传送方式,参数
																	// GET ,
																	// POST
													url : "bookSite/retrieveByIdActivityLevel.action?activityLevel_id="
															+ edid, // 数据提交地址,如果使用GET的时候data内容需要写在这里
													beforeSend : function(
															XMLHttpRequest) {
														// 传送开始之前,可以里面加个函数来执行loading等操作
														// ShowLoading();
													},
													success : function(data) {
														$('.updateit').html(
																data);
														sethash(resizePop);
													},
													error : function() {
														// 请求出错处理
													alert('请求出错');
												}
												});
									});
					
					$(".c369").click(function() {
						centerPopup("group");
						$(".deluser").show();
						$("#creatnew").show();
						sethash(resizePop);
					});
					$("#delu1").click(function() {
						$(".deluser").hide();
						$(".popfor1").hide();
						$(".popfor2").hide();
					});

					$("#delu8").click(function() {
						$(".deluser").hide();
						$(".popfor2").hide();
					});

					$("#cnew,#cnew1").click(function() {
						$(".deluser").hide();
						$("#creatnew").hide();
					});
				});
function isNumber(oNum) {
	if (!oNum)
		return false;
	var strP = /^\d+(\.\d+)?$/;
	if (!strP.test(oNum))
		return false;
	try {
		if (parseFloat(oNum) != oNum)
			return false;
	} catch (ex) {
		return false;
	}
	return true;
}