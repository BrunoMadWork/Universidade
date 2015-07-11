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
			User updated with success!
		   </div>
		   
		<% 
	}
	%>

	<div class="cinzento">
		<div class="noticias">
			<div class="latest">
				<a href="Register?operation=listusers"><img id="back" src="images/back.png"></a>
				<div class="label"><label id="latest">Edit User</label></div>
				
				<div class="usertable">
				
					<form action="${pageContext.request.contextPath}/Register?">
						 <table class="user">
						<%
							ArrayList<UserData> list = (ArrayList<UserData>) request.getAttribute("list");
							for(UserData user : list) 
							{
							long loginid=user.getId();%>
							<input type="hidden" value="<%= loginid%>" name="loginid">
								<tr>
									<td id="label">Name</td><td><input id="useredit" value="<%out.println(user.getName()); %>" id="data2" type="text" name="na"/></td>
								</tr>
								<tr>
									<td id="label">Username</td><td><input value="<%out.println(user.getLogin_name()); %>" id="data2" type="text" name="un"/></td>
								</tr>
								<tr>
									<td id="label">Email</td><td><input id="useredit" value="<%out.println(user.getEmail()); %>" id="data2" type="text" name="em"/></td>
								</tr>
								<tr>
									<td id="label">New password</td><td><input  id="data2" type="password" name="pw"/>*</td>
								</tr>
								
							<%}
						%>
						</table> 
						<input type="hidden" value="updateuser" name="operation">
						<div class="botaouser">
							<input id="submitt" type="submit" value="Confirm"><br>
						</div>
						<div class="blank">
						* leave it blank if you don't want to change it
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>