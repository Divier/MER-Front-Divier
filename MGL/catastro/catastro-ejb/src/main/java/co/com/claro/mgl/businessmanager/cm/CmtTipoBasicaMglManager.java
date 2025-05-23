package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtBasicaMglDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtTipoBasicaMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaTipoBasicaDto;
import co.com.claro.mgl.dtos.FiltroTablaTipoBasicaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CmtTipoBasicaMglManager {

    public List<CmtTipoBasicaMgl> findAll() throws ApplicationException {
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        return cmtTipoBasicaMglDaoImpl.findAll();
    }

    public CmtTipoBasicaMgl create(CmtTipoBasicaMgl cmtTipoBasicaMgl) throws ApplicationException {
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        return cmtTipoBasicaMglDaoImpl.create(cmtTipoBasicaMgl);
    }

    public CmtTipoBasicaMgl update(CmtTipoBasicaMgl cmtTipoBasicaMgl,
            String user, int perfil) throws ApplicationException {
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        return cmtTipoBasicaMglDaoImpl.updateCm(cmtTipoBasicaMgl,user,perfil);
    }

    public boolean delete(CmtTipoBasicaMgl cmtTipoBasicaMgl) throws ApplicationException {
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        return cmtTipoBasicaMglDaoImpl.delete(cmtTipoBasicaMgl);
    }

    public CmtTipoBasicaMgl findById(CmtTipoBasicaMgl cmtTipoBasicaMgl) throws ApplicationException {
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        return cmtTipoBasicaMglDaoImpl.find(cmtTipoBasicaMgl.getTipoBasicaId());
    }
    
    public CmtTipoBasicaMgl findByTipoBasicaId(BigDecimal id) throws ApplicationException{
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        return cmtTipoBasicaMglDaoImpl.findByTipoBasicaId(id);
    }

    /**
     * Buscar el tipo de basica. Busca un tipo de basica teniendo en cuentra el
     * nombre del tipo basica.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param nombreTipoBasica nombre del tipo basica a buscar.
     * @return la instancia del tipo basica solicitada o null en caso que no se
     * encuentre.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public CmtTipoBasicaMgl findTipoBasica(String nombreTipoBasica) throws ApplicationException {
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        return cmtTipoBasicaMglDaoImpl.find(nombreTipoBasica);
    }

    /**
     * Buscar el tipo de basica. Busca un tipo de basica teniendo en cuentra el
     * nombre del tipo basica.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param nombreTipoBasica nombre del tipo basica a buscar.
     * @return la instancia del tipo basica solicitada o null en caso que no se
     * encuentre.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public CmtTipoBasicaMgl findNombreTipoBasica(String nombreTipoBasica) throws ApplicationException {
        CmtTipoBasicaMglDaoImpl dao = new CmtTipoBasicaMglDaoImpl();
        return dao.find(nombreTipoBasica);
    }

    public FiltroConsultaTipoBasicaDto getTablasTipoBasicaSearch(FiltroTablaTipoBasicaDto filtro, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        List<CmtTipoBasicaMgl> tablasTipoBasicasMglList;
        FiltroConsultaTipoBasicaDto filtroConsulta;

  
        filtroConsulta = cmtTipoBasicaMglDaoImpl.findTablasTipoBasicaSearch(filtro, contar, firstResult, maxResults);
        tablasTipoBasicasMglList = filtroConsulta.getListaTablasTipoBasica();
        if (maxResults != 0) {
            for (CmtTipoBasicaMgl cmtTipoBasicaMgl : tablasTipoBasicasMglList) {
                if(cmtTipoBasicaMgl.getModulo()==null || cmtTipoBasicaMgl.getModulo().isEmpty()){
                    continue;
                }
                List<String> cadenaMod = Arrays.asList(cmtTipoBasicaMgl.getModulo().split(","));
                String prefix = "";
                String moduloVista = "";
                for (String modulo : cadenaMod) {
                    CmtBasicaMgl cmtBasicaMgl = new CmtBasicaMgl();
                    BigDecimal modId = new BigDecimal(modulo);
                    cmtBasicaMgl.setBasicaId(modId);
                    CmtBasicaMgl moduloV = cmtBasicaMglDaoImpl.findByBasicaId(cmtBasicaMgl);
                    if (moduloV != null) {
                        moduloVista += prefix + moduloV.getNombreBasica();
                        prefix = ",";
                        cmtTipoBasicaMgl.setModuloVista(moduloVista);
                    }
                }
            }
        }
        filtroConsulta.setListaTablasTipoBasica(tablasTipoBasicasMglList);
        return filtroConsulta;
    }

    public List<AuditoriaDto> construirAuditoria(CmtTipoBasicaMgl cmtTipoBasicaMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<CmtTipoBasicaMgl, CmtTipoBasicaAuditoriaMgl> utilsCMAuditoria =
                new UtilsCMAuditoria<>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(cmtTipoBasicaMgl);
        return listAuditoriaDto;
    }

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
    public List<CmtTipoBasicaMgl> findByComplemento(String complemento) throws ApplicationException {
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        return cmtTipoBasicaMglDaoImpl.findByComplemento(complemento);
    }

    /**
     * M&eacute;todo para consultar los tipos para eliminaci&oacute;n de CM
     * 
     * @return {@link List}&lt;{@link CmtTipoBasicaMgl}> listado de tipos encontrados
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    public List<CmtTipoBasicaMgl> encontrarTiposEliminacionCM() throws ApplicationException {
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        return cmtTipoBasicaMglDaoImpl.encontrarTiposEliminacionCM();
    }

    /**
     * Busca toda la lista de CmtTipoBasicaMgl en el repositorio sin las que
     * tiene informacion adicional
     *
     * @author Victor Bocanegra
     * @param complemento
     * @return List<CmtTipoBasicaMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtTipoBasicaMgl> findAllSinInfoAdi(String complemento) throws ApplicationException {
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        return cmtTipoBasicaMglDaoImpl.findAllSinInfoAdi(complemento);
    }

    /**
     * Busca el tipo basica por identificador interno de la app
     *
     * @author Carlos Villamil Hitss
     * @param codigoInternoApp codigo interno de la aplicacion
     * @return CmtTipoBasicaMgl tipo basica encontrado
     * @throws ApplicationException
     */
    
    public CmtTipoBasicaMgl findByCodigoInternoApp(String codigoInternoApp)
            throws ApplicationException{
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        return cmtTipoBasicaMglDaoImpl.findByCodigoInternoApp(codigoInternoApp);
    }
    
         /**
     * Busca el maximo codigo
     *
     * @author Victor Bocanegra
     * @return String
     * @throws ApplicationException
     */
        
    public String selectMaxCodigo() throws ApplicationException {
        
      CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = 
              new CmtTipoBasicaMglDaoImpl();
      return cmtTipoBasicaMglDaoImpl.selectMaxCodigo();
    }
    
    
        
         /**
     * Buscar registros tipo basica con los parametros proporcionados 
     *
     * @author cardenaslb
     * @return String
     * @throws ApplicationException
     */
    public List<CmtTipoBasicaMgl> findByAllFields(HashMap<String, Object> parametros) throws ApplicationException {
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        return cmtTipoBasicaMglDaoImpl.findByAllFields(parametros);
    }

}
