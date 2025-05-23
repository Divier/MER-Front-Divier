/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.visitasTecnicas.facade;

import co.com.claro.visitasTecnicas.entities.TmMasVtCarguemasivo;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author becerraarmr
 */
@Local
public interface TmMasVtCarguemasivoFacadeLocal {

  public BigDecimal findMaxArchivo();

  public boolean find(TmMasVtCarguemasivo aux);

  public boolean create(TmMasVtCarguemasivo aux);

  
}
