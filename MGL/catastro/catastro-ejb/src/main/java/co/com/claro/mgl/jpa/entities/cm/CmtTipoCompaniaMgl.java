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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_TIPO_COMPANIA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtTipoCompaniaMgl.findAll", query = "SELECT c FROM CmtTipoCompaniaMgl c "),
    @NamedQuery(name = "CmtTipoCompaniaMgl.findByNombreTipo", query = "SELECT c FROM CmtTipoCompaniaMgl c WHERE c.nombreTipo = :nombreTipo AND c.estadoRegistro = 1 ")
})
public class CmtTipoCompaniaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtTipoCompaniaMgl.cmtTipoCompaniaMglSq",
            sequenceName = "MGL_SCHEME.CMT_TIPO_COMPANIA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtTipoCompaniaMgl.cmtTipoCompaniaMglSq")
    @Column(name = "TIPO_COMPANIA_ID", nullable = false)
    private BigDecimal tipoCompaniaId;
    @Column(name = "NOMBRE_TIPO")
    private String nombreTipo;
    @Column(name = "CODIGO_TIPO")
    private String codigoTipo;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    /**
     * @return the tipoCompaniaId
     */
    public BigDecimal getTipoCompaniaId() {
        return tipoCompaniaId;
    }

    /**
     * @param tipoCompaniaId the tipoCompaniaId to set
     */
    public void setTipoCompaniaId(BigDecimal tipoCompaniaId) {
        this.tipoCompaniaId = tipoCompaniaId;
    }

    /**
     * @return the nombreTipo
     */
    public String getNombreTipo() {
        return nombreTipo;
    }

    /**
     * @param nombreTipo the nombreTipo to set
     */
    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    /**
     * @return the codigoTipo
     */
    public String getCodigoTipo() {
        return codigoTipo;
    }

    /**
     * @param codigoTipo the codigoTipo to set
     */
    public void setCodigoTipo(String codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the usuarioCreacion
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion the usuarioCreacion to set
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the perfilCreacion
     */
    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    /**
     * @param perfilCreacion the perfilCreacion to set
     */
    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    /**
     * @return the fechaEdicion
     */
    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    /**
     * @param fechaEdicion the fechaEdicion to set
     */
    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    /**
     * @return the usuarioEdicion
     */
    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    /**
     * @param usuarioEdicion the usuarioEdicion to set
     */
    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    /**
     * @return the perfilEdicion
     */
    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    /**
     * @param perfilEdicion the perfilEdicion to set
     */
    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    /**
     * @return the estadoRegistro
     */
    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    /**
     * @param estadoRegistro the estadoRegistro to set
     */
    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
    @Override
    public String toString(){
        return "["+tipoCompaniaId.toString()+"]"+nombreTipo;
    }

}
