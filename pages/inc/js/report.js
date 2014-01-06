/**
* Get Report Details.
*/
function viewReportDetails(projId) {

	if (!isNaN(projId)){

		$.getJSON(
			JSON_URL,
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
						detailHTML = detailHTML + "<colgroup><col style='width: 25%' /><col style='width: 25%' /><col style='width: 30%' /><col style='width: 20%' /></colgroup>";

						// head
						detailHTML = detailHTML + "<thead class='reportTotal'><tr><th>Week Start</th><th>Week End</th><th>Activity</th><th>&Sigma;&nbsp;Weekly</th></tr></thead>";

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

						//footer row
						detailHTML = detailHTML + "<tr class='reportTotal'>";
						detailHTML = detailHTML + "<td colspan='3'>TOTAL</td>";
						detailHTML = detailHTML + "<td title='Total across all above weeks'>" + jsonData.totalSum + "</td>";
						detailHTML = detailHTML + "</tr>";


						detailHTML = detailHTML +"</tbody>";
						detailHTML = detailHTML +"</table>";

						$("#reportDetails").html(detailHTML);

						document.getElementById('light').style.display='block';
						document.getElementById('fade').style.display='block';

						document.onkeydown = function(evt) {
							evt = evt || window.event;
							if (evt.keyCode == 27) {
								hideReportDetails();
							}
						};
						
						$("#light").draggable();
					}
				}
			}, 
			JSON_RESULT_TYPE );
	}
}

/**
* Hide Report Details.
*/
function hideReportDetails() {
	document.getElementById('reportDetails').innerHTML ='';
	document.getElementById('light').style.display='none';
	document.getElementById('fade').style.display='none';
}
