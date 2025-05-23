/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.ws.utils.rest;

import co.com.claro.mgl.error.ApplicationException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Utilitario para manejo y conversion de JSON en Rest Services.
 * Ajustado para implementar una versión más reciente de la librería jackson
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/03/23
 * @see co.com.claro.mgl.ws.utils.JSONUtils
 */
public class JsonRestUtil {

    private static final Logger LOGGER = LogManager.getLogger(JsonRestUtil.class);
    private static String nombreField;

    private JsonRestUtil() {
    }

    /**
     * Obtiene la representación JSON de un Objeto.
     *
     * @param objeto Objeto a convertir.
     * @return Object as JSON String.
     * @throws ApplicationException "Excepción personalizada
     */
    public static String convertirObjetoToJson(Object objeto) throws ApplicationException {

        String jsonString;

        try {
            Objects.requireNonNull(objeto);

            /*
             * Flag que determina si alguno de los hasDifferentAttributes de la clase
             * contiene al menos un nombre distinto al nombre asignado en la
             * anotación.
             */
            boolean contieneAtributosDiferentes = false;
            Field[] fields = objeto.getClass().getDeclaredFields();

            if (fields.length > 0) {
                nombreField = "";
                List<Field> listaFields = Arrays.asList(fields);
                Iterator<Field> itFields = listaFields.iterator();

                while (itFields.hasNext() && !contieneAtributosDiferentes) {
                    Field field = itFields.next();
                    contieneAtributosDiferentes = hasDifferentAttributes(field);
                }
            }

            if (!contieneAtributosDiferentes) {
                Gson g = new Gson();
                jsonString = g.toJson(objeto);
                return (jsonString);
            }

            // Utilizar librería Jackson.
            ObjectMapper objectMapper = new ObjectMapper();
            jsonString = objectMapper.writeValueAsString(objeto);
            return (jsonString);

        } catch (RuntimeException e) {
            String msgError = "Error al momento de convertir el objeto a JSON: EX000 " + e.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(msgError, e);

        } catch (Exception e) {
            String msgError = "Error al momento de convertir el objeto a JSON: " + e.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Verifica si los atributos recibidos son diferentes a los puestos en las anotaciones.
     *
     * @param field {@link Field}
     * @return boolean
     */
    private static boolean hasDifferentAttributes(Field field) {

        if (Objects.isNull(field)) {
            return false;
        }
        boolean atributosDiferentes = false;
        String nombreAtributo = field.getName();
        Annotation[] anotaciones = field.getAnnotations();

        if (Objects.isNull(anotaciones) || anotaciones.length == 0) {
            return false;
        }
        List<Annotation> listaAnotaciones = Arrays.asList(anotaciones);
        Iterator<Annotation> itAnotaciones = listaAnotaciones.iterator();

        while (itAnotaciones.hasNext() && !atributosDiferentes) {
            Annotation anotacion = itAnotaciones.next();
            atributosDiferentes = isDifferentAttribute(anotacion, nombreAtributo);
        }
        return atributosDiferentes;
    }

    /**
     * Verifica si el atributo es diferente al asignado en la anotación
     *
     * @param annotation     {@link Annotation}
     * @param nombreAtributo {@link String}
     * @return boolean
     */
    private static boolean isDifferentAttribute(Annotation annotation, String nombreAtributo) {

        if (annotation instanceof JsonProperty) {
            JsonProperty obj = (JsonProperty) annotation;
            nombreField = obj.value();
            // Verificar si el nombre del atributo es distinto al nombre asociado a la anotacion.
            return !nombreField.equals("##default") && !nombreField.equals(nombreAtributo);
        }
        return false;
    }

}

