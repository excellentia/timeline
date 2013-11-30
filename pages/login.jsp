<%-- TLD inclusion --%>
<%@ include file="inc/includes.inc.jsp"%>
<%-- Header Setup --%>
<c:set var="pageTitle" value="Login" />
<%@ include file="inc/header.inc.jsp"%>
<%-- Main content --%>
<div id="content">
	<div id="login" align="center">
		<form name="loginForm" id="loginForm" action="Login" method="POST">
			<table style="width: 27em;">
				<colgroup>
					<col style="width: 25%" />
					<col style="width: 75%" />
				</colgroup>
				<tbody>
					<tr>
						<td colspan="2" class="titleBar">Login</td>
					</tr>
					<tr>
						<td>User Id</td>
						<td><input type="text" class="dataEntry" id="userId" name="userId" tabindex="1" maxlength="20" /></td>
					</tr>
					<tr>
						<td>Password</td>
						<td><input type="password" class="dataEntry" id="password" name="password" tabindex="2" maxlength="20" /></td>
					</tr>
					<tr>
						<td style="font-size: 0.8em; border-right: 1px solid white">
							<a style="text-decoration: none;" title="Forgot Password ?" href="./Reset">Troubleshoot !</a>
						</td>
						<td style="text-align: right"><input type="submit" value="Login" id="loginButton" class="button" tabindex="3" /></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</div>
<%-- Footer Setup --%>
<%@ include file="inc/footer.inc.jsp"%>
<%-- Focus  --%>
<script type="text/javascript">focus('userId');</script>
