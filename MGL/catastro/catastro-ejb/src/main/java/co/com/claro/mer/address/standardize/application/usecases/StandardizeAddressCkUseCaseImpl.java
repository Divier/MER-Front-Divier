package co.com.claro.mer.address.standardize.application.usecases;

import co.com.claro.mer.address.standardize.domain.ports.IStandardizeAddressCkUseCase;
import co.com.claro.mer.address.standardize.domain.models.dto.CkAddressStructureDto;
import co.com.claro.mgl.utils.Constant;
import org.apache.commons.lang3.StringUtils;

/**
 * Implementación del caso de uso para estandarizar direcciones de tipo CK (calle - carrera).
 * @author Gildardo Mora
 * @version 1.0, 2025/02/28
 */
public class StandardizeAddressCkUseCaseImpl implements IStandardizeAddressCkUseCase {

    /**
     * Método que se encarga de estandarizar una dirección en el formato de calle - carrera.
     * @param address Dirección que se va a estandarizar.
     * @return Dirección estandarizada en el formato de calle - carrera.
     * @author Gildardo Mora
     */
    @Override
    public String standardizeCkAddress(CkAddressStructureDto address) {
        String standardizeAddress = getStandardizeMainTrackDirection(address)
                + getStandardizeGenerativeTrackDirection(address);
        standardizeAddress = standardizeAddress.replaceAll("\\s+", " ");
        return standardizeAddress.trim();
    }

    /**
     * Obtiene la dirección estandarizada de la vía principal.
     *
     * @param address Datos de la dirección a estandarizar
     * @return Dirección estandarizada de la vía principal
     * @author Gildardo Mora
     */
    private String getStandardizeMainTrackDirection(CkAddressStructureDto address){
        StringBuilder standardizeMainAddress = new StringBuilder();

        if (StringUtils.isNoneBlank(address.getMainTrackType(), address.getNumMainTrack())) {
            standardizeMainAddress.append(address.getMainTrackType())
                    .append(" ")
                    .append(address.getNumMainTrack())
                    .append(" ");
        }

        if (StringUtils.isNotBlank(address.getMainTrackFirstModifier())) {
            standardizeMainAddress.append(address.getMainTrackFirstModifier()).append(" ");
        }

        if (StringUtils.isNotBlank(address.getMainTrackSecondModifier())) {
            standardizeMainAddress.append("- ").append(address.getMainTrackSecondModifier()).append(" ");
        }

        if (StringUtils.isNotBlank(address.getBisMainTrack())) {
            if (address.getBisMainTrack().equalsIgnoreCase(Constant.OPTION_BIS)) {
                standardizeMainAddress.append(address.getBisMainTrack()).append(" ");
            } else {
                standardizeMainAddress.append(Constant.OPTION_BIS).append(" ")
                        .append(address.getBisMainTrack()).append(" ");
            }
        }

        if (StringUtils.isNotBlank(address.getMainTrackQuadrant())) {
            standardizeMainAddress.append(address.getMainTrackQuadrant()).append(" ");
        }

        return standardizeMainAddress.toString();
    }

    /**
     * Obtiene la dirección estandarizada de la vía generadora.
     *
     * @param address Datos de la dirección a estandarizar
     * @return Dirección estandarizada de la vía generadora
     * @author Gildardo Mora
     */
    private String getStandardizeGenerativeTrackDirection(CkAddressStructureDto address) {

        StringBuilder standardizeGenerativeAddress = new StringBuilder();
        String generativeTrack = buildGenerativeTrack(address);

        if (StringUtils.isNotBlank(generativeTrack)) {
            standardizeGenerativeAddress.append(generativeTrack);
        }

        if (StringUtils.isNotBlank(address.getGenerativeTrackFirstModifier())) {
            standardizeGenerativeAddress.append(address.getGenerativeTrackFirstModifier())
                    .append(" ");
        }

        if (StringUtils.isNotBlank(address.getGenerativeTrackSecondModifier())) {
            standardizeGenerativeAddress.append("- ")
                    .append(address.getGenerativeTrackSecondModifier())
                    .append(" ");
        }

        if (StringUtils.isNotBlank(address.getGenTrackCoDirectionLetter())) {
            standardizeGenerativeAddress.append("- ")
                    .append(address.getGenTrackCoDirectionLetter())
                    .append(" ");
        }

        if (StringUtils.isNotBlank(address.getBisGenerativeTrack())) {
            if (address.getBisGenerativeTrack().equalsIgnoreCase(Constant.OPTION_BIS)) {
                standardizeGenerativeAddress.append(address.getBisGenerativeTrack()).append(" ");
            } else {
                standardizeGenerativeAddress.append(Constant.OPTION_BIS )
                        .append(" ")
                        .append(address.getBisGenerativeTrack())
                        .append(" ");
            }
        }

        if (StringUtils.isNotBlank(address.getGenerativeTrackQuadrant())) {
            standardizeGenerativeAddress.append(address.getGenerativeTrackQuadrant())
                    .append(" ");
        }

        if (StringUtils.isNotBlank(address.getDirectionPlate())) {
            standardizeGenerativeAddress.append(address.getDirectionPlate());
        }

        return standardizeGenerativeAddress.toString();
    }

    /**
     * Construye el fragmento de la vía generadora.
     *
     * @param address Datos de la dirección a estandarizar
     * @return Fragmento de la vía generadora de la dirección estandarizada
     * @author Gildardo Mora
     */
    private String buildGenerativeTrack(CkAddressStructureDto address){
        String generativeTrack = "";
        if (StringUtils.isBlank(address.getNumGenerativeTrack())) {
            return generativeTrack;
        }

        if (StringUtils.isNotBlank(address.getNumGenerativeTrack())) {
            if (StringUtils.isNotBlank(address.getGenerativeTrackType())
                    && !address.getGenerativeTrackType().equalsIgnoreCase(Constant.VACIO)
                    && !address.getGenerativeTrackType().equalsIgnoreCase(Constant.V_VACIO)) {
                generativeTrack= address.getGenerativeTrackType() + " ";
            } else {
                generativeTrack += " # ";
            }

            generativeTrack += address.getNumGenerativeTrack() + " ";
        }

        return generativeTrack;
    }

}
