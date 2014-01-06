<%-- TLD inclusion --%>
<%@ include file="inc/includes.inc.jsp"%>

<%-- Header Setup --%>
<c:set var="pageTitle" value="Forgot Password" />
<c:set var="pageScriptName" value="reset" />
<%@ include file="inc/header.inc.jsp"%>

<%-- Main content --%>
<div id="content">
	<div id="login" align="center">
		<c:if test="${(question == null) || (userId == null)}">
			<div id="userArea">
				<form name="userForm" id="userForm" action="Reset" method="POST">
					<table style="width: 25em;">
						<colgroup>
							<col style="width: 20%" />
							<col style="width: 80%" />
						</colgroup>
						<tbody>
							<tr>
								<td colspan="2" class="titleBar">Forgot Password</td>
							</tr>
							<tr>
								<td>User Id</td>
								<td><input type="text" class="dataEntry" id="userId" name="userId" tabindex="1" maxlength="20" /></td>
							</tr>
							<tr>
								<td style="font-size: 0.8em; border-right: 1px solid white">
									<a style="text-decoration: none;" title="Login" href="Login">Login !</a>
								</td>
								<td style="text-align: right">
									<input type="submit" value="Submit" id="loginButton" class="button" tabindex="3" />
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</c:if>
		<c:if test='${(userId != null) && (userId != "") && (question != null) && (question !="")}'>
			<div id="questionArea">
				<form name="questionForm" id="questionForm" action="Reset" method="POST">
					<input type="hidden" name="userId" value="${userId}" />
					<table style="width: 30em;">
						<colgroup>
							<col style="width: 35%" />
							<col style="width: 65%" />
						</colgroup>
						<tbody>
							<tr>
								<td colspan="2" class="titleBar">Forgot Password</td>
							</tr>
							<tr id="secretQuestion">
								<td>Secret Question</td>
								<td>${question}</td>
							</tr>
							<tr id="secretAnswer">
								<td>Secret Answer</td>
								<td><input type="text" class="dataEntry" id="answer" name="answer" tabindex="2" maxlength="20" /></td>
							</tr>
							<tr style="text-align: right">
								<td colspan="2"><input type="submit" value="Cross Your Fingers" id="loginButton" class="button" tabindex="3" /></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</c:if>
	</div>
</div>
<%-- Footer Setup --%>
<%@ include file="inc/footer.inc.jsp"%>
<%-- Focus  --%>
<script type="text/javascript">
	focus('userId');
</script>
