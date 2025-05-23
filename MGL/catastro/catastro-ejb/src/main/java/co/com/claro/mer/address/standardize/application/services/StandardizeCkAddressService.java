package co.com.claro.mer.address.standardize.application.services;

import co.com.claro.mer.address.standardize.domain.models.dto.CkAddressStructureDto;
import co.com.claro.mer.address.standardize.domain.ports.IStandardizeAddressCkUseCase;
import co.com.claro.mer.address.standardize.domain.ports.IStandardizeComplementUseCase;
import lombok.RequiredArgsConstructor;

/**
 * Clase que implementa los procesos para estandarizar una dirección en el formato dirección calle - carrera.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/02/28
 */
@RequiredArgsConstructor
public class StandardizeCkAddressService {

    private final IStandardizeAddressCkUseCase standardizeAddressCkUseCase;
    private final IStandardizeComplementUseCase standardizeComplementUseCase;

    /**
     * Método que se encarga de estandarizar una dirección en el formato dirección calle - carrera.
     * @param address Dirección que se va a estandarizar.
     * @return Dirección estandarizada.
     * @author Gildardo Mora
     */
    public String standardizeCkAddress(CkAddressStructureDto address, boolean isMultiOrigin) {
        return standardizeAddressCkUseCase.standardizeCkAddress(address)
                + " "
                + standardizeComplementUseCase.standardizeComplement(address.getAddressComplement(), isMultiOrigin).toUpperCase();
    }

}
