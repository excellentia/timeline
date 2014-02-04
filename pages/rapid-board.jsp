<%-- TLD inclusion --%>
<%@ include file="inc/includes.inc.jsp"%>

<%-- Header Setup --%>
<c:set var="pageTitle" value="Rapid Board" />
<c:set var="pageScriptName" value="rapid-board" />
<%@ include file="inc/header.inc.jsp"%>

<%-- Menu Setup --%>
<c:set var="activeMenu" value="rapid" />
<%@ include file="inc/menu.inc.jsp"%>

<%-- Main content --%>
<div id="content">
	<div id="messageBar"></div>
	<div class="thin"><p>Select Project</p></div>
	<div id="searchBar" class="topPadding">
		<form action="RapidBoard" id="rapidForm" name="rapidForm" method="POST">
			<c:set var="selectedProjId" value="${projectId}" />
			<c:set var="selectedUserId" value="${userId}" />
			<div class="thin">
				<select id="projectSelect" size="1" name="projectId" class="dropDown" title="Select Project">
					<option value="0" selected="selected">Select A Project</option>
					<c:forEach var="project" items="${PROJECT_LIST.projects}">
						<option value="${project.code}" <c:if test="${selectedProjId == project.code}">selected="selected"</c:if>>${project.value}</option>
					</c:forEach>
				</select>
			</div>
			<div class="thin leftPadding">
				<input type="button" value="Show Rapid Board" class="button" onclick="getRapidBoardData('rapidForm','projectSelect')"/>
			</div>
		</form>
	</div>
	<div class="rapidBoardDiv">
		<c:if test="${((reply != null) && (reply.projectData != null))}">
			<div class="portlet-header ui-widget-header ui-corner-all">Rapid Board - ${reply.projectData.value}</div>
			<c:if test="${(reply.activityIdList != null)}">
				<c:forEach var="activityId" items="${reply.activityIdList}">
					<div class="stg-column">
						<div class="portlet-header" id="stage_${activityId}">${reply.getActivityName(activityId)}</div>
						<ul class="droptrue">
							<c:if test="${(reply.hasTasks())}">
								<c:forEach var="task" items="${reply.getActivityTasks(activityId)}">
									<li id="task_${task.code}">${task.value}</li>
								</c:forEach>
							</c:if>
						</ul>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${(reply.activityIdList == null) || (reply.msgError)}">
				<p class="error">No Activities Defined for Selected Project</p>
			</c:if>
		</c:if>
	</div>
	<div id="light" class="white_content" style="margin: 0; padding: 0.5em;">
		<div id="taskDetails" style="display: inline; width: 100%; height: 95%; clear: both"></div>
		<div id="closeBar" style="display: inline; width: 100%; padding-top: 0.5em; height: 5%; text-align: right; float: right; clear: both">
			<a href="javascript:void(0)" onclick="hideTimeEntryOverLay('quickTaskTimeEntry')" title="Click to Close / Press Esc">Close</a>
		</div>
	</div>
	<div id="fade" class="black_overlay"></div>
</div>
<%-- Footer Setup --%>
<%@ include file="inc/footer.inc.jsp"%>
