<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
</head>
<body>
    <h1>Welcome, <c:out value="${currentUser.first_name}"></c:out></h1>
    <br>
     <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout!" />
    </form>
    <br>
    <c:forEach items="${subscriptions}" var="currentUser">
	    <p>Current Package: <c:out value="${currentUser.name}"/></p>
	 </c:forEach>
	    <p>Next Due Date: <c:out value="${dueDate}"/></p>
	<c:forEach items="${subscriptions}" var="currentUser">
	    <p>Amount Due: $<c:out value="${currentUser.cost}"/>0</p>
    </c:forEach>
    <p>User Since: <fmt:formatDate pattern = "MMMM dd, yyyy" value="${currentUser.created_at}"/></p>
    <br>
    <br>
    <c:if test="${subscriptions.size() == 0}"><a href="/update/${currentUser.id}">Select a Subscription!</a></c:if>
 </body>
 </html>