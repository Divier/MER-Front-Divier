package co.com.claro.cmas400.ejb.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa el Request de datos requerido para consumo de
 * servicio Rest createVirtualHhppResource
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/03/17
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RequestDataCreaHhppVirtual {

    @JsonProperty("cuenta")
    private String cuenta;
    @JsonProperty("codigoDivision")
    private String codigoDivision;
    @JsonProperty("codigoComunidad")
    private String codigoComunidad;
    @JsonProperty("calle")
    private String calle;
    @JsonProperty("numeroCasa")
    private String numeroCasa;
    @JsonProperty("apartamentoHHPPPrimario")
    private String apartamentoHhppPrimario;
    @JsonProperty("apartamentoHHPPVirtual")
    private String apartamentoHhppVirtual;
    @JsonProperty("usuarioPeticion")
    private String usuarioPeticion;
}