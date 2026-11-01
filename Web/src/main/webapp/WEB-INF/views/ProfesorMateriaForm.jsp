<%@ page pageEncoding="UTF-8" %>
<%@ include file="cabecera.jspf" %>
<h1 class="mb-4">${editar ? 'Editar' : 'Agregar'}: Profesor / materia</h1>
<form action="ProfesorMateriaServlet" method="post" style="max-width:640px">
<input type="hidden" name="csrf" value="<c:out value='${sessionScope.csrf}'/>"><input type="hidden" name="accion" value="${editar ? 'update' : 'create'}"><input type="hidden" name="id" value="<c:out value='${originalId}'/>">
<div class="mb-3"><label class="form-label" for="profesorId">Profesor</label><select class="form-select" id="profesorId" name="profesorId" required><option value="">Seleccionar...</option><c:forEach var="opcion" items="${profesores}"><option value="${opcion.id}" ${registro.profesor.id == opcion.id ? 'selected' : ''}><c:out value="${opcion.nombre}"/> (#${opcion.id})</option></c:forEach></select></div>
<div class="mb-3"><label class="form-label" for="materiaId">Materia</label><select class="form-select" id="materiaId" name="materiaId" required><option value="">Seleccionar...</option><c:forEach var="opcion" items="${materias}"><option value="${opcion.id}" ${registro.materia.id == opcion.id ? 'selected' : ''}><c:out value="${opcion.nombre}"/> (#${opcion.id})</option></c:forEach></select></div>
<button type="submit" class="btn btn-primary">Guardar</button> <a href="ProfesorMateria.jsp" class="btn btn-secondary">Cancelar</a></form>
<%@ include file="pie.jspf" %>
