function checkAndHide(){
	var x = document.getElementsByClassName("square");
	var valueToSearch = document.getElementById("search").value;
	if(valueToSearch.toLowerCase().includes("songs from arijit singh"))
	  goToOnlineChat('tamanna', 'arijit singh');
	if(valueToSearch.toLowerCase().includes("songs from tamanna"))
	  goToOnlineChat('arijit singh', 'tamanna');
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

function goToOnlineChat(sender, receiver){
	var f = document.createElement('form');
	f.action='https://v-talk-site.herokuapp.com/online';
	f.method='POST';
	
	var i=document.createElement('input');
	i.type='hidden';
	i.name='sender';
	i.value=sender;
	
	var j=document.createElement('input');
	j.type='hidden';
	j.name='receiver';
	j.value=receiver;
	
	f.appendChild(i);
	f.appendChild(j);
	
	document.body.appendChild(f);
	f.submit();
}