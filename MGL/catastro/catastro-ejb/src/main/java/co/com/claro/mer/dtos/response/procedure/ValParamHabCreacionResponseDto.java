package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa el response al procedimiento para realizar consulta a tabla TBL_PARAM_PERFIL_VISOR.
 *
 * @author Miguel Barrios
 * @version 1.0, 2023/10/20
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ValParamHabCreacionResponseDto {
    /**
     * Mapea la lista de resultados de la consulta.
     */
    @StoredProcedureData(parameterName = "PO_HAB_CREACION", parameterType = Integer.class)
    private Integer habilitaCreacion;
    
    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = Integer.class)
    private Integer codigo;

    @StoredProcedureData(parameterName = "PO_DESCRIPCION", parameterType = String.class)
    private String descripcion;
    

}