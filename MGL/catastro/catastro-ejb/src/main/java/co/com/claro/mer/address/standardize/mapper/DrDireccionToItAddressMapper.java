package co.com.claro.mer.address.standardize.mapper;

import co.com.claro.mer.address.standardize.domain.models.dto.AddressComplementDto;
import co.com.claro.mer.address.standardize.domain.models.dto.ItAddressStructureDto;
import co.com.claro.mer.utils.enums.AddressTypeEnum;
import co.com.claro.mgl.jpa.entities.DrDireccion;

import java.util.Optional;
import java.util.function.Function;

/**
 * Clase que se encarga de mapear un objeto DrDireccion a una dirección de tipo IT.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/28
 */
public class DrDireccionToItAddressMapper implements Function<DrDireccion, ItAddressStructureDto> {


    @Override
    public ItAddressStructureDto apply(DrDireccion drDireccion) {
        return Optional.ofNullable(drDireccion)
                .map(this::buildAddressWithComplement)
                .orElseGet(() -> new ItAddressStructureDto(new AddressComplementDto()));
    }

    /**
     * Método que se encarga de construir una dirección de tipo IT a partir de una dirección detallada.
     *
     * @param drDireccion objeto DrDireccion del cual se va a construir la dirección de tipo IT.
     * @return Dirección de tipo IT.
     * @author Gildardo Mora
     */
    private ItAddressStructureDto buildAddressWithComplement(DrDireccion drDireccion) {
        AddressComplementDto addressComplement = buildAddressComplement(drDireccion);
        ItAddressStructureDto itAddress = new ItAddressStructureDto(addressComplement);
        configurarDatos(drDireccion, itAddress);
        return itAddress;
    }

    /**
     * Método que se encarga de configurar los datos de la dirección de tipo IT.
     *
     * @param direccion DrDireccion desde el cual se va a extraer la información.
     * @param itAddress Dirección de tipo IT a la cual se le va a configurar la información.
     * @author Gildardo Mora
     */
    private void configurarDatos(DrDireccion direccion, ItAddressStructureDto itAddress) {
        itAddress.setNeighborhood(direccion.getBarrio());
        itAddress.setItTypeLevel1(direccion.getMzTipoNivel1());
        itAddress.setItValueLevel1(direccion.getMzValorNivel1());
        itAddress.setItTypeLevel2(direccion.getMzTipoNivel2());
        itAddress.setItValueLevel2(direccion.getMzValorNivel2());
        itAddress.setItTypeLevel3(direccion.getMzTipoNivel3());
        itAddress.setItValueLevel3(direccion.getMzValorNivel3());
        itAddress.setItTypeLevel4(direccion.getMzTipoNivel4());
        itAddress.setItValueLevel4(direccion.getMzValorNivel4());
        itAddress.setItTypeLevel5(direccion.getMzTipoNivel5());
        itAddress.setItValueLevel5(direccion.getMzValorNivel5());
        itAddress.setItTypeLevel6(direccion.getMzTipoNivel6());
        itAddress.setItValueLevel6(direccion.getMzValorNivel6());
        itAddress.setItTypePlate(direccion.getItTipoPlaca());
        itAddress.setItValuePlate(direccion.getItValorPlaca());
    }

    /**
     * Método que se encarga de construir un complemento de dirección a partir de un objeto DrDireccion.
     * @param direccion DrDireccion del cual se va a construir el complemento de dirección.
     * @return Complemento de dirección construido a partir de la dirección detallada.
     * @author Gildardo Mora
     */
    private static AddressComplementDto buildAddressComplement(DrDireccion direccion) {
        DrDireccionToComplementAddressMapper mapper = new DrDireccionToComplementAddressMapper();
        AddressComplementDto complement = mapper.apply(direccion);
        complement.setAddressType(AddressTypeEnum.IT);
        complement.setNeighborhood(direccion.getBarrio());
        return complement;
    }

}
