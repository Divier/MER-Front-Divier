/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.dtos;

/**
 * Establecer el nombre de un atributo de una entidad y su valor 
 * <p>
 * Establecer el nombre de un atributo y el valor que se le asigna
 *
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 */
public class AtributoValorDto {

  private String nombreAtributo;
  private Object valorAtributo;

  /**
   * Crear la instancia
   * <p>
   * Crea una instancia
   * 
   * @author becerraarmr
   */
  public AtributoValorDto() {
  }

  /**
   * Crear la instancia
   * <p>
   * Crea una instancia
   * 
   * @author becerraarmr
   * @param nombreAtributo nombre de un atributo de una entidad
   * @param valorAtributo valor asignado al atributo
   */
  public AtributoValorDto(String nombreAtributo, Object valorAtributo) {
    this.nombreAtributo = nombreAtributo;
    this.valorAtributo = valorAtributo;
  }

  /**
   * Buscar el valor del atributo.
   * 
   * Busca el valor del atributo correspondiente.
   * 
   * @author becerraarmr
   * 
   * @return el valor que representa el atributo
   */
  public String getNombreAtributo() {
    return nombreAtributo;
  }

  /**
   * Actualizar el valor del atributo.
   * 
   * Actualiza el valor del atributo correspondiente.
   * 
   * @author becerraarmr
   * 
   * @param  nombreAtributo valor actualizar 
   */
  public void setNombreAtributo(String nombreAtributo) {
    this.nombreAtributo = nombreAtributo;
  }

  /**
   * Buscar el valor del atributo.
   * 
   * Busca el valor del atributo correspondiente.
   * 
   * @author becerraarmr
   * 
   * @return el valor que representa el atributo
   */
  public Object getValorAtributo() {
    return valorAtributo;
  }

  /**
   * Actualizar el valor del atributo.
   * 
   * Actualiza el valor del atributo correspondiente.
   * 
   * @author becerraarmr
   * 
   * @param valorAtributo valor nuevo
   */
  public void setValorAtributo(Object valorAtributo) {
    this.valorAtributo = valorAtributo;
  }

}
