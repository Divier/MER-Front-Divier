/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *Fabian Barrera
 * @author User
 */
@Entity
@Table(name = "CMT_RELACION", schema = "GESTIONNEW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtRelacionMgl.findAll", query = "SELECT c FROM CmtRelacionMgl c ")})
public class CmtRelacionMgl implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtRelacionMgl.CmtRelacionMglSeq",
            sequenceName = "MGL_SCHEME.CMT_RELACION_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtRelacionMgl.CmtRelacionMglSeq")
    
    @Column(name = "RELACIONID", nullable = false)
    private BigDecimal relacionId;
    @Column(name = "CUENTAMATRIZID")
    private BigDecimal cuentaMatrizId;
    @Column(name = "CODIGOID")
    private String codigoId;
    @Column(name = "ESTADOCM")
    private String estadoCM;
    @Column(name = "TIPOGA")
    private String tipoGA;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "JUSTIFICACION")
    private String justificacion;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date FechaCreacion;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date FechaModificacion;
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "PERFIL")
    private String perfil;

    @Transient
    private boolean seleccionado;

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
    
    public BigDecimal getRelacionId() {
        return relacionId;
    }

    public void setRelacionId(BigDecimal relacionId) {
        this.relacionId = relacionId;
    }

    public BigDecimal getCuentaMatrizId() {
        return cuentaMatrizId;
    }

    public void setCuentaMatrizId(BigDecimal cuentaMatrizId) {
        this.cuentaMatrizId = cuentaMatrizId;
    }

    public String getCodigoId() {
        return codigoId;
    }

    public void setCodigoId(String codigoId) {
        this.codigoId = codigoId;
    }

    public String getEstadoCM() {
        return estadoCM;
    }

    public void setEstadoCM(String estadoCM) {
        this.estadoCM = estadoCM;
    }

    public String getTipoGA() {
        return tipoGA;
    }

    public void setTipoGA(String tipoGA) {
        this.tipoGA = tipoGA;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Date getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(Date FechaCreacion) {
        this.FechaCreacion = FechaCreacion;
    }

    public Date getFechaModificacion() {
        return FechaModificacion;
    }

    public void setFechaModificacion(Date FechaModificacion) {
        this.FechaModificacion = FechaModificacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

}
