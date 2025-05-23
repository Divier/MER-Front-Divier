package co.com.claro.mgl.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Dto para apoyo en ejecuciones de procedimientos almacenados referentes a
 * marcaciones para creacion de hhpp virtuales.
 *
 * @author Gildardo Mora
 * @version 1.0, 2021/10/22
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CmtMarcacionesHhppVtDto {

    private BigDecimal marcacionId;
    private String codigoR1;
    private String descripcionR1;
    private String codigoR2;
    private String descripcionR2;
    private boolean aplicaHhppVt;
    private LocalDateTime fechaCreacion;
    private String usuarioCreacion;
    private int perfilCreacion;
    private LocalDateTime fechaEdicion;
    private String usuarioEdicion;
    private int perfilEdicion;
    private int estadoRegistro;
    private String estadoMarcacion;
    private LocalDateTime fechaSincronizacion;

}
