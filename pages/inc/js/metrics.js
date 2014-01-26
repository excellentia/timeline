var MIN_INDEX = 0.96;
var MAX_INDEX = 0.99;
var MIN_DEFECT_RATIO = 0.01;

var metricEntryDate = null;

var METRICS_ACTION = "MetricsDetailsAction.action";

var errorStyle = "border: 1px solid #FF0000; background : #FFDCDC; color : #FF0000;";
var successStyle = "border: 1px solid #00A200; background : #C0FFC0; color : #00A200;";
var originalMetricEntryDateStyle = "width: 12.5em; text-align: center; ";
var originalMetricEntryDateTitle = "Click Calendar Icon To Select A Week";

var EstimateData = function() {

	var bac = 0.0;
	var startDate = null;
	var endDate = null;
};

var MetricData = function() {

	var pv = 0.0;
	var ev = 0.0;
	var ac = 0.0;
	var atd = 0.0;
	var spe = 0.0;
	var defects = 0;
	var metricDate = null;
}

var CalculatedMetrics = function() {

	var etc = 0.0;
	var cpi = 0.0;
	var spi = 0.0;
	var ratio = 0.0;
}

/**
 * Allows inline edit of metrics.
 * 
 * @param rowHtmlId
 * @param prevRowHtmlId
 * @param projectDbId
 * @param metricDbId
 * @param bac
 */
function editMetricDetails(rowHtmlId, prevRowHtmlId, projectDbId, metricDbId, bac) {

	if ((rowHtmlId != null) && (projectDbId > 0) && (metricDbId > 0) && (bac > 0)) {

		var selectedRow = document.getElementById(rowHtmlId);

		if (selectedRow != null) {

			selectedRow.cells[1].innerHTML = "<input type='text' class='metricEntry timeEntry' id='pv' value='"
					+ trimToNull(selectedRow.cells[1].innerHTML) + "'/>";
			selectedRow.cells[2].innerHTML = "<input type='text' class='metricEntry timeEntry' id='ev' value='"
					+ trimToNull(selectedRow.cells[2].innerHTML) + "'/>";
			selectedRow.cells[3].innerHTML = "<input type='text' class='metricEntry timeEntry' id='ac' value='"
					+ trimToNull(selectedRow.cells[3].innerHTML) + "'/>";
			selectedRow.cells[4].innerHTML = "<input type='text' class='metricEntry timeEntry' id='atd' value='"
					+ trimToNull(selectedRow.cells[4].innerHTML) + "'/>";
			selectedRow.cells[5].innerHTML = "<input type='text' class='metricEntry timeEntry' id='spe' value='"
					+ trimToNull(selectedRow.cells[5].innerHTML) + "'/>";
			selectedRow.cells[6].innerHTML = "<input type='text' class='metricEntry timeEntry' id='defects' value='"
					+ trimToNull(selectedRow.cells[6].innerHTML) + "'/>";
					
			var prevRowHtmlIdVal = trimToNull(prevRowHtmlId);
			
			if(prevRowHtmlIdVal == null) {
				prevRowHtmlIdVal = "";
			}

			var saveHtml = "<img alt='Save' align='middle' class='icon' title='Save' src='" + saveIcon
					+ "' onclick=\"saveModifiedMetrics('" + rowHtmlId + "','" + prevRowHtmlIdVal + "',"
					+ projectDbId + ", " + metricDbId + ", " + bac + ")\"/>";
			selectedRow.cells[17].innerHTML = saveHtml;
		}
	}
}

/**
 * Saves Metrics modified.
 * 
 * @param rowHtmlId
 * @param prevRowHtmlId
 * @param projectDbId
 * @param metricDbId
 * @param bac
 */
function saveModifiedMetrics(rowHtmlId, prevRowHtmlId, projectDbId, metricDbId, bac) {

	//TODO:AG
	displayAlert(rowHtmlId + " : " + prevRowHtmlId + " : " + projectDbId + " : " + metricDbId + " : " + bac);

	if ((rowHtmlId != null) && (projectDbId > 0) && (metricDbId > 0) && (bac > 0)) {

		var selectedRow = document.getElementById(rowHtmlId);

		if (selectedRow != null) {

			var pv = getPreciseValue(getValidFloat(selectedRow.cells[1].children[0].value), 1);
			var ev = getPreciseValue(getValidFloat(selectedRow.cells[2].children[0].value), 1);
			var ac = getPreciseValue(getValidFloat(selectedRow.cells[3].children[0].value), 1);
			var atd = getPreciseValue(getValidFloat(selectedRow.cells[4].children[0].value), 1);
			var spe = getPreciseValue(getValidFloat(selectedRow.cells[5].children[0].value), 1);
			var defects = getValidInt(selectedRow.cells[6].children[0].value);

			var cumlPV = 0;
			var cumlEV = 0;
			var cumlAC = 0;
			var cumlATD = 0;
			var cumlSPE = 0;
			var cumlDefects = 0;

			var etc = 0;
			var cpi = 0;
			var spi = 0;
			var ratio = 0;

			if (trimToNull(prevRowHtmlId) == null) {

				cumlPV = getPreciseValue(pv, 1);
				cumlEV = getPreciseValue(ev, 1);
				cumlAC = getPreciseValue(ac, 1);
				cumlATD = getPreciseValue(atd, 1);
				cumlSPE = getPreciseValue(spe, 1);
				cumlDefects = defects;

			} else {

				var prevRow = document.getElementById(prevRowHtmlId);

				if (prevRow != null) {

					cumlPV = getPreciseValue(pv + trimToNull(prevRow.cells[1].innerHTML), 1);
					cumlEV = getPreciseValue(ev + trimToNull(prevRow.cells[2].innerHTML), 1);
					cumlAC = getPreciseValue(ac + trimToNull(prevRow.cells[3].innerHTML), 1);
					cumlATD = getPreciseValue(atd + trimToNull(prevRow.cells[4].innerHTML), 1);
					cumlSPE = getPreciseValue(spe + trimToNull(prevRow.cells[5].innerHTML), 1);
					cumlDefects = defects + trimToNull(prevRow.cells[6].innerHTML);
				}
			}

			etc = getPreciseValue(bac - ev, 1);
			cpi = getPreciseValue(ev / ac, 3);
			spi = getPreciseValue(ac / pv, 3);
			ratio = getPreciseValue(defects / spe, 3);

			//update the modified row
			selectedRow.cells[1].innerHTML = pv;
			selectedRow.cells[2].innerHTML = ev;
			selectedRow.cells[3].innerHTML = ac;
			selectedRow.cells[4].innerHTML = atd;
			selectedRow.cells[5].innerHTML = spe;
			selectedRow.cells[6].innerHTML = defects;

			selectedRow.cells[7].innerHTML = cumlPV;
			selectedRow.cells[8].innerHTML = cumlEV;
			selectedRow.cells[9].innerHTML = cumlAC;
			selectedRow.cells[10].innerHTML = cumlATD;
			selectedRow.cells[11].innerHTML = cumlSPE;
			selectedRow.cells[12].innerHTML = cumlDefects;

			selectedRow.cells[13].innerHTML = etc;
			selectedRow.cells[14].innerHTML = cpi
			selectedRow.cells[15].innerHTML = spi;
			selectedRow.cells[16].innerHTML = ratio;
		}
	}
}

/**
 * Handles the inline edit behaviour of Project BAC and Dates.
 * 
 * @param rowHTMLId HTML id of the row to be inline edited.
 * @param projDbId Database id of the project being edited.
 */
function editProjectBasicData(rowHTMLId, projDbId) {

	if ((rowHTMLId != null) && (projDbId > 0)) {

		var selectedRow = document.getElementById(rowHTMLId);

		if ((selectedRow != null)) {

			//BAC cell
			selectedRow.cells[2].innerHTML = "<input type='text' id='bac_" + rowHTMLId + "' value='"
					+ selectedRow.cells[2].innerHTML + "' class='bacEdit' maxlength='10'/>";

			//Start Date cell
			selectedRow.cells[3].innerHTML = "<input type='text' id='startDate_" + rowHTMLId + "' value='"
					+ selectedRow.cells[3].innerHTML
					+ "' class='dateEdit' readonly='readonly' maxlength='10' title='Click to Enter Start Date'/>";

			//End Date cell
			selectedRow.cells[4].innerHTML = "<input type='text' id='endDate_" + rowHTMLId + "' value='"
					+ selectedRow.cells[4].innerHTML
					+ "' class='dateEdit' readonly='readonly' maxlength='10' title='Click to Enter End Date'/>";

			//Save Button
			selectedRow.cells[16].innerHTML = "<img alt='Save' align='middle' class='icon' title='Save BAC and Project Dates' src='"
					+ saveIcon + "' onclick=\"saveProjectBasicData('" + rowHTMLId + "'," + projDbId + ")\"/>";

			//attach date picker behaviour
			$(".dateEdit").datepicker({
				showOtherMonths : true,
				selectOtherMonths : true,
				changeMonth : true,
				changeYear : true,
				firstDay : 1,
				dateFormat : "dd M yy"
			});
		}
	}
}

/**
 * Deletes Project Metrics.
 * 
 * @param rowHTMLId
 * @param projDbId
 */
function deleteProjectMetrics(rowHTMLId, projDbId) {

	document.getElementById(rowHTMLId).className = "deleteHighlight";

	$(this).dialog({
		title : "Confirm Delete (Project Dates, BAC & All Submitted Metrics)?",
		resizable : false,
		height : 50,
		modal : true,
		buttons : {
			"Delete" : function() {

				document.getElementById(rowHTMLId).className = "";
				$(this).dialog("close");

				$.post(JSON_URL, {
					operation : "DELETE_ALL_METRICS",
					projectId : projDbId
				}, function(data) {

					var jsonData = data;

					if (jsonData.error) {
						displayAlert(jsonData.error);
					} else {

						var projRow = document.getElementById(rowHTMLId);

						projRow.cells[2].innerHTML = "0.0";
						projRow.cells[3].innerHTML = "";
						projRow.cells[4].innerHTML = "";
					}
				}, JSON_RESULT_TYPE);
			},
			Cancel : function() {

				document.getElementById(rowHTMLId).className = "";
				$(this).dialog("close");
			}
		}
	});
}

/**
 * Saves Project Basic Data,
 * 
 * @param rowHTMLId
 * @param projDbId
 */
function saveProjectBasicData(rowHTMLId, projDbId) {

	if ((rowHTMLId != null) && (projDbId > 0)) {

		var selectedRow = document.getElementById(rowHTMLId);

		if (selectedRow != null) {

			var errMsg = "";
			var startDateTxt = selectedRow.cells[3].children[0].value;
			var endDateTxt = selectedRow.cells[4].children[0].value;

			var bacVal = getValidFloat(selectedRow.cells[2].children[0].value);

			if (bacVal <= 0) {
				errMsg = "BAC must be non-zero. ";
			}

			var startDateVal = getValidDate(startDateTxt);

			if (startDateVal == null) {
				errMsg = errMsg + "Start Date is required. ";
			}

			var endDateVal = getValidDate(endDateTxt);

			if (endDateVal == null) {
				errMsg = errMsg + "End Date is required. ";
			}

			if ((startDateVal != null) && (endDateVal != null) && (endDateVal < startDateVal)) {
				errMsg = errMsg + "End Date must be later than Start Date.";
			}

			if (errMsg != "") {
				displayAlert(errMsg);
			} else {

				$.post(
					JSON_URL,
					{
						operation : "SAVE_ESTIMATES",
						projectId : projDbId,
						bac : bacVal,
						startDate : startDateTxt + "",
						endDate : endDateTxt + ""
					},
					function(data) {
	
						var jsonData = data;
	
						if (jsonData.error) {
							displayAlert(jsonData.error);
						} else {
	
							var projRow = document.getElementById(rowHTMLId);
	
							projRow.cells[2].innerHTML = bacVal;
							projRow.cells[3].innerHTML = startDateTxt;
							projRow.cells[4].innerHTML = endDateTxt;
	
							var editHTML = "<img alt='Edit' class='icon' align='middle' title='Edit BAC and Project Dates' src='"
									+ editIcon
									+ "' onclick=\"editProjectBasicData('"
									+ rowHTMLId
									+ "',"
									+ projDbId + ")\"\>";
	
							projRow.cells[16].innerHTML = editHTML;
	
						}
					}, JSON_RESULT_TYPE);
			}
		}
	}
}

/**
 * Fetches Detailed Project Metrics.
 * 
 * @param selectElmId
 * @param targetElmId
 */
function getDetailedProjectMetrics(selectElmId, targetElmId) {

	if (selectElmId != null) {

		var selectedElm = document.getElementById(selectElmId);

		if (selectedElm != null) {
			var detailedProjId = getValidInt(selectedElm.value);

			if (detailedProjId > 0) {

				$.post(METRICS_ACTION, {
					projectId : detailedProjId
				}, function(data) {

					var htmlText = data;

					if (checkValidText(htmlText)) {

						var targetElm = document.getElementById(targetElmId);
						targetElm.innerHTML = htmlText;

					}

				}, TEXT_RESULT_TYPE);

			} else {

				var targetElm = document.getElementById(targetElmId);
				targetElm.innerHTML = "";

				displayAlert("Select A Project");

			}
		}
	}
};

/**
 * Utility Method -  Hides target html element.
 * 
 * @param elmHtmlId
 */
function hideMetricEntry(elmHtmlId) {

	$("#" + elmHtmlId).html("");
}

/**
 * Creates New Metrics Html content.
 * 
 * @returns {String}
 */
function getNewMetricHtml() {

	var htmlText = "<div class='tableSpacing' id='metricEntryTable'>";

	htmlText = htmlText + "<table style='width: 70%;'>";
	htmlText = htmlText + "<colgroup><col style='width: 32%' /><col style='width: 10%' /><col style='width: 10%' />";
	htmlText = htmlText
			+ "<col style='width: 10%' /><col style='width: 10%' /><col style='width: 10%' /><col style='width: 10%' />";
	htmlText = htmlText + "<col style='width: 8%' /></colgroup>";
	htmlText = htmlText + "<thead>";
	htmlText = htmlText + "<tr align='center'>";
	htmlText = htmlText + "<th class='detailTabArea' id='entryProjCell'></th>";
	htmlText = htmlText + "<th class='whiteBorderHeader' colspan='7'></th>";
	htmlText = htmlText + "</tr>";
	htmlText = htmlText + "<tr>";
	htmlText = htmlText + "<th>Period</th>";
	htmlText = htmlText + "<th title='Weekly Planned Value'>PV</th>";
	htmlText = htmlText + "<th title='Weekly Earned Value'>EV</th>";
	htmlText = htmlText + "<th title='Weekly Actual Cost'>AC</th>";
	htmlText = htmlText + "<th title='Weekly Actuals To Date'>ATD</th>";
	htmlText = htmlText + "<th title='Weekly Software Project Effort'>SPE</th>";
	htmlText = htmlText + "<th title='Weekly  Defects'>Bugs</th>";
	htmlText = htmlText + "<th>&nbsp;</th>";
	htmlText = htmlText + "</tr>";
	htmlText = htmlText + "</thead>";
	htmlText = htmlText + "<tbody class='reportBody'>";
	htmlText = htmlText + "<tr id='metricEntryRow'>";
	htmlText = htmlText + "<td id='dateCell'>";
	htmlText = htmlText + "<div class='thin'>";
	htmlText = htmlText
			+ "<input id='metricWeekEntry' value='Select A Week' type='text' class='dateSearch' style='width: 12.5em;text-align:center' readonly='readonly' title='Click Calendar Icon To Select A Week' disabled='disabled' />";
	htmlText = htmlText + "<input id='metricWeekPicker' type='hidden' />";
	htmlText = htmlText + "<img id='metricWeekImg' title='Click To Select A Week' alt='Click To Select A Week' src='"
			+ calendarIcon + "'/>";
	htmlText = htmlText + "</div>";
	htmlText = htmlText + "</td>";
	htmlText = htmlText + "<td id='pvCell'><input type='text' class='metricEntry timeEntry' id='pv' value='0'/></td>";
	htmlText = htmlText + "<td id='evCell'><input type='text' class='metricEntry timeEntry' id='ev' value='0'/></td>";
	htmlText = htmlText + "<td id='acCell'><input type='text' class='metricEntry timeEntry' id='ac' value='0'/></td>";
	htmlText = htmlText + "<td id='atdCell'><input type='text' class='metricEntry timeEntry' id='atd' value='0'/></td>";
	htmlText = htmlText + "<td id='speCell'><input type='text' class='metricEntry timeEntry' id='spe' value='0'/></td>";
	htmlText = htmlText
			+ "<td id='defectsCell'><input type='text' class='metricEntry timeEntry' id='defects' value='0'/></td>";
	htmlText = htmlText + "<td align='center'>";
	htmlText = htmlText + "<img alt='Save' align='middle' class='icon' title='Save' src='" + saveIcon
			+ "' onclick=\"saveMetricDetails('metricEntryRow')\"/>";
	htmlText = htmlText + "</td>";
	htmlText = htmlText + "</tr>";
	htmlText = htmlText + "</tbody>";
	htmlText = htmlText + "</table>";
	htmlText = htmlText + "</div>";

	return htmlText;
}

/**
 * Shows Metric Entry Area.
 * 
 * @param metricEntryAreaId
 */
function showMetricEntryArea(metricEntryAreaId) {

	var projSelected = $("#metricEntryProjId :selected");

	if (projSelected != null) {
		var entryProjId = projSelected.val();

		if (entryProjId > 0) {

			//ajax call for fetching Project Estimates
			$.post(JSON_URL, {
				operation : "SEARCH_ESTIMATES",
				projectId : entryProjId

			}, function(data) {

				var jsonData = data;

				if (jsonData.error) {
					displayAlert(jsonData.error);
				} else {
					var errMsg = validateEstimatePresent(jsonData);

					if (errMsg != null) {
						//means there is some errorMsgNoData
						errMsg = "Cannot Enter Metrics. Missing Project Level Data : " + errMsg;
						displayAlert(errMsg);
					} else {

						var newMetricHtml = getNewMetricHtml();
						$("#newMetricDiv").html(newMetricHtml);

						var entryProjName = projSelected.text();
						$("#entryProjCell").html("Enter Metrics : " + entryProjName);

						$(".metricEntry").change(
								function() {

									var cellVal = this.value;
									if ((cellVal == null) || (cellVal.trim() == '') || (cellVal.trim() == "")
											|| (isNaN(cellVal))) {
										this.value = 0;
									}
								});

						metricEntryDate = null;

						var selectCurrentWeek = function() {

							window.setTimeout(function() {

								$('.ui-weekpicker').find('.ui-datepicker-current-day a').addClass('ui-state-active')
										.removeClass('ui-state-default');
							}, 1);
						};

						var startDate;
						var endDate;

						var setDates = function(input) {

							var $input = $(input);
							var date = $input.datepicker('getDate');

							if (date !== null) {
								var firstDay = $input.datepicker("option", "firstDay");
								var dayAdjustment = date.getDay() - firstDay;

								if (dayAdjustment < 0) {
									dayAdjustment += 7;
								}

								// get the week start & end day
								startDate = new Date(date.getFullYear(), date.getMonth(), date.getDate()
										- dayAdjustment);
								endDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - dayAdjustment
										+ 6);

								// store the week start day
								metricEntryDate = $.datepicker.formatDate('dd.mm.yy', startDate);

								// create the display text
								var weekDetails = $.datepicker.formatDate('dd M', startDate) + ' - ';
								var year = $.datepicker.formatDate('yy', startDate);
								weekDetails = weekDetails + $.datepicker.formatDate('dd M', endDate) + ', ' + year;

								// set the formatted date in textbox
								$("#metricWeekEntry").val(weekDetails);

							}
						};

						$('#metricWeekPicker').datepicker({
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

								setDates(this);
								$(this).datepicker("hide");

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

						setDates('#metricWeekPicker');

						var $calendarTR = $('.ui-weekpicker .ui-datepicker-calendar tr');

						$calendarTR.live('mousemove', function() {

							$(this).find('td a').addClass('ui-state-hover');
						});

						$calendarTR.live('mouseleave', function() {

							$(this).find('td a').removeClass('ui-state-hover');
						});

						//handle click behaviour
						$("#metricWeekImg").click(function() {

							$("#metricWeekPicker").datepicker('show');
						});

					}
				}
			}, JSON_RESULT_TYPE);
		} else {
			hideMetricEntry("newMetricDiv");
			displayAlert("Select A Project");
		}
	}
}

/**
 * Utility Method - Validates if the Estimate is present.
 * 
 * @param jsonData
 * @returns
 */
function validateEstimatePresent(jsonData) {

	var errMsg = "";

	var bac = getValidFloat(jsonData.bac);

	if (bac <= 0) {
		errMsg = errMsg + "[BAC] ";
	}

	var projStartDate = trimToNull(jsonData.startDate);

	if (projStartDate == null) {
		errMsg = errMsg + "[Start Date] ";
	}

	var projEndDate = trimToNull(jsonData.endDate);

	if (projEndDate == null) {
		errMsg = errMsg + "[End Date] ";
	}

	return trimToNull(errMsg);
}

/**
 * Adds an error class and a title as message.
 * 
 * @param elmHtmlId
 * @param errMsg
 */
function addError(elmHtmlId, errMsg) {

	$("#" + elmHtmlId).attr("style", errorStyle).attr("title", errMsg);
}

/**
 * Adds a success css class.
 * 
 * @param elmHtmlId
 */
function addSuccess(elmHtmlId) {

	$("#" + elmHtmlId).attr("style", successStyle).removeAttr("title");
}

/**
 * Checks if a date is between 2 given dates.
 * 
 * @param estimateData
 * @param enteredMetrics
 * @returns {Boolean}
 */
function isDateInRange(estimateData, enteredMetrics) {

	var isValid = false;

	var projStartDate = estimateData.startDate;
	var projEndDate = estimateData.endDate;
	var enteredDate = enteredMetrics.metricDate;

	var start = projStartDate.setHours(0,0,0,0);
	var end = projEndDate.setHours(0,0,0,0);
	var diff = end-start;
	
	if(diff >= 0) {
		isValid = true;
	}
	
	return isValid;
}

/**
 * Validates metric numbers.
 * 
 * @param entered
 * @returns {Boolean}
 */
function validateNumbers(entered) {

	var hasError = false;

	if (entered.pv <= 0) {
		hasError = true;
		addError("pv", "PV should be +ve");
	} else {
		addSuccess("pv");
	}

	if (entered.ev <= 0) {
		hasError = true;
		addError("ev", "EV should be +ve");
	} else {
		addSuccess("ev");
	}

	if (entered.ac <= 0) {
		hasError = true;
		addError("ac", "AC should be +ve");
	} else {
		addSuccess("ac");
	}

	if (entered.atd <= 0) {
		hasError = true;
		addError("atd", "ATD should be +ve");
	} else {
		addSuccess("atd");
	}

	if (entered.spe < 0) {
		hasError = true;
		addError("spe", "SPE cannot be -ve");
	} else {
		addSuccess("spe");
	}

	if (entered.defects < 0) {
		hasError = true;
		addError("defects", "Bugs cannot be -ve");
	} else {
		addSuccess("defects");
	}

	return hasError;
}

/**
 * Validates metric dates.
 * 
 * @param estimate
 * @param entered
 * @returns {Boolean}
 */
function validateDates(estimate, entered) {

	var hasError = false;

	var newStyle = null;

	if (!isDateInRange(estimate, entered)) {
		hasError = true;
		newStyle = originalMetricEntryDateStyle + errorStyle;
		$("#metricWeekEntry").attr("style", newStyle).attr("title",
				"Selected Week is not within Project Start & End Dates");

	} else {

		newStyle = originalMetricEntryDateStyle + successStyle;
		$("#metricWeekEntry").attr("style", newStyle).attr("title", originalMetricEntryDateTitle);
	}

	return hasError;
}

/**
 * Resets metric entry area.
 */
function resetMetricEntryArea() {

	$("#metricWeekEntry").attr("title", originalMetricEntryDateTitle).attr("style", originalMetricEntryDateStyle).val(
			"Select A Week");
	$("#metricWeekPicker").removeAttr("value");

	$("#metricEntryRow .metricEntry").removeAttr("style").val("0");

}

/**
 * Validates metric data with Business Rules.
 * 
 * @param entered
 * @returns {Boolean}
 */
function applyDataSanityRules(entered) {

	var hasError = false;

	//Check EV <= PV 
	if (entered.ev > entered.pv) {
		hasError = true;
		addError("ev", "EV Cannot Be Greater Than PV");
	}

	//Check if AC >= EV 
	if (entered.ac < entered.ev) {
		hasError = true;
		addError("ac", "AC Cannot Be Less Than EV");
	}

	//Check if ATD >= AC 
	if (entered.atd < entered.ac) {
		hasError = true;
		addError("atd", "ATD Cannot Be Less Than AC");
	}

	//Check if SPE <= EV 
	if (entered.spe > entered.ev) {
		hasError = true;
		addError("spe", "SPE Cannot Be Greater Than EV");
	}

	return hasError;
}

/**
 * Validates metric data with Quality Rules.
 * 
 * @param calculated
 * @returns {Boolean}
 */
function applyQualityRules(calculated) {

	var hasError = false;

	// Check MIN_INDEX <= CPI <= MAX_INDEX 
	if ((calculated.cpi < MIN_INDEX) || (calculated.cpi > MAX_INDEX)) {
		hasError = true;
		var cpi = calculated.cpi;
		addError("ev", "This EV results in poor CPI : " + cpi);
		addError("ac", "This AC results in poor CPI : " + cpi);
	}

	// Check MIN_INDEX <= SPI <= MAX_INDEX 
	if ((calculated.spi < MIN_INDEX) || (calculated.spi > MAX_INDEX)) {
		hasError = true;
		var spi = calculated.spi;
		addError("ev", "This EV results in poor SPI : " + spi);
		addError("pv", "This PV results in poor SPI : " + spi);
	}

	// Check ratio >= MIN_DEFECT_RATIO
	if (calculated.ratio < MIN_DEFECT_RATIO) {
		hasError = true;
		var ratio = calculated.ratio;
		addError("spe", "This SPE results in poor defect ratio : " + ratio);
		addError("defects", "This defect count results in poor defect ratio : " + ratio);
	}

	return hasError;
}

/**
 * Validates user entered metrics.
 * 
 * @param estimate
 * @param entered
 * @returns calculatedMetrics
 */
function validateEnteredMetrics(estimate, entered) {

	var calculatedMetrics = null;

	//bitwise OR used to force both validations
	var hasError = validateNumbers(entered) | validateDates(estimate, entered);

	if (!hasError) {

		calculatedMetrics = new CalculatedMetrics();

		calculatedMetrics.etc = estimate.bac - entered.ev;
		calculatedMetrics.cpi = entered.ev / entered.ac;
		calculatedMetrics.spi = entered.ev / entered.pv;
		calculatedMetrics.ratio = entered.defects / entered.spe;

		//logical OR used to force conditional validation
		hasError = applyDataSanityRules(entered) || applyQualityRules(calculatedMetrics);

	}

	if (hasError) {
		calculatedMetrics = null;
		displayAlert("Save Failed : Entered Metrics contain errors.");
	}

	return calculatedMetrics;
}

/**
 * Creates EstimateData object from JSON received.
 * 
 * @param jsonData
 * @returns estimateData
 */
function createEstimateFromJson(jsonData) {

	var budget = getValidFloat(jsonData.bac);
	var projStartDate = getValidDate(jsonData.startDate);
	var projEndDate = getValidDate(jsonData.endDate);

	var estimateData = new EstimateData();

	estimateData.bac = budget;
	estimateData.startDate = projStartDate;
	estimateData.endDate = projEndDate;

	return estimateData;
}

/**
 * Creates MetricData object from used entered data.
 * 
 * @param metricEntryDate
 * @returns enteredMetrics
 */
function createMetricsFromPage(metricEntryDate) {

	var enteredMetrics = new MetricData();

	enteredMetrics.pv = getValidFloat($("#pv").val());
	enteredMetrics.ev = getValidFloat($("#ev").val());
	enteredMetrics.ac = getValidFloat($("#ac").val());
	enteredMetrics.atd = getValidFloat($("#atd").val());
	enteredMetrics.spe = getValidFloat($("#spe").val());
	enteredMetrics.defects = getValidInt($("#defects").val());
	enteredMetrics.metricDate = getValidDate(metricEntryDate);

	return enteredMetrics;
}

/**
 * Saves Metric Details.
 * 
 * @param metricDbId
 * @param metricEntryRowId
 */
function saveMetricDetails(metricDbId, metricEntryRowId) {

	var projDbId = $("#metricEntryProjId :selected").val();

	if ((projDbId > 0) && (metricEntryDate != null)) {

		//ajax call for fetching Project Estimates
		$.post(JSON_URL, {
			operation : "SEARCH_ESTIMATES",
			projectId : projDbId
		}, function(data) {

			var jsonData = data;

			if (jsonData.error) {
				displayAlert(jsonData.error);
				resetMetricEntryArea();
			} else {

				var errMsg = validateEstimatePresent(jsonData);

				if (errMsg == null) {

					var estimateData = createEstimateFromJson(jsonData);
					var enteredMetrics = createMetricsFromPage(metricEntryDate);
					var calculatedMetrics = validateEnteredMetrics(estimateData, enteredMetrics);

					if (calculatedMetrics != null) {

						//save via ajax
						$.post(JSON_URL, {
							operation : "SAVE_METRIC_DETAILS",
							projectId : projDbId,
							metricId : metricDbId,
							weekStartDate : metricEntryDate + "",
							pv : enteredMetrics.pv,
							ev : enteredMetrics.ev,
							ac : enteredMetrics.ac,
							atd : enteredMetrics.atd,
							spe : enteredMetrics.spe,
							bug : enteredMetrics.defects
						}, function(data) {

							var jsonData = data;

							if (jsonData.error) {
								displayAlert(jsonData.error);
								resetMetricEntryArea();
							} else {

								displayAlert("Metrics Saved");
								resetMetricEntryArea();
							}
						}, JSON_RESULT_TYPE);
					}
				} else {
					//some error is present
					errMsg = "Cannot Save. Missing Project Level Data : " + errMsg;
					displayAlert(errMsg);
				}
			}
		}, JSON_RESULT_TYPE);
	} else if (projDbId <= 0) {
		displayAlert("Select A Project");
	} else if (metricEntryDate == null) {
		displayAlert("Select A Week");
	}
};

$(function() {
	
	$("#metrics_accordion").accordion({
	    autoHeight : false,
	    navigation : true,
	    collapsible : true,
	    active : 0
	});
	
});