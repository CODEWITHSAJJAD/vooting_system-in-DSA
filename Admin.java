package project;

public class Admin {
    String adminID;
    String username;
    String password;

    public Admin(String adminID, String username, String password) {
        this.adminID = adminID;
        this.username = username;
        this.password = password;
    }

    public boolean authenticate(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }
}
