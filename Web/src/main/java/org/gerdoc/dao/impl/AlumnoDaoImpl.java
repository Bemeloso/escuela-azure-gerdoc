package org.gerdoc.dao.impl;
import org.gerdoc.model.*;
import org.gerdoc.dao.AlumnoDao;
public class AlumnoDaoImpl extends CrudDaoImpl<Alumno,Long> implements AlumnoDao { public AlumnoDaoImpl() {super(Alumno.class,"id",Alumno::getId);} }
