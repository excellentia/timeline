/**
 * Common variables.
 */
var JSON_URL = "ajax";

var JSON_RESULT_TYPE = "json";
var TEXT_RESULT_TYPE = "text";

var editIcon = "./pages/inc/icons/edit.png";
var deleteIcon = "./pages/inc/icons/delete.png";
var saveIcon = "./pages/inc/icons/save.png";
var resetIcon = "./pages/inc/icons/reset.png";
var adminIcon = "./pages/inc/icons/admin.png";
var noteIcon = "./pages/inc/icons/note.png";
var emailIcon ="./pages/inc/icons/email.png";
var calendarIcon ="./pages/inc/icons/calendar.png";
var addIcon ="./pages/inc/icons/add.png";

var editTitle = "Edit This Text";
var saveTitle = "Save This Text";
var adminTitle = "Admin User";
var resetTitle = "Reset User Password";
var noteTitle = "Attach A Note To This Item";
var emailTitle ="Send an Email";

var taskSaveTitle = "Save This Task";
var taskEditTitle = "Edit This Task";
var taskAddTitle = "Add Another Task";

var projectDeleteTitle = "Delete This Project";
var activityDeleteTitle = "Delete This Activity";
var userDeleteTitle = "Delete This User";
var stageDeleteTitle = "Delete This Stage";
var taskDeleteTitle = "Delete This Task";

var errorMsgNoData = "Mandatory Data Missing.";

var weekStartDate = null;

var leads = {};
var projectArr = {};
var activityArr = {};
var taskArr = {};

var accOptions = {
	autoHeight : false,
	navigation : true,
	collapsible : true,
	active : 0
};

/**
 * Handles focus on a particular element.
 * 
 * @param elementId
 */
function focus(elementId) {
	var elm = document.getElementById(elementId);
	
	if (elm != null) {
		elm.focus();
	} else {
		$('#'+elementId).focus();
	}
	
};

function populateActivitiesAndTask(projElmId, activityElmId, handleTask) {

	var selectElm = document.getElementById(projElmId);

	if (selectElm != null) {
		var selectedProjectId = getDropDownValueAsInt(projElmId);
		var rowId = selectElm.parentNode.parentNode.id;

		if (selectedProjectId > 0) {
			$.getJSON(
				JSON_URL, {
					operation : "ACTIVTIES",
					id : selectedProjectId
				},
				function(data) {
					var jsonData = data;

					if (jsonData.error) {
						
						displayAlert(jsonData.error);
						
					} else {
					if (activityElmId == null) {
						var actElmId = "activity_"+rowId;
						var activitySelectHTML = "<select size='1' class='timeEntrySelectEdit' name='activity' id='" + actElmId + "' ";
						activitySelectHTML = activitySelectHTML +"onchange=\"populateTasks('"+projElmId+"','" + actElmId+"',null)\">";
						
						var optionHTML = "<option value='0'>Select Activity</option>";

						for ( var j = 0; j < jsonData.activities.length; j++) {
							optionHTML = optionHTML + '<option value="' + jsonData.activities[j].code + '">' + jsonData.activities[j].value + '</option>';
							activityArr[jsonData.activities[j].code] = jsonData.activities[j].value;
						}

						activitySelectHTML = activitySelectHTML + optionHTML + "</select>";

						var selectElm = document.getElementById(projElmId);
						var newRow = selectElm.parentNode.parentNode;
						newRow.cells[2].innerHTML = activitySelectHTML;
						
						var leadNm = trimToNull(leads[selectedProjectId]);
						
						if(leadNm == null) {
							leadNm = "";
						}
						
						newRow.cells[3].innerHTML = leadNm;
						
						//clear tasks
						if(handleTask) {
							newRow.cells[4].innerHTML ="";
						}						

					} else {
						var selectElm = document.getElementById(activityElmId);
						var optionHTML = "<option value='0' selected='selected'>All Activities</option>";

						for ( var j = 0; j < jsonData.activities.length; j++) {
							optionHTML = optionHTML + '<option value="' + jsonData.activities[j].code + '">' + jsonData.activities[j].value + '</option>';
							activityArr[jsonData.activities[j].code] = jsonData.activities[j].value;
						}

						selectElm.innerHTML = optionHTML;
					}
				}
			}, JSON_RESULT_TYPE);
		} else {

			if (activityElmId == null) {
				var newRow = selectElm.parentNode.parentNode;
				newRow.cells[2].innerHTML = "";
				newRow.cells[3].innerHTML = "";

				if(handleTask) {
					newRow.cells[4].innerHTML = "";
				}
			} else {
				var selectElm = document.getElementById(activityElmId);
				var optionHTML = "<option value='0' selected='selected'>All Activities</option>";
				selectElm.innerHTML = optionHTML;
				
				if(handleTask) {
					var taskSelectElm = document.getElementById("searchTaskId");
					var optionHTML = "<option value='0' selected='selected'>All Tasks</option>";
					taskSelectElm.innerHTML = optionHTML;					
				}
			}
		}
	}
}

/**
 * Populates the Activities of a project in a Drop Down.
 * 
 * @param projElmId
 * @param activityElmId
 */
function populateActivities(projElmId, activityElmId) {
	populateActivitiesAndTask(projElmId, activityElmId, false);	
}

/**
 * Populates the Active Tasks of a project and activity combination in a Drop Down.
 * 
 * @param projElmId
 * @param actElmId
 * @param taskElmId
 */
function populateTasks(projElmId, actElmId, taskElmId) {

	var selectElm = document.getElementById(projElmId);

	if (selectElm != null) {
		var selectedProjectId = getDropDownValueAsInt(projElmId);
		
		selectElm = document.getElementById(actElmId);

		if (selectElm != null) {
			var selectedActivityId = getDropDownValueAsInt(actElmId);
			
			if ((selectedProjectId > 0) && (selectedActivityId > 0)) {
			
				$.getJSON(
					JSON_URL, {
						operation : "TASKS",
						projectId : selectedProjectId,
						activityId : selectedActivityId,
						status : 1
					},
					function(data) {
						var jsonData = data;

						if (!jsonData.error) {

							var taskSelectId = null;
							
							if (taskElmId == null) {
								taskSelectId = "task_"+selectedActivityId;
							} else {
								taskSelectId = taskElmId;
							}
							var taskSelectHTML = "<select size='1' class='timeEntrySelectEdit' name='task' id='" + taskSelectId + "'>";
							var optionHTML = "<option value='0'>All Tasks</option>";

							var project = null;
							var activity = null;
							var task = null;
							var taskPresent = true;
							
							for ( var i = 0; i < jsonData.projects.length; i++) {
								project = jsonData.projects[i];
								
								if(project.projectId == selectedProjectId) {
									
									for ( var j = 0; j < project.activities.length; j++) {
										activity = project.activities[j];
										
										if(activity.activityId == selectedActivityId) {
											
											for ( var k = 0; k < activity.tasks.length; k++) {
												
												task = activity.tasks[k];
												optionHTML = optionHTML + '<option value="' + task.taskId + '">' + task.taskName + '</option>';
												taskArr[task.taskId] = task.taskName;
											}
											taskPresent = false;
											break;
										}
									}
									
									break;
								}
							}

							if(!taskPresent) {
								taskSelectHTML = taskSelectHTML + optionHTML + "</select>";
								
								if (taskElmId == null) {
									var newRow = document.getElementById(projElmId).parentNode.parentNode;
									newRow.cells[4].innerHTML = taskSelectHTML;
								} else {
									//search section
									document.getElementById(taskElmId).innerHTML = taskSelectHTML;
								}
							}
						} else {
							//clear any previous tasks drop down
							if("searchActivityId" == actElmId) {
								var optionHTML = "<option value='0' selected='selected'>All Tasks</option>";
								document.getElementById(taskElmId).innerHTML = optionHTML;
							} else {
								var selectElm = document.getElementById(actElmId);
								var newRow = selectElm.parentNode.parentNode;
								newRow.cells[4].innerHTML = "";
								displayAlert("No Task Available For Selected Activity. Setup Tasks First.");
							}
							
						}
				}, JSON_RESULT_TYPE);
			} 
		}
	}
}

/**
 * Utility Method - Create a Valid date object from incoming String.
 * 
 * @param dateText
 * @returns
 */
function getValidDate(dateText) {

	var dat = null;

	if ((dateText != null) && (dateText != "")) {

		if (dateText.indexOf(".") > 0) {
			var day = dateText.substr(0, 2);
			var month = dateText.substr(3, 2);
			var year = dateText.substr(6, 4);

			dat = new Date(year, month - 1, day);
		} else {
			dat = new Date(dateText);
		}
	}

	return dat;
}

/**
 * Utility Method - Create a Valid Float object from incoming String.
 * 
 * @param floatText
 * @returns {Number}
 */
function getValidFloat(floatText) {

	var num = 0;

	if ((floatText != null) && (floatText != "") && (!isNaN(floatText))) {
		floatText = floatText.replace(",", "");

		if (!isNaN(floatText)) {
			num = parseFloat(floatText, 10);
		}
	}

	return num;
}

/**
 * Utility Method - Create a Valid Int object from incoming String.
 * 
 * @param intText
 * @returns {Number}
 */
function getValidInt(intText) {

	var num = 0;

	if ((intText != null) && (intText != "")) {

		intText = intText.replace(",", "");

		if (!isNaN(intText)) {
			num = parseInt(intText, 10);
		}
	}

	return num;
}

/**
 * Utility Method - Create a Valid Int object from incoming String.

 * 
 * @param intText
 * @returns {Number}
 */
function getValidBoolean(strText) {

	var val = false;
	
	var str = trimToNull(""+strText);

	if ((str != null) && (str == "true")) {
		val = true;
	}

	return val;
}


/**
 * Utility Method - Checks if a tetx is nit null and not empty.
 * 
 * @param text
 * @returns {Boolean}
 */
function checkValidText(text) {

	var retVal = false;

	if ((text != null) && (text != "")) {
		retVal = true;
	}

	return retVal;
}

function getPreciseValue(num, precision) {
	var val = num.toFixed(precision);
	return val;
}

/**
 * Utility Method - Trims an incoming String to null.
 * 
 * @param strVal
 * @returns
 */
function trimToNull(strVal) {

	var retVal = null;

	if ((strVal != null) && (strVal.trim() != "") && (strVal.trim() != '') && (strVal != "null")) {
		retVal = strVal.trim();
	}

	return retVal;
}

/**
 * Utility Method - Trims an incoming String to empty("").
 * 
 * @param strVal
 * @returns
 */
function trimToEmpty(strVal) {

	var retVal = trimToNull(strVal);

	if (retVal == null) {
		retVal = "";
	}

	return retVal;
}

/**
 * Utility Method - Displays alert.
 * @param strVal
 */
function displayAlert(strVal) {

	var msg = trimToNull(strVal);

	if (msg != null) {
		alert(msg);
	}
}


function getDropDownValueAsInt(selectElmId) {

	var selectVal = 0;
	var selectElm = $("#" + selectElmId + " :selected");

	if (selectElm != null) {
		selectVal = getValidInt(selectElm.val());
	}

	return selectVal;
}

function getDropDownValueAsText(selectElmId) {

	var selectVal = 0;
	var selectElm = $("#" + selectElmId + " :selected");

	if (selectElm != null) {
		selectVal = trimToNull(selectElm.text());
	}

	return selectVal;

}

function getTextInputValue(inputElmId) {
	var text = null;
	
	if (inputElmId != null) {
		text = trimToNull($("#" + inputElmId).val());
	}
	
	return text;
}

function getBooleanInputValue(inputElmId) {
	var val = false;

	if (inputElmId != null) {
		var text = trimToNull($("#" + inputElmId).val());

		if (text != null) {

			switch (text.toLowerCase()) {
				case "true":
				case "on":
				case "yes":
					val = true;
					break;
				default:
					break;
			}
		}
	}

	return val;
}
