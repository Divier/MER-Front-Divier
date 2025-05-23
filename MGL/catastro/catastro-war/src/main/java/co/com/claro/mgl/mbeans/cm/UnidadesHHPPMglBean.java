/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;


import co.com.claro.mgl.dtos.CmtPestaniaHhppDetalleDto;
import co.com.claro.mgl.dtos.CmtPestaniaHhppDto;
import co.com.claro.mgl.dtos.FiltroBusquedaDirecccionDto;
import co.com.claro.mgl.dtos.FiltroConsultaHhppTecDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.mbeans.direcciones.HhppDetalleSessionBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "unidadesHHPPMglBean")
@ViewScoped
public class UnidadesHHPPMglBean implements Serializable {

    @EJB
    private HhppMglFacadeLocal hhppMglFacade;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(UnidadesHHPPMglBean.class);
    private CuentaMatrizBean cuentaMatrizBean;
    private CmtSubEdificioMgl cmtSubEdificioMgl;
    private int numeroRegistrosConsulta = 8;
    private int actual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private String pageActual;
    private int filasPag = ConstantsCM.PAGINACION_OCHO_FILAS;
    private FiltroConsultaHhppTecDto filtroConsultaHhppTecDto;
    private CmtPestaniaHhppDto cmtPestaniaHhppDto;
    private HashMap<String, Object> params;
    private boolean habilitaObj = false;
    private CmtCuentaMatrizMgl cmtCuentaMatrizMgl;
    private String usuarioVT = null;
    private int perfilVT = 0;
    private HhppMgl hhppMgl;
    private boolean pintarPaginado = true;
    private String direccion;
      @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
       @EJB
    private CmtSubEdificioMglFacadeLocal cmtSubEdificioMglFacadeLocal;
     FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto = new FiltroBusquedaDirecccionDto();
    @EJB
    private CmtDireccionDetalleMglFacadeLocal cmtDireccionDetalleMglFacadeLocal;

    /**
     * Creates a new instance of UnidadesHHPPMglBean
     */
    public UnidadesHHPPMglBean()  {
       
        try {
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
            hhppMgl = new HhppMgl();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al UnidadesHHPPMglBean. " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void fillSqlList() {
        try {
            this.cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            Objects.requireNonNull(cuentaMatrizBean, "El objeto CuentaMatrizBean es nulo, no se pudo obtener la información.");
            cmtCuentaMatrizMgl = this.cuentaMatrizBean.getCuentaMatriz();
            cmtSubEdificioMgl = this.cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
            listInfoByPage(1);
        } catch (Exception e) {
            String msn = "Se genera error al cargar HHPP :UnidadesHHPPMglBean: fillSqlList()" ;
            FacesUtil.mostrarMensajeError(msn+ e.getMessage(), e, LOGGER);
        }
    }

    private void listInfoByPage(int page) {
        try {
            actual = page;
            Objects.requireNonNull(cmtSubEdificioMgl, "El objeto CmtSubEdificioMgl es nulo, no se pudo obtener la información.");
            getPageActual();
            String identificadorInterno = Optional.of(cmtSubEdificioMgl)
                    .map(CmtSubEdificioMgl::getEstadoSubEdificioObj)
                    .map(CmtBasicaMgl::getIdentificadorInternoApp).orElse(null);

            if (CollectionUtils.isNotEmpty(cmtSubEdificioMgl.getListHhpp())) {
                paginarHhpp(page, identificadorInterno);
                return;
            }

            if (Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO.equals(identificadorInterno)) {
                cmtPestaniaHhppDto = hhppMglFacade.findByHhppSubOrCM(filtroBusquedaDirecccionDto, page, filasPag, cmtSubEdificioMgl, Constant.FIND_HHPP_BY.CUENTA_MATRIZ);
                return;
            }

            cmtPestaniaHhppDto = hhppMglFacade.findByHhppSubOrCM(filtroBusquedaDirecccionDto, page, filasPag, cmtSubEdificioMgl, Constant.FIND_HHPP_BY.SUB_EDIFICIO);
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al UnidadesHHPPMglBean: listInfoByPage(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Realiza la paginación de los HHPP de acuerdo al subEdificio o cuenta matriz
     *
     * @param page Pagina actual
     * @param identificadorInterno Identificador interno del subEdificio
     * @throws ApplicationException Excepción en caso de error
     * @author Gildardo Mora
     */
    private void paginarHhpp(int page, String identificadorInterno) throws ApplicationException {
        boolean isMultiEdificio = Optional.ofNullable(identificadorInterno)
                .map(id -> id.equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO))
                .orElse(false);

        if (isMultiEdificio) {
            cmtPestaniaHhppDto = hhppMglFacade.findByHhppSubOrCM(filtroBusquedaDirecccionDto, page, filasPag, cmtSubEdificioMgl, Constant.FIND_HHPP_BY.CUENTA_MATRIZ);
            return;
        }

        BigDecimal cuentaMatrizId = Optional.ofNullable(cmtSubEdificioMgl)
                .map(CmtSubEdificioMgl::getCmtCuentaMatrizMglObj)
                .map(CmtCuentaMatrizMgl::getCuentaMatrizId)
                .orElseThrow(() -> new ApplicationException("La cuenta matriz no existe"));
        Long numSubEdificios = cmtSubEdificioMglFacadeLocal.countSubEdificiosCuentaMatriz(cuentaMatrizId);
        Constant.FIND_HHPP_BY findHhppBy = numSubEdificios > 1 ? Constant.FIND_HHPP_BY.SUB_EDIFICIO : Constant.FIND_HHPP_BY.CUENTA_MATRIZ;
        cmtPestaniaHhppDto = hhppMglFacade.findByHhppSubOrCM(filtroBusquedaDirecccionDto, page, filasPag, cmtSubEdificioMgl, findHhppBy);
    }

    public void findByFiltro() {
        pageFirst();
    }
    
    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error al momento de navegar a la primera página. EX000: {}", ex.getMessage(), ex);
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
            LOGGER.error("Error al momento de navegar a la página anterior. EX000: {}", ex.getMessage(), ex);
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
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error al momento de navegar a la página: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al momento de navegar a la página: " + e.getMessage(), e, LOGGER);
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
            LOGGER.error("Error al momento de navegar a la siguiente página. EX000: {}", ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error al momento de navegar a la última página. EX000: {}", ex.getMessage(), ex);
        }

    }
    
    public int getTotalPaginas() {
        try {
            int count;
            //consulta el estado valido de subEdificio (torre) a partir del identificador de app
            CmtBasicaMgl byCodigoInternoApp = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
            //Obtiene el ID del estado -> 50 (MULTI-MULTI-EDIFICIO)
            BigDecimal idEstadoMultiEdificio = Optional.ofNullable(byCodigoInternoApp)
                    .map(CmtBasicaMgl::getBasicaId)
                    .orElse(null);

            BigDecimal idEstadoSubEdificio = Optional.ofNullable(cmtSubEdificioMgl)
                    .map(CmtSubEdificioMgl::getEstadoSubEdificioObj)
                    .map(CmtBasicaMgl::getBasicaId).orElse(null);

            if (cmtSubEdificioMgl != null && CollectionUtils.isNotEmpty(cmtSubEdificioMgl.getListHhpp())) {
                if (idEstadoSubEdificio != null && idEstadoSubEdificio.equals(idEstadoMultiEdificio)) {
                    count = hhppMglFacade.countListHhppCM(filtroBusquedaDirecccionDto, cmtSubEdificioMgl);
                } else {
                    count = hhppMglFacade.countListHhppSubEdif(filtroBusquedaDirecccionDto, cmtSubEdificioMgl);
                }

                return calcularTotalPaginas(count);
            }

            if (idEstadoSubEdificio != null && idEstadoSubEdificio.equals(idEstadoMultiEdificio)) {
                count = hhppMglFacade.countListHhppCM(filtroBusquedaDirecccionDto, cmtSubEdificioMgl);
            } else {
                //consulta al cantidad de hhpp en el subEdificio
                count = hhppMglFacade.countListHhppSubEdif(filtroBusquedaDirecccionDto, cmtSubEdificioMgl);
            }

            return calcularTotalPaginas(count);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en UnidadesHHPPMglBean: getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en UnidadesHHPPMglBean: getTotalPaginas() " + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    /**
     * Calcula el total de páginas
     * @param count  Total de registros
     * @return Total de paginas
     * @author Gildardo Mora
     */
    private int calcularTotalPaginas(int count) {
        return (count % filasPag != 0) ? (count / filasPag) + 1 : count / filasPag;
    }

    public void buscar() {
        try {
            params = new HashMap<>();

            params.put("all", "1");
            params.put("direccion", this.direccion.toUpperCase());
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al armar la consulta de CM:"+e.getMessage()+"", ""));

        }
    }

    public CmtCuentaMatrizMgl getCmtCuentaMatrizMgl() {
        return cmtCuentaMatrizMgl;
    }

    public void setCmtCuentaMatrizMgl(CmtCuentaMatrizMgl cmtCuentaMatrizMgl) {
        this.cmtCuentaMatrizMgl = cmtCuentaMatrizMgl;
    }

    public String getPageActual() {
        try {
            int totalPaginas = getTotalPaginas();
            paginaList = IntStream.rangeClosed(1, totalPaginas)
                    .boxed()
                    .collect(Collectors.toList());
            pageActual = String.format("%d de %d", actual, totalPaginas);
            numPagina = Optional.of(actual).map(String::valueOf).orElse("1");
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en UnidadesHHPPMglBean:getPageActual(). " + e.getMessage(), e, LOGGER);
        }

        return pageActual;
    }
        
    public void verDetalleHhpp(CmtPestaniaHhppDetalleDto pestaniaHhppDetalleDto) {
        try {
            HhppMgl hhppMglSeleccionado = pestaniaHhppDetalleDto.getHhppMglLista();
           CmtDireccionDetalladaMgl direccionDetalladaSeleccionada = cmtDireccionDetalleMglFacadeLocal.findByHhPP(hhppMglSeleccionado);
          
             
            if (hhppMglSeleccionado != null) {
                
                SubDireccionMgl subDireccionMgl = null;
                if (hhppMglSeleccionado.getSubDireccionObj() != null) {
                    subDireccionMgl = hhppMglSeleccionado.getSubDireccionObj();
                }
                List<HhppMgl> lstHhppMgl = hhppMglFacade.findByDirAndSubDir(hhppMglSeleccionado.getDireccionObj(), subDireccionMgl);

                HhppMgl hhppMglSel;

                if (lstHhppMgl != null && !lstHhppMgl.isEmpty()) {
                    hhppMglSel = lstHhppMgl.get(0);
                    hhppMglSeleccionado.setHhppExistente(true);
                    hhppMglSeleccionado.setHhppMgl(hhppMglSel);
                }
                //Si la direccion detallada seleccionada tiene hhpp.
                HhppDetalleSessionBean hhppDetalleSessionBean
                        = (HhppDetalleSessionBean) JSFUtil.getBean("hhppDetalleSessionBean");
                //Si la direccion detallada tiene un hhpp asociado
                if (hhppMglSeleccionado.isHhppExistente()
                        && hhppMglSeleccionado.getHhppMgl() != null) {
                    // Instacia Bean de Session para obtener el hhpp seleccionado                    
                    hhppDetalleSessionBean.setHhppSeleccionado(hhppMglSeleccionado);
                    hhppDetalleSessionBean.setDireccionDetalladaSeleccionada(direccionDetalladaSeleccionada);
                    FacesUtil.navegarAPagina("/view/MGL/VT/moduloHhpp/detalleHhpp.jsf");
                }

            } else {
                String msnError = "Ocurrio un error al seleccionar el registro, "
                        + "intente nuevamente por favor ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msnError, ""));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en verDetalleHhpp. al redireccionar a detalle de Hhpp. ", e, LOGGER);
        }
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

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public FiltroConsultaHhppTecDto getFiltroConsultaHhppTecDto() {
        return filtroConsultaHhppTecDto;
    }

    public void setFiltroConsultaHhppTecDto(FiltroConsultaHhppTecDto filtroConsultaHhppTecDto) {
        this.filtroConsultaHhppTecDto = filtroConsultaHhppTecDto;
    }

    public CmtPestaniaHhppDto getCmtPestaniaHhppDto() {
        return cmtPestaniaHhppDto;
    }

    public void setCmtPestaniaHhppDto(CmtPestaniaHhppDto cmtPestaniaHhppDto) {
        this.cmtPestaniaHhppDto = cmtPestaniaHhppDto;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public boolean isHabilitaObj() {
        return habilitaObj;
    }

    public void setHabilitaObj(boolean habilitaObj) {
        this.habilitaObj = habilitaObj;
    }

    public int getNumeroRegistrosConsulta() {
        return numeroRegistrosConsulta;
    }

    public void setNumeroRegistrosConsulta(int numeroRegistrosConsulta) {
        this.numeroRegistrosConsulta = numeroRegistrosConsulta;
    }

    public String getUsuarioVT() {
        return usuarioVT;
    }

    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    public int getPerfilVT() {
        return perfilVT;
    }

    public void setPerfilVT(int perfilVT) {
        this.perfilVT = perfilVT;
    }

    public HhppMgl getHhppMgl() {
        return hhppMgl;
    }

    public void setHhppMgl(HhppMgl hhppMgl) {
        this.hhppMgl = hhppMgl;
    }

    public boolean isPintarPaginado() {
        return pintarPaginado;
    }

    public void setPintarPaginado(boolean pintarPaginado) {
        this.pintarPaginado = pintarPaginado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public FiltroBusquedaDirecccionDto getFiltroBusquedaDirecccionDto() {
        return filtroBusquedaDirecccionDto;
    }

    public void setFiltroBusquedaDirecccionDto(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto) {
        this.filtroBusquedaDirecccionDto = filtroBusquedaDirecccionDto;
    }

}
