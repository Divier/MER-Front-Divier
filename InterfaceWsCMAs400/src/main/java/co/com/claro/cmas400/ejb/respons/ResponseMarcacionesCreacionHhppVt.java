package co.com.claro.cmas400.ejb.respons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la estructura principal de respuesta del servicio Rest GetMoveReasonsRsHeaderResponse
 * Usado para la sincronización de razones y sub razones desde RR hacia la tabla de
 * marcaciones para creación de HHPP virtuales en MER.
 *
 * @author Gildardo Mora
 * @version 1.1, 2022/03/18 Rev Gildardo Mora
 * @since 1.0, 2022/01/11
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseMarcacionesCreacionHhppVt {

    @JsonProperty("getMoveReasonsResourceResponse")
    private GetMoveReasonsResourceResponse getMoveReasonsResourceResponse;
    @JsonProperty("headerResponse")
    private HeaderResponseBase headerResponse;

}
