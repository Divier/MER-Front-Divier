/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtAliadoMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtAliados;
import java.math.BigDecimal;

/**
 *
 * @author bocanegravm
 */
public class CmtAliadosMglManager {

    private CmtAliadoMglDaoImpl dao = new CmtAliadoMglDaoImpl();

    /**
     * Busca un Aliado en el repositorio.
     *
     * @author Victor Bocanegra
     * @param idAliado
     * @return un la informacion completa de un Aliado
     * @throws ApplicationException
     */
    public CmtAliados findByIdAliado(BigDecimal idAliado)
            throws ApplicationException {

        return dao.findByIdAliado(idAliado);
    }
}
