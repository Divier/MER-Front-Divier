/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mer.dtos.sp.cursors.CtmGestionSegCMDto;
import co.com.claro.mgl.dao.impl.cm.CtmGestionSegCMDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CtmGestionSegCM;
import java.math.BigDecimal;
import javax.persistence.NoResultException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manager de la pagina de seguridad. Permite manejar
 * a los datos de la cuenta matriz
 *
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
public class CtmGestionSegCMManager {

    private static final Logger LOGGER = LogManager.getLogger(CtmGestionSegCMManager.class);

    /**
     * guarda y actualiza la tabla
     * @param ctmGestionSegCMDto
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CtmGestionSegCMDto create(CtmGestionSegCMDto ctmGestionSegCMDto) throws ApplicationException {

        try {
            CtmGestionSegCMDaoImpl daoImpl = new CtmGestionSegCMDaoImpl();
            return daoImpl.save(ctmGestionSegCMDto);
        } catch (ApplicationException e) {
            LOGGER.error("Error al insertar la gestion de seguridad de la cuenta matriz".concat(e.getMessage()));
            throw new ApplicationException(e.getMessage(), e);

        }
    }
    
     /**
     *
     * @param ctmGestionSegCM
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CtmGestionSegCM update(CtmGestionSegCM ctmGestionSegCM) throws ApplicationException {

        try {
            CtmGestionSegCMDaoImpl daoImpl = new CtmGestionSegCMDaoImpl();
            return daoImpl.update(ctmGestionSegCM);
        } catch (ApplicationException e) {
            LOGGER.error("Error al actualizar la gestion de seguridad de la cuenta matriz".concat(e.getMessage()));
            throw new ApplicationException(e.getMessage(), e);

        }
    }

    /**
     *
     * @param cuentaMatriz
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CtmGestionSegCMDto findAllManagementAccount(BigDecimal cuentaMatriz) throws ApplicationException {

        try {
            CtmGestionSegCMDaoImpl daoImpl = new CtmGestionSegCMDaoImpl();
            return daoImpl.findAllManagementAccount(cuentaMatriz);
        } catch (NoResultException e) {
            return new CtmGestionSegCMDto();
        } catch (ApplicationException e) {
            LOGGER.error("Error al guardar el registro de gestion de seguridad de cuenta matrices".concat(e.getMessage()));
            throw new ApplicationException(e.getMessage(), e);

        }
    }
}
