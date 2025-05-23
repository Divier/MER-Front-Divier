/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "TEC_MARCAS", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarcasMgl.findAll", query = "SELECT m FROM MarcasMgl m WHERE m.estadoRegistro = 1 ORDER BY m.marCodigo ")})
public class MarcasMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "MarcasMgl.TEC_MARCAS_SQ",
            sequenceName = "MGL_SCHEME.TEC_MARCAS_SQ", allocationSize = 1)
    @GeneratedValue(generator = "MarcasMgl.TEC_MARCAS_SQ")
    @Column(name = "MARCAS_ID", nullable = false)
    private BigDecimal marId;
    @JoinColumn(name = "TIPO_MARCAS_ID", referencedColumnName = "TIPO_MARCAS_ID", nullable = true)
    @ManyToOne
    private TecTipoMarcas tmaId;
    @Column(name = "NOMBRE", nullable = false)
    private String marNombre;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date martFechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String marUsuarioCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date marFechaModificacion;
    @Column(name = "USUARIO_EDICION")
    private String marUsuarioModificacion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "CODIGO")
    private String marCodigo;

    public TecTipoMarcas getTmaId() {
        return tmaId;
    }

    public void setTmaId(TecTipoMarcas tmaId) {
        this.tmaId = tmaId;
    }

    public String getMarNombre() {
        return marNombre;
    }

    public void setMarNombre(String marNombre) {
        this.marNombre = marNombre;
    }

    public Date getMartFechaCreacion() {
        return martFechaCreacion;
    }

    public void setMartFechaCreacion(Date martFechaCreacion) {
        this.martFechaCreacion = martFechaCreacion;
    }

    public String getMarUsuarioCreacion() {
        return marUsuarioCreacion;
    }

    public void setMarUsuarioCreacion(String marUsuarioCreacion) {
        this.marUsuarioCreacion = marUsuarioCreacion;
    }

    public Date getMarFechaModificacion() {
        return marFechaModificacion;
    }

    public void setMarFechaModificacion(Date marFechaModificacion) {
        this.marFechaModificacion = marFechaModificacion;
    }

    public String getMarUsuarioModificacion() {
        return marUsuarioModificacion;
    }

    public void setMarUsuarioModificacion(String marUsuarioModificacion) {
        this.marUsuarioModificacion = marUsuarioModificacion;
    }

    public BigDecimal getMarId() {
        return marId;
    }

    public void setMarId(BigDecimal marId) {
        this.marId = marId;
    }

    public String getMarCodigo() {
        return marCodigo;
    }

    public void setMarCodigo(String marCodigo) {
        this.marCodigo = marCodigo;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }
    
}
