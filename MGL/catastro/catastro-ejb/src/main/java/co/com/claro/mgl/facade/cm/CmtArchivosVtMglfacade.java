/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtArchivosVtMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.Solicitud;
import co.com.claro.mgl.jpa.entities.cm.CmtAgendamientoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author bocanegravm
 */
@Stateless
public class CmtArchivosVtMglfacade implements CmtArchivosVtMglFacadeLocal {

    /**
     * Busca todos los Archivos vt en el repositorio
     *
     * @author Victor Bocanegra
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public List<CmtArchivosVtMgl> findAll()
            throws ApplicationException {
        CmtArchivosVtMglManager manager = new CmtArchivosVtMglManager();
        return manager.findAll();
    }

    /**
     * Guarda un Archivo asociado a una VT en el repositorio.
     *
     * @author Victor Bocanegra
     * @param cmtArchivosVtMgl
     * @param usuarioCrea
     * @param perfilCrea
     * @return un Archivo de soporte asociado a una VT.
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @Override
    public CmtArchivosVtMgl create(CmtArchivosVtMgl cmtArchivosVtMgl,
            String usuarioCrea, int perfilCrea) throws ApplicationException {
        CmtArchivosVtMglManager manager = new CmtArchivosVtMglManager();
        return manager.create(cmtArchivosVtMgl, usuarioCrea, perfilCrea);

    }

    /**
     * Busca lista de Archivos asociados a una VT en el repositorio
     *
     * @author Victor Bocanegra
     * @param cmtVisitaTecnicaMgl
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public List<CmtArchivosVtMgl> findAllByVt(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl)
            throws ApplicationException {
        CmtArchivosVtMglManager manager = new CmtArchivosVtMglManager();
        return manager.findAllByVt(cmtVisitaTecnicaMgl);
    }

    /**
     * Busca lista de Archivos de cierre de OT asociados a una OT en el
     * repositorio
     *
     * @author Victor Bocanegra
     * @param ordenTrabajoMgl orden de trabajo
     * @param origen origen archivo
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public List<CmtArchivosVtMgl> findByIdOtOrigenCO(CmtOrdenTrabajoMgl ordenTrabajoMgl, String origen)
            throws ApplicationException {

        CmtArchivosVtMglManager manager = new CmtArchivosVtMglManager();
        return manager.findByIdOtOrigenCO(ordenTrabajoMgl, origen);
    }

    /**
     * Consulta el maximo de la secuencia por Ot y origen de archivo Autor:
     * victor bocanegra
     *
     * @param ot
     * @param origenArchivos origen del archivo
     * @return el maximo de la secuencia
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int selectMaximoSecXOt(CmtOrdenTrabajoMgl ot, String origenArchivos)
            throws ApplicationException {

        CmtArchivosVtMglManager manager = new CmtArchivosVtMglManager();
        return manager.selectMaximoSecXOt(ot, origenArchivos);
    }

    /**
     * Elimina un ArchivoVt del repositorio.
     *
     * @author Victor Bocanegra
     * @param cmtArchivosVtMgl
     * @param usuarioMod
     * @param perfilMod
     * @return true si la transacion es satisfatoria false si no
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @Override
    public boolean delete(CmtArchivosVtMgl cmtArchivosVtMgl,
            String usuarioMod, int perfilMod) throws ApplicationException {

        CmtArchivosVtMglManager manager = new CmtArchivosVtMglManager();
        return manager.delete(cmtArchivosVtMgl, usuarioMod, perfilMod);

    }
    
    /**
     * Busca lista de Archivos asociados a una OT asociados al cierre de una
     * agenda en el repositorio
     * @param  agenda a consultar
     * @author Victor Bocanegra
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public List<CmtArchivosVtMgl> findByIdOtAndAge(CmtAgendamientoMgl agenda)
            throws ApplicationException {

        CmtArchivosVtMglManager manager = new CmtArchivosVtMglManager();
        return manager.findByIdOtAndAge(agenda);
    }
    
        /**
     * Busca lista de Archivos asociados a una solicitud en el repositorio
     *
     * @author Victor Bocanegra
     * @param solicitudCmMgl
     * @return CmtArchivosVtMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public CmtArchivosVtMgl findAllBySolId(CmtSolicitudCmMgl solicitudCmMgl)
            throws ApplicationException {
        
         CmtArchivosVtMglManager manager = new CmtArchivosVtMglManager();
        return manager.findAllBySolId(solicitudCmMgl);
    }
    
    
      /**
     * Busca lista de Archivos asociados a una VT en el repositorio
     *
     * @author Victor Bocanegra
     * @param cmtVisitaTecnicaMgl
     * @param origen
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public List<CmtArchivosVtMgl> findAllByVtxOrigen(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl, String origen)
            throws ApplicationException {
        CmtArchivosVtMglManager manager = new CmtArchivosVtMglManager();
        return manager.findAllByVtxOrigen(cmtVisitaTecnicaMgl,origen);
    }
    
    /**
     * Busca lista de Archivos de cierre de una OT
     *
     * @author Victor Bocanegra
     * @param ordenTrabajoMgl orden de trabajo
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public List<CmtArchivosVtMgl> findFilesByIdOt(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException {

        CmtArchivosVtMglManager manager = new CmtArchivosVtMglManager();
        return manager.findFilesByIdOt(ordenTrabajoMgl);
    }
    
        
    /**
     * Buscar un Archivo asociado a una VT por id en el repositorio.
     *
     * @author Victor Bocanegra
     * @param idArchivoVt
     * @return un Archivo de soporte asociado a una VT.
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    @Override
    public CmtArchivosVtMgl findById(BigDecimal idArchivoVt)
            throws ApplicationException {

        CmtArchivosVtMglManager manager = new CmtArchivosVtMglManager();
        return manager.findById(idArchivoVt);
    }
       /**
     * Busca lista de Archivos de cierre de una OT
     *
     * @author Victor Bocanegra
     * @param ordenTrabajoMgl orden de trabajo
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public List<CmtArchivosVtMgl> findFilesVTByIdOt(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException {

        CmtArchivosVtMglManager manager = new CmtArchivosVtMglManager();
        return manager.findFilesVTByIdOt(ordenTrabajoMgl);
    }
    
    /**
     * Busca lista de Archivos de una OTHhpp asociados al cierre de una agenda
     * en el repositorio
     *
     * @author Victor Bocanegra
     * @param agenda
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public List<CmtArchivosVtMgl> findByIdOtHhppAndAge(MglAgendaDireccion agenda)
            throws ApplicationException {

        CmtArchivosVtMglManager manager = new CmtArchivosVtMglManager();
        return manager.findByIdOtHhppAndAge(agenda);

    }
    
    /**
     * Busca lista de Archivos asociados a una solicitud en el repositorio
     *
     * @author Victor Bocanegra
     * @param solicitudHhpp
     * @return CmtArchivosVtMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    @Override
    public CmtArchivosVtMgl findAllBySolIdHhpp(Solicitud solicitudHhpp)
            throws ApplicationException {
        CmtArchivosVtMglManager manager = new CmtArchivosVtMglManager();
        return manager.findAllBySolIdHhpp(solicitudHhpp);
    }
}
