function mostrarBloqueo(e) {
    $('#page-loader').toggleClass('displayNone');
    e.preventDefault();
}

function mostrarOcultarBloqueo(e) {
    e.preventDefault();
    setTimeout(function () {
        $("#page-loader").fadeOut(1500);
    }, 5000);
}