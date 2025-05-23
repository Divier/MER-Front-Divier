package co.com.claro.mgl.mbeans.hhpp.virtual;

import co.com.claro.mgl.dtos.CmtLogMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroConsultaLogMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroLogMarcacionesHhppVtDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtLogMarcacionesHhppVtMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
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
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * @author Jimmy García
 * @use Clase Managed Bean para el proceso Log Marcaciones HHPP Virtual. Punto
 * de entrada e interacción de datos desde la vista.
 * @date 05-10-2021
 */
@ManagedBean(name = "logMarcacionesHhppVtMglBean")
@ViewScoped
public class LogMarcacionesHhppVtMglBean implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(LogMarcacionesHhppVtMglBean.class);
    @EJB
    private CmtLogMarcacionesHhppVtMglFacadeLocal cmtLogMarcacionesHhppVtMglFacade;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    //ES NECESARIO REVISAR LA FORMA EN QUE SE VAN A ASIGNAR O ENTREGAR LOS PERMISOS DE ROL AL FORMULARIO
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;

    /* ----------------------------------------------------------------*/
    @Getter
    @Setter
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    @Getter
    @Setter
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    @Getter
    @Setter
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    /* PAGINACION*/
    @Getter
    @Setter
    private List<Integer> paginaList;
    private static final int FILAS_POR_PAGINA = ConstantsCM.PAGINACION_QUINCE_FILAS;
    @Getter
    @Setter
    private String numPagina = "1";
    private int actual;
    int totalPaginas = 0;
    @Getter
    @Setter
    private boolean pintarPaginado = true;

    /* ELEMENTOS DE RESPUESTA EXCEPCIONES*/
    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private HashMap<String, Object> paramsValidacionActualizar;

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
    private int perfilVT = 0;
    private int perfil = 0;

    /* DATOS DE CARGUE*/
    @Getter
    @Setter
    private CmtLogMarcacionesHhppVtDto logMarcacionesHhppVt;
    @Getter
    @Setter
    private List<CmtLogMarcacionesHhppVtDto> tablasLogMarcacionesHhppVtMglList;
    @Getter
    @Setter
    private FiltroLogMarcacionesHhppVtDto filtroLogMarcacionesHhppVtDto = new FiltroLogMarcacionesHhppVtDto();
    @Getter
    @Setter
    private FiltroConsultaLogMarcacionesHhppVtDto filtroConsultaLogMarcacionesHhppVtDto = new FiltroConsultaLogMarcacionesHhppVtDto();

    /* EXTERNAL CONTEXT y SESSION MAP */
    ExternalContext exct = FacesContext.getCurrentInstance().getExternalContext();
    Map<String, Object> sessionMap = exct.getSessionMap();

    /* ELEMENTOS DE VISTA*/
    @Getter
    @Setter
    private boolean btnActualizar = false;

    /* PROCESOS DE VALIDACION*/
    @Getter
    @Setter
    private boolean mostrarPanelLogMarcaciones = true;
    private boolean cargar = true;
    @Getter
    @Setter
    private boolean ejecutaProcesoMarcaciones;

    /* ----------------------------------------------------------------*/

    /**
     * validacion de sesion e inicialización de entidad marcaciones creación
     * hhpp virtuales
     */
    public LogMarcacionesHhppVtMglBean() {
        try {
            securityLogin = new SecurityLogin(FacesContext.getCurrentInstance());
            if (!securityLogin.isLogin()) {

                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }

            /* VARIABLE CONTROL ACTUALIZACIÓN MARCACIONES*/
            ejecutaProcesoMarcaciones=false;
            sessionMap = exct.getSessionMap();
            sessionMap.put("ejecutaProcesoMarcaciones", ejecutaProcesoMarcaciones);

            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
            logMarcacionesHhppVt = new CmtLogMarcacionesHhppVtDto();

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al LogMarcacionesHhppVtMglBean. " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void fillSqlList() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            usuarioSesion = securityLogin.getLoginUser();
            perfil = securityLogin.getPerfilUsuario();
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioSesion);
            BigDecimal idMarcacion = (BigDecimal) session.getAttribute("id_marcacion_sended");
            cargar = true;
            pintarPaginado = true;
            //ASIGNACIÓN DE ID MARCACIÓN AL FILTRO
            filtroLogMarcacionesHhppVtDto.setIdMarcacion(idMarcacion);
            listInfoByPage(1);

        } catch (ApplicationException e) {
            String msgError = "Error en: " + ClassUtils.getCurrentMethodName(this.getClass()) + e.getMessage();
            FacesUtil.mostrarMensajeError(msgError, e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en LogMarcacionesHhppVtMglBean:fillSqlList(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Consulta la lista de log marcaciones para proceso de paginación
     *
     * @param page {@code int}
     * @author Gildardo Mora
     */
    public void listInfoByPage(int page) {
        try {
            int firstResult = 0;

            if (page > 1) {
                firstResult = (FILAS_POR_PAGINA * (page - 1));
            }

            filtroConsultaLogMarcacionesHhppVtDto = cmtLogMarcacionesHhppVtMglFacade.findTablasLogMarcacionesHhppVt(
                    filtroLogMarcacionesHhppVtDto, firstResult, FILAS_POR_PAGINA);
            tablasLogMarcacionesHhppVtMglList = filtroConsultaLogMarcacionesHhppVtDto.getListaTablasLogMarcacionesHhppVt();

            if (tablasLogMarcacionesHhppVtMglList.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "No se encontraron resultados para la consulta.", ""));
                return;
            }

            if (tablasLogMarcacionesHhppVtMglList.size() == 1) {
                logMarcacionesHhppVt = tablasLogMarcacionesHhppVtMglList.get(0);
            }

            actual = page;
            mostrarPanelLogMarcaciones = true;

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("No se encontraron resultados para la consulta:.." + e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en LogMarcacionesHhppVtMglBean: listInfoByPage(). " + e.getMessage(), e, LOGGER);
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
     * Redirige a la pagina especifica seleccionada en front.
     * @author Gildardo Mora
     * @param pagina {@link Integer}
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
     * Obtiene el total de paginas para el proceso de paginación.
     *
     * @author Gildardo Mora
     * @return totalPaginas
     */
    public int getTotalPaginas() {
        if (!pintarPaginado) {
            return 1;
        }

        int totalPaginasLoc = 0;

        try {
            long count = filtroConsultaLogMarcacionesHhppVtDto.getNumRegistros();
            totalPaginasLoc = (int) ((count % FILAS_POR_PAGINA != 0) ? (count / FILAS_POR_PAGINA) + 1 : count / FILAS_POR_PAGINA);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en LogMarcacionesHhppVtMglBean: getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        }
        return totalPaginasLoc;
    }

    /**
     *
     * @author Gildardo Mora
     * @return pageActual
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

    /**
     * Realiza el formateo de la fecha recibida.
     *
     * @param fecha {@link LocalDateTime}
     * @return {@link String} Retorna la fecha con el formato especifico en texto
     * @author Gildardo Mora
     */
    public String formatearFecha(LocalDateTime fecha){
        if (Objects.isNull(fecha)) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
        return fecha.format(formatter);
    }

}
