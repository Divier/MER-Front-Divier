package co.com.claro.mer.dtos.sp.cursors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Dto de respuesta para el cursor del procedimiento almacenado CON_FILTROS_SP
 *
 * @see co.com.claro.mer.dtos.response.procedure.ConFiltrosResponseDto para más información del dto de respuesta
 * @author  Manuel Hernández
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConFiltrosCursorDto {
    private BigDecimal orden;
    private String columna;
    private BigDecimal obligatoriedad;
    private String tipo;
    private BigDecimal longitud;
    private String html_e;
    private BigDecimal idColumna;
    private BigDecimal idColumnaPadre;
    private String nombreColumna;
    private String operador;
    private BigDecimal preloadLista;

}
