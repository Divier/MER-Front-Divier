package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSolicitudCmMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author bocanegravm
 */
@ManagedBean(name = "cmtConsultarEstadoSolBean")
@ViewScoped
public class CmtConsultarEstadoSolBean implements Serializable {

    

    @EJB
    private CmtSolicitudCmMglFacadeLocal cmtSolicitudCmMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
    
    private SecurityLogin securityLogin;
    private String usuarioVT = null;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    //Opciones agregadas para Rol
    private final String CNSBTIMCS = "CNSBTIMCS";
    private CmtTipoSolicitudMgl cmtTipoSolicitudMgl;

    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private static final Logger LOGGER = LogManager.getLogger(CmtConsultarEstadoSolBean.class);
    private String idSolicitudToFind;
    private CmtSolicitudCmMgl cmtSolicitudCmMgl;
    private String paginaSelected;

    public CmtConsultarEstadoSolBean() {
        try {           
            securityLogin = new SecurityLogin(FacesContext.getCurrentInstance());
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            cmtSolicitudCmMgl = new CmtSolicitudCmMgl();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al ReportesSolBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ReportesSolBean. " + e.getMessage(), e, LOGGER);
        }
    }
    
    @PostConstruct
    public void init() {
    }

    public String findById() {
        try {
            if (idSolicitudToFind != null && !idSolicitudToFind.trim().isEmpty()) {
                BigDecimal idSol = new BigDecimal(idSolicitudToFind.trim());
                cmtSolicitudCmMgl = cmtSolicitudCmMglFacadeLocal.findById(idSol);
                if (cmtSolicitudCmMgl != null) {
                    
                    int idTipoSolicitud = asignarFormulario(cmtSolicitudCmMgl.getTipoSolicitudObj().getAbreviatura());

                    switch (idTipoSolicitud) {

                        case 1:
                                session.setAttribute("idSolicitudCm", cmtSolicitudCmMgl);
                                paginaSelected = "solicitudCreacionCm";
                            break;
                        case 2:
                                session.setAttribute("idSolicitudCm", cmtSolicitudCmMgl);
                                paginaSelected = "solicitudModificacionCm";
                            break;
                        case 3:
                                session.setAttribute("idSolicitudCm", cmtSolicitudCmMgl);
                                paginaSelected = "solicitudCreacionVt";
                            break;
                        case 4:
                                session.setAttribute("idSolicitudCm", cmtSolicitudCmMgl);
                                paginaSelected = "solicitudCreaModHhpp";
                            break;
                        case 5:
                                session.setAttribute("idSolicitudCm", cmtSolicitudCmMgl);
                                paginaSelected = "solicitudCreaModHhpp";
                            break;
                        case 6:
                                session.setAttribute("idSolicitudCm", cmtSolicitudCmMgl);
                                paginaSelected = "solicitudEliminacionCm";
                            break;
                        default:
                            break;
                    }
                }else{
                    String msnError = "No se encontró ninguna solicitud de CCMM con el id ingresado";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    msnError, ""));
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Ocurrio un Error consultando la solicitud " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al consultar el estado de solicitud en CmtConsultarEstadoSolBean: findById()" + e.getMessage(), e, LOGGER);
        }
        return paginaSelected;
    }
    
    
    public String findBySolicitud(CmtSolicitudCmMgl solicitud) {
        try {
                cmtSolicitudCmMgl = solicitud;
                if (cmtSolicitudCmMgl != null) {
                    
                    int idTipoSolicitud = asignarFormulario(cmtSolicitudCmMgl.getTipoSolicitudObj().getAbreviatura());

                    switch (idTipoSolicitud) {

                        case 1:
                                session.setAttribute("idSolicitudCm", cmtSolicitudCmMgl);
                                paginaSelected = "solicitudCreacionCm";
                            break;
                        case 2:
                                session.setAttribute("idSolicitudCm", cmtSolicitudCmMgl);
                                paginaSelected = "solicitudModificacionCm";
                            break;
                        case 3:
                                session.setAttribute("idSolicitudCm", cmtSolicitudCmMgl);
                                paginaSelected = "solicitudCreacionVt";
                            break;
                        case 4:
                                session.setAttribute("idSolicitudCm", cmtSolicitudCmMgl);
                                paginaSelected = "solicitudCreaModHhpp";
                            break;
                        case 5:
                                session.setAttribute("idSolicitudCm", cmtSolicitudCmMgl);
                                paginaSelected = "solicitudCreaModHhpp";
                            break;
                        case 6:
                                session.setAttribute("idSolicitudCm", cmtSolicitudCmMgl);
                                paginaSelected = "solicitudEliminacionCm";
                            break;
                        default:
                            break;
                    }
                }
            
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el estado de la solicitud. EX000: " + e.getMessage(), e);
            String msn = "Ocurrio un Error consultando la solicitud en CmtConsultarEstadoSolBean: findBySolicitud() " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
        return  "/view/MGL/CM/solicitudes/consulta/"+paginaSelected;
    }
    
    public int asignarFormulario(String abreviatura) {
        try {

            if (ConstantsCM.TIPO_SOLICITUD_CREACION_CM.equals(abreviatura)) {
                return 1;
            } else if (ConstantsCM.TIPO_SOLICITUD_MODIFICACION_CM.equals(abreviatura)) {
                return 2;
            } else if (ConstantsCM.TIPO_SOLICITUD_VISITA_TECNICA.equals(abreviatura)) {
                return 3;
            } else if (ConstantsCM.TIPO_SOLICITUD_MODIFICACION_HHPP.equals(abreviatura)) {
                return 4;
            } else if (ConstantsCM.TIPO_SOLICITUD_CREACION_HHPP.equals(abreviatura)) {
                return 5;
            } else if (ConstantsCM.TIPO_SOLICITUD_ELIMINACION_CM.equals(abreviatura)) {
                return 6;
            }

        } catch (Exception ex) {
             FacesUtil.mostrarMensajeError("Se genera error asignando formularios en CmtConsultarEstadoSolBean: asignarFormulario()" + ex.getMessage(), ex, LOGGER);
            LOGGER.error("Error asignando formularios en CmtConsultarEstadoSolBean:  asignarFormulario.  " + ex.getMessage(), ex);
        }
        return 0;
    }


    public String getIdSolicitudToFind() {
        return idSolicitudToFind;
    }

    public void setIdSolicitudToFind(String idSolicitudToFind) {
        this.idSolicitudToFind = idSolicitudToFind;
    }

    public String getPaginaSelected() {
        return paginaSelected;
    }

    public void setPaginaSelected(String paginaSelected) {
        this.paginaSelected = paginaSelected;
    }

    public CmtTipoSolicitudMgl getCmtTipoSolicitudMgl() {
        return cmtTipoSolicitudMgl;
    }

    public void setCmtTipoSolicitudMgl(CmtTipoSolicitudMgl cmtTipoSolicitudMgl) {
        this.cmtTipoSolicitudMgl = cmtTipoSolicitudMgl;
    }
    
    
        
    // Validar Opciones por Rol
 
    public boolean validarOpcionConsultar() {
        return validarEdicionRol(CNSBTIMCS);
    }
    
    //funcion General
      private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
            return tieneRolPermitido;
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    

}
