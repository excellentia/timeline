/**
 * Allows inline edit of User Account Settings.
 * 
 * @param rowElmId
 * @param userDbId
 */
function editAccountSettings(rowElmId, userDbId) {

	if ((rowElmId != "") && (userDbId > 0)) {

		var selectedRow = document.getElementById(rowElmId);
		
		var maxlen = 20;
		
		if(rowElmId =="EMAIL") {
			maxlen = 50;
		}
		
		var editId = "edit_"+rowElmId;
		
		selectedRow.cells[1].innerHTML = "<input type='text' id = '"+editId+"' value='" + selectedRow.cells[1].innerHTML + "' class='accountAreaEdit' maxlength='"+maxlen+"'/>";
		selectedRow.cells[2].innerHTML = "<img alt='Save' align='middle' class='icon' title='" + saveTitle + "' src='" + saveIcon + "' onclick=\"saveAccountSettings('" + rowElmId + "'," + userDbId + ")\" />";
		focus(editId);
	} else {
		displayAlert("Editing Not Allowed.");
	}
}

/**
 * Saves User Account Settings.
 * 
 * @param elmId
 * @param userDbId
 */
function saveAccountSettings(elmId, userDbId) {

	if ((elmId != null) && (userDbId > 0)) {
		var selectedRow = document.getElementById(elmId);
		var userData = selectedRow.cells[1].children[0].value;
		var oprn = "MODIFY_USER";

		if ((elmId == "QUESTION") || (elmId == "ANSWER") || (elmId == "EMAIL") ) {
			oprn = "MODIFY_USER_PREF";
		}


		if (userData != "") {
			$.post(JSON_URL, {
				operation : oprn,
				id : userDbId,
				field : elmId,
				text : userData
			}, function(data) {

				var jsonData = data;

				if (jsonData.error) {
					displayAlert(jsonData.error);
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
					} else if ("EMAIL" == elmId) {
						savedValue = jsonData.email;
					}

					selectedRow.cells[1].innerHTML = savedValue;

					var editHTML = "<img alt='Edit' align='middle' class='icon' title='" + editTitle + "' src='"
					        + editIcon + "' onclick=\"editAccountSettings('" + elmId + "'," + userDbId + ")\"  />";
					selectedRow.cells[2].innerHTML = editHTML;
				}
			}, JSON_RESULT_TYPE);
		}
	}
}
