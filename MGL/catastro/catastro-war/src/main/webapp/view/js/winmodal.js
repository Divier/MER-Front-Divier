function openselection(urlref,ctrl1,ctrl2,label){
    	var parameter = window.showModalDialog(urlref,"","dialogHeight:400px; dialogWidth:800px;status:no;center:yes;help:no;resizable:no;scrollbars:yes;");
	if(parameter != null){
		document.getElementById(ctrl1).value = parameter[1];
		document.getElementById(ctrl2).value = parameter[0];
		document.getElementById(label).innerHTML=parameter[0] + ' - ' + parameter[1];
             	}
}
function deleteselection(ctrl1,ctrl2,label){
	document.getElementById(ctrl1).value = '';	
	document.getElementById(ctrl2).value = '';	
	document.getElementById(label).innerHTML='';
}
function dataselection(parameter1,parameter2){
 var parameter=[parameter1,parameter2];
	window.returnValue = parameter;
	window.close();
}
function imagedown(id,image) {                       
   document.images[id].src=image;
}
function imageup(id,image) {                       
   document.images[id].src=image;
}

function openhelp(urlref){
	var parameter = window.showModalDialog(urlref,"","dialogHeight:400px; dialogWidth:800px;status:no;center:yes;help:no;resizable:no;scrollbars:yes;");
}
function openwinmodal(urlref){
	var parameter = window.showModalDialog(urlref,"","dialogHeight:380px; dialogWidth:600px;status:no;center:yes;help:no;resizable:no;scrollbars:yes;");
}