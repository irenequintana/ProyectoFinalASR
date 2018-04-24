<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Listar</title>
		<style>
			body  {
    			background: lightblue;
			}
			table {
    			table-layout: auto;
    			width: 180px;
    			text-align: center;
			}
		</style>
	</head>
	
	<body>
	<div align=center>
		<table>
  			<tr>
    			<th>Español</th>
   				<th>Francés</th>
 			</tr>
 			<c:forEach items="${requestScope.palabras}" var="entry">
 				<tr>
   						<td>${entry.key}</td>
        				<td>${entry.value}</td>
				</tr>
  			</c:forEach>
  			</table>
  			</div>
	</body>
</html>