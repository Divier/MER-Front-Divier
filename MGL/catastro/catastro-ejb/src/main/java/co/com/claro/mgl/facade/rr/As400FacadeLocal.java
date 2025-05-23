/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.facade.rr;

import java.util.Map;
import javax.ejb.Local;

/**
 * Representar la clase con las acciones que puede realizar en la As400
 * 
 * Clase Interface para conocer las acciones que se pueden solicitar en la As400
 * 
 * @author becerraarmr
 * 
 * @version 2017 revisión 1.0
 */
@Local
public interface As400FacadeLocal {

  /**
   * 
   * Busca en la base de datos la data de la cuenta matriz
   * 
   * @author becerraarmr
   * @version 2017 revisión 1.0
   * 
   * @param cuentaMatriz
   * @return un Map con los valores consultados
   */
  Map<String, String> findDataCuentaMatriz(String cuentaMatriz);
  
}
