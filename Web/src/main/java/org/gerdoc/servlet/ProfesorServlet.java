package org.gerdoc.servlet;
import org.gerdoc.model.*;
import org.gerdoc.service.*;
import org.gerdoc.service.impl.*;
import org.gerdoc.util.Formulario;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
@WebServlet({"/ProfesorServlet","/Profesores.jsp"})
public class ProfesorServlet extends CatalogoServlet<Profesor,Long> {
    private final ProfesorService service=new ProfesorServiceImpl();
    protected ProfesorService service() {return service;}
    protected String nombre() {return "Profesor";}
    protected String pagina() {return "Profesores.jsp";}
    protected Long id(String raw) {return Formulario.id(raw);}
    protected Profesor leer(HttpServletRequest r) {Profesor x=new Profesor();x.setNombre(Formulario.texto(r,"nombre"));x.setEspecialidad(Formulario.texto(r,"especialidad"));if("update".equals(r.getParameter("accion"))) x.setId(Formulario.id(r.getParameter("id")));return x;}
    protected void opciones(HttpServletRequest r) {}
}
