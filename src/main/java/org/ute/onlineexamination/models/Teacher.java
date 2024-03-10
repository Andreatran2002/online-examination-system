package org.ute.onlineexamination.models;

import java.sql.Time;

public class Teacher {
    private Integer id;
    private Integer user_id;
    private String address;

    public Teacher(Integer user_id, String address, String title) {
        this.user_id = user_id;
        this.address = address;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Time getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Time deleted_at) {
        this.deleted_at = deleted_at;
    }

    public Time getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Time created_at) {
        this.created_at = created_at;
    }

    public Time getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Time updated_at) {
        this.updated_at = updated_at;
    }

    public Teacher(Integer id, Integer user_id, String address, String title, Time deleted_at, Time created_at, Time updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.address = address;
        this.title = title;
        this.deleted_at = deleted_at;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    private String title;

    public Teacher(Integer user_id) {
        this.user_id = user_id;
    }

    public Teacher() {
    }

    private Time deleted_at;
    private Time created_at;
    private Time updated_at;
}
