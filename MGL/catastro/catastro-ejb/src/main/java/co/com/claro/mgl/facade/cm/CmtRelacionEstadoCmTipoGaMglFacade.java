package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtRelacionEstadoCmTipoGaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRelacionEstadoCmTipoGaMgl;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Delega las peticiones de base de datos, invoca los metodos necesarios para
 * insertar, consultar, editar y/o eliminar registross en la base de datos.
 * 
* @author alejandro.martine.ext@claro.com.co
 * @versión 1.0
 */
@Stateless
public class CmtRelacionEstadoCmTipoGaMglFacade implements
        CmtRelacionEstadoCmTipoGaMglFacadeLocal {

    private String user = "";
    private int perfil = 0;

    /**
     * Consulta todos los registros.
     *
     * @return Lista de todos los datoss de la tabla.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<CmtRelacionEstadoCmTipoGaMgl> findAll()
            throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglManager manager =
                new CmtRelacionEstadoCmTipoGaMglManager();
        return manager.findAll();
    }

    /**
     * Inserta los registros en la tabla
     *
     * @param t
     * @return Notificacion de insercion del registro
     * @throws ApplicationException
     */
    @Override
    public CmtRelacionEstadoCmTipoGaMgl create(CmtRelacionEstadoCmTipoGaMgl t)
            throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglManager manager =
                new CmtRelacionEstadoCmTipoGaMglManager();
        return manager.create(t,user,perfil);
    }

    /**
     * Edita los campos del registro en la tabla
     *
     * @param t
     * @return Notificacion de actualizacion del registro
     * @throws ApplicationException
     */
    @Override
    public CmtRelacionEstadoCmTipoGaMgl update(CmtRelacionEstadoCmTipoGaMgl t)
            throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglManager manager =
                new CmtRelacionEstadoCmTipoGaMglManager();
        return manager.update(t,user,perfil);
    }

    /**
     * Elimina los registros en la tabla
     *
     * @param t
     * @return Norificacion de eliminacion del registro
     * @throws ApplicationException
     */
    @Override
    public boolean delete(CmtRelacionEstadoCmTipoGaMgl t)
            throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglManager manager =
                new CmtRelacionEstadoCmTipoGaMglManager();
        return manager.delete(t,user,perfil);
    }

    /**
     * Consulta el registro en la tabla por la llave primaria.
     *
     * @param sqlData
     * @return
     * @throws ApplicationException
     */
    @Override
    public CmtRelacionEstadoCmTipoGaMgl findById(CmtRelacionEstadoCmTipoGaMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");

    }

    /**
     * Consulta resgistros por el codigo resgristrado por el usuario.
     *
     * @param recCodigo codigo del registro
     * @return El/los registros que contengan el codigo especificado.
     * @throws ApplicationException
     */
    @Override
    public boolean exisiteCodigo (String recCodigo) throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglManager manager =
                new CmtRelacionEstadoCmTipoGaMglManager();
        return manager.exisiteCodigo(recCodigo);
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
    @Override
    public List<CmtRelacionEstadoCmTipoGaMgl> findByCodigoEstadoCm(String recCodigo, CmtBasicaMgl estadoCm) throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglManager manager =
                new CmtRelacionEstadoCmTipoGaMglManager();
        return manager.findByCodigoEstadoCm(recCodigo, estadoCm);
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
    @Override
    public List<CmtRelacionEstadoCmTipoGaMgl> findByEstadoCm(CmtBasicaMgl estadoCmFiltro) throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglManager manager =
                new CmtRelacionEstadoCmTipoGaMglManager();
        return manager.findByEstadoCm(estadoCmFiltro);
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
    @Override
    public List<CmtRelacionEstadoCmTipoGaMgl> findByFiltro(
            String codigoFiltro,
            CmtBasicaMgl estadoCmFiltro,
            CmtBasicaMgl tipoGaFiltro,
            String descripcionFiltro,
            String estadoFiltro) throws ApplicationException {
        CmtRelacionEstadoCmTipoGaMglManager manager =
                new CmtRelacionEstadoCmTipoGaMglManager();
        return manager.findByFiltro(codigoFiltro, estadoCmFiltro, tipoGaFiltro,
                descripcionFiltro, estadoFiltro);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }
    
    @Override
   public List<AuditoriaDto> construirAuditoria(CmtRelacionEstadoCmTipoGaMgl cmtRelacionEstadoCmTipoGaMgl) 
           throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
       CmtRelacionEstadoCmTipoGaMglManager cmtRelacionEstadoCmTipoGaMglManager= new CmtRelacionEstadoCmTipoGaMglManager();
        return cmtRelacionEstadoCmTipoGaMglManager.construirAuditoria(cmtRelacionEstadoCmTipoGaMgl);
   } 
    
}
