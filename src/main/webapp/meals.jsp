<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>MealTo</title>
    <link href="resources/css/style.css" rel="stylesheet">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h3>Подсчет калорий</h3>
<hr>

<table>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <c:forEach var="meals" items="${list}">
        <tr style="color: ${meals.excess == true ? 'red' : 'grey'}">
         <td>${meals.dateTime}</td>
         <td>${meals.description}</td>
         <td>${meals.calories}</td>
        </tr>
    </c:forEach>
</table>


</body>
</html>
