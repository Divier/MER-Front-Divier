
window.addEventListener('load', function(){
    $('#div_carga').hide();

});


function reloadIt() {
    if (window.location.href.substr(-2) !== "?r") {
        window.location = window.location.href + "?r";
    }
}

