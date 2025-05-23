package co.com.claro.mgl.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * DTO usado para consultas por filtro. Se utiliza para apoyar los filtros
 * especificados en las columnas de la vista de marcaciones para creación HHPP
 * virtuales.
 *
 * @author Gildardo Mora
 * @version 1.0, 29/09/29
 */

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FiltroMarcacionesHhppVtDto {

    private BigDecimal marcacionId;
    private String codigoR1;
    private String descripcionR1;
    private String codigoR2;
    private String descripcionR2;
    private Boolean aplicaHhppVt;
    private int estadoRegistro;

}