/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.constantes.cobertura.Constants;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.dtos.CmtFiltroConsultaArrendatarioDto;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.ArrendatarioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ArrendatarioMgl;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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

/**
 *
 * @author bocanegravm
 */
@ManagedBean(name = "arrendatarioMglBean")
@ViewScoped
public class ArrendatarioMglBean implements Serializable {

    @EJB
    private ArrendatarioMglFacadeLocal arrendatarioMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;

    private static final Logger LOGGER = LogManager.getLogger(ArrendatarioMglBean.class);
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private Integer perfilVt;
    private List<ArrendatarioMgl> arrendatariosMglList;
    private int filasPag15 = Constants.PAGINACION_QUINCE_FILAS;
    private ArrendatarioMgl arrendatariosMglSelected;
    private boolean habilitaDpto = true;
    private boolean habilitaCity = true;
    private BigDecimal idCiudad;
    private BigDecimal idDpto;
    private List<GeograficoPoliticoMgl> departamentoList;
    private List<GeograficoPoliticoMgl> ciudadesList;
    private List<GeograficoPoliticoMgl> centroPobladoList;
    private List<String> nameCentroPobladoList;
    private List<GeograficoPoliticoMgl> listCentroPoblados;
    private String nombreCentroPoblado;
    private GeograficoPoliticoMgl centroPobladoSelected;
    private GeograficoPoliticoMgl ciudadSelected;
    private GeograficoPoliticoMgl dptoSelected;
    private ArrendatarioMgl arrendatarioMgl;
    private boolean mostrarListaArrenda;
    private boolean mostrarAdminArrenda;
    private boolean botonCrear;
    private boolean botonMod;
    private boolean renderAuditoria;
    private List<AuditoriaDto> informacionAuditoria = null;
    private SecurityLogin securityLogin;
    private final String FORMULARIO = "ARRENDATARIOS";
    private final String NUEVO = "NUEVO";
    private final String DETALLE = "DETALLE";
    private final String LOG = "LOG";
    private final String ELIMINAR = "ELIMINAR";
    private final String CREAR = "CREAR";
    private final String EDITAR = "EDITAR";
    private final String VALIDAROPCNUEVOROLARR = "VALIDAROPCNUEVOROLARR";
    private final String VALIDAROPCEDITARROLARR = "VALIDAROPCEDITARROLARR";
    private final String VALIDAROPCDELROLARR = "VALIDAROPCDELROLARR";
    
    private CmtFiltroConsultaArrendatarioDto filtroConsultaArrendatarioDto = new CmtFiltroConsultaArrendatarioDto();

    /**
     * Creates a new instance of ArrendatarioMglBean
     */
    public ArrendatarioMglBean() {
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

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en ArrendatarioMglBean:" + e.getMessage() + " ", e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ArrendatarioMglBean: " + e.getMessage() + " ", e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            arrendatariosMglList = arrendatarioMglFacadeLocal.findAll();
            mostrarListaArrenda = true;
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en ArrendatarioMglBean: " + ex.getMessage() + " ", ex, LOGGER);
        }
    }

    /**
     * Funci&oacute;n que obtiene el valor completo de el <b>CENTRO POBLADO</b>
     * seleccionado por el usuario en pantalla.
     *
     * @throws ApplicationException
     */
    public void obtenerCentroPobladoSeleccionado() throws ApplicationException {

        if (nombreCentroPoblado != null && !nombreCentroPoblado.isEmpty()) {

            //Consulta el centro poblado
            listCentroPoblados
                    = geograficoPoliticoFacadeLocal.findCentroPobladosByNombre(nombreCentroPoblado);

            if (listCentroPoblados != null && !listCentroPoblados.isEmpty()
                    && listCentroPoblados.size() > 1) {
                ciudadesList = new ArrayList<>();
                //Varios Centro poblados con el mismo nombre busco las ciudades por ese nombre
                for (GeograficoPoliticoMgl centros : listCentroPoblados) {
                    GeograficoPoliticoMgl ciudadCon = geograficoPoliticoFacadeLocal.findById(centros.getGeoGpoId());
                    if (ciudadCon != null && ciudadCon.getGpoId() != null) {
                        ciudadesList.add(ciudadCon);
                    }
                }
                habilitaCity = false;

            } else if ((listCentroPoblados != null && !listCentroPoblados.isEmpty()
                    && listCentroPoblados.size() == 1)) {
                ciudadesList = new ArrayList<>();
                habilitaCity = true;
                habilitaDpto = true;
                //Hay un solo centro poblado con ese nombre
                centroPobladoSelected = listCentroPoblados.get(0);
                //Busco la ciudad de ese centro
                ciudadSelected = geograficoPoliticoFacadeLocal.findById(centroPobladoSelected.getGeoGpoId());
                if (ciudadSelected != null && ciudadSelected.getGpoId() != null) {
                    ciudadesList.add(ciudadSelected);
                    idCiudad = ciudadSelected.getGpoId();
                    //Busco el departamento de la  ciudad 
                    dptoSelected = geograficoPoliticoFacadeLocal.findById(ciudadSelected.getGeoGpoId());
                    if (dptoSelected != null && dptoSelected.getGpoId() != null) {
                        departamentoList = new ArrayList<>();
                        departamentoList.add(dptoSelected);
                        idDpto = dptoSelected.getGpoId();
                    }
                }
            }

        } else {
            idCiudad = null;
            idDpto = null;
            ciudadesList = null;
            departamentoList = null;
            centroPobladoSelected = null;
            dptoSelected = null;
            ciudadSelected = null;
            nombreCentroPoblado = null;
        }
    }

    /**
     * Obtiene el <b>DEPARTAMENTO</b> por ciudad de la base de datos.
     *
     * @throws ApplicationException
     */
    public void obtenerDepartamento() throws ApplicationException {

        if (ciudadesList != null && !ciudadesList.isEmpty() && idCiudad != null) {

            ciudadesList.stream().filter((ciudad) -> (ciudad.getGpoId().compareTo(idCiudad) == 0)).forEachOrdered((ciudad) -> {
                ciudadSelected = ciudad;
            });
            //Busco el departamento de la  ciudad 
            dptoSelected = geograficoPoliticoFacadeLocal.findById(ciudadSelected.getGeoGpoId());
            if (dptoSelected != null && dptoSelected.getGpoId() != null) {
                departamentoList = new ArrayList<>();
                departamentoList.add(dptoSelected);
                idDpto = dptoSelected.getGpoId();
            }
            if (listCentroPoblados != null && !listCentroPoblados.isEmpty()) {
                listCentroPoblados.stream().filter((centros) -> (centros.getGeoGpoId().compareTo(ciudadSelected.getGpoId()) == 0)).forEachOrdered((centros) -> {
                    centroPobladoSelected = centros;
                });
            }
        }

    }

    /**
     * Obtiene el listado del nombre de los <b>CENTRO POBLADOS</b> de la base de
     * datos.
     *
     * @throws ApplicationException
     */
    public void obtenerNombresCentroPobladosList() throws ApplicationException {

        nameCentroPobladoList = geograficoPoliticoFacadeLocal.findNamesCentroPoblado();
    }

    public void irArrendatarioDetalle(ArrendatarioMgl arrendatarioMglSel) throws ApplicationException {

        if (arrendatarioMglSel != null) {
            arrendatarioMgl = arrendatarioMglSel;

            obtenerNombresCentroPobladosList();
            nombreCentroPoblado = arrendatarioMgl.getCentroPoblado().getGpoNombre();
            centroPobladoSelected = arrendatarioMgl.getCentroPoblado();

            ciudadesList = new ArrayList<>();
            ciudadesList.add(arrendatarioMgl.getCiudad());
            idCiudad = arrendatarioMgl.getCiudad().getGpoId();
            ciudadSelected = arrendatarioMgl.getCiudad();

            departamentoList = new ArrayList<>();
            departamentoList.add(arrendatarioMgl.getDepartamento());
            idDpto = arrendatarioMgl.getDepartamento().getGpoId();
            dptoSelected = arrendatarioMgl.getDepartamento();

            mostrarAdminArrenda = true;
            mostrarListaArrenda = false;
            botonCrear = false;
            botonMod = true;
        }

    }

    public void eliminarArrendatarioList(ArrendatarioMgl arrendatarioMglElimina) throws ApplicationException {

        boolean elimina = arrendatarioMglFacadeLocal.delete(arrendatarioMglElimina, usuarioVT, perfilVt);
        if (elimina) {
            filtroConsultaArrendatarioDto = new CmtFiltroConsultaArrendatarioDto();
            String msnError = "Registro eliminado satisfatoriamente";
            arrendatariosMglList = arrendatarioMglFacadeLocal.findAll();
            info(msnError);
        } else {
            String msnError = "Ocurrio un error  eliminando el registro";
            error(msnError);
        }
    }

    public void irToCrearArrendatario() throws ApplicationException {

        arrendatarioMgl = new ArrendatarioMgl();
        mostrarAdminArrenda = true;
        mostrarListaArrenda = false;
        obtenerNombresCentroPobladosList();
        centroPobladoSelected = null;
        dptoSelected = null;
        ciudadSelected = null;
        idCiudad = null;
        idDpto = null;
        nombreCentroPoblado = null;
        botonCrear = true;
        botonMod = false;
        habilitaCity = true;

    }

    public void crearArrendatario() throws ApplicationException {

        if (nombreCentroPoblado == null) {
            String msnError = "Debe selecionar un CENTRO POBLADO "
                    + " por favor. ";
            warn(msnError);
        } else if (idCiudad == null) {
            String msnError = "Debe selecionar una CIUDAD "
                    + " por favor. ";
            warn(msnError);
        } else if (dptoSelected == null) {
            String msnError = "Debe selecionar un DEPARTAMENTO "
                    + " por favor. ";
            warn(msnError);
        } else if (arrendatarioMgl.getNombreArrendatario() == null
                || arrendatarioMgl.getNombreArrendatario().isEmpty()) {
            String msnError = "Debe ingresar un Nombre de arrendatario por favor. ";
            warn(msnError);
        } else if (arrendatarioMgl.getCuadrante() == null
                || arrendatarioMgl.getCuadrante().isEmpty()) {
            String msnError = "Debe ingresar un nombre de cuadrante por favor. ";
            warn(msnError);
        } else {
             if (arrendatarioMgl.getSlaPrevisita() != null && !arrendatarioMgl.getSlaPrevisita().isEmpty()){
                 if (!isNumeric(arrendatarioMgl.getSlaPrevisita())) {
                     String msnError = "El valor de SLA en dias de Previsita debe ser numerico.";
                     warn(msnError);
                     return;
                 }
             }             
              if (arrendatarioMgl.getSlaPermisos() != null && !arrendatarioMgl.getSlaPermisos().isEmpty()){
                 if (!isNumeric(arrendatarioMgl.getSlaPermisos())) {
                     String msnError = "El valor de SLA en dias de Permisos debe ser numerico.";
                     warn(msnError);
                     return;
                 }
             }             
            if (arrendatarioMgl.getSlaPrevisita() != null
                    && arrendatarioMgl.getSlaPrevisita().length() > 20) {
                String msnError = "El campo SLA Previsita excede la cantidad permitida que es 20 caracteres";
                error(msnError);
            } else if (arrendatarioMgl.getSlaPermisos() != null
                    && arrendatarioMgl.getSlaPermisos().length() > 20) {
                String msnError = "El campo SLA Permisos excede la cantidad permitida que es 20 caracteres";
                error(msnError);
            } else {
                ArrendatarioMgl arrendaUnico = arrendatarioMglFacadeLocal.
                        findByCentroAndNombreArrendaAndCuadrante(centroPobladoSelected,
                                arrendatarioMgl.getNombreArrendatario().trim(), arrendatarioMgl.getCuadrante().trim());
                if (arrendaUnico != null && arrendaUnico.getArrendatarioId() != null) {
                    String msnError = "El cuadrante ingresado ya existe en la base de datos.";
                    error(msnError);
                    return;
                } else {
                    arrendatarioMgl.setCentroPoblado(centroPobladoSelected);
                    arrendatarioMgl.setCiudad(ciudadSelected);
                    arrendatarioMgl.setDepartamento(dptoSelected);
                    arrendatarioMgl.setCuadrante(arrendatarioMgl.getCuadrante().trim());
                    arrendatarioMgl = arrendatarioMglFacadeLocal.create(arrendatarioMgl, usuarioVT, perfilVt);

                    if (arrendatarioMgl.getArrendatarioId() != null) {
                        String msnError = "Arrendatario creado satisfactoriamente";
                        info(msnError);

                        volverPanelListArrenda();
                    } else {
                        String msnError = "Ocurrio un error creando el Arrendatario";
                        error(msnError);
                    }
                }
            }
        }
    }

    public void modificarArrendatario() throws ApplicationException {

        if (nombreCentroPoblado == null) {
            String msnError = "Debe selecionar un Centro Poblado "
                    + " por favor. ";
            warn(msnError);
        } else if (dptoSelected == null) {
            String msnError = "Debe selecionar un Departamento "
                    + " por favor. ";
            warn(msnError);
        } else if (arrendatarioMgl.getNombreArrendatario() == null
                || arrendatarioMgl.getNombreArrendatario().isEmpty()) {
            String msnError = "Debe ingresar un Nombre de arrendatario "
                    + " por favor. ";
            warn(msnError);
        } else if (arrendatarioMgl.getCuadrante() == null
                || arrendatarioMgl.getCuadrante().isEmpty()) {
            String msnError = "Debe ingresar un nombre de cuadrante "
                    + " por favor. ";
            warn(msnError);
        } else {
            
            if (arrendatarioMgl.getSlaPrevisita() != null) {
                if (!isNumeric(arrendatarioMgl.getSlaPrevisita())) {
                    String msnError = "El valor de SLA en dias de Previsita debe ser numerico.";
                    warn(msnError);
                    return;
                }
            }
            if (arrendatarioMgl.getSlaPermisos() != null) {
                if (!isNumeric(arrendatarioMgl.getSlaPermisos())) {
                    String msnError = "El valor de SLA en dias de Permisos debe ser numerico.";
                    warn(msnError);
                    return;
                }
            }
              
            if (arrendatarioMgl.getSlaPrevisita() != null
                    && arrendatarioMgl.getSlaPrevisita().length() > 20) {
                String msnError = "El campo SLA Previsita excede la cantidad permitida que es 20 caracteres";
                error(msnError);
            } else if (arrendatarioMgl.getSlaPermisos() != null
                    && arrendatarioMgl.getSlaPermisos().length() > 20) {
                String msnError = "El campo SLA Permisos excede la cantidad permitida que es 20 caracteres";
                error(msnError);
            } else {
                ArrendatarioMgl modifica = arrendatarioMglFacadeLocal.findArrendatariosById(arrendatarioMgl.getArrendatarioId());

                if (modifica != null&& modifica.getCuadrante().trim().equalsIgnoreCase(arrendatarioMgl.getCuadrante().trim())) {
                    
                    arrendatarioMgl.setCentroPoblado(centroPobladoSelected);
                    arrendatarioMgl.setCiudad(ciudadSelected);
                    arrendatarioMgl.setDepartamento(dptoSelected);
                    arrendatarioMgl.setCuadrante(arrendatarioMgl.getCuadrante().trim());
                    arrendatarioMgl = arrendatarioMglFacadeLocal.update(arrendatarioMgl, usuarioVT, perfilVt);

                    String msnError = "Arrendatario modificado satisfactoriamente";
                    info(msnError);
                    arrendatariosMglList = arrendatarioMglFacadeLocal.findAll();
                    mostrarAdminArrenda = false;
                    mostrarListaArrenda = true;
                } else {
                    ArrendatarioMgl arrendaUnico = arrendatarioMglFacadeLocal.
                            findByCentroAndNombreArrendaAndCuadrante(centroPobladoSelected,
                                    arrendatarioMgl.getNombreArrendatario().trim(), arrendatarioMgl.getCuadrante().trim());
                    if (arrendaUnico != null && arrendaUnico.getArrendatarioId() != null) {
                        String msnError = "El cuadrante ingresado ya existe en la base de datos";
                        error(msnError);
                        return;
                    } else {
                        arrendatarioMgl.setCentroPoblado(centroPobladoSelected);
                        arrendatarioMgl.setCiudad(ciudadSelected);
                        arrendatarioMgl.setDepartamento(dptoSelected);
                        arrendatarioMgl.setCuadrante(arrendatarioMgl.getCuadrante().trim());
                        arrendatarioMgl = arrendatarioMglFacadeLocal.update(arrendatarioMgl, usuarioVT, perfilVt);

                        String msnError = "Arrendatario modificado satisfactoriamente";
                        info(msnError);
                        arrendatariosMglList = arrendatarioMglFacadeLocal.findAll();
                        mostrarAdminArrenda = false;
                        mostrarListaArrenda = true;
                    }
                }

            }
        }
    }

    public void volverPanelListArrenda() throws ApplicationException {

        mostrarAdminArrenda = false;
        mostrarListaArrenda = true;
        habilitaCity = true;
        arrendatariosMglList = arrendatarioMglFacadeLocal.findAll();
    }
    /**
     * <b>Mensaje de Advertencia</b>.
     *
     * M&eacute;todo que muestra el mensaje de advertencia, que llega como
     * par&aacute;metro, en la vista.
     *
     * @param mensaje Mensaje a mostrar en pantalla.
     */
    private void warn(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, ""));
    }

    /**
     * <b>Mensaje de Informaci&oacute;n</b>.
     *
     * metodo que muestra el mensaje de informacion, que llega como parametro,
     * en la vista
     *
     * @author Victor Bocanegra
     * @param mensaje
     */
    public void info(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", mensaje));
    }

    /**
     * <b>Mensaje de Error</b>.
     *
     * M&eacute;todo que muestra el mensaje de error, que llega como
     * par&aacute;metro, en la vista.
     *
     * @param mensaje Mensaje a mostrar en pantalla.
     */
    private void error(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
    }

    public void mostrarAuditoria(ArrendatarioMgl arrendatarioMglSel) {
        if (arrendatarioMglSel != null) {
            try {
                informacionAuditoria = arrendatarioMglFacadeLocal.construirAuditoria(arrendatarioMglSel);
                renderAuditoria = true;
                arrendatariosMglSelected = arrendatarioMglSel;
                mostrarListaArrenda = false;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                String msn = "Se presenta error al mostrar la auditoria";
                FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            }
        } else {
            String msn = "No se encontro informacion de auditoria";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }

    }
    
    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean validarPermisos(String accion) {
        try {
            return ValidacionUtil.validarVisualizacionRol(FORMULARIO, accion, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar permisos. EX000 " + e.getMessage(), e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION, ""));
            return false;
        }
    }

    public void ocultarAuditoria() {

        renderAuditoria = false;
        mostrarListaArrenda = true;
        mostrarAdminArrenda = false;

    }

    public boolean validarNuevo() {
        return validarPermisos(NUEVO);
    }

    public boolean validarDetalle() {
        return validarPermisos(DETALLE);
    }

    public boolean validarLOG() {
        return validarPermisos(LOG);
    }

    public boolean validarEliminar() {
        return validarPermisos(ELIMINAR);
    }

    public boolean validarCrear() {
        return validarPermisos(CREAR);
    }

    public boolean validarUpdate() {
        return validarPermisos(EDITAR);
    }
    
    public boolean validarNuevoRol() {
        return validarEdicion(VALIDAROPCNUEVOROLARR);
    }
    
    public boolean validarEditarRol() {
        return validarEdicion(VALIDAROPCEDITARROLARR);
    }
   
    public boolean validarOpcionEliminarRol() {
        return validarEdicion(VALIDAROPCDELROLARR);
    }
    
    private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al validarEdicion en ArrendatarioMglBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    public void filtrarInfo() throws ApplicationException {
        try {
            arrendatariosMglList = arrendatarioMglFacadeLocal.findArrendatariosByFiltro(filtroConsultaArrendatarioDto);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSN_PROCESO_FALLO + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en  ArrendatarioMglBean " + e.getMessage(), e, LOGGER);
        }
    }

    public List<ArrendatarioMgl> getArrendatariosMglList() {
        return arrendatariosMglList;
    }

    public void setArrendatariosMglList(List<ArrendatarioMgl> arrendatariosMglList) {
        this.arrendatariosMglList = arrendatariosMglList;
    }

    public int getFilasPag15() {
        return filasPag15;
    }

    public void setFilasPag15(int filasPag15) {
        this.filasPag15 = filasPag15;
    }

    public ArrendatarioMgl getArrendatariosMglSelected() {
        return arrendatariosMglSelected;
    }

    public void setArrendatariosMglSelected(ArrendatarioMgl arrendatariosMglSelected) {
        this.arrendatariosMglSelected = arrendatariosMglSelected;
    }

    public boolean isHabilitaDpto() {
        return habilitaDpto;
    }

    public void setHabilitaDpto(boolean habilitaDpto) {
        this.habilitaDpto = habilitaDpto;
    }

    public boolean isHabilitaCity() {
        return habilitaCity;
    }

    public void setHabilitaCity(boolean habilitaCity) {
        this.habilitaCity = habilitaCity;
    }

    public BigDecimal getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(BigDecimal idCiudad) {
        this.idCiudad = idCiudad;
    }

    public BigDecimal getIdDpto() {
        return idDpto;
    }

    public void setIdDpto(BigDecimal idDpto) {
        this.idDpto = idDpto;
    }

    public List<GeograficoPoliticoMgl> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<GeograficoPoliticoMgl> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public List<GeograficoPoliticoMgl> getCiudadesList() {
        return ciudadesList;
    }

    public void setCiudadesList(List<GeograficoPoliticoMgl> ciudadesList) {
        this.ciudadesList = ciudadesList;
    }

    public List<GeograficoPoliticoMgl> getCentroPobladoList() {
        return centroPobladoList;
    }

    public void setCentroPobladoList(List<GeograficoPoliticoMgl> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }

    public List<String> getNameCentroPobladoList() {
        return nameCentroPobladoList;
    }

    public void setNameCentroPobladoList(List<String> nameCentroPobladoList) {
        this.nameCentroPobladoList = nameCentroPobladoList;
    }

    public String getNombreCentroPoblado() {
        return nombreCentroPoblado;
    }

    public void setNombreCentroPoblado(String nombreCentroPoblado) {
        this.nombreCentroPoblado = nombreCentroPoblado;
    }

    public ArrendatarioMgl getArrendatarioMgl() {
        return arrendatarioMgl;
    }

    public void setArrendatarioMgl(ArrendatarioMgl arrendatarioMgl) {
        this.arrendatarioMgl = arrendatarioMgl;
    }

    public boolean isMostrarListaArrenda() {
        return mostrarListaArrenda;
    }

    public void setMostrarListaArrenda(boolean mostrarListaArrenda) {
        this.mostrarListaArrenda = mostrarListaArrenda;
    }

    public boolean isMostrarAdminArrenda() {
        return mostrarAdminArrenda;
    }

    public void setMostrarAdminArrenda(boolean mostrarAdminArrenda) {
        this.mostrarAdminArrenda = mostrarAdminArrenda;
    }

    public boolean isBotonCrear() {
        return botonCrear;
    }

    public void setBotonCrear(boolean botonCrear) {
        this.botonCrear = botonCrear;
    }

    public boolean isBotonMod() {
        return botonMod;
    }

    public void setBotonMod(boolean botonMod) {
        this.botonMod = botonMod;
    }

    public boolean isRenderAuditoria() {
        return renderAuditoria;
    }

    public void setRenderAuditoria(boolean renderAuditoria) {
        this.renderAuditoria = renderAuditoria;
    }

    public List<AuditoriaDto> getInformacionAuditoria() {
        return informacionAuditoria;
    }

    public void setInformacionAuditoria(List<AuditoriaDto> informacionAuditoria) {
        this.informacionAuditoria = informacionAuditoria;
    }

    public CmtFiltroConsultaArrendatarioDto getFiltroConsultaArrendatarioDto() {
        return filtroConsultaArrendatarioDto;
    }

    public void setFiltroConsultaArrendatarioDto(CmtFiltroConsultaArrendatarioDto filtroConsultaArrendatarioDto) {
        this.filtroConsultaArrendatarioDto = filtroConsultaArrendatarioDto;
    }
    
}
