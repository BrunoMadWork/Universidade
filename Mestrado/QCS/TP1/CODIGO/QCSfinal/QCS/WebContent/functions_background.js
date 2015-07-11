
 $(document).ready(function() {
 $('input[type="submit"]').attr('disabled','disabled');
$('input[type="text"]').keyup(function() {

var field1 = $("#field1").val();

	if((field1 >= 40 && field1 <= 130) && !(field1 == "" ))
	{
		$('input[type="submit"]').removeAttr('disabled');
	}
	else
	{
		$('input[type="submit"]').attr('disabled','disabled');
	}

});

});