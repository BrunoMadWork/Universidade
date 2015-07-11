$(document).ready(function(){
 
    var counter2 = 3;
 
    $('#tabela').on('click', '#addButton2', function() {
 
	if(counter2>10){
            alert("Only 10 textboxes allowed");
            return false;
	}   

	
	var newTextBoxDiv2 = $(document.createElement('div'))
	     .attr("id", 'TextBoxDiv2' + counter2);
	
	newTextBoxDiv2.after().html('<input type="text" name="blood' + counter2 + 
	      '" class="blood' + counter2 + '"id="blood' + counter2 + '"  >');
		  
	newTextBoxDiv2.appendTo("#TextBoxesGroup2");
 
	counter2++;
	$('#contador2').val(counter2 - 1);
     });
	 
 
     $("#removeButton2").click(function () {
	if(counter2<=3){
          alert("No more textboxes to remove");
          return false;
       }   


	counter2--;
 $('#contador2').val(counter2 - 1);

        $("#blood" + counter2).remove();
			
 
     });
 
     $("#getButtonValue").click(function () {
 
	var msg = '';
	for(i=1; i<counter; i++){
   	  msg += "\n Textbox #" + i + " : " + $('#textbox' + i).val();
	}
    	  alert(msg);
     });
  });