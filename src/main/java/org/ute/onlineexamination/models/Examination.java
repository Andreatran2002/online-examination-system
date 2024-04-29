package org.ute.onlineexamination.models;

import javafx.collections.ObservableList;

import java.sql.Timestamp;

public class Examination {
    private Integer id;
    private Integer course_id;
    private String name;
    private String description;
    private Timestamp start;
    private Timestamp end;
    private Integer time_retry ;
    private Integer scoring_type;
    private Boolean active;
    private Integer total_minutes;

    public ObservableList<ExamQuestion> questions;
    
    public Examination(){
        
    }

    public Integer getTotal_minutes() {
        return total_minutes;
    }

    public void setTotal_minutes(Integer total_minutes) {
        this.total_minutes = total_minutes;
    }

    public Course course ;

    public Examination(Integer course_id, String name, String description, Timestamp start, Timestamp end, Integer time_retry, Integer scoring_type, Boolean active) {
        this.course_id = course_id;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.time_retry = time_retry;
        this.scoring_type = scoring_type;
        this.active = active;
    }

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

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public Integer getTime_retry() {
        return time_retry;
    }

    public void setTime_retry(Integer time_retry) {
        this.time_retry = time_retry;
    }

    public Integer getScoring_type() {
        return scoring_type;
    }

    public void setScoring_type(Integer scoring_type) {
        this.scoring_type = scoring_type;
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

    private Timestamp deleted_at;
    private Timestamp created_at;
    private Timestamp updated_at;
}
