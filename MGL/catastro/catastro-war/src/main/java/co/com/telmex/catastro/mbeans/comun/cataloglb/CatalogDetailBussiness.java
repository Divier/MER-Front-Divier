package co.com.telmex.catastro.mbeans.comun.cataloglb;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.DataMetaTable;
import co.com.telmex.catastro.data.DataResult;
import co.com.telmex.catastro.data.DataRow;
import co.com.telmex.catastro.data.DataTable;
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
 * Clase CatalogDetailBussiness
 *
 * @author Ana María Malambo
 * @version	1.0
 */
public class CatalogDetailBussiness {

    private static final Logger LOGGER = LogManager.getLogger(CatalogDetailBussiness.class);

    /**
     *
     * @param request
     * @param response
     * @param id
     * @param idf
     * @param op
     * @param rol
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void buildForms(HttpServletRequest request, HttpServletResponse response, String id, String idf, String op) throws ApplicationException {

        try {
            DataMetaTable dataMetaTable = new DataMetaTable();
            String[] parameter = new String[3];
            parameter[0] = id;
            parameter[1] = "1";
            parameter[2] = idf;
            BigDecimal idd = new BigDecimal(id);
            dataMetaTable = ComunDelegate.queryDataMetaTable(idd, parameter);
            String table = dataMetaTable.getTablename();

            ArrayList column = new ArrayList();
            ArrayList columnsystem = new ArrayList();
            ArrayList edit = new ArrayList();
            ArrayList columnnull = new ArrayList();
            ArrayList valores = new ArrayList();
            ArrayList tipocolum = new ArrayList();
            ArrayList relacion = new ArrayList();
            ArrayList ctlidquery = new ArrayList();
            ArrayList visibility = new ArrayList();
            ArrayList nombrer = new ArrayList();
            ArrayList longitud = new ArrayList();

            ArrayList pk = new ArrayList();
            int i = 0;
            if (dataMetaTable != null) {
                List<DataTable> datacolums = dataMetaTable.getDatatable();
                for (DataTable data : datacolums) {
                    String b = data.getIsnull();
                    column.add(data.getName());
                    columnsystem.add(data.getNamesystem());
                    tipocolum.add(data.getType());
                    valores.add(data.getValue());
                    edit.add(data.getIsedit());
                    relacion.add(data.getNametTable());
                    ctlidquery.add(data.getCtlfk());
                    columnnull.add(data.getIsnull());
                    visibility.add(data.getIsvisibility());
                    longitud.add(data.getLongitud());

                    nombrer.add(data.getNamecamporel());
                    pk.add(data.getPk());
                    i++;
                }
                HttpSession session = request.getSession(true);
                session.setAttribute("create", dataMetaTable.getSqlCreate());
                session.setAttribute("update", dataMetaTable.getSqlUpdate());
                session.setAttribute("delete", dataMetaTable.getSqlDelete());
                session.setAttribute("tablarel", dataMetaTable.getRelacionalias());
                session.setAttribute("op", op);

                if (dataMetaTable.getRel().equals("") == false) {
                    String tabla = dataMetaTable.getRel();
                    String[] parameter3 = new String[1];
                    parameter3[0] = idf;
                    DataResult dataResults = new DataResult();
                    String[] parameter4 = new String[2];
                    parameter4[0] = id;
                    parameter4[1] = "3";
                    dataResults = ComunDelegate.queryDataResult(tabla, parameter3);
                    if (dataResults == null) {
                        String resdata = "0";
                        request.setAttribute("resdata", resdata);
                    } else {
                        String resdata = "1";
                        request.setAttribute("resdata", resdata);
                        List catalog_detalle = null;
                        catalog_detalle = (ArrayList) dataResults.getListNameColumn();

                        List<DataRow> resultfilter = null;
                        resultfilter = (ArrayList) dataResults.getListDataRow();
                        List col = null;
                        ArrayList valorc = null;
                        String colum = "";
                        String matriz[][] = new String[resultfilter.size()][100];
                        for (int j = 0; j < resultfilter.size(); j++) {
                            col = (ArrayList) resultfilter.get(j).getColumns();
                            for (int k = 0; k < col.size(); k++) {
                                matriz[j][k] = resultfilter.get(j).getColumn(k);
                            }
                        }
                        request.setAttribute("nomcolumnrel", catalog_detalle);
                        request.setAttribute("dataResultrel", dataResults);
                    }
                }
                String crea = dataMetaTable.getButtonCreate();
                String modifica = dataMetaTable.getButtonUpdate();
                String elimina = dataMetaTable.getButtonDelete();
                request.setAttribute("crea", crea);
                request.setAttribute("modifica", modifica);
                request.setAttribute("elimina", elimina);
                request.setAttribute("id", idf);
                session.setAttribute("tot", i);

            }

            ArrayList catalog_detalle = new ArrayList();
            ArrayList catalog_detalle_tipo = new ArrayList();

            request.setAttribute("datatable", column);
            request.setAttribute("datatable_tipo", tipocolum);
            request.setAttribute("valores", valores);
            request.setAttribute("edit", edit);
            request.setAttribute("columnnull", columnnull);
            request.setAttribute("relacion", relacion);
            request.setAttribute("query", ctlidquery);
            request.setAttribute("visibility", visibility);
            request.setAttribute("nombrer", nombrer);
            request.setAttribute("longitud", longitud);
            request.setAttribute("pk", pk);
            request.setAttribute("id", idf);

        } catch (Exception ex) {
            LOGGER.error("Error al momento de construir el formulario. EX000: " + ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @param operation
     * @param table_name
     * @param data
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void OperationForm(HttpServletRequest request, HttpServletResponse response, String operation, String table_name, List data) throws ApplicationException {
    }
}
