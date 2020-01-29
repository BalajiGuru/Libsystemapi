
	function reg() {
		var image = document.getElementById("img1").files[0];
		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;

		formdata = new FormData();
		formdata.append("username", username)
		formdata.append("password", password)

		formdata.append("image", image)

		var xmlrequest = new XMLHttpRequest();
		xmlrequest.open("POST", "/register", true);
		xmlrequest.onreadystatechange = function() {

			document.getElementById("username").value = "";
			document.getElementById("password").value = "";
			document.getElementById("img1").value = "";
			if (xmlrequest.readyState == 4 && xmlrequest.status == 200) {

				result = JSON.parse(xmlrequest.responseText);
				alert(result.message);

			}

		}
		//xmlrequest.setRequestHeader("Content-type","application/json");

		xmlrequest.send(formdata);
	}
