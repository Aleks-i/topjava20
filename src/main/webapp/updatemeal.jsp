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
<h3>Изменение еды по ID</h3>
<hr>

<form method="post">
    <table>
        <tr>
            <td><h3>Дата</h3></td>
            <td><input type="text" name="date"></td>
            <td><h3>Описание</h3></td>
            <td><input type="text" name="mealTo"></td>
            <td><h3>Калории</h3></td>
            <td><input type="number" name="calories"></td>
            <td><a href="${pageContext.request.contextPath}/meals?action=edit&id=${mealTo.id}">edit</a></td>
            <!--<td><input type="submit" value="Обновить"></td>-->
       </tr>
    </table>
</form>

</body>
</html>
