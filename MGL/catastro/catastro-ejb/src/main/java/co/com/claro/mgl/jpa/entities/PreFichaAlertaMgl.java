/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "TEC_PREFICHA_ALERTA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreFichaAlertaMgl.findToProcess", query = "SELECT p FROM PreFichaAlertaMgl p WHERE p.procesado ='0' AND p.fechaCreacion <= :dateToCompare")})
public class PreFichaAlertaMgl implements Serializable {
    
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "PreFichaAlertaMgl.preFichaAlertaSeq",
            sequenceName = "MGL_SCHEME.TEC_PREFICHA_ALERTA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "PreFichaAlertaMgl.preFichaAlertaSeq")
    @Column(name = "PREFICHA_ALERTA_ID", nullable = false)
    private BigDecimal id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREFICHA_ID", referencedColumnName = "PREFICHA_ID", nullable = false)
    private PreFichaMgl prefichaMgl;
    @Column(name = "FECHA_CREACION_PRE_ALERTA", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacionAlerta;
    @Column(name = "FECHA_ENVIO_PRE_ALERTA", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEnvio;
    @Column(name = "PROCESADO_PRE_ALERTA", nullable = true, length = 1)
    private String procesado;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = false)
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION", nullable = false)
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = false)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION", nullable = false)
    private int perfilEdicion;
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private int estadoRegistro;

    @Transient
    private boolean isProcesado;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public PreFichaMgl getPrefichaMgl() {
        return prefichaMgl;
    }

    public void setPrefichaMgl(PreFichaMgl prefichaMgl) {
        this.prefichaMgl = prefichaMgl;
    }
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getProcesado() {
        return procesado;
    }

    public void setProcesado(String procesado) {
        this.procesado = procesado;
    }

    public boolean isIsProcesado() {
        isProcesado = procesado.equalsIgnoreCase("1");        
        return isProcesado;
    }

    public Date getFechaCreacionAlerta() {
        return fechaCreacionAlerta;
    }

    public void setFechaCreacionAlerta(Date fechaCreacionAlerta) {
        this.fechaCreacionAlerta = fechaCreacionAlerta;
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

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
   
}
