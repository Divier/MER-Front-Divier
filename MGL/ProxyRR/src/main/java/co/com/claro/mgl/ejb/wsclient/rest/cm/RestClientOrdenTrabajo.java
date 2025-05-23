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
 * RestClientOrdenTrabajo es una clase que define el cliente RESTful Web
 * services de TablasBasicas en Cuentas Matrices (CMatricesAs400Services).
 *
 * @author camargomf
 */
public class RestClientOrdenTrabajo {

    private static final Logger LOGGER = LogManager.getLogger(RestClientOrdenTrabajo.class);

    private Client client;
    private WebResource webResource;

    /**
     * Constructor.
     *
     * @param baseUri
     */
    public RestClientOrdenTrabajo(String baseUri) {
        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(baseUri).path(Constants.REST_ORDEN_TRABAJO_SERVICE);
    }

    /**
     *
     * @param <T>
     * @param enumeratorServiceName
     * @param responseType
     * @param request
     * @param requestEntity
     * @return
     * @throws UniformInterfaceException
     * @throws IOException
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public <T> T callWebService(
            EnumeratorServiceName enumeratorServiceName, Class<T> responseType, Object request)
            throws UniformInterfaceException, IOException, ApplicationException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = JSONUtils.objetoToJson(request);
        } catch (ApplicationException ae) {
            LOGGER.error("Error en callWebService. EX000 " + ae.getMessage(), ae);
            throw new ApplicationException("Error en ProxyRR:RestClientOrdenTrabajo: " + ae.getMessage(), ae);
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
