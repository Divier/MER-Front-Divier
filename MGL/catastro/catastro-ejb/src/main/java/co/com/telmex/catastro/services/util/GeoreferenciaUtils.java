package co.com.telmex.catastro.services.util;

import co.com.claro.mer.utils.StringToolUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Clase GeoreferenciaUtils
 *
 * @author Nataly Orozco Torres
 * @version 1.0
 */
@Log4j2
public class GeoreferenciaUtils {

    /**
     * Validar si una dirección existe o no según la dirección estandarizada y el estado que tenga.
     *
     * @param address        {@link String}
     * @param state          {@link String}
     * @param valComplemento {@link String}
     * @return {@code boolean} Retorna true si la dirección es válida, false en caso contrario.
     */
    public boolean validExistAddress(String address, String state, String valComplemento) {
        if (StringUtils.isBlank(address) || StringUtils.isBlank(state) || StringUtils.isBlank(valComplemento)) {
            String msgError = "Se recibió un valor no apto para validar la dirección: "
                    + "address = '{}', state = '{}', valComplemento = '{}'";
            LOGGER.warn(msgError, address, state, valComplemento);
            return false;
        }

        List<String> validStates = Arrays.asList("B", "D", "F", "L", "M", "N", "I", "Y");

        if (!StringToolUtils.containsOnlyNumbers(valComplemento)) {
            LOGGER.warn("El complemento '{}' debería ser un número", valComplemento);
            return false;
        }

        try {
            int value = Integer.parseInt(valComplemento);
            return validStates.contains(state) && value >= 95;
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

}
