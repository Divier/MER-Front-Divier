package co.com.claro.mer.address.standardize.mapper;

import co.com.claro.mer.address.standardize.domain.models.dto.AddressComplementDto;
import co.com.claro.mer.address.standardize.domain.models.dto.BmAddressStructureDto;
import co.com.claro.mer.utils.enums.AddressTypeEnum;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;

import java.util.Optional;
import java.util.function.Function;

/**
 * Clase que se encarga de mapear una dirección detallada a una dirección de tipo BM.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/05
 */
public class DireccionDetalladaToBmAddressMapper implements Function<CmtDireccionDetalladaMgl, BmAddressStructureDto> {

    @Override
    public BmAddressStructureDto apply(CmtDireccionDetalladaMgl detalladaMgl) {
        return Optional.ofNullable(detalladaMgl)
                .map(this::buildAddressWithComplement)
                .orElseGet(() -> new BmAddressStructureDto(new AddressComplementDto()));
    }

    /**
     * Método que se encarga de construir una dirección de tipo BM a partir de una dirección detallada.
     *
     * @param detalladaMgl Dirección detallada de la cual se va a construir la dirección de tipo BM.
     * @return Dirección de tipo BM.
     * @author Gildardo Mora
     */
    private BmAddressStructureDto buildAddressWithComplement(CmtDireccionDetalladaMgl detalladaMgl) {
        AddressComplementDto addressComplement = buildAddressComplement(detalladaMgl);
        BmAddressStructureDto bmAddress = new BmAddressStructureDto(addressComplement);
        configurarDatos(detalladaMgl, bmAddress);
        return bmAddress;
    }

    /**
     * Método que se encarga de configurar los datos de la dirección de tipo BM.
     *
     * @param dir Dirección detallada de la cual se va a extraer la información.
     * @param bmAddress Dirección de tipo BM a la cual se le va a configurar la información.
     * @author Gildardo Mora
     */
    private void configurarDatos(CmtDireccionDetalladaMgl dir, BmAddressStructureDto bmAddress) {
        bmAddress.setNeighborhood(dir.getBarrio());
        bmAddress.setMzTypeLevel1(dir.getMzTipoNivel1());
        bmAddress.setMzValueLevel1(dir.getMzValorNivel1());
        bmAddress.setMzTypeLevel2(dir.getMzTipoNivel2());
        bmAddress.setMzValueLevel2(dir.getMzValorNivel2());
        bmAddress.setMzTypeLevel3(dir.getMzTipoNivel3());
        bmAddress.setMzValueLevel3(dir.getMzValorNivel3());
        bmAddress.setMzTypeLevel4(dir.getMzTipoNivel4());
        bmAddress.setMzValueLevel4(dir.getMzValorNivel4());
        bmAddress.setMzTypeLevel5(dir.getMzTipoNivel5());
        bmAddress.setMzValueLevel5(dir.getMzValorNivel5());
    }

    /**
     * Método que se encarga de construir un complemento de dirección a partir de una dirección detallada.
     *
     * @param direccion Dirección detallada de la cual se va a construir el complemento de dirección.
     * @return Complemento de dirección construido a partir de la dirección detallada.
     * @author Gildardo Mora
     */
    private static AddressComplementDto buildAddressComplement(CmtDireccionDetalladaMgl direccion) {
        DireccionDetalladaToComplementAddressMapper mapper = new DireccionDetalladaToComplementAddressMapper();
        AddressComplementDto complement = mapper.apply(direccion);
        complement.setAddressType(AddressTypeEnum.BM);
        complement.setNeighborhood(direccion.getBarrio());
        return complement;
    }

}
