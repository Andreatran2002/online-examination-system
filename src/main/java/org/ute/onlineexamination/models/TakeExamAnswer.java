package org.ute.onlineexamination.models;

import java.sql.Time;

public class TakeExamAnswer {
    private Integer id;
    private Integer take_exam_id;
    private Integer answer_id;
    private Time deleted_at;
    private Time created_at;

    public TakeExamAnswer() {
    }

    private Time updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTake_exam_id() {
        return take_exam_id;
    }

    public void setTake_exam_id(Integer take_exam_id) {
        this.take_exam_id = take_exam_id;
    }

    public Integer getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(Integer answer_id) {
        this.answer_id = answer_id;
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
}
