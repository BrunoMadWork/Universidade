<%@ include file="header.jsp" %>
<%@ page import="java.util.ArrayList" %> 
<%@ page import="toDatabase.DbNews" %>

<html>

<head>
	<title> CNN News </title>
	<link rel="stylesheet" type="text/css" href="style.css"></link>
	 <link href="jquery/jquery-ui.css" rel="stylesheet" type="text/css"/>
  <script type="text/javascript" src="jquery/jquery.min.js"></script>
  <script src="jquery/jquery-ui.min.js"></script>
  
  <script>
  $(document).ready(function() {
    $("#datepicker").datepicker();
  });
  </script>
  
  <script>
  var $inputs = $('input.st');
  $('select.type').change(function(){
    $inputs.prop('disabled', $(this).val() == 'any');
  });
  </script>
</head>
<body>
	<div class="cinzento">
		<div class="noticias">
			<div class="latest">
				
					<div class="label">
					<label id="searchresult">Advanced Search</label><p>
					</div>										
					
					<form name="advancedsearch" action="${pageContext.request.contextPath}/Register"> 
					<label id="select">Search by </label><select class="type" id="type" name="type">
						  <option value="any">Any</option>	
						  <option value="high">Highlights</option>
						  <option value="author">Author</option>
					</select>
					<label id="date">Date </label><input id="datepicker" name="datepicker" />
						<input type="radio" name="date" id="date" value="exact" > <label id="select">Exact date </label>
						<input type="radio" name="date" id="date" value="morerec" > <label id="select">More recent than</label>
						<input type="radio" name="date" id="date" value="older" checked> <label id="select">Older than </label><p>
					<input class="st" id="text_advsearch" type="text" name="st"/>
					<input type="hidden" name="operation" value="advancedsearch">
					<input id="procurar" type="submit" value="Search">
				</form>
			 
			 	<div class="searchauthor">
			 	
			 	<% ArrayList<String> list = (ArrayList<String>)request.getAttribute("list"); %>
			 	
			 		<form name="advancedsearchauthor" action="${pageContext.request.contextPath}/Register">
			 			<label id="select">Search by author</label><select class="type" id="type" name="author">
			 			
			 			<%
			 				for(String autor : list)
			 				{
			 					%><option value="<%= autor %>"><%= autor %></option><%
			 				}
			 			%>
						</select>
						<input type="hidden" name="operation" value="searchauthor">
						<input id="procurar" type="submit" value="Search">
					</form>
			 	</div>
			</div>
		</div>
	</div>
</body>