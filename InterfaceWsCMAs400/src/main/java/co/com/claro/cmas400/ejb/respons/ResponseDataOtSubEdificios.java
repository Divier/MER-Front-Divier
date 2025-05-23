/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.respons;

import java.util.regex.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@XmlRootElement
public class ResponseDataOtSubEdificios {

    @XmlElement
    private String tipoTrabajo = "";
    @XmlElement
    private String nombreTipoTrabajo = "";
    @XmlElement
    private String codigoCuenta = "";
    @XmlElement
    private String nombreCuenta = "";
    @XmlElement
    private String numeroTrabajo = "";
    @XmlElement
    private String tipoEdificio = "";
    @XmlElement
    private String nombreTipoEdificio = "";
    @XmlElement
    private String nombreEstado = "";
    @XmlElement
    private String direccion = "";
    @XmlElement
    private String telefono1 = "";
    @XmlElement
    private String telefono2 = "";
    @XmlElement
    private String contacto = "";
    @XmlElement
    private String totalUnidades = "";
    @XmlElement
    private String totalHomePassed = "";
    @XmlElement
    private String usuarioGrabacion = "";
    @XmlElement
    private String dealer = "";
    @XmlElement
    private String nombreDealer = "";
    @XmlElement
    private String observacion1 = "";
    @XmlElement
    private String observacion2 = "";
    @XmlElement
    private String observacion3 = "";
    @XmlElement
    private String observacion4 = "";
    @XmlElement
    private String observacion5 = "";
    @XmlElement
    private String observacion6 = "";
    @XmlElement
    private String observacion7 = "";
    @XmlElement
    private String arregloCodigo = "";
    @XmlElement
    private String arregloNombre = "";
    @XmlElement
    private String fechaProgramacion = "";
    @XmlElement
    private String horaProgramacion = "";
    @XmlElement
    private String tecnicoOt = "";
    @XmlElement
    private String resultadoModificacion = "";
    @XmlElement
    private String clase = "";
    @XmlElement
    private String fabrica = "";
    @XmlElement
    private String serie = "";
    @XmlElement
    private String estado = "";
    
    @XmlElement
    private String nitCliente= "";
    @XmlElement
    private String numeroOTPadre= "";
    @XmlElement
    private String codigoServicio= "";
    @XmlElement
    private String nombreCliente= "";
    @XmlElement
    private String segmento= "";
    @XmlElement
    private String nombreOTHija= "";
    @XmlElement
    private String contactoOTPadre= "";
    @XmlElement
    private String tipoServicio= "";
    @XmlElement
    private String numeroOThija= "";
    @XmlElement
    private String servicios= "";

    public ResponseDataOtSubEdificios() {
    }

    public ResponseDataOtSubEdificios(String responseString) {
        String[] spliter;
        spliter = responseString.split(Pattern.quote("|"));
        if (spliter.length > 0) {
            tipoTrabajo = spliter[0];
            nombreTipoTrabajo = spliter[1];
            codigoCuenta = spliter[2];
            nombreCuenta = spliter[3];
            numeroTrabajo = spliter[4];
            tipoEdificio = spliter[5];
            nombreTipoEdificio = spliter[6];
            nombreEstado = spliter[7];
            direccion = spliter[8];
            telefono1 = spliter[9];
            telefono2 = spliter[10];
            contacto = spliter[11];
            totalUnidades = spliter[12];
            totalHomePassed = spliter[13];
            usuarioGrabacion = spliter[14];
            dealer = spliter[15];
            nombreDealer = spliter[16];
            observacion1 = spliter[17];
            observacion2 = spliter[18];
            observacion3 = spliter[19];
            observacion4 = spliter[20];
            observacion5 = spliter[21];
            observacion6 = spliter[22];
            observacion7 = spliter[23];
            arregloCodigo = spliter[24];
            arregloNombre = spliter[25];
            fechaProgramacion = spliter[26];
            horaProgramacion = spliter[27];
            tecnicoOt = spliter[28];
            resultadoModificacion = spliter[29];
            clase = spliter[30];
            fabrica = spliter[31];
            serie = spliter[32];
            estado = spliter[33];
            if (spliter.length > 34) {
                nitCliente = spliter[34];
                numeroOTPadre = spliter[35];
                nombreCliente = spliter[36];
                segmento = spliter[37];
                numeroOThija = spliter[38];
            }
        }
    }

    public String getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(String tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public String getNombreTipoTrabajo() {
        return nombreTipoTrabajo;
    }

    public void setNombreTipoTrabajo(String nombreTipoTrabajo) {
        this.nombreTipoTrabajo = nombreTipoTrabajo;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public String getNumeroTrabajo() {
        return numeroTrabajo;
    }

    public void setNumeroTrabajo(String numeroTrabajo) {
        this.numeroTrabajo = numeroTrabajo;
    }

    public String getTipoEdificio() {
        return tipoEdificio;
    }

    public void setTipoEdificio(String tipoEdificio) {
        this.tipoEdificio = tipoEdificio;
    }

    public String getNombreTipoEdificio() {
        return nombreTipoEdificio;
    }

    public void setNombreTipoEdificio(String nombreTipoEdificio) {
        this.nombreTipoEdificio = nombreTipoEdificio;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTotalUnidades() {
        return totalUnidades;
    }

    public void setTotalUnidades(String totalUnidades) {
        this.totalUnidades = totalUnidades;
    }

    public String getTotalHomePassed() {
        return totalHomePassed;
    }

    public void setTotalHomePassed(String totalHomePassed) {
        this.totalHomePassed = totalHomePassed;
    }

    public String getUsuarioGrabacion() {
        return usuarioGrabacion;
    }

    public void setUsuarioGrabacion(String usuarioGrabacion) {
        this.usuarioGrabacion = usuarioGrabacion;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getNombreDealer() {
        return nombreDealer;
    }

    public void setNombreDealer(String nombreDealer) {
        this.nombreDealer = nombreDealer;
    }

    public String getObservacion1() {
        return observacion1;
    }

    public void setObservacion1(String observacion1) {
        this.observacion1 = observacion1;
    }

    public String getObservacion2() {
        return observacion2;
    }

    public void setObservacion2(String observacion2) {
        this.observacion2 = observacion2;
    }

    public String getObservacion3() {
        return observacion3;
    }

    public void setObservacion3(String observacion3) {
        this.observacion3 = observacion3;
    }

    public String getObservacion4() {
        return observacion4;
    }

    public void setObservacion4(String observacion4) {
        this.observacion4 = observacion4;
    }

    public String getObservacion5() {
        return observacion5;
    }

    public void setObservacion5(String observacion5) {
        this.observacion5 = observacion5;
    }

    public String getObservacion6() {
        return observacion6;
    }

    public void setObservacion6(String observacion6) {
        this.observacion6 = observacion6;
    }

    public String getObservacion7() {
        return observacion7;
    }

    public void setObservacion7(String observacion7) {
        this.observacion7 = observacion7;
    }

    public String getArregloCodigo() {
        return arregloCodigo;
    }

    public void setArregloCodigo(String arregloCodigo) {
        this.arregloCodigo = arregloCodigo;
    }

    public String getArregloNombre() {
        return arregloNombre;
    }

    public void setArregloNombre(String arregloNombre) {
        this.arregloNombre = arregloNombre;
    }

    public String getFechaProgramacion() {
        return fechaProgramacion;
    }

    public void setFechaProgramacion(String fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion;
    }

    public String getHoraProgramacion() {
        return horaProgramacion;
    }

    public void setHoraProgramacion(String horaProgramacion) {
        this.horaProgramacion = horaProgramacion;
    }

    public String getTecnicoOt() {
        return tecnicoOt;
    }

    public void setTecnicoOt(String tecnicoOt) {
        this.tecnicoOt = tecnicoOt;
    }

    public String getResultadoModificacion() {
        return resultadoModificacion;
    }

    public void setResultadoModificacion(String resultadoModificacion) {
        this.resultadoModificacion = resultadoModificacion;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getFabrica() {
        return fabrica;
    }

    public void setFabrica(String fabrica) {
        this.fabrica = fabrica;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getNumeroOTPadre() {
        return numeroOTPadre;
    }

    public void setNumeroOTPadre(String numeroOTPadre) {
        this.numeroOTPadre = numeroOTPadre;
    }

    public String getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(String codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getNombreOTHija() {
        return nombreOTHija;
    }

    public void setNombreOTHija(String nombreOTHija) {
        this.nombreOTHija = nombreOTHija;
    }

    public String getContactoOTPadre() {
        return contactoOTPadre;
    }

    public void setContactoOTPadre(String contactoOTPadre) {
        this.contactoOTPadre = contactoOTPadre;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getNumeroOThija() {
        return numeroOThija;
    }

    public void setNumeroOThija(String numeroOThija) {
        this.numeroOThija = numeroOThija;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

  
}
