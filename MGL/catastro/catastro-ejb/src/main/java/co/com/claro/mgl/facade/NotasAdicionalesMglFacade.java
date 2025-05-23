/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.NotasAdicionalesMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NotasAdicionalesMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author becerraarmr
 */
@Stateless
public class NotasAdicionalesMglFacade implements NotasAdicionalesMglFacadeLocal {

  @Override
  public List<NotasAdicionalesMgl> findAll() throws ApplicationException {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public NotasAdicionalesMgl create(NotasAdicionalesMgl notasAdicionalesMgl) 
          throws ApplicationException {
    NotasAdicionalesMglManager namm=new NotasAdicionalesMglManager();
    return namm.create(notasAdicionalesMgl);
  }

  @Override
  public NotasAdicionalesMgl update(NotasAdicionalesMgl t) throws ApplicationException {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean delete(NotasAdicionalesMgl t) throws ApplicationException {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public NotasAdicionalesMgl findById(NotasAdicionalesMgl sqlData) throws ApplicationException {
    throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }
  
}
