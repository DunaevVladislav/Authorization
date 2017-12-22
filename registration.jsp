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
    <meta charset="utf-8" name="viewport" content="user-scalable=no, width=device-width, height=device-height"/>
    <link rel="stylesheet" TYPE="text/css" HREF='<%= request.getRequestURL()%>/../css/index.css'>
    <link rel="stylesheet" TYPE="text/css" HREF='<%= request.getRequestURL()%>/../css/Form.css'>
    <script type="text/javascript" src='<%= request.getRequestURL()%>/../js/jquery-3.2.1.min.js'></script>
    <script type="text/javascript" src='<%= request.getRequestURL()%>/../js/submitForm.js'></script>
    <script type="text/javascript" src='<%= request.getRequestURL()%>/../js/crypt.js'></script>
    <script type="text/javascript" src='<%= request.getRequestURL()%>/../js/registration.js'></script>
    <title>Регистрация</title>
</head>
<body>
<% if (user != null){
    response.sendRedirect(request.getRequestURL() + "/..");
} else { %>
<div id="helloDiv">
    <p>
        Добро пожаловать на сайт электронной библиотеки
    </p>
</div>
<div id="divForm">
    <div id="nameForm">Регистрация</div>
    <form action='<%=request.getRequestURL()%>/../addUser' method="post">
        <input type="text" name="login" id="login" placeholder="Логин"><br/>
        <input type="text" name="email" id="email" placeholder="Email"><br/>
        <input type="password" name="password" id="password" placeholder="Пароль"><br/>
        <input type="password" name="repPassword" id="repPassword" placeholder="Повторите пароль"><br/>
        <p><input type="button" id = "submitForm" value="Зарегистрироваться"></p></br>
        <a href='<%=request.getRequestURL() + "/.."%>'><span>Войти</span></a>
    </form>
</div>
<% } %>
</body>
</html>