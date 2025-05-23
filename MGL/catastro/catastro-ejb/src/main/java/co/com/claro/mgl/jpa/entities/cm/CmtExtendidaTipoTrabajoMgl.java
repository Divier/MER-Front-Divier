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
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "CMT_EXTENDIDA_TIPO_TRABAJO" , schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
   @NamedQuery(name = "CmtExtendidaTipoTrabajoMgl.findAll", query = "SELECT c FROM CmtExtendidaTipoTrabajoMgl c WHERE c.estadoRegistro = 1 ORDER BY c.comunidadRrObj.nombreComunidad ASC "),
    @NamedQuery(name = "CmtExtendidaTipoTrabajoMgl.findBytipoTrabajoObj",
            query = "SELECT c FROM CmtExtendidaTipoTrabajoMgl c WHERE c.tipoTrabajoObj = :tipoTrabajoObj  "
            + "AND c.estadoRegistro = 1 ORDER BY c.comunidadRrObj.nombreComunidad ASC"),
    @NamedQuery(name = "CmtExtendidaTipoTrabajoMgl.getCountBytipoTrabajoObj", query = "SELECT COUNT(DISTINCT c) FROM   "
        + " CmtExtendidaTipoTrabajoMgl c  WHERE c.tipoTrabajoObj = :tipoTrabajoObj AND c.estadoRegistro = 1 "),
    @NamedQuery(name = "CmtExtendidaTipoTrabajoMgl.findBytipoTrabajoObjAndCom",  
        query = "SELECT c FROM CmtExtendidaTipoTrabajoMgl c WHERE c.tipoTrabajoObj = :tipoTrabajoObj AND c.comunidadRrObj = :comunidadRrObj  AND c.estadoRegistro = 1 ORDER BY c.comunidadRrObj.nombreComunidad ASC")
})

public class CmtExtendidaTipoTrabajoMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtExtendidaTipoTrabajoMgl.CmtExtendidaTipoTrabajoMglSq",
            sequenceName = "MGL_SCHEME.CMT_EXTENDIDA_TIPO_TRABAJO_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtExtendidaTipoTrabajoMgl.CmtExtendidaTipoTrabajoMglSq")
    @Column(name = "ID_EXTENDIDA_TIP_TRABAJO")
    private BigDecimal idExtTipTra;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_TRABAJO", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl tipoTrabajoObj;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMUNIDAD_RR_ID", referencedColumnName = "COMUNIDAD_RR_ID", nullable = false)
    private CmtComunidadRr comunidadRrObj;
    @Column(name = "LOCATION_CODIGO")
    private String locationCodigo;
    @NotNull
    @Column(name = "ESTADO_REGISTRO", columnDefinition = "default 1")
    private int estadoRegistro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Basic(optional = false)
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "TECNICO_ANTICIPADO")
    private String tecnicoAnticipado;
    @Column(name = "AGENDA_INMEDIATA")
    private String agendaInmediata;


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

    public BigDecimal getIdExtTipTra() {
        return idExtTipTra;
    }

    public void setIdExtTipTra(BigDecimal idExtTipTra) {
        this.idExtTipTra = idExtTipTra;
    }

    public CmtBasicaMgl getTipoTrabajoObj() {
        return tipoTrabajoObj;
    }

    public void setTipoTrabajoObj(CmtBasicaMgl tipoTrabajoObj) {
        this.tipoTrabajoObj = tipoTrabajoObj;
    }

    public String getLocationCodigo() {
        return locationCodigo;
    }

    public void setLocationCodigo(String locationCodigo) {
        this.locationCodigo = locationCodigo;
    }

    public CmtComunidadRr getComunidadRrObj() {
        return comunidadRrObj;
    }

    public void setComunidadRrObj(CmtComunidadRr comunidadRrObj) {
        this.comunidadRrObj = comunidadRrObj;
    }

    public String getTecnicoAnticipado() {
        return tecnicoAnticipado;
    }

    public void setTecnicoAnticipado(String tecnicoAnticipado) {
        this.tecnicoAnticipado = tecnicoAnticipado;
    }

    public String getAgendaInmediata() {
        return agendaInmediata;
    }

    public void setAgendaInmediata(String agendaInmediata) {
        this.agendaInmediata = agendaInmediata;
    }
}
