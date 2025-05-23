/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.entities.procesomasivo;

import java.io.Serializable;
import java.math.BigInteger;
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
 * Objetivo:
 * <p>
 * Descripción:
 *
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 */
@Entity
@Table(name = "TEC_TEC_HABI_CARGUE_ARC_LOG", catalog = "", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "HhppCargueArchivoLog.findAll", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen IS NULL")
  , @NamedQuery(name = "HhppCargueArchivoLog.findByIdArchivoLog", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen IS NULL AND h.idArchivoLog = :idArchivoLog")
  , @NamedQuery(name = "HhppCargueArchivoLog.findByNombreArchivo", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen IS NULL AND h.nombreArchivoCargue = :nombreArchivo")
  , @NamedQuery(name = "HhppCargueArchivoLog.findByNombreArchivoTcrm", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen IS NULL AND h.nombreArchivoTcrm = :nombreArchivo")
  , @NamedQuery(name = "HhppCargueArchivoLog.findByEstado", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen IS NULL AND h.estado = :estado")
  , @NamedQuery(name = "HhppCargueArchivoLog.findByEstadoOrderFechaRegistroAsc", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen IS NULL AND h.estado = :estado ORDER BY h.fechaRegistro DESC")
  , @NamedQuery(name = "HhppCargueArchivoLog.findByEstadoUsuarioOrderFechaRegistroDesc", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen IS NULL AND h.estado = :estado AND h.usuario=:usuario ORDER BY h.fechaRegistro DESC")
  , @NamedQuery(name = "HhppCargueArchivoLog.findByFechaRegistro", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen IS NULL AND h.fechaRegistro = :fechaRegistro")
  , @NamedQuery(name = "HhppCargueArchivoLog.findByCantidadTotal", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen IS NULL AND h.cantidadTotal = :cantidadTotal")
  , @NamedQuery(name = "HhppCargueArchivoLog.findByCantidadProcesada", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen IS NULL AND h.cantidadProcesada = :cantidadProcesada")
  , @NamedQuery(name = "HhppCargueArchivoLog.findByUsuario", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen IS NULL AND h.usuario = :usuario")
  , @NamedQuery(name = "HhppCargueArchivoLog.findByFechaModificacion", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen IS NULL AND h.fechaModificacion = :fechaModificacion")
  , @NamedQuery(name = "HhppCargueArchivoLog.findByOrigen", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen = 'FRAUDE'")
  , @NamedQuery(name = "HhppCargueArchivoLog.findByEstadoUsuarioOrderFechaRegistroDescByOrigen", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen = 'FRAUDE' AND h.estado = :estado AND h.usuario=:usuario ORDER BY h.fechaRegistro DESC")
  , @NamedQuery(name = "HhppCargueArchivoLog.findByEstadoOrderFechaRegistroAscByOrigen", query = "SELECT h FROM HhppCargueArchivoLog h WHERE h.origen = 'FRAUDE' AND h.estado = :estado ORDER BY h.fechaRegistro DESC")})
  
public class HhppCargueArchivoLog implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @SequenceGenerator(
          name = "HhppCargueArchivoLog.hhppCargeArchivoLogSq",
          sequenceName = "MGL_SCHEME.TEC_TEC_HABI_CARGUE_ARC_LOG_SQ", allocationSize = 1)
  @GeneratedValue(generator = "HhppCargueArchivoLog.hhppCargeArchivoLogSq")
  @Column(name = "ID_ARCHIVO_LOG", nullable = false)
  private Long idArchivoLog;
  @Basic(optional = false)
  @Size(min = 1, max = 30)
  @Column(name = "NOMBRE_ARCHIVO_TCRM", nullable = false, length = 30)
  private String nombreArchivoTcrm;
  @Basic(optional = false)
  @Column(nullable = false)
  private short estado;
  @Basic(optional = false)
  @Column(name = "FECHA_REGISTRO")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaRegistro;
  @Basic(optional = false)
  @Column(name = "CANTIDAD_TOTAL", nullable = false)
  private BigInteger cantidadTotal;
  @Basic(optional = false)
  @Column(name = "CANTIDAD_PROCESADA")
  private BigInteger cantidadProcesada;
  @Basic(optional = false)
  @Size(min = 1, max = 15)
  @Column(name = "USUARIO_CREACION", nullable = false, length = 15)
  private String usuario;
  @Basic(optional = false)
  @Column(name = "FECHA_MODIFICACION")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaModificacion;
  
  @Column(name = "UTILIZO_ROLLBACK")
  private String utilizoRollback;
  @Column(name = "ENVIO_TCRM")
  private String envioTcrm;
  @Column(name = "TIPO_MOD")
  private int tipoMod;
  
  @Column(name = "NOMBRE_ARCHIVO_CARGUE", nullable = false, length = 30)
  private String nombreArchivoCargue;
  
  @Column(name = "ORIGEN", nullable = true, length = 10)
  private String origen;
  
  @Basic(optional = false)
  @Column(name = "CANTIDAD_EXITOSO", nullable = false)
  private BigInteger cantidadExitoso;
  
  @Basic(optional = false)
  @Column(name = "CANTIDAD_FALLIDO", nullable = false)
  private BigInteger cantidadFallido;
  
  @Column(name = "ENVIO_EOC_BCSC")
  private String envioEocBcsc;
  
  
  @Column(name = "NOMBRE_ARCHIVO_EOC")
  private String nombreArchivoEOC;
 
  public HhppCargueArchivoLog() {
  }

  public HhppCargueArchivoLog(Long idArchivoLog) {
    this.idArchivoLog = idArchivoLog;
  }

  public HhppCargueArchivoLog(Long idArchivoLog, String nombreArchivo, short estado, Date fechaRegistro, BigInteger cantidadTotal, BigInteger cantidadProcesada, String usuario, Date fechaModificacion) {
    this.idArchivoLog = idArchivoLog;
    this.nombreArchivoCargue = nombreArchivo;
    this.estado = estado;
    this.fechaRegistro = fechaRegistro;
    this.cantidadTotal = cantidadTotal;
    this.cantidadProcesada = cantidadProcesada;
    this.usuario = usuario;
    this.fechaModificacion = fechaModificacion;
  }

  public Long getIdArchivoLog() {
    return idArchivoLog;
  }

  public void setIdArchivoLog(Long idArchivoLog) {
    this.idArchivoLog = idArchivoLog;
  }

  public short getEstado() {
    return estado;
  }

  public void setEstado(short estado) {
    this.estado = estado;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public BigInteger getCantidadTotal() {
    return cantidadTotal;
  }

  public void setCantidadTotal(BigInteger cantidadTotal) {
    this.cantidadTotal = cantidadTotal;
  }

  public BigInteger getCantidadProcesada() {
    return cantidadProcesada;
  }

  public void setCantidadProcesada(BigInteger cantidadProcesada) {
    this.cantidadProcesada = cantidadProcesada;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

    public String getUtilizoRollback() {
        return utilizoRollback;
    }

    public void setUtilizoRollback(String utilizoRollback) {
        this.utilizoRollback = utilizoRollback;
    }

    public String getEnvioTcrm() {
        return envioTcrm;
    }

    public void setEnvioTcrm(String envioTcrm) {
        this.envioTcrm = envioTcrm;
    }

    public int getTipoMod() {
        return tipoMod;
    }

    public void setTipoMod(int tipoMod) {
        this.tipoMod = tipoMod;
    }

    public String getNombreArchivoTcrm() {
        return nombreArchivoTcrm;
    }

    public void setNombreArchivoTcrm(String nombreArchivoTcrm) {
        this.nombreArchivoTcrm = nombreArchivoTcrm;
    }

    public String getNombreArchivoCargue() {
        return nombreArchivoCargue;
    }

    public void setNombreArchivoCargue(String nombreArchivoCargue) {
        this.nombreArchivoCargue = nombreArchivoCargue;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public BigInteger getCantidadExitoso() {
        return cantidadExitoso;
    }

    public void setCantidadExitoso(BigInteger cantidadExitoso) {
        this.cantidadExitoso = cantidadExitoso;
    }

    public BigInteger getCantidadFallido() {
        return cantidadFallido;
    }

    public void setCantidadFallido(BigInteger cantidadFallido) {
        this.cantidadFallido = cantidadFallido;
    }

    public String getEnvioEocBcsc() {
        return envioEocBcsc;
    }

    public void setEnvioEocBcsc(String envioEocBcsc) {
        this.envioEocBcsc = envioEocBcsc;
    }

    public String getNombreArchivoEOC() {
        return nombreArchivoEOC;
    }

    public void setNombreArchivoEOC(String nombreArchivoEOC) {
        this.nombreArchivoEOC = nombreArchivoEOC;
    }
    
    

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idArchivoLog != null ? idArchivoLog.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof HhppCargueArchivoLog)) {
      return false;
    }
    HhppCargueArchivoLog other = (HhppCargueArchivoLog) object;
    if ((this.idArchivoLog == null && other.idArchivoLog != null) || (this.idArchivoLog != null && !this.idArchivoLog.equals(other.idArchivoLog))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.com.claro.mgl.dao.impl.HhppCargueArchivoLog[ idArchivoLog=" + idArchivoLog + " ]";
  }

}
