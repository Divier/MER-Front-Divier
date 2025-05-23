
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.RrNodosDthManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RrNodosDth;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class RrNodosDthFacade implements RrNodosDthFacadeLocal {

    @Override
    public List<RrNodosDth> findNodosDthByCodCiudad(String codCiudad) throws ApplicationException {
        RrNodosDthManager nodosManager = new RrNodosDthManager();
        return nodosManager.findNodosDthByCodCiudad(codCiudad);
    }
}
