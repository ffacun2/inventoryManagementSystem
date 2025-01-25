package org.ims.model;

public class Administrator {

    private String usuario;
    private String password;

    private Administrator(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

}
