package co.com.telmex.catastro.services.seguridad;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.Privilegios;
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
 * Clase PrivilegiosRolEJB implementa PrivilegiosRolEJBRemote
 *
 * @author Ana María Malambo
 * @version	1.0
 */
@Stateless(name = "privilegiosRolEJB", mappedName = "privilegiosRolEJB", description = "privilegiosRol")
@Remote({PrivilegiosRolEJBRemote.class})
public class PrivilegiosRolEJB implements PrivilegiosRolEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(PrivilegiosRolEJB.class);

    /**
     *
     * @param rol
     * @param funcionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<Privilegios> queryPrivilegiosRol(BigDecimal rol, String funcionalidad) throws ApplicationException {
        List<Privilegios> listRol = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            //query que recibe el id del rol y el nombre de 
            //la funcionalidad a consultar.Retorna lista de campos con
            //el atributo estilo y si es visible 0 no (0 no visible).

            DataList list = adb.outDataList("ctl220", rol.toString(), funcionalidad);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listRol = new ArrayList<Privilegios>();
                for (DataObject obj : list.getList()) {
                    Privilegios privilegio = new Privilegios();
                    String nombre = obj.getString("ATR_NOMBRE");
                    String estilo = obj.getString("ATR_ESTILO");
                    privilegio.setAtrNombre(nombre);
                    privilegio.setAtrEstilo(estilo);
                    listRol.add(privilegio);
                }
            }
            return listRol;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar los privilegios del rol. EX000: " + e.getMessage(), e);
        }
    }
}
