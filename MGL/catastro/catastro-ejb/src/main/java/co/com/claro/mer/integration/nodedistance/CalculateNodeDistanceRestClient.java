/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.integration.nodedistance;

import co.com.claro.mer.integration.nodedistance.dto.AbstractWSRestClient;
import co.com.claro.mer.integration.nodedistance.dto.CalculateNodeDistanceRestRequest;
import co.com.claro.mer.integration.nodedistance.dto.CalculateNodeDistanceRestResponse;
import co.com.claro.mer.integration.nodedistance.exception.CalculateNodeDistanceRestClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Camilo Andres Ramirez
 * @version 1.0, 2024/09/30
 */
public class CalculateNodeDistanceRestClient extends AbstractWSRestClient {

    private static final Logger LOGGER = LogManager.getLogger(CalculateNodeDistanceRestClient.class);
    private String endPoint;

    public CalculateNodeDistanceRestClient(String endPoint) {
        this.endPoint = endPoint;
    }

    public CalculateNodeDistanceRestResponse calculateNodeDistance(
            CalculateNodeDistanceRestRequest calculateNodeDistanceRestRequest,
            Map<String, Object> headers) throws CalculateNodeDistanceRestClientException {

        CalculateNodeDistanceRestResponse response = null;
        Response responseWS = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            responseWS
                    = super.postJSON(
                            endPoint,
                            Entity.entity(objectMapper.writeValueAsString(calculateNodeDistanceRestRequest),
                            MediaType.APPLICATION_JSON),
                            headers);
            LOGGER.info("Response status [", String.valueOf(responseWS.getStatus()), "]");
            if (responseWS.getStatus() == org.apache.http.HttpStatus.SC_OK) {
                response = responseWS.readEntity(CalculateNodeDistanceRestResponse.class);
            } else {
                throw new CalculateNodeDistanceRestClientException("Status - " + responseWS.getStatus() + " / " + responseWS.getStatusInfo().getReasonPhrase());
            }
            return response;
        } catch (Exception ex) {
            throw new CalculateNodeDistanceRestClientException(ex.getMessage());
        } finally {
            if (responseWS != null) {
                responseWS.close();
            }
        }
    }
}
