package org.ute.onlineexamination.models;

import java.sql.Time;

public class Answer {
    private Integer id;
    private Integer question_id;
    private String content;

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

    private Boolean correct;
    private Boolean active;
    private Time deleted_at;
    private Time created_at;
    private Time updated_at;
}
