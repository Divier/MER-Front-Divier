/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.CmtPenetracionCMDto;
import co.com.claro.mgl.dtos.CmtPestanaPenetracionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.*;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author cardenaslb
 */
@ManagedBean(name = "penetracionMglBean")
@ViewScoped
public class PenetracionMglBean {

    private List<CmtBasicaMgl> listTablaBasicaEstadoCM;

    /**
     * @return the listaNodos
     */
    public List<NodoMgl> getListaNodos() {
        return listaNodos;
    }

    /**
     * @param listaNodos the listaNodos to set
     */
    public void setListaNodos(List<NodoMgl> listaNodos) {
        this.listaNodos = listaNodos;
    }

    /**
     * @return the nodoSeleccionado
     */
    public NodoMgl getNodoSeleccionado() {
        return nodoSeleccionado;
    }

    /**
     * @param nodoSeleccionado the nodoSeleccionado to set
     */
    public void setNodoSeleccionado(NodoMgl nodoSeleccionado) {
        this.nodoSeleccionado = nodoSeleccionado;
    }

    /**
     * @return the basicaIdEstadosTec
     */
    public CmtBasicaMgl getBasicaIdEstadosTec() {
        return basicaIdEstadosTec;
    }

    /**
     * @param basicaIdEstadosTec the basicaIdEstadosTec to set
     */
    public void setBasicaIdEstadosTec(CmtBasicaMgl basicaIdEstadosTec) {
        this.basicaIdEstadosTec = basicaIdEstadosTec;
    }

    private static final Logger LOGGER = LogManager.getLogger(PenetracionMglBean.class);
    private String usuarioVT = null;
    private int perfilVT = 0;
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    private final HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private CuentaMatrizBean cuentaMatrizBean;
    private CmtSubEdificioMgl subEdificioMgl;
    private List<CmtPenetracionCMDto> listaPenetracionTecnologias;
    private CmtPestanaPenetracionDto cmtPestanaPenetracionDto;
    private HashMap<String, Object> params;
    private int page;
    private int numeroRegistrosConsulta = 15;
    private String idVt;
    private String tecnologia;
    private String estadoTecnologia;
    private int cantHhpp;
    private int totalClientes;
    private float rentaMensual;
    private float costoAcometida;
    private float costoVT;
    private int actual;
    private String numPagina = "1";
    private boolean pintarPaginado = true;
    private int filasPag = ConstantsCM.PAGINACION_OCHO_FILAS;
    private List<Integer> paginaList;
    private String pageActual;
    private List<CmtBasicaMgl> tecnologiaList;
    private List<CmtOpcionesRolMgl> opcionesRol;

    @EJB
    private CmtTecnologiaSubMglFacadeLocal cmtTecnologiaSubMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal operacionesRolFacade;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private CmtInfoTecnicaMglFacadeLocal cmtInfoTecnicaMglFacadeLocal;

    public PenetracionMglBean() {
        try {
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                limpiar();
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
            this.cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            subEdificioMgl = this.cuentaMatrizBean.getSelectedCmtSubEdificioMgl();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al PenetracionMglBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PenetracionMglBean. " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void cargarListas() {
        try {
            this.cuentaMatrizBean = (CuentaMatrizBean) JSFUtil.getSessionBean(CuentaMatrizBean.class);
            subEdificioMgl = cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
            cmtPestanaPenetracionDto = new CmtPestanaPenetracionDto();
            cmtTecnologiaSubMglFacadeLocal.setUser(usuarioVT, perfilVT);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al PenetracionMglBean:cargarListas(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PenetracionMglBean:cargarListas() " + e.getMessage(), e, LOGGER);
        }
        buscar();
        loadTechnology();
        loadEstadosCCMM();
        cargarOpcionesROLAddTecnologia();
        listInfoByPage(1);
    }

    public void cargarOpcionesROLAddTecnologia() {
        try {
            opcionesRol = operacionesRolFacade.consultarOpcionesRol("CCMM_ADD_TEC");
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
            for (CmtOpcionesRolMgl cmtOpcionesRolMgl : opcionesRol) {
                cargarAddTecnologia = ValidacionUtil.validarVisualizacion("CCMM_ADD_TEC", "CCMM_ADD_TEC", operacionesRolFacade, securityLogin);
            }
        } catch (IOException | ApplicationException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public void loadEstadosCCMM() {
        try {
            //Consulta los Estado De Cuenta Matriz
            CmtTipoBasicaMgl tipoBasicaEstadosCm;
            tipoBasicaEstadosCm = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
            setListTablaBasicaEstadoCM(cmtBasicaMglFacadeLocal.
                    findByTipoBasica(tipoBasicaEstadosCm));
        } catch (ApplicationException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public void loadTechnology() {
        try {
            tecnologiaList = new ArrayList<>();

            tecnologiaList = cmtBasicaMglFacadeLocal.
                    findByTipoBasica(cmtTipoBasicaMglFacadeLocal.
                            findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA));

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en loadTechnology. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en loadTechnology. ", e, LOGGER);
        }
    }

    public void loadNodeCenters() {
        try {
            listaNodos = new ArrayList<>();
            CmtBasicaMgl tec = cmtBasicaMglFacadeLocal.findById(new BigDecimal(tecnologiasStr));
            listaNodos = nodoMglFacadeLocal.findNodosCentroPobladoAndTipoTecnologia(subEdificioMgl.getCmtCuentaMatrizMglObj().getCentroPoblado().getGpoId(), tec);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en loadNodeCenters. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en loadNodeCenters. ", e, LOGGER);
        }
    }

    private String listInfoByPage(int page) {
        try {
            actual = page;
            listaPenetracionTecnologias = new ArrayList<>();
            this.cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            if (subEdificioMgl != null) {
                if (subEdificioMgl.getEstadoSubEdificioObj().getBasicaId().equals(
                        cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId())) {
                    cmtPestanaPenetracionDto = cmtTecnologiaSubMglFacadeLocal.findListTecnologiasPenetracionSubCM(params, page, numeroRegistrosConsulta, subEdificioMgl,
                            Constant.FIND_HHPP_BY.CUENTA_MATRIZ);
                } else {
                    cmtPestanaPenetracionDto = cmtTecnologiaSubMglFacadeLocal.findListTecnologiasPenetracionSubCM(params, page, numeroRegistrosConsulta, subEdificioMgl,
                            Constant.FIND_HHPP_BY.SUB_EDIFICIO);
                }
            }
            listaPenetracionTecnologias = cmtPestanaPenetracionDto.getListaPenetracionTecnologias();
            if (listaPenetracionTecnologias == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No se encontraron resultados para la consulta.", ""));
                return "";
            }

        } catch (ApplicationException e) {
            String msn2 = "Error al PenetracionMglBean. " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en PenetracionMglBean:listInfoByPage(). " + e.getMessage(), e, LOGGER);
        }
        return "";
    }

    public void buscar() {
        try {
            params = new HashMap<>();

            params.put("all", "1");
            params.put("idVt", this.idVt);
            params.put("tecnologia", this.tecnologia);
            params.put("cantHhpp", this.cantHhpp);
            params.put("totalClientes", this.totalClientes);
            params.put("rentaMensual", this.rentaMensual);
            params.put("estadoTecnologia", this.estadoTecnologia);
            params.put("costoAcometida", this.costoAcometida);
            params.put("costoVT", this.costoVT);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al armar la consulta de CM.", ""));

        }
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la primera página. EX000 " + ex.getMessage(), ex);
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
            LOGGER.error("Error al ir a la página anterior. EX000 " + ex.getMessage(), ex);
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
            FacesUtil.mostrarMensajeError("Error en PenetracionMglBean:irPagina(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en PenetracionMglBean:irPagina(). " + e.getMessage(), e, LOGGER);
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
            LOGGER.error("Error al ir a la siguiente página EX000 " + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la última página. EX000 " + ex.getMessage(), ex);
        }

    }

    public int getTotalPaginas() {
        try {
            buscar();
            int count = 0;
            int totalPaginas = 0;
            if (subEdificioMgl != null && subEdificioMgl.getListHhpp() != null) {
                if (subEdificioMgl.getEstadoSubEdificioObj().getBasicaId().equals(
                        cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId())) {

                    count = cmtTecnologiaSubMglFacadeLocal.countListPenetracionTecCM(params, subEdificioMgl, Constant.FIND_HHPP_BY.CUENTA_MATRIZ);

                } else {
                    count = cmtTecnologiaSubMglFacadeLocal.countListPenetracionTecSub(params, subEdificioMgl, Constant.FIND_HHPP_BY.SUB_EDIFICIO);
                }

            }

            totalPaginas = (int) ((count % filasPag != 0) ? (count / filasPag) + 1 : count / filasPag);
            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al PenetracionMglBean:getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al PenetracionMglBean:getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    public String getPageActual() {
        try {
            paginaList = new ArrayList<>();
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
            FacesUtil.mostrarMensajeError("Error al PenetracionMglBean:getPageActual(). " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public boolean renderAgregar() {
        return renderAgregar && cargarAddTecnologia;
    }

    public boolean renderAceptarCancelar() {
        return renderAceptarCancelar && cargarAddTecnologia;
    }

    public boolean renderColumnaEditar() {
        return cargarAddTecnologia;
    }

    boolean renderAceptarCancelar = false;
    private boolean renderAceptarCancelarTabla = false;
    boolean renderAgregar = true;
    private String tecnologiasStr;
    private String nodoStr;
    private BigDecimal estadoStr;
    private CmtPenetracionCMDto penetracionEdit;
    public boolean cargarAddTecnologia;
    private CmtBasicaMgl basicaIdEstadosTec;
    private CmtBasicaMgl tecnologiaSeleccionada;

    private List<NodoMgl> listaNodos;
    private NodoMgl nodoSeleccionado;
    private boolean renderEditarTecnologia = false;

    public void addTecnologia() {
        renderAceptarCancelar = true;
        renderAgregar = false;
    }

    public void aceptarAddTecnologia() {
        try {
            validarDatos();

            listTablaBasicaEstadoCM.stream()
                    .filter((cmtBasicaMgl) -> (cmtBasicaMgl.getBasicaId().equals(estadoStr)))
                    .forEachOrdered((cmtBasicaMgl) -> basicaIdEstadosTec = cmtBasicaMgl);

            if (subEdificioMgl.getCuentaMatrizObj().getSubEdificioGeneral().getSubEdificioId().equals(subEdificioMgl.getSubEdificioId())) {
                CmtTecnologiaSubMgl tecnologiaSubMgl = cmtTecnologiaSubMglFacadeLocal.crearTecnSubXPenetracion(subEdificioMgl, nodoSeleccionado, basicaIdEstadosTec, usuarioVT, perfilVT);
                crearInfoTecnica(subEdificioMgl, tecnologiaSubMgl);
            } else {
                CmtTecnologiaSubMgl tecnologiaSubMgl;
                CmtBasicaMgl tec = new CmtBasicaMgl();
                tec.setBasicaId(new BigDecimal(tecnologiasStr));
                CmtTecnologiaSubMgl tecGeneral = cmtTecnologiaSubMglFacadeLocal.findBySubEdificioTecnologia(subEdificioMgl.getCmtCuentaMatrizMglObj().getSubEdificioGeneral(), tec);
                if (Objects.isNull(tecGeneral) || tecGeneral.getTecnoSubedificioId() == null) {
                    CmtBasicaMgl estadoEdifGeneral = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
                    tecnologiaSubMgl = cmtTecnologiaSubMglFacadeLocal.crearTecnSubXPenetracion(subEdificioMgl.getCmtCuentaMatrizMglObj().getSubEdificioGeneral(), nodoSeleccionado, estadoEdifGeneral, usuarioVT, perfilVT);
                } else {
                    tecnologiaSubMgl = cmtTecnologiaSubMglFacadeLocal.crearTecnSubXPenetracion(subEdificioMgl, nodoSeleccionado, basicaIdEstadosTec, usuarioVT, perfilVT);
                }
                crearInfoTecnica(subEdificioMgl, tecnologiaSubMgl);
            }
            listInfoByPage(1);
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al Agregar La nueva tecnologia " + ex.getMessage(), ex);
        }
        limpiar();
    }

    public void limpiar() {
        tecnologiasStr = "";
        tecnologiaSeleccionada = new CmtBasicaMgl();
        nodoSeleccionado = new NodoMgl();
        nodoStr = "";
        estadoStr = null;
        renderAceptarCancelar = false;
        renderAgregar = true;
    }

    public void validarDatos() throws ApplicationException {
        if (tecnologiasStr == null || tecnologiasStr.isEmpty()) {
            throw new ApplicationException("Seleccione una tecnologia por favor");
        }

        tecnologiasStr = tecnologiasStr.trim();

        if (estadoStr == null) {
            throw new ApplicationException("Seleccione un estado por favor.");
        }
        if (nodoStr == null || nodoStr.isEmpty()) {
            throw new ApplicationException("Ingrese un nodo por favor.");
        }

        nodoStr = nodoStr.trim();

        nodoSeleccionado = nodoMglFacadeLocal.findByCodigo(nodoStr.trim());
        tecnologiaSeleccionada = cmtBasicaMglFacadeLocal.findById(new BigDecimal(tecnologiasStr.trim()));

        Optional<CmtTecnologiaSubMgl> optTec = cmtTecnologiaSubMglFacadeLocal.findTecnoSubBySubEdi(subEdificioMgl).parallelStream()
                .filter(tec -> tec.getBasicaIdTecnologias().getBasicaId().equals(tecnologiaSeleccionada.getBasicaId()))
                .findFirst();

        if (optTec.isPresent()) {
            throw new ApplicationException("La tecnologia ya esta agregada para la CCMM");
        }

        if (nodoSeleccionado == null || !nodoSeleccionado.getGpoId().equals(subEdificioMgl.getCmtCuentaMatrizMglObj().getCentroPoblado().getGpoId())) {
            throw new ApplicationException("El Nodo No corresponde Centro Poblado o a la Tecnologia");
        }

        if (!nodoSeleccionado.getNodTipo().getBasicaId().equals(tecnologiaSeleccionada.getBasicaId())) {
            throw new ApplicationException("El Nodo No corresponde Centro Poblado o a la Tecnologia");
        }

        if (!subEdificioMgl.getCuentaMatrizObj().isUnicoSubEdificioBoolean()
                && !subEdificioMgl.getCuentaMatrizObj().getSubEdificioGeneral().getSubEdificioId().equals(subEdificioMgl.getSubEdificioId())
                && nodoSeleccionado.getNodTipo().getIdentificadorInternoApp().equals(Constant.RED_FO)) {
            throw new ApplicationException("El Nodo con red FO no puede ser asignado a torres solo a edificio General");
        }

        if (!subEdificioMgl.getCuentaMatrizObj().isUnicoSubEdificioBoolean()
                && subEdificioMgl.getCuentaMatrizObj().getSubEdificioGeneral().getSubEdificioId().equals(subEdificioMgl.getSubEdificioId())
                && !nodoSeleccionado.getNodTipo().getIdentificadorInternoApp().equals(Constant.RED_FO)) {
            throw new ApplicationException("Solo se puede agregar Nodo con RED FO al edificio General");
        }
    }

    public void cancelAddTecnologia() {
        tecnologiasStr = "";
        tecnologiaSeleccionada = new CmtBasicaMgl();
        nodoSeleccionado = new NodoMgl();
        nodoStr = "";
        estadoStr = null;
        renderAceptarCancelar = false;
        renderAgregar = true;
    }

    public boolean verificarRenderAceCan(CmtPenetracionCMDto penetracion) {
        return renderAceptarCancelarTabla && penetracionEdit != null && penetracion.equals(penetracionEdit);
    }

    public void editarTecnologia(CmtPenetracionCMDto penetracion) throws ApplicationException {

        if (!subEdificioMgl.getCuentaMatrizObj().isUnicoSubEdificioBoolean()) {
            //JDHT si es el edificio general, no permitir editar, solo a RED FO
            if (subEdificioMgl.getCuentaMatrizObj().getSubEdificioGeneral().getSubEdificioId().
                    equals(subEdificioMgl.getSubEdificioId())) {

                if (penetracion.getTecnologiaBasicaId() != null) {
                    CmtBasicaMgl cmtBasicaTecnologiaSeleccionada
                            = cmtBasicaMglFacadeLocal.findById(penetracion.getTecnologiaBasicaId());

                    if (cmtBasicaTecnologiaSeleccionada != null) {

                        if (cmtBasicaTecnologiaSeleccionada.getIdentificadorInternoApp() != null) {

                            if (!cmtBasicaTecnologiaSeleccionada.getIdentificadorInternoApp().equalsIgnoreCase(Constant.RED_FO)) {
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                "Para la torre general solo es editable la tecnología RED FO.", ""));
                                return;
                            } else {
                                setRenderAceptarCancelarTabla(true);
                                penetracionEdit = penetracion;
                                estadoStr = penetracionEdit.getCmtTecnologiaSubMgl().getBasicaIdEstadosTec().getBasicaId();
                                nodoStr = penetracionEdit.getCmtTecnologiaSubMgl().getNodoId().getNodCodigo();
                            }

                        } else {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                            "La tecnología del registro seleccionado no tiene Identificador App en la base de datos. No es posible editar", ""));
                            return;
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "No se encontró la tecnología del registro seleccionado", ""));
                        return;
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "No se encontró la tecnología del registro seleccionado para editar", ""));
                    return;
                }

            } else {

                setRenderAceptarCancelarTabla(true);
                penetracionEdit = penetracion;
                estadoStr = penetracionEdit.getCmtTecnologiaSubMgl().getBasicaIdEstadosTec().getBasicaId();
                nodoStr = penetracionEdit.getCmtTecnologiaSubMgl().getNodoId().getNodCodigo();
            }
        } else {

            setRenderAceptarCancelarTabla(true);
            penetracionEdit = penetracion;
            estadoStr = penetracionEdit.getCmtTecnologiaSubMgl().getBasicaIdEstadosTec().getBasicaId();
            if (penetracionEdit.getCmtTecnologiaSubMgl() != null && penetracionEdit.getCmtTecnologiaSubMgl().getNodoId() != null) {
                nodoStr = penetracionEdit.getCmtTecnologiaSubMgl().getNodoId().getNodCodigo();
            }
        }
    }

    public void editarOKTecnologia(CmtPenetracionCMDto penetracion) {
        try {
            if (nodoStr.isEmpty() || estadoStr == null || penetracionEdit == null) {
                FacesUtil.mostrarMensajeError("Se debe Seleccionar un estado y un nodo", null);
                limpiar();
                return;
            }
            setRenderAceptarCancelarTabla(false);
            nodoStr = nodoStr.trim();
            penetracionEdit = penetracion;
            nodoSeleccionado = nodoMglFacadeLocal.findByCodigo(nodoStr);
            listTablaBasicaEstadoCM.stream().filter((cmtBasicaMgl) -> (cmtBasicaMgl.getBasicaId().equals(estadoStr))).forEachOrdered((cmtBasicaMgl) -> {
                basicaIdEstadosTec = cmtBasicaMgl;
            });

            if (nodoSeleccionado == null || !nodoSeleccionado.getGpoId().equals(subEdificioMgl.getCmtCuentaMatrizMglObj().getCentroPoblado().getGpoId())) {
                FacesUtil.mostrarMensajeError("El Nodo No corresponde al Centro Poblado", null);
                limpiar();
                return;
            }

            if (penetracionEdit.getTecnologiaBasicaId() != null
                    && !penetracionEdit.getTecnologiaBasicaId().equals(BigDecimal.ZERO)) {
                if (!nodoSeleccionado.getNodTipo().getBasicaId()
                        .equals(penetracionEdit.getTecnologiaBasicaId())) {
                    FacesUtil.mostrarMensajeError("El Nodo No corresponde a la Tecnologia", null);
                    limpiar();
                    return;
                }
            }

            CmtTecnologiaSubMgl cmtTecnologiaSubMgl = penetracionEdit.getCmtTecnologiaSubMgl();
            cmtTecnologiaSubMgl.setNodoId(nodoSeleccionado);
            cmtTecnologiaSubMgl.setBasicaIdEstadosTec(getBasicaIdEstadosTec());
            CmtTecnologiaSubMgl cmtTecnologiaSubMglUpdate = cmtTecnologiaSubMglFacadeLocal.updateTecnoSub(cmtTecnologiaSubMgl, usuarioVT, perfilVT);
            if (cmtTecnologiaSubMglUpdate != null) {
                penetracionEdit = null;
                nodoStr = "";
                estadoStr = null;
                listInfoByPage(1);
                String msn = "Proceso exitoso, se ha actualizado la tecnologia ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            } else {
                FacesUtil.mostrarMensajeError("Ocurrio un error editando la Tecnologia", null);
            }

        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al actualizar Tecnologia " + ex.getMessage(), ex);
        }

    }

    public void editarCancelTecnologia(CmtPenetracionCMDto penetracion) {
        setRenderAceptarCancelarTabla(false);
        penetracionEdit = null;
        nodoStr = "";
        estadoStr = null;
    }

    public void crearInfoTecnica(CmtSubEdificioMgl subEdificioMgl,
                                 CmtTecnologiaSubMgl tecnologiaSubMgl) throws ApplicationException {

        CmtBasicaMgl cmtBasicaMglInfoN1Nodo = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.NIVEL_UNO_NODO);
        CmtBasicaMgl cmtBasicaMglInfoN1Nap = cmtBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.NIVEL_UNO_NAP);

        if (tecnologiaSubMgl != null
                && tecnologiaSubMgl.getTecnoSubedificioId() != null) {
            CmtInfoTecnicaMgl cmtInfoTecni = new CmtInfoTecnicaMgl();

            cmtInfoTecni.setIdSubedificio(subEdificioMgl);

            if (tecnologiaSubMgl.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_FOG")) {
                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nap);
            } else if (tecnologiaSubMgl.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_FOU")) {
                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nap);
            } else if (tecnologiaSubMgl.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_HOST")) {
                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
            } else if (tecnologiaSubMgl.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_BI")) {
                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
            } else if (tecnologiaSubMgl.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_DTH")) {
                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
            } else if (tecnologiaSubMgl.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_FTH")) {
                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
            } else if (tecnologiaSubMgl.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_UNI")) {
                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
            } else if (tecnologiaSubMgl.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_FOR")) {
                cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nap);
            }

            if (cmtInfoTecni != null && cmtInfoTecni.getBasicaIdInfoN1() != null) {

                cmtInfoTecni.setFechaCreacion(new Date());
                cmtInfoTecni.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
                cmtInfoTecni.setPerfilEdicion(0);
                cmtInfoTecni.setTecnologiaSubMglObj(tecnologiaSubMgl);
                cmtInfoTecnicaMglFacadeLocal.setUser(usuarioVT, perfilVT);
                cmtInfoTecni = cmtInfoTecnicaMglFacadeLocal.create(cmtInfoTecni);

            }
        }
    }

    public CuentaMatrizBean getCuentaMatrizBean() {
        return cuentaMatrizBean;
    }

    public void setCuentaMatrizBean(CuentaMatrizBean cuentaMatrizBean) {
        this.cuentaMatrizBean = cuentaMatrizBean;
    }

    public CmtSubEdificioMgl getSubEdificioMgl() {
        return subEdificioMgl;
    }

    public void setSubEdificioMgl(CmtSubEdificioMgl subEdificioMgl) {
        this.subEdificioMgl = subEdificioMgl;
    }

    public List<CmtPenetracionCMDto> getListaPenetracionTecnologias() {
        return listaPenetracionTecnologias;
    }

    public void setListaPenetracionTecnologias(List<CmtPenetracionCMDto> listaPenetracionTecnologias) {
        this.listaPenetracionTecnologias = listaPenetracionTecnologias;
    }

    public CmtPestanaPenetracionDto getCmtPestanaPenetracionDto() {
        return cmtPestanaPenetracionDto;
    }

    public void setCmtPestanaPenetracionDto(CmtPestanaPenetracionDto cmtPestanaPenetracionDto) {
        this.cmtPestanaPenetracionDto = cmtPestanaPenetracionDto;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNumeroRegistrosConsulta() {
        return numeroRegistrosConsulta;
    }

    public void setNumeroRegistrosConsulta(int numeroRegistrosConsulta) {
        this.numeroRegistrosConsulta = numeroRegistrosConsulta;
    }

    public String getIdVt() {
        return idVt;
    }

    public void setIdVt(String idVt) {
        this.idVt = idVt;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getEstadoTecnologia() {
        return estadoTecnologia;
    }

    public void setEstadoTecnologia(String estadoTecnologia) {
        this.estadoTecnologia = estadoTecnologia;
    }

    public int getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(int totalClientes) {
        this.totalClientes = totalClientes;
    }

    public float getRentaMensual() {
        return rentaMensual;
    }

    public void setRentaMensual(float rentaMensual) {
        this.rentaMensual = rentaMensual;
    }

    public float getCostoAcometida() {
        return costoAcometida;
    }

    public void setCostoAcometida(float costoAcometida) {
        this.costoAcometida = costoAcometida;
    }

    public float getCostoVT() {
        return costoVT;
    }

    public void setCostoVT(float costoVT) {
        this.costoVT = costoVT;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    /**
     * @return the tecnologiaList
     */
    public List<CmtBasicaMgl> getTecnologiaList() {
        return tecnologiaList;
    }

    /**
     * @return the tecnologiasStr
     */
    public String getTecnologiasStr() {
        return tecnologiasStr;
    }

    /**
     * @param tecnologiasStr the tecnologiasStr to set
     */
    public void setTecnologiasStr(String tecnologiasStr) {
        this.tecnologiasStr = tecnologiasStr;
    }

    /**
     * @return the nodoStr
     */
    public String getNodoStr() {
        return nodoStr;
    }

    /**
     * @param nodoStr the nodoStr to set
     */
    public void setNodoStr(String nodoStr) {
        this.nodoStr = nodoStr;
    }

    public BigDecimal getEstadoStr() {
        return estadoStr;
    }

    public void setEstadoStr(BigDecimal estadoStr) {
        this.estadoStr = estadoStr;
    }

    /**
     * @return the penetracionEdit
     */
    public CmtPenetracionCMDto getPenetracionEdit() {
        return penetracionEdit;
    }

    /**
     * @param penetracionEdit the penetracionEdit to set
     */
    public void setPenetracionEdit(CmtPenetracionCMDto penetracionEdit) {
        this.penetracionEdit = penetracionEdit;
    }

    /**
     * @return the renderAceptarCancelarTabla
     */
    public boolean isRenderAceptarCancelarTabla() {
        return renderAceptarCancelarTabla;
    }

    /**
     * @param renderAceptarCancelarTabla the renderAceptarCancelarTabla to set
     */
    public void setRenderAceptarCancelarTabla(boolean renderAceptarCancelarTabla) {
        this.renderAceptarCancelarTabla = renderAceptarCancelarTabla;
    }

    /**
     * @return the tecnologiaSeleccionada
     */
    public CmtBasicaMgl getTecnologiaSeleccionada() {
        return tecnologiaSeleccionada;
    }

    /**
     * @param tecnologiaSeleccionada the tecnologiaSeleccionada to set
     */
    public void setTecnologiaSeleccionada(CmtBasicaMgl tecnologiaSeleccionada) {
        this.tecnologiaSeleccionada = tecnologiaSeleccionada;
    }

    /**
     * @return the listTablaBasicaEstadoCM
     */
    public List<CmtBasicaMgl> getListTablaBasicaEstadoCM() {
        return listTablaBasicaEstadoCM;
    }

    /**
     * @param listTablaBasicaEstadoCM the listTablaBasicaEstadoCM to set
     */
    public void setListTablaBasicaEstadoCM(List<CmtBasicaMgl> listTablaBasicaEstadoCM) {
        this.listTablaBasicaEstadoCM = listTablaBasicaEstadoCM;
    }

    public boolean isRenderEditarTecnologia() {
        return renderEditarTecnologia;
    }

    public void setRenderEditarTecnologia(boolean renderEditarTecnologia) {
        this.renderEditarTecnologia = renderEditarTecnologia;
    }

}
