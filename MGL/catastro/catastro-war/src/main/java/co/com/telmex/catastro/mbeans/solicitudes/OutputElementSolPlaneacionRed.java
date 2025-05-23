package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.telmex.catastro.data.AddressService;
import java.util.List;

/**
 * Clase OuputElementSolPlaneacionRed
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
public class OutputElementSolPlaneacionRed {

    //Nombre de columnas
    private String nombre="";
    private String calle="";
    private String placa="";
    private String totalHhpp="0";
    private String aptos="";
    private String locales="0";
    private String oficinas="0";
    private String pisos="";
    private String barrio="";
    private String estrato="";
    private String distribucion="";
    private String validar= "";
    private String nodo;
    private String ciudad= "";
    private String departamento="";
    private AddressService addressValidated;
    private String errores_presentados="";
    private List<String> direcciones;    
    @Override
    public String toString() {
        return "OutputElementSolPlaneacionRed{" +"nombre="+nombre+
                "calle="+calle+
                "placa="+placa+
                "totalHhpp="+totalHhpp+
                "aptos="+aptos+
                "locales="+locales+
                "oficinas="+oficinas+
                "pisos="+pisos+
                "barrio="+barrio+
                "estrato="+estrato+
                "distribucion="+distribucion+
                "validar="+validar+
                "nodo="+nodo+
                "ciudad="+ciudad+
                "addressValidated="+addressValidated+
                "errores_presentados="+errores_presentados+
                 '}';
    }

    /**
     * @return 
     */    
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre
     */    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return 
     */        
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTotalHhpp() {
        return totalHhpp;
    }

    public void setTotalHhpp(String totalHhpp) {
        this.totalHhpp = totalHhpp;
    }

    public String getAptos() {
        return aptos;
    }

    public void setAptos(String aptos) {
        this.aptos = aptos;
    }

    public String getLocales() {
        return locales;
    }

    public void setLocales(String locales) {
        this.locales = locales;
    }

    public String getOficinas() {
        return oficinas;
    }

    public void setOficinas(String oficinas) {
        this.oficinas = oficinas;
    }

    public String getPisos() {
        return pisos;
    }

    public void setPisos(String pisos) {
        this.pisos = pisos;
    }


    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    public String getValidar() {
        return validar;
    }

    public void setValidar(String validar) {
        this.validar = validar;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }








    public AddressService getAddressValidated() {
        return addressValidated;
    }

    public void setAddressValidated(AddressService addressValidated) {
        this.addressValidated = addressValidated;
    }

    public String getErrores_presentados() {
        return errores_presentados;
    }

    public void setErrores_presentados(String errores_presentados) {
        this.errores_presentados +=" ; "+ errores_presentados;
    }

    /**
     * @return the distribucion
     */
    public String getDistribucion() {
        return distribucion;
    }

    /**
     * @param distribucion the distribucion to set
     */
    public void setDistribucion(String distribucion) {
        this.distribucion = distribucion;
    }

    /**
     * @return the barrioc
     */


    /**
     * @return the direccionRR
     */

    /**
     * @param direccionRR the direccionRR to set
     */

    /**
     * @return the direcciones
     */
    public List<String> getDirecciones() {
        return direcciones;
    }

    /**
     * @param direcciones the direcciones to set
     */
    public void setDirecciones(List<String> direcciones) {
        this.direcciones = direcciones;
    }


    /**
     * @return the Departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }


}