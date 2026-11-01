<%@ page pageEncoding="UTF-8" %>
<%@ include file="cabecera.jspf" %>
<h1 class="mb-4">${editar ? 'Editar' : 'Agregar'}: Profesores</h1>
<form action="ProfesorServlet" method="post" style="max-width:640px">
<input type="hidden" name="csrf" value="<c:out value='${sessionScope.csrf}'/>"><input type="hidden" name="accion" value="${editar ? 'update' : 'create'}"><input type="hidden" name="id" value="<c:out value='${originalId}'/>">
<div class="mb-3"><label class="form-label" for="nombre">Nombre</label><input class="form-control" id="nombre" name="nombre" type="text" required maxlength="100" value="<c:out value='${registro.nombre}'/>"></div>
<div class="mb-3"><label class="form-label" for="especialidad">Especialidad</label><input class="form-control" id="especialidad" name="especialidad" type="text" required maxlength="100" value="<c:out value='${registro.especialidad}'/>"></div>
<button type="submit" class="btn btn-primary">Guardar</button> <a href="Profesores.jsp" class="btn btn-secondary">Cancelar</a></form>
<%@ include file="pie.jspf" %>
