/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Admin
 */
import co.com.claro.mgl.businessmanager.cm.CmtTecnologiaSubMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtTecnologiaSubedificioMglFacade implements CmtTecnologiaSubedificioMglFacadeLocal {

    /**
     * Busca las tecnologias asociadas a un subedificio.
     *
     * @author Victor Bocanegra
     * @param cmtSubEdificioMgl SubEdificio
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    @Override
    public List<CmtTecnologiaSubMgl> findTecnoSubBySubEdi(
            CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {

        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        return cmtTecnologiaSubMglManager.findTecnoSubBySubEdi(cmtSubEdificioMgl);
    }

    /**
     * Busca las tecnologias asociadas a un subedificio por tecnologia.
     *
     * @author Victor Bocanegra
     * @param cmtSubEdificioMgl 
     * @param cmtBasicaMgl 
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    @Override
    public List<CmtTecnologiaSubMgl> findTecnoSubBySubEdiTec(CmtSubEdificioMgl cmtSubEdificioMgl, CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {

        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        return cmtTecnologiaSubMglManager.findTecnoSubBySubEdiTec(cmtSubEdificioMgl, cmtBasicaMgl);
    }

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
    @Override
    public CmtTecnologiaSubMgl crearTecSub(CmtSubEdificiosVt cmtSubEdificiosVt, 
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl, BigDecimal idVt,String usuarioCrea, int perfilCrea) throws ApplicationException {

        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        return cmtTecnologiaSubMglManager.crearTecSub(cmtSubEdificiosVt, cmtOrdenTrabajoMgl, idVt, usuarioCrea, perfilCrea);
    }

    /**
     * Metodo que lista los sub edificios sin una tecnologia en espesifico que
     * aplicarian para una Orden
     *
     * @param cmtOrdenTrabajoMgl
     * @return CmtTecnologiaSubMgl
     * @author Carlos Leonardo Villamil Hitss
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<CmtSubEdificioMgl> getSubEdificiosWithOutTecnology(
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl) throws ApplicationException {
        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        return cmtTecnologiaSubMglManager.getSubEdificiosWithOutTecnology(cmtOrdenTrabajoMgl);
    }
}