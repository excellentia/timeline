<%-- TLD inclusion --%>
<%@ include file="inc/includes.inc.jsp"%>

<%-- Header Setup --%>
<c:set var="pageTitle" value="Report" />
<c:set var="pageScriptName" value="report" />
<%@ include file="inc/header.inc.jsp"%>

<%-- Menu Setup --%>
<c:set var="activeMenu" value="report" />
<%@ include file="inc/menu.inc.jsp"%>

<%-- Main content --%>
<div id="content">
	<div id="messageBar"></div>
	<div id="reportAccordion">
		<%-- Project Specific Reports Section--%>
		<h3>
			<a href="#reportSection1">Project Specific Reports</a>
		</h3>
		<div>
			<p>Select Search Criteria</p>
			<div id="searchBar">
				<form action="./Report" id="searchForm" name="searchForm" method="POST">
					<c:set var="selectedProjId" value="${projectId}" />
					<c:set var="selectedActId" value="${activityId}" />
					<c:set var="selectedUserId" value="${userId}" />
					<div style="display: inline; padding: 0; margin: 0">
						<select id="projectId" size="1" name="projectId" class="dateSearch" title="Select Project" onchange="populateActivities('projectId','activityId')">
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
								<c:forEach var="activity" items="${ACTIVITY_LIST.getProjectActivitiesById(selectedProjId)}">
									<option value="${activity.code}" <c:if test="${selectedActId == activity.code}">selected="selected"</c:if>>${activity.value}</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
					<div style="display: inline; padding: 0; margin: 0;">
						<select id="userId" size="1" name="userId" class="dateSearch" title="Select User">
							<option value="0" <c:if test="${selectedUserId == 0}">selected="selected"</c:if>>All Users</option>
							<c:forEach var="user" items="${USER_LIST.users}">
								<option value="${user.id}" <c:if test="${selectedUserId == user.id}">selected="selected"</c:if>>${user.userName}</option>
							</c:forEach>
						</select>
					</div>
					<div style="display: inline; padding: 0; padding-left : 1em; margin: 0">
						<input type="submit" value="Create Report" class="button" />
					</div>
				</form>
			</div>
			<div>
				<c:if test="${((reply != null) && (reply.rowCount > 0))}">
					<form name="detailForm" id="detailForm" action="ReportDetail" method="POST">
						<input type="hidden" name="projectId" value="0"/>
					</form>
					<c:forEach var="projectId" items="${reply.projectIds}">
						<table id="timeTable" class="reportResult">
							<c:set var="activityList" value="${reply.getActivityRowList(projectId)}" />
							<c:set var="userList" value="${reply.getUserRowList(projectId)}" />
							<c:set var="activityCount" value="${activityList.size()}" />
							<c:set var="userCount" value="${userList.size()}" />
							<c:set var="activity" value="${activityList.get(0)}" />
							<c:set var="activityId" value="${activityList.get(0).rowId}" />
							<c:set var="total" value="0" />
							<colgroup>
								<col style="width: 50%" />
								<col style="width: 50%" />
							</colgroup>
							<thead>
								<tr>
									<th colspan="2" style="font-size: 1.5em;">${activity.projectName}</th>
								</tr>
								<tr class="reportTotal">
									<th>Activity</th>
									<th title="Total For Activity">&Sigma; Effort</th>
								</tr>
							</thead>
							<tbody class="reportBody">
								<tr>
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
									<td title="Total Across All Activities"><fmt:formatNumber value="${total}" maxFractionDigits="2"/></td>
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
									<td title="Total Across All Team Members"><fmt:formatNumber value="${total}" minFractionDigits="1" maxFractionDigits="1"/></td>
								</tr>
								<tr>
									<td colspan="2"><img id="showDetailReport" alt="View Details" align="middle" class="icon" title="${detailTitle}" src="${detailIconPath}" onclick="viewReportDetails(${projectId})" /></td>
								</tr>
							</tbody>
						</table>
					</c:forEach>
				</c:if>
				<c:if test="${(reply == null) || (reply.rowCount == 0)}">
					<p class="error">No Data Found</p>
				</c:if>
			</div>
		</div>
		<%-- Executive Reports Section--%>
		<h3>
			<a href="#reportSection2">Executive Reports</a>
		</h3>
		<div>
			<c:choose>
				<c:when test="${((reply != null) && (reply.rowCount > 0))}">
						<table id="executiveTable" style="width: 25%;">
							<colgroup>
								<col style="width: 50%" />
								<col style="width: 25%" />
								<col style="width: 25%" />
							</colgroup>
							<thead>
								<tr class="reportTotal">
									<th>Project</th>
									<th>&Sigma; Effort</th>
									<th>Include ?</th>
								</tr>
							</thead>
							<tbody class="reportBody">
								<c:set var="total" value="0" />
								<c:forEach var="projectId" items="${reply.projectIds}">
									<tr>
										<td>${reply.getProjectName(projectId)}</td>
										<td>${reply.getProjectTime(projectId)}</td>
										<c:set var="total" value="${total+reply.getProjectTime(projectId)}" />
										<c:set var="prjCheckElmId" value="check_project_${projectId}" />
										<td><input type="checkbox" checked="checked" id="${prjCheckElmId}" onclick="toggleProjectInclusion(${reply.getProjectTime(projectId)},'${prjCheckElmId}')"></td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr class="reportTotal" style="text-align: center;">
									<td>TOTAL</td>
									<td colspan="2" id="projTotal" title="Total Across All Projects Selcted"><fmt:formatNumber value="${total}" minFractionDigits="1" maxFractionDigits="1"/></td>
								</tr>
							</tfoot>
						</table>
				</c:when>
				<c:otherwise>
					<p class="error">No Data Found</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div id="light" class="white_content" style="margin : 0; padding : 0.5em;">
		<div id="reportDetails" style="display:inline; width: 100%;height : 95%; clear : both">
		</div>
		<div id="closeBar" style="display:inline; width: 100%; padding-top : 0.5em; height : 5%; text-align : right;float : right; clear : both">
			<a href = "javascript:void(0)" onclick = "hideReportDetails()" title="Click to Close / Press Esc">Close</a>
		</div>
	</div>
	<div id="fade" class="black_overlay"></div>
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
