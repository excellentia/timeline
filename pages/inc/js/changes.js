$(function() {
	
	$("#my_change_accordion").accordion({
	    autoHeight : false,
	    navigation : true,
	    collapsible : true,
	    active : 0
	});
	
	$('#fromDateImg').click(function() {
		$('#fromDate').datepicker('show');
	});

	$('#toDateImg').click(function() {
		$('#toDate').datepicker('show');
	});
	
	
	//set the initial dates
	{
		var currentDate = new Date();
		var from = $('#fromDate').val();
		var to = $('#toDate').val();

		if ((from == null) || (from == "")) {
			$('#fromDate').val($.datepicker.formatDate('dd M yy', currentDate));
		}

		if ((to == null) || (to == "")) {
			$('#toDate').val($.datepicker.formatDate('dd M yy', currentDate));
		}
	}
	
	$('#fromDate').datepicker({
	    showOtherMonths : true,
	    selectOtherMonths : true,
	    changeMonth : true,
	    changeYear : true,
	    firstDay : 1,
	    dateFormat : "dd M yy"
	});
	
	$('#toDate').datepicker({
	    showOtherMonths : true,
	    selectOtherMonths : true,
	    changeMonth : true,
	    changeYear : true,
	    firstDay : 1,
	    dateFormat : "dd M yy"
	});
	
});
