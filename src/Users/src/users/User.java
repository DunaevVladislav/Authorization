package users;

import crypt.Crypt;
import database.Database;

public class User {

    private Integer id;
    public Integer getId() {
        return id;
    }

    private String login;
    public String getLogin() {
        return login;
    }

    private String password;
    public String getPassword() {
        return password;
    }

    private String email;
    public String getEmail() {
        return email;
    }

    private  String dateRegistration;
    public String getDateRegistration() {
        return dateRegistration;
    }

    private Database database;

    public User(Integer id) throws Exception {
        database = new Database();
        this.id = id;
        this.login = database.getLogin(getId());
        initializeData();
    }

    public User(String login) throws Exception {
        database = new Database();
        this.login = login;
        this.id = database.getID(getLogin());
        initializeData();
    }

    private void initializeData() throws Exception{
        this.password = database.getPassword(getLogin());
        this.email = database.getEmail(getLogin());
        this.dateRegistration = database.getDateRegistration(getLogin());
        this.database.finalize();
    }

    public String getToken() throws Exception {
        return Crypt.getToken(this.getLogin(), this.getDateRegistration());
    }

}
