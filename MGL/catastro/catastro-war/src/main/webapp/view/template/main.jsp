<html>
    <head><link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/view/css/contenido.css"></head>
       <table border="0" width="98%" align="center">
            <tr>
                <td align="center"><img src="<%= request.getContextPath() %>/view/img/logo_claro.jpg" border="0" /></td>
                <td align="right" valign="middle" style="padding-right: 30px;"><span class="titulologo">M&oacute;dulo Expansi&oacute;n de Red</span><br />
                    <span class="subtitulo_logo">Portal para la consulta, validaci&oacute;n y estandarizaci&oacute;n de direcciones</span> </td>
            </tr>
            <tr><td colspan="2">
                    <iframe src="../../MenuSrv" name="title" allowtransparency="yes" scrolling="no" frameborder="0px" 
                            style="border: 0px; width:100%; height:200px; position:relative; left:0px;top:0px;margin:0;padding:0; z-index:988;" 
                            onMouseOver="this.style.zIndex = 998" onMouseOut="this.style.zIndex =988">
                   </iframe> 
                </td>
            </tr>
            <tr><td colspan="2">
                    <iframe id="contmenu" src="<%= request.getContextPath() %>/view/template/initialize.jsp" name="contmenu"  
                            frameborder="0px" allowtransparency="yes"
                            style="border: 0px; width:100%; height:500px; position:relative; left:0px;top:-160px;margin:0;padding:0;z-index:997;" 
                            onMouseOver="this.style.zIndex = 998" onMouseOut="this.style.zIndex =997">
                    </iframe> 
                </td>
            </tr>
            <tr><td align="center" colspan="2">
                    <span class="piepagina" style="margin-top:-300px">
                        Copyright 2013 - CLARO Colombia todos los derechos reservados.
                    </span>
                </td>
            </tr>
        </table>
</html>

