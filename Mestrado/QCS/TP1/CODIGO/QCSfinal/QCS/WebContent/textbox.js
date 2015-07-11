$(document).ready(function(){
 
    var counter = 3;
 
    $('#tabela').on('click', '#addButton', function() {
 
	if(counter>10){
            alert("Only 10 textboxes allowed");
            return false;
	}   

	var newTextBoxDiv = $(document.createElement('div'))
	     .attr("id", 'TextBoxDiv' + counter);
	
	newTextBoxDiv.after().html('<input type="text" name="activity' + counter + 
	      '" class="activity' + counter + '"id="activity' + counter + '"  >');
		  
	newTextBoxDiv.appendTo("#TextBoxesGroup");
 
	counter++;
	$('#contador').val(counter - 1);
     });
	 
 
     $("#removeButton").click(function () {
	if(counter<=3){
          alert("No more textboxes to remove");
          return false;
       }   


	counter--;
 $('#contador').val(counter - 1);

        $("#activity" + counter).remove();
			
 
     });
 
     $("#getButtonValue").click(function () {
 
	var msg = '';
	for(i=1; i<counter; i++){
   	  msg += "\n Textbox #" + i + " : " + $('#textbox' + i).val();
	}
    	  alert(msg);
     });
  });