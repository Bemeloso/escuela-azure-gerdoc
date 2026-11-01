package org.gerdoc.servlet;
import org.gerdoc.model.*;
import org.gerdoc.service.*;
import org.gerdoc.service.impl.*;
import org.gerdoc.util.Formulario;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
@WebServlet({"/MateriaServlet","/Materias.jsp"})
public class MateriaServlet extends CatalogoServlet<Materia,Long> {
    private final MateriaService service=new MateriaServiceImpl();
    protected MateriaService service() {return service;}
    protected String nombre() {return "Materia";}
    protected String pagina() {return "Materias.jsp";}
    protected Long id(String raw) {return Formulario.id(raw);}
    protected Materia leer(HttpServletRequest r) {Materia x=new Materia();x.setNombre(Formulario.texto(r,"nombre"));x.setCreditos(Formulario.entero(r,"creditos",100));if("update".equals(r.getParameter("accion"))) x.setId(Formulario.id(r.getParameter("id")));return x;}
    protected void opciones(HttpServletRequest r) {}
}
