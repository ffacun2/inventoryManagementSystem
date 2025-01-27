package org.ims.model;

public class User {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private String name;
    private String lastname;
    private String dni;


    public User(){}

    public User(String username, String password, String email, String role, String name, String lastname, String dni) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
    }

    public User(Long id,String username, String password, String email, String role, String name, String lastname, String dni) {
        this(username, password,email,role,name,lastname,dni);
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public  void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString(){
        return "User [id=" + id + ", username=" + username + ", email=" + email + ", role=" + role + ", name=" + name + ", lastname=" + lastname + ", dni=" + dni + "]";
    }

}
