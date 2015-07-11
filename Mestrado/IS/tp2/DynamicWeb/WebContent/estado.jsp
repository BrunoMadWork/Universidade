<%
   if("sucesso".equals(request.getParameter("estado"))){
	   String nome = request.getParameter("name");
	%>
<html>

<head>
	<title> CNN News </title>
	<link rel="stylesheet" type="text/css" href="style.css"></link>
	<script>
		setTimeout("self.close()", 5000 ) 
	</script>
</head>

<body class="page_register">

        <div class="estado">
				Obrigado por se registar <% out.println(nome); %>. <br>
				Poderá agora efectuar login. <p>
				Esta página irá fechar-se dentro de 5 segundos.
		</div>

</body>
</html>
<%
    } else if("insucesso".equals(request.getParameter("estado"))){%>
<html>

<head>
	<title> CNN News </title>
	<link rel="stylesheet" type="text/css" href="style.css"></link>
	<meta http-equiv="refresh" content="5;url=register.jsp" />
</head>

<body class="page_register">

        <div class="estado">
				Username escolhido já existe. <p>
				Será redereccionado para a página de registo dentro de 5 segundos.
		</div>

</body>
</html>
<%
    }
%>

