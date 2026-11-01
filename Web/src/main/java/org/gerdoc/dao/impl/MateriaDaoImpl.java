package org.gerdoc.dao.impl;
import org.gerdoc.model.*;
import org.gerdoc.dao.MateriaDao;
public class MateriaDaoImpl extends CrudDaoImpl<Materia,Long> implements MateriaDao { public MateriaDaoImpl() {super(Materia.class,"id",Materia::getId);} }
