
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Parzifal de León
 */
@Entity
@Table(name = "CMT_ESTABLECIMIENTO_CM", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtEstablecimientoCmMgl.findAll", query = "SELECT c FROM CmtEstablecimientoCmMgl c "),
    @NamedQuery(name = "CmtEstablecimientoMglDaoImpl.finById", query = "SELECT c FROM CmtEstablecimientoCmMgl c WHERE c.estadoRegistro=1 AND c.establecimientoId= :establesimientoId"),
    @NamedQuery(name = "CmtEstablecimientosMglDaoImpl.finBySubEdificio", query = "SELECT c FROM CmtEstablecimientoCmMgl c WHERE c.estadoRegistro=1 AND c.subEdificioObj= :subEdificioObj")
})
public class CmtEstablecimientoCmMgl implements Serializable {

    private static final long serialVersionUID = 8976543212323212L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "CmtEstablecimientoCmMgl.CmtEstablecimientoCmSq",
            sequenceName = "MGL_SCHEME.CMT_ESTABLECIMIENTO_CM_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtEstablecimientoCmMgl.CmtEstablecimientoCmSq")
    @Column(name = "ESTABLECIMIENTO_ID", nullable = false)
    private BigDecimal establecimientoId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID")
    private CmtCompaniaMgl companiaObj;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_ESTB")
    private CmtBasicaMgl tipoEstbObj;
    @Column(name = "CONTACTO", nullable = false, length = 100)
    private String contacto;
    @Column(name = "TELEFONO", nullable = false, length = 20)
    private String telefono;
    @Column(name = "EMAIL", nullable = false, length = 200)
    private String email;
    @Column(name = "DIRECCION", nullable = false, length = 100)
    private String direccion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID")
    private CmtSubEdificioMgl subEdificioObj;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GEOGRAFICO_PO_ID_DEPARTAMENTO")
    private GeograficoPoliticoMgl departamentoObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GEOGRAFICO_PO_ID_CIUDAD")
    private GeograficoPoliticoMgl ciudadObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GEOGRAFICO_PO_ID_POBLADO")
    private GeograficoPoliticoMgl centroPobladoObj;
    @Column(name = "BARRIO", nullable = true, length = 50)
    private String barrio;
    @OneToMany(mappedBy = "establecimientoObj", fetch = FetchType.LAZY)
    List<CmtEstablecimientoCmAuditoriaMgl> listAuditoria;  

    /**
     * @return the establecimientoId
     */
    public BigDecimal getEstablecimientoId() {
        return establecimientoId;
    }

    /**
     * @param establecimientoId the establecimientoId to set
     */
    public void setEstablecimientoId(BigDecimal establecimientoId) {
        this.establecimientoId = establecimientoId;
    }

    /**
     * @return the companiaObj
     */
    public CmtCompaniaMgl getCompaniaObj() {
        return companiaObj;
    }

    /**
     * @param companiaObj the companiaObj to set
     */
    public void setCompaniaObj(CmtCompaniaMgl companiaObj) {
        this.companiaObj = companiaObj;
    }

    /**
     * @return the tipoEstbObj
     */
    public CmtBasicaMgl getTipoEstbObj() {
        return tipoEstbObj;
    }

    /**
     * @param tipoEstbObj the tipoEstbObj to set
     */
    public void setTipoEstbObj(CmtBasicaMgl tipoEstbObj) {
        this.tipoEstbObj = tipoEstbObj;
    }

    /**
     * @return the contacto
     */
    public String getContacto() {
        return contacto;
    }

    /**
     * @param contacto the contacto to set
     */
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the subEdificioObj
     */
    public CmtSubEdificioMgl getSubEdificioObj() {
        return subEdificioObj;
    }

    /**
     * @param subEdificioObj the subEdificioObj to set
     */
    public void setSubEdificioObj(CmtSubEdificioMgl subEdificioObj) {
        this.subEdificioObj = subEdificioObj;
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

    public GeograficoPoliticoMgl getDepartamentoObj() {
        return departamentoObj;
    }

    public void setDepartamentoObj(GeograficoPoliticoMgl departamentoObj) {
        this.departamentoObj = departamentoObj;
    }

    public GeograficoPoliticoMgl getCiudadObj() {
        return ciudadObj;
    }

    public void setCiudadObj(GeograficoPoliticoMgl ciudadObj) {
        this.ciudadObj = ciudadObj;
    }

    public GeograficoPoliticoMgl getCentroPobladoObj() {
        return centroPobladoObj;
    }

    public void setCentroPobladoObj(GeograficoPoliticoMgl centroPobladoObj) {
        this.centroPobladoObj = centroPobladoObj;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public List<CmtEstablecimientoCmAuditoriaMgl> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<CmtEstablecimientoCmAuditoriaMgl> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }
    
}