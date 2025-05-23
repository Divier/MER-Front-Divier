/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa el request al procedimiento para realizar CRUD a tabla TEC_PREFICHA_GEOREFERENCIA_NEW.
 *
 * @author Johan Gomez
 * @version 1.0, 2023/09/05
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrudTecPrefichaGeoNewRequestDto {
    @StoredProcedureData(parameterName = "PI_PREFICHA_XLS_ID", parameterType = Integer.class)
    private Integer prefichaXlsId;
    @StoredProcedureData(parameterName = "PI_PREFICHA_TEC_HAB_DETA_ID", parameterType = Integer.class)
    private Integer prefichaTecHabDetaId;
    @StoredProcedureData(parameterName = "PI_ACTIVITY_ECONOMIC", parameterType = String.class)
    private String activityEconomic;
    @StoredProcedureData(parameterName = "PI_ADDRESS", parameterType = String.class)
    private String address;
    @StoredProcedureData(parameterName = "PI_ADDRESS_CODE", parameterType = String.class)
    private String addressCode;
    @StoredProcedureData(parameterName = "PI_ADDRESS_CODE_FOUND", parameterType = String.class)
    private String addressCodeFound;
    @StoredProcedureData(parameterName = "PI_ALTERNATE_ADDRESS", parameterType = String.class)
    private String alternateAddress;
    @StoredProcedureData(parameterName = "PI_APPLET_STANDAR", parameterType = String.class)
    private String appletStandar;
    @StoredProcedureData(parameterName = "PI_CATEGORY", parameterType = String.class)
    private String category;
    @StoredProcedureData(parameterName = "PI_CHAGE_NUMBER", parameterType = String.class)
    private String chageNumber;
    @StoredProcedureData(parameterName = "PI_COD_DANE_MCPIO", parameterType = String.class)
    private String codDaneMcpio;
    @StoredProcedureData(parameterName = "PI_CX", parameterType = String.class)
    private String cx;
    @StoredProcedureData(parameterName = "PI_CY", parameterType = String.class)
    private String cy;
    @StoredProcedureData(parameterName = "PI_DANE_CITY", parameterType = String.class)
    private String daneCity;
    @StoredProcedureData(parameterName = "PI_DANE_POP_AREA", parameterType = String.class)
    private String danePopArea;
    @StoredProcedureData(parameterName = "PI_EXIST", parameterType = String.class)
    private String exist;
    @StoredProcedureData(parameterName = "PI_ID_ADDRESS", parameterType = String.class)
    private String idAddress;
    @StoredProcedureData(parameterName = "PI_LEVEL_ECONOMIC", parameterType = String.class)
    private String levelEconomic;
    @StoredProcedureData(parameterName = "PI_LEVEL_LIVE", parameterType = String.class)
    private String levelLive;
    @StoredProcedureData(parameterName = "PI_LOCALITY", parameterType = String.class)
    private String locality;
    @StoredProcedureData(parameterName = "PI_NEIGHBORHOOD", parameterType = String.class)
    private String neighborhood;
    @StoredProcedureData(parameterName = "PI_NODO_DOS", parameterType = String.class)
    private String nodoDos;
    @StoredProcedureData(parameterName = "PI_NODO_TRES", parameterType = String.class)
    private String nodoTres;
    @StoredProcedureData(parameterName = "PI_NODO_UNO", parameterType = String.class)
    private String nodoUno;
    @StoredProcedureData(parameterName = "PI_QUALIFIERS", parameterType = String.class)
    private String qualifiers;
    @StoredProcedureData(parameterName = "PI_SOURCE", parameterType = String.class)
    private String source;
    @StoredProcedureData(parameterName = "PI_STATE_DEF", parameterType = String.class)
    private String stateDef;
    @StoredProcedureData(parameterName = "PI_TRASLATE", parameterType = String.class)
    private String traslate;
    @StoredProcedureData(parameterName = "PI_ZIP_CODE", parameterType = String.class)
    private String zipCode;
    @StoredProcedureData(parameterName = "PI_ZIP_CODE_DISTRICT", parameterType = String.class)
    private String zipCodeDistrict;
    @StoredProcedureData(parameterName = "PI_ZIP_CODE_STATE", parameterType = String.class)
    private String zipCodeState;
    @StoredProcedureData(parameterName = "PI_FECHA_CREACION", parameterType = Date.class)
    private Date fechaCreacion;
    @StoredProcedureData(parameterName = "PI_USUARIO_CREACION", parameterType = String.class)
    private String usuarioCreacion;
    @StoredProcedureData(parameterName = "PI_FECHA_EDICION", parameterType = Date.class)
    private Date fechaEdicion;
    @StoredProcedureData(parameterName = "PI_USUARIO_EDICION", parameterType = String.class)
    private String usuarioEdicion;
    @StoredProcedureData(parameterName = "PI_ESTADO_REGISTRO", parameterType = Integer.class)
    private Integer estadoRegistro;
    @StoredProcedureData(parameterName = "PI_CAMBIO_ESTRATO", parameterType = String.class)
    private String cambioEstrato;
    @StoredProcedureData(parameterName = "PI_CONFIABILIDAD", parameterType = Integer.class)
    private Integer confiabilidad;
    @StoredProcedureData(parameterName = "PI_CONFIABILIDAD_COMPLEMENTO", parameterType = Integer.class)
    private Integer confiabilidadComplemento;
}
