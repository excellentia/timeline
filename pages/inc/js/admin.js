var AdminConstants = {
	activityNameCellIdx : 0,
	activityModifyCellIdx : 1,
	activityDeleteCellIdx : 2	
};
/**
 * Deletes a Project.
 * 
 * @param projElementId
 * @param projDbId
 */
function deleteProject(projElementId, projDbId) {

	if (projDbId > 0) {

		var jsonData = null;

		$.post(JSON_URL, {
			operation : "DELETE_PROJECT",
			id : projDbId
		}, function(data) {

			jsonData = data;

			if (jsonData.error) {
				displayAlert(jsonData.error);
			} else {
				var project = document.getElementById(projElementId);
				project.parentNode.removeChild(project);
			}
		}, JSON_RESULT_TYPE);
	}
}

/**
 * Allows Inline Edit of a Project Lead.
 * 
 * @param leadElmId Lead HTML Element Id.
 * @param projDbId Project Database Id.
 * @param leadDbId Lead Database Id.
 */
function editLead(leadElmId, projDbId, leadDbId) {

	if (projDbId > 0) {

		$.getJSON(JSON_URL, {
			operation : "LEAD"
		}, function(data) {

			jsonData = data;

			if (jsonData.error) {
				displayAlert(jsonData.error);
			} else {

				var row = document.getElementById(leadElmId);
				var leadId = projDbId + "_ajaxLeadEdit";

				var leadHTML = "<select size='1' name='leads' class='leadAreaEdit' id='" + leadId + "'>";
				var optionHTML = '';

				for ( var i = 0; i < jsonData.admins.length; i++) {
					optionHTML = optionHTML + '<option value="' + jsonData.admins[i].code + '"';

					if ((leadDbId > 0) && (leadDbId == jsonData.admins[i].code)) {
						optionHTML = optionHTML + ' selected = "selected" ';
					}
					optionHTML = optionHTML + '>' + jsonData.admins[i].value + '</option>';
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

/**
 * Saves a Project Lead.
 * 
 * @param projDbId
 * @param leadElmId
 */
function saveLead(projDbId, leadElmId) {

	if (projDbId > 0) {

		var selectElm = document.getElementById(leadElmId);
		var leadId = 0;
		var leadName = '';

		for ( var i = 0; i < selectElm.options.length; i++) {
			if (selectElm.options[i].selected) {
				leadId = selectElm.options[i].value;
				leadName = selectElm.options[i].innerHTML;
				break;
			}
		}

		if (leadId > 0) {
			$.post(JSON_URL, {
				operation : "SAVE_LEAD",
				id : projDbId,
				refId : leadId
			}, function(data) {

				jsonData = data;

				if (jsonData.error) {
					displayAlert(jsonData.error);
				} else {
					var rowId = selectElm.parentNode.parentNode.id;
					var row = document.getElementById(rowId);
					var projLeadId = "project_" + projDbId + "_lead";
					row.cells[0].innerHTML = leadName;
					row.cells[0].className = "leadArea";
					row.cells[1].innerHTML = "<img alt=\"Edit Lead\" align=\"middle\" class=\"icon\" title=\""
							+ editTitle + "\" src=\"" + editIcon + "\" onclick=\"editLead('" + projLeadId + "',"
							+ projDbId + "," + leadId + ")\"/>";

				}
			}, JSON_RESULT_TYPE);
		} else {
			displayAlert("Admin cannot be Lead");
		}
	}
}

/**
 * Deletes a Project Activity.
 * 
 * @param actElementId
 * @param actDbId
 */
function deleteActivity(actElementId, actDbId) {

	if (actDbId > 0) {
		$.post(JSON_URL, {
			operation : "DELETE_ACTIVITY",
			id : actDbId
		}, function(data) {

			jsonData = data;

			if (jsonData.error) {
				displayAlert(jsonData.error);
			} else {
				var activity = document.getElementById(actElementId);
				activity.parentNode.removeChild(activity);
			}
		}, JSON_RESULT_TYPE);
	} else {

		var actRow = document.getElementById(actElementId);
		actRow.parentNode.removeChild(actRow);
	}
}

/**
 * Saves a Project Activity.
 * 
 * @param actElmId
 * @param actDbId
 * @param projDbId
 */
function saveActivity(actElmId, actDbId, projDbId) {

	if (actElmId != "") {
		var actRow = document.getElementById(actElmId);
		var actInput = actRow.children[0].children[0];
		var text = actInput.value;

		if (text != "") {
			$.post(JSON_URL, {
				operation : "SAVE_ACTIVITY",
				id : projDbId,
				refId : actDbId,
				text : text
			}, function(data) {

				jsonData = data;

				if (jsonData.error) {
					displayAlert(jsonData.error);
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
			}, JSON_RESULT_TYPE);
		}
	}
}

/**
 * Allows Inline Editing of A Project Activity.
 * 
 * @param actElmId
 * @param actDbId
 * @param projDbId
 */
function editActivity(actElmId, actDbId, projDbId) {

	var actRow = document.getElementById(actElmId);
	var nameCell = actRow.cells[AdminConstants.activityNameCellIdx];
	nameCell.className = null;
	
	var oldValue = nameCell.innerHTML;
	var editRowId = "name_"+actElmId;
	
	var actInput = "<input type='text' name='activityText' id='"+editRowId+"' value='" + oldValue + "' class='activityAreaEdit' />";
	nameCell.innerHTML = actInput;

	actRow.children[AdminConstants.activityModifyCellIdx].innerHTML = "<img alt='Save' align='middle' class='icon' title='" + saveTitle + "' src='"
			+ saveIcon + "' onclick=\"saveActivity('" + actElmId + "'," + actDbId + "," + projDbId + ")\"/>";
	actRow.children[AdminConstants.activityDeleteCellIdx].innerHTML = "<img alt='Delete' align='middle' class='icon' title='" + activityDeleteTitle
			+ "' src='" + deleteIcon + "' onclick=\"deleteActivity('" + actId + "'," + actDbId + ")\"/>";
		
	focus(editRowId);
}

/**
 * Creates a New Project Activity Row.
 * 
 * @param tableID
 * @param projDbId
 */
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

function toggleProjectStatus(projDbId, inputId, projTitleId) {

	var checkInput = document.getElementById(inputId);

	if (checkInput != null) {

		var projectStatus = checkInput.checked;

		$.post(JSON_URL, {
			operation : "SAVE_PROJECT_STATUS",
			id : projDbId,
			status : projectStatus
		}, function(data) {

			jsonData = data;

			if (jsonData.error) {
				displayAlert(jsonData.error);
			} else {
				// var projectId = jsonData.code;
				var projectName = jsonData.value;
				var projTitleArea = document.getElementById(projTitleId);
				if (projectStatus) {
					projTitleArea.className = "activeEntity";
				} else {
					projTitleArea.className = "inActiveEntity";
				}
			}
		}, JSON_RESULT_TYPE);
	}
}

function toggleDefaultActivity( rowElmId, actDbId, inputElmId) {
	
}

function toggleUserStatus(userDbId, inputId, userRowId) {

	var checkInput = document.getElementById(inputId);

	if (checkInput != null) {

		var userStatus = checkInput.checked;

		$.post(JSON_URL, {
			operation : "SAVE_USER_STATUS",
			id : userDbId,
			status : userStatus
		}, function(data) {

			jsonData = data;

			if (jsonData.error) {
				displayAlert(jsonData.error);
			} else {
				var userName = jsonData.value;

				var rowId = document.getElementById(userRowId);
				if (userStatus) {
					rowId.className = "activeEntity";
				} else {
					rowId.className = "inActiveEntity";
				}
			}
		}, JSON_RESULT_TYPE);
	}
}

/**
 * Saves a Project.
 * 
 * @param divID
 * @param projDbId
 * @param projNameId
 */
function saveProject(divID, projDbId, projNameId, copyProjElmId) {

	var projText = $("#" + projNameId).val();

	if (projText != "") {
		var copyProjId = getDropDownValueAsInt(copyProjElmId);
		
		$.post(
			JSON_URL,
			{
				operation : "SAVE_PROJECT",
				id : projDbId,
				text : projText,
				refId : copyProjId
			},
			function(data) {

				var jsonData = data;

				if (jsonData.error) {
					displayAlert(jsonData.error);
				} else {
					var projectId = jsonData.code;
					var projectText = jsonData.value;

					if (projDbId <= 0) {

						var projRowId = 'project_' + projectId + '_title';
						var statusId = 'project_status_' + projectId;

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

						var projLeadRowId = projectText + "_lead";
						var projLeadId = 0;
						newHTML = newHTML + "<tr id='" + projLeadRowId + "'>";
						newHTML = newHTML + "<td class='leadArea'>Please select...</td>";
						newHTML = newHTML
								+ "<td align='center' colspan='2'><img alt='Edit Lead' align='middle' class='icon' title='"
								+ editTitle + "' src='" + editIcon + "' onclick=\"editLead('"
								+ projLeadRowId + "'," + projectId + "," + projLeadId + ")\" /></td>";
						newHTML = newHTML + "</tr>";

						newHTML = newHTML + "</tbody>";
						newHTML = newHTML + "<tfoot><tr>";
						newHTML = newHTML + "<td align='left'>&nbsp;Active&nbsp;<input id='" + statusId
								+ "' type='checkbox' value='true' onchange=\"toggleProjectStatus("
								+ projectId + ",'" + statusId + "','" + projRowId + "')\"/></td>";
						newHTML = newHTML + "<td colspan='2' align='right'>";
						newHTML = newHTML
								+ "<input type='button' value='Add Activity' class='button' onclick=\"addNewActivity('"
								+ projectText + "'," + projectId + ")\" />";
						newHTML = newHTML + "</td></tfoot>";
						newHTML = newHTML + "</table>";

						$("#" + divID).append(newHTML);
						$("#" + projNameId).val("");
						displayAlert("Project Created");
					} else {
						var row = document.getElementById(divID);
						row.cells[0].innerHTML = projectText;
						var editHTML = "<img alt='Edit' align='middle' class='icon' title='" + editTitle
								+ "' src='" + editIcon + "' onclick=\"editProject('" + divID + "',"
								+ projectId + ")\" />";
						row.cells[1].innerHTML = editHTML;
						displayAlert("Project Updated");
					}
				}
			}, JSON_RESULT_TYPE);
	} else {
		displayAlert(errorMsgNoData);
	}
}

/**
 * Adds a New User Row.
 * 
 * @param tableID
 */
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
	newcell = row.insertCell(cellNum++);
	newcell.innerHTML = firstNameHTML;
	newcell.align = "center";

	/**
	 * Create Last Name Column
	 */
	newcell = row.insertCell(cellNum++);
	newcell.innerHTML = lastNameHTML;
	newcell.align = "center";

	/**
	 * Create Admin Column
	 */
	newcell = row.insertCell(cellNum++);

	/**
	 * Create Active/InActive Column
	 */
	newcell = row.insertCell(cellNum++);
	newcell.align = "center";

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

/**
 * Allows inline editing of a Project.
 * 
 * @param projTitleId
 * @param projDbId
 */
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

/**
 * Deletes a user.
 * 
 * @param userElmId
 * @param userDbId
 */
function deleteUser(userElmId, userDbId) {

	if (userDbId > 0) {
		$.post(JSON_URL, {
			operation : "DELETE_USER",
			id : userDbId
		}, function(data) {

			jsonData = data;

			if (jsonData.error) {
				displayAlert(jsonData.error);
			} else {
				var userRow = document.getElementById(userElmId);
				userRow.parentNode.removeChild(userRow);
			}
		}, JSON_RESULT_TYPE);
	} else if (userDbId == 0) {
		var userRow = document.getElementById(userElmId);
		userRow.parentNode.removeChild(userRow);
	} else {
		displayAlert('Cannot Remove Admin');
	}
}

/**
 * Allows Inline edit of a User.
 * 
 * @param userElmId
 * @param userDbId
 */
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

	var adminHTML = "<input type='checkbox' title='Toggle Admin Status'";
	row.cells[3].align = "center";

	if (row.cells[3].children[0] != null) {
		adminHTML = adminHTML + "checked ='true'";
	}

	adminHTML = adminHTML + " >";
	row.cells[3].innerHTML = adminHTML;

	var saveHTML = "<img alt='Save' align='middle' class='icon' title='Save This Text' src='" + saveIcon
			+ "' onclick=\"saveUser('" + userElmId + "'," + userDbId + ")\" />";

	row.cells[5].innerHTML = saveHTML;
}

/**
 * Saves a User.
 * 
 * @param userElmId
 * @param userDbId
 */
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
		
		var userStatus = false;
		
		if (userRow.children[4].children[0] != null) {
			userStatus = userRow.children[4].children[0].checked;
		}

		if ((userId != "") && (fName != "") && (lName != "")) {
			$.post(JSON_URL, {
				operation : "SAVE_USER",
				id : userDbId,
				USER_ID : userId,
				FIRST_NAME : fName,
				LAST_NAME : lName,
				ADMIN : admin
			}, function(data) {

				jsonData = data;

				if (jsonData.error) {
					displayAlert(jsonData.error);
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

					var userStatusId = "user_status_" + jsonData.id;
					var statusHTML = "<input id='" + userStatusId + "' type='checkbox'";

					if (userStatus == true) {
						statusHTML = statusHTML + " checked='checked' onchange=\"toggleUserStatus(" + jsonData.id + ",'"
								+ userStatusId + "','"+userElmId+"')\" />";
					} else {
						statusHTML = statusHTML + " onchange=\"toggleUserStatus(" + jsonData.id + ",'" + userStatusId
								+ "','"+userElmId+"')\" />";
					}

					userRow.children[4].innerHTML = statusHTML;

					var editHTML = "<img alt='Edit' align='middle' class='icon' title='" + editTitle + "' src='"
							+ editIcon + "' onclick=\"editUser('" + userElmId + "'," + jsonData.id + ")\" />";
					userRow.children[5].innerHTML = editHTML;

					var resetHTML = "<img alt='Reset' align='middle' class='icon' title='" + resetTitle + "' src='"
							+ resetIcon + "' onclick=\"resetUser('user_" + jsonData.id + "'," + jsonData.id + ")\"/>";
					userRow.children[6].innerHTML = resetHTML;

					var saveHTML = "<img alt='Delete' align='middle' class='icon' title='" + userDeleteTitle
							+ "' src='" + deleteIcon + "' onclick=\"deleteUser('" + userElmId + "', " + jsonData.id
							+ ")\" />";
					userRow.children[7].innerHTML = saveHTML;
				}
			}, JSON_RESULT_TYPE);
		} else {
			displayAlert(errorMsgNoData);
		}

	}
}

/**
 * Resets User credentials.
 * 
 * @param userElmId
 * @param userDbId
 */
function resetUser(userElmId, userDbId) {

	if (userDbId > 0) {

		$.post(JSON_URL, {
			operation : "RESET_USER",
			id : userDbId
		}, function(data) {

			jsonData = data;

			if (jsonData.error) {
				displayAlert(jsonData.error);
			} else {
				displayAlert(jsonData.message);
			}
		}, JSON_RESULT_TYPE);
	}
}

$(function() {

	$("#adminAccordion").accordion({
		autoHeight : false,
		navigation : true,
		collapsible : true,
		active : 2
	});

});
