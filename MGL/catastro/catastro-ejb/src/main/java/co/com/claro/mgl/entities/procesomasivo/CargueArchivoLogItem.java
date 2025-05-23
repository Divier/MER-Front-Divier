/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.entities.procesomasivo;

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
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representar la tabla CARGUE_ARCHIVO_LOG_ITEM
 * <p>
 * Representa la tabla CARGUE_ARCHIVO_LOG_ITEM la cual guarda la información de
 * los registros encontrados en cada uno de los archivos cargados.
 *
 * @author becerraarmr
 *
 */
@Entity
@Table(name = "TEC_CARGUE_ARCHIVO_LOG_ITEM", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CargueArchivoLogItem.findAll",
            query = "SELECT h FROM CargueArchivoLogItem h"),
    @NamedQuery(name = "CargueArchivoLogItem.findByIdArchivoLog",
            query = "SELECT h FROM CargueArchivoLogItem h "
            + "WHERE h.idArchivoLog = :idArchivoLog"),
    @NamedQuery(name = "CargueArchivoLogItem.findByIdArchivoLogAndProcesado",
            query = "SELECT h FROM CargueArchivoLogItem h "
            + "WHERE h.idArchivoLog = :idArchivoLog AND h.estadoProceso = 'PROCESADO' "),
    @NamedQuery(name = "CargueArchivoLogItem.findByIdArchivoLogAndIdComplemento",
            query = "SELECT h FROM CargueArchivoLogItem h "
            + "WHERE h.idArchivoLog = :idArchivoLog AND h.idComplemento = :idComplemento "),
    @NamedQuery(name = "CargueArchivoLogItem.findByIdArchivoLogFraude",
            query = "SELECT h FROM CargueArchivoLogItem h "
                  + " INNER JOIN HhppCargueArchivoLog hc ON h.idArchivoLog = hc.idArchivoLog "
                  + " WHERE h.idArchivoLog = :idArchivoLog "
                  + " AND hc.origen = 'FRAUDE' ")
})
public class CargueArchivoLogItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CargueArchivoLogItem.cargueArchivoLogItemSeq",
            sequenceName = "MGL_SCHEME.TEC_CARG_ARCHIVO_LOG_ITEM_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "CargueArchivoLogItem.cargueArchivoLogItemSeq")
    @Column(name = "ID_CARGUE_LOG", nullable = false)
    private BigDecimal idCargueLog;

    @Column(name = "ID_ARCHIVO_LOG", nullable = false)
    private Long idArchivoLog;

    @Basic(optional = false)
    @Size(min = 1, max = 4000)
    @Column(name = "INFO_ORIGINAL", nullable = false, length = 4000)
    private String info;
    
    @Basic(optional = false)
    @Size(min = 1, max = 4000)
    @Column(name = "INFO_MODIFICADA", nullable = false, length = 4000)
    private String infoMod;


    @Basic(optional = false)
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @Column(name = "ID_COMPLEMENTO", nullable = false)
    private BigDecimal idComplemento;
    
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "ESTADO_PROCESO", nullable = false)
    private String estadoProceso;
        
    @Basic(optional = false)
    @Size(min = 1, max = 4000)
    @Column(name = "DETALLE", nullable = false)
    private String detalle;
            
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_PROCESAMIENTO", nullable = false)
    private Date fechaProcesamiento;
    
    @Basic(optional = false)
    @Size(min = 1, max = 1000)
    @Column(name = "ENCABEZADO_CAMPOS", nullable = false)
    private String encabezadoCampos;
    
    @Column(name = "LLEGO_ROLLBACK")
    private String llegoRollback;
      
    @Column(name = "MOD_ROLLBACK")
    private String modRoolback;
    
    @Column(name = "NUEVOS_VALORES")
    private String nuevosValores;
        

    public CargueArchivoLogItem() {
    }

    public CargueArchivoLogItem(BigDecimal idCargueLog, Long idArchivoLog,
            String info, Date fechaRegistro) {
        this.idCargueLog = idCargueLog;
        this.idArchivoLog = idArchivoLog;
        this.info = info;
        this.fechaRegistro = fechaRegistro;
    }

    public CargueArchivoLogItem(BigDecimal idCargueLog) {
        this.idCargueLog = idCargueLog;
    }

    /**
     * @return the idCargueLog
     */
    public BigDecimal getIdCargueLog() {
        return idCargueLog;
    }

    /**
     * @param idCargueLog the idCargueLog to set
     */
    public void setIdCargueLog(BigDecimal idCargueLog) {
        this.idCargueLog = idCargueLog;
    }

    /**
     * @return the idArchivoLog
     */
    public Long getIdArchivoLog() {
        return idArchivoLog;
    }

    /**
     * @param idArchivoLog the idArchivoLog to set
     */
    public void setIdArchivoLog(Long idArchivoLog) {
        this.idArchivoLog = idArchivoLog;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return the fechaRegistro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the idComplemento
     */
    public BigDecimal getIdComplemento() {
        return idComplemento;
    }

    /**
     * @param idComplemento the idComplemento to set
     */
    public void setIdComplemento(BigDecimal idComplemento) {
        this.idComplemento = idComplemento;
    }

    /**
     * @return the infoMod
     */
    public String getInfoMod() {
        return infoMod;
    }
    
    /**
     * @param infoMod the infoMod to set
     */
    public void setInfoMod(String infoMod) {
        this.infoMod = infoMod;
    }

    public String getEstadoProceso() {
        return estadoProceso;
    }

    public void setEstadoProceso(String estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFechaProcesamiento() {
        return fechaProcesamiento;
    }

    public void setFechaProcesamiento(Date fechaProcesamiento) {
        this.fechaProcesamiento = fechaProcesamiento;
    }

    public String getEncabezadoCampos() {
        return encabezadoCampos;
    }

    public void setEncabezadoCampos(String encabezadoCampos) {
        this.encabezadoCampos = encabezadoCampos;
    }

    public String getLlegoRollback() {
        return llegoRollback;
    }

    public void setLlegoRollback(String llegoRollback) {
        this.llegoRollback = llegoRollback;
    }

    public String getModRoolback() {
        return modRoolback;
    }

    public void setModRoolback(String modRoolback) {
        this.modRoolback = modRoolback;
    }

    public String getNuevosValores() {
        return nuevosValores;
    }

    public void setNuevosValores(String nuevosValores) {
        this.nuevosValores = nuevosValores;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getIdCargueLog() != null ? getIdCargueLog().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CargueArchivoLogItem)) {
            return false;
        }
        CargueArchivoLogItem other = (CargueArchivoLogItem) object;
        return !((this.idArchivoLog == null && other.idArchivoLog != null)
                || (this.idArchivoLog != null
                && !this.idArchivoLog.equals(other.idArchivoLog)));
    }

    @Override
    public String toString() {
        return "idCargueLog=" + getIdCargueLog();
    }

}
