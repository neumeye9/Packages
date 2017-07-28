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
    <h1>Welcome to Dojoscriptions <c:out value="${currentUser.first_name}"></c:out></h1>
    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout" />
    </form>
    <br>
    <h2>Please choose a subscription and start date</h2>
    <form method="post" action="/chooseSubscription/${currentUser.id}">
    	<p>Due Date:
    		<select id="due_date" name="due_date">
    			<c:forEach  begin="1" end="31" varStatus="loop" var="count">
    				<option value="${loop.count}"><c:out value="${count}"/></option>
    			</c:forEach>
    		</select>
    	</p>
    	<p>Package:
    		<select name="subscription">
    			<c:forEach items="${subscriptions}" var="subscription">
    				<option value="${subscription.id}">${subscription.name} ($${subscription.cost}0)</option>
    			</c:forEach>
    		</select>	
    	</p>
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
   	<input type="submit" value="Sign Up!"/>
    	</form>
    	
    		
    
 
 
 </body>
 </html>
 