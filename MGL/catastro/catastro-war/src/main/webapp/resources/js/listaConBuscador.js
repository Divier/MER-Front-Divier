/*
 * aguilarjn@globalhitss
 */
/* Configuración para los select con buscador */

$(document).ready(function () {
    /** Esto es para todos los select*/
    $('.selectpicker').selectpicker({
        liveSearch: true
    });

    $('.selectpicker').on('rendered.bs.select', function (e) {
        $('.selectpicker').selectpicker({
            liveSearch: true
        });
    });
    $(document).on("change", '.selectpicker', function () {
        $(this).siblings('.dropdown-menu').hide();
    });

    $(document).on("click", '.dropdown-toggle', function () {
        if ($(this).siblings('.selectpicker').is(':disabled')) {
        } else {
            if ($(this).siblings('.dropdown-menu').css("display") === "none") {
                $(".open").css("display", "none");
                $(this).siblings('.dropdown-menu').show();
            } else {
                $(".open").css("display", "none");
            }
        }
    });

    $(document).on("click", function () {
        if (!event.target.classList.contains('dropdown-toggle') &&
                !event.target.classList.contains('filter-option')) {
            $(".open").css("display", "none");
        }
    });

    jsf.ajax.addOnEvent(function (data) {
        if (data.status === 'success') {
            $('.selectpicker').selectpicker({
                liveSearch: true
            });
        }
    });
});