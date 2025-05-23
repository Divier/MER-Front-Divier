/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.procesomasivo;

import co.com.claro.mgl.entities.procesomasivo.CargueArchivoLogItem;
import co.com.claro.mgl.error.ApplicationException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public interface CargueArchivoLogItemFacadeLocal{
    
    /**
     * Busca todos los registros que fueron leidos del excel
     *
     * @author Victor Bocanegra
     * @param idArchivo id del archivo resumen
     * @return List<CargueArchivoLogItem>
     * @throws ApplicationException
     */
         List<CargueArchivoLogItem> findByIdArchivoGeneral(Long idArchivo)
            throws ApplicationException;

    /**
     * Busca todos los registros que fueron leidos del masivo de fraudes
     * @author Diana Enriquez 
     * @param idArchivo id del archivo resumen
     * @return List<CargueArchivoLogItem>
     * @throws ApplicationException
     */
    public List<CargueArchivoLogItem> findByIdArchivoFraude(Long idArchivo)
            throws ApplicationException ;

    /**
     * Busca todos los registros que tuvieron cambios
     *
     * @author Victor Bocanegra
     * @param idArchivo id del archivo resumen
     * @return List<CargueArchivoLogItem>
     * @throws ApplicationException
     */
    List<CargueArchivoLogItem> findByIdArchivoGeneralAndProcesado(Long idArchivo)
            throws ApplicationException; 
    
    /**
     * Busca registro por id del archivo general y complemento
     *
     * @author Victor Bocanegra
     * @param idArchivo id del archivo resumen
     * @param idComplemento id del complemento
     * @return CargueArchivoLogItem
     * @throws ApplicationException
     */
    CargueArchivoLogItem findByIdArchivoLogAndIdComplemento(Long idArchivo, BigDecimal idComplemento)
            throws ApplicationException; 
    
    /**
     * Solitar la creación de un registro en la base de datos.
     * 
     * Se solicita por medio CargueArchivoLogItemDao actualizar un registro 
     * CargueArchivoLogItem en la base de datos.
     * 
     * @author bocanegra Vm
     * @param cargueArchivoLogItem objeto con la data a registrar en la base 
     * de datos.
     * @return el CargueArchivoLogItem creado con la id faltante la cual es 
     * generada de forma automática.
     * @throws ApplicationException Si el objeto a actualizar es nulo o si hay
     * problemas al momento de hacer la actualización en la base de datos.
     */
      CargueArchivoLogItem update(CargueArchivoLogItem cargueArchivoLogItem)
            throws ApplicationException;
}
