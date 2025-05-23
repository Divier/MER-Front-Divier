/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mer.dtos.response.service.RolPortalResponseDto;
import co.com.claro.mer.dtos.response.service.UsuarioPortalResponseDto;
import co.com.claro.mer.dtos.sp.cursors.CtmGestionSegCMAuditoriaDto;
import co.com.claro.mer.dtos.sp.cursors.CtmGestionSegCMDto;
import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;

import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.text.SimpleDateFormat;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpSession;
import co.com.claro.mgl.facade.cm.ICtmGestionSegCmFacadeLocal;
import co.com.claro.mgl.facade.cm.ICtmGestionSegCmAuditoriaFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;

/**
 * Bean de la pagina de seguridad. Permite manejar la
 * logica de administracion de la seguridad de una cuenta matriz
 * y su auditoria.
 *
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
@ManagedBean(name = "seguridadCuentaMatrizBean")
@ViewScoped
public class SeguridadCuentaMatrizBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
        private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    ExternalContext externalContext = facesContext.getExternalContext();
    HttpSession session = (HttpSession) externalContext.getSession(false);

    UsuarioPortalResponseDto usuarioSesion = (UsuarioPortalResponseDto) session.getAttribute("user");
    private static final Logger LOGGER = LogManager.getLogger(SeguridadCuentaMatrizBean.class);
    
    private CuentaMatrizBean cuentaMatrizBean;
    private CmtSubEdificioMgl subEdificioMgl;
    private CmtCuentaMatrizMgl cuentaMatrizMgl;
    private boolean validaCerraduraElectronica;
    private String usuarioVT = null;
    private int perfilVt = 0;
    private boolean tieneRolSeguridad;
    

    @EJB
    private ICtmGestionSegCmAuditoriaFacadeLocal ctmGestionSegCMAuditoriaFacadeLocal;  
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB 
    ICtmGestionSegCmFacadeLocal ctmGestionSegCMFacadeLocal;


    
    private List<CtmGestionSegCMAuditoriaDto> ctmGestionSegCMAuditoriaList;
    private List<CtmGestionSegCMAuditoriaDto> ctmGestionSegCMAuditoriaListFilter;
    private List<CmtBasicaMgl> tipoCerraduraElectronica;
    private CtmGestionSegCMDto ctmGestionSegCM;
    private String filtroUsuario;
    private String filtroFechaOperacion;
    private String filtroNombreColumna;
   
        public SeguridadCuentaMatrizBean()  throws ApplicationException{
        try {
        
              List<RolPortalResponseDto> rolesFiltrados = usuarioSesion.getListRoles().stream()
            .filter(rol -> "SEGCUMA01".equalsIgnoreCase(rol.getCodRol()))
            .collect(Collectors.toList());
              
              if(!rolesFiltrados.isEmpty()){
                  tieneRolSeguridad=true;
              }
              
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();


        } catch (IOException e) {
            LOGGER.error("Ocurrio una Exception en SeguridadCuentaMatrizBean");
            throw new ApplicationException(ConstantsCM.ERROR_CUENTA_MATRIZ, e);
        } 
    }


    @PostConstruct
    private void init(){
        try {
            ctmGestionSegCM = new CtmGestionSegCMDto();
            this.cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            cuentaMatrizMgl = this.cuentaMatrizBean.getCuentaMatriz();
            subEdificioMgl = this.cuentaMatrizBean.getSelectedCmtSubEdificioMgl();

            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
            CmtTipoBasicaMgl cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                co.com.claro.mgl.utils.Constant.TIPO_BASICA_TIPO_CERRADURAS_ELECTRONICAS);

            tipoCerraduraElectronica = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);
            ctmGestionSegCM=ctmGestionSegCMFacadeLocal.findAllManagementAccount(cuentaMatrizMgl.getCuentaMatrizId());
            ctmGestionSegCM.setCmtCuentaMatrizMgl(cuentaMatrizMgl.getCuentaMatrizId());
            if(ctmGestionSegCM.getTipoCerradElect()==null){
                ctmGestionSegCM.setTipoCerradElect(BigDecimal.ZERO);
            }
                
            asignarValorCamposCheck();
            ctmGestionSegCMAuditoriaList = ctmGestionSegCMAuditoriaFacadeLocal.findAllAuditParentAccount(cuentaMatrizMgl.getCuentaMatrizId());

            transformarTipoOperacion();
            setCtmGestionSegCMAuditoriaListFilter(ctmGestionSegCMAuditoriaList);
        } catch (ApplicationException e) {
            LOGGER.error("Error al inicializar el bean SeguridadCuentaMatrizBean ".concat(e.getMessage()));
            FacesUtil.mostrarMensajeError("Error en SeguridadCuentaMatrizBean. " + e.getMessage(), e, LOGGER);

        }
    }
    
    public void transformarTipoOperacion() {
        ctmGestionSegCMAuditoriaList = ctmGestionSegCMAuditoriaList.stream()
                .map(auditoria -> {
                    if ("I".equalsIgnoreCase(auditoria.getTipoOperacion())) {
                        auditoria.setTipoOperacion("INSERTAR");
                    } else if ("A".equalsIgnoreCase(auditoria.getTipoOperacion())) {
                        auditoria.setTipoOperacion("ACTUALIZAR");
                    }
                    
                    if ("PROP_SITIO_CELULAR".equalsIgnoreCase(auditoria.getNombreColumna())) {
                        auditoria.setNombreColumna("Celular Owner sitio");
                    } else if ("PROP_SITIO_NOMBRE".equalsIgnoreCase(auditoria.getNombreColumna())) {
                        auditoria.setNombreColumna("Nombre Owner sitio");
                    } else if ("FABRICANTE".equalsIgnoreCase(auditoria.getNombreColumna())) {
                        auditoria.setNombreColumna("Fabricante cerradura");
                    } else if ("SERIAL".equalsIgnoreCase(auditoria.getNombreColumna())) {
                        auditoria.setNombreColumna("Serial cerradura");
                    } else if ("TIPO_CERRAD_ELECT".equalsIgnoreCase(auditoria.getNombreColumna())) {
                        auditoria.setNombreColumna("Tipo de cerradura electrónica");
                    } else if ("CERRADURA_ELECT".equalsIgnoreCase(auditoria.getNombreColumna())) {
                        auditoria.setNombreColumna("Tipo de Seguridad: Tiene o no cerradura electrónica");
                    } else if ("NOMBRE_JEFE_ZONA".equalsIgnoreCase(auditoria.getNombreColumna())) {
                        auditoria.setNombreColumna("Nombre Jefe de Zona");
                    } else if ("CELULAR_JEFE_ZONA".equalsIgnoreCase(auditoria.getNombreColumna())) {
                        auditoria.setNombreColumna("Celular Jefe de Zona");
                    }
                    return auditoria;
                })
                .collect(Collectors.toList());
    }

    public void guardar() throws ApplicationException{
        try {
            boolean validaRcamposObligatorios=false;
            if(validaCerraduraElectronica){
                validaRcamposObligatorios=ctmGestionSegCM.getTipoCerradElect() == null || ctmGestionSegCM.getTipoCerradElect().compareTo(BigDecimal.ZERO)==0 || ctmGestionSegCM.getSerial() == null || ctmGestionSegCM.getSerial().isEmpty() || ctmGestionSegCM.getFabricante() == null || ctmGestionSegCM.getFabricante().isEmpty();
            }

            if (validaRcamposObligatorios || ctmGestionSegCM.getPropSitioCelular() == null || ctmGestionSegCM.getPropSitioCelular().trim().isEmpty()|| ctmGestionSegCM.getPropSitioNombre() == null || ctmGestionSegCM.getPropSitioNombre().trim().isEmpty()) {
                FacesUtil.mostrarMensajeWarn("Por favor diligenciar todos los campos obligatorios *");
            }
            if (validaRcamposObligatorios || ctmGestionSegCM.getCelularJefeZona() == null || ctmGestionSegCM.getCelularJefeZona().trim().isEmpty() || ctmGestionSegCM.getNombreJefeZona() == null || ctmGestionSegCM.getNombreJefeZona().trim().isEmpty()) {
                FacesUtil.mostrarMensajeWarn("Por favor diligenciar todos los campos obligatorios *");
            } else {
                getCerraduraElectronica(null);
                ctmGestionSegCM.setUsuarioActualiza(usuarioVT);
                ctmGestionSegCM=ctmGestionSegCMFacadeLocal.save(ctmGestionSegCM);

                ctmGestionSegCMAuditoriaList = ctmGestionSegCMAuditoriaFacadeLocal.findAllAuditParentAccount(cuentaMatrizMgl.getCuentaMatrizId());
                transformarTipoOperacion();
                setCtmGestionSegCMAuditoriaListFilter(ctmGestionSegCMAuditoriaList);
                String msn2 = "Registro Almacenado con Exito";
                FacesUtil.mostrarMensaje(msn2);
            }
        } catch (ApplicationException e) {
            LOGGER.error("Ocurrio una Exception al guardar la cuenta matriz");
            throw new ApplicationException(ConstantsCM.ERROR_GUARDAR_CUENTA_MATRIZ, e);
        } 
    }

    private void asignarValorCamposCheck() {
        if (ctmGestionSegCM.getCerraduraElect()!=null&&ctmGestionSegCM.getCerraduraElect().equalsIgnoreCase("S")) {
            validaCerraduraElectronica = true;
        }
    }
            
    public void getCerraduraElectronica(AjaxBehaviorEvent event) {
        if (validaCerraduraElectronica) {
            ctmGestionSegCM.setCerraduraElect("S");
        } else {
            ctmGestionSegCM.setCerraduraElect("N");
        }
        
        
    }
            

    public ICtmGestionSegCmAuditoriaFacadeLocal getCtmGestionSegCMAuditoriaFacadeLocal() {
        return ctmGestionSegCMAuditoriaFacadeLocal;
    }

    public void setCtmGestionSegCMAuditoriaFacadeLocal(ICtmGestionSegCmAuditoriaFacadeLocal ctmGestionSegCMAuditoriaFacadeLocal) {
        this.ctmGestionSegCMAuditoriaFacadeLocal = ctmGestionSegCMAuditoriaFacadeLocal;
    }

    public List<CtmGestionSegCMAuditoriaDto> getCtmGestionSegCMAuditoriaList() {
        return ctmGestionSegCMAuditoriaList;
    }

    public void setCtmGestionSegCMAuditoriaList(List<CtmGestionSegCMAuditoriaDto> ctmGestionSegCMAuditoriaList) {
        this.ctmGestionSegCMAuditoriaList = ctmGestionSegCMAuditoriaList;
    }

    public CtmGestionSegCMDto getCtmGestionSegCM() {
        return ctmGestionSegCM;
    }

    public void setCtmGestionSegCM(CtmGestionSegCMDto ctmGestionSegCM) {
        this.ctmGestionSegCM = ctmGestionSegCM;
    }

    public CmtSubEdificioMgl getSubEdificioMgl() {
        return subEdificioMgl;
    }

    public void setSubEdificioMgl(CmtSubEdificioMgl subEdificioMgl) {
        this.subEdificioMgl = subEdificioMgl;
    }

    public boolean isValidaCerraduraElectronica() {
        return validaCerraduraElectronica;
    }

    public void setValidaCerraduraElectronica(boolean validaCerraduraElectronica) {
        this.validaCerraduraElectronica = validaCerraduraElectronica;
    }

    public List<CtmGestionSegCMAuditoriaDto> getCtmGestionSegCMAuditoriaListFilter() {
        return ctmGestionSegCMAuditoriaListFilter;
    }

    public void setCtmGestionSegCMAuditoriaListFilter(List<CtmGestionSegCMAuditoriaDto> ctmGestionSegCMAuditoriaListFilter) {
        this.ctmGestionSegCMAuditoriaListFilter = ctmGestionSegCMAuditoriaListFilter;
    }
     
    public void filtroCampos() {
         ctmGestionSegCMAuditoriaList = getCtmGestionSegCMAuditoriaListFilter();
        transformarTipoOperacion();
        if (filtroNombreColumna != null && !filtroNombreColumna.isEmpty()) {
            ctmGestionSegCMAuditoriaList = ctmGestionSegCMAuditoriaList.stream()
                    .filter(valor -> valor.getNombreColumna().toLowerCase().contains(filtroNombreColumna.trim().toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (filtroFechaOperacion != null && !filtroFechaOperacion.isEmpty()) {
            ctmGestionSegCMAuditoriaList = ctmGestionSegCMAuditoriaList.stream()
                    .filter(valor -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(valor.getFechaOperacion())
                    .toLowerCase()
                    .contains(filtroFechaOperacion.trim().toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (filtroUsuario != null && !filtroUsuario.isEmpty()) {
            ctmGestionSegCMAuditoriaList = ctmGestionSegCMAuditoriaList.stream()
                    .filter(valor -> valor.getUsuario().toLowerCase().contains(filtroUsuario.trim().toLowerCase()))
                    .collect(Collectors.toList());
        }        
        LOGGER.info("Actualiza formulario: " + ctmGestionSegCMAuditoriaList.size() + " " + filtroNombreColumna + " " + filtroFechaOperacion + " " + filtroUsuario);
    }


    public String getFiltroUsuario() {
        return filtroUsuario;
    }

    public void setFiltroUsuario(String filtroUsuario) {
        this.filtroUsuario = filtroUsuario;
    }



    public String getFiltroFechaOperacion() {
        return filtroFechaOperacion;
    }

    public void setFiltroFechaOperacion(String filtroFechaOperacion) {
        this.filtroFechaOperacion = filtroFechaOperacion;
    }

    public String getFiltroNombreColumna() {
        return filtroNombreColumna;
    }

    public void setFiltroNombreColumna(String FiltroNombreColumna) {
        this.filtroNombreColumna = FiltroNombreColumna;
    }

    public List<CmtBasicaMgl> getTipoCerraduraElectronica() {
        return tipoCerraduraElectronica;
    }

    public void setTipoCerraduraElectronica(List<CmtBasicaMgl> tipoCerraduraElectronica) {
        this.tipoCerraduraElectronica = tipoCerraduraElectronica;
    }

    public int getPerfilVt() {
        return perfilVt;
    }

    public void setPerfilVt(int perfilVt) {
        this.perfilVt = perfilVt;
    }

    public boolean isTieneRolSeguridad() {
        return tieneRolSeguridad;
    }

    public void setTieneRolSeguridad(boolean tieneRolSeguridad) {
        this.tieneRolSeguridad = tieneRolSeguridad;
    }


}
