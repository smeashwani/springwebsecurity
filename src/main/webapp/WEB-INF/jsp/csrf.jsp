<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title>Admin</title>
</head>
<body>
Home page 
<br>
	<form action="csrfSubmit" method="post">
		Enter Name: <input type="name" />
		<sec:csrfInput /> 
		<input type="submit" />
	</form>
</body>
</body>
</html>