package org.ims.model;

public class User {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String Role;
    private String name;
    private String lastname;
    private int DNI;


    public User(){}

    public User(String username, String password, String email, String role, String name, String lastname, int DNI) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.Role = role;
        this.name = name;
        this.lastname = lastname;
        this.DNI = DNI;
    }

    public User(Long id,String username, String password, String email, String role, String name, String lastname, int DNI) {
        this(username, password,email,role,name,lastname,DNI);
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public  void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString(){
        return "User [id=" + id + ", username=" + username + ", email=" + email + ", role=" + Role + ", name=" + name + ", lastname=" + lastname + ", DNI=" + DNI + "]";
    }
}
