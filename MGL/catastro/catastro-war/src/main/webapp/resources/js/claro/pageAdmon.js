function start() {
    if (PF('statusDialog') !== null) {
        PF('statusDialog').show();
    }
}

function stop() {
    if (PF('statusDialog') !== null) {
        PF('statusDialog').hide();
    }
}