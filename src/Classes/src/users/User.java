package users;

import database.Database;
import utils.Crypt;

/**
 * зарегистрированный пользователь
 */
public class User {

    /**
     * id пользователя
     */
    private Integer id;
    /**
     * логин пользователя
     */
    private String login;
    /**
     * пароль пользователя
     */
    private String password;
    /**
     * email пользователя
     */
    private String email;
    /**
     * дата регистрации пользователя
     */
    private  String dateRegistration;
    /**
     * количество авторизаций
     */
    private Integer visits;
    /**
     * база данных
     */
    private Database database;
    /**
     * конструктор
     * инициализурет все данные пользователя из бд по id
     * @param id id пользователя
     * @throws Exception исключение при работе с бд
     */
    public User(Integer id) throws Exception {
        database = new Database();
        this.id = id;
        this.login = database.getLogin(getId());
        initializeData();
    }

    /**
     * конструктор
     * инициализурет все данные пользователя из бд по логину
     * @param login логин пользователя
     * @throws Exception исключение при работе с бд
     */
    public User(String login) throws Exception {
        database = new Database();
        this.login = login;
        this.id = database.getID(getLogin());
        initializeData();
    }

    /**
     * @return id пользователя
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return логин пользователя
     */
    public String getLogin() {
        return login;
    }

    /**
     * @return пароль пользователя
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return email пользователя
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return дату регистрации пользователя
     */
    public String getDateRegistration() {
        return dateRegistration;
    }

    /**
     * @return количество авторизаций
     */
    public Integer getVisits() {
        return visits;
    }

    /**
     * инициалирует пароль, email, дату регистрации и количество авторизаций
     * @throws Exception исключение при работе с бд
     */
    private void initializeData() throws Exception{
        this.password = database.getPassword(getLogin());
        this.email = database.getEmail(getLogin());
        this.dateRegistration = database.getDateRegistration(getLogin());
        this.visits = database.getVisits(getLogin());
    }

    /**
     * возвращает токен для данного пользователя
     * @return токен данного пользователя
     */
    public String getToken() {
        return Crypt.getToken(this.getLogin(), this.getDateRegistration());
    }

    /**
     * увеличивает счетчик авторизаций на 1
     * @throws Exception исключение при работе с бд
     */
    public void updateVisits() throws Exception{
        database.updateVisits(getLogin());
    }
}