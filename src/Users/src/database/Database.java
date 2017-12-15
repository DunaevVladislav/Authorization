package database;

import java.sql.*;
import java.util.Date;

public class Database {

    /**
     * путь к директории сервера
     */
    private static String path = Database.class.getProtectionDomain().getCodeSource().getLocation().getPath().replaceFirst("%20", " ") + "../../";
    public static String getPath() {
        return path;
    }

    /**
     * Соединение с базой данных
     */
    private Connection conn;
    /**
     *запрос к базе данных
     */
    public Statement statmt;

    /**
     * конструтор
     * подключение к базе данных
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Database() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite://" + path + "/WEB-INF/data/database.db");
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'login' text, 'password' text, 'email' text, 'time' text);");
    }

    /**
     *
     * @param login
     * @param password
     * @param email
     * @throws SQLException
     */
    public  void insert(String login, String password, String email) throws Exception {
        Date date = new Date();
        if (!isExist(login)){
            statmt.execute("INSERT INTO 'users' ('login', 'password', 'email', 'time') VALUES ('" + login +"','" + password + "','" + email + "', '" + date.toString() + "')");
        }
        else{
            throw new Exception("Duplicate login");
        }
    }

    /**
     * @return количество пользователей
     * @throws SQLException
     */
    public int getCountUsers() throws SQLException{
        ResultSet resSet = statmt.executeQuery("SELECT COUNT(*) FROM users");
        if (resSet.next())  return resSet.getInt(1);
        else return 0;
    }

    public boolean isExist(String login) throws SQLException {
        ResultSet resSet = statmt.executeQuery("SELECT COUNT(*) FROM users WHERE login='" + login + "'");
        int cntUser = 0;
        if (resSet.next()) {
            cntUser = resSet.getInt(1);
        }
        resSet.close();
        return (cntUser > 0);
    }

    public Integer getID(String login) throws Exception {
        ResultSet resSet = statmt.executeQuery("SELECT * FROM users WHERE login='" + login + "'");
        if (resSet.next()){
            String idStr =  resSet.getString("id");
            resSet.close();
            return Integer.parseInt(idStr);
        }
        else throw new Exception("User with login = " + login + "not found");
    }

    public String getPassword(String login) throws Exception {
        ResultSet resSet = statmt.executeQuery("SELECT * FROM users WHERE login='" + login + "'");
        if (resSet.next()){
            String pass = resSet.getString("password");
            resSet.close();
            return pass;
        }
        else throw new Exception("User with login = " + login + "not found");
    }

    public String getLogin(Integer id) throws Exception {
        ResultSet resSet = statmt.executeQuery("SELECT * FROM users WHERE id='" + id + "'");
        if (resSet.next()){
            String login = resSet.getString("login");
            resSet.close();
            return login;
        }
        else throw new Exception("User with id = " + id + "not found");
    }

    public String getEmail(String login) throws Exception {
        ResultSet resSet = statmt.executeQuery("SELECT * FROM users WHERE login='" + login + "'");
        if (resSet.next()){
            String email = resSet.getString("email");
            resSet.close();
            return email;
        }
        else throw new Exception("User with login = " + login + "not found");
    }

    public String getDateRegistration(String login) throws Exception {
        ResultSet resSet = statmt.executeQuery("SELECT * FROM users WHERE login='" + login + "'");        if (resSet.next()){
            String date = resSet.getString("time");
            resSet.close();
            return date;
        }
        else throw new Exception("User with login = " + login + "not found");
    }

    public void deleteUser(String login) throws Exception {
        if (isExist(login)) {
            statmt.execute("DELETE FROM 'users' WHERE login='" + login + "'");
        }
        else{
            throw new Exception("User with login = " + login + "not found");
        }
    }


    /**
     * деструктор
     * отключается от базы данных
     * @throws SQLException
     */
    @Override
    public void finalize() throws SQLException{
        try{
            conn.close();
            statmt.close();
        } catch (SQLException ignored){}
    }

}
