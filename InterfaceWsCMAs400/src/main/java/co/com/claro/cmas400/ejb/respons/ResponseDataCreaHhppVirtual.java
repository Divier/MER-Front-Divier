package co.com.claro.cmas400.ejb.respons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la estructura de salida de la data del servicio rest createVirtualHHPPResource
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/03/17
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseDataCreaHhppVirtual {
    @JsonProperty("oTCreada")
    private String oTCreada;
    @JsonProperty("apartamentoHHPPVirtual")
    private String apartamentoHhppVirtual;
    @JsonProperty("estadoOT")
    private String estadoOt;
    @JsonProperty("idDireccion")
    private String idDireccion;
    @JsonProperty("codigoRespuesta")
    private String codigoRespuesta;
    @JsonProperty("mensajeRespuesta")
    private String mensajeRespuesta;

}