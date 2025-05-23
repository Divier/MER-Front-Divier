package co.com.claro.visitasTecnicas.entities;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import net.telmex.pcml.service.ServiceStruct;

/**
 *
 * @author Orlando Velasquez
 */
@XmlRootElement(name = "HhppResponseQueryRegularUnitRR")
public class HhppResponseQueryRegularUnitRR {

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
    @XmlElement(name = "fechaAuditoria")
    private String fechaAuditoria;
    @XmlElement(name = "edificio")
    private String tipoMensaje;
    @XmlElement(name = "edificio")
    private String edificio;
    @XmlElement(name = "vendedor")
    private String vendedor;
    @XmlElement(name = "tipoAcometida")
    private String tipoAcometida;
    @XmlElement(name = "tipoCblAcometida")
    private String tipoCblAcometida;
    @XmlElement(name = "headEnd")
    private String headEnd;
    @XmlElement(name = "tipo")
    private String tipo;
    @XmlElement(name = "nodo")
    private String nodo;
    @XmlElement(name = "ultimaUbicacion")
    private String ultimaUbicacion;
    @XmlElement(name = "codigoPostal")
    private String codigoPostal;
    @XmlElement(name = "estrato")
    private String estrato;
    @XmlElement(name = "tipoTecnologiaHabilitada")
    private String tipoTecnologiaHabilitada;

    public HhppResponseQueryRegularUnitRR() {
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

    public String getFechaAuditoria() {
        return fechaAuditoria;
    }

    public void setFechaAuditoria(String fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getTipoAcometida() {
        return tipoAcometida;
    }

    public void setTipoAcometida(String tipoAcometida) {
        this.tipoAcometida = tipoAcometida;
    }

    public String getTipoCblAcometida() {
        return tipoCblAcometida;
    }

    public void setTipoCblAcometida(String tipoCblAcometida) {
        this.tipoCblAcometida = tipoCblAcometida;
    }

    public String getHeadEnd() {
        return headEnd;
    }

    public void setHeadEnd(String headEnd) {
        this.headEnd = headEnd;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getUltimaUbicacion() {
        return ultimaUbicacion;
    }

    public void setUltimaUbicacion(String ultimaUbicacion) {
        this.ultimaUbicacion = ultimaUbicacion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    public String getTipoTecnologiaHabilitada() {
        return tipoTecnologiaHabilitada;
    }

    public void setTipoTecnologiaHabilitada(String tipoTecnologiaHabilitada) {
        this.tipoTecnologiaHabilitada = tipoTecnologiaHabilitada;
    }
    
    
    

}
