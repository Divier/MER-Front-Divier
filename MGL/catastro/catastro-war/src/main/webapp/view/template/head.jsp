<html>
<head><link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/view/css/menustyle.css"></head>
<body style="position:absolute;z-index:999;background-color:transparent" >
<script language="javascript">
 function openIFrame(destino)
 {
  var arrFrames = parent.document.getElementsByTagName("IFRAME");
  for (var i = 0; i < arrFrames.length; i++) {
  if (arrFrames[i].id=="contmenu") arrFrames[i].src = destino;
 }
}
 </script>
 
<script type="text/javascript" src="<%= request.getContextPath() %>/view/js/milonic_src.js"></script>
<div style="margin-top:4px !important">
<div class=milonic><a href="http://www.milonic.com/">JavaScript Menu, DHTML Menu Powered By Milonic</a></div>
<script	type="text/javascript">
if(ns4)_d.write("<scr"+"ipt type=text/javascript src=<%= request.getContextPath() %>/view/js/mmenuns4.js><\/scr"+"ipt>");
  else _d.write("<scr"+"ipt type=text/javascript src=<%= request.getContextPath() %>/view/js/mmenudom.js><\/scr"+"ipt>");
</script>

<script language="JavaScript">
fixMozillaZIndex=true;
_menuCloseDelay=300;
_menuOpenDelay=50;
_subOffsetTop=2;
_subOffsetLeft=-2;

with(menuStyle=new mm_style()){
//bordercolor="#999999";
borderstyle="solid";
borderwidth=0;
borderradius="0 0 5px 5px";
fontfamily="Verdana, Tahoma, Arial";
fontweight="bold";
fontsize="11px";
fontstyle="normal";
headerbgcolor="#D52B1E";
headercolor="#000000";
offbgcolor="#D52B1E";
offcolor="#FFFFFF";
onbgcolor="#FFFFFF";
oncolor="#D52B1E";
outfilter="randomdissolve(duration=0.3)";
overfilter="Fade(duration=0.2);Alpha(opacity=90);Shadow(color=#777777', Direction=135, Strength=3)";
padding=4;
pagebgcolor="#adafaf";
pagecolor="black";
separatorcolor="#adafaf";
separatorsize=1;
subimage="<%= request.getContextPath() %>/view/img/arrow.gif";
subimagepadding=2;}
<% out.print (request.getAttribute("menu").toString()); %>
drawMenus();
</script>
</div>
  <img style="margin-top:8px;" border=0 src="<%= request.getContextPath() %>/view/img/menu.png" height="12px" width="100%"/>
 </body>
</html>


