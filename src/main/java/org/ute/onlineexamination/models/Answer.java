package org.ute.onlineexamination.models;

import java.sql.Timestamp;

public class Answer {
    private Integer id = -1 ;
    private Integer question_id;
    private String content;

    public Answer(){

    }

    public Answer(Integer question_id, String content, Boolean correct, Boolean active) {
        this.question_id = question_id;
        this.content = content;
        this.correct = correct;
        this.active = active;
    }
    public Answer(String content, Boolean correct, Boolean active) {
        this.content = content;
        this.correct = correct;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Timestamp getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Timestamp deleted_at) {
        this.deleted_at = deleted_at;
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

    private Boolean correct;
    private Boolean active;
    private Timestamp deleted_at;
    private Timestamp created_at;
    private Timestamp updated_at;
}
