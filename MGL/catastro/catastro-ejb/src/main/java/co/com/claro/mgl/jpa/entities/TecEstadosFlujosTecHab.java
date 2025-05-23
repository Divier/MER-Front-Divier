/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
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
 *
 * @author User
 */
@Entity
@Table(name = "TEC_ESTADOS_FLUJOS_TEC_HAB", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TecEstadosFlujosTecHab.findAll", query = "SELECT t FROM TecEstadosFlujosTecHab t")
    , @NamedQuery(name = "TecEstadosFlujosTecHab.findByEstadosFlujosTecHabId", query = "SELECT t FROM TecEstadosFlujosTecHab t WHERE t.estadosFlujosTecHabId = :estadosFlujosTecHabId")
    , @NamedQuery(name = "TecEstadosFlujosTecHab.findByBasicaEstadoTecnologia", query = "SELECT t FROM TecEstadosFlujosTecHab t WHERE t.basicaEstadoTecnologia = :basicaEstadoTecnologia")
    , @NamedQuery(name = "TecEstadosFlujosTecHab.findByEstadoActual", query = "SELECT t FROM TecEstadosFlujosTecHab t WHERE t.estadoActual = :estadoActual")
    , @NamedQuery(name = "TecEstadosFlujosTecHab.findByEstadoActual", query = "SELECT t FROM TecEstadosFlujosTecHab t WHERE t.estadoActual = :estadoActual")
    , @NamedQuery(name = "TecEstadosFlujosTecHab.findByEstadoSiguiente", query = "SELECT t FROM TecEstadosFlujosTecHab t WHERE t.estadoSiguiente = :estadoSiguiente")})
public class TecEstadosFlujosTecHab implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADOS_FLUJOS_TEC_HAB_ID")
    private Long estadosFlujosTecHabId;
    @Size(max = 30)
    @Column(name = "BASICA_ESTADO_TECNOLOGIA")
    private String basicaEstadoTecnologia;
    @Size(max = 30)
    @Column(name = "ESTADO_ACTUAL")
    private String estadoActual;
    @Size(max = 100)
    @Column(name = "ESTADO_SIGUIENTE")
    private String estadoSiguiente;

    public TecEstadosFlujosTecHab() {
    }

    public TecEstadosFlujosTecHab(Long estadosFlujosTecHabId) {
        this.estadosFlujosTecHabId = estadosFlujosTecHabId;
    }

    public Long getEstadosFlujosTecHabId() {
        return estadosFlujosTecHabId;
    }

    public void setEstadosFlujosTecHabId(Long estadosFlujosTecHabId) {
        this.estadosFlujosTecHabId = estadosFlujosTecHabId;
    }

    public String getBasicaEstadoTecnologia() {
        return basicaEstadoTecnologia;
    }

    public void setBasicaEstadoTecnologia(String basicaEstadoTecnologia) {
        this.basicaEstadoTecnologia = basicaEstadoTecnologia;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    public String getEstadoSiguiente() {
        return estadoSiguiente;
    }

    public void setEstadoSiguiente(String estadoSiguiente) {
        this.estadoSiguiente = estadoSiguiente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estadosFlujosTecHabId != null ? estadosFlujosTecHabId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TecEstadosFlujosTecHab)) {
            return false;
        }
        TecEstadosFlujosTecHab other = (TecEstadosFlujosTecHab) object;
        if ((this.estadosFlujosTecHabId == null && other.estadosFlujosTecHabId != null) || (this.estadosFlujosTecHabId != null && !this.estadosFlujosTecHabId.equals(other.estadosFlujosTecHabId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.TecEstadosFlujosTecHab[ estadosFlujosTecHabId=" + estadosFlujosTecHabId + " ]";
    }
    
}
