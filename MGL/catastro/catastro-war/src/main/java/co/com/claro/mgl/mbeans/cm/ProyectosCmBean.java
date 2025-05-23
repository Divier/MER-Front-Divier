/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.CmtFiltroProyectosDto;
import co.com.claro.mgl.dtos.CmtTecSiteSapRespDto;
import co.com.claro.mgl.dtos.CruReadCtechDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.ValidacionUtil;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "proyectosCmBean")
@ViewScoped
public class ProyectosCmBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(ProyectosCmBean.class);
    private static final String TAB_PROYECTOS_CCMM = "TABPROYECTOSCCMM";
    public static final String ERROR_SP = "Error ejecutando el Procedimiento almacenado.. ";
    private String usuarioVT = null;
    private int perfilVT = 0;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private CmtSubEdificioMgl subEdificioMgl;
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    
    @EJB
    private ICmtProyectosMglFacadeLocal cmtProyectosMglFacadeLocal;
    
    private CruReadCtechDto listInfoCtech;
            
    private String message;
    private CmtTecSiteSapRespDto infoProyectosDto;
    private boolean habilitaObj = false;
    
    private String space = "&nbsp;&nbsp;&nbsp;&nbsp;";
    private CuentaMatrizBean cuentaMatrizBean;
    
    @EJB
    private CmtValidadorDireccionesFacadeLocal direccionesFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    
    List<CmtTecnologiaSubMgl> lsCmtTecnologiaSubMgls;
    
    private boolean cargar = true;
    private boolean btnActivo = true;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private BigDecimal ciudad;
    private GeograficoPoliticoMgl centroPoblado;
    private GeograficoPoliticoMgl departamento;
    
    private String nodoCobertura = "";
    private String daneCP = "";
    private String daneMun = "";
    private String centroPob = "";
    private String sitio = "";
    private String ubicaTecnica = "";
    private String idSitio = "";
    
    
    private SecurityLogin securityLogin;

    public ProyectosCmBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
            this.cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            
            subEdificioMgl = this.cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
            
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se genera error en ProyectosCmBean class ..." + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
           FacesUtil.mostrarMensajeError("Se genera error en ProyectosCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {

            direccionesFacadeLocal.setUser(usuarioVT, perfilVT);
            centroPoblado = cuentaMatrizBean.getCuentaMatriz().getCentroPoblado();
            GeograficoPoliticoMgl municipio;
            
            daneCP = centroPoblado.getGeoCodigoDane();
            centroPob = centroPoblado.getGpoNombre();
            municipio = geograficoPoliticoMglFacadeLocal.findById(centroPoblado.getGeoGpoId());
            daneMun = municipio.getGeoCodigoDane();
            departamento = geograficoPoliticoMglFacadeLocal.findById(municipio.getGeoGpoId());
            
            readInfoCtech();
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error en proyectosCmBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en proyectosCmBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Metodo para validar si el usuario tiene permisos de creacion
     * @return  {@code boolean} true si tiene permisos de creacion, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolCrear(){
        try {
            return ValidacionUtil.validarVisualizacion(TAB_PROYECTOS_CCMM, ValidacionUtil.OPC_CREACION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de creación. ");
        }
        return false;
    }

    /**
     * Metodo para validar si el usuario tiene permisos de edicion
     * @return  {@code boolean} true si tiene permisos de edicion, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolEditar(){
        try {
            return ValidacionUtil.validarVisualizacion(TAB_PROYECTOS_CCMM, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de edición. ");
        }
        return false;
    }

    /**
     * Metodo para validar si el usuario tiene permisos de eliminacion
     * @return  {@code boolean} true si tiene permisos de eliminacion, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolEliminar(){
        try {
            return ValidacionUtil.validarVisualizacion(TAB_PROYECTOS_CCMM, ValidacionUtil.OPC_BORRADO, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de eliminación. ");
        }
        return false;
    }

    public void readInfoCtech() {
        try {
            
            CmtFiltroProyectosDto filtro = new CmtFiltroProyectosDto();
            filtro.setOpcion("C");
            filtro.setCcmm(cuentaMatrizBean.getCuentaMatriz().getCuentaMatrizId().intValue());
            filtro.setCentroPoblado(centroPob);
            filtro.setDaneCp(daneCP);
            filtro.setDaneMunicipio(daneMun);
            
            filtro.setIdSitio(idSitio);
            filtro.setSitio(sitio);
            filtro.setTipoSitio(null);
            filtro.setUbicacionTecnica(ubicaTecnica);
            filtro.setUsuarioEdicion(usuarioVT);
            
            filtro.setDisponibilidad(null);
            filtro.setUsuarioCreacion(null);
            
            filtro.setEstadoRegistro(1);
            
            //consumo SP COM_TECHNICALSITESAP_PKG.PRC_CRU_CTECH
            infoProyectosDto = cmtProyectosMglFacadeLocal.crudProyectosCm(filtro);
            if(infoProyectosDto.getListaReadCtechDto()!= null 
                    && infoProyectosDto.getListaReadCtechDto().get(0).getSitesapId() != null){
                listInfoCtech = infoProyectosDto.getListaReadCtechDto().get(0);
                sitio = listInfoCtech.getSitio();
                ubicaTecnica = listInfoCtech.getUbicacionTecnica();
                idSitio = listInfoCtech.getIdSitio();
                btnActivo = false;
            }
            else{
                sitio = "";
                ubicaTecnica = "";
                idSitio = "";
                btnActivo = true;
            }
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("No se encontraron resultados para la consulta:.." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
        }
        
    }

    
    public boolean validarInfoProy() {
        
        return (!sitio.isEmpty() && !ubicaTecnica.isEmpty() &&
                !idSitio.isEmpty());
    }

    
    public void guardarInfoProy() {
        if (validarInfoProy()) {
            crearInfoTecMgl();
        } else {
            String msn = "Todos los campos son obligatorios";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            
        }

    }

    public void crearInfoTecMgl() {
        try {
                CmtFiltroProyectosDto filtro = new CmtFiltroProyectosDto();
                filtro.setOpcion("I");
                filtro.setCcmm(cuentaMatrizBean.getCuentaMatriz().getCuentaMatrizId().intValue());
                filtro.setCentroPoblado(centroPob);
                filtro.setDaneCp(daneCP);
                filtro.setDaneMunicipio(daneMun);
            
                filtro.setIdSitio(idSitio);
                filtro.setSitio(sitio);
                filtro.setTipoSitio(null);
                filtro.setUbicacionTecnica(ubicaTecnica);
                filtro.setUsuarioEdicion(null);
            
                filtro.setDisponibilidad(null);
                filtro.setUsuarioCreacion(usuarioVT);
            
                filtro.setEstadoRegistro(1);
            
                //consumo SP COM_TECHNICALSITESAP_PKG.PRC_CRU_CTECH
                infoProyectosDto = cmtProyectosMglFacadeLocal.crudProyectosCm(filtro);
                if(infoProyectosDto.getCodigo() == 1){
                    readInfoCtech();
                    String msn = "Registro creado correctamente";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                }
                else{
                        String msn = ERROR_SP+ConstantsCM.SP_CRU_CTECH_CM+" : "+infoProyectosDto.getCodigo() + " - "+ infoProyectosDto.getResultado();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                }
            

        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
            String msn = "Proceso falló: " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
    }
    
    public void updateInfoProy() {
        try {
                CmtFiltroProyectosDto filtro = new CmtFiltroProyectosDto();
                filtro.setOpcion("A");
                filtro.setCcmm(cuentaMatrizBean.getCuentaMatriz().getCuentaMatrizId().intValue());
                filtro.setCentroPoblado(centroPob);
                filtro.setDaneCp(daneCP);
                filtro.setDaneMunicipio(daneMun);
            
                filtro.setIdSitio(idSitio);
                filtro.setSitio(sitio);
                filtro.setTipoSitio(null);
                filtro.setUbicacionTecnica(ubicaTecnica);
                filtro.setUsuarioEdicion(usuarioVT);
            
                filtro.setDisponibilidad(null);
                filtro.setUsuarioCreacion(null);
            
                filtro.setEstadoRegistro(1);
            
                //consumo SP COM_TECHNICALSITESAP_PKG.PRC_CRU_CTECH
                infoProyectosDto = cmtProyectosMglFacadeLocal.crudProyectosCm(filtro);
                if(infoProyectosDto.getCodigo() == 1){
                    readInfoCtech();
                    String msn = "Registro actualizado correctamente";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                }
                else{
                        String msn = ERROR_SP+ConstantsCM.SP_CRU_CTECH_CM+" : "+infoProyectosDto.getCodigo() + " - "+ infoProyectosDto.getResultado();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                }
            

        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
            String msn = "Proceso falló: " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
    }
    
        public void actualizarlInfoProy() {
            if (validarInfoProy()) {
                   updateInfoProy();
            } else {
                    String msn = "Todos los campos son obligatorios";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }    
        }
   

    public void eliminarlInfoProy() {

        try {
                CmtFiltroProyectosDto filtro = new CmtFiltroProyectosDto();
                filtro.setOpcion("D");
                filtro.setCcmm(cuentaMatrizBean.getCuentaMatriz().getCuentaMatrizId().intValue());
                filtro.setCentroPoblado(centroPob);
                filtro.setDaneCp(daneCP);
                filtro.setDaneMunicipio(daneMun);
            
                filtro.setIdSitio(idSitio);
                filtro.setSitio(sitio);
                filtro.setTipoSitio(null);
                filtro.setUbicacionTecnica(ubicaTecnica);
                filtro.setUsuarioEdicion(usuarioVT);
            
                filtro.setDisponibilidad(null);
                filtro.setUsuarioCreacion(null);
            
                filtro.setEstadoRegistro(0);
            
                //consumo SP COM_TECHNICALSITESAP_PKG.PRC_CRU_CTECH
                infoProyectosDto = cmtProyectosMglFacadeLocal.crudProyectosCm(filtro);
                if(infoProyectosDto.getCodigo() == 1){
                    limpiarCampos();
                    String msn = "Registro eliminado correctamente";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                }
                else{
                        String msn = ERROR_SP+ConstantsCM.SP_CRU_CTECH_CM+" : "+infoProyectosDto.getCodigo() + " - "+ infoProyectosDto.getResultado();
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                }
        
        } catch (ApplicationException e) {
            String msn = "Proceso falló : ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public void limpiarCampos() {

        sitio = "";
        ubicaTecnica = "";
        idSitio = "";
        infoProyectosDto = null;
        btnActivo = true;
    }

    public void reload(){
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            FacesUtil.navegarAPagina(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error al recargar: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al recargar: " + e.getMessage(), e, LOGGER);
        }
    }

        
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CmtSubEdificioMgl getSubEdificioMgl() {
        return subEdificioMgl;
    }

    public void setSubEdificioMgl(CmtSubEdificioMgl subEdificioMgl) {
        this.subEdificioMgl = subEdificioMgl;
    }

    public boolean isHabilitaObj() {
        return habilitaObj;
    }

    public void setHabilitaObj(boolean habilitaObj) {
        this.habilitaObj = habilitaObj;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public boolean isCargar() {
        return cargar;
    }

    public void setCargar(boolean cargar) {
        this.cargar = cargar;
    }
    
    public boolean isBtnActivo() {
        return btnActivo;
    }

    public void setBtnActivo(boolean btnActivo) {
        this.btnActivo = btnActivo;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public BigDecimal getCiudad() {
        return ciudad;
    }

    public void setCiudad(BigDecimal ciudad) {
        this.ciudad = ciudad;
    }

    public GeograficoPoliticoMgl getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(GeograficoPoliticoMgl centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public GeograficoPoliticoMgl getDepartamento() {
        return departamento;
    }

    public void setDepartamento(GeograficoPoliticoMgl departamento) {
        this.departamento = departamento;
    }

    public String getNodoCobertura() {
        return nodoCobertura;
    }

    public void setNodoCobertura(String nodoCobertura) {
        this.nodoCobertura = nodoCobertura;
    }

    public String getDaneCP() {
        return daneCP;
    }

    public void setDaneCP(String daneCP) {
        this.daneCP = daneCP;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getUbicaTecnica() {
        return ubicaTecnica;
    }

    public void setUbicaTecnica(String ubicaTecnica) {
        this.ubicaTecnica = ubicaTecnica;
    }

    public String getIdSitio() {
        return idSitio;
    }

    public void setIdSitio(String idSitio) {
        this.idSitio = idSitio;
    }

    public String getDaneMun() {
        return daneMun;
    }

    public void setDaneMun(String daneMun) {
        this.daneMun = daneMun;
    }

    public String getCentroPob() {
        return centroPob;
    }

    public void setCentroPob(String centroPob) {
        this.centroPob = centroPob;
    }

    public CruReadCtechDto getListInfoCtech() {
        return listInfoCtech;
    }

    public void setListInfoCtech(CruReadCtechDto listInfoCtech) {
        this.listInfoCtech = listInfoCtech;
    }
 
    
    
}
