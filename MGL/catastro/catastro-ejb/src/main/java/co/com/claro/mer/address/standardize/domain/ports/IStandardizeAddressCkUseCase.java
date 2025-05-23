package co.com.claro.mer.address.standardize.domain.ports;

import co.com.claro.mer.address.standardize.domain.models.dto.CkAddressStructureDto;

/**
 * Interfaz que define el caso de uso para estandarizar una dirección en el formato de calle - carrera.
 * @author Gildardo Mora
 * @version 1.0, 2025/02/28
 */
public interface IStandardizeAddressCkUseCase {

   /**
    * Método que se encarga de estandarizar una dirección en el formato de calle - carrera.
    * @param address Dirección que se va a estandarizar.
    * @return Dirección estandarizada en el formato de calle - carrera.
    * @author Gildardo Mora
    */
   String standardizeCkAddress(CkAddressStructureDto address);

}
