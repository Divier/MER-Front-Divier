package co.com.claro.mer.address.standardize.application.usecases;

import co.com.claro.mer.address.standardize.domain.ports.IStandardizeComplementUseCase;
import co.com.claro.mer.address.standardize.domain.models.dto.AddressComplementDto;
import co.com.claro.mer.utils.enums.AddressTypeEnum;
import co.com.claro.mer.utils.enums.ApartamentosCombinadosEnum;
import co.com.claro.mgl.utils.Constant;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Implementación del caso de uso para estandarizar el complemento de la dirección.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/04
 */
public class StandardizeComplementUseCaseImpl implements IStandardizeComplementUseCase {

    /**
     * Método que se encarga de estandarizar un complemento de dirección.
     * @param addressComplement Complemento de dirección que se va a estandarizar.
     * @return Complemento de dirección estandarizado.
     * @author Gildardo Mora
     */
    @Override
    public String standardizeComplement(AddressComplementDto addressComplement) {
        String standardizeComplement = getStandardizeComplementLevel1(addressComplement)
                + getStandardizeComplementLevel2(addressComplement)
                + getStandardizeComplementLevel3(addressComplement)
                + getStandardizeComplementLevel4(addressComplement)
                + getStandardizeComplementLevel5(addressComplement)
                + getStandardizeComplementNeighborhood(addressComplement)
                + getStandardizeComplementLevel6(addressComplement);

        standardizeComplement = standardizeComplement.replaceAll("\\s+", " ");
        return standardizeComplement.trim();
    }

    @Override
    public String standardizeComplement(AddressComplementDto addressComplement, boolean isMultiOrigin) {
        String standardizeComplement = getStandardizeComplementLevel1(addressComplement) +
                getStandardizeComplementLevel2(addressComplement)
                + getStandardizeComplementLevel3(addressComplement)
                + getStandardizeComplementLevel4(addressComplement)
                + getStandardizeComplementLevel5(addressComplement)
                + (isMultiOrigin ? getStandardizeComplementNeighborhood(addressComplement).replace("BARRIO", "") : "")
                + getStandardizeComplementLevel6(addressComplement);

        standardizeComplement = standardizeComplement.replaceAll("\\s+", " ");
        return standardizeComplement.trim();
    }

    private String getStandardizeComplementLevel1(AddressComplementDto addressComplement) {
        StringBuilder standardizeComplement = new StringBuilder();
        if (StringUtils.isNotBlank(addressComplement.getTypeComplementLevel1())
                && StringUtils.isNotBlank(addressComplement.getValueComplementLevel1())) {
            standardizeComplement.append(addressComplement.getTypeComplementLevel1())
                    .append(" ")
                    .append(addressComplement.getValueComplementLevel1())
                    .append(" ");
        }
        return standardizeComplement.toString();
    }

    private String getStandardizeComplementLevel2(AddressComplementDto addressComplement) {
        StringBuilder standardizeComplement = new StringBuilder();
        if (StringUtils.isNotBlank(addressComplement.getTypeComplementLevel2())
                && StringUtils.isNotBlank(addressComplement.getValueComplementLevel2())) {
            standardizeComplement.append(addressComplement.getTypeComplementLevel2())
                    .append(" ")
                    .append(addressComplement.getValueComplementLevel2())
                    .append(" ");
        }
        return standardizeComplement.toString();
    }

    private String getStandardizeComplementLevel3(AddressComplementDto addressComplement) {
        StringBuilder standardizeComplement = new StringBuilder();
        if (StringUtils.isNotBlank(addressComplement.getTypeComplementLevel3())
                && StringUtils.isNotBlank(addressComplement.getValueComplementLevel3())) {
            standardizeComplement.append(addressComplement.getTypeComplementLevel3())
                    .append(" ")
                    .append(addressComplement.getValueComplementLevel3())
                    .append(" ");
        }
        return standardizeComplement.toString();
    }

    private String getStandardizeComplementLevel4(AddressComplementDto addressComplement) {
        StringBuilder standardizeComplement = new StringBuilder();
        if (StringUtils.isNotBlank(addressComplement.getTypeComplementLevel4())
                && StringUtils.isNotBlank(addressComplement.getValueComplementLevel4())) {
            standardizeComplement.append(addressComplement.getTypeComplementLevel4())
                    .append(" ")
                    .append(addressComplement.getValueComplementLevel4())
                    .append(" ");
        }
        return standardizeComplement.toString();
    }

    private String getStandardizeComplementLevel5(AddressComplementDto addressComplement) {
        StringBuilder standardizeComplement = new StringBuilder();
        if (StringUtils.isNotBlank(addressComplement.getTypeComplementLevel5())
                && StringUtils.isNotBlank(addressComplement.getValueComplementLevel5())) {
            boolean noApartmentCombined = Arrays.stream(ApartamentosCombinadosEnum.values())
                    .noneMatch(x -> x.getTipoApartamento()
                            .equalsIgnoreCase(addressComplement.getTypeComplementLevel5()));

            if (noApartmentCombined) {
                standardizeComplement.append(addressComplement.getTypeComplementLevel5())
                        .append(" ")
                        .append(addressComplement.getValueComplementLevel5())
                        .append(" ");
            }

            standardizeComplement.append(getStandardizeComplementLevel5House(addressComplement));
            standardizeComplement.append(getStandardizeComplementLevel5Floor(addressComplement));
            standardizeComplement.append(getStandardizeComplementLevel5Atm(addressComplement));
        }

        if (StringUtils.isNotBlank(addressComplement.getTypeComplementLevel5())
                && StringUtils.isBlank(addressComplement.getValueComplementLevel5())
                && addressComplement.getTypeComplementLevel5().equalsIgnoreCase("CASA")) {
            standardizeComplement.append(" ")
                    .append("CASA");
            return standardizeComplement.toString();
        }

        if (StringUtils.isNotBlank(addressComplement.getTypeComplementLevel5())
                && StringUtils.isBlank(addressComplement.getValueComplementLevel5())
                && addressComplement.getTypeComplementLevel5().equalsIgnoreCase("RECEPTOR")) {
            standardizeComplement.append(" ")
                    .append("RECEPTOR");
            return standardizeComplement.toString();
        }

        if (StringUtils.isNotBlank(addressComplement.getTypeComplementLevel5())
                && StringUtils.isBlank(addressComplement.getValueComplementLevel5())
                && addressComplement.getTypeComplementLevel5().equalsIgnoreCase("FUENTE")) {
            standardizeComplement.append(" ")
                    .append("FUENTE");
        }

        return standardizeComplement.toString();
    }

    private String getStandardizeComplementLevel5House(AddressComplementDto addressComplement) {
        StringBuilder standardizeComplement = new StringBuilder();
        if (addressComplement.getTypeComplementLevel5().equalsIgnoreCase(Constant.OPT_CASA_PISO)) {
            standardizeComplement.append("CASA")
                    .append(" ")
                    .append(addressComplement.getValueComplementLevel5())
                    .append(" ");
        }
        return standardizeComplement.toString();
    }

    private String getStandardizeComplementLevel5Floor(AddressComplementDto addressComplement) {
        StringBuilder standardizeComplement = new StringBuilder();
        if (addressComplement.getTypeComplementLevel5().equalsIgnoreCase(Constant.OPT_PISO_INTERIOR)
                || addressComplement.getTypeComplementLevel5().equalsIgnoreCase(Constant.OPT_PISO_LOCAL)
                || addressComplement.getTypeComplementLevel5().equalsIgnoreCase(Constant.OPT_PISO_APTO)
        ) {
            standardizeComplement.append("PISO")
                    .append(" ")
                    .append(addressComplement.getValueComplementLevel5())
                    .append(" ");
        }
        return standardizeComplement.toString();
    }

    private String getStandardizeComplementLevel5Atm(AddressComplementDto addressComplement) {
        StringBuilder standardizeComplement = new StringBuilder();
        if (addressComplement.getTypeComplementLevel5().equalsIgnoreCase(Constant.OPT_PISO_CAJERO)) {
            if (addressComplement.getAddressType().equals(AddressTypeEnum.CK)) {
                standardizeComplement.append("PI")
                        .append(" ")
                        .append(addressComplement.getValueComplementLevel5())
                        .append(" ")
                        .append("CAJ")
                        .append(" ")
                        .append(addressComplement.getValueComplementLevel6());
                return standardizeComplement.toString();
            }

            if (addressComplement.getAddressType().equals(AddressTypeEnum.BM)) {
                standardizeComplement.append("CAJ")
                        .append(" ")
                        .append(addressComplement.getValueComplementLevel6())
                        .append(" ")
                        .append("PI")
                        .append(" ")
                        .append(addressComplement.getValueComplementLevel5());
                return standardizeComplement.toString();
            }

            if (addressComplement.getAddressType().equals(AddressTypeEnum.IT)) {
                standardizeComplement.append("PISO")
                        .append(" ")
                        .append(addressComplement.getValueComplementLevel5())
                        .append(" ")
                        .append(addressComplement.getTypeComplementLevel6())
                        .append(" ")
                        .append(addressComplement.getValueComplementLevel6())
                        .append(" ");
                return standardizeComplement.toString();
            }
        }

        return standardizeComplement.toString();
    }

    private String getStandardizeComplementNeighborhood(AddressComplementDto addressComplement) {
        StringBuilder standardizeComplement = new StringBuilder();
        if (StringUtils.isNotBlank(addressComplement.getNeighborhood())) {
            standardizeComplement.append(" ")
                    .append("BARRIO")
                    .append(" ")
                    .append(addressComplement.getNeighborhood())
                    .append(" ");
        }
        return standardizeComplement.toString();
    }

    private String getStandardizeComplementLevel6(AddressComplementDto addressComplement) {
        StringBuilder standardizeComplement = new StringBuilder();
        if (StringUtils.isNotBlank(addressComplement.getTypeComplementLevel6())
                && StringUtils.isNotBlank(addressComplement.getValueComplementLevel6())) {
            standardizeComplement.append(addressComplement.getTypeComplementLevel6())
                    .append(" ")
                    .append(addressComplement.getValueComplementLevel6())
                    .append(" ");
        }
        return standardizeComplement.toString();
    }

}
