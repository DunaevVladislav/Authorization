import users.MyCookie;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LogoutServlet extends HttpServlet{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html; charset=UTF8");
        PrintWriter out = response.getWriter();

        MyCookie cookie = new MyCookie(request, response);
        try {
            cookie.remove();
        } catch (Exception ignored) {}

        response.sendRedirect(request.getRequestURL() + "/..");
        out.close();
    }
}