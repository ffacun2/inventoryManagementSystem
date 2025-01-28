package org.ims.model;

public class Supplier {

    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String address;
    private String phone;
    private String location;

    public Supplier(){}

    public Supplier(String name, String lastname ,String email, String address, String phone, String location){
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.location = location;
        this.address = address;
        this.email = email;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Nombre :"+name+
                ", Apellido : "+lastname+
                ", Teléfono : "+phone+
                ", Email : "+email+
                ", Localización : "+location+
                ", Dirección : "+address
                ;
    }

}
