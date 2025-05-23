package co.com.claro.cmas400.ejb.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Header del request del servicio de envío de correos (Torre de control)
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/08/16
 */
@Getter
@Setter
@ToString
public class HeaderEmailRequest {
    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("system")
    private String system;

    @JsonProperty("user")
    private String user;

    @JsonProperty("password")
    private String password;

    @JsonProperty("requestDate")
    private String requestDate;

    @JsonProperty("ipApplication")
    private String ipApplication;

    @JsonProperty("traceabilityId")
    private String traceabilityId;

}
