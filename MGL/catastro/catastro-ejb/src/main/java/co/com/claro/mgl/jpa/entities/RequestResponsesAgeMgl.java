/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "MGL_REQUEST_RESPONSES_AGE", schema = "MGL_SCHEME")
@XmlRootElement
public class RequestResponsesAgeMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "RequestResponsesAgeMgl.seq_RequestResponsesAge",
            sequenceName = "MGL_SCHEME.MGL_REQUEST_RESPONSES_AGE_SQ", allocationSize = 1
    )
    @GeneratedValue(generator = "RequestResponsesAgeMgl.seq_RequestResponsesAge")
    @Column(name = "REQ_RES_ID", nullable = false)
    private BigDecimal requestRespId;

    @Column(name = "REQ_RES_TIPO")
    private String tipoPeticion;

    @Column(name = "REQ_RES_CONTENIDO")
    private String contenidoPeticion;

    @Column(name = "REQ_RES_OPERACION")
    private String tipoOperacion;

    @Column(name = "REQ_RES_NUMERO_ORDEN")
    private String numeroOrden;
    
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    

    public BigDecimal getRequestRespId() {
        return requestRespId;
    }

    public void setRequestRespId(BigDecimal requestRespId) {
        this.requestRespId = requestRespId;
    }

    public String getTipoPeticion() {
        return tipoPeticion;
    }

    public void setTipoPeticion(String tipoPeticion) {
        this.tipoPeticion = tipoPeticion;
    }

    public String getContenidoPeticion() {
        return contenidoPeticion;
    }

    public void setContenidoPeticion(String contenidoPeticion) {
        this.contenidoPeticion = contenidoPeticion;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
