<%@ page pageEncoding="UTF-8" %>
<%@ include file="cabecera.jspf" %>
<div class="d-flex justify-content-between align-items-center mb-3"><h1>Alumno / materia</h1><a class="btn btn-success" href="AlumnoMateriaServlet?accion=new">Agregar</a></div>
<div class="table-responsive"><table class="table table-striped table-bordered align-middle"><thead><tr><th scope="col">ID</th><th scope="col">Alumno</th><th scope="col">Materia</th><th scope="col">Calificación</th><th scope="col">Acciones</th></tr></thead><tbody>
<c:forEach var="r" items="${registros}"><tr><td><c:out value="${r.id}"/></td><td><c:out value="${r.alumno.nombre}"/></td><td><c:out value="${r.materia.nombre}"/></td><td><c:out value="${r.calificacion}"/></td><td class="text-nowrap">
<a class="btn btn-warning btn-sm" href="AlumnoMateriaServlet?accion=edit&amp;id=${r.id}">Editar</a>
<form action="AlumnoMateriaServlet" method="post" class="d-inline" onsubmit="return confirm('¿Eliminar este registro?');">
<input type="hidden" name="csrf" value="<c:out value='${sessionScope.csrf}'/>"><input type="hidden" name="accion" value="delete"><input type="hidden" name="id" value="${r.id}"><button class="btn btn-danger btn-sm" type="submit">Eliminar</button></form>
</td></tr></c:forEach><c:if test="${empty registros}"><tr><td colspan="5" class="text-center">No hay registros.</td></tr></c:if></tbody></table></div>
<%@ include file="pie.jspf" %>
