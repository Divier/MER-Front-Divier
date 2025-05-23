package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_HORARIO_RESTRICCION", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtHorarioRestriccionMgl.findAll", query = "SELECT c FROM CmtHorarioRestriccionMgl c "),
    @NamedQuery(name = "CmtHorarioRestriccionMgl.findById", query = "SELECT c FROM CmtHorarioRestriccionMgl c WHERE c.horRestriccionId = :HOR_RESTRICCION_ID"),
    @NamedQuery(name = "CmtHorarioRestriccionMgl.findByCompania", query = "SELECT c FROM CmtHorarioRestriccionMgl c WHERE c.companiaObj = :compania AND c.estadoRegistro= :estadoRegistro "),
    @NamedQuery(name = "CmtHorarioRestriccionMgl.findBySolicitud", query = "SELECT c FROM CmtHorarioRestriccionMgl c WHERE c.solicitudCm = :solicitudCm AND c.estadoRegistro= :estadoRegistro "),
    @NamedQuery(name = "CmtHorarioRestriccionMgl.deleteBySolicitud", query = "UPDATE CmtHorarioRestriccionMgl c SET c.estadoRegistro= :estadoRegistro,c.fechaEdicion= :fechaEdicion,c.usuarioEdicion= :usuarioEdicion,c.perfilEdicion= :perfilEdicion  WHERE  c.solicitudCm = :solicitudCm "),
    @NamedQuery(name = "CmtHorarioRestriccionMgl.findByVt", query = "SELECT c FROM CmtHorarioRestriccionMgl c WHERE c.vt = :vt AND c.estadoRegistro= :estadoRegistro "),
    @NamedQuery(name = "CmtHorarioRestriccionMgl.deleteByVt", query = "UPDATE CmtHorarioRestriccionMgl c SET c.estadoRegistro= :estadoRegistro,c.fechaEdicion= :fechaEdicion,c.usuarioEdicion= :usuarioEdicion,c.perfilEdicion= :perfilEdicion  WHERE  c.vt = :vt ")
})
public class CmtHorarioRestriccionMgl implements Serializable {

    private static final long serialVersionUID = 8976543212323212L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "CmtHorarioRestriccionMgl.CmtHorarioRestriccionMglSq",
            sequenceName = "MGL_SCHEME.CMT_HORARIO_RESTRICCION_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtHorarioRestriccionMgl.CmtHorarioRestriccionMglSq")
    @Column(name = "HOR_RESTRICCION_ID", nullable = false)
    private BigDecimal horRestriccionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID")
    private CmtCompaniaMgl companiaObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLICITUD_CM_ID")
    private CmtSolicitudCmMgl solicitudCm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VT_ID")
    private CmtVisitaTecnicaMgl vt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUENTAMATRIZ_ID", nullable = false)
    private CmtCuentaMatrizMgl cuentaMatrizObj;
    @Column(name = "DIA_INICIO")
    @Enumerated(EnumType.STRING)
    private DayOfWeek diaInicio;
    @Column(name = "DIA_FIN")
    @Enumerated(EnumType.STRING)
    private DayOfWeek diaFin;
    @Column(name = "DIRECCION", nullable = true, length = 20)
    private String direccion;
    @Column(name = "HORA_INICIO", nullable = true, length = 20)
    private String horaInicio;
    @Column(name = "HORA_FIN", nullable = true, length = 20)
    private String horaFin;
    @Column(name = "RAZON_RESTRICCION", nullable = true, length = 20)
    private String razonRestriccion;
    @Column(name = "TIPO_RESTRICCION")
    @Enumerated(EnumType.STRING)
    private TipoRestriccion tipoRestriccion;    
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

    /**
     * @return the horRestriccionId
     */
    public BigDecimal getHorRestriccionId() {
        return horRestriccionId;
    }

    /**
     * @param horRestriccionId the horRestriccionId to set
     */
    public void setHorRestriccionId(BigDecimal horRestriccionId) {
        this.horRestriccionId = horRestriccionId;
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
     * @return the cuentaMatrizObj
     */
    public CmtCuentaMatrizMgl getCuentaMatrizObj() {
        return cuentaMatrizObj;
    }

    /**
     * @param cuentaMatrizObj the cuentaMatrizObj to set
     */
    public void setCuentaMatrizObj(CmtCuentaMatrizMgl cuentaMatrizObj) {
        this.cuentaMatrizObj = cuentaMatrizObj;
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
     * @return the horaInicio
     */
    public String getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * @return the horaFin
     */
    public String getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaFin the horaFin to set
     */
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * @return the razonRestriccion
     */
    public String getRazonRestriccion() {
        return razonRestriccion;
    }

    /**
     * @param razonRestriccion the razonRestriccion to set
     */
    public void setRazonRestriccion(String razonRestriccion) {
        this.razonRestriccion = razonRestriccion;
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

    public DayOfWeek getDiaInicio() {
        return diaInicio;
    }

    public void setDiaInicio(DayOfWeek diaInicio) {
        this.diaInicio = diaInicio;
    }

    public DayOfWeek getDiaFin() {
        return diaFin;
    }

    public void setDiaFin(DayOfWeek diaFin) {
        this.diaFin = diaFin;
    }

    public TipoRestriccion getTipoRestriccion() {
        return tipoRestriccion;
    }

    public void setTipoRestriccion(TipoRestriccion tipoRestriccion) {
        this.tipoRestriccion = tipoRestriccion;
    }

    public CmtSolicitudCmMgl getSolicitudCm() {
        return solicitudCm;
    }

    public void setSolicitudCm(CmtSolicitudCmMgl solicitudCm) {
        this.solicitudCm = solicitudCm;
    }

    public CmtVisitaTecnicaMgl getVt() {
        return vt;
    }

    public void setVt(CmtVisitaTecnicaMgl vt) {
        this.vt = vt;
    }

}
