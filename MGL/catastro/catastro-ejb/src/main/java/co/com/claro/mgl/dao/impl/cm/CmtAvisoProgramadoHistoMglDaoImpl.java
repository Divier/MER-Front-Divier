/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtAvisosProgramadosHistoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtAvisosProgramadosMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author rodriguezluim
 */
public class CmtAvisoProgramadoHistoMglDaoImpl extends GenericDaoImpl<CmtAvisosProgramadosHistoMgl>{
    
        private static final Logger LOGGER = LogManager.getLogger(CmtAvisoProgramadoHistoMglDaoImpl.class);
    
    public void createAvisoProgramadoHistorico(CmtAvisosProgramadosMgl capm) {
        try {
            CmtAvisosProgramadosHistoMgl caphm = new CmtAvisosProgramadosHistoMgl();
            caphm.setCasoTcrmId(capm.getCasoTcrmId());
            caphm.setFechaNotificacion(new Date());
            caphm.setTecnologia(capm.getTecnologia());
            caphm.setFechaCreacion(new Date());
            caphm.setUsuarioCreacion("HITSS");
            caphm.setEstadoRegistro(1);
            
            // Validacion HHPP
            if(capm.getHhppId() != null && capm.getHhppId() != BigDecimal.ZERO){
                caphm.setHhppId(capm.getHhppId());
            }
            // Validacion TECNOLOGIA
            if(capm.getTecnologiaSubedificioId()!= null && capm.getTecnologiaSubedificioId() != BigDecimal.ZERO){
                caphm.setTecnologiaSubedificioId(capm.getTecnologiaSubedificioId());
            }
            // Validacion SOLICITUD
            if(capm.getSolicitudId()!= null && capm.getSolicitudId() != BigDecimal.ZERO){
                caphm.setSolicitudId(capm.getSolicitudId());
            }
            create(caphm);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
    }
    
}
