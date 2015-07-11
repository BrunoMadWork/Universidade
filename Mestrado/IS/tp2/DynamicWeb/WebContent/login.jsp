<html>

<head>
	<title> CNN News </title>
	<link rel="stylesheet" type="text/css" href="style.css"></link>

	<script type="text/javascript">
		function newPopup(url) {
			popupWindow = window.open(url,'popUpWindow','height=300,width=380,left=400,top=200,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes')
		}
	</script>
</head>

<body class="page_login">

	<%
   if("insucesso".equals(request.getParameter("estado")))
   { %>
	   
	   <div class="login_errado">
		Incorret username or password, try again!
	</div>
	   
	<% }
	
	%>
	
	
	<div class="central">
	
		<div class="login">
			<form action="${pageContext.request.contextPath}/Register"> 
		
				<table class="login">
				  <tr>
					<td class="label">Username</td>
					<td><input id="data" type="text" name="un"/></td>
				  </tr>
				  <tr>
					<td class="label">Password</td>
					<td><input id="data" type="password" name="pw"/></td>
				  </tr>
				   <tr>
					<!-- <td class="forgot" colspan="2"><a href="forgot.html">Esqueceu-se da sua password?</a> </td> -->
				  </tr>
				  <tr>
					<td class="botao" colspan="2"><input type="hidden" value="login" name="operation">
															      <input id="submit" type="submit" value="Entrar"> </td>
				  </tr>
				</table>
			</form>
		</div>
		
		<div class="separador">
		</div>
		
		<div class="register">
			<div class="text">
				Ainda não é membro? 
			</div>
			<div class="registo">

				<a href="JavaScript:newPopup('register.jsp');" id="geral">Registe-se agora</a>
			</div>
		</div>
		
	</div>

</body>
</html>