package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DireccionMglFacadeLocal;
import co.com.claro.mgl.facade.HhppMglFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.SubDireccionMglFacadeLocal;
import co.com.claro.mgl.facade.TipoHhppConexionMglFacadeLocal;
import co.com.claro.mgl.facade.TipoHhppRedMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.TipoHhppConexionMgl;
import co.com.claro.mgl.jpa.entities.TipoHhppRedMgl;
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
@ManagedBean(name = "hhppMglBean")
@ViewScoped
public class HhppMglBean {

    private String usuarioVT = null;
    private HhppMgl hhppMgl = null;
    private List<HhppMgl> hhppMglList;
    private String idSqlSelected;
    private String direccion;
    private String nodo;
    private String subDireccion;
    private String tipoHhppRed;
    private String tipoHhppConexion;
    private String nodoHhpp;
    public List<DireccionMgl> listDireccionMgl = null;
    public List<NodoMgl> listNodoMgl = null;
    public List<SubDireccionMgl> listSubDireccionMgl = null;
    public List<TipoHhppRedMgl> listTipoHhppRedMgl = null;
    public List<TipoHhppConexionMgl> listTipoHhppConexionMgl = null;
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
    private HhppMglFacadeLocal hhppMglFacade;
    @EJB
    private DireccionMglFacadeLocal direccionMglFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private SubDireccionMglFacadeLocal subDireccionMglFacadeLocal;
    @EJB
    private TipoHhppRedMglFacadeLocal tipoHhppRedMglFacadeLocal;
    @EJB
    private TipoHhppConexionMglFacadeLocal tipoHhppConexionMglFacadeLocal;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private String message;
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(HhppMglBean.class);

    /**
     * Creates a new instance of hhppMglBean
     */
    public HhppMglBean() {
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
                FacesUtil.mostrarMensajeError("Error en HhppMglBean. ", e, LOGGER);
            }catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error en HhppMglBean. ", e, LOGGER);
            }


            HhppMgl idHhppMgl = (HhppMgl) session.getAttribute("idHhppMgl");
            session.removeAttribute("idHhppMgl");
            if (idHhppMgl != null) {
                hhppMgl = idHhppMgl;
                irHhppMgl();
            } else {
                hhppMgl = new HhppMgl();
                NodoMgl nodom = new NodoMgl();
                nodom.setNodId(BigDecimal.ZERO);
                hhppMgl.setNodId(nodom);
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en HhppMglBean. ", e, LOGGER);
        }
    }

    @PostConstruct
    public void fillSqlList() {
        try {
            hhppMglList = hhppMglFacade.findAll();
            listTipoHhppConexionMgl = new ArrayList<TipoHhppConexionMgl>();
            listTipoHhppConexionMgl = tipoHhppConexionMglFacadeLocal.findAll();
            listDireccionMgl = new ArrayList<DireccionMgl>();
            listDireccionMgl = direccionMglFacadeLocal.findAll();
            listSubDireccionChange();
            listNodoMgl = new ArrayList<NodoMgl>();
            listNodoMgl = nodoMglFacadeLocal.findAll();

            listTipoHhppRedMgl = new ArrayList<TipoHhppRedMgl>();
            listTipoHhppRedMgl = tipoHhppRedMglFacadeLocal.findAll();
        } catch (ApplicationException e) {
            LOGGER.error("Error en fillSqlList. " , e);     
        } catch (Exception e) {
            LOGGER.error("Error en fillSqlList. " , e);     
        }
    }

    public String goActualizar() {
        try {
            hhppMgl = (HhppMgl) dataTable.getRowData();
            session.setAttribute("idHhppMgl", hhppMgl);
            return "hhppMglView";
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en goActualizar. ", e, LOGGER);     
            if (session == null) {
                return "hhppMglListView";
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en goActualizar. ", e, LOGGER);     
            if (session == null) {
                return "hhppMglListView";
            }
        }
        return null;
    }

    public String irHhppMgl() {
        try {
            
            direccion = hhppMgl.getDirId() == null ? "0" : hhppMgl.getDirId().toString();
            nodo = hhppMgl.getNodId().getNodId() == null ? "0" : hhppMgl.getNodId().getNodId().toString();
            listSubDireccionChange();
            subDireccion = hhppMgl.getSdiId() == null ? "0" : hhppMgl.getSdiId().toString();
            tipoHhppRed = hhppMgl.getThrId() == null ? "0" : hhppMgl.getThrId().toString();
            tipoHhppConexion = hhppMgl.getThcId() == null ? "0" : hhppMgl.getThcId().toString();
            return "hhppMglView";
        } catch (RuntimeException e) {
            LOGGER.error("Error en irHhppMgl " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en irHhppMgl " + e.getMessage(), e);
        }
        return null;
    }

    public String nuevoHhppMgl() {
        hhppMgl = null;
        hhppMgl = new HhppMgl();
        NodoMgl nodom = new NodoMgl();
        nodom.setNodId(BigDecimal.ZERO);
        hhppMgl.setNodId(nodom);
        return "hhppMglView";
    }

    public void crearHhppMgl() {
        try {
            hhppMgl.setHhpId(BigDecimal.ZERO);
            hhppMgl.setFechaCreacion(new Date());
            hhppMgl.setUsuarioCreacion(usuarioVT);
            hhppMgl.setDirId(new BigDecimal(direccion));
            if (hhppMgl.getDirId().equals(BigDecimal.ZERO)) {
                message = "Campo Dirección es requerido";
                return;
            }
            hhppMgl.setSdiId(new BigDecimal(subDireccion));
            if (hhppMgl.getSdiId().equals(BigDecimal.ZERO)) {
                message = "Campo Subdirección es requerido";
                return;
            }
            NodoMgl nodom = new NodoMgl();
            nodom.setNodId(   new BigDecimal(nodo)  );
            hhppMgl.setNodId(nodom);
            if (hhppMgl.getNodId().getNodId().compareTo(BigDecimal.ZERO) == 0) {
                message = "Campo Nodo es requerido";
                return;
            }
            hhppMgl.setThrId(new BigDecimal(tipoHhppRed));
            if (hhppMgl.getThrId().equals(BigDecimal.ZERO)) {
                message = "Campo Tipo HHPP red es requerido";
                return;
            }
            hhppMgl.setThcId(new BigDecimal(tipoHhppConexion));
            if (hhppMgl.getThcId().equals(BigDecimal.ZERO)) {
                message = "Campo Tipo HHPP conexión es requerido";
                return;
            }
            if (hhppMgl.getThhId() == null || hhppMgl.getThhId().equals("")) {
                message = "Campo THH_ID es requerido";
                return;
            }
            if (hhppMgl.getEhhId() == null || hhppMgl.getEhhId().getEhhNombre().equals("")) {
                message = "Campo EHH_ID es requerido";
                return;
            }

            hhppMglFacade.create(hhppMgl);
            message = "Proceso exitoso";
            session.setAttribute("message", message);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en crearHhppMgl. ", e, LOGGER);
            message = "Proceso falló";
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en crearHhppMgl. ", e, LOGGER);
            message = "Proceso falló";
            session.setAttribute("message", message);
        }
    }

    public void actualizarHhppMgl() {
        try {
            hhppMgl.setUsuarioEdicion((String) session.getAttribute("usuarioM"));
            hhppMgl.setFechaEdicion(new Date());
            if (hhppMgl.getDirId().equals(BigDecimal.ZERO)) {
                message = "Campo Dirección es requerido";
                return;
            }
            hhppMgl.setSdiId(new BigDecimal(subDireccion));
            if (hhppMgl.getSdiId().equals(BigDecimal.ZERO)) {
                message = "Campo Subdirección es requerido";
                return;
            }
            NodoMgl nodom = new NodoMgl();
            nodom.setNodId( new BigDecimal(nodo) );
            hhppMgl.setNodId(nodom);
            if (hhppMgl.getNodId().getNodId().compareTo(BigDecimal.ZERO) == 0) {
                message = "Campo Nodo es requerido";
                return;
            }
            hhppMgl.setThrId(new BigDecimal(tipoHhppRed));
            if (hhppMgl.getThrId().equals(BigDecimal.ZERO)) {
                message = "Campo Tipo HHPP red es requerido";
                return;
            }
            hhppMgl.setThcId(new BigDecimal(tipoHhppConexion));
            if (hhppMgl.getThcId().equals(BigDecimal.ZERO)) {
                message = "Campo Tipo HHPP conexión es requerido";
                return;
            }
            if (hhppMgl.getThhId() == null || hhppMgl.getThhId().equals("")) {
                message = "Campo THH_ID es requerido";
                return;
            }
            if (hhppMgl.getEhhId() == null || hhppMgl.getEhhId().getEhhNombre().equals("")) {
                message = "Campo EHH_ID es requerido";
                return;
            }
            hhppMglFacade.update(hhppMgl);
            message = "Proceso exitoso";
            session.setAttribute("message", message);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en actualizarHhppMgl. ", e, LOGGER);
            message = "Proceso falló";
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en actualizarHhppMgl. ", e, LOGGER);
            message = "Proceso falló";
            session.setAttribute("message", message);
        }
    }

    public void eliminarHhppMgl() {

        try {
            hhppMglFacade.delete(hhppMgl);
            message = "Proceso exitoso";
            session.setAttribute("message", message);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en eliminarHhppMgl. ", e, LOGGER);
            message = "Proceso falló";
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en eliminarHhppMgl. ", e, LOGGER);
            message = "Proceso falló";
            session.setAttribute("message", message);
        }

    }

    public void buscarHhppByNodo() {
        try {
            hhppMglList = hhppMglFacade.findHhppByCodNodo(getNodoHhpp());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en buscarHhppByNodo. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en buscarHhppByNodo. ", e, LOGGER);
        }
        pageFirst();
    }

    public void buscarHhppTodos() {
        setNodoHhpp("");
        buscarHhppByNodo();
    }

    public String getNodoHhpp() {
        return nodoHhpp;
    }

    public void setNodoHhpp(String nodoHhpp) {
        this.nodoHhpp = nodoHhpp;
    }

    public HhppMgl getHhppMgl() {
        return hhppMgl;
    }

    public void setHhppMgl(HhppMgl hhppMgl) {
        this.hhppMgl = hhppMgl;
    }

    public List<HhppMgl> getHhppMglList() {
        return hhppMglList;
    }

    public void setHhppMglList(List<HhppMgl> hhppMglList) {
        this.hhppMglList = hhppMglList;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String Direccion) {
        this.direccion = Direccion;
    }

    public List<DireccionMgl> getListDireccionMgl() {
        return listDireccionMgl;
    }

    public void setListDireccionMgl(List<DireccionMgl> listDireccionMgl) {
        this.listDireccionMgl = listDireccionMgl;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public List<NodoMgl> getListNodoMgl() {
        return listNodoMgl;
    }

    public void setListNodoMgl(List<NodoMgl> listNodoMgl) {
        this.listNodoMgl = listNodoMgl;
    }

    public String getSubDireccion() {
        return subDireccion;
    }

    public void setSubDireccion(String subDireccion) {
        this.subDireccion = subDireccion;
    }

    public List<SubDireccionMgl> getListSubDireccionMgl() {
        return listSubDireccionMgl;
    }

    public void setListSubDireccionMgl(List<SubDireccionMgl> listSubDireccionMgl) {
        this.listSubDireccionMgl = listSubDireccionMgl;
    }

    public String getTipoHhppRed() {
        return tipoHhppRed;
    }

    public void setTipoHhppRed(String tipoHhppRed) {
        this.tipoHhppRed = tipoHhppRed;
    }

    public String getTipoHhppConexion() {
        return tipoHhppConexion;
    }

    public void setTipoHhppConexion(String tipoHhppConexion) {
        this.tipoHhppConexion = tipoHhppConexion;
    }

    public List<TipoHhppRedMgl> getListTipoHhppRedMgl() {
        return listTipoHhppRedMgl;
    }

    public void setListTipoHhppRedMgl(List<TipoHhppRedMgl> listTipoHhppRedMgl) {
        this.listTipoHhppRedMgl = listTipoHhppRedMgl;
    }

    public List<TipoHhppConexionMgl> getListTipoHhppConexionMgl() {
        return listTipoHhppConexionMgl;
    }

    public void setListTipoHhppConexionMgl(List<TipoHhppConexionMgl> listTipoHhppConexionMgl) {
        this.listTipoHhppConexionMgl = listTipoHhppConexionMgl;
    }

    public void listSubDireccionChange() {
        try {
            listSubDireccionMgl = subDireccionMglFacadeLocal.findByIdDireccionMgl(new BigDecimal(direccion));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en listSubDireccionChange. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listSubDireccionChange. ", e, LOGGER);
        }

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
