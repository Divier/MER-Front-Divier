package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <b>Entidad de mapeo</b><br/>
 * Clase que mapea la tabla <i><b>CMT_OPCIONES_ROL</b></i> de la base de datos
 *
 * @author wgavidia
 * @version 2017/09/20
 */
@Entity
@XmlRootElement
@Table(name = "MGL_OPCIONES_ROL", schema = "MGL_SCHEME")
public class CmtOpcionesRolMgl implements Serializable {

    @Id
    @Basic
    @SequenceGenerator(
            name = "CmtOpcionesRolMgl.CmtOpcionesRolMglSeq",
            sequenceName = "MGL_SCHEME.MGL_OPCIONES_ROL_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtOpcionesRolMgl.CmtOpcionesRolMglSeq")
    @Column(name = "ID_ROL_OPCION")
    private BigDecimal id;

    @Basic
    @Column(name = "FORMULARIO")
    private String formulario;

    @Basic
    @Column(name = "NOMBRE_FORMULARIO")
    private String nombreFormulario;

    @Basic
    @Column(name = "NOMBRE_OPCION")
    private String nombreOpcion;

    @Basic
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Basic
    @Column(name = "ROL")
    private String rol;

    public CmtOpcionesRolMgl() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getFormulario() {
        return formulario;
    }

    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }

    public String getNombreFormulario() {
        return nombreFormulario;
    }

    public void setNombreFormulario(String nombreFormulario) {
        this.nombreFormulario = nombreFormulario;
    }

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public void setNombreOpcion(String nombreOpcion) {
        this.nombreOpcion = nombreOpcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
