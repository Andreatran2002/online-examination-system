package org.ute.onlineexamination.daos;

import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.models.ExamQuestion;

import java.util.List;
import java.util.Optional;

public class ExamQuestionDAO implements DAO<ExamQuestion> {
    @Override
    public List<ExamQuestion> getAll() {
        return null;
    }

    @Override
    public Optional<ExamQuestion> get(int id) {
        return Optional.empty();
    }

    @Override
    public Integer save(ExamQuestion examQuestion) {
        return null;
    }

    @Override
    public void update(ExamQuestion examQuestion) {

    }

    @Override
    public void delete(ExamQuestion examQuestion) {

    }
}
