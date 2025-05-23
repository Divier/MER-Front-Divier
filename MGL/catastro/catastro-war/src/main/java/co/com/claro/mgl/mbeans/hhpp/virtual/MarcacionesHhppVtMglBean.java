package co.com.claro.mgl.mbeans.hhpp.virtual;

import co.com.claro.mgl.dtos.CmtLogMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.CmtMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroConsultaMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroMarcacionesHhppVtDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtLogMarcacionesHhppVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtMarcacionesHhppVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Bean para vista, marcaciones para creación hhpp virtual. Define la logica
 * asociada a la vista marcaciones creación hhpp virtuales y sus interacciones con
 * los elementos.
 *
 * @author Gildardo Mora
 * @version 1.0, 2021/09/29
 */
@ViewScoped
@ManagedBean(name = "marcacionesHhppVtMglBean")
public class MarcacionesHhppVtMglBean implements Serializable {
    private static final Logger LOGGER = LogManager.getLogger(MarcacionesHhppVtMglBean.class);

    @EJB
    private CmtMarcacionesHhppVtMglFacadeLocal cmtMarcacionesHhppVtMglFacade;
    @EJB
    private CmtLogMarcacionesHhppVtMglFacadeLocal cmtLogMarcacionesHhppVtMglFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;

    /* ----------------------------------------------------------------*/
    @Getter
    @Setter
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    @Getter
    @Setter
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    /* PAGINACION*/
    @Getter
    @Setter
    private List<Integer> paginaList;
    private static final int FILAS_PAG = ConstantsCM.PAGINACION_QUINCE_FILAS;
    @Getter
    @Setter
    private String numPagina = "1";
    private int actual;
    @Getter
    @Setter
    private boolean pintarPaginado = true;
    private int totalPaginas = 0;

    /* ELEMENTOS DE RESPUESTA EXCEPCIONES*/
    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private Map<String, Object> paramsValidacionActualizar;

    /* DATOS SESION*/
    @Getter
    @Setter
    private SecurityLogin securityLogin;
    @Getter
    @Setter
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    @Getter
    @Setter
    private String usuarioSesion;
    private String usuarioVT = null;
    @Getter
    @Setter
    private String valorTiempo = "";
    private int perfilVT = 0;
    private int perfil = 0;
    private static final String LOGIN_USER_SECURITY = "loginUserSecurity";

    /* DATOS DE CARGUE*/
    @Getter
    @Setter
    private CmtMarcacionesHhppVtDto marcacionesHhppVt;
    @Getter
    @Setter
    private List<CmtMarcacionesHhppVtDto> tablasMarcacionesHhppVtMglList;
    @Getter
    @Setter
    private FiltroMarcacionesHhppVtDto filtroMarcacionesHhppVtDto = new FiltroMarcacionesHhppVtDto();
    @Getter
    @Setter
    private FiltroConsultaMarcacionesHhppVtDto filtroConsultaMarcacionesHhppVtDto = new FiltroConsultaMarcacionesHhppVtDto();

    /* ELEMENTOS DE VISTA*/
    @Getter
    @Setter
    private boolean btnActualizar = false;

    /* PROCESOS DE VALIDACION*/
    @Getter
    @Setter
    private boolean mostrarPanelLogMarcaciones = false;
    @Getter
    @Setter
    private boolean mostrarPanelListMarcacionesHhppVt = false;
    @Getter
    @Setter
    private boolean mostrarGifLoading;
    @Getter
    @Setter
    private boolean ejecutaProcesoMarcaciones = true;//DEBE ESTAR EN TRUE SIEMPRE AL PRIMER ACCESO

    /* EXTERNAL CONTEXT y SESSION MAP */
    ExternalContext exct = FacesContext.getCurrentInstance().getExternalContext();
    Map<String, Object> sessionMap = exct.getSessionMap();

    /* LISTAS DE CAMBIOS EN MARCACIONES */
    @Getter
    @Setter
    private List<CmtMarcacionesHhppVtDto> listaCambiosMarcaciones = new ArrayList<>();

    /* -----------------------------------------------------------------*/
    /**
     * validacion de sesion e inicialización de entidad marcaciones creación
     * hhpp virtuales
     */
    public MarcacionesHhppVtMglBean() {
        if (sessionMap.get("ejecutaProcesoMarcaciones") != null) {
            ejecutaProcesoMarcaciones = (boolean) sessionMap.get("ejecutaProcesoMarcaciones");
        }

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
            if (usuarioVT == null) {
                session.getAttribute(LOGIN_USER_SECURITY);
                usuarioVT = (String) session.getAttribute(LOGIN_USER_SECURITY);
            } else {
                session = (HttpSession) facesContext.getExternalContext().getSession(false);
                session.setAttribute(LOGIN_USER_SECURITY, usuarioVT);
            }
            marcacionesHhppVt = new CmtMarcacionesHhppVtDto();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en  " + ClassUtils.getCurrentMethodName(this.getClass()) + e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Exc Error " + ClassUtils.getCurrentMethodName(this.getClass()) + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void fillSqlList() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            usuarioSesion = securityLogin.getLoginUser();
            perfil = securityLogin.getPerfilUsuario();
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioSesion);
            pintarPaginado = true;
            consultarTiempoMinimoServicio();

            if (ejecutaProcesoMarcaciones) {
                LOGGER.info(" -- Ejecución de sincronización de marcaciones HHPP con RR");
                sincronizarRazones();
            }
            listInfoByPage(1);

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("AppExc Error en " + ClassUtils.getCurrentMethodName(this.getClass())
                    + e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error " + ClassUtils.getCurrentMethodName(this.getClass())
                    + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * consulta la lista de marcaciones para proceso de paginación
     *
     * @param page el número de página que se va a cargar
     * @author Gildardo Mora
     */
    public void listInfoByPage(int page) {
        try {
            int firstResult = 0;
            if (page > 1) {
                firstResult = (FILAS_PAG * (page - 1));
            }

            filtroMarcacionesHhppVtDto.setEstadoRegistro(1);
            filtroConsultaMarcacionesHhppVtDto = cmtMarcacionesHhppVtMglFacade.findTablasMarcacionesHhppVt(
                    filtroMarcacionesHhppVtDto, firstResult, FILAS_PAG);
            //consulta la lista de marcaciones registradas
            tablasMarcacionesHhppVtMglList = filtroConsultaMarcacionesHhppVtDto.getListaTablasMarcacionesHhppVt();

            if (tablasMarcacionesHhppVtMglList.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No se encontraron resultados para la consulta.", ""));
                return;
            }

            if (tablasMarcacionesHhppVtMglList.size() == 1) {
                marcacionesHhppVt = tablasMarcacionesHhppVtMglList.get(0);
            }

            actual = page;
            mostrarPanelListMarcacionesHhppVt = true;
            mostrarPanelLogMarcaciones = false;

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("No se encontraron resultados para la consulta. App Excp. :.." + e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en MarcacionesHhppVtMglBean: listInfoByPage(). " + e.getMessage(), e, LOGGER);
        }
    }

    /*  --- procesos de paginacion  ---   */
    public void pageFirst() {
        listInfoByPage(1);
    }

    public void pagePrevious() {
        irPagina(actual - 1);
    }

    public void irPagina() {
        irPagina(Integer.parseInt(numPagina));
    }

    public void pageNext() {
        irPagina(actual + 1);
    }

    public void pageLast() {
        totalPaginas = getTotalPaginas();
        listInfoByPage(totalPaginas);
    }

    /**
     * @param pagina número de página a la cual se desplaza en la vista
     * @author Gildardo Mora
     */
    private void irPagina(Integer pagina) {
        totalPaginas = getTotalPaginas();
        int nuevaPageActual = pagina;

        if (nuevaPageActual > totalPaginas) {
            nuevaPageActual = totalPaginas;
        }

        if (nuevaPageActual <= 0) {
            nuevaPageActual = 1;
        }
        listInfoByPage(nuevaPageActual);
    }

    /**
     * Obtiene el total de paginas para el proceso de paginación
     *
     * @return totalPaginas
     * @author Gildardo Mora
     */
    public int getTotalPaginas() {
        if (!pintarPaginado) {
            return 1;
        }

        int totalPaginasLoc = 0;

        try {
            filtroMarcacionesHhppVtDto.setEstadoRegistro(1);
            long count = filtroConsultaMarcacionesHhppVtDto.getNumRegistros();
            totalPaginasLoc = (int) ((count % FILAS_PAG != 0) ? (count / FILAS_PAG) + 1 : count / FILAS_PAG);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en "+ ClassUtils.getCurrentMethodName(this.getClass())
                    + e.getMessage(), e, LOGGER);
        }
        return totalPaginasLoc;
    }

    /**
     * Obtiene la información de la posición de paginación
     *
     * @return pageActual cadena de texto con información de posición actual de paginación
     * @author Gildardo Mora
     */
    public String getPageActual() {
        paginaList = new ArrayList<>();
        totalPaginas = getTotalPaginas();

        for (int i = 1; i <= totalPaginas; i++) {
            paginaList.add(i);
        }

        String pageActual = actual + " de " + totalPaginas;
        numPagina = String.valueOf(actual);
        return pageActual;
    }

    public void filtrarInfo() {
        listInfoByPage(1);
    }

    /* --- VALIDACIÓN DE ROLES */
    public boolean validarOpcionActualizar() { // Para validar rol sobre Botón actualizar
        return validarEdicionRol("TBLBASEXP");//Ajustar por el rol específico.
    }

    //Validar el rol
    private boolean validarEdicionRol(String formulario) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en MarcacionesHhppVtMglBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /* ACIONES DE ELEMENTOS DE LA VISTA */

    /**
     * Se encarga de llamar al cargue de la vista de logs de marcaciones
     *
     * @param marcacionHHPP {@link CmtMarcacionesHhppVtDto}
     * @return logMarcacionesCreaHHPPVirtualR1R2 Que indica el cargue de la vista de logs de marcaciones
     */
    public String mostrarLogMarcacionesHHPPVirtual(CmtMarcacionesHhppVtDto marcacionHHPP) {
        try {
            session.setAttribute("id_marcacion_sended", marcacionHHPP.getMarcacionId());
            return "logMarcacionesCreaHHPPVirtualR1R2";

        } catch (Exception e) {
            String msn = "Se presenta error al mostrar el logs de marcaciones";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        }

        return null;
    }

    /**
     * Actualiza el estado de las marcaciones de hhpp
     */
    public void actualizarMarcaciones() {
        try {
            List<CmtMarcacionesHhppVtDto> listaUpdateMarcaciones = new ArrayList<>();
            List<CmtLogMarcacionesHhppVtDto> listaCreateLogs = new ArrayList<>();

            if (Objects.isNull(listaCambiosMarcaciones) || listaCambiosMarcaciones.isEmpty()) {
                FacesUtil.mostrarMensajeWarn("No se seleccionaron marcaciones para actualizar");
                return;
            }

            for (CmtMarcacionesHhppVtDto marcacionHHPP : listaCambiosMarcaciones) {
                //CONSTRUCCIÓN OBJETO LOG
                CmtLogMarcacionesHhppVtDto logMarcaciones = new CmtLogMarcacionesHhppVtDto();
                logMarcaciones.setIdMarcacion(marcacionHHPP.getMarcacionId());
                logMarcaciones.setFechaCreacionLogMarcaHHPP(LocalDateTime.now());
                logMarcaciones.setUsuarioCreacionLog(usuarioVT);
                logMarcaciones.setEstadoRegistro(1);
                logMarcaciones.setPerfilCreacion(perfilVT);

                String valorNuevoMarcacionSiNo = marcacionHHPP.isAplicaHhppVt() ? "SI" : "NO";
                String valorAnteriorMarcacionSiNo = marcacionHHPP.isAplicaHhppVt() ? "NO" : "SI";
                logMarcaciones.setValorNuevoLogMarcaHHPP(valorNuevoMarcacionSiNo);
                logMarcaciones.setValorAntLogMarcaHHPP(valorAnteriorMarcacionSiNo);

                //ACTUALIZACIÓN MARCACIONES
                marcacionHHPP.setEstadoRegistro(1);
                listaUpdateMarcaciones.add(marcacionHHPP); //agrega la marcación a la lista por actualizar
                listaCreateLogs.add(logMarcaciones);//agrega el log a lista por crear
            }
            /*Actualiza las marcaciones*/
            Boolean updated = cmtMarcacionesHhppVtMglFacade.updateMarcacionesHhppVt(listaUpdateMarcaciones, usuarioVT, perfil);
            /*Registra el log de marcaciones*/
            Boolean logCreated = cmtLogMarcacionesHhppVtMglFacadeLocal.createLogMarcacion(listaCreateLogs, usuarioVT, perfilVT);
            listaCambiosMarcaciones.clear(); //limpia la lista indicadora de cambios

            if (Boolean.TRUE.equals(updated) || Boolean.TRUE.equals(logCreated)) {
                FacesUtil.mostrarMensaje("Marcaciones Actualizadas");
            }

        } catch (ApplicationException e) {
            String msn = "Se presenta error al actualizar marcaciones" + e.getMessage();
            FacesUtil.mostrarMensajeError(msn , e, LOGGER);
        }
    }

    /**
     * @param item objeto tipo Marcaciones, recuperado desde la tabla del front
     * @author Jimmy Garcia Ospina
     * @use Método que recupera el dato de la fila desde la tabla del front y lo
     * almacena en una lista siempre y cuando no exista en la lista.
     */
    public void listarCambiosAplicaHHPP(CmtMarcacionesHhppVtDto item) {

        if (listaCambiosMarcaciones != null) {

            if (listaCambiosMarcaciones.contains(item)) {
                listaCambiosMarcaciones.remove(item);

            } else {
                listaCambiosMarcaciones.add(item);
            }
        }
    }

    /**
     * Asigna el valor del tiempo minimo del servicio
     */
    private void consultarTiempoMinimoServicio() {
        try {
            valorTiempo = cmtMarcacionesHhppVtMglFacade.consultarTiempoMinimoServicio() + " dias";
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en MarcacioneshhppVtMglBean: consultarTiempoMinimoServicio(). "
                    + ex.getMessage(), ex, LOGGER);
        }
    }

    /**
     * Realiza el proceso de sincronización entre los datos del servicio, y los
     * datos de las marcaciones en MER.
     */
    private void sincronizarRazones() {
        FiltroMarcacionesHhppVtDto filtroSincronizar = new FiltroMarcacionesHhppVtDto();

        try {
            Boolean sincronizado = cmtMarcacionesHhppVtMglFacade.sincronizarRazones(filtroSincronizar, usuarioVT, perfilVT);
            if (Boolean.TRUE.equals(sincronizado)) {
                LOGGER.info("Se sincronizó las marcaciones para creación de HHPP Virtual con RR");
            }
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en MarcacioneshhppVtMglBean: sincronizarRazones(). "
                    + ex.getMessage(), ex, LOGGER);
        }
    }

}
