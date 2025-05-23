package co.com.claro.mer.address.standardize.application.usecases;

import co.com.claro.mer.address.standardize.domain.models.dto.ItAddressStructureDto;
import co.com.claro.mer.address.standardize.domain.ports.IStandardizeAddressItUseCase;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Clase que implementa los procesos para estandarizar una dirección de tipo IT (intraducible).
 * @author Gildardo Mora
 * @version 1.0, 2025/03/05
 */
public class StandardizeAddressItUseCaseImpl implements IStandardizeAddressItUseCase {

    /**
     * Método que se encarga de estandarizar una dirección en el formato dirección intraducible.
     * @param address Dirección que se va a estandarizar.
     * @return Dirección estandarizada en el formato dirección intraducible.
     * @author Gildardo Mora
     */
    @Override
    public String standardizeItAddress(ItAddressStructureDto address) {
        StringBuilder standardizeAddress = new StringBuilder();
        standardizeLevel1(address, standardizeAddress);
        standardizeLevel2(address, standardizeAddress);
        standardizeLevel3(address, standardizeAddress);
        standardizeLevel4(address, standardizeAddress);
        standardizeLevel5(address, standardizeAddress);
        standardizeLevel6(address, standardizeAddress);

        String result = standardizeAddress.toString().trim();
        result = result.replaceAll("\\s+", " ");
        return result;
    }

    private void standardizeLevel6(ItAddressStructureDto address, StringBuilder standardizeAddress) {
        if (StringUtils.isNotBlank(address.getItTypeLevel6())
                && StringUtils.isNotBlank(address.getItValueLevel6())) {
            standardizeAddress.append(" ")
                    .append(address.getItTypeLevel6())
                    .append(" ")
                    .append(address.getItValueLevel6())
                    .append(" ");
        }
    }

    private void standardizeLevel5(ItAddressStructureDto address, StringBuilder standardizeAddress) {
        if (StringUtils.isNotBlank(address.getItTypeLevel5())
                && StringUtils.isNotBlank(address.getItValueLevel5())) {
            standardizeAddress.append(" ")
                    .append(address.getItTypeLevel5())
                    .append(" ")
                    .append(address.getItValueLevel5())
                    .append(" ");
        }
    }

    private void standardizeLevel4(ItAddressStructureDto address, StringBuilder standardizeAddress) {
        if (StringUtils.isNotBlank(address.getItTypeLevel4())
                && StringUtils.isNotBlank(address.getItValueLevel4())) {
            standardizeAddress.append(" ")
                    .append(address.getItTypeLevel4())
                    .append(" ")
                    .append(address.getItValueLevel4())
                    .append(" ");
        }
    }

    private void standardizeLevel3(ItAddressStructureDto address, StringBuilder standardizeAddress) {
        if (StringUtils.isNotBlank(address.getItTypeLevel3())
                && StringUtils.isNotBlank(address.getItValueLevel3())) {
            standardizeAddress.append(" ")
                    .append(address.getItTypeLevel3())
                    .append(" ")
                    .append(address.getItValueLevel3())
                    .append(" ");
        }
    }

    private void standardizeLevel2(ItAddressStructureDto address, StringBuilder standardizeAddress) {
        if (StringUtils.isNotBlank(address.getItTypeLevel2())
                && StringUtils.isNotBlank(address.getItValueLevel2())) {
            standardizeAddress.append(" ")
                    .append(address.getItTypeLevel2())
                    .append(" ")
                    .append(address.getItValueLevel2())
                    .append(" ");
        }
    }

    private void standardizeLevel1(ItAddressStructureDto address, StringBuilder standardizeAddress) {
        if (StringUtils.isNotBlank(address.getItTypeLevel1())
                && StringUtils.isNotBlank(address.getItValueLevel1())) {
            standardizeAddress.append(address.getItTypeLevel1())
                    .append(" ")
                    .append(address.getItValueLevel1());
        } else {
            if (StringUtils.isNotBlank(address.getNeighborhood())) {
                standardizeAddress.append("BARRIO")
                        .append(" ")
                        .append(address.getNeighborhood())
                        .append(" ");
            }
        }
    }

    /**
     * Método que se encarga de estandarizar una dirección en el formato placa de dirección intraducible.
     * @param address Dirección que se va a estandarizar.
     * @return Dirección estandarizada en el formato placa de dirección intraducible.
     * @author Gildardo Mora
     */
    @Override
    public String standardizeItPlateAddress(ItAddressStructureDto address) {
        StringBuilder standardizeAddress = new StringBuilder();

        if (StringUtils.isBlank(address.getItTypePlate()) || StringUtils.isBlank(address.getItValuePlate())) {
            return "";
        }

        String[] typePlate = {"MANZANA-CASA", "VP-PLACA", "KDX"};
        boolean isValidPlate = Arrays.stream(typePlate)
                .anyMatch(plate -> address.getItTypePlate().trim().equalsIgnoreCase(plate));

        if (isValidPlate) {
            standardizeAddress.append(address.getItTypePlate())
                    .append(" ")
                    .append(address.getItValuePlate())
                    .append(" ");
        } else {
            standardizeAddress.append(address.getItValuePlate())
                    .append(" ");
        }

        if (address.getItTypePlate().equalsIgnoreCase("CONTADOR")
                || address.getItTypePlate().equalsIgnoreCase("KDX")) {
            standardizeAddress = new StringBuilder(address.getItTypePlate() + " " + address.getItValuePlate() + " ");
        }

        return standardizeAddress.toString().trim();
    }

}
