package org.gerdoc.service.impl;
import org.gerdoc.model.*;
import org.gerdoc.service.AlumnoMateriaService;
import org.gerdoc.dao.impl.AlumnoMateriaDaoImpl;
public class AlumnoMateriaServiceImpl extends CrudServiceImpl<AlumnoMateria,Long> implements AlumnoMateriaService { public AlumnoMateriaServiceImpl() {super(new AlumnoMateriaDaoImpl());} }
