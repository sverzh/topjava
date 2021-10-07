<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<button onclick="document.location='meals?action=add'">Add meal</button>
<br>
<table width="600" border="2" cellpadding="2" cellspacing="0">
    <tbody>
    <tr>
        <td width="200" align="center">Date</td>
        <td width="300" align="center">Description</td>
        <td width="100" align="center">Calories</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <c:forEach items="${listMealsTo}" var="mealTo">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color:
        <c:if test="${mealTo.excess}">red </c:if>
        <c:if test="${!mealTo.excess}">green </c:if>
                ">
            <td><%=TimeUtil.toFormatedDayTime(mealTo.getDate())%></td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td><a href="meals?id=${mealTo.id}&action=update">Update</a></td>
            <td><a href="meals?id=${mealTo.id}&action=delete">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
