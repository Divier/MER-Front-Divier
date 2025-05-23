<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<link href="view/css/contenido.css" rel="stylesheet" type="text/css"/>

<html>

    <%@page import="co.com.telmex.catastro.data.DataResult"%>
    <%@page import="co.com.telmex.catastro.data.DataTable"%>
    <%@page import="co.com.telmex.catastro.data.DataRow"%>
    <%@page import="java.util.ArrayList"%>
    <%@page import="java.lang.String"%>
    <%@page import="java.util.Iterator"%>
    <%@page import="java.lang.Object"%>
    <%@page import="java.util.List"%>

    <% ArrayList catalogfilterid = (ArrayList) session.getAttribute("filter");
        ArrayList catalogfilterlabel = (ArrayList) session.getAttribute("label");
        ArrayList catalogfiltercolumn = (ArrayList) session.getAttribute("column");
        ArrayList datatables = (ArrayList) request.getAttribute("id");
        ArrayList valor = (ArrayList) request.getAttribute("valor");
        ArrayList nomc = (ArrayList) session.getAttribute("nomcolumn");
        String alias = (String) session.getAttribute("alias");

        Object data = session.getAttribute("resultfilter");
        DataResult result = (DataResult) session.getAttribute("dataResult");
        String transaccion = (String) request.getAttribute("transaccion");
        String id = request.getParameter("id");
        String c = request.getParameter("c");
        String ct = request.getParameter("ctl");
        String index = request.getParameter("index");
    %>
    <script>
        function ocultar_tabla()
        {
            div = document.getElementById('flotante');
            div.style.display = 'none';
            parent.document.body.rows = '100,600*';
        }
        <% if (transaccion == "ok") {%>
        alert("La operacion se realizo con exito");
        <%}%>
    </script>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script language="JavaScript" src="view/js/popcalendar.js"></script>
        <script language="JavaScript" src="view/js/winmodal.js"></script>
        <script language="javaScript" src="view/js/paging.js"></script>
        <title>Catalogo</title>
    </head>



    <body>

        <div align="center"> 
            <div id="espacio" style="width: 766px;">
                <div id="cabecera" style="background-image: url(view/img/modulo_top.jpg); height: 83px;  width:766px;"></div>
                <div id="return"  align="left"  >
                    <button id="contenido_return" style= "background:url(view/img/backMenuHomePoliedro-3.gif);padding-left: 25px;padding-top: 8px;" onclick="window.location = '${sessionScope['urlVisitasTecnicas']}/Visitas_Tecnicas/faces/Gestion.jsp';"> Regresar al menu </button>
                </div>
                <div style= "background:url('view/img/rihc-header_02.png');width: 766px;" >
                    <div id="contenedorespacio" >
                        <div style= "height:30px; width:766px; text-align: center; font-size: 13px;padding-top: 8px;"> <b>Consulta <%=alias%></b> </div>
                    </div>
                </div>
            </div>
            <table columns="2" > 
                <form action="CatalogDataSrv" method="post">
                    <% for (int i = 0; i < catalogfilterid.size(); i++) {%>  
                    <tr>
                    <td class="EtiquetaConsulta">
                        <input type="hidden" id="id<%=i%>" name="id<%=i%>" value="<%=catalogfilterid.get(i)%>"/>
                        <input type="hidden" id="campobd<%=i%>" name="campobd<%=i%>" value="<%=catalogfiltercolumn.get(i)%>"/>
                        <%=catalogfilterlabel.get(i)%>
                    </td>
                    <td >
                        <input type="text"  name="valor<%=i%>" id="valor<%=i%>"/>
                        <input type="hidden" name="index" id="index" value="<%=index%>" />
                        <input type="hidden" name="idp" id="idp" value="<%=id%>" />
                        <input type="hidden" name="index" id="id" value="<%=id%>" />
                    </td>
                    </tr>
                    <%}%>
                    <tr>
                    <td colspan="2" >
                        <input type="hidden" name="ctlconsulta" id="ctlconsulta" value="<%=ct%>">
                        <input type="hidden" name="ctl" id="ctl" value="<%=c%>"/>
                        <input type="hidden" name="total" value="<%=session.getAttribute("totalfilter")%>"/>
                        <input type="submit"  class="buttonl"  value="Consultar"/>
                    </td>
                    </tr>
                </form>
            </table>
        </div>
        <div id="flotante" style="display:block; align:center">
            <br>
            <table align="center" class="Tabla" id="resultados"> 
                <tr> 
                    <% for (int i = 0; i < nomc.size(); i++) {%> 
                <th class="TablaTitulo TablaTituloCatalogo"><%=nomc.get(i)%></th>
                    <% }%>
                <th class="TablaTitulo TablaTituloCatalogo">ACCIÓN</th>
                </tr> 
                <% List<DataRow> resultfilter = null;
                    resultfilter = (ArrayList) result.getListDataRow();
                    List col = null;
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
                <td><form action="Catalogodetalle" method="get" >
                        <input type="hidden" name="idcol" id="idcol" value="<%=resultfilter.get(j).getColumn(0)%>" />
                        <input type="hidden" name="id" id="id" value="<%=id%>" /><input type="hidden" name="tipooperacion" id="tipooperacion" value="UPDATE" />
                        <input type="image" src="view/img/select.gif"  onclick="visualizar();"/></form></td>
                        <%  }%>
                </tr>
            </table> 
            <br>
            <div align="center" style="display:block; border: 0px;" id="NavPosicion"></div>

            <div align="center">
                <form action="Catalogodetalle" method="get">
                    <input type="hidden" name="id" id="id" value="<%=id%>" />
                    <input type="hidden" name="idcol" id="idcol" value="0" /> 
                    <input type="hidden" name="tipooperacion" id="tipooperacion" value="CREATE" /> 
                    <input type="submit" class="buttonl" value="Nuevo <%=alias%>" action="2" name="accion"/>
                </form>
            </div>
        </div>
        <div align="center"> 
            <div id="top">
                <div id="footer">
                    <div id="contenidofoot" >Copyright © 2008 TELMEX HOGAR S.A. - Todos los Derechos Reservados</div>
                </div>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        var pager = new Pager('resultados', 10);
        pager.init();
        pager.showPageNav('pager', 'NavPosicion');
        pager.showPage(1);
    </script>
</html>