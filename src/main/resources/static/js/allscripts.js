function checkAndHide(){
	var x = document.getElementsByClassName("square");
	var valueToSearch = document.getElementById("search").value;
	var i;
	for (i = 0; i < x.length; i++) {
	  var valueInBox = x[i].textContent;
	  if(!valueInBox.toLowerCase().includes(valueToSearch.toLowerCase())){
		  x[i].style.display = "none";
	  }else{
		  x[i].style.display = "inline";
	  }
	}
}

function clearSearchBox(){
	document.getElementById("search").value = "";
}