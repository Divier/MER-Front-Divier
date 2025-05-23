package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.services.util.ConsultaMasivaTable;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase ConsultaMasivaEJBRemote
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
@Remote
public interface ConsultaMasivaEJBRemote {

    /**
     * 
     * @param calle
     * @param cod_ciudad
     * @param cantMaxregistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<ConsultaMasivaTable> queryConsultaMasivaPorCruce(String calle, String cod_ciudad, String cantMaxregistros) throws ApplicationException;

    /**
     * 
     * @param nodo
     * @param ctaMatriz
     * @param tipoRed
     * @param estrato
     * @param nivelSocio
     * @param barrio
     * @param cod_ciudad
     * @param cantMaxregistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<ConsultaMasivaTable> queryConsultaMasivaPorPropiedades(String nodo, String ctaMatriz, String tipoRed, String estrato, String nivelSocio, String barrio, String cod_ciudad, String cantMaxregistros) throws ApplicationException;

    /**
     * 
     * @param acronimo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public int queryCantMaxRegistrosFromParametros(String acronimo) throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Nodo> queryNodos() throws ApplicationException;
}
