/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.FiltroConsultaInventarioTecDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtInventarioTecnologiaFacadeLocal;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtInventariosTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
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
@ManagedBean(name = "inventarioTecnologiaBean")
//@RequestScoped
@ViewScoped
public class InventarioTecnologiaBean {

    private static final Logger LOGGER = LogManager.getLogger(InventarioTecnologiaBean.class);
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private int perfilVt = 0;
    private static final int numeroRegistrosConsulta = 8;
    private int actual;
    private CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl;
    private CuentaMatrizBean cuentaMatrizBean;
    @EJB
    private CmtInventarioTecnologiaFacadeLocal cmtInventarioTecnologiaFacadeLocal;
    private FiltroConsultaInventarioTecDto filtroConsultaInventarioTecDto;
    private CmtSubEdificioMgl cmtSubEdificioMgl;
    private List<CmtInventariosTecnologiaMgl> listCmtInventariosTecnologiaMgl;
    private String numPagina;
    private List<Integer> paginaList;
    private String pageActual;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;

    public InventarioTecnologiaBean() {
        SecurityLogin securityLogin;
        try {
            securityLogin = new SecurityLogin(facesContext);
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();
            cmtInventariosTecnologiaMgl = new CmtInventariosTecnologiaMgl();
        } catch (IOException e) {
            String msn2 = "Error al cargar Bean HHPP:....";
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InventarioTecnologiaBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void cargarListas() {
        try {
            listCmtInventariosTecnologiaMgl = new ArrayList<CmtInventariosTecnologiaMgl>();
            this.cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            filtroConsultaInventarioTecDto = new FiltroConsultaInventarioTecDto();
            cmtSubEdificioMgl = cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
            listInfoByPage(1);
        } catch (Exception e) {
            String msn2 = "Error al cargar listas basicas del bean:...." + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            LOGGER.error(msn2);
        }
    }

    public String listInfoByPage(int page) {
        PaginacionDto<CmtInventariosTecnologiaMgl> paginacionDto = new PaginacionDto<CmtInventariosTecnologiaMgl>();
        try {
            if (cmtSubEdificioMgl != null && cmtSubEdificioMgl.getListHhpp() != null) {
                if (cmtSubEdificioMgl.getEstadoSubEdificioObj().getBasicaId().equals(
                        Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                    paginacionDto = cmtInventarioTecnologiaFacadeLocal.findBySubOrCM(page, numeroRegistrosConsulta, cmtSubEdificioMgl,
                            filtroConsultaInventarioTecDto, Constant.FIND_HHPP_BY.CUENTA_MATRIZ);
                } else {
                    paginacionDto = cmtInventarioTecnologiaFacadeLocal.findBySubOrCM(page, numeroRegistrosConsulta, cmtSubEdificioMgl,
                            filtroConsultaInventarioTecDto, Constant.FIND_HHPP_BY.SUB_EDIFICIO);
                }
            }
            listCmtInventariosTecnologiaMgl = paginacionDto.getListResultado();
            actual = page;
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InventarioTecnologiaBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void findByFiltro() {
        pageFirst();
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            String msn = "Se generea al cargar Inventario del Edificio:" + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        }
    }

    public void pagePrevious() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
            String msn = "Se generea al cargar HHPP de Edificio:" + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        }
    }

    public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException ex) {
            String msn = "Se generea al cargar HHPP de Edificio:" + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Se generea error en InventarioTecnologiaBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void pageNext() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
            String msn = "Se generea al cargar HHPP de Edificio:" + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            String msn = "Se generea al cargar HHPP de Edificio:" + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error(msn + ex);
        }

    }

    public int getTotalPaginas() {
        try {
            PaginacionDto<CmtInventariosTecnologiaMgl> paginacionDto = new PaginacionDto<CmtInventariosTecnologiaMgl>();
            if (cmtSubEdificioMgl != null && cmtSubEdificioMgl.getListHhpp() != null) {
                if (cmtSubEdificioMgl.getEstadoSubEdificioObj().getBasicaId().equals(
                        cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId())) {
                    paginacionDto = cmtInventarioTecnologiaFacadeLocal.findBySubOrCM(0, numeroRegistrosConsulta, cmtSubEdificioMgl,
                            filtroConsultaInventarioTecDto, Constant.FIND_HHPP_BY.CUENTA_MATRIZ_SOLO_CONTAR);
                } else {
                    paginacionDto = cmtInventarioTecnologiaFacadeLocal.findBySubOrCM(0, numeroRegistrosConsulta, cmtSubEdificioMgl,
                            filtroConsultaInventarioTecDto, Constant.FIND_HHPP_BY.SUB_EDIFICIO_SOLO_CONTAR);
                }
            }

            Long count = paginacionDto.getNumPaginas();
            int totalPaginas = (int) ((count % numeroRegistrosConsulta != 0)
                    ? (count / numeroRegistrosConsulta) + 1
                    : count / numeroRegistrosConsulta);
            return totalPaginas;
        } catch (ApplicationException e) {
            String msn = "Se generea al cargar HHPP de Edificio:";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
          
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InventarioTecnologiaBean class ..." + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public String getPageActual() {
        try {
            paginaList = new ArrayList<Integer>();
            int totalPaginas = getTotalPaginas();
            for (int i = 1; i <= totalPaginas; i++) {
                paginaList.add(i);
            }
            pageActual = String.valueOf(actual) + " de "
                    + String.valueOf(totalPaginas);

            if (numPagina == null) {
                numPagina = "1";
            }
            numPagina = String.valueOf(actual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InventarioTecnologiaBean class ..." + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public CmtInventariosTecnologiaMgl getCmtInventariosTecnologiaMgl() {
        return cmtInventariosTecnologiaMgl;
    }

    public void setCmtInventariosTecnologiaMgl(CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl) {
        this.cmtInventariosTecnologiaMgl = cmtInventariosTecnologiaMgl;
    }

    public CuentaMatrizBean getCuentaMatrizBean() {
        return cuentaMatrizBean;
    }

    public void setCuentaMatrizBean(CuentaMatrizBean cuentaMatrizBean) {
        this.cuentaMatrizBean = cuentaMatrizBean;
    }

    public CmtSubEdificioMgl getCmtSubEdificioMgl() {
        return cmtSubEdificioMgl;
    }

    public void setCmtSubEdificioMgl(CmtSubEdificioMgl cmtSubEdificioMgl) {
        this.cmtSubEdificioMgl = cmtSubEdificioMgl;
    }

    public List<CmtInventariosTecnologiaMgl> getListCmtInventariosTecnologiaMgl() {
        return listCmtInventariosTecnologiaMgl;
    }

    public void setListCmtInventariosTecnologiaMgl(List<CmtInventariosTecnologiaMgl> listCmtInventariosTecnologiaMgl) {
        this.listCmtInventariosTecnologiaMgl = listCmtInventariosTecnologiaMgl;
    }

    public FiltroConsultaInventarioTecDto getFiltroConsultaInventarioTecDto() {
        return filtroConsultaInventarioTecDto;
    }

    public void setFiltroConsultaInventarioTecDto(FiltroConsultaInventarioTecDto filtroConsultaInventarioTecDto) {
        this.filtroConsultaInventarioTecDto = filtroConsultaInventarioTecDto;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

}
