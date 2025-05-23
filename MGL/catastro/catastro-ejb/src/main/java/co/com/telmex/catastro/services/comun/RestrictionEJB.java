package co.com.telmex.catastro.services.comun;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.Restriccion;
import co.com.telmex.catastro.services.util.DireccionUtil;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase RestrictionEJB implementa RestrictionEJBRemote
 *
 * @author Ana María Malambo.
 * @version	1.0
 */
@Stateless(name = "restrictionEJB", mappedName = "restrictionEJB", description = "restriccion")
@Remote({RestrictionEJBRemote.class})
public class RestrictionEJB implements RestrictionEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(RestrictionEJB.class);

    /**
     *
     * @param rolId
     * @param nameTable
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<Restriccion> queryRestriction(BigDecimal rolId, String nameTable) throws ApplicationException{
        List<Restriccion> listRestriction = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            String[] parameterr = new String[2];
            parameterr[0] = rolId.toString();
            parameterr[1] = nameTable;
            DataList list = adb.outDataList("ctl131", (Object[]) parameterr);
            DireccionUtil.closeConnection(adb);

            if (list != null) {
                listRestriction = new ArrayList<Restriccion>();
                for (DataObject obj : list.getList()) {
                    Restriccion restriccion = new Restriccion();
                    restriccion.setTableName(obj.getString("ATR_NOMBRE"));
                    listRestriction.add(restriccion);
                }

            }
            return listRestriction;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar la restricción. EX000: " + e.getMessage(), e);
        }
    }
}
