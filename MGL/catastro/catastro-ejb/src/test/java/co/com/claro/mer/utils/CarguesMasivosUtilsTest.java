package co.com.claro.mer.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarguesMasivosUtilsTest {

    @Test
    void validarNombreArchivo() {
        assertTrue(
                CarguesMasivosUtils.validarNombreArchivo("CREACION_HHPP_CCMM_06122024_164457.csv",
                        "CREACION_HHPP_CCMM_ddmmaaaa_hhmmss.csv"));
    }
}