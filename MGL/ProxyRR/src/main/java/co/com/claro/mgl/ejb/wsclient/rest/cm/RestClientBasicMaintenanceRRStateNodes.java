/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.ejb.wsclient.rest.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.ws.utils.JSONUtils;
import co.com.claro.visitastecnicas.ws.utils.Constants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.io.IOException;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Felipe Garcia
 */
public class RestClientBasicMaintenanceRRStateNodes {

    private static final Logger LOGGER = LogManager.getLogger(RestClientBasicMaintenanceRRStateNodes.class);

    private Client client;
    private WebResource webResource;

    /**
     * Constructor de la calse.
     *
     * @param baseUri {@link String} URL donde esta expuesto el servicio
     */
    public RestClientBasicMaintenanceRRStateNodes(String baseUri) {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(baseUri).path(Constants.REST_BASICARR_ESTADO_NODOS_SERVICE);
    }

    /**
     * Metodo para realizar la consulta a servicios PUT
     *
     * @param <T> Clase que retorna la peticion
     * @param enumeratorServiceName Servicio que se esta consumiendo
     * @param responseType Clase que define el tipo de respuesta
     * @param requestEntity Objeto con los parametros de la petici&oacute;n
     * @return {@link T} Clase de retorno
     * @throws UniformInterfaceException Excepci&oaucte;n lanzada por error en
     * la url de peticiones
     * @throws IOException Excepcion lanzada por datos en la peticion
     */
    public <T> T callWebServiceMethodPUT(
            EnumeratorServiceName enumeratorServiceName, Class<T> responseType, Object request)
            throws UniformInterfaceException, IOException, ApplicationException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = JSONUtils.objetoToJson(request);
        } catch (ApplicationException ae) {
            LOGGER.error("Error en callWebServiceMethodPUT. EX000 " + ae.getMessage(), ae);
            throw new ApplicationException("Error en ProxyRR:RestClientBasicMaintenanceRRStateNodes: " + ae.getMessage(), ae);
        }
        String response = webResource.path(enumeratorServiceName.getServiceName())
                .type(MediaType.APPLICATION_JSON)
                .put(String.class, json);
        T temporalResponse = objectMapper.readValue(response, responseType);
        close();
        return temporalResponse;
    }

    /**
     * Metodo para realizar la consulta a servicios POST
     *
     * @param <T> Clase que retorna la peticion
     * @param enumeratorServiceName Servicio que se esta consumiendo
     * @param responseType Clase que define el tipo de respuesta
     * @param requestEntity Objeto con los parametros de la petici&oacute;n
     * @return {@link T} Clase de retorno
     * @throws UniformInterfaceException Excepci&oaucte;n lanzada por error en
     * la url de peticiones
     * @throws IOException Excepcion lanzada por datos en la peticion
     */
    public <T> T callWebServiceMethodPOST(
            EnumeratorServiceName enumeratorServiceName, Class<T> responseType, Object request)
            throws UniformInterfaceException, IOException, ApplicationException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = JSONUtils.objetoToJson(request);
        } catch (ApplicationException ae) {
            LOGGER.error("Error en callWebServiceMethodPOST. EX000 " + ae.getMessage(), ae);
            throw new ApplicationException("Error en ProxyRR:RestClientBasicMaintenanceRRStateNodes: " + ae.getMessage(), ae);
        }
        String response = webResource.path(enumeratorServiceName.getServiceName())
                .type(MediaType.APPLICATION_JSON)
                .post(String.class, json);
        T temporalResponse = objectMapper.readValue(response, responseType);
        close();
        return temporalResponse;
    }

    /**
     * Metodo para realizar la consulta a servicios DELETE
     *
     * @param <T> Clase que retorna la peticion
     * @param enumeratorServiceName Servicio que se esta consumiendo
     * @param responseType Clase que define el tipo de respuesta
     * @param requestEntity Objeto con los parametros de la petici&oacute;n
     * @return {@link T} Clase de retorno
     * @throws UniformInterfaceException Excepci&oaucte;n lanzada por error en
     * la url de peticiones
     * @throws IOException Excepcion lanzada por datos en la peticion
     */
    public <T> T callWebServiceMethodDELETE(
            EnumeratorServiceName enumeratorServiceName, Class<T> responseType, Object request)
            throws UniformInterfaceException, IOException, ApplicationException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = JSONUtils.objetoToJson(request);
        } catch (ApplicationException ae) {
            LOGGER.error("Error en __. EX000 " + ae.getMessage(), ae);
            throw new ApplicationException("Error en ProxyRR:RestClientBasicMaintenanceRRStateNodes.java	: " + ae.getMessage(), ae);
        }
        String response = webResource.path(enumeratorServiceName.getServiceName())
                .type(MediaType.APPLICATION_JSON)
                .put(String.class, json);
        T temporalResponse = objectMapper.readValue(response, responseType);
        close();
        return temporalResponse;
    }

    public void close() {
        client.destroy();
    }
}
