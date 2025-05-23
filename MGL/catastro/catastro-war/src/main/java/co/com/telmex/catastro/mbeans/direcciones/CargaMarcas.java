package co.com.telmex.catastro.mbeans.direcciones;

import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;

/**
 * Clase CargarMarcas
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
public class CargaMarcas {

    /**
     * 
     */
    public String barrio;
    /**
     * 
     */
    public String ciudad;
    /**
     * 
     */
    public String direccion;
    /**
     * 
     */
    public AddressService addressValidated;
    /**
     * 
     */
    public AddressGeodata addressGeodata;
    /**
     * 
     */
    public String direccion_a_validar;
    /**
     * 
     */
    public boolean existe_direccion;
    /**
     * 
     */
    public String errores_presentados = "";
    /**
     * 
     */
    public String formato_igac;
    /**
     * 
     */
    public String departamento;
    /**
     * 
     */
    public String marcas;
    /**
     * 
     */
    public String complemento;
    /**
     * 
     */
    public String estadoHhp;
    /**
     * 
     */
    public String carrera;
    /**
     * 
     */
    public String comunidad;
    /**
     * 
     */
    public String codigo;

    /**
     * 
     * @return
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * 
     * @param codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * 
     * @return
     */
    public String getComunidad() {
        return comunidad;
    }

    /**
     * 
     * @param comunidad
     */
    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    /**
     * 
     * @return
     */
    public String getCarrera() {
        return carrera;
    }

    /**
     * 
     * @param carrera
     */
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    /**
     * 
     * @return
     */
    public String getEstadoHhp() {
        return estadoHhp;
    }

    /**
     * 
     * @param estadoHhp
     */
    public void setEstadoHhp(String estadoHhp) {
        this.estadoHhp = estadoHhp;
    }

    /**
     * 
     * @return
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * 
     * @param complemento
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    /**
     * 
     */
    public String accion;

    /**
     * 
     * @return
     */
    public String getAccion() {
        return accion;
    }

    /**
     * 
     * @param accion
     */
    public void setAccion(String accion) {
        this.accion = accion;
    }

    /**
     * 
     * @return
     */
    public String getMarcas() {
        return marcas;
    }

    /**
     * 
     * @param marcas
     */
    public void setMarcas(String marcas) {
        this.marcas = marcas;
    }

    /**
     * 
     * @return
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * 
     * @param departamento
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    private Geografico geografico;
    private GeograficoPolitico geograficoPolitico;

    /**
     * 
     * @return
     */
    public Geografico getGeografico() {
        return geografico;
    }

    /**
     * 
     * @param geografico
     */
    public void setGeografico(Geografico geografico) {
        this.geografico = geografico;
    }

    /**
     * 
     * @return
     */
    public GeograficoPolitico getGeograficoPolitico() {
        return geograficoPolitico;
    }

    /**
     * 
     * @param geograficoPolitico
     */
    public void setGeograficoPolitico(GeograficoPolitico geograficoPolitico) {
        this.geograficoPolitico = geograficoPolitico;
    }

    /**
     * 
     * @return
     */
    public String getFormato_igac() {
        return formato_igac;
    }

    /**
     * 
     * @param formato_igac
     */
    public void setFormato_igac(String formato_igac) {
        this.formato_igac = formato_igac;
    }

    /**
     * 
     */
    public CargaMarcas() {
    }

    /**
     * 
     * @return
     */
    public String getBarrio() {
        return barrio;
    }

    /**
     * 
     * @param barrio
     */
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    /**
     * 
     * @return
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * 
     * @param ciudad
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * 
     * @return
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * 
     * @param direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * 
     * @return
     */
    public AddressService getAddressValidated() {
        return addressValidated;
    }

    /**
     * 
     * @param addressValidated
     */
    public void setAddressValidated(AddressService addressValidated) {
        this.addressValidated = addressValidated;
    }

    /**
     * 
     * @return
     */
    public AddressGeodata getAddressGeodata() {
        return addressGeodata;
    }

    /**
     * 
     * @param addressGeodata
     */
    public void setAddressGeodata(AddressGeodata addressGeodata) {
        this.addressGeodata = addressGeodata;
    }

    /**
     * 
     * @return
     */
    public String getDireccion_a_validar() {
        return direccion_a_validar;
    }

    /**
     * 
     * @param direccion_a_validar
     */
    public void setDireccion_a_validar(String direccion_a_validar) {
        this.direccion_a_validar = direccion_a_validar;
    }

    /**
     * 
     * @return
     */
    public boolean getExiste_direccion() {
        return existe_direccion;
    }

    /**
     * 
     * @param existe_direccion
     */
    public void setExiste_direccion(boolean existe_direccion) {
        this.existe_direccion = existe_direccion;
    }

    /**
     * 
     * @return
     */
    public String getErrores_presentados() {
        return errores_presentados;
    }

    /**
     * 
     * @param errores_presentados
     */
    public void setErrores_presentados(String errores_presentados) {
        this.errores_presentados = errores_presentados;
    }

    @Override
    public String toString() {
        return "{Departamento=" + departamento + ", Ciudad=" + ciudad + ",  direccion=" + direccion + ", barrio=" + barrio + ", direccion_a_validar=" + direccion_a_validar + ", errores_presentados=" + errores_presentados + ", accion=" + accion + '}';
    }
}