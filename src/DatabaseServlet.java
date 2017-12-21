import database.Database;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * сервлета, для работы с бд (для отладки)
 */
public class DatabaseServlet extends HttpServlet{

    /**
     * база данных
     */
    private Database database = null;

    /**
     * выводит html-таблицу с пользователями
     * @param out - объект PrintWriter, для вывода в html
     * @throws SQLException исключение, если не удалось подключиться к бд
     */
    private void outUsersTable(PrintWriter out) throws SQLException{
        ResultSet resSet = database.statmt.executeQuery("SELECT * FROM users");
        out.println("  <table border='1'>\n" +
                "   <caption><p id='title1'>Пользователи</p></caption>\n" +
                "   <tr>\n" +
                "    <th>id</th>\n" +
                "    <th>login</th>\n" +
                "    <th>пароль</th>\n" +
                "    <th>email</th>\n" +
                "    <th>время</th>\n" +
                "   </tr>");
        while (resSet.next()){
            out.println(
                    "<tr>" +
                        "<td>" + resSet.getString("id") + "</td>" +
                        "<td>" + resSet.getString("login") + "</td>" +
                        "<td>" + resSet.getString("password") + "</td>"+
                        "<td>" + resSet.getString("email") + "</td>" +
                        "<td>" + resSet.getString("time") + "</td>" +
                    "</tr>");
        }
        out.println("</table>");
        resSet.close();
    }


    /**+
     * ответ на GET-запрос
     * @param request запрос
     * @param response ответ
     * @throws IOException исключение, если не удалось получить поток вывода
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html; charset=UTF8");
        PrintWriter out = response.getWriter();

        try {
            database = new Database();
        } catch (SQLException|ClassNotFoundException e) {
            out.println("<p>Не удалось подключиться к базе данных</p>");
            out.close();
            return;
        }

        try {
            out.println("<html>");
            out.println("<head>" +
                    "<title>База данных</title>" +
                    "<meta charset='utf-8'>\n" +
                    "<link rel='stylesheet' type='text/css' href='css/index.css'/>\n" +
                    "</head>");
            out.println("<body>");
            out.println("<div id='tableDiv'>");
         //   out.println("<p>" + request.getRequestURL()+ "</p>");
            outUsersTable(out);
            out.println("</div>\n" +
                    "</body>\n" +
                    "</html>");

        } catch (SQLException e){
            out.println("<p>Что-то пошло не так</p>");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }

        database.finalize();

    }

}
