/**
 * 
 */
$(document).ready(function () {
	$('.btn-block').on('click',function(event) {
		event.preventDefault();
			var href = $(this).attr('href');

var table = document.getElementById("table"),rIndex,cIndex;
for(i=0; i<table.rows.length; i++)

	for (j=0;j<table.rows[i].cells.length; j++)
	{
		table.rows[i].cells[j].onclick = function()
		{
			rIndex = this.parentElement.rowIndex;
			var temp = table.rows[rIndex].cells[1].innerHTML;
			var divmain = document.getElementById("main");
			divmain.innerHTML = "do you want to delete the " + table.rows[rIndex].cells[1].innerHTML + " record ?";
		}
	}

		$('#deleteModal #delRef').attr('href',href);
		$('#deleteModal').modal();
		
		
	});
	
	});
	


