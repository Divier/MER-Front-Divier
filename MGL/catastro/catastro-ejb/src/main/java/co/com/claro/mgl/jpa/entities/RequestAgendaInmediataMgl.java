/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "MGL_REQUEST_AGENDA_INMEDIATA", schema = "MGL_SCHEME")
@XmlRootElement
public class RequestAgendaInmediataMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "RequestAgendaInmediataMgl.seq_RequestAgendaInmediata",
            sequenceName = "MGL_SCHEME.MGL_REQ_AGENDA_INMEDIATA_SQ", allocationSize = 1
    )
    @GeneratedValue(generator = "RequestAgendaInmediataMgl.seq_RequestAgendaInmediata")
    @Column(name = "REQ_AGE_INM_ID", nullable = false)
    private BigDecimal requestAgeInmId;

    @Column(name = "NUMERO_ORDEN")
    private String numeroOrden;

    @Column(name = "TYPE_ORDEN")
    private String typeOrden;

    @Column(name = "ID_TECNICO")
    private String idTecnico;

    @Column(name = "HORA_INICIO")
    private String horaInicio;

    @Column(name = "HORA_FIN")
    private String horaFin;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "DESCRIPCION_ERROR")
    private String descripcionError;

    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_ID", referencedColumnName = "OT_ID")
    private CmtOrdenTrabajoMgl ordenTrabajoMgl;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_DIRECCION_ID", referencedColumnName = "OT_DIRECCION_ID")
    private OtHhppMgl ordenDirHhppMgl;
    

    public BigDecimal getRequestAgeInmId() {
        return requestAgeInmId;
    }

    public void setRequestAgeInmId(BigDecimal requestAgeInmId) {
        this.requestAgeInmId = requestAgeInmId;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public String getTypeOrden() {
        return typeOrden;
    }

    public void setTypeOrden(String typeOrden) {
        this.typeOrden = typeOrden;
    }

    public String getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(String idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcionError() {
        return descripcionError;
    }

    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public CmtOrdenTrabajoMgl getOrdenTrabajoMgl() {
        return ordenTrabajoMgl;
    }

    public void setOrdenTrabajoMgl(CmtOrdenTrabajoMgl ordenTrabajoMgl) {
        this.ordenTrabajoMgl = ordenTrabajoMgl;
    }

    public OtHhppMgl getOrdenDirHhppMgl() {
        return ordenDirHhppMgl;
    }

    public void setOrdenDirHhppMgl(OtHhppMgl ordenDirHhppMgl) {
        this.ordenDirHhppMgl = ordenDirHhppMgl;
    }

}
