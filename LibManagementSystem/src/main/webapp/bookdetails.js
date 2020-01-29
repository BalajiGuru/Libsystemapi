
	function add()
	{
		var bname=document.getElementById("bname").value;
		var author=document.getElementById("author").value;
		var pages=document.getElementById("pages").value;
		var publisher=document.getElementById("publisher").value;
		var isbn=document.getElementById("isbn").value;
		var g=document.getElementById("genre").value;
		var genre=g.split(',')
		var data={}
		data.bname=bname;
		data.author=author;
		data.pages=pages;
		data.publisher=publisher;
		data.isbn=isbn;
		data.genre=genre;
		
		pdata=document.getElementById("tablebody").innerHTML;
		
		var xmlrequest =new XMLHttpRequest();
		xmlrequest.open("POST","/add",true);
		xmlrequest.onreadystatechange=function(){
				
			if(xmlrequest.readyState==4 && xmlrequest.status==200)
				{

		    	  document.getElementById("bname").value="";
		    	  document.getElementById("author").value="";
		    	  document.getElementById("pages").value="";
		    	  document.getElementById("publisher").value="";
		    	  document.getElementById("isbn").value="";
		    	  document.getElementById("genre").value="";
		    	  
		    	  result=JSON.parse(xmlrequest.responseText);
					
		    		if(result.success)
		    			{
			    			pdata=pdata+"<tr><td>"+result.book.bname+"</td>"
			    	 		 	  +"<td>"+result.book.author+"</td>"
			    	 		      +"<td>"+result.book.pages+"</td>"
			    	  		      +"<td>"+result.book.publisher+"</td>"
			    	  		      +"<td>"+result.book.isbn+"</td>"
			    	  			  +"<td>"+result.book.genre+"</td></tr>";
			    			
			    			document.getElementById("tablebody").innerHTML=pdata;
		    			}
		    		else
		    			{
		    				alert(result.reason);
		    			}
			}
			
			
			
		}
		xmlrequest.setRequestHeader("Content-type","application/json");
		xmlrequest.send(JSON.stringify(data));
	}


function display()
	{
		pdata=document.getElementById("tablebody").innerHTML;
		var xmlrequest =new XMLHttpRequest();
		var book;
		xmlrequest.open("GET","/view",true);
		xmlrequest.onreadystatechange=function(){
				
			if(xmlrequest.readyState==4 && xmlrequest.status==200)
			{
		    	result=JSON.parse(xmlrequest.responseText);
				for(i=0, l=result.length;i<l;i++)
				{
					book = result[i];
		    
					pdata=pdata+"<tr><td>"+book.bname+"</td>"
		    			  +"<td>"+book.author+"</td>"
		    			  +"<td>"+book.pages+"</td>"
		    			  +"<td>"+book.publisher+"</td>"
		    			  +"<td>"+book.isbn+"</td>"
		    			  +"<td>"+book.genre+"</td></tr>";
					
		    	}
				document.getElementById("tablebody").innerHTML=pdata;
				
				
			}
				
		}
		xmlrequest.send();
	}
	
function search()
	{
		pdata=document.getElementById("tablebody").innerHTML;
		
		var searchby = document.getElementById("searchby").value;
		var searchtext = document.getElementById("searchtext").value;
		var xmlrequest = new XMLHttpRequest();
		var data={};
		data.searchby=searchby;
		data.searchtext=searchtext;
		
		
		xmlrequest.open("POST","/search",true);
		xmlrequest.onreadystatechange=function(){
			
			
			if(xmlrequest.readyState==4 && xmlrequest.status==200)
			{
			
				
			    result=JSON.parse(xmlrequest.responseText);
				console.log(result)

				if(result.length==0)
					{
						pdata="";
						alert("No Data found!!");
					}
				else
					{
					pdata="";
						
				    	for(i=0, l=result.length;i<l;i++)
				    	{
				    	
				    	
				    		book = result[i];
			    
				    		pdata=pdata+"<tr><td>"+book.bname+"</td>"
			    			  +"<td>"+book.author+"</td>"
			    			  +"<td>"+book.pages+"</td>"
			    			  +"<td>"+book.publisher+"</td>"
			    			  +"<td>"+book.isbn+"</td>"
			    			  +"<td>"+book.genre+"</td></tr>";
				    			
					
				    	}
				    		 
					}
				document.getElementById("tablebody").innerHTML=pdata;
		    	
			}
			
		}
		xmlrequest.setRequestHeader("Content-type","application/json");
		xmlrequest.send(JSON.stringify(data));

	}

  	
		