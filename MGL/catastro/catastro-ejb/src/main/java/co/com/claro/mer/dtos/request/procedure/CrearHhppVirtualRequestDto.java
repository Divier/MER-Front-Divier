package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * DTO que representa el request al procedimiento para de crear un HHPP Virtual.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/06/02
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrearHhppVirtualRequestDto {

    @StoredProcedureData(parameterName = "PI_DIRECCION_ID", parameterType = BigDecimal.class)
    private BigDecimal direccionId;

    @StoredProcedureData(parameterName = "PI_NODO_ID", parameterType = BigDecimal.class)
    private BigDecimal nodoId;

    @StoredProcedureData(parameterName = "PI_TIPO_TECNOLOGIA_HAB_ID", parameterType = String.class)
    private String tipoTecnologiaId;

    @StoredProcedureData(parameterName = "PI_SUB_DIRECCION_ID", parameterType = BigDecimal.class)
    private BigDecimal subDireccionId;

    @StoredProcedureData(parameterName = "PI_TIPO_CONEXION_TEC_HABI_ID", parameterType = BigDecimal.class)
    private BigDecimal tipoConexionTecId;

    @StoredProcedureData(parameterName = "PI_TIPO_RED_TEC_HABI_ID", parameterType = BigDecimal.class)
    private BigDecimal tipoRedTecnologiaId;

    @StoredProcedureData(parameterName = "PI_USUARIO_CREACION", parameterType = String.class)
    private String usuarioCreacion;

    @StoredProcedureData(parameterName = "PI_USUARIO_EDICION", parameterType = String.class)
    private String usuarioEdicion;

    @StoredProcedureData(parameterName = "PI_ESTADO_ID", parameterType = String.class)
    private String estadoId;

    @StoredProcedureData(parameterName = "PI_TECNOLOGIA_HABILITADA_ID_RR", parameterType = String.class)
    private String tecnogiaHabilitadaIdRr;

    @StoredProcedureData(parameterName = "PI_CALLE", parameterType = String.class)
    private String calle;

    @StoredProcedureData(parameterName = "PI_PLACA", parameterType = String.class)
    private String placa;

    @StoredProcedureData(parameterName = "PI_APART", parameterType = String.class)
    private String apart;

    @StoredProcedureData(parameterName = "PI_COMUNIDAD", parameterType = String.class)
    private String comunidad;

    @StoredProcedureData(parameterName = "PI_DIVISION", parameterType = String.class)
    private String division;

    @StoredProcedureData(parameterName = "PI_ESTADO_UNIT", parameterType = String.class)
    private String estadoUnit;

    @StoredProcedureData(parameterName = "PI_VENDEDOR", parameterType = String.class)
    private String vendedor;

    @StoredProcedureData(parameterName = "PI_CODIGO_POSTAL", parameterType = String.class)
    private String codigoPostal;

    @StoredProcedureData(parameterName = "PI_TIPO_ACOMET", parameterType = String.class)
    private String tipoAcometida;

    @StoredProcedureData(parameterName = "PI_ULT_UBICACION", parameterType = String.class)
    private String ultUbicacion;

    @StoredProcedureData(parameterName = "PI_HEAD_END", parameterType = String.class)
    private String headEnd;

    @StoredProcedureData(parameterName = "PI_TIPO", parameterType = String.class)
    private String tipo;

    @StoredProcedureData(parameterName = "PI_EDIFICIO", parameterType = String.class)
    private String edificio;

    @StoredProcedureData(parameterName = "PI_TIPO_UNIDAD", parameterType = String.class)
    private String tipoUnidad;

    @StoredProcedureData(parameterName = "PI_TIPO_CBL_ACOMETIDA", parameterType = String.class)
    private String tipoCblAcometida;

    @StoredProcedureData(parameterName = "PI_NOTA_ADD1", parameterType = String.class)
    private String notaAdd1;

    @StoredProcedureData(parameterName = "PI_NOTA_ADD2", parameterType = String.class)
    private String notaAdd2;

    @StoredProcedureData(parameterName = "PI_NOTA_ADD3", parameterType = String.class)
    private String notaAdd3;

    @StoredProcedureData(parameterName = "PI_NOTA_ADD4", parameterType = String.class)
    private String notaAdd4;

    @StoredProcedureData(parameterName = "PI_SUBEDIFICIO_ID", parameterType = BigDecimal.class)
    private BigDecimal subEdificioId;

    @StoredProcedureData(parameterName = "PI_ESTADO_REGISTRO", parameterType = Integer.class)
    private Integer estadoRegistro;

    @StoredProcedureData(parameterName = "PI_CFM", parameterType = BigDecimal.class)
    private BigDecimal cfm;

    @StoredProcedureData(parameterName = "PI_TECNO_SUBEDIFICIO_ID", parameterType = BigDecimal.class)
    private BigDecimal tecnoSubEdificioId;

    @StoredProcedureData(parameterName = "PI_SUSCRIPTOR", parameterType = String.class)
    private String suscriptor;

    @StoredProcedureData(parameterName = "PI_MARKER", parameterType = String.class)
    private String marker;

    @StoredProcedureData(parameterName = "PI_PERFIL_CREACION", parameterType = Integer.class)
    private Integer perfilCreacion;

    @StoredProcedureData(parameterName = "PI_PERFIL_EDICION", parameterType = Integer.class)
    private Integer perfilEdicion;

    @StoredProcedureData(parameterName = "PI_HHPP_SC_PROCESADO_HHPP", parameterType = String.class)
    private String hhppScProcesado;

    @StoredProcedureData(parameterName = "PI_ORIGEN_FICHA", parameterType = String.class)
    private String origenFicha;

    @StoredProcedureData(parameterName = "PI_CUENTA_CLIENTE_TRASLADAR", parameterType = String.class)
    private String cuentaClienteTrasladar;

    @StoredProcedureData(parameterName = "PI_NAP", parameterType = String.class)
    private String nap;

    @StoredProcedureData(parameterName = "PI_ID_DIR_HHPP_VIRTUAL_RR", parameterType = String.class)
    private String idDirHhppVirtualRr;

}
