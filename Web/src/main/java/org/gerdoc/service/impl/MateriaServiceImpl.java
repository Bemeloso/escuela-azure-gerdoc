package org.gerdoc.service.impl;
import org.gerdoc.model.*;
import org.gerdoc.service.MateriaService;
import org.gerdoc.dao.impl.MateriaDaoImpl;
public class MateriaServiceImpl extends CrudServiceImpl<Materia,Long> implements MateriaService { public MateriaServiceImpl() {super(new MateriaDaoImpl());} }
