package org.gerdoc.dao;
import java.io.Serializable;
import java.util.List;
public interface CrudDao<T,I extends Serializable> {
    List<T> findAll(); T findById(I id); void save(T value); void update(I id,T value); void deleteById(I id);
}
