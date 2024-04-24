package org.ute.onlineexamination.models;

import java.sql.Timestamp;

public class ExamQuestion {
    private Integer id;
    private Integer question_id;
    private Integer exam_id;
    private Integer priority;

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getExam_id() {
        return exam_id;
    }

    public void setExam_id(Integer exam_id) {
        this.exam_id = exam_id;
    }

    public ExamQuestion(Integer question_id, Integer examId) {
        this.question_id = question_id;
        this.exam_id = examId ;
    }


    public ExamQuestion(Integer question_id) {
        this.question_id = question_id;
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

    public Timestamp getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Timestamp deleted_at) {
        this.deleted_at = deleted_at;
    }

    private Timestamp deleted_at;
    private Timestamp created_at;
    private Timestamp updated_at;
}
