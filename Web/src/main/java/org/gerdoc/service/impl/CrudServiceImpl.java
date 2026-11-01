package org.gerdoc.service.impl;
import org.gerdoc.dao.CrudDao;
import org.gerdoc.service.CrudService;
import java.io.Serializable;
import java.util.List;
public class CrudServiceImpl<T,I extends Serializable> implements CrudService<T,I> {
    private final CrudDao<T,I> dao;
    protected CrudServiceImpl(CrudDao<T,I> dao) {this.dao=dao;}
    public List<T> findAll() {return dao.findAll();}
    public T findById(I id) {return dao.findById(id);}
    public void save(T value) {dao.save(value);}
    public void update(I id,T value) {dao.update(id,value);}
    public void deleteById(I id) {dao.deleteById(id);}
}
