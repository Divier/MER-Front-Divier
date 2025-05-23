package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad mapeo tabla CMT_DETALLEFLUJO
 *
 * alejandro.martinez.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_DETALLE_FLUJO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtDetalleFlujoMgl.findAll", query = "SELECT c FROM CmtDetalleFlujoMgl c"),
    @NamedQuery(name = "CmtDetalleFlujoMgl.findByTipoFlujo", query = "SELECT c FROM CmtDetalleFlujoMgl c WHERE c.tipoFlujoOtObj = :tipoFlujoOt  AND c.basicaTecnologia = :tecnologia"),
    @NamedQuery(name = "CmtDetalleFlujoMgl.findByFlujoAndEstadoIni", query = "SELECT c FROM CmtDetalleFlujoMgl c WHERE c.tipoFlujoOtObj = :tipoFlujoOt AND c.estadoInicialObj = :estadoInicial AND c.basicaTecnologia = :tecnologia  and c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtDetalleFlujoMgl.findByFilter", query = "SELECT c FROM CmtDetalleFlujoMgl c WHERE (c.tipoFlujoOtObj.nombreBasica) like :tipoFlujo and UPPER (c.basicaTecnologia.nombreBasica) like :tecnologia and c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtDetalleFlujoMgl.countByFilter", query = "SELECT COUNT(1) FROM CmtDetalleFlujoMgl c WHERE (c.tipoFlujoOtObj.nombreBasica) like :tipoFlujo and UPPER (c.basicaTecnologia.nombreBasica) like :tecnologia and c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtDetalleFlujoMgl.findByCodigoInternoApp",query = "SELECT c FROM CmtDetalleFlujoMgl c WHERE c.identificadorInternoApp = :identificadorInternoApp AND c.estadoRegistro=1 "),
    @NamedQuery(name = "CmtDetalleFlujoMgl.findByFlujoAndEstadoProAndTecRazon", query = "SELECT c FROM CmtDetalleFlujoMgl c WHERE c.tipoFlujoOtObj = :tipoFlujoOt AND c.proximoEstado = :proximoEstado AND c.basicaTecnologia = :tecnologia "
            + "AND c.basicaRazonNodone IS NOT NULL  AND c.estadoRegistro = 1") ,
    @NamedQuery(name = "CmtDetalleFlujoMgl.findByFlujoAndEstadoIniAndTecRazon", query = "SELECT c FROM CmtDetalleFlujoMgl c WHERE c.tipoFlujoOtObj = :tipoFlujoOt AND c.estadoInicialObj = :estadoInicial AND c.basicaTecnologia = :tecnologia "
            + "AND c.basicaRazonNodone IS NOT NULL  AND c.estadoRegistro = 1") 
 })
public class CmtDetalleFlujoMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtDetalleFlujoMgl.CmtDetalleFlujoSq",
            sequenceName = "MGL_SCHEME.CMT_DETALLEFLUJO_SQ",
            allocationSize = 1)
    @GeneratedValue(generator
            = "CmtDetalleFlujoMgl.CmtDetalleFlujoSq")
    @Column(name = "DETALLEFLUJO_ID")
    private BigDecimal detalleFlujoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPOFLUJO_OT")
    private CmtBasicaMgl tipoFlujoOtObj;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TECNOLOGIA")
    private CmtBasicaMgl basicaTecnologia;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO_INICIAL")
    private CmtBasicaMgl estadoInicialObj;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_PROXIMO_ESTADO")
    private CmtBasicaMgl proximoEstado;

    @Column(name = "APROBACION", nullable = false, length = 1)
    private String aprobacion;

    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;

    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;

    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;

    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;

    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;

    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    @Column(name = "DETALLE", nullable = true, length = 200)
    private String Detalle;
    
    @Column(name = "ROL_APROBADOR", nullable = true, length = 10)
    private String rolAprobador;
    
    @Column(name = "IDENTIFICADOR_INTERNO_APP")
    private String identificadorInternoApp;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_RAZON_NODONE")
    private CmtBasicaMgl basicaRazonNodone;
    
    @Transient
    private boolean apruebaCambioEstado;

    /**
     * @return the detalleFlujoId
     */
    public BigDecimal getDetalleFlujoId() {
        return detalleFlujoId;
    }

    /**
     * @param detalleFlujoId the detalleFlujoId to set
     */
    public void setDetalleFlujoId(BigDecimal detalleFlujoId) {
        this.detalleFlujoId = detalleFlujoId;
    }

    /**
     * @return the estadoInicialId
     */
    public CmtBasicaMgl getEstadoInicialId() {
        return tipoFlujoOtObj;
    }

    /**
     * @param tipoFlujoOtObj
     */
    public void setEstadoInicialId(CmtBasicaMgl tipoFlujoOtObj) {
        this.tipoFlujoOtObj = tipoFlujoOtObj;
    }

    /**
     * @return the estadoInicialObj
     */
    public CmtBasicaMgl getEstadoInicialObj() {
        return estadoInicialObj;
    }

    /**
     * @param estadoInicialObj the estadoInicialObj to set
     */
    public void setEstadoInicialObj(CmtBasicaMgl estadoInicialObj) {
        this.estadoInicialObj = estadoInicialObj;
    }

    /**
     * @return the proximoEstado
     */
    public CmtBasicaMgl getProximoEstado() {
        return proximoEstado;
    }

    /**
     * @param proximoEstado the proximoEstado to set
     */
    public void setProximoEstado(CmtBasicaMgl proximoEstado) {
        this.proximoEstado = proximoEstado;
    }

    /**
     * @return the aprobacion
     */
    public String getAprobacion() {

        return aprobacion;
    }

    /**
     * @param aprobacion the aprobacion to set
     */
    public void setAprobacion(String aprobacion) {
        this.aprobacion = aprobacion;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the usuarioCreacion
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion the usuarioCreacion to set
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the perfilCreacion
     */
    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    /**
     * @param perfilCreacion the perfilCreacion to set
     */
    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    /**
     * @return the fechaEdicion
     */
    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    /**
     * @param fechaEdicion the fechaEdicion to set
     */
    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    /**
     * @return the usuarioEdicion
     */
    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    /**
     * @param usuarioEdicion the usuarioEdicion to set
     */
    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    /**
     * @return the perfilEdicion
     */
    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    /**
     * @param perfilEdicion the perfilEdicion to set
     */
    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    /**
     * @return the estadoRegistro
     */
    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    /**
     * @param estadoRegistro the estadoRegistro to set
     */
    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return Detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        Detalle = detalle;
    }
    /**
     * @return the tipoFlujoOtObj
     */
    public CmtBasicaMgl getTipoFlujoOtObj() {
        return tipoFlujoOtObj;
    }

    /**
     * @param tipoFlujoOtObj the tipoFlujoOtObj to set
     */
    public void setTipoFlujoOtObj(CmtBasicaMgl tipoFlujoOtObj) {
        this.tipoFlujoOtObj = tipoFlujoOtObj;
    }
    /**
     * @return the basicaTecnologia
     */
    public CmtBasicaMgl getBasicaTecnologia() {
        return basicaTecnologia;
    }

    /**
     * @param basicaTecnologia the basicaTecnologia to set
     */
    public void setBasicaTecnologia(CmtBasicaMgl basicaTecnologia) {
        this.basicaTecnologia = basicaTecnologia;
    }
    /**
     * @return the rolAprobador
     */
    public String getRolAprobador() {
        return rolAprobador;
    }

    /**
     * @param rolAprobador the rolAprobador to set
     */
    public void setRolAprobador(String rolAprobador) {
        this.rolAprobador = rolAprobador;
    }

    public boolean isApruebaCambioEstado() {
        return apruebaCambioEstado;
    }

    public void setApruebaCambioEstado(boolean apruebaCambioEstado) {
        this.apruebaCambioEstado = apruebaCambioEstado;
    }

    public String getIdentificadorInternoApp() {
        return identificadorInternoApp;
    }

    public void setIdentificadorInternoApp(String identificadorInternoApp) {
        this.identificadorInternoApp = identificadorInternoApp;
    }

    public CmtBasicaMgl getBasicaRazonNodone() {
        return basicaRazonNodone;
    }

    public void setBasicaRazonNodone(CmtBasicaMgl basicaRazonNodone) {
        this.basicaRazonNodone = basicaRazonNodone;
    }

}
