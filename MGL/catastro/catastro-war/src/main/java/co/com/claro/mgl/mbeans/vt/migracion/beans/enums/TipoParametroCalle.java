/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.migracion.beans.enums;

import java.io.Serializable;

/**
 * Objetivo:
 * <p>
 * Descripción:
 *
 * @author becerraarmr
 *
 * @version 2017 revisión 1.0
 */
public enum TipoParametroCalle implements Serializable {
  //Para realizar las busqueda en la base de datos con el parámetro tipo_segmento
  PARAMETRO_CALLE_SEGMENTO("TIPO_SEGMENTO"),
  PARAMETRO_CALLE_SOLICITUD("TIPO_SOLICITUD"),
  PARAMETRO_CALLE_APARTAMENTOS("APARTAMENTOS"),
  PARAMETRO_CALLE_VIAS("CALLES"),
  PARAMETRO_CALLE_VENTA("VENTA"),
  PARAMETRO_CALLE_USUARIOS("USUARIOS"),
  PARAMETRO_CALLE_PRODUCTO("PRODUCTO"),
  PARAMETROS_CALLE_RESULTADO_GESTION("RESULTADO_GESTION"),
  //Conjunto edificio
  PARAMETROS_CALLE_RESULTADO_GESTION_CE("RESULTADO_GESTION_CE"),
  //Verificación de casas  
  PARAMETROS_CALLE_RESULTADO_GESTION_VC("RESULTADO_GESTION_VC"),
  //Creación de cuenta Matriz
  PARAMETROS_CALLE_RESULTADO_GESTION_CREACM("RESULTADO_GESTION_CREACM"),
  //Creación Hhpp Cuenta Matriz
  PARAMETROS_CALLE_RESULTADO_GESTION_CREAHHPPCM("RESULTADO_GESTION_CREAHHPPCM"),
  //Modificación Cuenta Matriz
  PARAMETROS_CALLE_RESULTADO_GESTION_MODCM("RESULTADO_GESTION_MODCM"),
  PARAMETROS_CALLE_RESULTADO_GESTION_MODIFICACION("RESULTADO_GESTION_MODIFICACION");


  private final String valor;

  private TipoParametroCalle(String valor) {
    this.valor = valor;
  }

  public String getValor() {
    return valor;
  }
}
