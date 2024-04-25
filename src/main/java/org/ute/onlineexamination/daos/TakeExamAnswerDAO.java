package org.ute.onlineexamination.daos;

import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.models.TakeExamAnswer;

import java.util.List;
import java.util.Optional;

public class TakeExamAnswerDAO implements DAO<TakeExamAnswer> {
    @Override
    public List<TakeExamAnswer> getAll() {
        return null;
    }

    @Override
    public Optional<TakeExamAnswer> get(int id) {
        return Optional.empty();
    }

    @Override
    public Integer save(TakeExamAnswer takeExamAnswer) {
        return null;
    }

    @Override
    public void update(TakeExamAnswer takeExamAnswer) {

    }

    @Override
    public void delete(TakeExamAnswer takeExamAnswer) {

    }
}
