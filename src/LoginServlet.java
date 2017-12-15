import crypt.Crypt;
import database.Database;
import users.MyCookie;
import users.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html; charset=UTF8");
        PrintWriter out = response.getWriter();
        Database database;

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        boolean remember = (request.getParameter("remember") != null);

        if (login == null || password == null){
            out.println("Некорректные данные");
            out.close();
            return;
        }

            try {
                User user = new User(login);
                if (!Crypt.getHash(password).equals(user.getPassword())){
                    out.println("<p>Неверный пароль</p>");
                    out.close();
                    return;
                }
                MyCookie cookie = new MyCookie(request, response);
                if (remember) cookie.setCookie(user);
                else cookie.setSession(user);
            }catch (Exception e){
                out.println("<p>" + e.getMessage() + "</p>");
                out.close();
                return;
            }
        response.sendRedirect(request.getRequestURL() + "/..");
        out.close();

    }

}
