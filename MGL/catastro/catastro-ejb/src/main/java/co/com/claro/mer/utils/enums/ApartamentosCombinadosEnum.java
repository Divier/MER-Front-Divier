package co.com.claro.mer.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeración de tipos de apartamentos combinados en MER (PISO + APTO, PISO + INTERIOR, PISO + LOCAL)
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/02/13
 */
@AllArgsConstructor
@Getter
public enum ApartamentosCombinadosEnum {
    PISO_APTO("PISO + APTO", "PISO", "APARTAMENTO"),
    PISO_INTERIOR("PISO + INTERIOR", "PISO", "INTERIOR"),
    PISO_LOCAL("PISO + LOCAL", "PISO", "LOCAL"),
    CASA_PISO("CASA + PISO", "CASA", "PISO"),
    PISO_CAJERO("PISO + CAJERO", "PISO", "CAJERO"),;

    private final String tipoApartamento;
    private final String valorPrincipal;
    private final String valorSecundario;

}
