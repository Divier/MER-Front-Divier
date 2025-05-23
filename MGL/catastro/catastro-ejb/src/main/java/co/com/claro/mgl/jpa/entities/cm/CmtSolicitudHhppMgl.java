/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.HhppMgl;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author gilaj
 */
@Entity
@Table(name = "CMT_SOLICITUD_TECNO_HABILITADA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtSolicitudHhppMgl.findAll",query = "SELECT c FROM CmtSolicitudHhppMgl c WHERE c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtSolicitudHhppMgl.findByHhpp",query="SELECT c FROM CmtSolicitudHhppMgl c WHERE c.hhppMglObj = :hhppMglObj and c.estadoRegistro = 1" ),
    @NamedQuery(name = "CmtSolicitudHhppMgl.findBySubEdificio",query="SELECT c FROM CmtSolicitudHhppMgl c WHERE  c.cmtSubEdificioMglObj = :cmtSubEdificioMglObj and c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtSolicitudHhppMgl.findBySolicitud", query="SELECT c FROM CmtSolicitudHhppMgl c WHERE c.cmtSolicitudCmMglObj = :cmtSolicitudCmMglObj and c.estadoRegistro = 1")
})
public class CmtSolicitudHhppMgl implements Serializable {
    private static final long serialVersionUID = 1L;    
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtSolicitudHhppMgl.CmtSolicitudHhppMglSeq",
            sequenceName = "MGL_SCHEME.CMT_SOLICITUD_TECNO_HABI_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtSolicitudHhppMgl.CmtSolicitudHhppMglSeq")
    @Column(name = "SOLICITUD_TECNO_HABILITADA_ID", nullable = false)
    private BigDecimal cmsolicitudHhppId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TECNOLOGIA_HABILITADA_ID")
    private HhppMgl hhppMglObj;
            
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID")
    private CmtSubEdificioMgl cmtSubEdificioMglObj;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLICITUD_CM_ID")
    private CmtSolicitudCmMgl cmtSolicitudCmMglObj;
    
    @Column(name="OPCION_NIVEL5")
    private String opcionNivel5;
    
    @Column(name="VALOR_NIVEL5")
    private String valorNivel5;
    
    @Column(name="OPCION_NIVEL6")
    private String opcionNivel6;
    
    @Column(name="VALOR_NIVEL6")
    private String valorNivel6;
        
    @Column(name="FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @Column(name="USUARIO_CREACION")    
    private String usuarioCreacion;
    
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    
    @Column(name="FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    
    @Column(name = "TIPOSOLICITUD")
    private int tipoSolicitud;
    @Transient
    private String tipoHhpp;
    /* Brief 57762 Crear HHPP Virtual*/
    @Transient
    @Getter
    @Setter
    private String numCuentaClienteTraslado;
    @Transient
    @Setter
    @Getter
    private HhppMgl hhppMglVirtual;
    /* Cierre Brief 57762 */

    /* ----------------------------------------------------------------*/

    public BigDecimal getCmsolicitudHhppId() {
        return cmsolicitudHhppId;
    }

    public void setCmsolicitudHhppId(BigDecimal cmsolicitudHhppId) {
        this.cmsolicitudHhppId = cmsolicitudHhppId;
    }

    public HhppMgl getHhppMglObj() {
        return hhppMglObj;
    }

    public void setHhppMglObj(HhppMgl hhppMglObj) {
        this.hhppMglObj = hhppMglObj;
    }

    public CmtSubEdificioMgl getCmtSubEdificioMglObj() {
        return cmtSubEdificioMglObj;
    }

    public void setCmtSubEdificioMglObj(CmtSubEdificioMgl cmtSubEdificioMglObj) {
        this.cmtSubEdificioMglObj = cmtSubEdificioMglObj;
    }

    public CmtSolicitudCmMgl getCmtSolicitudCmMglObj() {
        return cmtSolicitudCmMglObj;
    }

    public void setCmtSolicitudCmMglObj(CmtSolicitudCmMgl cmtSolicitudCmMglObj) {
        this.cmtSolicitudCmMglObj = cmtSolicitudCmMglObj;
    }

    public String getOpcionNivel5() {
        return opcionNivel5;
    }

    public void setOpcionNivel5(String opcionNivel5) {
        this.opcionNivel5 = opcionNivel5;
    }

    public String getValorNivel5() {
        return valorNivel5;
    }

    public void setValorNivel5(String valorNivel5) {
        this.valorNivel5 = valorNivel5;
    }

    public String getOpcionNivel6() {
        return opcionNivel6;
    }

    public void setOpcionNivel6(String opcionNivel6) {
        this.opcionNivel6 = opcionNivel6;
    }

    public String getValorNivel6() {
        return valorNivel6;
    }

    public void setValorNivel6(String valorNivel6) {
        this.valorNivel6 = valorNivel6;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public int getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(int tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getTipoHhpp() {
        return tipoHhpp;
    }

    public void setTipoHhpp(String tipoHhpp) {
        this.tipoHhpp = tipoHhpp;
    }

}
