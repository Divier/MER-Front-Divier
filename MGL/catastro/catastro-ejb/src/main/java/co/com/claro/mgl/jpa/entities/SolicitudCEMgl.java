/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "MGL_SOLCITUD_CAMBIO_ESTRATO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitudCEMgl.findAll", query = "SELECT s FROM SolicitudCEMgl s")})
public class SolicitudCEMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
        name="SolicitudCEMgl.MGL_SOLCITUD_CAMBIO_ESTRATO_SQ",
        sequenceName="MGL_SCHEME.MGL_SOLCITUD_CAMBIO_ESTRATO_SQ", allocationSize = 1
    )
    @GeneratedValue(generator="SolicitudCEMgl.MGL_SOLCITUD_CAMBIO_ESTRATO_SQ" )
    @Column(name = "SOLCITUD_CAMBIO_ESTRATO_ID", nullable = false)
    private BigDecimal IdTmSolicitud;
    @Column(name = "USUARIO_CREADOR", nullable = false)
    private String UsuarioCreador;
    @Column(name = "PATH", nullable = false)
    private String Path;
    @Column(name = "FECHA_INGRESO", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date FechaIngreso;
    @Column(name = "IDSOLICITUD", nullable = false)
    private String IdSolicitud;

    public BigDecimal getIdTmSolicitud() {
        return IdTmSolicitud;
    }

    public void setIdTmSolicitud(BigDecimal IdTmSolicitud) {
        this.IdTmSolicitud = IdTmSolicitud;
    }

    public String getUsuarioCreador() {
        return UsuarioCreador;
    }

    public void setUsuarioCreador(String UsuarioCreador) {
        this.UsuarioCreador = UsuarioCreador;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String Path) {
        this.Path = Path;
    }

    public Date getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(Date FechaIngreso) {
        this.FechaIngreso = FechaIngreso;
    }

    public String getIdSolicitud() {
        return IdSolicitud;
    }

    public void setIdSolicitud(String IdSolicitud) {
        this.IdSolicitud = IdSolicitud;
    }
  
}