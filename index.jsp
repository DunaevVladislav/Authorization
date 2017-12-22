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
        <link rel="stylesheet" TYPE="text/css" href='<%= request.getRequestURL()%>css/index.css'>
        <link rel="stylesheet" TYPE="text/css" href='<%= request.getRequestURL()%>css/Form.css'>
        <script type="text/javascript" src='<%= request.getRequestURL()%>js/jquery-3.2.1.min.js'></script>
        <script type="text/javascript" src='<%= request.getRequestURL()%>js/submitForm.js'></script>
        <script type="text/javascript" src='<%= request.getRequestURL()%>js/crypt.js'></script>
        <script type="text/javascript" src='<%= request.getRequestURL()%>js/authorization.js'></script>
        <title>Авторизация</title>
    </head>
    <body>
    <% if (user != null){%>

        <div id="helloDiv">
            <p>
                <%=user.getLogin()%>, добро пожаловать на сайт электронной библиотеки
            </p>
            <p>
                Вы зашли на наш сайт: <%=user.getVisits().toString()%> раз(а)
            </p>
            <img src='<%= request.getRequestURL()%>img/book.png'/>
        </div>
        <div id="exit">
            <a href='<%= request.getRequestURL()%>logout'>Выход</a>
        </div>
    <%} else { %>
    <div id="helloDiv">
        <p>
           Добро пожаловать на сайт электронной библиотеки
        </p>
    </div>
    <div id="divForm">
        <div id="nameForm">Авторизация</div>
        <form action='<%=request.getRequestURL()%>login' method="post">
            <input type="text" name="login" id="login" placeholder="Логин"><br/>
            <input type="password" name="password" id="password" placeholder="Пароль"><br/>
            <input type="checkbox" name="remember" id="remember"><span>Запомнить меня</span><br>
            <p><input type="button" id = "submitForm" value="Войти"></p></br>
            <a href="registration.jsp"><span>Регистрация</span></a>
        </form>
    </div>
    <% } %>
    </body>
</html>