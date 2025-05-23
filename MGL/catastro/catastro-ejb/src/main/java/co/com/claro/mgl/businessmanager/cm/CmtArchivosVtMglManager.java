/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtArchivosVtMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglAgendaDireccion;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
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
public class CmtArchivosVtMglManager {

    private CmtArchivosVtMglDaoImpl dao = new CmtArchivosVtMglDaoImpl();

    /**
     * Busca todos los Archivos vt en el repositorio
     *
     * @author Victor Bocanegra
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtArchivosVtMgl> findAll()
            throws ApplicationException {

        return dao.findAll();
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
    public CmtArchivosVtMgl create(CmtArchivosVtMgl cmtArchivosVtMgl,
            String usuarioCrea, int perfilCrea) throws ApplicationException {
        cmtArchivosVtMgl = dao.createCm(cmtArchivosVtMgl, usuarioCrea, perfilCrea);
        return cmtArchivosVtMgl;
    }

    /**
     * Busca lista de Archivos asociados a una VT en el repositorio
     *
     * @author Victor Bocanegra
     * @param cmtVisitaTecnicaMgl
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtArchivosVtMgl> findAllByVt(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl)
            throws ApplicationException {

        return dao.findAllByVt(cmtVisitaTecnicaMgl);
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
    public List<CmtArchivosVtMgl> findByIdOtOrigenCO(CmtOrdenTrabajoMgl ordenTrabajoMgl, String origen)
            throws ApplicationException {

        return dao.findByIdOtOrigenCO(ordenTrabajoMgl, origen);
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

        return dao.selectMaximoSecXOt(ot, origenArchivos);
    }
    
    /**
     * Consulta el maximo de la secuencia por Vt Autor: victor bocanegra
     *
     * @param vt visita tecnica
     * @param origen origen del archivo
     * @return el maximo de la secuencia
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int selectMaximoSecXVt(CmtVisitaTecnicaMgl vt, String origen)
            throws ApplicationException {

        return dao.selectMaximoSecXVt(vt, origen);
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
    public boolean delete(CmtArchivosVtMgl cmtArchivosVtMgl,
            String usuarioMod, int perfilMod) throws ApplicationException {
        return dao.deleteCm(cmtArchivosVtMgl, usuarioMod, perfilMod);

    }
        
     /**
     * Busca lista de Archivos de una OT asociados al cierre de una agenda
     * en el repositorio
     * @param agenda agenda a consultar
     * @author Victor Bocanegra
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtArchivosVtMgl> findByIdOtAndAge(CmtAgendamientoMgl agenda)
            throws ApplicationException {

        return dao.findByIdOtAndAge(agenda);
    }
    
    /**
     * Busca lista de Archivos asociados a una solicitud en el repositorio
     *
     * @author Victor Bocanegra
     * @param solicitudCmMgl
     * @return CmtArchivosVtMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtArchivosVtMgl findAllBySolId(CmtSolicitudCmMgl solicitudCmMgl)
            throws ApplicationException {

        return dao.findAllBySolId(solicitudCmMgl);

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
    public List<CmtArchivosVtMgl> findAllByVtxOrigen(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl,String origen)
            throws ApplicationException {

        return dao.findAllByVtxOrigen(cmtVisitaTecnicaMgl,origen);
    }
    
    /**
     * Busca lista de Archivos de cierre de una OT
     *
     * @author Victor Bocanegra
     * @param ordenTrabajoMgl orden de trabajo
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtArchivosVtMgl> findFilesByIdOt(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException {

        return dao.findFilesByIdOt(ordenTrabajoMgl);
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
    public CmtArchivosVtMgl findById(BigDecimal idArchivoVt)
            throws ApplicationException {
        return dao.find(idArchivoVt);
    }
    
     /**
     * Busca un archivo por nombre, origen y ot.
     *
     * @author Victor Bocanegra
     * @param nombreArchivo
     * @param origen
     * @param ot
     * @return CmtArchivosVtMgl encontrado en el repositorio
     * @throws ApplicationException
     */
    public CmtArchivosVtMgl findByNombreArchivoAndOrigenAndOt(String nombreArchivo,
            String origen, CmtOrdenTrabajoMgl ot)
            throws ApplicationException {
        return dao.findByNombreArchivoAndOrigenAndOt(nombreArchivo, origen, ot);
    }


    
     /**
     * Busca lista de Archivos de cierre de una OT
     *
     * @author cardenaslb
     * @param ordenTrabajoMgl orden de trabajo
     * @return List<CmtArchivosVtMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtArchivosVtMgl> findFilesVTByIdOt(CmtOrdenTrabajoMgl ordenTrabajoMgl)
            throws ApplicationException {

        return dao.findFilesVTByIdOt(ordenTrabajoMgl);
    }
    
        
    /**
     * Busca un archivo por nombre, origen y otHhpp.
     *
     * @author Victor Bocanegra
     * @param nombreArchivo
     * @param origen
     * @param otHhpp
     * @return CmtArchivosVtMgl encontrado en el repositorio
     * @throws ApplicationException
     */
    public CmtArchivosVtMgl findByNombreArchivoAndOrigenAndOtHhpp(String nombreArchivo,
            String origen, OtHhppMgl otHhpp)
            throws ApplicationException {
        
        return dao.findByNombreArchivoAndOrigenAndOtHhpp(nombreArchivo, origen, otHhpp);
    }
    
    /**
     * Consulta el maximo de la secuencia por Othhpp y origen de archivo Autor:
     * victor bocanegra
     *
     * @param otHhpp
     * @param origenArchivos origen del archivo
     * @return el maximo de la secuencia
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public int selectMaximoSecXOtHhpp(OtHhppMgl otHhpp, String origenArchivos)
            throws ApplicationException {

        return dao.selectMaximoSecXOtHhpp(otHhpp, origenArchivos);
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
    public List<CmtArchivosVtMgl> findByIdOtHhppAndAge(MglAgendaDireccion agenda)
            throws ApplicationException {

        return dao.findByIdOtHhppAndAge(agenda);
    }
    
    /**
     * Busca lista de Archivos asociados a una solicitud en el repositorio
     *
     * @author Victor Bocanegra
     * @param solicitudHhpp
     * @return CmtArchivosVtMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtArchivosVtMgl findAllBySolIdHhpp(Solicitud solicitudHhpp)
            throws ApplicationException {

        return dao.findAllBySolIdHhpp(solicitudHhpp);
    }
}
