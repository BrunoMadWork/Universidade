<html>
<head>
	<title>Insulin Calculator</title>
	<link rel="stylesheet" type="text/css" href="style.css">
	<script src="jquery.min.js"></script>
	<script src="functions_standard.js"></script>
	
	<script type="text/javascript">
		function newPopup(url)
		{
			popupWindow = window.open(url,'popUpWindow','height=300,width=380,left=400,top=200,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes')
		}
	</script>
</head>

<body>
	<div class="container">
			<div class="titulo">
				Insulin Calculator
			</div>
			
			<div class="escolhido">
				Meal time insulin dose - Standard insulin sensitivity
			</div>
			
			<%
			
				if (request.getAttribute("resultado") != null)
				{
					Integer valor = (Integer) request.getAttribute("resultado");
					String informacao = (String) request.getAttribute("informacao");
					
					%>
					<div class="resultado">
					<%
					if(valor < 0)
					{
						out.println("No satisfactory result!");
					}
					else
					{
						out.println("Result: " + valor + " units of insulin <p>");
					}
					
					
					%>
					<a href="javascript:document.location.reload();"
ONMOUSEOVER="window.status='Refresh'; return true"
ONMOUSEOUT="window.status='ah... that was good'">
<img src="imagens/refresh.gif" 
width="115" height="31" border="0" /></a>
					<a href="Javascript:newPopup('Diabetes?operation=technical&information=<%out.println(informacao);%>');">Show technical information</a>
					</div>
					<%
					
				}
				else
				{
					%>
					
					<div class="text">
				Please fill in the fields below.
			</div>
			
			<div class="tabela">
			
				<form id="formulario" name="formulario" action="${pageContext.request.contextPath}/Diabetes">
					<table class="standard">
						<tr>
							<td id="descricao">Total grams of carbohydrates in the meal <span class="medidas">(between 60g-120g)</span></td>
							<td><input type="text" name="field1" id="field1"><span class="medidas">g</span></td> 
						</tr>
						<tr>
							<td id="descricao">Total grams of carbohydrates processed by 1 unit of rapid acting insulin <span class="medidas">(between 10g/unit-15g/unit)</span></td>
							<td><input type="text" name="field2" id="field2" value="12"><span class="medidas">g/unit</span></td>
						</tr>
						<tr>
							<td id="descricao">Actual blood sugar level measured before the meal <span class="medidas">(between 120mg/dl -250mg/dl)</span></td>
							<td><input type="text" name="field3" id="field3"><span class="medidas">mg/dl</span></td> 
						</tr>
						<tr>
							<td id="descricao">Target blood sugar before the meal <span class="medidas">(between 80mg/dl -120mg/dl)</span></td>
							<td><input type="text" name="field4" id="field4"><span class="medidas">mg/dl</span></td>
						</tr>
						<tr>
							<td id="descricao">Individual sensitivity <span class="medidas">(between 15mg/dl-100mg/dl)</span></td>
							<td><input type="text" name="field5" id="field5" value="50"><span class="medidas">mg/dl</span></td>
						</tr>
						<tr>
							<td id="descricao"></td>
							<td>
							<input type="hidden" value="standard" name="operation">
							<input id="submit" type="submit" name="submit" value="Calculate insuline dose"> </td>
						</tr>
					</table>
				</form>
			</div>
					
					<%
				}
			%>
			
			
			<a href="index.jsp"><img id="back" src="imagens/back.png"></a>
	</div>
</body>
</html>