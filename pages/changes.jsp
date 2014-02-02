<%-- TLD inclusion --%>
<%@ include file="inc/includes.inc.jsp"%>

<%-- Header Setup --%>
<c:set var="pageTitle" value="Change History" />
<c:set var="pageScriptName" value="changes" />
<%@ include file="inc/header.inc.jsp"%>

<%-- Menu Setup --%>
<c:set var="activeMenu" value="changes" />
<%@ include file="inc/menu.inc.jsp"%>

<%-- Main content --%>
<div id="content">
	<div id="messageBar"></div>
	<%-- Search Area --%>
	<div class="thin"><p>Select Search Criteria</p></div>
	<div id="searchBar" class="topPadding">
		<form action="Changes" id="searchForm" name="searchForm" method="POST">
			<c:set var="selectedUserId" value="${userId}" />
			<c:set var="selectedDataTypeId" value="${entityType}" />
			<c:set var="selectedOperationId" value="${operationType}" />
			<input type="hidden" name="search" value="true" />

			<div class="thin">From</div>
			<div class="thin rightPadding">
				<input type="text" id="fromDate" name="fromDate" class="dateSearch normalFont" style="width: 7em; text-align: center" value="${fromDate}" readonly="readonly">
				<img id="fromDateImg" src="${calendarIconPath}" />
			</div>
			<div class="thin">To</div>
			<div class="thin rightPadding">
				<input type="text" id="toDate" name="toDate" class="dateSearch normalFont" style="width: 7em; text-align: center" value="${toDate}"	readonly="readonly">
				<img id="toDateImg" src="${calendarIconPath}" />
			</div>
			<div class="thin">Type</div>
			<div class="thin rightPadding">
				<select id="entityType" size="1" name="entityType" class="dropDown normalFont" title="Select Data Type">
					<option value="0" <c:if test="${selectedDataTypeId == 0}">selected="selected"</c:if>>All Types</option>
					<c:forEach var="type" items="${DATA_TYPE_LIST}">
						<option value="${type.code}" <c:if test="${selectedDataTypeId == type.code}">selected="selected"</c:if>>${type.value}</option>
					</c:forEach>
				</select>
			</div>
			<div class="thin">Operation</div>
			<div class="thin rightPadding">
				<select id="operationType" size="1" name="operationType" class="dropDown normalFont" title="Select Operation Type">
					<option value="0" <c:if test="${selectedOperationId == 0}">selected="selected"</c:if>>All Operations</option>
					<c:forEach var="operationType" items="${OPERATION_TYPE_LIST}">
						<option value="${operationType.code}"
							<c:if test="${selectedOperationId == operationType.code}">selected="selected"</c:if>>${operationType.value}</option>
					</c:forEach>
				</select>
			</div>
			<c:if test="${SESSION_USER.admin}">
				<div class="thin">User</div>
				<div class="thin rightPadding">
					<select id="userId" size="1" name="userId" class="dropDown normalFont" title="Select User">
						<option value="0" <c:if test="${selectedUserId == 0}">selected="selected"</c:if>>All Users</option>
						<c:forEach var="user" items="${USER_LIST.users}">
							<option value="${user.id}" <c:if test="${selectedUserId == user.id}">selected="selected"</c:if>>${user.userName}</option>
						</c:forEach>
					</select>
				</div>
			</c:if>
			<div class="thin leftPadding">
				<input type="submit" value="Display Changes" class="button" style="font-size : 0.8em;"/>
			</div>
		</form>
	</div>
	<div>
		<c:if test="${search == true }">
			<c:if test="${((reply != null) && (reply.rowList != null) && (reply.rowList.size() > 0))}">
				<p>Search Results</p>
				<div style="margin-bottom: 2em;">
					<div id="my_change_accordion">
						<c:forEach var="change" items="${reply.rowList}">
							<c:set var="changeId">changeTable_${change.id}</c:set>
							<h3>
								<a href="#change_${changeId}">${change.formattedDate}&nbsp;-&nbsp;${change.formattedTime}&nbsp;-&nbsp;${change.operationText}&nbsp;:&nbsp;${change.dataTypeText}&nbsp;(&nbsp;${change.entityName}&nbsp;)&nbsp;by&nbsp;${change.userName}</a>
							</h3>
							<div>
								<table id="${changeId}" class="reportResult" style="width: 80%">
									<colgroup>
										<col style="width: 30%" />
										<col style="width: 35%" />
										<col style="width: 35%" />
									</colgroup>
									<thead>
										<tr class="reportTotal">
											<th>Field Name</th>
											<th>Old Value</th>
											<th>New Value</th>
										</tr>
									</thead>
									<tbody class="reportBody">
										<c:forEach var="detail" items="${change.details}">
											<tr>
												<td>${detail.fieldName}</td>
												<td>${detail.oldValue}</td>
												<td>${detail.newValue}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:if>
			<c:if test="${(reply == null) || (reply.rowList == null) || (reply.rowList.size() == 0)}">
				<p class="error">No Data Found</p>
			</c:if>
		</c:if>
	</div>
</div>
<%-- Footer Setup --%>
<%@ include file="inc/footer.inc.jsp"%>
