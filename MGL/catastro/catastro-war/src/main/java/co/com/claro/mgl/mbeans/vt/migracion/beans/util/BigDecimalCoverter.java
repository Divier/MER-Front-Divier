/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package co.com.claro.mgl.mbeans.vt.migracion.beans.util;

import java.math.BigDecimal;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Objetivo:
 *
 * Descripción:
 * 
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 */
@FacesConverter("bigDecimalConverter")
public class BigDecimalCoverter  implements Converter{

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) {
    BigDecimal bigDecimal=JsfUtil.valorBigDecimal(value);
    if(bigDecimal==null){
       FacesMessage msg = new FacesMessage("No es número válido");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ConverterException(msg); 
    }
    return bigDecimal;
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    return ((BigDecimal) value).toString();
  }

}
