$(document).ready(function(){
	var selid = $('#selid').val();
	var secoid = $('#secoid').val();
	if(selid!=''){
	$('#'+selid+'s').attr('selected', 'selected');
	$('#'+selid).addClass('li_mr');
	$('#hideli').children('li').not('#'+selid).removeClass('li_mr');
	}
	var beginval = $('#beginval').val();
	var endval = $('#endval').val();

	if(beginval == '')
	{
		
	}else{
		$('.combo-value[name=beginTime]').val(beginval);
		$('.combo-value[name=beginTime]').siblings('.combo-text').val(beginval);
	}

	if(endval == '')
	{
		
	}else{
		$('.combo-value[name=endTime]').val(endval);
		$('.combo-value[name=endTime]').siblings('.combo-text').val(endval);
	}
	
	$(document).ready(function(){
		$('#opId').change(function(){
			var one = $(this).val();
			$('#hideli').children('li').not('#'+one).removeClass('li_mr');
			$('#'+one).addClass('li_mr');
		});
	});	
});