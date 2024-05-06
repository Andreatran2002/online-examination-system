package org.ute.onlineexamination.base;

import org.ute.onlineexamination.models.StudentUser;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    List<StudentUser> getAll();

    Optional<T> get(int id);

    Integer save(T t);

    void update(T t);

    void delete(T t);
}