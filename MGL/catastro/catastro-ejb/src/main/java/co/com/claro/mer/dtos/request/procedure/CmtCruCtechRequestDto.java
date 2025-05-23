/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.dtos.request.procedure;
import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa el request al procedimiento para realizar CRUD a tabla CMT_COM_TECHNICALSITESAP.
 *
 * @author Johan Gomez
 * @version 1.0, 2024/12/09
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CmtCruCtechRequestDto {
    @StoredProcedureData(parameterName = "PI_ACTION", parameterType = String.class)
    private String opcion;

    @StoredProcedureData(parameterName = "PI_SITIO", parameterType = String.class)
    private String sitio;
    
    @StoredProcedureData(parameterName = "PI_DANE_MUNICIPIO", parameterType = String.class)
    private String daneMunicipio;
    
    @StoredProcedureData(parameterName = "PI_CENTRO_POBLADO", parameterType = String.class)
    private String centroPoblado;
    
    @StoredProcedureData(parameterName = "PI_UBICACIONTECNICA", parameterType = String.class)
    private String ubicacionTecnica;
    
    @StoredProcedureData(parameterName = "PI_DANE_CP", parameterType = String.class)
    private String daneCp;
    
    @StoredProcedureData(parameterName = "PI_ID_SITIO", parameterType = String.class)
    private String idSitio;
    
    @StoredProcedureData(parameterName = "PI_CCMM", parameterType = Integer.class)
    private Integer ccmm;
    
    @StoredProcedureData(parameterName = "PI_TIPO_DE_SITIO", parameterType = String.class)
    private String tipoSitio;
    
    @StoredProcedureData(parameterName = "PI_DISPONIBILIDAD", parameterType = String.class)
    private String disponibilidad;
    
    @StoredProcedureData(parameterName = "PI_USUARIO_CREACION", parameterType = String.class)
    private String usuarioCreacion;
    
    @StoredProcedureData(parameterName = "PI_USUARIO_EDICION", parameterType = String.class)
    private String usuarioEdicion;
    
    @StoredProcedureData(parameterName = "PI_ESTADO_REGISTRO", parameterType = Integer.class)
    private Integer estadoRegistro;
    
    
}
