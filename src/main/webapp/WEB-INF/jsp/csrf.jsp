<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>

<head>
	<title>Admin</title>
</head>

<body>
	Home page
	<br>
	<center>
		<h1>With CSRF token</h1>
		<form action="csrfSubmit" method="post">
			Enter Name: <input type="name" />
			<sec:csrfInput /> <!-- remove will not allow to send request, as default csrf is enable-->
			<input type="submit" />
		</form>


		<hr>
		<h1>Without CSRF token</h1>
		<form action="csrfSubmit" method="post">
			Enter Name: <input type="name" />
			<input type="submit" />
		</form>
	</center>
</body>

</html>