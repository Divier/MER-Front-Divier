
<%@page import="java.util.ArrayList"%>
<%@page import="java.lang.String"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.lang.Object"%>
<%@page import="java.util.List"%>
<%@page import="co.com.telmex.catastro.data.DataRow"%>
<%@page import="java.util.List"%>
<%@page import="co.com.telmex.catastro.data.DataResult"%>
<%@page import="java.util.ArrayList"%>
<%@page import="co.com.telmex.catastro.mbeans.comun.cataloglb.CatalogDetailBussiness"%>

<%ArrayList datatables = (ArrayList) request.getAttribute("datatable");
    ArrayList datatablestipo = (ArrayList) request.getAttribute("datatable_tipo");
    ArrayList valores = (ArrayList) request.getAttribute("valores");
    ArrayList columnull = (ArrayList) request.getAttribute("columnnull");
    ArrayList visibility = (ArrayList) request.getAttribute("visibility");
    ArrayList relacion = (ArrayList) request.getAttribute("relacion");
    ArrayList query = (ArrayList) request.getAttribute("query");
    String crea = (String) request.getAttribute("crea");
    String modifica = (String) request.getAttribute("modifica");
    String elimina = (String) request.getAttribute("elimina");
    String id = (String) request.getAttribute("id");
    String idp = (String) request.getParameter("id");
    String total = (String) request.getAttribute("total");
    String op = (String) session.getAttribute("op");
    ArrayList pk = (ArrayList) request.getAttribute("pk");
    ArrayList nomcrel = null;
    String alias = (String) session.getAttribute("alias");
    String tablarel = (String) session.getAttribute("tablarel");
    ArrayList nombrer = (ArrayList) request.getAttribute("nombrer");
    ArrayList longitud = (ArrayList) request.getAttribute("longitud");


    if (request.getAttribute("nomcolumnrel") != null) {
        nomcrel = (ArrayList) request.getAttribute("nomcolumnrel");
    }
    DataResult resultrel = (DataResult) request.getAttribute("dataResultrel");
    String idcol = (String) request.getParameter("idcol");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<link href="view/css/contenido.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="view/img/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="view/img/vanadium_es.js"></script>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="JavaScript" src="view/js/popcalendar.js"></script>
    <script language="JavaScript" src="view/js/winmodal.js"></script>

    <script language="JavaScript">
        function query(datasearch)
        {
            window.open('CatalogQuerySrv','','width=500, height=600');
        }
        function asignarpk(valor)
        {
            document.getElementById(idpk).val=valor;
            alert( document.getElementById(idpk));   
        }
  
        function visualizar()
        {
            parent.frames['resultsFrame'].location="/catastro-warIns/view/comun/catalogfooter.jsp";
        }
  
  
    </script>
    <title>Detalle Catalogo</title>
</head>
<body>
</body>
<html>
    <div align="left" class="TituloSeccion"><a href="javascript:history.back();">Consulta <%=alias%></a> <img src="view/img/flec.png"/>Manejo <%=alias%></div>
    <center>
        <br>
            <div>
                <div style="background-image: url(view/img/titulop.png); width: 100%;"><span style="color: #FFFFFF;">Manejo <%=alias%></span></div>
                <div id="tabla" >
                    <form id="formquery" action="Catalogodetalle" method="post">
                        <table class="Tabla" value="datatables">
                            <%
                 for (int l = 0; l < datatables.size(); l++) {%>  
                            <%if (visibility.get(l).equals(true) && (pk.get(l).equals(false))) {%>
                            <tr>
                                <td class="EtiquetaConsulta"><b><%=datatables.get(l)%></b></td>
                                <%} else if (pk.get(l).equals(true) && op.equals("CREATE") == false) {%>
                                    <tr>
                                        <input type="hidden" name="fecha<%=l%>" value="<%= datatables.get(l)%>"/>
                                        <td class="EtiquetaConsulta"><b><%=datatables.get(l)%></b></td>
                                <%}%> 
                                <% if (datatablestipo.get(l).equals("VARCHAR2") && (relacion.get(l).equals(""))) {
                                        if (visibility.get(l).equals(true) && (pk.get(l).equals(false))) {
                                    %>
                                        <td class="EtiquetaConsulta">
                                            <input type="Text"  class=":required :max_length;<%=longitud.get(l)%>"   name="valores<%=l%>" value="<%=valores.get(l)%>"/>
                                            <input type="hidden" name="pk<%=l%>" id="pk<%=l%>" value="<%=pk.get(l)%>"/>
                                            <input type="hidden" name="fecha<%=l%>" value="<%= datatables.get(l)%>"/>
                                 <%} else if (pk.get(l).equals(true) && op.equals("CREATE") == false) {%>
                                            <td class="EtiquetaConsulta">
                                                <input type="Text" readonly="readonly" disabled="disabled" name="valores<%=l%>" value="<%=valores.get(l)%>" />
                                                <input type="hidden" name="pk<%=l%>" id="pk<%=l%>" value="<%=pk.get(l)%>"/>
                                 <%} else {%>
                                                <input type="hidden" name="fecha<%=l%>" value="<%= datatables.get(l)%>"/>
                                                <input type="hidden" name="valores<%=l%>" value="<%=valores.get(l)%>"/>
                                                <input type="hidden" name="pk<%=l%>" id="pk<%=l%>" value="<%=pk.get(l)%>"/>
                                 <%}%>
                                                <input type="hidden" name="nulc<%=l%>" value="<%=columnull.get(l)%>"/>
                                            </td>
                                 <%}%>

                                        <% if (datatablestipo.get(l).equals("NUMBER") && (relacion.get(l).equals(""))) {
                                                if (visibility.get(l).equals(true) && (pk.get(l).equals(false))) { %>
                                        <td class="EtiquetaConsulta">
                                            <input type="Text"  class=":required :number :max_length;<%=longitud.get(l)%>"   name="valores<%=l%>" value="<%=valores.get(l)%>"/>
                                            <input type="hidden" name="fecha<%=l%>" value="<%= datatables.get(l)%>"/>
                                            <input type="hidden" name="pk<%=l%>" id="pk<%=l%>" value="<%=pk.get(l)%>"/>
                                        <%} else if (pk.get(l).equals(true) && op.equals("CREATE") == false) {%>
                                                <td class="EtiquetaConsulta">
                                                    <input type="Text" readonly="readonly" disabled="disabled" name="valores<%=l%>" value="<%=valores.get(l)%>"/>
                                                    <input type="hidden" name="pk<%=l%>" id="pk<%=l%>" value="<%=pk.get(l)%>"/>
                                        <%} else {%>
                                                    <input type="hidden" name="valores<%=l%>" value="<%=valores.get(l)%>"/>
                                                    <input type="hidden" name="fecha<%=l%>" value="<%= datatables.get(l)%>"/>
                                                    <input type="hidden" name="pk<%=l%>" id="pk<%=l%>" value="<%=pk.get(l)%>"/>
                                         <%}%>
                                                    <input type="hidden" name="nulc<%=l%>" value="<%=columnull.get(l)%>"/>
                                                </td>
                                         <%}%>
                                         <% if (datatablestipo.get(l).equals("TIMESTAMP(6)")) {
                                                if (visibility.get(l).equals(true)) {%>
                                            
                                                <td class="EtiquetaConsulta">
                                                    <input  name="valores<%=l%>" value="<%=valores.get(l)%>" type="text" id="dateArrival" onClick="popUpCalendar(this,this, 'dd-mm-yyyy');" size="10" />
                                                    <input type="hidden" name="pk<%=l%>" id="pk<%=l%>" value="<%=pk.get(l)%>"/>
                                         <%} else {%>
                                                    <input type="hidden" name="fecha<%=l%>" value="<%= datatables.get(l)%>"/>
                                                    <input  name="valores<%=l%>" value="<%=valores.get(l)%>" type="hidden" id="dateArrival" onClick="popUpCalendar(this,this, 'dd-mm-yyyy');" size="10"/>
                                         <%}%>
                                                    <input type="hidden" name="nulc<%=l%>" value="<%=columnull.get(l)%>"/>
                                                    <input type="hidden" name="pk<%=l%>" id="pk<%=l%>" value="<%=pk.get(l)%>"/>
                                                </td>
                                        <%}%>
                                        <% if (relacion.get(l).equals("") == false) {%>
                                                <td class="EtiquetaConsulta"> 
                                                    <input type="hidden" id="valores<%=l%>" name="valores<%=l%>" size="20" value="<%=valores.get(l)%>" redonly="redonly"/>
                                                <% if (nombrer.get(l) == null) {%>
                                                    <input  id="val<%=l%>" type="text" name="val<%=l%>" size="20" value="" readonly="readonly" class=" :required" />
                                                <%} else {%> 
                                                    <input  id="val<%=l%>" type="text" name="val<%=l%>" size="20" value="<%=nombrer.get(l)%>" redonly="redonly" class=" :required"/>
                                                <%}%>
                                                    <input type="hidden" name="fecha<%=l%>" value="<%= datatables.get(l)%>"/>  
                                                    <input id="valoresi<%=l%>" type="hidden" name="valores<%=l%>" size="10" value="<%=valores.get(l)%>"/>
                                                    <input type="hidden" name="nulc<%=l%>" value="<%=columnull.get(l)%>"/>
                                                    <input type="hidden" name="ctlquery" value="<%=query.get(l)%>"/>
                                                    <input type="hidden" name="pk<%=l%>" id="pk<%=l%>" value="<%=pk.get(l)%>"/>
                                                    <img src="view/img/busqueda.png"  width="20px" alt="busqueda"  onclick="openselection('CatalogQuerySrv?t=<%=relacion.get(l)%>&c=<%=query.get(l)%>','val<%=l%>','valores<%=l%>','cdata')" />
                                                </td>
                                                <%}%>
                                                        </tr>
                                <%}%>
                                                            <tr>
                                                                <td colspan="4" align="center">
                                                                    <% if (modifica.equals("1") && idcol.equals("0") == false) {%>
                                                                    <input type="hidden" name="id" value="<%=id%>"/>
                                                                    <input type="hidden" name="total" value="<%=total%>"/>
                                                                    <input type="hidden" name="elimina" value="<%=elimina%>"/>
                                                                    <input type="hidden" name="oper" id="oper" value="UPDATE" />                                                                        
                                                                    <input type="submit" class="buttonl" value="Modificar" action="1" name="accion" 
                                                                           onclick="return confirm('�Est� seguro que desea Modificar el registro?');"/> 
                                                                    <%}%> <% if (crea.equals("1") && idcol.equals("0") == true) {%>
                                                                    <input type="submit" class="buttonl" value="Guardar" action="2" name="accion" 
                                                                           onclick="return confirm('�Est� seguro que desea crear el registro?');" />
                                                                    <%}%><% if (elimina.equals("1") && idcol.equals("0") == false) {%>
                                                                    <input type="hidden" name="id" id="id" value="<%=id%>"/>
                                                                    <input type="submit" class="buttonl" value="Eliminar" name="accion"  
                                                                           onclick="return confirm('�Est� seguro que desea Eliminar el registro?');"/>
                                                                        </form>      
                                                                        <%}
                                                                    if (request.getAttribute("resdata").equals("0") && idcol.equals("0") == false) {%>
                                                                        <form  action="CatalogRelationSrv" method="get" >
                                                                            <input type="hidden" name="id" id="id" value="<%=idp%>" />
                                                                            <input type="hidden" name="oper" id="oper" value="CREATE" />
                                                                            <input type="hidden" name="idcol" id="idcol" value="0" />
                                                                            <input type="submit" value="Nuevo <%=tablarel%>" class="buttonl"/>
                                                                            <input type="hidden" name="tipooperacion" id="tipooperacion" value="CREATE" />
                                                                        </form>
                                                                        </td> 
                                                                <%}%>
                                                            </tr>
                                                            </table>
                                                            </div>
                                                            </div>
                                                            <%if (nomcrel.isEmpty() == false) {%>
                                                            <center>
                                                                <br>
                                                                    <div class="estilost">
                                                                        <div style="background-image: url(view/img/titulop.png); width: 100%;"><span style="color: #FFFFFF;"><%=tablarel%></span></div>
                                                                        <br>
                                                                            <table align="center" class="Tabla"> 
                                                                                <tr> 
                                                                                    <% for (int i = 0; i < nomcrel.size(); i++) {%> 
                                                                                    <th class="TablaTitulo TablaTituloCatalogo"><%=nomcrel.get(i)%></th>

                                                                                    <% }%>

                                                                                    <% List<DataRow> resultfilter = null;%>
                                                                                    <th class="TablaTitulo TablaTituloCatalogo">ACCI�N</th>
                                                                                </tr> 
                                                                                <%
                                                                                    resultfilter = (ArrayList) resultrel.getListDataRow();
                                                                                    List col = null;
                                                                                    if (resultfilter.size() != 0) {

                                                                                        for (int j = 0; j < resultfilter.size(); j++) {
                        if (j % 2 == 0) {%>    
                                                                                <tr class="EtiquetaConsulta">
                                                                                    <% } else {%>
                                                                                    <tr class="EtiquetaConsulta">
                                                                                        <%}%>

                                                                                        <%  col = (ArrayList) resultfilter.get(j).getColumns();
                 for (int k = 0; k < col.size(); k++) {%>  
                                                                                        <td><%=resultfilter.get(j).getColumn(k)%></td>
                                                                                        <% }%>
                                                                                        <td>
                                                                                            <form  action="CatalogRelationSrv" method="get" >
                                                                                                <input type="hidden" name="id" id="id" value="<%=idp%>" />
                                                                                                <input type="hidden" name="idcol" id="idcol" value="<%=resultfilter.get(j).getColumn(0)%>" />
                                                                                                <input type="hidden" name="name" id="name" value="<%=resultfilter.get(j).getColumn(1)%>" />
                                                                                                <input type="image" src="view/img/select.gif" /></td>
                                                                                        </form>
                                                                                        <%  }
                 }%>
                                                                                    </tr></table> 
                                                                            <br>
                                                                                <div align="center">
                                                                                    <form  action="CatalogRelationSrv" method="get" >
                                                                                        <input type="hidden" name="id" id="id" value="<%=idp%>" />
                                                                                        <input type="hidden" name="idcol" id="idcol" value="0" />
                                                                                        <input type="submit" value="Nuevo <%=tablarel%>" class="buttonl" /> 
                                                                                        <input type="hidden" name="tipooperacion" id="tipooperacion" value="CREATE" />
                                                                                        </td>
                                                                                        </form>  
                                                                                </div>
                                                                                </div>
                                                                                <br>
                                                                                    <table border="0" width="20%">
                                                                                        <tr>
                                                                                            <td align=center class="contenidoPagination"><%out.print(request.getAttribute("page").toString());%></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                    <%  }%>
                                                                                    <br>
                                                                                        <br>
                                                                                            <div align="center">
                                                                                                <form target="_top" action="CatalogRelationSrv" method="get" >
                                                                                                    <input type="hidden" name="id" id="id" value="<%=idp%>" />
                                                                                                    <input type="hidden" name="oper" id="oper" value="CREATE" />
                                                                                                    <input type="hidden" name="idcol" id="idcol" value="0" />
                                                                                                    <input type="submit" value="Nuevo <%=tablarel%>" class="buttonl"> </td>
                                                                                                </form>  
                                                                                            </div>

                                                                                            <br>
                                                                                                <table border="0" width="20%">
                                                                                                    <tr>
                                                                                                        <td align=center class="contenidoPagination"><%out.print(request.getAttribute("page").toString());%></td>
                                                                                                    </tr>
                                                                                                </table>
                                                                                                </html>  