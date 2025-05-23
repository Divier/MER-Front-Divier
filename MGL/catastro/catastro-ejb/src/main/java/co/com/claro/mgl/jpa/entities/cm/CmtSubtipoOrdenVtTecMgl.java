/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cardenaslb
 */
@Entity
@Table(name = "CMT_SUBTIPO_OT_VT_TEC", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtSubtipoOrdenVtTecMgl  implements Serializable {
     private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtSubtipoOrdenVtTecMgl.CmtSubtipoOrdenVtTecMglSq",
            sequenceName = "MGL_SCHEME.CMT_SUBTIPO_OT_VT_TEC_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtSubtipoOrdenVtTecMgl.CmtSubtipoOrdenVtTecMglSq")
    @Column(name = "ID_SUBTIPO_OT_VT_TEC")
    private BigDecimal idSubtipoOtVtTec;
    @JoinColumn(name = "ID_TIPO_OT")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtTipoOtMgl tipoFlujoOtObj;
    @JoinColumn(name = "BASICA_ID_TECNO")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl basicaTecnologia;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEdicion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCrecion;
    @OneToMany(mappedBy = "idSubtipoOtVtTec", fetch = FetchType.LAZY)
    List<CmtSubtipoOrdenVtTecAudMgl> listAuditoria;
    
    
      public CmtSubtipoOrdenVtTecMgl() {
          
    }

    public BigDecimal getIdSubtipoOtVtTec() {
        return idSubtipoOtVtTec;
    }

    public void setIdSubtipoOtVtTec(BigDecimal idSubtipoOtVtTec) {
        this.idSubtipoOtVtTec = idSubtipoOtVtTec;
    }

    public CmtTipoOtMgl getTipoFlujoOtObj() {
        return tipoFlujoOtObj;
    }

    public void setTipoFlujoOtObj(CmtTipoOtMgl tipoFlujoOtObj) {
        this.tipoFlujoOtObj = tipoFlujoOtObj;
    }

   

    public CmtBasicaMgl getBasicaTecnologia() {
        return basicaTecnologia;
    }

    public void setBasicaTecnologia(CmtBasicaMgl basicaTecnologia) {
        this.basicaTecnologia = basicaTecnologia;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
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

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public int getPerfilCrecion() {
        return perfilCrecion;
    }

    public void setPerfilCrecion(int perfilCrecion) {
        this.perfilCrecion = perfilCrecion;
    }

    public List<CmtSubtipoOrdenVtTecAudMgl> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<CmtSubtipoOrdenVtTecAudMgl> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }


      
      
}
