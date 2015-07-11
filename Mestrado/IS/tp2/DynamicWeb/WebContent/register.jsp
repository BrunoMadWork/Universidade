<html>

<head>
	<title> CNN News </title>
	<link rel="stylesheet" type="text/css" href="style.css"></link>
	
	<script type="text/javascript">
		function validateForm() {
			var user = document.forms["register"]["un"].value;
			var pass = document.forms["register"]["pw"].value;
			var name = document.forms["register"]["na"].value;
			var email = document.forms["register"]["em"].value;
			var x = document.forms["register"]["em"].value;
			var atpos = x.indexOf("@");
			var dotpos = x.lastIndexOf(".");
			
			if (user == null || user == "" || pass == null || pass == "" || name == null || name == "" || email == null || email == "") {
				alert("Tem que preencher todos os campos!");
				return false;
			}
			else  if (atpos< 1 || dotpos<atpos+2 || dotpos+2>=x.length) {
				 alert("O email não é válido.");
				return false;
			}
		}
	</script>
</head>

<body class="page_register">

	<div class="register_title">
		Formulário de registo
	</div>
	
	<div class="info">
		Preencha todos os campos abaixo para efetuar o registo!
	</div>
	
	<form name="register" action="${pageContext.request.contextPath}/Register" onsubmit="return validateForm()"> 
		<table class="login">
					  <tr>
						<td class="label">Username</td>
						<td><input id="data2" type="text" name="un"/></td>
					  </tr>
					  <tr>
						<td class="label">Password</td>
						<td><input id="data2" type="password" name="pw"/></td>
					  </tr>
						<tr>
						<td class="label">Nome</td>
						<td><input id="data3" type="text" name="na"/></td>
					  </tr>
					  <tr>
						<td class="label">E-mail</td>
						<td><input id="data3" type="text" name="em"/></td>
					  </tr>
					  <tr>
						<td class="botao" colspan="2"><input type="hidden" value="registo" name="operation">
																	  <input id="submit" type="submit" value="Registar">
																	  <input id="cancelar" type="submit" value="Cancelar" onclick="self.close()"> 
						</td>
					  </tr>
		</table>
	</form>

</body>
</html>