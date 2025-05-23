package co.com.telmex.catastro.services.seguridad;

import com.jlcg.db.AccessData;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase AuditoriaEJB
 * implementa AuditoriaEJBRemote
 *
 * @author 	Nataly Orozco Torres.
 * @version	1.0
 */
@Stateless(name = "auditoriaEJB", mappedName = "auditoriaEJB", description = "auditoria")
@Remote({AuditoriaEJBRemote.class})
public class AuditoriaEJB implements AuditoriaEJBRemote {

    /**
     * 
     */
    public AuditoriaEJB() {
    }

    /**
     * 
     * @param funcionalidad
     * @param nombreTabla
     * @param usuario
     * @param operacion
     * @param stringObject
     * @param adb
     * @return
     */
    @Override
    public int auditar(String funcionalidad, String nombreTabla, String usuario, String operacion, String stringObject, AccessData adb) {
        int result = 1;
        return result;
    }

    /**
     * 
     * @param funcionalidad
     * @param nombreTabla
     * @param usuario
     * @param operacion
     * @param queryData
     * @param adb
     * @return
     */
    @Override
    public int auditarCatalogo(String funcionalidad, String nombreTabla, String usuario, String operacion, String queryData, AccessData adb) {
        int result = 1;
        return result;
    }
}
