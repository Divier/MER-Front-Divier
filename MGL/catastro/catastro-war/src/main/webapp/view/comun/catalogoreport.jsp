<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
  <link href="view/css/contenido.css" rel="stylesheet" type="text/css"/>
 <script type="text/javascript" src="view/img/jquery-1.3.2.js"></script>
  <script type="text/javascript" src="view/img/vanadium_es.js"></script>
  
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
    ArrayList catalogType= (ArrayList) session.getAttribute("type");
    String total=(String) session.getAttribute("totalc");
    String id=request.getParameter("id");
    String export =(String) session.getAttribute("exportEx");
    String exportpdf= (String) session.getAttribute("exportPdf");
    String exportCvs=(String) session.getAttribute("exportCvs");
    String sqldata=(String) session.getAttribute("sqldata");
    String sqldata1=(String) session.getAttribute("sqldata1");
    DataResult result=(DataResult) session.getAttribute("dataResult");
    String dataex=(String) session.getAttribute("existdata");
    ArrayList nomc=(ArrayList) session.getAttribute("nomcolumn");
    ArrayList param=(ArrayList) session.getAttribute("parameter");
    String totalcolum=(String) session.getAttribute("totalcol");
    
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script language="javaScript" src="view/js/popcalendar.js"></script>
    <script language="javaScript" src="view/js/winmodal.js"></script>
    <script language="javaScript" src="view/js/paging.js"></script>
    <link href="view/css/contenido.css" rel="stylesheet" type="text/css"/>

    <title>Reportes</title>
</head>
<body>
    <div align="center">
      
        <table columns="2"  border="0" class="Tabla"> 
            <form action="CatalogReportSrv" method="post" name="form">
                <tr>
                    <td class="EtiquetaConsulta">Tipo Solicitud</td>
                    <td class="EtiquetaConsulta">
                        <select name="tipo" size="1" class="camposelect"> 
                           <option value="N">Solicitud Negocio</option> 
                           <option value="R">Solicitud de Red</option> 
                        </select> 
                     </td>
                </tr>
                <% for (int i = 0; i < catalogfilterid.size(); i++) {%>  
                <tr>
                    <input type="hidden" id="id<%=i%>" name="id<%=i%>" value="<%=catalogfilterid.get(i)%>"/>
                    <input type="hidden" id="campobd<%=i%>" name="campobd<%=i%>" value="<%=catalogfiltercolumn.get(i)%>"/>
                    <td class="EtiquetaConsulta"><%=catalogfilterlabel.get(i)%></td>
                    <% if(catalogType.get(i).equals("DATE"))
                    {%>               
                    <td class="EtiquetaConsulta">
                        <input  name="valores<%=i%>" value="<%=param.get(i)%>" type="text"  class=":required" id="dateArrival" onclick="popUpCalendar(this,this, 'dd-mm-yyyy');" size="10"/>
                        <img src="view/img/calendar.png"   width="20px" alt="<%=catalogfilterlabel.get(i)%>" onclick="popUpCalendar(this,form.valores<%=i%>, 'dd-mm-yyyy');" />
                <%}%>
               </tr>
               <%}%>          
               <input type="hidden" name="total" value="<%=total%>"/>
               <input type="hidden" name="sqldata" value="<%=sqldata%>"/>
               <input type="hidden" name="sqldata1" value="<%=sqldata1%>"/>
               <input type="hidden" name="id" value="<%=id%>"/>
               <input type="hidden" name="post" value="0"/>
               <tr>
                    <td colspan="2" style="align:center">
                        <center><input type="submit"  class="buttonl"  style="align:center" value="Filtrar"/></center>
                    </td>
            </tr>
              </form>
        </table>
    </div>
          <% if(dataex.equals("true")){%> 
    <form action="CatalogReportSrv" method="post">
     <div id="flotante" style="display:block; align:center">
          <input type="hidden" name="total" value="<%=total%>"/>
         <br>
         <table align="center" class="Tabla" id="resultados" > 
          <tr> 
             <% for(int l=0;l< nomc.size();l++){ %> 
            <th bgColor="#0062b6" style="color: #ffffff; width:auto !important" class="EtiquetaConsulta">
                <%=nomc.get(l)%>
            </th>
            <input type="hidden" name="colum<%=l%>" value="<%=nomc.get(l)%>"/>
            <% }%> 
            </tr> 
           <% List<DataRow> resultfilter=null;
            resultfilter=(ArrayList) result.getListDataRow();
             List col=null;
            for(int j=0;j<resultfilter.size();j++)
           { if(j % 2 == 0){%>    
                  <tr class="EtiquetaConsulta">
                 <% }else  {%>
                 <tr class="EtiquetaConsulta">
                 <%}%>
             <%  col=(ArrayList) resultfilter.get(j).getColumns();
                for(int k=0;k<col.size();k++)
                  {%>
                 <td style="width:auto">
                     <input type="text" class="TextoConsulta" value="<%=resultfilter.get(j).getColumn(k)%>" name="vl<%=j%>c<%=k%>"/>
                 </td>               
                <% }%>                              
                 <%  } %>                  
           </tr>
         </table> 
      <div align="center" style="display:block; border: 0px;" id="NavPosicion"></div>
 <br>  
 <input type="hidden" id="totalcol" name="totalcol" value="<%=nomc.size()%>" />
 <input type="hidden" id="totaldata" name="totaldata" value="<%= session.getAttribute("totalcol")%>" />
 <tr>
 <td>
     <input type="hidden" name="post" value="1"/>
     <% if(export.equals("1")){ %>
     <input type="submit"  class="buttonl"  value="Exportar Excel" action="E" name="export"/>
    </td>
 <% } if(exportpdf.equals("1")){%>
 <td>
     <input type="submit"  class="buttonl"  value="Exportar Pdf" action="P" name="export"/>
 </td>
 <% } if(exportCvs.equals("1")){%>
 <td><input type="submit"  class="buttonl"  value="Exportar Cvs" action="C" name="export"/>
 <%}%>
 </td>
 </tr>
  </div>
  </form>
    <%  } else {%>
    <script>alert("No existe información en ese rango de fechas");</script>
      <%  }%>
    <script type="text/javascript">
    var pager = new Pager('resultados', 10);
    pager.init();
    pager.showPageNav('pager', 'NavPosicion');
    pager.showPage(1);
    </script>
 </body>
</html>