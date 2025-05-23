/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.validacionParametrizada;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ValidacionParametrizadaTipoValidacionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ValidacionParametrizadaTipoValidacionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Juan David Hernandez
 */
@ViewScoped
@ManagedBean(name = "validacionParametrizadaMglBean")
public class ValidacionParametrizadaMglBean implements Serializable {

    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(ValidacionParametrizadaMglBean.class);
    private String usuarioVT = null;
    private int perfilVt = 0;
    private BigDecimal validacionSeleccionadaBasicaId;
    private List<ValidacionParametrizadaTipoValidacionMgl> validacionParametrizadaList;
    private List<CmtBasicaMgl> tipoValidacionParamentrizadaList;
    @EJB
    private ValidacionParametrizadaTipoValidacionMglFacadeLocal validacionParametrizadaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    

    public ValidacionParametrizadaMglBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                response.sendRedirect(SecurityLogin.SERVER_PARA_AUTENTICACION);
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();

            if (usuarioVT == null) {
                response.sendRedirect(SecurityLogin.SERVER_PARA_AUTENTICACION);
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en ValidacionParametrizadaMglBean. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ValidacionParametrizadaMglBean. ", e, LOGGER);
        }
    }
    

    @PostConstruct
    public void init() {
        try {
            obtenerTipoValidacionBasicaList();

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en init. al realizar cargue de configuración de listados: " +e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en init. al realizar cargue de configuración de listados: " +e.getMessage(), e, LOGGER);
        }
    }

        /**
     * Obtiene el listado de validaciones por tipo de la base de datos
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerValidacionParametrizadaList() throws ApplicationException {
        try {
           validacionParametrizadaList = validacionParametrizadaMglFacadeLocal
                    .findValidacionParametrizadaByTipo(validacionSeleccionadaBasicaId);
           
            if (validacionParametrizadaList != null && !tipoValidacionParamentrizadaList.isEmpty()) {

                for (ValidacionParametrizadaTipoValidacionMgl val : validacionParametrizadaList) {
                    if (val.getValidacionActiva().equals(BigDecimal.ONE)) {
                        val.setValidacionSeleccionada(true);
                    } else {
                        val.setValidacionSeleccionada(false);
                    }
                }
            }

           
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerValidacionParametrizadaList, al realizar cargue listado de validaciones por tipo. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerValidacionParametrizadaList, al realizar cargue listado de validaciones por tipo. ", e, LOGGER);
        }
    }
    
     /**
     * Obtiene el listado de validaciones disponibles por paramentrizar
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerTipoValidacionBasicaList() throws ApplicationException {
        try {
             //Obtiene valores de tipo de validaciones parametrizadas
            CmtTipoBasicaMgl tipoBasicaTipoValidacionParametrizada;
            tipoBasicaTipoValidacionParametrizada = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_VALIDACION_PARAMETRIZADA);
            
            tipoValidacionParamentrizadaList =
                    cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoValidacionParametrizada); 
           
        } catch (ApplicationException e) {
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            throw e;
        } catch (Exception e) {
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            throw new ApplicationException("Error en obtenerTipoValidacionBasicaList, al realizar cargue listado de validaciones por tipo: " + e.getMessage(), e);
        }
    }
    
    public void guardarParametrizacion(){
        try {
            if(validacionParametrizadaList != null){                
                for (ValidacionParametrizadaTipoValidacionMgl val : validacionParametrizadaList) {  
                    if(val.isValidacionSeleccionada()){
                        val.setValidacionActiva(BigDecimal.ONE);
                    }else{
                        val.setValidacionActiva(BigDecimal.ZERO);
                    }
                    validacionParametrizadaMglFacadeLocal.update(val);
                    String msnError = "Cambios guardados correctamente";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, msnError, ""));

                }
                
            }
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en guardarParametrizacion. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en guardarParametrizacion. ", e, LOGGER);
        }
    }
    
    public void limpiarParametrizacion(){
            validacionSeleccionadaBasicaId = null;
            validacionParametrizadaList = null;                        
    }
    
    
    
    
    
    
    
    
    
    

    public BigDecimal getValidacionSeleccionadaBasicaId() {
        return validacionSeleccionadaBasicaId;
    }

    public void setValidacionSeleccionadaBasicaId(BigDecimal validacionSeleccionadaBasicaId) {
        this.validacionSeleccionadaBasicaId = validacionSeleccionadaBasicaId;
    }

    public List<ValidacionParametrizadaTipoValidacionMgl> getValidacionParametrizadaList() {
        return validacionParametrizadaList;
    }

    public void setValidacionParametrizadaList(List<ValidacionParametrizadaTipoValidacionMgl> validacionParametrizadaList) {
        this.validacionParametrizadaList = validacionParametrizadaList;
    }

    public List<CmtBasicaMgl> getTipoValidacionParamentrizadaList() {
        return tipoValidacionParamentrizadaList;
    }

    public void setTipoValidacionParamentrizadaList(List<CmtBasicaMgl> tipoValidacionParamentrizadaList) {
        this.tipoValidacionParamentrizadaList = tipoValidacionParamentrizadaList;
    }
    
}
