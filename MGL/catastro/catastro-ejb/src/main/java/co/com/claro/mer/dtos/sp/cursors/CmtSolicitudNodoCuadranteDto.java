package co.com.claro.mer.dtos.sp.cursors;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase DTO de procedimiento almacenado SOLICITUD_NODO_CUADRANTE_SP 
 * Permite mapear los campos devueltos por el cursor
 *
 * @author Divier Casas
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CmtSolicitudNodoCuadranteDto implements Cloneable {

    private BigDecimal solicitudId;
    private Date fechaCreacion;
    private String tipoSolicitud;
    private String legado;
    private String resultadoAsociacion;
    private String ciudad;
    private String departamento;
    private String centroPoblado;
    private String estado;
    private String codigoNodo;
    private String divisional;
    private String usuarioBloqueo;
    private Integer idTipoTec;
    private String tipoTec;
    private Integer idComuRr;
    private String comuRr;
    private String nombreNodo;
    private Integer idEstadoNodo;
    private String estadoNodo;
    private String cuadranteId;
    private String email;
    private String resultadoGestion;
    private Date fechaCierreSol;
    private String disponibilidadGestion;
    private transient boolean selected = false;
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
