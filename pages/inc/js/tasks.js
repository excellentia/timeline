var newTaskRowCount = 0;

var TaskConstants = {
	newProjectSelectId : "newTaskProjectId",
	newActivitySelectId : "newTaskActivityId",
	newTaskTextInputId : "newTaskText",
	newTaskDescInputId : "newTaskDescription",
	newTaskSizeInputId : "newTaskSize",
	newTaskStatusInputId : "newTaskStatus",

	newTaskBodyId : "newTaskBody",
	newTaskDivId : "newTaskSection",

	newTaskTextCellIdx : 2,
	newTaskDescCellIdx : 3,
	newTaskSizeCellIdx : 4,
	newTaskStatusCellIdx : 5,
	newTaskEditIconCellIdx : 6,
	
	existTaskTextCellIdx : 1,
	existTaskDescCellIdx : 2,
	existTaskSizeCellIdx : 3,
	existTaskStatusCellIdx : 4,
	existTaskEditIconCellIdx : 5,

	existingProjSelectId : "searchProjectId",
	existingActivitySelectId : "searchActivityId",
	existingTaskStatusId : "taskStatusId",

	taskSearchResultsDivId : "myTaskSearchResults",
	taskResultsAccordionId : "taskSearchResultsAccordion"
};

var TaskData = function() {
	var projDbId = 0;
	var actDbId = 0;
	var taskDbId = 0;
	var taskText = null;
	var taskDesc = null;
	var active = false;
	var storyPoints = 0;
};

function validateTaskData(checkText, data) {

	var errorMsg = null;

	if (data.taskDbId > 0) {
		if (data.taskText == null) {
			errorMsg = "Enter Task Text";
		}
	} else {
		if (data.projDbId <= 0) {
			errorMsg = "Select A Project";
		} else if (data.actDbId <= 0) {
			errorMsg = "Select An Activity";
		} else if ((data.taskText == null) && (checkText)) {
			errorMsg = "Enter Task Text";
		} else if(data.storyPoints < 0) {
			errorMsg = "Story Points cannot be negative";
		}
	}

	return errorMsg;
}

function populateTaskData(isNew, existingTaskRowId, tskId) {

	var projId = 0;
	var actId = 0;
	var text = null;
	var desc = null;
	var active = false;
	var storyPoints = 0;

	if (existingTaskRowId == null) {

		projId = getDropDownValueAsInt(TaskConstants.newProjectSelectId);
		actId = getDropDownValueAsInt(TaskConstants.newActivitySelectId);
		text = getTextInputValue(TaskConstants.newTaskTextInputId);
		desc = getTextInputValue(TaskConstants.newTaskDescInputId);
		active = getBooleanInputValue(TaskConstants.newTaskStatusInputId);
		storyPoints = getValidInt(getTextInputValue(TaskConstants.newTaskSizeInputId));

	} else {

		var rowElm = document.getElementById(existingTaskRowId);

		if (rowElm != null) {

			if (isNew) {

				// task being edited was newly created
				text = trimToNull(rowElm.cells[TaskConstants.newTaskTextCellIdx].children[0].value);
				desc = trimToNull(rowElm.cells[TaskConstants.newTaskDescCellIdx].children[0].value);
				active = rowElm.cells[TaskConstants.newTaskStatusCellIdx].children[0].checked;
				storyPoints = getValidInt(trimToNull(rowElm.cells[TaskConstants.newTaskSizeCellIdx].children[0].value));

				projId = getDropDownValueAsInt(TaskConstants.newProjectSelectId);
				actId = getDropDownValueAsInt(TaskConstants.newActivitySelectId);

			} else {

				// task being edited was part of search results
				text = trimToNull(rowElm.cells[TaskConstants.existTaskTextCellIdx].children[0].value);
				desc = trimToNull(rowElm.cells[TaskConstants.existTaskDescCellIdx].children[0].value);
				active = rowElm.cells[TaskConstants.existTaskStatusCellIdx].children[0].checked;
				storyPoints = getValidInt(trimToNull(rowElm.cells[TaskConstants.existTaskSizeCellIdx].children[0].value));
				
				projId = getDropDownValueAsInt(TaskConstants.existingProjSelectId);
				actId = getDropDownValueAsInt(TaskConstants.existingActivitySelectId);
			}

		}
	}

	var data = new TaskData();

	data.projDbId = projId;
	data.actDbId = actId;
	data.taskDbId = tskId;
	data.taskText = text;
	data.taskDesc = desc;
	data.active = active;
	data.storyPoints = storyPoints;

	return data;
}

function createTask() {

	var taskData = populateTaskData(true, null, 0);
	var taskValidationMsg = validateTaskData(false, taskData);
	var projName = projectArr[taskData.projDbId];
	var actName = activityArr[taskData.actDbId];

	if (taskValidationMsg == null) {

		newTaskRowCount++;
		var rowId = "newTaskRow_" + newTaskRowCount;

		// check if table already exists
		var newTaskBody = document.getElementById(TaskConstants.newTaskBodyId);

		var deleteHTML = "<img alt='Delete' class='icon' title='" + taskDeleteTitle + "' src='" + deleteIcon
				+ "' onclick=\"deleteTask('" + rowId + "'," + 0 + ")\" align='middle'>";

		var saveHTML = "<img alt='Save' align='middle' class='icon' title='" + taskSaveTitle + "' src='" + saveIcon
		+ "' onclick=\"saveTask(" + true + ",'" + rowId + "',0)\"/>";

		if (newTaskBody == null) {

			var htmlText = "";
			htmlText = htmlText + "<table style='width: 80%;'>";

			htmlText = htmlText + "<colgroup>";
			htmlText = htmlText + "<col style='width: 12%' />";
			htmlText = htmlText + "<col style='width: 12%' />";
			htmlText = htmlText + "<col style='width: 20%' />";
			htmlText = htmlText + "<col style='width: 36%' />";
			htmlText = htmlText + "<col style='width: 6%' />";
			htmlText = htmlText + "<col style='width: 6%' />";
			htmlText = htmlText + "<col style='width: 4%' />";
			htmlText = htmlText + "<col style='width: 4%' />";
			htmlText = htmlText + "</colgroup>";

			htmlText = htmlText + "<thead>";
			htmlText = htmlText + "<tr align='center'>";
			htmlText = htmlText + "<th>Project</th>";
			htmlText = htmlText + "<th>Activity</th>";
			htmlText = htmlText + "<th>Task</th>";
			htmlText = htmlText + "<th>Description</th>";
			htmlText = htmlText + "<th>Story Points</th>";
			htmlText = htmlText + "<th>Active ?</th>";
			htmlText = htmlText + "<th colspan='2'>&nbsp;</th>";
			htmlText = htmlText + "</tr>";
			htmlText = htmlText + "</thead>";
			htmlText = htmlText + "<tbody class='reportBody' id='" + TaskConstants.newTaskBodyId + "'>";
			htmlText = htmlText + "<tr id ='" + rowId + "'>";
			htmlText = htmlText + "<td>" + projName + "</td>";
			htmlText = htmlText + "<td>" + actName + "</td>";
			htmlText = htmlText	+ "<td><input type='text' class='activityAreaEdit' style='margin:0; padding-left:0.5em;' id='"
					+ TaskConstants.newTaskTextInputId + "' maxlength='20'/></td>";
			htmlText = htmlText	+ "<td><input type='text' class='activityAreaEdit' style='margin:0; padding-left:0.5em;' id='"
					+ TaskConstants.newTaskDescInputId + "' maxlength='50'/></td>";
			htmlText = htmlText	+ "<td><input type='text' class='activityAreaEdit' style='margin:0; padding:0; width: 3em; text-align: center' id='"
					+ TaskConstants.newTaskSizeInputId + "' maxlength='3' value = '0'/></td>";
			htmlText = htmlText + "<td><input type='checkbox' id='" + TaskConstants.newTaskStatusInputId + "' checked='checked'/></td>";

			htmlText = htmlText + "<td>" + saveHTML + "</td>";
			htmlText = htmlText + "<td>" + deleteHTML + "</td>";
			htmlText = htmlText + "</tr>";
			htmlText = htmlText + "</tbody>";
			htmlText = htmlText + "</table>";

			// set the html in section
			$("#" + TaskConstants.newTaskDivId).html(htmlText);

		} else {

			var bodyHTML = newTaskBody.innerHTML;
			bodyHTML = bodyHTML + "<tr id ='" + rowId + "'>";
			bodyHTML = bodyHTML + "<td>" + projName + "</td>";
			bodyHTML = bodyHTML + "<td>" + actName + "</td>";
			bodyHTML = bodyHTML + "<td><input type='text' class='activityAreaEdit' id='"
						+ TaskConstants.newTaskTextInputId + "' maxlength='20'/></td>";
			bodyHTML = bodyHTML + "<td><input type='text' class='activityAreaEdit' id='"
						+ TaskConstants.newTaskDescInputId + "' maxlength='50'/></td>";
			bodyHTML = bodyHTML + "<td><input type='text' class='activityAreaEdit' style='margin:0; padding:0; width: 3em; text-align: center' id='"
						+ TaskConstants.newTaskSizeInputId + "' maxlength='3' value = '0'/></td>";
			bodyHTML = bodyHTML + "<td><input type='checkbox' id='" + TaskConstants.newTaskStatusInputId + "' checked='checked'/></td>";

			bodyHTML = bodyHTML + "<td>" + saveHTML + "</td>";
			bodyHTML = bodyHTML + "<td>" + deleteHTML + "</td>";
			bodyHTML = bodyHTML + "</tr>";

			newTaskBody.innerHTML = bodyHTML;
		}
	} else {
		displayAlert(taskValidationMsg);
	}
}

function clearTaskRow(taskRowId) {

	var taskRow = document.getElementById(taskRowId);

	if (taskRow != null) {
		var taskTableBody = taskRow.parentNode;

		if (taskTableBody != null) {

			//clear the entire section
			if (taskTableBody.rows.length == 1) {
				document.getElementById(TaskConstants.taskSearchResultsDivId).innerHTML = "";
			} else {
				taskTableBody.removeChild(taskRow);
			}
		}
	}
}

function deleteTask(taskRowId, taskDbId) {

	if (taskDbId > 0) {

		$.post(JSON_URL, {
			operation : "DELETE_TASK",
			id : taskDbId
		}, function(data) {

			var jsonData = data;

			if (jsonData.error) {
				displayAlert(jsonData.error);
			} else {
				clearTaskRow(taskRowId);
			}
		}, JSON_RESULT_TYPE);
	} else {
		clearTaskRow(taskRowId);
	}
};

function editTask(isNew, taskRowElmId, taskDbId) {

	var text = null;
	var desc = null;
	var size = 0;
	
	var saveHTML = "<img alt='Save' align='middle' class='icon' title='" + taskSaveTitle + "' src='" + saveIcon
	+ "' onclick=\"saveTask(" + isNew + ",'" + taskRowElmId + "'," + taskDbId + ")\"/>";
	
	var taskRow = document.getElementById(taskRowElmId);

	if (isNew) {
		text = taskRow.cells[TaskConstants.newTaskTextCellIdx].innerHTML;
		desc = taskRow.cells[TaskConstants.newTaskDescCellIdx].innerHTML;
		size = taskRow.cells[TaskConstants.newTaskSizeCellIdx].innerHTML;
		
		taskRow.cells[TaskConstants.newTaskTextCellIdx].innerHTML = "<input type='text' class='activityAreaEdit' id='"
				+ TaskConstants.taskTextInputId + "' value ='" + text + "'maxlength='20'/>";
		taskRow.cells[TaskConstants.newTaskDescCellIdx].innerHTML = "<input type='text' class='activityAreaEdit' id='"
				+ TaskConstants.taskDescInputId + "' value ='" + desc + "'maxlength='50'/>";
		taskRow.cells[TaskConstants.newTaskSizeCellIdx].innerHTML = "<input type='text' class='activityAreaEdit' style='margin:0; padding: 0; text-align: center' "
			+ TaskConstants.newTaskSizeInputId + " value ='" + size + "'maxlength='3'/>";
		taskRow.cells[TaskConstants.newTaskEditIconCellIdx].innerHTML = saveHTML;
	} else {
		text = taskRow.cells[TaskConstants.existTaskTextCellIdx].innerHTML;
		desc = taskRow.cells[TaskConstants.existTaskDescCellIdx].innerHTML;
		size = taskRow.cells[TaskConstants.existTaskSizeCellIdx].innerHTML;
		
		taskRow.cells[TaskConstants.existTaskTextCellIdx].innerHTML = "<input type='text' class='activityAreaEdit' "
				+ "value ='" + text + "'maxlength='20'/>";
		taskRow.cells[TaskConstants.existTaskDescCellIdx].innerHTML = "<input type='text' class='activityAreaEdit' "
				+ "value ='" + desc + "'maxlength='50'/>";
		taskRow.cells[TaskConstants.existTaskSizeCellIdx].innerHTML = "<input type='text' class='activityAreaEdit' style='margin:0; padding: 0; text-align: center' "
				+ "value ='" + size + "'maxlength='3'/>";
		taskRow.cells[TaskConstants.existTaskEditIconCellIdx].innerHTML = saveHTML;
	}

};

function saveTask(isNew, taskRowElmId, taskDbId) {

	var data = populateTaskData(isNew, taskRowElmId, taskDbId);
	var errMsg = validateTaskData(true, data);

	if (errMsg == null) {

		$.post(JSON_URL, {
			operation : "SAVE_TASK",
			projectId : data.projDbId,
			activityId : data.actDbId,
			taskId : data.taskDbId,
			text : data.taskText,
			status : data.active,
			description : data.taskDesc,
			size : data.storyPoints
		}, function(data) {

			var jsonData = data;

			if (jsonData.error) {
				displayAlert(jsonData.error);
			} else {
				var taskRow = document.getElementById(taskRowElmId);
				var taskRowId = "taskRow_" + jsonData.code;
				taskRow.id = taskRowId;

				var taskText = null;
				var desc = null;
				var size = null;
				
				var editHTML = "<img alt='Edit' align='middle' class='icon' title='" + taskEditTitle + "' src='"
				+ editIcon + "' onclick=\"editTask(" + isNew + ",'" + taskRowId + "',"
				+ jsonData.code + ")\"/>";


				// replace the text boxes with plain text
				if (isNew) {
					taskText = jsonData.value;
					desc = taskRow.cells[TaskConstants.newTaskDescCellIdx].children[0].value;
					size = taskRow.cells[TaskConstants.newTaskSizeCellIdx].children[0].value;
					
					taskRow.cells[TaskConstants.newTaskTextCellIdx].innerHTML = taskText;
					taskRow.cells[TaskConstants.newTaskDescCellIdx].innerHTML = desc;
					taskRow.cells[TaskConstants.newTaskSizeCellIdx].innerHTML = size;
					
					taskRow.cells[TaskConstants.newTaskEditIconCellIdx].innerHTML = editHTML;
					
				} else {
					
					taskText = jsonData.value;
					desc = taskRow.cells[TaskConstants.existTaskDescCellIdx].children[0].value;
					size = taskRow.cells[TaskConstants.existTaskSizeCellIdx].children[0].value;
					
					taskRow.cells[TaskConstants.existTaskTextCellIdx].innerHTML = taskText;
					taskRow.cells[TaskConstants.existTaskDescCellIdx].innerHTML = desc;
					taskRow.cells[TaskConstants.existTaskSizeCellIdx].innerHTML = size; 
					
					taskRow.cells[TaskConstants.existTaskEditIconCellIdx].innerHTML = editHTML;
				}
			}
		}, JSON_RESULT_TYPE);
	} else {
		displayAlert(errMsg);
	}
}

function searchTasks() {

	var projId = getDropDownValueAsInt(TaskConstants.existingProjSelectId);
	var actId = getDropDownValueAsInt(TaskConstants.existingActivitySelectId);
	var statusId = getDropDownValueAsInt(TaskConstants.existingTaskStatusId);

	$.post(JSON_URL, {
		operation : "TASKS",
		projectId : projId,
		activityId : actId,
		status : statusId
	}, function(data) {

		var jsonData = data;
		var resultsDivId = "#" + TaskConstants.taskSearchResultsDivId;

		//clear first
		$(resultsDivId).html();

		if (jsonData.error) {
			$(resultsDivId).html("<p class='error' style='margin:0; padding:0; margin-top: 1.5em;' >No Tasks Found</p>");
		} else {
			
			var searchHTML = "";
			searchHTML = searchHTML + "<div id='"+TaskConstants.taskSearchResultsAccordion+"' style='margin-top : 1.5em;'>";
			
			var project = null;
			var activity = null;
			var task = null;
			var rowId = null;
			var taskCount = 0;
			
			for ( var i = 0; i < jsonData.projects.length; i++) {
			
				project = jsonData.projects[i];
				
				searchHTML = searchHTML + "<h3><a href='#taskSection'"+i+">" +project.projectName+ "</a></h3>";
				searchHTML = searchHTML + "<div>";
				searchHTML = searchHTML + "<table style='width: 80%;'>";
				searchHTML = searchHTML + "<colgroup>";
				searchHTML = searchHTML + "<col style='width: 16%' />";
				searchHTML = searchHTML + "<col style='width: 26%' />";
				searchHTML = searchHTML + "<col style='width: 44%' />";
				searchHTML = searchHTML + "<col style='width: 4%' />";
				searchHTML = searchHTML + "<col style='width: 4%' />";
				searchHTML = searchHTML + "<col style='width: 4%' />";
				searchHTML = searchHTML + "<col style='width: 4%' />";
				searchHTML = searchHTML + "</colgroup>";
				searchHTML = searchHTML + "<thead>";
				searchHTML = searchHTML + "<tr>";
				searchHTML = searchHTML + "<th>Activity</th>";
				searchHTML = searchHTML + "<th>Task</th>";
				searchHTML = searchHTML + "<th>Description</th>";
				searchHTML = searchHTML + "<th>Story Points</th>";
				searchHTML = searchHTML + "<th>Active ?</th>";
				searchHTML = searchHTML + "<th colspan='2'>&nbsp;</th>";
				searchHTML = searchHTML + "</tr>";
				searchHTML = searchHTML + "</thead>";
				searchHTML = searchHTML + "<tbody id='timeTable_body' class='reportBody'>";
				
				var checkElmId = null;
				
				for ( var j = 0; j < project.activities.length; j++) {
					
					activity =  project.activities[j];
					
					for ( var k = 0; k < activity.tasks.length; k++) {
						
						task = activity.tasks[k];
						rowId = "task_"+ task.taskId;
						checkElmId = "task_check_" + task.taskId;
						
						searchHTML = searchHTML + "<tr id='"+rowId+"'>";
						searchHTML = searchHTML + "<td>"+activity.activityName+"</td>";
						searchHTML = searchHTML + "<td>"+task.taskName+"</td>";
						searchHTML = searchHTML + "<td>"+trimToEmpty(task.taskDescription)+"</td>";
						searchHTML = searchHTML + "<td>"+task.size+"</td>";
						searchHTML = searchHTML + "<td>";
						searchHTML = searchHTML + "<input type='checkbox' id='" + checkElmId + "' onchange =\"toggleTaskStatus('" + rowId +"','" + checkElmId + "',"+ task.taskId+")\" ";
						
						if(task.active) {
							searchHTML = searchHTML + " checked='checked' />";
						} else {
							searchHTML = searchHTML + " />";
						}
						
						searchHTML = searchHTML + "</td>";
						searchHTML = searchHTML + "<td align='center'>";
						searchHTML = searchHTML + "<img alt='Edit' align='middle' class='icon' title='"+taskEditTitle+"' src='"+editIcon+"' onclick=\"editTask(false,'"+rowId+"',"+task.taskId+")\"/>";
						searchHTML = searchHTML + "</td>";
						searchHTML = searchHTML + "<td align='center'>";
						searchHTML = searchHTML + "<img alt='Delete' align='middle' class='icon' title='"+taskEditTitle+"' src='"+deleteIcon+"' onclick=\"deleteTask('"+rowId+"',"+task.taskId+")\" />";
						searchHTML = searchHTML + "</td>";
						searchHTML = searchHTML + "</tr>";
						
						taskCount++;
					}
					
				}
				
				searchHTML = searchHTML + "</tbody>";
				searchHTML = searchHTML + "</table>";
				searchHTML = searchHTML + "</div>";	
			}

			searchHTML = searchHTML + "</div>";
			searchHTML = searchHTML + "<div style='margin-top: 1em;'>";
			searchHTML = searchHTML + "<p class='thin' style='font-size: 1em;font-weight: normal;'>Total Tasks Found : </p>";
			searchHTML = searchHTML + "<p class='thin' id='count' style='font-size: 1em;font-weight: normal;'>"+taskCount+"</p>";
			searchHTML = searchHTML + "</div>";
			
			//populate the HTML
			$(resultsDivId).html(searchHTML);
			
			//create runtime accordion
			var taskAccId = "#" + TaskConstants.taskSearchResultsAccordion;
			$(taskAccId).accordion(accOptions);
		}
	}, JSON_RESULT_TYPE);
}

function toggleTaskStatus(rowElmId, chkElmId, taskDbId) {

	var checkInput = document.getElementById(chkElmId);

	if ((taskDbId > 0) && (checkInput != null)) {

		var taskStatus = checkInput.checked;

		$.post(JSON_URL, {
			operation : "SAVE_TASK_STATUS",
			id : taskDbId,
			status : taskStatus
		}, function(data) {

			jsonData = data;

			if (jsonData.error) {
				displayAlert(jsonData.error);
			} else {
				var taskRow = document.getElementById(rowElmId);
				
				if (taskStatus) {
					taskRow.className = "activeEntity";
				} else {
					taskRow.className = "inActiveEntity";
				}
			}
		}, JSON_RESULT_TYPE);
	}
	
}

$(function() {

	$("#taskAccordion").accordion({
		autoHeight : false,
		navigation : true,
		collapsible : true,
		active : 2
	});

	var resultsDiv = document.getElementById(TaskConstants.taskResultsAccordionId);

	if (resultsDiv != null) {
		$("#" + TaskConstants.taskResultsAccordionId).accordion({
			autoHeight : false,
			navigation : true,
			collapsible : true,
			active : 0
		});
	}

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
