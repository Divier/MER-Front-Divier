package co.com.claro.mer.address.standardize.domain.ports;

import co.com.claro.mer.address.standardize.domain.models.dto.ItAddressStructureDto;

/**
 * Interfaz que define el caso de uso para estandarizar una dirección en el formato dirección intraducible.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/05
 */
public interface IStandardizeAddressItUseCase {

    /**
     * Método que se encarga de estandarizar una dirección en el formato dirección intraducible.
     * @param address Dirección que se va a estandarizar.
     * @return Dirección estandarizada en el formato dirección intraducible.
     * @author Gildardo Mora
     */
    String standardizeItAddress(ItAddressStructureDto address);

    /**
     * Método que se encarga de estandarizar una dirección en el formato placa de dirección intraducible.
     * @param address Dirección que se va a estandarizar.
     * @return Dirección estandarizada en el formato placa de dirección intraducible.
     * @author Gildardo Mora
     */
    String standardizeItPlateAddress(ItAddressStructureDto address);

}
