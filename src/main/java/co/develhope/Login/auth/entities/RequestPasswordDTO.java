package co.develhope.Login.auth.entities;

import javax.persistence.Entity;

public class RequestPasswordDTO {

    private String email;

    public RequestPasswordDTO() {
    }

    public RequestPasswordDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
