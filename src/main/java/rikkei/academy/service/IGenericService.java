package rikkei.academy.service;

import java.util.List;

public interface IGenericService<T> {
    List<T> findAll();
    void save(T t);
    void delete(int id);
    T findById(int id);
    List<T> findByName(String name);

}
