package co.com.claro.mer.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeración que contiene los tipos de direcciones.
 * <p>
 * CK: Dirección de alle-carrera.
 * BM: Dirección de barrio-manzana.
 * IT: Dirección de intraducible.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/04
 */
@AllArgsConstructor
@Getter
public enum AddressTypeEnum {

    CK("CK"),
    BM("BM"),
    IT("IT");

    private final String addressType;

}
