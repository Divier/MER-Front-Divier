<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
  
<link href="view/css/contenido.css" rel="stylesheet" type="text/css"/>
 <script type="text/javascript" src="view/img/jquery-1.3.2.js"></script>
  <script type="text/javascript" src="view/img/vanadium_es.js"></script>
  
  <style>
      .buttonl{
    width: 80px;
    background-position: left;
    background-image: url("../img/buttonl.png") !important;
    background-repeat: no-repeat;
    font-family: Arial, Helvetica, sans-serif;
    font-size: 11px;
    font-weight: normal;
    text-align: center;
    color:#FFFFFF;
    border-width: 1px;
    border-collapse: collapse;
    border-color: #E6E6E6;
    border: 0 none #CCCCCC;
    height: 20px;
    text-decoration: none;
}
.EtiquetaConsulta{
    text-align: left;
    background-position: left;
    height:20px;
    color:#666666;
    width: 100px;
    text-align:left;
    font-family: Arial;
    font-size: 11px;
    font-weight:bold;
    padding: 3px 3px 3px 3px;
}
  </style>
  
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <body>
    
	
	
	
     <div align="center">
     <form action="../../UploadArchivo" enctype="multipart/form-data" method="post">
      <label   class="EtiquetaConsulta" for="filename_1">Archivo: </label>
      <input id="filename_1" type="file" name="filename_1"  class=" :required" size="40"/>
      <br/>             
      <br/> 
      <center>
      <input type="submit" value="Cargar Archivo" class="buttonl"  style="color:#FFFFFF;"/>
      </center>
    </form>
     </div>
  </body>
</html>