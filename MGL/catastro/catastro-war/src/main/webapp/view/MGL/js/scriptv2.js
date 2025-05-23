$(window).on("load",function(){
    if (document.getElementById("formSolicitud:inicio") !== null) {
        // obtener delta time dentro del JS local storage (solo se realiza una vez).
        if (localStorage.getItem("clientServerDiffTime") === null) {
            var inicial = document.getElementById("formSolicitud:inicio").value;
            var ini = new Date();
            ini.setTime(inicial);
            // diferencia entre la fecha del cliente vs fecha inicial del servidor.
            delta = new Date() - ini;
            
            localStorage.setItem("clientServerDiffTime", delta);
        }
    }
});


/*PAGINADOR*/
function paginaTable() {
    if (jQuery('.tableViewCont').length > 0) {
        jQuery('.tableViewCont').DataTable({
            "language": {
                "lengthMenu": "Ver _MENU_ registros por p&aacute;gina",
                "zeroRecords": "No se encontraron datos",
                "info": "Viendo p&aacute;gina _PAGE_ de _PAGES_",
                "search": "Buscar:",
                "infoFiltered": "(Buscando en los _MAX_ registros cargados)",
                "paginate": {
                    "first": "Primero",
                    "last": "&Uacute;ltimo",
                    "next": "Siguiente",
                    "previous": "Anterior"
                }
            }
        });
        jQuery('.tableViewCont').show();
    }
}
/**BLOCK_GENERIC**/
var $myNewElemen = jQuery('<div id = "ajaxBlock" class="black_overlayError"><div class="claroWait"><div class="wBall" id="wBall_1"><div class="wInnerBall"></div></div><div class="wBall" id="wBall_2"><div class="wInnerBall"></div></div><div class="wBall" id="wBall_3"><div class="wInnerBall"></div></div><div class="wBall" id="wBall_4"><div class="wInnerBall"></div></div><div class="wBall" id="wBall_5"><div class="wInnerBall"></div></div></div></div>');
function delBlockClaro() {
    if (jQuery("#ajaxBlock").length >= 1) {
        jQuery("#ajaxBlock").remove();
    }
}
function divBlockClaro() {
    delBlockClaro();
    jQuery($myNewElemen).css("background-color", '#eff8fb');
    jQuery('body').append($myNewElemen);
    jQuery("#ajaxBlock").show();
}

/**CONTROL DE AJAX**/
var open = window.XMLHttpRequest.prototype.open,
        send = window.XMLHttpRequest.prototype.send,
        onReadyStateChange;
function openReplacement(method, url, async, user, password) {
    return open.apply(this, arguments);
}

function sendReplacement(data) {
    divBlockClaro();
    if (this.onreadystatechange) {
        this._onreadystatechange = this.onreadystatechange;
    }
    this.onreadystatechange = onReadyStateChangeReplacement;

    return send.apply(this, arguments);
}

function onReadyStateChangeReplacement() {
    if (this._onreadystatechange) {
        if (this.readyState === 4) {
            delBlockClaro();
        }
        return this._onreadystatechange.apply(this, arguments);
    }
}
//window.XMLHttpRequest.prototype.open = openReplacement;
//window.XMLHttpRequest.prototype.send = sendReplacement;

function addZero(i) {
    v = "0" + i;
    v = v.substr(v.length - 2);
    return v;
}

function myFunction() {
    var d = new Date();
    var cro = (new Date() - ini - delta);
    d.setTime(cro);
    var h = addZero(d.getHours() - 19);
    var m = addZero(d.getMinutes());
    var s = addZero(d.getSeconds());

    if (document.getElementById("formSolicitud:eimsTxt")) {
        if  (h < 0) { h = '00'; m = '00'; s = '00'; }
        document.getElementById("formSolicitud:eimsTxt").value = h + ":" + m + ":" + s;
        setTimeout("myFunction()", 1000);
    }
}
function myProces()
{
    document.getElementById('procesImg').style.display = 'block';
}

//**inicializar **//
jQuery(document).ready(function() {
    jQuery('#cssmenu > ul > li > a').click(function() {
        jQuery('#cssmenu li').removeClass('active');
        jQuery(this).closest('li').addClass('active');
        checkElement = jQuery(this).next();
        if ((checkElement.is('ul')) && (checkElement.is(':visible'))) {
            jQuery(this).closest('li').removeClass('active');
            checkElement.slideUp('normal');
        }
        if ((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
            jQuery('#cssmenu ul ul:visible').slideUp('normal');
            checkElement.slideDown('normal');
        }
        if (jQuery(this).closest('li').find('ul').children().length === 0) {
            return true;
        } else {
            return false;
        }
    });
    jQuery('#cssmenu > ul > li > ul > li > a').click(function() {
        jQuery('#cssmenu li ul li').removeClass('active');
        jQuery(this).closest('li').addClass('active');
        checkElement = jQuery(this).next();
        if ((checkElement.is('ul')) && (checkElement.is(':visible'))) {
            jQuery(this).closest('li').removeClass('active');
            checkElement.slideUp('normal');
        }
        if ((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
            jQuery('#cssmenu ul ul ul:visible').slideUp('normal');
            checkElement.slideDown('normal');
        }
        if (jQuery(this).closest('li').find('ul').children().length === 0) {
            return true;
        } else {
            return false;
        }
    });
    
    jQuery('#cssmenu > ul > li > ul > li > ul > li > a').click(function() {
        jQuery('#cssmenu li ul li ul li').removeClass('active');
        jQuery(this).closest('li').addClass('active');
        checkElement = jQuery(this).next();
        if ((checkElement.is('ul')) && (checkElement.is(':visible'))) {
            jQuery(this).closest('li').removeClass('active');
            checkElement.slideUp('normal');
        }
        if ((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
            jQuery('#cssmenu ul ul ul ul:visible').slideUp('normal');
            checkElement.slideDown('normal');
        }
        if (jQuery(this).closest('li').find('ul').children().length === 0) {
            return true;
        } else {
            return false;
        }
    });


    jQuery(".logo_Claro").click(function() {
        if (jQuery("#cssmenu").css("display") === 'none') {
            jQuery("#cssmenu").fadeIn("slow");
        } else {
            jQuery("#cssmenu").fadeOut("slow");
        }
    });
    jQuery(".unblockClaro").click(function() {
        setTimeout(delBlockClaro, 2000);
    });
    
    
    if (document.getElementById("formSolicitud:inicio") !== null) {
        var inicial = document.getElementById("formSolicitud:inicio").value;
        ini = new Date();
        ini.setTime(inicial);
        // diferencia entre la fecha del cliente vs fecha inicial del servidor.
        delta = localStorage.getItem("clientServerDiffTime");
        
        if (delta === null) {
            delta = new Date() - ini;
        }
        
        myFunction();
    }

    /**CONTROL SUBMIT**/
    window.onbeforeunload = function(e) {
        divBlockClaro();
    };
    paginaTable();

    if (jQuery.datepicker) {
        jQuery.datepicker.regional['es'] = {
            closeText: 'Cerrar',
            prevText: 'Ant',
            nextText: 'Sig',
            currentText: 'Hoy',
            monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
            dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
            dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mié', 'Juv', 'Vie', 'Sáb'],
            dayNamesMin: ['Do', 'Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sá'],
            weekHeader: 'Sm',
            dateFormat: 'dd/mm/yy',
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: false,
            yearSuffix: ''
        };
        jQuery.datepicker.setDefaults($.datepicker.regional['es']);
        jQuery('.datepicker').datepicker({
            showOn: 'button',
            buttonImage: window.location.pathname.substring(0, window.location.pathname.indexOf("/",2)) +'/view/images/calendar.gif',
            buttonImageOnly: true
        });
    }
});

function validaCaracteres(event) {
    var data = (document.all) ? event.keyCode : event.which;

    if (data == 8) {
        return true;
    }

    var patron = /[A-Za-z0-9]/;
    var data_final = String.fromCharCode(data);
    return patron.test(data_final);
}