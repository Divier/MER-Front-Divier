package co.com.claro.cmas400.ejb.respons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Encabezado del request del servicio de envío de correos (Torre de Control)
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/08/15
 */
@Getter
@Setter
@ToString
public class HeaderEmailResponse {

    @JsonProperty("responseDate")
    private String responseDate;

    @JsonProperty("traceabilityId")
    private String traceabilityId;
}
