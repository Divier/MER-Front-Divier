package co.com.telmex.catastro.mbeans.comun.cataloglb;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.DataMetaTable;
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
 * Clase CatalogRelationBussiness
 * Extiende de HttpServlet
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class CatalogRelationBussiness {

    private static final Logger LOGGER = LogManager.getLogger(CatalogRelationBussiness.class);
    
    /**
     * 
     * @param request
     * @param response
     * @param id
     * @param idf
     * @param nombre
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void buildRelation(HttpServletRequest request, HttpServletResponse response, String id, String idf, String nombre) throws ApplicationException {

        try {
            String[] parameter = new String[3];
            parameter[0] = id;
            parameter[1] = "4";
            parameter[2] = idf;


            BigDecimal idd = new BigDecimal(id);
            DataMetaTable dataMetaTable = ComunDelegate.queryDataMetaTable(idd, parameter);
            ArrayList column = new ArrayList<>();
            ArrayList edit = new ArrayList<>();
            ArrayList columnnull = new ArrayList<>();
            ArrayList valores = new ArrayList<>();
            ArrayList tipocolum = new ArrayList<>();
            ArrayList relacion = new ArrayList<>();
            ArrayList ctlidquery = new ArrayList<>();
            ArrayList visibility = new ArrayList<>();
            ArrayList nombrer = new ArrayList<>();
            ArrayList pk = new ArrayList<>();
            int i = 0;
            if (dataMetaTable != null) {
                List<DataTable> datacolums = dataMetaTable.getDatatable();
                if (datacolums != null) {
                    for (DataTable data : datacolums) {
                        column.add(data.getName());
                        tipocolum.add(data.getType());
                        valores.add(data.getValue());

                        edit.add(data.getIsedit());
                        relacion.add(data.getNametTable());
                        ctlidquery.add(data.getCtlfk());
                        columnnull.add(data.getIsnull());
                        visibility.add(data.getIsvisibility());
                        nombrer.add(data.getNamecamporel());
                        pk.add(data.getPk());
                        i++;
                    }

                }
                HttpSession session = request.getSession(true);
                session.setAttribute("create", dataMetaTable.getSqlCreate());
                session.setAttribute("update", dataMetaTable.getSqlUpdate());
                session.setAttribute("delete", dataMetaTable.getSqlDelete());


                String crea = dataMetaTable.getButtonCreate();
                String modifica = dataMetaTable.getButtonUpdate();
                String elimina = dataMetaTable.getButtonDelete();
                request.setAttribute("crea", crea);
                request.setAttribute("modifica", modifica);
                request.setAttribute("elimina", elimina);
                request.setAttribute("id", idf);
                session.setAttribute("tot", i);
            }

            request.setAttribute("datatable", column);
            request.setAttribute("datatable_tipo", tipocolum);
            request.setAttribute("valores", valores);
            request.setAttribute("edit", edit);
            request.setAttribute("columnnull", columnnull);
            request.setAttribute("relacion", relacion);
            request.setAttribute("query", ctlidquery);
            request.setAttribute("visibility", visibility);
            request.setAttribute("nombrer", nombrer);
            request.setAttribute("pk", pk);
        } catch (Exception ex) {
            LOGGER.error("Error al momento de construir la relación. EX000: " + ex.getMessage(), ex);
        }
    }
}
