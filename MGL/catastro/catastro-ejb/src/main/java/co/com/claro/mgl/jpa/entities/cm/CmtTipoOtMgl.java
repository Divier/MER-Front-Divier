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
 * Entidad mapeo tabla CMT_TIPOOT
 *
 * alejandro.martinez.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_TIPO_OT", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtTipoOtMgl.findAll", query = "SELECT c FROM CmtTipoOtMgl c"),
    @NamedQuery(name = "CmtTipoOtMgl.findByIdSubgrupo", query = "SELECT d FROM CmtTipoOtMgl d WHERE d.id_subgrupo = :subgrupoId and d.estadoRegistro=1 "),
    @NamedQuery(name = "CmtTipoOtMgl.findByBasicaId", query = "SELECT d FROM CmtTipoOtMgl d WHERE d.basicaIdTipoOt = :basicaIdTipoOt and d.estadoRegistro=1 "),
    @NamedQuery(name = "CmtTipoOtMgl.findByTipoTrabajoAndIsVT", query = "SELECT d FROM CmtTipoOtMgl d WHERE d.basicaIdTipoOt = :basicaIdTipoOt and d.esTipoVT='Y'  and d.estadoRegistro=1 "),
    @NamedQuery(name = "CmtTipoOtMgl.findByCodigoInternoApp", query = "SELECT d FROM CmtTipoOtMgl d WHERE d.identificadorInternoApp = :identificadorInternoApp and d.estadoRegistro=1 ")})
public class CmtTipoOtMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtTipoOtMgl.CmtTipoOtSq",
            sequenceName = "MGL_SCHEME.CMT_TIPO_OT_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtTipoOtMgl.CmtTipoOtSq")
    @Column(name = "TIPO_OT_ID")
    private BigDecimal idTipoOt;
    @Column(name = "DESCRIPCIONTIPO_OT", nullable = true, length = 100)
    private String descTipoOt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_FLUJO_OT")
    private CmtBasicaMgl tipoFlujoOt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO_INI_CM")
    private CmtBasicaMgl estadoIniCm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_OT")
    private CmtBasicaMgl basicaIdTipoOt;
    @Column(name = "OT_AGENDABLE", nullable = true, length = 1)
    private String otAgendable;
    @Column(name = "DETALLE", nullable = true, length = 200)
    private String detalle;
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
    @NotNull
    @Column(name = "ANS")
    private int ans;
    @NotNull
    @Column(name = "ANS_AVISO")
    private int ansAviso;
    @Column(name = "ID_SUBGRUPO")
    private BigDecimal id_subgrupo;
    @NotNull
    @Column(name = "FLAG_INV")
    private int flagInv;
    @Column(name = "ES_TIPO_VT", nullable = false, length = 1)
    private String esTipoVT;
    @Column(name = "OT_ACOMETIDA_A_GENERAR")
    private BigDecimal otAcoAGenerar;
    @Column(name = "IDENTIFICADOR_INTERNO_APP")
    private String identificadorInternoApp;
    @Column(name = "REQUIERE_ONYX")
    private String requiereOnyx;
    @Column(name = "ACTUALIZA_INFOTECNICA")
    private String actualizaInfoTec;
    
    public CmtTipoOtMgl() {
    }

    /**
     * @return the idTipoOt
     */
    public BigDecimal getIdTipoOt() {
        return idTipoOt;
    }

    /**
     * @param idTipoOt the idTipoOt to set
     */
    public void setIdTipoOt(BigDecimal idTipoOt) {
        this.idTipoOt = idTipoOt;
    }

    /**
     * @return the descTipoOt
     */
    public String getDescTipoOt() {
        return descTipoOt;
    }

    /**
     * @param descTipoOt the descTipoOt to set
     */
    public void setDescTipoOt(String descTipoOt) {
        this.descTipoOt = descTipoOt;
    }

    /**
     * @return the tipoFlujoOt
     */
    public CmtBasicaMgl getTipoFlujoOt() {
        return tipoFlujoOt;
    }

    /**
     * @param tipoFlujoOt the tipoFlujoOt to set
     */
    public void setTipoFlujoOt(CmtBasicaMgl tipoFlujoOt) {
        this.tipoFlujoOt = tipoFlujoOt;
    }

    /**
     * @return the estadoIniCm
     */
    public CmtBasicaMgl getEstadoIniCm() {
        return estadoIniCm;
    }

    /**
     * @param estadoIniCm the estadoIniCm to set
     */
    public void setEstadoIniCm(CmtBasicaMgl estadoIniCm) {
        this.estadoIniCm = estadoIniCm;
    }

    /**
     * @return the otAgendable
     */
    public String getOtAgendable() {
        return otAgendable;
    }

    /**
     * @param otAgendable the otAgendable to set
     */
    public void setOtAgendable(String otAgendable) {
        this.otAgendable = otAgendable;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
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

    /**
     * @return the ans
     */
    public int getAns() {
        return ans;
    }

    /**
     * @param ans the ans to set
     */
    public void setAns(int ans) {
        this.ans = ans;
    }

    /**
     * @return the ansAviso
     */
    public int getAnsAviso() {
        return ansAviso;
    }

    /**
     * @param ansAviso the ansAviso to set
     */
    public void setAnsAviso(int ansAviso) {
        this.ansAviso = ansAviso;
    }

    @Override
    public String toString() {
        return "[" + idTipoOt + "]" + descTipoOt;
    }

    public BigDecimal getId_subgrupo() {
        return id_subgrupo;
    }

    public void setId_subgrupo(BigDecimal id_subgrupo) {
        this.id_subgrupo = id_subgrupo;
    }


    public int getFlagInv() {
        return flagInv;
    }

    public void setFlagInv(int flagInv) {
        this.flagInv = flagInv;
    }

    public CmtBasicaMgl getBasicaIdTipoOt() {
        return basicaIdTipoOt;
    }

    public void setBasicaIdTipoOt(CmtBasicaMgl basicaIdTipoOt) {
        this.basicaIdTipoOt = basicaIdTipoOt;
    }

    public String getEsTipoVT() {
        return esTipoVT;
    }

    public void setEsTipoVT(String esTipoVT) {
        this.esTipoVT = esTipoVT;
    }

    public BigDecimal getOtAcoAGenerar() {
        return otAcoAGenerar;
    }

    public void setOtAcoAGenerar(BigDecimal otAcoAGenerar) {
        this.otAcoAGenerar = otAcoAGenerar;
    }

    public String getIdentificadorInternoApp() {
        return identificadorInternoApp;
    }

    public void setIdentificadorInternoApp(String identificadorInternoApp) {
        this.identificadorInternoApp = identificadorInternoApp;
    }

    public String getRequiereOnyx() {
        return requiereOnyx;
    }

    public void setRequiereOnyx(String requiereOnyx) {
        this.requiereOnyx = requiereOnyx;
    }

    public String getActualizaInfoTec() {
        return actualizaInfoTec;
    }

    public void setActualizaInfoTec(String actualizaInfoTec) {
        this.actualizaInfoTec = actualizaInfoTec;
    }
}
