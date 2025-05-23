package co.com.claro.cmas400.ejb.respons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la estructura de salida del servicio Rest GetMoveReasonsResource
 * en cuanto a los datos de razones y sub razones provenientes desde RR
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/01/11
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseDataMarcacionesHhppVt {

    @JsonProperty("codigoRazonR1")
    private String codigoRazonR1;
    @JsonProperty("DescripcionR1")
    private String descripcionR1;
    @JsonProperty("codigoRazonR2")
    private String codigoRazonR2;
    @JsonProperty("DescripcionR2")
    private String descripcionR2;
    @JsonProperty("Fecha")
    private String fecha;
    @JsonProperty("Estado")
    private String estado;

}
