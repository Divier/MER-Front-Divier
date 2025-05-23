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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "TEC_MARCAS_TECNOLOGIA_HAB", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarcasHhppMgl.findAll", 
            query = "SELECT m FROM MarcasHhppMgl m WHERE m.estadoRegistro = 1 "),
    @NamedQuery(name = "MarcasHhppMgl.findByIdMarcasHhppMgl", 
            query = "SELECT m FROM MarcasHhppMgl m WHERE m.marId = :marId AND m.estadoRegistro = 1"),
    @NamedQuery(name = "MarcasHhppMgl.findByMarcasHhppMglIdHhpp", 
            query = "SELECT m FROM MarcasHhppMgl m WHERE m.hhpp.hhpId = :hhpId AND m.estadoRegistro = 1"),
    @NamedQuery(name = "MarcasHhppMgl.findByMarcasHhppMglIdHhppSinEstado", 
            query = "SELECT m FROM MarcasHhppMgl m WHERE m.hhpp.hhpId IN :hhpId AND m.estadoRegistro = 1"),
    @NamedQuery(name = "MarcasHhppMgl.buscarMarcasHhppMglIdHhpp", 
            query = "SELECT m FROM MarcasHhppMgl m WHERE m.hhpp.hhpId =:hhpId and m.marId.marId =:marid ")
    })
public class MarcasHhppMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "MarcasHhppMgl.TEC_MARCAS_TECNOLOGIA_HAB_SQ",
            sequenceName = "MGL_SCHEME.TEC_MARCAS_TECNOLOGIA_HAB_SQ", allocationSize = 1)
    @GeneratedValue(generator = "MarcasHhppMgl.TEC_MARCAS_TECNOLOGIA_HAB_SQ")
    @Column(name = "MARCAS_TECNOLOGIA_HAB_ID", nullable = false)
    private BigDecimal mhhId;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date mhhFechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String mhhUsuarioCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date mhhFechaModificacion;
    @Column(name = "USUARIO_EDICION")
    private String mhhUsuarioModificacion;    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "TECNOLOGIA_HABILITADA_ID", nullable = false)
    private HhppMgl hhpp;    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARCAS_ID")
    private MarcasMgl marId;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "OBSERVACIONES")
    private String observaciones;

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public BigDecimal getMhhId() {
        return mhhId;
    }

    public void setMhhId(BigDecimal mhhId) {
        this.mhhId = mhhId;
    }

    public Date getMhhFechaCreacion() {
        return mhhFechaCreacion;
    }

    public void setMhhFechaCreacion(Date mhhFechaCreacion) {
        this.mhhFechaCreacion = mhhFechaCreacion;
    }

    public String getMhhUsuarioCreacion() {
        return mhhUsuarioCreacion;
    }

    public void setMhhUsuarioCreacion(String mhhUsuarioCreacion) {
        this.mhhUsuarioCreacion = mhhUsuarioCreacion;
    }

    public Date getMhhFechaModificacion() {
        return mhhFechaModificacion;
    }

    public void setMhhFechaModificacion(Date mhhFechaModificacion) {
        this.mhhFechaModificacion = mhhFechaModificacion;
    }

    public String getMhhUsuarioModificacion() {
        return mhhUsuarioModificacion;
    }

    public void setMhhUsuarioModificacion(String mhhUsuarioModificacion) {
        this.mhhUsuarioModificacion = mhhUsuarioModificacion;
    }

    public HhppMgl getHhpp() {
        return hhpp;
    }

    public void setHhpp(HhppMgl hhpp) {
        this.hhpp = hhpp;
    }

    public MarcasMgl getMarId() {
        return marId;
    }

    public void setMarId(MarcasMgl marId) {
        this.marId = marId;
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
    
    @Override
    public MarcasHhppMgl clone() throws CloneNotSupportedException {
        return (MarcasHhppMgl) super.clone();
    }

}
