/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author bocanegravm
 */
public class CmtOnyxResponseDto {
   
  private String NIT_Cliente;

    private String Nombre;

    private String Nombre_OT_Hija;

    private int OTP;

    private int OTH;

    private String FechaCreacionOTH;
    
    private Date fechaFechaOTH;

    private String FechaCreacionOTP;
    
    private Date fechaFechaOTP;

    private String ContactoTecnicoOTP;

    private String TelefonoContacto;

    private String Descripcion;

    private String Direccion;

    private String Segmento;

    private String TipoServicio;

    private String Servicios;

    private BigDecimal RecurrenteMensual;

    private String CodigoServicio;

    private String Vendedor;

    private String Telefono;

    private String NotasOTH;

    private String EstadoOTH;

    private String EstadoOTP;

    private String FechaCompromisoOTP;

    private String CodResolucion1OTP;

    private String CodResolucion2OTP;

    private String CodResolucion3OTP;

    private String CodResolucion4OTP;

    private String CodResolucion1OTH;

    private String CodResolucion2OTH;

    private String CodResolucion3OTH;

    private String CodResolucion4OTH;
    
    private String codProyecto;
    
    private String regionalDestino;
    
    private String aImplement;
    
    private String cellTec;
    
    private String emailCTec;
    
    private String ciudadFacturacion;

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

    public String getContactoTecnicoOTP() {
        return ContactoTecnicoOTP;
    }

    public void setContactoTecnicoOTP(String ContactoTecnicoOTP) {
        this.ContactoTecnicoOTP = ContactoTecnicoOTP;
    }

    public String getTelefonoContacto() {
        return TelefonoContacto;
    }

    public void setTelefonoContacto(String TelefonoContacto) {
        this.TelefonoContacto = TelefonoContacto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getSegmento() {
        return Segmento;
    }

    public void setSegmento(String Segmento) {
        this.Segmento = Segmento;
    }

    public String getTipoServicio() {
        return TipoServicio;
    }

    public void setTipoServicio(String TipoServicio) {
        this.TipoServicio = TipoServicio;
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
        return codProyecto;
    }

    public void setCodProyecto(String codProyecto) {
        this.codProyecto = codProyecto;
    }

    public String getRegionalDestino() {
        return regionalDestino;
    }

    public void setRegionalDestino(String regionalDestino) {
        this.regionalDestino = regionalDestino;
    }

    public String getaImplement() {
        return aImplement;
    }

    public void setaImplement(String aImplement) {
        this.aImplement = aImplement;
    }

    public String getCellTec() {
        return cellTec;
    }

    public void setCellTec(String cellTec) {
        this.cellTec = cellTec;
    }

    public String getEmailCTec() {
        return emailCTec;
    }

    public void setEmailCTec(String emailCTec) {
        this.emailCTec = emailCTec;
    }

    public String getCiudadFacturacion() {
        return ciudadFacturacion;
    }

    public void setCiudadFacturacion(String ciudadFacturacion) {
        this.ciudadFacturacion = ciudadFacturacion;
    }

    public Date getFechaFechaOTH() {
        return fechaFechaOTH;
    }

    public void setFechaFechaOTH(Date fechaFechaOTH) {
        this.fechaFechaOTH = fechaFechaOTH;
    }

    public Date getFechaFechaOTP() {
        return fechaFechaOTP;
    }

    public void setFechaFechaOTP(Date fechaFechaOTP) {
        this.fechaFechaOTP = fechaFechaOTP;
    }
    
    
}
