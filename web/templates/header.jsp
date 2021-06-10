<%@page import="javax.naming.InitialContext"%>
<%@page import="servicio.ServicioBeanLocal"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%! private ServicioBeanLocal servicio; %>
        
        <%
        InitialContext ctx=new InitialContext();
        servicio=(ServicioBeanLocal) ctx.lookup("java:global/EjemploJPA/ServicioBean!servicio.ServicioBeanLocal");
        %>
        
        <c:set var="productos" scope="page" value="<%=servicio.getProductos()%>"/>
        
<!DOCTYPE html>
<html>
    <head>
        <!--Import Google Icon Font-->
      <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
      <!--Import materialize.css-->
      <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>

      <!--Let browser know website is optimized for mobile-->
      <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>APP Mascotas</title>
    </head>
    <body>
        
