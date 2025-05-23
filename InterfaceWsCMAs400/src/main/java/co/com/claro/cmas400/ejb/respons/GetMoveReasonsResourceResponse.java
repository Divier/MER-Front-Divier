package co.com.claro.cmas400.ejb.respons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Clase que representa el esquema de salida del servicio GetMoveReasonsResource
 * que devuelve la lista de razones y sub razones provenientes de RR
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/01/11
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class GetMoveReasonsResourceResponse {

    @JsonProperty("OUT_LISR1R2")
    private List<ResponseDataMarcacionesHhppVt> marcacionesHhppVtList;
    @JsonProperty("codigo")
    private String codigo;
    @JsonProperty("descripcion")
    private String descripcion;

}
