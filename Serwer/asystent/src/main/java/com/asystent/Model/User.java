package com.asystent.Model;

/**
 * Created by Kamil on 2017-10-17.
 */
public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String about;
    private byte[] avatar;
    private boolean active;

    public User() {
        
    }

    public User(Long id, String username, String email, String password, String about, byte[] avatar, boolean active) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.about = about;
        this.avatar = avatar;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
