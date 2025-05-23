package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
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
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Parzifal de León
 */
@ManagedBean
@ViewScoped
public class GeograficoPoliticoMglBean {

    private List<GeograficoPoliticoMgl> geoPoliticoList;
    private GeograficoPoliticoMgl geoPolitico;
    private String idSqlSelected;
    private String message;
    private String nombre;
    private String inicio;
    private BigDecimal idPais;
    private BigDecimal idDepartamento;
    private BigDecimal idCiudad;
    /**
     * Paginacion Tabla
     */
    private HtmlDataTable dataTable = new HtmlDataTable();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    /**
     *
     */
    private List<GeograficoPoliticoMgl> dptoList;
    private List<GeograficoPoliticoMgl> ciudadList;
    private List<GeograficoPoliticoMgl> paisList;
    private boolean mostrarCiudad = false;
    private boolean mostrarCodigoZip = true;
    private boolean mostrarMultiorigen = false;
    private boolean mostrarCodTipoDir = false;
    private boolean mostrarNombre = true;
    private boolean mostrarPais = true;
    private boolean mostrarManzana = true;
    private boolean mostrarEliminar = true;
    private boolean mostrarActualizar = true;
    private boolean consultaTotal = true;

    private boolean mostrarDepartamento;
    private boolean deshabilitar = false;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geoPoliticoFacade;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    private static final Logger LOGGER = LogManager.getLogger(GeograficoPoliticoMglBean.class);
    private String usuarioVT = null;
    private int perfilVt = 0;
    private int usuarioID = 0;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private SecurityLogin securityLogin;
    
    
    //Opciones agregadas para Rol
    private final String ACDNEWBTN = "ACDNEWBTN";
    //Opciones agregadas para Rol
    private final String ACDFILBTN = "ACDFILBTN";
     //Opciones agregadas para Rol
    private final String ACDVTDBTN = "ACDVTDBTN";
     //Opciones agregadas para Rol
    private final String ACDIDBTN = "ACDIDBTN";

    public GeograficoPoliticoMglBean() {
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

                if (usuarioVT == null) {
                    session.getAttribute("usuarioM");
                    usuarioVT = (String) session.getAttribute("usuarioM");
                } else {
                    session = (HttpSession) facesContext.getExternalContext().getSession(false);
                    session.setAttribute("usuarioM", usuarioVT);
                }
                usuarioID = securityLogin.getPerfilUsuario();
                if (usuarioID == 0) {
                    session.getAttribute("usuarioIDM");
                    usuarioID = Integer.parseInt((String) session.getAttribute("usuarioIDM"));
                } else {
                    session = (HttpSession) facesContext.getExternalContext().getSession(false);
                    session.setAttribute("usuarioIDM", usuarioID);
                }
            
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en GeograficoPoliticoMglBean. ", e, LOGGER);
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en GeograficoPoliticoMglBean. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en GeograficoPoliticoMglBean. ", e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {

            Object geoPoliticoFlash = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("gpo");
            deshabilitar = false;
            geoPoliticoFacade.setUser(usuarioVT, perfilVt);
            if (geoPoliticoFlash != null) {
                geoPolitico = (GeograficoPoliticoMgl) geoPoliticoFlash;
                if (geoPolitico.getGpoNombre() != null && !geoPolitico.getGpoNombre().isEmpty()) {
                    deshabilitar = true;
                }

                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("gpo", null);

            }

            if (dptoList == null || dptoList.isEmpty()) {
                dptoList = geoPoliticoFacade.findDptos();
            }

            if (ciudadList == null || ciudadList.isEmpty()) {
                ciudadList = geoPoliticoFacade.findAllCiudades();
            }

            if (paisList == null || paisList.isEmpty()) {
                paisList = geoPoliticoFacade.findPaises();
            }

            if (geoPoliticoList == null || geoPoliticoList.isEmpty()) {
                geoPoliticoList = geoPoliticoFacade.findCiudadesDepartamentos();
            }

            if (geoPolitico != null) {

                if (geoPolitico.getGpoTipo() != null) {

                    if (geoPolitico.getGpoTipo().equals("CIUDAD")) {
                        mostrarCiudad = false;
                        mostrarCodigoZip = true;
                        mostrarDepartamento = true;
                        mostrarMultiorigen = false;
                        mostrarCodTipoDir = false;
                        mostrarNombre = true;
                        mostrarPais = false;
                        mostrarManzana = true;
                        idCiudad = geoPolitico.getGpoId();
                        idDepartamento=geoPolitico.getGeoGpoId();
                    }

                    if (geoPolitico.getGpoTipo().equals("DEPARTAMENTO")) {
                        mostrarMultiorigen = false;
                        mostrarCodigoZip = true;
                        mostrarDepartamento = false;
                        mostrarCiudad = false;
                        mostrarCodTipoDir = false;
                        mostrarNombre = true;
                        mostrarPais = true;
                        mostrarManzana = true;
                        idDepartamento=geoPolitico.getGpoId();
                        idPais = geoPolitico.getGeoGpoId();

                    }
                    if (geoPolitico.getGpoTipo().equals("CENTRO POBLADO")) {
                        mostrarCodigoZip = true;
                        mostrarDepartamento = true;
                        mostrarCiudad = true;
                        mostrarMultiorigen = true;
                        mostrarCodTipoDir = true;
                        mostrarNombre = true;
                        mostrarPais = false;
                        mostrarManzana = true;
                        idCiudad = geoPolitico.getGeoGpoId();
                        if (idCiudad != null) {
                            GeograficoPoliticoMgl geograficoPoliticoMglDpto = geoPoliticoFacade.findById(idCiudad);
                            if (geograficoPoliticoMglDpto != null && geograficoPoliticoMglDpto.getGpoId() != null) {
                                idDepartamento = geograficoPoliticoMglDpto.getGeoGpoId();
                            }
                        }
                    }
                }

            }

        } catch (ApplicationException e) {
            LOGGER.error("Error en init. " +e.getMessage(), e);      
        } catch (Exception e) {
            LOGGER.error("Error en init. " +e.getMessage(), e);      
        }
    }

    public void actualizarGeoMgl() throws ApplicationException {
        try {
            if (geoPolitico.getGpoTipo().equals("DEPARTAMENTO")) {
                if (!validarDepartamento()) {
                    return;
                }
            }

            if (geoPolitico.getGpoTipo().equals("CIUDAD")) {
                if (!validarCiudad()) {
                    return;
                }
            }

            if (geoPolitico.getGpoTipo().equals("CENTRO POBLADO")) {
                if (!validarCentroPoblado()) {
                    return;
                }
            }
            geoPolitico.setFechaEdicion(new Date());
            geoPolitico.setUsuarioEdicion(usuarioVT);
            geoPoliticoFacade.updateGeograficoPolitico(geoPolitico, usuarioVT, perfilVt);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Modificado con éxito", null));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en actualizarGeoMgl. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en actualizarGeoMgl. ", e, LOGGER);
        }
    }

    public void crearGeoMgl() throws ApplicationException {
        try {
            if (geoPolitico.getGpoTipo().equals("DEPARTAMENTO")) {
                if (idPais != null && !idPais.toString().isEmpty()) {
                    geoPolitico.setGeoGpoId(idPais);
                }
                if (!validarDepartamento()) {
                    return;
                }
            }

            if (geoPolitico.getGpoTipo().equals("CIUDAD")) {
                if (idDepartamento != null && !idDepartamento.toString().isEmpty()) {
                    geoPolitico.setGeoGpoId(idDepartamento);
                }
                if (!validarCiudad()) {
                    return;
                }
            }

            if (geoPolitico.getGpoTipo().equals("CENTRO POBLADO")) {
                if (idCiudad != null && !idCiudad.toString().isEmpty()) {
                    geoPolitico.setGeoGpoId(idCiudad);
                }
                if (!validarCentroPoblado()) {
                    return;
                }
            }

            geoPolitico.setGpoId(null);
            geoPolitico.setFechaCreacion(new Date());
            geoPolitico.setEstadoRegistro(1);
            geoPolitico.setFechaEdicion(new Date());
            geoPolitico.setUsuarioCreacion(usuarioVT);
            geoPolitico.setUsuarioEdicion(usuarioVT);
            geoPoliticoFacade.setUser(usuarioVT, usuarioID);
            geoPoliticoFacade.create(geoPolitico);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Creado con éxito", null));
            mostrarEliminar = true;
            mostrarActualizar = true;
            deshabilitar=true;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en crearGeoMgl. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en crearGeoMgl. ", e, LOGGER);
        }
    }

    public void eliminarGeoMgl() {
        try {
            geoPoliticoFacade.setUser(usuarioVT, usuarioID);
            geoPoliticoFacade.delete(geoPolitico);
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro Eliminado con éxito", null));
            mostrarEliminar = false;
            mostrarActualizar = false;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en eliminarGeoMgl. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en eliminarGeoMgl. ", e, LOGGER);
        }
    }

    public boolean validarDepartamento() {
        boolean valido = true;

        if (geoPolitico.getGpoTipo() == null || geoPolitico.getGpoTipo().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Tipo es requerido", null));
            valido = false;
        }

        if (geoPolitico.getGpoNombre() == null || geoPolitico.getGpoNombre().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Nombre es requerido", null));
            valido = false;
        }
     
        if (geoPolitico.getGeoCodigoDane() == null || geoPolitico.getGeoCodigoDane().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Código Dane es requerido", null));
            valido = false;
        }
        
        return valido;
    }

    public boolean validarCiudad() {
        boolean valido = true;

        if (geoPolitico.getGpoNombre() == null || geoPolitico.getGpoNombre().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Nombre es requerido", null));
            valido = false;
        }

        if (geoPolitico.getGpoTipo() == null || geoPolitico.getGpoTipo().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Tipo es requerido", null));
            valido = false;
        }      

        if (geoPolitico.getGeoCodigoDane() == null || geoPolitico.getGeoCodigoDane().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Código Dane es requerido", null));
            valido = false;
        }

        return valido;
    }

    public boolean validarCentroPoblado() {

        boolean valido = true;

        if (geoPolitico.getGpoTipo() == null || geoPolitico.getGpoTipo().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Tipo es requerido", null));
            valido = false;
        }

        if (geoPolitico.getGpoNombre() == null || geoPolitico.getGpoNombre().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Nombre es requerido", null));
            valido = false;
        }      

        if (geoPolitico.getGpoMultiorigen() == null || geoPolitico.getGpoMultiorigen().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Multiorigen es requerido", null));
            valido = false;
        }

        if (geoPolitico.getGpoCodTipoDireccion() == null || geoPolitico.getGpoCodTipoDireccion().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Código Tipo Dirección: es requerido", null));
            valido = false;
        }

        if (geoPolitico.getGeoCodigoDane() == null || geoPolitico.getGeoCodigoDane().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo Código Dane es requerido", null));
            valido = false;
        }
        
        return valido;
    }

    public void consultarCiudades() {
        try {
            if (idDepartamento != null && !idDepartamento.toString().isEmpty()) {
                ciudadList = geoPoliticoFacade.findCiudades(idDepartamento);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en consultarCiudades. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en consultarCiudades. ", e, LOGGER);
        }
    }

    public void buscarCiudadesDepartamentosByNombre() {
        try {
            geoPoliticoList = geoPoliticoFacade.findNombre(getNombre());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en buscarCiudadesDepartamentosByNombre. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en buscarCiudadesDepartamentosByNombre. ", e, LOGGER);
        }
        pageFirst();
    }

    public void buscarCiudadesDepartamentosTodas() {
        try {
            geoPoliticoList = geoPoliticoFacade.findCiudadesDepartamentos();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en buscarCiudadesDepartamentosTodas. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en buscarCiudadesDepartamentosTodas. ", e, LOGGER);
        }
        pageFirst();
    }

    public void mostrarTipo() {

        if (geoPolitico.getGpoTipo().equals("CIUDAD")) {
            mostrarCiudad = false;
            mostrarCodigoZip = true;
            mostrarDepartamento = true;
            mostrarMultiorigen = false;
            mostrarCodTipoDir = false;
            mostrarNombre = true;
            mostrarPais = false;
            mostrarManzana = true;
        }

        if (geoPolitico.getGpoTipo().equals("DEPARTAMENTO")) {
            mostrarMultiorigen = false;
            mostrarCodigoZip = true;
            mostrarDepartamento = false;
            mostrarCiudad = false;
            mostrarCodTipoDir = false;
            mostrarNombre = true;
            mostrarPais = true;
            mostrarManzana = true;
        }
        if (geoPolitico.getGpoTipo().equals("CENTRO POBLADO")) {
            mostrarCodigoZip = true;
            mostrarDepartamento = true;
            mostrarCiudad = true;
            mostrarMultiorigen = true;
            mostrarCodTipoDir = true;
            mostrarNombre = true;
            mostrarPais = false;
            mostrarManzana = true;
            
        }
    }
    
        public void filtrarCiudades() {

        if (geoPolitico.getGpoTipo().equals("CIUDAD")) {
            mostrarCiudad = false;
            mostrarCodigoZip = true;
            mostrarDepartamento = true;
            mostrarMultiorigen = false;
            mostrarCodTipoDir = false;
            mostrarNombre = true;
            mostrarPais = false;
            mostrarManzana = true;
        }
    }

    public boolean getMostrar() {
        return mostrarCiudad;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrarCiudad = mostrar;
    }

    public boolean getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(boolean deshabilitar) {
        this.deshabilitar = deshabilitar;
    }

    public String nuevoGeoMgl() {
        geoPolitico = new GeograficoPoliticoMgl();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("gpo", geoPolitico);
        return "geografico-politico-detalle";
    }

    public String goActualizar() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("gpo", geoPolitico);
        GeograficoPoliticoMgl gio = geoPolitico;
        return "geografico-politico-detalle";
    }
    
    public String retornaNivelSuperior(BigDecimal geoPadre) throws ApplicationException{
        
        String nombrePadre = "";

        if (geoPadre != null && !geoPadre.toString().isEmpty()) {
            GeograficoPoliticoMgl geograficoPoliticoPadreMgl
                    = geoPoliticoFacade.findById(geoPadre);
            if (geograficoPoliticoPadreMgl != null) {
                nombrePadre = geograficoPoliticoPadreMgl.getGpoNombre();
            }
        }

        return nombrePadre;
    }

    public List<GeograficoPoliticoMgl> getGeoPoliticoList() {
        return geoPoliticoList;
    }

    public void setGeoPoliticoList(List<GeograficoPoliticoMgl> geoPoliticoList) {
        this.geoPoliticoList = geoPoliticoList;
    }

    public String getIdSqlSelected() {
        return idSqlSelected;
    }

    public void setIdSqlSelected(String idSqlSelected) {
        this.idSqlSelected = idSqlSelected;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public GeograficoPoliticoMgl getGeoPolitico() {
        return geoPolitico;
    }

    public void setGeoPolitico(GeograficoPoliticoMgl geoPolitico) {
        this.geoPolitico = geoPolitico;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GeograficoPoliticoMgl> getDptoList() {
        return dptoList;
    }

    public void setDptoList(List<GeograficoPoliticoMgl> dptoList) {
        this.dptoList = dptoList;
    }

    public List<GeograficoPoliticoMgl> getCiudadList() {
        return ciudadList;
    }

    public void setCiudadList(List<GeograficoPoliticoMgl> ciudadList) {
        this.ciudadList = ciudadList;
    }

    public boolean getMostrarMultiorigen() {
        return mostrarMultiorigen;
    }

    public void setMostrarMultiorigen(boolean mostrarMultiorigen) {
        this.mostrarMultiorigen = mostrarMultiorigen;
    }

    // Actions Paging--------------------------------------------------------------------------
    public void pageFirst() {
        dataTable.setFirst(0);
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public void pagePrevious() {
        dataTable.setFirst(dataTable.getFirst() - dataTable.getRows());
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public void pageNext() {
        dataTable.setFirst(dataTable.getFirst() + dataTable.getRows());
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public void pageLast() {
        int count = dataTable.getRowCount();
        int rows = dataTable.getRows();
        dataTable.setFirst(count - ((count % rows != 0) ? count % rows : rows));
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public String getPageActual() {
        int count = dataTable.getRowCount();
        int rows = dataTable.getRows();
        int totalPaginas = (count % rows != 0) ? (count / rows) + 1 : count / rows;
        int actual = (dataTable.getFirst() / rows) + 1;

        paginaList = new ArrayList<Integer>();

        for (int i = 1; i <= totalPaginas; i++) {
            paginaList.add(i);
        }

        pageActual = actual + " de " + totalPaginas;

        if (numPagina == null) {
            numPagina = "1";
        }
        return pageActual;
    }

    public void irPagina(ValueChangeEvent event) {
        if(event != null && event.getNewValue() != null){
        String value = event.getNewValue().toString();
        dataTable.setFirst((new Integer(value) - 1) * dataTable.getRows());
        }
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

    public String getInicio() {
        this.inicio = "<<";
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public boolean isMostrarCiudad() {
        return mostrarCiudad;
    }

    public void setMostrarCiudad(boolean mostrarCiudad) {
        this.mostrarCiudad = mostrarCiudad;
    }

    public boolean isMostrarCodigoZip() {
        return mostrarCodigoZip;
    }

    public void setMostrarCodigoZip(boolean mostrarCodigoZip) {
        this.mostrarCodigoZip = mostrarCodigoZip;
    }

    public boolean isMostrarCodTipoDir() {
        return mostrarCodTipoDir;
    }

    public void setMostrarCodTipoDir(boolean mostrarCodTipoDir) {
        this.mostrarCodTipoDir = mostrarCodTipoDir;
    }

    public boolean isMostrarNombre() {
        return mostrarNombre;
    }

    public void setMostrarNombre(boolean mostrarNombre) {
        this.mostrarNombre = mostrarNombre;
    }

    public boolean isMostrarPais() {
        return mostrarPais;
    }

    public void setMostrarPais(boolean mostrarPais) {
        this.mostrarPais = mostrarPais;
    }

    public boolean isMostrarManzana() {
        return mostrarManzana;
    }

    public void setMostrarManzana(boolean mostrarManzana) {
        this.mostrarManzana = mostrarManzana;
    }

    public boolean isMostrarEliminar() {
        return mostrarEliminar;
    }

    public void setMostrarEliminar(boolean mostrarEliminar) {
        this.mostrarEliminar = mostrarEliminar;
    }

    public boolean isMostrarActualizar() {
        return mostrarActualizar;
    }

    public void setMostrarActualizar(boolean mostrarActualizar) {
        this.mostrarActualizar = mostrarActualizar;
    }

    public boolean isConsultaTotal() {
        return consultaTotal;
    }

    public void setConsultaTotal(boolean consultaTotal) {
        this.consultaTotal = consultaTotal;
    }

    public boolean isMostrarDepartamento() {
        return mostrarDepartamento;
    }

    public void setMostrarDepartamento(boolean mostrarDepartamento) {
        this.mostrarDepartamento = mostrarDepartamento;
    }

    public GeograficoPoliticoMglFacadeLocal getGeoPoliticoFacade() {
        return geoPoliticoFacade;
    }

    public void setGeoPoliticoFacade(GeograficoPoliticoMglFacadeLocal geoPoliticoFacade) {
        this.geoPoliticoFacade = geoPoliticoFacade;
    }

    public String getUsuarioVT() {
        return usuarioVT;
    }

    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public List<GeograficoPoliticoMgl> getPaisList() {
        return paisList;
    }

    public void setPaisList(List<GeograficoPoliticoMgl> paisList) {
        this.paisList = paisList;
    }

    public BigDecimal getIdPais() {
        return idPais;
    }

    public void setIdPais(BigDecimal idPais) {
        this.idPais = idPais;
    }

    public BigDecimal getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(BigDecimal idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public BigDecimal getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(BigDecimal idCiudad) {
        this.idCiudad = idCiudad;
    }
     

        // Validar Opciones por Rol
    
    public boolean validarOpcionNuevo() {
        return validarEdicionRol(ACDNEWBTN);
    }
    
    public boolean validarOpcionFiltrar() {
        return validarEdicionRol(ACDFILBTN);
    }
    public boolean validarOpcionVerTodas() {
        return validarEdicionRol(ACDVTDBTN);
    }
    public boolean validarOpcionlinkIdentificador() {
        return validarEdicionRol(ACDIDBTN);
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
