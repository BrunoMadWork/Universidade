

 $(document).ready(function() {
 $('input[type="submit"]').attr('disabled','disabled');
$('#tabela').on('keyup', 'input[type="text"]', function(){





var field1 = $("#field1").val();
var field2 = $("#field2").val();
var field3 = $("#field3").val();
var field4 = $("#field4").val();
var field5 = $("#field5").val();

	if((field1 >= 60 && field1 <= 120) && (field2 >= 10 && field2 <= 15) && (field3 >= 120 && field3 <= 250) && (field4 >= 80 && field4 <= 120) && (field5 >= 0 && field5 <= 10) && !(field1 == "" || field2 == "" || field3 == "" || field4 == "" || field5 == ""))
	{
		var check=0;
		var permissao=0;
		var totalactivity = $("#contador").val();
		for(var i = 1; i <= totalactivity ; i++)
		{
			var texto = "#activity" + i;
			var valor = $(texto).val();
			if(!(valor == "") && valor >= 0 && valor <=10)
			{
				if(i == totalactivity)
				{
				
				
				
				
				
				
				var totalactivity2 = $("#contador2").val();
		for(var j = 1; j <= totalactivity2 ; j++)
		{
			var texto2 = "#blood" + j;
			var valor2 = $(texto2).val();
			if(!(valor2 == "") && valor2 >= 15 && valor2 <=100)
			{
				if(j == totalactivity2)
				{
					$('input[type="submit"]').removeAttr('disabled');
				}
			}
			else
			{	$('input[type="submit"]').attr('disabled','disabled');
				return false;	 
			}
			
			
		}
				
				

				}
			}
			else
			{	$('input[type="submit"]').attr('disabled','disabled');
				return false;	 
			}
			
			
		}
		
	}
	else
	{
		$('input[type="submit"]').attr('disabled','disabled');
	}

});

});