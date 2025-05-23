package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import javax.ejb.Remote;

/**
 * Interfase ConsultaDireccionMasivaEJBRemote
 *
 * @author 	Ana María Malambo.
 * @version	1.0
 */
@Remote
public interface ConsultaDireccionMasivaEJBRemote {

    /**
     * 
     * @param dirFormatoIGAC
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean existAddressRepo(String dirFormatoIGAC) throws ApplicationException;

    /**
     * 
     * @param subdirFormatoIGAC
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean existSubAddressRepo(String subdirFormatoIGAC) throws ApplicationException;
}
