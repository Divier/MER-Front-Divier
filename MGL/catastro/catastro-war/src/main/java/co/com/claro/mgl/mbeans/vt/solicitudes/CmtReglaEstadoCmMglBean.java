package co.com.claro.mgl.mbeans.vt.solicitudes;

import co.com.claro.mgl.businessmanager.cm.CmtReglaEstadoCmMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtReglaEstadoCmMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtReglaEstadoCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@ViewScoped
@ManagedBean(name = "cmtReglaEstadoCmMglBean")
public class CmtReglaEstadoCmMglBean {

    @Getter
    @Setter
    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    @Getter
    @Setter
    private String usuarioVT = null;
    @Getter
    @Setter
    private int perfilVt = 0;
    private String cedulaUsuarioVT = null;
    private static final Logger LOGGER = LogManager.getLogger(CmtReglaEstadoCmMglBean.class);

    @Getter
    @Setter
    private CmtBasicaMgl estado;
    @Getter
    @Setter
    private CmtBasicaMgl estadoCombinado;
    @Getter
    @Setter
    private CmtBasicaMgl tecnologia;
    @Getter
    @Setter
    private List<CmtBasicaMgl> tipoEstadosList;
    @Getter
    @Setter
    private List<CmtBasicaMgl> tipoEstadosCombinadoList;
    @Getter
    @Setter
    private List<CmtBasicaMgl> tipoTecnologiaBasicaList;
    @Getter
    @Setter
    private List<CmtReglaEstadoCmMgl> ReglasEstadoList;
    @Getter
    @Setter
    private List<CmtReglaEstadoCmMgl> ReglasEstadoTmpList;
    @Getter
    @Setter
    private BigDecimal basicaIdEstado;
    @Getter
    @Setter
    private BigDecimal basicaIdTecnologia;

    @Getter
    @Setter
    private BigDecimal basicaIdEstadoComb;

    @Getter
    @Setter
    private String pageActual;

    @Getter
    @Setter
    private String numPagina = "1";

    @Getter
    @Setter
    private String inicioPagina = "<< - Inicio";

    @Getter
    @Setter
    private String anteriorPagina = "< - Anterior";

    @Getter
    @Setter
    private String finPagina = "Fin - >>";

    @Getter
    @Setter
    private String siguientePagina = "Siguiente - >";

    @Getter
    @Setter
    private List<Integer> paginaList;

    @Getter
    @Setter
    private int actual;

    @Getter
    @Setter
    private int filasPag = ConstantsCM.PAGINACION_OCHO_FILAS;

    @Getter
    @Setter
    private String regresarMenu = "<- Regresar Menú";

    @Getter
    @Setter
    private String tipoFiltro;

    @Getter
    @Setter
    private String contentFiltro;

    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtReglaEstadoCmMglFacadeLocal cmtReglaEstadoCmMglFacadeLocal;

    public CmtReglaEstadoCmMglBean() throws ApplicationException, IOException {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                response.sendRedirect(SecurityLogin.SERVER_PARA_AUTENTICACION);
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();
            cedulaUsuarioVT = securityLogin.getIdUser();

            if (usuarioVT == null) {
                response.sendRedirect(SecurityLogin.SERVER_PARA_AUTENTICACION);
            }
        } catch (IOException e) {
            mostrarMensaje("Se genera error Validando Autenticacion" + e.getMessage(), "ERROR");
        }
    }

    @PostConstruct
    public void init() {
        inicializarVariables();
        initForm();
    }

    private void inicializarVariables() {
        this.tipoEstadosList = new ArrayList<>();
        this.tipoEstadosCombinadoList = new ArrayList<>();
        this.tipoTecnologiaBasicaList = new ArrayList<>();
        this.ReglasEstadoList = new ArrayList<>();
        this.ReglasEstadoTmpList = new ArrayList<>();
        this.estado = null;
        this.estadoCombinado = null;
        this.tecnologia = null;
        this.basicaIdEstado = null;
        this.basicaIdEstadoComb = null;
        this.basicaIdTecnologia = null;
        this.tipoFiltro = null;
        this.contentFiltro = "";
        initRules();
    }

    public void initRules() {
        SolicitudSessionBean solicitudSessionBean = JSFUtil.getSessionBean(SolicitudSessionBean.class);
        int page = solicitudSessionBean.getPaginaActual();
        listInfoByPage(page == 0 ? 1 : page);
    }


    // Metodo encargado de realizar respectivas consultas para el formulario
    private void initForm() {
        consultaTecnologias();
        consultaEstados();
    }

    private void consultaTecnologias() {
        try {
            //Obtiene valores de tipo de tecnología
            CmtTipoBasicaMgl tipoBasicaTipoTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
            tipoTecnologiaBasicaList = mapperList(cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoTecnologia), "TEC");
        } catch (ApplicationException e) {
            mostrarMensaje("Error al obtener el tipo de tecnología" + e.getMessage(), "ERROR");
        }
    }

    private void consultaEstados() {
        try {
            CmtTipoBasicaMgl tipoBasicaEstado = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
            tipoEstadosList = mapperList(cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaEstado), "EST");
            tipoEstadosCombinadoList = mapperList(cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaEstado), "ESTC");
        } catch (ApplicationException e) {
            mostrarMensaje("Error al obtener el tipo de estado" + e.getMessage(), "ERROR");
        }
    }

    // Metodo encargado de obtener las reglas activas (estado =  1)
    private void consultaReglasActivas() {
        try {
            this.ReglasEstadoList = new ArrayList<>();
            this.ReglasEstadoList = cmtReglaEstadoCmMglFacadeLocal.findAllByStates(1);
        } catch (ApplicationException e) {
            mostrarMensaje("Error al obtener el las reglas activas " + e.getMessage(), "ERROR");
        }
    }

    // Metodo encargado de registrar la regla
    public void registrarRegla() {
        try {
            // VALIDAR LAS REGLAS
            if (validaCampos()
                    && validarReglaRegitrar()) {
                if (obtenerEstadoCmRrByReglaEstado(ReglasEstadoTmpList) == null) {
                    this.estadoCombinado = cmtBasicaMglFacadeLocal.findById(this.basicaIdEstadoComb);
                    cmtReglaEstadoCmMglFacadeLocal.setUser(usuarioVT, perfilVt);
                    // ADICION DEL ESTADO COMBINADO
                    ReglasEstadoTmpList.forEach((obj) -> {
                        obj.setEstadoCmBasicaId(estadoCombinado);
                    });
                    // REGISTRO DE REGLAS ENVIOANDO LA LISTA    
                    String result = cmtReglaEstadoCmMglFacadeLocal.addRule(ReglasEstadoTmpList);
                    if (!result.isEmpty()) {
                        String msn = "Regla " + result + " creada exitosamente.";
                        mostrarMensaje(msn, "SUCCESS");
                        init();
                    } else {
                        String msn = "No se pudo crear la regla.";
                        mostrarMensaje(msn, "ERROR");
                    }
                } else {
                    String msn = "No se pudo crear la regla, ya existe una regla con los mismos estados combinados";
                    mostrarMensaje(msn, "WARN");
                }

            }
        } catch (ApplicationException e) {
            mostrarMensaje("Error al registrar las reglas activas " + e.getMessage(), "ERROR");
        }
    }

    // Metodo encargado de adicionar a la lista Regla el estado combinado ( minimo 2 )
    public void adicionarEstadoCombinado() {
        try {
            if (this.basicaIdEstado != null && this.basicaIdTecnologia != null) {
                CmtReglaEstadoCmMgl estadoCombinadoTmp = new CmtReglaEstadoCmMgl();
                estadoCombinadoTmp.setEstadoTecBasicaId(cmtBasicaMglFacadeLocal.findById(basicaIdEstado));
                estadoCombinadoTmp.setTecnologiaBasicaId(cmtBasicaMglFacadeLocal.findById(basicaIdTecnologia));
                if (estadoCombinadoTmp.getEstadoTecBasicaId() != null && estadoCombinadoTmp.getTecnologiaBasicaId() != null) {
                    if (validarReglas(estadoCombinadoTmp)) {
                        this.ReglasEstadoTmpList.add(estadoCombinadoTmp);
                        this.basicaIdEstado = null;
                        this.basicaIdTecnologia = null;
                        mostrarMensaje("Estado combinado adicionado correctamente.", "SUCCESS");

                    }
                } else {
                    mostrarMensaje("Error al obtener el estado o la tecnologia", "WARN");
                }
            } else {
                String msn = "Se debe seleccionar de la lista";
                if (this.basicaIdEstado == null) {
                    msn += "Estado una opcion.";
                    mostrarMensaje(msn, "WARN");
                    return;
                }
                if (this.basicaIdTecnologia == null) {
                    msn += "Tecnologia una opcion.";
                    mostrarMensaje(msn, "WARN");
                }
            }
        } catch (ApplicationException e) {
            mostrarMensaje("Error al adicionar el estado combinado " + e.getMessage(), "ERROR");
        }
    }

    // Metodo encargado de validar que las reglas que se van adicionando no se repitan entre si
    private Boolean validarReglas(CmtReglaEstadoCmMgl estadoCombinado) {
        try {
            if (this.ReglasEstadoTmpList != null && !this.ReglasEstadoTmpList.isEmpty()) {
                for (CmtReglaEstadoCmMgl obj : this.ReglasEstadoTmpList) {
                    if (obj.getEstadoTecBasicaId().getBasicaId().equals(estadoCombinado.getEstadoTecBasicaId().getBasicaId())
                            && obj.getTecnologiaBasicaId().getBasicaId().equals(estadoCombinado.getTecnologiaBasicaId().getBasicaId())) {
                        String msn = "No se puede adicionar el Estado Combinado, Ya existe uno similar.";
                        mostrarMensaje(msn, "WARN");
                        return Boolean.FALSE;
                    }
                }
            }

        } catch (Exception e) {
            mostrarMensaje("Error al validar los estados combinados a adicionar " + e.getMessage(), "ERROR");
        }
        return Boolean.TRUE;
    }

    // Metodo encargado de validar los estados combinados de una regla para registrar
    private Boolean validarReglaRegitrar() {
        try {
            if (ReglasEstadoTmpList == null || this.ReglasEstadoTmpList.isEmpty()) {
                String msn = "No se ha seleccionado estados para la Regla.";
                mostrarMensaje(msn, "WARN");
                return Boolean.FALSE;
            }
            if (ReglasEstadoTmpList.size() <= 1) {
                String msn = "No se puede registrar la regla, minimo de registros por regla: 2";
                mostrarMensaje(msn, "WARN");
                return Boolean.FALSE;
            }
        } catch (Exception e) {
            mostrarMensaje("Error al validar la regla a registrar " + e.getMessage(), "ERROR");
        }
        return Boolean.TRUE;
    }

    // Metodo encargado de eliminar la regla 
    public void eliminarRegla(CmtReglaEstadoCmMgl combinedState) {
        try {
            if (combinedState != null) {
                if (cmtReglaEstadoCmMglFacadeLocal.disabledRule(combinedState.getNumeroRegla())) {
                    consultaReglasActivas();
                    mostrarMensaje("Regla " + combinedState.getNumeroRegla() + " deshabilitada con exito.", "SUCCESS");
                }
            }
        } catch (ApplicationException e) {
            mostrarMensaje("Error al intentar deshabilitar la regla" + e.getMessage(), "ERROR");
        } catch (Exception e) {
            mostrarMensaje("Error al intentar deshabilitar la regla " + e.getMessage(), "ERROR");
        }
    }

    // Metodo encargado de eliminar la lista de estados combinados temporal
    public void eliminarEstado(CmtReglaEstadoCmMgl combinedState) {
        try {
            if (combinedState != null) {
                this.ReglasEstadoTmpList.remove(combinedState);
                mostrarMensaje("Estado combinado removido con exito.", "SUCCESS");
            } else {
                mostrarMensaje("Fallo al remover el estado combinado.", "WARN");
            }
        } catch (Exception e) {
            mostrarMensaje("Error al intentar remover estado combinado de la lista " + e.getMessage(), "ERROR");
        }
    }

    // Metodo encargado de eliminar la lista de estados combinados temporal
    public void eliminarEstados() {
        try {
            if (this.ReglasEstadoTmpList != null && !this.ReglasEstadoTmpList.isEmpty()) {
                this.ReglasEstadoTmpList = new ArrayList<>();
                this.basicaIdEstado = null;
                this.basicaIdEstadoComb = null;
                this.basicaIdTecnologia = null;
                mostrarMensaje("Estados combinados removidos con exito.", "SUCCESS");
            }
        } catch (Exception e) {
            mostrarMensaje("Error al intentar remover estado combinado de la lista " + e.getMessage(), "ERROR");
        }
    }

    // metodo encargado de mostrar un mensaje en la vista
    public void mostrarMensaje(String msn, String tipoError) {
        switch (tipoError) {
            case "ERROR":
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                break;
            case "SUCCESS":
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                break;
            case "WARN":
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                break;
            default:
                break;
        }

    }

    // Metodo encargado de validar los campos obligatoris para crear regla
    private Boolean validaCampos() {
        String msn = " es obligatorio,seleccione uno de la lista.";
        if (this.basicaIdEstadoComb == null) {
            msn = "Estado combinado" + msn;
            mostrarMensaje(msn, "WARN");
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    private List<CmtBasicaMgl> mapperList(List<CmtBasicaMgl> listEstados, String tipo) {
        List<CmtBasicaMgl> listTmp = new ArrayList<>();
        listTmp.addAll(
            tipo.equals("ESTC")
            ? listEstados.stream().filter(x -> x.getNombreBasica().contains("+")).collect(Collectors.toList())
            : listEstados.stream().filter(x -> !x.getNombreBasica().contains("+")).collect(Collectors.toList())
        );
        return listTmp;
    }

    /**
     * Función que permite ir directamente a la página seleccionada de
     * resultados.
     *
     * @author Juan David Hernandez
     */
    public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error direccionando a página" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que carga la primera página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a la primera página" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     *
     * @author Juan David Hernandez
     */
    public void pageNext() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a la siguiente página" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que carga la última página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error direccionando a la última página" + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     *
     * @author Juan David Hernandez
     */
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
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error direccionando a la página anterior" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función que realiza paginación de la tabla.
     *
     * @param page;
     * @author Juan David Hernandez
     * @return
     */
    public String listInfoByPage(int page) {
        try {
            actual = page;
            getPageActual();
            this.ReglasEstadoList = cmtReglaEstadoCmMglFacadeLocal.findRulesPaginacion(page, filasPag, 1, this.tipoFiltro, this.contentFiltro);
            guardarPaginaActualSession();
        } catch (ApplicationException e) {
            mostrarMensaje("Error en lista de paginación " + e.getMessage(), "ERROR");
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en lista de paginación " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario en
     * los resultados.
     *
     * @author Juan David Hernandez
     * @return
     */
    public String getPageActual() {
        paginaList = new ArrayList<>();
        int totalPaginas = getTotalPaginas();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaList.add(i);
        }
        pageActual = String.valueOf(actual + " de " + totalPaginas);

        if (numPagina == null) {
            numPagina = "1";
        }
        numPagina = String.valueOf(actual);

        return pageActual;
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     *
     * @author Juan David Hernandez
     * @return
     */
    public int getTotalPaginas() {
        try {
            int totalPaginas;
            int pageSol = cmtReglaEstadoCmMglFacadeLocal.countRules(this.tipoFiltro, this.contentFiltro);
            totalPaginas = (pageSol % filasPag != 0) ? (pageSol / filasPag) + 1 : pageSol / filasPag;
            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error direccionando a la primera página" + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    /**
     * Función que guarda la pagina actual de la paginación del listado en
     * session para esta poder ser accedida desde el bean de la gestion de la
     * solicitud
     *
     * @author Juan David Hernandez
     */
    public void guardarPaginaActualSession() {
        try {
            // Instacia Bean de Session para obtener la solicitud seleccionada
            SolicitudSessionBean solicitudSessionBean = JSFUtil.getSessionBean(SolicitudSessionBean.class);

            //enviamos pagina actual a bean de session.
            solicitudSessionBean.setPaginaActual(actual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al guardar página actual en session" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Función el estado combinado segun cumplimiento de reglas parametrizadas
     *
     * @author Juan David Hernandez
     * @param estadoList
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CmtBasicaMgl obtenerEstadoCmRrByReglaEstado(List<CmtReglaEstadoCmMgl> estadoList)
            throws ApplicationException {
        try {

            if (estadoList != null && !estadoList.isEmpty()) {

                CmtReglaEstadoCmMglManager cmtReglaEstadoCmMglManager = new CmtReglaEstadoCmMglManager();
                List<String> numeroReglaList = cmtReglaEstadoCmMglManager.findNumeroReglaList();
                List<CmtReglaEstadoCmMgl> reglasExitosas = new ArrayList();
                if (numeroReglaList != null && !numeroReglaList.isEmpty()) {
                    for (String numeroRegla : numeroReglaList) {
                        List<CmtReglaEstadoCmMgl> cmtReglaEstadoCmMglList = cmtReglaEstadoCmMglManager
                                .findReglaByNumeroRegla(numeroRegla);
                        if (cmtReglaEstadoCmMglList != null && !cmtReglaEstadoCmMglList.isEmpty()) {
                            int contEstado = 0;
                            for (CmtReglaEstadoCmMgl estado : estadoList) {
                                for (CmtReglaEstadoCmMgl cmtReglaEstadoCmMgl1 : cmtReglaEstadoCmMglList) {
                                    if (cmtReglaEstadoCmMgl1.getTecnologiaBasicaId() != null
                                            && cmtReglaEstadoCmMgl1.getTecnologiaBasicaId().getBasicaId() != null
                                            && cmtReglaEstadoCmMgl1.getEstadoTecBasicaId() != null
                                            && cmtReglaEstadoCmMgl1.getEstadoTecBasicaId().getBasicaId() != null
                                            && estado.getEstadoTecBasicaId() != null
                                            && estado.getTecnologiaBasicaId() != null) {

                                        if (estado.getTecnologiaBasicaId().getBasicaId()
                                                .equals(cmtReglaEstadoCmMgl1.getTecnologiaBasicaId().getBasicaId())
                                                && estado.getEstadoTecBasicaId().getBasicaId().equals(
                                                        cmtReglaEstadoCmMgl1.getEstadoTecBasicaId().getBasicaId())) {
                                            contEstado++;
                                        }
                                    }
                                }

                                if (contEstado == estadoList.size()
                                        && cmtReglaEstadoCmMglList != null
                                        && !cmtReglaEstadoCmMglList.isEmpty()
                                        && cmtReglaEstadoCmMglList.size() == estadoList.size()) {
                                    cmtReglaEstadoCmMglList = cmtReglaEstadoCmMglManager
                                            .findReglaByNumeroRegla(numeroRegla);
                                    if (cmtReglaEstadoCmMglList != null && !cmtReglaEstadoCmMglList.isEmpty()) {
                                        reglasExitosas.add(cmtReglaEstadoCmMglList.get(0));
                                    }
                                }
                            }
                        } else {
                            return null;
                        }
                    }

                    if (!reglasExitosas.isEmpty() && reglasExitosas.size() == 1) {
                        return reglasExitosas.get(0).getEstadoCmBasicaId();
                    } else {
                        if (!reglasExitosas.isEmpty() && reglasExitosas.size() > 1) {
                            return null;
                        } else {
                            if (reglasExitosas.isEmpty()) {
                                return null;
                            }
                        }
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
        return null;
    }

    
    // Metodo encargado de filtrar por tipo y por valor ingresado las reglas existentes
    public void filtrar() {

        if (this.tipoFiltro == null) {
            mostrarMensaje("seleccione el tipo a filtrar.", "WARN");
        } else if (this.contentFiltro.isEmpty()) {
            mostrarMensaje("Ingrese el valor a filtrar.", "WARN");
        } else {
            if (this.tipoFiltro.equals("IDRULE") && !isNumeric(this.contentFiltro)) {
                mostrarMensaje("Valor ingresado no es numerico.", "WARN");
                return;
            }
            initRules();
        }

    }

    // Metodo encargado de limpiar los filtros y consultar nuevamente las reglas existentes
    public void limpiarFiltro() {
        this.tipoFiltro = null;
        this.contentFiltro = "";
        initRules();
    }

    private static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

}
