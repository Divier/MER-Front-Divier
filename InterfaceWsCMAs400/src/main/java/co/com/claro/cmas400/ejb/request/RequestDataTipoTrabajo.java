/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@XmlRootElement
public class RequestDataTipoTrabajo {

    @XmlElement
    private String codigo = "";
    @XmlElement
    private String nombre = "";
    @XmlElement
    private String notasTrabajo = "";
    @XmlElement
    private String costoPresupuesto = "";
    @XmlElement
    private String costoReal = "";
    @XmlElement
    private String asignacionTecnico = "";
    @XmlElement
    private String razonArreglo = "";
    @XmlElement
    private String asignacionInventario = "";
    @XmlElement
    private String desvinculaInventario = "";    
    @XmlElement
    private String userId = "";

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNotasTrabajo() {
        return notasTrabajo;
    }

    public void setNotasTrabajo(String notasTrabajo) {
        this.notasTrabajo = notasTrabajo;
    }

    public String getCostoPresupuesto() {
        return costoPresupuesto;
    }

    public void setCostoPresupuesto(String costoPresupuesto) {
        this.costoPresupuesto = costoPresupuesto;
    }

    public String getCostoReal() {
        return costoReal;
    }

    public void setCostoReal(String costoReal) {
        this.costoReal = costoReal;
    }

    public String getAsignacionTecnico() {
        return asignacionTecnico;
    }

    public void setAsignacionTecnico(String asignacionTecnico) {
        this.asignacionTecnico = asignacionTecnico;
    }

    public String getRazonArreglo() {
        return razonArreglo;
    }

    public void setRazonArreglo(String razonArreglo) {
        this.razonArreglo = razonArreglo;
    }

    public String getAsignacionInventario() {
        return asignacionInventario;
    }

    public void setAsignacionInventario(String asignacionInventario) {
        this.asignacionInventario = asignacionInventario;
    }

    public String getDesvinculaInventario() {
        return desvinculaInventario;
    }

    public void setDesvinculaInventario(String desvinculaInventario) {
        this.desvinculaInventario = desvinculaInventario;
    }

    public String getUserId(){
      return userId;
    }

    public void setUserId(String userId){
      this.userId = userId;
    }   
    
}
