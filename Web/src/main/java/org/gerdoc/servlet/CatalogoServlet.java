package org.gerdoc.servlet;
import org.gerdoc.service.CrudService;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
public abstract class CatalogoServlet<T,I extends Serializable> extends HttpServlet {
    protected abstract CrudService<T,I> service();
    protected abstract String nombre();
    protected abstract String pagina();
    protected abstract I id(String raw);
    protected abstract T leer(HttpServletRequest request);
    protected void opciones(HttpServletRequest request) { }
    protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        req.setCharacterEncoding("UTF-8");resp.setCharacterEncoding("UTF-8");
        HttpSession session=req.getSession();
        if(session.getAttribute("csrf")==null) session.setAttribute("csrf",UUID.randomUUID().toString());
        for(String key:List.of("mensaje","error")) {req.setAttribute(key,session.getAttribute(key));session.removeAttribute(key);}
        String accion=req.getParameter("accion");
        boolean form="new".equals(accion)||"edit".equals(accion);
        try {
            if(form) {
                if("edit".equals(accion)) {
                    T record=service().findById(id(req.getParameter("id")));
                    if(record==null) throw new NoSuchElementException("El registro ya no existe");
                    req.setAttribute("registro",record);req.setAttribute("originalId",req.getParameter("id"));
                }
                opciones(req);
            } else req.setAttribute("registros",service().findAll());
            req.setAttribute("titulo",nombre());
            req.setAttribute("editar","edit".equals(accion));
            req.getRequestDispatcher("/WEB-INF/views/"+nombre()+(form?"Form.jsp":"Lista.jsp")).forward(req,resp);
        } catch(IllegalArgumentException | NoSuchElementException ex) {resp.sendError(400,"Registro inválido o inexistente");}
    }
    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session=req.getSession(false);
        if(session==null || !Objects.equals(session.getAttribute("csrf"),req.getParameter("csrf"))) {resp.sendError(403,"Formulario vencido. Vuelve a abrir el catálogo.");return;}
        try {
            String action=req.getParameter("accion");
            if("delete".equals(action)) service().deleteById(id(req.getParameter("id")));
            else if("update".equals(action)) service().update(id(req.getParameter("id")),leer(req));
            else if("create".equals(action)) service().save(leer(req));
            else throw new IllegalArgumentException("Acción no válida");
            session.setAttribute("mensaje","Operación realizada correctamente.");
        } catch(IllegalArgumentException | NoSuchElementException ex) {session.setAttribute("error",ex.getMessage());}
        catch(org.hibernate.exception.ConstraintViolationException ex) {session.setAttribute("error","No se puede guardar o eliminar: hay una relación duplicada, inexistente o registros dependientes.");}
        catch(RuntimeException ex) {getServletContext().log("Falló una operación del catálogo "+nombre(),ex);session.setAttribute("error","La operación no se guardó. Revisa las relaciones y vuelve a intentarlo.");}
        resp.setStatus(HttpServletResponse.SC_SEE_OTHER);
        resp.setHeader("Location",resp.encodeRedirectURL(req.getContextPath()+"/"+pagina()));
    }
}
