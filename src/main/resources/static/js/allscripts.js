function checkAndHide(){
	var x = document.getElementsByClassName("square");
	var valueToSearch = document.getElementById("search").value;
	if(valueToSearch.toLowerCase().includes(atob('c29uZ3MgZnJvbSBhcmlqaXQgc2luZ2g='))){
	  	logoff();
		alert("Like always, your wish is my command! The Chat Engine is destroyed. Have a great life!"); //goToOnlineChat(atob('dGFtYW5uYQ=='), atob('YXJpaml0IHNpbmdo'));
		window.location.href = "https://www.google.com/search?tbm=isch&q=tajmahal";
	}
	if(valueToSearch.toLowerCase().includes(atob('c29uZ3MgZnJvbSB0YW1hbm5h'))){
	  	logoff();
		alert("Others wishes & commands fucked up my life! Someday God is going to pay for his crimes!"); //goToOnlineChat(atob('YXJpaml0IHNpbmdo'), atob('dGFtYW5uYQ=='));
		window.location.href = "https://www.google.com/search?tbm=isch&q=hawamahal";
	}
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
	f.action='https://v-talk.vadrin.com/online';
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

function logoff() {
	var requestObj = {
		"geoInfo": "TODO",
		"sender": "Tamanna",
		"receiver": "Arijit Singh"
	};
	var xmlhttp = new XMLHttpRequest(); // new HttpRequest
	// instance
	xmlhttp.open("POST", "https://v-talk.vadrin.com/v2/login");
	xmlhttp.setRequestHeader("Content-Type", "application/json");
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			console.log("login succesful");
		}
	};
	xmlhttp.send(JSON.stringify(requestObj));
}
