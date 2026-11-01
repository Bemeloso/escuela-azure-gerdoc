<%@ page pageEncoding="UTF-8" %>
<%@ include file="cabecera.jspf" %>
<div class="d-flex justify-content-between align-items-center mb-3"><h1>Profesor / materia</h1><a class="btn btn-success" href="ProfesorMateriaServlet?accion=new">Agregar</a></div>
<div class="table-responsive"><table class="table table-striped table-bordered align-middle"><thead><tr><th scope="col">Profesor</th><th scope="col">Materia</th><th scope="col">Acciones</th></tr></thead><tbody>
<c:forEach var="r" items="${registros}"><tr><td><c:out value="${r.profesor.nombre}"/></td><td><c:out value="${r.materia.nombre}"/></td><td class="text-nowrap">
<a class="btn btn-warning btn-sm" href="ProfesorMateriaServlet?accion=edit&amp;id=${r.profesor.id}-${r.materia.id}">Editar</a>
<form action="ProfesorMateriaServlet" method="post" class="d-inline" onsubmit="return confirm('¿Eliminar este registro?');">
<input type="hidden" name="csrf" value="<c:out value='${sessionScope.csrf}'/>"><input type="hidden" name="accion" value="delete"><input type="hidden" name="id" value="${r.profesor.id}-${r.materia.id}"><button class="btn btn-danger btn-sm" type="submit">Eliminar</button></form>
</td></tr></c:forEach><c:if test="${empty registros}"><tr><td colspan="3" class="text-center">No hay registros.</td></tr></c:if></tbody></table></div>
<%@ include file="pie.jspf" %>
