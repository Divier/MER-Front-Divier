<%-- 
    Document   : newjsp
    Created on : 17/02/2012, 03:53:20 PM
        Author     : Ana Maria
--%>

<%@page import="co.com.telmex.catastro.data.DataRow"%>
<%@page import="java.util.List"%>
<%@page import="co.com.telmex.catastro.data.DataResult"%>
<%@page import="java.util.ArrayList"%>
<%@page import="co.com.telmex.catastro.mbeans.comun.cataloglb.CatalogDataBussiness"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
  <link href="view/css/contenido.css" rel="stylesheet" type="text/css"/>
  <script language="javaScript" src="view/js/paging.js"></script>

  <script language="JavaScript">
  function visualizar()
  {
   parent.frames['resultsFrame'].location="/catastro-warIns/view/comun/catalogfooter.jsp";
  }
 </script>
 <style>
     .buttonc{
    width: 96px;
    height:26px;
    background-position: center;
    background-image:url("view/css/wps_buttoninput.png");
    background-repeat:no-repeat;
    font-weight: bold;
    font-family: Arial, Helvetica, sans-serif;
    text-align: center;
    color:#FFFFFF;
    border-width: 1px;
    border-collapse: collapse;
    border-color: #E6E6E6;
    background-position: 0 3px;
    border: 0 none #CCCCCC;
    font-size: 11px;
    font-weight: normal;
    height: 25px;
    margin: 0;
    padding: 0;
    text-decoration: none;
  }
  
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
 <%ArrayList datatables=(ArrayList) request.getAttribute("id");
 ArrayList valor=(ArrayList) request.getAttribute("valor");
 ArrayList nomc=(ArrayList) session.getAttribute("nomcolumn");
 Object data = session.getAttribute("resultfilter");
 DataResult result=(DataResult) session.getAttribute("dataResult");
  String id=(String) request.getAttribute("idp");    
 String existdata=(String) session.getAttribute("existdata");             
%>
  <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
     <div align="left" class="titulonavegacion"><img src="view/img/flec.png"/><a href="javascript:history.back();">Regresar</a> </div>
      <%if(nomc.isEmpty()==false)
     { %>
   
        <table align="center" border="1" class="altrowstable" id="resultados"> 
          <tr> 
             <% for(int i=0;i< nomc.size();i++)
             { %> 
              <th bgColor="#0062b6" style="color: #ffffff;" class="titulotabla"><%=nomc.get(i)%></th>
            <% }%>
               <th bgColor="#0062b6" style="color: #ffffff;" class="titulotabla">ACCION</th>
            </tr> 
           <% List<DataRow> resultfilter=null;
            resultfilter=(ArrayList) result.getListDataRow();
             List col=null;
             if(resultfilter!=null)
             {
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
               <td><form action="Catalogodetalle" method="get">
                         <input type="hidden" name="idcol" id="idcol" value="<%=resultfilter.get(j).getColumn(0)%>" />
                         <input type="hidden" name="id" id="id" value="<%=id%>" />
                        <input type="image" src="view/img/select.gif"  onclick="visualizar();"/></form></td>
            
             <%  }%>
     
 <%  }%></tr>
                 
   </table>
  <div style="display:block; border: 0px;" id="NavPosicion"></div>
   <%  } else {%>
   <script> alert("Datos no encontrados");</script>

   <%  }%>
  
   <% if(existdata.equals("false")) {%>
    <script> alert("Datos no existentes en Base de Datos, intente con otros datos");
        history.go(-1);
    </script>
   <%  }%>
    <br>
 <script type="text/javascript">
    var pager = new Pager('resultados', 10);
    pager.init();
    pager.showPageNav('pager', 'NavPosicion');
    pager.showPage(1);
    </script>  
   <br>
    <br>
 
</html>
