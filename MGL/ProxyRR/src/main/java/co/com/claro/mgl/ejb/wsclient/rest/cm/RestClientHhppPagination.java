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
 * @author Orlando Velasquez
 */
public class RestClientHhppPagination {

    private static final Logger LOGGER = LogManager.getLogger(RestClientHhppPagination.class);

    private Client client;
    private WebResource webResource;

    /**
     * Constructor.
     *
     * @param baseUri
     */
    public RestClientHhppPagination(String baseUri) {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(baseUri).path(Constants.REST_PAGINATION_HHPP);
    }

    /**
     *
     * @param <T>
     * @param enumeratorServiceName
     * @param responseType
     * @param requestEntity
     * @return
     * @throws UniformInterfaceException
     * @throws IOException
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
            throw new ApplicationException("Error en ProxyRR:RestClientHhppPagination: " + ae.getMessage(), ae);
        }
        String response = webResource.path(enumeratorServiceName.getServiceName())
                .type(MediaType.APPLICATION_JSON)
                .put(String.class, json);
        T temporalResponse = objectMapper.readValue(response, responseType);
        close();
        return temporalResponse;
    }

    /**
     * Destruye el cliente. Todos los recursos del sistema asociados con el
     * cliente se limpiarán.
     */
    public void close() {
        client.destroy();
    }
}
