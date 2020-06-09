<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>

<table>
    <c:forEach var="meals" items="${list}">
        <tr>
         <td>${meals.dateTime}</td>
         <td>${meals.description}</td>
         <td>${meals.calories}</td>
        </tr>
    </c:forEach>
</table>


</body>
</html>
