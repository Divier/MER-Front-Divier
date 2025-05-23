/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "MGL_ARRENDATARIO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArrendatarioMgl.findAll", query = "SELECT a FROM ArrendatarioMgl a WHERE a.estadoRegistro=1 order by a.arrendatarioId DESC "),
    @NamedQuery(name = "ArrendatarioMgl.findArrendatariosByCentro",
            query = "SELECT a FROM ArrendatarioMgl a WHERE a.estadoRegistro=1 AND a.centroPoblado = :centroPoblado "),
    @NamedQuery(name = "ArrendatarioMgl.findArrendatariosByCentroAndCityAndDpto",
            query = "SELECT a FROM ArrendatarioMgl a WHERE a.estadoRegistro=1 "
                    + " AND a.centroPoblado = :centroPoblado "
                    + " AND a.ciudad = :ciudad AND a.departamento = :departamento"),
    @NamedQuery(name = "ArrendatarioMgl.findByCentroAndNombreArrendaAndCuadrante",
            query = "SELECT a FROM ArrendatarioMgl a WHERE a.estadoRegistro=1 AND a.cuadrante = :cuadrante")
})
public class ArrendatarioMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "ArrendatarioMgl.seq_arrendatario",
            sequenceName = "MGL_SCHEME.MGL_ARRENDATARIO_SQ", allocationSize = 1
    )
    @GeneratedValue(generator = "ArrendatarioMgl.seq_arrendatario")
    @Column(name = "ARRENDATARIO_ID", nullable = false)
    private BigDecimal arrendatarioId;
    @Column(name = "PREVISITA")
    private String previsita;
    @Column(name = "NOMBRE_ARRENDATARIO")
    private String nombreArrendatario;
    @Column(name = "CUADRANTE")
    private String cuadrante;
    @Column(name = "SLA_PREVISITA")
    private String slaPrevisita;
    @Column(name = "SLA_PERMISOS")
    private String slaPermisos;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTAMENTO_ID", referencedColumnName = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private GeograficoPoliticoMgl departamento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CIUDAD_ID", referencedColumnName = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private GeograficoPoliticoMgl ciudad;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CENTRO_POBLADO_ID", referencedColumnName = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private GeograficoPoliticoMgl centroPoblado;  
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    
    @OneToMany(mappedBy = "arrendatarioObj", fetch = FetchType.LAZY)
    private List<ArrendatarioAuditoriaMgl> listAuditoria;
    

    public BigDecimal getArrendatarioId() {
        return arrendatarioId;
    }

    public void setArrendatarioId(BigDecimal arrendatarioId) {
        this.arrendatarioId = arrendatarioId;
    }
    
    public String getPrevisita() {
        return previsita;
    }

    public void setPrevisita(String previsita) {
        this.previsita = previsita;
    }

    public String getSlaPrevisita() {
        return slaPrevisita;
    }

    public void setSlaPrevisita(String slaPrevisita) {
        this.slaPrevisita = slaPrevisita;
    }

    public String getSlaPermisos() {
        return slaPermisos;
    }

    public void setSlaPermisos(String slaPermisos) {
        this.slaPermisos = slaPermisos;
    }

    public GeograficoPoliticoMgl getDepartamento() {
        return departamento;
    }

    public void setDepartamento(GeograficoPoliticoMgl departamento) {
        this.departamento = departamento;
    }

    public GeograficoPoliticoMgl getCiudad() {
        return ciudad;
    }

    public void setCiudad(GeograficoPoliticoMgl ciudad) {
        this.ciudad = ciudad;
    }

    public GeograficoPoliticoMgl getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(GeograficoPoliticoMgl centroPoblado) {
        this.centroPoblado = centroPoblado;
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

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public List<ArrendatarioAuditoriaMgl> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<ArrendatarioAuditoriaMgl> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

    public String getNombreArrendatario() {
        return nombreArrendatario;
    }

    public void setNombreArrendatario(String nombreArrendatario) {
        this.nombreArrendatario = nombreArrendatario;
    }

    public String getCuadrante() {
        return cuadrante;
    }

    public void setCuadrante(String cuadrante) {
        this.cuadrante = cuadrante;
    }
}
