/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtAprobacionesEstadoMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtAprobacionesEstadosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDetalleFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoIntxExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.Constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class CmtAprobacionesEstadoMglManager {

    CmtAprobacionesEstadoMglDaoImpl dao = new CmtAprobacionesEstadoMglDaoImpl();

    /**
     * Crea una Aprobacion de cambio de estado.Permite realizar la creacion de
 una Aprobacion de cambio de estado en el repositorio.
     *
     * @author Victor Bocanegra
     * @param aprobacionesEstadosMgl Aprobacion de cambio de estado a crear en
     * el repositorio
     * @param usuario
     * @param perfil
     * @return CmtAprobacionesEstadosMgl creada en el repositorio
     * @throws ApplicationException
     */
    public CmtAprobacionesEstadosMgl crearAprobacion(CmtAprobacionesEstadosMgl aprobacionesEstadosMgl, String usuario, int perfil)
            throws ApplicationException {

        aprobacionesEstadosMgl = dao.createCm(aprobacionesEstadosMgl, usuario, perfil);

        return aprobacionesEstadosMgl;
    }

    /**
     * Busca aprobaciones de cambio de estado por id de ot en el repositorio.
     *
     * @author Victor Bocanegra
     * @param idOt
     * @param id orden de trabajo
     * @return 
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl findLstAproByOt(BigDecimal idOt) throws ApplicationException {

        CmtOrdenTrabajoMgl ordenTrabajoMgl = null;
        CmtAprobacionesEstadosMgl aprobacionesEstadosMgl = dao.findLstAproByOt(idOt);

        if (aprobacionesEstadosMgl != null) {
            ordenTrabajoMgl = aprobacionesEstadosMgl.getOtObj();
        }
        return ordenTrabajoMgl;
    }

    /**
     * Busca las Ordenes de Trabajo cerradas externamente y aprobadas
     * financieramente
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param gpoDepartamento
     * @param departamento division de las solicitudes
     * @param gpoCiudad
     * @param ciudad comunidad de las solicitudes
     * @param tipoOt tipo de orden de trabajo
     * @param estadoInternoBasicaId estado interno de la ot
     * @param tecnologia de orden de trabajo
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByOtCloseAndApruebaFinan(List<BigDecimal> estadosxflujoList, 
            BigDecimal gpoDepartamento, BigDecimal gpoCiudad, CmtTipoOtMgl tipoOt, 
            BigDecimal estadoInternoBasicaId, BigDecimal tecnologia) throws ApplicationException {
       
        List<CmtAprobacionesEstadosMgl> otsAprobadasFinan = dao.findByOtCloseAndApruebaFinan
                (estadosxflujoList, gpoDepartamento, gpoCiudad, tipoOt, estadoInternoBasicaId,tecnologia);
        
        CmtEstadoIntxExtMglManager cmtEstadoIntxExtMglManager = new CmtEstadoIntxExtMglManager();
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        List<CmtOrdenTrabajoMgl> ordenes = new ArrayList<CmtOrdenTrabajoMgl>();
        CmtVisitaTecnicaMglManager visitaTecnicaMglManager = new CmtVisitaTecnicaMglManager();
        List<CmtSubEdificiosVt> subEdificiosVt;
        CmtSubEdificiosVtManager subEdificiosVtManager = new CmtSubEdificiosVtManager();


        if (otsAprobadasFinan != null) {
            if (!otsAprobadasFinan.isEmpty()) {
                for (CmtAprobacionesEstadosMgl aproOts : otsAprobadasFinan) {
                    CmtEstadoIntxExtMgl estadoInternoExterno;
                    CmtBasicaMgl cmtBasicaMgl = basicaMglManager.
                            findByCodigoInternoApp(Constant.BASICA_ESTADO_EXTERNO_CERRADO);
                    estadoInternoExterno = cmtEstadoIntxExtMglManager.
                            findByEstadoInterno(aproOts.getOtObj().getEstadoInternoObj());
                    if (cmtBasicaMgl.getBasicaId() != null) {
                        if (cmtBasicaMgl.getBasicaId().compareTo(estadoInternoExterno.getIdEstadoExt().getBasicaId()) == 0) {

                            CmtVisitaTecnicaMgl visitaTecnicaAct = visitaTecnicaMglManager.
                                    findVTActiveByIdOt(aproOts.getOtObj());
                            if (visitaTecnicaAct != null) {
                                if (visitaTecnicaAct.getIdVt() != null) {
                                    subEdificiosVt = subEdificiosVtManager.findByVtAndAcometida(visitaTecnicaAct);
                                    if (subEdificiosVt.size() > 0) {
                                        ordenes.add(aproOts.getOtObj());
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        return ordenes;

    }

    /**
     * Cuenta las Ordenes de Trabajo cerradas externamente y aprobadas
     * financieramente
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param gpoDepartamento
     * @param departamento division de las solicitudes
     * @param gpoCiudad
     * @param ciudad comunidad de las solicitudes
     * @param tipoOt tipo de orden de trabajo
     * @param estadoInternoBasicaId estado interno de la ot
     * @param tecnologia de orden de trabajo
     * @return int cuentas las ordenes
     * @throws ApplicationException
     */
    public int getCountOtCloseAndApruebaFinan(List<BigDecimal> estadosxflujoList, 
            BigDecimal gpoDepartamento, BigDecimal gpoCiudad, CmtTipoOtMgl tipoOt, 
            BigDecimal estadoInternoBasicaId, BigDecimal tecnologia) throws ApplicationException {

        List<CmtAprobacionesEstadosMgl> otsAprobadasFinan = dao.findByOtCloseAndApruebaFinan
                (estadosxflujoList, gpoDepartamento, gpoCiudad, tipoOt, estadoInternoBasicaId, tecnologia);
        
        CmtEstadoIntxExtMglManager cmtEstadoIntxExtMglManager = new CmtEstadoIntxExtMglManager();
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        List<CmtOrdenTrabajoMgl> ordenes = new ArrayList<CmtOrdenTrabajoMgl>();
        CmtVisitaTecnicaMglManager visitaTecnicaMglManager = new CmtVisitaTecnicaMglManager();
        List<CmtSubEdificiosVt> subEdificiosVt;
        CmtSubEdificiosVtManager subEdificiosVtManager = new CmtSubEdificiosVtManager();

        if (otsAprobadasFinan != null) {
            if (!otsAprobadasFinan.isEmpty()) {
                for (CmtAprobacionesEstadosMgl aproOts : otsAprobadasFinan) {
                    CmtEstadoIntxExtMgl estadoInternoExterno;
                    CmtBasicaMgl cmtBasicaMgl = basicaMglManager.
                            findByCodigoInternoApp(Constant.BASICA_ESTADO_EXTERNO_CERRADO);
                    estadoInternoExterno = cmtEstadoIntxExtMglManager.
                            findByEstadoInterno(aproOts.getOtObj().getEstadoInternoObj());
                    if (cmtBasicaMgl.getBasicaId() != null) {
                        if (cmtBasicaMgl.getBasicaId().compareTo(estadoInternoExterno.getIdEstadoExt().getBasicaId()) == 0) {

                            CmtVisitaTecnicaMgl visitaTecnicaAct = visitaTecnicaMglManager.
                                    findVTActiveByIdOt(aproOts.getOtObj());
                            if (visitaTecnicaAct != null) {
                                if (visitaTecnicaAct.getIdVt() != null) {
                                    subEdificiosVt = subEdificiosVtManager.findByVtAndAcometida(visitaTecnicaAct);
                                    if (!subEdificiosVt.isEmpty()) {
                                        ordenes.add(aproOts.getOtObj());
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        return ordenes.size();


    }

    /**
     * Busca una aprobacion de cambio de estado por orden de trabafo y flujo
     *
     * @author Victor Bocanegra
     * @param ot orden de trabajo
     * @param detalleFlujoMgl detalle flujo
     * @return CmtAprobacionesEstadosMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtAprobacionesEstadosMgl findByOtAndFlujo(CmtOrdenTrabajoMgl ot,
            CmtDetalleFlujoMgl detalleFlujoMgl) throws ApplicationException {
        return dao.findByOtAndFlujo(ot, detalleFlujoMgl);
    }

    /**
     * Busca aprobaciones de cambio de estado por id de ot y con permisos en el
     * repositorio.
     *
     * @author Victor Bocanegra
     * @param idOt
     * @param estadosxflujoList lista de estado X flujo
     * @return CmtOrdenTrabajoMgl orden aprobada financieramente en el
     * repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl findLstAproByOtAndPermisos(BigDecimal idOt,
            List<BigDecimal> estadosxflujoList) throws ApplicationException {

        CmtOrdenTrabajoMgl ordenTrabajoMgl = null;
        CmtAprobacionesEstadosMgl aprobacionesEstadosMgl = dao.findLstAproByOtAndPermisos(idOt, estadosxflujoList);

        if (aprobacionesEstadosMgl != null) {
            ordenTrabajoMgl = aprobacionesEstadosMgl.getOtObj();
        }
        return ordenTrabajoMgl;
    }
}
