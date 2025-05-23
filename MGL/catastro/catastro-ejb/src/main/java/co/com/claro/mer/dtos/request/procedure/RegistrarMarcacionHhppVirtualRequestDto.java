package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * DTO que representa el request del procedimiento que registra
 * las marcaciones para HHPP virtual.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/06/02
 * @see co.com.claro.mgl.dao.impl.cm.CmtMarcacionesHhppVtMglDaoImpl
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegistrarMarcacionHhppVirtualRequestDto {

    /**
     * Código de Razón (R1) para marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_CODIGO_R1", parameterType = String.class)
    private String codigoR1;

    /**
     * Descripción de razón (R1) para marcación de HHPP.
     */
    @StoredProcedureData(parameterName = "V_DESCRIPCION_R1", parameterType = String.class)
    private String descripcionR1;

    /**
     * Código de sub razón (R2) para marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_CODIGO_R2", parameterType = String.class)
    private String codigoR2;

    /**
     * Descripción de sub razón (R2) para marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_DESCRIPCION_R2", parameterType = String.class)
    private String descripcionR2;

    /**
     * Indica si aplica (1) o no aplica (0) la marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_APLICA_HHPP_VT", parameterType = String.class)
    private String aplicaHhppVirtual;

    /**
     * Estado de la marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_ESTADO_MARCACION", parameterType = String.class)
    private String estadoMarcacion;

    /**
     * Fecha de sincronización con RR de la marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_FECHA_SINCRONIZACION", parameterType = LocalDateTime.class)
    private LocalDateTime fechaSincronizacion;

    /**
     * Usuario que crea la marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_USUARIO_CREACION", parameterType = String.class)
    private String usuarioCreacion;

    /**
     * Perfil de usuario que crea la marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_PERFIL_CREACION", parameterType = Integer.class)
    private Integer perfilCreacion;

}
