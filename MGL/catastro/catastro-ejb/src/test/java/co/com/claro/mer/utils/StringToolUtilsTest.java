package co.com.claro.mer.utils;

import co.com.claro.mer.utils.enums.DelimitadorEnum;
import co.com.claro.mgl.error.ApplicationException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Pruebas unitarias asociadas a {@link StringToolUtils}
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/10/18
 */
class StringToolUtilsTest {

    /**
     * Comprueba el funcionamiento del proceso de conversion de String con un delimitador a una Lista
     *
     * @throws ApplicationException Excepción de la App
     */
    @Test
    void convertStringToList() throws ApplicationException {
        List<String> list = new ArrayList<>(Arrays.asList("BI", "DTH", "FOG"));
        String dataString = "BI|DTH|FOG";
        assertEquals(list, StringToolUtils.convertStringToList(dataString, DelimitadorEnum.PIPE, true));
        dataString = "BI,DTH,FOG";
        assertEquals(list, StringToolUtils.convertStringToList(dataString, DelimitadorEnum.COMA, true));
        dataString = "BI DTH FOG";
        assertEquals(list, StringToolUtils.convertStringToList(dataString, DelimitadorEnum.ESPACIO, true));
        dataString = "BI.DTH.FOG";
        assertEquals(list, StringToolUtils.convertStringToList(dataString, DelimitadorEnum.PUNTO, true));
        dataString = "BI;DTH;FOG";
        assertEquals(list, StringToolUtils.convertStringToList(dataString, DelimitadorEnum.PUNTO_Y_COMA, true));
        dataString = "BI_DTH_FOG";
        assertEquals(list, StringToolUtils.convertStringToList(dataString, DelimitadorEnum.GUION_BAJO, true));
        dataString = "BI-DTH-FOG";
        assertEquals(list, StringToolUtils.convertStringToList(dataString, DelimitadorEnum.GUION_MEDIO, true));
    }

    @Test
    void convertirCaracteresMayusculas() {
        String dataString = "bi dth foh";
        assertEquals("bi dtH foH", StringToolUtils.convertirCaracteresMayusculas(dataString, 'h'));
    }

    @Test
    void reemplazarCaracteres() {
        String dataString = "bi dth foh";
        assertEquals("bi dtt fot", StringToolUtils.reemplazarCaracteres(dataString, 'h', 't'));
    }
}