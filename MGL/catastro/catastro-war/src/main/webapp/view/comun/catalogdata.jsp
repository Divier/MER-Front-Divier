<% String id = request.getParameter("id");
   String ctl = request.getParameter( "ctl" );
   String index = request.getParameter( "index" );
   String c = request.getParameter( "c" );
   String userVT = "NA";
   String verificaVT = "NA";
   String urlreturnVT = "NA";
   if(request.getMethod().equalsIgnoreCase("POST")){
        userVT = request.getParameter("home");
        verificaVT = request.getParameter("vlid");
        urlreturnVT = request.getParameter("adss");
   }
   
  %>
<html>
<head>
</head>
     <frameset rows="90%,10%,*" name="general">
	<frame src="../../CatalogDataSrv?id=<%=id%>&ctl=<%=ctl%>&index=<%=index%>&c=<%=c%>&home=<%=userVT%>&vlid=<%=verificaVT%>&adss=<%=urlreturnVT%>" name="filter" frameborder="0" >
     </frameset>
<noframes>
<body >
</body>
</noframes>
</html>



