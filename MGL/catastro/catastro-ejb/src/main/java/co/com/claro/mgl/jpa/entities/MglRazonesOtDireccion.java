/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Orlando Velasquez
 */
@Entity
@Table(name = "MGL_RAZONES_OT_DIRECCION", catalog = "", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MglRazonesOtDireccion.findAll", query = "SELECT m FROM MglRazonesOtDireccion m")
})
public class MglRazonesOtDireccion implements Serializable {

    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "MglRazonesOtDireccion.MglRazonesOtDireccionSeq",
            sequenceName = "MGL_SCHEME.MGL_RAZONES_OT_DIRECCION_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "MglRazonesOtDireccion.MglRazonesOtDireccionSeq")
    @NotNull
    @Column(name = "RAZONES_OT_DIRECCIONES_ID", nullable = false)
    private BigDecimal razonesOtDireccionesId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_OT_ID", referencedColumnName = "TIPO_OT_ID", nullable = false)
    private TipoOtHhppMgl tipoOtId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO_GENERAL", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl estadoGeneral;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RAZON", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl razon;
    @Size(max = 20)
    @Column(name = "ROL", length = 20)
    private String rol;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    public MglRazonesOtDireccion() {
    }

    public MglRazonesOtDireccion(BigDecimal razonesOtDireccionesId) {
        this.razonesOtDireccionesId = razonesOtDireccionesId;
    }

    public MglRazonesOtDireccion(BigDecimal razonesOtDireccionesId,
            TipoOtHhppMgl tipoOtId, CmtBasicaMgl estadoGeneral, CmtBasicaMgl razon) {
        this.razonesOtDireccionesId = razonesOtDireccionesId;
        this.tipoOtId = tipoOtId;
        this.estadoGeneral = estadoGeneral;
        this.razon = razon;
    }

    public BigDecimal getRazonesOtDireccionesId() {
        return razonesOtDireccionesId;
    }

    public void setRazonesOtDireccionesId(BigDecimal razonesOtDireccionesId) {
        this.razonesOtDireccionesId = razonesOtDireccionesId;
    }

    public TipoOtHhppMgl getTipoOtId() {
        return tipoOtId;
    }

    public void setTipoOtId(TipoOtHhppMgl tipoOtId) {
        this.tipoOtId = tipoOtId;
    }

    public CmtBasicaMgl getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoGeneral(CmtBasicaMgl estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }

    public CmtBasicaMgl getRazon() {
        return razon;
    }

    public void setRazon(CmtBasicaMgl razon) {
        this.razon = razon;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

}
