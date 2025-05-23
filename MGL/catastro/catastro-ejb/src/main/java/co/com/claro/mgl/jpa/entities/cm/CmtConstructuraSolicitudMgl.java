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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_CONSTRUCTURA_SOLICITUD", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtConstructuraSolicitudMgl.findAll", query = "SELECT c FROM CmtConstructuraSolicitudMgl c ")})
public class CmtConstructuraSolicitudMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtConstructuraSolicitudMgl.CmtConstructuraSolicitudMglSeq",
            sequenceName = "MGL_SCHEME.CMT_CONSTRUCTURA_SOL_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtConstructuraSolicitudMgl.CmtConstructuraSolicitudMglSeq")
    @Column(name = "ID_CONSTRUCTORA_SOL", nullable = false)
    private BigDecimal idConstructoraSol;

    @Column(name = "ID_SOLICITUD_CM", nullable = false)
    private BigDecimal idSolicitudCm;

    @Column(name = "COMPANIA")
    private String compania;

    @Column(name = "FECHA_ENTEDIF")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEntedif;

    @Column(name = "TIPO_PROYECTO")
    private BigDecimal tipoProyect;

    @Column(name = "TIPO_ORIGEN_DATOS")
    private BigDecimal tipoOrigenDatos;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "ID_TIPO_ESTABLECIMIENTO")
    private BigDecimal idTipoEstablecimiento;

    @Column(name = "NOMBRE_ASESOR")
    private String nombreAsesor;

    @Column(name = "NOMBRE_ESPECIALISTA")
    private String nombreEspecialista;

    @Column(name = "TOTAL_UNIDADES_PLANEADAS")
    private BigDecimal totalUnidadesPlaneadas;

    @Column(name = "CODIGO_ASESOR")
    private BigDecimal codigoAsesor;

    @Column(name = "CODIGO_ESPECIALISTA")
    private BigDecimal codigoEspecialista;

    public BigDecimal getIdConstructoraSol() {
        return idConstructoraSol;
    }

    public void setIdConstructoraSol(BigDecimal idConstructoraSol) {
        this.idConstructoraSol = idConstructoraSol;
    }

    public BigDecimal getIdSolicitudCm() {
        return idSolicitudCm;
    }

    public void setIdSolicitudCm(BigDecimal idSolicitudCm) {
        this.idSolicitudCm = idSolicitudCm;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public Date getFechaEntedif() {
        return fechaEntedif;
    }

    public void setFechaEntedif(Date fechaEntedif) {
        this.fechaEntedif = fechaEntedif;
    }

    public BigDecimal getTipoProyect() {
        return tipoProyect;
    }

    public void setTipoProyect(BigDecimal tipoProyect) {
        this.tipoProyect = tipoProyect;
    }

    public BigDecimal getTipoOrigenDatos() {
        return tipoOrigenDatos;
    }

    public void setTipoOrigenDatos(BigDecimal tipoOrigenDatos) {
        this.tipoOrigenDatos = tipoOrigenDatos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getIdTipoEstablecimiento() {
        return idTipoEstablecimiento;
    }

    public void setIdTipoEstablecimiento(BigDecimal idTipoEstablecimiento) {
        this.idTipoEstablecimiento = idTipoEstablecimiento;
    }

    public String getNombreAsesor() {
        return nombreAsesor;
    }

    public void setNombreAsesor(String nombreAsesor) {
        this.nombreAsesor = nombreAsesor;
    }

    public String getNombreEspecialista() {
        return nombreEspecialista;
    }

    public void setNombreEspecialista(String nombreEspecialista) {
        this.nombreEspecialista = nombreEspecialista;
    }

    public BigDecimal getTotalUnidadesPlaneadas() {
        return totalUnidadesPlaneadas;
    }

    public void setTotalUnidadesPlaneadas(BigDecimal totalUnidadesPlaneadas) {
        this.totalUnidadesPlaneadas = totalUnidadesPlaneadas;
    }

    public BigDecimal getCodigoAsesor() {
        return codigoAsesor;
    }

    public void setCodigoAsesor(BigDecimal codigoAsesor) {
        this.codigoAsesor = codigoAsesor;
    }

    public BigDecimal getCodigoEspecialista() {
        return codigoEspecialista;
    }

    public void setCodigoEspecialista(BigDecimal codigoEspecialista) {
        this.codigoEspecialista = codigoEspecialista;
    }
}
