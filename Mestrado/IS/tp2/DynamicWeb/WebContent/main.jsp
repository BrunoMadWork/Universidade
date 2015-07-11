<%@ include file="header.jsp" %>
<%@ page import="java.util.ArrayList" %> 
<%@ page import="toDatabase.DbNews" %>

<html>

<head>
	<title> CNN News </title>
	<link rel="stylesheet" type="text/css" href="style.css"></link>
</head>
<body>
	<div class="cinzento">
		<div class="noticias">
			<div class="latest">
				
				<%
					String type = selected; //selected vem do header
					Integer numpages = (Integer) request.getAttribute("numpages");
           			Integer current = (Integer) request.getAttribute("current");
					%><div class="label"><%
					if("latest".equals(type))
					{
				%>
				
					<label id="latest">Latest News</label><p>
				
				<% } else if("world".equals(type))
				{
					%>
					<label id="latest">World</label><p>
					<% } else if("states".equals(type))
					{
						%>
						<label id="latest">United States</label><p>
						<% } else if("africa".equals(type))	
						{
							%>
							<label id="latest">Africa</label><p>
							<% } else if("asia".equals(type))
							{
								%>
								<label id="latest">Asia</label><p>
								<% } else if("europe".equals(type))
								{
									%>
									<label id="latest">Europe</label><p>
									<% } else if("latin".equals(type))
									{
										%>
										<label id="latest">Latin America</label><p>
										<% } else if("middle".equals(type))
										{
											%>
											<label id="latest">Middle East</label><p>
											<%}%></div><%											
					
					ArrayList<DbNews> list = (ArrayList<DbNews>) request.getAttribute("list");
				
			
					for(DbNews news : list) 
					{%>
						 <a id="news" href="Register?operation=selnew&page=<%out.println(current); %>&news=<%out.println(type); %>&chave=<% out.println(news.getChave()); %>"><%out.println(news.getTitle());%></a><%
						   out.println("<p>");
					}

           			
           			if(numpages != 1)
           			{
           			for(int i=1;i<=numpages;i++)
           			{
           				if(current == i)
           				{%><label id="numsel"><%
           					out.println(i);%></label><%       
           				}
           				else
           				{
           					%><a id="logout" href="${pageContext.request.contextPath}/Register?operation=news&news=<%out.println(type); %>&page=<% out.println(i); %>"><%out.println(i); %></a><%
           				}
           			}
           			}
           		%>
			</div>
		</div>
	</div>
</body>