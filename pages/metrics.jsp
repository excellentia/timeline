<%-- TLD inclusion --%>
<%@ include file="inc/includes.inc.jsp"%>

<%-- Header Setup --%>
<c:set var="pageTitle" value="Project Metrics" />
<c:set var="pageScriptName" value="metrics" />
<%@ include file="inc/header.inc.jsp"%>

<%-- Menu Setup --%>
<c:set var="activeMenu" value="metrics" />
<%@ include file="inc/menu.inc.jsp"%>

<%-- Main content --%>
<div id="content">
	<div id="messageBar"></div>
	<div id="metrics_accordion">
		<!-- Project Level Metrics-->
		<h3>
			<a href="#projectMetrics">Project Level Metrics</a>
		</h3>
		<div>
			<div id="searchBar">
				<c:set var="selectedProjId" value="${projectId}" />
				<form action="./Metrics" id="searchForm" name="searchForm" method="POST">
					<!-- Project -->
					<div class="thin">
						<select id="projectId" size="1" name="projectId" class="dateSearch" title="Select Project">
							<option value="0" <c:if test="${selectedProjId == 0}">selected="selected"</c:if>>All Projects</option>
							<c:forEach var="project" items="${PROJECT_LIST.projects}">
								<option value="${project.code}" <c:if test="${selectedProjId == project.code}">selected="selected"</c:if>>${project.value}</option>
							</c:forEach>
						</select>
					</div>
					<div class="thin">
						<input type="submit" value="Show Metrics" class="button" />
					</div>
				</form>
			</div>
			<div class="tableSpacing">
				<c:choose>
					<c:when test="${((projectLevelMetricReply == null) || (projectLevelMetricReply.projectLevelMetrics == null) || (projectLevelMetricReply.projectLevelMetrics.size() == 0))}">
						<p class="error">No Data Found</p>
					</c:when>
					<c:otherwise>
						<table style="width: 100%;" id="projectLevelTable">
							<colgroup>
								<col style="width: 8%" />
								<col style="width: 8%" />
								<col style="width: 6%" />
								<col style="width: 8%" />
								<col style="width: 8%" />
								<col style="width: 5%" />
								<col style="width: 5%" />
								<col style="width: 5%" />
								<col style="width: 5%" />
								<col style="width: 5%" />
								<col style="width: 5%" />
								<col style="width: 5%" />
								<col style="width: 5%" />
								<col style="width: 5%" />
								<col style="width: 5%" />
								<col style="width: 8%" />
								<col style="width: 2%" />
								<col style="width: 2%" />
							</colgroup>
							<thead>
								<tr align="center">
									<th class="tabArea" title="Metrics Till Date" colspan="2">Project Level Metrics</th>
									<th class="tabArea" title="Basic Data for Project" colspan="3" style="border-left: 1px solid white">Basic Data</th>
									<th class="tabArea" title="Cumulative Metrics" colspan="6" style="border-left: 1px solid white">Cumulative Metrics</th>
									<th class="tabArea" title="Calculated Metrics" colspan="4" style="border-left: 1px solid white">Calculated Metrics</th>
									<th class="whiteBorderHeader" colspan="3"></th>
								</tr>
								<tr>
									<th title="Project Name">Project</th>
									<th title="Project Lead">Lead</th>
									<th title="Budget At Completion">BAC</th>
									<th title="Project Start Date">Start Date</th>
									<th title="Project End Date">End Date</th>
									<th title="Cumulative Planned Value" class="reportTotal">&Sigma; PV</th>
									<th title="Cumulative Earned Value" class="reportTotal">&Sigma; EV</th>
									<th title="Cumulative Actual Cost" class="reportTotal">&Sigma; AC</th>
									<th title="Cumulative Actuals To Date" class="reportTotal">&Sigma; ATD</th>
									<th title="Cumulative Software Project Effort" class="reportTotal">&Sigma; SPE</th>
									<th title="Cumulative Defects" class="reportTotal">&Sigma; Bugs</th>
									<th title="Cumulative Expected To Complete" class="calculatedMetrics">&Sigma; ETC</th>
									<th title="Cumulative Schedule Project Efficiency" class="calculatedMetrics">&Sigma; SPI</th>
									<th title="Cumulative Cost Project Efficiency" class="calculatedMetrics">&Sigma; CPI</th>
									<th title="Cumulative Ratio : Defect / SPE" class="calculatedMetrics">&Sigma; Ratio</th>
									<th title="Last Period For Which Metrics Were Entered">Last Updated</th>
									<th colspan="2">&nbsp;</th>
								</tr>
							</thead>
							<tbody class="reportBody">
								<c:forEach var="projectLevelMetric" items="${projectLevelMetricReply.projectLevelMetrics}">
									
									<c:set var="projectEstimate" value="${projectLevelMetric.basicData}"/>
									<c:set var="basicMetrics" value="${projectLevelMetric.cumulativeMetrics}"/>
									<c:set var="calculatedMetrics" value="${projectLevelMetric.calculatedMetrics}"/>
									<c:set var="projectData" value="${projectEstimate.projectData}"/>
									<c:set var="projId" value="${projectData.code}" />
									<c:set var="rowProjId" value="projectLevel_${projId}" />
									
									<tr id="${rowProjId}">
										<td>${projectData.value}</td>
										<td>${projectData.leadName}</td>
										<td><fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${projectEstimate.budgetAtCompletion}"/></td>
										<td><fmt:formatDate value="${projectEstimate.startDate}" pattern="dd MMM yyyy"/></td>
										<td><fmt:formatDate value="${projectEstimate.endDate}" pattern="dd MMM yyyy"/></td>
										<td class="reportTotal"><fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${basicMetrics.plannedValue}"/></td>
										<td class="reportTotal"><fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${basicMetrics.earnedValue}"/></td>
										<td class="reportTotal"><fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${basicMetrics.actualCost}"/></td>
										<td class="reportTotal"><fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${basicMetrics.actualsToDate}"/></td>
										<td class="reportTotal"><fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${basicMetrics.softwareProgrammingEffort}"/></td>
										<td class="reportTotal"><fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${basicMetrics.defects}"/></td>
										<td class="calculatedMetrics"><fmt:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${calculatedMetrics.expectedToComplete}"/></td>
										<td class="calculatedMetrics"><fmt:formatNumber minFractionDigits="3" maxFractionDigits="3" value="${calculatedMetrics.schedulePerformanceIndex}"/></td>
										<td class="calculatedMetrics"><fmt:formatNumber minFractionDigits="3" maxFractionDigits="3" value="${calculatedMetrics.costPerformanceIndex}"/></td>
										<td class="calculatedMetrics"><fmt:formatNumber minFractionDigits="3" maxFractionDigits="3" value="${calculatedMetrics.defectRatio}"/></td>
										<td class="smallFont" title="${projectEstimate.lastSubmittedPeriod}">${projectEstimate.lastSubmittedPeriod}</td>
										<td align="center">
											<img alt="Edit" align="middle" class="icon" title="Edit BAC and Project Dates" src="${editIconPath}" onclick="editProjectBasicData('${rowProjId}', ${projId})"/>
										</td>
										<td align="center">
											<img alt="Delete" align="middle" class="icon" title="Delete BAC, Project Dates And All Metrics" src="${deleteIconPath}" onclick="deleteProjectMetrics('${rowProjId}', ${projId})"/>
										</td>
									</tr>
									<c:remove var="projectEstimate"/>
									<c:remove var="basicMetrics"/>
									<c:remove var="calculatedMetrics"/>
									<c:remove var="projectData"/>
									<c:remove var="projId"/>
									<c:remove var="rowProjId"/>
								</c:forEach>
							</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<!-- Detail Project Metrics-->
		<h3>
			<a href="#projectMetrics">Detailed Metrics</a>
		</h3>
		<div>
			<div id="searchBar">
				<!-- Project -->
				<div class="thin">
					<select id="detailedProjId" size="1" class="dateSearch" title="Select A Project">
						<option value="0" selected="selected">Select A Project</option>
						<c:forEach var="project" items="${PROJECT_LIST.projects}">
							<option value="${project.code}">${project.value}</option>
						</c:forEach>
					</select>
				</div>
				<div class="thin">
					<input id="weekArea" type="text" class="dateSearch" readonly="readonly" title="Select A Week" disabled="disabled" value="All Weeks" />
					<input id="weekpicker" type="hidden" /> 
					<img id="weekImg" title="Click To Select a Week" alt="Click To Select A Week" src="${calendarIconPath}" />
				</div>
				<div class="thin">
					<input type="button" value="Show Metrics" class="button" onclick="getDetailedProjectMetrics('detailedProjId','detailContent')"/>
				</div>
				<div id="detailContent"></div>
			</div>
		</div>
		<!-- Enter Project Metrics-->
		<h3>
			<a href="#projectMetrics">Enter Project Metrics</a>
		</h3>
		<div>
			<div id="searchBar">
				<!-- Project -->
				<div class="thin">
					<select id="metricEntryProjId" size="1" class="dateSearch" title="Select A Project" onchange="hideMetricEntry('newMetricDiv')">
						<option value="0" selected="selected">Select A Project</option>
						<c:forEach var="project" items="${PROJECT_LIST.projects}">
							<option value="${project.code}">${project.value}</option>
						</c:forEach>
					</select>
				</div>
				<div class="thin">
					<input type="button" value="Go" class="button" onclick="showMetricEntryArea('metricEntryTable')"/>
				</div>
				<div class="thin" id="newMetricDiv"></div>
			</div>
		</div>
	</div>
</div>
<%-- Footer Setup --%>
<%@ include file="inc/footer.inc.jsp"%>
