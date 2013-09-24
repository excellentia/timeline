
<div id="menuarea">
	<div id="menubar">
		<ul>
			<li><a <c:if test="${activeMenu == 'time'}">class="activeMenu"</c:if> href="./MyTime">Time</a></li>
			<li><a <c:if test="${activeMenu == 'report'}">class="activeMenu"</c:if> href="./Report">Reports</a></li>
			<li><a <c:if test="${activeMenu == 'changes'}">class="activeMenu"</c:if> href="./Changes">Changes</a></li>
			<c:if test="${SESSION_USER.admin}">
				<li><a <c:if test="${activeMenu == 'admin'}">class="activeMenu"</c:if> href="./Admin">Admin</a></li>
			</c:if>
		</ul>
	</div>
	<div id="userbar">
		<ul>
			<c:if test="${SESSION_USER.id > 0}">
				<li style="width: 80%; float: left;"><a id="userNameMenu" href="./Account" <c:if test="${activeMenu == 'account'}">style="background-color: #FFF; color: #7792F6; padding-left:0.3em;border: 1px solid #7792F6;"</c:if> title="Account Settings">${SESSION_USER.userName}</a></li>
			</c:if>
			<c:if test="${SESSION_USER.id == 0}">
				<li style="width: 80%; float: left;padding: 0 2em 0 0;">${SESSION_USER.userName}</li>
			</c:if>
			<li style="width: 20%; float: right;"><a href="./Logout" title="Logout" style="padding: 0"><img alt="Logout" class="icon" src="./pages/inc/icons/logout.png" align="middle" /></a></li>
		</ul>
	</div>
</div>
