package co.com.telmex.catastro.mbeans.comun.cataloglb;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.CatalogAdc;
import co.com.telmex.catastro.data.CatalogFilter;
import co.com.telmex.catastro.data.DataResult;
import co.com.telmex.catastro.data.DataRow;
import co.com.telmex.catastro.delegate.ComunDelegate;
import co.com.telmex.catastro.services.comun.CatalogEJB;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.util.PageReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Clase CatalogDataBussiness
 *
 * @author Ana María Malambo
 * @version	1.0
 */
public class CatalogDataBussiness {

    private static final Logger LOGGER = LogManager.getLogger(CatalogDataBussiness.class);

    /**
     *
     * @param request
     * @param response
     * @param id
     * @param ctl
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void filterForm(HttpServletRequest request, HttpServletResponse response, String id, String ctl) throws ApplicationException {
        PrintWriter out = null;
        try {
            List<CatalogFilter> filters = null;
            DataResult dataResult = new DataResult();
            String parameter[] = new String[2];
            String index = request.getParameter("index");
            if ("1".equals(index)) {
                parameter[0] = "null";
                parameter[1] = "";

            } else {
                int inddata = Integer.parseInt(index);
                int inddatap = (inddata + 10) - 1;
                int desde = inddata - 1;
                String numpag = inddatap + "";
                String desdep = desde + "";
                parameter[0] = "null";
                parameter[1] = "";

            }
            List filt = new ArrayList();
            ArrayList nombre = new ArrayList();
            ArrayList ID = new ArrayList();
            ArrayList Label = new ArrayList();
            ArrayList Columna = new ArrayList();
            String consulta = "";
            int i = 0;
            try {
                CatalogAdc catalogAdc = new CatalogAdc();
                BigDecimal idb = new BigDecimal(id);
                catalogAdc = ComunDelegate.queryCatalog(idb);

                dataResult = ComunDelegate.queryDataResult(ctl, parameter);
                filters = catalogAdc.getCatalogFilterList();
                HttpSession session = request.getSession(true);
                BigDecimal idc = catalogAdc.getCtlId();
                session.setAttribute("ctl_id", idc);
                session.setAttribute("alias", catalogAdc.getCtlAlias());
                consulta = catalogAdc.getCtlSql();
                for (CatalogFilter filte : filters) {
                    ID.add(filte.getCfiId());
                    Columna.add(filte.getCfiColumn());
                    Label.add(filte.getCfiLabel());
                    i++;
                }
                PageReport pageReport = new PageReport();
                CatalogEJB catalogEJB = new CatalogEJB();
                String parametercount[] = new String[1];
                parametercount[0] = "null";
                String c = request.getParameter("c");
                request.setAttribute(c, c);

                BigDecimal count = catalogEJB.countResul(c, parametercount);//consulta de cantidad de registros de la anterior consulta
                int countdata = count.intValue();
                String fullContextPath = FacesUtil.getFullRequestContextPath() != null ? FacesUtil.getFullRequestContextPath() : "";
                String url =  fullContextPath + "/view/comun/catalogdata.jsp?id=" + id + "&c=" + c + "&ctl=" + ctl;
                String page = pageReport.create(1, countdata, 10, url, "&");
                request.setAttribute("page", page);
            } catch (ApplicationException | NumberFormatException ex) {
                LOGGER.error("Error al momento de filtrar el formulario. EX000: " + ex.getMessage(), ex);
            } catch (Exception ex) {
                LOGGER.error("Error al momento de filtrar el formulario. EX000: " + ex.getMessage(), ex);
            }
            HttpSession session = request.getSession(true);
            if (filters != null) {
                session.setAttribute("filter", ID);
                session.setAttribute("label", Label);
                session.setAttribute("column", Columna);
                session.setAttribute("totalfilter", i);
                session.setAttribute("idconsulta", consulta);

            }
            List catalog_detalle = null;
            catalog_detalle = (ArrayList) dataResult.getListNameColumn();
            List<DataRow> resultfilter = null;
            resultfilter = (ArrayList) dataResult.getListDataRow();
            List col = null;
            Integer totaldata = resultfilter.size();
            String matriz[][] = new String[totaldata][100];
            for (int j = 0; j < resultfilter.size(); j++) {
                col = (ArrayList) resultfilter.get(j).getColumns();
                for (int k = 0; k < col.size(); k++) {
                    matriz[j][k] = resultfilter.get(j).getColumn(k);
                }
            }
            session.setAttribute("resultfilter", resultfilter);
            session.setAttribute("nomcolumn", catalog_detalle);
            session.setAttribute("dataResult", dataResult);
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            ArrayList ar1 = new ArrayList();
            ArrayList nomc = new ArrayList();
            ArrayList ar2 = new ArrayList();
            session.setAttribute("CourseID", ar1);
            session.setAttribute("nombc", nomc);
            session.setAttribute("CourseName", ar2);
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método  "
                    + "" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de filtrar el formulario. EX000: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método  "
                    + "" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de filtrar el formulario. EX000: " + ex.getMessage(), ex);
        }finally {
            out.close();
        }
    }

    /**
     *
     * @param request
     * @param response
     * @param data
     * @param ctld
     * @throws ApplicationException
     */
    public void queryDataResultado(HttpServletRequest request, HttpServletResponse response, String data[], String ctld) throws ApplicationException {
        PrintWriter out = null;
        try {
            CatalogFilter catalogfilter = new CatalogFilter();
            DataResult dataResult = new DataResult();
            HttpSession session = request.getSession(true);
            List<CatalogFilter> filters = null;
            String parameter[] = new String[2];
            String index = request.getParameter("index");
            PageReport pageReport = new PageReport();
            CatalogEJB catalogEJB = new CatalogEJB();
            String parametercount[] = new String[1];
            parametercount[0] = "null";
            String c = request.getParameter("ctl");
            String id = request.getParameter("idp");
            request.setAttribute("idp", id);
            if ("1".equals(index)) {
                parameter[0] = "null";
                parameter[1] = "";

            } else {
                int inddata = Integer.parseInt(index);
                int inddatap = (inddata + 10) - 1;
                int desde = inddata - 1;
                String numpag = inddatap + "";
                String desdep = desde + "";
                parameter[0] = "null";
                parameter[1] = "";

            }
            List filt = new ArrayList();
            ArrayList nombre = new ArrayList();
            ArrayList ID = new ArrayList();
            ArrayList Label = new ArrayList();
            ArrayList Columna = new ArrayList();
            String consulta = "";
            int i = 0;
            CatalogAdc catalogAdc = new CatalogAdc();
            BigDecimal idb = new BigDecimal(id);
            catalogAdc = ComunDelegate.queryCatalog(idb);
            filters = catalogAdc.getCatalogFilterList();
            BigDecimal idc = catalogAdc.getCtlId();
            session.setAttribute("ctl_id", idc);
            session.setAttribute("alias", catalogAdc.getCtlAlias());
            consulta = catalogAdc.getCtlSql();
            for (CatalogFilter filte : filters) {
                ID.add(filte.getCfiId());
                Columna.add(filte.getCfiColumn());
                Label.add(filte.getCfiLabel());
                i++;
            }
            String ctl_consulta = request.getParameter("ctlconsulta");
            dataResult = ComunDelegate.queryDataResult(ctl_consulta, data);
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
                String matriz[][] = new String[30][100];
                for (int j = 0; j < resultfilter.size(); j++) {
                    col = (ArrayList) resultfilter.get(j).getColumns();
                    for (int k = 0; k < col.size(); k++) {
                        matriz[j][k] = resultfilter.get(j).getColumn(k);
                    }
                }

                BigDecimal count = catalogEJB.countResul(c, parametercount);//consulta de cantidad de registros de la anterior consulta
                int countdata = count.intValue();

                session.setAttribute("resultfilter", resultfilter);
                session.setAttribute("nomcolumn", catalog_detalle);
                session.setAttribute("dataResult", dataResult);
            }
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            ArrayList ar1 = new ArrayList();
            ArrayList nomc = new ArrayList();
            ArrayList ar2 = new ArrayList();
            if (filters != null) {
                session.setAttribute("filter", ID);
                session.setAttribute("label", Label);
                session.setAttribute("column", Columna);
                session.setAttribute("totalfilter", i);
                session.setAttribute("idconsulta", consulta);

                session.setAttribute("CourseID", ar1);
                session.setAttribute("nombc", nomc);
            }
            session.setAttribute("existdata", existdata);
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método  "
                    + "" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de consultar la información. EX000: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método  "
                    + "" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de consultar la información. EX000: " + ex.getMessage(), ex);
        }
           finally {
            out.close();
        }
    }
}
