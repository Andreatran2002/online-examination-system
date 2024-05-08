package org.ute.onlineexamination.models;

import java.sql.Timestamp;

public class StudentUser {
    private int id;
    private int user_id;
    private String full_name;
    private String mobile;
    private String email;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Timestamp last_login;
    private Timestamp deleted_at;

    public StudentUser() {
        this.mobile="";
    }

    public StudentUser(int id, int user_id, String full_name, String mobile, String email, Timestamp created_at, Timestamp updated_at, Timestamp last_login, Timestamp deleted_at) {
        this.id = id;
        this.user_id = user_id;
        this.full_name = full_name;
        this.mobile = mobile;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.last_login = last_login;
        this.deleted_at = deleted_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Timestamp getLast_login() {
        return last_login;
    }

    public void setLast_login(Timestamp last_login) {
        this.last_login = last_login;
    }

    public Timestamp getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Timestamp deleted_at) {
        this.deleted_at = deleted_at;
    }
}
