/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.MultivalorFacadeLocal;
import co.com.claro.mgl.facade.VetoNodoFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.Multivalor;
import co.com.claro.mgl.jpa.entities.VetoNodo;
import co.com.telmex.catastro.util.FacesUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author User
 */
@ManagedBean(name = "reporteVetoNodoBean")
@ViewScoped
public class ReporteVetoNodoBean {

    
    private static final Logger LOGGER = LogManager.getLogger(ReporteVetoNodoBean.class);
    private String numPolitica;
    private Date initDate;
    private Date endDate;
    private String departamento;
    private List<GeograficoPoliticoMgl> listDptos;
    private String ciudad;
    private List<GeograficoPoliticoMgl> listCiudades;
    private String tipoVeto;
    private List<Multivalor> listTipoVeto;
    private HtmlDataTable vetosTable = new HtmlDataTable();
    private List<VetoNodo> vetoNodoList;
    @EJB
    MultivalorFacadeLocal multivalorFacadeLocal;
    @EJB
    GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    VetoNodoFacadeLocal vetoNodoFacadeLocal;

    public String getNumPolitica() {
        return numPolitica;
    }

    public void setNumPolitica(String numPolitica) {
        this.numPolitica = numPolitica;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public List<GeograficoPoliticoMgl> getListDptos() {
        return listDptos;
    }

    public void setListDptos(List<GeograficoPoliticoMgl> listDptos) {
        this.listDptos = listDptos;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<GeograficoPoliticoMgl> getListCiudades() {
        return listCiudades;
    }

    public void setListCiudades(List<GeograficoPoliticoMgl> listCiudades) {
        this.listCiudades = listCiudades;
    }

    public String getTipoVeto() {
        return tipoVeto;
    }

    public void setTipoVeto(String tipoVeto) {
        this.tipoVeto = tipoVeto;
    }

    public List<Multivalor> getListTipoVeto() {
        return listTipoVeto;
    }

    public void setListTipoVeto(List<Multivalor> listTipoVeto) {
        this.listTipoVeto = listTipoVeto;
    }

    public HtmlDataTable getVetosTable() {
        return vetosTable;
    }

    public void setVetosTable(HtmlDataTable vetosTable) {
        this.vetosTable = vetosTable;
    }

    public List<VetoNodo> getVetoNodoList() {
        return vetoNodoList;
    }

    public void setVetoNodoList(List<VetoNodo> vetoNodoList) {
        this.vetoNodoList = vetoNodoList;
    }

    @PostConstruct
    public void fillData() {
        try {
            //Cargamos los departamentos.
            listDptos = new ArrayList<GeograficoPoliticoMgl>();
            listDptos = geograficoPoliticoMglFacadeLocal.findDptos();

            //Cargamos los tipos Veto 
            listTipoVeto = new ArrayList<Multivalor>();
            listTipoVeto = multivalorFacadeLocal.loadTipoVeto();

        } catch (ApplicationException e) {
            LOGGER.error("Error en fillData. EX. " +e.getMessage(), e);     
        } catch (Exception e) {
            LOGGER.error("Error en fillData. EX. " +e.getMessage(), e);     
        }
    }

    public void listDeptosChange() {
        try {
            listCiudades = geograficoPoliticoMglFacadeLocal.findCiudades(new BigDecimal(departamento));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en listDeptosChange. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listDeptosChange. ", e, LOGGER);
        }
    }

    public String generarReporte() {
        try {
            vetoNodoList = vetoNodoFacadeLocal.findVetos(numPolitica, initDate, endDate, ciudad == null ? null : new BigDecimal(ciudad), tipoVeto);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en generarReporte. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en generarReporte. ", e, LOGGER);
        }
        return null;
    }

    public String exportExcel() {
        try {
            Map<String, Object[]> data = new TreeMap<String, Object[]>();
            int fila = 1;
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            data.put(String.valueOf(fila++), new Object[]{"Ciudad", "Nodo", "Politica", "Tipo Veto", "Fecha Inicio", "Fecha Fin"});
            //llenamos el Map con los datos a ser exportados al excel
            for (VetoNodo vn : vetoNodoList) {
                data.put(String.valueOf(fila++),
                        new Object[]{vn.getGpoId().toString(),
                    vn.getNodId().getNodCodigo().toString(),
                    vn.getVetPolitica(),
                    vn.getVetArea(),
                    formato.format(vn.getVetFechaInicio()),
                    formato.format(vn.getVetFechaFin())});
            }

            createFileXLSX(data, "Reporte_Veto_Nodo");
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        }
        return null;
    }

    private String createFileXLSX(Map<String, Object[]> data, String fileName) throws ApplicationException {
        try {
            //Blank workbook
            XSSFWorkbook workbook = fillWorkbook(data);

            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel");

            SimpleDateFormat formato = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");

            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + formato.format(new Date()) + ".xlsx\"");
            OutputStream output = response.getOutputStream();

            workbook.write(output);
            output.flush();
            output.close();
            fc.responseComplete();
        } catch (ApplicationException | IOException e) {
            LOGGER.error("Error en createFileXLSX. " +e.getMessage(), e);      
            throw new ApplicationException("Error en createFileXLSX. ", e);
        } catch (Exception e) {
            LOGGER.error("Error en createFileXLSX. " +e.getMessage(), e);      
            throw new ApplicationException("Error en createFileXLSX. ", e);
        }
        return null;
    }

    private XSSFWorkbook fillWorkbook(Map<String, Object[]> data) throws ApplicationException {
        try {
            //Blank workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            //Create a blank sheet
            XSSFSheet sheet = workbook.createSheet("Reporte Veto Nodos");

            //Iterate over data and write to sheet
            Set<String> keyset = data.keySet();
            int rownum = 0;
            for (String key : keyset) {
                Row row = sheet.createRow(rownum++);
                Object[] objArr = data.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof Integer) {
                        cell.setCellValue((Integer) obj);
                    }
                }
            }
            return workbook;
        } catch (RuntimeException e) {
            LOGGER.error("Error en fillWorkbook " +e.getMessage(), e);      
            throw new ApplicationException("Error en fillWorkbook ", e);
        } catch (Exception e) {
            LOGGER.error("Error en fillWorkbook " +e.getMessage(), e);      
            throw new ApplicationException("Error en fillWorkbook ", e);
        }
    }

    // Actions Paging--------------------------------------------------------------------------
    public void pageFirst() {
        vetosTable.setFirst(0);
    }

    public void pagePrevious() {
        vetosTable.setFirst(vetosTable.getFirst() - vetosTable.getRows());
    }

    public void pageNext() {
        vetosTable.setFirst(vetosTable.getFirst() + vetosTable.getRows());
    }

    public void pageLast() {
        int count = vetosTable.getRowCount();
        int rows = vetosTable.getRows();
        vetosTable.setFirst(count - ((count % rows != 0) ? count % rows : rows));
    }
}
