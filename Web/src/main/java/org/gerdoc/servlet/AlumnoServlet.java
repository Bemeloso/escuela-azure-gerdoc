package org.gerdoc.servlet;
import org.gerdoc.model.*;
import org.gerdoc.service.*;
import org.gerdoc.service.impl.*;
import org.gerdoc.util.Formulario;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
@WebServlet({"/AlumnoServlet","/Alumnos.jsp"})
public class AlumnoServlet extends CatalogoServlet<Alumno,Long> {
    private final AlumnoService service=new AlumnoServiceImpl();
    protected AlumnoService service() {return service;}
    protected String nombre() {return "Alumno";}
    protected String pagina() {return "Alumnos.jsp";}
    protected Long id(String raw) {return Formulario.id(raw);}
    protected Alumno leer(HttpServletRequest r) {Alumno x=new Alumno();x.setNombre(Formulario.texto(r,"nombre"));x.setEdad(Formulario.entero(r,"edad",120));x.setPromedio(Formulario.nota(r,"promedio",false));if("update".equals(r.getParameter("accion"))) x.setId(Formulario.id(r.getParameter("id")));return x;}
    protected void opciones(HttpServletRequest r) {}
}
