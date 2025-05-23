/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa el request al procedimiento para realizar CRUD a tabla TEC_PREFICHA_NEW.
 *
 * @author Johan Gomez
 * @version 1.0, 2023/09/05
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrudTecPrefichaNewRequestDto {
    
    @StoredProcedureData(parameterName = "PI_OPCION", parameterType = String.class)
    private String opcion;

    @StoredProcedureData(parameterName = "PI_PREFICHA_ID", parameterType = BigDecimal.class)
    private BigDecimal prefichaId;

    @StoredProcedureData(parameterName = "PI_USUARIO_GENERA", parameterType = String.class)
    private String usuarioGenera;

    @StoredProcedureData(parameterName = "PI_FECHA_GENERA", parameterType = Date.class)
    private Date fechaGenera;

    @StoredProcedureData(parameterName = "PI_USUARIO_VALIDACION", parameterType = String.class)
    private String usuarioValidacion;
    
    @StoredProcedureData(parameterName = "PI_FECHA_VALIDACION", parameterType = Date.class)
    private Date fechaValidacion;
    
    @StoredProcedureData(parameterName = "PI_USUARIO_MODIFICA", parameterType = String.class)
    private String usuarioModifica;
    
    @StoredProcedureData(parameterName = "PI_FECHA_MODIFICA", parameterType = Date.class)
    private Date fechaModifica;
    
    @StoredProcedureData(parameterName = "PI_FASE", parameterType = String.class)
    private String fase;
    
    @StoredProcedureData(parameterName = "PI_NOMBRE_ARCHIVO", parameterType = String.class)
    private String nombreArchivo;
    
    @StoredProcedureData(parameterName = "PI_GEORREFERENCIADA", parameterType = Integer.class)
    private Integer georreferenciada;
    
    @StoredProcedureData(parameterName = "PI_FICHA_CREADA", parameterType = Integer.class)
    private Integer fichaCreada;
    
    @StoredProcedureData(parameterName = "PI_FECHA_CREACION", parameterType = Date.class)
    private Date fechaCreacion;
    
    @StoredProcedureData(parameterName = "PI_USUARIO_CREACION", parameterType = String.class)
    private String usuarioCreacion;
    
    @StoredProcedureData(parameterName = "PI_PERFIL_CREACION", parameterType = String.class)
    private String perfilCreacion;
    
    @StoredProcedureData(parameterName = "PI_FECHA_EDICION", parameterType = Date.class)
    private Date fechaEdicion;
    
    @StoredProcedureData(parameterName = "PI_USUARIO_EDICION", parameterType = String.class)
    private String usuarioEdicion;
    
    @StoredProcedureData(parameterName = "PI_PERFIL_EDICION", parameterType = String.class)
    private String perfilEdicion;
    
    @StoredProcedureData(parameterName = "PI_ESTADO_REGISTRO", parameterType = Integer.class)
    private Integer estadoRegistro;
    
    @StoredProcedureData(parameterName = "PI_MARCAS", parameterType = String.class)
    private String marcas;
    
    @StoredProcedureData(parameterName = "PI_NOTA", parameterType = String.class)
    private String nota;
    
    @StoredProcedureData(parameterName = "PI_CANTIDAD_REGISTROS", parameterType = Integer.class)
    private Integer cantidadRegistros;
    
    @StoredProcedureData(parameterName = "PI_CANT_PROCESADOS", parameterType = Integer.class)
    private Integer cantidadProcesados;
    
    @StoredProcedureData(parameterName = "PI_CANT_ERROR", parameterType = Integer.class)
    private Integer cantidadError;
    
    @StoredProcedureData(parameterName = "PI_OBSERVACION", parameterType = String.class)
    private String observacion;
            
}
