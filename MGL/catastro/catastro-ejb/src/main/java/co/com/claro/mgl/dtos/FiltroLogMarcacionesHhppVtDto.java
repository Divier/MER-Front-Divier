package co.com.claro.mgl.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Dto usado para consultas por filtro. Se utiliza para apoyar los filtros
 * especificados en las columnas de la vista de marcaciones para creacion hhpp
 * virtuales.
 *
 * @author Gildardo Mora
 * @version 1.1, 2022/07/27
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FiltroLogMarcacionesHhppVtDto {

    private BigDecimal idLogMarcaHHPP;
    private BigDecimal idMarcacion;
    private String usuarioCreacionLogMarcaHHPP;
    private Date fechaCreacionLogMarcaHHPP;
    private String valorAntLogMarcaHHPP;
    private String valorNuevoLogMarcaHHPP;
}
