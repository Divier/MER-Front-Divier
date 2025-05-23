<% String id = request.getParameter("id");
 %>
<html>
<head>
</head>
     <frameset rows="90%,10%,*" name="general">
	<frame src="../../CatalogReportSrv?id=<%=id%>" name="report" frameborder="0" >
      </frameset>
<noframes>
<body >
</body>
</noframes>
</html>

