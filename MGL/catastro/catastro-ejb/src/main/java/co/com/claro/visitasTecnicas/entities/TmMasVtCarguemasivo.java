/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package co.com.claro.visitasTecnicas.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Objetivo:
 *
 * Descripción:
 * 
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 */
@Entity
@Table(name = "TM_MAS_VT_CARGUEMASIVO", catalog = "", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "TmMasVtCarguemasivo.findAll", query = "SELECT t FROM TmMasVtCarguemasivo t")
  , @NamedQuery(name = "TmMasVtCarguemasivo.findByIdSolicitud", query = "SELECT t FROM TmMasVtCarguemasivo t WHERE t.idSolicitud = :idSolicitud")
  , @NamedQuery(name = "TmMasVtCarguemasivo.findByResultadog", query = "SELECT t FROM TmMasVtCarguemasivo t WHERE t.resultadog = :resultadog")
  , @NamedQuery(name = "TmMasVtCarguemasivo.findByUsuarioc", query = "SELECT t FROM TmMasVtCarguemasivo t WHERE t.usuarioc = :usuarioc")
  , @NamedQuery(name = "TmMasVtCarguemasivo.findByRespuestac", query = "SELECT t FROM TmMasVtCarguemasivo t WHERE t.respuestac = :respuestac")
  , @NamedQuery(name = "TmMasVtCarguemasivo.findByArchivo", query = "SELECT t FROM TmMasVtCarguemasivo t WHERE t.archivo = :archivo")})
public class TmMasVtCarguemasivo implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID_SOLICITUD", nullable = false, precision = 0, scale = -127)
  private BigDecimal idSolicitud;
  @Size(max = 50)
  @Column(length = 50)
  private String resultadog;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(nullable = false, length = 50)
  private String usuarioc;
  @Size(max = 256)
  @Column(length = 256)
  private String respuestac;
  @Size(max = 50)
  @Column(length = 50)
  private String archivo;

  public TmMasVtCarguemasivo() {
  }

  public TmMasVtCarguemasivo(BigDecimal idSolicitud) {
    this.idSolicitud = idSolicitud;
  }

  public TmMasVtCarguemasivo(BigDecimal idSolicitud, String usuarioc) {
    this.idSolicitud = idSolicitud;
    this.usuarioc = usuarioc;
  }

  public BigDecimal getIdSolicitud() {
    return idSolicitud;
  }

  public void setIdSolicitud(BigDecimal idSolicitud) {
    this.idSolicitud = idSolicitud;
  }

  public String getResultadog() {
    return resultadog;
  }

  public void setResultadog(String resultadog) {
    this.resultadog = resultadog;
  }

  public String getUsuarioc() {
    return usuarioc;
  }

  public void setUsuarioc(String usuarioc) {
    this.usuarioc = usuarioc;
  }

  public String getRespuestac() {
    return respuestac;
  }

  public void setRespuestac(String respuestac) {
    this.respuestac = respuestac;
  }

  public String getArchivo() {
    return archivo;
  }

  public void setArchivo(String archivo) {
    this.archivo = archivo;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idSolicitud != null ? idSolicitud.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof TmMasVtCarguemasivo)) {
      return false;
    }
    TmMasVtCarguemasivo other = (TmMasVtCarguemasivo) object;
    if ((this.idSolicitud == null && other.idSolicitud != null) || (this.idSolicitud != null && !this.idSolicitud.equals(other.idSolicitud))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "co.com.claro.visitasTecnicas.entities.TmMasVtCarguemasivo[ idSolicitud=" + idSolicitud + " ]";
  }

}
