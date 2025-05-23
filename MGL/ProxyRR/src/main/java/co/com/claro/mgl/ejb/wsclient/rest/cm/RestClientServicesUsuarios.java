/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.ejb.wsclient.rest.cm;

/**
 *
 * @author bocanegravm
 */
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.ws.utils.JSONUtils;
import co.com.claro.visitastecnicas.ws.utils.Constants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.io.IOException;
import java.net.ConnectException;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

/**
 * Clase para construir las peticiones a los servicios de usuario
 *
 * @author Victor Bocanegra
 * @version 17/01/2019
 */
public class RestClientServicesUsuarios {

    private static final Logger LOGGER = LogManager.getLogger(RestClientServicesUsuarios.class);

    private Client client;
    private WebResource webResource;

    /**
     * Constructor de la calse.
     *
     * @param baseUri {@link String} URL donde esta expuesto el servicio
     */
    public RestClientServicesUsuarios(String baseUri) {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(baseUri).path(Constants.REST_USUARIOS_SERVICE);
    }

    /**
     * M&eacute;todo para realizar la consulta a servicios put
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
     * @throws java.net.ConnectException
     */
    public <T> T callWebService(EnumeratorServiceName serviceName,
            Class<T> response, Object request) throws UniformInterfaceException, IOException,
            ConnectException, ApplicationException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
        String json = "";
        try {
            json = JSONUtils.objetoToJson(request);
        } catch (ApplicationException ae) {
            LOGGER.error("Error en callWebService. EX000 " + ae.getMessage(), ae);
            throw new ApplicationException("Error en ProxyRR:RestClientServicesUsuarios: " + ae.getMessage(), ae);
        }
        String webResponse = webResource
                .path(serviceName.getServiceName())
                .type(MediaType.APPLICATION_JSON)
                .put(String.class, json);
        T responseObject = mapper.readValue(webResponse, response);
        client.destroy();
        return responseObject;
    }
}
