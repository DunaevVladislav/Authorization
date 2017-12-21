import users.MyCookie;
import utils.OutError;

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
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("text/html; charset=UTF8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException ignored) {}

        MyCookie cookie = new MyCookie(request, response);
        try {
            cookie.remove();

        } catch (Exception ignored) {}
        try {
            response.sendRedirect(request.getRequestURL() + "/..");
        } catch (IOException e) {
            if (out !=  null) {
                OutError.printError(out, request.getRequestURL() + "/..", "Выход осуществлен успешно! Перейдите на главную страницу.");
            }else{
                e.printStackTrace();
            }
        }

        if (out!= null) out.close();
    }
}