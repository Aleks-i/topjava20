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

    <!--table with meal-->
<table>
    <tr>
        <th>ID</th>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="mealTo" items="${list}">
         <tr style="color: ${mealTo.excess == true ? 'red' : 'green'}">
         <td>${mealTo.id}</td>
         <td><javatime:format pattern="yyyy-MM-dd HH:mm" value="${meal.dateTime}"/></td>
         <td>${mealTo.description}</td>
         <td>${mealTo.calories}</td>
         <td><a href="${pageContext.request.contextPath}/updatemeal.jsp"><button>edit</button></a ></td>
         <td><a href="${pageContext.request.contextPath}/meals?action=delete&id=${mealTo.id}">delete</a></td>
        </tr>
    </c:forEach>
</table>
<p>

    <!--method post to add-->
<h3>Добавить прием пищи</h3>
<form method="post">
    <table>
        <tr>
        <td><h3>Дата</h3></td>
        <td><input type="text" name="date" ></td>
        <td><h3>Описание</h3></td>
        <td><input type="text" name="meal"></td>
        <td><h3>Калории</h3></td>
        <td><input type="number" name="calories"></td>
        <td><input type="submit" value="Добавить прием пищи"></td>
       </tr>
    </table>
</form>
</body>
</html>
