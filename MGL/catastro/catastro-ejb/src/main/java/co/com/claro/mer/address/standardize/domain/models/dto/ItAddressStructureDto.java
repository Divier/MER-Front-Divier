package co.com.claro.mer.address.standardize.domain.models.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que representa la estructura de una dirección en el formato dirección intraducible.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/05
 */
@ToString
@Getter
@Setter
public class ItAddressStructureDto {

    /**
     * Barrio de la dirección
     */
    private String neighborhood;

    private String itTypePlate;

    private String itValuePlate;

    private String itTypeLevel1;

    private String itValueLevel1;

    private String itTypeLevel2;

    private String itValueLevel2;

    private String itTypeLevel3;

    private String itValueLevel3;

    private String itTypeLevel4;

    private String itValueLevel4;

    private String itTypeLevel5;

    private String itValueLevel5;

    private String itTypeLevel6;

    private String itValueLevel6;

    /**
     * Complemento de la dirección
     */
    private AddressComplementDto addressComplement;

    public ItAddressStructureDto(AddressComplementDto addressComplement) {
        this.addressComplement = addressComplement;
    }

}
