/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cardenaslb
 */
@Entity
@Table(name = "MGL_TIPO_BASICA_EXT", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtTipoBasicaExtMgl.findAll", query = "SELECT c FROM CmtTipoBasicaExtMgl c WHERE c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtTipoBasicaExtMgl.findByIdTipoBasicaExt", query = "SELECT c FROM CmtTipoBasicaExtMgl c WHERE c.idTipoBasicaExt = :idTipoBasicaExt"),
    @NamedQuery(name = "CmtTipoBasicaExtMgl.findByLabelCampo", query = "SELECT c FROM CmtTipoBasicaExtMgl c WHERE c.labelCampo = :labelCampo"),
    @NamedQuery(name = "CmtTipoBasicaExtMgl.findByCampoEntidadAs400", query = "SELECT c FROM CmtTipoBasicaExtMgl c WHERE c.campoEntidadAs400 = :campoEntidadAs400"),
    @NamedQuery(name = "CmtTipoBasicaExtMgl.findByValoresPosibles", query = "SELECT c FROM CmtTipoBasicaExtMgl c WHERE c.valoresPosibles = :valoresPosibles"),
    @NamedQuery(name = "CmtTipoBasicaExtMgl.findByIdTipoBasica", query = "SELECT c FROM CmtTipoBasicaExtMgl c WHERE c.idTipoBasica = :idTipoBasica ORDER BY c.idTipoBasicaExt ASC")})
public class CmtTipoBasicaExtMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtTipoBasicaExtMgl.CmtTipoBasicaExtMglSq",
            sequenceName = "MGL_SCHEME.MGL_TIPO_BASICA_EXT_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtTipoBasicaExtMgl.CmtTipoBasicaExtMglSq")
    @Column(name = "ID_TIPO_BASICA_EXT", nullable = false)
    private Long idTipoBasicaExt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "LABEL_CAMPO", nullable = false, length = 100)
    private String labelCampo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CAMPO_ENTIDAD_AS400", nullable = false, length = 100)
    private String campoEntidadAs400;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "VALORES_POSIBLES", nullable = false, length = 20)
    private String valoresPosibles;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_BASICA_ID")
    private CmtTipoBasicaMgl idTipoBasica;
    @NotNull
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @NotNull
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    @OneToMany(mappedBy = "idTipoBasicaExt", fetch = FetchType.LAZY)
    private List<CmtBasicaExtMgl> listCmtBasicaExtMgl;
    @Transient
    private List<String> camposYN;

    public CmtTipoBasicaExtMgl() {
    }

    public Long getIdTipoBasicaExt() {
        return idTipoBasicaExt;
    }

    public void setIdTipoBasicaExt(Long idTipoBasicaExt) {
        this.idTipoBasicaExt = idTipoBasicaExt;
    }

    public String getLabelCampo() {
        return labelCampo;
    }

    public void setLabelCampo(String labelCampo) {
        this.labelCampo = labelCampo;
    }

    public String getCampoEntidadAs400() {
        return campoEntidadAs400;
    }

    public void setCampoEntidadAs400(String campoEntidadAs400) {
        this.campoEntidadAs400 = campoEntidadAs400;
    }

    public String getValoresPosibles() {
        return valoresPosibles;
    }

    public void setValoresPosibles(String valoresPosibles) {
        this.valoresPosibles = valoresPosibles;
    }

    public CmtTipoBasicaMgl getIdTipoBasica() {
        return idTipoBasica;
    }

    public void setIdTipoBasica(CmtTipoBasicaMgl idTipoBasica) {
        this.idTipoBasica = idTipoBasica;
    }

    public List<CmtBasicaExtMgl> getListCmtBasicaExtMgl() {
        return listCmtBasicaExtMgl;
    }

    public void setListCmtBasicaExtMgl(List<CmtBasicaExtMgl> listCmtBasicaExtMgl) {
        this.listCmtBasicaExtMgl = listCmtBasicaExtMgl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoBasicaExt != null ? idTipoBasicaExt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmtTipoBasicaExtMgl)) {
            return false;
        }
        CmtTipoBasicaExtMgl other = (CmtTipoBasicaExtMgl) object;
        if ((this.idTipoBasicaExt == null && other.idTipoBasicaExt != null) || (this.idTipoBasicaExt != null && !this.idTipoBasicaExt.equals(other.idTipoBasicaExt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaExt[ idTipoBasicaExt=" + idTipoBasicaExt + " ]";
    }

    public List<String> getCamposYN() {
        List retorno = new ArrayList<String>();
        if (valoresPosibles != null && !valoresPosibles.isEmpty()) {
            retorno = Arrays.asList(valoresPosibles.split(","));

        }
        return retorno;
    }

    public void setCamposYN(List<String> camposYN) {
        this.camposYN = camposYN;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

}
