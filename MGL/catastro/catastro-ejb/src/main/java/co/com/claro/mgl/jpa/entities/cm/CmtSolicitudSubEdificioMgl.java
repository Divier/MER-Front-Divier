package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_SOLICITUD_SUBEDIFICIO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtSolicitudSubEdificioMgl.findAll", query = "SELECT c FROM CmtSolicitudSubEdificioMgl c "),
    @NamedQuery(name = "CmtSolicitudSubEdificioMgl.findBySolicitud", query = "SELECT c FROM CmtSolicitudSubEdificioMgl c WHERE c.solicitudCMObj = :solicitudCM")
})
public class CmtSolicitudSubEdificioMgl implements Serializable,Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtSolicitudSubEdificioMgl.CmtSolicitudSubEdificioMglSq",
            sequenceName = "MGL_SCHEME.CMT_SOLICITUD_SUBEDIFICIO_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtSolicitudSubEdificioMgl.CmtSolicitudSubEdificioMglSq")
    @Column(name = "SOLICITUD_SUBEDIFICIO_ID", nullable = false)
    private BigDecimal solSubEdificioId;
    @Column(name = "NOMBRE_SUBEDIFICIO")
    private String nombreSubedificio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO_SUBEDIFICIO")
    private CmtBasicaMgl estadoSubEdificioObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID_CONSTRUCTORA")
    private CmtCompaniaMgl companiaConstructoraObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_ACOMETIDA")
    private CmtBasicaMgl tipoAcometidaObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID_ADMINISTRACION")
    private CmtCompaniaMgl companiaAdministracionObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID_ASCENSOR")
    private CmtCompaniaMgl companiaAscensorObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NODO_ID")
    private NodoMgl nodoObj;
    @Column(name = "FECHA_ENTREGA_EDIFICIO")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEntregaEdificio;
    @Column(name = "ADMINISTRADOR")
    private String administrador;
    @Column(name = "UNIDADES_VT")
    private int unidadesVt;
    @Column(name = "UNIDADES")
    private int unidades;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "TELEFONO_PORTERIA")
    private String telefonoPorteria;
    @Column(name = "TELEFONO_PORTERIA2")
    private String telefonoPorteria2;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_EDIFICIO")
    private CmtBasicaMgl tipoEdificioObj;

    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    @Column(name = "DIRECCION")
    private String direccion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLICITUD_CM_ID")
    private CmtSolicitudCmMgl solicitudCMObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID")
    private CmtSubEdificioMgl subEdificioObj;

    @Column(name = "SOL_MOD_COBERTURA")
    private short solModCobertura;
    @Column(name = "SOL_ELIMINACION")
    private short solModEliminacion;
    @Column(name = "SOL_MOD_DATOS")
    private short solModDatos;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NODO_DISENO_ID")
    private NodoMgl nodoDisenoObj;
    @Column(name = "CONTACTO_ASCENSORES")
    private String contactoAcsensores;
    @Column(name = "CONTACTO_CONSTRUCTORA")
    private String contactoConstructora;

    @OneToOne(cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECCION_SOL_ID")
    private CmtDireccionSolicitudMgl direccionSolicitudObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTRATO")
    private CmtBasicaMgl estratoSubEdificioObj;

    @Transient
    private boolean seleccionado;
    @Transient
    private boolean solicitudEliminar;
    @Transient
    private boolean solicitudModificacion;
    @Transient
    private boolean solicitudModificacionCobertura;
    @Transient
    private boolean checkModNombreSubedificio;
    @Transient
    private boolean checkModEstadoSubEdificioObj;
    @Transient
    private boolean checkModCompaniaConstructoraObj;
    @Transient
    private boolean checkModCompaniaAdministracionObj;
    @Transient
    private boolean checkModCompaniaAscensorObj;
    @Transient
    private boolean checkModNodoObj;
    @Transient
    private boolean checkModFechaEntregaEdificio;
    @Transient
    private boolean checkModAdministrador;
    @Transient
    private boolean checkModTelefonoPorteria;
    @Transient
    private boolean checkModTelefonoPorteria2;
    @Transient
    private boolean checkModTipoEdificioObj;
    @Transient
    private boolean checkModDireccion;
    @Transient
    private boolean otroNodoSolicitud;
    @Transient
    private boolean edificioGeneral;
    @Transient
    private NodoMgl nodoDisGestion;
    
    
    private static final Logger LOGGER = LogManager.getLogger(CmtSolicitudSubEdificioMgl.class);

     public CmtSubEdificioMgl mapearCamposCmtSubEdificioMglMod() {
        CmtSubEdificioMgl modSe;
        if (this.getSubEdificioObj() != null) {
            modSe = this.getSubEdificioObj();
        } else {
            modSe = new CmtSubEdificioMgl();
        }

        modSe.setNombreSubedificio(this.getNombreSubedificio() == null ? modSe.getNombreSubedificio() : this.getNombreSubedificio());
        modSe.setTipoEdificioObj(this.getTipoEdificioObj() == null ? modSe.getTipoEdificioObj() : this.getTipoEdificioObj());
        modSe.setTelefonoPorteria(this.getTelefonoPorteria() == null ? modSe.getTelefonoPorteria() : this.getTelefonoPorteria());
        modSe.setTelefonoPorteria2(this.getTelefonoPorteria2() == null ? modSe.getTelefonoPorteria2() : this.getTelefonoPorteria2());
        modSe.setAdministrador(this.getAdministrador() == null ? modSe.getAdministrador() : this.getAdministrador());
        modSe.setCompaniaAdministracionObj(this.getCompaniaAdministracionObj() == null ? modSe.getCompaniaAdministracionObj() : this.getCompaniaAdministracionObj());
        modSe.setCompaniaAscensorObj(this.getCompaniaAscensorObj() == null ? modSe.getCompaniaAscensorObj() : this.getCompaniaAscensorObj());
        modSe.setFechaEntregaEdificio(this.getFechaEntregaEdificio() == null ? modSe.getFechaEntregaEdificio() : this.getFechaEntregaEdificio());
        modSe.setNodoObj(this.getNodoObj() == null ? modSe.getNodoObj() : this.getNodoObj());
        modSe.setEstadoSubEdificioObj(this.getEstadoSubEdificioObj() == null ? modSe.getEstadoSubEdificioObj() : this.getEstadoSubEdificioObj());
        modSe.setEstadoRegistro(this.getSolModEliminacion() == 1 ? 0 : 1);

        return modSe;
    }

    public CmtSubEdificioMgl mapearCamposCmtSubEdificioMglNuevo() {
        CmtSubEdificioMgl modSe = new CmtSubEdificioMgl();
        if (this.getSubEdificioObj() != null) {
            modSe = this.getSubEdificioObj();
        }

        modSe.setNombreSubedificio(this.getNombreSubedificio());
        modSe.setTipoEdificioObj(this.getTipoEdificioObj());
        modSe.setTelefonoPorteria(this.getTelefonoPorteria());
        modSe.setTelefonoPorteria2(this.getTelefonoPorteria2());
        modSe.setEstadoSubEdificioObj(this.getEstadoSubEdificioObj());
        modSe.setAdministrador(this.getAdministrador());
        modSe.setCompaniaAdministracionObj(this.getCompaniaAdministracionObj());
        modSe.setCompaniaAscensorObj(this.getCompaniaAscensorObj());
        modSe.setFechaEntregaEdificio(this.getFechaEntregaEdificio());
        return modSe;
    }

    public boolean existeSolSubEdiEliminar(List<CmtSolicitudSubEdificioMgl> list) {
        for (CmtSolicitudSubEdificioMgl sol : list) {
            if (sol.getSubEdificioObj() != null && sol.getSubEdificioObj().getSubEdificioId() != null && sol.getSubEdificioObj().getSubEdificioId().equals(this.getSubEdificioObj().getSubEdificioId())) {
                if (sol.isSolicitudEliminar()) {
                    return true;
                }
            }
        }
        return false;
    }

    public CmtSolicitudSubEdificioMgl obtenerSolCobertura(List<CmtSolicitudSubEdificioMgl> list) {
        CmtSolicitudSubEdificioMgl resultado = null;
        for (CmtSolicitudSubEdificioMgl sol : list) {
            if (sol.getSubEdificioObj() != null && sol.getSubEdificioObj().getSubEdificioId() != null && sol.getSubEdificioObj().getSubEdificioId().compareTo(getSubEdificioObj().getSubEdificioId()) == 0) {
                return sol;
            }
        }
        return resultado;
    }

    public List<CmtSolicitudSubEdificioMgl> removerSolicitudSubedificio(List<CmtSolicitudSubEdificioMgl> list) {
        CmtSolicitudSubEdificioMgl cmtSolSubEdi;
        for (int i = 0; i < list.size(); i++) {
            cmtSolSubEdi = list.get(i);
            if (cmtSolSubEdi.getSubEdificioObj() != null && cmtSolSubEdi.getSubEdificioObj().getSubEdificioId() != null && cmtSolSubEdi.getSubEdificioObj().getSubEdificioId().equals(this.getSubEdificioObj().getSubEdificioId())) {
                list.remove(i);
            }
        }
        return list;
    }

    public boolean validarHayCambioSubedificio() {

        if (this.getNodoObj().getNodId() != null) {
            return true;
        }
        if (this.getNodoObj().getNodCodigo() != null) {
            return true;
        }
        if (this.getNombreSubedificio() != null && !"".equals(this.getNombreSubedificio())) {
            return true;
        }
        if (this.getTelefonoPorteria() != null && !"".equals(this.getTelefonoPorteria())) {
            return true;
        }
         if (this.getTelefonoPorteria2()!= null && !"".equals(this.getTelefonoPorteria2())) {
            return true;
        }
        if (this.getAdministrador() != null && !"".equals(this.getAdministrador())) {
            return true;
        }
        if (this.getDireccion() != null && !"".equals(this.getDireccion())) {
            return true;
        }
        if (this.getFechaEntregaEdificio() != null) {
            return true;
        }
        if (this.getTipoEdificioObj().getBasicaId() != null) {
            return true;
        }
        if (this.getCompaniaAdministracionObj().getCompaniaId() != null) {
            return true;
        }
        if (this.getCompaniaAscensorObj().getCompaniaId() != null) {
            return true;
        }
        if (this.getEstadoSubEdificioObj() != null) {
            return true;
        }
        if (this.getDireccionSolicitudObj() != null && (this.getDireccionSolicitudObj().getDirFormatoIgac() != null || "".equals(this.getDireccionSolicitudObj().getDirFormatoIgac()))) {
            return true;
        }

        return false;
    }

    public CmtSolicitudSubEdificioMgl limpiarObjetosVaciosSubedificio() {

        if (this.getNodoObj() == null || this.getNodoObj().getNodCodigo() == null) {
            this.setNodoObj(null);
        }
        if (this.getNodoDisenoObj() == null || this.getNodoDisenoObj().getNodCodigo() == null) {
            this.setNodoDisenoObj(null);
        }
        if (this.getTipoEdificioObj() == null || this.getTipoEdificioObj().getBasicaId() == null) {
            this.setTipoEdificioObj(null);
        }
        if (this.getEstadoSubEdificioObj() == null || this.getEstadoSubEdificioObj().getBasicaId() == null) {
            this.setEstadoSubEdificioObj(null);
        }
        if (this.getCompaniaAdministracionObj() == null || this.getCompaniaAdministracionObj().getCompaniaId() == null) {
            this.setCompaniaAdministracionObj(null);
        }
        if (this.getCompaniaAscensorObj() == null || this.getCompaniaAscensorObj().getCompaniaId() == null) {
            this.setCompaniaAscensorObj(null);
        }
        if (this.getDireccionSolicitudObj() == null || this.getDireccionSolicitudObj().getDirFormatoIgac() == null || "".equals(this.getDireccionSolicitudObj().getDirFormatoIgac())) {
            this.setDireccionSolicitudObj(null);
        }

        return this;
    }

    @Override
    public CmtSolicitudSubEdificioMgl clone() throws CloneNotSupportedException {
        CmtSolicitudSubEdificioMgl obj = null;
        try {
            obj = (CmtSolicitudSubEdificioMgl) super.clone();
        } catch (CloneNotSupportedException ex) {
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			LOGGER.error(msg);
            throw ex;
        }

        return obj;
    }

    public String mostrarOtroNodoSolicitud() {
        otroNodoSolicitud = !otroNodoSolicitud;
        return null;
    }

    /**
     * @return the nombreSubedificio
     */
    public String getNombreSubedificio() {
        return nombreSubedificio;
    }

    /**
     * @param nombreSubedificio the nombreSubedificio to set
     */
    public void setNombreSubedificio(String nombreSubedificio) {
        this.nombreSubedificio = nombreSubedificio;
    }

    /**
     * @return the estadoSubEdificioObj
     */
    public CmtBasicaMgl getEstadoSubEdificioObj() {
        return estadoSubEdificioObj;
    }

    /**
     * @param estadoSubEdificioObj the estadoSubEdificioObj to set
     */
    public void setEstadoSubEdificioObj(CmtBasicaMgl estadoSubEdificioObj) {
        this.estadoSubEdificioObj = estadoSubEdificioObj;
    }

    /**
     * @return the companiaConstructoraObj
     */
    public CmtCompaniaMgl getCompaniaConstructoraObj() {
        return companiaConstructoraObj;
    }

    /**
     * @param companiaConstructoraObj the companiaConstructoraObj to set
     */
    public void setCompaniaConstructoraObj(CmtCompaniaMgl companiaConstructoraObj) {
        this.companiaConstructoraObj = companiaConstructoraObj;
    }

    /**
     * @return the tipoAcometidaObj
     */
    public CmtBasicaMgl getTipoAcometidaObj() {
        return tipoAcometidaObj;
    }

    /**
     * @param tipoAcometidaObj the tipoAcometidaObj to set
     */
    public void setTipoAcometidaObj(CmtBasicaMgl tipoAcometidaObj) {
        this.tipoAcometidaObj = tipoAcometidaObj;
    }

    /**
     * @return the companiaAdministracionObj
     */
    public CmtCompaniaMgl getCompaniaAdministracionObj() {
        return companiaAdministracionObj;
    }

    /**
     * @param companiaAdministracionObj the companiaAdministracionObj to set
     */
    public void setCompaniaAdministracionObj(CmtCompaniaMgl companiaAdministracionObj) {
        this.companiaAdministracionObj = companiaAdministracionObj;
    }

    /**
     * @return the companiaAscensorObj
     */
    public CmtCompaniaMgl getCompaniaAscensorObj() {
        return companiaAscensorObj;
    }

    /**
     * @param companiaAscensorObj the companiaAscensorObj to set
     */
    public void setCompaniaAscensorObj(CmtCompaniaMgl companiaAscensorObj) {
        this.companiaAscensorObj = companiaAscensorObj;
    }

    /**
     * @return the nodoObj
     */
    public NodoMgl getNodoObj() {
        return nodoObj;
    }

    /**
     * @param nodoObj the nodoObj to set
     */
    public void setNodoObj(NodoMgl nodoObj) {
        this.nodoObj = nodoObj;
    }

    /**
     * @return the fechaEntregaEdificio
     */
    public Date getFechaEntregaEdificio() {
        return fechaEntregaEdificio;
    }

    /**
     * @param fechaEntregaEdificio the fechaEntregaEdificio to set
     */
    public void setFechaEntregaEdificio(Date fechaEntregaEdificio) {
        this.fechaEntregaEdificio = fechaEntregaEdificio;
    }

    /**
     * @return the administrador
     */
    public String getAdministrador() {
        return administrador;
    }

    /**
     * @param administrador the administrador to set
     */
    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    /**
     * @return the unidadesVt
     */
    public int getUnidadesVt() {
        return unidadesVt;
    }

    /**
     * @param unidadesVt the unidadesVt to set
     */
    public void setUnidadesVt(int unidadesVt) {
        this.unidadesVt = unidadesVt;
    }

    /**
     * @return the unidades
     */
    public int getUnidades() {
        return unidades;
    }

    /**
     * @param unidades the unidades to set
     */
    public void setUnidades(int unidades) {
        this.unidades = unidades;
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
     * @return the telefonoPorteria
     */
    public String getTelefonoPorteria() {
        return telefonoPorteria;
    }

    /**
     * @param telefonoPorteria the telefonoPorteria to set
     */
    public void setTelefonoPorteria(String telefonoPorteria) {
        this.telefonoPorteria = telefonoPorteria;
    }

    /**
     * @return the telefonoPorteria2
     */
    public String getTelefonoPorteria2() {
        return telefonoPorteria2;
    }

    /**
     * @param telefonoPorteria2 the telefonoPorteria2 to set
     */
    public void setTelefonoPorteria2(String telefonoPorteria2) {
        this.telefonoPorteria2 = telefonoPorteria2;
    }

    /**
     * @return the tipoEdificioObj
     */
    public CmtBasicaMgl getTipoEdificioObj() {
        return tipoEdificioObj;
    }

    /**
     * @param tipoEdificioObj the tipoEdificioObj to set
     */
    public void setTipoEdificioObj(CmtBasicaMgl tipoEdificioObj) {
        this.tipoEdificioObj = tipoEdificioObj;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public BigDecimal getSolSubEdificioId() {
        return solSubEdificioId;
    }

    public void setSolSubEdificioId(BigDecimal solSubEdificioId) {
        this.solSubEdificioId = solSubEdificioId;
    }

    public CmtSolicitudCmMgl getSolicitudCMObj() {
        return solicitudCMObj;
    }

    public void setSolicitudCMObj(CmtSolicitudCmMgl solicitudCMObj) {
        this.solicitudCMObj = solicitudCMObj;
    }

    public CmtSubEdificioMgl getSubEdificioObj() {
        return subEdificioObj;
    }

    public void setSubEdificioObj(CmtSubEdificioMgl subEdificioObj) {
        this.subEdificioObj = subEdificioObj;
    }

    public short getSolModCobertura() {
        return solModCobertura;
    }

    public void setSolModCobertura(short solModCobertura) {
        this.solModCobertura = solModCobertura;
    }

    public short getSolModEliminacion() {
        return solModEliminacion;
    }

    public void setSolModEliminacion(short solModEliminacion) {
        this.solModEliminacion = solModEliminacion;
    }

    public short getSolModDatos() {
        return solModDatos;
    }

    public void setSolModDatos(short solModDatos) {
        this.solModDatos = solModDatos;
    }

    public boolean isCheckModNombreSubedificio() {
        return checkModNombreSubedificio;
    }

    public void setCheckModNombreSubedificio(boolean checkModNombreSubedificio) {
        if (!checkModNombreSubedificio) {
            this.nombreSubedificio = null;
        }
        this.checkModNombreSubedificio = checkModNombreSubedificio;
    }

    public boolean isCheckModEstadoSubEdificioObj() {
        return checkModEstadoSubEdificioObj;
    }

    public void setCheckModEstadoSubEdificioObj(boolean checkModEstadoSubEdificioObj) {
        this.checkModEstadoSubEdificioObj = checkModEstadoSubEdificioObj;
    }

    public boolean isCheckModCompaniaConstructoraObj() {
        return checkModCompaniaConstructoraObj;
    }

    public void setCheckModCompaniaConstructoraObj(boolean checkModCompaniaConstructoraObj) {
        this.checkModCompaniaConstructoraObj = checkModCompaniaConstructoraObj;
    }

    public boolean isCheckModCompaniaAdministracionObj() {
        return checkModCompaniaAdministracionObj;
    }

    public void setCheckModCompaniaAdministracionObj(boolean checkModCompaniaAdministracionObj) {
        if (!checkModCompaniaAdministracionObj) {
            this.companiaAdministracionObj = new CmtCompaniaMgl();
        }
        this.checkModCompaniaAdministracionObj = checkModCompaniaAdministracionObj;
    }

    public boolean isCheckModCompaniaAscensorObj() {
        return checkModCompaniaAscensorObj;
    }

    public void setCheckModCompaniaAscensorObj(boolean checkModCompaniaAscensorObj) {
        if (!checkModCompaniaAscensorObj) {
            this.companiaAscensorObj = new CmtCompaniaMgl();
        }
        this.checkModCompaniaAscensorObj = checkModCompaniaAscensorObj;
    }

    public boolean isCheckModNodoObj() {
        return checkModNodoObj;
    }

    public void setCheckModNodoObj(boolean checkModNodoObj) {
        this.checkModNodoObj = checkModNodoObj;
    }

    public boolean isCheckModFechaEntregaEdificio() {
        return checkModFechaEntregaEdificio;
    }

    public void setCheckModFechaEntregaEdificio(boolean checkModFechaEntregaEdificio) {
        if (!checkModFechaEntregaEdificio) {
            this.fechaEntregaEdificio = null;
        }
        this.checkModFechaEntregaEdificio = checkModFechaEntregaEdificio;
    }

    public boolean isCheckModAdministrador() {
        return checkModAdministrador;
    }

    public void setCheckModAdministrador(boolean checkModAdministrador) {
        if (!checkModAdministrador) {
            this.administrador = null;
        }
        this.checkModAdministrador = checkModAdministrador;
    }

    public boolean isCheckModTelefonoPorteria() {
        return checkModTelefonoPorteria;
    }

    public void setCheckModTelefonoPorteria(boolean checkModTelefonoPorteria) {
        if (!checkModTelefonoPorteria) {
            this.telefonoPorteria = null;
        }
        this.checkModTelefonoPorteria = checkModTelefonoPorteria;
    }

    public boolean isCheckModTelefonoPorteria2() {
        return checkModTelefonoPorteria2;
    }

    public void setCheckModTelefonoPorteria2(boolean checkModTelefonoPorteria2) {
        if (!checkModTelefonoPorteria2) {
            this.telefonoPorteria2 = null;
        }
        this.checkModTelefonoPorteria2 = checkModTelefonoPorteria2;
    }

    public boolean isCheckModTipoEdificioObj() {
        return checkModTipoEdificioObj;
    }

    public void setCheckModTipoEdificioObj(boolean checkModTipoEdificioObj) {
        if (!checkModTipoEdificioObj) {
            this.tipoEdificioObj = new CmtBasicaMgl();
        }
        this.checkModTipoEdificioObj = checkModTipoEdificioObj;
    }

    public boolean isSolicitudEliminar() {
        if (solModEliminacion == 1) {
            solicitudEliminar = true;

        } else {
            solicitudEliminar = false;
        }
        return solicitudEliminar;
    }

    public void setSolicitudEliminar(boolean solicitudEliminar) {
        if (solicitudEliminar) {
            solModEliminacion = new Short("1");
        } else {
            solModEliminacion = new Short("0");
        }
        this.solicitudEliminar = solicitudEliminar;
    }

    public boolean isSolicitudModificacion() {
        return solicitudModificacion;
    }

    public void setSolicitudModificacion(boolean solicitudModificacion) {
        this.solicitudModificacion = solicitudModificacion;
    }

    public boolean isSolicitudModificacionCobertura() {
        return solicitudModificacionCobertura;
    }

    public void setSolicitudModificacionCobertura(boolean solicitudModificacionCobertura) {
        this.solicitudModificacionCobertura = solicitudModificacionCobertura;
    }

    public boolean isCheckModDireccion() {
        return checkModDireccion;
    }

    public void setCheckModDireccion(boolean checkModDireccion) {
        if (!checkModDireccion) {
            direccionSolicitudObj = new CmtDireccionSolicitudMgl();
        }
        this.checkModDireccion = checkModDireccion;

    }

    public NodoMgl getNodoDisenoObj() {
        return nodoDisenoObj;
    }

    public void setNodoDisenoObj(NodoMgl nodoDisenoObj) {
        this.nodoDisenoObj = nodoDisenoObj;
    }

    public String getContactoAcsensores() {
        return contactoAcsensores;
    }

    public void setContactoAcsensores(String contactoAcsensores) {
        this.contactoAcsensores = contactoAcsensores;
    }

    public String getContactoConstructora() {
        return contactoConstructora;
    }

    public void setContactoConstructora(String contactoConstructora) {
        this.contactoConstructora = contactoConstructora;
    }

    public CmtDireccionSolicitudMgl getDireccionSolicitudObj() {
        return direccionSolicitudObj;
    }

    public void setDireccionSolicitudObj(CmtDireccionSolicitudMgl direccionSolicitudObj) {
        this.direccionSolicitudObj = direccionSolicitudObj;
    }

    public CmtBasicaMgl getEstratoSubEdificioObj() {
        return estratoSubEdificioObj;
    }

    public void setEstratoSubEdificioObj(CmtBasicaMgl estratoSubEdificioObj) {
        this.estratoSubEdificioObj = estratoSubEdificioObj;
    }

    public boolean isOtroNodoSolicitud() {
        return otroNodoSolicitud;
    }

    public void setOtroNodoSolicitud(boolean otroNodoSolicitud) {
        this.otroNodoSolicitud = otroNodoSolicitud;
    }

    public boolean isEdificioGeneral() {
        return edificioGeneral;
    }

    public void setEdificioGeneral(boolean edificioGeneral) {
        this.edificioGeneral = edificioGeneral;
    }

    public NodoMgl getNodoDisGestion() {
        return nodoDisGestion;
    }

    public void setNodoDisGestion(NodoMgl nodoDisGestion) {
        this.nodoDisGestion = nodoDisGestion;
    }
    
    

}
