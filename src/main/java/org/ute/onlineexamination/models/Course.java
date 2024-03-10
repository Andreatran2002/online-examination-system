package org.ute.onlineexamination.models;

import java.sql.Time;

public class Course {
    private Integer id;
    private Integer teacher_id;
    private String name;
    private String description ;
    private Time start;
    private Time end;

    public Course() {
    }

    public Integer getId() {
        return id;
    }

    public Course(Integer teacher_id, String name, String description, Time start, Time end) {
        this.teacher_id = teacher_id;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
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

    private Time deleted_at;
    private Time created_at;
    private Time updated_at;
}
