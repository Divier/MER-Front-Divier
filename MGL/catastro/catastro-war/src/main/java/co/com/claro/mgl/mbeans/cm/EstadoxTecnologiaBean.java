/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.EstadosTecnologiasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtEstadoxTecnologiaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
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
 * @author cardenaslb
 */
@ManagedBean(name = "estadoxTecnologiaBean")
@ViewScoped
public class EstadoxTecnologiaBean {

    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtEstadoxTecnologiaMglFacadeLocal cmtEstadoxTecnologiaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(EstadoxTecnologiaBean.class);
    private final String FORMULARIO = "ESTADOXTECNOLOGIA";
    private final String ROLOPCSAVEEXTTEC = "ROLOPCSAVEEXTTEC";

    private String usuarioVT = null;
    private int perfilVT = 0;
    private List<CmtBasicaMgl> listTablaBasicaEstadoCM;
    private List<CmtBasicaMgl> listTablaBasicaTecnologias;
    private CmtEstadoxTecnologiaMgl cmtEstadoxTecnologiaMgl;
    private List<CmtEstadoxTecnologiaMgl> listEstadoxTecnologias;
    private boolean btnActivo;
    private List<EstadosTecnologiasDto> selectedDataList;
    private int checked;
    private String estadoSelected;
    private List<CmtEstadoxTecnologiaMgl> listEstadoxTecnologiasBD;
    private SecurityLogin securityLogin;
    private String filtroEstadoCcmm;

    public EstadoxTecnologiaBean() {
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
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoxTecnologia class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoxTecnologia class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void cargarListas() {
        try {
            CmtTipoBasicaMgl tipoBasica;
            tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
            listTablaBasicaEstadoCM = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasica);

            tipoBasica = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            listTablaBasicaTecnologias = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasica);
            cmtEstadoxTecnologiaMglFacadeLocal.setUser(usuarioVT, perfilVT);
            selectedDataList = cmtEstadoxTecnologiaMglFacadeLocal.getListaConf(filtroEstadoCcmm);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoxTecnologia class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoxTecnologia class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void getSelectedItems() {

        try {
            CmtBasicaMgl cmtBasicaMgl = new CmtBasicaMgl();
            if (estadoSelected != null
                    && !estadoSelected.isEmpty()
                    && !estadoSelected.equals("0")) {
                cmtBasicaMgl.setBasicaId(new BigDecimal(estadoSelected));
                listEstadoxTecnologiasBD = cmtEstadoxTecnologiaMglFacadeLocal.findByEstado(cmtBasicaMgl);

            }
        } catch (ApplicationException e) {
            String msn = "Se presenta error al filtrar los registros: " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoxTecnologia class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void createEstadosxTecnologias() {
        try {
            boolean cmtEstadoxTecnologia = cmtEstadoxTecnologiaMglFacadeLocal.createConf(selectedDataList, usuarioVT, perfilVT);
            if (cmtEstadoxTecnologia) {
                String msn = "Proceso exitoso";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
            cmtEstadoxTecnologiaMglFacadeLocal.getListaConf(filtroEstadoCcmm);

        } catch (ApplicationException e) {
            cmtEstadoxTecnologiaMgl.setId(BigDecimal.ZERO);
            String msn = "Proceso falló: ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoxTecnologia class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     * @return
     */
    public boolean validarGuardado() {
        return validarAccion(ValidacionUtil.OPC_CREACION);
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     *
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el
     * componente
     * @return boolean indicador para verificar si se visualizan o no los
     * componentes
     */
    private boolean validarAccion(String opcion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, opcion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoxTecnologia class ..." + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    public boolean validarGuardarRol() {
        return validarEdicion(ROLOPCSAVEEXTTEC);
    }

    private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public List<CmtBasicaMgl> getListTablaBasicaEstadoCM() {
        return listTablaBasicaEstadoCM;
    }

    public void setListTablaBasicaEstadoCM(List<CmtBasicaMgl> listTablaBasicaEstadoCM) {
        this.listTablaBasicaEstadoCM = listTablaBasicaEstadoCM;
    }

    public List<CmtBasicaMgl> getListTablaBasicaTecnologias() {
        return listTablaBasicaTecnologias;
    }

    public void setListTablaBasicaTecnologias(List<CmtBasicaMgl> listTablaBasicaTecnologias) {
        this.listTablaBasicaTecnologias = listTablaBasicaTecnologias;
    }

    public CmtEstadoxTecnologiaMgl getCmtEstadoxTecnologiaMgl() {
        return cmtEstadoxTecnologiaMgl;
    }

    public void setCmtEstadoxTecnologiaMgl(CmtEstadoxTecnologiaMgl cmtEstadoxTecnologiaMgl) {
        this.cmtEstadoxTecnologiaMgl = cmtEstadoxTecnologiaMgl;
    }

    public List<CmtEstadoxTecnologiaMgl> getListEstadoxTecnologias() {
        return listEstadoxTecnologias;
    }

    public void setListEstadoxTecnologias(List<CmtEstadoxTecnologiaMgl> listEstadoxTecnologias) {
        this.listEstadoxTecnologias = listEstadoxTecnologias;
    }

    public boolean isBtnActivo() {
        return btnActivo;
    }

    public void setBtnActivo(boolean btnActivo) {
        this.btnActivo = btnActivo;
    }

    public boolean isActivacion() {
        if (checked == 0) {

            return false;
        } else {

            return true;
        }
    }

    public void setActivacion(boolean activacion) {
        if (activacion) {
            checked = 1;
        } else {
            checked = 0;
        }
    }
    
    public void filtrarInfo() throws ApplicationException {
      selectedDataList = cmtEstadoxTecnologiaMglFacadeLocal.getListaConf(filtroEstadoCcmm);
    }
        

    public List<EstadosTecnologiasDto> getSelectedDataList() {
        return selectedDataList;
    }

    public void setSelectedDataList(List<EstadosTecnologiasDto> selectedDataList) {
        this.selectedDataList = selectedDataList;
    }

    public String getEstadoSelected() {
        return estadoSelected;
    }

    public void setEstadoSelected(String estadoSelected) {
        this.estadoSelected = estadoSelected;
    }

    public List<CmtEstadoxTecnologiaMgl> getListEstadoxTecnologiasBD() {
        return listEstadoxTecnologiasBD;
    }

    public void setListEstadoxTecnologiasBD(List<CmtEstadoxTecnologiaMgl> listEstadoxTecnologiasBD) {
        this.listEstadoxTecnologiasBD = listEstadoxTecnologiasBD;
    }

    public String getFiltroEstadoCcmm() {
        return filtroEstadoCcmm;
    }

    public void setFiltroEstadoCcmm(String filtroEstadoCcmm) {
        this.filtroEstadoCcmm = filtroEstadoCcmm;
    }
    
}
