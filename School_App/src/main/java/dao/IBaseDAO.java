package dao;

import java.util.List;

public interface IBaseDAO<T> {

    public void add(T element);

    public void delete(long id);

    public T getById(long id);

    public List<T> getAll();

    public void close();

}

