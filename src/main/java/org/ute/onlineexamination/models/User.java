package org.ute.onlineexamination.models;


import java.sql.Timestamp;
import java.sql.Timestamp;

import org.mindrot.jbcrypt.BCrypt;

public class User {

    private int id;
    private String full_name;
    private String email;
    private String mobile;
    private Boolean is_admin;

    public Boolean getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Boolean is_admin) {
        this.is_admin = is_admin;
    }

    public User(String full_name, String email, String mobile, String password_hash) {
        this.full_name = full_name;
        this.email = email;
        this.mobile = mobile;
        this.password_hash = password_hash;
    }

    public User(String full_name, String email, String password) {
        this.full_name = full_name;
        this.email = email;
        String generatedSecuredPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        this.password_hash = generatedSecuredPasswordHash;
    }
    public Boolean checkPassword(String password){
        return BCrypt.checkpw(password, password_hash);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public Timestamp getLast_login() {
        return last_login;
    }

    public void setLast_login(Timestamp last_login) {
        this.last_login = last_login;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp uptimestampd_at) {
        this.updated_at = uptimestampd_at;
    }

    public Timestamp getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Timestamp deleted_at) {
        this.deleted_at = deleted_at;
    }

    public User(int id, String full_name, String email, String mobile, String password_hash, Timestamp last_login, Timestamp created_at, Timestamp updated_at, Timestamp deleted_at) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
        this.mobile = mobile;
        this.password_hash = password_hash;
        this.last_login = last_login;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    private String password_hash;
    private Timestamp last_login;

    public User() {
    }

    private Timestamp created_at;
    private Timestamp updated_at;
    private Timestamp deleted_at;

}
