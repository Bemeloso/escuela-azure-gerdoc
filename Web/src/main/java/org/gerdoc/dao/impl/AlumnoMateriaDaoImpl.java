package org.gerdoc.dao.impl;
import org.gerdoc.model.*;
import org.gerdoc.dao.AlumnoMateriaDao;
public class AlumnoMateriaDaoImpl extends CrudDaoImpl<AlumnoMateria,Long> implements AlumnoMateriaDao {
    public AlumnoMateriaDaoImpl() {super(AlumnoMateria.class,"id",AlumnoMateria::getId);}
    @Override protected void prepare(org.hibernate.Session session,AlumnoMateria value) {
        value.setAlumno(session.get(Alumno.class,value.getAlumno().getId()));
        value.setMateria(session.get(Materia.class,value.getMateria().getId()));
        if(value.getAlumno()==null || value.getMateria()==null) throw new IllegalArgumentException("El alumno o la materia ya no existen");
    }
}
