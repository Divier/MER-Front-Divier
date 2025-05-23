package co.com.claro.mer.dtos.sp.cursors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Clase DTO para el listado de Cargues de la vista Cargue Masivo de Archivos
 * Permite almacenar la informacion que se lista en la dataTable y ver los
 * archivos procesados, en proceso y procesados por odi
 *
 * @author Henry Sanchez Arango
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CargueInformacionDto {

    private BigDecimal linMasivoId;
    private String nombre;
    private String arc;
    private String arcOut;
    private String tipo;
    private Object fechaReg;
    private String filtro;
    private String estado;
    private BigDecimal totReg;

}
