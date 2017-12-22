import users.MyCookie;
import users.User;
import utils.CheckData;
import utils.Crypt;
import utils.OutError;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out;
        LinkedList<String> errors = new LinkedList<>();
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String rememberStr = request.getParameter("remember");
        boolean remember = (rememberStr != null && rememberStr.equals("true"));


        if (login == null || password == null || !CheckData.check(login) || !CheckData.check(password)){
            errors.add("Некорректный логин или пароль");
            OutError.printJSONError(errors, out);
            out.close();
            return;
        }

        try {
            User user = null;
            try{
                user = new User(login);
            } catch (Exception ignored){}
            if (user == null || !Crypt.getHash(password).equals(user.getPassword())){
                errors.add("Неверный логин или пароль");
                OutError.printJSONError(errors, out);
                out.close();
                return;
            }
            user.updateVisits();
            MyCookie cookie = new MyCookie(request, response);
            if (remember) {
                cookie.setCookie(user);
            }
            else {
                cookie.setSession(user);
            }
        }catch (Exception e){
            errors.add(e.getMessage());
            OutError.printJSONError(errors, out);
            out.close();
            return;
        }
        try {
            response.sendRedirect(request.getRequestURL() + "/..");
        } catch (IOException e) {
            errors.add("Авторизация прошла успешено! Перейдите на главную страницу.");
            OutError.printJSONError(errors, out);
        }
        out.close();
    }
}
