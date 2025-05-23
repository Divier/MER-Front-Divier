package co.com.claro.mer.address.standardize.domain.ports;

import co.com.claro.mer.address.standardize.domain.models.dto.AddressComplementDto;

/**
 * Interfaz que define el caso de uso para estandarizar un complemento de dirección.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/04
 */
public interface IStandardizeComplementUseCase {

    /**
     * Método que se encarga de estandarizar un complemento de dirección.
     * @param addressComplement Complemento de dirección que se va a estandarizar.
     * @return Complemento de dirección estandarizado.
     * @author Gildardo Mora
     */
    String standardizeComplement(AddressComplementDto addressComplement);

    /**
     * Método que se encarga de estandarizar un complemento de dirección.
     * @param addressComplement Complemento de dirección que se va a estandarizar.
     * @return Complemento de dirección estandarizado.
     * @param isMultiOrigin Indica si la dirección es de una ciudad multi origen.
     * @author Gildardo Mora
     */
    String standardizeComplement(AddressComplementDto addressComplement, boolean isMultiOrigin);

}
