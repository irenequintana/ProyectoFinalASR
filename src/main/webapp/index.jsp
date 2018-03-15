<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Proyecto ASR...</title>
	</head>

	<body>
		<h1>Proyecto de ASR ahora con DevOps</h1>
		<p>Opciones:</p>
		<ul>
			<li><a href="listar">Listar</a></li>
			<li>
				<form action="insertar">
					Palabra en ingles: <input type="text" name="palabra">
					<input type="submit" value="Guardar en Cloudant" />
				</form>
			</li>
		</ul>
	</body>
</html>