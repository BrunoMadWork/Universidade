
 $(document).ready(function() {
 $('input[type="submit"]').attr('disabled','disabled');
$('input[type="text"]').keyup(function() {
var field1 = $("#field1").val();
var field2 = $("#field2").val();
var field3 = $("#field3").val();
var field4 = $("#field4").val();
var field5 = $("#field5").val();


	if((field1 >= 60 && field1 <= 120) && (field2 >= 10 && field2 <= 15) && (field3 >= 120 && field3 <= 250) && (field4 >= 80 && field4 <= 120) && (field5 >= 15 && field5 <= 100) && !(field1 == "" || field2 == "" || field3 == "" || field4 == "" || field5 == ""))
	{
		$('input[type="submit"]').removeAttr('disabled');
	}
	else
	{
		$('input[type="submit"]').attr('disabled','disabled');
	}

});

});