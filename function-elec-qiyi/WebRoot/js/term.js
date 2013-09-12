function checkForm() {
	clearErr();
	$("input[name='term.name']")
			.val($.trim($("input[name='term.name']").val()));
	if ($("input[name='term.name']").val() == "") {
		$("#nameErr").html("请填写名称");
		return false;
	}
	if ($("input[name='term.name']").val().length > 30) {
		$("#nameErr").html("名称不能超过30个字");
		return false;
	}
	if (checkDate('openDate')) {
		return false;
	}
	if (checkDate('signDate')) {
		return false;
	}
	if (checkDate('lessonDate')) {
		return false;
	}
	if (checkDate('classTime')) {
		return false;
	}
	var comments = $.trim($("#comments").val());
	$("#comments").val(comments);
	if (comments.length > 200) {
		$("#commentsErr").html("不能超过200个字");
		return false;
	}
	return true;
}
function clearErr() {
	$("font[id$='Err']").html("");
}
function checkDate(type) {
	clearErr();
	var start = type + 'Start';
	var end = type + 'End';
	var err = type + 'Err';
	if ($.trim($("#" + start).val()) == '' || $.trim($("#" + end).val()) == '') {
		$("#" + start)[0].focus();
		$("#" + err).html('请选择时间');
		return true;
	}
	return false;
}