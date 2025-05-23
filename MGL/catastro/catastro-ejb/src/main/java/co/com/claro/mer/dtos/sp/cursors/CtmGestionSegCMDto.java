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

/**
 * DTO que representa el cursor de salida de la tabla CMT_GESTION_SEG_CM
 *
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CtmGestionSegCMDto {
    
    private BigDecimal id;
    private BigDecimal cmtCuentaMatrizMgl;
    private String cerraduraElect;
    private BigDecimal tipoCerradElect;
    private String serial;
    private String fabricante;
    private String propSitioNombre;
    private String propSitioCelular;
    private String usuarioActualiza;
    private String nombreJefeZona;
    private String celularJefeZona;
}