package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.Direccion;
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
 * Representa las Subdirecciones agregadas a una dirección implementa
 * Serialización
 *
 * @author Ana María Malambo.
 * @version	1.0
 */
@Stateless(name = "consultaDireccionMasivaWebServiceEJB", mappedName = "consultaDireccionWebServiceEJB", description = "consultaDireccionWebService")
@Remote({ConsultaDireccionMasivaWebServiceEJBRemote.class})
public class ConsultaDireccionMasivaWebServiceEJB implements ConsultaDireccionMasivaWebServiceEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(ConsultaDireccionMasivaWebServiceEJB.class);
    private static String MESSAGE_OK = "La operacion fue correcta.";
    private static String MESSAGE_ERROR = "Fallo en la operacion";

    /*
     *Valida si la direccion existe en el repositorio
     */
    /**
     *
     * @param dirFormatoIGAC
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean existAddressRepo(String dirFormatoIGAC) throws ApplicationException {
        boolean res = false;
        List<Direccion> direcciones = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("dir1", dirFormatoIGAC);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                direcciones = new ArrayList<Direccion>();
                for (DataObject obj : list.getList()) {
                    BigDecimal dir_id = obj.getBigDecimal("DIR_ID");
                    Direccion dir = new Direccion();
                    dir.setDirId(dir_id);
                    direcciones.add(dir);
                }
            }
            if (direcciones != null) {
                res = true;
            }
            return res;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de verificar la existencia de la dirección. EX000: " + e.getMessage(), e);
        }
    }

    /*
     *Valida si la subdireccion existe en el repositorio
     */
    /**
     *
     * @param subdirFormatoIGAC
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean existSubAddressRepo(String subdirFormatoIGAC) throws ApplicationException {
        boolean res = false;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("sdi1", subdirFormatoIGAC);
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                res = true;
            }
            return res;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de verificar la existencia de la subdirección. EX000: " + e.getMessage(), e);
        }
    }
}
