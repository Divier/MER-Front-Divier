package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Admin
 */
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.dtos.EstadosOtCmDirDto;
import co.com.claro.mgl.dtos.FiltroConjsultaBasicasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtBasicaMglFacade implements CmtBasicaMglFacadeLocal {

    private String user = "";
    private int perfil = 0;
     CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
    @Override
    public List<CmtBasicaMgl> findAll() throws ApplicationException {
       
        return cmtBasicaMglManager.findAll();
    }

    @Override
    public List<CmtBasicaMgl> findByTipoBasica(CmtTipoBasicaMgl tipoBasicaId)
            throws ApplicationException {
        return cmtBasicaMglManager.findByTipoBasica(tipoBasicaId);
    }
    
    
    /**
     * Retorna todos los registros de basica que necesita la vista de estados combinados
     *
     * @param tipoBasicaId
     * @return {@link List<String>} Retorna la lista de códigos de tecnología
     * parametrizados
     * @throws ApplicationException Excepción de la App
     * @author Angel Gonzalez
     */
    @Override
    public List<CmtBasicaMgl> findByTipoBasicaEstados(CmtTipoBasicaMgl tipoBasicaId) throws ApplicationException {
        return cmtBasicaMglManager.findByTipoBasicaEstadosCombinado(tipoBasicaId);
    }

    /**
     * Lista los diferentes tipos por el nombre.
     *
     * @author Antonio Gil
     * @return lista
     * @throws ApplicationException
     */
    @Override
    public List<CmtBasicaMgl> findTipoBasicaName(String tipoBasicaName)
            throws ApplicationException {
        return cmtBasicaMglManager.findTipoBasicaName(tipoBasicaName);
    }

    @Override
    public CmtBasicaMgl create(CmtBasicaMgl t) throws ApplicationException {
        return cmtBasicaMglManager.create(t, user, perfil);
    }

    @Override
    public CmtBasicaMgl update(CmtBasicaMgl t) throws ApplicationException {
        return cmtBasicaMglManager.update(t, user, perfil);
    }

    @Override
    public boolean delete(CmtBasicaMgl t) throws ApplicationException {
        return cmtBasicaMglManager.delete(t, user, perfil);
    }

    @Override
    public CmtBasicaMgl findById(BigDecimal id) throws ApplicationException {
        return cmtBasicaMglManager.findById(id);
    }

    @Override
    public CmtBasicaMgl findById(CmtBasicaMgl sqlData)
            throws ApplicationException {
        return cmtBasicaMglManager.findById(sqlData);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser == null || mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil No pueden ser nulos.");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public List<CmtBasicaMgl> findByFiltro(FiltroConjsultaBasicasDto consulta)
            throws ApplicationException {
        return cmtBasicaMglManager.findByFiltro(consulta);
    }

    @Override
    public List<AuditoriaDto> construirAuditoria(CmtBasicaMgl cmtBasicaMgl)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        if (cmtBasicaMgl != null) {
            return cmtBasicaMglManager.construirAuditoria(cmtBasicaMgl);
        } else {
            return null;
        }
    }

    @Override
    public String buscarUltimoCodigoNumerico(CmtTipoBasicaMgl tipoBasicaId)
            throws ApplicationException {
        return cmtBasicaMglManager.buscarUltimoCodigoNumerico(tipoBasicaId);
    }

    @Override
    public CmtBasicaMgl findByNombre(BigDecimal tipoBasica, String nombre) {
        return cmtBasicaMglManager.findByNombre(tipoBasica, nombre);

    }

    @Override
    public CmtBasicaMgl findEstratoByLevelGeo(String estratoGeo)  throws ApplicationException {
        return cmtBasicaMglManager.findEstratoByLevelGeo(estratoGeo) ;

    }

    /**
     * Lista los diferentes tipos por el Id.
     *
     * @author Juan David Hernandez
     * @param idTipoProyectoGeneral
     * @return lista
     * @throws ApplicationException
     */
    @Override
    public List<CmtBasicaMgl> findGrupoProyectoBasicaList
            (BigDecimal idTipoProyectoGeneral)
            throws ApplicationException {
         return cmtBasicaMglManager
                .findGrupoProyectoBasicaList(idTipoProyectoGeneral);
    }

    /**
     * Lista los diferentes tipos por el Id.
     *
     * @author Juan David Hernandez
     * @param idTipoGrupoProyecto
     * @return lista
     * @throws ApplicationException
     */
    @Override
    public List<CmtBasicaMgl> findProyectoBasicaList
            (BigDecimal idTipoGrupoProyecto)
            throws ApplicationException {
         return cmtBasicaMglManager.findProyectoBasicaList
                (idTipoGrupoProyecto);
    }

    /**
     * Función que se utiliza para obtener la abreviatura del tipo de solicitud
     *
     * @author Juan David Hernandez
     * @param tipoTecnologia
     * @return String abreviatura del tipo de solicitud
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public String findAbreviaturaTipoTecnologia(String tipoTecnologia)
            throws ApplicationException {
        return cmtBasicaMglManager
                .findAbreviaturaTipoTecnologia(tipoTecnologia);
    }

    /**
     * Función que obtiene el valor del tipo de tecnologia por abreviatura
     *
     * @author Juan David Hernandez
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    @Override
    public CmtBasicaMgl findValorTipoSolicitud(String tipoTecnologiaAbreviatura)
            throws ApplicationException {
        return cmtBasicaMglManager.findValorTipoSolicitud
                (tipoTecnologiaAbreviatura);
    }

    @Override
    public CmtBasicaMgl findByCodigoInternoApp(String codigoInternoApp) throws ApplicationException {
       return cmtBasicaMglManager.findByCodigoInternoApp(codigoInternoApp);
    }
    
       /**
     * Autor: Victor Bocanegar
     * metodo para obtener el valor extendido de una tipo basica estado hhpp
     *
     * @param basicaMgl
     * @return String
     * @throws ApplicationException
     */
    @Override
    public String findValorExtendidoEstHhpp(CmtBasicaMgl basicaMgl)
            throws ApplicationException{
        
        return cmtBasicaMglManager.findValorExtendidoEstHhpp(basicaMgl);
    }

    @Override
    public List<CmtBasicaMgl> findByNombreBasica(String nombreBasica) throws ApplicationException {
        return cmtBasicaMglManager.findByNombreBasica(nombreBasica);
    }
    
             /**
     * Busca Basica que contenga el prefijo en el nombre.
     *
     * @author Juan David Hernandez
     * @param prefijo que debe contener el registro 
     * @param tipoBasicaid 
     * @return List de CmtBasicaMgl encontrados
     * @throws ApplicationException
     */
            
    @Override
    public List<CmtBasicaMgl> findByPreFijo(String prefijo, BigDecimal tipoBasicaid) throws ApplicationException {
        return cmtBasicaMglManager.findByPreFijo(prefijo, tipoBasicaid);
    }

     /**
     * Busca Basica por el tipo de basica y el codigo de la basica
     *
     * @author Jonathan Peña 
     * @param codigo 
     * @param tipoBasicaid 
     * @return  CmtBasicaMgl encontrada
     * @throws ApplicationException
     */
     @Override
     public CmtBasicaMgl findByBasicaCode(String codigo, BigDecimal tipoBasicaid)
            throws ApplicationException{
       return  cmtBasicaMglManager.findByBasicaCode(codigo, tipoBasicaid);
     }
     
     
    @Override
    public List<CmtBasicaMgl> findEstadoResultadoOTRR(List<String> tipos , CmtTipoBasicaMgl tipoBasica)throws ApplicationException{
         return cmtBasicaMglManager.findEstadoResultadoOTRR(tipos , tipoBasica);
     }
    
    @Override
    public List<CmtBasicaMgl> findByCodigoBasicaList(List<CmtTipoSolicitudMgl> tipoSolicitudListByRol, CmtTipoBasicaMgl tipoBasicaId)
            throws ApplicationException {
            return cmtBasicaMglManager.findByCodigoBasicaList(tipoSolicitudListByRol, tipoBasicaId);
    }

    @Override
    public List<CmtBasicaMgl> findByAllFields(HashMap<String, Object> paramsValidacionGuardar) throws ApplicationException {
      return cmtBasicaMglManager.findByAllFields(paramsValidacionGuardar);
    }
    
    
    @Override
   public List<CmtBasicaMgl> findByTipoBasicaTecno(CmtBasicaMgl basicaTecnologia)
            throws ApplicationException{
       return cmtBasicaMglManager.findByTipoBasicaTecno(basicaTecnologia);
   }
   
   
    @Override
    public List<CmtBasicaMgl> findTecnolosgias()
            throws ApplicationException {
        return cmtBasicaMglManager.findTecnolosgias();
    }
    
    
    @Override
    public List<CmtBasicaMgl> findByTipoBasica(BigDecimal tipoBasicaId)
            throws ApplicationException {
        return cmtBasicaMglManager.findByTipoBasica(tipoBasicaId);
    }
    
    
    @Override
     public List<EstadosOtCmDirDto> findListEstadosCmDir(CmtTipoBasicaMgl tipoBasicaTipoOtCmId,CmtTipoBasicaMgl tipoBasicaTipoOtDirId)
            throws ApplicationException{
           return cmtBasicaMglManager.findListEstadosCmDir(tipoBasicaTipoOtCmId,tipoBasicaTipoOtDirId);
     }
     
    /**
     * Busca lista de Basicas por tipo basica y abreviatura
     *
     * @author Victor Bocanegra
     * @param tipoBasica
     * @param abreviatura
     * @return List de CmtBasicaMgl encontrados
     * @throws ApplicationException
     */
    @Override
    public List<CmtBasicaMgl> findByTipoBAndAbreviatura(BigDecimal tipoBasica, String abreviatura)
            throws ApplicationException {
        return cmtBasicaMglManager.findByTipoBAndAbreviatura(tipoBasica, abreviatura);
    }

    /**
     * Busca los códigos de tecnologías parametrizados por los usuarios.
     *
     * @return {@link List<String>} Retorna la lista de códigos de tecnología parametrizados
     * @throws ApplicationException Excepción de la App
     * @author Gildardo Mora
     */
    @Override
    public List<String> findCodigosTecnologiasPorValidarEnSolicitud() throws ApplicationException {
        return cmtBasicaMglManager.findCodigosTecnologiasPorValidarEnSolicitud();
    }
}
