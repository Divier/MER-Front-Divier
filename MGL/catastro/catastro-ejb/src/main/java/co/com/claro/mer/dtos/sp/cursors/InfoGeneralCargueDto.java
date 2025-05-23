package co.com.claro.mer.dtos.sp.cursors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Clase tipo DAO que permite acceder a los datos de las tablas ejecutando
 * paquetes de base de datos.
 *
 * @author Henry Sanchez Arango
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfoGeneralCargueDto {
    
    private BigDecimal masivoId;
    private String tipo;
    private String rutaIn;
    private String rutaOut;
    private String estNomArchivo;
    private BigDecimal contReg;
    private String tabla;
    private String delim;
    private String rol;
    private String pBase;
    private BigDecimal Encabezado;
    private BigDecimal validacionEstNomArchivo;

}
