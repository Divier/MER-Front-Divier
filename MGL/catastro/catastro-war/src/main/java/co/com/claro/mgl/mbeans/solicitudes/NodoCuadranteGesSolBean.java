package co.com.claro.mgl.mbeans.solicitudes;

import co.com.claro.mer.dtos.sp.cursors.CmtSolicitudNodoCuadranteDto;
import co.com.claro.mer.exception.ExceptionServBean;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.ICmtSolicitudNodoCuadFrontFacadeLocal;
import co.com.claro.mgl.facade.cm.ICmtSolicitudNodoCuadranteFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;

/**
 * Clase Bean para la vista de gestion de solicitudes de nodos a cuadrantes
 * Permite controlar la ventana
 *
 * @author Divier Casas
 * @version 1.0
 */
@Log4j2
@ManagedBean(name = "nodoCuadranteGesSolBean")
@ViewScoped
public class NodoCuadranteGesSolBean implements Serializable {

    private static final String GESTIONAR_SOLICITUD = "Gestionar Solicitud";
    private String usuarioVT = null;
    @Getter
    @Setter
    private int perfilVt = 0;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();

    @Getter
    @Setter
    private boolean showGestionSolicitudList;
    @Getter
    @Setter
    private boolean showGestionSolicitud;
    private boolean ordenMayorMenor;
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
    private int pageSize = ConstantsCM.PAGINACION_DIEZ_FILAS;
    @Getter
    @Setter
    private String regresarMenu = "<- Regresar Menú";
    @Getter
    @Setter
    private List<CmtSolicitudNodoCuadranteDto> lsCmtSolicitudNodoCuadranteDto;
    @Getter
    @Setter
    private List<CmtBasicaMgl> listBasicaDivisional;
    @Getter
    @Setter
    private boolean selAllSol = false;

    @Getter
    @Setter
    private List<GeograficoPoliticoMgl> ciudadesList;
    @Getter
    @Setter
    private List<GeograficoPoliticoMgl> departamentoList;
    @Getter
    @Setter
    private List<GeograficoPoliticoMgl> centroPobladoList;
    @Getter
    @Setter
    private List<String> lsNodes;
    @Getter
    @Setter
    private BigDecimal codDivisional;
    @Getter
    @Setter
    private String codDepartamento;
    @Getter
    @Setter
    private BigDecimal codCiudad;
    @Getter
    @Setter
    private BigDecimal codCentroPoblado;
    @Getter
    @Setter
    private String codigoNodo;
    @Getter
    @Setter
    private Integer codEstado;
    private HashMap<String, Object> mFilters = new HashMap<>();

    private SecurityLogin securityLogin;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @Inject
    private ExceptionServBean exceptionServBean;
    @EJB
    private ICmtSolicitudNodoCuadFrontFacadeLocal iSolNodoCuadFront;
    @EJB
    private ICmtSolicitudNodoCuadranteFacadeLocal iSolNodoCuadrante;
    private CmtSolicitudNodoCuadranteDto cmtSolicitudNodoCuadranteDto;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;

    public NodoCuadranteGesSolBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                response.sendRedirect(securityLogin.redireccionarLogin());
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();

            if (usuarioVT == null) {
                response.sendRedirect(securityLogin.redireccionarLogin());
            }
            showList();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al arrancar la pantalla" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al arrancar la pantalla" + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    private void init() {

        cmtSolicitudNodoCuadranteDto = new CmtSolicitudNodoCuadranteDto();
        NodoCuadranteSolicitudSessionBean solicitudSessionBean = JSFUtil.getSessionBean(NodoCuadranteSolicitudSessionBean.class);
        //Obtenemos pagina actual de bean de Session.
        int page = solicitudSessionBean.getPaginaActual();
        listInfoByPage(page == 0 ? 1 : page);
        getListDivisionales();
        getDepartmentList();
        getNodes();
    }

    /**
     * Metodo gestionar solicitud
     *
     * @param solicitud
     */
    public void gestionarSolicitud(CmtSolicitudNodoCuadranteDto solicitud) {

        bloquearDisponibilidadGestion(solicitud);
        //TO-DO
    }

    /**
     * Función que guarda la pagina actual de la paginación del listado en
     * session para esta poder ser accedida desde el bean de la gestion de la
     * solicitud
     *
     */
    public void guardarPaginaActualSession() {

        try {
            // Instacia Bean de Session para obtener la solicitud seleccionada
            NodoCuadranteSolicitudSessionBean solicitudSessionBean
                    = JSFUtil.getSessionBean(NodoCuadranteSolicitudSessionBean.class);

            //enviamos pagina actual a bean de session.
            solicitudSessionBean.setPaginaActual(actual);
        } catch (Exception e) {
            String msgError = "Error al guardar página actual en session ";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función que realiza paginación de la tabla.
     *
     * @param page;
     * @return
     */
    public String listInfoByPage(int page) {

        try {
            actual = page;
            mFilters.put("solicitudId", null);
            mFilters.put("cuadranteId", null);
            mFilters.put("codDivisional", codDivisional);
            mFilters.put("codCiudad", codCiudad);
            mFilters.put("codCentroPoblado", codCentroPoblado);
            mFilters.put("codigoNodo", codigoNodo);
            mFilters.put("codEstado", codEstado != null ? codEstado : 0);
            mFilters.put("legado", null);
            mFilters.put("resultadoAsociacion", null);
            getPageActual();
            lsCmtSolicitudNodoCuadranteDto = iSolNodoCuadFront.findByFilters(mFilters, ordenMayorMenor, page, pageSize);
            guardarPaginaActualSession();
        } catch (ApplicationException e) {
            String msgError = "Error en lista de paginación";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), GESTIONAR_SOLICITUD);
        }
        return null;
    }

    public void cambiarOrdenMayorMenorListado() {

        ordenMayorMenor = !ordenMayorMenor;
        restoreCheckbox();
        listInfoByPage(1);
    }

    /**
     * Función que carga la primera página de resultados
     */
    public void pageFirst() {

        try {
            restoreCheckbox();
            listInfoByPage(1);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la primera página " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función que carga la página anterior de resultados.
     */
    public void pagePrevious() {

        try {
            restoreCheckbox();
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
            exceptionServBean.notifyError(e, "Error direccionando a la página anterior " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función que cpermite ir directamente a la página seleccionada de
     * resultados.
     */
    public void irPagina() {

        try {
            restoreCheckbox();
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException e) {
            exceptionServBean.notifyError(e, "Error direccionando a página " + e.getMessage(), GESTIONAR_SOLICITUD);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a página: " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     */
    public void pageNext() {

        try {
            restoreCheckbox();
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error direccionando a la siguiente página " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función que carga la última página de resultados
     */
    public void pageLast() {

        try {
            restoreCheckbox();
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            exceptionServBean.notifyError(ex, "Error direccionando a la última página" + ex.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     *
     * @return
     */
    public int getTotalPaginas() {

        try {
            int totalPaginas;
            List<CmtSolicitudNodoCuadranteDto> lsCmtSolicitudNodoCuadranteDto = iSolNodoCuadFront.findByFilters(mFilters, ordenMayorMenor, 0, 0);
            int pageSol = lsCmtSolicitudNodoCuadranteDto != null && !lsCmtSolicitudNodoCuadranteDto.isEmpty() ? lsCmtSolicitudNodoCuadranteDto.size() : 0;
            totalPaginas = (pageSol % pageSize != 0)
                    ? (pageSol / pageSize) + 1 : pageSol / pageSize;
            return totalPaginas;
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al obtener el total de páginas " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario en
     * los resultados.
     *
     * @return
     */
    public String getPageActual() {

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

        return pageActual;
    }

    /**
     * Función para obtener listado de divisionales
     */
    public void getListDivisionales() {

        try {
            CmtTipoBasicaMgl tipoBasicaDivicional;
            tipoBasicaDivicional = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_DIVICIONAL_COMERCIAL_TELMEX);
            listBasicaDivisional = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaDivicional);
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener la lista de divisionales  "
                    + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }
    
    /**
     * Obtiene el listado de departamentos de la base de datos
     */
    public void getDepartmentList() {
        try {
            codDepartamento = null;
            departamentoList = geograficoPoliticoMglFacadeLocal.findDptos();
            ciudadesList = new ArrayList<>();
            departamentoList.forEach(dep -> {
                codDepartamento = dep.getGpoId().toString();
                getCitiesList(codDepartamento, dep.getGpoNombre().trim().toLowerCase());
            });
            ciudadesList = ciudadesList.stream().sorted(Comparator.comparing(GeograficoPoliticoMgl::getGpoNombre)).collect(Collectors.toList());
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener la lista de departamentos.  "
                    + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Obtiene el listado de departamentos de la base de datos
     * @param codDepartamento
     * @param nombDepartamento
     */
    public void getCitiesList(String codDepartamento, String nombDepartamento) {
        try {
            codCiudad = null;
            codCentroPoblado = null;
            List<GeograficoPoliticoMgl> lsCiudadesTemp = geograficoPoliticoMglFacadeLocal.findCiudades(new BigDecimal(codDepartamento));
            lsCiudadesTemp.forEach(ciudad -> ciudad.setGpoNombre(ciudad.getGpoNombre().concat(" (").concat(StringUtils.capitalize(nombDepartamento)).concat(")")));
            ciudadesList.addAll(lsCiudadesTemp);
            centroPobladoList = new ArrayList();
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener la lista de ciudades.  "
                    + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Obtiene el listado de centro poblado por ciudad de la base de datos
     */
    public void getPopulatedCenterList() {
        try {
            codCentroPoblado = null;
            filtrarCiudad();
            if (codCiudad != null) {
                centroPobladoList = geograficoPoliticoMglFacadeLocal.findCentroPoblado(codCiudad);
            } else {
                centroPobladoList = new ArrayList();
            }
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener la lista de centros poblados.  "
                    + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }
    
    /**
     * Obtiene el listado de nodos de la base de datos
     */
    public void getNodes() {
        try {
            lsNodes = iSolNodoCuadFront.findAllNodes();
        } catch (ApplicationException e) {
            exceptionServBean.notifyError(e, "Error al obtener la lista de nodos.  "
                    + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    public void filtrarDivisional() {

        try {
            restoreCheckbox();
            listInfoByPage(1);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al realizar cargue de solicitudes por id de divisional " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    public void filtrarCiudad() {

        try {
            restoreCheckbox();
            listInfoByPage(1);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al realizar cargue de solicitudes por id de ciudad " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }
    
    public void filtrarCentroPoblado() {

        try {
            restoreCheckbox();
            listInfoByPage(1);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al realizar cargue de solicitudes por id de centro poblado " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }
    
    public void findSolicitudByCodigoNodo() {

        try {
            restoreCheckbox();
            listInfoByPage(1);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al realizar cargue de solicitudes por codigo de nodo " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }
    
    public void filtrarPorEstado() {

        try {
            restoreCheckbox();
            listInfoByPage(1);
        } catch (Exception e) {
            exceptionServBean.notifyError(e, "Error al realizar cargue de solicitudes por id de estado " + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función que renderizar la pantalla de listado de solicitudes.
     */
    public void showList() {

        showGestionSolicitudList = true;
        showGestionSolicitud = false;
    }

    public void selectAllSol() {

        lsCmtSolicitudNodoCuadranteDto.forEach((sol) -> {
            sol.setSelected(selAllSol);
        });
    }

    private void restoreCheckbox() {

        setSelAllSol(false);
        selectAllSol();
    }

    /**
     * Función que desbloquea la disponibilidad de una solicitud
     *
     * @param solicitud
     * @author Divier Casas
     */
    public void desbloquearDisponibilidadGestion(CmtSolicitudNodoCuadranteDto solicitud) {
        try {
            if (solicitud != null) {
                iSolNodoCuadrante.configDisponibilidadGestion(solicitud.getSolicitudId(), null);
                NodoCuadranteSolicitudSessionBean solicitudSessionBean = JSFUtil.getSessionBean(NodoCuadranteSolicitudSessionBean.class);
                listInfoByPage(solicitudSessionBean.getPaginaActual());
            } else {
                String msnError = "Ha ocurrido un error seleccionando la solicitud, intente de nuevo por favor";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
            }
        } catch (ApplicationException e) {
            String msgError = "Error al desbloquear la disponibilidad de gestion de la solicitud";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }

    /**
     * Función que bloquea la disponibilidad de una solicitud
     *
     * @param solicitud
     * @author Divier Casas
     */
    private void bloquearDisponibilidadGestion(CmtSolicitudNodoCuadranteDto solicitud) {
        try {
            if (solicitud != null) {
                iSolNodoCuadrante.configDisponibilidadGestion(solicitud.getSolicitudId(), usuarioVT);
                NodoCuadranteSolicitudSessionBean solicitudSessionBean = JSFUtil.getSessionBean(NodoCuadranteSolicitudSessionBean.class);
                listInfoByPage(solicitudSessionBean.getPaginaActual());
            } else {
                String msnError = "Ha ocurrido un error seleccionando la solicitud, intente de nuevo por favor";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msnError, ""));
            }
        } catch (ApplicationException e) {
            String msgError = "Error al bloquear la disponibilidad de gestion de la solicitud";
            exceptionServBean.notifyError(e, msgError + e.getMessage(), GESTIONAR_SOLICITUD);
        }
    }
}
