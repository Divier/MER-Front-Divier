
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

 <%ArrayList datatables=(ArrayList) request.getAttribute("datatable");
   ArrayList datatablestipo=(ArrayList) request.getAttribute("datatable_tipo");
   ArrayList valores=(ArrayList) request.getAttribute("valores");
   ArrayList columnull=(ArrayList) request.getAttribute("columnnull");
   ArrayList visibility=(ArrayList) request.getAttribute("visibility");
    ArrayList nombrer=(ArrayList) request.getAttribute("nombrer");
   ArrayList relacion=(ArrayList) request.getAttribute("relacion");
   ArrayList query=(ArrayList) request.getAttribute("query");
   String crea=(String) request.getAttribute("crea");
   String modifica=(String) request.getAttribute("modifica");
   String elimina=(String) request.getAttribute("elimina");
   String id=(String) request.getAttribute("id");
   String sqlelimina=(String) request.getAttribute("id");
   String idcol=(String) request.getParameter("idcol");
   String total=(String) request.getAttribute("total");
   ArrayList nomcrel=(ArrayList) session.getAttribute("nomcolumnrel");
   DataResult resultrel=(DataResult) session.getAttribute("dataResultrel");
   String alias=(String) session.getAttribute("alias");
   String tablarel=(String) session.getAttribute("tablarel");     
     ArrayList pk=(ArrayList) request.getAttribute("pk"); 
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
   
  <link href="view/css/contenido.css" rel="stylesheet" type="text/css"/>
  <script type="text/javascript" src="view/img/jquery-1.3.2.js"></script>
  <script type="text/javascript" src="view/img/vanadium_es.js"></script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="JavaScript" src="view/js/popcalendar.js"></script>
<script language="JavaScript" src="view/js/winmodal.js"></script>

<script language="JavaScript">
  function query(datasearch)
  {
   window.open('CatalogQueryRelationSrv','','width=500, height=600');
  }
  function cerrar()
  {
  window.opener = self;
  self.window.close();
  }
  function visualizar()
  {
   parent.frames['resultsFrame'].location="/catastro-warIns/view/comun/catalogfooter.jsp";
  }
 </script>
<title>Detalle Catalogo</title>
</head>
 <body></body>
 <html>
   <div align="left" class="EtiquetaConsulta">
       <a href="javascript:history.go(-2);">Consulta <%=alias%></a> <img src="view/img/flec.png"/>
       </div>
       <div align="left" class="EtiquetaConsulta">
       <a href="javascript:history.back();">Manejo <%=alias%></a> <img src="view/img/flec.png"/> 
       <%=tablarel%>
   </div>
     <div id="tabla" class="Tabla">
       <form id="formquery" action="CatalogRelationSrv" method="post">
           <table  class="Tabla" value="datatables">
              <% 
                for(int i=0;i< datatables.size();i++)
                  { if (visibility.get(i).equals(true)) { %> 
                     <tr>
                         <td class="EtiquetaConsulta"><b><%=datatables.get(i)%></b></td>
                         <%}%>
                        <% if(datatablestipo.get(i).equals("VARCHAR2") && (relacion.get(i).equals("")) || datatablestipo.get(i).equals("NUMBER") && (relacion.get(i).equals("")))
                            {   if (visibility.get(i).equals(true)) {%>
                                <td class="EtiquetaConsulta">
                                    <input type="Text" name="valores<%=i%>" value="<%=valores.get(i)%>" size="25"/>
                                    <%} else{ %>
                                    <input type="hidden" name="valores<%=i%>" value="<%=valores.get(i)%>"/>
                                    <%}%>
                                    <input type="hidden" name="nulc<%=i%>" value="<%=columnull.get(i)%>"/>
                                </td>
                               <%}%>
                                 <% if(datatablestipo.get(i).equals("TIMESTAMP(6)"))
                                  { if (visibility.get(i).equals(true)) { %>
                                  <td class="EtiquetaConsulta">
                                      <input  name="valores<%=i%>" value="<%=valores.get(i)%>" type="text" id="dateArrival" onClick="popUpCalendar(this,'DD/MM/YY HH24:MI:SS');" size="25" />
                                      <%} else{ %>
                                   <input name="valores<%=i%>" value="<%=valores.get(i)%>" type="hidden" id="dateArrival" onClick="popUpCalendar(this,'DD/MM/YY HH24:MI:SS');" size="10" />
                                   <%} %>
                                   <input type="hidden" name="nulc<%=i%>" value="<%=columnull.get(i)%>"/>
                                  </td>
                         <%}%>
                                   <% if(relacion.get(i).equals("")==false )
                                 { %>
                                  <td class="EtiquetaConsulta">
                                      <input type="hidden" id="valores<%=i%>" type="text" name="valores<%=i%>" size="25" value="<%=valores.get(i)%>" readonly="readonly" class=" :required"/> 
                                      <% if( nombrer.get(i)==null){%>
                                      <input  id="val<%=i%>" type="text" name="val<%=i%>" size="20" value="" readonly="readonly" class=" :required" /> 
                                      <%} else {%> 
                                      <input  id="val<%=i%>" type="text" name="val<%=i%>" size="20" value="<%=nombrer.get(i)%>" redonly="redonly" class=":required"/>
                                      <%}%>
                                      <input id="valoresi<%=i%>" type="hidden" name="valores<%=i%>" size="10" value="<%=valores.get(i)%>"/>
                                      <input type="hidden" name="nulc<%=i%>" value="<%=columnull.get(i)%>"/>
                                      <input type="hidden" name="ctlquery" value="<%=query.get(i)%>"/>
                                      <img src="view/img/busqueda.png"  width="20px" alt="busqueda"  onclick="openselection('CatalogQuerySrv?t=<%=relacion.get(i)%>&c=<%=query.get(i)%>','val<%=i%>','valores<%=i%>','cdata')" />
                                  </td>
                             <%}%>
                         </tr>
              <%}%>
                        <tr>
                       <br/><br/>
                       <td colspan="3" align="center">
                         <% if(modifica.equals("1") && idcol.equals("0")==false){ %>
                        <br/>
                        <input type="hidden" name="id" value="<%=id%>"/><input type="hidden" name="total" value="<%=total%>"/>
                        <input type="hidden" name="elimina" value="<%=elimina%>"/>
                        <input type="submit" class="buttonl" value="Modificar" action="1" name="accion"/> 
                        <%}%><% if(elimina.equals("1") && idcol.equals("0")==false) { %>
                        <input type="submit"  class="buttonl" value="Eliminar" name="accion"  action="3" />
                        <br/>
                        <input type="hidden" name="id" value="<%=id%>"/>
                        <input type="hidden" name="total" value="<%=total%>"/>
                        <input type="hidden" name="elimina" value="<%=elimina%>"/>
                        <%}%>
                        <% if(crea.equals("1") && idcol.equals("0")==true) { %>
                        <input type="submit"  class="buttonl" value="Guardar" name="accion"  action="2" onclick="return confirm('¿Está seguro que desea crear el registro?');" />
                        <%}%>
                       </td>
                </tr>
            </table>
            </form>
         </div>          
       <br>	
</html>
    