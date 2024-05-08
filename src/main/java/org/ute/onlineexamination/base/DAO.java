package org.ute.onlineexamination.base;

import javafx.collections.ObservableList;
import org.ute.onlineexamination.models.TeacherUser;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    List<T> getAll();

    Optional<T> get(int id);

    Integer save(T t);

    void update(T t);

    void delete(T t);
}