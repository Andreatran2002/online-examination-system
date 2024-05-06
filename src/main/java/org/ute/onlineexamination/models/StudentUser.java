package org.ute.onlineexamination.models;

import java.sql.Timestamp;

public class StudentUser {
    private int StudentId;
    private int UserId;
    private String fullName;
    private String mobile;
    private String email;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Timestamp last_login;
    private Timestamp deleted_at;

    public StudentUser(int studentId, int userId, String fullName, String mobile, String email, Timestamp created_at, Timestamp updated_at, Timestamp last_login, Timestamp deleted_at) {
        StudentId = studentId;
        UserId = userId;
        this.fullName = fullName;
        this.mobile = mobile;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.last_login = last_login;
        this.deleted_at = deleted_at;
    }

    public StudentUser() {
    }

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
