package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import javax.ejb.Remote;

/**
 * Interfase CargueDireccionesBatchEJBRemote
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
@Remote
public interface CargueDireccionesBatchEJBRemote {

    /**
     * 
     * @param fileFlat
     * @param user
     * @return
     * @throws java.io.UnsupportedEncodingException
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws java.lang.Exception
     */
    public byte[] processFileAddress(java.lang.StringBuffer fileFlat, String user) throws java.io.UnsupportedEncodingException, ApplicationException;
}
