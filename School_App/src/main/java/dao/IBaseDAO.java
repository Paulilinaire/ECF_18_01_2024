package dao;

import java.util.List;

public interface IBaseDAO<T> {

    public void add(T element);

    public void delete(int id);

    public T getById(int id);

    public List<T> getAll();

    public void close();

}

