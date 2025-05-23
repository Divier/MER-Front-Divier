/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NotasAdicionalesMgl;
import javax.ejb.Local;

/**
 *
 * @author becerraarmr
 */
@Local
public interface NotasAdicionalesMglFacadeLocal 
        extends BaseFacadeLocal<NotasAdicionalesMgl>{
  
     @Override
     public NotasAdicionalesMgl create(NotasAdicionalesMgl notasAdicionalesMgl)throws ApplicationException;
  }
