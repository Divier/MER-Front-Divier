/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.visitasTecnicas.facade;

import co.com.claro.visitasTecnicas.business.TmMasVtCarguemasivoManager;
import co.com.claro.visitasTecnicas.entities.TmMasVtCarguemasivo;
import java.math.BigDecimal;
import javax.ejb.Stateless;

/**
 *
 * @author becerraarmr
 */
@Stateless
public class TmMasVtCarguemasivoFacade implements TmMasVtCarguemasivoFacadeLocal {

  @Override
  public BigDecimal findMaxArchivo() {
    TmMasVtCarguemasivoManager manager=new TmMasVtCarguemasivoManager();
    return manager.findMaxArchivo();
  }

  @Override
  public boolean find(TmMasVtCarguemasivo aux) {
    TmMasVtCarguemasivoManager manager=new TmMasVtCarguemasivoManager();
    return manager.find(aux);
  }
  
  @Override
  public boolean create(TmMasVtCarguemasivo aux){    
    TmMasVtCarguemasivoManager manager=new TmMasVtCarguemasivoManager();    
    return manager.create(aux);
  }

}
