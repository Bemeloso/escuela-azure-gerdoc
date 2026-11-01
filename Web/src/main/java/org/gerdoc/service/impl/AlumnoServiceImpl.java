package org.gerdoc.service.impl;
import org.gerdoc.model.*;
import org.gerdoc.service.AlumnoService;
import org.gerdoc.dao.impl.AlumnoDaoImpl;
public class AlumnoServiceImpl extends CrudServiceImpl<Alumno,Long> implements AlumnoService { public AlumnoServiceImpl() {super(new AlumnoDaoImpl());} }
