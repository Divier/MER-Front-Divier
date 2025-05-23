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
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Orlando Velasquez
 */
@Entity
@Table(name = "MGL_PARAMETROS_TRABAJOS_DETAIL", catalog = "", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MglParametrosTrabajosDetail.findAll", query = "SELECT m FROM MglParametrosTrabajosDetail m")
    , @NamedQuery(name = "MglParametrosTrabajosDetail.findByParamTrabajosDetailId", query = "SELECT m FROM MglParametrosTrabajosDetail m WHERE m.paramTrabajosDetailId = :paramTrabajosDetailId")
    , @NamedQuery(name = "MglParametrosTrabajosDetail.findByFechaInicio", query = "SELECT m FROM MglParametrosTrabajosDetail m WHERE m.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "MglParametrosTrabajosDetail.findByFechaFin", query = "SELECT m FROM MglParametrosTrabajosDetail m WHERE m.fechaFin = :fechaFin")
    , @NamedQuery(name = "MglParametrosTrabajosDetail.findByRegistrosModificados", query = "SELECT m FROM MglParametrosTrabajosDetail m WHERE m.registrosAfectados = :registrosAfectados")
    , @NamedQuery(name = "MglParametrosTrabajosDetail.findByEstado", query = "SELECT m FROM MglParametrosTrabajosDetail m WHERE m.estado = :estado")})
public class MglParametrosTrabajosDetail implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "PARAM_TRABAJOS_DETAIL_ID", nullable = false)
    @SequenceGenerator(
            name = "MglParametrosTrabajosDetail.MglParametrosTrabajosDetail",
            sequenceName = "MGL_SCHEME.MGL_PARAM_TRABAJOS_DETAI_SQ", allocationSize = 1)
    @GeneratedValue(generator = "MglParametrosTrabajosDetail.MglParametrosTrabajosDetail")
    private BigDecimal paramTrabajosDetailId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_INICIO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;
    @Column(name = "REGISTROS_AFECTADOS")
    private Long registrosAfectados;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO", nullable = false)
    private CmtBasicaMgl estado;

    @JoinColumn(name = "PARAMETROS_TRABAJOS_ID", referencedColumnName = "PARAMETROS_TRABAJOS_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MglParametrosTrabajos parametrosTrabajosId;

    public MglParametrosTrabajosDetail() {
    }

    public MglParametrosTrabajosDetail(BigDecimal paramTrabajosDetailId) {
        this.paramTrabajosDetailId = paramTrabajosDetailId;
    }

    public MglParametrosTrabajosDetail(BigDecimal paramTrabajosDetailId, Date fechaInicio) {
        this.paramTrabajosDetailId = paramTrabajosDetailId;
        this.fechaInicio = fechaInicio;
    }

    public BigDecimal getParamTrabajosDetailId() {
        return paramTrabajosDetailId;
    }

    public void setParamTrabajosDetailId(BigDecimal paramTrabajosDetailId) {
        this.paramTrabajosDetailId = paramTrabajosDetailId;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getRegistrosAfectados() {
        return registrosAfectados;
    }

    public void setRegistrosAfectados(Long registrosAfectados) {
        this.registrosAfectados = registrosAfectados;
    }

    public CmtBasicaMgl getEstado() {
        return estado;
    }

    public void setEstado(CmtBasicaMgl estado) {
        this.estado = estado;
    }

    public MglParametrosTrabajos getParametrosTrabajosId() {
        return parametrosTrabajosId;
    }

    public void setParametrosTrabajosId(MglParametrosTrabajos parametrosTrabajosId) {
        this.parametrosTrabajosId = parametrosTrabajosId;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MglParametrosTrabajosDetail)) {
            return false;
        }
        MglParametrosTrabajosDetail other = (MglParametrosTrabajosDetail) object;
        if ((this.paramTrabajosDetailId == null && other.paramTrabajosDetailId != null) || (this.paramTrabajosDetailId != null && !this.paramTrabajosDetailId.equals(other.paramTrabajosDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.MglParametrosTrabajosDetail[ paramTrabajosDetailId=" + paramTrabajosDetailId + " ]";
    }

}
