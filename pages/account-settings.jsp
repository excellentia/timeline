<%-- TLD inclusion --%>
<%@ include file="inc/includes.inc.jsp"%>

<%-- Header Setup --%>
<c:set var="pageTitle" value="My Account Settings" />
<c:set var="pageScriptName" value="account" />
<%@ include file="inc/header.inc.jsp"%>

<%-- Menu Setup --%>
<c:set var="activeMenu" value="account" />
<%@ include file="inc/menu.inc.jsp"%>

<%-- Main content --%>
<div id="content">
	<div id="account" align="center">
		<c:set var="userId" value="${SESSION_USER.id}" />
		<table style="width: 30em; margin: 0; padding: 0" id="${userId}">
			<colgroup>
				<col style="width: 30%" />
				<col style="width: 65%" />
				<col style="width: 5%" />
			</colgroup>
			<tbody>
				<tr>
					<td colspan="3" class="titleBar">Account Settings</td>
				</tr>
				<tr id="USER_ID">
					<td class="label">User Id</td>
					<td class="data">${SESSION_USER.userId}</td>
					<td align="center"><img alt="Edit" align="middle" class="icon" title="${editTitle}" src="${editIconPath}" onclick="editAccountSettings('USER_ID',${userId})" /></td>
				</tr>
				<tr id="FIRST_NAME">
					<td class="label">First Name</td>
					<td class="data">${SESSION_USER.firstName}</td>
					<td align="center"><img alt="Edit" align="middle" class="icon" title="${editTitle}" src="${editIconPath}" onclick="editAccountSettings('FIRST_NAME',${userId})" /></td>
				</tr>
				<tr id="LAST_NAME">
					<td class="label">Last Name</td>
					<td class="data">${SESSION_USER.lastName}</td>
					<td align="center"><img alt="Edit" align="middle" class="icon" title="${editTitle}" src="${editIconPath}" onclick="editAccountSettings('LAST_NAME',${userId})" /></td>
				</tr>
				<tr id="PASSWORD">
					<td class="label">Password</td>
					<td class="data">${SESSION_USER.password}</td>
					<td align="center"><img alt="Edit" align="middle" class="icon" title="${editTitle}" src="${editIconPath}" onclick="editAccountSettings('PASSWORD',${userId})" /></td>
				</tr>
				<tr id="QUESTION">
					<td class="label">Question</td>
					<td class="data">${preferences.question}</td>
					<td align="center"><img alt="Edit" align="middle" class="icon" title="${editTitle}" src="${editIconPath}" onclick="editAccountSettings('QUESTION',${userId})" /></td>
				</tr>
				<tr id="ANSWER">
					<td class="label">Answer</td>
					<td class="data">${preferences.answer}</td>
					<td align="center"><img alt="Edit" align="middle" class="icon" title="${editTitle}" src="${editIconPath}" onclick="editAccountSettings('ANSWER',${userId})" /></td>
				</tr>
				<tr id="EMAIL">
					<td class="label">Enterprise Id</td>
					<td class="data">${preferences.email}</td>
					<td align="center"><img alt="Edit" align="middle" class="icon" title="${editTitle}" src="${editIconPath}" onclick="editAccountSettings('EMAIL',${userId})" /></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<%-- Footer Setup --%>
<%@ include file="inc/footer.inc.jsp"%>
<%-- Focus  --%>
<script type="text/javascript">focus('userId');</script>