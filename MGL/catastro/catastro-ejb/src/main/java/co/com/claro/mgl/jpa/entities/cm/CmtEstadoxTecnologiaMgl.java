package co.com.claro.mgl.jpa.entities.cm;

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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cardenaslb
 */
@Entity
@Table(name = "CMT_ESTADOXTECNOLOGIA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtEstadoxTecnologiaMgl.findAll", query = "SELECT c FROM CmtEstadoxTecnologiaMgl c WHERE c.estadoRegistro=1")})

public class CmtEstadoxTecnologiaMgl implements Serializable {

    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtEstadoxTecnologiaMgl.CmtEstadoxTecnologiaMglSq",
            sequenceName = "MGL_SCHEME.CMT_ESTADOXTECNOLOGIA_SQ",
            allocationSize = 1)
    @GeneratedValue(generator
            = "CmtEstadoxTecnologiaMgl.CmtEstadoxTecnologiaMglSq")
    @Column(name = "ID_ESTADOXTECNOLOGIA")
    private BigDecimal id;

    /**
     * Identificador de tipo basica.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TECNOLOGIA")
    private CmtBasicaMgl basicaId_tecnologias;

    /**
     * Identificador de tipo de validacion.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADOS")
    private CmtBasicaMgl basicaId_estados;

    /**
     * Valor de viabilidad.
     */
    @Column(name = "ACTIVACION", nullable = true, length = 2)
    private int activacion;

    /**
     * Fecha de creacion temporal de los registros.
     */
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    /**
     * Usuario que creo el registro.
     */
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;

    /**
     * Perfil que creo el registro.
     */
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;

    /**
     * Fecha de edicion del registro.
     */
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;

    /**
     * Usuario de edicion del registro.
     */
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;

    /**
     * Perfil de edicion del registro.
     */
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;

    /**
     * Estado de registro 1 activo 0 inactivo.
     */
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Transient
    private int checked;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public CmtBasicaMgl getBasicaId_tecnologias() {
        return basicaId_tecnologias;
    }

    public void setBasicaId_tecnologias(CmtBasicaMgl basicaId_tecnologias) {
        this.basicaId_tecnologias = basicaId_tecnologias;
    }

    public CmtBasicaMgl getBasicaId_estados() {
        return basicaId_estados;
    }

    public void setBasicaId_estados(CmtBasicaMgl basicaId_estados) {
        this.basicaId_estados = basicaId_estados;
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

    public boolean getActivacion() {
        if (activacion == 0) {

            return false;
        } else {

            return true;
        }

    }

    public void setActivacion(boolean activacion) {
        this.activacion = activacion ? 1 : 0;

    }
    
}
