<%-- TLD inclusion --%>
<%@ include file="inc/includes.inc.jsp"%>

<%-- Header Setup --%>
<c:set var="pageTitle" value="Tasks" />
<c:set var="pageScriptName" value="tasks" />
<%@ include file="inc/header.inc.jsp"%>

<%-- Menu Setup --%>
<c:set var="activeMenu" value="tasks" />
<%@ include file="inc/menu.inc.jsp"%>

<%-- Main content --%>
<div id="content">
	<div id="messageBar"></div>
	<div id="taskAccordion">
		<%-- New Task Related Section--%>
		<h3>
			<a href="#taskSection1">Create New Task</a>
		</h3>
		<div>
			<div id="searchBar">
				<%-- Project --%>
				<div style="display: inline; padding: 0; margin: 0;">
					<select id="newTaskProjectId" name="newTaskProjectId" size="1" class="dateSearch" title="Select Project" onchange="populateActivities('newTaskProjectId','newTaskActivityId')">
						<option value="0" <c:if test="${newTaskProjectId == 0}">selected="selected"</c:if>>All Projects</option>
						<c:forEach var="project" items="${PROJECT_LIST.projects}">
							<option value="${project.code}" <c:if test="${newTaskProjectId == project.code}">selected="selected"</c:if>>${project.value}</option>
						</c:forEach>
					</select>
				</div>
				<%-- Activity --%>
				<div style="display: inline; padding: 0; margin: 0">
					<select id="newTaskActivityId" name="newTaskActivityId" size="1" class="dateSearch" title="Select Activity">
						<option value="0" <c:if test="${newTaskProjectId == 0}">selected="selected"</c:if>>All Activities</option>
						<c:if test="${newTaskProjectId > 0}">
							<c:forEach var="activity" items="${ACTIVITY_LIST.getProjectActivities(newTaskProjectId)}">
								<option value="${activity.code}" <c:if test="${selectedActId == activity.code}">selected="selected"</c:if>>${activity.value}</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
				<div style="display: inline; padding: 0; margin: 0; margin-left: 1em;">
					<%--<input type="button" value="Create" class="button" style="height: 1.5em;" onclick="createTask(${SESSION_USER.admin})" /> --%>
					<input type="button" value="Create" class="button" style="height: 1.5em;" onclick="createTask()" />
				</div>
			</div>
			<div id="newTaskSection" style="margin-top : 1.5em;"></div>
		</div>
		<%-- Search Tasks Section--%>
		<h3>
			<a href="#taskSection2">Search Existing Tasks</a>
		</h3>
		<div>
			<div id="searchBar">
				<%-- Project --%>
				<div style="display: inline; padding: 0; margin: 0;">
					<select id="searchProjectId" name="searchProjectId" size="1" class="dateSearch" title="Select Project" onchange="populateActivities('searchProjectId','searchActivityId')">
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
				<%-- Status --%>
				<div style="display: inline; padding: 0; margin: 0">
					<select id="taskStatusId" size="1" name="taskStatusId" class="dateSearch" title="Select Task Status">
						<option value="0" <c:if test="${taskStatusId == 0}">selected="selected"</c:if>>All Tasks</option>
						<option value="1" <c:if test="${taskStatusId == 1}">selected="selected"</c:if>>Active</option>
						<option value="2" <c:if test="${taskStatusId == 2}">selected="selected"</c:if>>Inactive</option>
					</select>
				</div>
				<div style="display: inline; padding: 0; margin: 0; margin-left: 1em;">
					<input type="button" value="Search" class="button" style="height: 1.5em;" onclick="searchTasks()" />
				</div>
			</div>
			<div id="myTaskSearchResults" style="margin-bottom: 2em;">
				<c:if test="${((taskReply != null) && (taskReply.hasTasks()))}">
					<c:set var="taskCount" value="0"/>
					<div id="taskSearchResultsAccordion" style="margin-top : 1.5em;">
						<c:forEach var="projectId" items="${taskReply.getProjectIds()}" varStatus="idx">
							<h3>
								<a href="#taskSection${idx.count}">${taskReply.getProjectName(projectId)}</a>
							</h3>
							<div>
								<table style="width: 80%;">
									<colgroup>
										<col style="width: 16%" />
										<col style="width: 26%" />
										<col style="width: 44%" />
										<col style="width: 4%" />
										<col style="width: 4%" />
										<col style="width: 4%" />
										<col style="width: 4%" />
									</colgroup>
									<thead>
										<tr>
											<th>Activity</th>
											<th>Task</th>
											<th>Description</th>
											<th>Story Points</th>
											<th>Active ?</th>
											<th colspan="2">&nbsp;</th>
										</tr>
									</thead>
									<tbody id="timeTable_body" class="reportBody">
										<c:set var="rowId" value=""/>
										<c:forEach var="activityId" items="${taskReply.getProjectActivityIds(projectId)}">
											<c:forEach var="task" items="${taskReply.getActivityTasks(activityId)}">
												<c:set var="rowId" value="task_${task.code}"/>
												<c:set var="taskCount" value="${taskCount+1}"/>
												<tr id="${rowId}">
													<td>${taskReply.getActivityName(activityId)}</td>
													<td>${task.value}</td>
													<td>${taskReply.getTaskDetail(task.code)}</td>
													<td>${taskReply.getTaskStoryPoints(task.code)}</td>
													<td align="center" title="Toggle Task Status">
														<c:set var="taskCheckId" value="task_check_${task.code}"/>
														<input id="${taskCheckId}" type='checkbox' value='${task.status}' <c:if test="${task.status}">checked="checked"</c:if> onchange="toggleTaskStatus('${rowId}','${taskCheckId}',${task.code})" />
													</td>
													<td align="center">
														<img alt="Edit" align="middle" class="icon" title="${taskNameEditTitle}" src="${editIconPath}" onclick="editTask(false,'${rowId}',${task.code})"/>
													</td>
													<td align="center">
														<img alt="Delete" align="middle" class="icon" title="${taskDeleteTitle}" src="${deleteIconPath}" onclick="deleteTask('${rowId}',${task.code})" />
													</td>
												</tr>
											</c:forEach>
										</c:forEach>
									</tbody>
								</table>
							</div>	
						</c:forEach>
					</div>
					<div style="margin-top: 1em;"><p class="thin" style="font-size: 1em;font-weight: normal;">Total Tasks Found : </p><p class="thin" id="count" style="font-size: 1em;font-weight: normal;">${taskCount}</p></div>
					<c:remove var="taskCount"/>	
				</c:if>
			</div>
		</div>
	</div>
</div>
<%-- Footer Setup --%>
<%@ include file="inc/footer.inc.jsp"%>