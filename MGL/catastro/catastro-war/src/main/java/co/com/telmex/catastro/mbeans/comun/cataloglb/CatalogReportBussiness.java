package co.com.telmex.catastro.mbeans.comun.cataloglb;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.CatalogAdc;
import co.com.telmex.catastro.data.CatalogFilterReport;
import co.com.telmex.catastro.data.DataResult;
import co.com.telmex.catastro.data.DataRow;
import co.com.telmex.catastro.delegate.ComunDelegate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Clase CatalogReportBussiness
 *
 * @author Ana María Malambo
 * @version	1.0
 */
public class CatalogReportBussiness {

    private static final Logger LOGGER = LogManager.getLogger(CatalogReportBussiness.class);

    /**
     *
     * @param request
     * @param response
     * @param id
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void filterFormReport(HttpServletRequest request, HttpServletResponse response, String id) throws ApplicationException {
        List<CatalogFilterReport> filters = null;
        String parameter[] = new String[1];
        parameter[0] = id;
        ArrayList ID = new ArrayList<>();
        ArrayList Label = new ArrayList<>();
        ArrayList Columna = new ArrayList<>();
        ArrayList type = new ArrayList<>();
        String sqldata = "";
        String sqldata1 = "";
        String exportEx = "";
        String exportPdf = "";
        String exportCvs = "";
        String consulta = "";
        ArrayList datafilt = new ArrayList<>();

        int i = 0;
        try {
            CatalogAdc catalogAdc = new CatalogAdc();
            filters = new ArrayList<>();
            filters = ComunDelegate.queryFilterReport(id);
            for (CatalogFilterReport filtro : filters) {
                ID.add(filtro.getCfrId());
                Columna.add(filtro.getCfrColumn());
                Label.add(filtro.getCfrLabel());
                type.add(filtro.getCfrType());
                sqldata = filtro.getCreSqldata();
                sqldata1 = filtro.getCreSqldata1();
                datafilt.add(" ");
                i++;

            }
            exportEx = "0";
            exportPdf = "0";
            exportCvs = "0";
        } catch (Exception ex) {
            LOGGER.error("Error al momento de filtrar el reporte. EX000: " + ex.getMessage(), ex);
        }
        HttpSession session = request.getSession(true);
        if (filters != null) {
            session.setAttribute("filter", ID);
            session.setAttribute("totalc", i + "");
            session.setAttribute("label", Label);
            session.setAttribute("column", Columna);
            session.setAttribute("exportEx", exportEx);
            session.setAttribute("exportPdf", exportPdf);
            session.setAttribute("exportCvs", exportCvs);
            session.setAttribute("sqldata", sqldata);
            session.setAttribute("sqldata1", sqldata1);
            session.setAttribute("parameter", datafilt);
            session.setAttribute("type", type);
            session.setAttribute("idconsulta", consulta);

        }

    }

    /**
     *
     * @param request
     * @param response
     * @param parameter
     * @param id
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void filterFormResult(HttpServletRequest request, HttpServletResponse response, String parameter[], String id) throws ApplicationException {

        List<CatalogFilterReport> filters = null;
        DataResult dataResult = null;
        dataResult = new DataResult();
        ArrayList ID = new ArrayList<>();
        ArrayList Label = new ArrayList<>();
        ArrayList Columna = new ArrayList<>();
        ArrayList type = new ArrayList<>();
        String consulta = "";
        String sqldata = "";
        String exportEx = "";
        String exportPdf = "";
        String exportCvs = "";
        ArrayList datafilt = new ArrayList<>();
        int i = 0;
        try {
            CatalogAdc catalogAdc = new CatalogAdc();
            filters = new ArrayList<>();
            filters = ComunDelegate.queryFilterReport(id);
            HttpSession session = request.getSession(true);
            BigDecimal idc = catalogAdc.getCtlId();

            for (CatalogFilterReport filtro : filters) {
                ID.add(filtro.getCfrId());
                Columna.add(filtro.getCfrColumn());
                Label.add(filtro.getCfrLabel());
                type.add(filtro.getCfrType());
                sqldata = filtro.getCreSqldata();
                exportEx = filtro.getCreExportExcel();
                exportPdf = filtro.getCreExportPdf();
                exportCvs = filtro.getCreExportCvs();
                i++;

            }
            String ctl_consulta = request.getParameter("sqldata");
            String ctl_consulta1 = request.getParameter("sqldata1");
            String tipo = request.getParameter("tipo");
            dataResult = ComunDelegate.queryDataResult((tipo.equals("N")) ? ctl_consulta : ctl_consulta1, parameter); 
            dataResult = null;
            String existdata = "false";
            List catalog_detalle = null;
            if (dataResult == null) {
                existdata = "false";
            } else {
                existdata = "true";

                catalog_detalle = (ArrayList) dataResult.getListNameColumn();
                List<DataRow> resultfilter = null;
                resultfilter = (ArrayList) dataResult.getListDataRow();
                List col = null;
                ArrayList valorc = null;
                String colum = "";
                for (int p = 0; p < parameter.length; p++) {
                    datafilt.add(parameter[p]);
                }
                String matriz[][] = new String[resultfilter.size()][100];

                for (int j = 0; j < resultfilter.size(); j++) {
                    col = (ArrayList) resultfilter.get(j).getColumns();
                    for (int k = 0; k < col.size(); k++) {
                        matriz[j][k] = resultfilter.get(j).getColumn(k);
                    }
                }

                session.setAttribute("resultfilter", resultfilter);
                session.setAttribute("nomcolumn", catalog_detalle);
                session.setAttribute("totcl", catalog_detalle);
                session.setAttribute("dataResult", dataResult);
                session.setAttribute("parameter", datafilt);
                String totlcols = resultfilter.size() + "";
                session.setAttribute("totalcol", totlcols);
            }
            response.setContentType("text/html;charset=UTF-8");
            session.setAttribute("existdata", existdata);
            session.setAttribute("exportEx", exportEx);
            session.setAttribute("exportPdf", exportPdf);
            session.setAttribute("exportCvs", exportCvs);
            session.setAttribute("sqldata", sqldata);
        } catch (Exception ex) {
            LOGGER.error("Error al momento de filtrar el resultado. EX000: " + ex.getMessage(), ex);
        }
        HttpSession session = request.getSession(true);
        if (filters != null) {
            session.setAttribute("filter", ID);
            session.setAttribute("totalc", i + "");
            session.setAttribute("label", Label);
            session.setAttribute("column", Columna);

            session.setAttribute("exportEx", exportEx);
            session.setAttribute("exportPdf", exportPdf);
            session.setAttribute("exportCvs", exportCvs);
            session.setAttribute("sqldata", sqldata);
            session.setAttribute("type", type);
            session.setAttribute("idconsulta", consulta);

        }

    }
}
