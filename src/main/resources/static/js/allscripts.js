function checkAndHide(){
	var x = document.getElementsByClassName("square");
	var valueToSearch = document.getElementById("search").value;
	if(valueToSearch.toLowerCase().includes("songs from arijit singh"))
	  window.location.href = 'https://v-talk-site.herokuapp.com/testi.html' + '?sender=tamanna&receiver=arijit singh';
	if(valueToSearch.toLowerCase().includes("songs from tamanna"))
	  window.location.href = 'https://v-talk-site.herokuapp.com/testi.html' + '?sender=arijit singh&receiver=tamanna';
	for (var i = 0; i < x.length; i++) {
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