/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.migracion.beans.enums;

/**
 *
 * @author becerraarmr
 */
public enum CierreMasivo {
  RESPUESTA_CM("rpta_cierremasivo"),
  NAME_FILE_CM("name_file_cierremasivo"),
  SIZE_FILE_CM("size_file_cierremasivo");
  
  /**
   * Valor de la constante
   */
  private final String valor;
  
  /**
   * Crear la instancia
   * <p>
   * Crea una instancia CierreMasivo
   *
   * @author becerraarmr
   */
  private CierreMasivo(String valor) {
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
