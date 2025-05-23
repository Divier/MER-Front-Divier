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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad mapeo tabla CMT_RESPONSABLES
 *
 * alejandro.martinez.ext@claro.com.co
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_RESPONSABLES",schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtGaCoordinadorEspecialistaMgl.findAll", query = "SELECT c FROM CmtGaCoordinadorEspecialistaMgl c")})
public class CmtGaCoordinadorEspecialistaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtGaCoordinadorEspecialistaMgl.CmtGaCoordinadorEspecialistaMglSq",
            sequenceName = "MGL_SCHEME.CMT_GA_COOR_ESPECIAL_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtGaCoordinadorEspecialistaMgl.CmtGaCoordinadorEspecialistaMglSq")
    @Column(name = "GA_COOR_ESPECIAL_ID")
    private BigDecimal gaCordinadorEspecialistaId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COORDINADOR_ID")
    private CmtGaCoordinadorEspecialistaMgl cmtGaCoordinadorObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_CARGO")
    private CmtBasicaMgl cargoObj;
    @Column(name = "NOMBRE", nullable = true, length = 50)
    private String nombre;
    @Column(name = "APELLIDO1", nullable = true, length = 20)
    private String primerApellido;
    @Column(name = "APELLIDO2", nullable = true, length = 20)
    private String segundoApellido;
    @Column(name = "DIVICION")
    private String divicion;
    @Column(name = "NUMERO_CONTACTO", nullable = true, length = 20)
    private String numeroContacto;
    @Column(name = "CORREO_CONTACTO", nullable = true, length = 200)
    private String correoContacto;
    @Column(name = "CELUAR_CONTACTO", nullable = true, length = 20)
    private String celularContacto;
    @Column(name = "REDAMESTRA_ID", nullable = true, length = 20)
    private String idRedMaestra;
    @Column(name = "RR_ID", nullable = true, length = 20)
    private String idRr;
    @Column(name = "USUARIO_AGENDAMIENTO", nullable = true, length = 20)
    private String usuarioAgendamiento;
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

    public BigDecimal getGaCordinadorEspecialistaId() {
        return gaCordinadorEspecialistaId;
    }

    public void setGaCordinadorEspecialistaId(BigDecimal gaCordinadorEspecialistaId) {
        this.gaCordinadorEspecialistaId = gaCordinadorEspecialistaId;
    }

    public CmtGaCoordinadorEspecialistaMgl getCmtGaCoordinadorObj() {
        return cmtGaCoordinadorObj;
    }

    public void setCmtGaCoordinadorObj(CmtGaCoordinadorEspecialistaMgl cmtGaCoordinadorObj) {
        this.cmtGaCoordinadorObj = cmtGaCoordinadorObj;
    }


    public CmtBasicaMgl getCargoObj() {
        return cargoObj;
    }

    public void setCargoObj(CmtBasicaMgl cargoObj) {
        this.cargoObj = cargoObj;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getDivicion() {
        return divicion;
    }

    public void setDivicion(String divicion) {
        this.divicion = divicion;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public String getCelularContacto() {
        return celularContacto;
    }

    public void setCelularContacto(String celularContacto) {
        this.celularContacto = celularContacto;
    }

    public String getIdRedMaestra() {
        return idRedMaestra;
    }

    public void setIdRedMaestra(String idRedMaestra) {
        this.idRedMaestra = idRedMaestra;
    }

    public String getIdRr() {
        return idRr;
    }

    public void setIdRr(String idRr) {
        this.idRr = idRr;
    }

    public String getUsuarioAgendamiento() {
        return usuarioAgendamiento;
    }

    public void setUsuarioAgendamiento(String usuarioAgendamiento) {
        this.usuarioAgendamiento = usuarioAgendamiento;
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
