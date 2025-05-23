/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Orlando Velasquez
 */
@Entity
@Table(name = "MGL_PARAMETROS_TRABAJOS", catalog = "", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MglParametrosTrabajos.findAll", query = "SELECT m FROM MglParametrosTrabajos m")
    , @NamedQuery(name = "MglParametrosTrabajos.findByParametrosTrabajosId", query = "SELECT m FROM MglParametrosTrabajos m WHERE m.parametrosTrabajosId = :parametrosTrabajosId")
    , @NamedQuery(name = "MglParametrosTrabajos.findByNombre", query = "SELECT m FROM MglParametrosTrabajos m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "MglParametrosTrabajos.findByValor", query = "SELECT m FROM MglParametrosTrabajos m WHERE m.valor = :valor")
    , @NamedQuery(name = "MglParametrosTrabajos.findByDescripcion", query = "SELECT m FROM MglParametrosTrabajos m WHERE m.descripcion = :descripcion")
    , @NamedQuery(name = "MglParametrosTrabajos.findByEstado", query = "SELECT m FROM MglParametrosTrabajos m WHERE m.estado = :estado")})
public class MglParametrosTrabajos implements Serializable {
    @Id
    @NotNull
    @Column(name = "PARAMETROS_TRABAJOS_ID", nullable = false)
    private Long parametrosTrabajosId;
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;
    @NotNull
    @Size(min = 1, max = 4000)
    @Column(name = "VALOR", nullable = false, length = 4000)
    private String valor;
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "DESCRIPCION", nullable = false, length = 1000)
    private String descripcion;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO", nullable = false)
    private CmtBasicaMgl estado;
    
    @Column(name = "NOMBRE_SERVIDOR")
    private String nombreServidor;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parametrosTrabajosId", fetch = FetchType.LAZY)
    private List<MglParametrosTrabajosDetail> mglParametrosTrabajosDetailList;

    public MglParametrosTrabajos() {
    }

    public MglParametrosTrabajos(Long parametrosTrabajosId) {
        this.parametrosTrabajosId = parametrosTrabajosId;
    }

    public MglParametrosTrabajos(Long parametrosTrabajosId, String nombre, String valor, String descripcion, CmtBasicaMgl estado) {
        this.parametrosTrabajosId = parametrosTrabajosId;
        this.nombre = nombre;
        this.valor = valor;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Long getParametrosTrabajosId() {
        return parametrosTrabajosId;
    }

    public void setParametrosTrabajosId(Long parametrosTrabajosId) {
        this.parametrosTrabajosId = parametrosTrabajosId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CmtBasicaMgl getEstado() {
        return estado;
    }

    public void setEstado(CmtBasicaMgl estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<MglParametrosTrabajosDetail> getMglParametrosTrabajosDetailList() {
        return mglParametrosTrabajosDetailList;
    }

    public void setMglParametrosTrabajosDetailList(List<MglParametrosTrabajosDetail> mglParametrosTrabajosDetailList) {
        this.mglParametrosTrabajosDetailList = mglParametrosTrabajosDetailList;
    }

    public String getNombreServidor() {
        return nombreServidor;
    }

    public void setNombreServidor(String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MglParametrosTrabajos)) {
            return false;
        }
        MglParametrosTrabajos other = (MglParametrosTrabajos) object;
        if ((this.parametrosTrabajosId == null && other.parametrosTrabajosId != null) || (this.parametrosTrabajosId != null && !this.parametrosTrabajosId.equals(other.parametrosTrabajosId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.MglParametrosTrabajos[ parametrosTrabajosId=" + parametrosTrabajosId + " ]";
    }
    
}
