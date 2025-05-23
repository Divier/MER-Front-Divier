package co.com.claro.mer.address.standardize.mapper;

import co.com.claro.mer.address.standardize.domain.models.dto.AddressComplementDto;
import co.com.claro.mer.address.standardize.domain.models.dto.CkAddressStructureDto;
import co.com.claro.mer.utils.enums.AddressTypeEnum;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;

import java.util.Optional;
import java.util.function.Function;

/**
 * Mapper que convierte objetos CmtDireccionDetalladaMgl a CkAddressStructureDto
 *
 * @author Gildardo Mora
 * @version 1.1, 2025/03/04
 */
public class DireccionDetalladaToCkAddressMapper implements Function<CmtDireccionDetalladaMgl, CkAddressStructureDto> {

    @Override
    public CkAddressStructureDto apply(CmtDireccionDetalladaMgl direccionDetallada) {
        return Optional.ofNullable(direccionDetallada)
                .map(this::buildAddressWithComplement)
                .orElseGet(() -> new CkAddressStructureDto(new AddressComplementDto()));
    }

    /**
     * Método que se encarga de construir una dirección de tipo CK a partir de una dirección detallada.
     * @param dir Dirección detallada de la cual se va a construir la dirección de tipo CK.
     * @return Dirección de tipo CK.
     * @author Gildardo Mora
     */
    private CkAddressStructureDto buildAddressWithComplement(CmtDireccionDetalladaMgl dir) {
        AddressComplementDto addressComplement = buildAddressComplement(dir);
        CkAddressStructureDto ckAddress = new CkAddressStructureDto(addressComplement);
        configurarDatosViaPrincipal(dir, ckAddress);
        configurarDatosViaGeneradora(dir, ckAddress);
        return ckAddress;
    }

    /**
     * Método que se encarga de configurar los datos de la dirección de tipo CK.
     * @param dir Dirección detallada de la cual se va a extraer la información.
     * @param ckAddress Dirección de tipo CK a la cual se le va a configurar la información.
     * @author Gildardo Mora
     */
    private void configurarDatosViaPrincipal(CmtDireccionDetalladaMgl dir, CkAddressStructureDto ckAddress) {
        ckAddress.setMainTrackType(dir.getTipoViaPrincipal());
        ckAddress.setNumMainTrack(dir.getNumViaPrincipal());
        ckAddress.setMainTrackFirstModifier(dir.getLtViaPrincipal());
        ckAddress.setMainTrackSecondModifier(dir.getNlPostViaP());
        ckAddress.setBisMainTrack(dir.getBisViaPrincipal());
        ckAddress.setMainTrackQuadrant(dir.getCuadViaPrincipal());
    }

    /**
     * Método que se encarga de configurar los datos de la vía generadora de la dirección de tipo CK.
     *
     * @param dir Dirección detallada de la cual se va a extraer la información.
     * @param ckAddress Dirección de tipo CK a la cual se le va a configurar la información.
     * @author Gildardo Mora
     */
    private void configurarDatosViaGeneradora(CmtDireccionDetalladaMgl dir, CkAddressStructureDto ckAddress) {
        ckAddress.setGenerativeTrackType(dir.getTipoViaGeneradora());
        ckAddress.setNumGenerativeTrack(dir.getNumViaGeneradora());
        ckAddress.setGenerativeTrackFirstModifier(dir.getLtViaGeneradora());
        ckAddress.setGenerativeTrackSecondModifier(dir.getNlPostViaG());
        ckAddress.setGenTrackCoDirectionLetter(dir.getLetra3G());
        ckAddress.setBisGenerativeTrack(dir.getBisViaGeneradora());
        ckAddress.setGenerativeTrackQuadrant(dir.getCuadViaGeneradora());
        ckAddress.setDirectionPlate(dir.getPlacaDireccion());
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
        complement.setAddressType(AddressTypeEnum.CK);
        complement.setNeighborhood(direccion.getBarrio());
        return complement;
    }

}