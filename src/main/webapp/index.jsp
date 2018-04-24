<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Proyecto Final ASR...</title>
		<style>
			body  {
    			background: lightblue url("http://public.mercado.com.ar/imagenes/0000050696.jpg") no-repeat fixed center;
			}
		</style>
	</head>

	<body>
		<h1>¡Viaja a Francia y hazte entender con este diccionario instanténeo!</h1>
		<p>Graba la palabra que quieras traducir:</p>
		<ul>
			<li>
				Traducir esta palabra:
				<button onclick="javascript:miFuncion()">Grabar</button>
			</li>
			<li><a href="listar">Consultar las palabras ya buscadas</a></li>			
		</ul>
		<script>  
		function miFuncion(){
  			  			
      		var extension = "";
      		if(MediaRecorder.isTypeSupported("audio/webm"))
      			extension = "webm";
      		if(MediaRecorder.isTypeSupported("audio/ogg"))
      			extension = "ogg";
      		navigator.mediaDevices.getUserMedia({ audio: true }) 
				.then(stream => { 
					const mediaRecorder = new MediaRecorder(stream); 
					mediaRecorder.start(); 
					
					const audioChunks = [];
					
					mediaRecorder.addEventListener("dataavailable", event => { 
						audioChunks.push(event.data); 
					});
					
					mediaRecorder.addEventListener("stop", () => { 
						const audioBlob = new Blob(audioChunks);
						/*const audioUrl = URL.createObjectURL(audioBlob); 
						const audio = new Audio(audioUrl); 
						audio.play();*/
 
						
						var form = new FormData(); 
			  			  form.append('audio', audioBlob, 'filename.'+extension); 
			  			  var xhr = new XMLHttpRequest(); 
			  			  xhr.onreadystatechange = function() 
			  			  { 
				  			  if (xhr.readyState == 4 && xhr.status == 200) 
				  			  { 
				  				/*var audio = document.createElement('audio');
				  				audio.src = xhr.responseText;
				  				audio.play();*/
				  				document.location = xhr.responseText;
				  				(new Audio(document.location.href+xhr.responseText)).play();
				  				//alert(document.location.href+xhr.responseText);
				  			  } 
			  			  }; 
			  			  xhr.open("POST", "translate"); 
			  			  xhr.send(form); 
					}); 
					
					setTimeout(() => { 
						mediaRecorder.stop(); 
					}, 3000); 


				}); 
		}
		</script>
	</body>
</html>