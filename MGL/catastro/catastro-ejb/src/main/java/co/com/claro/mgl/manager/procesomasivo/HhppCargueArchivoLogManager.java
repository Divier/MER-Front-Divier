/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.manager.procesomasivo;

import co.com.claro.mgl.dao.procesomasivo.HhppCargueArchivoLogDao;
import co.com.claro.mgl.entities.procesomasivo.HhppCargueArchivoLog;
import co.com.claro.mgl.error.ApplicationException;
import java.util.List;

/**
 * Solicitar acciones a HhhppCargueArchivoLog
 * <p>
 * Se realiza la lógica con las acciones a la entidad HhhppCargueArchivo
 *
 * @author becerraarmr
 *
 */
public class HhppCargueArchivoLogManager {

    public HhppCargueArchivoLog create(HhppCargueArchivoLog hhppCargueArchivoLog)
            throws ApplicationException {
        if (hhppCargueArchivoLog == null) {
            return null;
        }
        HhppCargueArchivoLogDao dao = new HhppCargueArchivoLogDao();
        Long valor = dao.sequencia();
        if (valor != null) {
            hhppCargueArchivoLog.setIdArchivoLog(valor);
            return dao.create(hhppCargueArchivoLog);
        }
        return null;
    }
  
 /**
  * Actualizar la base de datos
  * Actualizar un registro en la base de datos.
  * @author becerraarmr
  * @param hhppCargueArchivoLog objeto con el registro actualizar
  * @return el objeto actualizado
  * @throws ApplicationException error para actualizar. 
  */
  public HhppCargueArchivoLog update(HhppCargueArchivoLog hhppCargueArchivoLog)
          throws ApplicationException {
    if(hhppCargueArchivoLog==null) {
      return null;
    }
    HhppCargueArchivoLogDao dao = new HhppCargueArchivoLogDao();
    return dao.update(hhppCargueArchivoLog);
  }

  /**
   * Buscar el registro de archivo a procesar.
   * 
   * Solicitar en la base de datos el registro con el nombre del archivo a proce-
   * sar, que tenga estado 0 y el primero de orden ascendente.
   * @author becerraarmr
   * @return HhppCargueArchivoLog encontrado.
   * @throws co.com.claro.mgl.error.ApplicationException si hubo un problema 
   * con la consulta realizada.
   */
    public HhppCargueArchivoLog findToProcesar() throws ApplicationException {
        HhppCargueArchivoLogDao dao = new HhppCargueArchivoLogDao();
        return dao.findToProcesar();
    }

  /**
   * Solicitar la busqueda de los registros con el parámetro estado en 
 HhppCargueArchivoLogDao
   * @author becerraarmr
   * @param estado valor con el que se va a buscar
   * @return el listado de parámetros
   * @throws co.com.claro.mgl.error.ApplicationException si hubo un problema 
   * con la consulta realizada.
   */
    public List<HhppCargueArchivoLog> findAllEstado(short estado)
            throws ApplicationException {
        HhppCargueArchivoLogDao dao = new HhppCargueArchivoLogDao();
        return dao.findAllEstado(estado);
    }

  /**
   * Solicitar a HhppCargueArchivoLogDao la busqueda de registros 
 HhppCargueArchivoLog por rango y estado.
   * 
   * Se solicita al dao encargado la busqueda de los datos en un determinado
   * rango y según el estado que se quiere buscar.
   * @author becerraarmr
   * @param rango vector de inicio y fin de la busqueda
   * @param estado valor del estado a buscar
   * @param usuario quien realiza el cargue
   * @return un lisatado con los datos encontrados
   * @throws co.com.claro.mgl.error.ApplicationException si hubo un problema 
   * con la consulta realizada.
   */
    public List<HhppCargueArchivoLog> findRangeEstado(
            int[] rango, short estado, String usuario)
            throws ApplicationException {
        HhppCargueArchivoLogDao dao = new HhppCargueArchivoLogDao();
        if (usuario != null) {
            if (!usuario.isEmpty()) {
                return dao.findRangeEstado(rango, estado, usuario);
            }
        }
        return dao.findRangeEstado(rango, estado);
    }
    
    
    /**
   * Diana -> Solicitar a HhppCargueArchivoLogDao la busqueda de registros 
    HhppCargueArchivoLog por rango y estado, para Fraudes
   * 
   * @param rango vector de inicio y fin de la busqueda
   * @param estado valor del estado a buscar
   * @param usuario quien realiza el cargue
   * @return un lisatado con los datos encontrados
   * @throws co.com.claro.mgl.error.ApplicationException si hubo un problema 
   * con la consulta realizada.
   */
    public List<HhppCargueArchivoLog> findRangeEstadoByOrigen(
            int[] rango, short estado, String usuario)
            throws ApplicationException {
        HhppCargueArchivoLogDao dao = new HhppCargueArchivoLogDao();
        if (usuario != null) {
            if (!usuario.isEmpty()) {
                return dao.findRangeEstadoByOrigen(rango, estado, usuario);
            }
        }
        return dao.findRangeEstadoByOrigen(rango, estado);
    }

  /**
   * Solictatar a HhppCargueArchivoLogDao contar la cantida de registros
 según el estado.
   * Solicita el conteo de HhppCargueArchivoLog según el estado
   * @author becerraarmr
   * @param estado valor para realizar el conteo
   * @param usuario
   * @return el valor del conteo realizado
   * @throws ApplicationException si hay algún error al realizar el conteo.
   */
    public int countEstado(short estado, String usuario)
            throws ApplicationException {
        HhppCargueArchivoLogDao dao = new HhppCargueArchivoLogDao();
        if (usuario != null) {
            if (!usuario.isEmpty()) {
                return dao.countEstado(estado, usuario);
            }
        }
        return dao.countEstado(estado);
    }
  
      /**
     * Buscar en la base de datos el registro del archivo.
     *
     * @author bocanegra vm
     * @param nombreArchivo nombre del archivo
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException si hay algún error al
     * momento de realizar la petición.
     */
    public HhppCargueArchivoLog findArchivoByNombre(String nombreArchivo)
            throws ApplicationException {
        HhppCargueArchivoLogDao dao = new HhppCargueArchivoLogDao();
        return dao.findArchivoByNombre(nombreArchivo);
    }
 
    /**
     * Buscar en la base de datos el registro del archivo resumen con el nombre
     * del archivo TCRM.
     *
     * @author bocanegra vm
     * @param nombreArchivo nombre del archivo
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException si hay algún error al
     * momento de realizar la petición.
     */
    public HhppCargueArchivoLog findByNombreArchivoTcrm(String nombreArchivo)
            throws ApplicationException {

        HhppCargueArchivoLogDao dao = new HhppCargueArchivoLogDao();
        return dao.findByNombreArchivoTcrm(nombreArchivo);

    }
}
