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
        if (this.readyState == 4) {
            delBlockClaro();
        }
        return this._onreadystatechange.apply(this, arguments);
    }
}
window.XMLHttpRequest.prototype.open = openReplacement;
window.XMLHttpRequest.prototype.send = sendReplacement;

function addZero(i) {
    v = "0" + i;
    v = v.substr(v.length - 2);
    return v;
}

function myFunction() {
    var d = new Date();
    var cro = (new Date() - ini);
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
        if (jQuery(this).closest('li').find('ul').children().length == 0) {
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
        if (jQuery(this).closest('li').find('ul').children().length == 0) {
            return true;
        } else {
            return false;
        }
    });
    jQuery(".logo_Claro").click(function() {
        if (jQuery("#cssmenu").css("display") == 'none') {
            jQuery("#cssmenu").fadeIn("slow");
        } else {
            jQuery("#cssmenu").fadeOut("slow");
        }
    });
    jQuery(".unblockClaro").click(function() {
        setTimeout(delBlockClaro, 4000);
    });

    if (document.getElementById("formSolicitud:inicio") != null) {
        var inicial = document.getElementById("formSolicitud:inicio").value;
        ini = new Date();
        ini.setTime(inicial);
//        var v;
        myFunction();
    }
});
