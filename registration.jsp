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
    <title>Регистрация</title>
</head>
<body>
<% if (user != null){
    response.sendRedirect(request.getRequestURL() + "/..");
} else { %>
<form action='<%=request.getRequestURL()%>/../addUser' method="post">
    <p>Логин</p>
    <input type="text" name="login"><br/>
    <p>Почта</p>
    <input type="text" name="email"><br/>
    <p>Пароль</p>
    <input type="password" name="password"><br/>
    <p>Повторите пароль</p>
    <input type="password" name="repPassword"><br/>
    <p><input type="submit" value="Зарегистрироваться"></p>
</form>
<% } %>
</body>
</html>