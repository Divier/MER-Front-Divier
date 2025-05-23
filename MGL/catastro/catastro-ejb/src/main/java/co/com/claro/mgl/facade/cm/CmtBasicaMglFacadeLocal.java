package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.EstadosOtCmDirDto;
import co.com.claro.mgl.dtos.FiltroConjsultaBasicasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface CmtBasicaMglFacadeLocal extends BaseFacadeLocal<CmtBasicaMgl> {

     List<CmtBasicaMgl> findByTipoBasica(CmtTipoBasicaMgl tipoBasicaId)
            throws ApplicationException;
     
     List<CmtBasicaMgl> findByTipoBasicaEstados(CmtTipoBasicaMgl tipoBasicaId)
            throws ApplicationException;
     

     CmtBasicaMgl findById(BigDecimal id) throws ApplicationException;

    /**
     * Lista los diferentes tipos por el nombre.
     *
     * @author Antonio Gil
     * @param tipoBasicaName
     * @return
     * @throws ApplicationException
     */
     List<CmtBasicaMgl> findTipoBasicaName(String tipoBasicaName)
            throws ApplicationException;

     void setUser(String user, int perfil) throws ApplicationException;

     List<AuditoriaDto> construirAuditoria(CmtBasicaMgl cmtBasicaMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;

     List<CmtBasicaMgl> findByFiltro(FiltroConjsultaBasicasDto consulta) throws ApplicationException;

     String buscarUltimoCodigoNumerico(CmtTipoBasicaMgl tipoBasicaId) throws ApplicationException;

     CmtBasicaMgl findByNombre(BigDecimal tipoBasica, String nombre);

     CmtBasicaMgl findEstratoByLevelGeo(String estratoGeo) throws ApplicationException;

    /**
     * Lista los diferentes tipos por el Id.
     *
     * @author Juan David Hernandez
     * @param tipoProyectoGeneral
     * @return lista
     * @throws ApplicationException
     */
     List<CmtBasicaMgl> findGrupoProyectoBasicaList
            (BigDecimal tipoProyectoGeneral) throws ApplicationException;

    /**
     * Lista los diferentes tipos por el Id.
     *
     * @author Juan David Hernandez
     * @param tipoGrupoProyecto
     * @return lista
     * @throws ApplicationException
     */
     List<CmtBasicaMgl> findProyectoBasicaList(BigDecimal tipoGrupoProyecto)
            throws ApplicationException;

    /**
     * Función que se utiliza para obtener la abreviatura del tipo de solicitud
     *
     * @author Juan David Hernandez
     * @param tipoTecnologia
     * @return String abreviatura del tipo de solicitud
     * @throws co.com.claro.mgl.error.ApplicationException
     */
       String findAbreviaturaTipoTecnologia(String tipoTecnologia)
                throws ApplicationException;

    /**
     * Función que obtiene el valor del tipo de tecnologia por abreviatura
     *
     * @author Juan David Hernandez
     * @param tipoTecnologiaAbreviatura
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
     CmtBasicaMgl findValorTipoSolicitud(String tipoTecnologiaAbreviatura) 
             throws ApplicationException; 

     /**
     * Busca el codigo identificador interno de la app
     *
     * @author Lenis Cardenas
     * @param codigoInternoApp codigo interno de la aplicacion
     * @return CmtBasicaMgl  basica encontrado
     * @throws ApplicationException
     */
    
    CmtBasicaMgl findByCodigoInternoApp(String codigoInternoApp)
            throws ApplicationException;   

           /**
     * Autor: Victor Bocanegar
     * metodo para obtener el valor extendido de una tipo basica estado hhpp
     *
     * @param basicaMgl
     * @return String
     * @throws ApplicationException
     */
    String findValorExtendidoEstHhpp(CmtBasicaMgl basicaMgl)
            throws ApplicationException;
    
    
    List<CmtBasicaMgl> findByNombreBasica (String nomrbeBasica)throws ApplicationException;
    
         /**
     * Busca Basica que contenga el prefijo en el nombre.
     *
     * @author Juan David Hernandez
     * @param prefijo que debe contener el registro 
     * @param tipoBasicaid 
     * @return List de CmtBasicaMgl encontrados
     * @throws ApplicationException
     */
    
    List<CmtBasicaMgl> findByPreFijo(String prefijo, BigDecimal tipoBasicaid)
            throws ApplicationException;   
   
    /**
     * Busca Basica por el tipo de basica y el codigo de la basica
     *
     * @author Jonathan Peña 
     * @param codigo 
     * @param tipoBasicaid 
     * @return  CmtBasicaMgl encontrada
     * @throws ApplicationException
     */
    CmtBasicaMgl findByBasicaCode(String codigo, BigDecimal tipoBasicaid)
            throws ApplicationException;

    
    List<CmtBasicaMgl> findEstadoResultadoOTRR(List<String> tipos, CmtTipoBasicaMgl tipoBasica) throws ApplicationException;

    
    List<CmtBasicaMgl> findByCodigoBasicaList(List<CmtTipoSolicitudMgl> tipoSolicitudListByRol, CmtTipoBasicaMgl tipoBasicaId)
            throws ApplicationException;
    
    List<CmtBasicaMgl> findByAllFields(HashMap<String, Object> paramsValidacionGuardar)
            throws ApplicationException;
     
     
     List<CmtBasicaMgl> findByTipoBasicaTecno(CmtBasicaMgl basicaTecnologia)
            throws ApplicationException;
     
     
    List<CmtBasicaMgl> findTecnolosgias()
            throws ApplicationException;
     
     
     List<CmtBasicaMgl> findByTipoBasica(BigDecimal tipoBasicaId)
            throws ApplicationException;
     
     
     List<EstadosOtCmDirDto> findListEstadosCmDir(CmtTipoBasicaMgl tipoBasicaTipoOtCmId,CmtTipoBasicaMgl tipoBasicaTipoOtDirId)
            throws ApplicationException;
     
    /**
     * Busca lista de Basicas por tipo basica y abreviatura
     *
     * @author Victor Bocanegra
     * @param tipoBasica
     * @param abreviatura
     * @return List de CmtBasicaMgl encontrados
     * @throws ApplicationException
     */
    List<CmtBasicaMgl> findByTipoBAndAbreviatura(BigDecimal tipoBasica, String abreviatura)
            throws ApplicationException;

    /**
     * Busca los códigos de tecnologías parametrizados por los usuarios.
     *
     * @return {@link List<String>} Retorna la lista de códigos de tecnología parametrizados
     * @throws ApplicationException Excepción de la App
     * @author Gildardo Mora
     */
    List<String> findCodigosTecnologiasPorValidarEnSolicitud() throws ApplicationException;
}
