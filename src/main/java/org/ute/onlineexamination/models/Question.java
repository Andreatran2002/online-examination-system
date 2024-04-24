package org.ute.onlineexamination.models;

import javafx.collections.ObservableList;

import java.sql.Timestamp;

public class Question {
    private Integer id;
    private Integer course_id;
    private String content;
    private Boolean active;
    private Integer priority;

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getId() {
        return id;
    }

    private ObservableList<Answer> answers;
    public Question(String content){
        this.content = content;
    }

    public ObservableList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ObservableList<Answer> answers) {
        this.answers = answers;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Question(Integer id, Integer course_id, String content, Boolean active, Timestamp deleted_at, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.course_id = course_id;
        this.content = content;
        this.active = active;
        this.deleted_at = deleted_at;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Question() {
    }

    private Timestamp deleted_at;
    private Timestamp created_at;
    private Timestamp updated_at;
}
