<%-- TLD inclusion --%>
<%@ include file="inc/includes.inc.jsp"%>

<%-- Header Setup --%>
<c:set var="pageTitle" value="Report" />
<%@ include file="inc/header.inc.jsp"%>

<%-- Menu Setup --%>
<c:set var="activeMenu" value="report" />
<%@ include file="inc/menu.inc.jsp"%>

<%-- Main content --%>
<div id="content">
	<p>Select Search Criteria</p>
	<div id="searchBar">
		<form action="./Report" id="searchForm" name="searchForm" method="POST">
			<c:set var="selectedProjId" value="${projectId}" />
			<c:set var="selectedActId" value="${activityId}" />
			<c:set var="selectedUserId" value="${userId}" />
			<div style="display: inline; padding: 0; margin: 0">
				<select id="projectId" size="1" name="projectId" class="dateSearch" title="Select Project"
					onchange="populateActivities('projectId','activityId')">
					<option value="0" <c:if test="${selectedProjId == 0}">selected="selected"</c:if>>All Projects</option>
					<c:forEach var="project" items="${PROJECT_LIST.projects}">
						<option value="${project.code}" <c:if test="${selectedProjId == project.code}">selected="selected"</c:if>>${project.value}</option>
					</c:forEach>
				</select>
			</div>
			<div style="display: inline; padding: 0; margin: 0">
				<select id="activityId" size="1" name="activityId" class="dateSearch" title="Select Activity">
					<option value="0" <c:if test="${selectedActId == 0}">selected="selected"</c:if>>All Activities</option>
					<c:if test="${selectedProjId > 0}">
						<c:forEach var="activity" items="${ACTIVITY_LIST.getProjectActivities(selectedProjId)}">
							<option value="${activity.code}" <c:if test="${selectedActId == activity.code}">selected="selected"</c:if>>${activity.value}</option>
						</c:forEach>
					</c:if>
				</select>
			</div>
			<div style="display: inline; padding: 0; margin: 0">
				<select id="userId" size="1" name="userId" class="dateSearch" title="Select User">
					<option value="0" <c:if test="${selectedUserId == 0}">selected="selected"</c:if>>All Users</option>
					<c:forEach var="user" items="${USER_LIST.users}">
						<option value="${user.id}" <c:if test="${selectedUserId == user.id}">selected="selected"</c:if>>${user.userName}</option>
					</c:forEach>
				</select>
			</div>
			<div style="display: inline; padding: 0; margin: 0">
				<input type="submit" value="Create Report" class="button" />
			</div>
		</form>
	</div>
	<div>
		<c:if test="${((reply != null) && (reply.rowCount > 0))}">
			<c:forEach var="projectId" items="${reply.projectIds}">
				<table id="timeTable" class="reportResult">
					<colgroup>
						<col style="width: 30%" />
						<col style="width: 40%" />
						<col style="width: 30%" />
					</colgroup>
					<thead>
						<tr class="reportTotal">
							<th>Project</th>
							<th>Activity</th>
							<th title="Total For Activity">&Sigma; Effort</th>
						</tr>
					</thead>
					<tbody class="reportBody">
						<c:set var="activityList" value="${reply.getActivityRowList(projectId)}" />
						<c:set var="userList" value="${reply.getUserRowList(projectId)}" />
						<c:set var="activityCount" value="${activityList.size()}" />
						<c:set var="userCount" value="${userList.size()}" />
						<c:set var="activity" value="${activityList.get(0)}" />
						<c:set var="activityId" value="${activityList.get(0).rowId}" />
						<c:set var="total" value="0" />
						<tr>
							<td rowspan="${activityCount + userCount + 4}">${activity.projectName}</td>
							<td>${activity.rowName}</td>
							<td>${activity.rowTime}</td>
							<c:set var="total" value="${total+activity.rowTime}" />
						</tr>
						<c:forEach var="activity" begin="1" items="${activityList}">
							<tr>
								<td>${activity.rowName}</td>
								<td>${activity.rowTime}</td>
								<c:set var="total" value="${total+activity.rowTime}" />
							</tr>
						</c:forEach>
						<tr class="reportTotal">
							<td>TOTAL</td>
							<td title="Total Across All Activities">${total}</td>
						</tr>
						<tr>
							<td colspan="3"></td>
						</tr>
						<tr class="reportTotal">
							<th>Team Member</th>
							<th title="Total For Team Member">&Sigma; Effort</th>
						</tr>
						<c:set var="total" value="0" />
						<c:forEach var="user" items="${userList}">
							<tr>
								<td>${user.rowName}</td>
								<td>${user.rowTime}</td>
								<c:set var="total" value="${total+user.rowTime}" />
							</tr>
						</c:forEach>
						<tr class="reportTotal">
							<td>TOTAL</td>
							<td title="Total Across All Team Members">${total}</td>
						</tr>
					</tbody>
				</table>
			</c:forEach>
		</c:if>
		<c:if test="${(reply == null) || (reply.rowCount == 0)}">
			<p class="error">No Data Found</p>
		</c:if>
	</div>
	<%--
	<c:if test="${SESSION_USER.admin}">
		<div id="toolbar">
			<form action="./Report" id="exportForm" name="exportForm" method="POST">
				<input type="hidden" name="exportToFile" value="true" />
				<input type="submit" value="Export Excel" class="button" />
			</form>
		</div>
	</c:if>
	--%>
</div>
<%-- Footer Setup --%>
<%@ include file="inc/footer.inc.jsp"%>
