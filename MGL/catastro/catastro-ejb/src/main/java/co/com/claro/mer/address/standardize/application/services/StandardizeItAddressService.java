package co.com.claro.mer.address.standardize.application.services;

import co.com.claro.mer.address.standardize.domain.models.dto.ItAddressStructureDto;
import co.com.claro.mer.address.standardize.domain.ports.IStandardizeAddressItUseCase;
import co.com.claro.mer.address.standardize.domain.ports.IStandardizeComplementUseCase;
import lombok.RequiredArgsConstructor;

/**
 * Clase que implementa los procesos para estandarizar una dirección de tipo IT (intraducible).
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/05
 */
@RequiredArgsConstructor
public class StandardizeItAddressService {

    private final IStandardizeAddressItUseCase standardizeAddressItUseCase;
    private final IStandardizeComplementUseCase standardizeComplementUseCase;

    /**
     * Método que se encarga de estandarizar una dirección en el formato dirección intraducible.
     * @param address Dirección que se va a estandarizar.
     * @return Dirección estandarizada en el formato dirección intraducible.
     * @author Gildardo Mora
     */
    public String standardizeItAddress(ItAddressStructureDto address) {
        return standardizeAddressItUseCase.standardizeItAddress(address).toUpperCase()
                + " "
                + standardizeAddressItUseCase.standardizeItPlateAddress(address).toUpperCase()
                + " "
                + standardizeComplementUseCase.standardizeComplement(address.getAddressComplement()).toUpperCase();
    }

}
