package co.com.claro.cmas400.ejb.respons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Response del servicio de envío de correos (Torre de control)
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/08/15
 */
@Getter
@Setter
@ToString
public class ResponseEmail {

    @JsonProperty("headerResponse")
    private HeaderEmailResponse headerResponse;

    @JsonProperty("isValid")
    private String isValid;

    @JsonProperty("message")
    private String message;

}
