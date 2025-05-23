package co.com.claro.mer.dtos.sp.cursors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO que mapea la información de respuesta de consulta de la consulta
 * de marcaciones de HHPP virtual.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/06/02
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MarcacionesHhppVirtualDto {

    /**
     * Identificador de la marcación.
     */
    private BigDecimal marcacionId;
    /**
     * Código de razón de la marcación.
     */
    private String codigoR1;
    /**
     * Descripción de la razón de la marcación.
     */
    private String descripcionR1;
    /**
     * Código de sub razón de la marcación.
     */
    private String codigoR2;
    /**
     * Descripción de la sub razón de la marcación.
     */
    private String descripcionR2;
    /**
     * Indica si la marcación aplica para HHPP virtual.
     */
    private int aplicaHhppVt;
    /**
     * Fecha de creación de la marcación.
     */
    private LocalDateTime fechaCreacion;
    /**
     * Usuario de creación de la marcación.
     */
    private String usuarioCreacion;
    /**
     * Perfil del usuario que creación de la marcación.
     */
    private int perfilCreacion;
    /**
     * Fecha de edición de la marcación.
     */
    private LocalDateTime fechaEdicion;
    /**
     * Usuario de edición de la marcación.
     */
    private String usuarioEdicion;
    /**
     * Perfil del usuario que edita la marcación.
     */
    private int perfilEdicion;
    /**
     * Estado (activo = 1, inactivo = 0) del registro de la marcación.
     */
    private int estadoRegistro;
    /**
     * Estado de la marcación.
     */
    private String estadoMarcacion;
    /**
     * Fecha de sincronización de la marcación.
     */
    private LocalDateTime fechaSincronizacion;
}
