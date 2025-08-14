package net.codejava.bookstore;

public class User {
    private int id;
    private String username;
    private String userType;
    private String email;

    public User() {}

    public User(int id, String username, String email,String userType) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.userType=userType;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id;}

    public String getUsername() { return username;}
    public void setUsername(String username) { this.username = username;}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email;}
    
    public String getUserType() {return userType;}
    public void setUserType(String userType) {this.userType=userType;}
}
