<%-- TLD inclusion --%>
<%@ include file="inc/includes.inc.jsp"%>

<%-- Header Setup --%>
<c:set var="pageTitle" value="Time" />
<%@ include file="inc/header.inc.jsp"%>

<%-- Menu Setup --%>
<c:set var="activeMenu" value="time" />
<%@ include file="inc/menu.inc.jsp"%>

<%-- Main content --%>
<div id="content">
	<%-- Time Entry Area --%>
	<div id="accordion">
		<%-- New Entry Section--%>
		<h3>
			<a href="#section1">Create Time Entry</a>
		</h3>
		<div>
			<%-- Week Select Area --%>
			<p class="message">Select Week</p>
			<div id="searchBar">
				<input id="weekArea" type="text" class="dateSearch" readonly="readonly" title="Selected Week" disabled="disabled" /> 
				<input id="weekpicker" type="hidden" /> 
				<img id="weekImg" title="Click To Select Week" alt="Click To Select Week" src="./pages/inc/images/calendar.gif" />
			</div>
			<div>
				<table style="width: 100%;" id="timeTable">
					<colgroup>
						<col style="width: 11%" />
						<col style="width: 11%" />
						<col style="width: 11%" />
						<col style="width: 8%" />
						<col style="width: 8%" />
						<col style="width: 8%" />
						<col style="width: 8%" />
						<col style="width: 8%" />
						<col style="width: 8%" />
						<col style="width: 8%" />
						<col style="width: 5%" />
						<col style="width: 3%" />
						<col style="width: 3%" />
					</colgroup>
					<thead>
						<tr>
							<th>Project</th>
							<th>Activity</th>
							<th>Lead</th>
							<th id="day1Text">Mon</th>
							<th id="day2Text">Tue</th>
							<th id="day3Text">Wed</th>
							<th id="day4Text">Thu</th>
							<th id="day5Text">Fri</th>
							<th id="day6Text">Sat</th>
							<th id="day7Text">Sun</th>
							<th>Total</th>
							<th colspan="2">&nbsp;</th>
						</tr>
					</thead>
					<tbody id="timeTable_body" class="reportBody">
					</tbody>
				</table>
			</div>
			<div id="toolbar">
				<input type="button" value="Add Activity" class="button" onclick="addActivityRow('timeTable')" />
			</div>
		</div>
		<%-- Existing Entry Search Section--%>
		<h3>
			<a href="#section2">Search My Time Entries</a>
		</h3>
		<div>
			<%-- Search Area --%>
			<div id="searchBar">
				<%-- Start Week --%>
				<div style="display: inline; padding: 0; margin: 0">
					<input id="startWeekSearch" type="text" class="dateSearch" readonly="readonly" title="Start Week" disabled="disabled" value="${startWeekLabel}" /> 
					<input id="startWeekPicker" type="hidden" /> 
					<img id="startWeekImg" title="Click To Select Start Week" alt="Click To Select Start Week" src="./pages/inc/images/calendar.gif" /> &nbsp;to&nbsp;
				</div>
				<%-- End Week --%>
				<div style="display: inline; padding: 0; margin: 0">
					<input id="endWeekSearch" type="text" class="dateSearch" readonly="readonly" title="End Week" disabled="disabled" value="${endWeekLabel}" /> 
					<input id="endWeekPicker" type="hidden" /> 
					<img id="endWeekImg" title="Click To Select End Week" alt="Click To Select End Week" src="./pages/inc/images/calendar.gif" />
				</div>
				<%-- Project --%>
				<div style="display: inline; padding: 0; margin: 0; margin-left: 2em;">
					<select id="searchProjectId" name="" searchProjectId"" size="1" class="dateSearch" title="Select Project" onchange="populateActivities('searchProjectId','searchActivityId')">
						<option value="0" <c:if test="${selectedProjId == 0}">selected="selected"</c:if>>All Projects</option>
						<c:forEach var="project" items="${PROJECT_LIST.projects}">
							<option value="${project.code}" <c:if test="${selectedProjId == project.code}">selected="selected"</c:if>>${project.value}</option>
						</c:forEach>
					</select>
				</div>
				<%-- Activity --%>
				<div style="display: inline; padding: 0; margin: 0">
					<select id="searchActivityId" name="searchActivityId" size="1" class="dateSearch" title="Select Activity">
						<option value="0" <c:if test="${selectedActId == 0}">selected="selected"</c:if>>All Activities</option>
						<c:if test="${selectedProjId > 0}">
							<c:forEach var="activity" items="${ACTIVITY_LIST.getProjectActivities(selectedProjId)}">
								<option value="${activity.code}" <c:if test="${selectedActId == activity.code}">selected="selected"</c:if>>${activity.value}</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
				<%-- Users --%>
				<c:if test="${SESSION_USER.admin}">
					<div style="display: inline; padding: 0; margin: 0">
						<select id="searchUserId" size="1" name="searchUserId" class="dateSearch" title="Select User">
							<option value="0" <c:if test="${selectedUserId == 0}">selected="selected"</c:if>>All Users</option>
							<c:forEach var="user" items="${USER_LIST.users}">
								<option value="${user.id}" <c:if test="${selectedUserId == user.id}">selected="selected"</c:if>>${user.userName}</option>
							</c:forEach>
						</select>
					</div>
				</c:if>
				<div style="display: inline; padding: 0; margin: 0; margin-left: 1em;">
					<input type="button" value="Search" class="button" style="height: 1.5em;" onclick="searchEntries('mySearchResults','myEntrySearchResults_body','my_entry_accordion',${SESSION_USER.admin})" />
				</div>
			</div>
			<%-- Results Area --%>
			<div id="mySearchResults">
				<c:set var="weekList" value="${timeData.weekIds}" />
				<c:if test="${(weekList != null) && (weekList.size() > 0)}">
					<p>Search Results</p>
					<div style="margin-bottom: 2em;">
						<div id="my_entry_accordion">
							<c:forEach var="weekId" items="${weekList}">
								<c:set var="tableId">timeTable_${weekId}</c:set>
								<h3>
									<a href="#week_${weekId}">${timeData.getWeekLabel(weekId)}</a>
								</h3>
								<div>
									<table style="width: 100%;" id="${tableId}">
										<colgroup>
											<col style="width: 11%" />
											<col style="width: 11%" />
											<col style="width: 11%" />
											<col style="width: 8%" />
											<col style="width: 8%" />
											<col style="width: 8%" />
											<col style="width: 8%" />
											<col style="width: 8%" />
											<col style="width: 8%" />
											<col style="width: 8%" />
											<col style="width: 5%" />
											<col style="width: 3%" />
											<col style="width: 3%" />
										</colgroup>
										<thead>
											<tr>
												<th>Project</th>
												<th>Activity</th>
												<th>Lead</th>
												<c:forEach var="label" items="${timeData.getDayLabels(weekId)}" varStatus="cnt">
													<th id="day_${cnt.count}_Text">${label}</th>
												</c:forEach>
												<th>Total</th>
												<th colspan="2">&nbsp;</th>
											</tr>
										</thead>
										<tbody id="myEntrySearchResults_body" class="reportBody">
											<c:set var="entries" value="${timeData.getEntriesForWeek(weekId)}" />
											<c:if test="${entries != null}">
												<c:forEach var="weeklyEntry" items="${entries}">
													<c:set var="entryId">entry_${weeklyEntry.id}</c:set>
													<tr id='${entryId}' class="leadColumn">
														<td>${weeklyEntry.projectName} <input type="hidden" id="startDate_${weeklyEntry.id}" value="${weeklyEntry.formattedStartDate}" />
														</td>
														<td>${weeklyEntry.activityName}</td>
														<td>${weeklyEntry.leadName}</td>
														<td>${weeklyEntry.day_1_time}</td>
														<td>${weeklyEntry.day_2_time}</td>
														<td>${weeklyEntry.day_3_time}</td>
														<td>${weeklyEntry.day_4_time}</td>
														<td>${weeklyEntry.day_5_time}</td>
														<td class="weekEnd">${weeklyEntry.day_6_time}</td>
														<td class="weekEnd">${weeklyEntry.day_7_time}</td>
														<td class="rowTotal">${weeklyEntry.weeklySum}</td>
														<td align="center">
															<img alt="Edit" align="middle" class="icon" title="${editTitle}" src="${editIconPath}" 
																onclick="editTimeEntry('${entryId}',${weeklyEntry.projectId},${weeklyEntry.activityId}, ${weeklyEntry.id})" />
														</td>
														<td align="center">
															<img alt="Delete" align="middle" class="icon" title="${activityDeleteTitle}" src="${deleteIconPath}" 
																onclick="deleteTimeEntry('${entryId}',${weeklyEntry.id})" />
														</td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>

<%-- Footer Setup --%>
<%@ include file="inc/footer.inc.jsp"%>
<%-- Focus  --%>
<script type="text/javascript">focus('weekImg');</script>
