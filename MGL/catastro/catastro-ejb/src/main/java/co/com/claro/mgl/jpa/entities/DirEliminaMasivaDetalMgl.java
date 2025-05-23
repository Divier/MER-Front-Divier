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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "TEC_ELIMINA_MASIVA_DETAL", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DirEliminaMasivaDetalMgl.findAll", query = "SELECT d FROM DirEliminaMasivaDetalMgl d")})
public class DirEliminaMasivaDetalMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
   
    @Basic(optional = false)
    @SequenceGenerator(name = "DirEliminaMasivaDetalMgl.DirEliminaMasivaDetalSq",
            sequenceName = "MGL_SCHEME.TEC_ELIMINA_MASIVA_DETAL_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "DirEliminaMasivaDetalMgl.DirEliminaMasivaDetalSq")
    @Column(name = "ELIMINA_MASIVA_DETAL_ID", nullable = false)
    private BigDecimal emdID;
    @Column(name = "LOG_ELIMINACION_MASIVA_ID", nullable = false)
    private BigDecimal lemID;
    @Column(name = "TECNOLOGIA_HABILITADA_ID", nullable = false)
    private BigDecimal hhpId;
    @Column(name = "DIRECCION_ID", nullable = false)
    private BigDecimal dirId;
    @Column(name = "NODO_ID", nullable = false)
    private BigDecimal nodId;
    @Column(name = "TIPO_TECNOLOGIA_HABILITADA_ID", nullable = false)
    private String thhId;
    @Column(name = "SUB_DIRECCION_ID")
    private BigDecimal sdiId;
    @Column(name = "TIPO_CONEXION_TEC_HABIL_ID", nullable = false)
    private BigDecimal thcId;
    @Column(name = "TIPO_RED_TEC_HABIL_ID", nullable = false)
    private BigDecimal thrId;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date hhpFechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String hhpUsuarioCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date hhpFechaModificacion;
    @Column(name = "USUARIO_EDICION")
    private String hhpUsuarioModificacion;
    @Column(name = "EHH_ID", nullable = false)
    private String ehhId;
    @Column(name = "HHP_ID_RR")
    private String HhpIdrR;
    @Column(name = "HHP_CALLE")
    private String HhpCalle;
    @Column(name = "HHP_PLACA")
    private String HhpPlaca;
    @Column(name = "HHP_APART")
    private String HhpApart;
    @Column(name = "HHP_COMUNIDAD")
    private String HhpComunidad;
    @Column(name = "HHP_DIVISION")
    private String HhpDivision;
    @Column(name = "EMD_RESULTADO_PROCESO")
    private String emdResultadoProceso;
    @Column(name = "EMD_DESCRIPCION_RESULT_PROC")
    private String emdDescripcionResultProc;

    public BigDecimal getEmdID() {
        return emdID;
    }

    public void setEmdID(BigDecimal emdID) {
        this.emdID = emdID;
    }

    public BigDecimal getLemID() {
        return lemID;
    }

    public void setLemID(BigDecimal lemID) {
        this.lemID = lemID;
    }

    public BigDecimal getHhpId() {
        return hhpId;
    }

    public void setHhpId(BigDecimal hhpId) {
        this.hhpId = hhpId;
    }

    public BigDecimal getDirId() {
        return dirId;
    }

    public void setDirId(BigDecimal dirId) {
        this.dirId = dirId;
    }

    public BigDecimal getNodId() {
        return nodId;
    }

    public void setNodId(BigDecimal nodId) {
        this.nodId = nodId;
    }

    public String getThhId() {
        return thhId;
    }

    public void setThhId(String thhId) {
        this.thhId = thhId;
    }

    public BigDecimal getSdiId() {
        return sdiId;
    }

    public void setSdiId(BigDecimal sdiId) {
        this.sdiId = sdiId;
    }

    public BigDecimal getThcId() {
        return thcId;
    }

    public void setThcId(BigDecimal thcId) {
        this.thcId = thcId;
    }

    public BigDecimal getThrId() {
        return thrId;
    }

    public void setThrId(BigDecimal thrId) {
        this.thrId = thrId;
    }

    public Date getHhpFechaCreacion() {
        return hhpFechaCreacion;
    }

    public void setHhpFechaCreacion(Date hhpFechaCreacion) {
        this.hhpFechaCreacion = hhpFechaCreacion;
    }

    public String getHhpUsuarioCreacion() {
        return hhpUsuarioCreacion;
    }

    public void setHhpUsuarioCreacion(String hhpUsuarioCreacion) {
        this.hhpUsuarioCreacion = hhpUsuarioCreacion;
    }

    public Date getHhpFechaModificacion() {
        return hhpFechaModificacion;
    }

    public void setHhpFechaModificacion(Date hhpFechaModificacion) {
        this.hhpFechaModificacion = hhpFechaModificacion;
    }

    public String getHhpUsuarioModificacion() {
        return hhpUsuarioModificacion;
    }

    public void setHhpUsuarioModificacion(String hhpUsuarioModificacion) {
        this.hhpUsuarioModificacion = hhpUsuarioModificacion;
    }

    public String getEhhId() {
        return ehhId;
    }

    public void setEhhId(String ehhId) {
        this.ehhId = ehhId;
    }

    public String getHhpIdrR() {
        return HhpIdrR;
    }

    public void setHhpIdrR(String HhpIdrR) {
        this.HhpIdrR = HhpIdrR;
    }

    public String getHhpCalle() {
        return HhpCalle;
    }

    public void setHhpCalle(String HhpCalle) {
        this.HhpCalle = HhpCalle;
    }

    public String getHhpPlaca() {
        return HhpPlaca;
    }

    public void setHhpPlaca(String HhpPlaca) {
        this.HhpPlaca = HhpPlaca;
    }

    public String getHhpApart() {
        return HhpApart;
    }

    public void setHhpApart(String HhpApart) {
        this.HhpApart = HhpApart;
    }

    public String getHhpComunidad() {
        return HhpComunidad;
    }

    public void setHhpComunidad(String HhpComunidad) {
        this.HhpComunidad = HhpComunidad;
    }

    public String getHhpDivision() {
        return HhpDivision;
    }

    public void setHhpDivision(String HhpDivision) {
        this.HhpDivision = HhpDivision;
    }

    public String getEmdResultadoProceso() {
        return emdResultadoProceso;
    }

    public void setEmdResultadoProceso(String emdResultadoProceso) {
        this.emdResultadoProceso = emdResultadoProceso;
    }

    public String getEmdDescripcionResultProc() {
        return emdDescripcionResultProc;
    }

    public void setEmdDescripcionResultProc(String emdDescripcionResultProc) {
        this.emdDescripcionResultProc = emdDescripcionResultProc;
    }
 
}
