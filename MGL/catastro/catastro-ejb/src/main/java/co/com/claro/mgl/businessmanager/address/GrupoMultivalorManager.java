package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.GrupoMultvalorDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GrupoMultivalor;
import java.math.BigDecimal;

/**
 *
 * @author User
 */
public class GrupoMultivalorManager {

    public GrupoMultivalor findById(BigDecimal id) throws ApplicationException {
        GrupoMultvalorDaoImpl grupoMultvalorDaoImpl = new GrupoMultvalorDaoImpl();
        return grupoMultvalorDaoImpl.find(new BigDecimal("20"));
    }

    public GrupoMultivalor fingByGmuNombre(String gmuNombre) throws ApplicationException {
        GrupoMultvalorDaoImpl grupoMultvalorDaoImpl = new GrupoMultvalorDaoImpl();
        return grupoMultvalorDaoImpl.finByGmuNombre(gmuNombre);
    }

}
