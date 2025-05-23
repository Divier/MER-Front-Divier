package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DireccionMglFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.TipoDireccionMglFacadeLocal;
import co.com.claro.mgl.facade.UbicacionMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.TipoDireccionMgl;
import co.com.claro.mgl.jpa.entities.UbicacionMgl;
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
import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "direccionMglBean")
@ViewScoped
public class DireccionMglBean {

    private String usuarioVT = null;
    private DireccionMgl direccionMgl = null;
    private List<DireccionMgl> direccionMglList;
    private String idSqlSelected;
    private String tipoDireccion;
    private String ubicacion;
    private String geoPolitico;
    private String geoPoliticoCiudad;
    private String direccion;
    private List<GeograficoPoliticoMgl> dptoList;
    private List<GeograficoPoliticoMgl> ciudadList;
    public List<TipoDireccionMgl> listTipoDireccionMgl = null;
    public List<UbicacionMgl> listUbicacionMgl = null;
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
    @EJB
    private DireccionMglFacadeLocal direccionMglFacade;
    @EJB
    private TipoDireccionMglFacadeLocal tipoDireccionMglFacadeLocal;
    @EJB
    private UbicacionMglFacadeLocal ubicacionMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private String message;
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(DireccionMglBean.class);

    /**
     * Creates a new instance of direccionMglBean
     */
    public DireccionMglBean() {
        try {
            try {
                SecurityLogin securityLogin = new SecurityLogin(facesContext);
                if (!securityLogin.isLogin()) {
                    if (!response.isCommitted()) {
                        response.sendRedirect(securityLogin.redireccionarLogin());
                    }
                    return;
                }
                usuarioVT = securityLogin.getLoginUser();
                if (usuarioVT == null) {
                    session.getAttribute("usuarioM");
                    usuarioVT = (String) session.getAttribute("usuarioM");
                } else {
                    session = (HttpSession) facesContext.getExternalContext().getSession(false);
                    session.setAttribute("usuarioM", usuarioVT);
                }
            } catch (IOException e) {
                FacesUtil.mostrarMensajeError("Error en DireccionMglBean. ", e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error en DireccionMglBean. ", e, LOGGER);
            }

            DireccionMgl iddireccionMgl = (DireccionMgl) session.getAttribute("iddireccionMgl");
            session.removeAttribute("iddireccionMgl");
            DireccionMgl nd = direccionMgl;
            if (iddireccionMgl != null) {
                direccionMgl = iddireccionMgl;
                irdireccionMgl();
            } else {
                direccionMgl = new DireccionMgl();
            }
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en DireccionMglBean. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en DireccionMglBean. ", e, LOGGER);
        }
    }

    @PostConstruct
    public void fillSqlList() {
        try {
            listTipoDireccionMgl = new ArrayList<TipoDireccionMgl>();
            listTipoDireccionMgl = tipoDireccionMglFacadeLocal.findAll();
            listUbicacionMgl = new ArrayList<UbicacionMgl>();
            listUbicacionMgl = ubicacionMglFacadeLocal.findAll();

            dptoList = new ArrayList<GeograficoPoliticoMgl>();
            dptoList = geograficoPoliticoMglFacadeLocal.findDptos();
        } catch (ApplicationException e) {
            LOGGER.error("Error en fillSqlList. " +e.getMessage(), e);   
        } catch (Exception e) {
            LOGGER.error("Error en fillSqlList. " +e.getMessage(), e);   
        }
    }

    public String goActualizar() {
        try {
            direccionMgl = (DireccionMgl) dataTable.getRowData();
            session.setAttribute("iddireccionMgl", direccionMgl);
            return "direccionMglView";
        } catch (FacesException e) {
            FacesUtil.mostrarMensajeError("Error en goActualizar. ", e, LOGGER);
            if (session == null) {
                return "direccionMglListView";
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en goActualizar. ", e, LOGGER);
            if (session == null) {
                return "direccionMglListView";
            }
        }
        return null;
    }

    public String irdireccionMgl() {
        try {
            
            tipoDireccion = direccionMgl.getTipoDirObj() == null ? "0" : direccionMgl.getTipoDirObj().getTdiId().toString();
            ubicacion = direccionMgl.getUbicacion().getUbiId() == null ? "0" : direccionMgl.getUbicacion().getUbiId().toString();
            return "direccionMglView";
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en irdireccionMgl. ", e, LOGGER);
            return null;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irdireccionMgl. ", e, LOGGER);
            return null;
        }
    }

    public String nuevodireccionMgl() {
        direccionMgl = null;
        direccionMgl = new DireccionMgl();
        direccionMgl.setDirId(BigDecimal.ZERO);
        return "direccionMglView";
    }

    public void creardireccionMgl() {
        try {
            direccionMgl.setDirId(BigDecimal.ZERO);
            direccionMgl.setFechaCreacion(new Date());
            direccionMgl.setUsuarioCreacion(usuarioVT);
            TipoDireccionMgl tipoDIr = new TipoDireccionMgl();
            tipoDIr.setTdiId(new BigDecimal(tipoDireccion));
            direccionMgl.setTipoDirObj(tipoDIr);
            if (direccionMgl.getTipoDirObj().getTdiId().equals(BigDecimal.ZERO)) {
                message = "Campo Tipo dirección conexión es requerido";
                return;
            }
            UbicacionMgl ubicacionMgl = new UbicacionMgl();
            ubicacionMgl.setUbiId(new BigDecimal(ubicacion));
            direccionMgl.setUbicacion(ubicacionMgl);
            if (direccionMgl.getUbicacion().getUbiId().equals(BigDecimal.ZERO)) {
                message = "Campo Ubicación es requerido";
                return;
            }

            direccionMglFacade.create(direccionMgl);
            message = "Proceso exitoso";
            session.setAttribute("message", message);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en creardireccionMgl. ", e, LOGGER);
            message = "Proceso falló";
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en creardireccionMgl. ", e, LOGGER);
            message = "Proceso falló";
            session.setAttribute("message", message);
        }
    }

    public void actualizardireccionMgl() {
        try {
            direccionMgl.setUsuarioEdicion((String) session.getAttribute("usuarioM"));
            direccionMgl.setFechaEdicion(new Date());
            TipoDireccionMgl tipoDir = new TipoDireccionMgl();
            tipoDir.setTdiId(new BigDecimal(tipoDireccion));
            direccionMgl.setTipoDirObj(tipoDir);
            if (direccionMgl.getTipoDirObj().getTdiId().equals(BigDecimal.ZERO)) {
                message = "Campo Tipo dirección conexión es requerido";
                return;
            }
            UbicacionMgl ubicacionMgl = new UbicacionMgl();
            ubicacionMgl.setUbiId(new BigDecimal(ubicacion));
            direccionMgl.setUbicacion(ubicacionMgl);

            if (direccionMgl.getUbicacion().getUbiId().equals(BigDecimal.ZERO)) {
                message = "Campo Ubicación es requerido";
                return;
            }
            direccionMglFacade.update(direccionMgl);
            message = "Proceso exitoso";
            session.setAttribute("message", message);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en actualizardireccionMgl. ", e, LOGGER);            
            message = "Proceso falló";
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en actualizardireccionMgl. ", e, LOGGER);            
            message = "Proceso falló";
            session.setAttribute("message", message);
        }
    }

    public void eliminardireccionMgl() {
        try {
            direccionMglFacade.delete(direccionMgl);
            message = "Proceso exitoso";
            session.setAttribute("message", message);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en eliminardireccionMgl. ", e, LOGGER);
            message = "Proceso falló";
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en eliminardireccionMgl. ", e, LOGGER);
            message = "Proceso falló";
            session.setAttribute("message", message);
        }
    }

    public void consultarCiudades() {
        try {
            ciudadList = geograficoPoliticoMglFacadeLocal.findCiudades(new BigDecimal(geoPolitico));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en consultarCiudades. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en consultarCiudades. ", e, LOGGER);
        }
    }

    public void buscarByDireccion() {
        try {
            if (new BigDecimal(geoPolitico).equals(BigDecimal.ZERO)) {
                message = "Seleccione un Departamento";
                return;
            }

            if (new BigDecimal(geoPoliticoCiudad).equals(BigDecimal.ZERO)) {
                message = "Seleccione una Ciudad";
                return;
            }

            if (!(getDireccion() != null && !getDireccion().trim().isEmpty())) {
                message = "Digite un valor para Dirección";
                return;
            }
            if (getDireccion().trim().length() < 9) {
                message = "El valor de Dirección debe ser de minimo 8 caracteres";
                return;
            }
            direccionMglList = direccionMglFacade.findByDireccion(getDireccion(), new BigDecimal(geoPoliticoCiudad));
        } catch (ApplicationException e) {
            LOGGER.error("Error en buscarByDireccion. " +e.getMessage(), e);   
        } catch (Exception e) {
            LOGGER.error("Error en buscarByDireccion. " +e.getMessage(), e);   
        }
        pageFirst();
    }

    public void buscarByDireccionTodas() {
        setDireccion("");
        buscarByDireccion();
    }

    public DireccionMgl getDireccionMgl() {
        return direccionMgl;
    }

    public void setDireccionMgl(DireccionMgl direccionMgl) {
        this.direccionMgl = direccionMgl;
    }

    public List<DireccionMgl> getDireccionMglList() {
        return direccionMglList;
    }

    public void setDireccionMglList(List<DireccionMgl> direccionMglList) {
        this.direccionMglList = direccionMglList;
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

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<TipoDireccionMgl> getListTipoDireccionMgl() {
        return listTipoDireccionMgl;
    }

    public void setListTipoDireccionMgl(List<TipoDireccionMgl> listTipoDireccionMgl) {
        this.listTipoDireccionMgl = listTipoDireccionMgl;
    }

    public List<UbicacionMgl> getListUbicacionMgl() {
        return listUbicacionMgl;
    }

    public void setListUbicacionMgl(List<UbicacionMgl> listUbicacionMgl) {
        this.listUbicacionMgl = listUbicacionMgl;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGeoPolitico() {
        return geoPolitico;
    }

    public void setGeoPolitico(String geoPolitico) {
        this.geoPolitico = geoPolitico;
    }

    public String getGeoPoliticoCiudad() {
        return geoPoliticoCiudad;
    }

    public void setGeoPoliticoCiudad(String geoPoliticoCiudad) {
        this.geoPoliticoCiudad = geoPoliticoCiudad;
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
        HtmlDataTable t = dataTable;
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
        String value = event.getNewValue().toString();
        dataTable.setFirst((new Integer(value) - 1) * dataTable.getRows());
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

}
