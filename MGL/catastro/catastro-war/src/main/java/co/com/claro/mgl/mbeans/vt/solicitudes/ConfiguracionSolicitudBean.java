package co.com.claro.mgl.mbeans.vt.solicitudes;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtGrupoProyectoValidacionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtGrupoProyectoValidacionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.Constant;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Juan David Hernandez
 */
@ManagedBean(name = "configuracionSolicitudBean")
@ViewScoped
public class ConfiguracionSolicitudBean implements Serializable {

    private BigDecimal idGrupoProyectoBasica;
    private BigDecimal idProyectoBasica;
    private CmtGrupoProyectoValidacionMgl configuracionSeleccionada;
    private List<CmtBasicaMgl> grupoProyectoBasicaList;
    private List<CmtBasicaMgl> proyectoBasicaList;
    private List<CmtGrupoProyectoValidacionMgl> configuracionSolicitudList;
    private List<CmtGrupoProyectoValidacionMgl> configuracionSolicitudTmpList;
    private List<CmtBasicaMgl> tipoGrupoProyectoTmpList;
    private boolean showConfiguracionSolicitudList;
    private boolean showEditConfiguracionSolicitud;
    private String usuarioVT = null;
    private int perfilVt = 0;
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private String regresarMenu = "<- Regresar Menú";
    private int filasPag = ConstantsCM.PAGINACION_DIEZ_FILAS;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtGrupoProyectoValidacionMglFacadeLocal cmtGrupoProyectoValidacionMglFacadeLocal;
    private static final Logger LOGGER = LogManager.getLogger(ConfiguracionSolicitudBean.class);

    public ConfiguracionSolicitudBean() {
        try {
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();
            if (usuarioVT == null) {
                session.getAttribute("usuarioM");
                usuarioVT = (String) session.getAttribute("usuarioM");
            } else {
                session = (HttpSession) facesContext.getExternalContext().getSession(false);
                session.setAttribute("usuarioM", usuarioVT);
            }
            showListar();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConfiguracionSolicitudBean " + e.getMessage() , e, LOGGER);  
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConfiguracionSolicitudBean " + e.getMessage() , e, LOGGER);  
        }
    }

    @PostConstruct
    public void init() {
        try {
         CmtTipoBasicaMgl tipoBasicaGrupoProyectoGeneral
                 = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_GRUPO_PROYECTO_ID);

            grupoProyectoBasicaList = cmtBasicaMglFacadeLocal
                    .findGrupoProyectoBasicaList(tipoBasicaGrupoProyectoGeneral.getTipoBasicaId());
            
            CmtTipoBasicaMgl tipoBasicaGrupoProyecto
                 = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_PROYECTO_BASICA_ID);
            
            proyectoBasicaList = cmtBasicaMglFacadeLocal
                    .findProyectoBasicaList(tipoBasicaGrupoProyecto.getTipoBasicaId());
            
            cmtGrupoProyectoValidacionMglFacadeLocal
                    .setUser(usuarioVT, perfilVt);
            cargarConfiguracionSolicitudList(true);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error en cargue de listados "
                    + "de configuración de solicitudes" + e.getMessage() , e, LOGGER);  
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en cargue de listados "
                    + "de configuración de solicitudes" + e.getMessage() , e, LOGGER);  
        }
    }

    /**
     * Función utilizada para hacer el cargue del listado que se despliega en la
     * tabla principal.
     *
     * @author Juan David Hernandez
     * @param cargarPaginacion
     */
    public void cargarConfiguracionSolicitudList(boolean cargarPaginacion) {
        try {
            if (cargarPaginacion) {
                /* Cargue de listado de configuraciones de solicitudes
                 * existentes en la base de datos.*/
                configuracionSolicitudTmpList = cmtGrupoProyectoValidacionMglFacadeLocal.findAll();
            }
            configuracionSolicitudList = new ArrayList();
            if (!configuracionSolicitudTmpList.isEmpty()) {
                if (grupoProyectoBasicaList != null
                        && !grupoProyectoBasicaList.isEmpty()
                        && proyectoBasicaList != null
                        && !proyectoBasicaList.isEmpty()) {

                    tipoGrupoProyectoTmpList = new ArrayList();
                    tipoGrupoProyectoTmpList.addAll(grupoProyectoBasicaList);
                    tipoGrupoProyectoTmpList.addAll(proyectoBasicaList);

                    /*Realiza recorrido sobre lista global para extraer 
                     * listados internos y armar una nueva lista*/
                    for (CmtGrupoProyectoValidacionMgl cmtGeneral
                            : configuracionSolicitudTmpList) {

                        CmtGrupoProyectoValidacionMgl obj = new CmtGrupoProyectoValidacionMgl();
                        obj.setGrupoProyectoId(cmtGeneral.getGrupoProyectoId());
                        obj.setFechaCreacion(cmtGeneral.getFechaCreacion());
                        obj.setFechaEdicion(cmtGeneral.getFechaEdicion());
                        obj.setUsuarioCreacion(cmtGeneral.getUsuarioCreacion());
                        obj.setUsuarioEdicion(cmtGeneral.getUsuarioEdicion());
                        obj.setPerfilCreacion(cmtGeneral.getPerfilCreacion());
                        obj.setPerfilEdicion(cmtGeneral.getPerfilEdicion());
                        obj.setEstadoRegistro(cmtGeneral.getEstadoRegistro());

                        for (CmtBasicaMgl cmtBasica : tipoGrupoProyectoTmpList) {
                            if (cmtGeneral.getGrupoProyectoBasicaId() != null
                                    && cmtGeneral.getGrupoProyectoBasicaId()
                                    .equals(cmtBasica.getBasicaId())) {
                                obj.setGrupoProyectoBasicaId(cmtBasica.getBasicaId());
                                obj.setAbreviaturaGrupoProyecto(cmtBasica.getAbreviatura());
                                obj.setNombreGrupoProyecto(cmtBasica.getNombreBasica());
                            } else {
                                if (cmtGeneral.getProyectoBasicaId() != null
                                        && cmtGeneral.getProyectoBasicaId()
                                        .equals(cmtBasica.getBasicaId())) {
                                    obj.setProyectoBasicaId(cmtBasica.getBasicaId());
                                    obj.setAbreviaturaProyectoBasica(cmtBasica.getAbreviatura());
                                    obj.setNombreProyectoBasica(cmtBasica.getNombreBasica());
                                }
                            }
                        }

                        //Inserta objeto en listado que se desplegará en pantalla.
                        if (obj.getGrupoProyectoId() != null
                                && obj.getProyectoBasicaId() != null) {
                            configuracionSolicitudList.add(obj);
                        }
                    }
                }
            }        


        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error en cargue de listado de tabla de"
                + " configuración de solicitudes" + e.getMessage() , e, LOGGER);  
	} catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en cargue de listado de tabla de"
                + " configuración de solicitudes" + e.getMessage() , e, LOGGER);  
	}
    }

    /**
     * Realiza redireccionamiento a pantalla de editar
     *
     * @author Juan David Hernandez
     * @param configuracion
     */
    public void goEditarConfiguracion(CmtGrupoProyectoValidacionMgl configuracion) {
        try {
            if (configuracion != null) {
                configuracionSeleccionada = new CmtGrupoProyectoValidacionMgl();
                configuracionSeleccionada.setGrupoProyectoId(configuracion.getGrupoProyectoId());
                configuracionSeleccionada.setGrupoProyectoBasicaId(configuracion.getGrupoProyectoBasicaId());
                configuracionSeleccionada.setProyectoBasicaId(configuracion.getProyectoBasicaId());
                configuracionSeleccionada.setFechaCreacion(configuracion.getFechaCreacion());
                configuracionSeleccionada.setUsuarioCreacion(configuracion.getUsuarioCreacion());
                configuracionSeleccionada.setPerfilCreacion(configuracion.getPerfilCreacion());
                configuracionSeleccionada.setEstadoRegistro(configuracion.getEstadoRegistro());
                showEditar();
            } else {
                String msn = "Se ha presentado un error seleccionado "
                        + "la configuración. Intente de nuevo por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al redireccionar a editar" + e.getMessage() , e, LOGGER);  
        }
    }


    /**
     * Función que realiza inserción del registro en la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void guardarConfiguracion() {
        try {
            if (validarCrearConfiguracion()) {
                CmtGrupoProyectoValidacionMgl configuracionSolicitud = new CmtGrupoProyectoValidacionMgl();
                configuracionSolicitud.setGrupoProyectoBasicaId(idGrupoProyectoBasica);
                configuracionSolicitud.setProyectoBasicaId(idProyectoBasica);

                cmtGrupoProyectoValidacionMglFacadeLocal
                        .create(configuracionSolicitud);
                cargarConfiguracionSolicitudList(true);
                limpiarForm();
                String msn = "Configuración creada satisfactoriamente.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error al crear configuración" + e.getMessage() , e, LOGGER);  
	} catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al crear configuración" + e.getMessage() , e, LOGGER);  
	}
    }
    

    /**
     * Función que realiza la actualización del registro en la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void editarConfiguracion() {
        try {
            if (validarEditarConfiguracion()) {
                cmtGrupoProyectoValidacionMglFacadeLocal
                        .update(configuracionSeleccionada);
                limpiarForm();
                cargarConfiguracionSolicitudList(true);
                showListar();
                String msn = "Configuración editada satisfactoriamente";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error en editar configuracion" + e.getMessage() , e, LOGGER);  
	} catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en editar configuracion" + e.getMessage() , e, LOGGER);  
	}
    }

    /**
     * Función que elimina lógicamente el registro en la base de datos.
     *
     * @author Juan David Hernandez
     * @param configuracionDelete
     */
    public void eliminarConfiguracion(CmtGrupoProyectoValidacionMgl configuracionDelete) {
        try {
            if (configuracionDelete.getGrupoProyectoId() == null
                    || configuracionDelete.getGrupoProyectoId().equals(BigDecimal.ZERO)) {
                String msn = "Ha ocurrido un error al seleccionar el registro. "
                        + "Intente nuevamente por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            if (cmtGrupoProyectoValidacionMglFacadeLocal
                    .delete(configuracionDelete)) {
                limpiarForm();
                cargarConfiguracionSolicitudList(true);
                String msn = "Configuración eliminada satisfactoriamente";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            } else {
                String msn = "Ha ocurrido un error intentando eliminar"
                        + " el registro. Intente nuevamente por favor.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error en eliminar configuracion" + e.getMessage() , e, LOGGER);  
	} catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error en eliminar configuracion" + e.getMessage() , e, LOGGER);  
	}
    }

    /**
     * Función que valida que la información a persistir no se encuentre nula.
     *
     * @author Juan David Hernandez
     * @return 
     */
    public boolean validarCrearConfiguracion() {
        try {
            if (idGrupoProyectoBasica == null || idGrupoProyectoBasica.equals(BigDecimal.ZERO)) {
                String msn = "Seleccione un tipo de grupo de proyecto por favor.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            }
            if (idProyectoBasica == null || idProyectoBasica.equals(BigDecimal.ZERO)) {
                String msn = "Seleccione un tipo de proyecto por favor.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            }
            return true;
     
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al validar datos de crear"
                + " configuración" + e.getMessage() , e, LOGGER);
            return false;
	}
    }

    /**
     * Función que valida que la información a editar no se encuentre nula.
     *
     * @author Juan David Hernandez
     * @return boolean
     */
    public boolean validarEditarConfiguracion() {
        try {
            if (configuracionSeleccionada.getGrupoProyectoBasicaId() == null
                    || configuracionSeleccionada.getGrupoProyectoBasicaId().equals(BigDecimal.ZERO)) {
                String msn = "Seleccione un tipo de grupo de proyecto por favor.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            }
            if (configuracionSeleccionada.getProyectoBasicaId() == null
                    || configuracionSeleccionada.getProyectoBasicaId().equals(BigDecimal.ZERO)) {
                String msn = "Seleccione un tipo de proyecto por favor.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
                return false;
            }
            if (configuracionSeleccionada.getGrupoProyectoId() == null
                    || configuracionSeleccionada.getGrupoProyectoId().equals(BigDecimal.ZERO)) {
                String msn = "Ha ocurrido un error al seleccionar el registro. Intente nuevamente por favor.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return false;
            }
            return true;

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al validar datos de editar "
                + "configuración" + e.getMessage() , e, LOGGER); 
            return false;
        }
    }

    /**
     * Función que carga listado de configuraciones.
     *
     * @author Juan David Hernandez
     */
    public void goConfiguracionSolicitudesList() {
        try {
            limpiarForm();
            cargarConfiguracionSolicitudList(true);
            showListar();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al intentar regresar atrás a cargar"
                + " listado" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * Función que limpia la información de la pantalla.
     *
     * @author Juan David Hernandez
     */
    public void limpiarForm() {
        idGrupoProyectoBasica = BigDecimal.ZERO;
        idProyectoBasica = BigDecimal.ZERO;
        configuracionSeleccionada = new CmtGrupoProyectoValidacionMgl();
    }

    /**
     * Función que redirecciona al menú principal del a aplicación.
     *
     * @author Juan David Hernandez
     * @throws java.io.IOException
     */
    public void redirecToMenu() throws IOException {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Visitas_Tecnicas/faces/Gestion.jsp");

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se genera error al itentar regresar al menú"
                + " principal" + e.getMessage() , e, LOGGER);
	} catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al itentar regresar al menú"
                + " principal" + e.getMessage() , e, LOGGER);
	}
    }

    /**
     * Función que realiza paginación de la tabla.
     *
     * @return null;
     * @author Juan David Hernandez
     */
    private String listInfoByPage(int page) {
        try {
            actual = page;
            getPageActual();
            configuracionSolicitudTmpList = cmtGrupoProyectoValidacionMglFacadeLocal
                    .findPendientesParaGestionPaginacion(page, filasPag);
            cargarConfiguracionSolicitudList(false);
        
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en list Info By Page " + e.getMessage() , e, LOGGER);
	} catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en list Info By Page " + e.getMessage() , e, LOGGER);
	}
        return null;
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
            FacesUtil.mostrarMensajeError("Error en page First " + e.getMessage() , e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error en page Previous " + e.getMessage() , e, LOGGER);
	}
    }

    /**
     * Función que cpermite ir directamente a la página seleccionada de
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
            FacesUtil.mostrarMensajeError("Error en ir Pagina " + e.getMessage() , e, LOGGER);
	} catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ir Pagina " + e.getMessage() , e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error en page Next " + e.getMessage() , e, LOGGER);
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
        
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en page Last " + e.getMessage() , e, LOGGER);
	}
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     *
     * @author Juan David Hernandez
     * @return 
     */
    public int getTotalPaginas() {
        try {
            List<CmtGrupoProyectoValidacionMgl> configuracionSolicitudAuxList = cmtGrupoProyectoValidacionMglFacadeLocal.findAll();
            int pageSol = configuracionSolicitudAuxList.size();
            return ((pageSol % filasPag != 0)
                    ? (pageSol / filasPag) + 1 : pageSol / filasPag);

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginas " + e.getMessage() , e, LOGGER);
	} catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginas " + e.getMessage() , e, LOGGER);
	}
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario en
     * los resultados.
     *
     * @author Juan David Hernandez
     * @return 
     */
    public String getPageActual() {
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
        return pageActual;
    }

    /**
     * Función que permite rederizar la página de editar.
     *
     * @author Juan David Hernandez
     */
    public void showEditar() {
        showEditConfiguracionSolicitud = true;
        showConfiguracionSolicitudList = false;
    }

    /**
     * Función que permite rederizar la página de listado.
     *
     * @author Juan David Hernandez
     */
    public void showListar() {
        showEditConfiguracionSolicitud = false;
        showConfiguracionSolicitudList = true;
    }

    public boolean isShowConfiguracionSolicitudList() {
        return showConfiguracionSolicitudList;
    }

    public void setShowConfiguracionSolicitudList(boolean showConfiguracionSolicitudList) {
        this.showConfiguracionSolicitudList = showConfiguracionSolicitudList;
    }

    public boolean isShowEditConfiguracionSolicitud() {
        return showEditConfiguracionSolicitud;
    }

    public void setShowEditConfiguracionSolicitud(boolean showEditConfiguracionSolicitud) {
        this.showEditConfiguracionSolicitud = showEditConfiguracionSolicitud;
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

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public BigDecimal getIdProyectoBasica() {
        return idProyectoBasica;
    }

    public void setIdProyectoBasica(BigDecimal idProyectoBasica) {
        this.idProyectoBasica = idProyectoBasica;
    }

    public List<CmtBasicaMgl> getGrupoProyectoBasicaList() {
        return grupoProyectoBasicaList;
    }

    public void setGrupoProyectoBasicaList(List<CmtBasicaMgl> grupoProyectoBasicaList) {
        this.grupoProyectoBasicaList = grupoProyectoBasicaList;
    }

    public List<CmtBasicaMgl> getProyectoBasicaList() {
        return proyectoBasicaList;
    }

    public void setProyectoBasicaList(List<CmtBasicaMgl> proyectoBasicaList) {
        this.proyectoBasicaList = proyectoBasicaList;
    }

    public List<CmtGrupoProyectoValidacionMgl> getConfiguracionSolicitudList() {
        return configuracionSolicitudList;
    }

    public void setConfiguracionSolicitudList(List<CmtGrupoProyectoValidacionMgl> configuracionSolicitudList) {
        this.configuracionSolicitudList = configuracionSolicitudList;
    }

    public CmtGrupoProyectoValidacionMgl getConfiguracionSeleccionada() {
        return configuracionSeleccionada;
    }

    public void setConfiguracionSeleccionada(CmtGrupoProyectoValidacionMgl configuracionSeleccionada) {
        this.configuracionSeleccionada = configuracionSeleccionada;
    }

    public BigDecimal getIdGrupoProyectoBasica() {
        return idGrupoProyectoBasica;
    }

    public void setIdGrupoProyectoBasica(BigDecimal idGrupoProyectoBasica) {
        this.idGrupoProyectoBasica = idGrupoProyectoBasica;
    }

    public String getRegresarMenu() {
        return regresarMenu;
    }

    public void setRegresarMenu(String regresarMenu) {
        this.regresarMenu = regresarMenu;
    }

}
