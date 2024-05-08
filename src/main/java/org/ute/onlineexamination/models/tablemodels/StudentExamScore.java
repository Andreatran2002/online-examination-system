package org.ute.onlineexamination.models.tablemodels;

public class StudentExamScore {
    String email ;
    String fullName;
    Double score ;

    Integer timesRetry;

    public StudentExamScore() {
    }

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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getTimesRetry() {
        return timesRetry;
    }

    public void setTimesRetry(Integer timesRetry) {
        this.timesRetry = timesRetry;
    }
}
