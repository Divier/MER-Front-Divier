/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.mbeans.direcciones;

import co.com.claro.mgl.businessmanager.address.ComponenteDireccionesManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DireccionMglFacadeLocal;
import co.com.claro.mgl.facade.DrDireccionFacadeLocal;
import co.com.claro.mgl.facade.EstadoHhppFacadeLocal;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.PcmlFacadeLocal;
import co.com.claro.mgl.facade.RrCiudadesFacadeLocal;
import co.com.claro.mgl.facade.RrRegionalesFacadeLocal;
import co.com.claro.mgl.facade.SolicitudFacadeLocal;
import co.com.claro.mgl.facade.SubDireccionMglFacadeLocal;
import co.com.claro.mgl.facade.rr.NodoRRFacadeLocal;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.EstadoHhpp;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import co.com.claro.mgl.jpa.entities.RrRegionales;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.business.NodoRR;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.claro.visitasTecnicas.facade.HhpprrEJBRemote;
import co.com.claro.visitasTecnicas.facade.ParametrosMultivalorEJB;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.mbeans.direcciones.common.Constantes;
import co.com.telmex.catastro.services.direcciones.ConsultaEspecificaEJBRemote;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.util.MailSender;
import co.com.telmex.catastro.utilws.DataSourceFactory;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Parzifal de León
 */
@ViewScoped
@ManagedBean(name = "modificacionHhppGestionBean")
public final class ModificacionHHPPGestionBean {

    private Solicitud solicitud = new Solicitud();
    private String tipoSolicitud;
    private RrRegionales regional;
    private RrCiudades ciudad;
    private String rrRegional;
    private String rrCiudad;
    private Integer telContacto;
    private Integer telSolicitante;
    private String idSolicitante;
    private String cambioDir;
    private String estratoDir;
    private List<EstadoHhpp> estadoHhppList;
    private List<CmtRegionalRr> regionalList;
    private List<RrCiudades> ciudadesList;
    private CityEntity cityEntityPrincipal = new CityEntity();
    private static final Logger LOGGER = LogManager.getLogger(ModificacionHHPPGestionBean.class);
    private String idDirCatastroPrincipal;
    private String resultado;
    private String respuesta;
    private String respuestaTemp;
    private String barrioSugerido;
    private String barrio;
    @EJB
    private RrRegionalesFacadeLocal rrRegionalesFacade;
    @EJB
    private RrCiudadesFacadeLocal rrCiudadesFacade;
    @EJB
    private PcmlFacadeLocal pcmlFacadeLocal;
    @EJB
    ParametrosCallesFacadeLocal parametrosCallesFacade;
    @EJB
    private EstadoHhppFacadeLocal estadoHhppFacade;
    @EJB
    private SolicitudFacadeLocal solicitudFacade;
    @EJB
    DrDireccionFacadeLocal drDireccionFacade;
    @EJB
    private HhpprrEJBRemote hhpprrEJB;
    @EJB
    private NodoRRFacadeLocal nodoRRFacadeLocal;
    @EJB
    private DireccionMglFacadeLocal direccionFacade;
    @EJB
    private SubDireccionMglFacadeLocal subDireccionFacade;
    @EJB
    private ConsultaEspecificaEJBRemote consultaEstepecificaEJB;
    private ComponenteDireccionMBean componenteDireccionBean;
    private UnidadStructPcml unidad;
    private ComponenteDireccionesManager componenteDireccionesManager;
    private SecurityLogin securityLogin;
    private String msnCertificado = "";
    private boolean botonCrear;
    private boolean botonValidar = true;
    private boolean botonRegresar;
    private boolean panelSelectTipoMod = true;
    private boolean isNodoValidado = false;
    private boolean renderMulti = false;
    private boolean nuevoBarrioField = false;
    private boolean barrioField = false;
    private boolean botonNuevaDir = false;
    private boolean panelRegion = false;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private ParametrosMultivalorEJB parametrosMultivalorEJBRemote = new ParametrosMultivalorEJB();
    private String[] tipoModificacionList;
    private String nodoRender = String.valueOf(false);
    private String estadoRender = String.valueOf(false);
    private String cambioDirRender = String.valueOf(false);
    private String streetNameStr;
    private String houseNumberStr;
    private String aptoStr;
    private String antStreetNameStr;
    private String antHouseNumberStr;
    private String antAptoStr;
    private String direccionLabel = "Direccion Antigua";
    public List<AddressSuggested> barriosList = new ArrayList<AddressSuggested>();
    private List<String> nodoGeoList;
    private String nodoSolicitud;
    private ArrayList<NodoRR> listNodeNFI;

    public ModificacionHHPPGestionBean() throws IOException, SQLException, NamingException {
        FacesContext context = FacesContext.getCurrentInstance();
        componenteDireccionBean = (ComponenteDireccionMBean) context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(), "#{componenteDireccionMBean}", ComponenteDireccionMBean.class)
                .getValue(context.getELContext());

        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            idSolicitante = securityLogin.getIdUser();
            if (idSolicitante == null) {
                idSolicitante = (String) session.getAttribute("idUser");
            } else {
                session = (HttpSession) facesContext.getExternalContext().getSession(false);
                session.setAttribute("idUser", idSolicitante);
            }
            Connection connectionOracl;
            Statement statem;
            ResultSet resultSet;
            connectionOracl = DataSourceFactory.getOracleConnection();
            try {
                statem = connectionOracl.createStatement();

                String queryUser = "SELECT NOMBRE,EMAIL from USUARIOS WHERE ID_USUARIO = ?";
                PreparedStatement prepStmt = connectionOracl.prepareStatement(queryUser);
                prepStmt.setString(1, idSolicitante);
                resultSet = prepStmt.executeQuery();
                resultSet.next();
                solicitud.setSolicitante("" + resultSet.getString("NOMBRE") + "");
                solicitud.setCorreo("" + resultSet.getString("EMAIL") + "");

            } catch (SQLException e) {
                FacesUtil.mostrarMensajeError("Error en la consulta,campo solicitante nulo." + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error en la consulta,campo solicitante nulo." + e.getMessage(), e, LOGGER);
            }

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en ModificacionHHPPGestionBean." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ModificacionHHPPGestionBean." + e.getMessage(), e, LOGGER);
        }

        regionalList = new ArrayList<CmtRegionalRr>();

    }

    public void tipoModificacionChage() {
        direccionLabel = "Direccion Antigua";

        for (String s : tipoModificacionList) {
            LOGGER.error("tipo Modificacion " + s);

        }
        if (tipoModificacionList[0].equals("1")) {
            direccionLabel = "Direccion Nueva";
        }

    }

    public void obtenerListaRegionales() throws ApplicationException {
        regionalList = rrRegionalesFacade.findByUnibi(tipoSolicitud);
        ciudadesList = null;
        renderMulti = false;
        botonValidar = true;
        disbledForm(false);
        componenteDireccionBean.setVerificacionCasa(false);
        if (tipoSolicitud.equals("B")) {
            componenteDireccionBean.setVerificacionCasa(true);
        }
    }

    public void obtenerListaCiudades() throws ApplicationException {
        ciudadesList = rrCiudadesFacade.findByCodregional(rrRegional);
        renderMulti = false;
    }

    public void obtenerListaEstadoHhpp() {
        estadoHhppList = estadoHhppFacade.findAll();
    }

    public List<ParametrosCalles> obtenerDireccionPorTipo(String tipo) throws ApplicationException {
        return parametrosCallesFacade.findByTipo(tipo);
    }

    public void limpiarNodoEstado() {
        solicitud.setCambioNodo(null);
        solicitud.setEstadoHhppNuevo(null);
    }

    public void esMultiorigen() {
        disbledForm(false);
        botonValidar = true;
        componenteDireccionBean.setDisableViaGen(true);
        if (rrCiudad.equals("UCA") || rrCiudad.equals("CAL") || rrCiudad.equals("XCA")) {
            componenteDireccionBean.setDisableViaGen(false);
        }
        try {

            boolean isMultiorigen = componenteDireccionBean.validarMultiorigen(rrCiudad);
            LOGGER.error("esMultiorigen() " + rrCiudad);
            if (isMultiorigen) {
                LOGGER.error("multiorigen: true");
                String msn = "Esta ciudad es Multiorigen Digite la dirección y los campos solicitados, de click sobre 'validar barrio' para continuar con la gestión ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                botonValidar = false;
                renderMulti = true;
            } else {
                renderMulti = false;
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al Crear la Pagina." + e.getMessage(), e, LOGGER);
        }
    }

    public void disbledForm(boolean disable) {
        componenteDireccionBean.setReadOnly(disable);
        botonNuevaDir = disable;
        barrioField = disable;
        nuevoBarrioField = disable;
        botonValidar = disable;
        panelRegion = disable;
    }

    public void esOtroBarrio() {
        nuevoBarrioField = false;
        barrioSugerido = barrio;
        if (barrio.equals("otro")) {
            barrioSugerido = "";
            nuevoBarrioField = true;
        }
    }

    public void cargarBarrioSugerido(List<AddressSuggested> barrioSugerido, String direccion) {

        try {

            List<AddressSuggested> listaBarrioSugerido = barrioSugerido;
            barriosList = new ArrayList<AddressSuggested>();
            AddressSuggested vacio = new AddressSuggested();
            vacio.setNeighborhood("otro");
            vacio.setAddress("");
            if (listaBarrioSugerido.size() <= 0) {
                barriosList.add(vacio);
            } else {
                for (int i = 0; i < listaBarrioSugerido.size(); i++) {
                    boolean deletePos = false;
                    for (int j = 0; j < listaBarrioSugerido.size(); j++) {

                        if (listaBarrioSugerido.get(i).getNeighborhood().equalsIgnoreCase(listaBarrioSugerido.get(j).getNeighborhood())) {
                            if (direccion.equalsIgnoreCase(listaBarrioSugerido.get(j).getAddress())) {
                                if (deletePos) {
                                    listaBarrioSugerido.remove(j);
                                }
                                deletePos = true;
                            } else {
                                listaBarrioSugerido.remove(j);
                            }
                        }
                    }
                }
                barriosList = listaBarrioSugerido;
                barriosList.add(vacio);

            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error Cargando lista de Barrio sugerido." + e.getMessage(), e, LOGGER);
        }

    }

    public void limpiarCamposMultiorigen() {
        limpiarCampos();
        botonValidar = false;
    }

    public void limpiarCampos() {

        disbledForm(false);
        showButtons(false);
        unidad = null;
        isNodoValidado = false;
        panelSelectTipoMod = true;
        cambioDir = null;
        cambioDirRender = "false";
        nodoRender = "false";
        estadoRender = "false";
        panelRegion = false;
    }

    public void showButtons(boolean estado) {
        botonCrear = estado;
        botonRegresar = estado;
        botonValidar = !estado;
    }

    public void optionButtons() {

        if (solicitud.getCambioDir().equalsIgnoreCase("4")) {
            if (resultado.equalsIgnoreCase("CAMBIO DE NODO NO REALIZADO")) {
                botonCrear = true;
                botonValidar = false;
                botonRegresar = false;
            } else {
                botonCrear = false;
                botonValidar = true;
            }

        }
    }

    private String validaNodo(CityEntity cityEntityPrincipal) throws ApplicationException {
        boolean isCertificado = false;
        String nodo = "";

        String ndStr2 = cityEntityPrincipal.getNodo2() == null ? "" : cityEntityPrincipal.getNodo2().trim();
        if (!ndStr2.isEmpty()) {
            isCertificado = nodoRRFacadeLocal.isNodoRRCertificado(ndStr2, securityLogin.getLoginUser());
            if (!isCertificado) {
                nodo = ndStr2;
            }
        }

        return nodo;
    }

    @PostConstruct
    public void load() {
        String idSolSesion = (String) session.getAttribute("idSolSelected");
        if (idSolSesion != null && !idSolSesion.trim().isEmpty()) {
            session.setAttribute("idSolSelected", " ");
            BigDecimal id = new BigDecimal(idSolSesion);
            solicitud = solicitudFacade.findById(id);
            if (solicitud != null && !solicitud.toString().isEmpty()) {
                nodoSolicitud = solicitud.getCambioNodo();
            }
            try {
                listNodeNFI = consultaEstepecificaEJB.queryGetNodoRRNfiByCodCiudad(solicitud.getCiudad());
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("Error en load." + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error en load." + e.getMessage(), e, LOGGER);
            }
        }

    }

    public void consultarHHPP() throws ApplicationException {

        botonValidar = false;
        cambioDirRender = "false";
        nodoRender = "false";
        estadoRender = "false";
        if (solicitud.getCambioDir() != null
                && !solicitud.getCambioDir().trim().isEmpty()
                && solicitud.getCambioDir().trim().equalsIgnoreCase("1")) {
            cambioDirRender = "true";
            antStreetNameStr = solicitud.getStreetName();
            LOGGER.error("antStreetNameStr--> " + antStreetNameStr);
            antHouseNumberStr = solicitud.getHouseNumber();
            antAptoStr = solicitud.getAparmentNumber();
            streetNameStr = "";
            houseNumberStr = "";
            aptoStr = "";
            if (antStreetNameStr != null && !antStreetNameStr.trim().isEmpty() && antStreetNameStr.contains(",")) {
                streetNameStr = antStreetNameStr.substring(antStreetNameStr.indexOf(",") + 1);
                LOGGER.error("antStreetNameStr--> " + antStreetNameStr);
                LOGGER.error("StreetNameStr--> " + streetNameStr);
                antStreetNameStr = antStreetNameStr.substring(0, antStreetNameStr.indexOf(","));
            }
            if (antHouseNumberStr != null && !antHouseNumberStr.trim().isEmpty() && antHouseNumberStr.contains(",")) {
                houseNumberStr = antHouseNumberStr.substring(antHouseNumberStr.indexOf(",") + 1);
                antHouseNumberStr = antHouseNumberStr.substring(0, antHouseNumberStr.indexOf(","));
            }
            if (antAptoStr != null && !antAptoStr.trim().isEmpty() && antAptoStr.contains(",")) {
                aptoStr = antAptoStr.substring(antAptoStr.indexOf(",") + 1);
                antAptoStr = antAptoStr.substring(0, antAptoStr.indexOf(","));
            }
        } else {
            streetNameStr = solicitud.getStreetName();
            LOGGER.error("streetNameStr--> " + streetNameStr);
            houseNumberStr = solicitud.getHouseNumber();
            aptoStr = solicitud.getAparmentNumber();
        }

        if (solicitud.getNodo() != null && !solicitud.getNodo().trim().isEmpty()
                && solicitud.getCambioNodo() != null && !solicitud.getCambioNodo().isEmpty()) {
            botonValidar = true;
            nodoRender = "true";
        } else {
            botonCrear = true;
        }

        if (solicitud.getEstadoHhpp() != null && !solicitud.getEstadoHhpp().trim().isEmpty()
                && solicitud.getEstadoHhppNuevo() != null && !solicitud.getEstadoHhppNuevo().isEmpty()) {
            estadoRender = "true";
        }
        DrDireccion drDirec = drDireccionFacade.findByIdSolicitud(solicitud.getIdSolicitud());
        componenteDireccionBean.loadDirDetalle(drDirec);
        componenteDireccionBean.setReadOnly(true);

        String idDirCatastro = drDirec.getIdDirCatastro();
        BigDecimal dirId;
        nodoGeoList = new ArrayList<String>();
        if (idDirCatastro.contains("d")) {
            DireccionMgl dir = null;
            dirId = new BigDecimal(idDirCatastro.split("d")[1]);
            try {
                dir = direccionFacade.findByDirId(dirId).get(0);
                String nodo1 = dir.getDirNodouno();
                String nodo2 = dir.getDirNododos();
                String nodo3 = dir.getDirNodotres();

                if (listNodeNFI != null && !listNodeNFI.isEmpty()) {
                    for (NodoRR nodo : listNodeNFI) {
                        nodoGeoList.add(nodo.getCodigo());
                    }
                }

                if (nodo1 != null && !nodo1.isEmpty()) {
                    nodoGeoList.add(nodo1);
                }
                if (nodo2 != null && !nodo2.isEmpty()) {
                    nodoGeoList.add(nodo2);
                }
                if (nodo3 != null && !nodo3.isEmpty()) {
                    nodoGeoList.add(nodo3);
                }

            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("No se pueden cargar los nodos porque La dirección no existe." + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("No se pueden cargar los nodos porque La dirección no existe." + e.getMessage(), e, LOGGER);
            }

        } else if (idDirCatastro.contains("s")) {
            SubDireccionMgl subDir = new SubDireccionMgl();
            dirId = new BigDecimal(idDirCatastro.split("s")[1]);
            try {
                subDir.setSdiId(dirId);
                subDir = subDireccionFacade.findById(subDir);
                String nodo1 = subDir.getSdiNodouno();
                String nodo2 = subDir.getSdiNododos();
                String nodo3 = subDir.getSdiNodotres();

                if (listNodeNFI != null && !listNodeNFI.isEmpty()) {
                    for (NodoRR nodo : listNodeNFI) {
                        nodoGeoList.add(nodo.getCodigo());
                    }
                }

                if (nodo1 != null && !nodo1.isEmpty()) {
                    nodoGeoList.add(nodo1);
                }
                if (nodo2 != null && !nodo2.isEmpty()) {
                    nodoGeoList.add(nodo2);
                }
                if (nodo3 != null && !nodo3.isEmpty()) {
                    nodoGeoList.add(nodo3);
                }

            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("No se pueden cargar los nodos porque la subdirección no existe." + e.getMessage() , e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("No se pueden cargar los nodos porque la subdirección no existe." + e.getMessage() , e, LOGGER);
            }

        }

        List<DetalleDireccionEntity> detalle = ComponenteDireccionesManager.queryConsultarDetalle(solicitud.getIdSolicitud().toString());
        session.setAttribute("DireccionEntityPrincipal", detalle.get(0));
        if (detalle.get(0) != null) {
            DireccionRRManager direccionRRManager = new DireccionRRManager(detalle.get(0));
            respuesta = " Dirección Estandarizada: [Calle: " + direccionRRManager.getDireccion().getCalle()
                    + ", Placa: " + direccionRRManager.getDireccion().getNumeroUnidad()
                    + ", Número de Apartamento: " + direccionRRManager.getDireccion().getNumeroApartamento() + "]";
            respuestaTemp = respuesta;
        }

    }

    public void addResultadoToRespuesta() {
        if (resultado != null && !resultado.isEmpty()) {
            respuesta = resultado + " -" + respuestaTemp;
        }

    }

    public String validarNodo() {
        try {
            String nodoValidar = "";
            String nodoAproximado = solicitud.getCambioNodo();
            if (nodoAproximado != null && !nodoAproximado.isEmpty()) {
                nodoValidar = nodoAproximado;
            }
            if (nodoValidar.contains("BI:") || nodoValidar.contains("U1:") || nodoValidar.contains("U2:")) {
                nodoValidar = nodoValidar.substring(3);
            }
                boolean isCertificado = nodoRRFacadeLocal.isNodoRRCertificado(nodoValidar, securityLogin.getLoginUser());
                if (!isCertificado) {
                    String msn = Constantes.MSG_DIR_NODO_NO_CERTIFICADO + " " + nodoValidar;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                } else {
                    String msn = Constantes.MSG_NODO_CERTIFICADO + " " + nodoValidar;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                }
                botonCrear = true;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarNodo." + e.getMessage() , e, LOGGER);
        }
        return null;
    }

    public void finalizar() throws ApplicationException {
        if (resultado == null || resultado.isEmpty()) {
            String msn = " Debe Ingresar resultado";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error("msn: " + msn);
            return;
        }
        DireccionRREntity dirRREntity = new DireccionRREntity();
        String solicitudId = solicitud.getIdSolicitud().toString();
        BigDecimal id = new BigDecimal(solicitudId);
        DetalleDireccionEntity direccionEntity = (DetalleDireccionEntity) session.getAttribute("DireccionEntityPrincipal");
        String hhppGestionado = "";
        rrRegional = solicitud.getRegional();
        rrCiudad = solicitud.getCiudad();
        String nodoNuevo = solicitud.getCambioNodo();
        String estadoNuevo = solicitud.getEstadoHhppNuevo();
        String cambioDirMod = solicitud.getCambioDir();
        if (nodoNuevo != null && !nodoNuevo.trim().isEmpty()) {
            cambioDirMod = "4";
        }
        if (estadoNuevo != null && !estadoNuevo.trim().isEmpty()) {
            cambioDirMod += "3";
        }

        String antStreetName = solicitud.getStreetName();
        String antHouseNumber = solicitud.getHouseNumber();
        String antAptoNumber = solicitud.getAparmentNumber();
        String newStreetName = "";
        String newHouseNumber = "";
        String newAptoNumber = "";
        //si existe un cambio de direccion filtramos la info de forma adecuada
        if (solicitud.getCambioDir() != null && solicitud.getCambioDir().equalsIgnoreCase("1")) {
            if (antStreetName != null && !antStreetName.trim().isEmpty() && antStreetName.contains(",")) {
                newStreetName = antStreetName.substring(antStreetName.indexOf(",") + 1);
                antStreetName = antStreetName.substring(0, antStreetName.indexOf(","));
            }
            if (antHouseNumber != null && !antHouseNumber.trim().isEmpty() && antHouseNumber.contains(",")) {
                newHouseNumber = antHouseNumber.substring(antHouseNumber.indexOf(",") + 1);
                antHouseNumber = antHouseNumber.substring(0, antHouseNumber.indexOf(","));
            }
            if (antAptoNumber != null && !antAptoNumber.trim().isEmpty() && antAptoNumber.contains(",")) {
                newAptoNumber = antAptoNumber.substring(antAptoNumber.indexOf(",") + 1);
                antAptoNumber = antAptoNumber.substring(0, antAptoNumber.indexOf(","));
            }
        }

        if (!resultado.contains(" NO ")) {

            //cambiamos la direccion del hhpp
            if (solicitud.getCambioDir() != null && solicitud.getCambioDir().equalsIgnoreCase("1")) {
                hhpprrEJB.cambiarDirHHPPRR(rrCiudad, rrRegional,
                        antHouseNumber, antStreetName, antAptoNumber,
                        rrCiudad, rrRegional,
                        newHouseNumber, newStreetName, newAptoNumber,
                        solicitudId, securityLogin.getLoginUser(),
                        solicitud.getTipoSol(), "", direccionEntity.getIdtipodireccion());
                hhppGestionado = "true";
            }

            //cambiamos Nodo y estado del HHPP
            if (cambioDirMod != null && (cambioDirMod.contains("4") || cambioDirMod.contains("3"))) {
                hhppGestionado = hhpprrEJB.cambioEstadoNodoHHPP(direccionEntity, rrCiudad, rrRegional, nodoNuevo, estadoNuevo, cambioDirMod, solicitudId, securityLogin.getLoginUser(), solicitud.getTipoSol());
                if (hhppGestionado == null) {
                    hhppGestionado = "true";
                }
            }

        } else {
            hhppGestionado = "true";
        }

        if (hhppGestionado.trim().isEmpty()
                || hhppGestionado.trim().equalsIgnoreCase("false")) {
            String msn = " No fue posible Gestionar la solicitud del HHPP " + hhppGestionado;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            LOGGER.error("msn: " + msn);

        } else {
            solicitud.setRespuesta(" |" + new Date() + "|" + respuesta + "|");
            solicitud.setFechaCancelacion(new Date());
            solicitud.setEstado("FINALIZADO");
            solicitud.setUsuario(securityLogin.getLoginUser());
            solicitud.setRptGestion(resultado);
            solicitudFacade.update(solicitud);

            String dirEstandarizadaRR = "Numero de Unidad: " + dirRREntity.getNumeroUnidad()
                    + " Calle: " + dirRREntity.getCalle()
                    + " Número de Apartamento: " + dirRREntity.getNumeroApartamento();

            String msn = getMensajeBodyMail(resultado,
                    dirEstandarizadaRR.toUpperCase(), solicitud.getNodo(),
                    id.toString(), " ",
                    nodoNuevo, " ", dirRREntity.getComunidad(), dirRREntity.getDivision());
            msn += " " + respuesta;
            try {
                MailSender.send(Constantes.IP_SERV_CORREO, Constantes.MAIL_FROM,
                        "" + solicitud.getCorreo() + "",
                        "", "", "Su numero de VT es :'" + id.toString() + "' y se finalizo correctamente",
                        false, new StringBuffer("" + msn + ""), true);
            } catch (Exception e) {
               FacesUtil.mostrarMensajeError("Error Enviando el Mail." + e.getMessage() , e, LOGGER);
            }
            session.removeAttribute("DireccionEntityPrincipal");
            msn = "Solicitud gestionada con éxito";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            botonCrear = false;
        }
    }

    public String getMensajeBodyMail(String resptModulo,
            String paramDireccion, String paramNodoReal,
            String paramSolicitud, String paramCtaMatriz,
            String paramNodoSelect, String paramEstrato,
            String codCiudad, String codRegional) {
        try {
            String mensajebody = parametrosMultivalorEJBRemote.getBodymesajeMail(resptModulo);
            if (mensajebody != null && !mensajebody.isEmpty()) {
                mensajebody = mensajebody.replaceAll(Constantes.PARAMETRO_X1, paramDireccion);
                mensajebody = mensajebody.replaceAll(Constantes.PARAMETRO_X2, paramNodoReal);
                mensajebody = mensajebody.replaceAll(Constantes.PARAMETRO_X3, paramSolicitud);
                mensajebody = mensajebody.replaceAll(Constantes.PARAMETRO_X4, paramCtaMatriz);
                mensajebody = mensajebody.replaceAll(Constantes.PARAMETRO_X5, paramNodoSelect);
                mensajebody = mensajebody.replaceAll(Constantes.PARAMETRO_X6, paramEstrato);
                mensajebody = mensajebody.replaceAll(Constantes.PARAMETRO_X7, codCiudad);
                mensajebody = mensajebody.replaceAll(Constantes.PARAMETRO_X8, codRegional);
            }
            return mensajebody;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en getMensajeBodyMail." + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getMensajeBodyMail." + e.getMessage() , e, LOGGER);
        }

        return resptModulo;
    }

    public void desbloquearDisponibilidadGestion() throws ApplicationException {
        try {
            solicitudFacade.desbloquearDisponibilidadGestion(solicitud.getIdSolicitud());
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Visitas_Tecnicas/faces/Gestion.jsp");
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en desbloquearDisponibilidadGestion" + e.getMessage() , e, LOGGER);
            throw new ApplicationException("Error en desbloquearDisponibilidadGestion" + e.getMessage(), e);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en desbloquearDisponibilidadGestion" + e.getMessage() , e, LOGGER);
            throw new ApplicationException("Error en desbloquearDisponibilidadGestion" + e.getMessage(), e);
        }
    }

    public String obtenerEstado(String letra) {
        for (EstadoHhpp e : estadoHhppList) {
            if (e.getEhhId().equalsIgnoreCase(letra)) {
                return e.getEhhNombre();
            }
        }
        return null;
    }

    public String getRrRegional() {
        return rrRegional;
    }

    public void setRrRegional(String rrRegional) {
        this.rrRegional = rrRegional;
    }

    public String getRrCiudad() {
        return rrCiudad;
    }

    public void setRrCiudad(String rrCiudad) {
        this.rrCiudad = rrCiudad;
    }

    public String recargar() {
        return null;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public List<RrCiudades> getCiudadesList() {
        return ciudadesList;
    }

    public void setCiudadesList(List<RrCiudades> ciudadesList) {
        this.ciudadesList = ciudadesList;
    }

    public List<CmtRegionalRr> getRegionalList() {
        return regionalList;
    }

    public void setRegionalList(List<CmtRegionalRr> regionalList) {
        this.regionalList = regionalList;
    }

    public Integer getTelContacto() {
        return telContacto;
    }

    public void setTelContacto(Integer telContacto) {
        this.telContacto = telContacto;
    }

    public Integer getTelSolicitante() {
        return telSolicitante;
    }

    public void setTelSolicitante(Integer telSolicitante) {
        this.telSolicitante = telSolicitante;
    }

    public String getMsnCertificado() {
        return msnCertificado;
    }

    public void setMsnCertificado(String msnCertificado) {
        this.msnCertificado = msnCertificado;
    }

    public boolean isBotonCrear() {
        return botonCrear;
    }

    public void setBotonCrear(boolean botonCrear) {
        this.botonCrear = botonCrear;
    }

    public boolean isBotonValidar() {
        return botonValidar;
    }

    public void setBotonValidar(boolean botonValidar) {
        this.botonValidar = botonValidar;
    }

    public boolean isBotonRegresar() {
        return botonRegresar;
    }

    public void setBotonRegresar(boolean botonRegresar) {
        this.botonRegresar = botonRegresar;
    }

    public List<EstadoHhpp> getEstadoHhppList() {
        return estadoHhppList;
    }

    public void setEstadoHhppList(List<EstadoHhpp> estadoHhppList) {
        this.estadoHhppList = estadoHhppList;
    }

    public String getCambioDir() {
        return cambioDir;
    }

    public void setCambioDir(String cambioDir) {
        this.cambioDir = cambioDir;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public UnidadStructPcml getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadStructPcml unidad) {
        this.unidad = unidad;
    }

    public String[] getTipoModificacionList() {
        return tipoModificacionList;
    }

    public void setTipoModificacionList(String[] tipoModificacionList) {
        this.tipoModificacionList = tipoModificacionList;
    }

    public String getNodoRender() {
        return nodoRender;
    }

    public void setNodoRender(String nodoRender) {
        this.nodoRender = nodoRender;
    }

    public String getEstadoRender() {
        return estadoRender;
    }

    public void setEstadoRender(String estadoRender) {
        this.estadoRender = estadoRender;
    }

    public String getStreetNameStr() {
        return streetNameStr;
    }

    public void setStreetNameStr(String streetNameStr) {
        this.streetNameStr = streetNameStr;
    }

    public String getHouseNumberStr() {
        return houseNumberStr;
    }

    public void setHouseNumberStr(String houseNumberStr) {
        this.houseNumberStr = houseNumberStr;
    }

    public String getAptoStr() {
        return aptoStr;
    }

    public void setAptoStr(String aptoStr) {
        this.aptoStr = aptoStr;
    }

    public String getCambioDirRender() {
        return cambioDirRender;
    }

    public void setCambioDirRender(String cambioDirRender) {
        this.cambioDirRender = cambioDirRender;
    }

    public String getAntStreetNameStr() {
        return antStreetNameStr;
    }

    public void setAntStreetNameStr(String antStreetNameStr) {
        this.antStreetNameStr = antStreetNameStr;
    }

    public String getAntHouseNumberStr() {
        return antHouseNumberStr;
    }

    public void setAntHouseNumberStr(String antHouseNumberStr) {
        this.antHouseNumberStr = antHouseNumberStr;
    }

    public String getAntAptoStr() {
        return antAptoStr;
    }

    public void setAntAptoStr(String antAptoStr) {
        this.antAptoStr = antAptoStr;
    }

    public boolean isPanelSelectTipoMod() {
        return panelSelectTipoMod;
    }

    public void setPanelSelectTipoMod(boolean panelSelectTipoMod) {
        this.panelSelectTipoMod = panelSelectTipoMod;
    }

    public String getDireccionLabel() {
        return direccionLabel;
    }

    public void setDireccionLabel(String direccionLabel) {
        this.direccionLabel = direccionLabel;
    }

    public boolean isRenderMulti() {
        return renderMulti;
    }

    public void setRenderMulti(boolean renderMulti) {
        this.renderMulti = renderMulti;
    }

    public String getBarrioSugerido() {
        return barrioSugerido;
    }

    public void setBarrioSugerido(String barrioSugerido) {
        this.barrioSugerido = barrioSugerido;
    }

    public List<AddressSuggested> getBarriosList() {
        return barriosList;
    }

    public void setBarriosList(List<AddressSuggested> barriosList) {
        this.barriosList = barriosList;
    }

    public boolean isNuevoBarrioField() {
        return nuevoBarrioField;
    }

    public void setNuevoBarrioField(boolean nuevoBarrioField) {
        this.nuevoBarrioField = nuevoBarrioField;
    }

    public boolean isBotonNuevaDir() {
        return botonNuevaDir;
    }

    public void setBotonNuevaDir(boolean botonNuevaDir) {
        this.botonNuevaDir = botonNuevaDir;
    }

    public boolean isBarrioField() {
        return barrioField;
    }

    public void setBarrioField(boolean barrioField) {
        this.barrioField = barrioField;
    }

    public List<String> getNodoGeoList() {
        return nodoGeoList;
    }

    public void setNodoGeoList(List<String> nodoGeoList) {
        this.nodoGeoList = nodoGeoList;
    }

    public String getRespuestaTemp() {
        return respuestaTemp;
    }

    public void setRespuestaTemp(String respuestaTemp) {
        this.respuestaTemp = respuestaTemp;
    }

    public boolean isPanelRegion() {
        return panelRegion;
    }

    public void setPanelRegion(boolean panelRegion) {
        this.panelRegion = panelRegion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getNodoSolicitud() {
        return nodoSolicitud;
    }

    public void setNodoSolicitud(String nodoSolicitud) {
        this.nodoSolicitud = nodoSolicitud;
    }
}
