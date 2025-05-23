package co.com.claro.cmas400.ejb.respons;

import co.com.claro.cmas400.ejb.respons.dto.CodigoRazonSubrazon;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Representa la estructura de salida de la data del servicio rest validateMoveReasonsResource
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/03/15
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseDataValidaRazonesCreaHhppVt {
    @JsonProperty("estadoHHPP")
    private String estadoHhpp;
    @JsonProperty("descripcionEstado")
    private String descripcionEstado;
    @JsonProperty("codigoBloqueo1")
    private String codigoBloqueo1;
    @JsonProperty("descripcionBloqueo1")
    private String descripcionBloqueo1;
    @JsonProperty("codigoBloqueo2")
    private String codigoBloqueo2;
    @JsonProperty("descripcionBloqueo2")
    private String descripcionBloqueo2;
    @JsonProperty("codigoBloqueo3")
    private String codigoBloqueo3;
    @JsonProperty("descripcionBloqueo3")
    private String descripcionBloqueo3;
    @JsonProperty("codigoBloqueo4")
    private String codigoBloqueo4;
    @JsonProperty("descripcionBloqueo4")
    private String descripcionBloqueo4;
    @JsonProperty("ultimaOT")
    private String ultimaOt;
    @JsonProperty("direccion")
    private String direccion;
    @JsonProperty("codigoRespuestaHHPP")
    private String codigoRespuestaHhpp;
    @JsonProperty("mensajeRespuestaHHPP")
    private String mensajeRespuestaHhpp;
    @JsonProperty("codigoRazonSubrazon")
    private List<CodigoRazonSubrazon> codigoRazonSubrazon;
    @JsonProperty("estadoCuenta")
    private String estadoCuenta;
    @JsonProperty("fechaActivacionCuenta")
    private String fechaActivacionCuenta;
    @JsonProperty("codigoRespuesta")
    private String codigoRespuesta;
    @JsonProperty("mensajeRespuesta")
    private String mensajeRespuesta;

}