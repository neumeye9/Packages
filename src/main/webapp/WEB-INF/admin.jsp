<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
</head>
<body>
    <h1>Admin Dashboard</h1>
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout" />
    </form>
   	<br>
   	<h2>Customers</h2>
   	<br>
   	<table border=1>
   		<tr>
   			<th>Name</th>
   			<th> Next Due Date</th>
   			<th>Amount Due</th>
   			<th>Package Type</th>
   		</tr>
   		
   		<c:forEach items="${users}" var="user">
   		<tr>
   			<td><c:out value="${user.first_name}"/> <c:out value="${user.last_name}"/></td>
   				<td><c:out value="${dueDate}"/></td>
   			<c:forEach items="${user.subscriptions}" var="subscription">
   				<td>$<c:out value="${subscription.cost}"/>0</td>
   				<td><c:out value="${subscription.name}"/></td>
   			</c:forEach>
   		</tr>
   		</c:forEach>
   		
   	</table>
   	<h2>Packages</h2>
   	<br>
   	<table border=1>
   		<tr>
   			<th>Package Name</th>
   			<th>Package Cost</th>
   			<th>Available</th>
   			<th>Users</th>
   			<th>Actions</th>
   		<tr>
   		<c:forEach items="${subscriptions}" var="subscription">
   		<tr>
   			<td><c:out value="${subscription.name}"/></td>
   			<td>$<c:out value="${subscription.cost}"/>0</td>
   			<td>
   				<c:if test="${subscription.isAvailable() == true}">Unavailable</c:if>
	   			<c:if test ="${subscription.isAvailable() == false}">Available</c:if> 
   			</td>
   			<td>${subscription.count}</td>
   			<td><c:if test="${subscription.isAvailable() == false}"><a href="/admin/${admin.id}/deactivate/${subscription.id}">Deactivate</a></c:if>
	   			<c:if test = "${subscription.isAvailable() == true}"><a href ="/admin/${admin.id}/activate/${subscription.id}">Activate</a></c:if> 
	  			<c:if test="${subscription.getCount() == 0}"><a href="/admin/${admin.id}/delete/${subscription.id}">Delete</a></c:if>
  			</td>
   		</tr>
   		</c:forEach>
   	</table>
   	<br>
   	<h3>Create Package</h3>
   	<br>
   	<form:form method = "post" action="/admin/${admin.id}/makeSubscription" modelAttribute="subscription">
   		<p>
   			<label for="name"> Package Name</label>
   			<input type="text" id="name" name="name"/>
   		</p>
   		<p>
   			<label for="cost"> Cost </label>
   			<input type="text" id="cost" name="cost"/>
   		</p>
   		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Create"/>
    </form:form>
   		
</body>
</html>
