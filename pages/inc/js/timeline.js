/**
 * Common variables.
 */
var JSON_URL = "ajax";
var JSON_RESULT_TYPE = "json";

var TEXT_RESULT_TYPE = "text";
var METRICS_ACTION = "MetricsDetailsAction.action";

var editIcon = "./pages/inc/icons/edit.png";
var deleteIcon = "./pages/inc/icons/delete.png";
var saveIcon = "./pages/inc/icons/save.png";
var resetIcon = "./pages/inc/icons/reset.png";
var adminIcon = "./pages/inc/icons/admin.png";
var noteIcon = "./pages/inc/icons/note.png";
//var calendarIcon = "./pages/inc/images/calendar.gif";
var emailIcon ="./pages/inc/icons/email.png";
var calendarIcon ="./pages/inc/icons/calendar.png";


var editTitle = "Edit This Text";
var saveTitle = "Save This Text";
var adminTitle = "Admin User";
var resetTitle = "Reset User Password";
var noteTitle = "Attach A Note To This Item";
var emailTitle ="Send an Email";

var projectDeleteTitle = "Delete This Project";
var activityDeleteTitle = "Delete This Activity";
var userDeleteTitle = "Delete This User";

var errorMsgNoData = "Mandatory Data Missing.";

var errorStyle = "border: 1px solid #FF0000; background : #FFDCDC; color : #FF0000;";
var successStyle = "border: 1px solid #00A200; background : #C0FFC0; color : #00A200;";
var originalMetricEntryDateStyle = "width: 12.5em; text-align: center; ";
var originalMetricEntryDateTitle = "Click Calendar Icon To Select A Week";
var MIN_INDEX = 0.96;
var MAX_INDEX = 0.99;
var MIN_DEFECT_RATIO = 0.01;

var leads = {};
var activitySums = {};
var weekStartDate = null;

var projectArr = {};
var activityArr = {};

var projectsAvailableJSON = null;

var searchStartDate = null;
var searchEndDate = null;
var searchProjId = null;
var searchActId = null;

var metricEntryDate = null;

var itemCellIndex = 3;
var weekDayCellStart = 4;
var weekDayCellEndPlusOne = 11;
var weeklySumCellIndex = 11;
var weekDayDefaultValue = 0;

var searchStartWeekNum = null;
var searchEndWeekNum = null;
var searchStartYear = null;
var searchEndYear = null;

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
	$("#" + elementId).focus();
};

/**
 * Populates the Activities of a project in a Drop Down.
 * 
 * @param projElmId
 * @param activityElmId
 */
function populateActivities(projElmId, activityElmId) {

	var selectElm = document.getElementById(projElmId);

	if (selectElm != null) {
		var selectedProjectId = 0;

		for ( var i = 0; i < selectElm.options.length; i++) {
			if (selectElm.options[i].selected) {
				selectedProjectId = selectElm.options[i].value;

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

								var activitySelectHTML = "<select size='1' class='timeEntrySelectEdit' name='activity' id='activity_" + selectedProjectId + "'>";
								var optionHTML = "";

								for ( var j = 0; j < jsonData.activities.length; j++) {
									optionHTML = optionHTML + '<option value="' + jsonData.activities[j].code + '">' + jsonData.activities[j].value + '</option>';
									activityArr[jsonData.activities[j].code] = jsonData.activities[j].value;
								}

								activitySelectHTML = activitySelectHTML + optionHTML + "</select>";

								var selectElm = document.getElementById(projElmId);
								var newRow = selectElm.parentNode.parentNode;
								newRow.cells[2].innerHTML = activitySelectHTML;
								newRow.cells[3].innerHTML = leads[selectedProjectId];

							} else {
								var selectElm = document.getElementById(activityElmId);
								var optionHTML = "<option value='0' selected='selected'>All Activities</option>";

								for ( var j = 0; j < jsonData.activities.length; j++) {
									optionHTML = optionHTML + '<option value="' + jsonData.activities[j].code + '">' + jsonData.activities[j].value + '</option>';
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
					} else {
						var selectElm = document.getElementById(activityElmId);
						var optionHTML = "<option value='0' selected='selected'>All Activities</option>";
						selectElm.innerHTML = optionHTML;
					}
				}
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

	if ((floatText != null) && (floatText != "")) {
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
 * Utility Method - Displays alert.
 * @param strVal
 */
function displayAlert(strVal) {

	var msg = trimToNull(strVal);

	if (msg != null) {
		alert(msg);
	}
}
