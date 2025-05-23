/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Admin
 */
@Local
public interface CmtTecnologiaSubedificioMglFacadeLocal {

    /**
     * Busca las tecnologias asociadas a un subedificio.
     *
     * @author Victor Bocanegra
     * @param cmtSubEdificioMgl SubEdificio
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    List<CmtTecnologiaSubMgl> findTecnoSubBySubEdi(
            CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException;

    /**
     * Busca las tecnologias asociadas a un subedificio por tecnologia.
     *
     * @author Victor Bocanegra
     * @param cmtSubEdificioMgl 
     * @param cmtBasicaMgl
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    List<CmtTecnologiaSubMgl> findTecnoSubBySubEdiTec(CmtSubEdificioMgl cmtSubEdificioMgl, CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException;

    /**
     * Metodo que crear una tecnologia asociada a un subedificio VT
     *
     * @param cmtSubEdificiosVt
     * @param cmtOrdenTrabajoMgl
     * @param idVt
     * @param usuarioCrea
     * @param perfilCrea
     * @return CmtTecnologiaSubMgl
     * @author Victor Bocanegra
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    CmtTecnologiaSubMgl crearTecSub(CmtSubEdificiosVt cmtSubEdificiosVt, CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl, BigDecimal idVt, String usuarioCrea, int perfilCrea) throws ApplicationException;
    
    /**
     * Metodo que lista los sub edificios sin una tecnologia en 
     * espesifico que aplicarian para una Orden
     *
     * @param cmtOrdenTrabajoMgl
     * @return  List<CmtSubEdificioMgl>
     * @author Carlos Leonardo Villamil Hitss
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtSubEdificioMgl> getSubEdificiosWithOutTecnology(
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl) throws ApplicationException;
    
}
