package org.ute.onlineexamination.models;

import java.sql.Time;

public class Question {
    private Integer id;
    private Integer course_id;
    private String content;
    private Boolean active;

    public Integer getId() {
        return id;
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

    public Question(Integer id, Integer course_id, String content, Boolean active, Time deleted_at, Time created_at, Time updated_at) {
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

    private Time deleted_at;
    private Time created_at;
    private Time updated_at;
}
