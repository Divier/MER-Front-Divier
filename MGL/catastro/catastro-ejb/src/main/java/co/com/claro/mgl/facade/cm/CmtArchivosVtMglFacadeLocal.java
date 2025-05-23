/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

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

/**
 *
 * @author bocanegravm
 */
public interface CmtArchivosVtMglFacadeLocal {
   
    
    
    /**
     * Busca todos los Archivos vt en el repositorio
     *
     * @author Victor Bocanegra
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    List<CmtArchivosVtMgl> findAll()
            throws ApplicationException;

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
    CmtArchivosVtMgl create(CmtArchivosVtMgl cmtArchivosVtMgl,
            String usuarioCrea, int perfilCrea) throws ApplicationException;

    /**
     * Busca lista de Archivos asociados a una VT en el repositorio
     *
     * @author Victor Bocanegra
     * @param cmtVisitaTecnicaMgl
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    List<CmtArchivosVtMgl> findAllByVt(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl)
            throws ApplicationException;

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
    List<CmtArchivosVtMgl> findByIdOtOrigenCO(CmtOrdenTrabajoMgl ordenTrabajoMgl, String origen)
            throws ApplicationException;

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
    boolean delete(CmtArchivosVtMgl cmtArchivosVtMgl,
            String usuarioMod, int perfilMod) throws ApplicationException;

    /**
     * Busca lista de Archivos asociados a una OT asociados al cierre de una
     * agenda en el repositorio
     *
     * @param agenda a consultar
     * @author Victor Bocanegra
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    List<CmtArchivosVtMgl> findByIdOtAndAge(CmtAgendamientoMgl agenda)
            throws ApplicationException;

    /**
     * Busca lista de Archivos asociados a una solicitud en el repositorio
     *
     * @author Victor Bocanegra
     * @param solicitudCmMgl
     * @return CmtArchivosVtMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    CmtArchivosVtMgl findAllBySolId(CmtSolicitudCmMgl solicitudCmMgl)
            throws ApplicationException;

    /**
     * Busca lista de Archivos asociados a una VT y por Origen
     *
     * @author Lenis Cardenas
     * @param cmtVisitaTecnicaMgl
     * @param origen
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    List<CmtArchivosVtMgl> findAllByVtxOrigen(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl, String origen)
            throws ApplicationException;

    /**
     * Busca lista de Archivos de cierre de una OT
     *
     * @author Victor Bocanegra
     * @param ordenTrabajoMgl orden de trabajo
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    List<CmtArchivosVtMgl> findFilesByIdOt(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException;
    
    /**
     * Buscar un Archivo asociado a una VT por id en el repositorio.
     *
     * @author cardenaslb
     * @param idArchivoVt
     * @return un Archivo de soporte asociado a una VT.
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    CmtArchivosVtMgl findById(BigDecimal idArchivoVt)
            throws ApplicationException;
    
    
    
    List<CmtArchivosVtMgl> findFilesVTByIdOt(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException;
     
    /**
     * Busca lista de Archivos de una OTHhpp asociados al cierre de una agenda
     * en el repositorio
     *
     * @author Victor Bocanegra
     * @param agenda
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    List<CmtArchivosVtMgl> findByIdOtHhppAndAge(MglAgendaDireccion agenda)
            throws ApplicationException;
    
        /**
     * Busca lista de Archivos asociados a una solicitud en el repositorio
     *
     * @author Victor Bocanegra
     * @param solicitudHhpp
     * @return CmtArchivosVtMgl encontrada en el repositorio
     * @throws ApplicationException
     */
     CmtArchivosVtMgl findAllBySolIdHhpp(Solicitud solicitudHhpp)
            throws ApplicationException;
}
