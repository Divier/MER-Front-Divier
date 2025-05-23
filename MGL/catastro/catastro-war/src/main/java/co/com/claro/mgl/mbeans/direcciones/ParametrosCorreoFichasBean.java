package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.mbeans.util.PrimeFacesUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "parametrosCorreoFichasBean")
@ViewScoped
public class ParametrosCorreoFichasBean implements  Serializable{
    
    private static final Logger LOGGER = LogManager.getLogger(ParametrosCorreoFichasBean.class);
    private static final String ASUNTO_CORREO_FICHAS = "ASUNTO_CORREO_FICHAS_INFORME";
    private static final String CUERPO_CORREO_FICHAS = "CUERPO_CORREO_FICHAS_INFORME";
    
    private ParametrosMgl asuntoCorreo;
    private ParametrosMgl cuerpoCorreo;
    
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    
    private String usuarioVT = null;
    private int perfilVT = 0;
    
    public ParametrosCorreoFichasBean(){
        try {
            securityLogin = new SecurityLogin(FacesContext.getCurrentInstance());
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al TipoBasicaMglBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al TipoBasicaMglBean. " + e.getMessage(), e, LOGGER);
        }
    }
    
    
    @PostConstruct
    public void init (){
        try {
            asuntoCorreo = new ParametrosMgl();
            cuerpoCorreo = new ParametrosMgl();
            asuntoCorreo = parametrosMglFacadeLocal.findByAcronimoName(ASUNTO_CORREO_FICHAS);
            cuerpoCorreo = parametrosMglFacadeLocal.findByAcronimoName(CUERPO_CORREO_FICHAS);
            PrimeFacesUtil.update("frm_parametros_correo");
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en init. ", e, LOGGER);
        }catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en init. ", e, LOGGER);
        }
    }
    
    public void actualizarParametrosCorreo(){
        try {
            if(asuntoCorreo.getParValor() != null && !asuntoCorreo.getParValor().isEmpty() && cuerpoCorreo.getParValor() != null && !cuerpoCorreo.getParValor().isEmpty()){
                String cuerpo = cuerpoCorreo.getParValor();
                cuerpo = cuerpo.replace("<p>", "");
                cuerpo = cuerpo.replace("</p>", "");
                cuerpo = cuerpo.replace("<br>", "");
                cuerpo = cuerpo.replace("<ol>", "");
                cuerpo = cuerpo.replace("</ol>", "");
                cuerpo = cuerpo.replace("<li>", "");
                cuerpo = cuerpo.replace("</li>", "");
                cuerpo = cuerpo.replace("<strong>", "");
                cuerpo = cuerpo.replace("</strong>", "");
                if(asuntoCorreo.getParValor().length() <= 1500 && cuerpo.length() <= 1500 ){
                    parametrosMglFacadeLocal.update(asuntoCorreo);
                    parametrosMglFacadeLocal.update(cuerpoCorreo);
                    PrimeFacesUtil.update("frm_parametros_correo");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Parametros Actualizados Correctamente..", ""));   
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La cantidad maxima de caracteres para Asunto y Cuerpo es de 1500.", ""));
                }
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Asunto y Cuerpo no deben ser vacios.", ""));
            }
           
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en actualizarParametrosCorreo. ", e, LOGGER);
        }catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en actualizarParametrosCorreo. ", e, LOGGER);
        }
    }

    public ParametrosMgl getAsuntoCorreo() {
        return asuntoCorreo;
    }

    public void setAsuntoCorreo(ParametrosMgl asuntoCorreo) {
        this.asuntoCorreo = asuntoCorreo;
    }

    public ParametrosMgl getCuerpoCorreo() {
        return cuerpoCorreo;
    }

    public void setCuerpoCorreo(ParametrosMgl cuerpoCorreo) {
        this.cuerpoCorreo = cuerpoCorreo;
    }
    
    
        private final String BTNACPARM = "BTNACPARM";
    
    // Validar Opciones por Rol
    
    public boolean validarOpcionActualizar() {
        return validarEdicionRol(BTNACPARM);
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
