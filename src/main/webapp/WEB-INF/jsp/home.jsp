<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title>Admin</title>
</head>
<body>
Home page 
<br>
	<h2>Welcome to spring security</h2>
	<sec:authorize access="isAuthenticated()"> 
		Hello <sec:authentication property="principal.username" />
		<a href="<c:url value='/logout' />">Click here to logout</a>
	</sec:authorize>
	
	<sec:authorize access="!isAuthenticated()"> 
		<a href="admin">Admin page</a>
	</sec:authorize>
	
	<sec:authorize access="hasRole('ADMIN')">  
		It will display only to Admin Users  
	</sec:authorize>
</body>
</body>
</html>