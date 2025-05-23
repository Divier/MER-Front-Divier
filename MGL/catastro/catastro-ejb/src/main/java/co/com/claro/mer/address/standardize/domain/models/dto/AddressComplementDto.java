package co.com.claro.mer.address.standardize.domain.models.dto;

import co.com.claro.mer.utils.enums.AddressTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO para almacenar la información de los complementos de una dirección.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/03
 */
@ToString
@Getter
@Setter
public class AddressComplementDto {

    private AddressTypeEnum addressType;

    private String neighborhood;

    private String typeComplementLevel1;

    private String valueComplementLevel1;

    private String typeComplementLevel2;

    private String valueComplementLevel2;

    private String typeComplementLevel3;

    private String valueComplementLevel3;

    private String typeComplementLevel4;

    private String valueComplementLevel4;

    private String typeComplementLevel5;

    private String valueComplementLevel5;

    private String typeComplementLevel6;

    private String valueComplementLevel6;

}
