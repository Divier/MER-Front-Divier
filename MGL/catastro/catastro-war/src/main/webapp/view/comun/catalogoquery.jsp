<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="co.com.telmex.catastro.mbeans.comun.cataloglb.CatalogQueryBussiness"%>
<%@page import="co.com.telmex.catastro.data.DataResultquery"%>
<%@page import="co.com.telmex.catastro.data.DataRow"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
   
  <link href="view/css/contenido.css" rel="stylesheet" type="text/css"/>
 <script language="javaScript" src="view/js/paging.js"></script>
   
 <%
  DataResultquery result=(DataResultquery) request.getAttribute("dataResultquery");
   ArrayList nomcq=(ArrayList) request.getAttribute("nomcolumn");
   String namedata= (String) request.getAttribute("nombret");
   if(namedata.equals("MULTIVALOR"))
             {
                    namedata="ESTADO";
             }
    %>


 <script  type="text/javascript">
  function dataselection(qcampo,campoc)
  {
   
   var parameter=[qcampo,campoc];
  
	window.returnValue = parameter;
	window.close();  }</script> 
</script>

   <form id="formquer" action="CatalogQuerySrv" method="post" name="formularioLogin">
   </form>
<title><%=namedata %></title>
<table class="Tabla" id="resultados" align="Center"> 
          <tr> 
             <% for(int i=0;i< nomcq.size();i++){ %> 
             <th class="TablaTitulo TablaTituloCatalogo"><%=nomcq.get(i)%></th>
            <% }%>
              <th class="TablaTitulo TablaTituloCatalogo">ACCIÓN</th>
            </tr> 
          <% List<DataRow> resultquery=null;
            resultquery=(ArrayList) result.getListDataRowq();
             List col=null;
            for(int j=0;j<resultquery.size();j++)
            { if(j % 2 == 0){%>    
                  <tr class="EtiquetaConsulta">
                 <% }else  {%>
                 <tr class="EtiquetaConsulta">
                 <%}%>
             <%  col=(ArrayList) resultquery.get(j).getColumns();
                for(int k=0;k<col.size();k++)
                  {%>
                  <td><%=resultquery.get(j).getColumn(k)%></td>
                  <% }%>
                <td>
                    <input type="hidden" styleClass="TextoConsulta" id="qcampo" name="qcampo" value="<%=resultquery.get(j).getColumn(0)%>"/>
                    <input type="hidden" styleClass="TextoConsulta" id="qcampoc" name="qcampoc" value="<%=resultquery.get(j).getColumn(1)%>"/>
                    <input type="image" src="view/img/select.gif"  onClick="dataselection('<%=resultquery.get(j).getColumn(0)%>','<%=resultquery.get(j).getColumn(1)%>')" />
                </td>            
             <% }%>
             </tr>
        </table>
       <div align="center" style="display:block; border: 0px;" id="NavPosicion"></div>
           
    <script type="text/javascript">
    var pager = new Pager('resultados', 10);
    pager.init();
    pager.showPageNav('pager', 'NavPosicion');
    pager.showPage(1);
    </script>