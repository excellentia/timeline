var jsonUrl = "ajax";
var editIcon = "./pages/inc/icons/edit.png";
var deleteIcon = "./pages/inc/icons/delete.png";
var saveIcon = "./pages/inc/icons/save.png";
var resetIcon = "./pages/inc/icons/reset.png";
var adminIcon = "./pages/inc/icons/admin.png";

var editTitle = "Edit This Text";
var saveTitle = "Save This Text";
var adminTitle = "admin User";
var resetTitle = "Reset User Password";

var projectDeleteTitle = "Delete This Project";
var activityDeleteTitle = "Delete This Activity";
var userDeleteTitle = "Delete This User";

var errorMsgNoData = "Mandatory Data Missing.";

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

function populateActivities(projElmId, activityElmId) {

	var selectElm = document.getElementById(projElmId);

	if (selectElm != null) {
		var selectedProjectId = 0;

		for ( var i = 0; i < selectElm.options.length; i++) {
			if (selectElm.options[i].selected) {
				selectedProjectId = selectElm.options[i].value;

				if (selectedProjectId > 0) {

					$
							.getJSON(
									jsonUrl,
									{
										operation : "ACTIVTIES",
										id : selectedProjectId
									},
									function(data) {

										var jsonData = data;

										if (jsonData.error) {
											alert(jsonData.error);
										} else {
											if (activityElmId == null) {

												var activitySelectHTML = "<select size='1' class='timeEntrySelectEdit' name='activity' id='activity_"
														+ selectedProjectId + "'>";
												var optionHTML = "";

												for ( var j = 0; j < jsonData.activities.length; j++) {
													optionHTML = optionHTML + '<option value="'
															+ jsonData.activities[j].code + '">'
															+ jsonData.activities[j].value + '</option>';
													activityArr[jsonData.activities[j].code] = jsonData.activities[j].value;
												}

												activitySelectHTML = activitySelectHTML + optionHTML + "</select>";

												var selectElm = document.getElementById(projElmId);
												var newRow = selectElm.parentNode.parentNode;
												newRow.cells[1].innerHTML = activitySelectHTML;
												newRow.cells[2].innerHTML = leads[selectedProjectId];

											} else {
												var selectElm = document.getElementById(activityElmId);
												var optionHTML = "<option value='0' selected='selected'>All Activities</option>";

												for ( var j = 0; j < jsonData.activities.length; j++) {
													optionHTML = optionHTML + '<option value="'
															+ jsonData.activities[j].code + '">'
															+ jsonData.activities[j].value + '</option>';
												}

												selectElm.innerHTML = optionHTML;
											}
										}
									});
				} else {
					if (activityElmId == null) {
						var newRow = selectElm.parentNode.parentNode;
						newRow.cells[1].innerHTML = "";
						newRow.cells[2].innerHTML = "";
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

function updateWeeklySum(rowElmId) {

	if (rowElmId != "") {
		var activeRow = document.getElementById(rowElmId);
		var currSum = 0.0;
		var val;

		for ( var i = 3; i < 10; i++) {
			val = activeRow.cells[i].children[0].value;

			if ((val != null) && (!isNaN(val))) {
				currSum = currSum + parseFloat(val, 10);
			} else {
				activeRow.cells[i].children[0].value = 0;
			}
		}

		activeRow.cells[10].innerHTML = currSum;
	}
}

function deleteTimeEntry(rowElmId, entryDbId) {

	if (rowElmId != "") {

		if (entryDbId > 0) {
			$.post(jsonUrl, {
				operation : "DELETE_TIME_ENTRY",
				entryId : entryDbId
			}, function(data) {

				jsonData = data;

				if (jsonData.error) {
					alert(jsonData.error);
				} else {
					var activityRow = document.getElementById(rowElmId);
					activityRow.parentNode.removeChild(activityRow);
				}
			}, "json");
		} else if (entryDbId == 0) {
			var activityRow = document.getElementById(rowElmId);
			activityRow.parentNode.removeChild(activityRow);
		}
	}
}

function editTimeEntry(rowElmId, projDbId, actDbId, entryDbId) {

	if (rowElmId != "") {
		var activityRow = document.getElementById(rowElmId);

		// project
		{
			var selectId = "project_" + entryDbId;

			var projectHTML = "<select size='1' class='timeEntrySelectEdit' name='project' id='" + selectId
					+ "' onchange=\"populateActivities('" + selectId
					+ "', null)\"><option value='0'>Select Project...</option>";
			var optionHTML = "";

			if ((projectsAvailableJSON != null) && (projectsAvailableJSON.projects != null)) {
				for ( var i = 0; i < projectsAvailableJSON.projects.length; i++) {
					optionHTML = optionHTML + "<option value = '" + projectsAvailableJSON.projects[i].code + "'";

					if (projDbId == projectsAvailableJSON.projects[i].code) {
						optionHTML = optionHTML + " selected='selected' ";
					}

					optionHTML = optionHTML + ">" + projectsAvailableJSON.projects[i].value + "</option>";
					leads[projectsAvailableJSON.projects[i].code] = projectsAvailableJSON.projects[i].leadname;
					projectArr[projectsAvailableJSON.projects[i].code] = projectsAvailableJSON.projects[i].value;
				}
			}

			projectHTML = projectHTML + optionHTML + "</select>";
			activityRow.cells[0].innerHTML = projectHTML;
		}

		// activities
		{
			var activitySelectHTML = "<select size='1' name='activity' class='timeEntrySelectEdit' id='activity_"
					+ actDbId + "'>";
			var optionHTML = "";

			$
					.getJSON(
							jsonUrl,
							{
								operation : "ACTIVTIES",
								id : projDbId
							},
							function(data) {

								var activtiesAvailableJSON = data;

								for ( var i = 0; i < activtiesAvailableJSON.activities.length; i++) {
									optionHTML = optionHTML + "<option value='"
											+ activtiesAvailableJSON.activities[i].code + "'";

									if (actDbId == activtiesAvailableJSON.activities[i].code) {
										optionHTML = optionHTML + " selected='selected' ";
									}

									optionHTML = optionHTML + ">" + activtiesAvailableJSON.activities[i].value
											+ "</option>";
									activityArr[activtiesAvailableJSON.activities[i].code] = activtiesAvailableJSON.activities[i].value;
								}

								activitySelectHTML = activitySelectHTML + optionHTML + "</select>";
								activityRow.cells[1].innerHTML = activitySelectHTML;
							});
		}

		// days
		{
			var selectPrefix = "<input type='text' class='timeEntryEdit' value='";
			var selectSufix = "' id='" + entryDbId + "_day1' class='timeEntry'onchange=\"updateWeeklySum('" + rowElmId
					+ "')\">";

			for ( var i = 3; i < 10; i++) {
				activityRow.cells[i].innerHTML = selectPrefix + activityRow.cells[i].innerHTML + selectSufix;
			}
		}

		// save button
		{
			var saveHTML = "<img alt='Save' class='icon' title='" + saveTitle + "' src='" + saveIcon
					+ "' onclick=\"saveTimeEntry('" + rowElmId + "'," + entryDbId + ")\" align='middle'>";
			activityRow.cells[11].innerHTML = saveHTML;
		}
	}
}

function saveTimeEntry(rowElmId, entryDbId) {

	if (rowElmId != "") {

		var entryRow = document.getElementById(rowElmId);

		// get the selected project
		var projDbId = 0;
		var projects = entryRow.cells[0].children[0].options;

		for ( var i = 0; i < projects.length; i++) {
			if (projects[i].selected) {
				projDbId = projects[i].value;
			}
		}

		// get the selected activity
		var actDbId = 0;
		var activities = entryRow.cells[1].children[0].options;

		for ( var i = 0; i < activities.length; i++) {
			if (activities[i].selected) {
				actDbId = activities[i].value;
			}
		}

		// get the weekly entries
		var timeData = {};
		var sum = 0;

		for ( var i = 3; i < 10; i++) {
			timeData[i - 3] = entryRow.cells[i].children[0].value;
			sum = sum + parseInt(timeData[i - 3], 10);
		}

		var weekStart = weekStartDate;

		if (entryDbId > 0) {
			var startDateElm = document.getElementById("startDate_" + entryDbId);

			if (startDateElm != null) {
				weekStart = startDateElm.value;
			}
		}

		if ((projDbId > 0) && (actDbId > 0) && (sum > 0) && ((weekStart != null) || (entryDbId > 0))) {

			$.post(jsonUrl, {
				operation : "SAVE_TIME_ENTRY",
				entryId : entryDbId,
				projectId : projDbId,
				activityId : actDbId,
				weekStartDate : weekStart,
				day1 : timeData[0],
				day2 : timeData[1],
				day3 : timeData[2],
				day4 : timeData[3],
				day5 : timeData[4],
				day6 : timeData[5],
				day7 : timeData[6]
			}, function(data) {

				var jsonData = data;

				if (jsonData.error) {
					alert(jsonData.error);
				} else {

					var activityRow = document.getElementById(rowElmId);

					activityRow.cells[0].innerHTML = projectArr[projDbId];
					activityRow.cells[1].innerHTML = activityArr[actDbId];
					activityRow.cells[3].innerHTML = timeData[0];
					activityRow.cells[4].innerHTML = timeData[1];
					activityRow.cells[5].innerHTML = timeData[2];
					activityRow.cells[6].innerHTML = timeData[3];
					activityRow.cells[7].innerHTML = timeData[4];
					activityRow.cells[8].innerHTML = timeData[5];
					activityRow.cells[9].innerHTML = timeData[6];
					activityRow.className = "leadColumn";

					var editHTML = "<img alt='Edit' class='icon' title='" + editTitle + "' src='" + editIcon
							+ "' onclick=\"editTimeEntry('" + rowElmId + "'," + projDbId + "," + actDbId + ","
							+ jsonData.code + ")\" align='middle'>";
					activityRow.cells[11].innerHTML = editHTML;

				}
			}, "json");
		}
	}
}

/**
 * Create a new table row.
 * 
 * @param tableID Id of the target table .
 */
function addActivityRow(tableID) {

	var selectedWeek = document.getElementById('weekArea');

	if ((selectedWeek.value == null) || (selectedWeek.value == "")) {
		alert("Select Week First");
	} else {

		$
				.getJSON(
						jsonUrl,
						{
							operation : "project"
						},
						function(data) {

							var jsonData = data;

							if (jsonData.error) {
								alert(jsonData.error);
							} else {
								var table = document.getElementById(tableID);
								var tbody = table.tBodies[0];
								var rowCount = tbody.rows.length;
								var newId = rowCount;

								var row = tbody.insertRow(rowCount);
								var rowId = "entry_" + newId;
								row.id = rowId;

								var selectId = "project_" + newId;

								var projectHTML = "<select class='timeEntrySelectEdit' size='1' id='" + selectId
										+ "' onchange=\"populateActivities('" + selectId
										+ "',null)\"><option value='0'>Select Project...</option>";
								var optionHTML = '';

								for ( var i = 0; i < jsonData.projects.length; i++) {
									optionHTML = optionHTML + '<option value="' + jsonData.projects[i].code + '">'
											+ jsonData.projects[i].value + '</option>';
									leads[jsonData.projects[i].code] = jsonData.projects[i].leadname;
									projectArr[jsonData.projects[i].code] = jsonData.projects[i].value;
								}

								projectHTML = projectHTML + optionHTML + "</select>";

								var timeWeekDayHTML = "<input value='0' class='timeEntryEdit' type='text' onchange=\"updateWeeklySum('"
										+ rowId + "')\">";
								var timeWeekEndHTML = "<input value='0' class='timeEntryEdit weekEnd' type='text' onchange=\"updateWeeklySum('"
										+ rowId + "')\">";
								var deleteHTML = "<img alt='Delete' class='icon' title='Delete this row' src='"
										+ deleteIcon + "' onclick=\"deleteTimeEntry('" + rowId + "'," + 0
										+ ")\" align='middle'>";
								var saveHTML = "<img alt='Save' class='icon' title='" + saveTitle + "' src='"
										+ saveIcon + "' onclick=\"saveTimeEntry('" + rowId + "',0)\" align='middle'>";
								var cellNum = 0;

								/**
								 * Create Project Column
								 */
								var newcell = row.insertCell(cellNum++);
								newcell.innerHTML = projectHTML;

								/**
								 * Create Activity Column
								 */
								newcell = row.insertCell(cellNum++);

								/**
								 * Create Lead Column
								 */
								newcell = row.insertCell(cellNum++);
								newcell.className = "leadColumn";

								/**
								 * Week Day
								 */
								for ( var i = 1; i <= 5; i++) {
									newcell = row.insertCell(cellNum++);
									newcell.innerHTML = timeWeekDayHTML;
								}

								/**
								 * Week End
								 */
								for ( var i = 1; i <= 2; i++) {
									newcell = row.insertCell(cellNum++);
									newcell.innerHTML = timeWeekEndHTML;
								}

								/**
								 * Create Total Column
								 */
								newcell = row.insertCell(cellNum++);
								newcell.className = "rowTotal";
								newcell.innerHTML = 0;

								/**
								 * Create Edit Column
								 */
								newcell = row.insertCell(cellNum++);
								newcell.align = "center";
								newcell.innerHTML = saveHTML;

								/**
								 * Create Delete Column
								 */
								newcell = row.insertCell(cellNum++);
								newcell.align = "center";
								newcell.innerHTML = deleteHTML;
							}
						});
	}
}

function deleteProject(projElementId, projDbId) {

	if (projDbId > 0) {

		var jsonData = null;

		$.post(jsonUrl, {
			operation : "DELETE_PROJECT",
			id : projDbId
		}, function(data) {

			jsonData = data;

			if (jsonData.error) {
				alert(jsonData.error);
			} else {
				var project = document.getElementById(projElementId);
				project.parentNode.removeChild(project);
			}
		}, "json");
	}
}

function editLead(leadElmId, projDbId) {

	if (projDbId > 0) {

		$.getJSON(jsonUrl, {
			operation : "LEAD"
		}, function(data) {

			jsonData = data;

			if (jsonData.error) {
				alert(jsonData.error);
			} else {

				var row = document.getElementById(leadElmId);
				var leadId = projDbId + "_ajaxLeadEdit";

				var leadHTML = "<select size='1' name='leads' class='leadAreaEdit' id='" + leadId + "'>";
				var optionHTML = '';

				for ( var i = 0; i < jsonData.admins.length; i++) {
					optionHTML = optionHTML + '<option value="' + jsonData.admins[i].code + '">'
							+ jsonData.admins[i].value + '</option>';
				}

				leadHTML = leadHTML + optionHTML + "</select>";
				row.cells[0].innerHTML = leadHTML;
				row.cells[0].className = null;

				var saveHTML = "<img alt='Save' align='middle' class='icon' title='Save This Text'";
				saveHTML = saveHTML + "src='" + saveIcon + "' onclick=\"saveLead(" + projDbId + ",'" + leadId
						+ "')\" />";

				row.cells[1].innerHTML = saveHTML;
			}
		});
	}
}

function saveLead(projDbId, leadElmId) {

	if (projDbId > 0) {

		var selectElm = document.getElementById(leadElmId);
		var leadId = 0;
		var leadName = '';

		for ( var i = 0; i < selectElm.options.length; i++) {
			if (selectElm.options[i].selected) {
				leadId = selectElm.options[i].value;
				leadName = selectElm.options[i].innerHTML;
			}
		}

		if (leadId > 0) {
			$.post(jsonUrl, {
				operation : "SAVE_LEAD",
				id : projDbId,
				refId : leadId
			}, function(data) {

				jsonData = data;

				if (jsonData.error) {
					alert(jsonData.error);
				} else {
					var rowId = selectElm.parentNode.parentNode.id;
					var row = document.getElementById(rowId);
					var projLeadId = "project_" + projDbId + "_lead";
					row.cells[0].innerHTML = leadName;
					row.cells[0].className = "leadArea";
					row.cells[1].innerHTML = "<img alt=\"Edit Lead\" align=\"middle\" class=\"icon\" title=\""
							+ editTitle + "\" src=\"" + editIcon + "\" onclick=\"editLead('" + projLeadId + "',"
							+ projDbId + ")\"/>";

				}
			}, "json");
		} else {
			alert("Admin cannot be Lead");
		}
	}
}

function deleteActivity(actElementId, actDbId) {

	if (actDbId > 0) {
		$.post(jsonUrl, {
			operation : "DELETE_ACTIVITY",
			id : actDbId
		}, function(data) {

			jsonData = data;

			if (jsonData.error) {
				alert(jsonData.error);
			} else {
				var activity = document.getElementById(actElementId);
				activity.parentNode.removeChild(activity);
			}
		}, "json");
	} else {

		var actRow = document.getElementById(actElementId);
		actRow.parentNode.removeChild(actRow);
	}

}
function saveActivity(actElmId, actDbId, projDbId) {

	if (actElmId != "") {
		var actRow = document.getElementById(actElmId);
		var actInput = actRow.children[0].children[0];
		var text = actInput.value;

		if (text != "") {
			$.post(jsonUrl, {
				operation : "SAVE_ACTIVITY",
				id : projDbId,
				refId : actDbId,
				text : text
			}, function(data) {

				jsonData = data;

				if (jsonData.error) {
					alert(jsonData.error);
				} else {
					var actId = "activity_" + jsonData.code;
					actRow.id = actId;
					actRow.cells[0].innerHTML = jsonData.value;
					actRow.cells[0].className = "activityArea";

					actRow.children[1].innerHTML = "<img alt='Edit' align='middle' class='icon' title='" + editTitle
							+ "' src='" + editIcon + "' onclick=\"editActivity('" + actId + "'," + jsonData.code + ","
							+ projDbId + ")\" />";
					actRow.children[2].innerHTML = "<img alt='Delete' align='middle' class='icon' title='"
							+ activityDeleteTitle + "' src='" + deleteIcon + "' onclick=\"deleteActivity('" + actId
							+ "'," + jsonData.code + ")\"/>";
				}
			}, "json");
		}
	}
}

function editActivity(actElmId, actDbId, projDbId) {

	var actRow = document.getElementById(actElmId);
	actRow.cells[0].className = null;
	var oldValue = actRow.cells[0].innerHTML;
	var actInput = "<input type='text' name='activityText' value='" + oldValue + "' class='activityAreaEdit' />";
	actRow.cells[0].innerHTML = actInput;
	actRow.cells[0].children[0].focus();

	actRow.children[1].innerHTML = "<img alt='Save' align='middle' class='icon' title='" + saveTitle + "' src='"
			+ saveIcon + "' onclick=\"saveActivity('" + actElmId + "'," + actDbId + "," + projDbId + ")\"/>";
	actRow.children[2].innerHTML = "<img alt='Delete' align='middle' class='icon' title='" + activityDeleteTitle
			+ "' src='" + deleteIcon + "' onclick=\"deleteActivity('" + actId + "'," + actDbId + ")\"/>";
}

function addNewActivity(tableID, projDbId) {

	var table = document.getElementById(tableID);
	var rowCount = table.rows.length;
	var newId = rowCount + 1;

	newId = 'activity_' + projDbId + '_' + newId;
	var row = table.insertRow(rowCount - 1);
	row.id = newId;

	var activityHTML = "<input type='text' value='' class='activityAreaEdit' />";
	var saveHTML = "<img alt='Save' align='middle' class='icon' title='" + saveTitle + "' src='" + saveIcon
			+ "' onclick=\"saveActivity('" + newId + "',0," + projDbId + ")\"/>";
	var deleteHTML = "<img alt='Delete' align='middle' class='icon' title='" + activityDeleteTitle + "' src='"
			+ deleteIcon + "' onclick=\"deleteActivity('" + newId + "',0)\"/>";

	var cellNum = 0;

	/**
	 * Create Activity Column
	 */
	var newcell = row.insertCell(cellNum++);
	newcell.innerHTML = activityHTML;

	/**
	 * Create Save Column
	 */
	newcell = row.insertCell(cellNum++);
	newcell.innerHTML = saveHTML;
	newcell.align = "center";

	/**
	 * Create Delete Column
	 */
	newcell = row.insertCell(cellNum);
	newcell.innerHTML = deleteHTML;
	newcell.align = "center";

	row.cells[0].children[0].focus();

}

function saveProject(divID, projDbId, projNameId) {

	var projText = $("#" + projNameId).val();

	if (projText != "") {
		var jsonData = null;

		$
				.post(
						jsonUrl,
						{
							operation : "SAVE_PROJECT",
							id : projDbId,
							text : projText
						},
						function(data) {

							jsonData = data;

							if (jsonData.error) {
								alert(jsonData.error);
							} else {
								var projectId = jsonData.code;
								var projectText = jsonData.value;

								if (projDbId <= 0) {

									var projRowId = 'project_' + projectId + '_title';

									var newHTML = "<table class='projectTable' id='"
											+ projectText
											+ "'><colgroup><col style='width: 88%' /><col style='width: 6%' /><col style='width: 6%' /></colgroup>";
									newHTML = newHTML + "<tbody>";
									newHTML = newHTML + "<tr id='" + projRowId + "'>";
									newHTML = newHTML + "<td class='projectArea'>" + projectText + "</td>";
									newHTML = newHTML
											+ "<td align='center'><img alt='Edit' align='middle' class='icon' title='"
											+ editTitle + "' src='" + editIcon + "' onclick=\"editProject('"
											+ projRowId + "'," + projectId + ")\" /></td>";
									newHTML = newHTML
											+ "<td align='center'><img alt='Delete' align='middle' class='icon' title='"
											+ projectDeleteTitle + "' src='" + deleteIcon
											+ "' onclick=\"deleteProject('" + projectText + "'," + projectId
											+ ")\" /></td>";
									newHTML = newHTML + "</tr>";

									var projLeadId = projectText + "_lead";
									newHTML = newHTML + "<tr id='" + projLeadId + "'>";
									newHTML = newHTML + "<td class='leadArea'>Please select...</td>";
									newHTML = newHTML
											+ "<td align='center' colspan='2'><img alt='Edit Lead' align='middle' class='icon' title='"
											+ editTitle + "' src='" + editIcon + "' onclick=\"editLead('" + projLeadId
											+ "'," + projectId + ")\" /></td>";
									newHTML = newHTML + "</tr>";

									newHTML = newHTML + "</tbody>";
									newHTML = newHTML
											+ "<tfoot><tr><td colspan='3' align='right'><input type='button' value='Add Activity' class='button' onclick=\"addNewActivity('"
											+ projectText + "'," + projectId + ")\" /></td></tfoot>";
									newHTML = newHTML + "</table>";

									$("#" + divID).append(newHTML);
									$("#" + projNameId).val("");
								} else {
									var row = document.getElementById(divID);
									row.cells[0].innerHTML = projectText;
									var editHTML = "<img alt='Edit' align='middle' class='icon' title='" + editTitle
											+ "' src='" + editIcon + "' onclick=\"editProject('" + divID + "',"
											+ projectId + ")\" />";
									row.cells[1].innerHTML = editHTML;
								}
							}
						}, "json");
	} else {
		alert(errorMsgNoData);
	}
}

function addNewUser(tableID) {

	var table = document.getElementById(tableID);
	var rowCount = table.rows.length;

	var row = table.insertRow(rowCount - 1);
	var newId = rowCount + 1;
	newId = "ajaxEditUser_" + newId;

	row.id = newId;

	var userIdHTML = "<input type='text' class='newUserEdit' maxlength='10'/>";
	var firstNameHTML = "<input type='text' class='newUserEdit'/>";
	var lastNameHTML = "<input type='text' class='newUserEdit'/>";
	var deleteUserHTML = "<img alt='Delete' align='middle' class='icon' title='" + userDeleteTitle + "' src='"
			+ deleteIcon + "' onclick=\"deleteUser('" + newId + "'," + 0 + ")\"/>";

	var saveUserHTML = "<img alt='Save' align='middle' class='icon' title='" + saveTitle + "' src='" + saveIcon
			+ "' onclick=\"saveUser('" + newId + "',0)\"/>";

	var cellNum = 0;

	/**
	 * Create User ID Column
	 */
	var newcell = row.insertCell(cellNum++);
	newcell.innerHTML = userIdHTML;
	newcell.align = "center";

	/**
	 * Create First Name Column
	 */
	var newcell = row.insertCell(cellNum++);
	newcell.innerHTML = firstNameHTML;
	newcell.align = "center";

	/**
	 * Create Last Name Column
	 */
	var newcell = row.insertCell(cellNum++);
	newcell.innerHTML = lastNameHTML;
	newcell.align = "center";

	/**
	 * Create Admin Column
	 */
	var newcell = row.insertCell(cellNum++);

	/**
	 * Create Edit Column
	 */
	newcell = row.insertCell(cellNum++);
	newcell.innerHTML = saveUserHTML;
	newcell.align = "center";

	/**
	 * Create Reset Column
	 */
	newcell = row.insertCell(cellNum++);
	newcell.align = "center";

	/**
	 * Create Delete Column
	 */
	newcell = row.insertCell(cellNum++);
	newcell.innerHTML = deleteUserHTML;
	newcell.align = "center";

	/**
	 * Focus
	 */
	row.cells[0].children[0].focus();
}

function editProject(projTitleId, projDbId) {

	var row = document.getElementById(projTitleId);
	var textId = projDbId + "_ajaxEdit";
	var oldValue = row.cells[0].innerHTML;

	var projectHTML = "<input type='text' id='" + textId + "' class='projectAreaEdit' value='" + oldValue + "' />";

	row.cells[0].innerHTML = projectHTML;

	var saveHTML = "<img alt='Save' align='middle' class='icon' title='Save This Text'";
	saveHTML = saveHTML + "src='" + saveIcon + "' onclick=\"saveProject('" + projTitleId + "'," + projDbId + ",'"
			+ textId + "')\" />";

	row.cells[1].innerHTML = saveHTML;
}

function deleteUser(userElmId, userDbId) {

	if (userDbId > 0) {
		$.post(jsonUrl, {
			operation : "DELETE_USER",
			id : userDbId
		}, function(data) {

			jsonData = data;

			if (jsonData.error) {
				alert(jsonData.error);
			} else {
				var userRow = document.getElementById(userElmId);
				userRow.parentNode.removeChild(userRow);
			}
		}, "json");
	} else if (userDbId == 0) {
		var userRow = document.getElementById(userElmId);
		userRow.parentNode.removeChild(userRow);
	} else {
		alert('Cannot Remove Admin');
	}
}

function editUser(userElmId, userDbId) {

	var row = document.getElementById(userElmId);
	var field = "<input type='text' class='newUserEdit'/>";

	var oldValue = row.cells[0].innerHTML;
	row.cells[0].innerHTML = field;
	row.cells[0].children[0].value = oldValue;
	row.cells[0].children[0].focus();

	oldValue = row.cells[1].innerHTML;
	row.cells[1].innerHTML = field;
	row.cells[1].children[0].value = oldValue;

	oldValue = row.cells[2].innerHTML;
	row.cells[2].innerHTML = field;
	row.cells[2].children[0].value = oldValue;

	var adminHTML = "<input type='checkbox' ";
	row.cells[3].align = "center";

	if (row.cells[3].children[0] != null) {
		adminHTML = adminHTML + "checked ='true'";
	}

	adminHTML = adminHTML + " >";
	row.cells[3].innerHTML = adminHTML;

	var saveHTML = "<img alt='Save' align='middle' class='icon' title='Save This Text' src='" + saveIcon
			+ "' onclick=\"saveUser('" + userElmId + "'," + userDbId + ")\" />";

	row.cells[4].innerHTML = saveHTML;
}

function saveUser(userElmId, userDbId) {

	if (userElmId != null) {
		var userRow = document.getElementById(userElmId);
		var admin = false;

		if (userRow.children[3].children[0] != null) {
			admin = userRow.children[3].children[0].checked;
		}

		var userId = userRow.children[0].children[0].value;
		var fName = userRow.children[1].children[0].value;
		var lName = userRow.children[2].children[0].value;

		if ((userId != "") && (fName != "") && (lName != "")) {
			$.post(jsonUrl, {
				operation : "SAVE_USER",
				id : userDbId,
				USER_ID : userId,
				FIRST_NAME : fName,
				LAST_NAME : lName,
				ADMIN : admin
			}, function(data) {

				jsonData = data;

				if (jsonData.error) {
					alert(jsonData.error);
				} else {
					var userRow = document.getElementById(userElmId);

					userRow.cells[0].innerHTML = jsonData.userId;
					userRow.cells[1].innerHTML = jsonData.firstName;
					userRow.cells[2].innerHTML = jsonData.lastName;

					var adminHTML = "";

					if (jsonData.admin == "true") {
						adminHTML = "<img alt='Admin' align='middle' class='icon' title='" + adminTitle + "' src=\""
								+ adminIcon + "\"/>";
					}

					if (userRow.children[3].children[0] != null) {
						userRow.children[3].children[0].parentNode.removeChild(userRow.children[3].children[0]);
					}

					userRow.children[3].innerHTML = adminHTML;

					var editHTML = "<img alt='Edit' align='middle' class='icon' title='" + editTitle + "' src='"
							+ editIcon + "' onclick=\"editUser('" + userElmId + "'," + jsonData.id + ")\" />";
					userRow.children[4].innerHTML = editHTML;

					var resetHTML = "<img alt='Reset' align='middle' class='icon' title='" + resetTitle + "' src='"
							+ resetIcon + "' onclick=\"resetUser('user_" + jsonData.id + "'," + jsonData.id + ")\"/>";
					userRow.children[5].innerHTML = resetHTML;

					var saveHTML = "<img alt='Delete' align='middle' class='icon' title='" + userDeleteTitle
							+ "' src='" + deleteIcon + "' onclick=\"deleteUser('" + userElmId + "', " + jsonData.id
							+ ")\" />";
					userRow.children[6].innerHTML = saveHTML;
				}
			}, "json");
		} else {
			alert(errorMsgNoData);
		}

	}
}

function resetUser(userElmId, userDbId) {

	if (userDbId > 0) {

		$.post(jsonUrl, {
			operation : "RESET_USER",
			id : userDbId
		}, function(data) {

			jsonData = data;

			if (jsonData.error) {
				alert(jsonData.error);
			} else {
				alert(jsonData.message);
			}
		}, "json");
	}
}

function editAccountSettings(rowElmId, userDbId) {

	if ((rowElmId != "") && (userDbId > 0)) {

		var selectedRow = document.getElementById(rowElmId);
		selectedRow.cells[1].innerHTML = "<input type='text' value='" + selectedRow.cells[1].innerHTML
				+ "' class='accountAreaEdit' maxlength='20'/>";
		selectedRow.cells[2].innerHTML = "<img alt='Save' align='middle' class='icon' title='" + saveTitle + "' src='"
				+ saveIcon + "' onclick=\"saveAccountSettings('" + rowElmId + "'," + userDbId + ")\" />";

	} else {
		alert("Editing Not Allowed.");
	}
}

function saveAccountSettings(elmId, userDbId) {

	if ((elmId != null) && (userDbId > 0)) {
		var selectedRow = document.getElementById(elmId);
		var userData = selectedRow.cells[1].children[0].value;
		var oprn = "MODIFY_USER";

		if ((elmId == "QUESTION") || (elmId == "ANSWER")) {
			oprn = "MODIFY_USER_PREF";
		}

		if (userData != "") {
			$.post(jsonUrl, {
				operation : oprn,
				id : userDbId,
				field : elmId,
				text : userData
			}, function(data) {

				var jsonData = data;

				if (jsonData.error) {
					alert(jsonData.error);
				} else {
					var selectedRow = document.getElementById(elmId);
					selectedRow.cells[1].children[0].parentNode.removeChild(selectedRow.cells[1].children[0]);
					var savedValue = null;
					var menuName = document.getElementById("userNameMenu");

					if ("USER_ID" == elmId) {
						savedValue = jsonData.userId;
					} else if ("FIRST_NAME" == elmId) {
						savedValue = jsonData.firstName;
						menuName.innerHTML = jsonData.userName;
					} else if ("LAST_NAME" == elmId) {
						savedValue = jsonData.lastName;
						menuName.innerHTML = jsonData.userName;
					} else if ("PASSWORD" == elmId) {
						savedValue = jsonData.password;
					} else if ("QUESTION" == elmId) {
						savedValue = jsonData.question;
					} else if ("ANSWER" == elmId) {
						savedValue = jsonData.answer;
					}

					selectedRow.cells[1].innerHTML = savedValue;

					var editHTML = "<img alt='Edit' align='middle' class='icon' title='" + editTitle + "' src='"
							+ editIcon + "' onclick=\"editAccountSettings('" + elmId + "'," + userDbId + ")\"  />";
					selectedRow.cells[2].innerHTML = editHTML;
				}
			}, "json");
		}
	}
}

function searchEntries(divId, tbodyElmId, accordId, adminUser) {

	var startDate = searchStartDate;
	var endDate = searchEndDate;
	var projId = document.getElementById("searchProjectId").value;
	var actId = document.getElementById("searchActivityId").value;

	var usrElm = document.getElementById("searchUserId");
	var userId = 0;

	if (usrElm != null) {
		userId = usrElm.value;
	}

	// clear the new entry table
	var entryTableBody = document.getElementById("timeTable_body");

	if ((entryTableBody != null) && (entryTableBody.hasChildNodes())) {
		$("#timeTable_body").html();
	}

	if ((tbodyElmId != null) && (tbodyElmId != "")) {

		$
				.getJSON(
						jsonUrl,
						{
							operation : "SEARCH_ENTRIES",
							weekStartDate : startDate,
							weekEndDate : endDate,
							startWeekNum : searchStartWeekNum,
							endWeekNum : searchEndWeekNum,
							startYear : searchStartYear,
							endYear : searchEndYear,
							projectId : projId,
							activityId : actId,
							userDbId : userId
						},
						function(data) {

							var jsonData = data;

							if (jsonData.error) {
								$("#" + divId).html();
								$("#" + divId).html("<p class='error'>No Data Found</p>");
							} else {

								if (jsonData.weeklyData.length > 0) {
									var accDivHTML = "<p>Search Results</p><div style='margin-bottom: 2em;'><div id='"
											+ accordId + "'>";
									var weekHTML = "";
									var currWeekData = null;
									var dayLabel = null;
									var entry = null;
									var entryRowId = null;

									for ( var i = 0; i < jsonData.weeklyData.length; i++) {
										currWeekData = jsonData.weeklyData[i];
										weekHTML = "<h3><a href='#week_" + currWeekData.weekId + "'>"
												+ currWeekData.weekName + "</a></h3>";
										weekHTML = weekHTML + "<div>";
										weekHTML = weekHTML + "<table style='width: 100%;' id='timeTable_"
												+ currWeekData.weekId + "'>";
										weekHTML = weekHTML
												+ "<colgroup><col style='width: 11%' /><col style='width: 11%' /><col style='width: 11%' /><col style='width: 8%' /><col style='width: 8%' /><col style='width: 8%' /><col style='width: 8%' /><col style='width: 8%' /><col style='width: 8%' /><col style='width: 8%' /><col style='width: 5%' /><col style='width: 3%' /><col style='width: 3%' /></colgroup>";

										// head
										weekHTML = weekHTML
												+ "<thead><tr><th>Project</th><th>Activity</th><th>Lead</th>";

										for ( var j = 0; j < currWeekData.weekDayLabels.length; j++) {
											dayLabel = currWeekData.weekDayLabels[j];
											weekHTML = weekHTML + "<th id=day_" + j + "_Text>" + dayLabel.label
													+ "</th>";
										}

										weekHTML = weekHTML + "<th>Total</th><th colspan='2'>&nbsp;</th></tr></thead>";

										// body
										weekHTML = weekHTML + "<tbody id='" + tbodyElmId + "_" + currWeekData.weekId
												+ "' class='reportBody'>";

										for ( var k = 0; k < currWeekData.entryData.length; k++) {
											entry = currWeekData.entryData[k];
											entryRowId = "entry_" + entry.entryId;

											weekHTML = weekHTML + "<tr id='" + entryRowId + "' class='leadColumn'>";
											weekHTML = weekHTML + "<td>" + entry.projectName
													+ "<input type='hidden' id='startDate_" + entry.entryId
													+ "' value='" + entry.startDate + "' /></td>";
											weekHTML = weekHTML + "<td>" + entry.activityName + "</td>";
											weekHTML = weekHTML + "<td>" + entry.leadName + "</td>";

											weekHTML = weekHTML + "<td>" + entry.day1 + "</td>";
											weekHTML = weekHTML + "<td>" + entry.day2 + "</td>";
											weekHTML = weekHTML + "<td>" + entry.day3 + "</td>";
											weekHTML = weekHTML + "<td>" + entry.day4 + "</td>";
											weekHTML = weekHTML + "<td>" + entry.day5 + "</td>";
											weekHTML = weekHTML + "<td class='weekEnd'>" + entry.day6 + "</td>";
											weekHTML = weekHTML + "<td class='weekEnd'>" + entry.day7 + "</td>";
											weekHTML = weekHTML + "<td class='rowTotal'>" + entry.total + "</td>";

											weekHTML = weekHTML
													+ "<td align='center'><img alt='Edit' class='icon' title='"
													+ editTitle + "' src='" + editIcon + "' onclick=\"editTimeEntry('"
													+ entryRowId + "'," + entry.projectId + "," + entry.activityId
													+ "," + entry.entryId + ")\" align='middle'></td>";
											weekHTML = weekHTML
													+ "<td align='center'><img alt='Delete' class='icon' title='Delete this row' src='"
													+ deleteIcon + "' onclick=\"deleteTimeEntry('" + entryRowId + "',"
													+ entry.entryId + ")\" align='middle'></td>";

										}
										weekHTML = weekHTML + "</tbody></table></div>";
										accDivHTML = accDivHTML + weekHTML;
									}
									// clear existing and add results message
									$("#" + divId).html();
									$("#" + divId).html(accDivHTML + "</div></div>");
									$("#" + accordId).accordion(accOptions);
								}
							}
						});
	}
}

/**
 * Get Report Details.
 */
function viewReportDetails(projId) {

	if (!isNaN(projId)) {

		$
				.getJSON(
						jsonUrl,
						{
							operation : "REPORT_DETAIL",
							projectId : projId
						},
						function(data) {

							var jsonData = data;

							if (jsonData.error) {
								$("#reportDetails").html("<p class='error'>No Data Found</p>");
							} else {

								if (jsonData.details.length > 0) {

									var detailHTML = "<table style='width: 100%;' id='detailTable'>";
									detailHTML = detailHTML
											+ "<colgroup><col style='width: 25%' /><col style='width: 25%' /><col style='width: 30%' /><col style='width: 20%' /></colgroup>";

									// head
									detailHTML = detailHTML
											+ "<thead class='reportTotal'><tr><th>Week Start</th><th>Week End</th><th>Activity</th><th>&Sigma;&nbsp;Weekly</th></tr></thead>";

									// body
									detailHTML = detailHTML + "<tbody class='reportBody'>";

									var currDetail = null;

									for ( var k = 0; k < jsonData.details.length; k++) {
										currDetail = jsonData.details[k];
										detailHTML = detailHTML + "<tr>";
										detailHTML = detailHTML + "<td>" + currDetail.weekStartDate + "</td>";
										detailHTML = detailHTML + "<td>" + currDetail.weekEndDate + "</td>";
										detailHTML = detailHTML + "<td>" + currDetail.activityName + "</td>";
										detailHTML = detailHTML + "<td>" + currDetail.weeklySum + "</td>";
										detailHTML = detailHTML + "</tr>";
									}

									// footer row
									detailHTML = detailHTML + "<tr class='reportTotal'>";
									detailHTML = detailHTML + "<td colspan='3'>TOTAL</td>";
									detailHTML = detailHTML + "<td title='Total across all above weeks'>"
											+ jsonData.totalSum + "</td>";
									detailHTML = detailHTML + "</tr>";

									detailHTML = detailHTML + "</tbody>";
									detailHTML = detailHTML + "</table>";

									$("#reportDetails").html(detailHTML);

									document.getElementById('light').style.display = 'block';
									document.getElementById('fade').style.display = 'block';

									document.onkeydown = function(evt) {

										evt = evt || window.event;
										if (evt.keyCode == 27) {
											hideReportDetails();
										}
									};

									$("#light").draggable();
								}
							}
						});
	}
}

/**
 * Hide Report Details.
 */
function hideReportDetails() {

	document.getElementById('reportDetails').innerHTML = '';
	document.getElementById('light').style.display = 'none';
	document.getElementById('fade').style.display = 'none';
}

function focus(elementId) {

	$("#" + elementId).focus();
};

$(function() {

	$("#accordion").accordion({
		autoHeight : false,
		navigation : true,
		collapsible : true,
		active : 0
	});

	$("#my_entry_accordion").accordion({
		autoHeight : false,
		navigation : true,
		collapsible : true,
		active : 0
	});

	$("#my_change_accordion").accordion({
		autoHeight : false,
		navigation : true,
		collapsible : true,
		active : 0
	});

	$("#weekImg").click(function() {

		$("#weekpicker").datepicker('show');
	});

	$('#fromDateImg').click(function() {

		$('#fromDate').datepicker('show');
	});

	$('#toDateImg').click(function() {

		$('#toDate').datepicker('show');
	});

	var currentDate = new Date();

	$(function() {

		var from = $('#fromDate').val();

		if ((from == null) || (from == "")) {
			$('#fromDate').val($.datepicker.formatDate('dd M yy', currentDate));
		}

		var to = $('#toDate').val();

		if ((to == null) || (to == "")) {
			$('#toDate').val($.datepicker.formatDate('dd M yy', currentDate));
		}

	});

	$(function() {

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

	$(function() {

		var startDate;
		var endDate;

		var selectCurrentWeek = function() {

			window.setTimeout(function() {

				$('.ui-weekpicker').find('.ui-datepicker-current-day a').addClass('ui-state-active').removeClass(
						'ui-state-default');
			}, 1);
		};

		var setDates = function(input, targetCalendarFieldId) {

			var $input = $(input);
			var selectedDate = $input.datepicker('getDate');

			if (selectedDate !== null) {
				var dateObj = new Date(selectedDate);
				var firstDay = $input.datepicker("option", "firstDay");
				var dayAdjustment = dateObj.getDay() - firstDay;

				if (dayAdjustment < 0) {
					dayAdjustment += 7;
				}

				// get the week start & end day
				startDate = new Date(dateObj.getFullYear(), dateObj.getMonth(), dateObj.getDate() - dayAdjustment);
				endDate = new Date(dateObj.getFullYear(), dateObj.getMonth(), dateObj.getDate() - dayAdjustment + 6);

				// store the week start day
				if (targetCalendarFieldId == "weekArea") {
					weekStartDate = $.datepicker.formatDate('dd.mm.yy', startDate);
				} else if (targetCalendarFieldId == "startWeekSearch") {
					searchStartDate = $.datepicker.formatDate('dd.mm.yy', startDate);
					searchStartWeekNum = $.datepicker.iso8601Week(endDate)
					searchStartYear = endDate.getFullYear();
				} else if (targetCalendarFieldId == "endWeekSearch") {
					searchEndWeekNum = $.datepicker.iso8601Week(endDate)
					searchEndYear = endDate.getFullYear();
				}

				// create the display text
				var weekDetails = $.datepicker.formatDate('dd M', startDate) + ' - ';
				var year = $.datepicker.formatDate('yy', endDate);
				weekDetails = weekDetails + $.datepicker.formatDate('dd M', endDate) + ', ' + year;

				// set the formatted date in textbox
				$("#" + targetCalendarFieldId).val(weekDetails);
			}
		};

		var setDateLabels = function(strtDt) {

			var startDate = strtDt;

			// set the day labels
			$("#day1Text").text(
					$.datepicker.formatDate('D : dd-M', new Date(startDate.getFullYear(), startDate.getMonth(),
							startDate.getDate())));
			$("#day2Text").text(
					$.datepicker.formatDate('D : dd-M', new Date(startDate.getFullYear(), startDate.getMonth(),
							startDate.getDate() + 1)));
			$("#day3Text").text(
					$.datepicker.formatDate('D : dd-M', new Date(startDate.getFullYear(), startDate.getMonth(),
							startDate.getDate() + 2)));
			$("#day4Text").text(
					$.datepicker.formatDate('D : dd-M', new Date(startDate.getFullYear(), startDate.getMonth(),
							startDate.getDate() + 3)));
			$("#day5Text").text(
					$.datepicker.formatDate('D : dd-M', new Date(startDate.getFullYear(), startDate.getMonth(),
							startDate.getDate() + 4)));
			$("#day6Text").text(
					$.datepicker.formatDate('D : dd-M', new Date(startDate.getFullYear(), startDate.getMonth(),
							startDate.getDate() + 5)));
			$("#day7Text").text(
					$.datepicker.formatDate('D : dd-M', new Date(startDate.getFullYear(), startDate.getMonth(),
							startDate.getDate() + 6)));

			// clean any rows already present
			$("#timeTable_body").empty();
		};

		$('#weekpicker').datepicker({
			beforeShow : function() {

				$('#ui-datepicker-div').addClass('ui-weekpicker');
				selectCurrentWeek();
			},
			onClose : function() {

				$('#ui-datepicker-div').removeClass('ui-weekpicker');
			},
			showOtherMonths : true,
			selectOtherMonths : true,
			changeMonth : true,
			changeYear : true,
			firstDay : 1,
			showWeek : true,
			onSelect : function(dateText, inst) {

				setDates(this, "weekArea");
				$(this).datepicker("hide");
				setDateLabels(startDate);
				selectCurrentWeek();
				$(this).change();
			},
			beforeShowDay : function(date) {

				var cssClass = '';

				if (date >= startDate && date <= endDate) {
					cssClass = 'ui-datepicker-current-day';
				}

				return [ true, cssClass ];
			},
			onChangeMonthYear : function(year, month, inst) {

				selectCurrentWeek();
			}
		});

		setDates('#weekpicker');

		var $calendarTR = $('.ui-weekpicker .ui-datepicker-calendar tr');

		$calendarTR.live('mousemove', function() {

			$(this).find('td a').addClass('ui-state-hover');
		});

		$calendarTR.live('mouseleave', function() {

			$(this).find('td a').removeClass('ui-state-hover');
		});

		$("#startWeekImg").click(function() {

			$('#startWeekSearch').datepicker('show');
		});

		$('#startWeekSearch').datepicker({
			beforeShow : function() {

				$('#ui-datepicker-div').addClass('ui-weekpicker');
				selectCurrentWeek();
			},
			onClose : function() {

				$('#ui-datepicker-div').removeClass('ui-weekpicker');
			},
			showOtherMonths : true,
			selectOtherMonths : true,
			changeMonth : true,
			changeYear : true,
			firstDay : 1,
			showWeek : true,
			onSelect : function(dateText, inst) {

				setDates(this, "startWeekSearch");
				selectCurrentWeek();
				$(this).change();
			},
			beforeShowDay : function(date) {

				var cssClass = '';
				if (date >= startDate && date <= endDate)
					cssClass = 'ui-datepicker-current-day';
				return [ true, cssClass ];
			},
			onChangeMonthYear : function(year, month, inst) {

				selectCurrentWeek();
			}
		});

		$("#endWeekImg").click(function() {

			$('#endWeekSearch').datepicker('show');
		});

		$('#endWeekSearch').datepicker({
			beforeShow : function() {

				$('#ui-datepicker-div').addClass('ui-weekpicker');
				selectCurrentWeek();
			},
			onClose : function() {

				$('#ui-datepicker-div').removeClass('ui-weekpicker');
			},
			showOtherMonths : true,
			selectOtherMonths : true,
			changeMonth : true,
			changeYear : true,
			firstDay : 1,
			showWeek : true,
			onSelect : function(dateText, inst) {

				setDates(this, "endWeekSearch");
				selectCurrentWeek();
				$(this).change();
			},
			beforeShowDay : function(date) {

				var cssClass = '';
				if (date >= startDate && date <= endDate)
					cssClass = 'ui-datepicker-current-day';
				return [ true, cssClass ];
			},
			onChangeMonthYear : function(year, month, inst) {

				selectCurrentWeek();
			}
		});
	});

	$.getJSON(jsonUrl, {
		operation : "project"
	}, function(data) {

		projectsAvailableJSON = data;

		if ((projectsAvailableJSON != null) && (projectsAvailableJSON.projects != null)) {
			for ( var i = 0; i < projectsAvailableJSON.projects.length; i++) {
				projectArr[projectsAvailableJSON.projects[i].code] = projectsAvailableJSON.projects[i].value;
			}
		}
	});
});