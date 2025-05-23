/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtAprobacionesEstadoMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtAprobacionesEstadosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDetalleFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class CmtAprobacionesEstadoMglFacade implements CmtAprobacionesEstadoMglFacadelocal {

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
    @Override
    public CmtAprobacionesEstadosMgl crearAprobacion(CmtAprobacionesEstadosMgl aprobacionesEstadosMgl, String usuario, int perfil)
            throws ApplicationException {

        CmtAprobacionesEstadoMglManager manager = new CmtAprobacionesEstadoMglManager();
        return manager.crearAprobacion(aprobacionesEstadosMgl, usuario, perfil);

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
    @Override
    public CmtOrdenTrabajoMgl findLstAproByOt(BigDecimal idOt) throws ApplicationException {

        CmtAprobacionesEstadoMglManager manager = new CmtAprobacionesEstadoMglManager();
        return manager.findLstAproByOt(idOt);
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
    @Override
    public List<CmtOrdenTrabajoMgl> findByOtCloseAndApruebaFinan(List<BigDecimal> estadosxflujoList, 
            BigDecimal gpoDepartamento, BigDecimal gpoCiudad, CmtTipoOtMgl tipoOt, 
            BigDecimal estadoInternoBasicaId, BigDecimal tecnologia) throws ApplicationException {

        CmtAprobacionesEstadoMglManager manager = new CmtAprobacionesEstadoMglManager();
        return manager.findByOtCloseAndApruebaFinan
                (estadosxflujoList, gpoDepartamento, gpoCiudad, tipoOt, estadoInternoBasicaId, tecnologia);
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
     * @return int cuenta las ordenes
     * @throws ApplicationException
     */
    @Override
    public int getCountOtCloseAndApruebaFinan(List<BigDecimal> estadosxflujoList, 
            BigDecimal gpoDepartamento, BigDecimal gpoCiudad, CmtTipoOtMgl tipoOt, 
            BigDecimal estadoInternoBasicaId, BigDecimal tecnologia) throws ApplicationException {

        CmtAprobacionesEstadoMglManager manager = new CmtAprobacionesEstadoMglManager();
        return manager.getCountOtCloseAndApruebaFinan
                (estadosxflujoList, gpoDepartamento, gpoCiudad, tipoOt, estadoInternoBasicaId, tecnologia);
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
    @Override
    public CmtAprobacionesEstadosMgl findByOtAndFlujo(CmtOrdenTrabajoMgl ot,
            CmtDetalleFlujoMgl detalleFlujoMgl) throws ApplicationException {
        CmtAprobacionesEstadoMglManager manager = new CmtAprobacionesEstadoMglManager();
        return manager.findByOtAndFlujo(ot, detalleFlujoMgl);
    }
    
        
      /**
     * Busca aprobaciones de cambio de estado por id de ot y con permisos en el
     * repositorio.
     *
     * @author Victor Bocanegra
     * @param idOt
     * @param estadosxflujoList lista de estado X flujo
     * @return CmtAprobacionesEstadosMgl orden aprobad financieramente en el
     * repositorio
     * @throws ApplicationException
     */
    @Override
    public CmtOrdenTrabajoMgl findLstAproByOtAndPermisos(BigDecimal idOt,
            List<BigDecimal> estadosxflujoList) throws ApplicationException {
        CmtAprobacionesEstadoMglManager manager = new CmtAprobacionesEstadoMglManager();
        return manager.findLstAproByOtAndPermisos(idOt, estadosxflujoList);

    }
}
