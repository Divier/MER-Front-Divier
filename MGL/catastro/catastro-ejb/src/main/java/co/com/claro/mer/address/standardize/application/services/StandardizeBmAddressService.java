package co.com.claro.mer.address.standardize.application.services;

import co.com.claro.mer.address.standardize.domain.models.dto.BmAddressStructureDto;
import co.com.claro.mer.address.standardize.domain.ports.IStandardizeAddressBmUseCase;
import co.com.claro.mer.address.standardize.domain.ports.IStandardizeComplementUseCase;
import lombok.RequiredArgsConstructor;

/**
 * Clase que implementa los procesos para estandarizar una dirección en el formato dirección barrio - manzana.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/05
 */
@RequiredArgsConstructor
public class StandardizeBmAddressService {

    private final IStandardizeAddressBmUseCase standardizeAddressBmUseCase;
    private final IStandardizeComplementUseCase standardizeComplementUseCase;

    /**
     * Método que se encarga de estandarizar una dirección en el formato dirección barrio - manzana.
     * @param address Dirección que se va a estandarizar.
     * @return Dirección estandarizada.
     * @author Gildardo Mora
     */
    public String standardizeBmAddress(BmAddressStructureDto address) {
        return standardizeAddressBmUseCase.standardizeBmAddress(address)
                + " "
                + standardizeComplementUseCase.standardizeComplement(address.getAddressComplement()).toUpperCase();
    }

}
