package org.ute.onlineexamination.models;

import java.sql.Timestamp;

public class TakeExam {
    private Integer id;
    private Integer student_id;
    private Integer exam_id;
    private Timestamp start;
    private Timestamp end;
    private double scoring ;

    public TakeExam(Integer student_id, Integer exam_id, Timestamp start, Timestamp end, double scoring) {
        this.student_id = student_id;
        this.exam_id = exam_id;
        this.start = start;
        this.end = end;
        this.scoring = scoring;
    }
    public TakeExam(Integer student_id, Integer exam_id, Timestamp start) {
        this.student_id = student_id;
        this.exam_id = exam_id;
        this.start = start;
        this.end = end;
        this.scoring = scoring;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public Integer getExam_id() {
        return exam_id;
    }

    public void setExam_id(Integer exam_id) {
        this.exam_id = exam_id;
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

    public double getScoring() {
        return scoring;
    }

    public void setScoring(double scoring) {
        this.scoring = scoring;
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
