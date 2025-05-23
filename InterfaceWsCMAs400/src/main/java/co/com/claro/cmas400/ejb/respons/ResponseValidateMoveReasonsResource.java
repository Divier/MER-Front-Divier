package co.com.claro.cmas400.ejb.respons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la estructura principal de respuesta del servicio Rest validateMoveReasonsResource
 * Usado para validar el estado de una cuenta en RR, así como también validar HHPP
 * y entrega su estado para el proceso de creación de HHPP virtual en MER.
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/03/15
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseValidateMoveReasonsResource {

    @JsonProperty("headerResponse")
    private HeaderResponseBase headerResponse;
    @JsonProperty("validateMoveReasonsResourceResponse")
    private ResponseDataValidaRazonesCreaHhppVt validateMoveReasonsResourceResponse;

}
