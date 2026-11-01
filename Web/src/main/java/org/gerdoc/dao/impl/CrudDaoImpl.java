package org.gerdoc.dao.impl;
import org.gerdoc.dao.CrudDao;
import org.gerdoc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.Serializable;
import java.util.*;
import java.util.function.*;
public class CrudDaoImpl<T,I extends Serializable> implements CrudDao<T,I> {
    private final Class<T> type;
    private final String order;
    private final Function<T,I> key;
    protected CrudDaoImpl(Class<T> type,String order,Function<T,I> key) {this.type=type;this.order=order;this.key=key;}
    public List<T> findAll() {try(Session s=HibernateUtil.getSessionFactory().openSession()) {return s.createQuery("from "+type.getSimpleName()+" order by "+order,type).list();}}
    public T findById(I id) {try(Session s=HibernateUtil.getSessionFactory().openSession()) {return s.get(type,id);}}
    private void transaction(Consumer<Session> work) {
        try(Session s=HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx=s.beginTransaction();
            try {work.accept(s);s.flush();tx.commit();}
            catch(RuntimeException ex) {if(tx.isActive()) tx.rollback();throw ex;}
        }
    }
    protected void prepare(Session session,T value) { }
    public void save(T value) {transaction(s->{prepare(s,value);s.persist(value);});}
    public void update(I id,T value) {
        transaction(s->{
            T old=s.get(type,id);
            if(old==null) throw new NoSuchElementException("El registro ya no existe");
            prepare(s,value);
            if(!id.equals(key.apply(value))) {
                if(s.get(type,key.apply(value))!=null) throw new IllegalArgumentException("La asignación ya existe");
                s.delete(old);s.flush();s.persist(value);
            } else s.merge(value);
        });
    }
    public void deleteById(I id) {transaction(s->{T old=s.get(type,id);if(old==null) throw new NoSuchElementException("El registro ya no existe");s.delete(old);});}
}
