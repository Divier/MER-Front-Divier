package co.com.claro.cmas400.ejb.respons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa el header de respuesta de los servicios Rest
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/03/17
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class HeaderResponseBase {

    private String statusCode;
    private String statusDesc;
    private String responseDate;
    private String traceabilityId;
    @JsonIgnoreProperties
    private HeaderErrorResponseBase headerError;

}
