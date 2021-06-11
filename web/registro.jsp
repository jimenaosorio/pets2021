<%@include file="templates/header.jsp" %>

<div class="row">
    <div class="col s4 offset-s4 z-depth-3">
        <form action="control.do" method="post">
            <div class="input-field col s12">
                <input id="rut" name="rut" type="text" class="validate">
                <label for="rut">RUT</label>
            </div>
            <div class="input-field col s12">
                <input id="nombre" name="nombre" type="text" class="validate">
                <label for="nombre">Nombre</label>
            </div>
            <div class="input-field col s12">
                <input id="apellido" name="apellido" type="text" class="validate">
                <label for="apellido">Apellido</label>
            </div>
            <div class="input-field col s12">
                <input id="email" name="email" type="text" class="validate">
                <label for="email">E-Mail</label>
            </div>
            <div class="input-field col s12">
                <input id="clave" name="clave" type="password" class="validate">
                <label for="clave">Clave</label>
            </div>
            <div class="input-field col s12">
                <input id="clave2" name="clave2" type="password" class="validate">
                <label for="clave2">Confirme la clave</label>
            </div>
            <button class="btn right" name="boton" value="registrar">Registrar</button>
            <br/><br/><br/>
        </form>
        <p class="red-text">${requestScope.msg}</p>
    </div>
</div>

<%@include file="templates/footer.jsp" %>