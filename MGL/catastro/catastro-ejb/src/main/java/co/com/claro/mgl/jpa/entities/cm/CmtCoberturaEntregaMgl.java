/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@Entity
@Table(name = "CMT_COBERTURA_ENTREGA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtCoberturaEntregaMgl.findAll", query = "SELECT c FROM CmtCoberturaEntregaMgl c"),
    @NamedQuery(name = "CmtCoberturaEntregaMgl.findCoberturaEntregaListByCentroPobladoId", query = "SELECT c FROM CmtCoberturaEntregaMgl c WHERE C.geograficoPolitico.gpoId = :gpoId AND c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtCoberturaEntregaMgl.findCoberturaEntregaListByCentroPobladoIdOperadorId", query = "SELECT c FROM CmtCoberturaEntregaMgl c WHERE C.geograficoPolitico.gpoId = :gpoId AND C.operadorBasicaID.basicaId = :operadorId AND c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtCoberturaEntregaMgl.findListaCoberturaEntrega", query = "SELECT CONCAT('(',c.operadorBasicaID.codigoBasica,')(',c.basica.codigoBasica,')') FROM CmtCoberturaEntregaMgl c WHERE C.geograficoPolitico.gpoId = :gpoId AND c.estadoRegistro = 1")
})
public class CmtCoberturaEntregaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtCoberturaEntregaMgl.cmtCoberturaEntregaSq",
            sequenceName = "MGL_SCHEME.CMT_COBERTURA_ENTREGA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtCoberturaEntregaMgl.cmtCoberturaEntregaSq")
    @NotNull
    @Column(name = "COBERTURA_ENTREGA_ID")
    private BigDecimal coberturaEntregaId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GPO_ID")
    private GeograficoPoliticoMgl geograficoPolitico;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID")
    private CmtBasicaMgl basica;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERADOR_BASICA_ID")
    private CmtBasicaMgl operadorBasicaID;

    @Column(name = "ESTADO_REGISTRO")
    private Short estadoRegistro;

    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Size(max = 20)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "PERFIL_CREACION")
    private Long perfilCreacion;

    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;

    @Size(max = 20)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;

    @Size(max = 15)
    @Column(name = "PERFIL_EDICION")
    private String perfilEdicion;

    public BigDecimal getCoberturaEntregaId() {
        return coberturaEntregaId;
    }

    public void setCoberturaEntregaId(BigDecimal coberturaEntregaId) {
        this.coberturaEntregaId = coberturaEntregaId;
    }

    public GeograficoPoliticoMgl getGeograficoPolitico() {
        return geograficoPolitico;
    }

    public void setGeograficoPolitico(GeograficoPoliticoMgl geograficoPolitico) {
        this.geograficoPolitico = geograficoPolitico;
    }

    public CmtBasicaMgl getBasica() {
        return basica;
    }

    public void setBasica(CmtBasicaMgl basica) {
        this.basica = basica;
    }

    public Short getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(Short estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
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

    public Long getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(Long perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
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

    public String getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(String perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    /**
     * @return the operadorBasicaID
     */
    public CmtBasicaMgl getOperadorBasicaID() {
        return operadorBasicaID;
    }

    /**
     * @param operadorBasicaID the operadorBasicaID to set
     */
    public void setOperadorBasicaID(CmtBasicaMgl operadorBasicaID) {
        this.operadorBasicaID = operadorBasicaID;
    }
}
