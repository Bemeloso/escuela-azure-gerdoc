package org.gerdoc.servlet;
import org.gerdoc.model.*;
import org.gerdoc.service.*;
import org.gerdoc.service.impl.*;
import org.gerdoc.util.Formulario;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
@WebServlet({"/AlumnoMateriaServlet","/AlumnoMateria.jsp"})
public class AlumnoMateriaServlet extends CatalogoServlet<AlumnoMateria,Long> {
    private final AlumnoMateriaService service=new AlumnoMateriaServiceImpl();
    protected AlumnoMateriaService service() {return service;}
    protected String nombre() {return "AlumnoMateria";}
    protected String pagina() {return "AlumnoMateria.jsp";}
    protected Long id(String raw) {return Formulario.id(raw);}
    protected AlumnoMateria leer(HttpServletRequest r) {AlumnoMateria x=new AlumnoMateria();Alumno alumno=new AlumnoServiceImpl().findById(Formulario.id(r.getParameter("alumnoId")));Materia materia=new MateriaServiceImpl().findById(Formulario.id(r.getParameter("materiaId")));if(alumno==null||materia==null) throw new IllegalArgumentException("El alumno o la materia ya no existen");x.setAlumno(alumno);x.setMateria(materia);x.setCalificacion(Formulario.nota(r,"calificacion",true));if("update".equals(r.getParameter("accion"))) x.setId(Formulario.id(r.getParameter("id")));return x;}
    protected void opciones(HttpServletRequest r) {r.setAttribute("alumnos",new AlumnoServiceImpl().findAll());r.setAttribute("materias",new MateriaServiceImpl().findAll());}
}
