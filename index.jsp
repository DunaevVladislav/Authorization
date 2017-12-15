<%@ page import="users.MyCookie" %>
<%@ page import="users.User" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%
    MyCookie cookie = new MyCookie(request, response);
    User user = null;
    try {
        user = cookie.getUser();
    } catch (Exception ignored){}
%>
<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" TYPE="text/css" HREF="css/index.css">
        <title>Авторизация</title>
    </head>
    <body>
    <% if (user != null){%>
        <p>Привет <%=user.getLogin()%></p>
        <a href="logout">Выход</a>
    <%} else { %>
    <form action='<%=request.getRequestURL()%>login' method="post">
            <p>Логин</p>
            <input type="text" name="login"><br/>
            <p>Пароль</p>
            <input type="password" name="password"><br/>
            <text>Запомнить </text>
            <input type="checkbox" name="remember"><br/>
            <p><input type="submit" value="Отправить"></p></br>
            <a href="registration.jsp">Регистрация</a>
        </form>
    <% } %>
    </body>
</html>