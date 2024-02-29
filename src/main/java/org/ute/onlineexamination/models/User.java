package org.ute.onlineexamination.models;


public class User {

    private int id;

    private String fullName;
    private String email;
    private String password;
    public User(String fullName, String email,String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }
}
