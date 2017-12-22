import database.Database;
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
import java.sql.SQLException;
import java.util.LinkedList;

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
        Database database;



        try {
            database = new Database();
        } catch (SQLException |ClassNotFoundException e) {
            errors.add("Не удалось подключиться к базе данных");
            OutError.printJSONError(errors, out);
            out.close();
            return;
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String repPassword = request.getParameter("repPassword");



        if (login == null || !CheckData.check(login)){
            errors.add("Логин должен состоять из латинских букв или цифр");
            errors.add("Длина логина должна быть от 3 до 32 символов");
        }
        if(password == null || repPassword == null || !CheckData.check(password) || !CheckData.check(repPassword)){
            errors.add("Некорректный пароль");
        }


        if (password !=null && repPassword != null && !password.equals(repPassword)) {
            errors.add("Пароли не совпадают");
        }

        if (email == null || !CheckData.checkEmail(email)){
            errors.add("Некорректный email");
        }



        if (!errors.isEmpty()){
            OutError.printJSONError(errors, out);
            out.close();
            return;
        }

        try{
            database.insert(login, Crypt.getHash(password), email);
        } catch (Exception e){
            errors.add("Пользователь с таким логином уже сущетсвует");
            OutError.printJSONError(errors, out);
            out.close();
            return;
        }
        try{
            database.finalize();
            User user = new User(login);
            MyCookie cookie = new MyCookie(request, response);
            cookie.setSession(user);
        } catch (Exception e){
            errors.add(e.getMessage());
        }
        OutError.printJSONError(errors, out);
        out.close();
    }

}
