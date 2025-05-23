package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.constantes.cm.MensajeTipoValidacion;
import co.com.claro.mgl.dtos.CmtReglaValidacionDto;
import co.com.claro.mgl.dtos.CmtTipoValidacionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtReglaValidacionFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoValidacionMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtReglaValidacion;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoValidacionMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Manager Bean administrar la página de reglaValidacion.xhtml. Manager Bean
 * administrar la página de reglaValidacion.xhtml.
 *
 * @author ortizjaf
 * @version 1.0 revision 15/05/2017.
 * @see Serializable
 */
@ManagedBean(name = "reglaValidacionBean")
@ViewScoped
public class ReglaValidacionBean implements Serializable {

    /**
     * Constante para el manejo del log de la clase.
     */
    private static final Logger LOGGER = LogManager.getLogger(ReglaValidacionBean.class);
    /**
     * Usuario de la sesión.
     */
    private String usuarioVT = null;
    /**
     * Perfil que pertenece al usuario.
     */
    private int perfilVT = 0;
    /**
     * Contexto de jsf.
     */
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    /**
     * Contiene la sesión del http.
     */
    private HttpSession session = (HttpSession) facesContext.getExternalContext()
            .getSession(false);
    /**
     * Contiene la respuesta del http.
     */
    private HttpServletResponse response = (HttpServletResponse) facesContext
            .getExternalContext().getResponse();
    /**
     * Página actual del paginador.
     */
    private String pageActual;
    /**
     * Página inicial del paginador.
     */
    private String numPagina = "1";
    /**
     * Lista de paginas que contiene actualmente el paginador.
     */
    private List<Integer> paginaList;
    /**
     * Atributo auxiliar de la página actual.
     */
    private int actual;
    /**
     * Cantidad de filas que se puede visualizar en el paginador.
     */
    private int filasPag = ConstantsCM.PAGINACION_CINCO_FILAS;
    /**
     * Lista de dtos de la regla de validación.
     */
    private List<CmtReglaValidacionDto> reglaValidacionList;
    /**
     * Regla de validación dto seleccionada.
     */
    private CmtReglaValidacionDto reglaValidacionDtoSelected;
    /**
     * Lista de proyectos.
     */
    private List<CmtBasicaMgl> basicaProyectoList;
    /**
     * Lista de tipos de validación.
     */
    private List<CmtTipoValidacionMgl> tipoValidacionList;
    /**
     * Lista de proyectos con tipos de validación asociados.
     */
    private List<CmtTipoValidacionMgl> tipoValidacionProyectoList;
    /**
     * Proyecto seleccionado del filtro de búsqueda.
     */
    private String proyectoFiltroSelected;
    /**
     * Proyecto seleccionado para mostrar en los componentes del detalle.
     */
    private String proyectoDetalleSelected;
    /**
     * tipo de validación seleccionado para mostrar en los componentes del
     * detalle.
     */
    private String tipoValidacionDetalleSelected;
    /**
     * Valor de la validación configurado en la pantalla de detalle.
     */
    private String valorValidacionDetalleSelected;
    /**
     * True para renderizar los componentes de detalle de lo contrario false.
     */
    private boolean renderDetalle;
    /**
     * Injección de la interfaz de parámetros.
     */
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    /**
     * Injección la interfaz de básicas.
     */
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    /**
     * Injección de la interfaz de regla de validación.
     */
    @EJB
    private CmtReglaValidacionFacadeLocal reglaValidacionFacadeLocal;
    /**
     * Injección de la interfaz de tipo de validación.
     */
    @EJB
    private CmtTipoValidacionMglFacadeLocal tipoValidacionMglFacadeLocal;

    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    private final String FORMULARIO_ADICION = "TIPOVALIDACION";
    private final String FORMULARIO = "REGLAVALIDACION";
    private final String ROLOPCDELREGVAL = "ROLOPCDELREGVAL";
    private final String ROLOPCIDREGVAL = "ROLOPCIDREGVAL";
    private final String ROLOPCCREATEREGVAL = "ROLOPCCREATEREGVAL";
    private SecurityLogin securityLogin;

    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    
    /**
     * Constructor de esta clase. Constructor de clase que obtiene el usuario y
     * el perfil.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 15/05/2017.
     */
    public ReglaValidacionBean() {
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
        } catch (IOException ex) {
            String msn = "Error al ReglaValidacionBean. ";
            LOGGER.error(msn, ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ReglaValidacionBean. " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Inicialización de variables. Inicialización de variables necesarias para
     * el funcionamiento de la página administrada por el bean.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 15/05/2017.
     */
    @PostConstruct
    public void init() {
        try {
            renderDetalle = false;
            initDetalle();
            CmtTipoBasicaMgl tipoBasicaTipoProyecto = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_PROYECTO_BASICA_ID);
            basicaProyectoList = basicaMglFacadeLocal.
                    findByTipoBasica(tipoBasicaTipoProyecto);

            tipoValidacionList = tipoValidacionMglFacadeLocal.findAll();
            //Asignamos el usuario y perfil a los FacadeLocal
            reglaValidacionFacadeLocal.setUser(usuarioVT, perfilVT);
            tipoValidacionMglFacadeLocal.setUser(usuarioVT, perfilVT);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean:init() " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean:init() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Obtener la regla de validación según filtro. Obtiene la regla de
     * validación, inicia el detalle y llena la tabla.
     *
     * @author Johnntan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param e Contiene el valor seleccionado por el usuario.
     * @return null.
     */
    public String obtenerReglaValidacionFiltro(ValueChangeEvent e) {
        proyectoFiltroSelected = (String) e.getNewValue();
        renderDetalle = false;
        initDetalle();
        return listInfoByPage(1);
    }

    /**
     * Consultar las reglas de validación. Consultar y almacenar las reglas de
     * validación.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param page Página actual del paginador.
     * @return null.
     */
    private String listInfoByPage(int page) {
        try {
            actual = page;
            getPageActual();
            CmtBasicaMgl basicaProyecto = null;
            if (proyectoFiltroSelected != null
                    && !proyectoFiltroSelected.trim().isEmpty()) {
                basicaProyecto = new CmtBasicaMgl();
                basicaProyecto.setBasicaId(
                        new BigDecimal(proyectoFiltroSelected));
            }
            reglaValidacionList = reglaValidacionFacadeLocal.
                    findByFiltroPaginadoDto(actual, filasPag, basicaProyecto);
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO ;
            FacesUtil.mostrarMensajeError(msn+ e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean:listInfoByPage() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Obtener el total de paginas. Obtiene el total de paginas segun la
     * consulta.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return El total de paginas segun la consulta.
     */
    public int getTotalPaginas() {
        try {
            CmtBasicaMgl basicaProyecto = null;
            if (proyectoFiltroSelected != null
                    && !proyectoFiltroSelected.trim().isEmpty()) {
                basicaProyecto = new CmtBasicaMgl();
                basicaProyecto.setBasicaId(
                        new BigDecimal(proyectoFiltroSelected));
            }
            int pageReglas = reglaValidacionFacadeLocal.
                    countByFiltroPaginado(basicaProyecto);
            int totalPaginas = (int) ((pageReglas % filasPag != 0)
                    ? (pageReglas / filasPag) + 1 : pageReglas / filasPag);

            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean:getTotalPaginas() " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean:getTotalPaginas() " + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    /**
     * Consultar todas las reglas de validación. Consulta todas las reglas de
     * validación.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return null.
     */
    public String verTodas() {
        proyectoFiltroSelected = null;
        return listInfoByPage(1);
    }

    /**
     * Realizar la consultay trae los datos de las primeras páginas. Realiza la
     * consultay trae los datos de las primeras páginas.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     */
    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a primera página. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     * Navegar en el paginador haica atrás. Navega en el paginador haica atrás.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
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
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la página anterior. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     * Navagar en el paginador hacia una página predeterminada. Navaga en el
     * paginador hacia una página predeterminada.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
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
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean:irPagina(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean:irPagina(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Navegar en el paginador hacia la siguiente página. Navega en el paginador
     * hacia la siguiente página.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     */
    public void pageNext() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la siguiente página. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     * Navegar hacia la última página del paginador. Navega hacia la última
     * página del paginador.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     */
    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la última página. EX000 " + ex.getMessage(), ex);
        }
    }

    /**
     * Obtener la pagina actual. Obtiene la pagina actual.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return La página actual.
     */
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
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean: getPageActual(). " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    /**
     * Inicializar los componentes del detalle. Inicializa los objetos
     * utilizados en los componentes de detalle.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     */
    public void initDetalle() {
        reglaValidacionDtoSelected = new CmtReglaValidacionDto();
        proyectoDetalleSelected = "";
        tipoValidacionDetalleSelected = "";
        valorValidacionDetalleSelected = "";
        reglaValidacionDtoSelected.setReglaValidacion(new CmtReglaValidacion());
        reglaValidacionDtoSelected.getReglaValidacion().setProyecto(new CmtBasicaMgl());
        reglaValidacionDtoSelected.setTipoValidacionList(new ArrayList<CmtTipoValidacionDto>());
    }

    /**
     * Renderizar la pantalla de detalle. Renderiza la pantalla de detalle.
     *
     * @param reglaDtoSelected La regla de validación seleccionada.
     * @return null.
     */
    public String goActualizar(CmtReglaValidacionDto reglaDtoSelected) {
        renderDetalle = true;
        proyectoDetalleSelected = "";
        tipoValidacionDetalleSelected = "";
        valorValidacionDetalleSelected = "";
        reglaValidacionDtoSelected = reglaDtoSelected;
        proyectoDetalleSelected =
                reglaValidacionDtoSelected.getReglaValidacion().
                getProyecto().getBasicaId().toString();
        return null;
    }

    /**
     * Rederizar la pantalla de creación. Rederiza la pantalla de creación de
     * reglas de validación.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return null.
     */
    public String goCrear() {
        renderDetalle = true;
        initDetalle();
        return null;
    }

    /**
     * Agregar una validacion a una ReglaDto.Agrega una validacion a una
 ReglaDto. Permite Adicionar un validacion a una regla Dto sin persistir.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return 
     */
    public String addValidacionToRegla() {
        try {
            if (tipoValidacionDetalleSelected != null
                    && !tipoValidacionDetalleSelected.trim().isEmpty()
                    && valorValidacionDetalleSelected != null
                    && !valorValidacionDetalleSelected.trim().isEmpty()) {
                CmtTipoValidacionMgl validacionToAdd = new CmtTipoValidacionMgl();
                validacionToAdd.setIdTipoValidacion(
                        new BigDecimal(tipoValidacionDetalleSelected));
                reglaValidacionFacadeLocal.addValidacionToReglaDto(
                        reglaValidacionDtoSelected, validacionToAdd, valorValidacionDetalleSelected);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        Constant.MSN_PROCESO_EXITOSO, ""));
            } else {
                String msn = "Debe seleccionar Tipo de validacion y el valor de la validacion";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean:addValidacionToRegla(). " + e.getMessage(), e, LOGGER);
        }

        return null;
    }

    /**
     * Adicionar reglas al dto de regla validación. Adiciona las reglas al dto
     * de regla validación.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     */
    public void adicionarReglas() {
        try {
            if (proyectoDetalleSelected != null
                    && !proyectoDetalleSelected.trim().isEmpty()) {

                List<CmtTipoValidacionDto> tipoValidacionDtoList =
                        tipoValidacionMglFacadeLocal.findReglasByProyecto(new BigDecimal(proyectoDetalleSelected));
                reglaValidacionDtoSelected.setTipoValidacionList(tipoValidacionDtoList);
            }
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean: adicionarReglas(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Crear una regla a partir a una ReglaDto. Crea una regla a partir a una
     * ReglaDto. Permite Crear una regla a partir a una ReglaDto en el
     * repositorio.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 15/05/2017.
     * @return null.
     */
    public String crearRegla() {
        try {
            if (proyectoDetalleSelected != null
                    && !proyectoDetalleSelected.trim().isEmpty()) {

                if (reglaValidacionDtoSelected.getTipoValidacionList() == null
                        || reglaValidacionDtoSelected.getTipoValidacionList().size() < 1) {
                    String msn = MensajeTipoValidacion.VALIDA_CREACION_REGLA.getValor();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                    return null;
                }

                reglaValidacionDtoSelected.getReglaValidacion().getProyecto().
                        setBasicaId(new BigDecimal(proyectoDetalleSelected));
                reglaValidacionFacadeLocal.createReglaFromDto(
                        reglaValidacionDtoSelected);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        Constant.MSN_PROCESO_EXITOSO, ""));
                renderDetalle = false;
                initDetalle();
                listInfoByPage(1);
            } else {
                String msn = "Debe seleccionar el proyecto para asignar la regla";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            }
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean:crearRegla() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Actualizar una regla a partir a una ReglaDto. Actualiza una regla a
     * partir a una ReglaDto. Permite Actualizar una regla a partir a una
     * ReglaDto en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 15/05/2017.
     * @return null.
     */
    public String actualizarRegla() {
        try {
            reglaValidacionFacadeLocal.updateReglaFromDto(
                    reglaValidacionDtoSelected);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    Constant.MSN_PROCESO_EXITOSO, ""));
            renderDetalle = false;
            initDetalle();
            listInfoByPage(1);
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean:actualizarRegla(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Eliminar la regla de la tabla Elimina la regla de la tabla
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param reglaDtoSelected Regla dto seleccionada para ser eliminada.
     * @return null.
     */
    public String eliminarReglaFromTable(CmtReglaValidacionDto reglaDtoSelected) {
        try {
            reglaValidacionDtoSelected = reglaDtoSelected;
            return eliminarRegla();

        } catch (Exception e) {
            LOGGER.error("Error al eliminar la regla. EX000 " + e.getMessage(), e);
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
        return null;
    }

    /**
     * Eliminar una regla. Elimina una regla. Permite eliminar logicamente una
     * regla en el repositorio.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return null.
     */
    public String eliminarRegla() {
        try {
            reglaValidacionFacadeLocal.deleteRegla(
                    reglaValidacionDtoSelected);
            initDetalle();
            renderDetalle = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    Constant.MSN_PROCESO_EXITOSO, ""));
            renderDetalle = false;
            initDetalle();
            listInfoByPage(1);
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean:eliminarRegla(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    /**
     * Realizar la eliminación del tipo de validación. Realiza la eliminación
     * del tipo de validación seleccionado de una determinada regla.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param tipoValidacionDto Tipo de validación a eliminar.
     * @param reglaValidacion Regla de validación que posee el tipo de
     * validación.
     */
    public void eliminarTipoValidacion(CmtTipoValidacionDto tipoValidacionDto, CmtReglaValidacionDto reglaValidacion) {
        String msn = Constant.MSN_PROCESO_EXITOSO;
        try {
            reglaValidacion.getTipoValidacionList().remove(tipoValidacionDto);
            reglaValidacionFacadeLocal.updateReglaFromDto(reglaValidacion);
            listInfoByPage(1);
            reglaValidacionFacadeLocal.validarTiposValidacionReglas(reglaValidacionList);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean:eliminarTipoValidacion. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean:eliminarTipoValidacion " + e.getMessage(), e, LOGGER);
        }
    }
    
    public boolean validarOpcionReglaIdRol() {
        return validarEdicionRol(ROLOPCIDREGVAL);
    }
    
    public boolean validarOpcionEliminarRol() {
        return validarEdicionRol(ROLOPCDELREGVAL);
    }
    
    public boolean validarOpcionNuevaReglaRol() {
        return validarEdicionRol(ROLOPCCREATEREGVAL);
    }
    
      private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, 
                    cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean validarCreacion() {
        return validarAccion(ValidacionUtil.OPC_CREACION, FORMULARIO);
    }

    /**
     *
     * @return
     */
    public boolean validarEdicion() {
        return validarAccion(ValidacionUtil.OPC_EDICION, FORMULARIO);
    }

    /**
     *
     * @return
     */
    public boolean validarBorrado() {
        return validarAccion(ValidacionUtil.OPC_BORRADO, FORMULARIO);
    }

    /**
     *
     * @return
     */
    public boolean validarAdicionValidacion() {
        return validarAccion(ValidacionUtil.OPC_CREACION, FORMULARIO_ADICION);
    }

    /**
     *
     * @return
     */
    public boolean validarEdicionValidacion() {
        return validarAccion(ValidacionUtil.OPC_EDICION, FORMULARIO_ADICION);
    }

    /**
     *
     * @return
     */
    public boolean validarBorradoValidacion() {
        return validarAccion(ValidacionUtil.OPC_BORRADO, FORMULARIO_ADICION);
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     *
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el
     * componente
     * @return boolean indicador para verificar si se visualizan o no los
     * componentes
     */
    private boolean validarAccion(String opcion, String formulario) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, opcion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ReglaValidacionBean:validarAccion. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * Obtener la lista de las reglas de validación. Obtiene la lista de las
     * reglas de validación.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return La lista de las reglas de validación.
     */
    public List<CmtReglaValidacionDto> getReglaValidacionList() {
        return reglaValidacionList;
    }

    /**
     * Cambiar la lista de las reglas de validación. Cambia la lista de las
     * reglas de validación.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param reglaValidacionList La lista de las reglas de validación.
     */
    public void setReglaValidacionList(List<CmtReglaValidacionDto> reglaValidacionList) {
        this.reglaValidacionList = reglaValidacionList;
    }

    /**
     * Obtener la lista de proyectos. Obtener la lista de proyectos.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return La lista de proyectos.
     */
    public List<CmtBasicaMgl> getBasicaProyectoList() {
        return basicaProyectoList;
    }

    /**
     * Obtener la lista de proyectos. Obtener la lista de proyectos.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param basicaProyectoList La lista de proyectos.
     */
    public void setBasicaProyectoList(List<CmtBasicaMgl> basicaProyectoList) {
        this.basicaProyectoList = basicaProyectoList;
    }

    /**
     * Obtener el proyecto seleccionado. Obtiene el proyecto seleccionado.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return El proyecto seleccionado.
     */
    public String getProyectoFiltroSelected() {
        return proyectoFiltroSelected;
    }

    /**
     * Cambiar el proyecto seleccionado. Cambiar el proyecto seleccionado.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param proyectoFiltroSelected El proyecto seleccionado.
     */
    public void setProyectoFiltroSelected(String proyectoFiltroSelected) {
        this.proyectoFiltroSelected = proyectoFiltroSelected;
    }

    /**
     * Obtener el número de la página. Obtiene el número de la página.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return El número de la página.
     */
    public String getNumPagina() {
        return numPagina;
    }

    /**
     * Cambiar el número de la página. Cambiar el número de la página.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param numPagina
     */
    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    /**
     * Obtener la lista de las páginas. Obtiene la lista de las páginas.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return La lista de las páginas.
     */
    public List<Integer> getPaginaList() {
        return paginaList;
    }

    /**
     * Cambiar la lista de las páginas. Cambia la lista de las páginas.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param paginaList La lista de las páginas.
     */
    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    /**
     * Obtener la página actual. Obtiene la página actual.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return La página actual.
     */
    public int getActual() {
        return actual;
    }

    /**
     * Cambiar la página actual. Cambiar la página actual.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param actual
     */
    public void setActual(int actual) {
        this.actual = actual;
    }

    /**
     * Obtener las filas del paginador. Obtiene las filas del paginador.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return Las filas del paginador.
     */
    public int getFilasPag() {
        return filasPag;
    }

    /**
     * Cambiar las filas del paginador. Cambia las filas del paginador.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param filasPag
     */
    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    /**
     * Obtener el dto seleccionado de la regla de validación. Obtiene el dto
     * seleccionado de la regla de validación.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return El dto seleccionado de la regla de validación.
     */
    public CmtReglaValidacionDto getReglaValidacionDtoSelected() {
        return reglaValidacionDtoSelected;
    }

    /**
     * Cambiar el dto seleccionado de la regla de validación. Cambiar el dto
     * seleccionado de la regla de validación.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param reglaValidacionDtoSelected
     */
    public void setReglaValidacionDtoSelected(CmtReglaValidacionDto reglaValidacionDtoSelected) {
        this.reglaValidacionDtoSelected = reglaValidacionDtoSelected;
    }

    /**
     * Obtener renderización de los componentes de detalle. Obtiene true para
     * renderizar los componentes de detalle de lo contrario false.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return true para renderizar los componentes de detalle de lo contrario
     * false.
     */
    public boolean isRenderDetalle() {
        return renderDetalle;
    }

    /**
     * Cambiar renderización de los componentes de detalle. Cambia true para
     * renderizar los componentes de detalle de lo contrario false.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param renderDetalle
     */
    public void setRenderDetalle(boolean renderDetalle) {
        this.renderDetalle = renderDetalle;
    }

    /**
     * Obtener el detalle del proyecto selecciónado. Obtiene el detalle del
     * proyecto selecciónado.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return El detalle del proyecto selecciónado.
     */
    public String getProyectoDetalleSelected() {
        return proyectoDetalleSelected;
    }

    /**
     * Cambiar el detalle del proyecto selecciónado. Cambia el detalle del
     * proyecto selecciónado.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param proyectoDetalleSelected El detalle del proyecto selecciónado.
     */
    public void setProyectoDetalleSelected(String proyectoDetalleSelected) {
        this.proyectoDetalleSelected = proyectoDetalleSelected;
    }

    /**
     * Obtener el ditpo de validación seleccionado. Obtiene el ditpo de
     * validación seleccionado.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return El ditpo de validación seleccionado.
     */
    public String getTipoValidacionDetalleSelected() {
        return tipoValidacionDetalleSelected;
    }

    /**
     * Cambiar el ditpo de validación seleccionado. Cambia el ditpo de
     * validación seleccionado.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param tipoValidacionDetalleSelected
     */
    public void setTipoValidacionDetalleSelected(String tipoValidacionDetalleSelected) {
        this.tipoValidacionDetalleSelected = tipoValidacionDetalleSelected;
    }

    /**
     * Obtener la lista con los tipos de validación. Obtiene la lista con los
     * tipos de validación.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return La lista con los tipos de validación.
     */
    public List<CmtTipoValidacionMgl> getTipoValidacionList() {
        return tipoValidacionList;
    }

    /**
     * Cambiar la lista con los tipos de validación. Cambia la lista con los
     * tipos de validación.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param tipoValidacionList La lista con los tipos de validación.
     */
    public void setTipoValidacionList(List<CmtTipoValidacionMgl> tipoValidacionList) {
        this.tipoValidacionList = tipoValidacionList;
    }

    /**
     * Obtener el valor seleccionado para la validación (
     * S/N/Proceso/NA/Restringido). Obtiene el valor seleccionado para la
     * validación ( S/N/Proceso/NA/Restringido).
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @return el valor seleccionado para la validación (
     * S/N/Proceso/NA/Restringido).
     */
    public String getValorValidacionDetalleSelected() {
        return valorValidacionDetalleSelected;
    }

    /**
     * Cambiar el valor seleccionado para la validación (
     * S/N/Proceso/NA/Restringido). Cambia el valor seleccionado para la
     * validación ( S/N/Proceso/NA/Restringido).
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 15/05/2017.
     * @param valorValidacionDetalleSelected El valor seleccionado para la
     * validación (S/N/Proceso/NA/Restringido).
     */
    public void setValorValidacionDetalleSelected(String valorValidacionDetalleSelected) {
        this.valorValidacionDetalleSelected = valorValidacionDetalleSelected;
    }

}
