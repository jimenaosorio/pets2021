<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav>
    <div class="nav-wrapper">
      <a href="#" class="brand-logo">
          <c:if test="${not empty sessionScope.admin}">
              Bienvenido ${sessionScope.admin.nombreUser}
          </c:if>
          <c:if test="${not empty sessionScope.cliente}">
              Bienvenido ${sessionScope.cliente.nombreUser}
          </c:if>
      </a>
      <ul id="nav-mobile" class="right hide-on-med-and-down">
          <c:if test="${not empty sessionScope.admin}">
            <li><a href="misdatos.jsp">Mis Datos</a></li>
            <li><a href="categoria.jsp">Categorias</a></li>
            <li><a href="admin_producto.jsp">Productos</a></li>
            <li><a href="#">Ventas</a></li>
            <li><a href="salir.jsp">Cerrar Sesion</a></li>
          </c:if>
          <c:if test="${not empty sessionScope.cliente}">
            <li><a href="misdatos.jsp">Mis Datos</a></li>
            <li><a href="#">Carrito de compras</a></li>
            <li><a href="#">Mis Compras</a></li>
            <li><a href="producto.jsp">Productos</a></li>
            <li><a href="salir.jsp">Cerrar Sesion</a></li>
          </c:if>
      </ul>
    </div>
  </nav>