package database;

import java.sql.*;
import java.util.Date;

/**
 * класс для работы с базой данных
 */
public class Database {

    /**
     * путь к директории сервера
     */
    private static String path = Database.class.getProtectionDomain().getCodeSource().getLocation().getPath().replaceFirst("%20", " ") + "../../";
    /**
     *запрос к базе данных
     */
    public Statement statmt;
    /**
     * Соединение с базой данных
     */
    private Connection conn;

    /**
     * конструтор
     * подключение к базе данных
     * @throws SQLException исключение, если не удалось подключиться к бд
     * @throws ClassNotFoundException исключение, если не удалось найти библиотеку для работы с бд
     */
    public Database() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite://" + path + "/WEB-INF/data/database.db");
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'login' TEXT, 'password' TEXT, 'email' text, 'time' TEXT, 'visits' INTEGER);");
    }

    /**
     * @return путь к директории сервера
     */
    public static String getPath() {
        return path;
    }

    /**
     * добавляет в бд нового пользователя
     * @param login логин пользователя
     * @param password пароль пользователя
     * @param email email пользователя
     * @throws SQLException ошибка, если не удалось подключиться к бд
     */
    public  void insert(String login, String password, String email) throws Exception {
        Date date = new Date();
        if (!isExist(login)){
            statmt.execute("INSERT INTO 'users' ('login', 'password', 'email', 'time', 'visits') VALUES " +
                    "('" + login +"','" + password + "','" + email + "', '" + date.toString() + "', '1')");
        }
        else{
            throw new Exception("Duplicate login");
        }
    }

    /**
     * @return количество пользователей
     * @throws SQLException исключение, если не удалось подключиться к бд
     */
    public int getCountUsers() throws SQLException{
        ResultSet resSet = statmt.executeQuery("SELECT COUNT(*) FROM users");
        if (resSet.next())  return resSet.getInt(1);
        else return 0;
    }

    /**
     * проверяет существование пользователя
     * @param login логин пользователя
     * @return существует ли пользователь
     * @throws SQLException исключение, если не удалось подключиться к бд
     */
    public boolean isExist(String login) throws SQLException {
        ResultSet resSet = statmt.executeQuery("SELECT COUNT(*) FROM users WHERE login='" + login + "'");
        int cntUser = 0;
        if (resSet.next()) {
            cntUser = resSet.getInt(1);
        }
        resSet.close();
        return (cntUser > 0);
    }

    /**
     * @param login логин пользователя
     * @return id пользователя
     * @throws Exception генерирует исключение, в случае отсутствия пользователя с данным логином
     */
    public Integer getID(String login) throws Exception {
        ResultSet resSet = statmt.executeQuery("SELECT * FROM users WHERE login='" + login + "'");
        if (resSet.next()){
            String idStr =  resSet.getString("id");
            resSet.close();
            return Integer.parseInt(idStr);
        }
        else throw new Exception("User with login = " + login + " not found");
    }

    /**
     * @param login логин пользователя
     * @return пароль пользователя
     * @throws Exception генерирует исключение, в случае отсутствия пользователя с данным логином
     */
    public String getPassword(String login) throws Exception {
        ResultSet resSet = statmt.executeQuery("SELECT * FROM users WHERE login='" + login + "'");
        if (resSet.next()){
            String pass = resSet.getString("password");
            resSet.close();
            return pass;
        }
        else throw new Exception("User with login = " + login + " not found");
    }

    /**
     * @param id id пользователя
     * @return логин пользователя
     * @throws Exception генерирует исключение, в случае отсутствия пользователя с данным логином
     */
    public String getLogin(Integer id) throws Exception {
        ResultSet resSet = statmt.executeQuery("SELECT * FROM users WHERE id='" + id + "'");
        if (resSet.next()){
            String login = resSet.getString("login");
            resSet.close();
            return login;
        }
        else throw new Exception("User with id = " + id + " not found");
    }

    /**
     * @param login логин пользователя
     * @return email пользователя
     * @throws Exception генерирует исключение, в случае отсутствия пользователя с данным логином
     */
    public String getEmail(String login) throws Exception {
        ResultSet resSet = statmt.executeQuery("SELECT * FROM users WHERE login='" + login + "'");
        if (resSet.next()){
            String email = resSet.getString("email");
            resSet.close();
            return email;
        }
        else throw new Exception("User with login = " + login + " not found");
    }

    /**
     * @param login логин пользователя
     * @return дату регистрации пользователя
     * @throws Exception генерирует исключение, в случае отсутствия пользователя с данным логином
     */
    public String getDateRegistration(String login) throws Exception {
        ResultSet resSet = statmt.executeQuery("SELECT * FROM users WHERE login='" + login + "'");
        if (resSet.next()){
            String date = resSet.getString("time");
            resSet.close();
            return date;
        }
        else throw new Exception("User with login = " + login + " not found");
    }

    /**
     * @param login логин пользователя
     * @return количество авторизаций пользователя
     * @throws Exception генерирует исключение, в случае отсутствия пользователя с данным логином
     */
    public Integer getVisits(String login) throws Exception {
        ResultSet resSet = statmt.executeQuery("SELECT * FROM users WHERE login='" + login + "'");
        if (resSet.next()){
            Integer visits = resSet.getInt("visits");
            resSet.close();
            return visits;
        }
        else throw new Exception("User with login = " + login + " not found");
    }

    /**
     * увеличивает счетчик авторизаций на 1
     * @param login логин пользователя
     * @throws Exception исключение, в случае отсутствия пользователя с данным логином
     * @throws SQLException исключение, если не удалось подключиться к бд
     */
    public void updateVisits(String login) throws Exception, SQLException {
        Integer  visits  = getVisits(login);
        visits++;
        statmt.execute("UPDATE users SET visits = '" + visits + "' WHERE login = '" + login + "'");

    }

    /**
     * удаляет пользователя
     * @param login логин пользователя
     * @throws Exception генерирует исключение, в случае отсутствия пользователя с данным логином
     */
    public void deleteUser(String login) throws Exception {
        if (isExist(login)) {
            statmt.execute("DELETE FROM 'users' WHERE login='" + login + "'");
        }
        else{
            throw new Exception("User with login = " + login + " not found");
        }
    }


    /**
     * деструктор
     * отключение от базы данных
     */
    @Override
    public void finalize(){
        try{
            conn.close();
            statmt.close();
        } catch (SQLException ignored){}
    }
}
