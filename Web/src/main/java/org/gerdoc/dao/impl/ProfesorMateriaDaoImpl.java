package org.gerdoc.dao.impl;
import org.gerdoc.model.*;
import org.gerdoc.dao.ProfesorMateriaDao;
public class ProfesorMateriaDaoImpl extends CrudDaoImpl<ProfesorMateria,ProfesorMateriaId> implements ProfesorMateriaDao {
    public ProfesorMateriaDaoImpl() {super(ProfesorMateria.class,"profesor.id,materia.id",x->new ProfesorMateriaId(x.getProfesor().getId(),x.getMateria().getId()));}
    @Override protected void prepare(org.hibernate.Session session,ProfesorMateria value) {
        // Las relaciones que integran la clave deben pertenecer a la transacción actual.
        value.setProfesor(session.get(Profesor.class,value.getProfesor().getId()));
        value.setMateria(session.get(Materia.class,value.getMateria().getId()));
        if(value.getProfesor()==null || value.getMateria()==null) throw new IllegalArgumentException("El profesor o la materia ya no existen");
    }
}
