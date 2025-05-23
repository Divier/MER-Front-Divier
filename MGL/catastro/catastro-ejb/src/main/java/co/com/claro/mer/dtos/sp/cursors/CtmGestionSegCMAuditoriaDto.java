/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.claro.mer.dtos.sp.cursors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO que representa el cursor de salida de la tabla CMT_GESTION_SEG_CM_$AUD
 *
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CtmGestionSegCMAuditoriaDto {
    
    private BigDecimal idAuditoria;
    private BigDecimal cuentaMatrizId;
    private String nombreColumna;
    private String tipoOperacion;
    private Date fechaOperacion;
    private String usuario;
    private String valorAntiguo;
    private String valorNuevo;
    private BigDecimal idGestionSegCM;

}
