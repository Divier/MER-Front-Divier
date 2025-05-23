package co.com.claro.mer.utils.enums;

import lombok.Getter;

/**
 * Enumerado para definir los delimitadores usados en las cadenas de texto
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/06/06
 */
public enum DelimitadorEnum {

    COMA(","),
    PUNTO_Y_COMA(";"),
    PUNTO("."),
    ESPACIO(" "),
    GUION_MEDIO("-"),
    GUION_BAJO("_"),
    PIPE("|");

    /* ------------------------------------------ */
    @Getter
    public final String delimitador;

    DelimitadorEnum(String delimitador) {
        this.delimitador = delimitador;
    }
}
