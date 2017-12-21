import database.Database;
import users.MyCookie;
import users.User;
import utils.Crypt;
import utils.OutError;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * сервлет для регистарции пользователей
 */
public class RegistrationServlet extends HttpServlet {

    /**
     * обрабатывает POST-запрос на регистрацию нового пользователя
     * @param request запрос
     * @param response ответ
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){


        response.setContentType("text/html; charset=UTF8");
        PrintWriter out;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String repPassword = request.getParameter("repPassword");

        if (login == null || password == null || email == null || repPassword == null || login.isEmpty() || email.isEmpty()){
            OutError.printError(out, request.getRequestURL() + "/..", "Некорректные данные");
            return;
        }

        if (!password.equals(repPassword)) {
            OutError.printError(out, request.getRequestURL() + "/..", "Пароли не совпадают");
            return;
        }

        if (password.length() < 6){
            OutError.printError(out, request.getRequestURL() + "/..", "Пароли слишком короткий");
            return;
        }

        Database database;
        try {
            database = new Database();
        } catch (SQLException |ClassNotFoundException e) {
            OutError.printError(out, request.getRequestURL() + "/..", "Не удалось подключиться к базе данных");
            return;
        }

        try{
            database.insert(login, Crypt.getHash(password), email);
        } catch (Exception e){
            OutError.printError(out, request.getRequestURL() + "/..", "Пользователь с таким логином уже сущетсвует");
            return;
        }
        try{
            database.finalize();
            User user = new User(login);
            MyCookie cookie = new MyCookie(request, response);
            cookie.setSession(user);
        } catch (Exception e){
            OutError.printError(out, request.getRequestURL() + "/..", "e.getMessage()");
            return;
        }
        try {
            response.sendRedirect(request.getRequestURL() + "/..");
        } catch (IOException e) {
            OutError.printError(out, request.getRequestURL() + "/..", "Регистрация прошла успешено! Перейдите на главную страницу.");
        }
        out.close();

    }

}
