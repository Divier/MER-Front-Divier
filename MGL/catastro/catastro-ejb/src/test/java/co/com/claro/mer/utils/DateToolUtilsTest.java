package co.com.claro.mer.utils;

import co.com.claro.mer.utils.constants.ConstantsCargueMasivo;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateToolUtilsTest {

    @Test
    void formatoFechaLocalDateTime() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 6);
        Date date = calendar.getTime();

        assertEquals("06/12/2024", DateToolUtils.formatoFechaDate(date, ConstantsCargueMasivo.DATE_FORMAT_FILTROS_FECHA));
    }
}