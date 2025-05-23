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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad mapeo tabla CMT_ORDENTRABAJORR
 *
 * alejandro.martinez.ext@claro.com.co
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_ORDEN_TRABAJO_RR", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtOrdenTrabajoRrMgl.findAll", query = "SELECT c FROM CmtOrdenTrabajoRrMgl c")})
public class CmtOrdenTrabajoRrMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtOrdenTrabajoRrMgl.CmtOrdenTrabajoRrMglSq",
            sequenceName = "MGL_SCHEME.CMT_ORDENTRABAJORR_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtOrdenTrabajoRrMgl.CmtOrdenTrabajoRrMglSq")
    @Column(name = "OT_RR_ID")
    private BigDecimal idOtRr;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_ID")
    private CmtOrdenTrabajoMgl OtObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_OT_RR_ID")
    private CmtTipoOtRrMgl tipoOtRrObj;
    @Column(name = "FECHA_CIERRE_OT")
    @Temporal(TemporalType.DATE)
    private Date fechaCierreOt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VT_ID")
    private CmtVisitaTecnicaMgl visitaTecnicaObj;
    @Column(name = "DETALLE", nullable = true, length = 200)
    private String detalle;
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
    @NotNull
    @Column(name = "ID_OT_RR")
    private BigDecimal idRr;
    
    private transient CmtBasicaMgl tipoTrabajoRR;

    /**
     * @return the idOtRr
     */
    public BigDecimal getIdOtRr() {
        return idOtRr;
    }

    /**
     * @param idOtRr the idOtRr to set
     */
    public void setIdOtRr(BigDecimal idOtRr) {
        this.idOtRr = idOtRr;
    }

    /**
     * @return the otObj
     */
    public CmtOrdenTrabajoMgl getOtObj() {
        return OtObj;
    }

    /**
     * @param otObj the otObj to set
     */
    public void setOtObj(CmtOrdenTrabajoMgl otObj) {
        OtObj = otObj;
    }

    /**
     * @return the tipoOtRrObj
     */
    public CmtTipoOtRrMgl getTipoOtRrObj() {
        return tipoOtRrObj;
    }

    /**
     * @param tipoOtRrObj the tipoOtRrObj to set
     */
    public void setTipoOtRrObj(CmtTipoOtRrMgl tipoOtRrObj) {
        this.tipoOtRrObj = tipoOtRrObj;
    }

    /**
     * @return the fechaCierreOt
     */
    public Date getFechaCierreOt() {
        return fechaCierreOt;
    }

    /**
     * @param fechaCierreOt the fechaCierreOt to set
     */
    public void setFechaCierreOt(Date fechaCierreOt) {
        this.fechaCierreOt = fechaCierreOt;
    }

    /**
     * @return the visitaTecnicaObj
     */
    public CmtVisitaTecnicaMgl getVisitaTecnicaObj() {
        return visitaTecnicaObj;
    }

    /**
     * @param visitaTecnicaObj the visitaTecnicaObj to set
     */
    public void setVisitaTecnicaObj(CmtVisitaTecnicaMgl visitaTecnicaObj) {
        this.visitaTecnicaObj = visitaTecnicaObj;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
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
     * @return the idRr
     */
    public BigDecimal getIdRr() {
        return idRr;
    }
    /**
     * @param idRr the estadoRegistro to set
     */
    public void setIdRr(BigDecimal idRr) {
        this.idRr = idRr;
    }
    
    
       
    
    @PrePersist
    public void prePersist() {
        estadoRegistro = 1;
        detalle = "CREACION";
    }
    
    @PreUpdate
    public void preUpdate() {
        detalle = "EDICION";
    }

    public CmtBasicaMgl getTipoTrabajoRR() {
        return tipoTrabajoRR;
    }

    public void setTipoTrabajoRR(CmtBasicaMgl tipoTrabajoRR) {
        this.tipoTrabajoRR = tipoTrabajoRR;
    }


   
}
