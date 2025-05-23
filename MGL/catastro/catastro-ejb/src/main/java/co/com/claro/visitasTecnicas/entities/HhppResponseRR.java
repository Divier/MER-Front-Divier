package co.com.claro.visitasTecnicas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import net.telmex.pcml.service.ServiceStruct;

/**
 *
 * @author diazjuar
 */
@XmlRootElement(name = "HhppResponseRR")
public class HhppResponseRR implements Serializable{
  
  @XmlElement(name = "mensaje")
  private String mensaje;
  @XmlElement(name = "street")
  private String street;
  @XmlElement(name = "house")
  private String house;
  @XmlElement(name = "apartamento")
  private String apartamento;
  @XmlElement(name = "wOrder")
  private String wOrder;
  @XmlElement(name = "longitud")
  private String longitud;
  @XmlElement(name = "latitud")
  private String latitud;
  @XmlElement(name = "services")
  private ArrayList<ServiceStruct> services;
  @XmlElement(name = "estadoUnidad")
  private String estadoUnidad;
  @XmlElement(name = "existenciaUnidad")
  private boolean existenciaUnidad;
  @XmlElement(name = "cuenta")
  private String cuenta;
  @XmlElement(name = "comunidad")
  private String comunidad;
  @XmlElement(name = "division")
  private String division;
  @XmlElement(name = "tipoMensaje")
  private String tipoMensaje;
  
  
  public HhppResponseRR(){
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getHouse() {
    return house;
  }

  public void setHouse(String house) {
    this.house = house;
  }

  public String getApartamento() {
    return apartamento;
  }

  public void setApartamento(String apartamento) {
    this.apartamento = apartamento;
  }

  public String getwOrder() {
    return wOrder;
  }

  public void setwOrder(String wOrder) {
    this.wOrder = wOrder;
  }

  public String getLongitud() {
    return longitud;
  }

  public void setLongitud(String longitud) {
    this.longitud = longitud;
  }

  public String getLatitud() {
    return latitud;
  }

  public void setLatitud(String latitud) {
    this.latitud = latitud;
  }

  public ArrayList<ServiceStruct> getServices() {
    return services;
  }

  public void setServices(ArrayList<ServiceStruct> services) {
    this.services = services;
  }

  public String getEstadoUnidad() {
    return estadoUnidad;
  }

  public void setEstadoUnidad(String estadoUnidad) {
    this.estadoUnidad = estadoUnidad;
  }

  public boolean isExistenciaUnidad() {
    return existenciaUnidad;
  }

  public void setExistenciaUnidad(boolean existenciaUnidad) {
    this.existenciaUnidad = existenciaUnidad;
  }

  public String getCuenta() {
    return cuenta;
  }

  public void setCuenta(String cuenta) {
    this.cuenta = cuenta;
  }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }
    
    


  
  
  
}
