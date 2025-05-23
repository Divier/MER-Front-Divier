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
public enum TipoSolicitud {
  //La accion de solicitar replanteamiento.
  TIPO_SOL_REPLANTEAMIENTO("REPLANVTCON"),
  //La accion de solicitar visita tecnica edificio conjunto.
  TIPO_SOL_VT_EDIFICIO_CONJUNTO("VTCONJUNTOS"),
  //La accion de solicitar replanteamiento en casa.
  TIPO_SOL_VT_CASAS("REPLANVTCASA"),
  //La accion de solicitar la creacion de cuentra matriz.
  TIPO_SOL_CREACION_CUENTA_MATRIZ("CREACIONCM"),
  //la accion de solicitar viabilidad de internet.
  TIPO_SOL_VIABILIDAD_INTERNET("VIINTERNET"),
  //La accion de solicitar creacion de hhpp cuenta matriz.
  TIPO_SOL_CREACION_HHPP_CUENTA_MATRIZ("HHPPCMATRIZ"),
  //La accion de solicitar modificar eliminar cuenta matriz.
  TIPO_SOL_MODIFICAR_ELIMINAR_CUENTA_MATRIZ("VTMODECM"),
  //Estado de la solicitud, verificación de casas.
  TIPO_VTCASA("VTCASA"),
  //Estado de la solicitud, HHPP Unidireccional.
  TIPO_VTHHPPUNIDI("CREACION HHPP UNIDI"),
  //Estado de la solicitud, Cambio Estrato.
  TIPO_CAMBIO_ESTRATO("CAMBIOEST"),
   //Estado de la solicitud, Vt edificio o conjunto
  TIPO_VT_EDIFICIO_CONJUNTO("VTCONJUNTOS"),
   //Estado de la solicitud,Vt casas.
  TIPO_VT_CASAS("REPLANVTCASA"),
   //Estado de la solicitud, Creacion CM.
  TIPO_CREACION_CM("CREACIONCM"),
   //Estado de la solicitud, Crecaion de hhpp en cuenta matriz
  TIPO_CREACION_HHPP_CM("HHPPCMATRIZ"),
   //Estado de la solicitud, modificar y/o eliminar cuenta matriz.
  TIPO_MOD_ELI_CM("VTMODECM"),
  
  
    ///////////////////////////////////////
    VALOR_TIPO_SOL_CREACION_HHPP("0"),
    VALOR_TIPO_SOL_CAMBIO_DIR("1"),
    VALOR_TIPO_SOL_CAMBIO_ESTRATO("2"),
    VALOR_TIPO_SOL_REPLANTEAMIENTO("4"),
    VALOR_TIPO_SOL_VIABILIDAD_INTERNET("5"),
    VALOR_TIPO_CAMBIO_ESTRATO_MG("6"),
    VALOR_TIPO_VT_EDIFICIO_CONJUNTO("7"),
    VALOR_TIPO_VT_CASAS("8"),
    VALOR_TIPO_CREACION_CM("9"),
    VALOR_TIPO_CREACION_HHPP_CM("10"),
    VALOR_TIPO_MOD_ELI_CM("11"),
    VALOR_TIPO_REACTIVACION_HHPP("3"),
    VALOR_TIPO_SOL_VALIDACION_COBERTURA("12"),
    VALOR_TIPO_ESCALAMIENTOS_HHPP("13");

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
  private TipoSolicitud(String valor) {
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
