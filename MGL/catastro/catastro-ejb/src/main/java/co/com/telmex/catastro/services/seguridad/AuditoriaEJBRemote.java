package co.com.telmex.catastro.services.seguridad;

import com.jlcg.db.AccessData;
import javax.ejb.Remote;

/**
 * Interfase AuditoriaEJBRemote
 *
 * @author 	Nataly Orozco Torres.
 * @version	1.0
 */
@Remote
public interface AuditoriaEJBRemote {

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
    public int auditar(String funcionalidad, String nombreTabla, String usuario, String operacion, String stringObject, AccessData adb);

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
    public int auditarCatalogo(String funcionalidad, String nombreTabla, String usuario, String operacion, String queryData, AccessData adb);
}