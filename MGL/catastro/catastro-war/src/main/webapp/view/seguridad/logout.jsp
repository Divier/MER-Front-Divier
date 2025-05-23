<%-- 
    Document   : logout
    Created on : 21/08/2012, 11:07:21 AM
    Author     : Ana Maria
--%>
<%@page import="co.com.telmex.catastro.util.FacesUtil"%>
<%@page import="javax.faces.context.FacesContext"%>
<%@page import="javax.faces.context.ExternalContext"%>
    <% 
       HttpSession sesion = request.getSession(); 
         sesion.setAttribute("sesionuser",""); 
         sesion.setAttribute("sesionrol",""); 
         sesion.setAttribute("menu",""); 
         sesion.invalidate(); 

    %>
   
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <script>
        parent.document.location = "<%= request.getContextPath() %>/view/seguridad/login.jsf";
    </script>
 
</html>
