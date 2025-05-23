package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtTipoOtMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaSlaOtDto;
import co.com.claro.mgl.dtos.FiltroConsultaSubTipoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.utils.Constant;
import java.math.BigDecimal;
import java.util.List;
import co.com.claro.mgl.dtos.TipoOtCmDirDto;
/**
 * Manager Tipo de Orden de Trabajo. Contiene la logica de negocio para el
 * manejo de los tipos de ordenes de trabajo en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtTipoOtMglManager {

    CmtTipoOtMglDaoImpl dao = new CmtTipoOtMglDaoImpl();

    /**
     * Obtiene todos los Tipo de Ordenes de Trabajo. Permite obtener todos los
     * tipos de Ordenes de Trabajo que existen en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @return Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findAll() throws ApplicationException {
        return dao.findAllItemsActive();
    }

    /**
     * Obtiene un Tipo de Ordenes de Trabajo por su ID. Permite obtener un tipo
     * de Ordene de Trabajo que existen en el repositorio por el ID.
     *
     * @author Johnnatan Ortiz
     * @param idTipoOt Id del tipo de Orden de trabajo
     * @return Tipo de Ordene de Trabajo
     * @throws ApplicationException
     */
    public CmtTipoOtMgl findById(BigDecimal idTipoOt) throws ApplicationException {
        return dao.find(idTipoOt);
    }

    /**
     * Valida el estado de la CM para la creacion de la OT. Permite validar el
     * estado actual de la cuenta matriz para la creacion de un Orden de
     * trabajo.
     *
     * @author Johnnatan Ortiz
     * @param tipoOt Tipo de Orden de trabajo
     * @param cuentaMatrizMgl Cuenta matriz sobre la cual se quiere crear la OT
     * @return si el estado actual de la CM permite la creacion de la OT
     * @throws ApplicationException
     */
    public boolean validaEstadoCm(CmtTipoOtMgl tipoOt,
            CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException {
        boolean result = false;
        CmtSubEdificioMglManager subEdificioMglManager = new CmtSubEdificioMglManager();

        if (tipoOt != null && tipoOt.getEstadoIniCm() != null && cuentaMatrizMgl != null) {
            List<CmtSubEdificioMgl> subEdificioList =
                    subEdificioMglManager.findSubEdificioByCuentaMatriz(cuentaMatrizMgl);
            //validamos que existan subEdificios asociados a la CM
            if (subEdificioList != null && !subEdificioList.isEmpty()) {
                if (subEdificioList.size() == 1) {
                    CmtBasicaMgl estadoSubEdificio = subEdificioList.get(0).getEstadoSubEdificioObj();
                    /*
                     * validamos si el edificio encontrado se trata del edificio 
                     * general de una CM Multiedificio, sino verificamos el estado 
                     * del para compararlo contra el estado en cual se debe encontrar 
                     * la CM para crear la OT
                     */
                    if (estadoSubEdificio.getIdentificadorInternoApp()
                            .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                        result = true;
                    } else {
                        result = estadoSubEdificio.getBasicaId().compareTo(
                                tipoOt.getEstadoIniCm().getBasicaId()) == 0;
                    }
                } else {
                    /*
                     * buscamos la existencia de un subedificio general de la cm
                     * multiedificio
                     */
                    for (CmtSubEdificioMgl se : subEdificioList) {
                        if (se.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                                && se.getEstadoSubEdificioObj().getIdentificadorInternoApp()
                                .equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                            result = true;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    public CmtTipoOtMgl create(CmtTipoOtMgl cmtTipoBasicaMgl) throws ApplicationException {
        return dao.create(cmtTipoBasicaMgl);
    }

    public CmtTipoOtMgl update(CmtTipoOtMgl cmtTipoBasicaMgl) throws ApplicationException {
        return dao.update(cmtTipoBasicaMgl);
    }

    public boolean delete(CmtTipoOtMgl cmtTipoBasicaMgl) throws ApplicationException {
        return dao.delete(cmtTipoBasicaMgl);
    }

    public FiltroConsultaSlaOtDto findListTablaSlaOt(FiltroConsultaSubTipoDto filtro,
            boolean contar, int firstResult, int maxResults) throws ApplicationException {
        return dao.findListTablaSlaOt(filtro, contar, firstResult, maxResults);
    }

    public List<CmtTipoOtMgl> findByBasicaId(CmtBasicaMgl cmtBasicaMgl) throws ApplicationException {
        return dao.findByBasicaId(cmtBasicaMgl);
    }

    /**
     * Busca el subtipo de ot por identificador interno de la app
     *
     * @author Victor Bocanegra
     * @param codigoInternoApp codigo interno de la tabla
     * @return CmtTipoOtMgl encontrado
     * @throws ApplicationException
     */
    public CmtTipoOtMgl findByCodigoInternoApp(String codigoInternoApp) throws ApplicationException {
        return dao.findByCodigoInternoApp(codigoInternoApp);
    }
    
    /**
     * Busca los subtipo de ot escluyendo los de acometida u otro
     *
     * @author Victor Bocanegra
     * @param idAco id de acometidas u otro
     * @return List<CmtTipoOtMgl> encontrado
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findByNoIdAco(List<BigDecimal> idAco) throws ApplicationException {
        return dao.findByNoIdAco(idAco);

    }
    
    /**
     * Obtiene todos los Tipo de Ordenes de Trabajo que generan OT de acometidas
     * que existen en el repositorio.
     *
     * @param tipoOtAcometida tipo OT acometida
     * @author Victor Bocanegra
     * @return List<CmtTipoOtMgl> Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findAllTipoOtGeneraAco(CmtTipoOtMgl tipoOtAcometida)
            throws ApplicationException {
        return dao.findAllTipoOtGeneraAco(tipoOtAcometida);
    }
    
    /**
     * Obtiene todos los Sub Tipos de Ordenes de Trabajo que generan acometidas
     * en el repositorio.
     *
     * @author Victor Bocanegra
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findAllTipoOtAcometidas()
            throws ApplicationException {

        return dao.findAllTipoOtAcometidas();
    }
  
    /**
     * Obtiene todos los Sub Tipos de Ordenes de Trabajo de un tipode trabajo
     * que no son acometidas son VTs en el repositorio.
     *
     * @author Victor Bocanegra
     * @param cmtBasicaMgl Tipo de trabajo
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findByTipoTrabajoAndIsVT(CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {

        return dao.findByTipoTrabajoAndIsVT(cmtBasicaMgl);

    }
    
    /**
     * Obtiene todos los Sub Tipos de Ordenes de Trabajo Vts que generan
     * acometidas en el repositorio.
     *
     * @author Victor Bocanegra
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findAllTipoOtVts()
            throws ApplicationException {

        return dao.findAllTipoOtVts();
    }
    
      
    /**
     * Obtiene todos los Sub Tipos de Ordenes de Trabajo dado un id 
     *
     * @author @cardeenaslb
     * @param cmtBasicaMgl Tipo de trabajo
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findByIdTipoTrabajo(BigDecimal cmtBasicaMgl)
            throws ApplicationException {

        return dao.findById(cmtBasicaMgl);

    }
          
    /**
     * Obtiene todos los Sub Tipos de Ordenes de Trabajo dado el tipo de ot y la tecnologia  
     *
     * @author
     * @param tipoOt
     * @param basicaTecnologia @cardenaslb
     * @return List<CmtTipoOtMgl> Sub Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findByTipoTrabajoAndTecno(CmtBasicaMgl tipoOt, CmtBasicaMgl basicaTecnologia)
            throws ApplicationException {

        return dao.findByTipoTrabajoAndTecno( tipoOt, basicaTecnologia);

    }
    
    
    public List<TipoOtCmDirDto> findAllSubTipoOtCmHhpp() throws ApplicationException {
        return dao.findAllSubTipoOtCmHhpp();
    }
  
    /**
     * Obtiene todos los Sub Tipo de Ordenes de Trabajo segun la tecnologia
     *
     * @author bocanegravm
     * @param basicaTecnologia
     * @return List<CmtTipoOtMgl> Tipos de Ordenes de Trabajo
     * @throws ApplicationException
     */
    public List<CmtTipoOtMgl> findSubTipoOtByTecno(CmtBasicaMgl basicaTecnologia)
            throws ApplicationException {
        
        return dao.findSubTipoOtByTecno(basicaTecnologia);
        
    }
}
