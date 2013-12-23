<html>
	<head>
		<%
			response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
			response.setHeader("Pragma", "no-cache"); //HTTP 1.0
			response.setDateHeader("Expires", 0);
		%>
		<link rel="stylesheet" type="text/css" href="./pages/inc/css/timeline.css"/>
		<link rel="stylesheet" type="text/css" href="./pages/inc/css/jquery-ui-1.8.24.custom.css"/>
		<script type="text/javascript"  src="./pages/inc/jquery/jquery-1.8.2.min.js" ></script>
		<script type="text/javascript" src="./pages/inc/jquery/jquery-ui-1.8.24.custom.min.js" ></script>
		<script type="text/javascript" src="./pages/inc/js/timeline.js" ></script>
		<title>${pageTitle}</title>
	</head>
	<body>
		<div id="container">
			<%-- Header Text --%>
			<c:set var="applicationHeader" value="Timeline" />
			<div id="header">${applicationHeader}</div>
			<c:remove var="applicationHeader"/>
