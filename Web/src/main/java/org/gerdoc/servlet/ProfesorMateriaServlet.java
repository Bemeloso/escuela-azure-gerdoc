package org.gerdoc.servlet;
import org.gerdoc.model.*;
import org.gerdoc.service.*;
import org.gerdoc.service.impl.*;
import org.gerdoc.util.Formulario;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
@WebServlet({"/ProfesorMateriaServlet","/ProfesorMateria.jsp"})
public class ProfesorMateriaServlet extends CatalogoServlet<ProfesorMateria,ProfesorMateriaId> {
    private final ProfesorMateriaService service=new ProfesorMateriaServiceImpl();
    protected ProfesorMateriaService service() {return service;}
    protected String nombre() {return "ProfesorMateria";}
    protected String pagina() {return "ProfesorMateria.jsp";}
    protected ProfesorMateriaId id(String raw) {String[] parts=raw==null?new String[0]:raw.split("-",-1);if(parts.length!=2) throw new IllegalArgumentException("Clave compuesta inválida");return new ProfesorMateriaId(Formulario.id(parts[0]),Formulario.id(parts[1]));}
    protected ProfesorMateria leer(HttpServletRequest r) {ProfesorMateria x=new ProfesorMateria();Profesor profesor=new ProfesorServiceImpl().findById(Formulario.id(r.getParameter("profesorId")));Materia materia=new MateriaServiceImpl().findById(Formulario.id(r.getParameter("materiaId")));if(profesor==null||materia==null) throw new IllegalArgumentException("El profesor o la materia ya no existen");x.setProfesor(profesor);x.setMateria(materia);return x;}
    protected void opciones(HttpServletRequest r) {r.setAttribute("profesores",new ProfesorServiceImpl().findAll());r.setAttribute("materias",new MateriaServiceImpl().findAll());}
}
