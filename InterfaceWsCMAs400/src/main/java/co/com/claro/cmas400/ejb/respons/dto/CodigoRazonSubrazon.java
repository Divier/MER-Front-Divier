package co.com.claro.cmas400.ejb.respons.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa la salida del conjunto de códigos de razones y sub razones
 * que forman parte de la respuesta del servicio
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/03/16
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CodigoRazonSubrazon {

    @JsonProperty("codigoRazon")
    private String codigoRazon;
    @JsonProperty("codigoSubRazon")
    private String codigoSubrazon;
}
