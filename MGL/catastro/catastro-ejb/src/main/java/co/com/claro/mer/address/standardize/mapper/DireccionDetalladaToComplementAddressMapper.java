package co.com.claro.mer.address.standardize.mapper;

import co.com.claro.mer.address.standardize.domain.models.dto.AddressComplementDto;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;

import java.util.Optional;
import java.util.function.Function;

/**
 * Mapper que convierte objetos CmtDireccionDetalladaMgl a AddressComplementDto
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/05
 */
public class DireccionDetalladaToComplementAddressMapper implements Function<CmtDireccionDetalladaMgl, AddressComplementDto> {

    @Override
    public AddressComplementDto apply(CmtDireccionDetalladaMgl detalladaMgl) {
        return Optional.ofNullable(detalladaMgl)
                .map(this::convertToAddressComplementDto)
                .orElseGet(AddressComplementDto::new);
    }

    /**
     * Método que convierte un objeto CmtDireccionDetalladaMgl a AddressComplementDto.
     * @param detallada Objeto CmtDireccionDetalladaMgl que se va a convertir.
     * @return Objeto AddressComplementDto convertido.
     * @author Gildardo Mora
     */
    private AddressComplementDto convertToAddressComplementDto(CmtDireccionDetalladaMgl detallada) {
        AddressComplementDto addressComplement = new AddressComplementDto();
        addressComplement.setTypeComplementLevel1(detallada.getCpTipoNivel1());
        addressComplement.setValueComplementLevel1(detallada.getCpValorNivel1());
        addressComplement.setTypeComplementLevel2(detallada.getCpTipoNivel2());
        addressComplement.setValueComplementLevel2(detallada.getCpValorNivel2());
        addressComplement.setTypeComplementLevel3(detallada.getCpTipoNivel3());
        addressComplement.setValueComplementLevel3(detallada.getCpValorNivel3());
        addressComplement.setTypeComplementLevel4(detallada.getCpTipoNivel4());
        addressComplement.setValueComplementLevel4(detallada.getCpValorNivel4());
        addressComplement.setTypeComplementLevel5(detallada.getCpTipoNivel5());
        addressComplement.setValueComplementLevel5(detallada.getCpValorNivel5());
        addressComplement.setTypeComplementLevel6(detallada.getCpTipoNivel6());
        addressComplement.setValueComplementLevel6(detallada.getCpValorNivel6());
        return addressComplement;
    }

}
