/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO que representa el request de entrada del procedimiento CMT_GESTION_SEG_CM_PKG.CMT_INS_GESTI_SEG_CM_PRC
 *
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CtmGestionSegCMIntRequestDto {
    @StoredProcedureData(parameterName = "PI_ID", parameterType = BigDecimal.class)
    private BigDecimal id;
    @StoredProcedureData(parameterName = "PI_CUENTAMATRIZ_ID", parameterType = BigDecimal.class)
    private BigDecimal cuentaMatrizId;
    @StoredProcedureData(parameterName = "PI_CERRADURA_ELECT", parameterType = String.class)
    private String cerraduraElect;
    @StoredProcedureData(parameterName = "PI_TIPO_CERRAD_ELECT", parameterType = BigDecimal.class)
    private BigDecimal tipoCerradElect;
    @StoredProcedureData(parameterName = "PI_SERIAL", parameterType = String.class)
    private String serial;
    @StoredProcedureData(parameterName = "PI_FABRICANTE", parameterType = String.class)
    private String fabricante;
    @StoredProcedureData(parameterName = "PI_PROP_SITIO_NOMBRE", parameterType = String.class)
    private String propSitioNombre;
    @StoredProcedureData(parameterName = "PI_PROP_SITIO_CELULAR", parameterType = String.class)
    private String propSitioCelular;
    @StoredProcedureData(parameterName = "PI_USUARIO_ACTUALIZA", parameterType = String.class)
    private String usuarioActualiza;
    @StoredProcedureData(parameterName = "PI_NOMBRE_JEFE_ZONA", parameterType = String.class)
    private String nombreJefeZona;
    @StoredProcedureData(parameterName = "PI_CELULAR_JEFE_ZONA", parameterType = String.class)
    private String celularJefeZona;

    @Override
    public String toString() {
        return "CtmGestionSegCMIntRequestDto{" + "id=" + id + ", cuentaMatrizId=" + cuentaMatrizId + ", cerraduraElect=" + cerraduraElect + ", tipoCerradElect=" + tipoCerradElect + ", serial=" + serial + ", fabricante=" + fabricante + ", propSitioNombre=" + propSitioNombre + ", propSitioCelular=" + propSitioCelular + ", nombreJefeZona=" + nombreJefeZona + ", celularJefeZona=" + celularJefeZona + ", usuarioActualiza=" + usuarioActualiza + '}';
    }
    
    

}