/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.migracion.beans;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.TecArcCamEstratoFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.TecArchivosCambioEstrato;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.AccionesVT;
import static co.com.claro.mgl.mbeans.vt.migracion.beans.enums.EstadoSolicitud.FINALIZADO;
import static co.com.claro.mgl.mbeans.vt.migracion.beans.enums.EstadoSolicitud.PENDIENTE;
import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.TipoParametroCalle;
import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.TipoSolicitud;
import co.com.claro.mgl.mbeans.vt.migracion.beans.util.JsfUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.MailSender;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;


/**
 *
 * @author becerraarmr
 */
public class CreateSolicitud extends VisitasTecnicasController {

 private static final Logger LOGGER = LogManager.getLogger(CreateSolicitud.class);

    private Solicitud solicitud;

    private String dirCalleAlterna;

    private String dirTipoCalleCompleta;

    private String dirComplemento;

    private String dirTipoCalleAlterna;

    private String dirCalleCompleta;

    private String dirPlacaAlternaUno;

    private String dirPlacaAlternaDos;

    private String dirAptoAlterna;

    private String numPuertaUno;

    private String numPuertaDos;

    private Map<String, BigDecimal> regionales;//label, value

    private Map<String, BigDecimal> ciudades;

    private Map<String, String> tipoSolicitudes;

    private Map<String, String> apartamentos;

    private Map<String, String> calles;

    private Map<String, String> tipoVentas;

    private Map<String, String> productosNuevos;

    private Map<String, String> usuarios;

    private Map<String, String> resultadoGestiones;

    private boolean canCreate = true;
    
 

  private boolean hayMensaje=false;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private SecurityLogin securityLogin;
    private String usuarioVT = null;
    private int perfilVT = 0;

    private BigDecimal idRegional;
    private String tipoCuenta;
    private boolean cambiaEstratoCM = true;
    private boolean cambiaEstratoHHPP = true;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cuentaMatrizMglFacadeLocal;
    @EJB
    private TecArcCamEstratoFacadeLocal tecArcCamEstratoFacadeLocal;
    CmtCuentaMatrizMgl cuentaMatrizMgl;
    
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
    
    private BigDecimal ciudadRr;
    private String estAntHhpp;
    private String estNueHhpp;
    private List<ParametrosCalles> resultGestionCambioEs;
    private UploadedFile fileCambioEstrato;
    private List<String> lstArchivosCambiosEstrato;
    private boolean creoSolicitud = false;
    public HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

    @EJB
    private UsuarioServicesFacadeLocal usuarioServicesFacade;
    @EJB
    private CmtBasicaMglFacadeLocal basicasMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    private UsuariosServicesDTO usuarioSolicitudCm;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cmtCuentaMatrizMglFacadeLocal;
   
    //Opciones agregadas para Rol
    private final String IGBTNCRSL = "IGBTNCRSL";
    //Opciones agregadas para Rol
    private final String IGBTNVBIT = "IGBTNVBIT";
    //Opciones agregadas para Rol
    private final String IGBTNVTEC = "IGBTNVTEC";
    //Opciones agregadas para Rol
    private final String IGBTVTCAS = "IGBTVTCAS";
    //Opciones agregadas para Rol
    private final String IGBTCTAMZ = "IGBTCTAMZ";
    //Opciones agregadas para Rol
    private final String IGBTHHPMZ = "IGBTHHPMZ";
    //Opciones agregadas para Rol
    private final String IGBTDELCU = "IGBTDELCU";
    //Opciones agregadas para Rol
    private final String IGBTINGDTOS = "IGBTINGDTOS";
    /**
     * Creates a new instance of ControllerSolicitud
     */
    public CreateSolicitud() {
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
            lstArchivosCambiosEstrato = new ArrayList<String>();

        } catch (IOException ex) {
            LOGGER.error("Se genera error en " + CreateSolicitud.class.getName() + ex);
            String msnErr = "Ha ocurrido un error generando la solicitud ." + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msnErr, ""));
        } catch (Exception ex) {
            LOGGER.error("Se genera error en " + CreateSolicitud.class.getName() + ex);
            String msnErr = "Ha ocurrido un error generando la solicitud." + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msnErr, ""));
        }
    }

    private void cargarRegionales() {
        try {
            List<CmtRegionalRr> listAux = this.rrRegionalesFacadeLocal.findAllRegionales();
            regionales = JsfUtil.getRegionales(listAux);
        } catch (ApplicationException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    public Map<String, BigDecimal> getRegionales() {
        cargarRegionales();
        return regionales;
    }

    public Map<String, String> getUsuarios() {
        cargarTipoSolicitudes(TipoParametroCalle.PARAMETRO_CALLE_USUARIOS);
        return usuarios;
    }

    public Map<String, String> getResultadoGestiones(AccionesVT accion) {
        TipoParametroCalle tipoParametroCalle;

        AccionesVT accionesVt = (AccionesVT) session.getAttribute("accionVt");
        if (accionesVt != null) {
            accion = accionesVt;
        }

    switch(accion){
            case GESTIONAR_VT_REPLANTEAMIENTO:
                session.removeAttribute("solicitud");
                session.removeAttribute("usuario");
                session.removeAttribute("facade");
                session.removeAttribute("accionVt");
      case GESTIONAR_VIABILIDAD_INTERNET:{
        cargarTipoSolicitudes(TipoParametroCalle.
                PARAMETROS_CALLE_RESULTADO_GESTION);
                session.removeAttribute("solicitud");
                session.removeAttribute("usuario");
                session.removeAttribute("facade");
                session.removeAttribute("accionVt");
                break;
            }
      case GESTIONAR_VT_EDIFICIO_CONJUNTO:{
        cargarTipoSolicitudes(TipoParametroCalle.
                PARAMETROS_CALLE_RESULTADO_GESTION);
                break;
            }
      case GESTIONAR_VT_EN_CASAS:{
        cargarTipoSolicitudes(TipoParametroCalle.
                PARAMETROS_CALLE_RESULTADO_GESTION);
                break;
            }
      case GESTIONAR_CREACION_CUENTA_MATRIZ:{
        cargarTipoSolicitudes(TipoParametroCalle.
                PARAMETROS_CALLE_RESULTADO_GESTION_CREACM);
                break;
            }
      case GESTIONAR_CREACION_HHPP_EN_CUENTA_MATRIZ:{
        cargarTipoSolicitudes(TipoParametroCalle.
                PARAMETROS_CALLE_RESULTADO_GESTION_CREAHHPPCM);
                break;
            }
      case GESTIONAR_MODIFICAR_ELIMINAR_CUENTA_MATRIZ:{
        cargarTipoSolicitudes(TipoParametroCalle.
                PARAMETROS_CALLE_RESULTADO_GESTION_MODCM);
                break;
            }
      case GESTIONAR_ACTUALIZACION_CAMBIO_ESTRATO:{
        cargarTipoSolicitudes(TipoParametroCalle.
                PARAMETROS_CALLE_RESULTADO_GESTION_CE);
                session.removeAttribute("solicitud");
                session.removeAttribute("usuario");
                session.removeAttribute("facade");
                session.removeAttribute("accionVt");
                break;
            }
        }
        return resultadoGestiones;
    }

    private Map<String, String> findByTipos(TipoParametroCalle tipo) {
        Map<String, String> lista = new LinkedHashMap();
        try {
            lista.put("", ""); //label, value
            List<ParametrosCalles> listAux = parametrosCallesFacadeLocal.
                    findByTipo(tipo.getValor());
      if(TipoParametroCalle.PARAMETROS_CALLE_RESULTADO_GESTION.equals(tipo)){
        List<ParametrosCalles> listResultadoGestion=new ArrayList<ParametrosCalles>();
                for (ParametrosCalles parametrosCalles : listAux) {
          String valor=parametrosCalles.getIdTipo();
          if("RESULTADO_GESTION".equalsIgnoreCase(valor)){
                        listResultadoGestion.add(parametrosCalles);
                    }
                }
        listAux=listResultadoGestion;
            }
            lista = JsfUtil.getParametrosCalles(listAux);
        } catch (ApplicationException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return lista;
    }

    private void cargarTipoSolicitudes(TipoParametroCalle tipoParametroCalle) {
        switch (tipoParametroCalle) {
            case PARAMETRO_CALLE_SEGMENTO:
            case PARAMETRO_CALLE_SOLICITUD: {
                tipoSolicitudes = findByTipos(tipoParametroCalle);
                break;
            }
            case PARAMETRO_CALLE_APARTAMENTOS: {
                apartamentos = findByTipos(tipoParametroCalle);
                break;
            }
            case PARAMETRO_CALLE_VIAS: {
                calles = findByTipos(tipoParametroCalle);
                break;
            }
            case PARAMETRO_CALLE_VENTA: {
                tipoVentas = findByTipos(tipoParametroCalle);
                break;
            }
            case PARAMETRO_CALLE_PRODUCTO: {
                productosNuevos = findByTipos(tipoParametroCalle);
                break;
            }
            case PARAMETRO_CALLE_USUARIOS: {
                usuarios = findByTipos(tipoParametroCalle);
                break;
            }
            case PARAMETROS_CALLE_RESULTADO_GESTION:
            case PARAMETROS_CALLE_RESULTADO_GESTION_CE:
            case PARAMETROS_CALLE_RESULTADO_GESTION_VC:
            case PARAMETROS_CALLE_RESULTADO_GESTION_CREAHHPPCM:
            case PARAMETROS_CALLE_RESULTADO_GESTION_CREACM:
      case PARAMETROS_CALLE_RESULTADO_GESTION_MODCM: 
      {
                resultadoGestiones = findByTipos(tipoParametroCalle);
                break;
            }
        }
    }

    public Map<String, String> getTipoSolicitudesTipoSegmento() {
        cargarTipoSolicitudes(TipoParametroCalle.PARAMETRO_CALLE_SOLICITUD);
        return tipoSolicitudes;
    }

    public Map<String, String> getTipoSolicitudesTipoVenta() {
        cargarTipoSolicitudes(TipoParametroCalle.PARAMETRO_CALLE_VENTA);
        return tipoVentas;
    }

    public Map<String, String> getTipoSolicitudesTipo() {
        cargarTipoSolicitudes(TipoParametroCalle.PARAMETRO_CALLE_SEGMENTO);
        return tipoSolicitudes;
    }

    public Map<String, String> getTipoSolicitudesTipoSolicitud() {
        cargarTipoSolicitudes(TipoParametroCalle.PARAMETRO_CALLE_SOLICITUD);
        return tipoSolicitudes;
    }

    public Map<String, String> getApartamentos() {
        cargarTipoSolicitudes(TipoParametroCalle.PARAMETRO_CALLE_APARTAMENTOS);
        return apartamentos;
    }

    public Map<String, String> getTiposDeVias() {
        cargarTipoSolicitudes(TipoParametroCalle.PARAMETRO_CALLE_VIAS);
        return calles;
    }

    public void regionalCodeChanged(ValueChangeEvent e) {
        cargarCiudades(e.getNewValue().toString());
    }

    private void cargarCiudades(String codRegional) {
        try {
            BigDecimal idReg = new BigDecimal(codRegional);
            List<CmtComunidadRr> lista = this.rrCiudadesFacadeLocal.findByIdRegional(idReg);
            ciudades = JsfUtil.getCiudades(lista);
        } catch (ApplicationException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * @return the ciudades
     */
    public Map<String, BigDecimal> getCiudades() {
        return ciudades;
    }

    /**
     * @return the solicitud
     */
    public Solicitud getSolicitud() {

        Solicitud solSeleccionada = (Solicitud) session.getAttribute("solicitud");

        if (solSeleccionada != null) {
            this.solicitud = solSeleccionada;
        }

        if (this.solicitud == null) {
            this.solicitud = new Solicitud();
            cargarSolicitante();
        }
        return solicitud;
    }

    public void setSolicitud(Solicitud nueva) {
        this.solicitud = nueva;
    }

    private void prepareCreateSolicitudVtReplanteamiento() throws ApplicationException {
        if (validaCuentaMatriz()) {
            solicitud.setCambioDir("4");
            createSolicitud(TipoSolicitud.TIPO_SOL_REPLANTEAMIENTO);
            solicitud.setTorres(BigDecimal.ZERO);
        } else {
            JsfUtil.addErrorMessage("La cuenta matriz ingresada no retorna "
                    + "Valor en la base de Edificios Por favor Verifique");
        }
    }

    public String prepareSolicitud() {
        return "/view/MGL/VT/Migracion/Solicitudes/Crear.xhtml?faces-redirect=true";
    }

    private void prepareCreateSolicitudVtEdificioConjunto() throws ApplicationException {
        if (validaCuentaMatriz()) {
            solicitud.setCambioDir("7");
            createSolicitud(TipoSolicitud.TIPO_SOL_VT_EDIFICIO_CONJUNTO);
            solicitud.setTorres(BigDecimal.ZERO);
        } else {
            JsfUtil.addErrorMessage("La cuenta matriz ingresada no retorna "
                    + "Valor en la base de Edificios Por favor Verifique");
        }

    }

  public void prepareCreate(AccionesVT accionVT) throws ApplicationException{
    switch(accionVT){
      case SOLICITAR_VT_EDIFICIO_CONJUNTO:{
                prepareCreateSolicitudVtEdificioConjunto();
                break;
            }
      case SOLICITAR_VT_REPLANTEAMIENTO:{
                prepareCreateSolicitudVtReplanteamiento();
                break;
            }
      case SOLICITAR_VT_EN_CASA:{
                prepareCreateSolicitudVtCasas();
                break;
            }
      case SOLICITAR_CREACION_CUENTA_MATRIZ:{
                prepareCreateSolicitudCreacionCuentaMatriz();
                break;
            }
      case SOLICITAR_CREACION_HHPP_EN_CUENTA_MATRIZ:{
                prepareCreateSolicitudCreacionHhppCuentaMatriz();
                break;
            }
      case SOLICITAR_MODIFICAR_ELIMINACION_CUENTA_MATRIZ:
      {
                prepareCreateSolicitudModificarEliminarCuentaMatriz();
                break;
            }
      case SOLICITAR_VIABILIDAD_INTERNET:{
                prepareCreateSolicitudViabilidadInternet();
                break;
            }
      case SOLICITAR_CAMBIO_ESTRATO:{
                prepareCreateSolicitudCambioEstrato();
                break;
            }
        }
    hayMensaje=true;
    }

    private void prepareCreateSolicitudViabilidadInternet() throws ApplicationException {

        if (validaCuentaMatriz()) {
            if (this.solicitud.getTelefono() != null) {
                solicitud.setTelContacto(this.solicitud.getTelefono());
            }
            solicitud.setCambioDir("5");
            solicitud.setCuentaMatriz("0");
            solicitud.setTorres(BigDecimal.ZERO);
            createSolicitud(TipoSolicitud.TIPO_SOL_VIABILIDAD_INTERNET);
        } else {
            JsfUtil.addErrorMessage("La cuenta matriz ingresada no retorna "
                    + "Valor en la base de Edificios Por favor Verifique");
        }

    }

    private void prepareCreateSolicitudCambioEstrato() throws ApplicationException {

        if (validaCuentaMatriz()) {
            solicitud.setCambioDir("6");
            createSolicitud(TipoSolicitud.TIPO_CAMBIO_ESTRATO);
        } else {
            JsfUtil.addErrorMessage("La cuenta matriz ingresada no retorna "
                    + "Valor en la base de Edificios Por favor Verifique");
        }
    }

    private void prepareCreateSolicitudVtCasas() throws ApplicationException {
        solicitud.setCuentaMatriz("0");
        solicitud.setCambioDir("8");
        solicitud.setTorres(BigDecimal.ZERO);
        createSolicitud(TipoSolicitud.TIPO_SOL_VT_CASAS);
    }

    private void prepareCreateSolicitudCreacionCuentaMatriz() throws ApplicationException {
        solicitud.setCuentaMatriz("0");
        solicitud.setCambioDir("9");
        createSolicitud(TipoSolicitud.TIPO_SOL_CREACION_CUENTA_MATRIZ);
    }

    private void prepareCreateSolicitudCreacionHhppCuentaMatriz() throws ApplicationException {
        if (validaCuentaMatriz()) {
            solicitud.setTorres(BigDecimal.ZERO);
            solicitud.setCambioDir("10");
            createSolicitud(TipoSolicitud.TIPO_SOL_CREACION_HHPP_CUENTA_MATRIZ);
        } else {
            JsfUtil.addErrorMessage("La cuenta matriz ingresada no retorna "
                    + "Valor en la base de Edificios Por favor Verifique");
        }
    }

    private void prepareCreateSolicitudModificarEliminarCuentaMatriz() throws ApplicationException {
        if (validaCuentaMatriz()) {
            if (this.solicitud != null) {
                this.solicitud.setContacto("0");
                this.solicitud.setTelContacto("0");
            }
            solicitud.setCambioDir("11");
            createSolicitud(TipoSolicitud.TIPO_SOL_MODIFICAR_ELIMINAR_CUENTA_MATRIZ);
        } else {
            JsfUtil.addErrorMessage("La cuenta matriz ingresada no retorna "
                    + "Valor en la base de Edificios Por favor Verifique");
        }
    }

    private void preCreateSolicitud() {
        if (solicitud != null) {

            solicitud.setEstado(PENDIENTE.getValor());

            String dirComplementoA = dirComplemento == null
                    ? "" : dirComplemento + " ";

            String dirTipoCalleCompletaA = dirTipoCalleCompleta == null
                    ? "" : dirTipoCalleCompleta;
            String dirCalleCompletaA = dirCalleCompleta == null
                    ? "" : " " + dirCalleCompleta;
            String direccion;
            String numPuertaUnoA = numPuertaUno == null
                    ? "" : numPuertaUno;
            String numPuertaDosA = numPuertaDos == null
                    ? "" : " " + numPuertaDos;

            if (solicitud.getStreetName() != null && solicitud.getHouseNumber() != null) {
                direccion = dirTipoCalleCompletaA + " " + solicitud.getStreetName() + " " + "#" + " " + solicitud.getHouseNumber();
            } else {
                direccion = dirTipoCalleCompletaA + " " + dirCalleCompletaA + " " + "#" + " " + numPuertaUnoA + "-" + numPuertaDosA;
            }
            solicitud.setDireccion(direccion.toUpperCase());
            solicitud.setNumPuerta(numPuertaUnoA + numPuertaDosA);

            String dirTipoCalleAlternaA = dirTipoCalleAlterna == null
                    ? "" : dirTipoCalleAlterna + " ";
            dirCalleAlterna = dirCalleAlterna == null
                    ? "" : dirCalleAlterna + " ";
            dirPlacaAlternaUno = dirPlacaAlternaUno == null
                    ? "" : dirPlacaAlternaUno + " ";
            dirPlacaAlternaDos = dirPlacaAlternaDos == null
                    ? "" : dirPlacaAlternaDos + " ";
            dirAptoAlterna = dirAptoAlterna == null ? "" : dirAptoAlterna;
            solicitud.setDireccion1(dirTipoCalleAlternaA + dirCalleAlterna
                    + dirPlacaAlternaUno + dirPlacaAlternaDos + dirAptoAlterna);

            solicitud.setFechaIngreso(new Date());

        }
    }

    private void createSolicitud(TipoSolicitud tipo) throws ApplicationException {
        if (solicitud != null) {

            //precrea la solicitud para ajustar algunos datos.
            if (tipo.getValor().equalsIgnoreCase("CAMBIOEST") || tipo.getValor().equalsIgnoreCase("HHPPCMATRIZ")) {
                solicitud.setEstado(PENDIENTE.getValor());
                solicitud.setFechaIngreso(new Date());
                String dirComplementoA = dirComplemento == null
                        ? "" : dirComplemento + " ";
                String numPuertaUnoA = numPuertaUno == null
                        ? "" : numPuertaUno;
                solicitud.setStreetName(dirComplementoA);
                solicitud.setHouseNumber(numPuertaUnoA);
                if (cuentaMatrizMgl != null) {
                    if (cuentaMatrizMgl.getDireccionPrincipal() != null) {
                        solicitud.setDireccion(cuentaMatrizMgl.getDireccionPrincipal().
                                getDireccionObj().getDirFormatoIgac());
                }else{
                        JsfUtil.addErrorMessage("La cuenta matriz no tiene una dirección principal.");
                        return;
                    }
                } else {
                    JsfUtil.addErrorMessage("No existe la cuenta matriz en el sistema.");
                }
            } else if (tipo.getValor().equalsIgnoreCase("VTMODECM")) {
                if (cuentaMatrizMgl != null) {
                    if (cuentaMatrizMgl.getDireccionPrincipal() != null) {
                        solicitud.setDireccionAntiguaIgac(cuentaMatrizMgl.getDireccionPrincipal().
                                getDireccionObj().getDirFormatoIgac());
                        preCreateSolicitud();
                    }
                } else {
                    JsfUtil.addErrorMessage("No existe la cuenta matriz en el sistema.");
                }
            } else {
                preCreateSolicitud();
            }

            solicitud.setTipo(tipo.getValor());
            solicitud.setDisponibilidadGestion("" + 0);
            if (idRegional != null) {
                CmtRegionalRr regionalRr = rrRegionalesFacadeLocal.findRegionalById(idRegional);
                if (regionalRr.getRegionalRrId() != null) {
                    solicitud.setRegional(regionalRr.getNombreRegional());
                }
            }
            if (ciudadRr != null) {
                CmtComunidadRr comunidadRr = rrCiudadesFacadeLocal.findByIdComunidad(ciudadRr);
                if (comunidadRr.getComunidadRrId() != null) {
                    solicitud.setCiudad(comunidadRr.getNombreComunidad());
                }
            }

            try {
                solicitud.setUsuario(usuarioVT);
                solicitud = getSolicitudFacadeLocal().create(solicitud);
                JsfUtil.addSuccessMessage("Los datos han sido ingresados al sistema, para su gestión. El numero de solicitud es: "
                        + getSolicitud().getIdSolicitud());
                canCreate = false;
                // envio correo
                if (tipo.getValor().equalsIgnoreCase("CAMBIOEST")) {
                    //Guardo los documentos de cambio de estrato
                    if (lstArchivosCambiosEstrato.size() > 0) {
                        for (String archivo : lstArchivosCambiosEstrato) {
                            String rutaArchivo;
                            String[] partsUrls = archivo.split(";");
                            rutaArchivo = partsUrls[0];
                            TecArchivosCambioEstrato tecArchivosCambioEstrato = new TecArchivosCambioEstrato();
                            tecArchivosCambioEstrato.setUrlArchivoSoporte(rutaArchivo);
                            tecArchivosCambioEstrato.setSolicitudObj(solicitud);
                            int posicionPunto = partsUrls[1].indexOf('.');
                            String nombreFile = partsUrls[1].substring(0, posicionPunto);
                            String extension = FilenameUtils.getExtension(partsUrls[1]);

                            tecArchivosCambioEstrato.setNombreArchivo(nombreFile + "_" + solicitud.getIdSolicitud().toString() + "." + extension);

                            tecArchivosCambioEstrato = tecArcCamEstratoFacadeLocal.
                                    crear(tecArchivosCambioEstrato, usuarioVT, perfilVT);

                            if (tecArchivosCambioEstrato != null) {
                                LOGGER.info("Archivo guardado en el repositorio");
                            }
                        }
              creoSolicitud=true;
                    }
                }
               
                if (solicitud.getIdSolicitud() != null || creoSolicitud) {

                    NodoMgl nodoSolicitud = null;
                    CmtBasicaMgl tecnologiaSolicitud = null;
                    GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                    GeograficoPoliticoMgl centroPoblado = null;
                    GeograficoPoliticoMgl ciudad = null;
                    GeograficoPoliticoMgl departamento = null;
                    String departSol = "";
                    String ciudadSol = "";
                    String centroPobladoSol = "";
                   
                    
                    usuarioSolicitudCm = usuarioServicesFacade.consultaInfoUserPorUsuario(usuarioVT);
                   
                    if (tipo.getValor().equalsIgnoreCase("CAMBIOEST")) {
                        ciudadSol = cuentaMatrizMgl.getMunicipio().getGpoNombre();
                        departSol = cuentaMatrizMgl.getDepartamento().getGpoNombre();
                        centroPobladoSol = cuentaMatrizMgl.getCentroPoblado().getGpoNombre();
                        solicitud.setCuentaMatriz(cuentaMatrizMgl.getNumeroCuenta().toString());
                        if (cuentaMatrizMgl.getCuentaId() != null) {
                            solicitud.setCuenta(cuentaMatrizMgl.getCuentaId().substring(cuentaMatrizMgl.getCuentaId().length() - 7));
                        }
                       
                    } else {
                        if (solicitud.getNodo() != null && !solicitud.getNodo().isEmpty()) {
                            nodoSolicitud = nodoMglFacadeLocal.findByCodigo(solicitud.getNodo().toUpperCase());
                        }
                        if (nodoSolicitud != null) {
                            tecnologiaSolicitud = basicasMglFacadeLocal.findById(nodoSolicitud.getNodTipo().getBasicaId());
                        }
                        if (solicitud.getCiudad() != null && nodoSolicitud != null) {

                            centroPoblado = geograficoPoliticoManager.findById(nodoSolicitud.getGpoId());
                            ciudad = geograficoPoliticoManager.findById(centroPoblado.getGeoGpoId());
                            departamento = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());
                        }

                        solicitud.setTecnologiaId(tecnologiaSolicitud);
                        ciudadSol = ciudad.getGpoNombre();
                        departSol = departamento.getGpoNombre();
                        centroPobladoSol = centroPoblado.getGpoNombre();
                    }
                
                    // enviar correo
                    try {
                        MailSender.send(parametrosMglFacadeLocal, 
                                usuarioSolicitudCm.getEmail(), // MAIL TO
                                "Su número de Solicitud es: "
                                + solicitud.getIdSolicitud().toString(), // ASUNTO
                                mensajeCorreoSolicitud(solicitud, departSol, ciudadSol, centroPobladoSol) // CUERPO
                        );
                        
                    } catch (ApplicationException e) {
                        throw new ApplicationException("Se produjo un error al momento de enviar el correo: " + e.getMessage(), e);
                    } catch (Exception e) {
                        throw new ApplicationException("Se produjo un error al momento de enviar el correo: " + e.getMessage(), e);
                    }

                }
               
            } catch (ApplicationException ex) {
                LOGGER.error(ex.getMessage(), ex);
                JsfUtil.addErrorMessage("Error al momento de guardar la "
                        + "solicitud: " + ex.getMessage());
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
                JsfUtil.addErrorMessage("Error al momento de guardar la "
                        + "solicitud: " + ex.getMessage());
            }
        
        } else {
            JsfUtil.addErrorMessage("No se puede procesar la solicitud.");
        }
    }

    /**
     * Finalizar solicitud
     * <p>
     * Finaliza la solicitud, cambiando el estado a FINALIZADO y actualizando
     * otros datos
     *
     * @author becerraarmr
     *
     * @return un String con el valor a donde va a retornar al dar clic en
     * finalizar Solicitud
     */
    public String finalizarSolicitud() {
        if (solicitud != null) {
            try {
                solicitud.setEstado(FINALIZADO.getValor());
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                solicitud.setRespuesta("|" + format.format(new Date()) + "|"
                        + solicitud.getRespuesta());
                solicitud.setFechaCancelacion(new Date());
                solicitud = getSolicitudFacadeLocal().update(solicitud);
                
                //enviar correo
                NodoMgl nodoSolicitud = null;
                CmtBasicaMgl tecnologiaSolicitud = null;
                GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                GeograficoPoliticoMgl centroPoblado = null;
                GeograficoPoliticoMgl ciudad = null;
                GeograficoPoliticoMgl departamento = null;
                usuarioSolicitudCm = usuarioServicesFacade.consultaInfoUserPorUsuario(usuarioVT);
                if (solicitud.getNodo() != null && !solicitud.getNodo().isEmpty()) {
                    nodoSolicitud = nodoMglFacadeLocal.findByCodigo(solicitud.getNodo());
                }
                if (nodoSolicitud != null) {
                    tecnologiaSolicitud = basicasMglFacadeLocal.findById(nodoSolicitud.getNodTipo().getBasicaId());
                }
                if (solicitud.getCiudad() != null) {
                     centroPoblado = geograficoPoliticoManager.findById(nodoSolicitud.getGpoId());
                        ciudad = geograficoPoliticoManager.findById(centroPoblado.getGeoGpoId());
                        departamento = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());
                }

                solicitud.setTecnologiaId(tecnologiaSolicitud);
                
                try {
                    MailSender.send(parametrosMglFacadeLocal, 
                            usuarioSolicitudCm.getEmail(), // MAIL TO
                            "Su número de Solicitud es :"
                            + solicitud.getIdSolicitud().toString(), // ASUNTO
                            mensajeCorreoSolicitud(solicitud, 
                                    departamento != null ? departamento.getGpoNombre() : "", 
                                    ciudad != null ? ciudad.getGpoNombre() : "", 
                                    centroPoblado != null ? centroPoblado.getGpoNombre() : "") // CUERPO
                    );

                } catch (ApplicationException e) {
                    throw new ApplicationException("Se produjo un error al momento de enviar el correo: " + e.getMessage(), e);
                } catch (Exception e) {
                    throw new ApplicationException("Se produjo un error al momento de enviar el correo: " + e.getMessage(), e);
                }
                
                
                JsfUtil.addSuccessMessage("Solicitud Finalizada: "
                        + getSolicitud().getIdSolicitud());
            } catch (ApplicationException ex) {
                LOGGER.error(ex.getMessage(), ex);
                JsfUtil.addErrorMessage("Error al momento de finalizar la "
                        + "solicitud: " + ex.getMessage());
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
                JsfUtil.addErrorMessage("Error al momento de finalizar la "
                        + "solicitud: " + ex.getMessage());
            }
        } else {
            JsfUtil.addErrorMessage("No se puede procesar la solicitud");
        }
        return "";
    }

    /**
     * Cargar datos del solictante.
     */
    private void cargarSolicitante() {

        cargarUsuario(usuarioVT);
        if (getUsuario() != null && this.solicitud != null) {
            if (getUsuario().getCedula() != null) {
                this.solicitud.setIdSolicitante(new BigDecimal(getUsuario().getCedula()));
            }
            this.solicitud.setSolicitante(getUsuario().getNombre());
            this.solicitud.setCorreo(getUsuario().getEmail());
        }
    }

    private boolean validaCuentaMatriz() {
        boolean sw = false;//Validación inicia no valida
        if (this.solicitud != null) {
            if (this.solicitud.getCuentaMatriz() != null
                    && !this.solicitud.getCuentaMatriz().equalsIgnoreCase("0")) {
                try {
                    List<CmtCuentaMatrizMgl> cuenta = cuentaMatrizMglFacadeLocal.findByNumeroCM(new BigDecimal(this.solicitud.getCuentaMatriz()));
                    if (cuenta.size() > 0) {
                        cuentaMatrizMgl = cuenta.get(0);
                        sw = true;
                        solicitud.setCmtCuentaMatrizMglSol(cuentaMatrizMgl);
                    } else {
                        sw = false;
                    }
                } catch (ApplicationException ex) {
                    LOGGER.error(ex.getMessage(), ex);
                    JsfUtil.addErrorMessage("La cuenta matriz ingresada no se pudo "
                            + "verificar en la base de datos");
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage());
                    JsfUtil.addErrorMessage("La cuenta matriz ingresada no se pudo "
                            + "verificar en la base de datos");
                }
            }
        }
        return sw;
    }

    /**
     * @return the canCreate
     */
    public boolean isCanCreate() {
        return canCreate;
    }

    /**
     * @return the dirCalleAlterna
     */
    public String getDirCalleAlterna() {
        return dirCalleAlterna;
    }

    /**
     * @param dirCalleAlterna the dirCalleAlterna to set
     */
    public void setDirCalleAlterna(String dirCalleAlterna) {
        this.dirCalleAlterna = dirCalleAlterna;
    }

    /**
     * @return the dirPlacaAlternaUno
     */
    public String getDirPlacaAlternaUno() {
        return dirPlacaAlternaUno;
    }

    /**
     * @param dirPlacaAlternaUno the dirPlacaAlternaUno to set
     */
    public void setDirPlacaAlternaUno(String dirPlacaAlternaUno) {
        this.dirPlacaAlternaUno = dirPlacaAlternaUno;
    }

    /**
     * @return the dirPlacaAlternaUno
     */
    public String getDirPlacaAlternaDos() {
        return dirPlacaAlternaDos;
    }

    /**
     * @param dirPlacaAlternaDos the dirPlacaAlternaDos to set
     */
    public void setDirPlacaAlternaDos(String dirPlacaAlternaDos) {
        this.dirPlacaAlternaDos = dirPlacaAlternaDos;
    }

    /**
     * @return the dirAptoAlterna
     */
    public String getDirAptoAlterna() {
        return dirAptoAlterna;
    }

    /**
     * @param dirAptoAlterna the dirAptoAlterna to set
     */
    public void setDirAptoAlterna(String dirAptoAlterna) {
        this.dirAptoAlterna = dirAptoAlterna;
    }

    /**
     * @return the dirCalleCompleta
     */
    public String getDirCalleCompleta() {
        return dirCalleCompleta;
    }

    /**
     * @param dirCalleCompleta the dirCalleCompleta to set
     */
    public void setDirCalleCompleta(String dirCalleCompleta) {
        this.dirCalleCompleta = dirCalleCompleta;
    }

    /**
     * @return the productosNuevos
     */
    public Map<String, String> getProductosNuevos() {
        cargarTipoSolicitudes(TipoParametroCalle.PARAMETRO_CALLE_PRODUCTO);
        return productosNuevos;
    }

    /**
     * @return the dirTipoCalleCompleta
     */
    public String getDirTipoCalleCompleta() {
        return dirTipoCalleCompleta;
    }

    /**
     * @param dirTipoCalleCompleta the dirTipoCalleCompleta to set
     */
    public void setDirTipoCalleCompleta(String dirTipoCalleCompleta) {
        this.dirTipoCalleCompleta = dirTipoCalleCompleta;
    }

    /**
     * @return the dirTipoCalleAlterna
     */
    public String getDirTipoCalleAlterna() {
        return dirTipoCalleAlterna;
    }

    /**
     * @param dirTipoCalleAlterna the dirTipoCalleAlterna to set
     */
    public void setDirTipoCalleAlterna(String dirTipoCalleAlterna) {
        this.dirTipoCalleAlterna = dirTipoCalleAlterna;
    }

    /**
     * @return the numPuertaUno
     */
    public String getNumPuertaUno() {
        return numPuertaUno;
    }

    /**
     * @param numPuertaUno the numPuertaUno to set
     */
    public void setNumPuertaUno(String numPuertaUno) {
        this.numPuertaUno = numPuertaUno;
    }

    /**
     * @return the numPuertaDos
     */
    public String getNumPuertaDos() {
        return numPuertaDos;
    }

    /**
     * @param numPuertaDos the numPuertaDos to set
     */
    public void setNumPuertaDos(String numPuertaDos) {
        this.numPuertaDos = numPuertaDos;
    }

    public String getDirComplemento() {
        return dirComplemento;
    }

    public void setDirComplemento(String dirComplemento) {
        this.dirComplemento = dirComplemento;
    }

    /**
     * @return the hayMensaje
     */
    public boolean isHayMensaje() {
        return hayMensaje;
    }

    public BigDecimal getIdRegional() {
        return idRegional;
    }

    public void setIdRegional(BigDecimal idRegional) {
        this.idRegional = idRegional;
    }

    public void cambiarTipoCuenta() {
        if (tipoCuenta.equalsIgnoreCase("1")) {
            cambiaEstratoCM = true;
            cambiaEstratoHHPP = false;
        } else if (tipoCuenta.equalsIgnoreCase("2")) {
            cambiaEstratoHHPP = true;
            cambiaEstratoCM = false;
        } else {
            cambiaEstratoCM = false;
            cambiaEstratoHHPP = false;
        }
    }

    public String guardarArchivo() throws IOException {

        Date fecha = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String fechaN = format.format(fecha);

        if (lstArchivosCambiosEstrato.size() > 3) {
            String msg = "Solo se pueden subir un maximo de 4 archivos";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
        } else {
            if (fileCambioEstrato != null && fileCambioEstrato.getFileName() != null) {

                String fileName = fechaN;
                fileName += FilenameUtils.getName(fileCambioEstrato.getFileName());
                File file = new File(System.getProperty("user.dir"));
                String extension = FilenameUtils.getExtension(fileCambioEstrato.getFileName());

                File archive = File.createTempFile(fileName + "-", "." + extension, file);
                FileOutputStream output = new FileOutputStream(archive);
                output.write(fileCambioEstrato.getContent());
                output.close();

                try {

                    String responseArc = getSolicitudFacadeLocal().
                            uploadArchivoCambioEstrato(archive,
                                    fileName);

                    if (responseArc == null
                            || responseArc.isEmpty()) {

                        String msg = "Error al subir el archivo";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        msg, ""));

                    } else {
                        String msg = "Archivo subido satisfactoriamente";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        msg, ""));
                        lstArchivosCambiosEstrato.add(responseArc + ";" + fileCambioEstrato.getFileName());

                    }
                    archive.delete();

                } catch (ApplicationException | IOException e) {
                    String msg = "Error al guardar el archivo" + e.getMessage();
                    if (archive != null) {
                        try {
                            Files.deleteIfExists(archive.toPath());
                        } catch (IOException ex) {
                             LOGGER.error(ex.getMessage());
                        }
                    }
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                    LOGGER.error(msg, e);
                } catch (Exception e) {
                    String msg = "Error al guardar el archivo" + e.getMessage();
                    if (archive != null) {
                        try {
                            Files.deleteIfExists(archive.toPath());
                        } catch (IOException ex) {
                            LOGGER.error(ex.getMessage());
                        }
                    }
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));
                    LOGGER.error(msg, e);
                }
            } else {
                String msg = "Debe seleccionar un archivo para guardar";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msg, ""));
            }
        }
        return "";
    }

    public String armarUrl(String nombre) {

        String urlDoc;
        String[] partsUrls = nombre.split(";");
        urlDoc = partsUrls[0];

        return urlDoc;
    }

    public String armarNombre(String nombre) {

        String nombreArchivo;
        String[] partsUrls = nombre.split(";");
        nombreArchivo = partsUrls[1];
        return nombreArchivo;
    }

    public String eliminarArchivo(String nombre) {

        if (lstArchivosCambiosEstrato.remove(nombre)) {
            String msg = "Archivo borrado satisfatoriamente";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            msg, ""));

        }

        return "";
    }

    public StringBuffer mensajeCorreoSolicitud(Solicitud soliciti, String departamento, String ciudad, String centroPoblado) {
        StringBuffer mensaje = new StringBuffer();
        if (soliciti != null) {

            if (soliciti.getIdSolicitud() != null) {
                mensaje.append("<b>N&uacute;mero Solicitud\t:</b>" + soliciti.getIdSolicitud() + "<br/>");
            }
            if (soliciti != null) {
                mensaje.append("<b>Tipo Solicitud:</b>" + soliciti.getTipoSol() + "<br/>");
            }
            if (soliciti.getTipo().equalsIgnoreCase("CAMBIOEST")) {
                mensaje.append("<b>N° CM. RR:</b>" + soliciti.getCuentaMatriz() + "<br/>");
            }
            if (soliciti.getTipo().equalsIgnoreCase("CAMBIOEST")) {
                mensaje.append("<b> N° CM. MGL:</b>" + soliciti.getCuenta() + "<br/>");
            }
            if (soliciti != null && soliciti.getTecnologiaId() != null) {
                mensaje.append("<b>Tipo de Tecnologia es:</b>" + soliciti.getTecnologiaId().getNombreBasica() + "<br/>");
            }
            if (soliciti != null && soliciti.getTecnologiaId() != null) {
                mensaje.append("<b>Nombre de Cuenta Matriz:</b>" + soliciti.getCuentaMatriz() + "<br/>");
            }
            if (soliciti != null && soliciti.getTecnologiaId() != null) {
                mensaje.append("<b>Dirección Principal:</b>" + soliciti.getDireccion() + "<br/>");
            }
            if (soliciti.getEstratoAntiguo() != null && soliciti.getEstratoAntiguo() != null) {
                mensaje.append("<b>Estrato Actual:</b>" + soliciti.getEstratoNuevo()+ "<br/>");
            }
            if (soliciti.getEstratoAntiguo() != null && soliciti.getEstratoAntiguo() != null) {
                mensaje.append("<b>Estrato Antiguo:</b>" + soliciti.getEstratoAntiguo()+ "<br/>");
            }
            if (soliciti != null) {
                mensaje.append("<b>Departamento:</b>" + departamento + "<br/>");
            }

            if (soliciti != null) {
                mensaje.append("<b>Ciudad:</b>" + ciudad + "<br/>");
            }
            if (soliciti != null) {
                mensaje.append("<b>Centro Poblado:</b>" + centroPoblado + "<br/>");
            }
            if (soliciti != null) {
                mensaje.append("<b>Unidades Planeadas:</b>" + soliciti.getCantidadHHPP() + "<br/>");
            }

            if (soliciti.getEstado() != null && !soliciti.getEstado().isEmpty()) {
                mensaje.append("<b>Estado Solicitud:</b>" + soliciti.getEstado() + "<br/>");
            }

            if (soliciti.getUsuario() != null && !soliciti.getUsuario().isEmpty()) {
                mensaje.append("<b>Usuario:</b>" + soliciti.getUsuario() + "<br/>");
            }
        }

        return mensaje;
    }
    
     public StringBuffer mensajeCorreoGestion(Solicitud soliciti, String departamento, String ciudad, String centroPoblado) {
        StringBuffer mensaje = new StringBuffer();
        if (soliciti != null) {

            if (soliciti.getIdSolicitud() != null) {
                mensaje.append("<b>N&uacute;mero Solicitud\t:</b>" + soliciti.getIdSolicitud() + "<br/>");
            }
             if (soliciti != null) {
                mensaje.append("<b>Estado de la Solicitud:</b>" + soliciti.getEstado()+ "<br/>");
            }
            if (soliciti != null) {
                mensaje.append("<b>Tipo Solicitud:</b>" + soliciti.getTipoSol() + "<br/>");
            }
            if (soliciti != null && soliciti.getCuentaMatriz() != null) {
                mensaje.append("<b>Número de Cuenta Matriz:</b>" + soliciti.getCuentaMatriz() + "<br/>");
            }
            if (soliciti != null && soliciti.getCuentaMatriz() != null) {
                mensaje.append("<b>Nombre de Cuenta Matriz:</b>" + soliciti.getCuentaMatriz() + "<br/>");
            }
            if (soliciti != null && soliciti.getDireccion() != null) {
                mensaje.append("<b>Dirección Principal:</b>" + soliciti.getDireccion() + "<br/>");
            }
            if (soliciti != null && soliciti.getEstratoAntiguo() != null) {
                mensaje.append("<b>Estrato:</b>" + soliciti.getEstratoAntiguo()+ "<br/>");
            }
            if (soliciti != null && soliciti.getEstratoAntiguo() != null) {
                mensaje.append("<b>Estrato:</b>" + soliciti.getEstratoAntiguo()+ "<br/>");
            }
            if (soliciti != null && soliciti.getRespuesta()!= null) {
                mensaje.append("<b>Respuesta Actual:</b>" + soliciti.getRespuesta()+ "<br/>");
            }
             if (soliciti != null && soliciti.getRptGestion()!= null) {
                mensaje.append("<b>Respuesta Actual:</b>" + soliciti.getRptGestion()+ "<br/>");
            }
            if (soliciti != null) {
                mensaje.append("<b>Departamento:</b>" + departamento + "<br/>");
            }

            if (soliciti != null) {
                mensaje.append("<b>Ciudad:</b>" + ciudad + "<br/>");
            }
            if (soliciti != null) {
                mensaje.append("<b>Centro Poblado:</b>" + centroPoblado + "<br/>");
            }
            if (soliciti.getUsuario() != null && !soliciti.getUsuario().isEmpty()) {
                mensaje.append("<b>Usuario:</b>" + soliciti.getUsuario() + "<br/>");
            }
        }

        return mensaje;
    }
    

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public boolean isCambiaEstratoCM() {
        return cambiaEstratoCM;
    }

    public void setCambiaEstratoCM(boolean cambiaEstratoCM) {
        this.cambiaEstratoCM = cambiaEstratoCM;
    }

    public boolean isCambiaEstratoHHPP() {
        return cambiaEstratoHHPP;
    }

    public void setCambiaEstratoHHPP(boolean cambiaEstratoHHPP) {
        this.cambiaEstratoHHPP = cambiaEstratoHHPP;
    }

    public BigDecimal getCiudadRr() {
        return ciudadRr;
    }

    public void setCiudadRr(BigDecimal ciudadRr) {
        this.ciudadRr = ciudadRr;
    }

    public String getEstAntHhpp() {
        return estAntHhpp;
    }

    public void setEstAntHhpp(String estAntHhpp) {
        this.estAntHhpp = estAntHhpp;
    }

    public String getEstNueHhpp() {
        return estNueHhpp;
    }

    public void setEstNueHhpp(String estNueHhpp) {
        this.estNueHhpp = estNueHhpp;
    }

    public List<ParametrosCalles> getResultGestionCambioEs() {
        return resultGestionCambioEs;
    }

    public void setResultGestionCambioEs(List<ParametrosCalles> resultGestionCambioEs) {
        this.resultGestionCambioEs = resultGestionCambioEs;
    }

    public UploadedFile getFileCambioEstrato() {
        return fileCambioEstrato;
    }

    public void setFileCambioEstrato(UploadedFile fileCambioEstrato) {
        this.fileCambioEstrato = fileCambioEstrato;
    }

    public List<String> getLstArchivosCambiosEstrato() {
        return lstArchivosCambiosEstrato;
    }

    public void setLstArchivosCambiosEstrato(List<String> lstArchivosCambiosEstrato) {
        this.lstArchivosCambiosEstrato = lstArchivosCambiosEstrato;
    }

    public boolean isCreoSolicitud() {
        return creoSolicitud;
    }

    public void setCreoSolicitud(boolean creoSolicitud) {
        this.creoSolicitud = creoSolicitud;
    }

    public CmtCuentaMatrizMgl getCuentaMatrizMgl() {
        return cuentaMatrizMgl;
    }

    public void setCuentaMatrizMgl(CmtCuentaMatrizMgl cuentaMatrizMgl) {
        this.cuentaMatrizMgl = cuentaMatrizMgl;
    }
    
    
    // Validar Opciones por Rol
    public boolean validarOpcionIngresarDatos() {
        return validarEdicionRol(IGBTNCRSL);
    }
    
    public boolean validarOpcionIngresarViabilidadInternet() {
        return validarEdicionRol(IGBTNVBIT);
    }
    
    public boolean validarOpcionIngresarTecEdifConjunto() {
        return validarEdicionRol(IGBTNVTEC);
    }
    
    public boolean validarOpcionIngresarEnCasa() {
        return validarEdicionRol(IGBTVTCAS);
    }
    
    public boolean validarOpcionIngresarCuentaMatriz() {
        return validarEdicionRol(IGBTCTAMZ);
    }
    
    public boolean validarOpcionIngresarHHPPCuentaMatriz() {
        return validarEdicionRol(IGBTHHPMZ);
    }
    
    public boolean validarOpcionIngresarDatosEliminarCuentaMatriz() {
        return validarEdicionRol(IGBTDELCU);
    }
    
     public boolean validarOpcionIngresarDatosCambioEstrato() {
        return validarEdicionRol(IGBTINGDTOS);
    }
    //funcion General
      private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
            return tieneRolPermitido;
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
}