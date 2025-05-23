package co.com.telmex.catastro.mbeans.comun.cataloglb;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.DataResultquery;
import co.com.telmex.catastro.data.DataRow;
import co.com.telmex.catastro.delegate.ComunDelegate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase CatalogQueryRelationBussiness
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class CatalogQueryRelationBussiness {

    /**
     * 
     * @param request
     * @param response
     * @param ctlid
     * @param nombret
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void buildResul(HttpServletRequest request, HttpServletResponse response, String ctlid, String nombret) throws ApplicationException {
        DataResultquery dataResultquery = new DataResultquery();
        String[] parameter = new String[2];
        parameter[0] = nombret;
        parameter[1] = "4";

        dataResultquery = ComunDelegate.queryDataResultq(ctlid, parameter);

        List catalog_detalleq = null;
        catalog_detalleq = (ArrayList) dataResultquery.getListNameColumnq();
        List<DataRow> resultquery = null;
        resultquery = (ArrayList) dataResultquery.getListDataRowq();
        List col = null;
        ArrayList valorc = null;
        String colum = "";
        String matriz[][] = new String[30][100];
        for (int j = 0; j < resultquery.size(); j++) {
            col = (ArrayList) resultquery.get(j).getColumns();
            for (int k = 0; k < col.size(); k++) {
                matriz[j][k] = resultquery.get(j).getColumn(k);

            }
        }

        request.setAttribute("resultquery", resultquery);
        request.setAttribute("dataResultquery", dataResultquery);
        request.setAttribute("nomcolumn", catalog_detalleq);


        ArrayList catalog_detalle = new ArrayList();
        catalog_detalle.add("Id");
        catalog_detalle.add("Nombre");
        catalog_detalle.add("tipo");
        catalog_detalle.add("fecha");

        ArrayList catalog_detalle_tipo = new ArrayList();
        catalog_detalle_tipo.add("varchar2");
        catalog_detalle_tipo.add("varchar2");
        catalog_detalle_tipo.add("tipo");
        catalog_detalle_tipo.add("date");

        request.setAttribute("datatable", catalog_detalle);
        request.setAttribute("datatable_tipo", catalog_detalle_tipo);
    }
}