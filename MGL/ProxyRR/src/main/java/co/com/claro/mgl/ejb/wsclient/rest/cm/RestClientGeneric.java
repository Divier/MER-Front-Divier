package co.com.claro.mgl.ejb.wsclient.rest.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.ws.utils.rest.JsonRestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Objects;

/**
 * Cliente genérico para conexión a servicios Rest
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/03/15
 */
public class RestClientGeneric {

    private static final Logger LOGGER = LogManager.getLogger(RestClientGeneric.class);
    private final Client client;
    private final WebResource webResource;

    /**
     * Crea el cliente rest
     *
     * @param baseUri         Uri de conexión al servicio Rest
     * @param pathServiceName Ruta final del acceso al recurso del servicio (no siempre se usa)
     */
    public RestClientGeneric(String baseUri, String pathServiceName) {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(baseUri).path(pathServiceName);
    }

    /**
     * Permite consumir servicio Rest por método HTTP POST
     *
     * @param responseType Indica la estructura de respuesta del servicio
     * @param request      Petición del servicio con la data requerida
     * @param <T>          Clase que retornará en la estructura del response
     * @return temporalResponse
     * @throws UniformInterfaceException Si la respuesta en http no es la esperada
     * @throws IOException               En caso de interrupción de la operación
     * @throws ApplicationException      Excepción de la aplicación en caso de algún error
     */
    public <T> T callWebServiceMethodPost(Class<T> responseType, Object request)
            throws UniformInterfaceException, IOException, ApplicationException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json;

        try {
            validateRequestAndResponseType(request, responseType);
            json = JsonRestUtil.convertirObjetoToJson(request);
        } catch (ApplicationException ae) {
            String msgError = "Error en ProxyRR:RestClientGeneric:callWebServiceMethodPOST. EX000 " + ae.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(msgError, ae);
        }

        String response = webResource.path("")
                .type(MediaType.APPLICATION_JSON)
                .post(String.class, json);

        T temporalResponse = objectMapper.readValue(response, responseType);
        close();
        return temporalResponse;
    }

    /**
     * Permite consumir servicio Rest con método HTTP PUT
     *
     * @param responseType Indica la estructura de respuesta del servicio
     * @param request      Petición del servicio con la data requerida
     * @param <T>          Clase que retornará en la estructura del response
     * @return temporalResponse
     * @throws ApplicationException Excepción de la aplicación
     * @author Gildardo Mora
     */
    public <T> T callWebServiceMethodPut(Class<T> responseType, Object request)
            throws ApplicationException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json;

        try {
            validateRequestAndResponseType(request, responseType);
            json = JsonRestUtil.convertirObjetoToJson(request);
        } catch (ApplicationException ae) {
            String msgError = "Error en ProxyRR:RestClientGeneric:callWebServiceMethodPut. EX000 " + ae.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(ae);
        }

        try {
            String response = webResource.path("")
                    .type(MediaType.APPLICATION_JSON)
                    .put(String.class, json);
            return objectMapper.readValue(response, responseType);
        } catch (UniformInterfaceException | ClientHandlerException | JsonProcessingException e) {
            String msgError = "Error en ProxyRR:RestClientGeneric:callWebServiceMethodPut. EX000 " + e.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(e);
        } finally {
            close();
        }
    }

    /**
     * Validación sobre request y el tipo de respuesta para el servicio
     *
     * @param request      {@link Object}
     * @param responseType {@link Class<T>}
     * @param <T>          {@link T}
     * @throws ApplicationException Excepción de la App
     */
    private <T> void validateRequestAndResponseType(Object request, Class<T> responseType) throws ApplicationException {
        if (Objects.isNull(request)) {
            throw new ApplicationException("El Request no puede ser nulo para hacer la petición al servicio");
        }

        if (Objects.isNull(responseType)) {
            throw new ApplicationException("El tipo de Response no puede ser nulo para hacer la petición al servicio.");
        }
    }

    /**
     * Destruye el cliente rest. Todos los recursos asociados con el cliente
     * serán limpiados
     */
    private void close() {
        if (client != null) {
            client.destroy();
        }
    }

}
