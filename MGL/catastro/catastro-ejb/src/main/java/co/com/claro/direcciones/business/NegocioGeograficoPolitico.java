/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.direcciones.business;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

/**
 *
 * @author carlos.villa.ext
 */
public class NegocioGeograficoPolitico {
    
    private static final Logger LOGGER = LogManager.getLogger(NegocioGeograficoPolitico.class);

    public GeograficoPolitico queryGeograficoPolitico(BigDecimal gpo_id)  throws ApplicationException{
        GeograficoPolitico geograficoPo = null;
        AccessData adb = null ;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("gpo1", gpo_id.toString());
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                geograficoPo = new GeograficoPolitico();
                geograficoPo.setGpoId(obj.getBigDecimal("GEOGRAFICO_POLITICO_ID"));
                geograficoPo.setGpoNombre(obj.getString("NOMBRE"));
                geograficoPo.setGpoTipo(obj.getString("TIPO"));
                geograficoPo.setGpoCodigoDane(obj.getString("CODIGO"));
                BigDecimal geo_gpo_id = obj.getBigDecimal("GEOGRAFICO_GEO_POLITICO_ID");
                geograficoPo.setGpoCodTipoDireccion(obj.getString("COD_TIPO_DIRECCION"));
                geograficoPo.setGpoMultiorigen(obj.getString("MULTIORIGEN"));
                if (geo_gpo_id != null) {
                    GeograficoPolitico geo_gpo = queryGeograficoPolitico(geo_gpo_id);
                    geograficoPo.setGeograficoPolitico(geo_gpo);
                }
            }
            return geograficoPo;
        } catch ( ExceptionDB e) {
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de obtener la información geográfica. EX000: " + e.getMessage(), e);
        }catch (ApplicationException e){
            LOGGER.error(e.getMessage());
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de obtener la información geográfica. EX000: " + e.getMessage(), e);
        }      
    }
}
