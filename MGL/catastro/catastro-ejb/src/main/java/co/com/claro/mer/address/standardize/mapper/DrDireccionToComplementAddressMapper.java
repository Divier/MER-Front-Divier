package co.com.claro.mer.address.standardize.mapper;

import co.com.claro.mer.address.standardize.domain.models.dto.AddressComplementDto;
import co.com.claro.mgl.jpa.entities.DrDireccion;

import java.util.Optional;
import java.util.function.Function;

/**
 * Mapper que convierte objetos DrDireccion a AddressComplementDto
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/28
 */
public class DrDireccionToComplementAddressMapper implements Function<DrDireccion, AddressComplementDto> {

    @Override
    public AddressComplementDto apply(DrDireccion drDireccion) {
        return Optional.ofNullable(drDireccion)
                .map(this::convertToAddressComplementDto)
                .orElseGet(AddressComplementDto::new);
    }

    /**
     * Método que convierte un objeto DrDireccion a AddressComplementDto.
     * @param dr Objeto DrDireccion que se va a convertir.
     * @return Objeto AddressComplementDto convertido.
     * @author Gildardo Mora
     */
    private AddressComplementDto convertToAddressComplementDto(DrDireccion dr) {
        AddressComplementDto addressComplement = new AddressComplementDto();
        addressComplement.setTypeComplementLevel1(dr.getCpTipoNivel1());
        addressComplement.setValueComplementLevel1(dr.getCpValorNivel1());
        addressComplement.setTypeComplementLevel2(dr.getCpTipoNivel2());
        addressComplement.setValueComplementLevel2(dr.getCpValorNivel2());
        addressComplement.setTypeComplementLevel3(dr.getCpTipoNivel3());
        addressComplement.setValueComplementLevel3(dr.getCpValorNivel3());
        addressComplement.setTypeComplementLevel4(dr.getCpTipoNivel4());
        addressComplement.setValueComplementLevel4(dr.getCpValorNivel4());
        addressComplement.setTypeComplementLevel5(dr.getCpTipoNivel5());
        addressComplement.setValueComplementLevel5(dr.getCpValorNivel5());
        addressComplement.setTypeComplementLevel6(dr.getCpTipoNivel6());
        addressComplement.setValueComplementLevel6(dr.getCpValorNivel6());
        return addressComplement;
    }

}
