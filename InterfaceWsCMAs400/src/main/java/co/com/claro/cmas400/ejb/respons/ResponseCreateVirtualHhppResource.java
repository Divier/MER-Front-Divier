package co.com.claro.cmas400.ejb.respons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la estructura principal de respuesta del servicio Rest createVirtualHHPPResource
 * Usado para la creación de los HHPP virtuales para traslados.
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/03/17
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseCreateVirtualHhppResource {

    @JsonProperty("headerResponse")
    private HeaderResponseBase headerResponse;
    @JsonProperty("createVirtualHHPPResourceResponse")
    private ResponseDataCreaHhppVirtual createVirtualHhppResourceResponse;
}
