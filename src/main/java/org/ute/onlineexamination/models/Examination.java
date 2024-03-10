package org.ute.onlineexamination.models;

import java.sql.Time;

public class Examination {
    private Integer id;
    private Integer course_id;
    private String name;
    private String description;
    private Time start;
    private Time end;
    private Integer times_retry ;
    private Integer scoring_type;
    private Boolean active;

    public Examination(Integer course_id, String name, String description, Time start, Time end, Integer times_retry, Integer scoring_type, Boolean active) {
        this.course_id = course_id;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.times_retry = times_retry;
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

    public Integer getTimes_retry() {
        return times_retry;
    }

    public void setTimes_retry(Integer times_retry) {
        this.times_retry = times_retry;
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
