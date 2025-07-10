/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.integration.ws.usaggeocoverage;

import co.com.claro.mer.integration.ws.usaggecoverage.dto.UsagGeoCoverageRestRequest;
import co.com.claro.mer.integration.ws.usaggecoverage.dto.UsagGeoCoverageRestResponse;
import co.com.claro.mer.integration.ws.usaggeocoverage.exception.UsagGeoCoverageRestClientException;
import co.com.claro.mer.integration.nodedistance.dto.AbstractWSRestClient;
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
public class UsagGeoCoverageRestClient extends AbstractWSRestClient {

    private static final Logger LOGGER = LogManager.getLogger(UsagGeoCoverageRestClient.class);
    private String endPoint;

    public UsagGeoCoverageRestClient(String endPoint) {
        this.endPoint = endPoint;
    }

    public UsagGeoCoverageRestResponse coordsByNode(
            UsagGeoCoverageRestRequest usagGeoCoverageRestRequest,
            Map<String, Object> headers) throws UsagGeoCoverageRestClientException {

        UsagGeoCoverageRestResponse response = null;
        Response responseWS = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            responseWS
                    = super.postJSON(
                            endPoint,
                            Entity.entity(objectMapper.writeValueAsString(usagGeoCoverageRestRequest),
                            MediaType.APPLICATION_JSON),
                            headers);
            LOGGER.info("Response status [", String.valueOf(responseWS.getStatus()), "]");
            if (responseWS.getStatus() == org.apache.http.HttpStatus.SC_OK) {
                response = responseWS.readEntity(UsagGeoCoverageRestResponse.class);
            } else {
                throw new UsagGeoCoverageRestClientException("Status - " + responseWS.getStatus() + " / " + responseWS.getStatusInfo().getReasonPhrase());
            }
            return response;
        } catch (Exception ex) {
            throw new UsagGeoCoverageRestClientException(ex.getMessage());
        } finally {
            if (responseWS != null) {
                responseWS.close();
            }
        }
    }
}
