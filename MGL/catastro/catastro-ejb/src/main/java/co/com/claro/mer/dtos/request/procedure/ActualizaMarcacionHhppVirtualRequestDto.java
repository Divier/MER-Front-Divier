package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * DTO encargado de mapear la estructura del request para la ejecución del procedimiento almacenado,
 * encargado de actualizar la información de la marcación de HHPP virtual.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/06/02
 * @see co.com.claro.mgl.dao.impl.cm.CmtMarcacionesHhppVtMglDaoImpl
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ActualizaMarcacionHhppVirtualRequestDto {

    /**
     * Mapea el ID de la marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_MARCACIONES_HHPP_VT_ID", parameterType = String.class)
    private String marcacionHhppVtId;

    /**
     * Mapea el usuario de edición de la marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_USUARIO_EDICION", parameterType = String.class)
    private String usuarioEdicion;

    /**
     * Mapea el perfil de edición de la marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_PERFIL_EDICION", parameterType = Integer.class)
    private Integer perfilEdicion;

    /**
     * Mapea el estado de registro (activo = 1, inactivo = 0)
     * de la marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_ESTADO_REGISTRO", parameterType = String.class)
    private String estadoRegistro;

    /**
     * Mapea si la marcación aplica (1) o no (0) para proceso de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_APLICA_HHPP_VT", parameterType = String.class)
    private String aplicaHhppVirtual;

    /**
     * Mapea la descripción de la razón (R1) para marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_DESCRIPCION_R1", parameterType = String.class)
    private String descripcionR1;

    /**
     * Mapea la descripción de la sub razón (R2) para marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_DESCRIPCION_R2", parameterType = String.class)
    private String descripcionR2;

    /**
     * Mapea la fecha de sincronización con RR de la marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_FECHA_SINCRONIZACION", parameterType = LocalDateTime.class)
    private LocalDateTime fechaSincronizacion;

    /**
     * Mapea el estado de la marcación de HHPP virtual.
     * <p>
     * Los posibles valores son:
     * NR = Nueva Razón. , NS = Nueva Sub Razón.
     * MR = Modificación de Razón. , MS = Modificación de Sub Razón.
     * BR = Borrado de Razón. , BS = Borrado de Sub Razón
     */
    @StoredProcedureData(parameterName = "V_ESTADO_MARCACION", parameterType = String.class)
    private String estadoMarcacion;

}
