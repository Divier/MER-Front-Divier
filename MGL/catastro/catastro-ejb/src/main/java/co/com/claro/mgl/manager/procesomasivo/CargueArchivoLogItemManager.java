/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.manager.procesomasivo;

import co.com.claro.mgl.dao.procesomasivo.CargueArchivoLogItemDao;
import co.com.claro.mgl.entities.procesomasivo.CargueArchivoLogItem;
import co.com.claro.mgl.error.ApplicationException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Realizar las acciones lógicas de CargueArchivoLogItem
 * <p>
 * Se realizan todas las acciones lógicas para el CargueArchivoLogItem y se hace
 * las solictudes a la base de datos por medio del dao CargueArchivoLogItemDao
 *
 * @author becerraarmr
 *
 */
public class CargueArchivoLogItemManager {

    /**
     * Solitar la creación de un registro en la base de datos.
     * 
     * Se solicita por medio CargueArchivoLogItemDao crear un registro 
     * CargueArchivoLogItem en la base de datos.
     * 
     * @author becerraarmr
     * @param cargueArchivoLogItem objeto con la data a registrar en la base 
     * de datos.
     * @return el CargueArchivoLogItem creado con la id faltante la cual es 
     * generada de forma automática.
     * @throws ApplicationException Si el objeto a registrar es nulo o si hay
     * problemas al momento de hacer la insercción en la base de datos.
     */
    public CargueArchivoLogItem create(CargueArchivoLogItem cargueArchivoLogItem)
            throws ApplicationException {
        if(cargueArchivoLogItem==null){
            throw new ApplicationException("No se pude crear un registro en la "
                    + "base de datos porque el objeto a crear es nulo");
        }
        
        CargueArchivoLogItemDao dao = new CargueArchivoLogItemDao();
        return dao.create(cargueArchivoLogItem);
    }
    
    /**
     * Solitar la creación de un registro en la base de datos.
     * 
     * Se solicita por medio CargueArchivoLogItemDao actualizar un registro 
     * CargueArchivoLogItem en la base de datos.
     * 
     * @author becerraarmr
     * @param cargueArchivoLogItem objeto con la data a registrar en la base 
     * de datos.
     * @return el CargueArchivoLogItem creado con la id faltante la cual es 
     * generada de forma automática.
     * @throws ApplicationException Si el objeto a actualizar es nulo o si hay
     * problemas al momento de hacer la actualización en la base de datos.
     */
    public CargueArchivoLogItem update(CargueArchivoLogItem cargueArchivoLogItem)
            throws ApplicationException {
        if(cargueArchivoLogItem==null){
            throw new ApplicationException("No se pudo actualizar un registro en la "
                    + "base de datos porque el objeto a crear es nulo");
        }
        CargueArchivoLogItemDao dao = new CargueArchivoLogItemDao();
        return dao.update(cargueArchivoLogItem);
    }
    
    /**
     * Busca todos los registros que fueron leidos del excel
     *
     * @author Victor Bocanegra
     * @param idArchivo id del archivo resumen
     * @return List<CargueArchivoLogItem>
     * @throws ApplicationException
     */
    public List<CargueArchivoLogItem> findByIdArchivoGeneral(Long idArchivo)
            throws ApplicationException {
        CargueArchivoLogItemDao dao = new CargueArchivoLogItemDao();
        return dao.findByIdArchivoGeneral(idArchivo);
    }

    /**
     * Busca todos los registros que fueron leidos del masivo de fraudes
     * @author Diana Enriquez 
     * @param idArchivo id del archivo resumen
     * @return List<CargueArchivoLogItem>
     * @throws ApplicationException
     */
    public List<CargueArchivoLogItem> findByIdArchivoFraude(Long idArchivo)
            throws ApplicationException {
        CargueArchivoLogItemDao dao = new CargueArchivoLogItemDao();
        return dao.findByIdArchivoFraude(idArchivo);
    }
    
   
    /**
     * Busca todos los registros que tuvieron cambios
     *
     * @author Victor Bocanegra
     * @param idArchivo id del archivo resumen
     * @return List<CargueArchivoLogItem>
     * @throws ApplicationException
     */
    public List<CargueArchivoLogItem> findByIdArchivoGeneralAndProcesado(Long idArchivo)
            throws ApplicationException {

        CargueArchivoLogItemDao dao = new CargueArchivoLogItemDao();
        return dao.findByIdArchivoGeneralAndProcesado(idArchivo);
    }
    
    /**
     * Busca registro por id del archivo general y complemento
     *
     * @author Victor Bocanegra
     * @param idArchivo id del archivo resumen
     * @param idComplemento id del complemento
     * @return CargueArchivoLogItem
     * @throws ApplicationException
     */
    public CargueArchivoLogItem findByIdArchivoLogAndIdComplemento(Long idArchivo, BigDecimal idComplemento)
            throws ApplicationException {

        CargueArchivoLogItemDao dao = new CargueArchivoLogItemDao();
        return dao.findByIdArchivoLogAndIdComplemento(idArchivo, idComplemento);
    }
}
