/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.facade.procesomasivo;

import co.com.claro.mgl.entities.procesomasivo.HhppCargueArchivoLog;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.manager.procesomasivo.HhppCargueArchivoLogManager;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Mostrar las acciones para HhppCargueArchivoLog
 * Se muestra los métodos que se pueden usar para realizar diferentes acciones
 * con HhppCargueArchivoLog
 * 
 * @author becerraarmr
 */
@Stateless
public class HhppCargueArchivoLogFacade implements HhppCargueArchivoLogFacadeLocal {

  /**
   * Mostrar el listado de HhppCargueArchivoLog con estado
   * 
   * Muestra el listado de registros HhppCargueArchivoLog con el estado 
   * insertado en el parámetro de entrada.
   * @author becerraarmr
   * @param estado valor del estado para realizar la busqued
   * @return un listado según el estado establecido.
   * @throws ApplicationException si hay algún error en la busqueda de los datos
   */
  @Override
  public List<HhppCargueArchivoLog> findAllEstado(short estado) 
          throws ApplicationException {
    HhppCargueArchivoLogManager manager = new HhppCargueArchivoLogManager();
    return manager.findAllEstado(estado);
  }

  @Override
  public boolean delete(HhppCargueArchivoLog t) throws ApplicationException {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public HhppCargueArchivoLog findById(HhppCargueArchivoLog sqlData) throws ApplicationException {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }

  /**
   * Crear registro en la tabla
   * Se crea un nuevo registro en la base de datos
   * 
   * @param t objeto con la data para crear el nuevo registro
   * @return HhppCargueArchivoLog con el objeto creado o null en caso de no crear
   * nada
   * @throws ApplicationException si hay algún problema al momento de crear 
   * el registro en la base de datos.
   */
  @Override
  public HhppCargueArchivoLog create(HhppCargueArchivoLog t) 
          throws ApplicationException {
    HhppCargueArchivoLogManager manager = new HhppCargueArchivoLogManager();
    return manager.create(t);
  }

  /**
   * Actualizar registro
   * Se actualiza el regisro al parámetro correspondiente.
   * 
   * @author becerraarmr
   * @param t registro con la data a actualizar
   * @return HhppCargueArchivoLog con la data actualizada a null si no se 
   * puede realizar la acción
   * @throws ApplicationException si hay algún error
   */
  @Override
  public HhppCargueArchivoLog update(HhppCargueArchivoLog t) 
          throws ApplicationException {
    HhppCargueArchivoLogManager manager = new HhppCargueArchivoLogManager();
    return manager.update(t);
  }

  /**
   * Buscar listado de HhppCargueArchivoLog por rango y estado
   * 
   * Se solicita la busqueda de HhppCargueArchivoLog por los parámetros rango y
   * estado.
   * @author becerraarmr
   * @param rango vector de dos posiciones para el inicio y el final
   * @param estado valor del estado del HhppCargueArchivoLog
   * @param usuario usuario quien tiene asignado el recurso.
   * @return un listado con los registros encontrados
   * @throws ApplicationException si hay algún error al momento de realizar 
   * las peticiones.
   */
  @Override
  public List<HhppCargueArchivoLog> findRangeEstado(
          int[] rango, short estado,String usuario) 
          throws ApplicationException {
    HhppCargueArchivoLogManager manager = new HhppCargueArchivoLogManager();
    return manager.findRangeEstado(rango,estado,usuario);
  }
  
  /**
   * Diana -> Buscar listado de HhppCargueArchivoLog por rango y estado para Fraudes
   * @param rango
   * @param estado
   * @param usuario
   * @return
   * @throws ApplicationException 
   */
   @Override
   public List<HhppCargueArchivoLog> findRangeEstadoByOrigen(
          int[] rango, short estado,String usuario) 
          throws ApplicationException {
    HhppCargueArchivoLogManager manager = new HhppCargueArchivoLogManager();
    return manager.findRangeEstadoByOrigen(rango,estado,usuario);
  }

  /**
   * Contar según el estado
   * Solicita al HhppCargueArchivoLogManager contar la cantidad según el estado
   * @author becerraarmr
   * @param estado valor para hacer la busqueda
   * @param usuario usuario que tiene registrado el registro
   * @return el valor encontrado según la solicitud.
   * @throws co.com.claro.mgl.error.ApplicationException
   */
  @Override
  public int countEstado(short estado,String usuario) 
          throws ApplicationException {
    HhppCargueArchivoLogManager manager = new HhppCargueArchivoLogManager();
    return manager.countEstado(estado,usuario);
  }

  @Override
  public List<HhppCargueArchivoLog> findAll() throws ApplicationException {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
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
    @Override
    public HhppCargueArchivoLog findArchivoByNombre(String nombreArchivo)
            throws ApplicationException {

        HhppCargueArchivoLogManager manager = new HhppCargueArchivoLogManager();
        return manager.findArchivoByNombre(nombreArchivo);
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
    @Override
    public HhppCargueArchivoLog findByNombreArchivoTcrm(String nombreArchivo)
            throws ApplicationException {

        HhppCargueArchivoLogManager manager = new HhppCargueArchivoLogManager();
        return manager.findByNombreArchivoTcrm(nombreArchivo);
    }
}
