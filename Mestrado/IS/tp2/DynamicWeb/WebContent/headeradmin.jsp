<%
	String selected = "";
	if(request.getParameter("news") != null)
	{
		selected = request.getParameter("news");
	}
%>
<html>

<head>
	<title> CNN News </title>
	<link rel="stylesheet" type="text/css" href="style.css"></link>
	<% 
		if(session.getAttribute( "currentSessionUser" )==null)
		{%>
			<meta http-equiv="refresh" content="0;url=login.jsp" />
	<%} %>
</head>
<body>

	<div class="superior">
	</div>
	<div class="meio">
	
		<div class="content_meio">
		
			<a href="Register?operation=listusers"><img src="images/logo.png" id="logo"></a>
			<div class="adminlabel">Admin Console</div>
			<div class="username">
				<%@ page session="true"%>
				<% out.println("Hi! "); %>
				<%= session.getAttribute( "currentSessionUser" ) %>

				<div class="logout"><a href="Register?operation=logout" id="logout">Log out</a></div>
			</div>
			
		</div>
	</div>
	<div class="menu">
		<div class="content_menu">
			
		</div>
	</div>
	
</body>
</html>