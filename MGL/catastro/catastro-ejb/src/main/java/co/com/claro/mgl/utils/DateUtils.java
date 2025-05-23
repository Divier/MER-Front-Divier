package co.com.claro.mgl.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Clase que contiene utilitarios relacionados con Fechas.
 *
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class DateUtils {
    private static final Logger LOGGER = LogManager.getLogger(DateUtils.class.getName());

    /**
     * Obtiene el tiempo transcurrido entre dos fechas.
     *
     * @param fechaInicio Fecha de Inicio.
     * @param fechaFin Fecha de Fin.
     * @return <i>hh:mi:ss</i>
     */
    public static String getTiempoEntreFechas(Date fechaInicio, Date fechaFin) {
        String result = "00:00:00";
        if (fechaInicio != null) {
            Date fechaG = new Date();
            if (fechaFin != null) {
                fechaG = fechaFin;
            }
            long diffDate = fechaG.getTime() - fechaInicio.getTime();
            //Diferencia de las Fechas en Segundos
            double diffSeconds = Math.abs(Double.parseDouble(diffDate + "") / 1000d);
            //Diferencia de las Fechas en Minutos
            double diffMinutes = Math.abs(Double.parseDouble(diffDate + "") / (60d * 1000d));
            long seconds = Math.round(diffSeconds % 60);
            long minutes = Math.round(diffMinutes % 60);
            long hours = Math.round(Math.abs(Double.parseDouble(diffDate + "") / (60d * 60d * 1000d)));

            String hoursStr = (hours < 10 ? "0" + String.valueOf(hours) : String.valueOf(hours));
            String minutesStr = (minutes < 10 ? "0" + String.valueOf(minutes) : String.valueOf(minutes));
            String secondsStr = (seconds < 10 ? "0" + String.valueOf(seconds) : String.valueOf(seconds));

            result = hoursStr + ":" + minutesStr + ":" + secondsStr;
        }
        return result;
    }

    public static String getTiempoEntreFechasConFormato(Date fechaInicio, Date fechaFin) {
        String result = "00:00:00";
        if (fechaInicio != null) {
            Date fechaG = new Date();
            if (fechaFin != null) {
                fechaG = fechaFin;
            }
            SimpleDateFormat formatterIni = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
            SimpleDateFormat formatterFin = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
            String fechaInicial = formatterIni.format(fechaInicio);
            String fechaFinal = formatterFin.format(fechaG);
            Date fechaIniFinal = null;
            Date fechaFinFinal = null;
            try {
                fechaIniFinal = formatterIni.parse(fechaInicial);
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
            try {
                fechaFinFinal = formatterFin.parse(fechaFinal);
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
            double hours = (double) (((double)fechaFinFinal.getTime() - (double)fechaIniFinal.getTime()) / (60 * 60 * 1000));
            DecimalFormat df = new DecimalFormat("0.00");
            String hoursStr = df.format((double) (hours));
            result = hoursStr;
        }
        return result;
    }

    public static String getHoraMinEntreFechasConFormato(Date fechaInicio, Date fechaFin) {
        String result = "00:00:00";
        if (fechaInicio != null) {
            Date fechaG = new Date();
            if (fechaFin != null) {
                fechaG = fechaFin;
            }
            SimpleDateFormat formatterIni = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
            SimpleDateFormat formatterFin = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
            String fechaInicial = formatterIni.format(fechaInicio);
            String fechaFinal = formatterFin.format(fechaG);
            Date fechaIniFinal = null;
            Date fechaFinFinal = null;
            try {
                fechaIniFinal = formatterIni.parse(fechaInicial);
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
            try {
                fechaFinFinal = formatterFin.parse(fechaFinal);
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }

            //Diferencia de las Fechas en Minutos
            double hours = (double) (((double) fechaFinFinal.getTime() - (double) fechaIniFinal.getTime()) / (60 * 60 * 1000));
            DecimalFormat df = new DecimalFormat("0.00");
            String hoursStr = df.format((double) (hours));
            result = hoursStr;
        }
        return result;
    }

    public static String getTiempoEntreFechasTimeSlot(Date fechaInicio, Date fechaFin, String timeSlot) {
        String result = "00:00:00";
        if (fechaInicio != null) {

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatterIni = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            SimpleDateFormat formatterFin = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
            String fechaIni = formatterIni.format(fechaInicio);
            String fechaFinish = formatterFin.format(fechaFin);
            Date fechaIniFinal = null;
            Date fechaFinFinal = null;
            String hoursStr = "";
            try {
                fechaIniFinal = formatterIni.parse(fechaIni);
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
            calendar.setTime(fechaIniFinal);
            // se le agrega a la fecha la hora indicada en el campo time slot
            calendar.add(Calendar.HOUR, Integer.parseInt(timeSlot));
            //lo que más quieras sumar
            Date fechaAgenda = calendar.getTime();
            try {
                fechaFinFinal = formatterFin.parse(fechaFinish);
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
            double hours = (double) (((double)fechaAgenda.getTime() - (double)fechaFinFinal.getTime()) / (60 * 60 * 1000));
            DecimalFormat df = new DecimalFormat("0.00");
            hoursStr = df.format((double) (hours));
            result = hoursStr;
        }
        return result;
    }

    public static String setTimeSlotFechaAgenda(Date fechaAgenda, String timeSlot) {
        String result = "00:00:00";
        if (fechaAgenda != null) {

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatterIni = new SimpleDateFormat("dd-MM-yyyy 00:00:00", Locale.ENGLISH);
            String fechaIni = formatterIni.format(fechaAgenda);
            Date fechaIniFinal = null;
            String hoursStr = null;
            try {
                fechaIniFinal = formatterIni.parse(fechaIni);
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
            calendar.setTime(fechaIniFinal);
            if (timeSlot != null && !timeSlot.contains("Durante")) {
                timeSlot = timeSlot.substring(0, 2);
                // se le agrega a la fecha la hora indicada en el campo time slot
                calendar.add(Calendar.HOUR, Integer.parseInt(timeSlot));
                //lo que más quieras sumar
                Date fechaAgendamiento = calendar.getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
                hoursStr = formatter.format(fechaAgendamiento);;
            } else {
                hoursStr = formatterIni.format(fechaAgenda);
            }

            result = hoursStr;
        }
        return result;
    }

    public static String getTiempoEntreFechasAgendOnyx(Date fechaInicio, Date fechaFin, String timeSlot) {
        String result = "00:00:00";
        if (fechaInicio != null) {
            Date fechaG = new Date();
            if (fechaFin != null) {
                fechaG = fechaFin;
            }
            SimpleDateFormat formatterIni = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
            SimpleDateFormat formatterFin = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
            String fechaInicial = formatterIni.format(fechaInicio);
            Date fechaIniFinal = null;
            Date fechaFinFinal = null;
            try {
                fechaIniFinal = formatterIni.parse(fechaInicial);
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }

            String fechaAgendamiento = setTimeSlotFechaAgenda(fechaFin, timeSlot);
            try {
                fechaFinFinal = formatterFin.parse(fechaAgendamiento);
            } catch (ParseException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }

            //Diferencia de las Fechas en Segundos
             double hours = (double) (((double)fechaFinFinal.getTime() - (double)fechaIniFinal.getTime()) / (60 * 60 * 1000));
            DecimalFormat df = new DecimalFormat("0.00");
            String hoursStr = df.format((double) (hours));
            result = hoursStr;
        }
        return result;
    }

}
