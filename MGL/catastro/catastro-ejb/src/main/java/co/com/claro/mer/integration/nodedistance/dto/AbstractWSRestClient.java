/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.integration.nodedistance.dto;

import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

/**
 * @author Camilo Andres Ramirez
 * @version 1.0, 2024/09/30
 */
public class AbstractWSRestClient {

    public static Client client = ClientBuilder.newClient();

    public AbstractWSRestClient() {

        super();
    }

    public Response postJSON(String url, Entity<?> entity) {

        WebTarget target = client.target(url);
        return target.request(MediaType.APPLICATION_JSON).post(entity);
    }

    public Response postJSON(String url, Entity<?> entity, Map<String, Object> headers) {

        WebTarget target = client.target(url);
        return target.request(MediaType.APPLICATION_JSON).headers(new MultivaluedHashMap<>(headers)).post(entity);
    }
}
