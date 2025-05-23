package co.com.claro.mer.error.application.usecases;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Permite obtener detalles exactos de la traza del error ocurrido
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/27
 */
public class ObtainErrorDetails {

    public String obtainDetails(Exception exception) {
        if (Objects.isNull(exception)) {
            return "<br>NO SE RECIBIÓ UNA CAUSA DE EXCEPCIÓN <br>";
        }

        final String SEPARATOR_LINE = "______________________________________";
        Throwable excepcionInterna = exception;
        StackTraceElement stackTrace = null;
        StackTraceElement[] stackTraceElements = null;
        List<String> mensajesInternos = new ArrayList<>();

        //Se captura los datos de la excepción más interna
        while (Objects.nonNull(excepcionInterna.getCause())) {
            excepcionInterna = excepcionInterna.getCause();
            stackTrace = excepcionInterna.getStackTrace()[0];
            stackTraceElements = excepcionInterna.getStackTrace();
            mensajesInternos.add(excepcionInterna.getMessage());
        }

        if (Objects.isNull(stackTrace)) {
            stackTrace = exception.getStackTrace()[0];
        }

        if (Objects.isNull(stackTraceElements) || stackTraceElements.length == 0) {
            stackTraceElements = exception.getStackTrace();
        }

        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("<br>").append("EXCEPCIÓN CAUSADA POR: ").append("<br>");
        errorMessage.append(SEPARATOR_LINE).append("<br>");
        errorMessage.append("<br>");
        List<String> causeList = new ArrayList<>(Arrays.asList(excepcionInterna.toString().split(StringUtils.SPACE)));

        for (String cause : causeList) {
            errorMessage.append(cause).append(" ");
        }

        errorMessage.append("<br>").append("<br>");
        errorMessage.append("DETALLE DE EXCEPCIÓN: ").append("<br>");
        errorMessage.append(SEPARATOR_LINE).append("<br>");
        errorMessage.append("MENSAJE PRINCIPAL: ").append(exception.getMessage() != null ? exception.getMessage() : "");
        errorMessage.append("<br>").append("CLASE: ").append(stackTrace.getClassName());
        errorMessage.append("<br>").append("MÉTODO: ").append(stackTrace.getMethodName());
        errorMessage.append("<br>").append("NÚMERO DE LÍNEA: ").append(stackTrace.getLineNumber());
        errorMessage.append("<br>").append("MENSAJES INTERNOS: ");
        mensajesInternos.removeIf(StringUtils::isNumeric);
        mensajesInternos.forEach(msg -> errorMessage.append("<br>").append(msg));
        errorMessage.append("<br>").append("<br>");
        errorMessage.append("PILA DEL ERROR: ").append("<br>");
        errorMessage.append(SEPARATOR_LINE).append("<br>");
        errorMessage.append("<br>");

        for (StackTraceElement stackTraceElement : stackTraceElements) {
            errorMessage.append(stackTraceElement.toString()).append("<br>");
        }

        return errorMessage.toString().replace("\n", "")
                .replace("\r", "")
                .replace("\"", "")
                .replace("'", "");
    }

}
