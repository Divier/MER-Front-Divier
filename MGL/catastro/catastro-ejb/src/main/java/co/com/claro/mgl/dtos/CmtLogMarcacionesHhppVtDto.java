package co.com.claro.mgl.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para apoyo en ejecuciones de procedimientos almacenados referentes a logs
 * de marcación para creación de HHPP virtuales.
 *
 * @author Gildardo Mora
 * @version 1.1, 2022/03/23 Rev Gildardo Mora
 * @since 1.0, 2021/10/25
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CmtLogMarcacionesHhppVtDto {

    /**
     * Identificador del log de marcación para HHPP virtual.
     */
    private BigDecimal idLogMarcaHHPP;
    /**
     * Identificador de la marcación para HHPP virtual.
     */
    private BigDecimal idMarcacion;
    /**
     * Usuario que creó el log de marcación para HHPP virtual.
     */
    private String usuarioCreacionLog;
    /**
     * Fecha de creación del log de marcación para HHPP virtual.
     */
    private LocalDateTime fechaCreacionLogMarcaHHPP;
    /**
     * Valor antiguo del log de marcación para HHPP virtual.
     */
    private String valorAntLogMarcaHHPP;
    /**
     * Valor nuevo del log de marcación para HHPP virtual.
     */
    private String valorNuevoLogMarcaHHPP;
    /**
     * Estado del registro (activo = 1, inactivo = 0)
     * del log de marcación para HHPP virtual.
     */
    private int estadoRegistro;
    /**
     * Perfil del usuario que creó el log de marcación para HHPP virtual.
     */
    private int perfilCreacion;
    /**
     * Fecha de edición del log de marcación para HHPP virtual.
     */
    private LocalDateTime fechaEdicionLogMarcaHHPP;
    /**
     * Usuario que editó el log de marcación para HHPP virtual.
     */
    private String usuarioEdicionLog;
    /**
     * Perfil del usuario que editó el log de marcación para HHPP virtual.
     */
    private int perfilEdicion;

}
