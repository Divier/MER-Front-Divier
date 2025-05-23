package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.dao.impl.cm.CmtBasicaMglDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaNodosDto;
import co.com.claro.mgl.dtos.FiltroConjsultaBasicasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacade;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComunidadRrFacade;
import co.com.claro.mgl.facade.cm.CmtComunidadRrFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTecnologiaSubMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.rest.dtos.NodoMerDto;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "nodoMglBean")
@ViewScoped
@Log4j2
@Getter
@Setter
public class NodoMglBean implements Serializable {

    /**
     *
     */
    
    @EJB
    private NodoMglFacadeLocal nodoMglFacade = new NodoMglFacade();
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtComunidadRrFacadeLocal cmtComunidadRrFacadeLocal = new CmtComunidadRrFacade();
    @EJB
    private CmtSubEdificioMglFacadeLocal subEdificioMglfacadeLocal;
    @EJB
    private CmtTecnologiaSubMglFacadeLocal tecnologiaSubMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @Inject
    private BlockReportServBean blockReportServBean;
    /**
     * Paginacion Tabla
     */
    private String estiloObligatorio = "<font color='red'>*</font>";
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private String usuarioID = null;
    private NodoMgl nodoMgl = null;
    private List<NodoMgl> nodoMglList;
    private String idSqlSelected;
    private BigDecimal divisional;
    private BigDecimal area;
    private BigDecimal distrito;
    private BigDecimal unidadGestion;
    private BigDecimal zona;
    private String geograficoPoliticoMgl;
    private String geograficoMgl;
    private String centroPoblado;
    private String codigo;
    private GeograficoPoliticoMgl geograficoPoliticoMglDpo;
    private boolean guardado = true;
    public List<CmtBasicaMgl> listBasicaDivicional = null;
    public List<CmtBasicaMgl> listBasicaDistrito = null;
    private List<CmtBasicaMgl> listBasicaOpera = new ArrayList<>();
    public List<CmtBasicaMgl> listBasicaUnidadGestion = null;
    public List<CmtBasicaMgl> listBasicaZona = null;
    public List<GeograficoPoliticoMgl> listGeograficoPoliticoMgl;
    public List<GeograficoPoliticoMgl> listGeograficoMgl;
    private List<GeograficoPoliticoMgl> listCentrosPoblados;
    public List<CmtBasicaMgl> listBasicaArea = null;
    private String certificado;
    private List<CmtBasicaMgl> listBasica = null;//valbuenayf lista tecnologia
    private BigDecimal nodoTipo;   //valbuenayf    tipo de tecnologia 
    private String message;
    private HtmlDataTable dataTable = new HtmlDataTable();
    private String numPagina;
    private List<Integer> paginaList;
    private String pageActual;
    private int actual;
    private static final int numeroRegistrosConsulta = 15;
    private CmtFiltroConsultaNodosDto cmtFiltroConsultaNodosDto = new CmtFiltroConsultaNodosDto();
    GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
    CmtBasicaMglDaoImpl basicaMgl = new CmtBasicaMglDaoImpl();
    //campos agregados para mostrar en la vista denodoMglView
    //lista de aliados y 
    public List<CmtBasicaMgl> listBasicaAliados = null;
    public List<CmtBasicaMgl> listBasicaEstadoNodos = null;
    public List<CmtComunidadRr> listComunidadess = null;
    private BigDecimal aliado;
    private BigDecimal estado;
    private String codigoComunidadRR;
    private String factibilidad;
    private static final String[] NOM_COLUMNAS = {"Departamento","Ciudad","Centro Poblado","Codigo",
                "Nombre","Comunidad","Divicional","Area","Distrito", "Zona",
                "Unidad de Gestion", "Tipo Nodo", "Estado Nodo", "Usuario Creación",
                "Fecha Creación","fecha Modificación","Fecha Apertura"};
    private boolean botonExport;
    private final String VALIDARNUEVONODOROLND = "VALIDARNUEVONODOROLND";
    private final String VALIDAREXPORTARROLND = "VALIDAREXPORTARROLND";
    private final String VALIDAROPCIONVERROLND = "VALIDAROPCIONVERROLND";
    private SecurityLogin securityLogin;
    private boolean flag;
    private String anchoBanda;
    private List<CmtBasicaMgl> anchoBandaList;
    private static final String ANCHO_BANDA = "141237";


    /**
     * Creates a new instance of NodoMglBean
     */
    public NodoMglBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            if (usuarioVT == null) {
                session.getAttribute("loginUserSecurity");
                usuarioVT = (String) session.getAttribute("loginUserSecurity");
            } else {
                session = (HttpSession) facesContext.getExternalContext().getSession(false);
                session.setAttribute("loginUserSecurity", usuarioVT);
            }
            usuarioID = securityLogin.getIdUser();
            if (usuarioID == null) {
                session.getAttribute("usuarioIDM");
                usuarioID = (String) session.getAttribute("usuarioIDM");
            } else {
                session = (HttpSession) facesContext.getExternalContext().getSession(false);
                session.setAttribute("usuarioIDM", usuarioID);
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en NodoMglBean. ", e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en NodoMglBean. ", e, LOGGER);
        }
    }

    @PostConstruct
    public void fillSqlList() {
        try {
            NodoMgl idNodoMgl = (NodoMgl) session.getAttribute("idNodoMgl");
            session.removeAttribute("idNodoMgl");
            if (idNodoMgl != null) {
                nodoMgl = idNodoMgl;
                irNodoMgl();
            } else {
                nodoMgl = new NodoMgl();
                nodoMgl.setNodId(BigDecimal.ZERO);
                nodoMgl.setNodUsuarioCreacion(usuarioVT);
                listGeograficoMgl = new ArrayList<>();
                listCentrosPoblados = new ArrayList<>();
            }
            nodoMglList = new ArrayList<>();
            botonExport=true;
            listInfoByPage(1);

            //Autor: victor bocanegra
            //se cargan las divicionales de basica
            CmtTipoBasicaMgl tipoBasicaDivicional;
            tipoBasicaDivicional = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_DIVICIONAL_COMERCIAL_TELMEX);
            listBasicaDivicional = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaDivicional);

            CmtTipoBasicaMgl tipoBasicaDistrito;
            tipoBasicaDistrito = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_DISTRITOS_COMERCIALES_TELMEX);
            listBasicaDistrito = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaDistrito);

            CmtTipoBasicaMgl tipoBasicaUnidadGestion;
            tipoBasicaUnidadGestion = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_UNIDADES_GESTION_TELMEX);
            listBasicaUnidadGestion = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaUnidadGestion);

            CmtTipoBasicaMgl tipoBasicaZona;
            tipoBasicaZona = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_ZONA_COMERCIALES_TELMEX);
            listBasicaZona = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaZona);

            CmtTipoBasicaMgl tipoBasicaArea;
            tipoBasicaArea = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_AREAS_COMERCIALES_TELMEX);
            listBasicaArea = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaArea);

            //Autor: Jonathan  Peña
            //se cargan la lista de los aliados de basica
            CmtTipoBasicaMgl tipoBasicaAliados;
            tipoBasicaAliados = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_ALIADOS);
            listBasicaAliados = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaAliados);

            //Autor: Jonathan  Peña
            //se cargan la lista de los estados nodos de basica
            CmtTipoBasicaMgl tipoBasicaEstadoNodos;
            tipoBasicaEstadoNodos = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_NODO);
            listBasicaEstadoNodos = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaEstadoNodos);

            listGeograficoPoliticoMgl = geograficoPoliticoMglFacadeLocal.findDptos();
            //valbuenayf inicio cargar lista tecnologia
            listBasica = cargarListaBasica();
            consultarAnchoBanda();
            
            CmtTipoBasicaMgl tipoBasicaOpera;
            tipoBasicaOpera = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(Constant.TIPO_BASICA_OPERA);
            listBasicaOpera = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaOpera);
            
        } catch (ApplicationException e) {
            LOGGER.error("Error en fillSqlList. " +e.getMessage(), e);   
        } catch (Exception e) {
            LOGGER.error("Error en fillSqlList. " +e.getMessage(), e);   
        }
    }

    public String goActualizar() {
        try {
            nodoMgl = (NodoMgl) dataTable.getRowData();
            session.setAttribute("idNodoMgl", nodoMgl);
            return "nodoMglView";
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en goActualizar. ", e, LOGGER);
            if (session == null) {
                return "nodoMglListView";
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en goActualizar. ", e, LOGGER);
            if (session == null) {
                return "nodoMglListView";
            }
        }
        return null;
    }

    public void irNodoMgl() {

        try {
            setGuardado(false);
            
            if (nodoMgl.getDivId() != null) {
                divisional = nodoMgl.getDivId().getBasicaId() == null ? new BigDecimal("0") : nodoMgl.getDivId().getBasicaId();
            } else {
                divisional = new BigDecimal("0");
            }
            if (nodoMgl.getAreId() != null) {
                area = nodoMgl.getAreId().getBasicaId() == null ? new BigDecimal("0") : nodoMgl.getAreId().getBasicaId();
            } else {
                area = new BigDecimal("0");
            }
            if (nodoMgl.getDisId() != null) {
                distrito = nodoMgl.getDisId().getBasicaId() == null ? new BigDecimal("0") : nodoMgl.getDisId().getBasicaId();
            } else {
                distrito = new BigDecimal("0");
            }
            if (nodoMgl.getUgeId() != null) {
                unidadGestion = nodoMgl.getUgeId().getBasicaId() == null ? new BigDecimal("0") : nodoMgl.getUgeId().getBasicaId();
            } else {
                unidadGestion = new BigDecimal("0");
            }
            if (nodoMgl.getZonId() != null) {
                zona = nodoMgl.getZonId().getBasicaId() == null ? new BigDecimal("0") : nodoMgl.getZonId().getBasicaId();
            } else {
                zona = new BigDecimal("0");
            }
            if (nodoMgl.getEstado() != null) {
                estado = nodoMgl.getEstado().getBasicaId() == null ? new BigDecimal("0") : nodoMgl.getEstado().getBasicaId();
            } else {
                estado = new BigDecimal("0");
            }
            
            if (nodoMgl.getNodTipo() != null) {
                nodoTipo = nodoMgl.getNodTipo().getBasicaId() == null ? new BigDecimal("0") : nodoMgl.getNodTipo().getBasicaId();
            } else {
                nodoTipo = new BigDecimal("0");
            }
            
            aliado = nodoMgl.getAliadoId() == null ? new BigDecimal("0")
                    : nodoMgl.getAliadoId().getBasicaId();
            
            factibilidad = nodoMgl.getFactibilidad() != null ? nodoMgl.getFactibilidad() : "";
            
            buscarGeograficoPoliticoById(nodoMgl.getGpoId());
            geograficoMgl = geograficoPoliticoMglDpo.getGeoGpoId() == null ? "0"
                    : geograficoPoliticoMglDpo.getGeoGpoId().toString();//geograficoPoliticoMgl  son las ciudades
            centroPoblado = nodoMgl.getGpoId() == null ? "0"
                    : nodoMgl.getGpoId().toString();//geograficoMgl  son los centros poblados
            
            anchoBanda = nodoMgl.getAnchoBanda() != null ? nodoMgl.getAnchoBanda() : "";

            CmtBasicaMgl inactivo;
            
            inactivo = basicaMgl.findByCodigoInternoApp(Constant.BASICA_TIPO_ESTADO_NODO_NO_CERTIFICADO);
            
            
            if (nodoMgl.getEstado() == null) {
                nodoMgl.setEstado(inactivo);
            }
            
            if (nodoMgl.getComId() != null) {
                codigoComunidadRR = nodoMgl.getComId().getCodigoRr();
            }
            
            buscarGeograficoPoliticoById(new BigDecimal(geograficoMgl));
            geograficoPoliticoMgl = geograficoPoliticoMglDpo.getGeoGpoId() == null ? "0"
                    : geograficoPoliticoMglDpo.getGeoGpoId().toString();//geograficoPoliticoMgl es la ciudad
            
            consultarCiudades();
            consultarCentrosPoblados();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irNodoMgl: "+e.getMessage()+"", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irNodoMgl: "+e.getMessage()+"", e, LOGGER);
        }
    }

    public String nuevoNodoMgl() {
        nodoMgl = null;
        nodoMgl = new NodoMgl();
        nodoMgl.setNodId(BigDecimal.ZERO);
        setGuardado(true);
        return "nodoMglView";
    }

    public void crearNodoMgl() {
        try {
            nodoMgl.setNodId(null);
            NodoMgl nodo = nodoMglFacade.findByCodigo(nodoMgl.getNodCodigo());
            if(nodo != null){
                message = "El códido del nodo que desea crear ya se encuentra registrado. Intente con otro codigo por favor.";
                session.setAttribute("message", message);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, ""));
                return;
            }
            if (configurarNodo()) {
                nodoMgl.setAnchoBanda(anchoBanda);
                nodoMgl.setEstadoRegistro(1);
                nodoMgl.setNodUsuarioCreacion(usuarioVT);
                nodoMgl.setNodFechaCreacion(new Date());
                nodoMgl = nodoMglFacade.create(nodoMgl);
                setGuardado(false);
                message = "Proceso exitoso: se ha creado el nodo  <b>" + nodoMgl.getNodId() + "</b>  "
                        + "satisfactoriamente";
                session.setAttribute("message", message);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));

            } else {
                session.setAttribute("message", message);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));

            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en crearNodoMgl: "+e.getMessage()+" ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en crearNodoMgl: "+e.getMessage()+" ", e, LOGGER);
        }
    }

    public void actualizarlNodoMgl() {
        try {
            if (configurarNodo()) {
                NodoMgl nodoUpd = nodoMglFacade.findByCodigo(nodoMgl.getNodCodigo());
                if (nodoUpd != null && nodoUpd.getNodId() != null) {
                    if (nodoUpd.getNodId().compareTo(nodoMgl.getNodId()) == 0) {
                        nodoMgl.setNodUsuarioModificacion((String) session.getAttribute("usuarioM"));
                        nodoMgl.setNodFechaModificacion(new Date());
                        nodoMgl.setAnchoBanda(anchoBanda);
                        nodoMglFacade.update(nodoMgl);
                        message = "Proceso exitoso: Se ha actualizado el nodo:  <b>" + nodoMgl.getNodNombre() + "</b> "
                                + " satisfatoriamente";
                        session.setAttribute("message", message);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));

                    } else {
                        message = "Proceso falló: Ya existe un nodo con el mismo codigo";
                        session.setAttribute("message", message);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
                    }
                }else {
                    message = "Proceso falló: No se puede modificar el codigo del nodo";
                    session.setAttribute("message", message);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
                }
            } else {
                session.setAttribute("message", message);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));

            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en actualizarlNodoMgl: "+e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en actualizarlNodoMgl: "+e.getMessage(), e, LOGGER);
        }
    }

    private boolean configurarNodo() throws ApplicationException {

        
        if (nodoTipo != null) {
            CmtBasicaMgl tipoBasicaMgl = cmtBasicaMglFacadeLocal.findById(nodoTipo);
            if (tipoBasicaMgl != null) {
                nodoMgl.setNodTipo(tipoBasicaMgl);
            } else {
                message = "basica tipo Nodo  " + nodoTipo + "  no encontrada en el repositorio";
                return false;
            }
        } else {
            message = "Campo Tipo Nodo es obligatorio";
            return false;
        }
  
        if(centroPoblado != null){
          nodoMgl.setGpoId(new BigDecimal(centroPoblado));  
        }

        if (StringUtils.isNotBlank(codigoComunidadRR)) {
            CmtComunidadRr comId = cmtComunidadRrFacadeLocal.findByCodigoRR(codigoComunidadRR);
            nodoMgl.setComId(comId);
            if (nodoMgl.getComId() == null) {
                message = "Comunidad RR no encontrada";
                return false;
            }
        } else {
            message = "Campo Comunidad es requerido";
            return false;
        }

        if (nodoMgl.getNodCampoAdicional5().length() > 50) {
            message = "SDS/BTS BACKUP supera los 50 caracteres";
            return false;
        }


        if (unidadGestion != null) {
            CmtBasicaMgl uniGesBasicaMgl = cmtBasicaMglFacadeLocal.findById(unidadGestion);
            if (uniGesBasicaMgl != null) {
                nodoMgl.setUgeId(uniGesBasicaMgl);
            } else {
                message = "basica Unidad Gestion  " + unidadGestion + "  no encontrada en el repositorio";
                return false;
            }
        } else {
            message = "Campo Unidad gestión es obligatorio";
            return false;
        }

        if (divisional != null) {
            CmtBasicaMgl divBasicaMgl = cmtBasicaMglFacadeLocal.findById(divisional);
            if (divBasicaMgl != null) {
                nodoMgl.setDivId(divBasicaMgl);
            } else {
                message = "basica Divicional " + divisional + " no encontrada en el repositorio";
                return false;
            }
        } else {
            message = "Campo Divicional es obligatorio";
            return false;
        }

        if (area != null) {
            CmtBasicaMgl areaBasicaMgl = cmtBasicaMglFacadeLocal.findById(area);
            if (areaBasicaMgl != null) {
                nodoMgl.setAreId(areaBasicaMgl);
            } else {
                message = "basica Area  " + area + " no encontrada en el repositorio";
                return false;
            }
        } else {
            message = "Campo Area es obligatorio";
            return false;
        }

        if (distrito != null) {
            CmtBasicaMgl distBasicaMgl = cmtBasicaMglFacadeLocal.findById(distrito);
            if (distBasicaMgl != null) {
                nodoMgl.setDisId(distBasicaMgl);
            } else {
                message = "basica Distrito " + distrito + " no encontrada en el repositorio";
                return false;
            }
        } else {
            message = "Campo Distrito es obligatorio";
            return false;
        }

        if (zona != null) {
            CmtBasicaMgl zonaBasicaMgl = cmtBasicaMglFacadeLocal.findById(zona);
            if (zonaBasicaMgl != null) {
                nodoMgl.setZonId(zonaBasicaMgl);
            } else {
                message = "basica Zona " + zona + " no encontrada en el repositorio";
                return false;
            }
        } else {
            message = "Campo Zona es obligatorio";
            return false;
        }

        if (estado != null) {
            CmtBasicaMgl estaBasicaMgl = cmtBasicaMglFacadeLocal.findById(estado);
            if (estaBasicaMgl != null) {
                nodoMgl.setEstado(estaBasicaMgl);
            } else {
                message = "basica Estado " + estado + " no encontrada en el repositorio";
                return false;
            }
        } else {
            message = "Campo Estado es obligatorio";
            return false;
        }

        if (aliado != null) {
            CmtBasicaMgl aliaBasicaMgl = cmtBasicaMglFacadeLocal.findById(aliado);
            if (aliaBasicaMgl != null) {
                nodoMgl.setAliadoId(aliaBasicaMgl);
            } else {
                message = "basica Aliado " + aliaBasicaMgl + " no encontrada en el repositorio";
                return false;
            }
        } else {
            message = "Campo Aliado es obligatorio";
            return false;
        }
        
        if (factibilidad == null) {
            nodoMgl.setFactibilidad(null);
        } else {
            nodoMgl.setFactibilidad(factibilidad);
        }

        return true;
    }

    public String eliminarlNodoMgl() {
        try {
            List<CmtSubEdificioMgl> subEdificioMgls = subEdificioMglfacadeLocal.findSubEdificioByNodo(nodoMgl);
            if (!subEdificioMgls.isEmpty()) {
                message = "Proceso fallo: no se puede eliminar  el nodo:  " + nodoMgl.getNodNombre() + " "
                        + " esta asociado a subedificios";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
                session.setAttribute("message", message);
                return null;
            } else {
                List<CmtTecnologiaSubMgl> tecnologiaSubMgl = tecnologiaSubMglFacadeLocal.findTecnoSubByNodo(nodoMgl);
                if (!tecnologiaSubMgl.isEmpty()) {
                    message = "Proceso fallo: no se puede eliminar  el nodo:  " + nodoMgl.getNodNombre() + " "
                            + " esta asociado a tecnologias sub edificios";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
                    session.setAttribute("message", message);
                    return null;
                }
            }
            nodoMglFacade.delete(nodoMgl);
            message = "Proceso exitoso: Se ha eliminado el nodo:  <b>" + nodoMgl.getNodNombre() + "</b> "
                    + " satisfatoriamente";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
            session.setAttribute("message", message);

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en eliminarlNodoMgl: "+e.getMessage()+"", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en eliminarlNodoMgl: "+e.getMessage()+"", e, LOGGER);
        }
        return "nodoMglListView";
    }

    /**
     * valbuenayf Metodo para asignar el valor al tipo de tecnologia
     */
    public void listBasicaChange() {
        if (this.nodoTipo == null || this.nodoTipo.equals(new BigDecimal("0"))) {
            nodoMgl.setNodTipo(null);
            return;
        }

        for (CmtBasicaMgl b : listBasica) {
            if (b.getBasicaId().compareTo(this.nodoTipo) == 0) {
                nodoMgl.setNodTipo(b);
                return;
            }
        }
        nodoMgl.setNodTipo(null);
    }

    public void consultarCiudades() throws ApplicationException {
        try {
            listGeograficoMgl = geograficoPoliticoMglFacadeLocal.findCiudades(new BigDecimal(geograficoPoliticoMgl));
        } catch (ApplicationException e) {
            LOGGER.error("Error en consultarCiudades. " +e.getMessage(), e); 
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en consultarCiudades. " +e.getMessage(), e); 
            throw new ApplicationException("Error en consultarCiudades. ",e);
        }
    }

    public void consultarCentrosPoblados() throws ApplicationException {
        try {
            listCentrosPoblados =
                    geograficoPoliticoMglFacadeLocal.findCentrosPoblados(new BigDecimal(geograficoMgl));
        } catch (ApplicationException e) {
            LOGGER.error("Error en consultarCentrosPoblados. " +e.getMessage(), e);  
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en consultarCentrosPoblados. " +e.getMessage(), e); 
            throw new ApplicationException("Error en consultarCentrosPoblados. ",e);
        }
    }

    public void asignarFechaActivacionNodo() {
        if (nodoMgl.getEstado() != null
                && nodoMgl.getEstado().getIdentificadorInternoApp().equalsIgnoreCase(Constant.BASICA_TIPO_ESTADO_NODO_CERTIFICADO)) {
            nodoMgl.setNodFechaActivacion(new Date());
        } else {
            nodoMgl.setNodFechaActivacion(null);
        }
    }

    public void buscarGeograficoPoliticoById(BigDecimal gpoId) throws ApplicationException {
        try {
            geograficoPoliticoMglDpo = geograficoPoliticoMglFacadeLocal.findById(gpoId);
        } catch (ApplicationException e) {
            LOGGER.error("Error en buscarGeograficoPoliticoById. " +e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en buscarGeograficoPoliticoById. " +e.getMessage(), e);    
            throw new ApplicationException("Error en buscarGeograficoPoliticoById. ",e);
        }
    }

    /**
     * valbuenayf metodo para cargar las tecnologias
     *
     * @return
     */
    public List<CmtBasicaMgl> cargarListaBasica() {
        List<CmtBasicaMgl> resultList = new ArrayList<>();
        try {
            CmtTipoBasicaMgl tipoBasicaId =
                    cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            resultList = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaId);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarListaBasica. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarListaBasica. ", e, LOGGER);
        }
        return resultList;
    }

    public String listInfoByPage(int page) {
        try {
            PaginacionDto<NodoMgl> paginacionDto =
                    nodoMglFacade.findAllPaginado(page, numeroRegistrosConsulta, cmtFiltroConsultaNodosDto, 
                            geograficoPoliticoManager,false);
            nodoMglList = paginacionDto.getListResultado();            
            actual = page;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPage: "+e.getMessage()+"", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPage: "+e.getMessage()+"", e, LOGGER);
        }
        return null;
    }

    public void findByFiltro() {
        pageFirst();
    }

    public void pageFirst() {
        listInfoByPage(1);
    }

    public void pagePrevious() {
        int totalPaginas = getTotalPaginas();
        int nuevaPageActual = actual - 1;
        if (nuevaPageActual > totalPaginas) {
            nuevaPageActual = totalPaginas;
        }
        if (nuevaPageActual <= 0) {
            nuevaPageActual = 1;
        }
        listInfoByPage(nuevaPageActual);
    }

    public void irPagina() {
        int totalPaginas = getTotalPaginas();
        int nuevaPageActual = Integer.parseInt(numPagina);
        if (nuevaPageActual > totalPaginas) {
            nuevaPageActual = totalPaginas;
        }
        listInfoByPage(nuevaPageActual);
    }

    public void pageNext() {
        int totalPaginas = getTotalPaginas();
        int nuevaPageActual = actual + 1;
        if (nuevaPageActual > totalPaginas) {
            nuevaPageActual = totalPaginas;
        }
        listInfoByPage(nuevaPageActual);
    }

    public void pageLast() {
        int totalPaginas = getTotalPaginas();
        listInfoByPage(totalPaginas);
    }

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

    public int getTotalPaginas() {
        try {
            PaginacionDto<NodoMgl> paginacionDto =
                    nodoMglFacade.findAllPaginado(0, numeroRegistrosConsulta, cmtFiltroConsultaNodosDto,
                            geograficoPoliticoManager,false);
            Long count = paginacionDto.getNumPaginas();
            int totalPaginas = (int) ((count % numeroRegistrosConsulta != 0)
                    ? (count / numeroRegistrosConsulta) + 1
                    : count / numeroRegistrosConsulta);
            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginas. Al cargar lista de Nodos: "+e.getMessage()+"", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginas. Al cargar lista de Nodos: "+e.getMessage()+"", e, LOGGER);
        }
        return 1;
    }

    /**
     * Autor: Victor Bocanegra Reporte para la consulta de nodos
     *
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    public String exportExcel() throws ApplicationException {
        try {            
            int expLonPag = 1000;
            PaginacionDto<NodoMgl> paginacionDto
                    = nodoMglFacade.findAllPaginado(0, expLonPag, cmtFiltroConsultaNodosDto, geograficoPoliticoManager, true);

            long totalPag = paginacionDto.getNumPaginas();
            paginacionDto = null;
            StringBuilder sb = new StringBuilder();
            byte[] csvData;
            SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");

            for (int j = 0; j < NOM_COLUMNAS.length; j++) {
                sb.append(NOM_COLUMNAS[j]);
                if (j < NOM_COLUMNAS.length) {
                    sb.append(";");
                }
            }
            sb.append("\n");
            
            String todayStr = formato.format(new Date());
            String fileName = "NodosMGLConsulte" + "_" + todayStr + "." + "csv";
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
            response1.setHeader("Content-disposition", "attached; filename=\""
                    + fileName + "\"");
            response1.setContentType("application/octet-stream;charset=UTF-8;");           
            response1.setCharacterEncoding("UTF-8");
            
            csvData = sb.toString().getBytes();
            response1.getOutputStream().write(csvData);
            

            for (int exPagina = 1; exPagina <= ((totalPag/expLonPag)+ (totalPag%expLonPag != 0 ? 1: 0)); exPagina++) {
                        
                PaginacionDto<NodoMgl> consultaPaginacionDto
                     = nodoMglFacade.findAllPaginado(exPagina, expLonPag, null, 
                             geograficoPoliticoManager,false);
                
                //listado de nodos cargados      
                if (consultaPaginacionDto != null && consultaPaginacionDto.getListResultadoNodoMer() != null
                        && !consultaPaginacionDto.getListResultadoNodoMer().isEmpty()) {
                    
                   for (NodoMerDto nodoMer : consultaPaginacionDto.getListResultadoNodoMer()) {

                        if (sb.toString().length() > 1) {
                            sb.delete(0, sb.toString().length());
                        }

                        sb.append(nodoMer.getDepartamento());
                        sb.append(";");
                        sb.append(nodoMer.getCiudad());
                        sb.append(";");
                        sb.append(nodoMer.getCentroPoblado());
                        sb.append(";");
                        sb.append(nodoMer.getCodigo());
                        sb.append(";");
                        sb.append(nodoMer.getNombre());
                        sb.append(";");
                        sb.append(nodoMer.getComunidad());
                        sb.append(";");
                        sb.append(nodoMer.getDivision());
                        sb.append(";");
                        sb.append(nodoMer.getArea());
                        sb.append(";");
                        sb.append(nodoMer.getDistrito());
                        sb.append(";");
                        sb.append(nodoMer.getZona());
                        sb.append(";");
                        sb.append(nodoMer.getUnidadGestion());
                        sb.append(";");
                        sb.append(nodoMer.getTipoNodo());
                        sb.append(";");
                        sb.append(nodoMer.getEstadoNodo());
                        sb.append(";");
                        sb.append(nodoMer.getUsuarioCreacion());
                        sb.append(";");
                        sb.append(nodoMer.getFechaCreacion());
                        sb.append(";");
                        sb.append(nodoMer.getFechaModificacion());
                        sb.append(";");
                        sb.append(nodoMer.getFechaApertura());
                        sb.append(";");
                        sb.append("\n");
                        csvData = sb.toString().getBytes();
                        response1.getOutputStream().write(csvData);
                        response1.getOutputStream().flush();
                        response1.flushBuffer();
                        consultaPaginacionDto = null;
                        consultaPaginacionDto = new PaginacionDto<>();
                    }
                    consultaPaginacionDto = null;
                    consultaPaginacionDto = new PaginacionDto<>();
                    System.gc();
                    botonExport=true;
                    PrimeFaces.current().ajax().update("formPrincipal:panelButom");
                    
                }
            }     
            response1.getOutputStream().close();
            fc.responseComplete();
          
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        }
        return null;
    }

    /**
     * Autor: Dayver Delahoz Reporte para la consulta de nodos
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String exportExcelC() throws ApplicationException {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Exportar ADMINISTRACIÓN NODOS")) return StringUtils.EMPTY;

            int limitIni = 500;
            int limit = 10000;


                Integer total = nodoMglFacade.countAllPaginadoExport(cmtFiltroConsultaNodosDto);

                ExportNodoCsv exportNodoCsv = new ExportNodoCsv(NOM_COLUMNAS, nodoMglFacade, cmtFiltroConsultaNodosDto, limitIni, limit, total, this);
                exportNodoCsv.run();


        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        }
        return "";
    }

     public String consultaNombreCentro(BigDecimal centro){
        
        String nomCentro= "";
        
        try {
            GeograficoPoliticoMgl centroPo = geograficoPoliticoMglFacadeLocal.findById(centro);
            if(centroPo.getGpoId() != null){
               nomCentro = centroPo.getGpoNombre();
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en consultaNombreCentro: "+e.getMessage()+"", e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en consultaNombreCentro: "+e.getMessage()+"", e, LOGGER);

        }
        return nomCentro;
    }
    
    public String consultaNombreCiudad(BigDecimal centro){
        
        String nomCiudad= "";
        
        try {
            GeograficoPoliticoMgl centroPo = geograficoPoliticoMglFacadeLocal.findById(centro);
            if(centroPo.getGpoId() != null){
                GeograficoPoliticoMgl ciudad= geograficoPoliticoMglFacadeLocal.findById(centroPo.getGeoGpoId());
                if(ciudad.getGpoId() != null){
                  nomCiudad=ciudad.getGpoNombre();
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en consultaNombreCiudad : "+e.getMessage()+"", e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en consultaNombreCiudad : "+e.getMessage()+"", e, LOGGER);

        }
        return nomCiudad;
    }
    public String consultaNombreDepto(BigDecimal centro) {

        String nomDepto = "";

        try {
            GeograficoPoliticoMgl centroPo = geograficoPoliticoMglFacadeLocal.findById(centro);
            if (centroPo.getGpoId() != null) {
                GeograficoPoliticoMgl ciudad = geograficoPoliticoMglFacadeLocal.findById(centroPo.getGeoGpoId());
                if (ciudad.getGpoId() != null) {
                    GeograficoPoliticoMgl depto = geograficoPoliticoMglFacadeLocal.findById(ciudad.getGeoGpoId());
                    if (depto.getGpoId() != null) {
                        nomDepto = depto.getGpoNombre();
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en consultaNombreDepto. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en consultaNombreDepto. ", e, LOGGER);
        }
        return nomDepto;
    }

     /**
     * @Autor: Gildardo Mora
     * consulta el valor del SDS/BTS BACKUP registrado en el campoAdicional5
     *
     * @param codigoNodo
     * @return
     */
    public String consultaCampoSdsBtsBackup(String codigoNodo){
        String campoAdicional5="";
        try {
          NodoMgl  nodoMglC= nodoMglFacade.findByCodigo(codigoNodo);
            if (nodoMglC!=null) {
                campoAdicional5=nodoMglC.getNodCampoAdicional5();
            }
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Error en consultaCampoSdsBtsBackup. ", e, LOGGER);

        } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Error en consultaCampoSdsBtsBackup. ", e, LOGGER);
        }
        return campoAdicional5;
    }

    public boolean validarNuevoNodoRol() {
        return validarEdicion(VALIDARNUEVONODOROLND);
    }

    public boolean validarExportarRol() {
        return validarEdicion(VALIDAREXPORTARROLND);
    }

    public boolean validarOpcionVerRol() {
        return validarEdicion(VALIDAROPCIONVERROLND);
    }

    private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario,
                    ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al validarEdicion en NodoMglBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * Método para obtener el listado de ancho de banda
     */
    public void consultarAnchoBanda() {
        try {
            final FiltroConjsultaBasicasDto filtroConjsultaBasicasDto = new FiltroConjsultaBasicasDto();
            filtroConjsultaBasicasDto.setIdTipoBasica(new BigDecimal(ANCHO_BANDA));
            anchoBandaList = cmtBasicaMglFacadeLocal.findByFiltro(filtroConjsultaBasicasDto);
        } catch (ApplicationException e) {
            String msn = "Se presenta error al filtrar los registros: " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en TablasBasicasMglBean: consultarByTipoTablaBasica(). " + e.getMessage(), e, LOGGER);
        }
    }


    public NodoMgl getNodoMgl() {
        return nodoMgl;
    }

    public void setNodoMgl(NodoMgl nodoMgl) {
        this.nodoMgl = nodoMgl;
    }

    public List<NodoMgl> getNodoMglList() {
        return nodoMglList;
    }

    public void setNodoMglList(List<NodoMgl> NodoMglList) {
        this.nodoMglList = NodoMglList;
    }

    public String getIdSqlSelected() {
        return idSqlSelected;
    }

    public void setIdSqlSelected(String idSqlSelected) {
        this.idSqlSelected = idSqlSelected;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public String getGeograficoPoliticoMgl() {
        return geograficoPoliticoMgl;
    }

    public void setGeograficoPoliticoMgl(String geograficoPoliticoMgl) {
        this.geograficoPoliticoMgl = geograficoPoliticoMgl;
    }

    public List<GeograficoPoliticoMgl> getListGeograficoPoliticoMgl() {
        return listGeograficoPoliticoMgl;
    }

    public void setListGeograficoPoliticoMgl(List<GeograficoPoliticoMgl> listGeograficoPoliticoMgl) {
        this.listGeograficoPoliticoMgl = listGeograficoPoliticoMgl;
    }

    public String getGeograficoMgl() {
        return geograficoMgl;
    }

    public void setGeograficoMgl(String geograficoMgl) {
        this.geograficoMgl = geograficoMgl;
    }

    public List<GeograficoPoliticoMgl> getListGeograficoMgl() {
        return listGeograficoMgl;
    }

    public void setListGeograficoMgl(List<GeograficoPoliticoMgl> listGeograficoMgl) {
        this.listGeograficoMgl = listGeograficoMgl;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public void setPageActual(String pageActual) {
        this.pageActual = pageActual;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public List<CmtBasicaMgl> getListBasica() {
        return listBasica;
    }

    public void setListBasica(List<CmtBasicaMgl> listBasica) {
        this.listBasica = listBasica;
    }

    public BigDecimal getNodoTipo() {
        return nodoTipo;
    }

    public void setNodoTipo(BigDecimal nodoTipo) {
        this.nodoTipo = nodoTipo;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public CmtFiltroConsultaNodosDto getCmtFiltroConsultaNodosDto() {
        return cmtFiltroConsultaNodosDto;
    }

    public void setCmtFiltroConsultaNodosDto(CmtFiltroConsultaNodosDto cmtFiltroConsultaNodosDto) {
        this.cmtFiltroConsultaNodosDto = cmtFiltroConsultaNodosDto;
    }

    public String getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(String centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public List<GeograficoPoliticoMgl> getListCentrosPoblados() {
        return listCentrosPoblados;
    }

    public void setListCentrosPoblados(List<GeograficoPoliticoMgl> listCentrosPoblados) {
        this.listCentrosPoblados = listCentrosPoblados;
    }

    public List<CmtBasicaMgl> getListBasicaDivicional() {
        return listBasicaDivicional;
    }

    public void setListBasicaDivicional(List<CmtBasicaMgl> listBasicaDivicional) {
        this.listBasicaDivicional = listBasicaDivicional;
    }

    public List<CmtBasicaMgl> getListBasicaDistrito() {
        return listBasicaDistrito;
    }

    public void setListBasicaDistrito(List<CmtBasicaMgl> listBasicaDistrito) {
        this.listBasicaDistrito = listBasicaDistrito;
    }

    public List<CmtBasicaMgl> getListBasicaZona() {
        return listBasicaZona;
    }

    public void setListBasicaZona(List<CmtBasicaMgl> listBasicaZona) {
        this.listBasicaZona = listBasicaZona;
    }

    public List<CmtBasicaMgl> getListBasicaArea() {
        return listBasicaArea;
    }

    public void setListBasicaArea(List<CmtBasicaMgl> listBasicaArea) {
        this.listBasicaArea = listBasicaArea;
    }

    public List<CmtBasicaMgl> getListBasicaUnidadGestion() {
        return listBasicaUnidadGestion;
    }

    public void setListBasicaUnidadGestion(List<CmtBasicaMgl> listBasicaUnidadGestion) {
        this.listBasicaUnidadGestion = listBasicaUnidadGestion;
    }

    public BigDecimal getDivisional() {
        return divisional;
    }

    public void setDivisional(BigDecimal divisional) {
        this.divisional = divisional;
    }

    public BigDecimal getDistrito() {
        return distrito;
    }

    public void setDistrito(BigDecimal distrito) {
        this.distrito = distrito;
    }

    public BigDecimal getZona() {
        return zona;
    }

    public void setZona(BigDecimal zona) {
        this.zona = zona;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getUnidadGestion() {
        return unidadGestion;
    }

    public void setUnidadGestion(BigDecimal unidadGestion) {
        this.unidadGestion = unidadGestion;
    }

    public List<CmtBasicaMgl> getListBasicaAliados() {
        return listBasicaAliados;
    }

    public void setListBasicaAliados(List<CmtBasicaMgl> listBasicaAliados) {
        this.listBasicaAliados = listBasicaAliados;
    }

    public List<CmtBasicaMgl> getListBasicaEstadoNodos() {
        return listBasicaEstadoNodos;
    }

    public void setListBasicaEstadoNodos(List<CmtBasicaMgl> listBasicaEstadoNodos) {
        this.listBasicaEstadoNodos = listBasicaEstadoNodos;
    }

    public BigDecimal getAliado() {
        return aliado;
    }

    public void setAliado(BigDecimal aliado) {
        this.aliado = aliado;
    }

    public BigDecimal getEstado() {
        return estado;
    }

    public void setEstado(BigDecimal estado) {
        this.estado = estado;
    }

    public String getUsuarioVT() {
        return usuarioVT;
    }
    
    public String getCodigoComunidadRR() {
        return codigoComunidadRR;
    }

    public void setCodigoComunidadRR(String codigoComunidadRR) {
        this.codigoComunidadRR = codigoComunidadRR;
    }

    public boolean isBotonExport() {
        return botonExport;
    }

    public void setBotonExport(boolean botonExport) {
        this.botonExport = botonExport;
    }

    public String getFactibilidad() {
        return factibilidad;
    }

    public void setFactibilidad(String factibilidad) {
        this.factibilidad = factibilidad;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * get field
     *
     * @return anchoBanda
     */
    public String getAnchoBanda() {
        return this.anchoBanda;
    }

    /**
     * set field
     *
     * @param anchoBanda
     */
    public void setAnchoBanda(String anchoBanda) {
        this.anchoBanda = anchoBanda;
    }

    /**
     * get field
     *
     * @return anchoBAndaList
     */
    public List<CmtBasicaMgl> getAnchoBandaList() {
        return this.anchoBandaList;
    }

    /**
     * set field
     *
     * @param anchoBAndaList
     */
    public void setAnchoBAndaList(List<CmtBasicaMgl> anchoBandaList) {
        this.anchoBandaList = anchoBandaList;
    }
    
    public List<CmtBasicaMgl> getListBasicaOpera() {
        return listBasicaOpera;
    }

    public void setListBasicaOpera(List<CmtBasicaMgl> listBasicaOpera) {
        this.listBasicaOpera = listBasicaOpera;
    }
}
