/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.SqlDataFacadeLocal;
import co.com.claro.mgl.jpa.entities.SqlData;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "sqlDataBean")
@ViewScoped
public class SqlDataBean {
    
    private static final Logger LOGGER = LogManager.getLogger(SqlDataBean.class);

    private SqlData sqlData = null;
    private List<SqlData> sqlDataList;
    private String idSqlSelected;
    private String idSqlData;
    private boolean mostrar = true;
    private boolean actualizar = true;
    /** Paginacion Tabla  */
    private HtmlDataTable dataTable = new HtmlDataTable();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    /***/
    @EJB
    private SqlDataFacadeLocal SqlDataFacade;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private String message;

    /**
     * Creates a new instance of SqlDataBean
     */
    public SqlDataBean() {
        try {LOGGER.error("const" );
            SqlData idNodoMgl = (SqlData) session.getAttribute("idSqlData");
            session.removeAttribute("idSqlData");
            if (idNodoMgl != null) {
                sqlData = idNodoMgl;
                irSqlData();
            } else {
                sqlData = new SqlData();
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en SqlDataBean. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SqlDataBean. ", e, LOGGER);
        }

    }

    @PostConstruct
    private void fillSqlList() {

        try {LOGGER.error("posConst" );LOGGER.error( "numPagCons " + numPagina);
            sqlDataList = new ArrayList<SqlData>();
            sqlDataList = SqlDataFacade.findAll();
        } catch (ApplicationException e) {
            LOGGER.error("Error en fillSqlList. " +e.getMessage(), e);  
        } catch (Exception e) {
            LOGGER.error("Error en fillSqlList. " +e.getMessage(), e);  
        }

    }

    public String goActualizar() {
        try {
            sqlData = (SqlData) dataTable.getRowData();
            session.setAttribute("idSqlData", sqlData);
            return "sqlDataView";
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en goActualizar. ", e, LOGGER);
            if (session == null) {
                return "sqlDataListView";
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en goActualizar. ", e, LOGGER);
            if (session == null) {
                return "sqlDataListView";
            }
        }
        return null;
    }

    public String irSqlData() throws ApplicationException {
            return "sqlDataView";
    }

    public String nuevoSqlData() {
        sqlData = null;
        sqlData = new SqlData();
        return "sqlDataView";

    }

    public void crearlSqlData() {
        try {
            if (sqlData.getId() == null || sqlData.getId().trim().isEmpty()) {
                message = "Campo Id Sql es requerido";
                return;
            }
            if (sqlData.getSentence() == null || sqlData.getSentence().trim().isEmpty()) {
                message = "Campo Sentencia Sql es requerido";
                actualizar = false;
                return;
            }
            SqlDataFacade.create(sqlData);
            message = "Proceso exitoso";
            session.setAttribute("message", message);
        } catch (ApplicationException e) {
            String error = e.toString();
            if (error.contains("unique constraint")) {
                error = " Id Sql ya Existe";
                mostrar = false;
            }
            FacesUtil.mostrarMensajeError("Error en crearlSqlData. "+ error +" ", e, LOGGER);
            message = "Proceso falló  " + error;
            session.setAttribute("message", message);
        }


    }

    public void actualizarlSqlData() {

        try {
            if (sqlData.getId() == null || sqlData.getId().trim().isEmpty()) {
                message = "Campo Id Sql es requerido";
                return;
            }
            if (sqlData.getSentence() == null || sqlData.getSentence().trim().isEmpty()) {
                message = "Campo Sentencia Sql es requerido";
                return;
            }
            SqlDataFacade.update(sqlData);
            message = "Proceso exitoso";
            session.setAttribute("message", message);
        } catch (ApplicationException e) {
            String error = e.toString();
            if (error.contains("unique constraint")) {
                error = " Id Sql ya Existe";
                mostrar = false;
            }
            FacesUtil.mostrarMensajeError("Error en crearlSqlData. "+ error +" ", e, LOGGER);
            message = "Proceso falló  " + error;
            session.setAttribute("message", message);
        }catch (Exception e) {
            String error = e.toString();
            if (error.contains("unique constraint")) {
                error = " Id Sql ya Existe";
                mostrar = false;
            }
            FacesUtil.mostrarMensajeError("Error en crearlSqlData. "+ error +" ", e, LOGGER);
            message = "Proceso falló  " + error;
            session.setAttribute("message", message);
        }

    }

    public void eliminarlSqlData() {

        try {
            SqlDataFacade.delete(sqlData);
            message = "Proceso exitoso";
            session.setAttribute("message", message);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en crearlSqlData. ", e, LOGGER);
            message = "Proceso falló";
            session.setAttribute("message", message);
        }catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en crearlSqlData. ", e, LOGGER);
            message = "Proceso falló";
            session.setAttribute("message", message);
        }

    }

    public void buscarSqlDataById() {
        sqlDataList = SqlDataFacade.findSqlDataById(getIdSqlData());
        pageFirst();
    }

    public void buscarSqlDataTodos() {
        setIdSqlData("");
        buscarSqlDataById();
    }

 
    public SqlData getSqlData() {
        return sqlData;
    }

    public void setSqlData(SqlData sqlData) {
        this.sqlData = sqlData;
    }

    public List<SqlData> getSqlDataList() {
        return sqlDataList;
    }

    public void setSqlDataList(List<SqlData> sqlDataList) {
        this.sqlDataList = sqlDataList;
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

    public boolean isMostrar() {
        return mostrar;
    }

    public void setMostrar(boolean mostrar) {
        this.mostrar = mostrar;
    }

    public boolean isActualizar() {
        return actualizar;
    }

    public void setActualizar(boolean actualizar) {
        this.actualizar = actualizar;
    }

    public String getIdSqlData() {
        return idSqlData;
    }

    public void setIdSqlData(String idSqlData) {
        this.idSqlData = idSqlData;
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
        dataTable.setFirst(dataTable.getFirst() + dataTable.getRows());LOGGER.error("rows Nex " + dataTable.getRows());LOGGER.error("Firsx " + dataTable.getFirst());
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public void pageLast() {
        int count = dataTable.getRowCount();LOGGER.error("count " + count);
        int rows = dataTable.getRows();LOGGER.error("rows " + count);
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
        
        for(int i = 1 ; i<= totalPaginas; i++ ){
        paginaList.add(i);
        }
        

        pageActual = actual + " de " + totalPaginas;
        
        if(numPagina == null ){
        numPagina = "1";
        }
        return pageActual;
    }

    
    public void irPagina(ValueChangeEvent event) {
        String value = event.getNewValue().toString();
        dataTable.setFirst((new Integer(value) - 1) * dataTable.getRows());
        LOGGER.error("numPagina " + value);
        LOGGER.error("ir a pagina " + dataTable.getFirst());
        
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
