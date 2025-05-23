package co.com.claro.cmas400.ejb.respons;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la estructura de respuesta de error del servicio
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/07/29
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class HeaderErrorResponseBase {

    private String errorCode;
    private String errorDescription;
    private String errorType;
}
