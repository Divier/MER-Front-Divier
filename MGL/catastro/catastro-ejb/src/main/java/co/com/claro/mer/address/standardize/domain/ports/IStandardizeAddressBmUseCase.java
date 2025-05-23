package co.com.claro.mer.address.standardize.domain.ports;

import co.com.claro.mer.address.standardize.domain.models.dto.BmAddressStructureDto;

/**
 * Interfaz que define el caso de uso para estandarizar una dirección en el formato dirección barrio - manzana.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/05
 */
public interface IStandardizeAddressBmUseCase {

    /**
     * Método que se encarga de estandarizar una dirección en el formato dirección barrio - manzana.
     * @param address Dirección que se va a estandarizar.
     * @return Dirección estandarizada en el formato
     * @author Gildardo Mora
     */
    String standardizeBmAddress(BmAddressStructureDto address);

}
