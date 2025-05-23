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
@Table(name = "TEC_NOTAS_ADICIONALES_TEC_HAB", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotasAdicionalesMgl.findAll", query = "SELECT s FROM NotasAdicionalesMgl s"),
    @NamedQuery(name = "NotasAdicionalesMgl.findNotasAdicionalesIdHhpp", query = "SELECT s FROM NotasAdicionalesMgl s WHERE s.HhppId = :hhppId ORDER BY s.FechaCreacion DESC")
})
public class NotasAdicionalesMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "NotasAdicionalesMgl.TEC_NOTAS_ADI_TEC_HAB_SQ",
            sequenceName = "MGL_SCHEME.TEC_NOTAS_ADI_TEC_HAB_SQ", allocationSize = 1)
    @GeneratedValue(generator = "NotasAdicionalesMgl.TEC_NOTAS_ADI_TEC_HAB_SQ")
    @Column(name = "NOTAS_ADICIONALES_TEC_HAB_ID", nullable = false)
    private BigDecimal Id;
    @Column(name = "IDSOLICITUD", nullable = false)
    private String IdSolicitud;
    @Column(name = "TECNOLOGIA_HABILITADA_ID", nullable = false)
    private String HhppId;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.DATE)
    private Date FechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String UsuarioCreacion;
    @Column(name = "FECHA_EDICION")
    private String FechaModificacion;
    @Column(name = "USUARIO_EDICION")
    private String UsuarioModificacion;
    @Column(name = "NOTA")
    private String Nota;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Transient
    private boolean selected = false;
    @Transient
    private String resultado;
    @Transient
    private String descripcion;

    public BigDecimal getId() {
        return Id;
    }

    public void setId(BigDecimal Id) {
        this.Id = Id;
    }

    public String getIdSolicitud() {
        return IdSolicitud;
    }

    public void setIdSolicitud(String IdSolicitud) {
        this.IdSolicitud = IdSolicitud;
    }

    public String getHhppId() {
        return HhppId;
    }

    public void setHhppId(String HhppId) {
        this.HhppId = HhppId;
    }

    public Date getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(Date FechaCreacion) {
        this.FechaCreacion = FechaCreacion;
    }

    public String getUsuarioCreacion() {
        return UsuarioCreacion;
    }

    public void setUsuarioCreacion(String UsuarioCreacion) {
        this.UsuarioCreacion = UsuarioCreacion;
    }

    public String getFechaModificacion() {
        return FechaModificacion;
    }

    public void setFechaModificacion(String FechaModificacion) {
        this.FechaModificacion = FechaModificacion;
    }

    public String getUsuarioModificacion() {
        return UsuarioModificacion;
    }

    public void setUsuarioModificacion(String UsuarioModificacion) {
        this.UsuarioModificacion = UsuarioModificacion;
    }

    public String getNota() {
        return Nota;
    }

    public void setNota(String Nota) {
        this.Nota = Nota;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

}
