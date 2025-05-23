package co.com.claro.mer.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Enumeración de tipos de solicitudes HHPP
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/12/06
 */
@Getter
@AllArgsConstructor
public enum TiposSolicitudesHhppEnum {

    DEFAULT("-1"),
    CREACION_HOME_PASSED("0"),
    CAMBIO_DIRECCION("1"),
    CAMBIO_ESTRATO("2"),
    ESCALAMIENTO_HHPP("13"),
    TRASLADO_HHPP_BLOQUEADO("5")
    ;

    private final String codigoBasica;

    public static TiposSolicitudesHhppEnum fromCodigoBasica(String codigoBasica) {
        return Arrays.stream(values())
                .filter(tipo -> tipo.getCodigoBasica().equals(codigoBasica))
                .findFirst().orElse(DEFAULT);
    }

}
