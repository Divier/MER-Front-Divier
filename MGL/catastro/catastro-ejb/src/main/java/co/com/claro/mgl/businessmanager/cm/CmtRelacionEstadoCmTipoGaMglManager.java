package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtRelacionEstadoCmTipoGaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRelacionEstadoCmTipoGaAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRelacionEstadoCmTipoGaMgl;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Administra las peticiones a la base de datos.
 * 
* @author alejandro.martine.ext@claro.com.co
 * @versión 1.0
 */
public class CmtRelacionEstadoCmTipoGaMglManager {

    /**
     * Consulta todos los registros.
     *
     * @return Lista de todos los datoss de la tabla.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtRelacionEstadoCmTipoGaMgl> findAll()
            throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglDaoImpl daoImpl =
                new CmtRelacionEstadoCmTipoGaMglDaoImpl();
        return daoImpl.findAll();
    }

    /**
     * Inserta los registros en la tabla
     *
     * @param t
     * @param user
     * @param perfil
     * @return Notificacion de insercion del registro
     * @throws ApplicationException
     */
    public CmtRelacionEstadoCmTipoGaMgl create(CmtRelacionEstadoCmTipoGaMgl cmtRelacionEstadoCmTipoGaMgl, String user, int perfil)
            throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglDaoImpl daoImpl =
                new CmtRelacionEstadoCmTipoGaMglDaoImpl();
        return daoImpl.createCm(cmtRelacionEstadoCmTipoGaMgl, user, perfil);
    }

    /**
     * Edita los campos del registro en la tabla
     *
     * @param t
     * @param user
     * @param perfil
     * @return Notificacion de actualizacion del registro
     * @throws ApplicationException
     */
    public CmtRelacionEstadoCmTipoGaMgl update(CmtRelacionEstadoCmTipoGaMgl cmtRelacionEstadoCmTipoGaMgl, String user, int perfil)
            throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglDaoImpl daoImpl =
                new CmtRelacionEstadoCmTipoGaMglDaoImpl();
        return daoImpl.updateCm(cmtRelacionEstadoCmTipoGaMgl, user, perfil);
    }

    /**
     * Elimina los registros en la tabla
     *
     * @param t
     * @param user
     * @param perfil
     * @return Norificacion de eliminacion del registro
     * @throws ApplicationException
     */
    public boolean delete(CmtRelacionEstadoCmTipoGaMgl cmtRelacionEstadoCmTipoGaMgl, String user, int perfil)
            throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglDaoImpl daoImpl =
                new CmtRelacionEstadoCmTipoGaMglDaoImpl();
        return daoImpl.deleteCm(cmtRelacionEstadoCmTipoGaMgl, user, perfil);
    }

    /**
     * Consulta resgistros por el codigo resgristrado por el usuario.
     *
     * @param recCodigo codigo del registro
     * @return El/los registros que contengan el codigo especificado.
     * @throws ApplicationException
     */
    public boolean exisiteCodigo(String recCodigo) throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglDaoImpl daoImp =
                new CmtRelacionEstadoCmTipoGaMglDaoImpl();
        List<CmtRelacionEstadoCmTipoGaMgl> cmtRelacionEstadoCmTipoGaMgls =
                daoImp.findByCodigo(recCodigo);
        if (cmtRelacionEstadoCmTipoGaMgls != null && !cmtRelacionEstadoCmTipoGaMgls.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Consulta resgistros por el codigo y el estado de la cuenta matriz
     * resgristrado por el usuario
     *
     * @param recCodigo
     * @param estadoCm
     * @return El/los registros que contengan el codigo y estado de cuenta
     * matriz especificados.
     * @throws ApplicationException
     */
    public List<CmtRelacionEstadoCmTipoGaMgl> findByCodigoEstadoCm(String recCodigo, CmtBasicaMgl estadoCm) throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglDaoImpl daoImp =
                new CmtRelacionEstadoCmTipoGaMglDaoImpl();
        return daoImp.findByCodigoEstadoCm(recCodigo, estadoCm);
    }

    /**
     * Consulta resgistros por el estado de la cuenta matriz resgristrado por el
     * usuario
     *
     * @param estadoCmFiltro
     * @return El/los registros que contengan estado de cuenta matriz
     * especificados.
     * @throws ApplicationException
     */
    public List<CmtRelacionEstadoCmTipoGaMgl> findByEstadoCm(CmtBasicaMgl estadoCmFiltro) throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglDaoImpl daoImp =
                new CmtRelacionEstadoCmTipoGaMglDaoImpl();
        return daoImp.findByEstadoCm(estadoCmFiltro);
    }

    /**
     * Consulta los registros que cumplan con los criterios de busqueda
     * comprendidos por los parametros.
     *
     * @param codigoFiltro codigo del registro
     * @param estadoCmFiltro estado de cuenta matriz
     * @param tipoGaFiltro tipo de gestion avanzada
     * @param descripcionFiltro descripcion del registro
     * @param estadoFiltro estado del registro
     * @return
     * @throws ApplicationException
     */
    public List<CmtRelacionEstadoCmTipoGaMgl> findByFiltro(String codigoFiltro,
            CmtBasicaMgl estadoCmFiltro,
            CmtBasicaMgl tipoGaFiltro,
            String descripcionFiltro,
            String estadoFiltro) throws ApplicationException {

        CmtRelacionEstadoCmTipoGaMglDaoImpl daoImpl =
                new CmtRelacionEstadoCmTipoGaMglDaoImpl();
        return daoImpl.findByFiltro(codigoFiltro, estadoCmFiltro, tipoGaFiltro,
                descripcionFiltro, estadoFiltro);

    }

    public List<AuditoriaDto> construirAuditoria(CmtRelacionEstadoCmTipoGaMgl cmtRelacionEstadoCmTipoGaMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<CmtRelacionEstadoCmTipoGaMgl, CmtRelacionEstadoCmTipoGaAuditoriaMgl> utilsCMAuditoria = new UtilsCMAuditoria<CmtRelacionEstadoCmTipoGaMgl, CmtRelacionEstadoCmTipoGaAuditoriaMgl>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(cmtRelacionEstadoCmTipoGaMgl);
        return listAuditoriaDto;
    }
}
