/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtTipoBasicaExtMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class CmtTipoBasicaExtMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtTipoBasicaExtMglManager.class);

    /**
     * Función obtiene lista de campos de la tabla basica ext
     *
     * @author Lenis Cardenas
     * @param cmtTipoBasicaMgl
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtTipoBasicaExtMgl> findCamposByTipoBasica(CmtTipoBasicaMgl cmtTipoBasicaMgl)
            throws ApplicationException {
        try {
            List<CmtTipoBasicaExtMgl> listaCmtTipoBasicaExtMgl;
            CmtTipoBasicaExtMglDaoImpl cmtTipoBasicaExtMglDaoImpl = new CmtTipoBasicaExtMglDaoImpl();
            listaCmtTipoBasicaExtMgl = cmtTipoBasicaExtMglDaoImpl.findCamposByTipoBasica(cmtTipoBasicaMgl);
            for (CmtTipoBasicaExtMgl cmtTipoBasicaExtMgl : listaCmtTipoBasicaExtMgl) {
                List<String> cadenaValores = Arrays.asList(cmtTipoBasicaExtMgl.getValoresPosibles().split(","));
                cmtTipoBasicaExtMgl.setCamposYN(cadenaValores);
            }
            return listaCmtTipoBasicaExtMgl;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    public List<CmtTipoBasicaExtMgl> findAll()
            throws ApplicationException {
        try {
            List<CmtTipoBasicaExtMgl> listaCmtTipoBasicaExtMgl;
            CmtTipoBasicaExtMglDaoImpl cmtTipoBasicaExtMglDaoImpl = new CmtTipoBasicaExtMglDaoImpl();
            listaCmtTipoBasicaExtMgl = cmtTipoBasicaExtMglDaoImpl.findAll();
            return listaCmtTipoBasicaExtMgl;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

}
