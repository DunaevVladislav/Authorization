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
import java.sql.SQLException;

public class RegistrationServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html; charset=UTF8");
        PrintWriter out = response.getWriter();

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String repPassword = request.getParameter("repPassword");

        if (login == null || password == null || email == null || repPassword == null){
            out.println("<p>Некорректные данные</p>");
            return;
        }

        if (!password.equals(repPassword)) {
            out.println("<p>Пароли не совпадают</p>");
            return;
        }

        Database database;
        try {
            database = new Database();
        } catch (SQLException |ClassNotFoundException e) {
            out.println("<p>Не удалось подключиться к базе данных</p>");
            out.close();
            return;
        }

        try{
            database.insert(login, Crypt.getHash(password), email);
        } catch (Exception e){
            out.println("<p>Пользователь с таким логином уже сущетсвует</p>");
            return;
        }
        try{
            database.finalize();
            User user = new User(login);
            MyCookie cookie = new MyCookie(request, response);
            cookie.setSession(user);
        } catch (Exception e){
            out.println("<p>" + e.getMessage() + "</p>");
        }
        response.sendRedirect(request.getRequestURL() + "/..");
        out.close();

    }

}
