package co.develhope.Login.auth.entities;

import co.develhope.Login.user.entities.User;

import javax.persistence.Entity;


public class LoginRTO {

    private User user;
    private String JWT;

    public LoginRTO() {
    }

    public LoginRTO(User user, String JWT) {
        this.user = user;
        this.JWT = JWT;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJWT() {
        return JWT;
    }

    public void setJWT(String JWT) {
        this.JWT = JWT;
    }
}
