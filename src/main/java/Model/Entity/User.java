package Model.Entity;

public class User {
    private int id;
    private String login;
    private String password;
    private String role;

    /**
     * Constructor for User
     * @param id
     * @param login
     * @param password
     * @param role
     */
    public User(int id, String login, String password, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    // Getters
    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setLogin(String login) { this.login = login; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}
