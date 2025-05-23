package co.com.claro.mer.utils;

import co.com.claro.mgl.error.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * Utilitario para operaciones con fechas.
 * <p>
 * Realiza cálculos de rango entre fechas, conversiones de tipos de objeto, formato
 * y demás operaciones relacionadas.
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/05/20
 */
public class DateToolUtils {

    private DateToolUtils() throws ApplicationException {
        throw new ApplicationException("DateToolUtils");
    }

    private static final int ERROR_DATE_IS_AFTER_TODAY = -1;
    private static final Logger LOGGER = LogManager.getLogger(DateToolUtils.class);

    /* -------------- Operaciones con rangos de Fecha  ---------------- */

    /**
     * Calcula el número de días trascurridos desde una determinada fecha hasta la fecha actual
     *
     * @param oldDate {@link Date} Fecha a comparar
     * @return Número de días transcurridos, Retorna "-1" si la fecha es superior a hoy.
     * @throws ApplicationException Excepción personalizada de app
     * @author Gildardo Mora
     */
    public static int getNumberOfDaysElapsedUntilToday(Date oldDate) throws ApplicationException {
        LocalDateTime dateConverted = convertDateToLocalDateTime(oldDate);
        return getNumberOfDaysElapsedUntilToday(dateConverted);
    }

    /**
     * Calcula el número de días trascurridos desde una determinada fecha hasta la fecha actual
     *
     * @param oldDate {@link LocalDate} Fecha a comparar
     * @return Número de días transcurridos, Retorna "-1" si la fecha es superior a hoy.
     * @throws ApplicationException Excepción personalizada de app
     * @author Gildardo Mora
     */
    public static int getNumberOfDaysElapsedUntilToday(LocalDate oldDate) throws ApplicationException {
        LocalDateTime dateConverted = convertLocalDateToLocalDateTime(oldDate);
        return getNumberOfDaysElapsedUntilToday(dateConverted);
    }

    /**
     * Calcula el número de días trascurridos desde una fecha determinada hasta la fecha actual
     *
     * @param oldDate {@link LocalDateTime} Fecha a comparar
     * @return Número de días transcurridos, Retorna "-1" si la fecha es superior a hoy.
     * @throws ApplicationException Excepción personalizada de app
     * @author Gildardo Mora
     */
    public static int getNumberOfDaysElapsedUntilToday(LocalDateTime oldDate) throws ApplicationException {
        try {
            Objects.requireNonNull(oldDate, "La fecha a validar no puede ser nula");
            LocalDateTime dateToday = LocalDateTime.now();

            if (oldDate.isAfter(dateToday)) {
                return ERROR_DATE_IS_AFTER_TODAY;
            }

            return (int) Duration.between(oldDate, dateToday).toDays();

        } catch (Exception e) {
            LOGGER.error("Ocurrió un error al momento de obtener el número de dias : ", e);
            throw new ApplicationException(e);
        }
    }

    /**
     * Calcula el número de días trascurridos entre 2 fechas
     *
     * @param oldDate    {@link LocalDateTime} Fecha antigua a comparar
     * @param recentDate {@link LocalDateTime} Fecha reciente a comparar
     * @return Número de días transcurridos, Retorna "-1" si la fecha es superior a hoy.
     * @throws ApplicationException Excepción personalizada de app
     * @author Gildardo Mora
     */
    public static int getNumberOfDaysElapsedBetween(LocalDateTime oldDate, LocalDateTime recentDate) throws ApplicationException {
        try {
            if (Objects.isNull(oldDate) || Objects.isNull(recentDate)) {
                throw new ApplicationException("Las fechas a validar no pueden ser nulas");
            }

            if (oldDate.isAfter(recentDate)) {
                return ERROR_DATE_IS_AFTER_TODAY;
            }

            return (int) Duration.between(oldDate, recentDate).toDays();

        } catch (Exception e) {
            LOGGER.error("Ocurrió un error al momento de obtener el número de dias", e);
            throw new ApplicationException(e);
        }
    }


    /*  ------------- Conversión entre tipos de Objetos de Fecha  ----------- */

    /**
     * Convierte una fecha de tipo Date a tipo LocalDateTime
     *
     * @param date {@link Date} fecha a convertir
     * @return {@link LocalDateTime} Fecha convertida
     * @author Gildardo Mora
     */
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        Objects.requireNonNull(date, "La fecha a convertir no puede ser nula");
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Convierte una fecha de tipo LocalDate a tipo LocalDateTime
     *
     * @param localDate {@link LocalDate} fecha a convertir
     * @return {@link LocalDateTime} Fecha convertida
     * @author Gildardo Mora
     */
    public static LocalDateTime convertLocalDateToLocalDateTime(LocalDate localDate) {
        Objects.requireNonNull(localDate, "La fecha a convertir no puede ser nula");
        return localDate.atTime(LocalTime.now());
    }

    /**
     * Permite dar formato a fechas que sean tipo LocalDateTime
     *
     * @param date   {@link LocalDate}
     * @param format {@link String}
     * @return {@link String}
     */
    public static String formatoFechaLocalDate(LocalDate date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }
    
     /**
     * Permite dar formato a fechas que sean tipo LocalDateTime
     *
     * @param date   {@link LocalDateTime}
     * @param format {@link String}
     * @return {@link String}
     */
    public static String formatoFechaLocalDateTime(LocalDateTime date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    /**
     * Permite dar formato a fechas que sean tipo Date
     *
     * @param date   {@link Date}
     * @param format {@link String}
     * @return {@link String}
     */
    public static String formatoFechaDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * Permite validar si una fecha cumple con un formato específico
     * @param fecha fecha a validar
     * @param formato formato esperado
     * @return True = Cumple con el formato, False = No cumple con el formato
     *@author Manuel Hernández Rivas
     */
    public static  boolean validarFormatoFecha(String fecha, String formato) {
        if (Objects.isNull(fecha) || Objects.isNull(formato) || fecha.length() != formato.length()) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        sdf.setLenient(false);
        try {
            sdf.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}