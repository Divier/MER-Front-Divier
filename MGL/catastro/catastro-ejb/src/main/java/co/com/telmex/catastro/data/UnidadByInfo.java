/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.data;

import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import net.telmex.pcml.service.SuscriberVO;

/**
 *
 * @author diazjuar
 */
@XmlRootElement(name = "UnidadByInfo")
public class UnidadByInfo  implements Serializable {
  
 private  UnidadStructPcml unidadStructPcml;
 private  SuscriberVO Cuenta;
 private  String mensaje;
 private String  Estado;

  public String getEstado() {
    return Estado;
  }

  public void setEstado(String Estado) {
    this.Estado = Estado;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public UnidadStructPcml getUnidadStructPcml() {
    return unidadStructPcml;
  }

  public void setUnidadStructPcml(UnidadStructPcml unidadStructPcml) {
    this.unidadStructPcml = unidadStructPcml;
  }

  public SuscriberVO getCuenta() {
    return Cuenta;
  }

  public void setCuenta(SuscriberVO Cuenta) {
    this.Cuenta = Cuenta;
  }


 
}
