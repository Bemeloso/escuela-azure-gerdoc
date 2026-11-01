<%@ page pageEncoding="UTF-8" %>
<%@ include file="cabecera.jspf" %>
<h1 class="mb-4">${editar ? 'Editar' : 'Agregar'}: Alumnos</h1>
<form action="AlumnoServlet" method="post" style="max-width:640px">
<input type="hidden" name="csrf" value="<c:out value='${sessionScope.csrf}'/>"><input type="hidden" name="accion" value="${editar ? 'update' : 'create'}"><input type="hidden" name="id" value="<c:out value='${originalId}'/>">
<div class="mb-3"><label class="form-label" for="nombre">Nombre</label><input class="form-control" id="nombre" name="nombre" type="text" required maxlength="100" value="<c:out value='${registro.nombre}'/>"></div>
<div class="mb-3"><label class="form-label" for="edad">Edad</label><input class="form-control" id="edad" name="edad" type="number" required min="1" max="120" step="1" value="<c:out value='${registro.edad}'/>"></div>
<div class="mb-3"><label class="form-label" for="promedio">Promedio</label><input class="form-control" id="promedio" name="promedio" type="number" required min="0" max="10" step="0.01" value="<c:out value='${registro.promedio}'/>"></div>
<button type="submit" class="btn btn-primary">Guardar</button> <a href="Alumnos.jsp" class="btn btn-secondary">Cancelar</a></form>
<%@ include file="pie.jspf" %>
