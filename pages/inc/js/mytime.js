
var WEEKDAY_DEFAULT_VALUE = 0;

var USER_CELLINDEX = 0;
var PROJECT_CELLINDEX = USER_CELLINDEX + 1;
var ACTIVITY_CELLINDEX = PROJECT_CELLINDEX + 1;
var LEAD_CELLINDEX = ACTIVITY_CELLINDEX + 1;
var TASK_CELLINDEX = LEAD_CELLINDEX + 1;

var WEEKDAY_CELLSTART = TASK_CELLINDEX + 1;
var WEEKLYSUM_CELLINDEX = WEEKDAY_CELLSTART + 7;
var EDIT_SAVE_CELLINDEX = WEEKLYSUM_CELLINDEX + 1;
var DELETE_CELLINDEX = EDIT_SAVE_CELLINDEX + 1;

var activitySums = {};
var tasksArr = {};

var searchStartWeekNum = null;
var searchEndWeekNum = null;
var searchStartYear = null;
var searchEndYear = null;

var projectsAvailableJSON = null;

var searchStartDate = null;
var searchEndDate = null;
var searchProjId = null;
var searchActId = null;

var newEntryRowCount = 0;

/**
 * Updates Weekly Sum of a given Time Entry Row.
 * 
 * @param rowElmId
 */
function updateWeeklySum(rowElmId) {

	if (rowElmId != "") {
		var activeRow = document.getElementById(rowElmId);

		var currSum = 0.0;
		var val;

		for ( var i = WEEKDAY_CELLSTART; i < WEEKLYSUM_CELLINDEX; i++) {
			val = activeRow.cells[i].children[0].value;

			if ((val != null) && (val != "") && (!isNaN(val)) ) {
				currSum = currSum + parseFloat(val, 10);

				if(val.charAt(0) == '.') {
					activeRow.cells[i].children[0].value = "0"+val;
				}
			} else {
				activeRow.cells[i].children[0].value = 0;
			}
		}

		activeRow.cells[WEEKLYSUM_CELLINDEX].innerHTML = currSum;
	}
}

/**
 * Allows IN place editing of a Time Entry.
 * 
 * @param rowElmId
 * @param projDbId
 * @param actDbId
 * @param entryDbId
 */
function editTimeEntry(rowElmId, projDbId, actDbId, taskDbId, entryDbId) {

	if (rowElmId != "") {
		var activityRow = document.getElementById(rowElmId);

		// project
		var projSelId = null;
		var actSelectId = null;
		var taskSelectId = null;
		{
			projSelId = "project_" + entryDbId;
			
			var projectHTML = "<select size='1' class='timeEntrySelectEdit' name='project' id='" + projSelId + "' onchange=\"populateActivitiesAndTask('" + projSelId + "', null, true)\"><option value='0'>Select Project...</option>";
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
			activityRow.cells[1].innerHTML = projectHTML;
		}

		// activities
		{
			actSelectId = "activity_"+actDbId;
			taskSelectId = "task_"+actDbId;
			
			var activitySelectHTML = "<select size='1' name='activity' class='timeEntrySelectEdit' id='"
			        + actSelectId + "' onchange=\"populateTasks('"+projSelId+"','"+actSelectId+"','"+taskSelectId+"')\">";
			var optionHTML = "";

			$.getJSON(
				JSON_URL,
				{
					operation : "ACTIVTIES",
					id : projDbId
				},
				function(data) {
					var activtiesAvailableJSON = data;

					for ( var i = 0; i < activtiesAvailableJSON.activities.length; i++) {
						optionHTML = optionHTML + "<option value='" + activtiesAvailableJSON.activities[i].code + "'";

						if (actDbId == activtiesAvailableJSON.activities[i].code) {
							optionHTML = optionHTML + " selected='selected' ";
						}
						
						optionHTML = optionHTML + ">" + activtiesAvailableJSON.activities[i].value + "</option>";
						activityArr[activtiesAvailableJSON.activities[i].code] = activtiesAvailableJSON.activities[i].value;
					}

					activitySelectHTML = activitySelectHTML + optionHTML + "</select>";
					activityRow.cells[2].innerHTML = activitySelectHTML;
			});
		}
		
		// tasks
		{
			var taskSelectHtml = "<select size='1' name='task' class='timeEntrySelectEdit' id='" + taskSelectId+ "'>";
			var taskOptionHTML = "";

			$.getJSON(
				JSON_URL,
				{
					operation : "TASKS",
					projectId : projDbId,
					activityId : actDbId
				},
				function(data) {
					var jsonData = data;
					var elmId = 0;
					var elmText = null;
					
					var project = null;
					var activity = null;
					var task = null;
					
					for ( var i = 0; i < jsonData.projects.length; i++) {
					
						project = jsonData.projects[i];
						
						if(project.projectId == projDbId) {
							
							for ( var j = 0; j < project.activities.length; j++) {
								
								activity =  project.activities[j];
								
								if(activity.activityId == actDbId) {
									
									for ( var k = 0; k < activity.tasks.length; k++) {
										
										task = activity.tasks[k];
										
										elmId = task.taskId;
										elmText = task.taskName;
										
										taskOptionHTML = taskOptionHTML + "<option value='" + elmId + "'";

										if (taskDbId == elmId) {
											taskOptionHTML = taskOptionHTML + " selected='selected' ";
										}
										
										taskOptionHTML = taskOptionHTML + ">" + elmText + "</option>";
										taskArr[elmId] = elmText;
									}
									
									break;
								}
							}
						}
					}

					taskSelectHtml = taskSelectHtml + taskOptionHTML + "</select>";
					activityRow.cells[TASK_CELLINDEX].innerHTML = taskSelectHtml;
			});
		}

		// days
		{
			var selectPrefix = "<input type='text' class='timeEntryEdit' value='";
			var selectSufix = "' id='" + entryDbId + "_day1' class='timeEntry'onchange=\"updateWeeklySum('" + rowElmId + "')\">";

			for ( var i = WEEKDAY_CELLSTART; i < WEEKLYSUM_CELLINDEX; i++) {
				activityRow.cells[i].innerHTML = selectPrefix + activityRow.cells[i].innerHTML + selectSufix;
			}
		}

		// save button
		{
			var saveHTML = "<img alt='Save' class='icon' title='" + saveTitle + "' src='" + saveIcon + "' onclick=\"saveTimeEntry('" + rowElmId + "'," + entryDbId + ",0)\" align='middle'>";
			activityRow.cells[EDIT_SAVE_CELLINDEX].innerHTML = saveHTML;
		}
	}
}

function validateTimeData(projId, actId, taskId, timeData) {
	
	var errMsg = null;
	
	if(projId <= 0) {
		errMsg = "Missing Project Data";
	} else if(actId <= 0) {
		errMsg = "Missing Activity Data";
	} else if(taskId <= 0) {
		errMsg = "Missing Task Data";
	} else if (timeData <= 0) {
		errMsg = "Missing Time Data";
	}
	
	return errMsg;
}

/**
 * Saves a Time Entry Row.
 * 
 * @param rowElmId
 * @param entryDbId
 * @param proxiedUserDbId
 */
function saveTimeEntry(rowElmId, entryDbId, proxiedUserDbId) {

	if (rowElmId != "") {

		var entryRow = document.getElementById(rowElmId);

		// get the selected project
		var projDbId = 0;
		var projects = entryRow.cells[PROJECT_CELLINDEX].children[0].options;

		for ( var i = 0; i < projects.length; i++) {
			if (projects[i].selected) {
				projDbId = projects[i].value;
				break;
			}
		}

		// get the selected activity
		var actDbId = 0;
		var activities = entryRow.cells[ACTIVITY_CELLINDEX].children[0].options;

		for ( var i = 0; i < activities.length; i++) {
			if (activities[i].selected) {
				actDbId = activities[i].value;
				break;
			}
		}
		
		// get the selected task
		var taskDbId = 0;
		var tasks = entryRow.cells[TASK_CELLINDEX].children[0].options;

		for ( var i = 0; i < tasks.length; i++) {
			if (tasks[i].selected) {
				taskDbId = tasks[i].value;
				break;
			}
		}

		// get the weekly entries
		var timeData = {};
		var sum = 0;

		for ( var i = 0; i < 7; i++) {
			timeData[i] = entryRow.cells[i + WEEKDAY_CELLSTART].children[0].value;
			
			if ((timeData[i] != null) && (timeData[i] != "") && (!isNaN(timeData[i])) ) {
				sum = sum + parseFloat(timeData[i], 10);
			}
			
		}

		var weekStart = weekStartDate;

		if (entryDbId > 0) {
			var startDateElm = document.getElementById("startDate_" + entryDbId);

			if (startDateElm != null) {
				weekStart = startDateElm.value;
			}
		}
		
		var errMsg = validateTimeData(projDbId, actDbId, taskDbId, sum);
		
		if(errMsg != null) {
			displayAlert(errMsg);
			
		} else if ((weekStart != null) || (entryDbId > 0)) {

			$.post(JSON_URL, {
			    operation : "SAVE_TIME_ENTRY",
			    entryId : entryDbId,
			    projectId : projDbId,
			    activityId : actDbId,
			    taskId : taskDbId,
			    weekStartDate : weekStart,
			    proxiedUserDbId : proxiedUserDbId,
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
					displayAlert(jsonData.error);
				} else {

					var activityRow = document.getElementById(rowElmId);

					activityRow.cells[PROJECT_CELLINDEX].innerHTML = projectArr[projDbId];
					activityRow.cells[ACTIVITY_CELLINDEX].innerHTML = activityArr[actDbId];
					activityRow.cells[TASK_CELLINDEX].innerHTML = taskArr[taskDbId];
					
					for ( var idx = 0; idx < 7; idx++) {
						activityRow.cells[idx + WEEKDAY_CELLSTART].innerHTML = timeData[idx];
					}
					
					activityRow.className = "leadColumn";

					var editHTML = "<img alt='Edit' class='icon' title='" + editTitle + "' src='" + editIcon + "' onclick=\"editTimeEntry('" + rowElmId + "'," + projDbId + "," + actDbId + "," + taskDbId+ "," + jsonData.code + ")\" align='middle'>";
					activityRow.cells[EDIT_SAVE_CELLINDEX].innerHTML = editHTML;

				}
			}, JSON_RESULT_TYPE);
		}
	}
}

/**
 * Create a new Time Entry table row.
 * 
 * @param tableID Id of the target table .
 */
function addEntryRow(tableID) {

	var selectedWeek = document.getElementById('weekArea');

	if ((selectedWeek.value == null) || (selectedWeek.value == "")) {
		displayAlert("Select Week First");
	} else {

		$.getJSON(
			JSON_URL,
			{
				operation : "project",
				status : "true"
			},
			function(data) {
				var jsonData = data;

				if (jsonData.error) {
					displayAlert(jsonData.error);
				} else {
					var table = document.getElementById(tableID);
					var tbody = table.tBodies[0];
					var rowCount = tbody.rows.length;
					var newId = rowCount;
					newEntryRowCount = rowCount;

					var row = tbody.insertRow(rowCount);
					var rowId = "entry_" + newId;
					row.id = rowId;

					var userName = 'Me';

					var proxyUserElm = document.getElementById('proxyUserArea');
					var proxiedUserDbId = 0;

					if (proxyUserElm != null) {
						for ( var i = 0; i < proxyUserElm.options.length; i++) {
							if ((proxyUserElm.options[i].selected) && (proxyUserElm.options[i].value > 0)) {
								proxiedUserDbId = proxyUserElm.options[i].value;
								userName = proxyUserElm.options[i].innerHTML;
								break;
							}
						}
					}
					
					var selectId = "project_" + newId;
					var projectHTML = "<select class='timeEntrySelectEdit' size='1' id='" + selectId + "' onchange=\"populateActivitiesAndTask('" + selectId + "',null,true)\"><option value='0'>Select Project...</option>";
					var optionHTML = '';

					for ( var i = 0; i < jsonData.projects.length; i++) {
						optionHTML = optionHTML + '<option value="' + jsonData.projects[i].code + '">' + jsonData.projects[i].value + '</option>';
						leads[jsonData.projects[i].code] = jsonData.projects[i].leadname;
						projectArr[jsonData.projects[i].code] = jsonData.projects[i].value;
					}

					projectHTML = projectHTML + optionHTML + "</select>";

					var timeWeekDayHTML = "<input value='0' class='timeEntryEdit' type='text' onchange=\"updateWeeklySum('" + rowId + "')\">";
					var timeWeekEndHTML = "<input value='0' class='timeEntryEdit weekEnd' type='text' onchange=\"updateWeeklySum('" + rowId + "')\">";
					var deleteHTML = "<img alt='Delete' class='icon' title='Delete this row' src='" 
						+ deleteIcon + "' onclick=\"deleteTimeEntry('" + rowId + "'," + 0 + ")\" align='middle'>";
					var saveHTML = "<img alt='Save' class='icon' title='" + saveTitle + "' src='" 
						+ saveIcon + "' onclick=\"saveTimeEntry('" + rowId + "',0,"+proxiedUserDbId+")\" align='middle'>";

					var cellNum = 0;

					/**
					* Create User Column
					*/
					var newcell = row.insertCell(cellNum++);
					newcell.innerHTML = userName;
					
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
					* Create Task Column
					*/
					newcell = row.insertCell(cellNum++);

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
			}
		);
	}
}

/**
 * Delete a given Time Entry.
 * 
 * @param rowElmId
 * @param entryDbId
 */
function deleteTimeEntry(rowElmId, entryDbId) {

	if (rowElmId != "") {

		if (entryDbId > 0) {
			$.post(JSON_URL, {
				operation : "DELETE_TIME_ENTRY",
				entryId : entryDbId
			}, function(data) {
				jsonData = data;

				if (jsonData.error) {
					displayAlert(jsonData.error);
				} else {
					var activityRow = document.getElementById(rowElmId);
					activityRow.parentNode.removeChild(activityRow);
				}
			}, JSON_RESULT_TYPE);
		} else if (entryDbId == 0) {
			var activityRow = document.getElementById(rowElmId);
			activityRow.parentNode.removeChild(activityRow);
		}
	}
}

/**
 * Searches Time Entries.
 * 
 * @param divId
 * @param tbodyElmId
 * @param accordId
 * @param adminUser
 */
function searchEntries(divId, tbodyElmId, accordId, adminUser) {

	var startDate = searchStartDate;
	var endDate = searchEndDate;
	var projId = document.getElementById("searchProjectId").value;
	var actId = document.getElementById("searchActivityId").value;
	var tskId = document.getElementById("searchTaskId").value;

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

		$.getJSON(
			JSON_URL,
			{
				operation : "SEARCH_ENTRIES",
				weekStartDate : searchStartDate,
				weekEndDate : searchEndDate,
				startWeekNum : searchStartWeekNum,
				endWeekNum : searchEndWeekNum,
				startYear : searchStartYear,
				endYear : searchEndYear,
				projectId : projId,
				activityId : actId,
				taskId : tskId,
				userDbId : userId
			},
			function(data) {
			
				var jsonData = data;
			
				if (jsonData.error) {
					$("#" + divId).html();
					$("#" + divId).html("<p class='error'>No Data Found</p>");
				} else {
			
					if (jsonData.weeklyData.length > 0) {
						var accDivHTML = "<p style='margin: 1em 0 1em 0;'>Search Results</p><div style='margin-bottom: 2em;'><div id='" + accordId + "'>";
						var weekHTML = "";
						var currWeekData = null;
						var dayLabel = null;
						var entry = null;
						var entryRowId = null;
			
						for ( var i = 0; i < jsonData.weeklyData.length; i++) {
							currWeekData = jsonData.weeklyData[i];
							weekHTML = "<h3><a href='#week_" + currWeekData.weekId + "'>" + currWeekData.weekName + "</a></h3>";
							weekHTML = weekHTML + "<div>";
							weekHTML = weekHTML + "<table style='width: 100%;' id='timeTable_" + currWeekData.weekId + "'>";
							weekHTML = weekHTML + "<colgroup>";
							weekHTML = weekHTML + "<col style='width: 10%' />";
							weekHTML = weekHTML + "<col style='width: 10%' />";
							weekHTML = weekHTML + "<col style='width: 10%' />";
							weekHTML = weekHTML + "<col style='width: 10%' />";
							weekHTML = weekHTML + "<col style='width: 7%' />";
							weekHTML = weekHTML + "<col style='width: 6%' />";
							weekHTML = weekHTML + "<col style='width: 6%' />";
							weekHTML = weekHTML + "<col style='width: 6%' />";
							weekHTML = weekHTML + "<col style='width: 6%' />";
							weekHTML = weekHTML + "<col style='width: 6%' />";
							weekHTML = weekHTML + "<col style='width: 6%' />";
							weekHTML = weekHTML + "<col style='width: 6%' />";
							weekHTML = weekHTML + "<col style='width: 5%' />";
							weekHTML = weekHTML + "<col style='width: 3%' />";
							weekHTML = weekHTML + "<col style='width: 3%' />";
							weekHTML = weekHTML + "</colgroup>";

							// head
							weekHTML = weekHTML + "<thead>";
							weekHTML = weekHTML + "<tr>";
							weekHTML = weekHTML + "<th>User</th>";
							weekHTML = weekHTML + "<th>Project</th>";
							weekHTML = weekHTML + "<th>Activity</th>";
							weekHTML = weekHTML + "<th>Lead</th>";
							weekHTML = weekHTML + "<th>Task</th>";

							for ( var j = 0; j < currWeekData.weekDayLabels.length; j++) {
								dayLabel = currWeekData.weekDayLabels[j];
								weekHTML = weekHTML + "<th id=day_" + j + "_Text>" + dayLabel.label + "</th>";
							}

							weekHTML = weekHTML + "<th>Total</th>";
							weekHTML = weekHTML + "<th colspan='2'>&nbsp;</th>";
							weekHTML = weekHTML + "</tr>";
							weekHTML = weekHTML + "</thead>";

							// body
							weekHTML = weekHTML + "<tbody id='" + tbodyElmId + "_" + currWeekData.weekId + "' class='reportBody'>";

							for ( var k = 0; k < currWeekData.entryData.length; k++) {
								entry = currWeekData.entryData[k];
								entryRowId = "entry_" + entry.entryId;

								weekHTML = weekHTML + "<tr id='" + entryRowId + "' class='leadColumn'>";
								weekHTML = weekHTML + "<td>" + entry.userName + "</td>";
								weekHTML = weekHTML + "<td>" + entry.projectName
								        + "<input type='hidden' id='startDate_" + entry.entryId
								        + "' value='" + entry.startDate + "' /></td>";
								weekHTML = weekHTML + "<td>" + entry.activityName + "</td>";
								weekHTML = weekHTML + "<td>";
								
								//lead name
								if (entry.leadName != null) {
									weekHTML = weekHTML + entry.leadName + "</td>";
								} else {
									weekHTML = weekHTML + "</td>";
								}

								//task
								weekHTML = weekHTML + "<td>" + entry.taskName + "</td>";
								
								//days
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
									+ editTitle + "' src='" + editIcon + "' onclick=\"editTimeEntry('"+ entryRowId + "'," + entry.projectId + "," + entry.activityId + "," + entry.entryId + ")\" align='middle'></td>";
								weekHTML = weekHTML
									+ "<td align='center'><img alt='Delete' class='icon' title='Delete this row' src='"
									+ deleteIcon + "' onclick=\"deleteTimeEntry('" + entryRowId + "'," + entry.entryId + ")\" align='middle'></td>";

							}
							weekHTML = weekHTML + "</tbody></table></div>";
							accDivHTML = accDivHTML + weekHTML;
						}
						
						accDivHTML = accDivHTML +"</div></div>";
						
						//add the export form
						{
							accDivHTML = accDivHTML +"<div style='display: inline; padding: 0; margin: 0; margin-left: 1em;'>";
							accDivHTML = accDivHTML +"<form name='exportForm' id='exportForm' action='Export' method='POST'>";
							accDivHTML = accDivHTML +"<input type='hidden' id='exportStartDate' name='exportStartDate' value='' />";
							accDivHTML = accDivHTML +"<input type='hidden' id='exportEndDate' name='exportEndDate' value='' />";
							accDivHTML = accDivHTML +"<input type='hidden' id='exportProjectId' name='exportProjectId' value='' />";
							accDivHTML = accDivHTML +"<input type='hidden' id='exportActivityId' name='exportActivityId' value='' />";
							accDivHTML = accDivHTML +"<input type='hidden' id='exportUserDbId' name='exportUserDbId' value='' />";
							accDivHTML = accDivHTML +"<input type='hidden' name='command' value='TIME_ENTRIES' />";
							accDivHTML = accDivHTML +"<input type='button' value='Export' class='button' style='height: 1.5em;' onclick=\"exportEntries('exportForm')\" />";
							accDivHTML = accDivHTML +"</form>";
							accDivHTML = accDivHTML +"</div>";
						}
						
						// clear existing and add results message
						$("#" + divId).html();
						$("#" + divId).html(accDivHTML);
						$("#" + accordId).accordion(accOptions);
					}
				}
			});
	}
}

function searchMissingEntries(accordId, divId) {

	// clear the new entry table
	$("#"+accordId).html();

	if ((searchStartDate != null) && (searchEndDate != null)) {

		$.getJSON(
			JSON_URL,
			{
				operation : "SEARCH_USERS_WITHOUT_ENTRIES",
				weekStartDate : searchStartDate,
				weekEndDate : searchEndDate
			},
			function(data) {
				var jsonData = data;

				if (jsonData.error) {
					$("#" + divId).html();
					$("#" + divId).html("<p class='error'>No Data Found</p>");
				} else {
					if (jsonData.weeklyUserData.length > 0) {
						
						var accDivHTML = "<p style='margin: 1em 0 1em 0;'>Users With Missing Entries</p><div style='margin-bottom: 2em;'><div id='" + accordId + "'>";
						var weekHTML = "";
						var currWeekData = null;
						var user = null;
						var allUserEmail = "";

						for ( var i = 0; i < jsonData.weeklyUserData.length; i++) {
							currWeekData = jsonData.weeklyUserData[i];
							weekHTML = "<h3><a href='#missing_week_" + currWeekData.weekId + "'>" + currWeekData.weekName + "</a></h3>";
							weekHTML = weekHTML + "<div>";
							weekHTML = weekHTML + "<table style='width: 15%;' id='missing_timeTable_" + currWeekData.weekId + "'>";
							weekHTML = weekHTML + "<colgroup>";
							weekHTML = weekHTML + "<col style='width: 15%' />";
							weekHTML = weekHTML + "<col style='width: 70%' />";
							weekHTML = weekHTML + "<col style='width: 15%' />";
							weekHTML = weekHTML + "</colgroup>";

							// head
							weekHTML = weekHTML + "<thead>";
							weekHTML = weekHTML + "<tr>";
							weekHTML = weekHTML + "<th colspan='3'>User Name</th>";
							weekHTML = weekHTML + "</thead>";

							// body
							weekHTML = weekHTML + "<tbody class='reportBody'>";

							for ( var k = 0; k < currWeekData.users.length; k++) {
								user = currWeekData.users[k];
								weekHTML = weekHTML + "<tr>";
								weekHTML = weekHTML + "<td>" + (k+1) + "</td>";
								weekHTML = weekHTML + "<td style='text-align: left; padding-left: 0.5em'>" + user.userName + "</td>";
								weekHTML = weekHTML + "<td>";

								if (trimToNull(user.email) != null) {
									weekHTML = weekHTML + "<a href='mailto:" + user.email + "'><img class='icon' title='"+emailTitle+"' alt='"+emailTitle+"' src='"+emailIcon+"'/></a>";
									allUserEmail = user.email  + "," + allUserEmail;
								}
								
								weekHTML = weekHTML + "</td>";
								weekHTML = weekHTML + "</tr>";
							}
							
							weekHTML = weekHTML + "</tbody>";
							weekHTML = weekHTML + "<tfoot>";
							weekHTML = weekHTML + "<tr>";
							weekHTML = weekHTML + "<td colspan='2' style='text-align: center;'>Total Users : "+k+"</td>";
							weekHTML = weekHTML + "<td>";
							weekHTML = weekHTML + "<a href='mailto:"+ allUserEmail +"'>";
							weekHTML = weekHTML + "<img class='icon' style='margin: 0; padding: 0; border: 2px solid #609AFA' title='Send Email To Users (Ones with Email Icon)' alt='Send Email' src='"+emailIcon+"'/>";
							weekHTML = weekHTML + "</a>";
							weekHTML = weekHTML + "</td>";
							weekHTML = weekHTML + "</tr>";
							weekHTML = weekHTML + "</tfoot>"
							weekHTML = weekHTML + "</table>";
							weekHTML = weekHTML + "</div>";
							
							accDivHTML = accDivHTML + weekHTML;
							
							allUserEmail = "";
						}
						
						accDivHTML = accDivHTML + "</div></div>";
						
						// clear existing and add results message
						$("#" + divId).html();
						$("#" + divId).html(accDivHTML);
						$("#" + accordId).accordion(accOptions);
					}
				}
			}
		);
	}
};

function exportEntries(formId) {

	if(formId != null) {
	
		var startDate = searchStartDate;
		var endDate = searchEndDate;
		var projId = document.getElementById("searchProjectId").value;
		var actId = document.getElementById("searchActivityId").value;

		var usrElm = document.getElementById("searchUserId");
		var userId = 0;

		if (usrElm != null) {
			userId = usrElm.value;
		}

		document.getElementById("exportStartDate").value = startDate ;
		document.getElementById("exportEndDate").value = endDate ;
		document.getElementById("exportProjectId").value = projId ;
		document.getElementById("exportActivityId").value = actId;
		document.getElementById("exportUserDbId").value = userId ;
		
		//submit the form
		document.getElementById(formId).submit();
		
	}
}

$(function() {
	
	$("#newEntryAccordion").accordion({
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
	
	$("#my_missing_entry_accordion").accordion({
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
				$('.ui-weekpicker').find('.ui-datepicker-current-day a').addClass('ui-state-active').removeClass('ui-state-default');
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
				} else if ((targetCalendarFieldId == "startWeekSearch") || (targetCalendarFieldId == "missingStartWeekSearch")) {
					searchStartDate = $.datepicker.formatDate('dd.mm.yy', startDate);
					searchStartWeekNum = $.datepicker.iso8601Week(endDate)
					searchStartYear = endDate.getFullYear();
				} else if ((targetCalendarFieldId == "endWeekSearch") || (targetCalendarFieldId == "missingEndWeekSearch")) {
					searchEndDate = $.datepicker.formatDate('dd.mm.yy', endDate);
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
			$("#day1Text").text( $.datepicker.formatDate('D : dd-M', new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate())));
			$("#day2Text").text( $.datepicker.formatDate('D : dd-M', new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate() + 1)));
			$("#day3Text").text( $.datepicker.formatDate('D : dd-M', new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate() + 2)));
			$("#day4Text").text( $.datepicker.formatDate('D : dd-M', new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate() + 3)));
			$("#day5Text").text( $.datepicker.formatDate('D : dd-M', new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate() + 4)));
			$("#day6Text").text( $.datepicker.formatDate('D : dd-M', new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate() + 5)));
			$("#day7Text").text( $.datepicker.formatDate('D : dd-M', new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate() + 6)));

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
		
		//missing user start week
		$("#missingStartWeekImg").click(function() {
			$('#missingStartWeekSearch').datepicker('show');
		});

		$('#missingStartWeekSearch').datepicker({
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
			    setDates(this, "missingStartWeekSearch");
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
		
		//missing user end week
		$("#missingEndWeekImg").click(function() {
			$('#missingEndWeekSearch').datepicker('show');
		});

		$('#missingEndWeekSearch').datepicker({
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
			    setDates(this, "missingEndWeekSearch");
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
	
	$.getJSON(JSON_URL, {
		operation : "project",
		status : "true"
	}, function(data) {
		projectsAvailableJSON = data;

		if ((projectsAvailableJSON != null) && (projectsAvailableJSON.projects != null)) {
			for ( var i = 0; i < projectsAvailableJSON.projects.length; i++) {
				projectArr[projectsAvailableJSON.projects[i].code] = projectsAvailableJSON.projects[i].value;
			}
		}
	});
});
