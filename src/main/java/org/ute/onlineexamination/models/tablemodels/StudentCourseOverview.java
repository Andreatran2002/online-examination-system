package org.ute.onlineexamination.models.tablemodels;

import java.sql.Timestamp;

public class StudentCourseOverview {
    String email ;
    String fullName;
    String mobile;
    Integer totalTestDone;

    public Integer getTotalTestDone() {
        return totalTestDone;
    }

    public void setTotalTestDone(Integer totalTestDone) {
        this.totalTestDone = totalTestDone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    Timestamp registerAt;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public Timestamp getRegisterAt() {
        return registerAt;
    }

    public void setRegisterAt(Timestamp registerAt) {
        this.registerAt = registerAt;
    }

    public StudentCourseOverview() {
    }

    public StudentCourseOverview(String email, String fullName, Timestamp registerAt) {
        this.email = email;
        this.fullName = fullName;
        this.registerAt = registerAt;
    }
}
