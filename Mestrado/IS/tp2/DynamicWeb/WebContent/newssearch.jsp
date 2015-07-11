<%@ include file="header.jsp" %>
<%@ page import="java.util.ArrayList" %> 
<%@ page import="toDatabase.DbNews" %>

<%!
public static String arranjahora(int hora)
{
	int num = 0;
	String horanova = "";
	
	int length = String.valueOf(hora).length();
	if (length == 1)
	{
		int primeiro = Integer.parseInt(Integer.toString(hora).substring(0, 1));
		horanova = "" + "00:0" + primeiro;
	}
	else if (length == 2)
	{
		int primeiro = Integer.parseInt(Integer.toString(hora).substring(0, 1));
		int segundo = Integer.parseInt(Integer.toString(hora).substring(1, 2));
		horanova = "" + "00:" + primeiro + segundo ;
	}
	else if(length == 3)
	{
		int primeiro = Integer.parseInt(Integer.toString(hora).substring(0, 1));
		int segundo = Integer.parseInt(Integer.toString(hora).substring(1, 2));
		int terceiro = Integer.parseInt(Integer.toString(hora).substring(2, 3));
		
		horanova = "" + primeiro + ":" + segundo + terceiro;

	}
	else if (length == 4)
	{
		int primeiro = Integer.parseInt(Integer.toString(hora).substring(0, 1));
		int segundo = Integer.parseInt(Integer.toString(hora).substring(1, 2));
		int terceiro = Integer.parseInt(Integer.toString(hora).substring(2, 3));
		int quarto = Integer.parseInt(Integer.toString(hora).substring(3, 4));
		
		horanova = "" + primeiro + segundo + ":" + terceiro + quarto;

	}

	return horanova;
}

%>
<html>

<head>
	<title> CNN News </title>
	<link rel="stylesheet" type="text/css" href="style.css"></link>
</head>
<body>
	<div class="cinzento">
		<div class="noticias">
		
			<%  ArrayList<DbNews> list = (ArrayList<DbNews>) request.getAttribute("list");
				for(DbNews news : list){
				
				int hora=news.getTime();
				String horanova=arranjahora(hora);
				%>
						 
					
			<div class="latest">
				<%
					if(request.getAttribute("type") != null)
					{
						if("author".equals(request.getAttribute("type")))
						{
							String author = (String) request.getAttribute("author");
							%>
							<a href="Register?operation=searchauthor&author=<%out.println(author); %>"><img id="back" src="images/back.png"></a>
						<%
						}
						else
						{
							String st = (String) request.getAttribute("st");
							String datepicker = (String) request.getAttribute("datepicker");
							String date = (String) request.getAttribute("date");
							String type = (String) request.getAttribute("type");
					
						%>
							<a href="Register?operation=advancedsearch&st=<%out.println(st); %>&datepicker=<%out.println(datepicker); %>&date=<%out.println(date); %>&type=<%out.println(type); %>"><img id="back" src="images/back.png"></a>
						<%}
					}
					else
					{%>
						<a href="Register?operation=search&st=<%out.println(request.getAttribute("words")); %>"><img id="back" src="images/back.png"></a>
					<%}
				
				%>
				<div class="label">
					<label id="latest"><%out.println(news.getTitle());%></label>
					<div class="data">
						<%out.println(news.getAuthor());%><br>
						<%out.println(news.getDay());%>
						<%out.println(news.getMonth());%>
						<%out.println(news.getYear());%>
						<%out.println(", "+horanova); %>
					</div><p>
				</div>
				
				<div class="photo">
					<%
						if(news.getPhotos() != null)
						{%>
							<img id="photo" src="<% out.println(news.getPhotos());%>">
						<%}
					%>
				</div>
				<div class="highlights">
					Highlights<p>
					<div class="high">
						<%out.println((news.getHighlits()));%>
					</div>
				</div>
				<div class="textnew">
					<%out.println(news.getText());%>
				</div>
				<div class="data">
					Original url - <a id="logout" href="<%out.println(news.getUrl()); %>"><%out.println(news.getUrl()); %></a>
				</div>

			</div>
			<%}%>
		</div>
	</div>
</body>