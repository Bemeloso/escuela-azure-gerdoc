package org.gerdoc.service.impl;
import org.gerdoc.model.*;
import org.gerdoc.service.ProfesorMateriaService;
import org.gerdoc.dao.impl.ProfesorMateriaDaoImpl;
public class ProfesorMateriaServiceImpl extends CrudServiceImpl<ProfesorMateria,ProfesorMateriaId> implements ProfesorMateriaService { public ProfesorMateriaServiceImpl() {super(new ProfesorMateriaDaoImpl());} }
