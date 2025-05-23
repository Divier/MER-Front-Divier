/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.migracion.beans;

import co.com.claro.mgl.mbeans.vt.migracion.beans.enums.CierreMasivo;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author becerraarmr
 */
public class CierreMasivoBean {

  /**
   * Creates a new instance of CierreMasivoBean
   */
  public CierreMasivoBean() {
  }
  public String prepareCierreMasivo(){
    cleanAttributeSession();
    return "/view/MGL/VT/Migracion/Solicitudes/CerrarMasivamente.xhtml?faces-redirect=true";
  }
  
  public String getResultado() {
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);
    return (String)session.getAttribute(CierreMasivo.RESPUESTA_CM.getValor());
  }
  
  public String getNameFile() {
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);
    return (String)session.getAttribute(CierreMasivo.NAME_FILE_CM.getValor());
  }
  
  public Integer getSizeFile() {
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);
    return (Integer)session.getAttribute(CierreMasivo.SIZE_FILE_CM.getValor());
  }
  
  private void cleanAttributeSession(){
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);
    
    session.setAttribute(CierreMasivo.RESPUESTA_CM.getValor(), "");
    session.setAttribute(CierreMasivo.NAME_FILE_CM.getValor(), "");
  }
  
}