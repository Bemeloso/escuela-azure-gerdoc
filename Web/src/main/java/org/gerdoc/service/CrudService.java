package org.gerdoc.service;
import java.io.Serializable;
import java.util.List;
public interface CrudService<T,I extends Serializable> {
    List<T> findAll(); T findById(I id); void save(T value); void update(I id,T value); void deleteById(I id);
}
