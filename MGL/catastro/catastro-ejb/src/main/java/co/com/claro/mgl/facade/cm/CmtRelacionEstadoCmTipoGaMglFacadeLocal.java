
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRelacionEstadoCmTipoGaMgl;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * La clase funciona como interfaz para los metodos implementados en 
 * CmtRelacionEstadoCmTipoGaMglFacade.
 * 
 * @author alejandro.martine.ext@claro.com.co
 * @version 1.0
 */
public interface CmtRelacionEstadoCmTipoGaMglFacadeLocal extends 
        BaseFacadeLocal<CmtRelacionEstadoCmTipoGaMgl> {

    /**
     *
     * @param t objeto de tipo CmtRelacionEstadoCmTipoGaMgl
     * @return
     * @throws ApplicationException
     */
    @Override
    public CmtRelacionEstadoCmTipoGaMgl create
            (CmtRelacionEstadoCmTipoGaMgl t) throws ApplicationException;

    /**
     *
     * @param t objeto de tipo CmtRelacionEstadoCmTipoGaMgl
     * @return
     * @throws ApplicationException
     */
    @Override
    public CmtRelacionEstadoCmTipoGaMgl update
            (CmtRelacionEstadoCmTipoGaMgl t) throws ApplicationException;

    /**
     *
     * @param t objeto de tipo CmtRelacionEstadoCmTipoGaMgl
     * @return
     * @throws ApplicationException
     */
    @Override
    public boolean delete
            (CmtRelacionEstadoCmTipoGaMgl t) throws ApplicationException;

    /**
     *
     * @param sqlData objeto de tipo CmtRelacionEstadoCmTipoGaMgl
     * @return
     * @throws ApplicationException
     */
    @Override
    public CmtRelacionEstadoCmTipoGaMgl findById
            (CmtRelacionEstadoCmTipoGaMgl sqlData) throws ApplicationException;

    /**
     *
     * @param recCodigo codigo del registro
     * @return
     * @throws ApplicationException
     */
    public boolean exisiteCodigo (String recCodigo) throws ApplicationException;

    /**
     *
     * @param recCodigo codigo del registro
     * @param estadoCm estado de cuenta matriz
     * @return
     * @throws ApplicationException
     */
    public List<CmtRelacionEstadoCmTipoGaMgl> findByCodigoEstadoCm
            (String recCodigo, CmtBasicaMgl estadoCm) throws ApplicationException;

    /**
     *
     * @param estadoCmFiltro estado de cuenta matriz
     * @return
     * @throws ApplicationException
     */
    public List<CmtRelacionEstadoCmTipoGaMgl> findByEstadoCm
            (CmtBasicaMgl estadoCmFiltro) throws ApplicationException;

    /**
     *
     * @param codigoFiltro codigo del registro 
     * @param estadoCmFiltro estado de cuenta matriz
     * @param tipoGaFiltro tipo de gestion avanzada
     * @param descripcionFiltro descripcion del registro
     * @param estadoFiltro estado del registro
     * @return
     * @throws ApplicationException
     */
    public List<CmtRelacionEstadoCmTipoGaMgl> findByFiltro(
            String codigoFiltro, 
            CmtBasicaMgl estadoCmFiltro, 
            CmtBasicaMgl tipoGaFiltro, 
            String descripcionFiltro, 
            String estadoFiltro) throws ApplicationException;   
    
    public void setUser(String user,int perfil) throws ApplicationException ;    
    
   public List<AuditoriaDto> construirAuditoria(CmtRelacionEstadoCmTipoGaMgl cmtRelacionEstadoCmTipoGaMgl) 
           throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;    
}
