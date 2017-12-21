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

/**
 * сервлет для авторизации пользователя
 */
public class LoginServlet extends HttpServlet{

    /**
     * обрабатывает POST-запрос на авторизацию пользователя
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
        Database database;

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        boolean remember = (request.getParameter("remember") != null);

        if (login == null || password == null){
            OutError.printError(out, request.getRequestURL() + "/..", "Некорректные данные");
            return;
        }
            try {
                User user = null;
                try{
                    user = new User(login);
                } catch (Exception ignored){}
                if (user == null || !Crypt.getHash(password).equals(user.getPassword())){
                    OutError.printError(out, request.getRequestURL() + "/..", "Неверный логин или пароль");
                    return;
                }
                user.updateVisits();
                MyCookie cookie = new MyCookie(request, response);
                if (remember) cookie.setCookie(user);
                else cookie.setSession(user);
            }catch (Exception e){
                OutError.printError(out, request.getRequestURL() + "/..", e.getMessage());
                return;
            }
        try {
            response.sendRedirect(request.getRequestURL() + "/..");
        } catch (IOException e) {
            OutError.printError(out, request.getRequestURL() + "/..", "Авторизация прошла успешено! Перейдите на главную страницу.");
        }
        out.close();
    }
}
