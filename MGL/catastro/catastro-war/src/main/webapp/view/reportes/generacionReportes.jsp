<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
  <link href="view/css/contenido.css" rel="stylesheet" type="text/css"/>

  <style>
      
    .altrowstable{
 border-width: 1px !important;
 padding: 0px;
 border-style: solid;
 border-color: #a9c6c9 !important;
 text-align: center;
 cellspacing:0;
 border-top-color:#F1F1F2;
 
  }
  table.altrowstable td {
 border-width: 1px;
 padding: 2px;
 border-style: solid;
 border-color: #F1F1F2;
 text-align: center;
 }
   .titulonavegacion {
    text-decoration:none;
    color:#0174DF;
    text-align: left;
    padding-left: 23px;
    font-weight: bold;
    font-family: Arial, Helvetica, sans-serif;
 }
 
  </style>
<html>
    
<%@page import="co.com.telmex.catastro.data.DataResult"%>
<%@page import="co.com.telmex.catastro.data.DataTable"%>
<%@page import="co.com.telmex.catastro.data.DataRow"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.lang.String"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.lang.Object"%>
<%@page import="java.util.List"%>

 <% ArrayList catalogfilterid= (ArrayList) session.getAttribute("filter");
 ArrayList catalogfilterlabel= (ArrayList) session.getAttribute("label");
 ArrayList catalogfiltercolumn= (ArrayList) session.getAttribute("column");
 ArrayList datatables=(ArrayList) request.getAttribute("id");
 ArrayList valor=(ArrayList) request.getAttribute("valor");
 ArrayList nomc=(ArrayList) session.getAttribute("nomcolumn");
  String alias=(String) session.getAttribute("alias");

 Object data = session.getAttribute("resultfilter");
 DataResult result=(DataResult) session.getAttribute("dataResult");
 String transaccion =(String) request.getAttribute("transaccion"); 
 String id=request.getParameter("id");
  String c=request.getParameter("c");
  String ct=request.getParameter("ctl");
  String index=request.getParameter("index");
%>
<script>
    function doLoad()
{
    // the timeout value should be the same as in the "refresh" meta-tag
    setTimeout( "refresh()", 2*1000 );
}
   <% if(transaccion=="ok") {%>
    alert("La operaciÃ³n se realizo con exito");
   <%} %>
</script>
<script language="javascript">
</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src="view/js/popcalendar.js"></script>
<script language="JavaScript" src="view/js/winmodal.js"></script>
<title>Generación de Reportes</title>
    </head>
    <body onload="doLoad()">
        <div align="left" class="titulonavegacion">Reporte <%=alias%></div>
        <div align="center">
      <table columns="2" > 
    <form action="CatalogDataSrv" method="post">
          <% for(int i=0;i< catalogfilterid.size();i++)
            {%>  
            <tr><input type="hidden" id="id<%=i%>" name="id<%=i%>" value="<%=catalogfilterid.get(i)%>"/>
              <input type="hidden" id="campobd<%=i%>" name="campobd<%=i%>" value="<%=catalogfiltercolumn.get(i)%>"/>
               <td><%=catalogfilterlabel.get(i)%></td>
              
                <td ><input type="text"  name="valor<%=i%>" id="valor<%=i%>"/><input type="hidden" name="index" id="index" value="<%=index%>" /><input type="hidden" name="idp" id="idp" value="<%=id%>" /><input type="hidden" name="index" id="id" value="<%=id%>" /></td>
              </tr>
            <%}%>
            <tr><td colspan="2" align="center"><input type="hidden" name="ctlconsulta" id="ctlconsulta" value="<%=ct%>"><input type="hidden" name="ctl" id="ctl" value="<%=c%>"/><input type="hidden" name="total" valor="<%=session.getAttribute("totalfilter")%>"/><input type="submit"  class="buttonm"  value="Consultar"/></td></tr>
         </form>
        </table>
        </div>
     <div id="flotante" style="display:block; align:center">
         <br>
         <table align="center" class="altrowstable" > 
          <tr> 
             <% for(int i=0;i< nomc.size();i++){ %> 
             <th bgColor="#0062b6" style="color: #ffffff;" class="titulotabla"><%=nomc.get(i)%></th>
            <% }%>
              <th bgColor="#0062b6" style="color: #ffffff;" class="titulotabla">ACCION</th>
            </tr> 
           <% List<DataRow> resultfilter=null;
            resultfilter=(ArrayList) result.getListDataRow();
             List col=null;
            for(int j=0;j<resultfilter.size();j++)
           { if(j % 2 == 0){%>    
                  <tr class="contenidotelmex">
                 <% }else  {%>
                 <tr class="contenidotelmex">
                 <%}%>
             <%  col=(ArrayList) resultfilter.get(j).getColumns();
                for(int k=0;k<col.size();k++)
                  {%>
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
 
 
   <div align="center">
   <form action="Catalogodetalle" method="get"><input type="hidden" name="id" id="id" value="<%=id%>" /><input type="hidden" name="idcol" id="idcol" value="0" /> <input type="hidden" name="tipooperacion" id="tipooperacion" value="CREATE" /> <input type="submit" class="buttonm" value="Nuevo <%=alias%>" action="2" name="accion"/></form>
 </div>
  </div>
  </body>
</html>