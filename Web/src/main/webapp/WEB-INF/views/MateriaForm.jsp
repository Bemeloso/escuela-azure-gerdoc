<%@ page pageEncoding="UTF-8" %>
<%@ include file="cabecera.jspf" %>
<h1 class="mb-4">${editar ? 'Editar' : 'Agregar'}: Materias</h1>
<form action="MateriaServlet" method="post" style="max-width:640px">
<input type="hidden" name="csrf" value="<c:out value='${sessionScope.csrf}'/>"><input type="hidden" name="accion" value="${editar ? 'update' : 'create'}"><input type="hidden" name="id" value="<c:out value='${originalId}'/>">
<div class="mb-3"><label class="form-label" for="nombre">Nombre</label><input class="form-control" id="nombre" name="nombre" type="text" required maxlength="100" value="<c:out value='${registro.nombre}'/>"></div>
<div class="mb-3"><label class="form-label" for="creditos">Créditos</label><input class="form-control" id="creditos" name="creditos" type="number" required min="1" max="100" step="1" value="<c:out value='${registro.creditos}'/>"></div>
<button type="submit" class="btn btn-primary">Guardar</button> <a href="Materias.jsp" class="btn btn-secondary">Cancelar</a></form>
<%@ include file="pie.jspf" %>
