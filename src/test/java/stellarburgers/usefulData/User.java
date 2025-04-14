package stellarburgers.usefulData;

public class User {
    private String email;
    private String password;
    private String name;

    //для регистрации
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // для авторизации
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() { }

    // Геттеры и сеттеры - с помощью Lombok
}