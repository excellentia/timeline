var TASK_DETAILS_ACTION = "TaskDetails.action";

function getRapidBoardData(formId,selectElmId) {
	
	var projDbId = getDropDownValueAsInt(selectElmId);
			
	if(projDbId > 0) {
		document.getElementById(formId).submit();
	} else {
		displayAlert("Select A Project");
	}			
}

function getDbId(elmTextId, replacment) {
	
	var elmId = 0;
	var str = trimToNull(elmTextId);
	
	if (str != null) {
		str = str.replace(replacment,"");
		if (!isNaN(str)) {
			elmId = getValidInt(str);
		}
	}
	
	return elmId;
}

function modifyTask(taskDbId, newStageDbId) {

	if ((taskDbId > 0) && (newStageDbId > 0)) {

		var jsonData = null;

		$.post(JSON_URL, {
			operation : "MODIFY_TASK_STAGE",
			taskId : taskDbId,
			activityId : newStageDbId
		}, function(data) {

			jsonData = data;

			if (jsonData.error) {
				displayAlert(jsonData.error);
			}
		}, JSON_RESULT_TYPE);
	}
}


function hideTimeEntryOverLay(elmId) {
	document.getElementById(elmId).innerHTML ='';
	document.getElementById('light').style.display='none';
	document.getElementById('fade').style.display='none';	
}
/*
function quickTaskTimeEntry(taskElmId, taskText, taskDbId) {

	if ((taskDbId > 0) && (taskText != null)) {

		var quickEntryHTML = "";
		quickEntryHTML = quickEntryHTML +"<div id='searchBar'>";
		quickEntryHTML = quickEntryHTML +"<div class='weekChoice'>";
		quickEntryHTML = quickEntryHTML +"<input id='weekArea' type='text' class='dateSearch' readonly='readonly' title='Selected Week' disabled='disabled' /> <input id='weekpicker' type='hidden' /> <img id='weekImg'";
		quickEntryHTML = quickEntryHTML +"title='Click To Select Week' alt='Click To Select Week' src='./pages/inc/icons/calendar.png'/>";
		quickEntryHTML = quickEntryHTML +"</div>";
		quickEntryHTML = quickEntryHTML +"</div>";
		
		quickEntryHTML = quickEntryHTML +"<div>";
		quickEntryHTML = quickEntryHTML +"<table style='width: 75%;' id='quickEntryTable'>";
		
		quickEntryHTML = quickEntryHTML +"<colgroup>";
		quickEntryHTML = quickEntryHTML +"<col style='width: 10%' />";
		quickEntryHTML = quickEntryHTML +"<col style='width: 10%' />";
		quickEntryHTML = quickEntryHTML +"<col style='width: 10%' />";
		quickEntryHTML = quickEntryHTML +"<col style='width: 10%' />";
		quickEntryHTML = quickEntryHTML +"<col style='width: 10%' />";
		quickEntryHTML = quickEntryHTML +"<col style='width: 10%' />";
		quickEntryHTML = quickEntryHTML +"<col style='width: 10%' />";
		quickEntryHTML = quickEntryHTML +"<col style='width: 10%' />";
		quickEntryHTML = quickEntryHTML +"<col style='width: 10%' />";
		quickEntryHTML = quickEntryHTML +"<col style='width: 5%' />";
		quickEntryHTML = quickEntryHTML +"<col style='width: 5%' />";
		quickEntryHTML = quickEntryHTML +"</colgroup>";
		
		quickEntryHTML = quickEntryHTML +"<thead>";
		quickEntryHTML = quickEntryHTML +"<tr>";
		quickEntryHTML = quickEntryHTML +"<th>Task</th>";
		quickEntryHTML = quickEntryHTML +"<th id='day1Text'>Mon</th>";
		quickEntryHTML = quickEntryHTML +"<th id='day2Text'>Tue</th>";
		quickEntryHTML = quickEntryHTML +"<th id='day3Text'>Wed</th>";
		quickEntryHTML = quickEntryHTML +"<th id='day4Text'>Thu</th>";
		quickEntryHTML = quickEntryHTML +"<th id='day5Text'>Fri</th>";
		quickEntryHTML = quickEntryHTML +"<th id='day6Text'>Sat</th>";
		quickEntryHTML = quickEntryHTML +"<th id='day7Text'>Sun</th>";
		quickEntryHTML = quickEntryHTML +"<th>Total</th>";
		quickEntryHTML = quickEntryHTML +"<th colspan='2'>&nbsp;</th>";
		quickEntryHTML = quickEntryHTML +"</tr>";
		quickEntryHTML = quickEntryHTML +"</thead>";
		
		quickEntryHTML = quickEntryHTML +"<tbody id='quickEntryTable_body' class='reportBody'>";
		quickEntryHTML = quickEntryHTML +"<tr>";
		quickEntryHTML = quickEntryHTML +"<td>"+ taskText+"</td>";
		
		var selectPrefix = "<input type='text' class='timeEntryEdit' id='" + taskDbId + "_day1' class='timeEntry'onchange=\"updateWeeklySum('" + rowElmId + "')\">";
		
		quickEntryHTML = quickEntryHTML +"<td id='day1Text'>Mon</td>";
		quickEntryHTML = quickEntryHTML +"<td id='day2Text'>Tue</td>";
		quickEntryHTML = quickEntryHTML +"<td id='day3Text'>Wed</td>";
		quickEntryHTML = quickEntryHTML +"<td id='day4Text'>Thu</td>";
		quickEntryHTML = quickEntryHTML +"<td id='day5Text'>Fri</td>";
		quickEntryHTML = quickEntryHTML +"<td id='day6Text'>Sat</td>";
		quickEntryHTML = quickEntryHTML +"<td id='day7Text'>Sun</td>";
		quickEntryHTML = quickEntryHTML +"<td>Total</td>";
		quickEntryHTML = quickEntryHTML +"<td>Total</td>";
		quickEntryHTML = quickEntryHTML +"<td>Total</td>";
		quickEntryHTML = quickEntryHTML +"</tbody>";
		
		quickEntryHTML = quickEntryHTML +"</table>";
		quickEntryHTML = quickEntryHTML +"</div>";
		
		$("#quickTaskTimeEntry").html(quickEntryHTML);
		
	}
}

*/
function saveTaskTime() {

	var jsonData = null;

	$.post(JSON_URL, {
		operation : "MODIFY_TASK_STAGE",
		taskId : taskDbId,
		activityId : newStageDbId
	}, function(data) {

		jsonData = data;

		if (jsonData.error) {
			displayAlert(jsonData.error);
		}
	}, JSON_RESULT_TYPE);
}

function showTaskDetails(taskDbId) {

	if (taskDbId > 0) {
	
		$.post(TASK_DETAILS_ACTION, {
			taskId : taskDbId
		}, function(data) {

			var htmlText = data;

			if (checkValidText(htmlText)) {
				var targetElm = document.getElementById("taskDetails");
				targetElm.innerHTML = htmlText;
				
				document.getElementById('light').style.display='block';
				document.getElementById('fade').style.display='block';

				document.onkeydown = function(evt) {
					evt = evt || window.event;
					if (evt.keyCode == 27) {
						hideTimeEntryOverLay("taskDetails");
					}
				};
				
				$("#light").draggable();
			}
			
		}, TEXT_RESULT_TYPE);
	}
}

$(function() {

	var classNam = 'ui-widget ui-widget-content ui-helper-clearfix ui-corner-all center-text';

	$(".rapidBoardDiv").addClass(classNam);
	$(".stg-column").addClass(classNam);
	$(".stg-column").find(".portlet-header").addClass("ui-widget-header ui-corner-all");

	$("ul.droptrue").addClass("portlet-content sortableUL");
	$("ul.droptrue li").addClass("ui-state-highlight center-text sortableLI");
	
	$("div.colHolder").sortable({
		connectWith: "div.colHolder"
	});
	
	$( ".sortableUL" ).sortable({
		connectWith: ".sortableUL",
		receive: function(event, ui) {
				
			var elmText = ""+$(event.originalEvent.target).attr("id");
			var movedTaskId = getDbId(elmText,"task_");
			
			elmText = ""+$(event.originalEvent.target).parent().parent().children('.portlet-header').attr("id");
			var newStageId = getDbId(elmText,"stage_");
			
			modifyTask(movedTaskId, newStageId);
			
			var sizeElm = $(event.target).parent().find(".sizeDiv");
			var originalSize = getValidInt($(sizeElm).text());
			var taskSize = getValidInt($(event.originalEvent.target).attr("size"));

			if (taskSize > 0) {
			
				//update the size
				var newSize = originalSize + taskSize;
				$(sizeElm).html(newSize);
			}
			
		},
		remove: function( event, ui ) {
			
			var sizeElm = $(event.target).parent().find(".sizeDiv");
			var originalSize = getValidInt($(sizeElm).text());
			var taskSize = getValidInt($(event.originalEvent.target).attr("size"));

			if ((originalSize > 0) && (taskSize > 0)) {
			
				//update the size
				var newSize = originalSize - taskSize;
				$(sizeElm).html(newSize);
			}
			
		}		
	}).disableSelection();
	
	/* double click event */
	$('.sortableLI').dblclick(function() {
		var taskElmId = ""+ $(this).attr('id');
		var taskDbId = getDbId(taskElmId,"task_");
		//quickTaskTimeEntry(taskElmId, taskText, taskDbId);
		showTaskDetails(taskDbId);
	});
	
	$("div.colHolder").disableSelection();
});