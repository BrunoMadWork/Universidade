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
		
			<a href="Register?operation=news&news=latest"><img src="images/logo.png" id="logo"></a>
			
			<div class="username">
				<%@ page session="true"%>
				<% out.println("Hi! "); %>
				<%= session.getAttribute( "currentSessionUser" ) %>

				<div class="logout"><a href="Register?operation=logout" id="logout">Log out</a></div>
			</div>
			<div class="search">
				<form name="search" action="${pageContext.request.contextPath}/Register"> 
					<input value="search by highlights" onfocus="if(this.value == 'search by highlights') {this.value=''}" onblur="if(this.value == ''){this.value ='search by highlights'}" id="text_search" type="text" name="st"/>
					<input type="hidden" name="operation" value="search">
					<input id="procurar" type="submit" value="Search">
				</form>
			</div>
			
			<div class="advanced_search">
				<a href="Register?operation=advancedauthor" id="search">Advanced Search</a>
			</div>
		</div>
	</div>
	<div class="menu">
		<div class="content_menu">
			<ul id="zonas">
					<% 
						if("latest".equals(selected))
						{%>
							<a id="menu2"  href="Register?operation=news&news=latest"><li style="background-color: rgb(220,230,221);">Home</li></a>
						<%}
						else
						{%>
							<a id="menu" href="Register?operation=news&news=latest"><li>Home</li></a>
						<%}
					%>
					
					<% 
						if("world".equals(selected))
						{%>
							<a id="menu2"  href="Register?operation=news&news=world"><li style="background-color: rgb(220,230,221);">World</li></a>
						<%}
						else
						{%>
							<a id="menu" href="Register?operation=news&news=world"><li>World</li></a>
						<%}
					%>
					
					<% 
						if("states".equals(selected))
						{%>
							<a id="menu2"  href="Register?operation=news&news=states"><li style="background-color: rgb(220,230,221);">United States</li></a>
						<%}
						else
						{%>
							<a id="menu" href="Register?operation=news&news=states"><li>United States</li></a>
						<%}
					%>
					
					<% 
						if("africa".equals(selected))
						{%>
							<a id="menu2"  href="Register?operation=news&news=africa"><li style="background-color: rgb(220,230,221);">Africa</li></a>
						<%}
						else
						{%>
							<a id="menu" href="Register?operation=news&news=africa"><li>Africa</li></a>
						<%}
					%>
					
					<% 
						if("asia".equals(selected))
						{%>
							<a id="menu2"  href="Register?operation=news&news=asia"><li style="background-color: rgb(220,230,221);">Asia</li></a>
						<%}
						else
						{%>
							<a id="menu" href="Register?operation=news&news=asia"><li>Asia</li></a>
						<%}
					%>
					
					<% 
						if("europe".equals(selected))
						{%>
							<a id="menu2"  href="Register?operation=news&news=europe"><li style="background-color: rgb(220,230,221);">Europe</li></a>
						<%}
						else
						{%>
							<a id="menu" href="Register?operation=news&news=europe"><li>Europe</li></a>
						<%}
					%>
					
					<% 
						if("latin".equals(selected))
						{%>
							<a id="menu2"  href="Register?operation=news&news=latin"><li style="background-color: rgb(220,230,221);">Latin America</li></a>
						<%}
						else
						{%>
							<a id="menu" href="Register?operation=news&news=latin"><li>Latin America</li></a>
						<%}
					%>
					
					<% 
						if("middle".equals(selected))
						{%>
							<a id="menu2"  href="Register?operation=news&news=middle"><li style="background-color: rgb(220,230,221);">Middle East</li></a>
						<%}
						else
						{%>
							<a id="menu" href="Register?operation=news&news=middle"><li>Middle East</li></a>
						<%}
					%>
			</ul>
		</div>
	</div>
	
</body>
</html>