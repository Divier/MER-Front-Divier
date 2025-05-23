package co.com.claro.mer.dtos.sp.cursors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Clase DTO para el listado de items de los filtros tipo select.
 * @author Manuel Hernández Rivas
 * @version 1.0, 2025/02/27
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListaItemsFiltrosDto {
    private BigDecimal idItem;
    private String nombreItem;
}
