package co.com.claro.mer.address.standardize.mapper;

import co.com.claro.mer.address.standardize.domain.models.dto.AddressComplementDto;
import co.com.claro.mer.address.standardize.domain.models.dto.ItAddressStructureDto;
import co.com.claro.mer.utils.enums.AddressTypeEnum;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;

import java.util.Optional;
import java.util.function.Function;

/**
 * Clase que se encarga de mapear una dirección detallada a una dirección de tipo IT.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/05
 */
public class DireccionDetalladaToItAddressMapper implements Function<CmtDireccionDetalladaMgl, ItAddressStructureDto> {


    @Override
    public ItAddressStructureDto apply(CmtDireccionDetalladaMgl detalladaMgl) {
        return Optional.ofNullable(detalladaMgl)
                .map(this::buildAddressWithComplement)
                .orElseGet(() -> new ItAddressStructureDto(new AddressComplementDto()));
    }

    /**
     * Método que se encarga de construir una dirección de tipo IT a partir de una dirección detallada.
     *
     * @param detalladaMgl Dirección detallada de la cual se va a construir la dirección de tipo IT.
     * @return Dirección de tipo IT.
     * @author Gildardo Mora
     */
    private ItAddressStructureDto buildAddressWithComplement(CmtDireccionDetalladaMgl detalladaMgl) {
        AddressComplementDto addressComplement = buildAddressComplement(detalladaMgl);
        ItAddressStructureDto itAddress = new ItAddressStructureDto(addressComplement);
        configurarDatos(detalladaMgl, itAddress);
        return itAddress;
    }

    /**
     * Método que se encarga de configurar los datos de la dirección de tipo IT.
     *
     * @param dir Dirección detallada de la cual se va a extraer la información.
     * @param itAddress Dirección de tipo IT a la cual se le va a configurar la información.
     * @author Gildardo Mora
     */
    private void configurarDatos(CmtDireccionDetalladaMgl dir, ItAddressStructureDto itAddress) {
        itAddress.setNeighborhood(dir.getBarrio());
        itAddress.setItTypeLevel1(dir.getMzTipoNivel1());
        itAddress.setItValueLevel1(dir.getMzValorNivel1());
        itAddress.setItTypeLevel2(dir.getMzTipoNivel2());
        itAddress.setItValueLevel2(dir.getMzValorNivel2());
        itAddress.setItTypeLevel3(dir.getMzTipoNivel3());
        itAddress.setItValueLevel3(dir.getMzValorNivel3());
        itAddress.setItTypeLevel4(dir.getMzTipoNivel4());
        itAddress.setItValueLevel4(dir.getMzValorNivel4());
        itAddress.setItTypeLevel5(dir.getMzTipoNivel5());
        itAddress.setItValueLevel5(dir.getMzValorNivel5());
        itAddress.setItTypeLevel6(dir.getMzTipoNivel6());
        itAddress.setItValueLevel6(dir.getMzValorNivel6());
        itAddress.setItTypePlate(dir.getItTipoPlaca());
        itAddress.setItValuePlate(dir.getItValorPlaca());
    }

    /**
     * Método que se encarga de construir un complemento de dirección a partir de una dirección detallada.
     * @param direccion Dirección detallada de la cual se va a construir el complemento de dirección.
     * @return Complemento de dirección construido a partir de la dirección detallada.
     * @author Gildardo Mora
     */
    private static AddressComplementDto buildAddressComplement(CmtDireccionDetalladaMgl direccion) {
        DireccionDetalladaToComplementAddressMapper mapper = new DireccionDetalladaToComplementAddressMapper();
        AddressComplementDto complement = mapper.apply(direccion);
        complement.setAddressType(AddressTypeEnum.IT);
        complement.setNeighborhood(direccion.getBarrio());
        return complement;
    }

}
