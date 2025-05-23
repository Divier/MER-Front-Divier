package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DirEliminaMasivaDetalMglFacadeLocal;
import co.com.claro.mgl.facade.DirLogEliminacionMasivaMglFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.DirEliminaMasivaDetalMgl;
import co.com.claro.mgl.jpa.entities.DirLogEliminacionMasivaMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
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
@ManagedBean(name = "dirLogEliminacionMasivaMglBean")
@ViewScoped
public class DirLogEliminacionMasivaMglBean {
    private static final Logger LOGGER = LogManager.getLogger(DirLogEliminacionMasivaMglBean.class);

    private String usuarioVT = null;
    private DirLogEliminacionMasivaMgl dirLogEliminacionMasivaMgl = null;
    private List<DirLogEliminacionMasivaMgl> dirLogEliminacionMasivaMglList;
    private List<DirEliminaMasivaDetalMgl> dirEliminaMasivaDetalMglList;
    private String idSqlSelected;
    private String tipoDireccion;
    private String ubicacion;
    private String geoPolitico;
    private String geoPoliticoCiudad;
    private String direccion;
    private Date fechaInicial;
    private Date fechaFinal;
    private List<GeograficoPoliticoMgl> dptoList;
    private List<GeograficoPoliticoMgl> ciudadList;
    private List<UbicacionMgl> listUbicacionMgl = null;
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
    private DirLogEliminacionMasivaMglFacadeLocal dirLogEliminacionMasivaMglFacade;

    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private DirEliminaMasivaDetalMglFacadeLocal dirEliminaMasivaDetalMglFacadeLocal;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private String message;
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    /**
     * Creates a new instance of dirLogEliminacionMasivaMglBean
     */
    public DirLogEliminacionMasivaMglBean() {
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
                FacesUtil.mostrarMensajeError("Error en DirLogEliminacionMasivaMglBean. ", e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error en DirLogEliminacionMasivaMglBean. ", e, LOGGER);
            }

            List<DirEliminaMasivaDetalMgl> iddirLogEliminacionMasivaMgl = (List<DirEliminaMasivaDetalMgl>) session.getAttribute("iddirLogEliminacionMasivaMgl");
            session.removeAttribute("iddirLogEliminacionMasivaMgl");
            DirLogEliminacionMasivaMgl nd = dirLogEliminacionMasivaMgl;
            if (iddirLogEliminacionMasivaMgl != null) {
                dirEliminaMasivaDetalMglList = iddirLogEliminacionMasivaMgl;
                irdirLogEliminacionMasivaMgl();
            } else {
                dirLogEliminacionMasivaMgl = new DirLogEliminacionMasivaMgl();
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en DirLogEliminacionMasivaMglBean. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en DirLogEliminacionMasivaMglBean. ", e, LOGGER);
        }
    }

    @PostConstruct
    public void fillSqlList() {
        try {
            listUbicacionMgl = new ArrayList<UbicacionMgl>();
            
            dptoList = new ArrayList<GeograficoPoliticoMgl>();
            dptoList = geograficoPoliticoMglFacadeLocal.findDptos();
        } catch (ApplicationException e) {
            LOGGER.error("Error en fillSqlList.  " +e.getMessage(), e);   
        } catch (Exception e) {
            LOGGER.error("Error en fillSqlList.  " +e.getMessage(), e);   
        }
    }

    public String goActualizar() {
        try {
            dirEliminaMasivaDetalMglList = dirEliminaMasivaDetalMglFacadeLocal.findByLemId(new BigDecimal(idSqlSelected));
            dirLogEliminacionMasivaMgl = (DirLogEliminacionMasivaMgl) dataTable.getRowData();
            session.setAttribute("iddirLogEliminacionMasivaMgl", dirEliminaMasivaDetalMglList);
            return "DirLogEliminacionMasivaMglView";
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en goActualizar. ", e, LOGGER);
            if (session == null) {
                return "DirLogEliminacionMasivaMglListView";
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en goActualizar. ", e, LOGGER);
            if (session == null) {
                return "DirLogEliminacionMasivaMglListView";
            }
        }
        return null;
    }

    public String irdirLogEliminacionMasivaMgl() throws ApplicationException {
        try {
            dirLogEliminacionMasivaMgl = (DirLogEliminacionMasivaMgl) dataTable.getRowData();
            session.setAttribute("dirLogEliminacionMasivaMgl", dirLogEliminacionMasivaMgl);

            return "DirLogEliminacionMasivaMglView";
        } catch (RuntimeException e) {
            LOGGER.error("Error en irdirLogEliminacionMasivaMgl " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en irdirLogEliminacionMasivaMgl " + e.getMessage(), e);
            return null;
        }
    }

    public String nuevodirLogEliminacionMasivaMgl() {
        dirLogEliminacionMasivaMgl = null;
        dirLogEliminacionMasivaMgl = new DirLogEliminacionMasivaMgl();
        dirLogEliminacionMasivaMgl.setLemID(BigDecimal.ZERO);
        return "DirLogEliminacionMasivaMglView";
    }

    public void eliminardirLogEliminacionMasivaMgl() {
        try {
            dirLogEliminacionMasivaMglFacade.delete(dirLogEliminacionMasivaMgl);
            message = "Proceso exitoso";
            session.setAttribute("message", message);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en eliminardirLogEliminacionMasivaMgl. ", e, LOGGER);
            message = "Proceso falló";
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en eliminardirLogEliminacionMasivaMgl. ", e, LOGGER);
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

    public void buscarByFechas() throws ApplicationException {
        try {
            if (getFechaInicial() == null) {
                message = "Seleccionar fecha Incial";
                return;
            }
            if (getFechaFinal() == null) {
                message = "Seleccionar fecha Final";
                return;
            }
            dirLogEliminacionMasivaMglList =
                    dirLogEliminacionMasivaMglFacade.findByBetweenDate(fechaInicial, fechaFinal);
        } catch (ApplicationException e) {
            LOGGER.error("Error en buscarByFechas. " +e.getMessage(), e); 
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en buscarByFechas. " +e.getMessage(), e); 
            throw new ApplicationException("Error en buscarByFechas. " ,e);
        }
        pageFirst();
    }

    public void buscarByDireccionTodas() {
        try {
            setDireccion("");
            buscarByFechas();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en buscarByDireccionTodas. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en buscarByDireccionTodas. ", e, LOGGER);
        }
    }

    public DirLogEliminacionMasivaMgl getdirLogEliminacionMasivaMgl() {
        return dirLogEliminacionMasivaMgl;
    }

    public void setdirLogEliminacionMasivaMgl(DirLogEliminacionMasivaMgl dirLogEliminacionMasivaMgl) {
        this.dirLogEliminacionMasivaMgl = dirLogEliminacionMasivaMgl;
    }

    public List<DirLogEliminacionMasivaMgl> getdirLogEliminacionMasivaMglList() {
        return dirLogEliminacionMasivaMglList;
    }

    public void setdirLogEliminacionMasivaMglList(List<DirLogEliminacionMasivaMgl> dirLogEliminacionMasivaMglList) {
        this.dirLogEliminacionMasivaMglList = dirLogEliminacionMasivaMglList;
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

    public List<DirEliminaMasivaDetalMgl> getDirEliminaMasivaDetalMglList() {
        return dirEliminaMasivaDetalMglList;
    }

    public void setDirEliminaMasivaDetalMglList(List<DirEliminaMasivaDetalMgl> dirEliminaMasivaDetalMglList) {
        this.dirEliminaMasivaDetalMglList = dirEliminaMasivaDetalMglList;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
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

        paginaList = new ArrayList<>();

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
