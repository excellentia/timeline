<%-- TLD inclusion --%>
<%@ include file="inc/includes.inc.jsp"%>

<%-- Header Setup --%>
<c:set var="pageTitle" value="Admin" />
<%@ include file="inc/header.inc.jsp"%>

<%-- Menu Setup --%>
<c:set var="activeMenu" value="admin" />
<%@ include file="inc/menu.inc.jsp"%>

<%-- Main content --%>
<div id="content">
	<div id="accordion">
		<%-- User Related Section--%>
		<h3>
			<a href="#section2">User Account Management</a>
		</h3>
		<div>
			<table style="width: 50%;" id="userTable">
				<colgroup>
					<col style="width: 18%" />
					<col style="width: 28%" />
					<col style="width: 28%" />
					<col style="width: 8%" />
					<col style="width: 6%" />
					<col style="width: 6%" />
					<col style="width: 6%" />
				</colgroup>
				<thead>
					<tr>
						<th>User ID</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Admin</th>
						<th colspan="3">&nbsp;</th>
					</tr>
				</thead>
				<c:if test="${(userReply!= null) && (userReply.users != null) && (userReply.users.size() > 0)}">
					<tbody class="reportBody">
						<c:forEach var="user" items="${userReply.users}">
							<c:set var="userId">user_${user.id}</c:set>
							<tr id="${userId}">
								<td>${user.userId}</td>
								<td>${user.firstName}</td>
								<td>${user.lastName}</td>
								<td>
									<c:if test="${user.admin}">
										<img alt="Admin" align="middle" class="icon" title="${adminTitle}" src="${adminIconPath}" />
									</c:if></td>
								<td><img alt="Edit" align="middle" class="icon" title="${editTitle}" src="${editIconPath}" onclick="editUser('${userId}',${user.id})" /></td>
								<td><img alt="Reset" align="middle" class="icon" title="${resetTitle}" src="${resetIconPath}" onclick="resetUser('${userId}',${user.id})" /></td>
								<td><img alt="Delete" align="middle" class="icon" title="${userDeleteTitle}" src="${deleteIconPath}" onclick="deleteUser('${userId}',${user.id})" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</c:if>
				<tfoot>
					<tr>
						<td colspan="7" style="text-align: right">
							<input type="button" value="Add User" class="button" onclick="addNewUser('userTable')" />
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
		<%-- Project Related Section--%>
		<h3>
			<a href="#section1">Project & Activity Configuration</a>
		</h3>
		<div>
			<div id="searchBar">
				<input type="text" class="projectSearch" id="newProject" name="newProject" title="Enter New Project Name" maxlength="20" /> 
				<input type="button" value="Create Project" class="button"	onclick="saveProject('projectSection',0, 'newProject')" />
			</div>
			<div id="projectSection">
				<c:if test="${(projectReply != null) && (projectReply.projects != null) && (projectReply.projects.size() > 0)}">
					<c:forEach var="project" items="${projectReply.projects}">
						<c:set var="projId">project_${project.code}</c:set>
						<table class="projectTable" id='${projId}'>
							<colgroup>
								<col style="width: 88%" />
								<col style="width: 6%" />
								<col style="width: 6%" />
							</colgroup>
							<tbody>
								<c:set var="projTitleId">${projId}_title</c:set>
								<tr id="${projTitleId}">
									<td class="projectArea">${project.value}</td>
									<td align="center"><img alt="Edit" align="middle" class="icon" title="${editTitle}" src="${editIconPath}" onclick="editProject('${projTitleId}',${project.code})" /></td>
									<td align="center"><img alt="Delete" align="middle" class="icon" title="${projectDeleteTitle}" src="${deleteIconPath}" onclick="deleteProject('${projId}',${project.code})" /></td>
								</tr>
								<c:set var="projLeadId">${projId}_lead</c:set>
								<c:set var="leadName" value="${project.leadName}" />
								<c:if test="${project.leadName == null}">
									<c:set var="leadName" value="Please select..." />
								</c:if>
								<tr id="${projLeadId}">
									<td class="leadArea">${leadName}</td>
									<td align="center" colspan="2"><img alt="Edit Lead" align="middle" class="icon" title="${editTitle}" src="${editIconPath}" onclick="editLead('${projLeadId}',${project.code})" /></td>
								</tr>
								<c:if test="${activityReply != null}">
									<c:forEach var="activity" items="${activityReply.getProjectActivitiesById(project.code)}">
										<c:set var="actId">activity_${activity.code}</c:set>
										<tr id='${actId}'>
											<td class="activityArea">${activity.value}</td>
											<td align="center"><img alt="Edit" align="middle" class="icon" title="${editTitle}" src="${editIconPath}" onclick="editActivity('${actId}',${activity.code},${project.code})" /></td>
											<td align="center"><img alt="Delete" align="middle" class="icon" title="${activityDeleteTitle}" src="${deleteIconPath}" onclick="deleteActivity('${actId}', ${activity.code})" /></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="3" align="right">
										<input type="button" value="Add Activity" class="button" onclick="addNewActivity('${projId}',${project.code})" />
									</td>
							</tfoot>
						</table>
					</c:forEach>
				</c:if>
			</div>
		</div>
	</div>
</div>
<%-- Footer Setup --%>
<%@ include file="inc/footer.inc.jsp"%>
