/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtReenvioTcrmMglDaoImpl;
import co.com.claro.mgl.jpa.entities.cm.CmtReenvioTcrmMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 *
 * @author valbuenayf
 */
public class CmtReenvioTcrmMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtReenvioTcrmMglManager.class);

    /**
     * metodo para listar todos los mensajes pendientes para enviar TCRM
     *
     * @return
     */
    public List<CmtReenvioTcrmMgl> findAllCmtReenvioTcrmMgl() {
        List<CmtReenvioTcrmMgl> resultList = null;
        try {
            CmtReenvioTcrmMglDaoImpl cmtReenvioTcrmMglDaoImpl = new CmtReenvioTcrmMglDaoImpl();
            resultList = cmtReenvioTcrmMglDaoImpl.findAllCmtReenvioTcrmMgl();
        } catch (Exception e) {
            LOGGER.error("Error en findAllCmtReenvioTcrmMgl de CmtReenvioTcrmMglManager" + e);
        }
        return resultList;

    }

    /**
     * metodo para crear los mensajes pendientes para enviar TCRM
     *
     * @param cmtReenvioTcrmMgl
     * @return
     */
    public CmtReenvioTcrmMgl crearCmtReenvioTcrmMgl(CmtReenvioTcrmMgl cmtReenvioTcrmMgl) {
        CmtReenvioTcrmMgl respuesta = null;
        try {
            CmtReenvioTcrmMglDaoImpl cmtReenvioTcrmMglDaoImpl = new CmtReenvioTcrmMglDaoImpl();
            respuesta = cmtReenvioTcrmMglDaoImpl.crearCmtReenvioTcrmMgl(cmtReenvioTcrmMgl);
        } catch (Exception e) {
                String msg = "Error en findAllCmtReenvioTcrmMgl de CmtReenvioTcrmMglManager '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
        }
        return respuesta;

    }
}
