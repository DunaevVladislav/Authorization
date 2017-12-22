import users.MyCookie;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * сервлет для разлогинивания пользователя
 */
public class LogoutServlet extends HttpServlet{

    /**
     * обработка GET-запрса
     * @param request запрос
     * @param response ответ
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        MyCookie cookie = new MyCookie(request, response);
        cookie.remove();
        response.sendRedirect(request.getRequestURL() + "/..");

        out.close();
    }
}