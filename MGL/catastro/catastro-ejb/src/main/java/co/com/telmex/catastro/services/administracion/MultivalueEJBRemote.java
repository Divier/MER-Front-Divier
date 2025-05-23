package co.com.telmex.catastro.services.administracion;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.GrupoMultivalor;
import co.com.telmex.catastro.data.Multivalor;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * Clase MultivalueEJBRemote
 *
 * @author 	Jose Luis Caicedo Gonzalez.
 * @version	1.0
 */
@Remote
public interface MultivalueEJBRemote {

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    List<GrupoMultivalor> queryGroupMultivalue() throws ApplicationException;

    /**
     * 
     * @param gmuId
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    List<Multivalor> queryMultivalue(BigDecimal gmuId) throws ApplicationException;

    /**
     * 
     * @param row
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    List<Multivalor> queryMultivalueTest(Long row) throws ApplicationException;

    /**
     * 
     * @param gmuId
     * @param row
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    List<Multivalor> queryMultivalueByGroup(BigDecimal gmuId, Long row) throws ApplicationException;

    /**
     * 
     * @param gmuId
     * @param mul_valor
     * @param mul_descripcion
     * @param user_crea
     * @param user_mod
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    BigDecimal insertMultivalueAtGroup(BigDecimal gmuId, String mul_valor, String mul_descripcion,
            String user_crea, String user_mod) throws ApplicationException;

    /**
     * 
     * @param mul_valor
     * @param mul_descripcion
     * @param user_mod
     * @param idMulti
     * @param idGroup
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    boolean updateMultivalueAtGroup(String mul_valor, String mul_descripcion, String user_mod,
            BigDecimal idMulti, BigDecimal idGroup) throws ApplicationException;

    /**
     * 
     * @param idMulti
     * @param idGroup
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    boolean deleteMultivalueAtGroup(BigDecimal idMulti, BigDecimal idGroup) throws ApplicationException;
}
