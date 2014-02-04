<%-- TLD inclusion --%>
<%@ include file="inc/includes.inc.jsp"%>

<div>
	<div style="padding-bottom: 2em;">
		<table style="width: 100%;">
			<colgroup>
				<col style="width: 25%" />
				<col style="width: 25%" />
				<col style="width: 20%" />
				<col style="width: 30%" />
			</colgroup>
			<thead>
				<tr>
					<th colspan="4" style="background: #609AFA; color: white">Task Details</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="detail" value="${reply.detailRow}" />
				<tr>
					<td class="label">Task</td>
					<td>${detail.taskName}</td>
					<td class="label">Date Created</td>
					<td><fmt:formatDate value="${detail.taskCreateDate}" pattern="dd MMM yyyy"/></td>
				</tr>
				<tr>
					<td class="label">Project</td>
					<td>${detail.projectName}</td>
					<td class="label">Stage</td>
					<td>${detail.activeStageName}</td>
				</tr>
				<tr>
					<td class="label">Description</td>
					<td colspan="3">${detail.taskDescription}</td>
				</tr>
				<c:remove var="detail" />
			</tbody>
		</table>
	</div>
	<div style="padding-bottom: 2em;">
		<table style="width: 100%;">
			<colgroup>
				<col style="width: 30%" />
				<col style="width: 20%" />
				<col style="width: 20%" />
				<col style="width: 30%" />
			</colgroup>
			<thead>
				<tr>
					<th colspan="4" style="background: #609AFA; color: white">Stage Changes</th>
				</tr>
				<tr>
					<th>Stage Name</th>
					<th>Start Date</th>
					<th>End Date</th>
					<th>Modified By</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${reply.stageRows != null}">
					<c:set var="endDate" value="" />
					<c:forEach var="stageRow" items="${reply.stageRows}">
						<tr>
							<td style="text-align: center">${stageRow.stageName}</td>
							<td style="text-align: center"><fmt:formatDate value="${stageRow.changeDate}" pattern="dd MMM yyyy hh:mm:ss"/></td>
							<td style="text-align: center"><fmt:formatDate value="${endDate}" pattern="dd MMM yyyy hh:mm:ss"/></td>
							<td style="text-align: center">${stageRow.modifierUser}</td>
						</tr>
						<c:set var="endDate" value="${stageRow.changeDate}" />
					</c:forEach>
					<c:remove var="endDate" />
				</c:if>
			</tbody>
		</table>
	</div>
	<div>
		<table style="width: 100%;">
			<colgroup>
				<col style="width: 30%" />
				<col style="width: 50%" />
				<col style="width: 20%" />
			</colgroup>
			<thead>
				<tr>
					<th colspan="3" style="background: #609AFA; color: white">Time Entries</th>
				</tr>
				<tr>
					<th>User Name</th>
					<th>Week</th>
					<th>Time</th>
				</tr>
			</thead>
			<c:set var="total" value="0" />
			<tbody>
				<c:if test="${reply.timeRows != null}">
					<c:forEach var="timeRow" items="${reply.timeRows}">
						<tr>
							<td style="text-align: center">${timeRow.user}</td>
							<td style="text-align: center">${timeRow.displayWeek}</td>
							<td style="text-align: center">${timeRow.time}</td>
						</tr>
						<c:set var="total" value="${total + timeRow.time}" />
					</c:forEach>
				</c:if>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="2" class="label" style="text-align: center">TOTAL</td>
					<td style="text-align: center">${total}</td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>
