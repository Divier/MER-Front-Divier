package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.FiltroConsultaTipoBasicaDto;
import co.com.claro.mgl.dtos.FiltroTablaTipoBasicaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface CmtTipoBasicaMglFacadeLocal extends BaseFacadeLocal<CmtTipoBasicaMgl> {

   CmtTipoBasicaMgl findByNombreTipoBasica(String tipoBasica) 
          throws ApplicationException;

   CmtTipoBasicaMgl findByTipoBasicaId(BigDecimal id) throws ApplicationException;
    
   void setUser(String user, int perfil) throws ApplicationException;

   FiltroConsultaTipoBasicaDto findTablasTipoBasica(FiltroTablaTipoBasicaDto filtro,
            boolean contar, int firstResult, int maxResults) throws ApplicationException;

    List<AuditoriaDto> construirAuditoria(CmtTipoBasicaMgl cmtTipoBasicaMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    /**
     * M&eacute;todo para consultar los tipos para eliminaci&oacute;n de CM
     * 
     * @return {@link List}&lt;{@link CmtTipoBasicaMgl}> listado de tipos encontrados
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    List<CmtTipoBasicaMgl> encontrarTiposEliminacionCM() throws ApplicationException;

    /**
     * Busca lista de CmtTipoBasicaMgl en el repositorio
     *
     * @author Victor Bocanegra
     * @param complemento
     * @return List<CmtTipoBasicaMgl> encontrada en el repositorio
     * @throws ApplicationException
     *
     *
     */
    List<CmtTipoBasicaMgl> findByComplemento(String complemento) throws ApplicationException;

    /**
     * Busca toda la lista de CmtTipoBasicaMgl en el repositorio sin las que
     * tiene informacion adicional
     *
     * @author Victor Bocanegra
     * @param complemento
     * @return List<CmtTipoBasicaMgl> encontrada en el repositorio
     * @throws ApplicationException
     *
     *
     */
    List<CmtTipoBasicaMgl> findAllSinInfoAdi(String complemento) throws ApplicationException;
    
/**
     * Busca el tipo basica por identificador interno de la app
     *
     * @author Carlos Villamil Hitss
     * @param codigoInternoApp codigo interno de la aplicacion
     * @return CmtTipoBasicaMgl tipo basica encontrado
     * @throws ApplicationException
     */
    
    CmtTipoBasicaMgl findByCodigoInternoApp(String codigoInternoApp)
            throws ApplicationException;  
    
        /**
     * Busca el maximo codigo
     *
     * @author Victor Bocanegra
     * @return String
     * @throws ApplicationException
     */
    String selectMaxCodigo() throws ApplicationException; 
    
    
      /**
     * Buscar tabla tipo basica en la basica de datos 
     *
     * @author cardenaslb
     * @return List<CmtTipoBasicaMgl> encontrada en el repositorio
     * @throws ApplicationException
     *
     *
     */
    List<CmtTipoBasicaMgl> findByAllFields( HashMap<String, Object> parametros) throws ApplicationException;
}
