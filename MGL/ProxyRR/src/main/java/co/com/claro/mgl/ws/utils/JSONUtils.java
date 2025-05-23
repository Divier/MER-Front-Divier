/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.ws.utils;

import co.com.claro.mgl.error.ApplicationException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Utilitarios de JSON.
 *
 * @author espinosadiea
 */
public class JSONUtils {

    private static final Logger LOGGER = LogManager.getLogger(JSONUtils.class);

    /**
     * Obtiene la representaci&oacute;n JSON de un Objeto.
     *
     * @param objeto Objeto a convertir.
     * @return Object as JSON String.
     * @throws ApplicationException
     */
    public static String objetoToJson(Object objeto) throws ApplicationException {
        String jsonString = null;
        if (objeto != null) {
            try {
                /**
                 * Flag que determina si alguno de los atributos de la clase
                 * contiene al menos un nombre distinto al nombre asignado en la
                 * anotaci&oacute;n.
                 */
                boolean contieneAtributosDiferentes = false;

                Field[] fields = objeto.getClass().getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    String nombreField, nombreAtributo;

                    List<Field> listaFields = Arrays.asList(fields);
                    Iterator<Field> itFields = listaFields.iterator();
                    while (itFields.hasNext() && !contieneAtributosDiferentes) {
                        Field field = itFields.next();
                        if (field != null) {

                            // Nombre del atributo.
                            nombreAtributo = field.getName();

                            Annotation[] anotaciones = field.getAnnotations();
                            if (anotaciones != null && anotaciones.length > 0) {
                                List<Annotation> listaAnotaciones = Arrays.asList(anotaciones);
                                Iterator<Annotation> itAnotaciones = listaAnotaciones.iterator();

                                while (itAnotaciones.hasNext() && !contieneAtributosDiferentes) {
                                    Annotation anotacion = itAnotaciones.next();
                                    if (anotacion instanceof JsonProperty) {
                                        JsonProperty obj = (JsonProperty) anotacion;
                                        nombreField = obj.value();
                                        // Verificar si el nombre del atributo es distinto al nombre asociado a la anotacion.
                                        contieneAtributosDiferentes = !nombreField.equals("##default") && !nombreField.equals(nombreAtributo);
                                    } else if (anotacion instanceof XmlElement) {
                                        XmlElement obj = (XmlElement) anotacion;
                                        nombreField = obj.name();
                                        // Verificar si el nombre del atributo es distinto al nombre asociado a la anotacion.
                                        contieneAtributosDiferentes = !nombreField.equals("##default") && !nombreField.equals(nombreAtributo);
                                    }
                                }
                            }
                        }
                    }
                }

                if (!contieneAtributosDiferentes) {
                    Gson g = new Gson();
                    jsonString = g.toJson(objeto);
                } else {
                    // Utilizar libreria Jackson.
                    ObjectMapper objectMapper = new ObjectMapper();
                    jsonString = objectMapper.writeValueAsString(objeto);
                }

            } catch (RuntimeException e) {
                LOGGER.error("Error en __. EX000 " + e.getMessage(), e);
                throw new ApplicationException("Error al momento de convertir el objeto a JSON: " + e.getMessage(), e);
            } catch (Exception e) {
                LOGGER.error("Error en __. EX000 " + e.getMessage(), e);
                throw new ApplicationException("Error al momento de convertir el objeto a JSON: " + e.getMessage(), e);
            }
        }

        return (jsonString);
    }
}
