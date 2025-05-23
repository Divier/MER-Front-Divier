/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.migracion.beans.enums;

/**
 * Establecer las constantes de tipo solicitud.
 * 
 * Establece cada una de las constantes usadas para tipo de solicitudes.
 * 
 * @author becerraarmr
 * @version 2017 revisión 1.0
 */
public enum EstadoSolicitud {
  //La accion de solicitar replanteamiento.
  PENDIENTE("PENDIENTE"),
  //La accion de solicitar visita tecnica edificio conjunto.
  FINALIZADO("FINALIZADO"),
  
  VERIFICADO("VERIFICADO"),

  RECHAZADO("RECHAZADO");
  
  /**
   * Valor de la constante
   */
  private final String valor;

  /**
   * Crear la instancia
   * <p>
   * Crea una instancia TipoSolicitud
   *
   * @author becerraarmr
   */
  private EstadoSolicitud(String valor) {
    this.valor = valor;
  }

  /**
   * Buscar el valor del atributo.
   * <p>
   * Muestra el valor del atributo.
   *
   * @author becerraarmr
   *
   * @return el valor que representa el atributo.
   */
  public String getValor() {
    return valor;
  }

}
