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
					ArrayList<DbNews> list = (ArrayList<DbNews>) request.getAttribute("list");
					String words = (String) request.getAttribute("words");
					%>
					<div class="label">
					<%
						if(request.getAttribute("type")!=null)
						{
							if("author".equals(request.getAttribute("type")))
							{
								%>
								<label id="searchresult"><%out.println(list.size() + " result(s) found for the author " + request.getAttribute("author")); %></label><p>
								<%
							}
							else
							{
								%>
								<label id="searchresult"><%out.println(list.size() + " result(s) found "); %></label><p>
								<%
							}
						}
						else
						{%>
							<label id="searchresult"><%out.println(list.size() + " result(s) found for the terms: " + words); %></label><p>
						<%}
					%>
					</div>										
					
					
				
					<%
					if(list.size() > 0)
					{
					for(DbNews news : list) 
					{
						if(request.getAttribute("type")!=null)
						{
							if("author".equals(request.getAttribute("type")))
							{
								String nome = (String) request.getAttribute("author");
								%>
								 <a id="news" href="Register?operation=seladvauthor&chave=<% out.println(news.getChave()); %>&author=<% out.println(nome); %>"><%out.println(news.getTitle());%></a><%
								 out.println("<p>");
							}
							else
							{
								String type = (String) request.getAttribute("type");
								String datepicker = (String) request.getAttribute("datepicker");
								String st = (String) request.getAttribute("st");
								String date = (String) request.getAttribute("date");
								%>
								 <a id="news" href="Register?operation=seladvsearch&chave=<% out.println(news.getChave()); %>&datepicker=<% out.println(datepicker); %>&st=<% out.println(st); %>&date=<% out.println(date); %>&type=<% out.println(type); %>"><%out.println(news.getTitle());%></a><%
								   out.println("<p>");
							}
						}
						else
						{
							%>
							 <a id="news" href="Register?operation=selnewsearch&chave=<% out.println(news.getChave()); %>&st=<% out.println(words); %>"><%out.println(news.getTitle());%></a><%
							   out.println("<p>");
						}	
					}
					}
					else
					{%>
						<div class="textnew">
					Sorry, we couldn't get any result for the given terms.
					</div>	
					<%}
					%>
			</div>
		</div>
	</div>
</body>