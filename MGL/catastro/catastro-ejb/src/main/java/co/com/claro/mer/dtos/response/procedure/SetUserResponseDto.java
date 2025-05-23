package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * Dto de respuesta del procedimiento almacenado SET_USER_SP
 * <p>
 *     Parametros:
 *     <ul>
 *         <li>PO_ID_USUARIO_PORTAL</li>
 *         <li>PO_ID_PERFIL</li>
 *         <li>PO_COD_PERFIL</li>
 *         <li>PO_DESC_PERFIL</li>
 *         <li>PO_ID_AREA</li>
 *         <li>PO_AREA</li>
 *         <li>PO_NOMBRE</li>
 *         <li>PO_TELEFONO</li>
 *         <li>PO_EMAIL</li>
 *         <li>PO_DIRECCION</li>
 *         <li>PO_USUARIO</li>
 *         <li>PO_CODIGO</li>
 *         <li>PO_DESCRIPCION</li>
 *     </ul>
 * </p>
 * @see co.com.claro.mer.annotations.StoredProcedureData para mas informacion de los parametros
 * @see co.com.claro.mer.dtos.request.procedure.SetUserRequestDto para mas informacion de los parametros de entrada
 * @autor Manuel Hernández
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetUserResponseDto {

    @StoredProcedureData(parameterName = "PO_ID_USUARIO_PORTAL", parameterType = BigDecimal.class)
    private BigDecimal idUsuarioPortal;

    @StoredProcedureData(parameterName = "PO_ID_PERFIL", parameterType = BigDecimal.class)
    private BigDecimal idPerfil;

    @StoredProcedureData(parameterName = "PO_COD_PERFIL", parameterType = String.class)
    private String codPerfil;

    @StoredProcedureData(parameterName = "PO_DESC_PERFIL", parameterType = String.class)
    private String descPerfil;

    @StoredProcedureData(parameterName = "PO_ID_AREA", parameterType = BigDecimal.class)
    private BigDecimal idArea;

    @StoredProcedureData(parameterName = "PO_AREA", parameterType = String.class)
    private String area;

    @StoredProcedureData(parameterName = "PO_NOMBRE", parameterType = String.class)
    private String nombre;

    @StoredProcedureData(parameterName = "PO_TELEFONO", parameterType = String.class)
    private String telefono;

    @StoredProcedureData(parameterName = "PO_EMAIL", parameterType = String.class)
    private String email;

    @StoredProcedureData(parameterName = "PO_DIRECCION", parameterType = String.class)
    private String direccion;

    @StoredProcedureData(parameterName = "PO_USUARIO", parameterType = String.class)
    private String usuario;

    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = Integer.class)
    private Integer codigo;

    @StoredProcedureData(parameterName = "PO_DESCRIPCION", parameterType = String.class)
    private String descripcion;
}
