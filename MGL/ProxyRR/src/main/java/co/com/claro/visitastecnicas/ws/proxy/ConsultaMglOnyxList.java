/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.visitastecnicas.ws.proxy;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement(name = "consultaMglOnyxList")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaMglOnyxList", propOrder = {
    "NIT_Cliente",
    "Nombre",
    "Nombre_OT_Hija",
    "OTP",
    "OTH",
    "FechaCreacionOTH",
    "FechaCreacionOTP",
    "NombreContactoTecnicoPredOTP",
    "TelefonoContactoTecnicoPredOTP",
    "EmailContactTecnicoPredOTP",
    "DireccionFacturacion",
    "CiudadFacturacion",
    "Descripcion",
    "Segmento",
    "SolucionPrev",
    "Servicios",
    "RecurrenteMensual",
    "CodigoServicio",
    "Vendedor",
    "Telefono",
    "NotasOTH",
    "EstadoOTH",
    "EstadoOTP",
    "FechaCompromisoOTP",
    "CodResolucion1OTP",
    "CodResolucion2OTP",
    "CodResolucion3OTP",
    "CodResolucion4OTP",
    "CodResolucion1OTH",
    "CodResolucion2OTH",
    "CodResolucion3OTH",
    "CodResolucion4OTH",
    "CodProyecto",
    "RegionalDestino",
    "AliadoImplementacion"
   
})
public class ConsultaMglOnyxList {

    @XmlElement(name = "NIT_Cliente")
    protected String NIT_Cliente;

    @XmlElement(name = "Nombre")
    protected String Nombre;

    @XmlElement(name = "Nombre_OT_Hija")
    protected String Nombre_OT_Hija;

    @XmlElement(name = "OTP")
    protected int OTP;

    @XmlElement(name = "OTH")
    protected int OTH;

    @XmlElement(name = "FechaCreacionOTH")
    protected String FechaCreacionOTH;

    @XmlElement(name = "FechaCreacionOTP")
    protected String FechaCreacionOTP;

    @XmlElement(name = "NombreContactoTecnicoPredOTP")
    protected String NombreContactoTecnicoPredOTP;

    @XmlElement(name = "TelefonoContactoTecnicoPredOTP")
    protected String TelefonoContactoTecnicoPredOTP;

    @XmlElement(name = "Descripcion")
    protected String Descripcion;

    @XmlElement(name = "DireccionFacturacion")
    protected String DireccionFacturacion;

    @XmlElement(name = "Segmento")
    protected String Segmento;

    @XmlElement(name = "SolucionPrev")
    protected String SolucionPrev;

    @XmlElement(name = "Servicios")
    protected String Servicios;

    @XmlElement(name = "RecurrenteMensual")
    protected BigDecimal RecurrenteMensual;

    @XmlElement(name = "CodigoServicio")
    protected String CodigoServicio;

    @XmlElement(name = "Vendedor")
    protected String Vendedor;

    @XmlElement(name = "Telefono")
    protected String Telefono;

    @XmlElement(name = "NotasOTH")
    protected String NotasOTH;

    @XmlElement(name = "EstadoOTH")
    protected String EstadoOTH;

    @XmlElement(name = "EstadoOTP")
    protected String EstadoOTP;

    @XmlElement(name = "FechaCompromisoOTP")
    protected String FechaCompromisoOTP;

    @XmlElement(name = "CodResolucion1OTP")
    protected String CodResolucion1OTP;

    @XmlElement(name = "CodResolucion2OTP")
    protected String CodResolucion2OTP;

    @XmlElement(name = "CodResolucion3OTP")
    protected String CodResolucion3OTP;

    @XmlElement(name = "CodResolucion4OTP")
    protected String CodResolucion4OTP;

    @XmlElement(name = "CodResolucion1OTH")
    protected String CodResolucion1OTH;

    @XmlElement(name = "CodResolucion2OTH")
    protected String CodResolucion2OTH;

    @XmlElement(name = "CodResolucion3OTH")
    protected String CodResolucion3OTH;

    @XmlElement(name = "CodResolucion4OTH")
    protected String CodResolucion4OTH;
    
    @XmlElement(name = "CodProyecto")
    protected String CodProyecto;
    
    @XmlElement(name = "RegionalDestino")
    protected String RegionalDestino;
    
    @XmlElement(name = "AliadoImplementacion")
    protected String AliadoImplementacion;

    @XmlElement(name = "EmailContactTecnicoPredOTP")
    protected String EmailContactTecnicoPredOTP;
    
    @XmlElement(name = "CiudadFacturacion")
    protected String CiudadFacturacion;
    
    public String getNIT_Cliente() {
        return NIT_Cliente;
    }

    public void setNIT_Cliente(String NIT_Cliente) {
        this.NIT_Cliente = NIT_Cliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getNombre_OT_Hija() {
        return Nombre_OT_Hija;
    }

    public void setNombre_OT_Hija(String Nombre_OT_Hija) {
        this.Nombre_OT_Hija = Nombre_OT_Hija;
    }

    public int getOTP() {
        return OTP;
    }

    public void setOTP(int OTP) {
        this.OTP = OTP;
    }

    public int getOTH() {
        return OTH;
    }

    public void setOTH(int OTH) {
        this.OTH = OTH;
    }

    public String getFechaCreacionOTH() {
        return FechaCreacionOTH;
    }

    public void setFechaCreacionOTH(String FechaCreacionOTH) {
        this.FechaCreacionOTH = FechaCreacionOTH;
    }

    public String getFechaCreacionOTP() {
        return FechaCreacionOTP;
    }

    public void setFechaCreacionOTP(String FechaCreacionOTP) {
        this.FechaCreacionOTP = FechaCreacionOTP;
    }

    public String getNombreContactoTecnicoPredOTP() {
        return NombreContactoTecnicoPredOTP;
    }

    public void setNombreContactoTecnicoPredOTP(String NombreContactoTecnicoPredOTP) {
        this.NombreContactoTecnicoPredOTP = NombreContactoTecnicoPredOTP;
    }

    public String getTelefonoContactoTecnicoPredOTP() {
        return TelefonoContactoTecnicoPredOTP;
    }

    public void setTelefonoContactoTecnicoPredOTP(String TelefonoContactoTecnicoPredOTP) {
        this.TelefonoContactoTecnicoPredOTP = TelefonoContactoTecnicoPredOTP;
    }

    public String getAliadoImplementacion() {
        return AliadoImplementacion;
    }

    public void setAliadoImplementacion(String AliadoImplementacion) {
        this.AliadoImplementacion = AliadoImplementacion;
    }

    public String getEmailContactTecnicoPredOTP() {
        return EmailContactTecnicoPredOTP;
    }

    public void setEmailContactTecnicoPredOTP(String EmailContactTecnicoPredOTP) {
        this.EmailContactTecnicoPredOTP = EmailContactTecnicoPredOTP;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

   

    public String getSegmento() {
        return Segmento;
    }

    public void setSegmento(String Segmento) {
        this.Segmento = Segmento;
    }

    public String getServicios() {
        return Servicios;
    }

    public void setServicios(String Servicios) {
        this.Servicios = Servicios;
    }

    public BigDecimal getRecurrenteMensual() {
        return RecurrenteMensual;
    }

    public void setRecurrenteMensual(BigDecimal RecurrenteMensual) {
        this.RecurrenteMensual = RecurrenteMensual;
    }

    public String getCodigoServicio() {
        return CodigoServicio;
    }

    public void setCodigoServicio(String CodigoServicio) {
        this.CodigoServicio = CodigoServicio;
    }

    public String getVendedor() {
        return Vendedor;
    }

    public void setVendedor(String Vendedor) {
        this.Vendedor = Vendedor;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getNotasOTH() {
        return NotasOTH;
    }

    public void setNotasOTH(String NotasOTH) {
        this.NotasOTH = NotasOTH;
    }

    public String getEstadoOTH() {
        return EstadoOTH;
    }

    public void setEstadoOTH(String EstadoOTH) {
        this.EstadoOTH = EstadoOTH;
    }

    public String getEstadoOTP() {
        return EstadoOTP;
    }

    public void setEstadoOTP(String EstadoOTP) {
        this.EstadoOTP = EstadoOTP;
    }

    public String getFechaCompromisoOTP() {
        return FechaCompromisoOTP;
    }

    public void setFechaCompromisoOTP(String FechaCompromisoOTP) {
        this.FechaCompromisoOTP = FechaCompromisoOTP;
    }

    public String getCodResolucion1OTP() {
        return CodResolucion1OTP;
    }

    public void setCodResolucion1OTP(String CodResolucion1OTP) {
        this.CodResolucion1OTP = CodResolucion1OTP;
    }

    public String getCodResolucion2OTP() {
        return CodResolucion2OTP;
    }

    public void setCodResolucion2OTP(String CodResolucion2OTP) {
        this.CodResolucion2OTP = CodResolucion2OTP;
    }

    public String getCodResolucion3OTP() {
        return CodResolucion3OTP;
    }

    public void setCodResolucion3OTP(String CodResolucion3OTP) {
        this.CodResolucion3OTP = CodResolucion3OTP;
    }

    public String getCodResolucion4OTP() {
        return CodResolucion4OTP;
    }

    public void setCodResolucion4OTP(String CodResolucion4OTP) {
        this.CodResolucion4OTP = CodResolucion4OTP;
    }

    public String getCodResolucion1OTH() {
        return CodResolucion1OTH;
    }

    public void setCodResolucion1OTH(String CodResolucion1OTH) {
        this.CodResolucion1OTH = CodResolucion1OTH;
    }

    public String getCodResolucion2OTH() {
        return CodResolucion2OTH;
    }

    public void setCodResolucion2OTH(String CodResolucion2OTH) {
        this.CodResolucion2OTH = CodResolucion2OTH;
    }

    public String getCodResolucion3OTH() {
        return CodResolucion3OTH;
    }

    public void setCodResolucion3OTH(String CodResolucion3OTH) {
        this.CodResolucion3OTH = CodResolucion3OTH;
    }

    public String getCodResolucion4OTH() {
        return CodResolucion4OTH;
    }

    public void setCodResolucion4OTH(String CodResolucion4OTH) {
        this.CodResolucion4OTH = CodResolucion4OTH;
    }

    public String getCodProyecto() {
        return CodProyecto;
    }

    public void setCodProyecto(String CodProyecto) {
        this.CodProyecto = CodProyecto;
    }

    public String getRegionalDestino() {
        return RegionalDestino;
    }

    public void setRegionalDestino(String RegionalDestino) {
        this.RegionalDestino = RegionalDestino;
    }


    public String getDireccionFacturacion() {
        return DireccionFacturacion;
    }

    public void setDireccionFacturacion(String DireccionFacturacion) {
        this.DireccionFacturacion = DireccionFacturacion;
    }

    public String getSolucionPrev() {
        return SolucionPrev;
    }

    public void setSolucionPrev(String SolucionPrev) {
        this.SolucionPrev = SolucionPrev;
    }

    public String getCiudadFacturacion() {
        return CiudadFacturacion;
    }

    public void setCiudadFacturacion(String CiudadFacturacion) {
        this.CiudadFacturacion = CiudadFacturacion;
    }
}