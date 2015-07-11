<%@ include file="headeradmin.jsp" %>
<%@ page import="java.util.ArrayList" %> 
<%@ page import="toDatabase.DbNews" %>
<%@ page import="data.UserData" %>

<html>

<head>
	<title> CNN News </title>
	<link rel="stylesheet" type="text/css" href="style.css"></link>
</head>
<body>
<%
	if(request.getAttribute("state") != null)
	{
	   %>
		   
		   <div class="updatesucesso">
			User deleted with success!
		   </div>
		   
		<% 
	}
	%>
	<div class="cinzento">
		<div class="noticias">
			<div class="latest">
				<div class="label"><label id="latest">Users' List</label></div>
				
				<div class="usertable">
					 <table class="usertable">
					  <tr>
					    <th>Name</th>
					    <th>Username</th>
					    <th>Email</th>
					    <th>Edit</th>
					    <th>Delete</th>
					  </tr>
					<%
						ArrayList<UserData> list = (ArrayList<UserData>) request.getAttribute("list");
						for(UserData user : list) 
						{%><tr>
							<td><%out.println(user.getName());%></td>
							<td><%out.println(user.getLogin_name());%></td>
							<td><%out.println(user.getEmail());%></td>
							<td id="iconn"><a id="logout" href="Register?operation=edit&loginid=<%out.println(user.getId());%>"><img id="icon" src="images/edit.png"></a></td>
							<td id="iconn"><a id="logout" href="Register?operation=delete&loginid=<%out.println(user.getId());%>"><img id="icon" src="images/delete.png"></a></td>
						</tr>
						<%}
					%>
					</table> 
				</div>
			</div>
		</div>
	</div>
</body>