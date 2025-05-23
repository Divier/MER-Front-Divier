package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import javax.ejb.Remote;

/**
 * Interfase CrearDirFactEJBRemote
 * Representa La información para la creación de direcciones de facturación
 *
 * @author 	Camilo E. Gaviria
 * @version	1.0
 */
@Remote
public interface CrearDirFactEJBRemote {

    /**
     * 
     * @param fileFlat
     * @param user
     * @return
     * @throws java.io.UnsupportedEncodingException
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws java.lang.Exception
     */
    public byte[] processFileAddress(java.lang.StringBuffer fileFlat, String user) throws java.io.UnsupportedEncodingException,ApplicationException;
}
