<html>
<head>
<title>Technical Information</title>
<body>
	<% 
	
	
	if (request.getAttribute("informacao") != null)
	{
		String informacao = (String) request.getAttribute("informacao");
		out.println(informacao);
	}
	%>
</body>
</head>
</html>