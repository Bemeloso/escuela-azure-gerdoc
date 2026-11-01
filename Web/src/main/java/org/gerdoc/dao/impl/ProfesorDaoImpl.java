package org.gerdoc.dao.impl;
import org.gerdoc.model.*;
import org.gerdoc.dao.ProfesorDao;
public class ProfesorDaoImpl extends CrudDaoImpl<Profesor,Long> implements ProfesorDao { public ProfesorDaoImpl() {super(Profesor.class,"id",Profesor::getId);} }
