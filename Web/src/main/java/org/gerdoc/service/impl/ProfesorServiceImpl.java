package org.gerdoc.service.impl;
import org.gerdoc.model.*;
import org.gerdoc.service.ProfesorService;
import org.gerdoc.dao.impl.ProfesorDaoImpl;
public class ProfesorServiceImpl extends CrudServiceImpl<Profesor,Long> implements ProfesorService { public ProfesorServiceImpl() {super(new ProfesorDaoImpl());} }
