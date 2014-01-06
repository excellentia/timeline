<%-- TLD inclusion --%>
<%@ include file="inc/includes.inc.jsp"%>

<%-- Header Setup --%>
<c:set var="pageTitle" value="Time" />
<c:set var="pageScriptName" value="mytime" />
<%@ include file="inc/header.inc.jsp"%>

<%-- Menu Setup --%>
<c:set var="activeMenu" value="time" />
<%@ include file="inc/menu.inc.jsp"%>

<%-- Main content --%>
<div id="content">
	<%-- Time Entry Area --%>
	<div id="newEntryAccordion">
		<%-- New Entry Section--%>
		<h3>
			<a href="#section1">Create Time Entry</a>
		</h3>
		<div>
			<%-- Week Select Area --%>
			<p class="message">Select Week</p>
			<div id="searchBar">
				<div class="weekChoice">
					<input id="weekArea" type="text" class="dateSearch" readonly="readonly" title="Selected Week" disabled="disabled" /> <input id="weekpicker" type="hidden" /> <img id="weekImg"
						title="Click To Select Week" alt="Click To Select Week" src="./pages/inc/images/calendar.gif"
					/>
				</div>
				<div class="proxyChoice">
					<%-- Proxy Users --%>
					<c:if test="${SESSION_USER.admin}">
						<div style="display: inline; padding: 0; margin: 0; padding-left: 2em;">
							<select id="proxyUserArea" size="1" name="proxiedUserDbId" class="dateSearch" title="Select User to Proxy">
								<option value="0" selected="selected">Select Proxy User...</option>
								<c:forEach var="user" items="${USER_LIST.users}">
									<c:if test="${SESSION_USER.id != user.id}">
										<option value="${user.id}">${user.userName}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</c:if>
				</div>
			</div>
			<div>
				<table style="width: 100%;" id="timeTable">
					<colgroup>
						<col style="width: 10%" />
						<col style="width: 10%" />
						<col style="width: 10%" />
						<col style="width: 10%" />
						<col style="width: 7%" />
						<col style="width: 7%" />
						<col style="width: 7%" />
						<col style="width: 7%" />
						<col style="width: 7%" />
						<col style="width: 7%" />
						<col style="width: 7%" />
						<col style="width: 5%" />
						<col style="width: 3%" />
						<col style="width: 3%" />
					</colgroup>
					<thead>
						<tr>
							<th>User</th>
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
				<input type="button" value="Add Entry" class="button" onclick="addEntryRow('timeTable')" />
			</div>
		</div>
		<%-- Existing Entry Search Section--%>
		<h3>
			<a href="#section2">Search Time Entries</a>
		</h3>
		<div>
			<%-- Search Area --%>
			<div id="searchBar">
				<%-- Start Week --%>
				<div style="display: inline; padding: 0; margin: 0">
					<input id="startWeekSearch" type="text" class="dateSearch" readonly="readonly" title="Start Week" disabled="disabled" value="${startWeekLabel}" /> <input id="startWeekPicker" type="hidden" /> <img
						id="startWeekImg" title="Click To Select Start Week" alt="Click To Select Start Week" src="./pages/inc/images/calendar.gif"
					/> &nbsp;to&nbsp;
				</div>
				<%-- End Week --%>
				<div style="display: inline; padding: 0; margin: 0">
					<input id="endWeekSearch" type="text" class="dateSearch" readonly="readonly" title="End Week" disabled="disabled" value="${endWeekLabel}" /> <input id="endWeekPicker" type="hidden" /> <img
						id="endWeekImg" title="Click To Select End Week" alt="Click To Select End Week" src="./pages/inc/images/calendar.gif"
					/>
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
					<p style="margin: 1em 0 1em 0;">Search Results</p>
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
											<col style="width: 10%" />
											<col style="width: 10%" />
											<col style="width: 10%" />
											<col style="width: 10%" />
											<col style="width: 7%" />
											<col style="width: 7%" />
											<col style="width: 7%" />
											<col style="width: 7%" />
											<col style="width: 7%" />
											<col style="width: 7%" />
											<col style="width: 7%" />
											<col style="width: 5%" />
											<col style="width: 3%" />
											<col style="width: 3%" />
										</colgroup>
										<thead>
											<tr>
												<th>User</th>
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
														<td>${weeklyEntry.userFullName}</td>
														<td>${weeklyEntry.projectName} <input type="hidden" id="startDate_${weeklyEntry.id}" value="${weeklyEntry.formattedStartDate}" />
														</td>
														<td>${weeklyEntry.activityName}</td>
														<td><c:if test="${weeklyEntry.leadName != null}">${weeklyEntry.leadName}</c:if></td>
														<td>${weeklyEntry.day_1_time}</td>
														<td>${weeklyEntry.day_2_time}</td>
														<td>${weeklyEntry.day_3_time}</td>
														<td>${weeklyEntry.day_4_time}</td>
														<td>${weeklyEntry.day_5_time}</td>
														<td class="weekEnd">${weeklyEntry.day_6_time}</td>
														<td class="weekEnd">${weeklyEntry.day_7_time}</td>
														<td class="rowTotal">${weeklyEntry.weeklySum}</td>
														<td align="center"><img alt="Edit" align="middle" class="icon" title="${editTitle}" src="${editIconPath}"
															onclick="editTimeEntry('${entryId}',${weeklyEntry.projectId},${weeklyEntry.activityId}, ${weeklyEntry.id})"
														/></td>
														<td align="center"><img alt="Delete" align="middle" class="icon" title="${activityDeleteTitle}" src="${deleteIconPath}" onclick="deleteTimeEntry('${entryId}',${weeklyEntry.id})" /></td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</c:forEach>
						</div>
					</div>
					<div style="display: inline; padding: 0; margin: 0; margin-left: 1em;">
						<form name="exportForm" id="exportForm" action="Export" method="POST">
							<input type="hidden" id="exportStartDate" name="exportStartDate" value="" />
							<input type="hidden" id="exportEndDate" name="exportEndDate" value="" />
							<input type="hidden" id="exportProjectId" name="exportProjectId" value="" />
							<input type="hidden" id="exportActivityId" name="exportActivityId" value="" />
							<input type="hidden" id="exportUserDbId" name="exportUserDbId" value="" />
							<input type="hidden" name="command" value="TIME_ENTRIES" />
							<input type="button" value="Export" class="button" style="height: 1.5em;" onclick="exportEntries('exportForm')" />
						</form>
					</div>
				</c:if>
			</div>
		</div>
		<%-- Pending Entry User Search Section--%>
		<h3>
			<a href="#section3">Pending Entries</a>
		</h3>
		<div>
			<%-- Search Area --%>
			<div id="searchBar">
				<%-- Start Week --%>
				<div style="display: inline; padding: 0; margin: 0">
					<input id="missingStartWeekSearch" type="text" class="dateSearch" readonly="readonly" title="Start Week" disabled="disabled" value="${startWeekLabel}" /> 
					<input id="missingStartWeekPicker" type="hidden" /> 
					<img id="missingStartWeekImg" title="Click To Select Start Week" alt="Click To Select Start Week" src="./pages/inc/images/calendar.gif"/> &nbsp;to&nbsp;
				</div>
				<%-- End Week --%>
				<div style="display: inline; padding: 0; margin: 0">
					<input id="missingEndWeekSearch" type="text" class="dateSearch" readonly="readonly" title="End Week" disabled="disabled" value="${endWeekLabel}" /> 
					<input id="missingEndWeekPicker" type="hidden" /> 
					<img id="missingEndWeekImg" title="Click To Select End Week" alt="Click To Select End Week" src="./pages/inc/images/calendar.gif"/>
				</div>
				<div style="display: inline; padding: 0; margin: 0; margin-left: 1em;">
					<input type="button" value="Search" class="button" style="height: 1.5em;" onclick="searchMissingEntries('my_missing_entry_accordion','missingUserResults')" />
				</div>
			</div>
			<%-- Results Area --%>
			<div id="missingUserResults">
				<c:set var="userCount" value="${pendingUsers.userCount}" />
				<c:set var="weekIdList" value="${pendingUsers.weekIdList}" />
				<c:if test="${(weekIdList != null) && (weekIdList.size() > 0)}">
					<p style="margin: 1em 0 1em 0;">Users With Missing Entries</p>
					<div style="margin-bottom: 2em;">
						<div id="my_missing_entry_accordion">
							<c:forEach var="weekId" items="${weekIdList}">
								<c:set var="tableId">missing_timeTable_${weekId}</c:set>
								<h3>
									<a href="#missing_week_${weekId}">${pendingUsers.getWeekLabel(weekId)}</a>
								</h3>
								<div>
									<table style="width: 15%;" id="${tableId}">
										<colgroup>
											<col style="width: 15%" />
											<col style="width: 70%" />
											<col style="width: 15%" />
										</colgroup>
										<thead>
											<tr>
												<th colspan="3">User Name</th>
											</tr>
										</thead>
										<c:set var="users" value="${pendingUsers.getWeeklyUsers(weekId)}" />
										<c:set var="userCount" value="${users.size()}" />
										<c:set var="allUserEmail" value="" />
										<tbody class="reportBody">
											<c:if test="${users != null}">
												<c:forEach var="user" items="${users}" varStatus="count">
													<tr>
														<td>${count.index+1}</td>
														<td style="text-align: left; padding-left : 0.5em;">${user.userName}</td>
														<td>
															<c:set var="email" value="${pendingUsers.getUserEmail(user.id)}" />
															<c:if test="${email != null}">
																<a href="mailto:${email}"><img class="icon" id="sendEmail" title="${emailTitle}" alt="${emailTitle}" src="${emailIconPath}"/></a>
																<c:set var="allUserEmail" value="${email},${allUserEmail}" />
															</c:if>
															<c:remove var="email"/>
														</td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
										<tfoot>
											<tr>
												<td colspan="2" style="text-align: center;">Total Users : ${userCount}</td>
												<td>
													<a href="mailto:${allUserEmail}">
														<img class="icon" style="margin: 0; padding: 0; border: 2px solid #609AFA" id="sendEmail" title="Send Email To Users (Ones with Email Icon)" alt="Send Email" src="${emailIconPath}"/>
													</a>
												</td>
											</tr>
										</tfoot>
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

