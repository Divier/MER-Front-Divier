/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.ejb.wsclient.rest.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.visitastecnicas.ws.utils.Constants;
import co.com.claro.mgl.ws.utils.JSONUtils;
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
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

/**
 *
 * @author JPeña
 */
public class RestClientBasicMaintenanceRRPlants {

    private static final Logger LOGGER = LogManager.getLogger(RestClientBasicMaintenanceRRPlants.class);

    private Client client;
    private WebResource webResource;

    /**
     * Constructor de la calse.
     *
     * @param baseUri {@link String} URL donde esta expuesto el servicio
     */
    public RestClientBasicMaintenanceRRPlants(String baseUri) {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(baseUri).path(Constants.REST_BASICORR_PLANTA);
    }

    /**
     * Metodo para realizar la consulta a servicios post
     *
     * @param <T> Clase que retorna la petici&oacute;n
     * @param serviceName Servicio que se esta consumiendo
     * @param response Clase que define el tipo de respuesta
     * @param request Objeto con los par&aacute;metros de la petici&oacute;n
     * @return {@link T} Clase de retorno
     * @throws UniformInterfaceException Excepci&oaucte;n lanzada por error en
     * la url de petici&oacute;n
     * @throws IOException Excepci&oacute;n lanzada por datos en la
     * petici&oacute;n
     */
    public <T> T callWebServicePost(EnumeratorServiceName serviceName,
            Class<T> response, Object request) throws UniformInterfaceException, IOException, ApplicationException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
        String json = "";
        try {
            json = JSONUtils.objetoToJson(request);
        } catch (ApplicationException ae) {
            LOGGER.error("Error en callWebServicePost. EX000 " + ae.getMessage(), ae);
            throw new ApplicationException("Error en ProxyRR:RestClientBasicMaintenanceRRPlants: " + ae.getMessage(), ae);
        }
        LOGGER.info(json);
        String webResponse = webResource
                .path(serviceName.getServiceName())
                .type(MediaType.APPLICATION_JSON)
                .post(String.class, json);
        T responseObject = mapper.readValue(webResponse, response);
        client.destroy();
        return responseObject;
    }

    /**
     * Metodo para realizar la consulta a servicios delete
     *
     * @param <T> Clase que retorna la petici&oacute;n
     * @param serviceName Servicio que se esta consumiendo
     * @param response Clase que define el tipo de respuesta
     * @param request Objeto con los par&aacute;metros de la petici&oacute;n
     * @return {@link T} Clase de retorno
     * @throws UniformInterfaceException Excepci&oaucte;n lanzada por error en
     * la url de petici&oacute;n
     * @throws IOException Excepci&oacute;n lanzada por datos en la
     * petici&oacute;n
     */
    public <T> T callWebServiceDelete(EnumeratorServiceName serviceName,
            Class<T> response, Object request) throws UniformInterfaceException, IOException, ApplicationException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
        String json = "";
        try {
            json = JSONUtils.objetoToJson(request);
        } catch (ApplicationException ae) {
            LOGGER.error("Error en callWebServiceDelete. EX000 " + ae.getMessage(), ae);
            throw new ApplicationException("Error en ProxyRR:RestClientBasicMaintenanceRRPlants: " + ae.getMessage(), ae);
        }
        LOGGER.info(json);
        String webResponse = webResource
                .path(serviceName.getServiceName())
                .type(MediaType.APPLICATION_JSON)
                .put(String.class, json);
        T responseObject = mapper.readValue(webResponse, response);
        client.destroy();
        return responseObject;
    }

    /**
     * Metodo para realizar la consulta a servicios put
     *
     * @param <T> Clase que retorna la petici&oacute;n
     * @param serviceName Servicio que se esta consumiendo
     * @param response Clase que define el tipo de respuesta
     * @param request Objeto con los par&aacute;metros de la petici&oacute;n
     * @return {@link T} Clase de retorno
     * @throws UniformInterfaceException Excepci&oaucte;n lanzada por error en
     * la url de petici&oacute;n
     * @throws IOException Excepci&oacute;n lanzada por datos en la
     * petici&oacute;n
     */
    public <T> T callWebServicePut(EnumeratorServiceName serviceName,
            Class<T> response, Object request) throws UniformInterfaceException, IOException, ApplicationException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
        String json = "";
        try {
            json = JSONUtils.objetoToJson(request);
        } catch (ApplicationException ae) {
            LOGGER.error("Error en callWebServicePut. EX000 " + ae.getMessage(), ae);
            throw new ApplicationException("Error en ProxyRR:RestClientBasicMaintenanceRRPlants: " + ae.getMessage(), ae);
        }
        LOGGER.info(json);
        String webResponse = webResource
                .path(serviceName.getServiceName())
                .type(MediaType.APPLICATION_JSON)
                .put(String.class, json);
        T responseObject = mapper.readValue(webResponse, response);
        client.destroy();
        return responseObject;
    }

}
