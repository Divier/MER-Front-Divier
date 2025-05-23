package co.com.claro.mer.address.standardize.application.usecases;

import co.com.claro.mer.address.standardize.domain.models.dto.BmAddressStructureDto;
import co.com.claro.mer.address.standardize.domain.ports.IStandardizeAddressBmUseCase;
import org.apache.commons.lang3.StringUtils;

/**
 * Implementación del caso de uso para estandarizar una dirección en el formato dirección barrrio - manzana.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/05
 */
public class StandardizeAddressBmUseCaseImpl implements IStandardizeAddressBmUseCase {

    /**
     * Método que se encarga de estandarizar una dirección en el formato dirección barrio - manzana.
     * @param address Dirección que se va a estandarizar.
     * @return Dirección estandarizada en el formato
     * @author Gildardo Mora
     */
    @Override
    public String standardizeBmAddress(BmAddressStructureDto address) {
        StringBuilder standardizeAddress = new StringBuilder();

        if (StringUtils.isNotBlank(address.getMzTypeLevel1())
                && StringUtils.isNotBlank(address.getMzValueLevel1())) {
            standardizeAddress.append(address.getMzTypeLevel1())
                    .append(" ")
                    .append(address.getMzValueLevel1());
        } else {
            if (StringUtils.isNotBlank(address.getNeighborhood())) {
                standardizeAddress.append("BARRIO")
                        .append(" ")
                        .append(address.getNeighborhood())
                        .append(" ");
            }
        }

        if (StringUtils.isNotBlank(address.getMzTypeLevel2())
                && StringUtils.isNotBlank(address.getMzValueLevel2())) {
            standardizeAddress.append(" ")
                    .append(address.getMzTypeLevel2())
                    .append(" ")
                    .append(address.getMzValueLevel2())
                    .append(" ");
        }

        if (StringUtils.isNotBlank(address.getMzTypeLevel3())
                && StringUtils.isNotBlank(address.getMzValueLevel3())) {
            standardizeAddress.append(" ")
                    .append(address.getMzTypeLevel3())
                    .append(" ")
                    .append(address.getMzValueLevel3())
                    .append(" ");
        }

        if (StringUtils.isNotBlank(address.getMzTypeLevel4())
                && StringUtils.isNotBlank(address.getMzValueLevel4())) {
            standardizeAddress.append(" ")
                    .append(address.getMzTypeLevel4())
                    .append(" ")
                    .append(address.getMzValueLevel4())
                    .append(" ");
        }

        if (StringUtils.isNotBlank(address.getMzTypeLevel5())
                && StringUtils.isNotBlank(address.getMzValueLevel5())) {
            standardizeAddress.append(" ")
                    .append(address.getMzTypeLevel5())
                    .append(" ")
                    .append(address.getMzValueLevel5())
                    .append(" ");
        }

        String result = standardizeAddress.toString().trim();
        result = result.replaceAll("\\s+", " ");
        return result;
    }

}
