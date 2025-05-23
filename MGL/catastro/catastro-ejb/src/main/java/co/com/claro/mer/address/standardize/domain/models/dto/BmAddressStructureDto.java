package co.com.claro.mer.address.standardize.domain.models.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO para almacenar la información de la estructura de una dirección de tipo BM (Barrio - Manzana).
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/05
 */
@ToString
@Getter
@Setter
public class BmAddressStructureDto {

    /**
     * Barrio de la dirección
     */
    private String neighborhood;

    private String mzTypeLevel1;

    private String mzValueLevel1;

    private String mzTypeLevel2;

    private String mzValueLevel2;

    private String mzTypeLevel3;

    private String mzValueLevel3;

    private String mzTypeLevel4;

    private String mzValueLevel4;

    private String mzTypeLevel5;

    private String mzValueLevel5;

    /**
     * Complemento de la dirección
     */
    private AddressComplementDto addressComplement;

    public BmAddressStructureDto(AddressComplementDto addressComplement) {
        this.addressComplement = addressComplement;
    }

}
