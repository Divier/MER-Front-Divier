/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.direcciones.business;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author carlos.villa.ext
 */
public class NegocioLocalidad {

    private static final Logger LOGGER = LogManager.getLogger(NegocioLocalidad.class);

    public Geografico queryGeograficoLocalidadByNombre(String idCity, String nombreLocalidad) throws ApplicationException, ExceptionDB {
        Geografico geograficoLocalidad = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("Loc01", idCity, nombreLocalidad, Constant.ID_TGE_LOCALIDAD.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                geograficoLocalidad = new Geografico();
                geograficoLocalidad.setGeoId(obj.getBigDecimal("GEO_ID"));
                geograficoLocalidad.setGeoNombre(obj.getString("GEO_NOMBRE"));
            }
            return geograficoLocalidad;
        } catch ( ExceptionDB e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de obtener la información geográfica. EX000: " + e.getMessage(), e);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de obtener la información geográfica. EX000: " + e.getMessage(), e);
        }
    }
}
